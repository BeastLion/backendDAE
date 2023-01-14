package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Enum.InsuranceType;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.RepairServices;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Technician;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RepairServiceDTO {

    private Long id;

    private String name;

    private String location;

    private InsuranceType insuranceType;
    private List<Technician> technicians;

    public RepairServiceDTO() {
        this.technicians = new ArrayList<>();
    }

    public RepairServiceDTO(Long id, String name, String location, InsuranceType insuranceType) {
        this();
        this.id = id;
        this.name = name;
        this.location = location;
        this.insuranceType = insuranceType;
    }

    public static RepairServiceDTO toDTO(RepairServices repairServices) {
        return new RepairServiceDTO(
                repairServices.getId(),
                repairServices.getName(),
                repairServices.getLocation(),
                repairServices.getInsuranceType()
        );
    }

    public static List<RepairServiceDTO> toDTOs(List<RepairServices> repairServices) {
        return repairServices.stream().map(RepairServiceDTO::toDTO).collect(Collectors.toList());
    }

}

