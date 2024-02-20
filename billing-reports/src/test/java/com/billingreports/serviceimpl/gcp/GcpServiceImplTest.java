package com.billingreports.serviceimpl.gcp;

import com.billingreports.entities.gcp.Gcp;
import com.billingreports.exceptions.ValidDateRangeException;
import com.billingreports.repositories.gcp.GcpRespository;
import com.billingreports.service.gcp.GcpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import java.time.ZoneId;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GcpServiceImplTest {

    @Mock
    private GcpRespository gcpRepositoryMock;

    @InjectMocks
    private GcpServiceImpl gcpService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllData() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock data for Gcp objects
        List<Gcp> expectedGcpData = Arrays.asList(
                new Gcp("1", "Service1", new Date(), "Description1", 100.0),
                new Gcp("2", "Service2", new Date(), "Description2", 200.0),
                new Gcp("3", "Service3", new Date(), "Description3", 300.0)
        );

        // Mock the behavior of the repository method
        when(gcpRepositoryMock.findAll()).thenReturn(expectedGcpData);

        // Call the method under test
        List<Gcp> actualGcpData = gcpService.getAllData();

        // Verify that the method returns the expected list of Gcp objects
        assertEquals(expectedGcpData, actualGcpData);

        // Optionally, verify that the findAll() method was called on the repository
        verify(gcpRepositoryMock, times(1)).findAll();
    }


    @Test
    void getDistinctServiceDescriptions() {
    }

    @Test
    void getAllDataBydateRange() {
        // Mock input data for a valid date range
        String validStartDate = "2023-01-01";
        String validEndDate = "2023-12-31";
        LocalDateTime validStartLocalDate = LocalDate.parse(validStartDate).atStartOfDay();
        LocalDateTime validEndLocalDate = LocalDate.parse(validEndDate).atTime(LocalTime.MAX);
        List<Gcp> expectedValidData = Arrays.asList(
                new Gcp("1", "Service1", new Date(), "Description1", 100.0),
                new Gcp("2", "Service2", new Date(), "Description2", 200.0)
                // Add more Gcp objects as needed
        );
        // Mock input data for an invalid date range
        String invalidStartDate = "2023-01-01";
        String invalidEndDate = "2024-01-01";

        // Mock the behavior of the repository method for valid and invalid date ranges
        when(gcpRepositoryMock.findByDateRange(eq(validStartLocalDate), eq(validEndLocalDate)))
                .thenReturn(expectedValidData);
        when(gcpRepositoryMock.findByDateRange(eq(LocalDate.parse(invalidStartDate).atStartOfDay()),
                eq(LocalDate.parse(invalidEndDate).atTime(LocalTime.MAX))))
                .thenThrow(ValidDateRangeException.class);

        // Call the method under test for a valid date range
        List<Gcp> actualValidData = gcpService.getAllDataBydateRange(validStartDate, validEndDate);

        // Verify that the method returns the expected data for a valid date range
        Assertions.assertEquals(expectedValidData, actualValidData);

        // Call the method under test for an invalid date range and expect an exception
        Assertions.assertThrows(ValidDateRangeException.class, () -> {
            gcpService.getAllDataBydateRange(invalidStartDate, invalidEndDate);
        });

        // Optionally, verify that the repository method was called with the correct parameters
        verify(gcpRepositoryMock, times(1)).findByDateRange(eq(validStartLocalDate), eq(validEndLocalDate));
        verify(gcpRepositoryMock, times(1)).findByDateRange(eq(LocalDate.parse(invalidStartDate).atStartOfDay()),
                eq(LocalDate.parse(invalidEndDate).atTime(LocalTime.MAX)));
    }

    @Test
    void getAllDataByMonths() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock current date
        LocalDateTime endDate = LocalDate.now().atTime(LocalTime.MAX);

        // Calculate start date based on months
        int months = 3; // Example value
        LocalDateTime startDate = LocalDate.now().minusMonths(months - 1).withDayOfMonth(1).atStartOfDay();

        // Mock data for Gcp objects
        // Convert LocalDateTime to Date
        Date startDateDate = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date endDateDate = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

       // Create Gcp objects with the converted dates
        List<Gcp> expectedGcpData = Arrays.asList(
                new Gcp("1", "Service1", startDateDate, "Description1", 100.0),
                new Gcp("2", "Service2", endDateDate, "Description2", 200.0)
                // Add more Gcp objects as needed
        );

        // Mock the behavior of the repository method
        when(gcpRepositoryMock.findByDateRange(startDate, endDate)).thenReturn(expectedGcpData);

        // Call the method under test
        List<Gcp> actualGcpData = gcpService.getAllDataByMonths(months);

        // Verify that the method returns the expected list of Gcp objects
        assertEquals(expectedGcpData, actualGcpData);

        // Optionally, verify that the findByDateRange() method was called on the repository
        verify(gcpRepositoryMock, times(1)).findByDateRange(startDate, endDate);
    }

    @Test
    void getDataByServiceDescAndDateRange() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock data for the parameters
        String serviceDescription = "Test Service";
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";

        // Convert start and end dates to LocalDateTime
        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay().plusDays(1).minusSeconds(1); // Adjust for the end of the day

        // Convert LocalDateTime to Date
        Date startDateDate = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDateDate = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // Mock data for Gcp objects
        List<Gcp> expectedGcpData = Arrays.asList(
                new Gcp("1", "Service1", startDateDate, serviceDescription, 100.0),
                new Gcp("2", "Service2", endDateDate, serviceDescription, 200.0)
                // Add more Gcp objects as needed
        );

        // Mock the behavior of the repository method
        when(gcpRepositoryMock.findByServiceDescriptionAndDateRange(eq(serviceDescription), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(expectedGcpData);

        // Call the method under test
        List<Gcp> actualGcpData = gcpService.getDataByServiceDescAndDateRange(serviceDescription, startDate, endDate);

        // Verify that the method returns the expected list of Gcp objects
        assertEquals(expectedGcpData, actualGcpData);

        // Optionally, verify that the findByServiceDescriptionAndDateRange() method was called on the repository
        verify(gcpRepositoryMock, times(1)).findByServiceDescriptionAndDateRange(eq(serviceDescription), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getDataByServiceDescAndMonths() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock data for the parameters
        String serviceDescription = "Test Service";
        int months = 3;

        LocalDateTime endDate = LocalDate.now().atTime(LocalTime.MAX);
        LocalDateTime startDate = LocalDate.now().minusMonths(months - 1).withDayOfMonth(1).atStartOfDay();

        // Convert LocalDateTime to Date
        Date startDateTime = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        // Mock data for Gcp objects
        List<Gcp> expectedGcpData = Arrays.asList(
                new Gcp("1", "Service1", startDateTime, serviceDescription, 100.0),
                new Gcp("2", "Service2", endDateTime, serviceDescription, 200.0)
                // Add more Gcp objects as needed
        );

        // Mock the behavior of the repository method
        when(gcpRepositoryMock.findByServiceDescriptionAndDateRange(eq(serviceDescription), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(expectedGcpData);

        // Call the method under test
        List<Gcp> actualGcpData = gcpService.getDataByServiceDescAndMonths(serviceDescription, months);

        // Verify that the method returns the expected list of Gcp objects
        assertEquals(expectedGcpData, actualGcpData);

        // Optionally, verify that the findByServiceDescriptionAndDateRange() method was called on the repository
        verify(gcpRepositoryMock, times(1)).findByServiceDescriptionAndDateRange(eq(serviceDescription), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getBillingDetails() {
    }

    @Test
    void calculateMonthlyTotalBills() {
    }

    @Test
    void generateBillingPeriod() {
    }

    @Test
    void getBillingDetailsUsingRangeAndDate() {
    }

    @Test
    void getServiceTopFiveTotalCosts() {
    }
}