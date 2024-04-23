package kafka.model.test;

import kafka.model.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * конфигурация для автоматической отправки  случайного сообщения
 */
@NoArgsConstructor
@Getter
@Setter
public class DataTestOptions {
    /**
     * периодичность отправки сообщений
     */
    private int delayInSeconds;

    /**
     * типы измениний которые будут созадваться автоматически
     */
    private Data.MeasurementType[] measurementTypes;


}
