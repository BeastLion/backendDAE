package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.ClientType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClientDetailDTO{

    private int financialNumber;

    private ClientType clientType;

    private LocalDate birthDate;

    private String address;

    private String username;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String password;

    private String email;

    private List<Occurrence> occurrences;

    public ClientDetailDTO() {
        this.occurrences = new ArrayList<>();
    }

    public ClientDetailDTO(int financialNumber, ClientType clientType, LocalDate birthDate, String address, String username, String name, String lastName, String phoneNumber, String password, String email, List<Occurrence> occurrences) {
        this.financialNumber = financialNumber;
        this.clientType = clientType;
        this.birthDate = birthDate;
        this.address = address;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.occurrences = occurrences;
    }

    public static ClientDetailDTO toDTO(ClientDetailDTO clientDetailDTO) {
        return new ClientDetailDTO(
                clientDetailDTO.getFinancialNumber(),
                clientDetailDTO.getClientType(),
                clientDetailDTO.getBirthDate(),
                clientDetailDTO.getAddress(),
                clientDetailDTO.getUsername(),
                clientDetailDTO.getName(),
                clientDetailDTO.getLastName(),
                clientDetailDTO.getPhoneNumber(),
                clientDetailDTO.getPassword(),
                clientDetailDTO.getEmail(),
                clientDetailDTO.getOccurrences()
        );
    }
}
