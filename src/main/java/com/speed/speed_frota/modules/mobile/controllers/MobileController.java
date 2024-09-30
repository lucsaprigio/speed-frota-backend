package com.speed.speed_frota.modules.mobile.controllers;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.speed.speed_frota.modules.mobile.utils.FileUtils;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speed.speed_frota.modules.mobile.dto.FleetDTO;
import com.speed.speed_frota.modules.mobile.dto.MobileDTO;
import com.speed.speed_frota.modules.mobile.dto.UserDTO;
import com.speed.speed_frota.modules.mobile.entities.MobileEntity;
import com.speed.speed_frota.modules.mobile.useCases.ActiveUserUseCase;
import com.speed.speed_frota.modules.mobile.useCases.DeleteUserUseCase;
import com.speed.speed_frota.modules.mobile.useCases.FindUserUseCase;
import com.speed.speed_frota.modules.mobile.useCases.ListUsersUseCase;
import com.speed.speed_frota.modules.mobile.useCases.RegisterUserUseCase;

@RestController
@RequestMapping("/api/mobile")
public class MobileController {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private FindUserUseCase findUserUseCase;

    @Autowired
    private ActiveUserUseCase activeUserUseCase;

    @Autowired
    private ListUsersUseCase listUsersUseCase;

    @Autowired
    private DeleteUserUseCase deleteUserUSeCase;

    @PostMapping("/")
    public ResponseEntity<Object> createData(@RequestBody MobileDTO mobileDTO) {
        try {
            MobileEntity mobileInfo = new MobileEntity();

            String md5String = mobileDTO.getMd5().substring(0, Math.min(mobileDTO.getMd5().length(), 40));

            mobileInfo.setCnpj(mobileDTO.getCnpj());
            mobileInfo.setMd5(md5String);

            String content = "|1|" + mobileDTO.getMd5() + "|" + mobileDTO.getCnpj() + "|";

            byte[] file = FileUtils.generateArchive(md5String, content);

            mobileInfo.setArquivo(file);
            mobileInfo.setAtivo("N");

            MobileEntity result = registerUserUseCase.execute(mobileInfo);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/send-fleets/{md5}")
    public ResponseEntity<Object> sendFleet(@RequestBody List<FleetDTO> fleetDTOList, @PathVariable("md5") String md5) {
        try {

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
            String formatedDate = date.format(formatter);

            String fileName = md5 + "_" + formatedDate + ".txt";

            StringBuilder content = new StringBuilder();

            List<Integer> ids = new ArrayList<>();

            for (FleetDTO fleetDTO : fleetDTOList) {
                content.append("|2|")
                        .append(fleetDTO.getId()).append("|")
                        .append(md5).append("|")
                        .append(fleetDTO.getDescription()).append("|")
                        .append(fleetDTO.getPrice()).append("|")
                        .append(fleetDTO.getProvider()).append("|")
                        .append(fleetDTO.getVehicle_id()).append("|")
                        .append(fleetDTO.getObs()).append("|")
                        .append("\n");

                ids.add(fleetDTO.getId());
            }

            FileUtils.generateArchive(fileName, content.toString());

            return ResponseEntity.ok().body(ids);
        } catch (Exception e) {
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

    @GetMapping("/fetch/{deviceId}")
    public ResponseEntity<Object> findUsersByMd5(@PathVariable("deviceId") String md5) {
        try {
            String fileReaded = FileUtils.readArchive(md5);

            return ResponseEntity.ok().body(fileReaded);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<MobileEntity>> listAllUsers() {
        try {
            List<MobileEntity> result = listUsersUseCase.execute();

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/active-user")
    public ResponseEntity<Object> activateUser(@RequestBody UserDTO userDTO) {
        try {
            MobileEntity updatedUser = activeUserUseCase.execute(userDTO);

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<Object> activateUser(@RequestBody MobileDTO mobileDTO) {
        try {
            deleteUserUSeCase.execute(mobileDTO);

            return ResponseEntity.ok("Usuário excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
