package kr.co.nextcore.collectormodule.weather.scheduler;

import kr.co.nextcore.collectormodule.weather.service.impl.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WeatherScheduler {

    @Autowired
    WeatherAPIService wService;
    @Scheduled(cron = "00 30 2,5,8,11,14,17,20,23 * * *") // 3시간 주기 매 발표 시간이 10분이므로 30분에 수집
    public void getWeatherData(){
        wService.request();
    }
}
