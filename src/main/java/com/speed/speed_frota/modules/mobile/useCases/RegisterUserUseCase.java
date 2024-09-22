package com.speed.speed_frota.modules.mobile.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speed.speed_frota.modules.mobile.entities.MobileEntity;
import com.speed.speed_frota.modules.mobile.repositories.MobileRepository;

@Service
public class RegisterUserUseCase {
    
    @Autowired
    private MobileRepository mobileRepository;

    public MobileEntity execute(MobileEntity mobileEntity ) {
        System.out.println(mobileEntity);
        return this.mobileRepository.save(mobileEntity);
    }
}
