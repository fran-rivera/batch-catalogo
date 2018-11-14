package com.frg.springbatch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExportController {

    @Autowired
    ExportService exportService;

    /**
     * Handle request to download an Excel document
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getCatalogo")
    public String download() {
        exportService.getCatalogo();
        System.out.println("ExportController.download:  Proceso terminado! :) ");
        return "Guay FRG!!";
    }


}
