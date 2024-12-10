package kr.co.nextcore.collectormodule.vibesensor.mapper;

import kr.co.nextcore.collectormodule.vibesensor.vo.VibeSensorDataVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface VibeSensorMapper {
    
    int selectSkeyFromSensorId(String sensorId);
    void insertData(VibeSensorDataVo vo);

    VibeSensorDataVo selectThresAndStatus(int skey);

    int selectConveyorMoveStatus(String sKey);

    Map<String, String> selectBefore1MinuteAvg(String sKey);
}
