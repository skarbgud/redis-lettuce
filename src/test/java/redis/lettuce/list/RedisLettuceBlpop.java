package redis.lettuce.list;

import io.lettuce.core.KeyValue;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import redis.lettuce.CommandAction;
import redis.lettuce.CommandTemplate;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RedisLettuceBlpop {

    String key = "lettuce:blpop";
    String[] valueAry = "hello,world,message".split(",");


    @Test
    public void blpopTest() {
        // 비동기 실행
        CompletableFuture.runAsync(() -> {
            blpop();
        });
        push();
    }

    public void blpop() {
        CommandAction commandAction = (redisCommands -> {
            while (true) {
                KeyValue<String, String> blpop = redisCommands.blpop(10, key);
                if (blpop != null) {
                    System.out.println("blpop = " + blpop.getValue());
                }
            }
        });

        CommandTemplate.commandAction(commandAction);
    }

    public void push() {
        CommandAction commandAction = (redisCommands -> {
            // rpush
            for (String message : valueAry) {
                Mono.delay(Duration.ofSeconds(4)).block();
                System.out.println("push...");
                redisCommands.rpush(key, message);
            }
        });

        CommandTemplate.commandAction(commandAction);
    }
}
