package cm.ex.merch.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String id;

    private String fullName;

    private String email;

    private List<String> authorityList;
}
