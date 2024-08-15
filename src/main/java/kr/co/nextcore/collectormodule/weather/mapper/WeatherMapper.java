package kr.co.nextcore.collectormodule.weather.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface WeatherMapper {
    int insert(Map<String, String> map);
}
