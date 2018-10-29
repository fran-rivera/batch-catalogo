package com.frg.springbatch.rest.in;

import com.frg.springbatch.login.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * This class demonstrates how we can read the input of our batch job from an
 * external REST API.
 *
 * @author Fran Rivera
 */
class RESTLoginReader implements ItemReader<LoginDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTLoginReader.class);

    private final String apiUrl;
    private final RestTemplate restTemplate;


    private LoginDTO loginData;

    RESTLoginReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public LoginDTO read() throws Exception {
        LOGGER.info("Reading the information of login");

        if (loginDataIsNotInitialized()) {
            loginData = fetchLoginDataFromAPI();
        }

        return loginData;
    }

    private boolean loginDataIsNotInitialized() {
        return this.loginData == null;
    }

    private LoginDTO fetchLoginDataFromAPI() {
        LOGGER.debug("Fetching login data from an external API by using the url: {}", apiUrl);

        ResponseEntity<LoginDTO> response = restTemplate.getForEntity(apiUrl, LoginDTO.class);
        LoginDTO loginData = response.getBody();

        LOGGER.debug("Login: ", loginData.getGuid());

        return loginData;
    }
}
