package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;

import java.time.LocalDate;

public class UserDTO {

    private Long id;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private LocalDate birthDate;

    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private int phoneNumber;
    @Getter
    @Setter
    private ClientType clientType;

    @Getter
    @Setter
    private int financialNumber;

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String lastName, LocalDate birthDate, String address, int phoneNumber, ClientType clientType, int financialNumber, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.financialNumber = financialNumber;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
