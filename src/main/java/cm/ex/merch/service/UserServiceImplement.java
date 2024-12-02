package cm.ex.merch.service;

import cm.ex.merch.dto.request.authentication.SignInUserDto;
import cm.ex.merch.dto.request.authentication.SignUpUserDto;
import cm.ex.merch.dto.request.user.UpdateUserDto;
import cm.ex.merch.dto.response.user.UserInfoDto;
import cm.ex.merch.dto.response.user.UserInfo;
import cm.ex.merch.dto.response.user.UserInfoListDto;
import cm.ex.merch.entity.User;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.entity.user.Ban;
import cm.ex.merch.repository.AuthorityRepository;
import cm.ex.merch.repository.BanRepository;
import cm.ex.merch.repository.UserRepository;
import cm.ex.merch.dto.response.authentication.LoginResponse;
import cm.ex.merch.dto.response.authentication.BasicUserResponse;
import cm.ex.merch.security.authentication.UserAuth;
import cm.ex.merch.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements UserService, UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BanRepository banRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    Logger logger = LoggerFactory.getLogger(UserServiceImplement.class);

    @Override
    public BasicUserResponse addUser(SignUpUserDto signUpUserDto) {

        BasicUserResponse userResponse = new BasicUserResponse(false, "User with this email already exists", "create");

        User userEmail = userRepository.findByEmail(signUpUserDto.getEmail());
        if(userEmail != null) return userResponse;

        Set<Authority> authorityList = Set.of(authorityRepository.findByAuthority("user"));

        User user = modelMapper.map(signUpUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityList);

        userRepository.save(user);

        return new BasicUserResponse(true, "Account created successfully", "create");
    }

    @Override
    public LoginResponse getUserToken(SignInUserDto signInUserDto){

        LoginResponse loginResponse = new LoginResponse(false,"User not found, cannot make token",null);

        User user = userRepository.findByEmail(signInUserDto.getEmail());
        if(user == null || !passwordEncoder.matches(signInUserDto.getPassword(),user.getPassword())) return loginResponse;

        UserAuth userAuth = new UserAuth(user.getId().toString(),true,user.getEmail(),user.getPassword(),null,user.getPassword(),convertToGrantedAuthorities(user.getAuthority()));
        String jwtToken = jwtService.generateToken(userAuth);

        return new LoginResponse(true,"User token",jwtToken);
    }

    @Override
    public UserInfoDto getUserById() throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        UserInfoDto invalidBasicUserInfoDto = new UserInfoDto(false,"User not found", null);
        User user = userRepository.findUserByUserId(userAuth.getId());

        if(user == null || !userAuth.getEmail().equalsIgnoreCase(user.getEmail())) return invalidBasicUserInfoDto;

        UserInfo userInfo = modelMapper.map(userAuth, UserInfo.class);
        userInfo.setAuthorityList(convertToStringListAuthorities(userAuth.getAuthority()));

        return new UserInfoDto(true, "User details", userInfo);
    }

    @Override
    public UserInfoListDto listAllUsers() throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        if (userAuth.getAuthority().contains(new SimpleGrantedAuthority(authorityRepository.findByAuthority("user").getAuthority()))) throw new AccessDeniedException("Access denied. Not authorized.");

        List<User> userList = userRepository.findAll();

        List<UserInfo> userInfoList = userList.stream().map(
                user -> {
                    UserInfo userInfo = new UserInfo(user.getId().toString(),user.getFullName(),user.getEmail(),null);
                    userInfo.setAuthorityList(convertToStringListAuthorities(userAuth.getAuthority()));
                    return userInfo;
                })
        .toList();
        return new UserInfoListDto(true,"User details list",userInfoList);
    }

    @Override
    public UserInfoListDto listAllUserByAuthority(String authority) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        if (userAuth.getAuthority().contains(new SimpleGrantedAuthority(authorityRepository.findByAuthority("user").getAuthority()))) throw new AccessDeniedException("Access denied. Not authorized.");

        List<User> userList = userRepository.findUserByAuthority(authority);
        List<UserInfo> userInfoList = userList.stream().map(
                        user -> {
                            UserInfo userInfo = new UserInfo(user.getId().toString(),user.getFullName(),user.getEmail(),null);
                            userInfo.setAuthorityList(convertToStringListAuthorities(userAuth.getAuthority()));
                            return userInfo;
                        })
                .toList();
        return new UserInfoListDto(true,"User details list",userInfoList);
    }

    @Override
    public BasicUserResponse updateUser(UpdateUserDto updateUserDto) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        BasicUserResponse invalidBasicUserResponse = new BasicUserResponse(false,"User not found", "update");
        User user = userRepository.findUserByUserId(updateUserDto.getId());

        if(user == null || !userAuth.getEmail().equalsIgnoreCase(user.getEmail())) return invalidBasicUserResponse;

        Set<Authority> authorityList = Set.of(authorityRepository.findByAuthority("user"));

        user = modelMapper.map(updateUserDto, User.class);
        user.setId(UUID.fromString(updateUserDto.getId()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityList);

        userRepository.save(user);

        return new BasicUserResponse(true, "User updated successfully", "update");
    }

    @Override
    public BasicUserResponse banUserById(String userId, String reason) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        if (userAuth.getAuthority().contains(new SimpleGrantedAuthority(authorityRepository.findByAuthority("user").getAuthority()))) throw new AccessDeniedException("Access denied. Not authorized.");

        BasicUserResponse invalidBasicUserResponse = new BasicUserResponse(false,"User not found", "update");
        User user = userRepository.findUserByUserId(userId);

        if(user == null || !userAuth.getEmail().equalsIgnoreCase(user.getEmail())) return invalidBasicUserResponse;

        invalidBasicUserResponse = new BasicUserResponse(true,"This user is already banned", "update");
        Ban bannedUser = banRepository.findBannedUserByUserId(userId);
        if(bannedUser != null) return invalidBasicUserResponse;

        Ban banUser = new Ban(reason,user);
        banRepository.save(banUser);

        return new BasicUserResponse(true,"User "+user.getFullName()+" is banned", "update");
    }

    @Override
    public BasicUserResponse banUsersByIds(List<String> userId, String reason){

        userId.forEach(id -> {
            try {
                BasicUserResponse basicUserResponse = banUserById(id, reason);
            } catch (AccessDeniedException e) {
                throw new RuntimeException("ERROR: error while banning. "+e);
            }
        });

        return new BasicUserResponse(true,"All users are banned successfully", "update");
    }

    @Override
    public BasicUserResponse deleteUserById() throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        BasicUserResponse invalidBasicUserResponse = new BasicUserResponse(false,"User not found", "update");
        User user = userRepository.findUserByUserId(userAuth.getId());

        if(user == null || !userAuth.getEmail().equalsIgnoreCase(user.getEmail())) return invalidBasicUserResponse;

        userRepository.delete(user);
        return new BasicUserResponse(true,"User deleted successfully", "delete");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .username(user.getEmail())
                .authorities(convertToGrantedAuthorities(user.getAuthority()))
                .build();
    }

    private List<GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
    public static List<String> convertToStringListAuthorities(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority) // Extract the authority name
                .collect(Collectors.toList());
    }
}
