package kr.co.nextcore.collectormodule.vibesensor.controller;

import kr.co.nextcore.collectormodule.vibesensor.service.impl.VibeDataParsingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VibeSensorController {
    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    VibeDataParsingService vibeDataParsingService;
    @PostMapping("/vibeSensorData")
    public String getVibeSensorData(@RequestBody Map<String, Object> data){

//        logger.info(data.toString());
//        Util.logWrite("vibeSensor", data.toString());
        vibeDataParsingService.parse(data);

        return "success";
    }
}
