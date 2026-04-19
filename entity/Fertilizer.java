package com.ecosystem.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fertilizer")
public class Fertilizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageUrl;

    private String name;

    private String type;

    private Double quantity;

    private Double price;

    private String company;

    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}