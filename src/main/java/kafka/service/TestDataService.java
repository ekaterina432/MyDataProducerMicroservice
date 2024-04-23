package kafka.service;

import kafka.model.test.DataTestOptions;

public interface TestDataService {
    void sendMessage(DataTestOptions testOptions);
}
