package com.hu.Virtualize.commands.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommand {
    private Long adminId;
    private Long shopId;
    private Long productId;

    private String productName;
    private Integer productPrice;
    private String brandName;
    private String categoryType;
    private String productType;
    private String productDescription;
    private Byte[] productImage;
}

/*
for insert case
most give shopId, adminId
doesn't give productId
    give all details except *productId*
 */

/*
For update
must give productId, shopId, adminId
 */

/*
for delete
must give productId, shopId, adminId
 */