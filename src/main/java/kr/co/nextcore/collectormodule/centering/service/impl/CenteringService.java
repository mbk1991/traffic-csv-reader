package kr.co.nextcore.collectormodule.centering.service.impl;

import kr.co.nextcore.collectormodule.centering.mapper.CenteringMapper;
import kr.co.nextcore.collectormodule.common.Util;
import kr.co.nextcore.collectormodule.common.faProcess.FaultService;
import kr.co.nextcore.collectormodule.common.faProcess.SensorEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CenteringService {

    @Autowired
    FaultService fService;
    @Autowired
    CenteringMapper mapper;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void insert(Map<String, Object> data) {
        mapper.insert(data);
    }

    public void addData(Map<String, Object> data, String absolutePath, String imageName, String imageType) {
        String getDate = Util.getCurrentTime("yyyyMMddHHmmss");
        Map<String, Object> getSensorData = mapper.selectSKey("hopp_" + data.get("pc"));

        data.put("sKey", getSensorData.get("skey"));
        data.put("fltStatus", getSensorData.get("fltstatus"));
        data.put("getDate", getDate);
        data.put("imagePath", absolutePath);
        data.put("imageName", imageName);
        data.put("imageType", imageType);
        String imageFlag = (!absolutePath.equals("")) ? "T" : "F";
        data.put("imageFlag", imageFlag);

    }

    public void faChk(Map<String, Object> data) {
        String hopperingStatus = (String) data.get("hopperingStatus");
        String fltStatus = data.get("fltStatus").toString();
        String sKey = data.get("sKey").toString();

        //todo: hard cording
        int pcNo = (sKey.equals("18"))? 1:2;

        if (fltStatus.equals("0")) {  // current fltStatus 0:ok, 1:fault
            if (hopperingStatus.equals("F")) {
                //fault
                String evtMsg = String.format("호퍼링 PC%d",pcNo) + fService.getSensorEventCodeDesc(SensorEventCode.HOPP_CENTERING_WARN);
                String faMsg = fService.makeFaultMessage(
                        SensorEventCode.HOPP_CENTERING_WARN,
                        sKey,
                        (String) data.get("getDate"),
                        evtMsg
                );
                fService.sendAlarm(faMsg);
            }
        } else {
            if (hopperingStatus.equals("T")) {
                //recovery
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.HOPP_CENTERING_WARN_REC);
                String faMsg = fService.makeFaultMessage(
                        SensorEventCode.HOPP_CENTERING_WARN_REC,
                        sKey,
                        (String) data.get("getDate"),
                        evtMsg
                );
                fService.sendAlarm(faMsg);
            }
        }
    }
}
