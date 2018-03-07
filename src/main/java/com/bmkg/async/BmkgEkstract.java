/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.async;

import com.bmkg.model.LogBmkg;
import com.bmkg.model.UtiWeatherBmkg;
import com.bmkg.repo.BmkgLogRepo;
import com.bmkg.repo.UtiWeatherBmkgRepo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 *
 * @author Taufik AB
 */
@Component
public class BmkgEkstract {

    @Autowired
    private BmkgLogRepo logRepo;

    @Autowired
    private UtiWeatherBmkgRepo repo;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Boolean bmkgData1(LogBmkg ina,LogBmkg hires) {
        save(ina,hires,0, 3, "Pertama");
        return true;
    }

    public Boolean bmkgData2(LogBmkg ina,LogBmkg hires) {
        save(ina,hires,3, 6, "Kedua");
        return true;

    }

    public Boolean bmkgData3(LogBmkg ina,LogBmkg hires) {

        save(ina,hires,6, 9, "Ketiga");
        return true;
    }

    public Boolean bmkgData4(LogBmkg ina,LogBmkg hires) {

        save(ina,hires,9, 12, "Keempat");
        return true;
    }

    public Boolean bmkgData5(LogBmkg ina,LogBmkg hires) {

        save(ina,hires,12, 15, "Kelima");
        return true;
    }

    public Boolean bmkgData6(LogBmkg ina,LogBmkg hires) {

        save(ina,hires,15, 18, "Keenam");
        return true;
    }

    public Boolean bmkgData7(LogBmkg ina,LogBmkg hires) {

        save(ina,hires,18, 21, "Ketujuh");
        return true;
    }

    public Boolean bmkgData8(LogBmkg ina,LogBmkg hires) {

        save(ina,hires,21, 25, "KeDelapan");
        return true;
    }

