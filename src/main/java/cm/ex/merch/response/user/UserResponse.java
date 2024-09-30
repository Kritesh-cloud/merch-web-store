package cm.ex.merch.response.user;

import cm.ex.merch.response.basic.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserResponse extends Response {
    private String type; //create, read, update, delete

    public UserResponse(boolean status, String message, String type) {
        super(status, message);
        this.type = type;
    }

}
