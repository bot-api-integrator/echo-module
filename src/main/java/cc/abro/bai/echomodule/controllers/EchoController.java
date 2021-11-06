package cc.abro.bai.echomodule.controllers;

import cc.abro.bai.echomodule.model.dto.ReceiveMessage;
import cc.abro.bai.echomodule.services.EchoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class EchoController {

    private final EchoService echoService;

    @KafkaListener(topics = "${echoModuleTopicName}", containerFactory = "kafkaListenerFactoryJson")
    public void echo(ReceiveMessage message) {
        System.out.println("Echo: " + message);
        echoService.echo(message);
    }
}
