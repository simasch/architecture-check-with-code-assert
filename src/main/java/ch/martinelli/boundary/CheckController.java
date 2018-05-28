package ch.martinelli.boundary;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    public String check() {
        return "ok";
    }
}
