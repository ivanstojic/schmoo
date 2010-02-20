package com.ordecon.schmoo.ws;

import com.ordecon.schmoo.base.modules.ModuleTemplate;
import com.ordecon.schmoo.HibernateSingleton;
import com.ordecon.schmoo.ws.interfaces.ModuleTemplateFunctions;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Ivan Stojic
 */
public class ModuleTemplateFunctionsImpl implements ModuleTemplateFunctions {
    public List<ModuleTemplate> GetModuleTemplates() {
        return HibernateSingleton.getSession().createQuery("from ModuleTemplate").list();
    }
}
