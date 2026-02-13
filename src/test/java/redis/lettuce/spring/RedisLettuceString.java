package redis.lettuce.spring;

import io.lettuce.core.GetExArgs;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class RedisLettuceString {

    // string
    // set, get, mset, mget
    // incr. decr
    @Test
    public void setGet() {
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withDatabase(0) // 0~15
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> redisCommands = connection.sync();

        String key = "lettuce:string";
        String value = "hello";

        redisCommands.set(key, value);

        String get = redisCommands.get(key);
        System.out.println("get = " + get);

        // TTL time to live
        Long ttl = redisCommands.ttl(key);
        System.out.println("ttl = " + ttl);

        redisCommands.expire(key, Duration.ofMinutes(1));
        System.out.println("ttl = " + redisCommands.ttl(key));

        SetArgs setArgs = SetArgs.Builder
//                .ex(90)
                .keepttl()
                ;
        redisCommands.set(key, value + "_new", setArgs);
        System.out.println("ttl = " + redisCommands.ttl(key));

        // redis 6.2 getDel(조회하면서 삭제), getEx(조회하면서 ttl설정)
        String getDel = redisCommands.getdel(key);
        System.out.println("getDel = " + getDel);
        System.out.println("ttl = " + redisCommands.ttl(key));

        String isDel = redisCommands.get(key);
        System.out.println("isDel = " + isDel);

        String getexKey = "lettuce:getexKey";
        redisCommands.set(getexKey, value);
        GetExArgs getExArgs = GetExArgs.Builder.ex(120);
        String getex = redisCommands.getex(getexKey, getExArgs);
        System.out.println("getex = " + getex);
        System.out.println("ttl = " + redisCommands.ttl(getexKey));
    }
}
