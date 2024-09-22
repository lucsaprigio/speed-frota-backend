package com.speed.speed_frota.modules.mobile.useCases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speed.speed_frota.modules.mobile.dto.MobileDTO;
import com.speed.speed_frota.modules.mobile.entities.MobileEntity;
import com.speed.speed_frota.modules.mobile.repositories.MobileRepository;

@Service
public class DeleteUserUseCase {
    
    @Autowired
    private MobileRepository mobileRepository;

    public void execute(MobileDTO mobileTO) {
        Optional<MobileEntity> userExists = this.mobileRepository.findByMd5AndCnpj(mobileTO.getMd5(), mobileTO.getCnpj());

        if(userExists.isPresent()) {
            MobileEntity user = userExists.get();

             this.mobileRepository.delete(user);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
