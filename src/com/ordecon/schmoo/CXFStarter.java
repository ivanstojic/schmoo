package com.ordecon.schmoo;

import com.ordecon.schmoo.ws.ModuleInstanceFunctionsImpl;
import com.ordecon.schmoo.ws.ModuleTemplateFunctionsImpl;
import com.ordecon.schmoo.ws.interfaces.ModuleInstanceFunctions;
import com.ordecon.schmoo.ws.interfaces.ModuleTemplateFunctions;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.aegis.databinding.AegisServiceConfiguration;

public class CXFStarter {
    public static void main(String[] args) {
        ServerFactoryBean templateFunctions = new ServerFactoryBean();
        templateFunctions.getServiceFactory().setDataBinding(new AegisDatabinding());
        templateFunctions.getServiceFactory().getServiceConfigurations().add(0, new AegisServiceConfiguration());
        templateFunctions.setServiceBean(new ModuleTemplateFunctionsImpl());
        templateFunctions.setServiceClass(ModuleTemplateFunctions.class);
        templateFunctions.setAddress("http://localhost:8191/ModuleTemplateFunctions");
        templateFunctions.create();

        ServerFactoryBean instanceFunctions = new ServerFactoryBean();
        templateFunctions.getServiceFactory().setDataBinding(new AegisDatabinding());
        instanceFunctions.getServiceFactory().getServiceConfigurations().add(0, new AegisServiceConfiguration());
        instanceFunctions.setServiceBean(new ModuleInstanceFunctionsImpl());
        instanceFunctions.setServiceClass(ModuleInstanceFunctions.class);
        instanceFunctions.setAddress("http://localhost:8191/ModuleInstanceFunctions");
        instanceFunctions.create();
    }
}
