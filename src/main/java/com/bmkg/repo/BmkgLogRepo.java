/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.repo;

import com.bmkg.model.LogBmkg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Taufik AB
 */
@Repository
public interface BmkgLogRepo extends JpaRepository<LogBmkg, Integer>, JpaSpecificationExecutor<LogBmkg>{
    
    LogBmkg findByNamafile(String namaFile);
    @Query(value = "select * from LOGBMKG where ENDDOWNLOAD is null and STATUS='new' and NAMAFILE like 'InaFlows%'",nativeQuery = true)
    LogBmkg findByDownloadTerakhirIna();
    @Query(value = "select * from LOGBMKG where ENDDOWNLOAD is null and STATUS='new' and NAMAFILE like 'hires%'",nativeQuery = true)
    LogBmkg findByDownloadTerakhirHires();
    
}
