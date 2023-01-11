package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private ClientBean clientBean;

    @EJB
    private InsurerBean insurerBean;

 /*   @Transactional
    public void getApiData(){
    }*/

    @PostConstruct
    public void populateDB() {
        insurerBean.getAll();

        System.out.println("Hello Java EE!");

        clientBean.create("ola", "ola", LocalDate.EPOCH,
                "ola", "910202022", 5000000,
                "ola", "ola", "ola@ola.pt", 123123123123L);

    }
}
