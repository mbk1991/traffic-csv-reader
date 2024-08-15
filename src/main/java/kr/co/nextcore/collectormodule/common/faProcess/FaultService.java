package kr.co.nextcore.collectormodule.common.faProcess;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

@Service
public class FaultService {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    FaultMapper mapper;

    @Value("${custom.sensor.faMsg.ip}")
    private String sensorFaProcIp;
    @Value("${custom.sensor.faMsg.port}")
    private String sensorFaProcPort;

    public void sendAlarm(String msg) {
        try(Socket faSocket = new Socket(sensorFaProcIp, Integer.parseInt(sensorFaProcPort));
            DataOutputStream  sendStream  = new DataOutputStream(faSocket.getOutputStream());
            DataInputStream readStream = new DataInputStream(faSocket.getInputStream())) {

            byte[] byteMsg = msg.getBytes("UTF-8");

            sendStream.write(byteMsg);
        } catch (Exception e) {
            logger.error("ERROR: send_alarm(): " + e.getMessage());
        }
    }

    /*"faCode,sKey,ocDate,evtMsg"*/
    public String makeFaultMessage(String faCode, String sKey, String ocDate, String evtMsg){
        StringBuffer faMsg = new StringBuffer();
        faMsg.append(faCode);
        faMsg.append(",");
        faMsg.append(sKey);
        faMsg.append(",");
        faMsg.append(ocDate);
        faMsg.append(",");
        faMsg.append(evtMsg);
        faMsg.append(";");

        return faMsg.toString();
    }

    public String getSensorEventCodeDesc(String code) {
        return mapper.selsectSensorEventCodeDesc(code);
    }
}

