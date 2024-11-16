package cm.ex.merch.dto.request.product;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserDto {

    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "input field cannot be blank")
    private String fullName;

    @NotBlank(message = "input field cannot be blank")
    private String password;

}