package seo.dale.raddit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest{

    @Mock
    private TopicRepository repository;

    @InjectMocks
    private TopicService service;

    @Test
    public void testAddTopic() {
        String toAdd = "test topic content";
        when(repository.save(any(Topic.class))).thenReturn(new Topic(toAdd));
        service.addTopic(toAdd);
        verify(repository).save(any(Topic.class));
    }

    @Test
    public void testUpvote() {
        Topic mockTopic = new Topic("mock topic content");
        mockTopic.setUps(20);
        String mockId = mockTopic.getId();
        when(repository.findOne(mockId)).thenReturn(mockTopic);

        assertThat(mockTopic.getUps())
                .as("Before: should be as it was.")
                .isEqualTo(20);

        service.upvote(mockId);

        assertThat(mockTopic.getUps())
                .as("After: should be one more.")
                .isEqualTo(21);

        verify(repository).findOne(mockId);
    }

    @Test
    public void testDownvote() {
        Topic mockTopic = new Topic("mock topic content");
        mockTopic.setDowns(30);
        String mockId = mockTopic.getId();
        when(repository.findOne(mockId)).thenReturn(mockTopic);

        assertThat(mockTopic.getDowns())
                .as("Before: should be as it was.")
                .isEqualTo(30);

        service.downvote(mockId);

        assertThat(mockTopic.getDowns())
                .as("After: should be one more.")
                .isEqualTo(31);

        verify(repository).findOne(mockId);
    }

}