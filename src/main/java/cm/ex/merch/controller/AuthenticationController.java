package cm.ex.merch.controller;

import cm.ex.merch.dto.request.SignInUserDto;
import cm.ex.merch.dto.request.SignUpUserDto;
import cm.ex.merch.response.token.LoginResponse;
import cm.ex.merch.response.user.UserResponse;
import cm.ex.merch.service.UserServiceImplement;
import cm.ex.merch.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("")
@RestController
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserServiceImplement userService;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @GetMapping("/test")
    public String test(){

        logger.trace("A TRACE Message"); // doesn't work
        logger.debug("A DEBUG Message"); // doesn't work
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
        logger.info("secret: {}",secretKey);

        return "Merch Test Controller";
    }

    @GetMapping("/data")
    public String data(){

        logger.trace("A TRACE Message"); // doesn't work
        logger.debug("A DEBUG Message"); // doesn't work
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Merch Data Controller";
    }

    @GetMapping("/signUp")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid SignUpUserDto signUpUserDto) {
        logger.info("[INFO] Authentication.controller - register. SignUpUserDto : {}", signUpUserDto.toString());
        return ResponseEntity.ok(userService.addUser(signUpUserDto));
    }

    @GetMapping("/signIn")
    public ResponseEntity<LoginResponse> signIn(@RequestBody @Valid SignInUserDto signInUserDto) {
        logger.info("[INFO] Authentication.controller - register. SignInUserDto : {}", signInUserDto.toString());
        return ResponseEntity.ok(userService.getUserToken(signInUserDto));
    }

    @GetMapping("/me")
    public void getMe(){

    }

}
