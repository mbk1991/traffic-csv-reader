package kr.co.nextcore.collectormodule.centering.controller;

import kr.co.nextcore.collectormodule.centering.service.impl.CenteringService;
import kr.co.nextcore.collectormodule.common.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class CenteringController {
    Logger logger = LogManager.getLogger();

    @Autowired
    CenteringService cService;

    @PostMapping(value = "/centeringData", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String getCenteringData(@RequestPart("data") Map<String, Object> data,
                                   @RequestPart(value = "image", required = false) MultipartFile image) {
        logger.info("*** Hopper Data Receive ***");
        logger.info(data.toString());
        Util.logWrite("centering", data.toString());

        String absolutePath = "";
        String imageName = "";
        String imageType = "";
        try {
            if (image != null) {
                Util.logWrite("centering", imageName);
                try {
                    //image file save
                    String dateTime = Util.getCurrentTime("yyyyMMddHHss");
                    absolutePath = Util.saveImage(image, dateTime);
                    imageName = dateTime + "_" + image.getOriginalFilename();
                    imageType = image.getContentType();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }

            //centerin data & image info insert
            cService.addData(data, absolutePath, imageName, imageType);
            cService.insert(data);

            //faChk
            cService.faChk(data);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return "success";
    }
}


