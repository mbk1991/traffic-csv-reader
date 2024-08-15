package FlowCollect.vo;

public class Netflow {
    private String collect_time;
    private String ts;
    private String te;
    private String td;
    private String sa;
    private String da;
    private int sp;
    private int dp;
    private String pr;
    private String flg;
    private int fwd;
    private String ipVer;
    private int sas;
    private int das;
    private int inif;  // in interface
    private int outif; // out interface
    private long ipkt;
    private long opkt;
    private long ibyt;
    private long obyt;
    private int tcpFlag;
    private int tos;
    private int smk;
    private int dmk;
    private int dir;
    private String mpls1;
    private String mpls2;
    private String mpls3;
    private String mpls4;
    private String mpls5;
    private String mpls6;
    private String mpls7;
    private String mpls8;
    private String mpls9;
    private String mpls10;
    private int icmpType;
    private int icmpCode;
    private String nh;     //next hop
    private String nhb;    //next hop BGP
    private int svln;
    private int dvln;
    private String ismc;
    private String idmc;
    private String osmc;
    private String odmc;
    private int stos;
    private int dtos;
    private String cl;
    private String sl;
    private String al;
    private String ra;
    private String eng;
    private String exid;
    private String tr;


    public String getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getTe() {
        return te;
    }

    public void setTe(String te) {
        this.te = te;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public int getFwd() {
        return fwd;
    }

    public void setFwd(int fwd) {
        this.fwd = fwd;
    }

    public String getIpVer() {
        return ipVer;
    }

    public void setIpVer(String ipVer) {
        this.ipVer = ipVer;
    }

    public int getSas() {
        return sas;
    }

    public void setSas(int sas) {
        this.sas = sas;
    }

    public int getDas() {
        return das;
    }

    public void setDas(int das) {
        this.das = das;
    }

    public int getInif() {
        return inif;
    }

    public void setInif(int inif) {
        this.inif = inif;
    }

    public int getOutif() {
        return outif;
    }

    public void setOutif(int outif) {
        this.outif = outif;
    }

    public long getIpkt() {
        return ipkt;
    }

    public void setIpkt(long ipkt) {
        this.ipkt = ipkt;
    }

    public long getOpkt() {
        return opkt;
    }

    public void setOpkt(long opkt) {
        this.opkt = opkt;
    }

    public long getIbyt() {
        return ibyt;
    }

    public void setIbyt(long ibyt) {
        this.ibyt = ibyt;
    }

    public long getObyt() {
        return obyt;
    }

    public void setObyt(long obyt) {
        this.obyt = obyt;
    }

    public int getTcpFlag() {
        return tcpFlag;
    }

    public void setTcpFlag(int tcpFlag) {
        this.tcpFlag = tcpFlag;
    }

    public int getTos() {
        return tos;
    }

    public void setTos(int tos) {
        this.tos = tos;
    }

    public int getSmk() {
        return smk;
    }

    public void setSmk(int smk) {
        this.smk = smk;
    }

    public int getDmk() {
        return dmk;
    }

    public void setDmk(int dmk) {
        this.dmk = dmk;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getMpls1() {
        return mpls1;
    }

    public void setMpls1(String mpls1) {
        this.mpls1 = mpls1;
    }

    public String getMpls2() {
        return mpls2;
    }

    public void setMpls2(String mpls2) {
        this.mpls2 = mpls2;
    }

    public String getMpls3() {
        return mpls3;
    }

    public void setMpls3(String mpls3) {
        this.mpls3 = mpls3;
    }

    public String getMpls4() {
        return mpls4;
    }

    public void setMpls4(String mpls4) {
        this.mpls4 = mpls4;
    }

    public String getMpls5() {
        return mpls5;
    }

    public void setMpls5(String mpls5) {
        this.mpls5 = mpls5;
    }

    public String getMpls6() {
        return mpls6;
    }

    public void setMpls6(String mpls6) {
        this.mpls6 = mpls6;
    }

    public String getMpls7() {
        return mpls7;
    }

    public void setMpls7(String mpls7) {
        this.mpls7 = mpls7;
    }

    public String getMpls8() {
        return mpls8;
    }

    public void setMpls8(String mpls8) {
        this.mpls8 = mpls8;
    }

    public String getMpls9() {
        return mpls9;
    }

    public void setMpls9(String mpls9) {
        this.mpls9 = mpls9;
    }

    public String getMpls10() {
        return mpls10;
    }

    public void setMpls10(String mpls10) {
        this.mpls10 = mpls10;
    }

    public int getIcmpType() {
        return icmpType;
    }

    public void setIcmpType(int icmpType) {
        this.icmpType = icmpType;
    }

    public int getIcmpCode() {
        return icmpCode;
    }

    public void setIcmpCode(int icmpCode) {
        this.icmpCode = icmpCode;
    }

    public String getNh() {
        return nh;
    }

    public void setNh(String nh) {
        this.nh = nh;
    }

    public String getNhb() {
        return nhb;
    }

    public void setNhb(String nhb) {
        this.nhb = nhb;
    }

    public int getSvln() {
        return svln;
    }

    public void setSvln(int svln) {
        this.svln = svln;
    }

    public int getDvln() {
        return dvln;
    }

    public void setDvln(int dvln) {
        this.dvln = dvln;
    }

    public String getIsmc() {
        return ismc;
    }

    public void setIsmc(String ismc) {
        this.ismc = ismc;
    }

    public String getIdmc() {
        return idmc;
    }

    public void setIdmc(String idmc) {
        this.idmc = idmc;
    }

    public String getOsmc() {
        return osmc;
    }

    public void setOsmc(String osmc) {
        this.osmc = osmc;
    }

    public String getOdmc() {
        return odmc;
    }

    public void setOdmc(String odmc) {
        this.odmc = odmc;
    }

    public int getStos() {
        return stos;
    }

    public void setStos(int stos) {
        this.stos = stos;
    }

    public int getDtos() {
        return dtos;
    }

    public void setDtos(int dtos) {
        this.dtos = dtos;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getExid() {
        return exid;
    }

    public void setExid(String exid) {
        this.exid = exid;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }
}
