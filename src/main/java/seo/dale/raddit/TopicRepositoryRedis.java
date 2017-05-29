package seo.dale.raddit;

import java.util.List;

/**
 * Topic Repository Redis Implementation
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

    @Override
    public List<Topic> findTopN(int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void upvote(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void downvote(String id) {
        throw new UnsupportedOperationException();
    }

}