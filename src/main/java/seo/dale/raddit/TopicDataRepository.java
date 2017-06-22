package seo.dale.raddit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TopicDataRepository implements TopicRepository {

    @Autowired
    private TopicMongoRepository mongoRepo;

    @Override
    public Topic save(Topic topic) {
        return mongoRepo.save(topic);
    }

    @Override
    public Topic findOne(String id) {
        return mongoRepo.findOne(id);
    }

    @Override
    public List<Topic> findAll() {
        return mongoRepo.findAll();
    }

    @Override
    public Long count() {
        return mongoRepo.count();
    }

    @Override
    public List<Topic> findTopN(int size) {
        Page<Topic> page = mongoRepo.findByOrderByUpsDesc(new PageRequest(0, size));
        return page != null ? page.getContent() : Collections.emptyList();
    }

}
