package com.jannesh.service;

import com.jannesh.entity.Vendor;
import com.jannesh.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepo;

    @InjectMocks
    private VendorService vendorService;

    @Test
    public void shouldSaveVendorSuccessfully() {
        Vendor newVendor = new Vendor();
        newVendor.setName("ABC");

        UUID savedVendorId = UUID.randomUUID();

        Vendor savedVendor = new Vendor();
        savedVendor.setVendorId(savedVendorId);
        savedVendor.setName("ABC");

        when(vendorRepo.save(any(Vendor.class))).thenReturn(savedVendor);

        Vendor vendor = vendorService.createVendor(newVendor);

        assertEquals("ABC", vendor.getName());
        assertEquals(savedVendorId, savedVendor.getVendorId());
        verify(vendorRepo, times(1)).save(any(Vendor.class));
    }

    @Test
    public void shouldFetchVendorByVendorIdSuccessfully() {
        UUID vendorId = UUID.randomUUID();

        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorId);
        vendor.setName("ABC");

        when(vendorRepo.findById(vendorId)).thenReturn(Optional.of(vendor));

        Vendor savedVendor = vendorService.fetchVendorByVendorId(vendorId);

        assertEquals("ABC", savedVendor.getName());
        verify(vendorRepo, times(1)).findById(vendorId);
    }

    @Test
    public void shouldThrowExceptionWhenVendorNotFound() {
        UUID vendorId = UUID.randomUUID();

        when(vendorRepo.findById(vendorId)).thenReturn(Optional.empty());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
           vendorService.fetchVendorByVendorId(vendorId);
        });

        assertEquals("Vendor Not Found", e.getMessage());

        verify(vendorRepo).findById(vendorId);
    }
}
