/*
 * @(#)SPOTToXSD.java   2010-04-06
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.spot.compiler;

import com.appnativa.spot.SPOTNode;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FormatException;
import com.appnativa.util.ObjectList;
import com.appnativa.util.StringList;
import com.appnativa.util.XMLDataItem;
import com.appnativa.util.XMLUtils;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.*;
import java.util.Hashtable;

/**
 * DOCUMENT ME!
 *
 *
 * @version    2.3
 * @author     Don DeCoteau
 */
public class SPOTToXSD {
  static Hashtable nameSpaceObjects = new Hashtable();
  static Hashtable objects          = new Hashtable();
  String           defImportNS      = null;
  String           namespace        = "";
  String           origNamespace    = "";
  List<String>     otherNamespaces  = null;
  String           target           = "";
  String           xsdnamespace     =
    "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"unqualified\" attributeFormDefault=\"unqualified\"";
  List<String> subTypes;

  /**
   * Add escape sequences to a value
   *
   *
   * @param value  DOCUMENT ME!
   *
   * @return The escaped string
   */
  public static String escape(String value) {
    if (value == null) {
      return null;
    }

    int    len = value.length();
    char[] b   = value.toCharArray();

    return escape(b, 0, b.length);
  }

  /**
   * Add escape sequences to a value
   *
   * @param chars The value to be escape
   * @param pos  DOCUMENT ME!
   * @param len  DOCUMENT ME!
   *
   * @return The escaped string
   */
  public static String escape(char[] chars, int pos, int len) {
    StringBuilder out = new StringBuilder(len + 16);

    len += pos;

    for (int i = pos; i < len; i++) {
      escapeChararacter(chars[i], out);
    }

    return out.toString();
  }

