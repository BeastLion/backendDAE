package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Enum.ClientType;

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

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");
         clientBean.create(1L,"ola","ola",LocalDate.EPOCH,
                 "ola","910202022",5000000,
                 "ola","ola","ola@ola.pt");
        clientBean.create(2L,"ola2","ola2",LocalDate.EPOCH,
                "ola2","910202022",3000000,
                "ola","ola","ola2@ola2.pt");
    }
}
