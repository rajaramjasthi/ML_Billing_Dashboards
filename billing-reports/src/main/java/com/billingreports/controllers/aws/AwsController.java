package com.billingreports.controllers.aws;

import com.billingreports.entities.aws.Aws;
import com.billingreports.entities.aws.AwsAggregateResult;
import com.billingreports.exceptions.ValidDateRangeException;
import com.billingreports.service.aws.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aws")
public class AwsController {

    @Autowired
    private AwsService awsService;

//    @GetMapping("/month")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Aws>> getBillingDetailsForDuration(@RequestParam(name = "months") int months) {
//        List<Aws> billingDetails = awsService.getBillingDetailsForDuration(months);
//        return new ResponseEntity<>(billingDetails, HttpStatus.OK);
//    }
//
//    @GetMapping("/data/count")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public Long getDataCount() {
//        return awsService.getCountOfData();
//    }

    @GetMapping("/getall")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Aws>> getAllServices() {
        List<Aws> awsData = awsService.getAllServices();
        return new ResponseEntity<List<Aws>>(awsData, HttpStatus.OK);
    }

//    @GetMapping("/service/startdate/enddate")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Aws>> getDataByServiceAndDateRange(@RequestParam String service,
//                                                                  @RequestParam String startDate, @RequestParam String endDate) {
//        List<Aws> data = awsService.getDataByServiceAndDateRange(service, startDate, endDate);
//        return ResponseEntity.ok(data);
//    }

//    @GetMapping("/service/months")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Aws>> getBillingDetailsForDuration(@RequestParam("service") String service,
//                                                                  @RequestParam("months") int months) {
//
//        List<Aws> billingDetails = awsService.getBillingDetailsForDuration(service, months);
//
//        if (billingDetails.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(billingDetails);
//        }
//    }

    @GetMapping("/distinct-services")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String[]> getDistinctServices() {
        String[] distinctServices = awsService.getUniqueServicesAsArray();
        if (distinctServices.length == 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(distinctServices);
        }
    }

    @GetMapping("/billing-details")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getBillingDetails(
            @RequestParam(value = "service"/* , required = false */) String service,
            @RequestParam /* (required = false) */ String startDate, @RequestParam String endDate,
            @RequestParam(defaultValue = "0"/* required = false */) Integer months) {

        if ((service == null || service.isEmpty()) && (startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty()) && months <= 0) {
            throw new ValidDateRangeException("Enter Valid Inputs");
        }

        // Replace the following placeholders with your actual service calls
        List<Aws> billingDetails = awsService.getBillingDetails(service, startDate, endDate, months);

        List<Map<String, Double>> monthlyTotalAmounts = awsService.calculateMonthlyTotalBills(billingDetails);

        Double totalAmount = billingDetails.stream().mapToDouble(Aws::getAmount).sum();

        List<Map<String, Object>> billingPeriod = awsService.generateBillingPeriod(startDate, endDate, months);

        List<AwsAggregateResult> aggregateResults = awsService.getServiceTopFiveTotalCosts(startDate, endDate, months);
        // Create a response map
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("billingDetails", billingDetails);
        response.put("monthlyTotalAmounts", monthlyTotalAmounts);
        response.put("totalAmount", totalAmount);
        response.put("billingPeriod", billingPeriod);
        response.put("top5Services", aggregateResults);

        return ResponseEntity.ok(response);

    }

}
