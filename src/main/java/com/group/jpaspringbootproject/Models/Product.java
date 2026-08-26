package com.group.jpaspringbootproject.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String name ;
    private Integer price ;
    private String description;
    private Integer quantity;
    private LocalDateTime createdAt;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imagefile;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Product() {
    }

    public Product(Integer id, String name, Integer price, String description, Integer quantity, LocalDateTime createdAt, String imageName, String imageType, byte[] imagefile) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagefile = imagefile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImagefile() {
        return imagefile;
    }

    public void setImagefile(byte[] imagefile) {
        this.imagefile = imagefile;
    }
}
