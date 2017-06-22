package seo.dale.raddit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TopicMongoRepositoryTest {

    @Autowired
    public TopicMongoRepository repository;

    @Test
    public void test() {
        Topic toAdd = new Topic("test");
        repository.save(toAdd);

        Topic found = repository.findOne(toAdd.getId());
        assertThat(found).isEqualTo(toAdd);

        found.incUp();
        repository.save(found);

        repository.delete(found);

        Topic deleted = repository.findOne(found.getId());
        assertThat(deleted).isNull();
    }

    @Test
    public void testFindTopN() throws Exception {
        Page<Topic> topics = repository.findByOrderByUpsDesc(new PageRequest(0, 20));
        System.out.println("#topics.size(): " + topics.getContent().size());
        System.out.println("#topics: " + topics.getContent());
    }

}