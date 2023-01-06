package pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDate;

@Startup
@Singleton
public class ConfigBean {


    @EJB
    private ClientBean clientBean;

    @EJB
    private TestBean testBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");
        clientBean.create(1L, "ola", "ola", LocalDate.EPOCH,
                "ola", "910202022", 5000000,
                "ola", "ola", "ola@ola.pt");

        testBean.getA();
    }
}