    public void save(LogBmkg ina,LogBmkg hires,int t1, int t2, String thread) {

        log.info("Mulai Parse Data BMKG pada {}", new Date());
        String urlDataNc = hires.getLokasi();
        NetcdfFile ncfile = null;
        String urlDataNcSea = ina.getLokasi();
        NetcdfFile ncfileSea = null;
        try {

            //untuk hires
            ncfile = NetcdfFile.open(urlDataNc);

            //Untuk Data Yang inaFlows
            ncfileSea = NetcdfFile.open(urlDataNcSea);
            String location = ncfileSea.getLocation().substring(urlDataNcSea.length() - 16, urlDataNcSea.length() - 8);
            //String [] tanggalLocation = location[location.length-1].split("_"); 
            //System.out.println(location);
            Variable u = ncfileSea.findVariable("u");
            Variable v = ncfileSea.findVariable("v");
            Variable hs = ncfileSea.findVariable("depth");

            //Untuk Wind
            Variable varU = ncfile.findVariable("uwnd");
            Variable varV = ncfile.findVariable("vwnd");
            //pada time ke 0-24
            //loopint latitude 20180123

            Variable varL = ncfileSea.findVariable("lon");
            Variable varLatitude = ncfileSea.findVariable("lat");
            Array dataLongitude = varL.read("0:1375:3");
            Array dataLatitude = varLatitude.read("0:750:3");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh");
            ArrayList<UtiWeatherBmkg> listBmkgBatch = new ArrayList<>();
            int data = 0;
            int dataSave = 0;
            for (int t = t1; t < t2; t++) {
                for (int h = 0; h < 4; h++) {
                    Array hSea = hs.read(h + ":" + h + ":1");
                    Array uSea = u.read(t + ":" + t + ":1," + h + ":" + h + ":1, 0:750:3, 0:1375:3");
                    Array vSea = v.read(t + ":" + t + ":1," + h + ":" + h + ":1, 0:750:3, 0:1375:3");
                    Date dt = sdf.parse(location + " " + String.valueOf(t * 3));
                    for (int lat = 0; lat < 251; lat++) {
                        //looping longitude
                        for (int lon = 0; lon < 459; lon++) {
                            Double dir = 0.0;
                            Double dirWind = 0.0;
                            int longwind = (int) (16 * (dataLongitude.getDouble(lon) - 90));
                            int latwind = (int) (16 * (dataLatitude.getDouble(lat) + 15));
                            Array dataU = varU.read(t + ":" + t + ":1, " + latwind + ":" + latwind + ":1, " + longwind + ":" + longwind + ":1");
                            Array dataV = varV.read(t + ":" + t + ":1, " + latwind + ":" + latwind + ":1, " + longwind + ":" + longwind + ":1");
                            Double vSeaKec = null;
                            try {
                                vSeaKec = Math.sqrt((uSea.getDouble(0) * uSea.getDouble(0)) + (vSea.getDouble(0) * vSea.getDouble(0)));
                            } catch (Exception e) {
                                vSeaKec = null;
                            }
                            Double vWindKec = null;
                            try {
                                vWindKec = Math.sqrt((dataU.getDouble(0) * dataU.getDouble(0)) + (dataV.getDouble(0) * dataV.getDouble(0)));
                            } catch (Exception e) {
                                vWindKec = null;
                            }

                            //set direction
                            try {
                                dir = calculationDirection(uSea.getDouble(lon), vSea.getDouble(lon));
                            } catch (Exception e) {
                                dir = null;
                            }
                            try {
                                dirWind = calculationDirection(dataU.getDouble(0), dataV.getDouble(0));
                            } catch (Exception e) {
                                dirWind = null;
                            }
                            UtiWeatherBmkg bmkg = repo.findAllByLongitudeAndLatitudeAndDatefilename(dataLongitude.getDouble(lon),
                                    dataLatitude.getDouble(lat), dt);
//                        UtiWeatherBmkg bmkg = new UtiWeatherBmkg();
                            if (bmkg == null) {
                                bmkg = new UtiWeatherBmkg();
                                bmkg.setDateCreated(new Date());
                            }
                            bmkg.setCreatedBy("bmkg");
                            bmkg.setLatitude(dataLatitude.getDouble(lat));
                            bmkg.setLongitude(dataLongitude.getDouble(lon));
                            bmkg.setCuSpd(vSeaKec);
                            bmkg.setWindSpd(vWindKec);
                            bmkg.setCuDir(dir);
                            bmkg.setWindDir(dirWind);
                            bmkg.setDatefilename(dt);
                            bmkg.setHSea(hSea.getDouble(0));
                            listBmkgBatch.add(bmkg);
                            if (listBmkgBatch.size() == 10000) {
                                data++;
                                log.info("Mulai ke {} jumlah data Save : {} =>Thread ke : {}", data, data * 10000, thread);
//                                System.out.println("Mulai ke {} jumlah data Save : {} =>Thread ke : {}" + thread);
                                withBatchingSave(listBmkgBatch, "");
                                listBmkgBatch.clear();
                            }
                            dataSave++;
//                            System.out.println("v kec : " + vSeaKec + "  :=>dir : " + dir + " ======== v kec wind : " + vWindKec + "  :=>dirWind : " + dirWind);
//                            System.out.println(bmkg.getDatefilename());
                        }
                    }
                }

            }
            withBatchingSave(listBmkgBatch, "done");
            System.out.println("Mulai ke {} jumlah data Save : {} =>Thread ke : {}" + thread);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double calculationDirection(Double u, Double v) {
        Double dir = 0.0;
        if (u > 0 && v > 0) {
            dir = Math.atan2(u, v);
        } else if (u < 0 && v > 0) {
            dir = Math.atan2(u, v) + 90;
        } else if (u < 0 && v < 0) {
            dir = Math.atan2(u, u) + 180;
        } else if (u > 0 && v < 0) {
            dir = Math.atan2(u, v) + 270;
        }
        return dir;
    }

    public void withBatchingSave(List<UtiWeatherBmkg> list, String a) {

        repo.save(list);

        if (a.equals("done")) {
            log.info("Selesai Parse Data BMKG pada {} ", new Date());
        }
    }
}
