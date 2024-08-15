package kr.co.nextcore.collectormodule.aroundview.scheduler;

import kr.co.nextcore.collectormodule.aroundview.service.ArvwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class ArroundViewScheduler {


    @Autowired
    ArvwService aService;
    @Scheduled(cron="00 */5 * * * *")
    void getNetworkAndArvwCamStatus(){
        //1. isReachable Chk
        aService.arvwStatusChk();
    }
}
