/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.async;

import com.bmkg.model.LogBmkg;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Taufik AB
 */
@Service
public class BmkgAsync {

    @Autowired
    private BmkgEkstract bmkg;

    @Async
    public CompletableFuture<Boolean> data1(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData1(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data2(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData2(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data3(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData3(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data4(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData4(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data5(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData5(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data6(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData6(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data7(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData7(ina,hires));
    }

    @Async
    public CompletableFuture<Boolean> data8(LogBmkg ina,LogBmkg hires) throws IOException {
        return CompletableFuture.completedFuture(bmkg.bmkgData8(ina,hires));
    }
}
