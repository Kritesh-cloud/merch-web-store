package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.request.SignInUserDto;
import cm.ex.merch.dto.request.SignUpUserDto;
import cm.ex.merch.entity.User;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.dto.response.authentication.LoginResponse;
import cm.ex.merch.dto.response.user.BasicUserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    //CREATE
    public BasicUserResponse addUser(SignUpUserDto signUpUserDto);

    //READ
    public LoginResponse getUserToken(SignInUserDto signInUserDto);
    public User getUserById(UUID userId);
    public List<User> listAllUsers();
    public List<User> listAllUserByAuthority(Authority authority);

    //UPDATE
    public BasicUserResponse UpdateUser(User user);
    public BasicUserResponse BanUserById(UUID userId, String reason);
    public BasicUserResponse BanUsersByIds(List<UUID> userId, String reason);

    //DELETE
    public BasicUserResponse deleteUserById(UUID userId);
}