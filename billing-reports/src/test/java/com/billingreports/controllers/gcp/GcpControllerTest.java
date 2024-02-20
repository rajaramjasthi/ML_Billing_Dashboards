package com.billingreports.controllers.gcp;

import com.billingreports.entities.gcp.Gcp;
import com.billingreports.entities.gcp.GcpAggregateResult;
import com.billingreports.exceptions.ValidDateRangeException;
import com.billingreports.service.gcp.GcpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GcpControllerTest {

    @Mock
    private GcpService gcpService;

    @InjectMocks
    private GcpController gcpController;

    @Test
    void getAll() {
        List<Gcp> mockGcpList = Collections.singletonList(new Gcp(/* initialize Gcp object here */));

        // Mock service method
        when(gcpService.getAllData()).thenReturn(mockGcpList);

        // Call controller method
        ResponseEntity<List<Gcp>> responseEntity = gcpController.getAll();

        // Assert response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockGcpList, responseEntity.getBody());
}

    @Test
    void getDistinctServiceDescriptions() {
        // Mock data
        List<String> mockServiceDescriptions = Collections.singletonList("SomeService");

        // Mock service method
        when(gcpService.getDistinctServiceDescriptions()).thenReturn(mockServiceDescriptions);

        // Call controller method
        ResponseEntity<List<String>> responseEntity = gcpController.getDistinctServiceDescriptions();

        // Assert response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockServiceDescriptions, responseEntity.getBody());
    }

    @Test
    void getBillingDetails_WithValidInput() {
        // Mock data
        List<Gcp> mockBillingDetails = Collections.singletonList(new Gcp());
        List<Map<String, Double>> mockMonthlyTotalAmounts = new ArrayList<>();
        Double mockTotalAmount = 1000.0;
        List<Map<String, Object>> mockBillingPeriod = new ArrayList<>();
        List<GcpAggregateResult> mockAggregateResults = new ArrayList<>();

        // Mock service method
        when(gcpService.getBillingDetails("description", "startDate", "endDate", 0)).thenReturn(mockBillingDetails);
        when(gcpService.calculateMonthlyTotalBills(mockBillingDetails)).thenReturn(mockMonthlyTotalAmounts);
        when(gcpService.generateBillingPeriod("startDate", "endDate", 0)).thenReturn(mockBillingPeriod);
        when(gcpService.getServiceTopFiveTotalCosts("startDate", "endDate", 0)).thenReturn(mockAggregateResults);

        // Call controller method
        ResponseEntity<?> responseEntity = gcpController.getBillingDetails("description", "startDate", "endDate", 0);

        // Assert response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // You may add more detailed assertions for the response body if needed
    }

    @Test
    void getBillingDetails_WithMissingParameters() {
        // Call controller method with missing parameters
        ValidDateRangeException exception = assertThrows(ValidDateRangeException.class,
                () -> gcpController.getBillingDetails(null, null, null, null));

        // Assert exception
        assertEquals("Enter valid inputs", exception.getMessage());
    }
}

