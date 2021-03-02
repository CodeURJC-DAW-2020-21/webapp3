package es.dawequipo3.growing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GrowingController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
