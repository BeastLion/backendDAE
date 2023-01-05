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
    private UserBean userBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        userBean.create(1, "tiago", "aguiar", LocalDate.EPOCH, "rua oliveira", 910005544, ClientType.CLIENT, 2123332212, "carnificin", "123456", "tiago002@gmail.com");

    }
}
