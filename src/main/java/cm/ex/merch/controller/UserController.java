package cm.ex.merch.controller;

import cm.ex.merch.dto.request.user.UpdateUserDto;
import cm.ex.merch.dto.response.authentication.BasicUserResponse;
import cm.ex.merch.dto.response.user.UserInfoDto;
import cm.ex.merch.dto.response.user.UserInfoListDto;
import cm.ex.merch.service.UserServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    final UserServiceImplement userServiceImplement;

    public UserController(UserServiceImplement userServiceImplement) {
        this.userServiceImplement = userServiceImplement;
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> getUserById() throws AccessDeniedException {
        return new ResponseEntity<UserInfoDto>(userServiceImplement.getUserById(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<UserInfoListDto> listAllUsers() throws AccessDeniedException {
        return new ResponseEntity<UserInfoListDto>(userServiceImplement.listAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/list-by-authority/{authority}")
    public ResponseEntity<UserInfoListDto> listAllUserByAuthority(@PathVariable String authority) throws AccessDeniedException {
        return new ResponseEntity<UserInfoListDto>(userServiceImplement.listAllUserByAuthority(authority), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<BasicUserResponse> UpdateUser(@RequestBody UpdateUserDto updateUserDto) throws AccessDeniedException {
        return new ResponseEntity<BasicUserResponse>(userServiceImplement.updateUser(updateUserDto), HttpStatus.OK);
    }

    @PostMapping("/ban")
//    POST /ban?userId=12345&reason=just-cause
    public ResponseEntity<BasicUserResponse> BanUserById(@RequestParam String userId, @RequestParam String reason) throws AccessDeniedException {
        return new ResponseEntity<BasicUserResponse>(userServiceImplement.banUserById(userId, reason), HttpStatus.OK);
    }

    @PostMapping("/multiple-ban")
    public ResponseEntity<BasicUserResponse> BanUsersByIds(@RequestParam List<String> userIds, @RequestParam String reason) throws AccessDeniedException {
        return new ResponseEntity<BasicUserResponse>(userServiceImplement.banUsersByIds(userIds, reason), HttpStatus.OK);
    }

    @PostMapping("/me/delete-account")
    public ResponseEntity<BasicUserResponse> deleteUserById() throws AccessDeniedException {
        return new ResponseEntity<BasicUserResponse>(userServiceImplement.deleteUserById(), HttpStatus.OK);
    }

}
