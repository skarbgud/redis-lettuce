package redis.lettuce.list;

import org.junit.jupiter.api.Test;
import redis.lettuce.CommandAction;
import redis.lettuce.CommandTemplate;

public class RedisLettuceList {

    String key = "lettuce:list";
    String[] valueAry = "a,b,c,d,e".split(",");

    // 선입선출 queue
    @Test
    public void fifo() {
        CommandAction action = (redisCommands -> {
            // push: lpush, rpush
            redisCommands.rpush(key, valueAry);

            // pop: lpop, rpop
            String lpop = null;
            while ((lpop = redisCommands.lpop(key)) != null) {
                System.out.println("lpop = " + lpop);
            }
        });

        CommandTemplate.commandAction(action);
    }

    @Test
    public void lifo() {
        CommandAction action = (redisCommands -> {
            // push: lpush, rpush
            redisCommands.lpush(key, valueAry);

            // pop: lpop, rpop
            String lpop = null;
            while ((lpop = redisCommands.lpop(key)) != null) {
                System.out.println("lpop = " + lpop);
            }
        });

        CommandTemplate.commandAction(action);
    }
}
