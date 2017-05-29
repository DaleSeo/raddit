package seo.dale.raddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Autowired
	private TopicService service;

	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testFindTop20() throws Exception {
		MvcResult result = mvc.perform(
				get("/topics")
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		byte[] responseBytes = result.getResponse().getContentAsByteArray();
		List<Topic> topics = mapper.readValue(responseBytes, List.class);

		assertThat(topics)
				.as("should have the size of 5.")
				.hasSize(service.findTop20().size());
	}


	@Test
	public void testAddTopic() throws Exception {
		mvc.perform(
				post("/topics")
						.content("test")
		)
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	public void testOne() throws Exception {
		Topic testTopic = service.findTop20().get(2);

		MvcResult result = mvc.perform(
				get("/topics/" + testTopic.getId())
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		byte[] responseBytes = result.getResponse().getContentAsByteArray();
		Topic found = mapper.readValue(responseBytes, Topic.class);

		assertThat(found)
				.as("should have the same topics.")
				.isEqualTo(testTopic);
	}

	@Test
	public void testUpVote() throws Exception {
		Topic testTopic = service.findTop20().get(2);
		int before = testTopic.getUps();

		mvc.perform(
				put("/topics/" + testTopic.getId() + "/up")
		)
				.andDo(print())
				.andExpect(status().isNoContent());

		assertThat(service.findOne(testTopic.getId()).getUps())
				.isEqualTo(before + 1);
	}

	@Test
	public void testDownVote() throws Exception {
		Topic testTopic = service.findTop20().get(2);
		int before = testTopic.getDowns();

		mvc.perform(
				put("/topics/" + testTopic.getId() + "/down")
		)
				.andDo(print())
				.andExpect(status().isNoContent());

		assertThat(service.findOne(testTopic.getId()).getDowns())
				.isEqualTo(before + 1);
	}

}