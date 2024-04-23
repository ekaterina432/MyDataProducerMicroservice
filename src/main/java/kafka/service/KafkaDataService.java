package kafka.service;

import kafka.model.Data;

public interface KafkaDataService {
    void send(Data data);
}
