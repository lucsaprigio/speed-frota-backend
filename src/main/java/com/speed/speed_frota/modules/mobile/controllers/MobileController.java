package com.speed.speed_frota.modules.mobile.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speed.speed_frota.modules.mobile.dto.MobileDTO;
import com.speed.speed_frota.modules.mobile.entities.MobileEntity;
import com.speed.speed_frota.modules.mobile.useCases.FindUserUseCase;
import com.speed.speed_frota.modules.mobile.useCases.RegisterUserUseCase;
import com.speed.speed_frota.modules.mobile.utils.FileUtils;

@RestController
@RequestMapping("/api/mobile")
public class MobileController {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private FindUserUseCase findUserUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> createData(@RequestBody MobileDTO mobileDTO) {
        try {
            MobileEntity mobileInfo = new MobileEntity();

            mobileInfo.setCnpj(mobileDTO.getCnpj());
            mobileInfo.setMd5(mobileDTO.getMd5());

            String content = "|1|" + mobileDTO.getMd5() + "|" + mobileDTO.getCnpj() + "|";

            byte[] file = FileUtils.generateArchive(mobileDTO.getMd5(), content);

            mobileInfo.setArquivo(file);
            mobileInfo.setAtivo("N");

            MobileEntity result = registerUserUseCase.execute(mobileInfo);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Object> findUser(@RequestBody MobileDTO mobileDTO) {
        try {
            MobileEntity result = findUserUseCase.execute(mobileDTO);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/users/{deviceId}")
    public ResponseEntity<Object> findUsersByMd5(@PathVariable("deviceId") String md5) {
        try {
            String fileReaded = FileUtils.readArchive(md5);
            System.out.println(fileReaded);

            return ResponseEntity.ok().body(fileReaded);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
