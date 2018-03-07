/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.repo;

import com.bmkg.model.UtiWeatherBmkg;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Taufik AB
 */
@Repository
public interface UtiWeatherBmkgRepo extends JpaRepository<UtiWeatherBmkg, Long>, JpaSpecificationExecutor<UtiWeatherBmkg> {
    
    UtiWeatherBmkg findAllByLongitudeAndLatitudeAndDatefilename(Double longitude,Double latitude,Date tgl);
    UtiWeatherBmkg findAllByLongitudeAndLatitudeAndDatefilenameAndHSea(Double longitude,Double latitude,Date tgl,Double hsea);
    
}
