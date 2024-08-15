package kr.co.nextcore.collectormodule.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class RequestTest {
    HttpClient httpClient;
    HttpRequest httpRequest;


    public static void main(String[] args){
        try {
            // HTTP GET 요청을 보낼 URL 설정
            URL url = new URL("http://121.165.242.171:38080/centeringData");

            // HttpURLConnection 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 요청 방식 설정 (GET, POST, PUT 등)
            conn.setRequestMethod("GET");


            // 서버로부터 응답 코드 받기
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 서버 응답 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 응답 내용 출력
            System.out.println("Response Content: " + response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
