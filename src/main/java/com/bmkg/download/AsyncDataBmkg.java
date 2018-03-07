/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.download;

import com.bmkg.model.LogBmkg;
import com.bmkg.repo.BmkgLogRepo;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 *
 * @author Taufik AB
 */
@Service
public class AsyncDataBmkg {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DownloadFile download;

    @Autowired
    private BmkgLogRepo logRepo;

    @Async
    public Future<Map<String, LogBmkg>> downloadInaFlow() throws IOException, ParseException, InterruptedException {
        Map<String, LogBmkg> result =download.startIna(logRepo.findByDownloadTerakhirIna(),
                logRepo.findByDownloadTerakhirHires());
        return new AsyncResult<>(result);

    }
}
