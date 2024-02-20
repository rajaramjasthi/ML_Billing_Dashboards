package com.billingreports.service.aws;

import com.billingreports.entities.aws.Aws;
import com.billingreports.entities.aws.AwsAggregateResult;

import java.util.List;
import java.util.Map;

public interface AwsService {
//    public Aws save(Aws aws);

    // Get Details Methods
    public List<Aws> getAllDataByDateRange(String startDate, String endDate);

    public List<Aws> getBillingDetailsForDuration(int months);

    public List<Aws> getBillingDetailsForDuration(String service, int months);

    public List<Aws> getDataByServiceAndDateRange(String service, String startDate, String endDate);

    //Get All Details
    public List<Aws> getBillingDetails(String serviceName, String startDate, String endDate, Integer months);

    // Get Unique Services Method
    public String[] getUniqueServicesAsArray();

    //Get All Services Method
    public List<Aws> getAllServices();

    //Get Count of Data
    public Long getCountOfData();

    // Get Billing Period Method
    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    // Get Monthly Total Bills
    List<Map<String, Double>> calculateMonthlyTotalBills(List<Aws> billingDetails);

    // Get Top 5 Services
    public List<AwsAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months);

    public List<Aws> getBillingDetailsUsingRangeAndDuration(String startDate, String endDate, Integer months);
}
