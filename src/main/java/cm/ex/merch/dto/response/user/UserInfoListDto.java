package cm.ex.merch.dto.response.user;

import cm.ex.merch.dto.response.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoListDto extends Response {

    private List<UserInfo> userInfoList;

    public UserInfoListDto(boolean status, String message, List<UserInfo> userInfoList) {
        super(status, message);
        this.userInfoList = userInfoList;
    }
}
