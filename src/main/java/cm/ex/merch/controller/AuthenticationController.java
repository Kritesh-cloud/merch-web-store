package cm.ex.merch.controller;

import cm.ex.merch.dto.request.authentication.SignInUserDto;
import cm.ex.merch.dto.request.authentication.SignUpUserDto;
import cm.ex.merch.dto.response.authentication.LoginResponse;
import cm.ex.merch.dto.response.authentication.BasicUserResponse;
import cm.ex.merch.service.UserServiceImplement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("")
@RestController
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserServiceImplement userService;

    @GetMapping("/test")
    public String test(){
        return "Merch Test Controller";
    }

    @PostMapping("/signUp")
    public ResponseEntity<BasicUserResponse> signUp(@RequestBody @Valid SignUpUserDto signUpUserDto) {
        logger.info("[INFO] Authentication.controller - register. SignUpUserDto : {}", signUpUserDto.toString());
        return ResponseEntity.ok(userService.addUser(signUpUserDto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<LoginResponse> signIn(@RequestBody @Valid SignInUserDto signInUserDto) {

        logger.info("[[INFO]] Authentication.controller - register. SignInUserDto : {}", signInUserDto.toString());
        LoginResponse loginResponse = userService.getUserToken(signInUserDto);
        logger.info("[[INFO]] Authentication.controller - register. loginResponse : {}",loginResponse.toString());
        return ResponseEntity.ok(loginResponse);
    }

}
