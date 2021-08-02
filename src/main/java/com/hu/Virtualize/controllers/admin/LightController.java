package com.hu.Virtualize.controllers.admin;

import com.hu.Virtualize.commands.admin.LightCommand;
import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.entities.LightEntity;
import com.hu.Virtualize.services.admin.service.LightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

@Slf4j
@RequestMapping("/admin/light")
@RestController
@CrossOrigin("*")
public class LightController {

    @Autowired
    private LightService lightService;

    
    @PostMapping("/create")
    public ResponseEntity<?> insertLight(@RequestBody LightCommand lightCommand) {
        log.info("Admin add new shop in his list");
        AdminEntity admin = lightService.insertLight(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

   
    @PutMapping("/update")
    public ResponseEntity<?> updateShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin update the shop details");
        AdminEntity admin = lightService.updateShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

   
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteShop(@RequestBody LightCommand lightCommand) {
        log.info("Admin delete the shop in his list");
        AdminEntity admin = lightService.deleteLight(lightCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLidhtById(@PathVariable Long id){
        log.info("Admin find the shop by shop id");
        Set<LightEntity> lights = lightService.getAllLightByAdminId(id);
        return new ResponseEntity<>(lights,HttpStatus.OK);
    }

   
    @GetMapping("/status/{lightId}")
    public void renderImageFromDB(@PathVariable String lightId, HttpServletResponse response) {
        try {
            LightEntity lightEntity = lightService.findLightById(Long.valueOf(lightId));

            byte[] byteArray = new byte[lightEntity.getLighStatus().length];

            int i = 0;
            for (Byte wrappedByte : lightEntity.getLightSatte()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
