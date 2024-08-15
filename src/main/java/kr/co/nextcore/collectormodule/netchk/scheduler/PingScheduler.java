package kr.co.nextcore.collectormodule.netchk.scheduler;

import kr.co.nextcore.collectormodule.netchk.service.PingChkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PingScheduler {
    @Autowired
    PingChkService pService;

    @Scheduled(cron="00 */5 * * * *")
    void SensorDevicePingChk(){
        pService.CenterSensorPingCheck();
    }
}
