/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmkg.model;

import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "LOGBMKG")
@Data
public class LogBmkg {
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_BMKG_SEQ_ID")
    @SequenceGenerator(sequenceName = "LOG_BMKG_SEQ", allocationSize = 1, name = "LOG_BMKG_SEQ_ID")
    private Integer id;
    private String namafile;
    private String lokasi;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdownload;
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddownload;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startprocess;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endprocess;
    private String status;
    private String flag;
}
