package seo.dale.raddit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisShardInfo;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() throws URISyntaxException {
        URI redisUri = new URI("redis://h:p4ac808bb2718af0d90f6a4a9c0c8e1758a70cc2384a774cb6bb144f734febb65@ec2-34-224-49-43.compute-1.amazonaws.com:8959");
        return new JedisConnectionFactory(new JedisShardInfo(redisUri));
    }

    @Bean
    public RedisOperations<String, String> stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

//    @Bean
//    public RedisConnectionFactory connectionFactory() throws URISyntaxException {
//        return new JedisConnectionFactory();
//    }

}
