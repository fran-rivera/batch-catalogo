package com.frg.springbatch.rest;

import com.frg.springbatch.common.ExcelWriter;
import com.frg.springbatch.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportService {

    public static final String PROPERTY_REST_API_LOGIN_URL = "rest.api.login.url";

    private static final String PROPERTY_REST_API_URL_CATEGORIES = "rest.api.categories.url";
    private static final String PROPERTY_REST_API_URL_PRODUCTS = "rest.api.products.url";

    @Autowired
    private Environment environment;


    @Autowired
    private ExcelWriter excelWriter;

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

        System.out.println("ExportService.getCatalogo : Obtenidas " + categoryData.getDetailCategory().size() + " categorias!");


        try {
            List<CategoryDetail> categoryError = new ArrayList<>();
            List<ProductDetail> productDetailList = new ArrayList<>();
            excelWriter.createBook();

            //CategoryDetail category = categoryData.getDetailCategory().get(0);
            for (CategoryDetail category : categoryData.getDetailCategory()) {

                if (category.getCODIGO().equals(category.getGRUPO())) {
                    System.out.println("ExportService.getCatalogo: Obteniendo productos de la Categoria: " + category.getDescricao());

                    String urlProducto = environment.getProperty(PROPERTY_REST_API_URL_PRODUCTS).replace("<login>", loginData.getGuid()).replace("<categoria>", category.getCODIGO());

                    try {

                        ResponseEntity<ProductDTO> responseProducts = restTemplate.getForEntity(urlProducto, ProductDTO.class);
                        ProductDTO productData = responseProducts.getBody();

                        System.out.println("ExportService.getCatalogo: Obtenidos: " + productData.getProductDetail().size() + " productos para la Categoría: " + category.getDescricao());

                        for (ProductDetail productDetail : productData.getProductDetail()){
                            productDetailList.add(productDetail);
                        }

                        excelWriter.addSheet(category.getDescricao());
                        excelWriter.addRow(excelWriter.getHoja(), productDetailList);

                        if (excelWriter.getNumHojas() == 2) break;


                    } catch (HttpServerErrorException e) {

                        System.out.println("No se han obtenido productos para la categoria: " + category.getDescricao());
                        categoryError.add(category);

                    }
                }
            }

            excelWriter.closeBook(excelWriter.getHoja());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
