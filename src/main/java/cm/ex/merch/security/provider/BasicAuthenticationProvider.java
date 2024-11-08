package cm.ex.merch.security.provider;

import cm.ex.merch.entity.User;
import cm.ex.merch.repository.UserRepository;
import cm.ex.merch.security.authentication.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(BasicAuthenticationProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Basic Authentication Provider");
        UserAuth basicUserAuth = (UserAuth) authentication;

        User user = userRepository.findByEmail(basicUserAuth.getEmail());

        if(user == null) {
//            return new UserAuth();
            return new UserAuth(false, null, null, null, null, null);
        }

        if(passwordEncoder.matches(basicUserAuth.getPassword(), user.getPassword())) {

//            TODO get authority list from User and convert into authorityList in UserAuth
//            Set<GrantedAuthority> authoritySet = new HashSet<>();
//            for(Authority auth : user.getAuthorities() ) {
//                authoritySet.add(new SimpleGrantedAuthority(auth.getName()));
//            }
//            List<GrantedAuthority> authorityList = new ArrayList<>(authoritySet);

            return new UserAuth(true, user.getEmail(), null, null,user.getFullName(), null);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
