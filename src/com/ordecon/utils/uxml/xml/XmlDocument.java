package com.ordecon.utils.uxml.xml;

import java.text.*;
import java.util.*;


public class XmlDocument extends XmlNode {
    protected Hashtable globals = new Hashtable();

    public XmlDocument(String rootNode) {
        this(rootNode, 1, 0);
    }

    public XmlDocument(String rootNode, int verMaj, int verMin) {
        super(rootNode);
        globals.put("version", verMaj + "." + verMin);
    }

    public XmlDocument() {
        super("");
    }

    public String getRootName() {
        return name;
    }

    public String getXml() {
        String xml = "<?xml";
        xml += encodeAttributes(globals);
        xml += " ?>\r\n";
        return xml + encode(0);
    }

    public void parse(String xml) throws Exception {
        StringCharacterIterator ci = new StringCharacterIterator(xml);

        skipSpace(ci);
        if (ci.current() == '<') {
            int s = ci.getIndex();
            ci.next();
            skipSpace(ci);
            if (ci.current() == '?') {
                ci.next();
                skipSpace(ci);
                getWord(ci);
                skipSpace(ci);
                parseAttributes(ci, globals);
                skipSpace(ci);
                if (ci.current() == '?') {
                    ci.next();
                    skipSpace(ci);
                    if (ci.current() == '>') {
                        ci.next();
                        parse(ci);
                    } else {
                        // no end brace for global
                        throw new Exception("No decl end brace");
                    }
                } else {
                    // no end ? for global
                    throw new Exception("No decl end '?'");
                }
            } else {
                ci.setIndex(s);
                parse(ci);
            }
        } else {
            // no tag start
            throw new Exception("No document start");
        }
    }

}