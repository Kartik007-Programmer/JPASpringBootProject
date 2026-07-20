package com.group.jpaspringbootproject.Repository;

import com.group.jpaspringbootproject.Models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
//
    @Query("SELECT p FROM Product p WHERE "+
            "LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
            "LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%')) ")
    List<Product> searchProductByKeyword(String keyword);
}
