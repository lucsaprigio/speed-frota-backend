package com.speed.speed_frota.modules.mobile;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity(name = "recebe")
public class MobileEntity {
    
    @Id
    @GeneratedValue()
    private Integer iten;

    @Column(name = "cnpj" ,columnDefinition = "VARCHAR(15)")
    private String cnpj;

    @Column(name = "md5" ,columnDefinition = "VARCHAR(25)")
    private String md5;

    @Lob
    @Column(name = "arquivo", columnDefinition="BLOB")
    private byte[] arquivo;

    @Column(name = "ativo", columnDefinition = "VARCHAR(1)")
    private String ativo;

    @CreationTimestamp
    private LocalDateTime dta_trans;
}
