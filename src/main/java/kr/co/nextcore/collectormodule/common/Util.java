package kr.co.nextcore.collectormodule.common;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String getCurrentTime(String format){
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(format));
    }
    public static String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getToday(){
        LocalDate now = LocalDate.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static void logWrite(String fileName, String msg){
        String logPath = "./log";
        PrintWriter pw = null;
        try {
            File logDir = new File(logPath);
            if(!logDir.exists()){
                logDir.mkdir();
            }
            pw = new PrintWriter(new FileWriter(logPath + "/" + fileName + Util.getToday(), true), true);
            pw.println("[" + Util.getCurrentTime() + "] " + msg);
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

    public static String saveImage(MultipartFile file, String dateTime) throws IOException {
        String directoryPath = "./image";

        Path directory = Paths.get(directoryPath).toAbsolutePath().normalize();
        Files.createDirectories(directory);
        String fileName = StringUtils.cleanPath(dateTime + "_" + file.getOriginalFilename());
        Path targetPath = directory.resolve(fileName).normalize();
        file.transferTo(targetPath);

        return Paths.get(directoryPath).toAbsolutePath().toString();
    }
}
