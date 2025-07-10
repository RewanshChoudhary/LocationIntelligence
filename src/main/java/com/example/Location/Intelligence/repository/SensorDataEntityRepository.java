package com.example.Location.Intelligence.repository;

import com.example.Location.Intelligence.dto.QueryResponseDto;
import com.example.Location.Intelligence.model.SensorDataEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorDataEntityRepository extends JpaRepository<SensorDataEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SensorDataEntity d WHERE d.createdAt < :cutoff")
    void deleteOlderThan(@Param("cutoff") LocalDateTime cutoff);


    @Query(value = """
    SELECT 
        sensor_type,
        value,
        unit,
        time_stamp,
        name,
        category,
        ST_AsGeoJSON(location) AS locationGeoJson
    FROM sensor_data_entity
    WHERE ST_DWithin(
        location::geography,
        ST_SetSRID(ST_MakePoint(:long, :lat), 4326)::geography,
            
        5000
    )
    """, nativeQuery = true)

    //Scans all the enteries at the range of 5 kms from a point

    List<Object[]> getAllSensorsWithinDist(
            @Param("long") double longitude,
            @Param("lat") double latitude
    );


    @Query(value = """
    SELECT 
        sensor_type,
        value,
        unit,
        time_stamp,
        name,
        category,
        ST_AsGeoJSON(location) AS locationGeoJson,
        ST_Distance(location::geography, ST_MakePoint(:long, :lat)::geography) AS distance
    FROM 
        sensor_data_entity
    WHERE 
        ST_DWithin(location::geography, ST_MakePoint(:long, :lat)::geography, 5000)
    ORDER BY 
        distance
    LIMIT 10
    """, nativeQuery = true)
    List<Object[]> getNearbySensorsRaw(@Param("long") double longitude, @Param("lat") double latitude);



    @Query(value = """
    SELECT 
        sensor_type,
        value,
        unit,
        time_stamp,
        name,
        category,
        ST_AsGeoJSON(location) AS locationGeoJson
    FROM sensor_data_entity
    WHERE location && ST_MakeEnvelope(:minlong, :minlat, :maxlong, :maxlat, 4326)
    """, nativeQuery = true)

    //Scans for all enteries nearby in a defined subspace

    List<QueryResponseDto> getNearbySubspace(
            @Param("minlong") double minLong,
            @Param("minlat") double minLat,
            @Param("maxlong") double maxLong,
            @Param("maxlat") double maxLat
    );


@Query(value= """
select sensor_type,
        value,
        unit,
        time_stamp,
        name,
        category,
        ST_AsGeoJSON(location) AS locationGeoJson
     
        FROM sensor_data_entity
        WHERE sensor_type = :type
        AND name = :locName
        AND time_stamp >= :currTime
    """,nativeQuery = true)
List <QueryResponseDto> getLiveSensorData(@Param("type") String type,@Param("name") String locName,@Param("currTime") LocalDateTime currTime);


@Query(value ="""
    select sensor_type,
        value,
        unit,
        time_stamp,
        name,
        category,
        ST_AsGeoJSON(location) AS locationGeoJson
     
        FROM sensor_data_entity
        WHERE location = :locName
        and sensor_type= :sensorType
        
        ORDER BY value desc
        limit :ranking
              """,nativeQuery = true)
public List<QueryResponseDto> getPeaksInListing(@Param("locName") String locName,@Param("sensorType") String sensorType,@Param("ranking") int ranking);


}


