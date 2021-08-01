package com.hu.Virtualize.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    
    private String productName;
    private Integer productPrice;

    private String brandName;               // peter england, raymond, RESTAURANT name, kitchen brand name
    private String categoryType;     // Cloth, RESTAURANT, kitchen, FURNITURE, MEDICINE

    // this will use  -- Cloths(Male,female,unisex), FURNITURE(chair, table, bed,..)
    private String productType;

    private String productDescription;

    @Lob
    private Byte[] productImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    Set<LightTypeEntity> productDiscounts = new HashSet<>();

    public ProductEntity( String productName,Integer productPrice, String brandName,   String categoryType, String productType, String productDescription) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.brandName = brandName;
        this.categoryType = categoryType;
        this.productType = productType;
        this.productDescription = productDescription;
    }
}
