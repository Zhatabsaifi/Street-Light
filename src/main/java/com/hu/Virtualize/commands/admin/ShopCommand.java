package com.hu.Virtualize.commands.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCommand {
    private Long adminId;

    // ShopId will pass when admin want to change the shop details.
    private Long shopId;

    private String shopName;
    private String shopLocation;
    private String shopDescription;
    private String GST;
}

/* 1  -- insert
when you add shop in admin list then pass
{
    adminId, ShopName, shopLocation, GST
}
 */

/* 2 -- update
when you want to update the shop details in admin list, then pass
{
    adminId, shopId, shopName(optional), shopLocation(option)
}

*/

/* 3 -- delete
when you want to delete the user details, then pass this
{
    adminId, shopId
}

*/