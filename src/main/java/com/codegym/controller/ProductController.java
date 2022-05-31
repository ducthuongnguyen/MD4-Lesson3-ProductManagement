package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.IProductService;
import com.codegym.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {
    private final IProductService productService = new ProductService();

    @GetMapping()
    public String showList(Model model) {
//        String key = null;
        List<Product> products;
//        if (key == null || key == "") {
        products = productService.findAll();
//        } else {
//            products = productService.findByName(key);
//        }
        model.addAttribute("products", products);
        return "/index";
    }

    @GetMapping("/create")
    public String createForm() {
        return "/create";
    }

    @PostMapping("/save")
    public String save(Product product) {
        product.setId((int) (Math.random() * 1000));
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/delete")
    public String deleteForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(Product product) {
        productService.delete(product.getId());
        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/edit";
    }

    @PostMapping("/edit")
    public String edit(Product product) {
        productService.update(product.getId(), product);
        return "redirect:/product";
    }

    @GetMapping("/search")
    public String find(@RequestParam String key, Model model) {
        List<Product> rs = productService.findByName(key);
        model.addAttribute("products", rs);
        return "/index";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute(productService.findById(id));
        return "/view";
    }
}
