package kafka.web.controller;

import kafka.model.Data;
import kafka.model.test.DataTestOptions;
import kafka.service.KafkaDataService;
import kafka.service.TestDataService;
import kafka.web.dto.DataDto;
import kafka.web.dto.DataTestOptionsDto;
import kafka.web.mapper.DataMapper;
import kafka.web.mapper.DataTestOptionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data")
@RequiredArgsConstructor
public class DataController {
    private final KafkaDataService kafkaDataService;

    /**
     * Для конвертации даты
     */
    private final DataMapper dataMapper;

    private final DataTestOptionsMapper dataTestOptionsMapper;

    /**
     * тестовый сервис
     */
    private final TestDataService testDataService;


    @PostMapping("/send")
    public void send(@RequestBody DataDto dto) {
        Data data = dataMapper.toEntity(dto);
        kafkaDataService.send(data);
    }

    @PostMapping("/test/send")
    public void testSend(@RequestBody DataTestOptionsDto testOptionsDto) {
        DataTestOptions testOptions = dataTestOptionsMapper.toEntity(testOptionsDto);
        testDataService.sendMessage(testOptions);


    }
}
