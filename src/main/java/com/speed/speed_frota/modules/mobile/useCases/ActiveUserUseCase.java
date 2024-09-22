package com.speed.speed_frota.modules.mobile.useCases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speed.speed_frota.modules.mobile.dto.UserDTO;
import com.speed.speed_frota.modules.mobile.entities.MobileEntity;
import com.speed.speed_frota.modules.mobile.repositories.MobileRepository;

@Service
public class ActiveUserUseCase {

    @Autowired
    private MobileRepository mobileRepository;

    public MobileEntity execute(UserDTO userDTO) {

        Optional<MobileEntity> userExists = this.mobileRepository.findByMd5AndCnpj(userDTO.getMd5(), userDTO.getCnpj());

        if (userExists.isPresent()) {
            MobileEntity user = userExists.get();

            user.setAtivo(userDTO.getAtivo());

            return mobileRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }

    }
}
