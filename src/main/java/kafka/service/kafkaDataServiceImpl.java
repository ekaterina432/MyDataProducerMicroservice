package kafka.service;

import kafka.model.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor //для KafkaSender
public class kafkaDataServiceImpl implements KafkaDataService {

    private final KafkaSender<String, Object> sender;

    /**
     * отправка сообщений
     */
    @Override
    public void send(Data data) {
        //определяю в какой топик буду отправлять сообщения
        String topic = switch (data.getMeasurementType()) {
            case TEMPERATURE -> "data-temperature";
            case VOLTAGE -> "data-voltage";
            case POWER -> "data-power";
        };
        //отправка сообщения
        sender.send(Mono.just( //Mono инкапсулирует в себе данные и использует для того, чтобы не дожидаться ответа от кафки
                                SenderRecord.create(
                                        topic,
                                        0,
                                        System.currentTimeMillis(), //фиксирую время отправки
                                        String.valueOf(data.hashCode()), //хеш код обьекта дата в качестве ключа сообщения
                                        data,
                                        null
                                )
                        )
                )
                .subscribe();//подписаться на то, что пришло для выполнения запроса

    }
}
