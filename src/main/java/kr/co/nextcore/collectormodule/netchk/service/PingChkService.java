package kr.co.nextcore.collectormodule.netchk.service;

import kr.co.nextcore.collectormodule.common.Util;
import kr.co.nextcore.collectormodule.common.faProcess.FaultService;
import kr.co.nextcore.collectormodule.common.faProcess.SensorEventCode;
import kr.co.nextcore.collectormodule.netchk.mapper.PingMapper;
import kr.co.nextcore.collectormodule.netchk.vo.Sensor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class PingChkService {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    FaultService fService;
    @Autowired
    PingMapper mapper;

    public void CenterSensorPingCheck() {
        logger.info("*** Network Check Schedule ***");
        List<Sensor> sList = selectCenterSensorForPingChk();
        for(Sensor s: sList){
            int sKey = s.getsKey();
            String ip = s.getIp();
            pingChk(sKey, ip);
        }
    }

    private void pingChk(int sKey, String ip) {
        try {
            InetAddress inet = Inet4Address.getByName(ip);
            if(inet.isReachable(3000)){
                logger.info(">>> {} is rechable!", ip);
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.PING_REC);
                String faMsg = fService.makeFaultMessage(SensorEventCode.PING_REC, sKey+"", Util.getCurrentTime("yyyyMMddHHmm00"), evtMsg);
                fService.sendAlarm(faMsg);
            }else{
                logger.info(">>> {} is unrechable!", ip);
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.PING_FAIL);
                String faMsg = fService.makeFaultMessage(SensorEventCode.PING_FAIL, sKey+"", Util.getCurrentTime("yyyyMMddHHmm00"), evtMsg);
                fService.sendAlarm(faMsg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private List<Sensor> selectCenterSensorForPingChk() {
        List<Map<String,Object>> sMapList = mapper.selectCenterSensorList();
        List<Sensor> sList = new ArrayList<>();
        for(Map<String, Object> sensorMap: sMapList){
            Sensor s = new Sensor();
            int sKey = Integer.parseInt(sensorMap.get("skey").toString());
            String ip = (String) sensorMap.get("ip");
            s.setsKey(sKey);
            s.setIp(ip);
            sList.add(s);
            logger.info("network check list : {}({})",sKey, ip);
        }

        return sList;
    }
}
