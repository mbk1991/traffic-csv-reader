# traffic_csv_reader
:traffic csv 파일을 1분에 1회 읽어서 DB에 적재하는 프로그램
<br>라우터 -> nfcapd -> nfdump -> (csv파일)

### 주요 설정
<ol>
<li>청크사이즈 설정</li>
<li>스레드풀 사이즈 설정</li>
<li>커넥션풀 사이즈 설정</li>
</ol>

# Specification
<ul>
  <li>java8</li>
  <li>HikariCP 4.0.3</li>
  <li>Mysql 8.0.15</li>
</ul>

# Description


![nfcapd컨테이너- Flow CSV Loader drawio](https://github.com/user-attachments/assets/d42aca18-f36d-4419-9fbb-bfc5f22e0f14)
![Untitled (2)](https://github.com/user-attachments/assets/9657f00d-177e-4d1e-adc4-d60cf2dde8fd)
