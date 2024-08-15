package kr.co.nextcore.collectormodule;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

class CollectorModuleApplicationTests {

    @Test
    void contextLoads() {
    }
    
    @Test
    void 경로테스트(){
        Path directory = Paths.get("./image").toAbsolutePath().normalize();
        System.out.println("directory = " + directory);

        String currDir = Paths.get("./").toAbsolutePath().toString();
        System.out.println("currDir = " + currDir);

        String UserDir = System.getProperty("user.dir");
        System.out.println("UserDir = " + UserDir);
    }

}
