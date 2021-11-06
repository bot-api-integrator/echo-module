package cc.abro.bai.echomodule.services;

import cc.abro.bai.echomodule.model.dto.AddListener;
import cc.abro.bai.echomodule.model.dto.ReceiveMessage;
import cc.abro.bai.echomodule.model.dto.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EchoService {

    private final KafkaTemplate<String, Object> kafkaTemplateJson;
    private final String addListenerTopic;
    private final String echoModuleTopicName;

    public EchoService(KafkaTemplate<String, Object> kafkaTemplateJson,
                       @Value("${commandRouter.addListenerTopic}") String addListenerTopic,
                       @Value("${echoModuleTopicName}") String echoModuleTopicName) {
        this.kafkaTemplateJson = kafkaTemplateJson;
        this.addListenerTopic = addListenerTopic;
        this.echoModuleTopicName = echoModuleTopicName;
    }

    @PostConstruct
    public void init() {
        kafkaTemplateJson.send(addListenerTopic, new AddListener("", echoModuleTopicName));
    }

    public void echo(ReceiveMessage message) {
        kafkaTemplateJson.send(message.getIntegratorTopic(), new SendMessage(message.getText(), message.getPhotos()));
    }
}
