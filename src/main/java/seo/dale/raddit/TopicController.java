package seo.dale.raddit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

	private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

	private final TopicService service;

	@Autowired
	public TopicController(TopicService service) {
		this.service = service;
	}

	/**
	 * GET /topics
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Topic> findTop20() {
		return service.findTop20();
	}

	/**
	 * POST /topics
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void contribute(@RequestBody String content) {
		service.addTopic(content);
	}

	/**
	 * GET /topics/{id}
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Topic findOne(@PathVariable String id) {
		return service.findOne(id);
	}

	/**
	 * PUT /topics/{id}/up
	 */
	@RequestMapping(value = "/{id}/up", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void upVote(@PathVariable String id) {
		service.upvote(id);
	}

	/**
	 * PUT /topics/{id}/down
	 */
	@RequestMapping(value = "/{id}/down", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void downVote(@PathVariable String id) {
		service.downvote(id);
	}

	/**
	 * Exception Handling
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(Exception exception) {
		logger.error("Error", exception);
		return exception.getMessage();
	}

}
