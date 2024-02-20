package com.billingreports.controllers.gcp;

import com.billingreports.entities.gcp.Gcp;
import com.billingreports.entities.gcp.GcpAggregateResult;
import com.billingreports.exceptions.ValidDateRangeException;
import com.billingreports.service.gcp.GcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gcp")
public class GcpController {
    @Autowired
    private GcpService gcpService;

    @GetMapping("/getall")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Gcp>> getAll() {

        List<Gcp> getall = gcpService.getAllData();
        return new ResponseEntity<List<Gcp>>(getall, HttpStatus.OK);
    }

    @GetMapping("/distinctServiceDescriptions")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<String>> getDistinctServiceDescriptions() {
        List<String> serviceName = gcpService.getDistinctServiceDescriptions();
        return ResponseEntity.ok(serviceName);
    }

//    @GetMapping("/dataBetweenDates")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Gcp>> getDataBetweenDates(@RequestParam("startDate") String startDate,
//                                                         @RequestParam("endDate") String endDate) {
//        List<Gcp> betweenDates = gcpService.getAllDataBydateRange(startDate, endDate);
//        return ResponseEntity.ok(betweenDates);
//    }

//    @GetMapping("/dataByMonths")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Gcp>> getDataByMonths(@RequestParam("months") int months) {
//        List<Gcp> betweenMonths = gcpService.getAllDataByMonths(months);
//        return ResponseEntity.ok(betweenMonths);
//    }

//    @GetMapping("/serviceDesc/Dates")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Gcp>> getDataByServiceDespAndDates(
//            @RequestParam("serviceDescription") String serviceDescription, @RequestParam("startDate") String startDate,
//            @RequestParam("endDate") String endDate) {
//        List<Gcp> betweenServiceAndDates = gcpService.getDataByServiceDescAndDateRange(serviceDescription, startDate,
//                endDate);
//        return ResponseEntity.ok(betweenServiceAndDates);
//    }

//    @GetMapping("/serviceDesc/months")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public ResponseEntity<List<Gcp>> getDataByServicedescAndMonths(
//            @RequestParam("serviceDescription") String serviceDescription,
//            @RequestParam("months") int months) {
//        List<Gcp> betweenServiceAndMonths = gcpService.getDataByServiceDescAndMonths(serviceDescription, months);
//        return ResponseEntity.ok(betweenServiceAndMonths);
//    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<?> getBillingDetails(
            @RequestParam String serviceDescription,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "0") Integer months
    ) {
        if (serviceDescription == null && startDate == null && endDate == null && months == null) {
            // If any required parameter is missing, return a response indicating the required fields
            throw new ValidDateRangeException("Enter valid inputs");
        }
        // Replace the following placeholders with your actual service calls
        List<Gcp> billingDetails = gcpService.getBillingDetails(serviceDescription, startDate, endDate, months);

        List<Map<String, Double>> monthlyTotalAmounts = gcpService.calculateMonthlyTotalBills(billingDetails);

        Double totalAmount = billingDetails.stream().mapToDouble(Gcp::getCost).sum();
        List<Map<String, Object>> billingPeriod = gcpService.generateBillingPeriod(startDate, endDate, months);

        List<GcpAggregateResult> aggregateResults = gcpService.getServiceTopFiveTotalCosts(startDate, endDate, months);
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
