package com.billingreports.entities.azure;

import com.billingreports.serializer.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Azure")
public class Azure {
    @Id
    @Field("_id")
    private String id;

    @Field("ResourceType")
    private String resourceType;

//    @Field("CostUSD")
//    private double costUSD;

    @Field("Cost")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double cost;

    @Field("Currency")
    private String currency;

    @Field("UsageDate")
    private String usageDate;

    @Field("SubscriptionID")
    private String subscriptionId;

    @Field("SubscriptionName")
    private String subscriptionName;
}
