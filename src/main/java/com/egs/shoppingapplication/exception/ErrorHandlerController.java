package com.egs.shoppingapplication.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController {

    @RequestMapping("/error-page")
    @ResponseBody
    public String getErrorPath(@RequestParam String msg) {
        return "<center><h1>Something went wrong</h1>" +
                "<h3>" + msg + "</h3></center>";
    }
}