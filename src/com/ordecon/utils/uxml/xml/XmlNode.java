package com.ordecon.utils.uxml.xml;

import java.util.*;
import java.text.*;

public class XmlNode
{
  protected String name;
  protected String value;

  protected Hashtable attributes;
  protected Vector nodes;

  public XmlNode(String name)
  {
    this(name, null);
  }

  public XmlNode(String name, String value)
  {
    this.name = name;
    this.value = value;
    nodes = null;
    attributes = null;
  }

  public XmlNode addNode(String name)
  {
    return addNode(name, null);
  }

  public XmlNode addNode(String name, String value)
  {
    if (nodes == null) nodes = new Vector();
    XmlNode node = new XmlNode(name, value);
    nodes.add(node);
    return node;
  }

  public void setAttribute(String name, String value)
  {
    if (attributes == null) attributes = new Hashtable();
    attributes.put(name, value);
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public XmlNode getNode(String name)
  {
    if (nodes != null) {
      for (int i=0; i<nodes.size(); i++) {
        XmlNode n = (XmlNode)nodes.get(i);
        if (n.name.equalsIgnoreCase(name)) return n;
      }
    }
    return null;
  }

  public String getNodeValue(String name)
  {
    XmlNode n = getNode(name);
    if (n != null) return n.getValue();
    return null;
  }

  public String getNodeValueRecursive(String name)
  {
    XmlNode n = getNodeRecursive(name);
    if (n != null) return n.getValue();
    return null;
  }

  public Vector getNodes(String name)
  {
    Vector res = new Vector();
    if (nodes != null) {
      for (int i=0; i<nodes.size(); i++) {
        XmlNode n = (XmlNode)nodes.get(i);
        if (n.name.equalsIgnoreCase(name)) res.add(n);
      }
    }
    return res;
  }

  public XmlNode getNodeRecursive(String name)
  {
    if (name.equalsIgnoreCase(name)) return this;
    if (nodes != null) {
      for (int i=0; i<nodes.size(); i++) {
        XmlNode n = (XmlNode)nodes.get(i);
        XmlNode r = n.getNodeRecursive(name);
        if (r != null) return r;
      }
    }
    return null;
  }

  public Vector getNodesRecursive(String name)
  {
    Vector res = new Vector();
    getNodesRecursiveInt(name, res);
    return res;
  }

  private void getNodesRecursiveInt(String name, Vector ns)
  {
    if (nodes != null) {
      for (int i=0; i<nodes.size(); i++) {
        XmlNode n = (XmlNode)nodes.get(i);
        if (n.name.equalsIgnoreCase(name)) ns.add(n);
        n.getNodesRecursiveInt(name, ns);
      }
    }
  }

  public String getValue()
  {
    return value;
  }

  public String getName()
  {
    return name;
  }

  public String getAttributeValue(String name)
  {
    if (attributes != null)
      return (String)attributes.get(name);
    return null;
  }

  protected String indent(int level)
  {
    String res = "";
    for (int i=0; i<level; i++) res += "\t";
    return res;
  }

  protected String encodeAttributes(Hashtable atts)
  {
    String res = "";
    if (atts != null) {
      for (Iterator i = atts.entrySet().iterator(); i.hasNext(); ) {
        Map.Entry at = (Map.Entry)i.next();
        res += " " + at.getKey() + "=\"" + at.getValue() + "\"";
      }
    }
    return res;
  }

  protected String encode(int level)
  {
    String xml = "";
    xml += indent(level) + "<" + name;
    xml += encodeAttributes(attributes);
    if (value != null || nodes != null) {
      xml += ">";

      if (value != null)
        xml += encodeStr(value);

      if (nodes != null) {
        xml += "\r\n";
        for (int i = 0; i < nodes.size(); i++)
          xml += ((XmlNode)nodes.get(i)).encode(level + 1);
        xml += indent(level);
      }

      xml += "</" + name + ">\r\n";
    } else {
      xml += "/>\r\n";
    }

    return xml;
  }

  protected String encodeStr(String data)
  {
    String res = data.replaceAll("&", "&amp;");
    res = res.replaceAll("<", "&lt;");
    res = res.replaceAll(">", "&gt;");
    res = res.replaceAll("\"", "&quot;");
    res = res.replaceAll("'", "&apos;");
    return res;
  }

  protected String decodeStr(String data)
  {
    String res = data.replaceAll("&lt;", "<");
    res = res.replaceAll("&gt;", ">");
    res = res.replaceAll("&quot;", "\"");
    res = res.replaceAll("&apos;", "'");
    res = res.replaceAll("&amp;", "&");
    return res;
  }

  protected boolean isSpace(char c)
  {
    return Character.isWhitespace(c);
  }

  protected boolean isDelim(char c)
  {
    return Character.isWhitespace(c) || c == '<' || c == '>' || c == '=' || c == '/' || c=='?';
  }


  protected void skipSpace(StringCharacterIterator xml)
  {
    for (char c = xml.current(); c != xml.DONE && isSpace(c); c = xml.next());
  }


  protected String getWord(StringCharacterIterator xml)
  {
    String res = "";
    skipSpace(xml);
    for (char c = xml.current(); c != xml.DONE && !isDelim(c); c = xml.next()) res += c;
    if (res.equals("")) return null;
    return res;
  }

  protected String getData(StringCharacterIterator xml)
  {
    String res = "";
    skipSpace(xml);
    if (xml.current() == '<') return null;
    for (char c = xml.current(); c != xml.DONE && c != '<'; c = xml.next()) res += c;
    return decodeStr(res);
  }

  protected void parseAttribute(StringCharacterIterator xml, Hashtable atts) throws Exception
  {
    String n = getWord(xml);
    if (n != null && !n.equals("")) {
      skipSpace(xml);
      if (xml.current() == '=') {
        xml.next();
        String v = getWord(xml);
        if (v != null) {
          if ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'"))) {
            v = v.substring(1, v.length() - 1);
          }
          atts.put(n, v);
        } else {
          // no attribute value
          throw new Exception("no value (" + n + ")");
        }
      } else {
        // missing = for attribute
        throw new Exception("missing '=' after name (" + n + ")");
      }
    } else {
      // invalid attribute name
      throw new Exception("Invalid name");
    }
  }

