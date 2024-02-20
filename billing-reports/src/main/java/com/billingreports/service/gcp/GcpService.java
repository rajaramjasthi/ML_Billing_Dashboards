package com.billingreports.service.gcp;

import com.billingreports.entities.gcp.Gcp;
import com.billingreports.entities.gcp.GcpAggregateResult;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GcpService {


    // getting all data
    public List<Gcp> getAllData();

    //getting all data between dates
    public List<Gcp> getAllDataBydateRange(String startDate, String endDate);

    // getting all data based on months
    public List<Gcp> getAllDataByMonths(int months);

    //getting the data by servicedesc and daterange
    public List<Gcp> getDataByServiceDescAndDateRange(String service, String startDate, String endDate);

    // getting the data by servicedesc and months
    public List<Gcp> getDataByServiceDescAndMonths(String serviceDesc, int months);

    // Get All Details Method
    public List<Gcp> getBillingDetails(String serviceDescription, String startDate, String endDate , Integer months);

    // Get Monthly Total Bills
    List<Map<String, Double>> calculateMonthlyTotalBills(List<Gcp> billingDetails);

    // getting the unique serviceDescription
    public List<String> getDistinctServiceDescriptions();

    // Get Billing Period
    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    public List<Gcp> getBillingDetailsUsingRangeAndDate(String startDate, String endDate, Integer months);

    // Get Top 5 Consumers
    public List<GcpAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months);

}
