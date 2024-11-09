package cm.ex.merch.configuration;

import cm.ex.merch.entity.User;
import cm.ex.merch.entity.product.Category;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.repository.AuthorityRepository;
import cm.ex.merch.repository.CategoryRepository;
import cm.ex.merch.repository.ProductRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Bean
    CommandLineRunner initDatabase() {
        DatabasePopulate dbp = new DatabasePopulate();
        return args -> {

            dbp.populateAuthority(authorityRepository);
            dbp.populateUser(userRepository, authorityRepository, passwordEncoder);
            dbp.populateCategory(categoryRepository);
//            dbp.populateProduct(productRepository);

        };
    }

    private void populateAuthority(AuthorityRepository authorityRepository){
        if (authorityRepository.count() == 0) {
            authorityRepository.save(new Authority("admin", "1"));
            authorityRepository.save(new Authority("moderator", "2"));
            authorityRepository.save(new Authority("user", "3"));

            System.out.println("Authority Repository has been populated with three(3) initial authorities.");
        }
    }

    private void populateUser(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder){
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
    }

    private void populateCategory(CategoryRepository categoryRepository){
        if(categoryRepository.count() == 0){
            categoryRepository.save(new Category("Apparel"));
            categoryRepository.save(new Category("Figurines"));
            categoryRepository.save(new Category("Posters"));
            categoryRepository.save(new Category("Cards"));
            categoryRepository.save(new Category("Mugs"));
            categoryRepository.save(new Category("Keychains "));
            categoryRepository.save(new Category("Accessories"));
            categoryRepository.save(new Category("Pins"));
            categoryRepository.save(new Category("Costumes"));
            categoryRepository.save(new Category("Decor"));

            System.out.println("Category Repository has been populated with ten(10) initial categories.");
        }
    }

    private void populateProduct(ProductRepository productRepository){
        if(productRepository.count() == 0){



            System.out.println("Category Repository has been populated with ten(10) initial products.");
        }
    }
}