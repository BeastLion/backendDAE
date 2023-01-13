package pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PolicyDTO {
    private long policyCode;

    private long price;
    private String subscriptionDate;
    private long loyaltyPeriod;
    private long coverAmount;
    private String securedGood;

    private Insurance insurance;

    public PolicyDTO() {
    }

    public PolicyDTO(long policyCode, long price, String subscriptionDate, long loyaltyPeriod, long coverAmount, String securedGood, Insurance insurance) {
        this.policyCode = policyCode;
        this.price = price;
        this.subscriptionDate = subscriptionDate;
        this.loyaltyPeriod = loyaltyPeriod;
        this.coverAmount = coverAmount;
        this.securedGood = securedGood;
        this.insurance = insurance;
    }

    public static PolicyDTO toDTO(Policy policy) {
        return new PolicyDTO(
                policy.getPolicyCode(),
                policy.getPrice(),
                policy.getSubscriptionDate().toString(),
                policy.getLoyaltyPeriod(),
                policy.getCoverAmount(),
                policy.getSecuredGood(),
                policy.getInsurance()
        );
    }

    public static List<PolicyDTO> toDTOs(List<Policy> policy) {
        return policy.stream().map(PolicyDTO::toDTO).collect(Collectors.toList());
    }
}
