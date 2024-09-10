package com.speed.speed_frota.modules.mobile.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speed.speed_frota.modules.mobile.MobileEntity;
import com.speed.speed_frota.modules.mobile.MobileRepository;

@Service
public class RegisterUserUseCase {
    
    @Autowired
    private MobileRepository mobileRepository;

    public MobileEntity execute(MobileEntity mobileEntity ) {
        return this.mobileRepository.save(mobileEntity);
    }
}
