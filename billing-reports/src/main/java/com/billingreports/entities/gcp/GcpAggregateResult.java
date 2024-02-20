package com.billingreports.entities.gcp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GcpAggregateResult {
    private String serviceDescription;
    private double totalCost;
}