  protected void parseAttributes(StringCharacterIterator xml, Hashtable atts) throws Exception
  {
    skipSpace(xml);

    while (xml.current() != '>' && xml.current() != '?' && xml.current() != '/') {
      parseAttribute(xml, atts);
      skipSpace(xml);
    }
  }

  protected void parse(StringCharacterIterator xml) throws Exception
  {
    name = null;
    try {
      // find start
      skipSpace(xml);
      if (xml.current() == '<') {
        xml.next();
        name = getWord(xml);
        if (name != null) {
          skipSpace(xml);
          if (xml.current() != '/') {
            if (xml.current() != '>') {
              // parse attributes
              try {
                attributes = new Hashtable();
                parseAttributes(xml, attributes);
                skipSpace(xml);
                if (xml.current() != '/') {
                  if (xml.current() == '>') {
                    parseData(xml);
                  } else {
                    // no end brace
                    throw new Exception("No end brace");
                  }
                } else {
                  if (xml.next() == '>') {
                    xml.next();
                    return;
                  } else {
                    // error, no end brace
                    throw new Exception("No end brace");
                  }
                }
              } catch (Exception e) {
                throw new Exception("Cannot parse attributes: " + e.getMessage());
              }
            } else {
              parseData(xml);
            }
          } else {
            if (xml.next() == '>') {
              xml.next();
              return;
            } else {
              // error, no end brace
              throw new Exception("No end brace");
            }
          }
        } else {
          throw new Exception("No tag name");
        }
      } else {
        throw new Exception("No tag start");
      }

    } catch (Exception e) {
      if (name != null)
        throw new Exception("Tag " + name + ": " + e.getMessage());
      else
        throw e;
    }
  }

  protected void parseData(StringCharacterIterator xml) throws Exception
  {
    xml.next();
   value = getData(xml);

   while (xml.current() == '<' && xml.current() != xml.DONE) {
     if (xml.next() == '/') {
       xml.next();
       String en = getWord(xml);
       if (en.equals(name)) {
         skipSpace(xml);
         if (xml.current() == '>') {
           xml.next();
           return;
         } else {
           // error, extra chars in tag end
           throw new Exception("Extra chars in tag end");
         }
       } else {
         // error, end tag mismatch
         throw new Exception("End tag mismatch");
       }
     } else {
       xml.previous();
       if (nodes == null)
         nodes = new Vector();
       XmlNode n = new XmlNode("");
       n.parse(xml);
       nodes.add(n);
     }
     skipSpace(xml);
   }
   // no end tag
   throw new Exception("No end tag");
  }

}