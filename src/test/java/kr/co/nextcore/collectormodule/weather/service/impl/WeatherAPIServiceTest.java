package kr.co.nextcore.collectormodule.weather.service.impl;

import kr.co.nextcore.collectormodule.common.Util;
import org.junit.jupiter.api.Test;

class WeatherAPIServiceTest {
    
    
    @Test
    void 기상청API용날짜포맷(){
        String currentTime = Util.getCurrentTime("yyyyMMdd");
        System.out.println("currentTime = " + currentTime);
    }

}