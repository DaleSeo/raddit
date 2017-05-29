package seo.dale.raddit;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicRepositoryMemoryTest {

    private TopicRepository repository;

    @Before
    public void setUp() {
        repository = new TopicRepositoryMemory();
    }

    @Test
    public void test() {
        Long initialCount = repository.count();
        assertThat(initialCount)
                .as("should no topics at the beginning.")
                .isZero();

        Topic toSave = new Topic("test topic");

        repository.save(toSave);
        assertThat(repository.count())
                .as("should count 1 after saving a topic.")
                .isEqualTo(1);

        Topic found = repository.findOne(toSave.getId());
        assertThat(found)
                .as("should be equal to each other.")
                .isEqualTo(toSave);

        repository.save(new Topic("test topic 2"));
        repository.save(new Topic("test topic 3"));

        List<Topic> allTopics = repository.findAll();

        assertThat(allTopics)
                .as("should have the size of 3.")
                .hasSize(3)
                .as("should contain the first saved topic.")
                .contains(toSave);
    }

    @Test
    public void testFindTopN() {
        Random random = new Random();

        int[] ranUps = IntStream.range(0, 20)
                .map(num -> random.nextInt(20))
                .toArray();

        System.out.println(Arrays.toString(ranUps));

        IntStream.of(ranUps)
                .forEach(ups -> repository.save(new Topic("topic with " + ups, ups, 0)));

        List<Topic> top10 = repository.findTopN(10);

        for (Topic topic : top10) {
            System.out.println(topic);
        }

        assertThat(top10)
                .as("should contain 10 topics.")
                .hasSize(10);
    }

}
