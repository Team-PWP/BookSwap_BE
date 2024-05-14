package team_pwp.swap_be.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/test2")
    public String test2() {
        return "Hello World2!";
    }

    @GetMapping("/test3")
    public String test3() {
        return "Hello World3!";
    }

    @GetMapping("/test4")
    public String test4() {
        return "Hello World4!";
    }
}
