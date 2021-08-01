package com.hu.Virtualize.services.admin.service;

import com.hu.Virtualize.commands.admin.ShopCommand;
import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.entities.LightEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface LightService {
    AdminEntity insertShop(ShopCommand shopCommand);
    AdminEntity updateShop(ShopCommand shopCommand);
    AdminEntity deleteShop(ShopCommand shopCommand);
    Set<LightEntity> getAllShopsByAdminId(Long id);
    LightEntity findShopById(Long shopId);
    String insertShopImage(Long shopId, MultipartFile multipartFile);
}
