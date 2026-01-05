package Controller;

import DTO.TalonDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("get")
    public String get(@RequestBody TalonDTO talonDTO) {
        return "Hello World"+talonDTO;


    }

}
