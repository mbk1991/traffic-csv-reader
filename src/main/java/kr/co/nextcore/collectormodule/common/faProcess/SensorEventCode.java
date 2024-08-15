package kr.co.nextcore.collectormodule.common.faProcess;

public class SensorEventCode {
    /**
     * 30001	네트워크(PING) 다운
     * 30002	네트워크(PING) 업
     * 30003	어라운드뷰 카메라 상태 false
     * 30004	어라운드뷰 카메라 상태 true
     * 30005	진동 센서 데이터 미수신
     * 30006	진동 센서 데이터 미수신 해제
     * 30007	진동 온도 임계 초과
     * 30008	진동 온도 임계 초과 복구
     * 30009	진동 속도 임계 초과
     * 30010	진동 속도 임계 초과 복구
     * 30011	진동 각속도 임계 초과
     * 30012	진동 각속도 임계 초과 복구
     * 30013	진동 변위 임계 초과
     * 30014	진동 변위 임계 초과 복구
     * 30015	진동 주파수 임계 초과
     * 30016	진동 주파수 임계 초과 복구
     * 30017	호퍼 센터링 경고
     * 30018	호펴 센터링 경고 해제
     */
    public static final String SHIP_DISTANCE_IN = "10001";
    public static final String SHIP_DISTANCE_OUT = "10002";
    public static final String PING_FAIL = "30001";
    public static final String PING_REC = "30002";
    public static final String ARVW_CAM_FAIL = "30003";
    public static final String ARVW_CAM_REC = "30004";
    public static final String VIBE_SENSOR_NO_DATA = "30005";
    public static final String VIBE_SENSOR_NO_DATA_REC = "30006";
    public static final String VIBE_TEMP_THRES_EXEED = "30007";
    public static final String VIBE_TEMP_THRES_EXEED_REC = "30008";
    public static final String VIBE_V_THRES_EXEED = "30009";
    public static final String VIBE_V_THRES_EXEED_REC = "30010";
    public static final String VIBE_AD_THRES_EXEED = "30011";
    public static final String VIBE_AD_THRES_EXEED_REC = "30012";
    public static final String VIBE_D_THRES_EXEED = "30013";
    public static final String VIBE_D_THRES_EXEED_REC = "30014";
    public static final String VIBE_HZ_THRES_EXEED = "30015";
    public static final String VIBE_HZ_THRES_EXEED_REC = "30016";
    public static final String HOPP_CENTERING_WARN = "30017";
    public static final String HOPP_CENTERING_WARN_REC = "30018";
    public static final String CONVEYOR_MOVE = "30019";
    public static final String CONVEYOR_STOP = "30020";

}
