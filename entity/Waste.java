package com.ecosystem.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "waste")
public class Waste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String name;
    private String type;
    private Double quantity;
    private Double price;
    private String location;

    @JsonIgnoreProperties({"password"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}