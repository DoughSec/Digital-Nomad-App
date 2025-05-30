package com.nomad.services;

import com.nomad.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RestUserService implements UserService {

    private static final String BASE_API_URL = "http://localhost:8080/users";
    private RestClient restClient = RestClient.create(BASE_API_URL);

    @Override
    public User getUserById(int id) throws RestClientResponseException {
        return restClient.get()
                .uri("/" + id)
                .retrieve()
                .body(User.class);
    }

    @Override
    public User getUserByUsername(String userName) throws RestClientResponseException {
        return restClient.get()
                .uri("/" + userName)
                .retrieve()
                .body(User.class);
    }

    @Override
    public User createUser(User newUser) {
        User returnedUser = null;
        try {
            returnedUser = restClient.post()
                    .contentType(APPLICATION_JSON)
                    .body(newUser)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getStatusCode().value());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return returnedUser;
    }

}
