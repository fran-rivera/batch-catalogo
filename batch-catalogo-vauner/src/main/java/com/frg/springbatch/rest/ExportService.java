package com.frg.springbatch.rest;

import com.frg.springbatch.login.CategoryDTO;
import com.frg.springbatch.login.CategoryDetail;
import com.frg.springbatch.login.LoginDTO;
import com.frg.springbatch.login.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExportService {

    public static final String PROPERTY_REST_API_LOGIN_URL = "rest.api.login.url";

    private static final String PROPERTY_REST_API_URL_CATEGORIES = "rest.api.categories.url";
    private static final String PROPERTY_REST_API_URL_PRODUCTS = "rest.api.products.url";

    @Autowired
    private Environment environment;

    private final RestTemplate restTemplate;



    public ExportService() {
        restTemplate = new RestTemplate();
    }

    public void getCatalogo() {

        ResponseEntity<LoginDTO> response = restTemplate.getForEntity(environment.getRequiredProperty(PROPERTY_REST_API_LOGIN_URL), LoginDTO.class);
        LoginDTO loginData = response.getBody();

        String urlCategory = environment.getProperty(PROPERTY_REST_API_URL_CATEGORIES).replace("<login>", loginData.getGuid());

        ResponseEntity<CategoryDTO> responseCategories = restTemplate.getForEntity(urlCategory, CategoryDTO.class);
        CategoryDTO categoryData = responseCategories.getBody();

        System.out.println("ExportService.getCatalogo : Obtenidas " + categoryData.getDetailCategory().size()+ " categorias!");

        for (CategoryDetail category : categoryData.getDetailCategory()){
            if (category.getCODIGO() .equals(category.getGRUPO())){
                System.out.println("ExportService.getCatalogo: Obteniendo productos de la Categoria: " + category.getDescricao());

                String urlProducto = environment.getProperty(PROPERTY_REST_API_URL_PRODUCTS).replace("<login>", loginData.getGuid()).replace("<categoria>",category.getCODIGO());

                ResponseEntity<ProductDTO> responseProducts = restTemplate.getForEntity(urlProducto, ProductDTO.class);
                ProductDTO productData = responseProducts.getBody();

                System.out.println("ExportService.getCatalogo: Obtenidos: " +productData.getProductDetail().size()+" productos para la Categoría:"+category.getDescricao());
            }
        }


    }
}
