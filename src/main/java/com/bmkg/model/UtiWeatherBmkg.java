/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Taufik AB
 */
@Entity
@Table(name = "UTI_WEATHERBMKG")//UTI_WEATHERBMKG
@Data
public class UtiWeatherBmkg implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic (optional = false)
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "UTI_WEATHERBMKG_SEQ_ID")
    @SequenceGenerator(sequenceName = "UTI_WEATHERBMKG_SEQ",allocationSize = 1, name="UTI_WEATHERBMKG_SEQ_ID")
    @Column(name = "weatherbmkgid")
    private Long weatherBmkgID;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "winddir")
    private Double windDir;
    @Column(name = "windspd")
    private Double windSpd;
    @Column(name = "wsc")
    private Double wsc;
    @Column(name = "cudir")
    private Double cuDir;
    @Column(name = "cuspd")
    private Double cuSpd;
    @Column(name = "ekp")
    private Double ekp;
    @Column(name = "wavedir")
    private Double waveDir;
    @Column(name = "ptot")
    private Double pTot;
    @Column(name = "htot")
    private Double hTot;
    @Column(name = "h1per10")
    private Double h1Per10;
    @Column(name = "h1per100")
    private Double h1Per100;
    @Column(name = "seadir")
    private Double seaDir;
    @Column(name = "psea")
    private Double pSea;
    @Column(name = "hsea")
    private Double hSea;
    @Column(name = "swelldir")
    private Double swellDir;
    @Column(name = "pswell")
    private Double pSwell;
    @Column(name = "hswell")
    private Double hSwell;
    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "createdby", length = 50)
    private String createdBy;
    @Column(name = "lastmodified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Column(name = "modifiedby", length = 50)
    private String modifiedBy;
    @Column(name = "usersiteid")
    private String userSiteID;
    @Column(name = "FILEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datefilename;
    
}