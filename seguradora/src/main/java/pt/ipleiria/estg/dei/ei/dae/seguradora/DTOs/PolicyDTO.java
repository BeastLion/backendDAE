package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PolicyDTO {
    private long policyCode;

    private Insurance insurance;

    private Client client;

    private long price;

    private LocalDate subscriptionDate;

    private long loyaltyPeriod;

    private long coverAmount;
    private String securedGood;

    public PolicyDTO() {
    }

    public PolicyDTO(long policyCode,  Client client, long price, LocalDate subscriptionDate, long loyaltyPeriod, long coverAmount, String securedGood) {
        this();
        this.policyCode = policyCode;
        this.client = client;
        this.price = price;
        this.subscriptionDate = subscriptionDate;
        this.loyaltyPeriod = loyaltyPeriod;
        this.coverAmount = coverAmount;
        this.securedGood = securedGood;
    }

    public static PolicyDTO toDTO(Policy policy) {
        return new PolicyDTO(
                policy.getPolicyCode(),
                policy.getClient(),
                policy.getPrice(),
                policy.getSubscriptionDate(),
                policy.getLoyaltyPeriod(),
                policy.getCoverAmount(),
                policy.getSecuredGood()
        );
    }

    public static List<PolicyDTO> toDTOs(List<Policy> policy) {
        return policy.stream().map(PolicyDTO::toDTO).collect(Collectors.toList());
    }
}
