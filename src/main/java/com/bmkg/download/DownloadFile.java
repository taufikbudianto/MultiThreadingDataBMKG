/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.download;

import com.bmkg.model.LogBmkg;
import com.bmkg.repo.BmkgLogRepo;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Taufik AB
 */
@Component
public class DownloadFile {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${locationFile}")
    private String locationfile;

    @Autowired
    private BmkgLogRepo logRepo;

    public Map<String, LogBmkg> startIna(LogBmkg logIna, LogBmkg logHires) throws ParseException, InterruptedException {
        boolean ret = false;
        String urlInaflows = "http://peta-maritim.bmkg.go.id/render/netcdf/seacurrents/" + logIna.getNamafile();
        URL url;
        try {

            Map<String, LogBmkg> map = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            url = new URL(urlInaflows);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic cmVuZGVyb2ZzOnJlbmRlcjIzMDM=");
            if (conn.getResponseCode() == 200) {
                if (logIna.getStatus().equals("new")) {

                    log.info("Mulai download file InaFlows");
                    System.out.println("Mulai download file InaFlows");
                    logIna.setStartdownload(new Date());
                    logIna.setFlag("Start Download");
                    logRepo.save(logIna);

                    FileUtils.copyInputStreamToFile(conn.getInputStream(), new File(logIna.getLokasi()));

                    log.info("Selesai download file InaFlows Done");
                    logIna.setEnddownload(new Date());
                    logIna.setFlag("Selesai Download");
                    logRepo.save(logIna);
                    LogBmkg bm = new LogBmkg();
                    String n = "";
                    String lokasi = "";
                    if (logIna.getNamafile().substring(18, logIna.getNamafile().length()).equals("0000.nc")) {
                        lokasi = locationfile + logIna.getNamafile().substring(0, logIna.getNamafile().length() - 7) + "1200.nc";
                        n = logIna.getNamafile().substring(0, logIna.getNamafile().length() - 7) + "1200.nc";
                    } else {
                        lokasi = locationfile + "InaFlows_" + sdf.format(new Date()) + "_0000.nc";
                        n = "InaFlows_" + sdf.format(new Date()) + "_0000.nc";
                    }
                    bm.setNamafile(n);
                    bm.setLokasi(lokasi);
                    bm.setFlag("Persiapan");
                    bm.setStatus("new");
                    logRepo.save(bm);
                    Boolean hiress = true;
                    while (hiress) {
                        hiress = getHires(logHires);
                        Thread.sleep(30 * 60 * 1000);
                    }
                    logIna.setStartprocess(new Date());
                    logIna.setFlag("Mulai Parse");
                    logIna.setStatus("process");
                    logRepo.save(logIna);
                    logHires.setStartprocess(new Date());
                    logHires.setFlag("Mulai Parse");
                    logHires.setStatus("process");
                    logRepo.save(logHires);
                    map.put("ina", logIna);
                    map.put("hires", logHires);
                    return map;
                }
            }
            if (conn.getResponseCode() == 404) {
                log.info("File Belum Tersedia Untuk data==> " + logIna.getNamafile());
            }

        } catch (MalformedURLException ex) {
            log.info(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            log.info(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private Boolean getHires(LogBmkg logHires) throws ParseException {
        Boolean result = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
        String t = sdf2.format(sdf.parse(logHires.getNamafile().substring(6, 14)));
        String urlHires = "http://peta-maritim.bmkg.go.id/render/netcdf/" + t + "/" + logHires.getNamafile();
        URL url;
        try {
            url = new URL(urlHires);
            System.out.println("Mulai download file Hires");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic cmVuZGVyb2ZzOnJlbmRlcjIzMDM=");
            if (conn.getResponseCode() == 200) {
                if (logHires.getStatus().equals("new")) {
                    logHires.setStartdownload(new Date());
                    logHires.setFlag("Start Download");
                    logRepo.save(logHires);

                    FileUtils.copyInputStreamToFile(conn.getInputStream(), new File(logHires.getLokasi()));

                    log.info("Selesai download file Hires Done");
                    logHires.setEnddownload(new Date());
                    logHires.setFlag("Selesai Download");
                    logRepo.save(logHires);
                    LogBmkg bm = new LogBmkg();
                    String n = "";
                    String lokasi = "";
                    if (logHires.getNamafile().substring(18, logHires.getNamafile().length()).equals("0000.nc")) {
                        lokasi = locationfile + logHires.getNamafile().substring(0, logHires.getNamafile().length() - 7) + "1200.nc";
                        n = logHires.getNamafile().substring(0, logHires.getNamafile().length() - 7) + "1200.nc";
                    } else {
                        lokasi = locationfile + "hires_" + sdf.format(new Date()) + "_0000.nc";
                        n = "hires_" + sdf.format(new Date()) + "_0000.nc";
                    }
                    bm.setNamafile(n);
                    bm.setLokasi(lokasi);
                    bm.setFlag("Persiapan");
                    bm.setStatus("new");
                    logRepo.save(bm);
                    result = false;
                }

            }
            else if (conn.getResponseCode() == 404) {
                log.info("File Belum Tersedia Untuk data==> " + logHires.getNamafile());
            }

        } catch (MalformedURLException ex) {
            log.info(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            log.info(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

}
