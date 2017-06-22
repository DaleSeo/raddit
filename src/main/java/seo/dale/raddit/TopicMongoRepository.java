package seo.dale.raddit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TopicMongoRepository extends MongoRepository<Topic, String> {

    Page<Topic> findByOrderByUpsDesc(Pageable pageable);

}
