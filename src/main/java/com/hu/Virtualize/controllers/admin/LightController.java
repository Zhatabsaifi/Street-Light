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

    /**
     * This api will help you to add new shop under admin.
     * @param shopCommand shop and admin details.
     * @return admin details
     */
    @PostMapping("/create")
    public ResponseEntity<?> insertShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin add new shop in his list");
        AdminEntity admin = lightService.insertShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    /**
     * This function will update the shop details.
     * @param shopCommand shop details.
     * @return updated details
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin update the shop details");
        AdminEntity admin = lightService.updateShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    /**
     * This function will delete the shop in admin shops.
     * @param shopCommand shop details
     * @return updated detail
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteShop(@RequestBody ShopCommand shopCommand) {
        log.info("Admin delete the shop in his list");
        AdminEntity admin = lightService.deleteShop(shopCommand);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    /**
     * This function will return the shop by shopId.
     * @param id shop id
     * @return shop object
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopsById(@PathVariable Long id){
        log.info("Admin find the shop by shop id");
        Set<LightEntity> shops = lightService.getAllShopsByAdminId(id);
        return new ResponseEntity<>(shops,HttpStatus.OK);
    }

    /**
     * This api will insert image for specific shop
     * @param lightId shop Id
     * @param multipartFile image for shop
     * @return 200 OK status
     */
    @PostMapping("/changeLightStatus/{lightId}")
    public ResponseEntity<String> insertShopImage(@PathVariable String lightId, @RequestParam("image") MultipartFile multipartFile) {
        log.info("Admin try to change the shop image");
        String status = lightService.insertShopImage(Long.valueOf(lightId), multipartFile);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    /**
     * This function will render the shop image
     * @param lightId shop id
     * @param response http servlet response stream
     */
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
