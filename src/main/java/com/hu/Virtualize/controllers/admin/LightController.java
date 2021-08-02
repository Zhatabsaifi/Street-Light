package com.hu.Virtualize.controllers.admin;

import com.hu.Virtualize.commands.admin.ShopCommand;
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
    public ResponseEntity<?> insertShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin add new shop in his list");
        AdminEntity admin = lightService.insertShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

   
    @PutMapping("/update")
    public ResponseEntity<?> updateShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin update the shop details");
        AdminEntity admin = lightService.updateShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

   
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin delete the shop in his list");
        AdminEntity admin = lightService.deleteShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShopsById(@PathVariable Long id){
        log.info("Admin find the shop by shop id");
        Set<LightEntity> shops = lightService.getAllShopsByAdminId(id);
        return new ResponseEntity<>(shops,HttpStatus.OK);
    }

    
    @PostMapping("/changeLightStatus/{lightId}")
    public ResponseEntity<String> insertShopImage(@PathVariable String lightId, @RequestParam("image") MultipartFile multipartFile) {
        log.info("Admin try to change the shop image");
        String status = lightService.insertShopImage(Long.valueOf(lightId), multipartFile);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

   
    @GetMapping("/status/{lightId}")
    public void renderImageFromDB(@PathVariable String lightId, HttpServletResponse response) {
        try {
            LightEntity lightEntity = lightService.findShopById(Long.valueOf(lightId));

            byte[] byteArray = new byte[lightEntity.getShopImage().length];

            int i = 0;
            for (Byte wrappedByte : lightEntity.getShopImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        } catch (Exception e) {
            log.error("Image fetch error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
