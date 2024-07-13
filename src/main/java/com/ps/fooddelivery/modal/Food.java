package com.ps.fooddelivery.modal;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "food")
public class Food {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;

}
