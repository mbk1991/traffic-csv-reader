package kr.co.nextcore.collectormodule.netchk.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PingMapper {
     List<Map<String, Object>> selectCenterSensorList();
}
