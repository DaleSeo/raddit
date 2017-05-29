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
        Topic found = repository.findOne(id);
        found.incUp();
    }

    public void downvote(String id) {
        Topic found = repository.findOne(id);
        found.incDown();
    }

    public List<Topic> findTop20() {
        return repository.findTopN(20);
    }

    public Topic findOne(String id) {
    	return repository.findOne(id);
    }

}
