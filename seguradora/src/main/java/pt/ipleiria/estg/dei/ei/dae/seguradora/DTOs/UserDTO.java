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

    public UserDTO() {
    }

    public UserDTO(String username) {
        this.username = username;
    }

        public static UserDTO from(User user) {
            return new UserDTO(
                    user.getUsername()
            );
        }

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }
}
