/*
 * The source is licensed under Creative Commons BY-SA 3.0 license. For details, please review
 * the license web site located at http://creativecommons.org/licenses/by-sa/3.0/, or the copy
 * of the license text delivered with the installation package.
 */

package com.ordecon.schmoo;

import com.ordecon.schmoo.base.modules.connectors.Connector;
import com.ordecon.schmoo.base.modules.ModuleParameterTemplate;
import com.ordecon.schmoo.base.modules.ModuleTemplate;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class Communication {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        PropertyConfigurator.configure("log4j.cfg");
        Logger log = Logger.getLogger(Communication.class);
        log.info("Schmoo Server starting...");

        Configuration configuration = new Configuration().configure(new File("hibernate.cfg.xml"));
        SessionFactory sf = configuration.buildSessionFactory();

        Class c = Class.forName("com.ordecon.schmoo.smsc.modules.http.HTTPConnector");
        Connector con = (Connector) c.newInstance();
        //con.$sendTestMessage();

        Session s = sf.openSession();
        ModuleTemplate ct = (ModuleTemplate) s.load(ModuleTemplate.class, 1);
        log.info(ct);

        for (Object o : ct.getParameters()) {
            ModuleParameterTemplate parameterTemplate = (ModuleParameterTemplate) o;
            log.info(parameterTemplate);
        }
    }
}
