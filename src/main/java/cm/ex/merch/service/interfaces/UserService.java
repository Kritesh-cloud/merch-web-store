package cm.ex.merch.service.interfaces;

import cm.ex.merch.entity.User;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.response.user.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    //CREATE
    public UserResponse addUser(User user);

    //READ
    public User getUserById(UUID userId);
    public List<User> listAllUsers();
    public List<User> listAllUserByAuthority(Authority authority);

    //UPDATE
    public UserResponse UpdateUser(User user);
    public UserResponse BanUserById(UUID userId, String reason);
    public UserResponse BanUsersByIds(List<UUID> userId, String reason);

    //DELETE
    public UserResponse deleteUserById(UUID userId);
}