package com.speed.speed_frota.modules.mobile.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity(name = "db_recebe")
@SequenceGenerator(name="recebe_seq", allocationSize = 1)
public class MobileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recebe_seq")
    private Integer iten;

    @Column(name = "cnpj" ,columnDefinition = "VARCHAR(15)")
    private String cnpj;

    @Column(name = "md5" ,columnDefinition = "VARCHAR(55)")
    private String md5;

    @Lob
    @Column(name = "arquivo", columnDefinition="BLOB")
    private byte[] arquivo;

    @Column(name = "ativo", columnDefinition = "VARCHAR(1)")
    private String ativo;

    @CreationTimestamp
    private LocalDateTime dta_trans;
}
