package com.revature.controllers;

import com.revature.dtos.ProductCreateDTO;
import com.revature.dtos.ProductDTO;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prod")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000",
        "http://localhost:5000", "http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true")
public class ProductController {

    private final ProductService prodService;
    private final ModelMapper modMap = new ModelMapper();

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }

    // @Authorized
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(prodService.findAll());
    }

    // @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = prodService.findById(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    // @Authorized
    @PutMapping
    public ResponseEntity<Product> upsertProduct(@RequestBody ProductCreateDTO newProd) {
        Product prod = modMap.map(newProd, Product.class);
        return ResponseEntity.ok(prodService.save(prod));
    }

    // @Authorized
    @PatchMapping
    public ResponseEntity<List<Product>> purchaseProduct(@RequestBody ProductDTO[] prodDTO) {
        List<Product> prodList = new ArrayList<>();

        for (ProductDTO i : prodDTO) {
            Optional<Product> optional = prodService.findById(i.getProdIdDto());

            if (!optional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Product prod = optional.get();

            if (prod.getProdQuantity() - i.getProdDtoQuantity() < 0) {
                return ResponseEntity.badRequest().build();
            }

            prod.setProdQuantity(prod.getProdQuantity() - i.getProdDtoQuantity());
            prodList.add(prod);
        }

        prodService.saveAll(prodList);

        return ResponseEntity.ok(prodList);
    }

    // @Authorized
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        Optional<Product> optional = prodService.findById(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prodService.delete(id);

        return ResponseEntity.ok(optional.get());
    }
    // New Stuff

    // @Authorized
    @GetMapping("/search")
    public ResponseEntity<?> polyProductSearch(
            @RequestParam(required = false, name = "descQuery") final String descQuery,
            @RequestParam(required = false, name = "nameQuery") final String nameQuery,
            @RequestParam(required = false, name = "imageQuery") final String imageQuery,
            @RequestParam(required = false, name = "priceQuery") final Double priceQuery,
            @RequestParam(required = false, name = "priceQueryRangeMin") final Double priceQueryRangeMin,
            @RequestParam(required = false, name = "priceQueryRangeMax") final Double priceQueryRangeMax)
    {

        //Takes the HTTP request routed to /api/prod/search and checks which of the above are appended
        if (descQuery != null) {
            //descQuery checks if a string is present in Product.prodDesc
            Optional<List<Product>> taggedProducts = prodService.findByDescription(descQuery);
            if (!taggedProducts.isPresent())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(taggedProducts.get());

        } else if (nameQuery != null) {
            //nameQuery checks if a string is present in Product.prodName
            Optional<List<Product>> namedProducts = prodService.findByName(nameQuery);
            if (!namedProducts.isPresent())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(namedProducts.get());

        } else if (imageQuery != null) {
            //imageQuery checks if a string is present in Product.prodImage
            //Note -- this string is a directory to an asset folder within the front end
            Optional<List<Product>> imagedProducts = prodService.findByImage(imageQuery);
            if (!imagedProducts.isPresent())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(imagedProducts.get());

        } else if (priceQuery != null) {
            //priceQuery checks exact price
            Optional<List<Product>> pricedProducts = prodService.findByPrice(priceQuery);
            if (!pricedProducts.isPresent())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(pricedProducts.get());

        } else if (priceQueryRangeMin != null && priceQueryRangeMax != null) {
            //takes priceMin and priceMax and performs a query to find products within that range
            Optional<List<Product>> pricedProducts = prodService.findByPriceRange(priceQueryRangeMin, priceQueryRangeMax);
            if (!pricedProducts.isPresent())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(pricedProducts.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Product());
    }
}
