/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebApp;

/**
 *
 * @author J. Eduardo Arias
 */
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebController {
    
    @GetMapping("/status")
    public String status() {
        return "{\"status\":\"Greetings from Spring Boot Ping Pong Synergy "
                + java.time.LocalDate.now() + ", "
                + java.time.LocalTime.now()
                + ". " + "The server is Runnig!\"}";
    }
    
    @PostMapping("/login")
    public  String login(@RequestParam(value = "user") String user,@RequestParam(value = "password") String password){
        System.out.println("{\"user\":\""+ user+"\" , \"password\": \""+ password  +"\"  }");
        return "{\"user\":\""+ user+"\"}";
    }
    
}
