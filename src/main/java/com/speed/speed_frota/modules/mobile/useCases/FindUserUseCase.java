package com.speed.speed_frota.modules.mobile.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speed.speed_frota.modules.mobile.dto.MobileDTO;
import com.speed.speed_frota.modules.mobile.entities.MobileEntity;
import com.speed.speed_frota.modules.mobile.repositories.MobileRepository;

@Service
public class FindUserUseCase {

    @Autowired
    private MobileRepository mobileRepository;

    public MobileEntity execute(MobileDTO mobileDTO) {
        return this.mobileRepository
            .findByMd5AndCnpj(mobileDTO.getMd5(), mobileDTO.getCnpj())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
