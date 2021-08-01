package com.hu.Virtualize.geolocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

@Slf4j
@Component
public class GeoLocation {
    private final List<Location> latLngAddressTable = new ArrayList<>();

    public GeoLocation() {
        String fileLocation = "src/main/resources/static/locationTable/IndiaLocation.csv";

        fetchData(fileLocation);
    }

    /**
     * This function will fetch all details available in csv file and convert into location object list.
     * @param file file location
     */
    public void fetchData(String file) {
        try {
            Scanner sc = new Scanner(new File(file));

            // read every line in the csv file.
            while (sc.hasNext()) {
                String cityLatLng = sc.next();

                // break string into part
                StringTokenizer st = new StringTokenizer(cityLatLng,",");
                String[] arr = new String[3];

                int index = 0;
                while (st.hasMoreTokens()) {
                    arr[index++] = st.nextToken();
                }

                // convert data into location object.
                Location location = new Location();
                location.setCity(arr[0]);
                location.setLat(Double.parseDouble(arr[1]));
                location.setLng(Double.parseDouble(arr[2]));

                latLngAddressTable.add(location);
            }
            sc.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * This function will return the distance between two points.
     * @param latitude1 x for point 1
     * @param longitude1 y for point 1
     * @param latitude2 x for point 2
     * @param longitude2 y for point 2
     * @return distance between two point by  - sqrt( (x2 - x1)^2 + (y2-y1)^2 ) formula
     */
    public Double distanceBetween(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        return Math.sqrt((latitude2 - latitude1)*1.0*(latitude2 - latitude1) + (longitude2 - longitude1)*1.0*(longitude2 - longitude1));
    }

    /**
     * This function will find the city name according to the latitude and longitude
     * @param latitude latitude
     * @param longitude longitude
     * @return return the city for provide lat and lng
     */
    public String findCityByLatAndLng(Double latitude, Double longitude) {
        Location currentLocation = new Location(0.0, 0.0 , "");
        Double minDistance = 0.0;

        for(int i=0;i<latLngAddressTable.size();i++) {
            Location location = latLngAddressTable.get(i);

            if(i == 0) {
                minDistance = distanceBetween(latitude,longitude,location.getLat(),location.getLng());
                currentLocation = location;
            } else {
                Double dis = distanceBetween(latitude,longitude, location.getLat(),location.getLng());

                if(dis < minDistance) {
                    minDistance = dis;
                    currentLocation = location;
                }
            }
        }
        return currentLocation.getCity();
    }
}

// https://simplemaps.com/data/in-cities
