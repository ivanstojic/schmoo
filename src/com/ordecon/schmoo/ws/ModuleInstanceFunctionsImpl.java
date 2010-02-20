package com.ordecon.schmoo.ws;

import com.ordecon.schmoo.base.modules.ModuleInstance;
import com.ordecon.schmoo.base.modules.ModuleTemplate;
import com.ordecon.schmoo.HibernateSingleton;
import com.ordecon.schmoo.ws.interfaces.ModuleInstanceFunctions;

import java.util.List;

import org.hibernate.Session;

/**
 * @author Ivan Stojic
 */
public class ModuleInstanceFunctionsImpl implements ModuleInstanceFunctions {
    public List<ModuleInstance> ListModuleInstances() {
        return HibernateSingleton.getSession().createQuery("from ModuleInstance").list();
    }

    public void CreateModuleInstance(int type, String name) {
        Session s = HibernateSingleton.getSession();

        ModuleTemplate template = (ModuleTemplate) s.load(ModuleTemplate.class, type);
        ModuleInstance instance = template.createInstance(name);

        if (instance != null) {
            // Ya Rly!
        }
    }
}
