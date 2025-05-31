package com.nomad.services;

import com.nomad.model.Trip;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RestTripService implements TripService {

    //TODO: check local host port
    private static final String BASE_API_URL = "http://localhost:9000/bookings";
    private RestClient restClient = RestClient.create(BASE_API_URL);
//    @Override
//    public boolean isValidService(List<Service> services, int serviceId) {
//        boolean isValid = true;
//        for(Service service : services) {
//            if(service.getServiceId() == serviceId) {
//                return isValid;
//            }
//        }
//        return !isValid;
//    }

    @Override
    public boolean checkValidTripId(List<Trip> trips, int tripId) {
        boolean isValid = true;
        for (Trip trip : trips) {
            if(trip.getTripId() == tripId) {
                return isValid;
            }
        }
        return !isValid;
    }

    @Override
    public Trip getTrip(int id) throws RestClientResponseException {
        return restClient.get()
                .uri("/" + id)
                .retrieve()
                .body(Trip.class);
    }

    @Override
    public Trip add(Trip newTrip) {
        Trip returnedTrip = null;
        try {
            returnedTrip = restClient.post()
                    .contentType(APPLICATION_JSON)
                    .body(newTrip)
                    .retrieve()
                    .body(Trip.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getStatusCode().value());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return returnedTrip;
    }

    @Override
    public boolean update(Trip updatedTrip) {
        boolean isSuccess = false;
        try {
            restClient.put()
                    .uri("/" + updatedTrip.getTripId())
                    .contentType(APPLICATION_JSON)
                    .body(updatedTrip)
                    .retrieve()
                    .toBodilessEntity();

            isSuccess = true;
        } catch (RestClientResponseException e) {
            System.out.println(e.getStatusCode().value());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean delete(int tripId) {
        boolean isSuccess = false;
        try {
            restClient.delete()
                    .uri("/" + tripId)
                    .retrieve()
                    .toBodilessEntity();

            isSuccess = true;
        } catch (RestClientResponseException e) {
            System.out.println(e.getStatusCode().value());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return isSuccess;
    }
}
