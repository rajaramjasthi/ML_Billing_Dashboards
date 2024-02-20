package com.billingreports.controllers.azure;

import com.billingreports.entities.azure.Azure;
import com.billingreports.service.azure.AzureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AzureControllerTest {

    @Mock
    private AzureService azureService;

    @InjectMocks
    private AzureController azureController;

    @Test
    void getAllData() {
        List<Azure> mockData = Collections.singletonList(new Azure(/* pass required parameters */));

        // Mock service behavior
        when(azureService.getAll()).thenReturn(mockData);

        // Perform the GET request
        ResponseEntity<List<Azure>> responseEntity = azureController.getAllData();

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response body
        assertEquals(mockData, responseEntity.getBody());
    }

    @Test
    void getBillingDetails() throws Exception {
        // Mock request parameters
        String resourceType = "VM";
        String subscriptionName = "example_subscription";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";
        int months = 1;

        // Mock data to be returned by the service
        List<Azure> mockBillingDetails = Arrays.asList(new Azure(/* pass required parameters */), new Azure(/* pass required parameters */));
        when(azureService.getBillingDetails(resourceType, subscriptionName, startDate, endDate, months)).thenReturn(mockBillingDetails);

        // Perform the GET request
        ResponseEntity<?> responseEntity = azureController.getBillingDetails(resourceType, subscriptionName, startDate, endDate, months);

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response body
        // You can further assert the response body if needed
    }

    @Test
    void getDistinctSubscriptionIds() throws Exception {
        // Mock data to be returned by the service
        List<String> mockSubscriptionIds = Arrays.asList("sub1", "sub2");
        when(azureService.getDistinctSubscriptionIds()).thenReturn(mockSubscriptionIds);

        // Perform the GET request
        ResponseEntity<List<String>> responseEntity = azureController.getDistinctSubscriptionIds();

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response body
        assertEquals(mockSubscriptionIds, responseEntity.getBody());
    }

    @Test
    void getDistinctSubscriptionNames() throws Exception {
        // Mock data to be returned by the service
        List<String> mockSubscriptionNames = Arrays.asList("sub1", "sub2");
        when(azureService.getDistinctSubscriptionNames()).thenReturn(mockSubscriptionNames);

        // Perform the GET request
        ResponseEntity<List<String>> responseEntity = azureController.getDistinctSubscriptionNames();

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response body
        assertEquals(mockSubscriptionNames, responseEntity.getBody());
    }

    @Test
    void getDistinctResourceType() throws Exception {
        // Mock data to be returned by the service
        List<String> mockResourceTypes = Arrays.asList("VM", "Storage");
        when(azureService.getDistinctResourceType()).thenReturn(mockResourceTypes);

        // Perform the GET request
        ResponseEntity<List<String>> responseEntity = azureController.getDistinctResourceType();

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response body
        assertEquals(mockResourceTypes, responseEntity.getBody());
    }

    @Test
    void getDistinctResourceTypeBySubscriptionName() throws Exception {
        // Mock request parameter
        String subscriptionName = "example_subscription";

        // Mock data to be returned by the service
        List<String> mockResourceTypes = Arrays.asList("VM", "Storage");
        when(azureService.getDistinctResourceTypeBySubscriptionName(subscriptionName)).thenReturn(mockResourceTypes);

        // Perform the GET request
        List<String> response = azureController.getDistinctResourceTypeBySubscriptionName(subscriptionName);

        // Assert the response body
        assertEquals(mockResourceTypes, response);
    }
}