package com.billingreports.entities.gcp;

import com.billingreports.serializer.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "GCP")
public class Gcp {


    @Id
    @Field("_id")
    private String id;

    @Field("Service ID")
    private String serviceId;

    @Field("Date")
    private Date date;

    @Field("Service description")
    private String serviceDescription;

    @Field("Cost ($)")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double cost;

}
