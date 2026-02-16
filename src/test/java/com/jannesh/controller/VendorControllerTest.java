package com.jannesh.controller;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.entity.Vendor;
import com.jannesh.service.VendorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendorControllerTest {

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    @Test
    public void saveVendorTest() {
        SaveVendorDTO newVendorDTO = new SaveVendorDTO();
        newVendorDTO.setName("ABC Traders");

        VendorDTO savedVendorDTO = new VendorDTO();
        savedVendorDTO.setName("ABC Traders");

        when(vendorService.createVendorDTO(newVendorDTO)).thenReturn(savedVendorDTO);

        VendorDTO vendorDTO = vendorController.saveVendor(newVendorDTO);

        assertEquals("ABC Traders", vendorDTO.getName());
        verify(vendorService, times(1)).createVendorDTO(newVendorDTO);
    }
}
