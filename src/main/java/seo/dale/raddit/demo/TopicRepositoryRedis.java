package seo.dale.raddit.demo;

import java.util.List;

/**
 * TODO: Implement when Redis is allowed
 */
public class TopicRepositoryRedis implements TopicRepository {

    @Override
    public Topic save(Topic topic) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Topic findOne(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Topic> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long count() {
        throw new UnsupportedOperationException();
    }

}