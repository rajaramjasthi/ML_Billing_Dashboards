package com.billingreports.serviceimpl.aws;
import com.billingreports.entities.aws.Aws;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.billingreports.repositories.aws.AwsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwsServiceImplTest {

    @Mock
    private AwsRepository awsRepository;

    @InjectMocks
    private AwsServiceImpl awsServiceImpl;
    private List<String> uniqueServiceList;
    private Mockito Mockito;
    private AwsServiceImplTest awsServiceSpy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUniqueServicesAsArray() {
        // Given
            List<String> uniqueServiceList = new ArrayList<>();
            uniqueServiceList.add("{\"Service\":\"service1\"}");
            uniqueServiceList.add("{\"Service\":\"service2\"}");
            uniqueServiceList.add("{\"Service\":\"service1\"}");

            // Mocking the behavior of AwsRepository
            when(awsRepository.findDistinctByService()).thenReturn(uniqueServiceList);
            // When
            String[] result = awsServiceImpl.getUniqueServicesAsArray();

            // Then
            assertArrayEquals(new String[]{"service1", "service2"}, result);
        }

    @Test
    void getAllServices() {
        List<Aws> awsData = Arrays.asList(new Aws(), new Aws());
        when(awsRepository.findAll()).thenReturn(awsData);

        // Act
        List<Aws> result = awsServiceImpl.getAllServices();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getCountOfData() {
        // Given
        long expectedCount = 10L;
        when(awsRepository.count()).thenReturn(expectedCount);
        // When
        long actualCount = awsServiceImpl.getCountOfData();

        // Then
        assertEquals(expectedCount, actualCount);
    }


    @Test
    void getBillingDetailsForDuration() {
//        // Given
//        int months = 3;
//        LocalDate endDate = LocalDate.of(2024, 2, 19); // Corrected end date to match actual date
//        LocalDate startDate = endDate.minusMonths(months - 1).withDayOfMonth(1);
//
//        String strStartDate = startDate.toString();
//        String strEndDate = endDate.toString();
//
//        // Mock the behavior of awsRepository.findByStartDateGreaterThanEqualAndStartDateLessThanEqual
//        List<Aws> expectedBillingDetails = Arrays.asList(new Aws(), new Aws()); // Provide expected billing details
//        when(awsRepository.findByStartDateGreaterThanEqualAndStartDateLessThanEqual(strStartDate, strEndDate))
//                .thenReturn(expectedBillingDetails);
//
//        // When
//        List<Aws> actualBillingDetails = awsServiceImpl.getBillingDetailsForDuration(months);
//
//        // Then
//        assertEquals(expectedBillingDetails, actualBillingDetails);
    }

    @Test
    void getAllDataByDateRange() {
        // Given
        String startDate = "2024-01-01";
        String endDate = "2024-02-01";
        List<Aws> expectedBillingDetails = Arrays.asList(new Aws(), new Aws()); // Provide expected billing details

        // Mocking the behavior of awsRepository.findByStartDateGreaterThanEqualAndStartDateLessThanEqual
        when(awsRepository.findByStartDateGreaterThanEqualAndStartDateLessThanEqual(startDate, endDate))
                .thenReturn(expectedBillingDetails);

        // When
        List<Aws> actualBillingDetails = awsServiceImpl.getAllDataByDateRange(startDate, endDate);

        // Then
        assertEquals(expectedBillingDetails, actualBillingDetails);
    }

    @Test
    void getDataByServiceAndDateRange() {
        // Given
        String service = "Amazon Elastic Compute Cloud - Compute";
        String startDate = "2024-01-01";
        String endDate = "2024-02-01";
        List<Aws> expectedBillingDetails = Arrays.asList(new Aws(), new Aws()); // Provide expected billing details

        // Mocking the behavior of awsRepository.findByServiceAndStartDateGreaterThanEqualAndStartDateLessThanEqual
        when(awsRepository.findByServiceAndStartDateGreaterThanEqualAndStartDateLessThanEqual(service, startDate, endDate))
                .thenReturn(expectedBillingDetails);

        // When
        List<Aws> actualBillingDetails = awsServiceImpl.getDataByServiceAndDateRange(service, startDate, endDate);

        // Then
        assertEquals(expectedBillingDetails, actualBillingDetails);
    }

    @Test
    void testGetBillingDetailsForDuration() {
        String service = "Amazon Elastic Compute Cloud - Compute";
        int months = 3;
        List<Aws> expectedBillingDetails = Arrays.asList(new Aws(), new Aws()); // Provide expected billing details

        // Mocking the behavior of awsRepository.findByServiceAndStartDateGreaterThanEqualAndStartDateLessThanEqual
        when(awsRepository.findByServiceAndStartDateGreaterThanEqualAndStartDateLessThanEqual(anyString(), anyString(), anyString()))
                .thenReturn(expectedBillingDetails);

        // When
        List<Aws> actualBillingDetails = awsServiceImpl.getBillingDetailsForDuration(service, months);

        // Then
        assertEquals(expectedBillingDetails, actualBillingDetails);
    }

    @Test
    void getBillingDetails() {
        // Given
        String serviceName = "AmazonCloudWatch";
        String startDate = "2024-01-01";
        String endDate = "2024-02-01";
        Integer months = 0;
        List<Aws> expectedBillingDetails = new ArrayList<>();

        // When
        List<Aws> actualBillingDetails = awsServiceImpl.getBillingDetails(serviceName, startDate, endDate, months);

        // Then
        assertEquals(expectedBillingDetails, actualBillingDetails);
    }


    @Test
    void generateBillingPeriod() {
        // Given
        AwsServiceImpl awsServiceImpl = new AwsServiceImpl();
        String startDate = null;
        String endDate = null;
        Integer months = 3;

        // When
        List<Map<String, Object>> billingPeriod = awsServiceImpl.generateBillingPeriod(startDate, endDate, months);

        // Then
        assertEquals(1, billingPeriod.size());
        LocalDate currentDate = LocalDate.now();
        LocalDate expectedStartDate = currentDate.minusMonths(months - 1).withDayOfMonth(1);
        String expectedFormattedStartDate = expectedStartDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH));
        String expectedFormattedEndDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH));
        assertEquals(expectedFormattedStartDate + " to " + expectedFormattedEndDate, billingPeriod.get(0).get("BillingPeriod"));

        // Given
        startDate = "2023-01-01";
        endDate = "2023-02-01";
        months = null;

        // When
        billingPeriod = awsServiceImpl.generateBillingPeriod(startDate, endDate, months);

        // Then
        assertEquals(1, billingPeriod.size());
        String expectedBillingPeriod = "01-Jan-2023 to 01-Feb-2023";
        assertEquals(expectedBillingPeriod, billingPeriod.get(0).get("BillingPeriod"));
    }

    @Test
    void calculateMonthlyTotalBills() {
        // Given
        Aws aws1 = mock(Aws.class);
        Mockito.when(aws1.getStartDate()).thenReturn("2023-01-01");
        Mockito.when(aws1.getAmount()).thenReturn(100.0);

        Aws aws2 = mock(Aws.class);
        Mockito.when(aws2.getStartDate()).thenReturn("2023-02-01");
        Mockito.when(aws2.getAmount()).thenReturn(200.0);

        AwsServiceImpl awsServiceImpl = new AwsServiceImpl();
        List<Aws> billingDetails = Arrays.asList(aws1, aws2);

        // When
        List<Map<String, Double>> monthlyTotalBills = awsServiceImpl.calculateMonthlyTotalBills(billingDetails);

        // Then
        assertEquals(2, monthlyTotalBills.size());

        Map<String, Double> januaryBills = monthlyTotalBills.get(0);
        assertEquals(1, januaryBills.size());
        assertEquals(100.0, januaryBills.get("Jan-2023"));

        Map<String, Double> februaryBills = monthlyTotalBills.get(1);
        assertEquals(1, februaryBills.size());
        assertEquals(200.0, februaryBills.get("Feb-2023"));
    }

    @Test
    void getBillingDetailsUsingRangeAndDuration() {

    }



    @Test
    void getServiceTopFiveTotalCosts () {
    }
}