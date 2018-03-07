/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.async;

import com.bmkg.download.AsyncDataBmkg;
import com.bmkg.model.LogBmkg;
import com.bmkg.repo.BmkgLogRepo;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Taufik AB
 */
@Component
@ConditionalOnProperty("sar.scheduler.enable")
public class BmkgSchedullerDownload {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AsyncDataBmkg dataBmkg;
    @Autowired
    private BmkgAsync bmkg;
    @Autowired
    private BmkgLogRepo logRepo;

    @Scheduled(cron = "${sar.scheduler.time}")
    private void schedulerForDownloadBmkg() throws IOException, InterruptedException, ExecutionException, ParseException {
        Future<Map<String, LogBmkg>> contentsFuture = dataBmkg.downloadInaFlow();
        System.out.println("--------Mulaiiii--------");
        System.out.println(contentsFuture.get());
        Map<String, LogBmkg> m = contentsFuture.get();
//        Map<String, LogBmkg> m = new HashMap<>();
//        m.put("ina", logRepo.findOne(1));
//        m.put("hires", logRepo.findOne(3));
        LogBmkg ina = m.get("ina");
        LogBmkg hires = m.get("hires");
        logger.info("Mulai Process");
        if (!m.isEmpty()) {
            CompletableFuture<Boolean> page1 = bmkg.data1(ina, hires);
            CompletableFuture<Boolean> page2 = bmkg.data2(ina, hires);
            CompletableFuture<Boolean> page3 = bmkg.data3(ina, hires);
            CompletableFuture<Boolean> page4 = bmkg.data4(ina, hires);
            CompletableFuture<Boolean> page5 = bmkg.data5(ina, hires);
            CompletableFuture<Boolean> page6 = bmkg.data6(ina, hires);
            CompletableFuture<Boolean> page7 = bmkg.data7(ina, hires);
            CompletableFuture<Boolean> page8 = bmkg.data8(ina, hires);
            CompletableFuture.allOf(page1, page2, page3, page4, page5, page6, page7, page8).join();
            logger.info("Page 1--> " + page1.get());
            logger.info("Page 2--> " + page2.get());
            logger.info("Page 3--> " + page3.get());
            logger.info("Page 4--> " + page4.get());
            logger.info("Page 5--> " + page5.get());
            logger.info("Page 6--> " + page6.get());
            logger.info("Page 7--> " + page7.get());
            logger.info("Page 8--> " + page8.get());
            hires.setEndprocess(new Date());
            hires.setStatus("done");
            hires.setFlag("Ok");
            logRepo.save(hires);
            ina.setEndprocess(new Date());
            ina.setStatus("done");
            ina.setFlag("Ok");
            logRepo.save(ina);
        }

    }
}
