package cm.ex.merch.controller;

import cm.ex.merch.entity.User;
import cm.ex.merch.entity.user.Message;
import cm.ex.merch.repository.UserMessageRepository;
import cm.ex.merch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    @PostMapping("/addUser")
    public String addUser(){

        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@gmail.com");
        user.setPassword("password");

        userRepository.save(user);
        return "";
    }

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody String message){
        System.out.println("message: "+message);
        try {
            User user = userRepository.findByEmail("john@gmail.com");

            Message msg = new Message();
            msg.setMessage(message);
            msg.setDatetime(LocalDateTime.now());
            msg.setUser(user);
            System.out.println(msg.toString());

            userMessageRepository.save(msg);

        }
        catch (Exception e){
            System.out.println("EXX: "+e);
        }
    }

}
