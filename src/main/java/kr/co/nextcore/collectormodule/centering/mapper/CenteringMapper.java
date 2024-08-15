package kr.co.nextcore.collectormodule.centering.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CenteringMapper {
    void insert(Map<String, Object> centerMap);
    Map<String, Object> selectSKey(String sensorId);
}
