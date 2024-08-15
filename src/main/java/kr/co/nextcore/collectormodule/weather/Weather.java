package kr.co.nextcore.collectormodule.weather;

/**
 1.TMP: 1시간 기온
 2.UUU: 풍속(동서 성분)
 3.VVV: 풍속(남북 성분)
 4.VEC: 풍향
 5.WSD: 풍속
 6.SKY: 하늘상태
 7.PTY: 강수형태
 8.POP: 강수확률
 9.WAV: 파고
 10.PCP: 1시간 강수량
 11.REH: 습도
 12.SNO: 1시간 신적설
 * **/
public enum Weather {
    TMP(0),
    UUU(1),
    VVV(2),
    VEC(3),
    WSD(4),
    SKY(5),
    PTY(6),
    POP(7),
    WAV(8),
    PCP(9),
    REH(10),
    SNO(11);

    private final int idx;

    Weather(int idx) {
        this.idx = idx;
    }
    public int getIndex(){
        return this.idx;
    }
}


