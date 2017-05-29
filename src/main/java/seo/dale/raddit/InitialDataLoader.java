package seo.dale.raddit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Scanner;

@Component
@Profile("!production")
public class InitialDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);

    private final TopicRepository repository;

    @Autowired
    public InitialDataLoader(TopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(new ClassPathResource("mock_topics.dat").getInputStream());
        Random random = new Random();
        while (scanner.hasNext()) {
            Topic topic = new Topic(scanner.nextLine(), random.nextInt(10), random.nextInt(10));
            logger.info("{}", topic);
            repository.save(topic);
        }
    }

}
