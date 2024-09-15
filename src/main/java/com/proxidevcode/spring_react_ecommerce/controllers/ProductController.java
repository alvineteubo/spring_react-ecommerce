package com.proxidevcode.spring_react_ecommerce.controllers;

import static com.proxidevcode.spring_react_ecommerce.utils.AppConstants.PAGE_NUMBER;
import static com.proxidevcode.spring_react_ecommerce.utils.AppConstants.PAGE_SIZE;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;
import com.proxidevcode.spring_react_ecommerce.repositories.ProductRepository;
import com.proxidevcode.spring_react_ecommerce.services.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;



    @GetMapping
    public ResponseEntity<PagedResponse<ProductResponse>> getAllProducts(
    @RequestParam(name = "page", required = false, defaultValue = PAGE_NUMBER)int page, 
    @RequestParam(name="size", required=false, defaultValue = PAGE_SIZE)int size){
        
        return ResponseEntity.ok(productService.getAllProducts(page, size));

    }

   @PostMapping
   public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
   }
   
   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> getProduct(@PathVariable long id){
    return ResponseEntity.ok(productService.getProduct(id));
   }
   
   @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

  
   
   

    @GetMapping("/category/{id}")
    public ResponseEntity<PagedResponse<ProductResponse>> getProductByCategory(
        @PathVariable(value="id") long id,
        @RequestParam(name = "page", required = false, defaultValue = PAGE_NUMBER) int page,
        @RequestParam(name = "size", required = false, defaultValue = PAGE_SIZE) int size){
        
        PagedResponse<ProductResponse> response = productService.getProductsByCategory(id, page, size);
        return ResponseEntity.ok(response);
    }
    

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
       productService.DeleteProduct(id);
       return ResponseEntity.noContent().build();
   }
}

