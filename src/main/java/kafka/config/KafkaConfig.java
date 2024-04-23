package kafka.config;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    /**
     * сервер на который будет отравлена информация
     */
    @Value("${spring.kafka.bootstrap-service}")
    private String server;

    private final XML settings;

    /**
     * топик хранит данные о температуре
     */
    @Bean
    public NewTopic temperatureTopic() {
        return TopicBuilder.name("data-temperature")
                .partitions(5)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG,//по истечении 7 дней сообщение удалится из очереди
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();

    }

    /**
     * топик хранит данные о вольтах
     */
    @Bean
    public NewTopic voltageTopic() {
        return TopicBuilder.name("data-voltage")
                .partitions(5)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG,//по истечении 7 дней сообщение удалится из очереди
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();

    }

    /**
     * топик хранит данные о мощности
     */
    @Bean
    public NewTopic powerTopic() {
        return TopicBuilder.name("data-power")
                .partitions(5)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG,//по истечении 7 дней сообщение удалится из очереди
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();

    }


    /**
     * Конфигурация кафки того, кто будет отправлять сообщения в кафку
     */
    @Bean
    SenderOptions<String, Object> senderOptions() {
        Map<String, Object> props = new HashMap<>(3);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, //называет сервер
                server
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,//передаю ключ из producer.xml
                new TextXPath(this.settings, "//keySerializer")
                        .toString()
        );
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,//передаю ключ из producer.xml
                new TextXPath(this.settings, "//valueSerializer")
                        .toString()
        );

        return SenderOptions.create(props);

    }

    /**
     * Отправляет сообщения из DataService
     *
     * @return
     */
    @Bean
    public KafkaSender<String, Object> sender() {
        return KafkaSender.create(senderOptions()); //создается kafka sender
    }


}
