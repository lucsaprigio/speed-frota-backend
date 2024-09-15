package com.speed.speed_frota.modules.mobile.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.speed.speed_frota.modules.mobile.entities.MobileEntity;

public interface MobileRepository extends JpaRepository<MobileEntity, Integer> {
    Optional<MobileEntity> findByMd5AndCnpj(String md5, String cnpj);
}
