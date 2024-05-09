package org.mh.client.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/first/test")
    public String test(){
        return "first Test";
    }
}
