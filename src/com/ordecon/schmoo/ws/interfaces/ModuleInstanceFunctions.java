package com.ordecon.schmoo.ws.interfaces;

import com.ordecon.schmoo.base.modules.ModuleInstance;

import java.util.List;

/**
 * @author Ivan Stojic
 */
public interface ModuleInstanceFunctions {
    public List<ModuleInstance> ListModuleInstances();
    public void CreateModuleInstance(int type, String name);
}
