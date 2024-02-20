package com.billingreports.controllers.aws;

import com.billingreports.entities.aws.Aws;
import com.billingreports.service.aws.AwsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwsControllerTest {
    @InjectMocks
    private AwsController awsController;
    @Mock
    private AwsService awsService;

    @Test
    void getAllServices()
    {
        List<Aws> mockAwsData = new ArrayList<>();
        mockAwsData.add(new Aws(/* Pass required parameters for Aws object */));

        // Mock the behavior of awsService.getAllServices() to return mock data
        when(awsService.getAllServices()).thenReturn(mockAwsData);

        // Call the controller method
        ResponseEntity<List<Aws>> responseEntity = awsController.getAllServices();

        // Verify that the response status code is HttpStatus.OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that the response body contains the mock data
        assertEquals(mockAwsData, responseEntity.getBody());

    }

    @Test
    void getDistinctServices()
    {
        String[] mockDistinctServices = {"Service1", "Service2"};
        when(awsService.getUniqueServicesAsArray()).thenReturn(mockDistinctServices);

        // Act
        ResponseEntity<String[]> response = awsController.getDistinctServices();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDistinctServices, response.getBody());
    }

    @Test
    void getBillingDetails() {
        // Arrange
        List<Aws> mockBillingDetails = Arrays.asList(new Aws(), new Aws());
        when(awsService.getBillingDetails(null, null, null, 3)).thenReturn(mockBillingDetails);

        // Act
        ResponseEntity<?> response = awsController.getBillingDetails(null, null, null, 3);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}