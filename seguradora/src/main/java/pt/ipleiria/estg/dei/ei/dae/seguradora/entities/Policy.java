package pt.ipleiria.estg.dei.ei.dae.seguradora.entities;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Insurer.Insurance;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.Client;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Policy {
    @Transient
    @NotNull
    @Getter
    @Setter
    private long policyCode;

    @Transient
    @NotNull
    @Getter
    @Setter
    private Insurance insurance;

    @Transient
    @NotNull
    @Getter
    @Setter
    private Client client;

    @Transient
    @NotNull
    @Getter
    @Setter
    private long price;

    @Transient
    @NotNull
    @Getter
    @Setter
    private LocalDate subscriptionDate;

    @Transient
    @NotNull
    @Getter
    @Setter
    private long loyaltyPeriod;

    @Transient
    @NotNull
    @Getter
    @Setter
    private long coverAmount;

    @Transient
    @NotNull
    @Getter
    @Setter
    private String securedGood;

    public Policy() {
    }

    public Policy(long policyCode, Insurance insurance, Client client, long price, LocalDate subscriptionDate, long loyaltyPeriod, long coverAmount, String securedGood) {
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
