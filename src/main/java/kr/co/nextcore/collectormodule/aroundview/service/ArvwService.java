package kr.co.nextcore.collectormodule.aroundview.service;

import kr.co.nextcore.collectormodule.aroundview.mapper.ArvwMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArvwService {

    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    ArvwMapper mapper;
    final String[] ARVW_CAM_KEY_ARR = {"CAMERA_STBD_FWD", "CAMERA_PORT_FWD", "CAMERA_PORT_AFT", "CAMERA_STBD_AFT"};

    public void arvwStatusChk() {
        logger.info("*** AroundView Schedule ***");

        //1. select arvw gw
        List<Map<String, Object>> arvwGw = mapper.selectArvwGwIp();
//            arvwGw.stream().forEach(System.out::println);

        //2. ping chk
        for (Map<String, Object> sensor : arvwGw) {
            pingChk(sensor);
        }
        //3. arvw cam chk
        for (Map<String, Object> sensor : arvwGw) {
            if ((sensor.get("network_flag")).equals("1")) { // isReachable
                arvwCamChk(sensor);
            }
        }
    }


    private void arvwCamChk(Map<String, Object> sensor) {
        String svisionPcIp = ((String) sensor.get("ip")).replace("254", "1"); // 100.100.110.254 -> 100.100.110.1

        String requestUrl = "http://" + svisionPcIp + "/sytem_state";

        try {
            URL url = new URL(requestUrl.toString());
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            logger.info("Response code: {}", conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            Map<String, String> arvwStatusMap = arvwStatusParse(sb.toString()); // String to Map

            String pSensorId = (String) sensor.get("sensor_id"); // parant: svisionPC, child: arvwCam

            for (String camKey : ARVW_CAM_KEY_ARR) {
                String camFlag = arvwStatusMap.get(camKey);
                int flag = camFlag.equals("true") ? 1 : 0;
                if (arvwStatusMap.containsKey(camKey)) {
                    String sensorId = pSensorId + "_" + camKey; // make arvwCam sensor_id
                    mapper.updateArvwStatusFlag(flag, sensorId);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * {
     * "timestamp": "2024-04-03T14:09:39",
     * <p>
     * "media": "LIVE",
     * <p>
     * "device_status": {
     * "CAMERA_STBD_FWD": true,
     * "CAMERA_PORT_FWD": true,
     * "CAMERA_PORT_AFT": true,
     * "CAMERA_STBD_AFT": true,
     * "DATA_AIS": true,
     * <p>
     * "DATA_IMU": true
     * },
     * "is_playing": true,
     * "is_streaming_enabled": true,
     * "is_remote_control_enabled": false
     * }
     * <p>
     * ARVW CAM DOWN
     * ARVW CAM UP
     **/
    private Map<String, String> arvwStatusParse(String jsonStr) {
        Map<String, String> map = new HashMap<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonStr);
            JSONObject deviceStatus = (JSONObject) json.get("device_status");

            for (String key : ARVW_CAM_KEY_ARR) {
                if (deviceStatus.containsKey(key)) {
                    String status = (String) deviceStatus.get(key);
                    map.put(key, status);
                }
            }
        } catch (ParseException e) {
            //logger 변경
            logger.info(e.getMessage());
        }

        return map;
    }

    private void pingChk(Map<String, Object> sensor) {
        long skey = (long) sensor.get("skey");
        String ip = (String) sensor.get("ip");
        int network_flag = 0; // false

        try {
            InetAddress inetAddress = Inet4Address.getByName(ip);

            if (inetAddress.isReachable(5000)) {
                //network_flag :1
                logger.info("{} ping ok", ip);
                if (mapper.updateNetworkFlagTrue(skey)) sensor.put("network_flag", 1);
            } else {
                //network_flag :0
                logger.info("{} ping fail", ip);
                mapper.updateNetworkFlagFalse(skey);
            }
            sensor.put("network_flag", network_flag);

        } catch (UnknownHostException e) {
            //logging
            logger.error("ping chk fail : " + e.getMessage());
        } catch (IOException e) {
            logger.error("ping chk fail : timeout : " + e.getMessage());
        }
    }
}
