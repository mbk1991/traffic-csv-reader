package kr.co.nextcore.collectormodule.weather.service.impl;

import kr.co.nextcore.collectormodule.common.Util;
import kr.co.nextcore.collectormodule.weather.mapper.WeatherMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherAPIService {
    @Value("${custom.weather.api_endpoint}")
    private  String END_POINT;
    @Value("${custom.weather.api_key}")
    private  String SERVICE_KEY;
    private final String PAGE_NO = "1";
    private final String NUM_OF_ROWS = "14";
    private final String DATA_TYPE = "JSON";
    private final String NX = "53";
    private final String NY = "96";
    private String base_date;
    private String base_time;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WeatherMapper mapper;

    public void request(){
        logger.info("*** Weather Schedule ***");

        base_date = Util.getCurrentTime("yyyyMMdd"); //today
        base_time = Util.getCurrentTime("HH00"); //time

        try {
            StringBuilder urlBuilder = new StringBuilder(END_POINT); /*URL*/
            logger.info("url : {}", urlBuilder);
            logger.info("END_POINT = {}", END_POINT);
            logger.info("SERVICE_KEY = {}", SERVICE_KEY);

            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +SERVICE_KEY); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGE_NO, "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUM_OF_ROWS, "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(DATA_TYPE, "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(base_date, "UTF-8")); /*‘21년 6월 28일 발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(base_time, "UTF-8")); /*06시 발표(정시단위) */
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(NX, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(NY, "UTF-8")); /*예보지점의 Y 좌표값*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            logger.info("Response code: {}", conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            Map<String,String> weatherMap = parse(sb.toString());

            //null chk
            if(!(weatherMap.get("POP") == null) && !(weatherMap.get("POP").equals(""))){
                insert(weatherMap);
            }
            rd.close();
            conn.disconnect();
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private Map<String,String> parse(String jsonStr){
        Map<String,String> map = new HashMap<>();
        map.put("getDate", base_date + base_time + "00");

        try {
            JSONParser parser       = new JSONParser();
            JSONObject jsonObject   = (JSONObject)parser.parse(jsonStr);
            JSONObject response     = (JSONObject) jsonObject.get("response");
            JSONObject body         = (JSONObject) response.get("body");
            JSONObject items        = (JSONObject) body.get("items");
            JSONArray  item         = (JSONArray) items.get("item");

            for(int i=0; i<item.size(); i++){
                JSONObject data = (JSONObject) item.get(i);
                map.put((String)data.get("category"), (String)data.get("fcstValue"));
            }

        } catch (ParseException e) {
            logger.info(e.getMessage());
        } catch (Exception e){
            logger.info(e.getMessage());
        }
        return map;
    }

    private int insert(Map<String,String> map){
        int result = mapper.insert(map);
        return result;
    }

}
