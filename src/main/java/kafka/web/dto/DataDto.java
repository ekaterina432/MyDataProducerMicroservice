package kafka.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import kafka.model.Data;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class DataDto {

    private Long sensorId;
    @JsonFormat(pattern = "yyyy-MM-dd 'T HH:mm:ss")
    private LocalDateTime timestamp;
    /**
     * значение, которое прислал датчик
     */
    private double measurement;

    /**
     * типы датчиков
     */
    private Data.MeasurementType measurementType;


}
