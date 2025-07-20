package com.example.Location.Intelligence.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable

public class LocationInfo {
    @Column(name="location_info_name")
    private String name;
    @Column(name="location_info_category")
    private String category;
    @Transient
    private double latitude;
    @Transient
    private double longitude;
}