  /**
   * The main entry point for the application.
   *
   * @param args Array of parameters passed to the application via the command
   *        line.
   */
  public static void main(String[] args) {
    try {

      /*  */
      args = new String[] { "rare_objects.spot", "com.appnativa.rare.spot=rare_objects", };

      // */
      if (args.length < 2) {
        usage();

        return;
      }

      System.err.println("Parsing " + args[0]);

      java.io.FileReader f = new java.io.FileReader(args[0]);
      SPOTToXSD          x = new SPOTToXSD();

      x.toXSD(f, args[1], (args.length > 2)
                          ? args[2]
                          : null);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Generates XSD  files from an SPOT node The generated files conform to
   * the 02 May 2001 W3C XML Schema specification
   *
   * @param node the node
   * @param nspace namespace that the schema belongs to
   *
   * @throws java.text.ParseException if the format of the SPOT is invalid.
   */
  public void toXSD(SPOTNode node, String nspace) throws java.text.ParseException {
    int          i        = 0;
    int          n        = 0;
    SPOTNode     x        = null;
    ObjectList   elements = node.childNodes;
    List<String> toks     = null;
    int          len      = (elements == null)
                            ? 0
                            : elements.size();

    if (len == 0) {
      return;
    }

    if (nspace != null) {
      toks = CharScanner.getTokens(nspace, '=');

      if (toks.size() != 2) {
        throw new java.text.ParseException("Bad namespace parameter: " + nspace, 0);
      }

      origNamespace = toks.get(0);
      namespace     = origNamespace + ":";
      target        = toks.get(1);
    }

    String       s;
    List<String> sa = null;

    try {
      PrintWriter pw = new PrintWriter(System.out);

      pw.println("<?xml version = \"1.0\" encoding = \"UTF-8\"?>");
      pw.println("<!--Generated by SPOTToXSD v1.0-->");
      pw.print("<xsd:schema " + xsdnamespace);

      if (otherNamespaces != null) {
        for (n = 0; n < otherNamespaces.size(); n++) {
          sa = CharScanner.getTokens(otherNamespaces.get(n), '=');

          if ((sa != null) && (sa.size() > 2)) {
            pw.println();
            pw.print("       xmlns:" + sa.get(0) + "=\"" + sa.get(1) + "\"");
          }
        }
      }

      if (namespace != null) {
        pw.println();
        pw.print("       xmlns:" + origNamespace + "=\"" + target + "\"");
        pw.println();
        pw.print("       targetNamespace=\"" + target + "\"");
      }

      pw.println(">");
      println(pw, 1, "<xsd:annotation>");
      println(pw, 2, "<xsd:documentation>");
      println(pw, 2, "</xsd:documentation>");
      println(pw, 1, "</xsd:annotation>");
      pw.println();

      if (otherNamespaces != null) {
        for (n = 0; n < otherNamespaces.size(); n++) {
          sa = CharScanner.getTokens(otherNamespaces.get(n), '=');

          if ((sa != null) && (sa.size() > 2)) {
            println(pw, 1, "<xsd:import namespace=\"" + sa.get(1) + "\" schemaLocation=\"" + sa.get(2) + "\"/>");
            readSchema(sa.get(0), sa.get(2));

            if (n == 0) {
              defImportNS = sa.get(0) + ":";
            }
          }
        }
      }

      for (i = 0; i < len; i++) {
        x          = (SPOTNode) elements.get(i);
        x.theDepth = 1;
        objects.put(x.elementName, x);
        nameSpaceObjects.put(x.elementName, namespace);
        toXSDParent(x, pw);
      }

      pw.println();
      len = (subTypes == null)
            ? 0
            : subTypes.size();

      for (i = 0; i < len; i++) {
        pw.println(subTypes.get(i));
        pw.println();
      }

      pw.println("</xsd:schema>");
      pw.flush();
      System.out.flush();
    } catch(Exception e) {
      e.printStackTrace();
      System.err.println((("last Line node:" + x) != null)
                         ? x.toString()
                         : "");
    }
  }

  /**
   * Generates XSD  files from an SPOT formatted stream The generated files
   * conform to the 02 May 2001 W3C XML Schema specification
   *
   * @param in The reader for the SPOT formatted stream
   * @param nspace namespace that the schema belongs to
   * @param imp Dependent schemas to import
   *
   * @throws java.text.ParseException if the format of the SPOT is invalid.
   */
  public void toXSD(Reader in, String nspace, String imp) throws java.text.ParseException {
    SPOTNode x = new SPOTNode();

    try {
      x.read(in);

      if (imp != null) {
        otherNamespaces = CharScanner.getTokens(imp, ',');
      }

      toXSD(x, nspace);
    } catch(FormatException ex) {
      System.err.println(x.toString());

      throw ex;
    }
  }

  /**
   * DOCUMENT ME!
   */
  public static void usage() {
    System.err.println("Converts SPOT structures to their 02 May 2001 W3C XML Schema representations");
    System.err.println("The output is written to the console.\n");
    System.err.println("Usage: SPOTToXSD.exe sourceFile namespace [import,[import],...]");
    System.err.println(" source\t- the file containing th SPOT to be parsed.");
    System.err.println(" namespace\t- the namespace for this schema in format <name=target>.");
    System.err.println(" import\t\t- namespaces to import specified in the");
    System.err.println("\t\t  format <name=traget=schemalocation>.");
  }

  void addAttributes(Map attrs, Writer out, int depth) {
    if (attrs == null) {
      return;
    }

    Iterator  it = attrs.entrySet().iterator();
    Map.Entry me;
    String    key;
    String    val;

    while(it.hasNext()) {
      me  = (Map.Entry) it.next();
      key = (String) me.getKey();
      val = (String) me.getValue();

      if (val == null) {
        println(out, depth, "<xsd:attribute name=\"" + key + "\" type=\"xsd:string\" use=\"optional\"/>");
      } else {
        println(out, depth,
                "<xsd:attribute name=\"" + key + "\" type=\"xsd:string\" use=\"optional\" default=\"" + val + "\"/>");
      }
    }
  }

  void addSybType(String s) {
    if (subTypes == null) {
      subTypes = new ArrayList<String>();
    }

    subTypes.add(s);;
  }

  boolean annotate(Writer pw, SPOTNode node, int depth) {
    if ((node.theComment == null) && (node.theDescription == null)) {
      return false;
    }

    println(pw, depth + 1, "<xsd:annotation>");
    print(pw, depth + 2, "<xsd:documentation>");

    if (node.theDescription != null) {
      print(pw, 0, escape(node.theDescription));
    } else {
      print(pw, 0, escape(node.theComment));
    }

    println(pw, 0, "</xsd:documentation>");
    println(pw, depth + 1, "</xsd:annotation>");

    return true;
  }

  void print(Writer out, int depth, String s) {
    try {
      XMLUtils.writePadding(out, depth);
      out.write(s);
    } catch(IOException ex) {}
  }

  void println(Writer out, int depth, String s) {
    try {
      XMLUtils.writePadding(out, depth);
      out.write(s);
      SPOTNode.newLine(out);
    } catch(IOException ex) {}
  }

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   * @param choices DOCUMENT ME!
   * @param depth DOCUMENT ME!
   * @param fixedValue DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String createChoices(SPOTNode node, int depth) {
    StringWriter sw;
    String       name       = node.elementName;
    String[]     choices    = node.theChoices;
    String       fixedValue = node.fixedValue;
    PrintWriter  pw         = new PrintWriter(sw = new StringWriter());

    if (fixedValue != null) {
      print(pw, depth, "<xsd:element name=\"" + name + "\" fixed=\"" + fixedValue + "\"");

      if (node.isOptional) {
        pw.print(" minOccurs=\"0\"");
      }

      pw.println(">");
      annotate(pw, node, node.theDepth);
      println(pw, depth, "\t<xsd:simpleType>");
      println(pw, depth, "\t\t<xsd:restriction base=\"xsd:string\"/>");
      println(pw, depth, "\t</xsd:simpleType>");
    } else {
      print(pw, depth, "<xsd:element name=\"" + name + "\"");

      if (node.isOptional) {
        pw.print(" minOccurs=\"0\"");
      }

      pw.println(">");
      println(pw, depth, "\t<xsd:simpleType>");
      println(pw, depth, "\t\t<xsd:restriction base=\"xsd:string\">");

      for (int i = 0; i < choices.length; i++) {
        println(pw, depth, "\t\t\t<xsd:enumeration value=" + escape(choices[i]) + "/>");
      }

      println(pw, depth, "\t\t</xsd:restriction>");
      println(pw, depth, "\t</xsd:simpleType>");
    }

    print(pw, depth, "</xsd:element>");
    pw.flush();

    return sw.toString();
  }

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   * @param choices DOCUMENT ME!
   * @param depth DOCUMENT ME!
   * @param fixedValue DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String createEnumeratedElement(SPOTNode node) {
    if (node.hasAttributes() && (node.fixedValue == null)) {
      return createEnumeratedElementWithAttributes(node);
    }

    StringWriter sw;
    PrintWriter  pw    = new PrintWriter(sw = new StringWriter());
    int          depth = node.theDepth;

    if (node.fixedValue != null) {
      print(pw, depth, "<xsd:element name=\"" + node.elementName + "\" fixed=\"" + node.fixedValue + "\"");

      if (node.isOptional) {
        pw.print(" minOccurs=\"0\"");
      }

      pw.println(">");
      annotate(pw, node, node.theDepth);
      println(pw, depth, "\t<xsd:simpleType>");
      println(pw, depth, "\t\t<xsd:restriction base=\"xsd:string\"/>");
      println(pw, depth, "\t</xsd:simpleType>");
    } else {
      print(pw, depth, "<xsd:element name=\"" + node.elementName + "\"");
      pw.println(setElementAttributes(node) + ">");
      annotate(pw, node, node.theDepth);
      println(pw, depth, "\t<xsd:simpleType>");
      println(pw, depth, "\t\t<xsd:restriction base=\"xsd:string\">");

      for (int i = 0; i < node.theChoices.length; i++) {
        println(pw, depth, "\t\t\t<xsd:enumeration value=\"" + node.theChoices[i] + "\"/>");
        println(pw, depth, "\t\t\t<xsd:enumeration value=\"" + node.theNumChoices[i].toString() + "\"/>");
      }

      println(pw, depth, "\t\t</xsd:restriction>");
      println(pw, depth, "\t</xsd:simpleType>");
    }

    print(pw, depth, "</xsd:element>");
    pw.flush();

    return sw.toString();
  }

  protected void createEnumeratedElementBaseType(SPOTNode node, String basename) {
    StringWriter sw;
    PrintWriter  pw    = new PrintWriter(sw = new StringWriter());
    int          depth = 1;

    println(pw, depth, "<xsd:simpleType name=\"" + basename + "\">");
    println(pw, depth + 1, "<xsd:restriction base=\"xsd:string\">");

    for (int i = 0; i < node.theChoices.length; i++) {
      println(pw, depth + 2, "<xsd:enumeration value=\"" + node.theChoices[i] + "\"/>");
      println(pw, depth + 2, "<xsd:enumeration value=\"" + node.theNumChoices[i].toString() + "\"/>");
    }

    println(pw, depth + 1, "</xsd:restriction>");
    println(pw, depth, "</xsd:simpleType>");
    pw.flush();
    this.addSybType(sw.toString());
  }

  protected String createEnumeratedElementWithAttributes(SPOTNode node) {
    StringWriter sw;
    PrintWriter  pw       = new PrintWriter(sw = new StringWriter());
    String       basename = node.elementName + "Base";

    createEnumeratedElementBaseType(node, basename);
    pw.println();

    int depth = node.theDepth;

    print(pw, depth, "<xsd:element name=\"" + node.elementName + "\"");
    pw.println(setElementAttributes(node) + ">");
    annotate(pw, node, node.theDepth);
    println(pw, depth + 1, "<xsd:complexType>");
    println(pw, depth + 2, "<xsd:simpleContent>");
    println(pw, depth + 3, "<xsd:extension  base=\"" + namespace + basename + "\">");
    addAttributes(node.theAttributes, pw, depth + 4);
    println(pw, depth + 3, "</xsd:extension>");
    println(pw, depth + 2, "</xsd:simpleContent>");
    println(pw, depth + 1, "</xsd:complexType>");
    print(pw, depth, "</xsd:element>");
    pw.flush();

    return sw.toString();
  }

  /**
   * DOCUMENT ME!
   *
   * @param in DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String readLine(BufferedReader in) {
    try {
      return in.readLine();
    } catch(IOException e) {
      return null;
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param ns DOCUMENT ME!
   * @param file DOCUMENT ME!
   */
  protected void readSchema(String ns, String file) {
    ns += ":";

    if (file.charAt(0) == '\"') {
      file = file.substring(1);
    }

    int i = file.indexOf('\"');

    if (i != -1) {
      file = file.substring(0, i);
    }

    try {
      FileInputStream f   = new FileInputStream(file);
      XMLDataItem     xml = XMLDataItem.parse(f);
      String          name;
      XMLDataItem     x;
      int             len = xml.getItemCount();

      for (i = 0; i < len; i++) {
        x = (XMLDataItem) xml.getItem(i);

        if (!x.getName().equalsIgnoreCase("complexType") &&!x.getName().equalsIgnoreCase("xsd:complexType")) {
          continue;
        }

        name = x.getAttribute("name");

        if ((name != null) && (name.length() > 0)) {
          nameSpaceObjects.put(name, ns);
        }
      }
    } catch(Exception e) {}
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param pw DOCUMENT ME!
   */
  protected void toXSDEx(SPOTNode node, PrintWriter pw) {
    int      n = 0;
    SPOTNode x = null;
    String   s;
    String   min   = null;
    String   max   = null;
    String   range = node.theRange;

    if (range != null) {
      n = range.indexOf("..");

      if (n != -1) {
        min = range.substring(0, n);
        max = range.substring(n + 2);
      }
    }

    if (node.elementType.equals(SPOTNode.typeRefine)) {
      return;
    }

    n = SPOTNode.findType(node.elementType);

    if (n == -1) {
      s = (String) nameSpaceObjects.get(node.elementType + "Type");

      if (s == null) {
        s = namespace;
      }

      print(pw, node.theDepth,
            "<xsd:element name=\"" + node.elementName + "\" type=\"" + s + node.elementType + "Type\"");

      if ((node.theComment == null) && (node.theDescription == null)) {
        pw.println(setElementAttributes(node) + "/>");
        addAttributes(node.theAttributes, pw, node.theDepth);
      } else {
        pw.println(setElementAttributes(node) + ">");
        addAttributes(node.theAttributes, pw, node.theDepth);
        annotate(pw, node, node.theDepth);
        println(pw, node.theDepth, "</xsd:element>");
      }

      return;
    }

    if (node.theChoices != null) {
      if (node.elementType.equals(node.typeEnum)) {
        pw.println(createEnumeratedElement(node));
      } else {    // PrintableString
        pw.println(createChoices(node, node.theDepth + 1));
      }

      return;
    }

    if (node.elementType.equals(node.typeSet)) {
      x = (SPOTNode) node.childNodes.get(0);
      println(pw, node.theDepth, "<xsd:element name=\"" + node.elementName + "\"" + setElementAttributes(node) + ">");
      annotate(pw, node, node.theDepth);
      println(pw, node.theDepth + 1, "<xsd:complexType>");
      println(pw, node.theDepth + 2, "<xsd:sequence>");

      // println(pw, node.theDepth + 3 , "<xsd:attribute name=\"type\" type=\"xsd:Name\" use=\"optional\"/>");
      x.theDepth = node.theDepth + 3;
      toXSDEx(x, pw);
      pw.println();
      println(pw, node.theDepth + 2, "</xsd:sequence>");
      println(pw, node.theDepth + 2, "<xsd:attribute name=\"count\" type=\"xsd:integer\" use=\"optional\"/>");
      println(pw, node.theDepth + 2, "<xsd:attribute name=\"type\" type=\"xsd:string\" use=\"optional\"/>");
      addAttributes(node.theAttributes, pw, node.theDepth + 2);
      println(pw, node.theDepth + 1, "</xsd:complexType>");
      println(pw, node.theDepth, "</xsd:element>");
    } else if (node.elementType.equals(node.typeSequence)) {
      println(pw, node.theDepth, "<xsd:element name=\"" + node.elementName + "\"" + setElementAttributes(node) + ">");
      annotate(pw, node, node.theDepth);
      toXSDParent(node, pw);
      println(pw, node.theDepth, "</xsd:element>");
    } else if (node.elementType.equals(node.typeExtends)) {
      println(pw, node.theDepth, "<xsd:element name=\"" + node.elementName + "\"" + setElementAttributes(node) + ">");
      annotate(pw, node, node.theDepth);
      toXSDParent(node, pw);
      println(pw, node.theDepth, "</xsd:element>");
    } else if (node.elementType.equalsIgnoreCase(node.typeAny)) {
      println(pw, node.theDepth, "<xsd:element name=\"" + node.elementName + "\"" + setElementAttributes(node) + ">");
      annotate(pw, node, node.theDepth);
      println(pw, node.theDepth + 1, "<xsd:complexType>");
      println(pw, node.theDepth + 2, "<xsd:complexContent>");
      println(pw, node.theDepth + 3, "<xsd:extension base=\"xsd:anyType\">");
      println(pw, node.theDepth + 4, "<xsd:attribute name=\"type\" type=\"xsd:string\" use=\"optional\"/>");
      println(pw, node.theDepth + 3, "</xsd:extension>");
      println(pw, node.theDepth + 2, "</xsd:complexContent>");
      addAttributes(node.theAttributes, pw, node.theDepth + 2);
      println(pw, node.theDepth + 1, "</xsd:complexType>");
      println(pw, node.theDepth, "</xsd:element>");
    } else {
      boolean attrs = node.hasAttributes();
      String  ext   = attrs
                      ? "extension"
                      : "restriction";

      println(pw, node.theDepth, "<xsd:element name=\"" + node.elementName + "\"" + setElementAttributes(node) + ">");
      annotate(pw, node, node.theDepth);

      if (attrs) {
        println(pw, node.theDepth + 1, "<xsd:complexType>");
        println(pw, node.theDepth + 1, "<xsd:simpleContent>");
        min   = null;
        max   = null;
        range = null;
      } else {
        println(pw, node.theDepth + 1, "<xsd:simpleType>");
      }

      if (node.elementType.equals(node.typeInteger)) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:long\">");

        if ((min != null) && (min.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:minInclusive value=\"" + min + "\"/>");
        }

        if ((max != null) && (max.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:maxInclusive value=\"" + max + "\"/>");
        }
      } else if (node.elementType.equals(node.typeReal)) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:double\">");

        if ((min != null) && (min.length() > 0)) {
          if (min.indexOf('.') == -1) {
            min += ".0";
          }

          println(pw, node.theDepth + 2, "<xsd:minInclusive value=\"" + min + "\"/>");
        }

        if ((max != null) && (max.length() > 0)) {
          if (max.indexOf('.') == -1) {
            max += ".0";
          }

          println(pw, node.theDepth + 2, "<xsd:maxInclusive value=\"" + max + "\"/>");
        }
      } else if (node.elementType.equalsIgnoreCase(node.typePrintableString)) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:string\">");

