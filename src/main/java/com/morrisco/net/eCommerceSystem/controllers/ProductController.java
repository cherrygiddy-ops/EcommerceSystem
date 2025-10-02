package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.ProductDto;
import com.morrisco.net.eCommerceSystem.mappers.ProductMapper;
import com.morrisco.net.eCommerceSystem.repositories.CategoryRepository;
import com.morrisco.net.eCommerceSystem.repositories.ProductRepository;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Getter
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, ProductMapper mapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public Iterable<ProductDto> getProducts(@RequestHeader(name = "x-auth-token",required = false) String authToken, @RequestParam(required = false,name = "categoryId") Byte categoryId){

//        if (categoryId ==null)
//               return productRepository.findAll().stream()
//                .map(mapper::toProductDto)
//                .toList();
//           return productRepository.findByCategory(new Category(categoryId)).stream()
//                   .map(mapper::toProductDto).toList();
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable  long id){
//        var product = productRepository.findById(id).orElse(null);
//
//        if (product==null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(mapper.toProductDto(product));
        return null;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, UriComponentsBuilder uriBuilder){
//        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
//        if (category==null)
//            return ResponseEntity.badRequest().build();
//        System.out.println(category);
//        var product = mapper.toProductEntity(productDto);
//        product.setCategory(category);
//        productRepository.save(product);
//
//        var productDto1=mapper.toProductDto(product);
//
//        var uri =uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
//        return ResponseEntity.created(uri).body(productDto1);
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto request, @PathVariable(name = "id") Long id){
//        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
//        if (category==null)
//            return ResponseEntity.badRequest().build();
//        var product =productRepository.findById(id).orElse(null);
//
//        if (product == null)
//            return ResponseEntity.notFound().build();
//
//        mapper.updateUser(request,product);
//        product.setCategory(category);
//        productRepository.save(product);
//
//        return ResponseEntity.ok(mapper.toProductDto(product));
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") long id) {
        var product = productRepository.findById(id).orElse(null);

        if (product == null)
            return ResponseEntity.notFound().build();
        productRepository.delete(product);
     return ResponseEntity.noContent().build();
    }
    }
