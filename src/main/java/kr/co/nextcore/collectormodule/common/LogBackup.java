package kr.co.nextcore.collectormodule.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogBackup {

    public static void write(String fileName, String msg){

        String logPath = "./log";

        PrintWriter pw = null;
        try {
            File file = new File(logPath);
            if(!file.exists()){
                file.mkdir();
            }

            pw = new PrintWriter(new FileWriter(logPath + "/" + fileName + Util.getToday(), true), true);
            pw.println("[" + Util.getCurrentTime() + "] " + msg);
            pw.flush();
        } catch (IOException e) {
        } finally {
            try {
                if (pw != null) pw.close();
            } catch (Exception e) {
            }
        }
    }
}
