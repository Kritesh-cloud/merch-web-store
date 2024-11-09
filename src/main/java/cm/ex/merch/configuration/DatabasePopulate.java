package cm.ex.merch.configuration;

import cm.ex.merch.entity.User;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.repository.AuthorityRepository;
import cm.ex.merch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DatabasePopulate {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Check if data already exists to avoid duplication
            if (authorityRepository.count() == 0) {
                authorityRepository.save(new Authority("admin", "1"));
                authorityRepository.save(new Authority("moderator", "2"));
                authorityRepository.save(new Authority("user", "3"));

                System.out.println("Authority Repository has been populated with three(3) initial authorities.");
            }

            if(userRepository.count() == 0){
                Authority authority = authorityRepository.findByAuthority("user");
                Set<Authority> authoritySet = new HashSet<>();
                authoritySet.add(authority);
                userRepository.save(new User("Ape","ape@gmail.com",passwordEncoder.encode("password"),authoritySet));
                userRepository.save(new User("Bear","Bear@gmail.com",passwordEncoder.encode("password"),authoritySet));
                userRepository.save(new User("Cat","cat@gmail.com",passwordEncoder.encode("password"),authoritySet));
                userRepository.save(new User("Dog","dog@gmail.com",passwordEncoder.encode("password"),authoritySet));
                userRepository.save(new User("Elephant","elephant@gmail.com",passwordEncoder.encode("password"),authoritySet));

                System.out.println("User Repository has been populated with five(5) initial users.");
            }
        };
    }
}
