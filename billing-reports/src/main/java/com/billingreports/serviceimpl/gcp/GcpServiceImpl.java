package com.billingreports.serviceimpl.gcp;

import com.billingreports.entities.gcp.Gcp;
import com.billingreports.entities.gcp.GcpAggregateResult;
import com.billingreports.exceptions.ValidDateRangeException;
import com.billingreports.repositories.gcp.GcpRespository;
import com.billingreports.service.gcp.GcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GcpServiceImpl implements GcpService {

    @Autowired
    private GcpRespository gcpRepository;

    @Override
    public List<Gcp> getAllData() {

        return gcpRepository.findAll();
    }


    @Override
    public List<String> getDistinctServiceDescriptions() {
        List<String> serviceDescriptions = gcpRepository.findDistinctServiceDescriptionBy();
        return extractUniqueServiceDescriptions(serviceDescriptions);
    }

    private List<String> extractUniqueServiceDescriptions(List<String> serviceDescriptions) {
        Set<String> uniqueServiceSet = new HashSet<>();
        List<String> uniqueServiceList = new ArrayList<>();

        for (String jsonStr : serviceDescriptions) {
            String serviceDescription = extractServiceDescription(jsonStr);
            if (serviceDescription != null) {
                uniqueServiceSet.add(serviceDescription);
            }
        }

        uniqueServiceList.addAll(uniqueServiceSet);
        return uniqueServiceList;
    }

    private String extractServiceDescription(String jsonStr) {

        int startIndex = jsonStr.indexOf("Service description\": \"") + "Service description\": \"".length();
        int endIndex = jsonStr.indexOf("\"", startIndex);
        if (startIndex >= 0 && endIndex >= 0) {
            return jsonStr.substring(startIndex, endIndex);
        }
        return null; // Return null if extraction fails
    }

    @Override
    public List<Gcp> getAllDataBydateRange(String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startLocalDate = LocalDate.parse(startDate, formatter).atStartOfDay();
        LocalDateTime endLocalDate = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);

        // Calculate the period between startLocalDate and endLocalDate
        Period period = Period.between(startLocalDate.toLocalDate(), endLocalDate.toLocalDate());

        // Check if the period is more than one year
        if ((period.getYears() > 1) || (period.getYears() == 1 && period.getMonths() > 0) || (period.getYears() == 1 && period.getDays() > 0)) {
            System.out.println("Years: " + period.getYears() + ", Months: " + period.getMonths() + ", Days: " + period.getDays());
            throw new ValidDateRangeException("Select in range within 12 months");
        }

        System.out.println("Start Date: " + startLocalDate);
        System.out.println("End date: " + endLocalDate);

        System.out.println("Years: " + period.getYears() + ", Months: " + period.getMonths() + ", Days: " + period.getDays());

        return gcpRepository.findByDateRange(startLocalDate, endLocalDate);
    }

    @Override
    public List<Gcp> getAllDataByMonths(int months) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime endDate = LocalDate.now().atTime(LocalTime.MAX);
        LocalDateTime startDate = LocalDate.now().minusMonths(months - 1).withDayOfMonth(1).atStartOfDay();
        System.out.println("Start Date: " + startDate);
        System.out.println("End date: " + endDate);

        return gcpRepository.findByDateRange(startDate, endDate);
    }

    @Override
    public List<Gcp> getDataByServiceDescAndDateRange(String serviceDescription, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startLocalDate = LocalDate.parse(startDate, formatter).atStartOfDay();
        LocalDateTime endLocalDate = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);

        // Calculate the period between startLocalDate and endLocalDate
        Period period = Period.between(startLocalDate.toLocalDate(), endLocalDate.toLocalDate());

        // Check if the period is more than one year
        if ((period.getYears() > 1) || (period.getYears() == 1 && period.getMonths() > 0) || (period.getYears() == 1 && period.getDays() > 0)) {
            System.out.println("Years: " + period.getYears() + ", Months: " + period.getMonths() + ", Days: " + period.getDays());
            throw new ValidDateRangeException("Select in range within 12 months");
        }

        System.out.println("Start Date: " + startDate);
        System.out.println("End date: " + endDate);

        System.out.println("Years: " + period.getYears() + ", Months: " + period.getMonths() + ", Days: " + period.getDays());

        return gcpRepository.findByServiceDescriptionAndDateRange(serviceDescription, startLocalDate, endLocalDate);
    }

    @Override
    public List<Gcp> getDataByServiceDescAndMonths(String serviceDesc, int months) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime endDate = LocalDate.now().atTime(LocalTime.MAX);
        LocalDateTime startDate = LocalDate.now().minusMonths(months - 1).withDayOfMonth(1).atStartOfDay();
        System.out.println("Start Date: " + startDate);
        System.out.println("End date: " + endDate);

        return gcpRepository.findByServiceDescriptionAndDateRange(serviceDesc, startDate, endDate);
    }


    @Override
    public List<Gcp> getBillingDetails(String serviceDescription, String startDate, String endDate, Integer months) {
        List<Gcp> billingDetails;

        /* months == null || */
        if (serviceDescription.isEmpty() && startDate != null && endDate != null && months == 0) {

            billingDetails = getAllDataBydateRange(startDate, endDate);
        } else /* months == null || */ if (serviceDescription.isEmpty() && Objects.requireNonNull(startDate).isEmpty() && Objects.requireNonNull(endDate).isEmpty() && months > 0) {

            billingDetails = getAllDataByMonths(months);

        } else if (!serviceDescription.isEmpty() && startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty() && months == 0) {

            billingDetails = getDataByServiceDescAndDateRange(serviceDescription, startDate, endDate);
        } else if (!serviceDescription.isEmpty() && Objects.requireNonNull(startDate).isEmpty() && endDate.isEmpty() && months != null && months > 0) {

            billingDetails = getDataByServiceDescAndMonths(serviceDescription, months);

        } else {
            throw new ValidDateRangeException("Please give the valid date or duration");
        }

        // Return the response map or adjust the return value as needed
        return billingDetails;
    }

    @Override
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<Gcp> billingDetails) {
        Map<String, Double> monthlyTotalBillsMap = new LinkedHashMap<>();
        Map<Integer, String> monthNames = Map.ofEntries(
                Map.entry(1, "Jan"), Map.entry(2, "Feb"), Map.entry(3, "Mar"),
                Map.entry(4, "Apr"), Map.entry(5, "May"), Map.entry(6, "Jun"),
                Map.entry(7, "Jul"), Map.entry(8, "Aug"), Map.entry(9, "Sep"),
                Map.entry(10, "Oct"), Map.entry(11, "Nov"), Map.entry(12, "Dec")
        );

        for (Gcp gcp : billingDetails) {
            Date usageDate = gcp.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(usageDate);
            int year = calendar.get(Calendar.YEAR);
            int monthNumber = calendar.get(Calendar.MONTH) + 1; // Adding 1 to match the map keys
            String monthName = monthNames.get(monthNumber);

            double cost = gcp.getCost();
            String monthYear = monthName + "-" + year;

            // If the month key exists in the map, add the cost; otherwise, put a new entry
            monthlyTotalBillsMap.put(monthYear, monthlyTotalBillsMap.getOrDefault(monthYear, 0.0) + cost);
        }

        // Sort the monthly total bills by year and month
        List<Map.Entry<String, Double>> sortedBills = new ArrayList<>(monthlyTotalBillsMap.entrySet());
        Collections.sort(sortedBills, (entry1, entry2) -> {
            String[] parts1 = entry1.getKey().split("-");
            String[] parts2 = entry2.getKey().split("-");
            int year1 = Integer.parseInt(parts1[1]);
            int year2 = Integer.parseInt(parts2[1]);
            int monthOrder1 = monthNames.entrySet().stream()
                    .filter(entry -> entry.getValue().equalsIgnoreCase(parts1[0]))
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(-1);
            int monthOrder2 = monthNames.entrySet().stream()
                    .filter(entry -> entry.getValue().equalsIgnoreCase(parts2[0]))
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(-1);
            if (year1 != year2) {
                return Integer.compare(year1, year2);
            }
            return Integer.compare(monthOrder1, monthOrder2);
        });

        List<Map<String, Double>> monthlyTotalBillsList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sortedBills) {
            Map<String, Double> monthEntry = new LinkedHashMap<>();
            monthEntry.put(entry.getKey(), entry.getValue());
            monthlyTotalBillsList.add(monthEntry);
        }

        return monthlyTotalBillsList;
    }


    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months) {
        List<Map<String, Object>> billingPeriod = new ArrayList<>();
        Map<String, Object> periodData = new HashMap<>();

        if (months != null && months > 0) {
            LocalDate currentDate = LocalDate.now();

            // Calculate the start date as the first day of the month 'months' months ago
            LocalDate startDate1 = currentDate.minusMonths(months - 1).withDayOfMonth(1);

            // Format the dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
            String formattedStartDate = startDate1.format(formatter);
            String formattedEndDate = currentDate.format(formatter);

            // Generate the period data with the desired format
            periodData.put("BillingPeriod", formattedStartDate + " to " + formattedEndDate);
            billingPeriod.add(periodData);
        } else if (startDate != null && endDate != null) {
            // Logic for start date and end date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
            LocalDate parsedStartDate = LocalDate.parse(startDate);
            LocalDate parsedEndDate = LocalDate.parse(endDate);

            String formattedStartDate = parsedStartDate.format(formatter);
            String formattedEndDate = parsedEndDate.format(formatter);

            Map<String, Object> periodData1 = new HashMap<>();
            periodData1.put("BillingPeriod", formattedStartDate + " to " + formattedEndDate);
            billingPeriod.add(periodData1);
        }

        return billingPeriod;
    }

    @Override
    public List<Gcp> getBillingDetailsUsingRangeAndDate(String startDate, String endDate, Integer months) {
        List<Gcp> billingDetails;

        if (startDate != null && endDate != null && months == 0) {

            billingDetails = getAllDataBydateRange(startDate, endDate);
        } else if (Objects.requireNonNull(startDate).isEmpty() && Objects.requireNonNull(endDate).isEmpty() && months > 0) {

            billingDetails = getAllDataByMonths(months);
        } else {
            throw new ValidDateRangeException("Please select valid months or dates for top 5 services");
        }
        // Return the response map or adjust the return value as needed
        return billingDetails;
    }

    @Override
    public List<GcpAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months) {
        List<Gcp> billingDetails = getBillingDetailsUsingRangeAndDate(startDate, endDate, months);

        Map<String, Double> serviceTotalCostMap = billingDetails.stream()
                .collect(Collectors.groupingBy(Gcp::getServiceDescription, Collectors.summingDouble(Gcp::getCost)));

        List<GcpAggregateResult> top5Services = serviceTotalCostMap.entrySet().stream()
                .map(entry -> new GcpAggregateResult(entry.getKey(), round(entry.getValue(), 2)))
                .sorted((b1, b2) -> Double.compare(b2.getTotalCost(), b1.getTotalCost())) // Sort in descending order
                .limit(5)
                .collect(Collectors.toList());

        return top5Services;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr);
    }

    // Utility method to format LocalDate to String
    private String formatDate(LocalDate date) {
        return date.toString();
    }

}