        if ((min != null) && (min.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:minLength value=\"" + min + "\"/>");
        }

        if ((max != null) && (max.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:maxLength value=\"" + max + "\"/>");
        }

        if ((min == null) && (min == null) && (range != null) && (range.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:length value=\"" + range + "\"/>");
        }
      } else if (node.elementType.equalsIgnoreCase(node.typeOctetString)
                 || node.elementType.equalsIgnoreCase("ByteString")) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:base64Binary\">");

        if ((min != null) && (min.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:minLength value=\"" + min + "\"/>");
        }

        if ((max != null) && (max.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:maxLength value=\"" + max + "\"/>");
        }

        if ((min == null) && (min == null) && (range != null) && (range.length() > 0)) {
          println(pw, node.theDepth + 2, "<xsd:length value=\"" + range + "\"/>");
        }
      } else if (node.elementType.equalsIgnoreCase(node.typeBoolean)) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:boolean\">");
      } else if (node.elementType.equalsIgnoreCase(node.typeDate)) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:date\">");

        if (min != null) {
          println(pw, node.theDepth + 2, "<xsd:minInclusive value=\"" + min + "\"/>");
        }

        if (max != null) {
          println(pw, node.theDepth + 2, "<xsd:maxInclusive value=\"" + max + "\"/>");
        }
      } else if (node.elementType.equalsIgnoreCase(node.typeTime)) {
        println(pw, node.theDepth + 2, "<xsd:restriction base=\"xsd:time\">");

        if (min != null) {
          println(pw, node.theDepth + 2, "<xsd:minInclusive value=\"" + min + "\"/>");
        }

        if (max != null) {
          println(pw, node.theDepth + 2, "<xsd:maxInclusive value=\"" + max + "\">");
        }
      } else if (node.elementType.equalsIgnoreCase(node.typeDateTime)) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:dateTime\">");

        if (min != null) {
          println(pw, node.theDepth + 2, "<xsd:minInclusive value=\"" + min + "\"/>");
        }

        if (max != null) {
          println(pw, node.theDepth + 2, "<xsd:maxInclusive value=\"" + max + "\"/>");
        }
      } else if (node.elementType.equalsIgnoreCase("UTCTime")) {
        println(pw, node.theDepth + 2, "<xsd:" + ext + " base=\"xsd:string\">");

        if (min != null) {
          println(pw, node.theDepth + 2, "<xsd:minInclusive value=\"" + min + "\"/>");
        }

        if (max != null) {
          println(pw, node.theDepth + 2, "<xsd:maxInclusive value=\"" + max + "\"/>");
        }
      }

      addAttributes(node.theAttributes, pw, node.theDepth + 3);
      println(pw, node.theDepth + 2, "</xsd:" + ext + ">");

      if (attrs) {
        println(pw, node.theDepth + 1, "</xsd:simpleContent>");
        println(pw, node.theDepth + 1, "</xsd:complexType>");
      } else {
        println(pw, node.theDepth + 1, "</xsd:simpleType>");
      }

      println(pw, node.theDepth, "</xsd:element>");
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param pw DOCUMENT ME!
   */
  protected void toXSDParent(SPOTNode node, PrintWriter pw) {
    if (node.isRefine) {
      SPOTNode parent = (SPOTNode) objects.get(node.extendsType);

      if (parent != null) {
        toXSDParentRefine(node, parent, pw);

        return;
      }
    }

    int    i     = 0;
    int    depth = 0;
    String s;

    if ((node.parentNode != null) && (node.parentNode.parentNode == null)) {    // top level node
      pw.println();
      println(pw, node.theDepth,
              "<xsd:element name=\"" + node.elementName + "\" type=\"" + namespace + node.elementName + "Type\"/>");
      println(pw, node.theDepth, "<xsd:complexType name=\"" + node.elementName + "Type\">");
    } else {
      println(pw, node.theDepth, "<xsd:complexType>");
    }

    annotate(pw, node, node.theDepth);

    if ((node.extendsType != null) &&!node.extendsType.equalsIgnoreCase("SEQUENCE")) {    // EXTENDS
      depth += 2;
      s     = (String) nameSpaceObjects.get(node.extendsType + "Type");

      if (s == null) {
        s = namespace;
      }

      println(pw, node.theDepth + 1, "<xsd:complexContent>");
      println(pw, node.theDepth + 2, "<xsd:extension base=\"" + s + node.extendsType + "Type\">");
    }

    println(pw, node.theDepth + depth, "\t<xsd:sequence maxOccurs=\"1\">");

    SPOTNode   x        = null;
    ObjectList elements = node.childNodes;
    int        len      = (elements == null)
                          ? 0
                          : elements.size();

    for (i = 0; i < len; i++) {
      x          = (SPOTNode) elements.get(i);
      x.theDepth = node.theDepth + 3;
      toXSDEx(x, pw);
      pw.println();
    }

    println(pw, node.theDepth + depth, "\t</xsd:sequence>");

    if (depth > 0) {
      addAttributes(node.theAttributes, pw, node.theDepth + 1);
      println(pw, node.theDepth + 2, "</xsd:extension>");
      println(pw, node.theDepth + 1, "</xsd:complexContent>");
    }

    println(pw, node.theDepth, "</xsd:complexType>");
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param parent DOCUMENT ME!
   * @param pw DOCUMENT ME!
   */
  protected void toXSDParentRefine(SPOTNode node, SPOTNode parent, PrintWriter pw) {
    int        i        = 0;
    int        p        = 0;
    ObjectList elements = null;
    int        depth    = 0;
    String     fixedValue;
    String     value;
    String     range;
    String[]   choices;
    boolean    optional;

    if ((node.parentNode != null) && (node.parentNode.parentNode == null)) {    // top level node
      pw.println();
      println(pw, node.theDepth,
              "<xsd:element name=\"" + node.elementName + "\" type=\"" + namespace + node.elementName + "Type\"/>");
      println(pw, node.theDepth, "<xsd:complexType name=\"" + node.elementName + "Type\">");
    } else {
      println(pw, node.theDepth, "<xsd:complexType>");
    }

    annotate(pw, node, node.theDepth);
    depth += 4;
    println(pw, node.theDepth + 1, "<xsd:complexContent>");
    println(pw, node.theDepth + 2, "<xsd:restriction base=\"" + namespace + node.extendsType + "Type\">");
    println(pw, node.theDepth + 3, "<xsd:sequence maxOccurs=\"unbounded\">");
    pw.println();

    SPOTNode x  = null;
    SPOTNode xx = null;

    elements = parent.childNodes;

    int len = (elements == null)
              ? 0
              : elements.size();

    for (i = 0; i < len; i++) {
      x          = (SPOTNode) elements.get(i);
      p          = node.indexOf(x.elementName, 0);
      fixedValue = x.defaultValue;
      value      = x.defaultValue;
      range      = x.theRange;
      choices    = x.theChoices;
      optional   = x.isOptional;

      if (p != -1) {
        xx = (SPOTNode) node.elementAt(p);

        if (xx.fixedValue != null) {
          x.fixedValue = xx.fixedValue;
        }

        if (xx.defaultValue != null) {
          x.defaultValue = xx.defaultValue;
        }

        if (xx.theRange != null) {
          x.theRange = xx.theRange;
        }

        if (xx.theChoices != null) {
          x.theChoices = xx.theChoices;
        }

        x.isOptional = xx.isOptional;
      }

      x.theDepth = node.theDepth + depth;
      toXSDEx(x, pw);
      x.fixedValue   = fixedValue;
      x.defaultValue = value;
      x.theRange     = range;
      x.theChoices   = choices;
      x.isOptional   = optional;
      pw.println();
    }

    elements = node.childNodes;
    len      = (elements == null)
               ? 0
               : elements.size();

    for (i = 0; i < len; i++) {
      x          = (SPOTNode) elements.get(i);
      x.theDepth = node.theDepth + depth;
      toXSDEx(x, pw);
      pw.println();
    }

    println(pw, node.theDepth + depth, "\t</xsd:sequence>");
    println(pw, node.theDepth + 2, "</xsd:restriction>");
    println(pw, node.theDepth + 1, "</xsd:complexContent>");
    addAttributes(node.theAttributes, pw, node.theDepth + 1);
    println(pw, node.theDepth, "</xsd:complexType>");
    pw.println();
  }

  /**
   * DOCUMENT ME!
   *
   * @param x DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String setElementAttributes(SPOTNode x) {
    String s = "";

    if ((x.parentNode != null) && x.parentNode.elementType.equalsIgnoreCase("SET")) {
      String range = x.parentNode.theRange;
      String min   = "1";
      String max   = "unbounded";

      if (range != null) {
        int n = range.indexOf("..");

        if (n != -1) {
          min = range.substring(0, n);
          max = range.substring(n + 2);

          if (min.length() == 0) {
            min = "0";
          }

          if (max.length() == 0) {
            max = "unbounded";
          }
        } else {
          min = range;
          max = range;
        }
      }

      s = " minOccurs=\"" + min + "\" maxOccurs=\"" + max + "\"";
    } else {
      if (x.isOptional || ((x.defaultValue != null) && (x.defaultValue.length() > 0))) {
        s = " minOccurs=\"0\"";
      }
    }

    if ((x.defaultValue != null) && (x.defaultValue.length() > 0)) {
      if (x.defaultValue.charAt(0) == '\"') {
        s += (" default=" + x.defaultValue);
      } else {
        s += (" default=\"" + x.defaultValue + "\"");
      }
    } else if ((x.fixedValue != null) && (x.fixedValue.length() > 0)) {
      s += (" fixed=\"" + x.fixedValue + "\"");
    }

    return s;
  }

  private static void escapeChararacter(char c, StringBuilder out) {
    if (c == '<') {
      out.append("&lt;");
    } else if (c == '>') {
      out.append("&gt;");
    } else if (c == '&') {
      out.append("&amp;");
    } else if (c == '\'') {
      out.append("&apos;");
    } else if (c == 13) {
      out.append(' ');
    } else if (c == 10) {
      out.append((char) 10);
    } else {
      if (c < 32) {
        out.append('&');
        out.append('#');
        out.append((int) c);
        out.append(';');
      } else {
        out.append(c);
      }
    }
  }
}