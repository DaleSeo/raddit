package seo.dale.raddit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
public class TopicRepositoryRedisTest {

    private TopicRepositoryRedis repository;

    @Autowired
    private RedisOperations<String, String> redisTemplate;

    @Before
    public void setUp() {
        repository = new TopicRepositoryRedis(redisTemplate);

        Map<String, Topic> topicMap = IntStream.range(0, 3)
                .boxed()
                .map(n -> new Topic("test"))
                .collect(Collectors.toMap(Topic::getId, topic -> topic));

        repository.setTopicMap(topicMap);
    }

    @Test
    public void testCount() {
        Long initialCount = repository.count();
        assertThat(initialCount)
                .as("should 3 topics at the beginning.")
                .isEqualTo(3);
    }

    @Test
    public void testSaveAndFind() {
        Topic toSave = new Topic("test topic");

        repository.save(toSave);
        assertThat(repository.count())
                .as("should count 4 after saving a topic.")
                .isEqualTo(4);

        Topic found = repository.findOne(toSave.getId());
        assertThat(found)
                .as("should be equal to each other.")
                .isEqualTo(toSave);

        List<Topic> allTopics = repository.findAll();

        assertThat(allTopics)
                .as("should have the size of 4.")
                .hasSize(4)
                .as("should contain the first saved topic.")
                .contains(toSave);
    }

    @Test
    public void testFindTopN() {
        Random random = new Random();

        int[] randNums = IntStream.range(0, 20)
                .map(num -> random.nextInt(20))
                .toArray();

        IntStream.of(randNums)
                .forEach(num -> repository.save(new Topic("topic with " + num, num, 0)));

        List<Topic> top10s = repository.findTopN(10);

        assertThat(top10s)
                .as("should contain 10 topics.")
                .hasSize(10);

        int[] sortedRandNums = IntStream.of(randNums)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(i -> i)
                .toArray();

        for (int i = 0; i < top10s.size(); i++) {
            assertThat(top10s.get(i).getUps())
                    .as("should be sorted by ups in descending order.")
                    .isEqualTo(sortedRandNums[i]);
        }
    }

}