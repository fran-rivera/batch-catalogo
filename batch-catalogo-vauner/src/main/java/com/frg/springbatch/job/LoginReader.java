package com.frg.springbatch.job;

import com.frg.springbatch.login.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This class demonstrates how we can read the input of our batch job from an
 * external REST API.
 *
 * @author Fran Rivera
 */
public class LoginReader implements ItemReader<LoginDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginReader.class);

    private final String apiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    private static final String PROPERTY_REST_API_URL_CATEGORIES = "rest.api.categories.url";

    private LoginDTO loginData;

    LoginReader(String apiUrl, RestTemplate restTemplate) {
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

    public LoginDTO fetchLoginDataFromAPI() {
        LOGGER.debug("Fetching login data from an external API by using the url: {}", apiUrl);

        ResponseEntity<LoginDTO> response = restTemplate.getForEntity(apiUrl, LoginDTO.class);
        LoginDTO loginData = response.getBody();

        //ResponseEntity<CategoryDTO_old> responseCategories = restTemplate.getForEntity(environment.getRequiredProperty(PROPERTY_REST_API_URL_CATEGORIES), CategoryDTO_old.class);
        //CategoryDTO_old categoryData = responseCategories.getBody();


        LOGGER.debug("Login: ", loginData.getGuid());

        return loginData;
    }
}
