package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.ProductDTO;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("productNameFilter") Optional<String> productNameFilter,
                           @RequestParam("minPrice") Optional<BigDecimal> minPrice,
                           @RequestParam("maxPrice") Optional<BigDecimal> maxPrice) {
        logger.info("List page requested");

        List<ProductDTO> products;
        if (productNameFilter.isPresent() && !productNameFilter.get().isBlank()) {
            products = productService.findWithFilter(productNameFilter.get());
        } else if (minPrice.isPresent() || maxPrice.isPresent()){
            if (minPrice.isEmpty()) {
                products = productService.findByPrice(new BigDecimal(0), maxPrice.get());
            } else if (maxPrice.isEmpty()) {
                products = productService.findByPrice(minPrice.get(), BigDecimal.valueOf(Integer.MAX_VALUE));
            } else {
                products = productService.findByPrice(minPrice.get(), maxPrice.get());
            }
        } else {
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Edit page for id {} requested", id);

        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
        return "product_form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("product") ProductDTO product, BindingResult result) {
        logger.info("Update endpoint requested");

        if (result.hasErrors()) {
            return "product_form";
        }

        logger.info("Updating product with id {}", product.getId());
        productService.save(product);

        return "redirect:/product";
    }

    @GetMapping("/new")
    public String create(Model model) {
        logger.info("Create new product request");

        model.addAttribute("product", new ProductDTO());
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") Long id) {
        logger.info("Product delete requested");

        productService.delete(id);
        return "redirect:/product";
    }
}
