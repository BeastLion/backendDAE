package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String userType;

    private List<Policy> policyList;

    public UserDTO() {
    }

    public UserDTO(String username, String userType) {
        this.username = username;
        this.userType = userType;
    }

        public static UserDTO from(User user) {
            return new UserDTO(
                    user.getUsername(),
                    user.getUserType()
            );
        }

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }

}
