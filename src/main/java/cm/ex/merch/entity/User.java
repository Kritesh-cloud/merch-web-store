package cm.ex.merch.entity;
;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter user data")
    @Column(name = "full_name")
    private String fullName;

    @NotBlank(message="please enter user data")
    @Column(name = "email")
    private String email;

    @NotBlank(message="please enter user data")
    @Column(name = "password")
    private String password;

}