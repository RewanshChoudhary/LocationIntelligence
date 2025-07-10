package com.example.Location.Intelligence.service;

import com.example.Location.Intelligence.dto.QueryResponseDto;
import com.example.Location.Intelligence.repository.SensorDataEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class ListingPeaksService {
    private final SensorDataEntityRepository sensorDataEntityRepository;


    public List<QueryResponseDto> getDataByRanking(String locName,String sensorType,int ranking){



        return sensorDataEntityRepository.getPeaksInListing(locName,sensorType,ranking);
    }



}
