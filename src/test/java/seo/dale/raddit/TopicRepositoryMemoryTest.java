package seo.dale.raddit;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicRepositoryMemoryTest {

    private TopicRepository repository;

    @Before
    public void setUp() {
        repository = new TopicRepositoryMemory();
    }

    @Test
    public void test() {
        assertThat(repository.count()).isZero();
    }

}
