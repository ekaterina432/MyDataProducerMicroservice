package kafka.service;

import kafka.model.Data;
import lombok.RequiredArgsConstructor;
import kafka.model.test.DataTestOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TestDataServiceImpl implements TestDataService {
    /**
     * сервис будет триггерится и запускать шедулер каждые 10 секунд
     */
    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();

    private final KafkaDataService kafkaDataService;


    @Override
    public void sendMessage(DataTestOptions testOptions) {
        //если есть хотя 1 тип
        if (testOptions.getMeasurementTypes().length > 0) {
            executorService.scheduleAtFixedRate(() -> { //функция, которая будет отправлять сообщение
                        Data data = new Data();
                        data.setSensorId(
                                (long) getRandomNumber(1, 10)//id от 1 до 10 датчика из которого отправляются данные на сервис
                        );
                        data.setMeasurement(
                                getRandomNumber(15, 100)
                        );

                        data.setMeasurementType(
                                getRandomMeasurementType(
                                        testOptions.getMeasurementTypes()  //типы датчиков
                                )
                        );
                        data.setTimestamp(
                                LocalDateTime.now()
                        );

                        kafkaDataService.send(data);
                    },
                    0, //через сколько начать
                    testOptions.getDelayInSeconds(), //период
                    TimeUnit.SECONDS);//количество секунд
        }

    }

    private double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    private Data.MeasurementType getRandomMeasurementType(Data.MeasurementType[] measurementTypes) {
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }


}
