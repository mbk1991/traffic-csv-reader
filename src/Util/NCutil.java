package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

public class NCutil {
    public static String LOG_FILE;
    public static String regResultMsg;

    public synchronized static String getRegResultMsg() {
        return regResultMsg;
    }

    public synchronized static void setRegResultMsg(String msg) {
        regResultMsg = msg;
    }

    public static void intToByteLittle(int i, byte[] byteArray, int offset) {
        byteArray[offset] = (byte) ((i >> 0) & 0xff);
        byteArray[offset + 1] = (byte) ((i >> 8) & 0xff);
        byteArray[offset + 2] = (byte) ((i >> 16) & 0xff);
        byteArray[offset + 3] = (byte) ((i >> 24) & 0xff);
    }

    public static void intToByteBig(int i, byte[] byteArray, int offset) {
        byteArray[offset + 3] = (byte) (i & 0xff);
        byteArray[offset + 2] = (byte) ((i >> 8) & 0xff);
        byteArray[offset + 1] = (byte) ((i >> 16) & 0xff);
        byteArray[offset] = (byte) ((i >> 24) & 0xff);
    }

    public static int ByteToInt(byte[] byteArray, int offset) {
        return byteArray[offset++] << 24 | (byteArray[offset++] & 0xff) << 16 | (byteArray[offset++] & 0xff) << 8 | (byteArray[offset] & 0xff);
    }

    public static String UnixtimeToString(int date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long ldate = date;
        ldate *= 1000;
        Date date_obj = new Date(ldate);
        return sdf.format(date_obj);
    }

    public synchronized static String SystemCommand(String[] command) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            Process ps = Runtime.getRuntime().exec(command);
            ps.waitFor();
            br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            while (br.ready()) {
                sb.append(br.readLine());
                sb.append("\n");
            }
        } catch (Exception e) {
            log_msg(LOG_FILE, "ERROR: SystemCommand(): " + e.getMessage());
            sb = null;
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception e) {
            }
        }
        if (sb == null) return null;
        return sb.toString();
    }

    public synchronized static String getCurrentLogTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd.HH:mm:ss"));
    }

    public synchronized static String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public synchronized static String getCurrentTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public synchronized static String getMinusDay(int n, String pattern){
        return LocalDateTime.now().minusDays(n).format(DateTimeFormatter.ofPattern(pattern));
    }

    public synchronized static String convertTime(String str, String beforeFormat, String afterFormat){
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(beforeFormat)).format(DateTimeFormatter.ofPattern(afterFormat));
    }

    public synchronized static Calendar StringToCalendar(String date_str) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = sdf.parse(date_str);
            cal.setTime(date);
        } catch (ParseException e) {
            cal = null;
            log_msg(LOG_FILE, "ERROR: StringToCalendar(): " + e.getMessage());
        }
        return cal;
    }

    public synchronized static LocalDateTime StringToLocalDateTime(String dateStr){
        return LocalDateTime.parse(dateStr,DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public synchronized static String CalendarToString(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(cal.getTime());
    }


    public synchronized static void setLogFileName(String fileName) {
        LOG_FILE = fileName;
    }

    public synchronized static void log_msg(String logFile, String sMsg) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(logFile, true), true);
            pw.println("[" + getCurrentLogTime() + "] " + sMsg);
            pw.flush();
        } catch (IOException e) {
            System.out.println("Error: log_msg(): " + e.getMessage());
        } finally {
            try {
                if (pw != null) pw.close();
            } catch (Exception e) {
            }
        }
    }

    public synchronized static void log_msg(String sMsg) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(LOG_FILE, true), true);
            pw.println("[" + getCurrentLogTime() + "] " + sMsg);
            pw.flush();

        } catch (IOException e) {
            System.out.println("Error: log_msg(): " + e.getMessage());
        } finally {
            try {
                if (pw != null) pw.close();
            } catch (Exception e) {
            }
        }
    }

    public static boolean file_save(String filename, String sMsg) {
        FileOutputStream fo = null;
        FileChannel fc = null;
        boolean flag = false;

        try {
            byte[] _byte = sMsg.getBytes();
            ByteBuffer bb = ByteBuffer.allocate(_byte.length);
            bb.put(_byte);
            bb.clear();

            fo = new FileOutputStream(filename);
            fc = fo.getChannel();

            fc.write(bb);
            flag = true;
        } catch (Exception e) {
            log_msg("Error: EOZutil.file_save(): " + e.getMessage());
        } finally {
            try {
                if (fo != null) fo.close();
                if (fc != null) fc.close();
            } catch (Exception ex) {
            }
        }
        return flag;
    }

    public static void fileDelete(String deleteFileName) {
        File I = new File(deleteFileName);
        I.delete();
    }

    public static int getTerm_time(String startDate, String endDate) {
        long diffMillis = 0l;
        int diff = 0;
        try {
            Date startDay = new SimpleDateFormat("yyyyMMddHHmmss").parse(startDate);
            Calendar startDayCal = new GregorianCalendar();
            startDayCal.setTime(startDay);

            Date endDay = new SimpleDateFormat("yyyyMMddHHmmss").parse(endDate);
            Calendar endDayCal = new GregorianCalendar();
            endDayCal.setTime(endDay);
            diffMillis = startDayCal.getTimeInMillis() - endDayCal.getTimeInMillis();
            diff = (int) (diffMillis / 1000);
        } catch (ParseException e) {
            e.printStackTrace(System.out);
        }
        return diff;
    }

    public static Double getDoubleValue(Object value) {
        return ((Short) value) / 10.0;
    }

    public static Boolean getBooleanValue(Object value) {
        return (Boolean) value;
    }

    public static Integer getIntegerValue(Object value) {
        return (Integer) value;
    }

    public static Short getShortValue(Object value) {
        return (Short) value;
    }

}
