package seo.dale.raddit;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

}
