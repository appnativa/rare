/*
 * @(#)SPOTToJava.java   2010-04-01
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.spot.compiler;

import com.appnativa.spot.*;
import com.appnativa.util.*;
import com.appnativa.util.SNumber;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * DOCUMENT ME!
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class SPOTToJava {
  static final String javaComment = " * ";
  static final char[] padding     = new char[256];
  static String[]     theTypes    = {
    "PrintableString", "OctetString", "Set", "Sequence", "Any", "UTCTime", "Date", "Time", "Integer", "Real",
    "Enumerated", "Extends", "DateTime", "Boolean", "ByteString"
  };

  static {
    int len = padding.length;

    for (int i = 0; i < len; i++) {
      padding[i] = ' ';
    }
  }

  HashMap      comments         = new HashMap();
  String       genbegin         = "//";
  String       gencon           = "GENERATED_METHODS";
  String       genend           = "//}";
  String       geninner         = "GENERATED_INNER_CLASSES";
  String       genmembers       = "GENERATED_MEMBERS";
  String       homepkg          = "com.appnativa.spot";
  List<String> imports          = null;
  String       initializeMethod = "spot_initialize";
  String       interfaces       = "";
  String       lasncomment      = "//GENERATED_COMMENT{}";
  String       lasnelement      = "iSPOTElement";
  String       nameprefix       = "SPOT";
  String       lasnany          = nameprefix + "Any";
  String       precon           = null;
  String       preend           = null;
  String       preinner         = null;
  String       premembers       = null;
  int          readBufferLen    = 1024;
  String       userimportandcom = null;
  String       usermark         = "//USER_IMPORTS_AND_COMMENTS_MARK{}";
  String       userpkg          = "";
  SPOTNode     rootElement;

  /**
   * Constructs ... DOCUMENT ME!
   *
   */
  public SPOTToJava() {}

  /**
   * The main entry point for the application.
   *
   * @param args Array of parameters passed to the application via the command line.
   */
  public static void main(String[] args) {
    if ((args == null) || (args.length == 0)) {
      args    = new String[3];
      args[0] = "test.spot";
      args[1] = ".";
      args[2] = "com.appnativa.parser";
    }

    try {
      if (args.length < 1) {
        usage();

        return;
      }

      String          access;
      String          aut = "Don DeCoteau";
      String          cp  = null;
      String          ver = "1.0";
      String          dd  = ".";
      String          src;
      String          pkg  = null;
      String          imp  = null;
      String          ifs  = null;
      String          spot = null;
      SPOTToJava      x    = new SPOTToJava();
      FileInputStream f;

      if (args[0].endsWith(".xml")) {
        f = new FileInputStream(args[0]);

        XMLDataItem xml = XMLDataItem.parse(f);

        access = xml.getValueAsString("access");
        aut    = xml.getValueAsString("author");
        ver    = xml.getValueAsString("version");
        cp     = xml.getValueAsString("copyright");
        src    = xml.getValueAsString("source");
        dd     = xml.getValueAsString("destination");
        pkg    = xml.getValueAsString("package");
        imp    = xml.getValueAsString("imports");
        ifs    = xml.getValueAsString("interfaces");

        if (args.length > 1) {
          src = args[1];
        }

        if ((spot != null) && (spot.length() > 0)) {
          x.homepkg = spot;
        }

        if (dd == null) {
          dd = ".";
        }

        if (access == null) {
          access = "public";
        }

        if (ver == null) {
          ver = "1.0";
        }

        if (aut == null) {
          aut = "Don DeCoteau";
        }

        if (src == null) {
          System.err.println("<source> attribute missing from XML file");

          return;
        }
      } else {
        src = args[0];

        if (args.length > 1) {
          dd = args[1];
        }

        if (dd.length() == 0) {
          dd = ".";
        }

        access = (args.length > 4)
                 ? args[4]
                 : "public";
        access.toLowerCase();
        pkg = (args.length > 2)
              ? args[2]
              : null;
        imp = (args.length > 3)
              ? args[3]
              : null;
      }

      if ((dd.charAt(dd.length() - 1) != '\\') & (dd.charAt(dd.length() - 1) != '/')) {
        dd += "\\";
      }

      if (cp != null) {
        cp = cp.trim();
      }

      System.out.println("Parsing " + src);
      System.out.println("Destination directory is: " + dd);

      FileReader fr = new java.io.FileReader(src);

      if (ifs != null) {
        x.interfaces = " implements " + ifs;
      }

      x.toJava(fr, dd, pkg, imp, access, aut, ver, cp);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Generates java class files from an SPOT formatted stream
   *
   * @param in The reader containing the SPOT formatted stream
   * @param path The path where the generated files will be placed
   * @param pkg The package the the generated files will be part of
   * @param imp A semicolon seperated list of packages import for the generated classes
   * @param access A string representing the access modifier ("public" or "protected") for the
   *        member variables for the object.
   * @param aut DOCUMENT ME!
   * @param ver DOCUMENT ME!
   * @param cp DOCUMENT ME!
   */
  public void toJava(Reader in, String path, String pkg, String imp, String access, String aut, String ver, String cp)
          throws java.text.ParseException {
    SPOTNode x = null;

    try {
      x = new SPOTNode(in);
    } catch(FormatException ex) {
      try {
        if (x != null) {
          System.err.println(x.toString());
        }
      } catch(Exception ex1) {}

      ex.printStackTrace();
    }

    if (pkg != null) {
      userpkg = pkg;
    }

    x.theDepth = 0;
    toJava(x, path, pkg, imp, access, aut, ver, cp);
  }

  /**
   * Generates java class files from an SPOTNode object
   *
   * @param node The SPOTNode object
   * @param path The path where the generated files will be placed
   * @param pkg The package the the generated files will be part of
   * @param imp A semicolon seperated list of packages import for the generated classes
   * @param access A string representing the access modifier ("public" or "protected") for the
   *        member variables for the object.
   * @param aut DOCUMENT ME!
   * @param ver DOCUMENT ME!
   * @param cp DOCUMENT ME!
   */
  public void toJava(SPOTNode node, String path, String pkg, String imp, String access, String aut, String ver,
                     String cp)
          throws java.text.ParseException {
    int        i        = 0;
    int        n        = 0;
    SPOTNode   x        = null;
    Date       date     = new Date();
    ObjectList elements = node.childNodes;
    int        len      = (elements == null)
                          ? 0
                          : elements.size();
    int        depth    = 1;
    boolean    xtend    = false;

    if (len == 0) {
      return;
    }

    if ((imp != null) && (imp.length() > 0)) {
      imports = CharScanner.getTokens(imp, ';');
    }

    if ((pkg != null) && (pkg.length() == 0)) {
      pkg = null;
    }

    ByteArrayOutputStream    stream = new ByteArrayOutputStream(1024);
    PrintWriter              pw     = new PrintWriter(stream);
    String                   s;
    java.io.FileOutputStream fo;
    ArrayList                classes = new ArrayList();
    ArrayList                method;
    HashMap                  methods  = new HashMap();
    HashMap                  refinits = new HashMap();

    methods.put(initializeMethod, new ArrayList());

    boolean printcomment = false;

    rootElement = node;

    for (i = 0; i < len; i++) {
      printcomment = false;
      x            = (SPOTNode) elements.get(i);
      x.theDepth   = 0;
      readExisting(path + x.elementName + ".java");
      s     = (x.extendsType == null)
              ? x.elementType
              : x.extendsType;
      n     = SPOTNode.findType(s);
      xtend = x.extendsType != null;

      if (n != -1) {
        s = nameprefix + theTypes[n];
      }

      System.out.println("Reading class '" + x.elementName + "'");
      pw.println("/**************************************************************************");
      pw.println(" * " + x.elementName + ".java - " + date.toString());

      if ((cp != null) && (cp.length() > 0)) {
        pw.println(" *");
        pw.print(" ");
        pw.println(cp);
      }

      pw.println(" *");
      pw.println(" * Generated by the Sparse Notation(tm) To Java Compiler v1.0");
      pw.println(
          " * Note 1: Code entered after the \"//USER_IMPORTS_AND_COMMENTS_MARK{}\" comment and before the class declaration will be preserved.");
      pw.println(" * Note 2: Code entered out side of the other   comment blocks will be preserved");
      pw.println(
          " * Note 3: If you edit the automatically generated comments and want to preserve your edits remove the //GENERATED_COMMENT{} tags");
      pw.println(" */\n");

      if (pkg != null) {
        pw.println("package " + pkg + ";\n");
      }

      if (imports != null) {
        for (n = 0; n < imports.size(); n++) {
          pw.println("import " + imports.get(n) + ";");
        }
      }

      pw.println("import " + homepkg + ".*;\n");
      pw.println(usermark);

      if (x.theDescription != null) {
        do {
          if (userimportandcom == null) {
            printcomment = true;

            break;
          }

          n = userimportandcom.indexOf("/*");

          if (n == -1) {
            printcomment = true;

            break;
          }

          if ((n = userimportandcom.indexOf(lasncomment)) != -1) {
            userimportandcom = userimportandcom.substring(0, n).trim();
            userimportandcom += "\n";
            printcomment     = true;
          }
        } while(false);
      }

      if (userimportandcom != null) {
        pw.println(userimportandcom);
      } else {
        pw.println();
      }

      if (printcomment) {
        comment(pw, 0, x.theDescription, false);

        try {
          if ((aut != null) && (aut.length() != 0)) {
            pw.write(" *");
            SPOTNode.newLine(pw);
            pw.write(" *");
            pw.write(" @author ");
            pw.write(aut);
            SPOTNode.newLine(pw);

            if ((ver != null) && (ver.length() != 0)) {
              pw.write(" *");
              pw.write(" @version ");
              pw.write(ver);
              SPOTNode.newLine(pw);
            }
          } else if ((ver != null) && (ver.length() != 0)) {
            pw.write(" *");
            SPOTNode.newLine(pw);
            pw.write(" *");
            pw.write(" @version ");
            pw.write(ver);
            SPOTNode.newLine(pw);
          }

          pw.write(" */  ");
          SPOTNode.newLine(pw);
        } catch(IOException ex) {}
      }

      pw.print("public class " + x.elementName + " extends " + s + interfaces + " {\n");

      if (premembers != null) {
        pw.println(premembers);
      }

      println(pw, depth, genbegin + genmembers + "{\n");
      toJavaParent(x, pw, classes, methods, refinits, access);
      println(pw, depth, genend + genmembers);

      if (precon != null) {
        pw.println(precon);
      } else {
        pw.println();
      }

      println(pw, depth, genbegin + gencon + "{\n");
      print(pw, depth, "/**\n");
      print(pw, depth, " * Creates a new optional <code>" + x.elementName + "</code> object.\n");
      print(pw, depth, " */\n");
      print(pw, depth, "public " + x.elementName + "()");
      print(pw, depth, "{\n");
      print(pw, depth + 1, "this(true);\n");
      print(pw, depth, "}\n\n");
      print(pw, depth, "/**\n");
      print(pw, depth, " * Creates a new <code>" + x.elementName + "</code> object.\n");
      print(pw, depth, " *\n");
      print(pw, depth,
            " * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)\n");
      print(pw, depth, " */\n");
      print(pw, depth, "public " + x.elementName + "( boolean optional ) {\n");
      print(pw, depth + 1, "super( optional, false );\n");
      print(pw, depth + 1, "spot_setElements();\n");
      print(pw, depth, "}\n\n");
      print(pw, depth, "/**\n");
      print(pw, depth, " * Creates a new <code>" + x.elementName + "</code> object.\n");
      print(pw, depth, " *\n");
      print(pw, depth,
            " * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)\n");
      print(pw, depth, " */\n");
      print(pw, depth, "protected " + x.elementName + "( boolean optional,boolean setElements ) {\n");
      print(pw, depth + 1, "super( optional, setElements );\n");
      print(pw, depth, "}\n\n");
      print(pw, depth, "/**\n");
      print(pw, depth, " * Adds elements to the object elements map\n");
      print(pw, depth, " *\n");
      print(pw, depth, " */\n");
      print(pw, depth, "protected void spot_setElements()  {\n");
      print(pw, depth + 1, "super.spot_setElements();\n");

      if (x.theAttributes != null) {
        Iterator  it = x.theAttributes.entrySet().iterator();
        Map.Entry me;
        String    sn;
        String    v;

        while(it.hasNext()) {
          me = (Map.Entry) it.next();
          sn = (String) me.getKey();
          v  = (String) me.getValue();

          if (v != null) {
            v = "\",\"" + v + "\");";
          } else {
            v = "\",null);";
          }

          println(pw, depth + 1, "spot_defineAttribute(\"" + sn + v);
        }
      }

      method = (ArrayList) methods.get(initializeMethod);
      n      = method.size();

      for (int p = 0; p < n; p++) {
        s = (String) method.get(p);
        pw.print(s);
      }

      print(pw, depth, "}\n\n");
      method.clear();

      if (!access.equals("public")) {
        createAccessMethods(x, pw);
        pw.println();
      }

      createReferenceMethods(x, pw, refinits, access.equals("public"));
      println(pw, depth, genend + gencon);

      if (preinner != null) {
        pw.println(preinner);
      } else {
        pw.println();
      }

      n = classes.size();
      println(pw, depth, genbegin + geninner + "{\n");

      if (n > 0) {
        for (int p = 0; p < n; p++) {
          s = (String) classes.get(p);
          pw.print(s);
        }
      }

      println(pw, depth, genend + geninner);

      if (preend != null) {
        pw.println(preend);
      } else {
        pw.print("}");
      }

      pw.flush();

      try {
        s  = path + x.elementName + ".java";
        fo = new java.io.FileOutputStream(s);
        fo.write(stream.toByteArray());
        fo.close();
        System.out.println("Generated java class file '" + s + "'");
      } catch(java.io.IOException e) {
        System.out.println(e);
      }

      classes.clear();
      stream.reset();
    }
  }

  /**
   * DOCUMENT ME!
   */
  public static void usage() {
    System.out.println("Usage: SPOTToJava sourceFile outputPath [package]  [import;[import];...] [access]");
    System.out.println(" sourceFile\t- the file containing the Sparse Notation to be parsed.");
    System.out.println(" outputPath\t- the directory where the generated java files are to be stored.");
    System.out.println(" package\t- the package name (optional) for the generated classes.");
    System.out.println(" import\t- an optional semicolon seperated list of packages import for the generated classe.");
  }

  String cleanWhiteSpace(String s) {
    StringBuilder out = new StringBuilder(s.length());
    char[]        b   = s.toCharArray();
    int           len = b.length;
    char          lc  = 0;

    for (int i = 0; i < len; i++) {
      if (b[i] > 32) {
        lc = 0;
        out.append(b[i]);

        continue;
      }

      if (lc == 32) {
        continue;
      }

      lc = 32;
      out.append(' ');
    }

    return out.toString();
  }

  void comment(Writer out, int depth, String s, boolean close) {
    try {
      s = s.trim();

      int len  = s.length();
      int wrap = 60;

      if (len == 0) {
        return;
      }

      List<String> lines  = CharScanner.getTokens(s, '\n');
      List<String> lines2 = new StringList();

      len = lines.size() - 1;
      out.write(lasncomment);
      SPOTNode.newLine(out);
      writePadding(out, depth);
      out.write("/**");
      SPOTNode.newLine(out);
      s = lines.get(0).trim();

      if (s.length() != 0) {
        s      = leftJustify(wrap, s);
        lines2 = CharScanner.getTokens(s, '\n');

        if (lines2 != null) {
          for (int n = 0; n < lines2.size(); n++) {
            writePadding(out, depth);
            out.write(javaComment);
            s = lines2.get(n).trim();
            out.write(s);
            SPOTNode.newLine(out);
          }
        }
      }

      for (int i = 1; i < lines.size(); i++) {
        writePadding(out, depth);
        out.write(javaComment);
        s = lines.get(i).trim();

        if (s.length() == 0) {
          SPOTNode.newLine(out);

          continue;
        }

        out.write("<p>");
        SPOTNode.newLine(out);
        s      = leftJustify(wrap, s);
        lines2 = CharScanner.getTokens(s, '\n');

        if (lines2 != null) {
          for (int n = 0; n < lines2.size(); n++) {
            writePadding(out, depth);
            out.write(javaComment);
            s = lines2.get(n).trim();
            out.write(s);
            SPOTNode.newLine(out);
          }
        }

        writePadding(out, depth);
        out.write(javaComment);
        out.write("</p>");
        SPOTNode.newLine(out);
      }

      if (close) {
        writePadding(out, depth);
        out.write(" */  ");
        SPOTNode.newLine(out);
      }
    } catch(IOException ex) {}
  }

  String leftJustify(int width, String st) {
    StringBuilder buf       = new StringBuilder(st);
    int           lastspace = -1;
    int           linestart = 0;
    int           i         = 0;

    while(i < buf.length()) {
      if (buf.charAt(i) == ' ') {
        lastspace = i;
      }

      if (buf.charAt(i) == '\n') {
        lastspace = -1;
        linestart = i + 1;
      }

      if (i > ((linestart + width) - 1)) {
        if (lastspace != -1) {
          buf.setCharAt(lastspace, '\n');
          linestart = lastspace + 1;
          lastspace = -1;
        } else {
          buf.insert(i, '-');
          buf.insert(i, '\n');
          linestart = i + 2;
        }
      }

      i++;
    }

    return buf.toString();
  }

  String makeAccessName(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }

  String makeGeneratedName(String name) {
    return "C" + name.substring(0, 1).toUpperCase() + name.substring(1);
  }

  String makeSetArg(SPOTNode x, ArrayList classes, ArrayList init, String access) throws java.text.ParseException {
    String       val = null;
    String       s;
    StringWriter sw       = new StringWriter();
    PrintWriter  pw       = new PrintWriter(sw);
    HashMap      methods  = new HashMap();
    HashMap      refinits = new HashMap();

    methods.put(initializeMethod, new ArrayList());

    String pkg;

    if (x.elementType.equals("REFINE")) {
      throw new java.text.ParseException("cannot REFINE an node in a SET", 0);
    }

    toJavaEx(x, pw, classes, methods, refinits, access);
    pw.flush();

    String arg = sw.toString().trim();
    int    n;

    pkg = (x.extendsType == null)
          ? x.elementType
          : x.extendsType;

    if (pkg.endsWith(SPOTNode.typeSequence) || (SPOTNode.findType(pkg) == -1)) {
      pkg = userpkg;
    } else {
      pkg = homepkg;
    }

    n = arg.indexOf('\n');

    while(n != -1) {
      arg = arg.substring(n + 1).trim();
      n   = arg.indexOf('\n');
    }

    n = arg.indexOf('=');

    do {
      if (n == -1) {
        break;
      }

      if (x.isReference) {
        access = "proctected";
      }

      s   = arg.substring(0, n);
      arg = arg.substring(n + 1);
      n   = access.length();

      if ((n > 0) &&!s.startsWith(access)) {
        break;
      }

      s = s.substring(n).trim();

      if (!s.endsWith(x.elementName)) {
        break;
      }

      n = arg.length();

      if ((n < 2) || (arg.charAt(n - 1) != ';')) {
        break;
      }

      s = arg.substring(0, n - 1).trim();

      if (!s.startsWith("new ")) {
        if (s.equals("null")) {
          s = x.elementType;
        }
      } else {
        val = s;

        /*
         * if (pkg == homepkg) {
         * val = s;
         * break;
         * }
         *
         * s = s.substring(4).trim();
         * n = s.indexOf('(');
         *
         * if (n == -1) {
         * break;
         * }
         * s = s.substring(0, n).trim();
         * }
         * if(s.indexOf('.') != -1 || (pkg==null || pkg.length()==0)) {
         * val=("\"" + s + "\"");
         * }
         * else {
         * val = ("new " + pkg + "." + s + "()");
         */
      }
    } while(false);

    return val;
  }

  static String print(int depth, String s) {
    StringBuilder out = new StringBuilder();

    writePadding(out, depth);
    out.append(s);

    return out.toString();
  }

  static void print(PrintWriter out, String s) {
    out.print(s);
  }

  static void print(Writer out, int depth, String s) {
    try {
      writePadding(out, depth);
      out.write(s);
    } catch(IOException ex) {}
  }

  static String println(int depth, String s) {
    StringBuilder out = new StringBuilder();

    writePadding(out, depth);
    out.append(s);
    out.append(SPOTNode.lineSeparator);

    return out.toString();
  }

  static void println(PrintWriter out, String s) {
    out.println(s);
  }

  static String println(int depth, String s, int ln) {
    StringBuilder out = new StringBuilder();

    writePadding(out, depth);
    out.append(s);

    for (int i = 0; i < ln; i++) {
      out.append(SPOTNode.lineSeparator);
    }

    return out.toString();
  }

  static void println(Writer out, int depth, String s) {
    try {
      writePadding(out, depth);
      out.write(s);
      SPOTNode.newLine(out);
    } catch(IOException ex) {}
  }

  static void println(Writer out, int depth, String s, int ln) {
    try {
      writePadding(out, depth);
      out.write(s);

      for (int i = 0; i < ln; i++) {
        out.write(SPOTNode.lineSeparator);
      }
    } catch(IOException ex) {}
  }

  static void writePadding(StringBuilder out, int depth) {
    if (depth == 0) {
      return;
    }

    int len = padding.length;

    depth *= 2;

    while(depth > len) {
      out.append(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.append(padding, 0, depth);
    }
  }

  static void writePadding(Writer out, int depth) throws IOException {
    if (depth == 0) {
      return;
    }

    depth *= 2;

    int len = padding.length;

    while(depth > len) {
      out.write(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.write(padding, 0, depth);
    }
  }

  SPOTNode getRefinedElement(SPOTNode x) {
    if ((rootElement == null) || (x.parentNode == null) || (x.parentNode == x)) {
      return null;
    }

    SPOTNode r    = null;
    String   name = x.elementName;

    x = x.parentNode;

    while((r == null) && (x != null) && (x.elementType != null)) {
      if (x.elementType.equalsIgnoreCase(SPOTNode.typeExtends)) {
        x = rootElement.elementFor(x.extendsType);
        r = x.elementFor(name);

        continue;
      }

      r = x.elementFor(name);

      break;
    }

    return r;
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param pw DOCUMENT ME!
   */
  protected void createAccessMethods(SPOTNode node, PrintWriter pw) {
    int      i  = 0;
    int      n  = 0;
    SPOTNode x  = null;
    SPOTNode xx = null;
    String   s;
    char[]   c = new char[(node.theDepth + 1) * 2];

    Arrays.fill(c, ' ');

    String tab = new String(c);
    String name;
    int    len = node.size();

    if (len == 0) {
      return;
    }

    for (i = 0; i < len; i++) {
      x    = (SPOTNode) node.elementAt(i);
      name = makeAccessName(x.elementName);

      if (x.elementType.equalsIgnoreCase("REFINE")) {
        continue;
      }

      if (x.elementType.equalsIgnoreCase("INTEGER")) {
        pw.println(tab + "public long get" + name + "() { return " + x.elementName + ".getValue(); };");
        pw.println(tab + "public void set" + name + "(long val) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("ENUMERATED")) {
        pw.println(tab + "public int get" + name + "() { return " + x.elementName + ".getValue(); };");
        pw.println(tab + "public void set" + name + "(int val) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("SET")) {
        xx = (SPOTNode) x.childNodes.get(0);
        n  = SPOTNode.findType(xx.elementType);
        s  = null;

        if (n == -1) {
          s = xx.elementType;
        }

        if (s == null) {
          pw.println(tab + "public String[] get" + name + "() { return " + x.elementName + ".stringValues(); };");
        } else {
          pw.println(tab + "public " + s + "[] get" + name + "() { return (" + s + "[])" + x.elementName
                     + ".getValues(); };");
        }

        if (s == null) {
          pw.println(tab + "public void set" + name + "(String  val[]) {");
          pw.println(tab + tab + x.elementName + ".setValue(val);");
        } else {
          pw.println(tab + "public void set" + name + "(" + s + "  val[]){");
          pw.println(tab + tab + x.elementName + ".setValue(val);");
        }

        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("REAL")) {
        pw.println(tab + "public double get" + name + "() { return " + x.elementName + ".getValue(); };");
        pw.println(tab + "public void set" + name + "(double val) \n" + tab + "{");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("SEQUENCE")) {
        pw.println(tab + "public " + makeGeneratedName(x.elementName) + " get" + name + "() { return " + x.elementName
                   + "; };");
        pw.println(tab + "public String get" + name + "(String name) { return " + x.elementName
                   + ".getValue(name); };");
        pw.println(tab + "public void set" + name + "(String  name, String val) {");
        pw.println(tab + tab + x.elementName + ".setValue(name,val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("PrintableString") || x.elementType.equalsIgnoreCase("OctetString")) {
        pw.println(tab + "public String get" + name + "() { return " + x.elementName + ".stringValue(); };");
        pw.println(tab + "public void set" + name + "(String  val) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("ByteString")) {
        pw.println(tab + "public byte[] get" + name + "() { return " + x.elementName + ".getValue(); };");
        pw.println(tab + "public void set" + name + "(String  val) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
        pw.println(tab + "public void set" + name + "(byte  val[]) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("BOOLEAN")) {
        pw.println(tab + "public boolean get" + name + "() { return " + x.elementName + ".getValue(); };");
        pw.println(tab + "public void set" + name + "(boolean val) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else if (x.elementType.equalsIgnoreCase("DATE") || node.elementType.equalsIgnoreCase("DateTime")
                 || node.elementType.equalsIgnoreCase("TIME") || node.elementType.equalsIgnoreCase("UTCTime")) {
        pw.println(tab + "public String get" + name + "() { return " + x.elementName + ".getValue(); };");
        pw.println(tab + "public void set" + name + "(String  val) {");
        pw.println(tab + tab + x.elementName + ".setValue(val);");
        pw.println(tab + "}\n");
      } else {
        pw.println(tab + "public " + x.elementType + " get" + name + "() { return " + x.elementName + "; };");
      }
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   * @param choices DOCUMENT ME!
   * @param depth DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String createChoicesInit(String name, String[] choices, int depth) {
    char[] c = new char[depth * 2];

    Arrays.fill(c, ' ');

    String        tab = new String(c);
    StringBuilder s   = new StringBuilder(tab);

    s.append("{\n");
    s.append(tab);
    s.append(tab);
    s.append("String choices[] = new String[");
    s.append(choices.length);
    s.append("];\n");

    for (int i = 0; i < choices.length; i++) {
      s.append(tab);
      s.append(tab);
      s.append("choices[ ");
      s.append(i);
      s.append(" ] = ");
      s.append(choices[i]);
      s.append(";\n");
    }

    s.append(tab);
    s.append(tab);
    s.append(name);
    s.append(".spot_setChoices( choices );\n");
    s.append(tab);
    s.append("}\n");

    return s.toString();
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param name DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String createEnumeratedClass(SPOTNode node, String name) {
    String[]  sa    = node.theChoices;
    SNumber[] na    = node.theNumChoices;
    int       depth = node.theDepth;
    char[]    c     = new char[depth * 2];

    Arrays.fill(c, ' ');

    String tab = new String(c);
    String s   = tab;
    String cs  = tab + tab + tab + "int    nchoices[] = {\n";
    String gs  = tab + tab + tab + "String schoices[] = {\n";
    String pad = tab + tab + tab + "       ";
    String gv  = tab + tab + tab + "return \"{\"\n";

    s += "/**\n";
    s += (tab + " * Class that defines the valid set of choices for\n");
    s += (tab + " * the <code>" + node.parentNode.elementName + "." + node.elementName + "</code> ENUMERATED object\n");
    s += (tab + " */\n");
    s += (tab + "public static class " + name + " extends " + nameprefix + "Enumerated {\n\n");

    for (int i = 0; i < sa.length; i++) {
      if (node.theChoicesComments[i] != null) {
        s += (tab + tab + "/** " + node.theChoicesComments[i].substring(2) + " */\n");
      }

      s += (tab + tab + "public final static int " + sa[i] + " = " + na[i].toString() + ";\n");

      if (i == (sa.length - 1)) {
        cs += (pad + " " + na[i].toString() + "\n" + pad + "};\n\n");
        gs += (pad + " \"" + sa[i] + "\"\n" + pad + "};\n\n");
        gv += (tab + tab + tab + "+\"" + sa[i] + "(" + na[i].toString() + ") }\";\n");
      } else {
        cs += (pad + " " + na[i].toString() + ",\n");
        gs += (pad + " \"" + sa[i] + "\",\n");
        gv += (tab + tab + tab + "+\"" + sa[i] + "(" + na[i].toString() + "), \"\n");
      }
    }

    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "()\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super();\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " *  @param optional <code>true</code> if the node the object represents is optional\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "(boolean optional)\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super(optional);\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param val the value\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "( int val )\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super();\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + tab + "setValue(val);\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param val       the value\n");
    s += (tab + tab + " * @param optional  <code>true</code> if the node the object represents is optional\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "( int val, boolean optional)\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super(optional);\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + tab + "setValue(val);\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param val        the value\n");
    s += (tab + tab + " * @param defaultval the default value\n");
    s += (tab + tab + " * @param optional   <code>true</code> if the node the object represents is optional\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "( String val, int defaultval, boolean optional)\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super(optional);\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + tab + "setValue(val);\n");
    s += (tab + tab + tab + "spot_setDefaultValue(defaultval);\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + node.elementName + "</code> object\n");
    s += (tab + tab + " * the <code>" + node.parentNode.elementName + "." + node.elementName
          + "</code> ENUMERATED object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param val        the value\n");
    s += (tab + tab + " * @param defaultval the default value\n");
    s += (tab + tab + " * @param optional   <code>true</code> if the node the object represents is optional\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "( String val, String defaultval, boolean optional)\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super(optional);\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + tab + "setValue(val);\n");
    s += (tab + tab + tab + "spot_setDefaultValue(defaultval);\n");
    s += (tab + tab + "}\n\n");

    // s += createCloneMethod(name, depth);
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Retrieves the range of valid values for the object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @return the valid range as a displayable string\n");
    s += (tab + tab + " */\n");
    s += (tab + tab + "public String spot_getValidityRange()\n");
    s += (tab + tab + "{\n");
    s += gv;
    s += (tab + tab + "}\n");
    s += ("\n" + tab + tab + "private void spot_setChoices()\n");
    s += (tab + tab + "{\n");
    s += cs;
    s += "\n";
    s += gs;
    s += (tab + tab + tab + "spot_setChoices(schoices,nchoices);\n");
    s += (tab + tab + "}\n");
    s += (tab + "}\n");

    return s;
  }

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   * @param choices DOCUMENT ME!
   * @param depth DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String createPSChoiceClass(String name, String[] choices, int depth) {
    char[] c = new char[depth * 2];

    Arrays.fill(c, ' ');

    String tab = new String(c);
    String s   = tab + "public static class " + name + " extends " + nameprefix + "PrintableString {\n\n";
    String gs  = tab + tab + tab + "String[] choices    = new String[" + choices.length + "];\n";

    for (int i = 0; i < choices.length; i++) {
      gs += (tab + tab + tab + "choices[" + i + "] = " + choices[i] + ";\n");
    }

    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "()\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super();\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param optional   <code>true</code> if the node the object represents is optional\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "(boolean optional)\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super(optional);\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + "}\n\n");
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param val        the value\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "( String val )\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + tab + "setValue( val );\n");
    s += (tab + tab + "}\n\n");

    /*
     * s += (tab + tab + "/**\n");
     *  s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
     *  s += (tab + tab + " * \n");
     *  s += (tab + tab + " * @param val        the value\n");
     *  s += (tab + tab + " * @param min        the object's minimum acceptable valu\n");
     *  s += (tab + tab + " * @param max        the object's maximum acceptable value\n");
     *  s += (tab + tab + " * @param defaultval the default value\n");
     *  s += (tab + tab + " * @param optional   <code>true</code> if the node the object represents is optional\n");
     *  s += (tab + tab + " /;
     *  s += ("\n" + tab + tab + "public " + name + "( String val, String min, String max, String defaultval, boolean optional)\n");
     *  s += (tab + tab + "{\n");
     *  s += (tab + tab + tab + "super();\n");
     *  s += (tab + tab + tab + "setChoices();\n");
     *  s += (tab + tab + tab + "setValues(val,min,max,optional);\n");
     *  s += (tab + tab + tab + "setDefaultValue(defaultval);\n");
     *  s += (tab + tab + "}\n\n");
     */
    s += (tab + tab + "/**\n");
    s += (tab + tab + " * Creates a new <code>" + name + "</code> object\n");
    s += (tab + tab + " * \n");
    s += (tab + tab + " * @param val        the value\n");
    s += (tab + tab + " * @param defaultval the default value\n");
    s += (tab + tab + " * @param optional   <code>true</code> if the node the object represents is optional\n");
    s += (tab + tab + " */");
    s += ("\n" + tab + tab + "public " + name + "( String val, String defaultval, boolean optional)\n");
    s += (tab + tab + "{\n");
    s += (tab + tab + tab + "super();\n");
    s += (tab + tab + tab + "spot_setChoices();\n");
    s += (tab + tab + tab + "setValues(val,null,null,optional);\n");
    s += (tab + tab + tab + "spot_setDefaultValue(defaultval);\n");
    s += (tab + tab + "}\n\n");

    // s += createCloneMethod(name, depth);
    s += ("\n" + tab + tab + "private void spot_setChoices()\n");
    s += (tab + tab + "{\n");
    s += gs;
    s += (tab + tab + tab + "spot_setChoices(choices);\n");
    s += (tab + tab + "}\n");
    s += (tab + "}\n");

    return s;
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param pw DOCUMENT ME!
   * @param createget DOCUMENT ME!
   */
  protected void createReferenceMethods(SPOTNode node, PrintWriter pw, HashMap refinits, boolean createget) {
    int      i = 0;
    SPOTNode x = null;
    int      n;
    String   type;
    String   arg = null;
    char[]   c   = new char[(node.theDepth + 1) * 2];

    Arrays.fill(c, ' ');

    String    tab = new String(c);
    String    name;
    int       len = node.size();
    ArrayList inits;
    int       plen;

    if (len == 0) {
      return;
    }

    for (i = 0; i < len; i++) {
      x = (SPOTNode) node.elementAt(i);

      if (!x.isReference) {
        continue;
      }

      type = x.elementType;

      if ((n = SPOTNode.findType(type)) != -1) {
        if (type.equals(SPOTNode.typeSequence)) {
          type = makeGeneratedName(x.elementName);
        } else if (type.equals(SPOTNode.typeExtends)) {
          type = x.extendsType;
        } else if (type.equals(SPOTNode.typeSet)) {
          arg = (x.userMap == null)
                ? null
                : (String) x.userMap.get("ARG");

          if (arg == null) {
            continue;
          }

          type = nameprefix + "Set";
        } else {
          continue;
        }
      }

      name = makeAccessName(x.elementName);

      if (createget) {
        pw.println(tab + "/**");
        pw.println(tab + " * Gets the reference to the " + x.elementName + " element");
        pw.println(tab + " * ");
        pw.println(tab + " * @return the reference to the " + x.elementName + " element");
        pw.println(tab + " */");
        pw.println(tab + "public " + type + " get" + name + "() { return " + x.elementName + "; }");
      }

      pw.println();
      pw.println(tab + "/**");
      pw.println(tab + " * Creates and sets the reference to the " + x.elementName + " element");
      pw.println(tab + " * ");
      pw.println(tab + " * @return the newly created element value");
      pw.println(tab + " */");
      pw.println(tab + "public " + type + " create" + name + "Reference() {");
      pw.println(tab + "  if ( " + x.elementName + " == null ) {");

      if (x.elementType.equals(SPOTNode.typeSet)) {
        pw.println(tab + "    " + x.elementName + " = new " + arg + ";");
      } else {
        pw.println(tab + "    " + x.elementName + " = new " + type + "(true);");
      }

      pw.println(tab + "    super.spot_setReference( \"" + x.elementName + "\" , " + x.elementName + ");");
      inits = (ArrayList) refinits.get(x.elementName);
      plen  = (inits == null)
              ? 0
              : inits.size();

      for (int p = 0; p < plen; p++) {
        pw.println(tab + "    " + (String) inits.get(p));
      }

      pw.println(tab + "  }");
      pw.println(tab + "  return " + x.elementName + ";");
      pw.println(tab + "}");
      pw.println();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param x DOCUMENT ME!
   * @param name DOCUMENT ME!
   * @param ex DOCUMENT ME!
   * @param access DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String createSequenceClass(SPOTNode x, String name, String ex, String access)
          throws java.text.ParseException {
    String                s       = "public static class " + name + " extends " + nameprefix + ex + " {\n";
    int                   n       = 0;
    ByteArrayOutputStream stream  = new ByteArrayOutputStream(1024);
    PrintWriter           pw      = new PrintWriter(stream);
    ArrayList             classes = new ArrayList();
    ArrayList             method;
    HashMap               methods  = new HashMap();
    HashMap               refinits = new HashMap();

    methods.put(initializeMethod, new ArrayList());
    print(pw, x.theDepth, "/**\n");
    print(pw, x.theDepth,
          " * Class that represents the <code>" + x.parentNode.elementName + "." + x.elementName + "</code> object\n");
    print(pw, x.theDepth, " */\n");
    println(pw, x.theDepth, s);

    int depth = x.theDepth + 1;

    toJavaParent(x, pw, classes, methods, refinits, "public");

    if (!access.equals("public")) {
      createAccessMethods(x, pw);
    }

    // pw.print("\n");
    print(pw, depth, "/**\n");
    print(pw, depth, " * Creates a new optional <code>" + name + "</code> object.\n");
    print(pw, depth, " */\n");
    print(pw, depth, "public " + name + "() {\n");
    print(pw, depth + depth, "this( true );\n");
    print(pw, depth, "}\n\n");
    print(pw, depth, "/**\n");
    print(pw, depth, " * Creates a new <code>" + name + "</code> object.\n");
    print(pw, depth, " *\n");
    print(pw, depth, " * @param optional  <code>true</code> if the node is optional; <code>false</code> otherwise)\n");
    print(pw, depth, " */\n");
    print(pw, depth, "public " + name + "( boolean optional ) {\n");
    print(pw, depth + depth, "super( optional, false );\n");
    print(pw, depth + depth, "spot_setElements();\n");
    print(pw, depth, "}\n\n");
    print(pw, depth, "/**\n");
    print(pw, depth, " * Adds elements to the object elements map\n");
    print(pw, depth, " */\n");
    print(pw, depth, "protected void spot_setElements() {\n");
    print(pw, depth + 1, "super.spot_setElements();\n");

    if (x.theAttributes != null) {
      Iterator  it = x.theAttributes.entrySet().iterator();
      Map.Entry me;
      String    sn;
      String    v;

      while(it.hasNext()) {
        me = (Map.Entry) it.next();
        sn = (String) me.getKey();
        v  = (String) me.getValue();

        if (v != null) {
          v = "\",\"" + v + "\");";
        } else {
          v = "\",null);";
        }

        println(pw, depth + 1, "spot_defineAttribute(\"" + sn + v);
      }
    }

    method = (ArrayList) methods.get(initializeMethod);
    n      = method.size();

    for (int p = 0; p < n; p++) {
      s = (String) method.get(p);
      pw.print(s);
    }

    print(pw, x.theDepth + 1, "}\n");
    print(pw, x.theDepth, "}\n\n");
    pw.flush();

    return stream.toString();
  }

  /**
   * DOCUMENT ME!
   *
   * @param file DOCUMENT ME!
   */
  protected void readExisting(String file) {
    FileReader f     = null;
    String     line  = null;
    String     oline = null;
    int        state = -1;
    int        n     = 0;

    userimportandcom = null;
    premembers       = null;
    precon           = null;
    preinner         = null;
    preend           = null;
    comments.clear();

    String  comment        = null;
    boolean mainclassfound = false;
    boolean membersfound   = false;

    try {
      f = new FileReader(file);
    } catch(Exception e) {
      return;
    }

    BufferedReader in = new BufferedReader(f, readBufferLen);

    do {
      if ((oline = readLine(in)) == null) {
        break;
      }

      line = oline.trim();

      if (line.equalsIgnoreCase(usermark)) {
        state = 0;

        continue;
      } else if (line.startsWith("public class") &&!mainclassfound) {
        mainclassfound = true;
        state          = 1;    // pre members

        continue;
      } else if (line.equalsIgnoreCase(genbegin + genmembers + "{")) {
        state        = -2;
        membersfound = true;

        continue;
      } else if (line.equalsIgnoreCase(genend + genmembers)) {
        state = 2;             // pre con

        continue;
      } else if (line.equalsIgnoreCase(genbegin + gencon + "{")) {
        state = -1;

        continue;
      } else if (line.equalsIgnoreCase(genend + gencon)) {
        state = 3;             // pre inner

        continue;
      } else if (line.equalsIgnoreCase(genbegin + geninner + "{")) {
        state = -1;

        continue;
      } else if (line.equalsIgnoreCase(genend + geninner)) {
        state = 4;             // pre end

        continue;
      }

      switch(state) {
        case 0 :
          if (userimportandcom == null) {
            userimportandcom = oline;
          } else {
            userimportandcom += ("\n" + oline);
          }

          break;

        case 1 :
          if (premembers == null) {
            premembers = oline;
          } else {
            premembers += ("\n" + oline);
          }

          break;

        case 2 :
          if (precon == null) {
            precon = oline;
          } else {
            precon += ("\n" + oline);
          }

          break;

        case 3 :
          if (preinner == null) {
            preinner = oline;
          } else {
            preinner += ("\n" + oline);
          }

          break;

        case 4 :
          if (preend == null) {
            preend = oline;
          } else {
            preend += ("\n" + oline);
          }

          break;

        case -2 :
          if (line.startsWith("/*")) {
            comment = oline;

            while(true) {
              if (comment.endsWith("*/")) {
                line = readLine(in);

                break;
              }

              line = readLine(in);

              if (line == null) {
                break;
              }

              comment += ("\n" + line);
            }
          } else if (line.startsWith("//")) {
            comment = oline;

            while(true) {
              line = readLine(in);

              if (line == null) {
                break;
              }

              if (!line.startsWith("//")) {
                break;
              }

              comment += ("\n" + line);
            }
          }

          if ((comment != null) && (line != null) && ((n = line.indexOf('=')) != -1)) {
            line = line.substring(0, n);
            line = line.trim();

            if ((n = line.indexOf(' ')) == -1) {
              break;
            }

            line = line.substring(n + 1);
            line = line.trim();

            if ((n = line.indexOf(' ')) == -1) {
              break;
            }

            line = line.substring(n + 1);
            line = line.trim();

            if (line.length() == 0) {
              break;
            }

            comments.put(line, comment);
            comment = null;
          }

          break;

        default :
          break;
      }
    } while(true);

    if ((state != -1) && (preend == null)) {
      preend = "";
    }

    if (!membersfound && (premembers != null)) {
      premembers = null;
    }
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
   * @param node DOCUMENT ME!
   * @param pw DOCUMENT ME!
   * @param classes DOCUMENT ME!
   * @param methods DOCUMENT ME!
   * @param access DOCUMENT ME!
   *
   * @throws FormatException DOCUMENT ME!
   */
  protected void toJavaEx(SPOTNode node, PrintWriter pw, ArrayList classes, HashMap methods, HashMap refinits,
                          String access)
          throws java.text.ParseException {
    int      nt      = 0;
    int      p       = 0;
    String   comment = null;
    SPOTNode x       = null;
    String   s;
    String   min      = null;
    String   max      = null;
    String   type     = null;
    String   rtype    = null;
    String   value    = null;
    String   defvalue = null;

    access += " ";

    ArrayList init = (ArrayList) methods.get(initializeMethod);

    if (node.theRange != null) {
      p = node.theRange.indexOf("..");

      if (p != -1) {
        min = node.theRange.substring(0, p);
        max = node.theRange.substring(p + 2);

        if (max.length() == 0) {
          max = null;
        }

        if (min.length() == 0) {
          min = null;
        }
      } else {
        min = node.theRange;
        max = node.theRange;
      }
    }

    if ((node.fixedValue != null)) {
      value = node.fixedValue;

      if (!com.appnativa.util.Helper.isNumeric(value, true, true)) {
        if (!value.startsWith("\"")) {
          value = "\"" + value + "\"";
        }
      }
    } else {
      value = "null";
    }

    if (node.defaultValue != null) {
      defvalue = node.defaultValue;
    }

    if (node.elementType.equalsIgnoreCase("REFINE")) {
      x = getRefinedElement(node);

      if ((x != null) && (x.elementType.equals("ENUMERATED") || x.elementType.equals(SPOTNode.typePrintableString))) {
        if (node.defaultValue != null) {
          if (x.theNumChoices != null) {
            if (!x.checkEnumDefault(node.defaultValue)) {
              throw new FormatException("The DEFAULT value of '%s' for node %s is not a valid choice",
                                        node.defaultValue, x.elementName);
            }
          } else {
            if (!x.checkChoicesDefault(node.defaultValue)) {
              throw new FormatException("The DEFAULT value of '%s' for node %s is not a valid choice",
                                        node.defaultValue, x.elementName);
            }
          }
        }

        if (node.fixedValue != null) {
          if (x.theNumChoices != null) {
            if (!x.checkEnumDefault(node.fixedValue)) {
              throw new FormatException("The VALUE value of '%s' for node %s is not a valid choice", node.fixedValue,
                                        x.elementName);
            }
          } else {
            if (!x.checkChoicesDefault(node.fixedValue)) {
              throw new FormatException("The VALUE value of '%s' for node %s is not a valid choice", node.fixedValue,
                                        x.elementName);
            }
          }
        }

        rtype = makeGeneratedName(node.elementName);
      }

      if (node.fixedValue != null) {
        if (value.startsWith("\"") && (rtype != null)) {
          value = rtype + "." + node.fixedValue;
        }

        init.add(print(node.theDepth + 1, node.elementName + ".setValue( " + value + " );\n"));
      }

      if (node.defaultValue != null) {
        if (!com.appnativa.util.Helper.isNumeric(defvalue, true, true)) {
          if ((defvalue.indexOf('.') == -1) &&!defvalue.startsWith("\"")) {
            if (rtype != null) {
              defvalue = rtype + "." + defvalue;
            } else {
              defvalue = "\"" + defvalue + "\"";
            }
          }
        }

        init.add(print(node.theDepth + 1, node.elementName + ".spot_setDefaultValue( " + defvalue + " );\n"));
      }

      init.add(print(node.theDepth + 1, node.elementName + ".spot_setOptional(  " + (node.isOptional
              ? "true"
              : "false") + "  );\n"));

      if (node.theRange != null) {
        init.add(print(node.theDepth + 1, node.elementName + ".spot_setRange( " + min + ", " + max + " );\n"));
      }

      if (node.theChoices != null) {
        init.add(print(node.theDepth + 1, "super.theChoices=new String[" + node.theChoices.length + "];"));
        init.add(createChoicesInit("super", node.theChoices, node.theDepth));
      }

      if (node.theAttributes != null) {
        Iterator  it = node.theAttributes.entrySet().iterator();
        Map.Entry me;
        String    n;
        String    v;

        while(it.hasNext()) {
          me = (Map.Entry) it.next();
          n  = (String) me.getKey();
          v  = (String) me.getValue();

          if (v != null) {
            v = "\",\"" + v + "\");";
          } else {
            v = "\",null);";
          }

          init.add(println(node.theDepth + 1, "spot_defineAttribute(\"" + n + v));
        }
      }

      return;
    }

    nt = SPOTNode.findType(node.elementType);

    if (nt != -1) {
      type = nameprefix + theTypes[nt];
    } else {
      type = node.elementType;
    }

    if (node.theChoices != null) {
      if (node.elementType.equalsIgnoreCase("ENUMERATED")) {
        type = makeGeneratedName(node.elementName);

        if ((defvalue != null) &&!com.appnativa.util.Helper.isNumeric(defvalue, true, true)) {
          defvalue = type + "." + defvalue;
        }

        classes.add(createEnumeratedClass(node, type));
      } else {
        type = makeGeneratedName(node.elementName);
        classes.add(createPSChoiceClass(type, node.theChoices, node.theDepth));
      }
    }

    comment = (String) comments.get(node.elementName);

    if ((node.theComment != null) && (comment != null)) {
      if (comment.indexOf(lasncomment) != -1) {
        comment = null;
      }
    }

    if ((comment == null) && (node.theComment != null)) {
      pw.println(lasncomment);
      comment = print(node.theDepth, "/** " + cleanWhiteSpace(node.theComment) + " */ ");
    }

    if (comment != null) {
      pw.println(comment);
    }

    if (node.isContainer()) {
      if (node.elementType.equalsIgnoreCase("SET")) {
        x = (SPOTNode) node.childNodes.get(0);
        s = makeSetArg(x, classes, init, access);

        if (node.isReference &&!node.isRefine) {
          print(pw, node.theDepth, "protected " + nameprefix + "Set " + node.elementName + " = null;");

          if (node.userMap == null) {
            node.userMap = new HashMap();
          }

          if ((min == null) || (max == null)) {    // if ether is null then we need string paramaters
            min = (min == null)
                  ? "-1"
                  : min;
            max = (max == null)
                  ? "-1"
                  : max;
          }

          StringBuilder sb = new StringBuilder();

          sb.append(nameprefix);
          sb.append("Set( \"");
          sb.append(x.elementName);
          sb.append("\", ");
          sb.append(s);
          sb.append(", ");
          sb.append(min);
          sb.append(", ");
          sb.append(max);

          if (node.isOptional) {
            sb.append(", true");
          } else {
            sb.append(", false ");
          }

          sb.append(");");

          // node.userMap.put("ARG",nameprefix + "Set( \"" + x.elementName + "\", "+s +", null, null, true)");
          node.userMap.put("ARG", sb.toString());
        } else {
          print(pw, node.theDepth,
                access + nameprefix + "Set " + node.elementName + " = new " + nameprefix + "Set( \"" + x.elementName
                + "\", ");
          pw.print(s);

          if ((min == null) || (max == null)) {    // if ether is null then we need string paramaters
            min = (min == null)
                  ? "-1"
                  : min;
            max = (max == null)
                  ? "-1"
                  : max;

            // min   = (min == null) ? "null" : ("\"" + min + "\"");
            // max   = (max == null) ? "null" : ("\"" + max + "\"");
          }

          if ((min != null) &&!com.appnativa.util.Helper.isNumeric(min, true, true)) {
            min = ("\"" + min + "\"");
          }

          if ((max != null) &&!com.appnativa.util.Helper.isNumeric(max, true, true)) {
            max = ("\"" + max + "\"");
          }

          pw.print(", ");
          pw.print(min);
          pw.print(", ");
          pw.print(max);

          if (node.isOptional) {
            pw.print(", true");
          } else {
            pw.print(", false ");
          }

          pw.print(");");
        }
      } else {
        type = makeGeneratedName(node.elementName);    // "C" + node.theElementName.substring(0, 1).toUpperCase() + node.theElementName.substring(1);

        if (node.elementType.equalsIgnoreCase("EXTENDS")) {
          s = node.extendsType;
        } else {
          s = "Sequence";
        }

        classes.add(createSequenceClass(node, type, s, access));

        if (node.isReference) {
          print(pw, node.theDepth, "protected " + type + " " + node.elementName + " = null;");
        } else {
          print(pw, node.theDepth, access + type + " " + node.elementName + " = new " + type + "(");

          if (node.isOptional) {
            pw.print(" true ");
          }

          pw.print(");");
        }
      }
    } else {
      if (node.elementType.equalsIgnoreCase("ANY")) {
        print(pw, node.theDepth, access + type + " " + node.elementName + " = new " + type + "(");

        if ((node.definedBy != null) && (node.definedBy.length() != 0)) {
          pw.print(" \"");

          if (node.definedBy.indexOf('.') == -1) {
            if ((userpkg != null) && (userpkg.length() > 0)) {
              pw.print(userpkg);
              pw.print('.');
            }
          }

          pw.print(node.definedBy);
          pw.print("\"");

          if (node.isOptional) {
            pw.print(", true");
          }
        } else {
          if (node.isOptional) {
            pw.print("true");
          }
        }

        pw.print(");");
      } else if (node.theRange == null) {        // no RANGE parameter
        if (node.isReference && (nt == -1)) {    // reference to a predefined class
          print(pw, node.theDepth, "protected " + type + " " + node.elementName + " = null;");
        } else {
          print(pw, node.theDepth, access + type + " " + node.elementName + " = new " + type + "(");

          if ((node.fixedValue != null) || (defvalue != null)) {
            pw.print(value);
          }

          if (defvalue != null) {
            pw.print(", " + defvalue);
            pw.print(node.isOptional
                     ? ", true"
                     : ", false ");
          } else if (node.isOptional) {
            pw.print((node.fixedValue != null)
                     ? ", true "
                     : "true");
          }

          pw.print(");");
        }
      } else {
        min = (min == null)
              ? "null"
              : ("\"" + min + "\"");
        max = (max == null)
              ? "null"
              : ("\"" + max + "\"");
        print(pw, node.theDepth, access + type + " " + node.elementName + " = new " + type + "(");
        pw.print(value);
        pw.print(", " + min + ", " + max);

        if (defvalue != null) {
          pw.print(", " + node.defaultValue);
        }

        if (node.isOptional) {
          pw.print(", true");
        } else {
          pw.print(", false");
        }

        pw.print(");");
      }
    }

    s = print(node.theDepth + 1, "spot_addElement( \"" + node.elementName + "\", " + node.elementName + " );\n");
    init.add(s);

    if (node.theAttributes != null) {
      Iterator  it = node.theAttributes.entrySet().iterator();
      Map.Entry me;
      String    n;
      String    v;

      while(it.hasNext()) {
        me = (Map.Entry) it.next();
        n  = (String) me.getKey();
        v  = (String) me.getValue();

        if (v != null) {
          v = "\",\"" + v + "\");";
        } else {
          v = "\",null);";
        }

        if (node.isReference) {
          ArrayList rinit = (ArrayList) refinits.get(node.elementName);

          if (rinit == null) {
            rinit = new ArrayList();
            refinits.put(node.elementName, rinit);
            rinit.add(println(0, node.elementName + ".spot_defineAttribute(\"" + n + v));
          }
        } else {
          init.add(println(node.theDepth + 1, node.elementName + ".spot_defineAttribute(\"" + n + v));
        }
      }
    }

    pw.println();
  }

  /**
   * DOCUMENT ME!
   *
   * @param node DOCUMENT ME!
   * @param pw DOCUMENT ME!
   * @param classes DOCUMENT ME!
   * @param methods DOCUMENT ME!
   * @param access DOCUMENT ME!
   */
  protected void toJavaParent(SPOTNode node, PrintWriter pw, ArrayList classes, HashMap methods, HashMap refinits,
                              String access)
          throws java.text.ParseException {
    int        i        = 0;
    ObjectList elements = node.childNodes;
    SPOTNode   x        = null;
    int        len      = (elements == null)
                          ? 0
                          : elements.size();

    if (len == 0) {
      return;
    }

    for (i = 0; i < (len - 1); i++) {
      x          = (SPOTNode) elements.get(i);
      x.theDepth = node.theDepth + 1;
      toJavaEx(x, pw, classes, methods, refinits, access);
      pw.println();
    }

    x          = (SPOTNode) elements.get(i);
    x.theDepth = node.theDepth + 1;
    toJavaEx(x, pw, classes, methods, refinits, access);
    pw.println();
  }

  /**
   * DOCUMENT ME!
   *
   * @param in DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected String toString(Reader in) throws java.text.ParseException {
    SPOTNode x = new SPOTNode(in);

    return x.toString();
  }
}
