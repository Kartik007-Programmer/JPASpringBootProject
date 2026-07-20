package com.group.jpaspringbootproject.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

//    public Product() {
//    }
//
//    public Product(int id, String name, int price) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                '}';
//    }
}
