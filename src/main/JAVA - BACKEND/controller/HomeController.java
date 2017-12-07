package controller;

import com.google.gson.Gson;
import entity.Home;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        Gson gson = new Gson();
        Home homeObj = new Home();
        homeObj.setId(1);
        homeObj.setTest("test");
        return gson.toJson(homeObj);
    }
}
