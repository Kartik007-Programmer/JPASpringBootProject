package com.group.jpaspringbootproject.Services;

import com.group.jpaspringbootproject.Models.Product;
import com.group.jpaspringbootproject.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getProducts() {
        return repo.findAll() ;
    }

    public Product getProductById(int prodId) {
        return repo.findById(prodId).get();
    }

    public Product AddProduct(Product prod, MultipartFile imagefile) throws IOException {
        prod.setImageName(imagefile.getOriginalFilename());
        prod.setImageType(imagefile.getContentType());
        prod.setImagefile(imagefile.getBytes());
        try {
            return  repo.save(prod);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  repo.save(prod);
    }

    public Product  UpdateProduct(Product prod, MultipartFile imagefile) throws IOException {
        if (imagefile != null && !imagefile.isEmpty()) {
            prod.setImageName(imagefile.getOriginalFilename());
            prod.setImageType(imagefile.getContentType());
            prod.setImagefile(imagefile.getBytes());
        } else {
            Product existing = repo.findById(prod.getId()).orElse(null);
            if (existing != null) {
                prod.setImagefile(existing.getImagefile());
                prod.setImageName(existing.getImageName());
                prod.setImageType(existing.getImageType());
            }
        }
        return repo.save(prod);
    }

    public void deleteProductById(int prodId) {
        repo.deleteById(prodId);
    }

    public List<Product> searchProduct(String keyword) {
        return repo.searchProductByKeyword(keyword);
    }
}
