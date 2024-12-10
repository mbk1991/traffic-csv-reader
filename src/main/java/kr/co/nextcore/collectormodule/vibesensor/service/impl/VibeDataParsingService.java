package kr.co.nextcore.collectormodule.vibesensor.service.impl;

import kr.co.nextcore.collectormodule.common.Util;
import kr.co.nextcore.collectormodule.common.faProcess.FaultService;
import kr.co.nextcore.collectormodule.common.faProcess.SensorEventCode;
import kr.co.nextcore.collectormodule.vibesensor.mapper.VibeSensorMapper;
import kr.co.nextcore.collectormodule.vibesensor.vo.VibeSensorDataVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VibeDataParsingService {

    @Autowired
    FaultService fService;
    @Autowired
    VibeSensorMapper mapper;
    Logger logger = LogManager.getLogger(this.getClass());

    public void parse(Map<String, Object> data) {
        logger.info("*** VibeData Receive ***");
        List<VibeSensorDataVo> voList = null;
        String mainId = data.get("mainId") + "";
        String sensorData = "";
        int gw_gubun = mainId.equals("1000") ? 1 : 2;
        String currDate = Util.getCurrentTime("yyyyMMddHHmmss");

        for (int sensorNo = 1; data.containsKey("sensor" + sensorNo); sensorNo++) {
            int sKey = -1;
            try{
                sKey = mapper.selectSkeyFromSensorId(mainId + sensorNo);
            }catch(Exception e){
                //sensor key continue;
                continue;
            }
            if(sKey == -1) continue;

            sensorData = data.get("sensor" + sensorNo).toString();
            if (sensorData.length() < 10){
                //this sensor no data fault
                logger.error("vibe sensor ({}) no data", sKey);
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_SENSOR_NO_DATA);
                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_SENSOR_NO_DATA, sKey+"", currDate, evtMsg);
                fService.sendAlarm(faMsg);
                continue;

            }else{
                logger.info("vibe sensor ({}) get data", sKey);
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_SENSOR_NO_DATA_REC);
                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_SENSOR_NO_DATA_REC, sKey+"", currDate, evtMsg);
                fService.sendAlarm(faMsg);
            }

            voList = setVoList(jsonArrToMapList(sensorData, sKey, gw_gubun));
            if (voList == null) break;
            for (VibeSensorDataVo vo : voList) {
                mapper.insertData(vo);
                //faultCheck
                faChk(vo);
            }
        }
    }


    private List<VibeSensorDataVo> setVoList(List<Map<String, String>> sensorDataMapList) {
        if (sensorDataMapList == null) return null;

        List<VibeSensorDataVo> voList = new ArrayList<>();
        for (Map<String, String> sensorData : sensorDataMapList) {
            voList.add(mapToVo(sensorData));
        }
        return voList;
    }

    private VibeSensorDataVo mapToVo(Map<String, String> sensorData) {
        VibeSensorDataVo vo = new VibeSensorDataVo();

        vo.setsKey(Integer.parseInt(sensorData.get("sKey")));
        vo.setGetDate(Util.getCurrentTime("yyyyMMddHHmmss"));
        vo.setRt(sensorData.get("rt"));
        vo.setTemp(Double.parseDouble(sensorData.get("temp")));
        vo.setVx(Double.parseDouble(sensorData.get("vx")));
        vo.setVy(Double.parseDouble(sensorData.get("vy")));
        vo.setVz(Double.parseDouble(sensorData.get("vz")));
        vo.setAdx(Double.parseDouble(sensorData.get("adx")));
        vo.setAdy(Double.parseDouble(sensorData.get("ady")));
        vo.setAdz(Double.parseDouble(sensorData.get("adz")));
        vo.setDx(Double.parseDouble(sensorData.get("dx")));
        vo.setDy(Double.parseDouble(sensorData.get("dy")));
        vo.setDz(Double.parseDouble(sensorData.get("dz")));
        vo.setHzx(Double.parseDouble(sensorData.get("hzx")));
        vo.setHzy(Double.parseDouble(sensorData.get("hzy")));
        vo.setHzz(Double.parseDouble(sensorData.get("hzz")));
        vo.setGw_gubun(Integer.parseInt(sensorData.get("gw_gubun")));

        return vo;
    }

    private List<Map<String, String>> jsonArrToMapList(String jsonArrStr, int sKey, int gw_gubun) {
        List<Map<String, String>> list = new ArrayList<>();
        if (jsonArrStr.equals("") || jsonArrStr == null || jsonArrStr.equals("[]")) return null;

        jsonArrStr = jsonArrStr.trim();
        if (jsonArrStr.startsWith("[") && jsonArrStr.endsWith("]")) {
            jsonArrStr = jsonArrStr.substring(1, jsonArrStr.length() - 1);

            for (String eachData : jsonArrStr.trim().split("},")) {
                eachData = eachData.trim();
                if (eachData.startsWith("{")) eachData = eachData.replace("{", "");
                if (eachData.endsWith("}")) eachData = eachData.replace("}", "");

                Map<String, String> keyValMap = new HashMap<>();
                for (String keyVal : eachData.trim().split(", ")) {
                    String[] keyValArr = keyVal.trim().split("=");
                    String key = keyValArr[0];
                    String val = keyValArr[1];
                    keyValMap.put(key, val);
                }
                keyValMap.put("sKey", sKey + "");
                keyValMap.put("gw_gubun", gw_gubun + "");
                list.add(keyValMap);
            }
        } else {
            //no jsonArr
            logger.info("no json arr");
        }
        return list;
    }

    private void selectThresAndStatus(VibeSensorDataVo vo) {

        int skey = vo.getsKey();
        VibeSensorDataVo thresAndStatus = mapper.selectThresAndStatus(skey);

        vo.setPsKey(thresAndStatus.getPsKey());
        vo.setFltstatus(thresAndStatus.getFltstatus());

        vo.setTempUpperThres(thresAndStatus.getTempUpperThres());
        vo.setTempLowerThres(thresAndStatus.getTempLowerThres());

        vo.setVxThres(thresAndStatus.getVxThres());
        vo.setVyThres(thresAndStatus.getVyThres());
        vo.setVzThres(thresAndStatus.getVzThres());

        vo.setAdxThres(thresAndStatus.getAdxThres());
        vo.setAdyThres(thresAndStatus.getAdyThres());
        vo.setAdzThres(thresAndStatus.getAdzThres());

        vo.setDxThres(thresAndStatus.getDxThres());
        vo.setDyThres(thresAndStatus.getDyThres());
        vo.setDzThres(thresAndStatus.getDzThres());

        vo.setHzxThres(thresAndStatus.getHzxThres());
        vo.setHzyThres(thresAndStatus.getHzyThres());
        vo.setHzzThres(thresAndStatus.getHzzThres());
    }

    private void faChk(VibeSensorDataVo vo) {
        //vo: output parameter
        selectThresAndStatus(vo);
        String sKey = vo.getsKey() + "";
        int currFltStatus = vo.getFltstatus();
        String getDate = vo.getGetDate();

        /**tmp chk**/
        double currTemp = vo.getTemp();
        double tempUpperThres = vo.getTempUpperThres();
        double tempLowerThres = vo.getTempLowerThres();
        if (currTemp < tempLowerThres || tempUpperThres < currTemp) {
            //fault
            String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_TEMP_THRES_EXEED)
                    + String.format("(%.2f/%.2f/%.2f)",currTemp,tempLowerThres,tempUpperThres);
            String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_TEMP_THRES_EXEED, sKey, getDate, evtMsg);
            fService.sendAlarm(faMsg);

        } else if (tempLowerThres <= currTemp && currTemp <= tempUpperThres) {
            //recovery
            if (currFltStatus != 0) {
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_TEMP_THRES_EXEED_REC)
                        + String.format("(%.2f/%.2f/%.2f)",currTemp,tempLowerThres,tempUpperThres);
                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_TEMP_THRES_EXEED_REC, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);
            }
        }

        /**v chk**/
        double currVx = vo.getVx();
        double currVy = vo.getVy();
        double currVz = vo.getVz();
        double vxThres = vo.getVxThres();
        double vyThres = vo.getVyThres();
        double vzThres = vo.getVzThres();
        if (currVx > vxThres || currVy > vyThres || currVz > vzThres) {
            //fault
            String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_V_THRES_EXEED)
                    + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currVx,currVy,currVz,vxThres,vyThres,vzThres);

            String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_V_THRES_EXEED, sKey, getDate, evtMsg);
            fService.sendAlarm(faMsg);
        } else if (currVx <= vxThres && currVy <= vyThres && currVz <= vzThres) {
            //recovery
            if (currFltStatus != 0) {
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_V_THRES_EXEED_REC)
                        + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currVx,currVy,currVz,vxThres,vyThres,vzThres);

                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_V_THRES_EXEED_REC, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);
            }
        }

        /**ad chk**/
        double currAdx = vo.getAdx();
        double currAdy = vo.getAdy();
        double currAdz = vo.getAdz();
        double adxThres = vo.getAdxThres();
        double adyThres = vo.getAdyThres();
        double adzThres = vo.getAdzThres();
        if (currAdx > adxThres || currAdy > adyThres || currAdz > adzThres) {
            //fault
            String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_AD_THRES_EXEED)
                    + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currAdx,currAdy,currAdz,adxThres,adyThres,adzThres);

            String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_AD_THRES_EXEED, sKey, getDate, evtMsg);
            fService.sendAlarm(faMsg);
        } else if (currAdx <= adxThres && currAdy <= adyThres && currAdz <= adzThres) {
            //recovery
            if (currFltStatus != 0) {
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_AD_THRES_EXEED_REC)
                        + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currAdx,currAdy,currAdz,adxThres,adyThres,adzThres);

                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_AD_THRES_EXEED_REC, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);
            }
        }

        /**d chk**/
        double currDx = vo.getDx();
        double currDy = vo.getDy();
        double currDz = vo.getDz();
        double dxThres = vo.getDxThres();
        double dyThres = vo.getDyThres();
        double dzThres = vo.getDzThres();
        if (currDx > dxThres || currDy > dyThres || currDz > dzThres) {
            //fault
            String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_D_THRES_EXEED)
                    + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currDx,currDy,currDz,dxThres,dyThres,dzThres);

            String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_D_THRES_EXEED, sKey, getDate, evtMsg);
            fService.sendAlarm(faMsg);
        } else if (currDx <= dxThres && currDy <= dyThres && currDz <= dzThres) {
            //recovery
            if (currFltStatus != 0) {
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_D_THRES_EXEED_REC)
                        + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currDx,currDy,currDz,dxThres,dyThres,dzThres);

                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_D_THRES_EXEED_REC, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);
            }
        }

        /**hz chk**/
        double currHzx = vo.getHzx();
        double currHzy = vo.getHzy();
        double currHzz = vo.getHzz();
        double hzxThres = vo.getHzxThres();
        double hzyThres = vo.getHzyThres();
        double hzzThres = vo.getHzzThres();
        if (vo.getPsKey().equals("11")) { // psKey 11:docking , 12:conveyor
            if (currHzx > hzxThres || currHzy > hzyThres || currHzz > hzzThres) {
                //fault
                String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_HZ_THRES_EXEED)
                        + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currHzx,currHzy,currHzz,hzxThres,hzyThres,hzzThres);

                String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_HZ_THRES_EXEED, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);
            } else if (currHzx <= hzxThres && currHzy <= hzyThres && currHzz <= hzzThres) {
                //recovery
                if (currFltStatus != 0) {
                    String evtMsg = fService.getSensorEventCodeDesc(SensorEventCode.VIBE_HZ_THRES_EXEED_REC)
                            + String.format("(%.2f/%.2f/%.2f %.2f/%.2f/%.2f)",currHzx,currHzy,currHzz,hzxThres,hzyThres,hzzThres);

                    String faMsg = fService.makeFaultMessage(SensorEventCode.VIBE_HZ_THRES_EXEED_REC, sKey, getDate, evtMsg);
                    fService.sendAlarm(faMsg);
                }
            }

        } else { //conveyor operation chk
            int conveyorNo = (sKey.equals("16"))? 1:2;
            int flag = mapper.selectConveyorMoveStatus(sKey);
            boolean isMove = flag == 1;

            // before 1minute avg, zero value filtering.
            // 10,0,10,0,10,0  -> (10+0+10+0+10+0) / 3
            Map<String,String> currAvgMap = mapper.selectBefore1MinuteAvg(sKey);
            double hzxAvg = Double.parseDouble(currAvgMap.get("hzxavg"));
            double hzyAvg = Double.parseDouble(currAvgMap.get("hzyavg"));
            double hzzAvg = Double.parseDouble(currAvgMap.get("hzzavg"));


            if ((hzxAvg > hzxThres || hzyAvg > hzyThres || hzzAvg > hzzThres) && !isMove) {
                //conveyor move
                String evtMsg = String.format("%d번 ", conveyorNo) + fService.getSensorEventCodeDesc(SensorEventCode.CONVEYOR_MOVE);
                String faMsg = fService.makeFaultMessage(SensorEventCode.CONVEYOR_MOVE, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);

            } else if ((hzxAvg <= hzxThres && hzyAvg <= hzyThres && hzzAvg <= hzzThres) && isMove) {
                //conveyor stop
                String evtMsg = String.format("%d번 ", conveyorNo) + fService.getSensorEventCodeDesc(SensorEventCode.CONVEYOR_STOP);
                String faMsg = fService.makeFaultMessage(SensorEventCode.CONVEYOR_STOP, sKey, getDate, evtMsg);
                fService.sendAlarm(faMsg);
            }
        }
    }
}
