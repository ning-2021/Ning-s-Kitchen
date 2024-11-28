package org.example.controller;

import org.example.service.NavService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.model.NavItem;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class NavController {
    private final NavService navService;
    public NavController(NavService navService) {
        this.navService = navService;
    }

    @GetMapping("/navbarItems")
    public List<NavItem> fetchAllNavItems() {
        return navService.findNavItems();
    }

}
