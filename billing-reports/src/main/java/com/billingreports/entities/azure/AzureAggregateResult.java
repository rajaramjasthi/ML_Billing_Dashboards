package com.billingreports.entities.azure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AzureAggregateResult {
    private String ResourceType;
    private double totalCost;
    private String currency;
}
