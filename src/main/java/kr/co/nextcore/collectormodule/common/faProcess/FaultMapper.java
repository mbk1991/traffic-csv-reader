package kr.co.nextcore.collectormodule.common.faProcess;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaultMapper {


    String selsectSensorEventCodeDesc(String code);
}
