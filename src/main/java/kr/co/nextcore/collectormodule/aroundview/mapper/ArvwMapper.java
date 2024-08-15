package kr.co.nextcore.collectormodule.aroundview.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArvwMapper {

    List<Map<String, Object>> selectArvwGwIp();

    boolean updateNetworkFlagTrue(long skey);

    boolean updateNetworkFlagFalse(long skey);

    void updateArvwStatusFlag(int flag, String sensorId);
}
