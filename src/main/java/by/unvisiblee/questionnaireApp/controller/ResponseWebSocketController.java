package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.ResponseDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ResponseWebSocketController {

    @MessageMapping("/response")
    @SendTo("/questionnaire/response")
    public ResponseDto sendNewResponse(ResponseDto responseDto) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseDto;
    }
}
