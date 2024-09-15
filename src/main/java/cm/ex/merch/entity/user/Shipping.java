package cm.ex.merch.entity.user;

import cm.ex.merch.entity.User;
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
@Table(name = "user_shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter your shipping information")
    @Column(name = "name")
    private String name;

    @NotBlank(message="please enter your shipping information")
    @Column(name = "email")
    private String email;

    @NotBlank(message="please enter your shipping information")
    @Column(name = "street_address")
    private String streetAddress;

    @NotBlank(message="please enter your shipping information")
    @Column(name = "city")
    private String city;

    @NotBlank(message="please enter your shipping information")
    @Column(name = "province")
    private String province;

    @NotBlank(message="please enter your shipping information")
    @Column(name = "postal_code")
    private String postalCode;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
