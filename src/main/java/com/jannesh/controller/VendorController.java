package com.jannesh.controller;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @GetMapping("/get/{vendorId}")
    public VendorDTO getVendorDetails(@PathVariable("vendorId") UUID vendorId) {
        return vendorService.fetchVendorDetails(vendorId);
    }

    @PostMapping("/save")
    public VendorDTO saveVendor(@RequestBody SaveVendorDTO requestDTO) {
        return vendorService.createVendorDTO(requestDTO);
    }
}
