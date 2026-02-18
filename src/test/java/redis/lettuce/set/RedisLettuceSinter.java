package redis.lettuce.set;

import org.junit.jupiter.api.Test;
import redis.lettuce.CommandAction;
import redis.lettuce.CommandTemplate;

import java.util.Set;

public class RedisLettuceSinter {

    String mainKey = "lettuce:set:";
    String namKey = "nam";
    String gyukey = "gyu";
    String hyeongKey = "hyeong";
    @Test
    public void setAdvanced() {
        CommandAction action = (redisCommands -> {
            //sadd
            redisCommands.sadd(namKey, "1","2","3","5");
            redisCommands.sadd(gyukey, "3","4","5","6");
            redisCommands.sadd(hyeongKey, "4","5","7");

            // smembers
            System.out.println(namKey + " = " + redisCommands.smembers(namKey));
            System.out.println(gyukey + " = " + redisCommands.smembers(gyukey));
            System.out.println(hyeongKey + " = " + redisCommands.smembers(hyeongKey));

            Set<String> sinter = redisCommands.sinter(namKey, gyukey, hyeongKey);
            System.out.println("sinter = " + sinter);

            Set<String> sunion = redisCommands.sunion(namKey, gyukey, hyeongKey);
            System.out.println("sunion = " + sunion);

            Set<String> sdiff = redisCommands.sdiff(namKey, gyukey, gyukey);
            System.out.println("sdiff = " + sdiff);
        });

        CommandTemplate.commandAction(action);
    }
}
