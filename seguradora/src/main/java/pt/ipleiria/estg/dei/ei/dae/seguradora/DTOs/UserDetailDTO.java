package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class UserDetailDTO  {

    private String name;

    private String lastName;

    private String phoNumber;

    private String email;

    public UserDetailDTO() {
    }

    public UserDetailDTO(String name, String lastName, String phoNumber, String email) {
        this();
        this.name = name;
        this.lastName = lastName;
        this.phoNumber = phoNumber;
        this.email = email;
    }

    public static UserDetailDTO toDTO(User user) {
        return new UserDetailDTO(
                user.getName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail()

        );
    }

    public static List<UserDetailDTO> toDTOs(List<User> users) {
        return users.stream().map(UserDetailDTO::toDTO).collect(Collectors.toList());
    }
}
