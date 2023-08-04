package nl.novi.backendgarageservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private String myName;

    @GetMapping("/hello")
    public String sayHello(@RequestParam(required = false) String name) {
        if (name != null) {
            return "Hello " + name;
        } else {
            return "Hello stranger";
        }
    }

    @PostMapping("/save")
    public void saveName(@RequestParam String name) {
        this.myName = name;
    }

    @GetMapping("/retrieve")
    public String retrieveName() {
        return this.myName;
    }
}
