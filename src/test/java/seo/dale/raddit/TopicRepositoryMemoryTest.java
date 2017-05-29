package seo.dale.raddit;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TopicRepositoryMemoryTest {

    private TopicRepository repository;

    @Before
    public void setUp() {
        Map<String, Topic> topicMap = new HashMap<>();
	    IntStream.range(0, 3)
			    .boxed()
			    .forEach(n -> {
				    Topic topic = new Topic("test");
			    	topicMap.put(topic.getId(), topic);
			    });
    	repository = new TopicRepositoryMemory(topicMap);

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

    @Test
    public void testUpvote() {
        Topic testTopic = new Topic("mock topic content", 20, 0);
        repository.save(testTopic);

        assertThat(testTopic.getUps())
                .as("Before: should be as it was.")
                .isEqualTo(20);

        repository.upvote(testTopic.getId());

        assertThat(testTopic.getUps())
                .as("After: should be one more.")
                .isEqualTo(21);
    }

    @Test
    public void testDownvote() {
        Topic testTopic = new Topic("mock topic content", 0, 30);
        repository.save(testTopic);

        assertThat(testTopic.getDowns())
                .as("Before: should be as it was.")
                .isEqualTo(30);

        repository.downvote(testTopic.getId());

        assertThat(testTopic.getDowns())
                .as("After: should be one more.")
                .isEqualTo(31);
    }

}
