package com.ordecon.schmoo;

import com.ordecon.schmoo.base.modules.ModuleTemplate;
import com.ordecon.schmoo.base.modules.ModuleInstance;

/*
 * The source is licensed under Creative Commons BY-SA 3.0 license. For details, please review
 * the license web site located at http://creativecommons.org/licenses/by-sa/3.0/, or the copy
 * of the license text delivered with the installation package.
 */

public class SystemTest {
    public static void main(String[] args) {
        ModuleTemplate mt = new ModuleTemplate();
        mt.setClazz("com.ordecon.schmoo.smsc.connectors.http.HTTPConnector");

        ModuleInstance mi = mt.createInstance("myhttp");
    }
}
