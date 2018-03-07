/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.async;

import com.bmkg.download.DownloadFile;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Taufik AB
 */
@Component
public class DataAsync {

    @Autowired
    private BmkgAsync bmkg;
    @Autowired
    private DownloadFile bmkgDownload;

    public void downloadContents() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
        String d = sdf.format(new Date());
        String d2 = sdf2.format(new Date());
//        return bmkgDownload.startIna(0, d);
    }

    public void data() throws IOException {
//        bmkg.data1();
//        bmkg.data2();
//        bmkg.data3();
//        bmkg.data4();
//        bmkg.data5();
//        bmkg.data6();
//        bmkg.data7();
//        bmkg.data8();
//        bmkg.data9();
    }
}
