package seo.dale.raddit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InitialDataLoaderTest {

    private InitialDataLoader dataLoader;

    @Mock
    private TopicRepository repository;

    @Before
    public void setUp() {
        dataLoader = new InitialDataLoader(repository);
    }

    @Test
    public void run() throws Exception {
        when(repository.save(any(Topic.class))).thenReturn(null);
        dataLoader.run();
    }

}