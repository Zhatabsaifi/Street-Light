package com.hu.Virtualize.commands.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCommand {
    private Long adminId;
    private Long shopId;
    private Long productId;

    private Long discountId;
    private String discountName;
    private Integer discountPercentage;
    private String endDate;   // YYYY-MM-DD
    private String discountDescription;
}

/* for insert
    must give -- adminId, shopId, ProductId,  discountName, discountPercentage, endDate
 */

/* for delete
    must give -- adminId, shopId, ProductId, discountId
 */