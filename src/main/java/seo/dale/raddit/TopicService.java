package seo.dale.raddit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicService {

    private final TopicRepository repository;

    @Autowired
    public TopicService(TopicRepository repository) {
        this.repository = repository;
    }

    public void contribute(String content) {
        Topic toSave = new Topic(content);
        repository.save(toSave);
    }

    public void upvote(String id) {
        repository.upvote(id);
    }

    public void downvote(String id) {
        repository.downvote(id);
    }

    public List<Topic> findTop20() {
        return repository.findTopN(20);
    }

    public Topic findOne(String id) {
    	return repository.findOne(id);
    }

}
