package org.superbiz.moviefun;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.util.MimeTypeUtils.TEXT_PLAIN_VALUE;


@RestController
public class RabbitMessageController {
    private final RabbitTemplate rabbitTemplate;
    private final String rabbitQueue;

    public RabbitMessageController(RabbitTemplate rabbitTemplate,
                                   @Value("${rabbitmq.queue}") String rabbitQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitQueue = rabbitQueue;
    }

    @PostMapping(value = {"/rabbit"})
    public Map<String, String> publishMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(rabbitQueue, message);
        Map<String, String> response = new HashMap<>();
        response.put("response", "This is the response");
        return response;
    }
}
