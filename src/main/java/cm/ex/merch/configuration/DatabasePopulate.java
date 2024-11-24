package cm.ex.merch.configuration;

import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.User;
import cm.ex.merch.entity.image.Image;
import cm.ex.merch.entity.product.Category;
import cm.ex.merch.entity.product.Review;
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Bean
    CommandLineRunner initDatabase() {
        DatabasePopulate dbp = new DatabasePopulate();
        return args -> {

            dbp.populateAuthority(authorityRepository);
            dbp.populateCategory(categoryRepository);
            dbp.populateUser(userRepository, authorityRepository, passwordEncoder);
            dbp.populateProduct(productRepository, categoryRepository, imageRepository);
            dbp.populateReview(productRepository, userRepository, reviewRepository);

        };
    }

    private void populateAuthority(AuthorityRepository authorityRepository) {
        if (authorityRepository.count() == 0) {
            authorityRepository.save(new Authority("admin", "1"));
            authorityRepository.save(new Authority("moderator", "2"));
            authorityRepository.save(new Authority("user", "3"));

            System.out.println("Authority Repository has been populated with three(3) initial authorities.");
        }
    }

    private void populateUser(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        if (userRepository.count() == 0) {
            Authority authorityAdmin = authorityRepository.findByAuthority("admin");
            Authority authorityModerator = authorityRepository.findByAuthority("moderator");
            Authority authorityUser = authorityRepository.findByAuthority("user");
            Set<Authority> authoritySet = new HashSet<>();

            authoritySet.add(authorityAdmin);
            userRepository.save(new User("Admin", "admin@gmail.com", passwordEncoder.encode("password"), authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityModerator);
            userRepository.save(new User("Moderator One", "moderator1@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("Moderator Two", "moderator2@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("Moderator Three", "moderator3@gmail.com", passwordEncoder.encode("password"), authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityUser);
            userRepository.save(new User("User One", "user1@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("User Two", "user2@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("User Three", "user3@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("User Four", "user4@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("User Five", "user5@gmail.com", passwordEncoder.encode("password"), authoritySet));
            userRepository.save(new User("User Six", "user6@gmail.com", passwordEncoder.encode("password"), authoritySet));

            System.out.println("User Repository has been populated with five(5) initial users.");
        }
    }

    private void populateCategory(CategoryRepository categoryRepository) {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("apparel"));
            categoryRepository.save(new Category("figurines"));
            categoryRepository.save(new Category("posters"));
            categoryRepository.save(new Category("cards"));
            categoryRepository.save(new Category("mugs"));
            categoryRepository.save(new Category("keychains"));
            categoryRepository.save(new Category("accessories"));
            categoryRepository.save(new Category("pins"));
            categoryRepository.save(new Category("costumes"));
            categoryRepository.save(new Category("decor"));
            categoryRepository.save(new Category("miscellaneous"));

            System.out.println("Category Repository has been populated with ten(10) initial categories.");
        }
    }

    private void populateProduct(ProductRepository productRepository, CategoryRepository categoryRepository, ImageRepository imageRepository) throws IOException {
        if (productRepository.count() == 0) {
            byte[] imageData;
            Product lastProduct;
            List<Product> productList = new ArrayList<>();
            String deviceFilePath = "/home/kritesh-thapa/allfile/coding/backend/SpringApi/MerchWebStore/src/main/java/cm/ex/merch/configuration/images/";

            productList.add(new Product("Super Mario T-Shirt", "Super Mario", "A classic red T-shirt featuring Super Mario graphics.", "Nintendo", 25, 100, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Pikachu Hoodie", "Pokémon", "A cozy hoodie with Pikachu's face on it.", "Nintendo", 45, 50, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Zelda Triforce Necklace", "The Legend of Zelda", "A gold-plated necklace featuring the Triforce symbol.", "Nintendo", 15, 75, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("accessories")));
            productList.add(new Product("Master Chief Figurine", "Halo", "A detailed action figurine of Master Chief.", "Xbox", 30, 25, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("figurines")));
            productList.add(new Product("Cyberpunk 2077 Poster", "Cyberpunk 2077", "A vibrant poster with Cyberpunk 2077 artwork.", "CD Projekt Red", 10, 150, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("posters")));
            productList.add(new Product("Pokémon Card Pack", "Pokémon", "A pack of collectible Pokémon trading cards.", "Nintendo", 5, 500, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("cards")));
            productList.add(new Product("Overwatch Mug", "Overwatch", "A ceramic mug with the Overwatch logo.", "Blizzard", 12, 120, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("mugs")));
            productList.add(new Product("Minecraft Creeper Keychain", "Minecraft", "A keychain with a mini Creeper figurine.", "Mojang", 7, 80, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("keychains")));
            productList.add(new Product("Elden Ring Pin Set", "Elden Ring", "A set of enamel pins inspired by Elden Ring.", "Bandai Namco", 10, 200, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("pins")));
            productList.add(new Product("Witcher Costume", "The Witcher", "A costume designed like Geralt of Rivia's armor.", "CD Projekt Red", 70, 20, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("costumes")));
            productList.add(new Product("Vault Boy Bobblehead", "Fallout", "A Fallout Vault Boy bobblehead for decor.", "Bethesda", 15, 60, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("decor")));
            productList.add(new Product("Sonic T-Shirt", "Sonic the Hedgehog", "A T-shirt with Sonic's iconic face.", "Sega", 20, 75, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Assassin's Creed Hidden Blade Replica", "Assassin's Creed", "A realistic replica of the Hidden Blade.", "Ubisoft", 50, 25, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("accessories")));
            productList.add(new Product("Link Figurine", "The Legend of Zelda", "A collectible figurine of Link with his sword.", "Nintendo", 35, 30, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("figurines")));
            productList.add(new Product("Resident Evil Poster", "Resident Evil", "A spooky Resident Evil poster.", "Capcom", 8, 150, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("posters")));
            productList.add(new Product("Yugi's Deck Cards", "Yu-Gi-Oh!", "A set of cards inspired by Yugi's deck.", "Konami", 15, 60, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("cards")));
            productList.add(new Product("Halo Logo Mug", "Halo", "A ceramic mug with the Halo logo.", "Xbox", 10, 90, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("mugs")));
            productList.add(new Product("Fortnite Llama Keychain", "Fortnite", "A mini Llama keychain from Fortnite.", "Epic Games", 6, 100, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("keychains")));
            productList.add(new Product("Dark Souls Crest Pin", "Dark Souls", "An enamel pin with the Dark Souls crest.", "Bandai Namco", 9, 200, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("pins")));
            productList.add(new Product("Mario Bros Costume", "Super Mario", "A costume of Mario from Super Mario Bros.", "Nintendo", 65, 30, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("costumes")));
            productList.add(new Product("Zelda Decorative Shield", "The Legend of Zelda", "A decorative shield resembling the Hylian Shield.", "Nintendo", 50, 15, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("decor")));
            productList.add(new Product("Minecraft T-Shirt", "Minecraft", "A T-shirt with Creeper graphics.", "Mojang", 18, 100, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Lara Croft Figurine", "Tomb Raider", "A collectible figurine of Lara Croft.", "Square Enix", 28, 40, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("figurines")));
            productList.add(new Product("Bloodborne Hunter Poster", "Bloodborne", "A poster of the Hunter from Bloodborne.", "FromSoftware", 9, 120, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("posters")));
            productList.add(new Product("Final Fantasy Cards", "Final Fantasy", "A collectible set of Final Fantasy trading cards.", "Square Enix", 12, 75, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("cards")));
            productList.add(new Product("Cuphead Mug", "Cuphead", "A retro-style mug featuring Cuphead.", "Studio MDHR", 10, 60, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("mugs")));
            productList.add(new Product("Pikachu Keychain", "Pokémon", "A mini Pikachu keychain.", "Nintendo", 5, 150, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("keychains")));
            productList.add(new Product("Kratos T-Shirt", "God of War", "A black T-shirt featuring Kratos and his Leviathan Axe.", "PlayStation", 22, 85, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Aloy Hoodie", "Horizon Zero Dawn", "A hoodie with artwork of Aloy in a post-apocalyptic setting.", "Guerrilla Games", 48, 40, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Ezio Auditore Necklace", "Assassin's Creed", "A necklace inspired by Ezio's iconic hidden blade.", "Ubisoft", 18, 70, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("accessories")));
            productList.add(new Product("Spider-Man Figurine", "Spider-Man", "A detailed figurine of Spider-Man in his classic pose.", "Marvel", 40, 60, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("figurines")));
            productList.add(new Product("League of Legends Poster", "League of Legends", "A high-resolution poster featuring popular champions.", "Riot Games", 12, 130, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("posters")));
            productList.add(new Product("Magic: The Gathering Booster Pack", "Magic: The Gathering", "A booster pack of collectible Magic cards.", "Wizards of the Coast", 5, 300, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("cards")));
            productList.add(new Product("Apex Legends Mug", "Apex Legends", "A ceramic mug with the Apex Legends logo.", "EA", 13, 90, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("mugs")));
            productList.add(new Product("Animal Crossing Leaf Keychain", "Animal Crossing", "A green leaf keychain inspired by Animal Crossing.", "Nintendo", 8, 120, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("keychains")));
            productList.add(new Product("Persona 5 Phantom Thief Pin", "Persona 5", "An enamel pin with the Phantom Thief logo.", "Atlus", 10, 150, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("pins")));
            productList.add(new Product("Samus Aran Costume", "Metroid", "A cosplay costume inspired by Samus Aran's power suit.", "Nintendo", 80, 10, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("costumes")));
            productList.add(new Product("Elder Scrolls Decorative Map", "The Elder Scrolls", "A decorative map of Tamriel.", "Bethesda", 25, 50, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("decor")));
            productList.add(new Product("Pac-Man T-Shirt", "Pac-Man", "A retro T-shirt featuring Pac-Man and ghosts.", "Bandai Namco", 19, 70, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Doom Slayer Helmet", "Doom", "A replica of the Doom Slayer's iconic helmet.", "Bethesda", 55, 25, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("accessories")));
            productList.add(new Product("Tracer Figurine", "Overwatch", "A collectible figurine of Tracer with her Pulse Pistols.", "Blizzard", 33, 40, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("figurines")));
            productList.add(new Product("Minecraft Village Poster", "Minecraft", "A poster depicting a peaceful Minecraft village.", "Mojang", 9, 160, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("posters")));
            productList.add(new Product("Dungeons & Dragons Dice Set", "Dungeons & Dragons", "A set of multi-sided dice for D&D gameplay.", "Wizards of the Coast", 8, 180, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("cards")));
            productList.add(new Product("Witcher Wolf Medallion Mug", "The Witcher", "A mug with the Wolf Medallion from The Witcher.", "CD Projekt Red", 12, 80, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("mugs")));
            productList.add(new Product("Luigi Keychain", "Super Mario", "A Luigi mini figurine keychain.", "Nintendo", 6, 140, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("keychains")));
//            productList.clear();
            productList.add(new Product("Cyberpunk Samurai Logo Pin", "Cyberpunk 2077", "A metallic pin with the Samurai logo.", "CD Projekt Red", 11, 100, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("pins")));
            productList.add(new Product("Ryu Costume", "Street Fighter", "A cosplay costume inspired by Ryu from Street Fighter.", "Capcom", 65, 30, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("costumes")));
            productList.add(new Product("Hollow Knight Wall Art", "Hollow Knight", "Decorative wall art featuring the Hollow Knight character.", "Team Cherry", 20, 35, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("decor")));
            productList.add(new Product("Link's Shield T-Shirt", "The Legend of Zelda", "A green T-shirt with Link's Hylian Shield design.", "Nintendo", 23, 90, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("apparel")));
            productList.add(new Product("Kingdom Hearts Keyblade Necklace", "Kingdom Hearts", "A necklace featuring the iconic Keyblade.", "Square Enix", 15, 75, LocalDateTime.now(), LocalDateTime.now(), "Available", new Category("accessories")));

            int count = 1;
            for (Product prd : productList) {
                prd.setCategory(categoryRepository.findByName(prd.getCategory().getName()));
                productRepository.save(prd);
//                TODO undo these comments. its for saving images in db.
                imageData = Files.readAllBytes(Paths.get(deviceFilePath + prd.getName() + ".jpg"));
                String imgFile = Base64.getEncoder().encodeToString(imageData);
                lastProduct = productRepository.findOneLastProduct();
                Image image = new Image(imgFile, lastProduct.getName(), ".jpg", lastProduct.getDescription(), "product-main", lastProduct);
                imageRepository.save(image);
                System.out.println("Saved product " + count + " : " + prd.getName());
                count++;
            }
            System.out.println("Category Repository and Image Repository has been populated with fifty(50) initial products.");
        }
    }

    private void populateReview(ProductRepository productRepository, UserRepository userRepository, ReviewRepository reviewRepository) {

        if (reviewRepository.count() == 0) {
            List<Product> productList = productRepository.findProductByLimit(0, 10);
            List<User> userList = userRepository.findUserByAuthority("user");

            List<String> reviewList = new ArrayList<>();
            reviewList.add("Amazing quality and exactly what I was hoping for!");
            reviewList.add("Perfect gift for any Gamers!");
            reviewList.add("Sturdy, well-made, and worth every penny.");
            reviewList.add("Great collectible piece; it exceeded my expectations.");
            reviewList.add("The colors are vibrant and the details are incredible.");
            reviewList.add("Arrived on time and in great condition.");
            reviewList.add("Fantastic addition to my gaming collection.");
            reviewList.add("A bit pricey but totally worth it for the quality.");
            reviewList.add("The product feels durable and high-quality.");
            reviewList.add("Perfect size and looks awesome on display!");
            reviewList.add("A must-have for hardcore fans!");
            reviewList.add("Came well-packaged and without any damage.");
            reviewList.add("Good quality but the colors could be a little more vibrant.");
            reviewList.add("I love the attention to detail on this item!");
            reviewList.add("Nice, but the size was a bit smaller than expected.");
            reviewList.add("Looks exactly like the pictures; I’m really happy with it.");
            reviewList.add("The design is spot-on with the game aesthetic.");
            reviewList.add("Makes a perfect gift for gamers.");
            reviewList.add("High-quality material; doesn’t feel cheap at all.");
            reviewList.add("Adds a great touch to my gaming setup.");
            reviewList.add("Very well-crafted! Definitely a collector’s item.");
            reviewList.add("Love it, but the price could be a little lower.");
            reviewList.add("It’s cool, but the paint job could be a bit better.");
            reviewList.add("Super happy with this purchase. It’s my new favorite!");
            reviewList.add("Such a unique piece for any fan.");
            reviewList.add("Just as advertised! Worth the purchase.");
            reviewList.add("Exactly what I was looking for to complete my collection.");
            reviewList.add("The detail on this item is amazing.");
            reviewList.add("A bit lightweight, but it still looks great.");
            reviewList.add("Perfect for showing off my love for [Game Name].");
            reviewList.add("The finish is smooth and looks professionally done.");
            reviewList.add("A little overpriced, but the quality is solid.");
            reviewList.add("Great for decor or as a conversation piece.");
            reviewList.add("Solid quality, and it looks awesome in person.");
            reviewList.add("For the price, it’s a really good value.");
            reviewList.add("Perfect replica of what’s seen in the game.");
            reviewList.add("Feels sturdy and has a nice weight to it.");
            reviewList.add("Love the level of detail, really well-made.");
            reviewList.add("Great addition to any fan’s collection!");
            reviewList.add("Surprisingly high-quality material for the price.");
            reviewList.add("Better than I expected! Looks fantastic on display.");
            reviewList.add("The colors are spot-on to the game’s design.");
            reviewList.add("Not as big as I thought, but still really nice.");
            reviewList.add("Adds a great touch to my gaming space.");
            reviewList.add("Solidly built and worth every penny.");
            reviewList.add("A great keepsake for fans of [Game Name].");
            reviewList.add("Quality is good, but wish it was a bit more detailed.");
            reviewList.add("Makes an amazing addition to my collection.");
            reviewList.add("Just what I needed to complete my setup.");
            reviewList.add("Absolutely love it! Highly recommend for fans.");

            int reviewCount = 1;
            for (Product product : productList) {
                Random random = new Random();
                int numberOfUsersToPick = random.nextInt(4) + 3; // 3 to 6
                Collections.shuffle(userList);
                List<User> suffledUserList = userList.subList(0, numberOfUsersToPick);

                for (User user : suffledUserList) {
                    int randomReview = random.nextInt(50);
                    int randomPriority = random.nextInt(5) + 1;
                    Review review = new Review(reviewList.get(randomReview), randomPriority, LocalDateTime.now(), LocalDateTime.now(), user, product);
                    System.out.println("review (" + reviewCount + ") : " + review.toString());
                    reviewRepository.save(review);
                    reviewCount++;
                }

            }
        }
    }
}