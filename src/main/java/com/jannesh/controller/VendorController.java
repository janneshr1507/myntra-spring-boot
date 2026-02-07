package com.jannesh.controller;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @PostMapping("/save")
    public VendorDTO saveVendor(@RequestBody SaveVendorDTO requestDTO) {
        return vendorService.createVendorDTO(requestDTO);
    }
}
