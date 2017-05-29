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

    public void addTopic(String content) {
        Topic toSave = new Topic(content);
        repository.save(toSave);
    }

    public void upvote(String id) {
        Topic found = repository.findOne(id);
        found.setUps(found.getUps() + 1);
    }

    public void downvote(String id) {
        Topic found = repository.findOne(id);
        found.setDowns(found.getDowns() + 1);
    }

    public List<Topic> findTop20() {
        // TODO: ask repository to sort topics by ups in descending order and return top 20
        return null;
    }

}
