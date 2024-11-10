package cm.ex.merch.dto.response.authentication;

import cm.ex.merch.dto.response.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends Response {

    private String token;

    public LoginResponse(boolean status, String message, String token) {
        super(status, message);
        this.token = token;
    }
}
