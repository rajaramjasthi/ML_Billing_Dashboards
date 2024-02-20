package com.billingreports.serviceimpl.azure;
import com.billingreports.entities.azure.Azure;
import com.billingreports.entities.azure.AzureAggregateResult;
import com.billingreports.service.azure.AzureService;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.billingreports.repositories.azure.AzureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AzureServiceImplTest {

    @Mock
    private AzureRepository azureRepository;

    @Mock
    private AzureService azureService;


    @Mock
    private MongoTemplate mongoTemplateMock;

    @InjectMocks
    private AzureServiceImpl azureServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCountOfData() throws NoSuchFieldException, IllegalAccessException {
        // Create a mock instance of AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Use reflection to set the mock repository
        Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
        field.setAccessible(true);
        field.set(azureService, azureRepositoryMock);

        // Define the expected count
        long expectedCount = 10L; // Example count

        // Stub the behavior of count() method in the mock repository
        when(azureRepositoryMock.count()).thenReturn(expectedCount);

        // Call the method under test
        long actualCount = azureService.getCountOfData();

        // Verify that the method returns the expected count
        assertEquals(expectedCount, actualCount);

        // Optionally, verify that the count() method was called on the repository
        verify(azureRepositoryMock, times(1)).count();
    }


    @Test
    void getAll() throws Exception {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Use reflection to set the mock repository directly
        Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
        field.setAccessible(true);
        field.set(azureService, azureRepositoryMock);

        // Mock data
        List<Azure> expectedAzureList = Arrays.asList(
                new Azure("1", "Resource1", 100.0, "USD", "2022-01-01", "123456", "Subscription1"),
                new Azure("2", "Resource2", 200.0, "USD", "2022-01-02", "789012", "Subscription2")
                // Add more Azure objects as needed
        );

        // Stub the behavior of findAll() method in the mock repository
        when(azureRepositoryMock.findAll()).thenReturn(expectedAzureList);

        // Call the method under test
        List<Azure> actualAzureList = azureService.getAll();

        // Verify that the method returns the expected list
        assertEquals(expectedAzureList, actualAzureList);

        // Optionally, verify that the findAll() method was called on the repository
        verify(azureRepositoryMock, times(1)).findAll();
    }


    @Test
    void getAllDataBydateRange() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findByUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findByUsageDateGreaterThanEqualAndUsageDateLessThanEqual(formattedStartDate, formattedEndDate))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getAllDataBydateRange(formattedStartDate, formattedEndDate);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getAllDataByMonths() {// Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        int months = 3;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months - 1).withDayOfMonth(1);
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findByUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findByUsageDateGreaterThanEqualAndUsageDateLessThanEqual(formattedStartDate, formattedEndDate))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getAllDataByMonths(months);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getDataByResourseTypeAndDateRange() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String resourceType = "ResourceType";
        String startDate = "2022-01-01";
        String endDate = "2022-02-01";
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findByResourceTypeAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findByResourceTypeAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(resourceType, startDate, endDate))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getDataByResourseTypeAndDateRange(resourceType, startDate, endDate);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }


    @Test
    void getDataByResourseTypeAndMonths() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String resourceType = "ResourceType";
        int months = 3; // Example number of months
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months - 1).withDayOfMonth(1);
        String strStartDate = startDate.toString();
        String strEndDate = endDate.toString();
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findByResourceTypeAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findByResourceTypeAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(resourceType, strStartDate, strEndDate))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getDataByResourseTypeAndMonths(resourceType, months);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getDataByResourseTypeAndSubscriptionNameAndDateRange() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String resourceType = "ResourceType";
        String subscriptionName = "SubscriptionName";
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findByResourceTypeAndSubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findByResourceTypeAndSubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(resourceType, subscriptionName, startDate, endDate))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getDataByResourseTypeAndSubscriptionNameAndDateRange(resourceType, subscriptionName, startDate, endDate);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getDataByResourseTypeAndSubscriptionNameAndDuration() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String resourceType = "ResourceType";
        String subscriptionName = "SubscriptionName";
        Integer months = 3; // Example duration
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findByResourceTypeAndSubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findByResourceTypeAndSubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getDataByResourseTypeAndSubscriptionNameAndDuration(resourceType, subscriptionName, months);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getDataBySubscriptionNameAndMonths() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String subscriptionName = "SubscriptionName";
        int months = 3; // Example months
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findBySubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findBySubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(anyString(), anyString(), anyString()))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getDataBySubscriptionNameAndMonths(subscriptionName, months);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getDataBySubscriptionNameAndRange() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String subscriptionName = "SubscriptionName";
        String startDate = "2023-01-01"; // Example start date
        String endDate = "2023-01-31"; // Example end date
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of findBySubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual() method in the mock repository
        when(azureRepositoryMock.findBySubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(anyString(), anyString(), anyString()))
                .thenReturn(expectedData);

        // Call the method under test
        List<Azure> actualData = azureService.getDataBySubscriptionNameAndRange(subscriptionName, startDate, endDate);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }

    @Test
    void getBillingDetails() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String resourceType = "";
        String subscriptionName = "";
        String startDate = "2023-01-01"; // Example start date
        String endDate = "2023-01-31"; // Example end date
        Integer months = 0; // Example months
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Call the method under test directly
        List<Azure> actualData = azureService.getBillingDetails(resourceType, subscriptionName, startDate, endDate, months);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }


    @Test
    void calculateMonthlyTotalBills() {
        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Mock data
        List<Azure> billingDetails = Arrays.asList(
                new Azure("1", "ResourceType1", 100.0, "USD", "2023-01-15", "123456", "Subscription1"),
                new Azure("2", "ResourceType2", 200.0, "USD", "2023-01-20", "789012", "Subscription2"),
                new Azure("3", "ResourceType1", 150.0, "USD", "2023-02-10", "123456", "Subscription1")
                // Add more Azure objects as needed
        );

        // Call the method under test
        List<Map<String, Double>> result = azureService.calculateMonthlyTotalBills(billingDetails);

        // Expected output
        List<Map<String, Double>> expectedOutput = Arrays.asList(
                Collections.singletonMap("Jan-2023", 300.0), // Total cost for January 2023
                Collections.singletonMap("Feb-2023", 150.0)  // Total cost for February 2023
                // Add more expected entries as needed
        );

        // Verify that the method returns the expected result
        assertEquals(expectedOutput, result);
    }

    @Test
    void generateBillingPeriod() {
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set test parameters
        Integer months = 3;
        String startDate = "2023-01-01";
        String endDate = "2023-01-31";

        // Call the method under test with months
        List<Map<String, Object>> resultWithMonths = azureService.generateBillingPeriod(null, null, months);

        // Call the method under test with start date and end date
        List<Map<String, Object>> resultWithDates = azureService.generateBillingPeriod(startDate, endDate, null);

        // Expected output for months
        LocalDate currentDate = LocalDate.now();
        LocalDate startDateMonths = currentDate.minusMonths(months - 1).withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        String formattedStartDateMonths = startDateMonths.format(formatter);
        String formattedEndDateMonths = currentDate.format(formatter);
        String expectedPeriodMonths = formattedStartDateMonths + " to " + formattedEndDateMonths;
        Map<String, Object> expectedDataMonths = Collections.singletonMap("BillingPeriod", expectedPeriodMonths);
        List<Map<String, Object>> expectedOutputMonths = Collections.singletonList(expectedDataMonths);

        // Expected output for start date and end date
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        String formattedStartDate = parsedStartDate.format(formatter);
        String formattedEndDate = parsedEndDate.format(formatter);
        String expectedPeriodDates = formattedStartDate + " to " + formattedEndDate;
        Map<String, Object> expectedDataDates = Collections.singletonMap("BillingPeriod", expectedPeriodDates);
        List<Map<String, Object>> expectedOutputDates = Collections.singletonList(expectedDataDates);

        // Verify that the method returns the expected result with months
        assertEquals(expectedOutputMonths, resultWithMonths);

        // Verify that the method returns the expected result with start date and end date
        assertEquals(expectedOutputDates, resultWithDates);
    }


    @Test
    void getBillingDetailsUsingSubscriptionRangeAndDate() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            java.lang.reflect.Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data
        String subscriptionName = "SubscriptionName";
        String startDate = "2023-01-01";
        String endDate = "2023-01-31";
        Integer months = 0; // Example months
        List<Azure> expectedData = Arrays.asList(/* create your expected Azure objects */);

        // Call the method under test
        List<Azure> actualData = azureService.getBillingDetailsUsingSubscriptionRangeAndDate(subscriptionName, startDate, endDate, months);

        // Verify that the method returns the expected data
        assertEquals(expectedData, actualData);
    }


    @Test
    void getServiceTopFiveTotalCosts() {
        // Mock AzureRepository
        AzureRepository azureRepositoryMock = mock(AzureRepository.class);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Set the azureRepository field directly using reflection
        try {
            java.lang.reflect.Field field = AzureServiceImpl.class.getDeclaredField("azureRepository");
            field.setAccessible(true);
            field.set(azureService, azureRepositoryMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Mock data for getBillingDetailsUsingSubscriptionRangeAndDate method
        String subscriptionName = "SubscriptionName";
        String startDate = "2023-01-01";
        String endDate = "2023-01-31";
        Integer months = 0;
        List<Azure> expectedBillingDetails = Arrays.asList(/* create your expected Azure objects */);

        // Stub the behavior of getBillingDetailsUsingSubscriptionRangeAndDate method
        when(azureService.getBillingDetailsUsingSubscriptionRangeAndDate(subscriptionName, startDate, endDate, months))
                .thenReturn(expectedBillingDetails);

        // Mock data for expected top 5 services
        List<AzureAggregateResult> expectedTop5Services = Arrays.asList(/* create your expected AzureAggregateResult objects */);

        // Call the method under test
        List<AzureAggregateResult> actualTop5Services = azureService.getServiceTopFiveTotalCosts(subscriptionName, startDate, endDate, months);

        // Verify that the method returns the expected top 5 services
        assertEquals(expectedTop5Services, actualTop5Services);
    }

    @Test
    void getDistinctSubscriptionIds() {
        // Mock data for distinct subscription IDs
        List<String> expectedDistinctSubscriptionIds = new ArrayList<>(List.of("SubscriptionID1", "SubscriptionID2", "SubscriptionID3"));

        // Mock the DistinctIterable
        DistinctIterable<String> distinctIterableMock = mock(DistinctIterable.class);
        when(distinctIterableMock.into(anyList())).thenReturn(expectedDistinctSubscriptionIds);

        // Mock the MongoCollection
        MongoCollection mongoCollectionMock = mock(MongoCollection.class);
        when(mongoCollectionMock.distinct(eq("SubscriptionID"), any(Bson.class), eq(String.class))).thenReturn(distinctIterableMock);

        // Mock the MongoTemplate
        MongoTemplate mongoTemplateMock = mock(MongoTemplate.class);
        when(mongoTemplateMock.getCollection("Azure")).thenReturn(mongoCollectionMock);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Use reflection to set the mongoTemplate field
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("mongoTemplate");
            field.setAccessible(true);
            field.set(azureService, mongoTemplateMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Call the method under test
        List<String> actualDistinctSubscriptionIds = azureService.getDistinctSubscriptionIds();

        // Verify that the method returns the expected distinct subscription IDs
        assertEquals(expectedDistinctSubscriptionIds, actualDistinctSubscriptionIds);
    }

    @Test
    void getDistinctSubscriptionNames() {
        // Mock data for distinct subscription names
        List<String> expectedDistinctSubscriptionNames = List.of("SubscriptionName1", "SubscriptionName2", "SubscriptionName3");

        // Mock the DistinctIterable
        DistinctIterable<String> distinctIterableMock = mock(DistinctIterable.class);
        when(distinctIterableMock.into(anyList())).thenReturn(new ArrayList<>(expectedDistinctSubscriptionNames)); // Use ArrayList constructor to create a mutable list

        // Mock the MongoCollection
        MongoCollection mongoCollectionMock = mock(MongoCollection.class);
        when(mongoCollectionMock.distinct(eq("SubscriptionName"), any(Bson.class), eq(String.class))).thenReturn(distinctIterableMock);

        // Mock the MongoTemplate
        MongoTemplate mongoTemplateMock = mock(MongoTemplate.class);
        when(mongoTemplateMock.getCollection("Azure")).thenReturn(mongoCollectionMock);

        // Create an instance of AzureServiceImpl
        AzureServiceImpl azureService = new AzureServiceImpl();

        // Use reflection to set the mongoTemplate field
        try {
            Field field = AzureServiceImpl.class.getDeclaredField("mongoTemplate");
            field.setAccessible(true);
            field.set(azureService, mongoTemplateMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Call the method under test
        List<String> actualDistinctSubscriptionNames = azureService.getDistinctSubscriptionNames();

        // Verify that the method returns the expected distinct subscription names
        assertEquals(expectedDistinctSubscriptionNames, actualDistinctSubscriptionNames);
    }

//    @Test
//    void getDistinctResourceTypeBySubscriptionName() {
//
//    }
//
//    @Test
//    void getDistinctResourceType() {
//    }
}