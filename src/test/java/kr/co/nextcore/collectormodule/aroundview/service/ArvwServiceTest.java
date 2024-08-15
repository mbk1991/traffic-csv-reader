package kr.co.nextcore.collectormodule.aroundview.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ArvwServiceTest {

    @Test
    void ping체크테스트(){
        try {
            InetAddress ia = Inet4Address.getByName("192.168.10.202");
            if(ia.isReachable(3000)){
                System.out.println("ping success");
            }else{
                System.out.println("ping fail");
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}