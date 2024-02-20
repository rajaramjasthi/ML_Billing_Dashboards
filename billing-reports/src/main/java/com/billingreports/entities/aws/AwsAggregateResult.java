package com.billingreports.entities.aws;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsAggregateResult {
    private String Service;
    private double totalCost;
}
