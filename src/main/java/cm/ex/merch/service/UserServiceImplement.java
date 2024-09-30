package cm.ex.merch.service;

import cm.ex.merch.entity.User;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.entity.user.Ban;
import cm.ex.merch.repository.BanRepository;
import cm.ex.merch.repository.UserRepository;
import cm.ex.merch.response.user.UserResponse;
import cm.ex.merch.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BanRepository banRepository;

    @Override
    public UserResponse addUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setType("Create");
        userResponse.setStatus(true);
        userResponse.setMessage("Account created successfully");
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null){
            userResponse.setStatus(false);
            userResponse.setMessage("User with this email already exists");
            return userResponse;
        }
        userRepository.save(user);
        return userResponse;
    }

    @Override
    public User getUserById(UUID userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> listAllUserByAuthority(Authority authority){
        return userRepository.findUserByAuthority(authority.getAuthority());
    }

    @Override
    public UserResponse UpdateUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setType("Update");
        userResponse.setStatus(true);
        userResponse.setMessage("User updated successfully");
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail == null){
            userResponse.setStatus(false);
            userResponse.setMessage("User not found");
            return userResponse;
        }
        userRepository.save(user);
        return userResponse;
    }

    @Override
    public UserResponse BanUserById(UUID userId, String reason) {
        UserResponse userResponse = new UserResponse();
        userResponse.setType("Update");
        userResponse.setStatus(true);
        userResponse.setMessage("User banned successfully");
        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            userResponse.setStatus(false);
            userResponse.setMessage("User not found");
            return userResponse;
        }

        Optional<Ban> bannedUser = banRepository.findByUserId(userId);
        if(bannedUser.isPresent()){
            userResponse.setStatus(true);
            userResponse.setMessage("This user is already banned");
            return userResponse;
        }

        Ban banUser = new Ban();
        banUser.setUser(user.get());
        banUser.setDatetime(LocalDateTime.now());
        banUser.setReason(reason);
        banRepository.save(banUser);
        return userResponse;
    }

    @Override
    public UserResponse BanUsersByIds(List<UUID> userId, String reason) {
        UserResponse userResponse = new UserResponse();
        userResponse.setType("Update");
        userResponse.setStatus(true);
        userResponse.setMessage("All users banned successfully");

        try {
            userId.forEach(id -> BanUserById(id, reason));
        }
        catch(Exception e){
            System.out.println("ERROR: Banning multiple users. ERROR: "+e);
            userResponse.setStatus(false);
            userResponse.setMessage("Couldn't ban all of these users.");
            return userResponse;
        }
        return userResponse;
    }

    @Override
    public UserResponse deleteUserById(UUID userId) {
        UserResponse userResponse = new UserResponse();
        userResponse.setType("Update");
        userResponse.setStatus(true);
        userResponse.setMessage("User deleted successfully");
        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            userResponse.setStatus(false);
            userResponse.setMessage("User not found");
            return userResponse;
        }

        userRepository.delete(user.get());
        return userResponse;
    }

}
