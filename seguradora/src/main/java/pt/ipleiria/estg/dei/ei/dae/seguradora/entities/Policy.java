package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Policy {
    @NotNull
    private long policyCode;
    @NotNull
    private Insurance insurance;
    @NotNull
    private Client client;
    @NotNull
    private long price;
    @NotNull
    private LocalDate subscriptionDate;
    @NotNull
    private long loyaltyPeriod;
    @NotNull
    private long coverAmount;
    private String securedGood;

    public Policy() {
    }

    public Policy(long policyCode, Insurance insurance, Client client, long price, LocalDate subscriptionDate, long loyaltyPeriod, long coverAmount, String securedGood) {
        this();
        this.policyCode = policyCode;
        this.insurance = insurance;
        this.client = client;
        this.price = price;
        this.subscriptionDate = subscriptionDate;
        this.loyaltyPeriod = loyaltyPeriod;
        this.coverAmount = coverAmount;
        this.securedGood = securedGood;
    }
}
