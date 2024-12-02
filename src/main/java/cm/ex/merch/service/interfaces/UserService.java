package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.request.authentication.SignInUserDto;
import cm.ex.merch.dto.request.authentication.SignUpUserDto;
import cm.ex.merch.dto.request.user.UpdateUserDto;
import cm.ex.merch.dto.response.user.UserInfoDto;
import cm.ex.merch.dto.response.user.UserInfoListDto;
import cm.ex.merch.dto.response.authentication.LoginResponse;
import cm.ex.merch.dto.response.authentication.BasicUserResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {

    //CREATE
    public BasicUserResponse addUser(SignUpUserDto signUpUserDto);

    //READ
    public LoginResponse getUserToken(SignInUserDto signInUserDto);
    public UserInfoDto getUserById() throws AccessDeniedException;
    public UserInfoListDto listAllUsers() throws AccessDeniedException;
    public UserInfoListDto listAllUserByAuthority(String authority) throws AccessDeniedException;

    //UPDATE
    public BasicUserResponse updateUser(UpdateUserDto updateUserDto) throws AccessDeniedException;
    public BasicUserResponse banUserById(String userId, String reason) throws AccessDeniedException;
    public BasicUserResponse banUsersByIds(List<String> userId, String reason);

    //DELETE
    public BasicUserResponse deleteUserById() throws AccessDeniedException;
}