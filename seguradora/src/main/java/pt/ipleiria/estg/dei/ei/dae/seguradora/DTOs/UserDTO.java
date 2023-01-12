package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String user_type;

    public UserDTO() {
    }

    public UserDTO(String username, String user_type) {
        this.username = username;
        this.user_type = user_type;
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

    public String getUserType() {
        return user_type;
    }
}
