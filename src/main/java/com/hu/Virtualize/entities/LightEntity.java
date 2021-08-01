package com.hu.Virtualize.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class LightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    private String shopName;
    private String shopLocation;
    private String shopDescription;
    private String GST;

    @Lob
    private Byte[] shopImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shopId", referencedColumnName = "shopId")
    Set<ProductEntity> shopProducts = new HashSet<>();


    public LightEntity(String shopName, String GST) {
        this.shopName = shopName;
        this.GST = GST;
    }

    public LightEntity(String shopName, String shopLocation, String shopDescription, String GST) {
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.shopDescription = shopDescription;
        this.GST = GST;
    }

    public LightEntity(String shopName, Byte[] shopImage, String GST) {
        this.shopName = shopName;
        this.shopImage = shopImage;
        this.GST = GST;
    }
}
