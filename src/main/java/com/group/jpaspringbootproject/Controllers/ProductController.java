package com.group.jpaspringbootproject.Controllers;

import com.group.jpaspringbootproject.Models.Product;
import com.group.jpaspringbootproject.Services.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ProductController
{
    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public ModelAndView Home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("HomePage.html");
        return mv;
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
       return productService.getProducts();
    }

    @GetMapping("/products/{ProdId}")
    public Product getProductById(@PathVariable int ProdId){
        return productService.getProductById(ProdId);
    }

    @PostMapping("/products")
    public ResponseEntity<?> AddProduct(@RequestPart("product") Product prod,
                                        @RequestPart("imagefile") MultipartFile imagefile){
//        System.out.println("New Product : "+prod);
        Product saved;
        try {
            saved = productService.AddProduct(prod, imagefile);
            return ResponseEntity.ok(saved);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @RequestPart("product") Product prod,
            @RequestPart(value = "imagefile", required = false) MultipartFile imagefile) {

        try {
            Product updated = productService.UpdateProduct(prod,imagefile);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/products/{ProdId}")
    public void deleteProductById(@PathVariable int ProdId){
        productService.deleteProductById(ProdId);
    }

    @GetMapping("/products/{ProdId}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable int ProdId){
        Product p = productService.getProductById(ProdId);
        return  ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(p.getImagefile());
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword){
        System.out.println("searching : "+keyword);
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }
}
