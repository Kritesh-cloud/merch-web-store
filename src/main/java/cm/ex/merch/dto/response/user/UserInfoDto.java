package cm.ex.merch.dto.response.user;

import cm.ex.merch.dto.response.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto extends Response {

    private UserInfo userInfo;

    public UserInfoDto(boolean status, String message, UserInfo userInfo) {
        super(status, message);
        this.userInfo = userInfo;
    }
}
