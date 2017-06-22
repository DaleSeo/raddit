package seo.dale.raddit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
public class RedisTest {

    @Autowired
    private RedisOperations<String, String> template;

    @Test
    public void testValue() {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        valueOperations.set("foo", "bar2");
        System.out.println("#valueOperations.get(\"foo\"):" + valueOperations.get("foo"));
    }

    @Test
    public void testZSet() {
        ZSetOperations<String, String> zSetOperations = template.opsForZSet();

        Arrays.asList("A", "ABC", "BC")
                .stream()
                .forEach(word -> zSetOperations.add("words", word, word.length()));
        Set<String> wordSet = zSetOperations.range("words", 0, -1);
        System.out.println("#wordSet: " + wordSet);
    }

}