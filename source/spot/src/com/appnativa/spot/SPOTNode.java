/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.spot;

import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.IntList;
import com.appnativa.util.SNumber;

import com.google.j2objc.annotations.Weak;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class represents a node in a SPOT definition.
 * It can parse text-based SPOT definitions an create node objects represent thode definitions
 *
 * @author Don DeCoteau
 * @version   2.0
 */
public class SPOTNode implements iSPOTConstants {

  /** SPOT Value keyword */
  public static final String keywordDefault = "Default";

  /** SPOT "DefinedBy" keyword */
  public static final String keywordDefinedBy = "DefinedBy";

  /** SPOT "Optional" keyword */
  public static final String keywordManditory = "Manditory";

  /** SPOT "Optional" keyword */
  public static final String keywordOptional = "Optional";

  /** SPOT "Range" keyword */
  public static final String keywordRange = "Range";

  /** SPOT "Reference" keyword */
  public static final String keywordReference = "Reference";

  /** SPOT "Value" keyword */
  public static final String keyworkValue = "Value";

  /** OS line separator */
  public static final String lineSeparator = "\n";

  /** an array SPOT element types */
  public static final String[] theTypes = {
    "PrintableString", "OctetString", "Set", "Sequence", "Any", "UTCTime", "Date", "Time", "Integer", "Real",
    "Enumerated", "Extends", "DateTime", "Boolean", "ByteString", "Refine"
  };

  /** represents a value of any defined type */
  public static final String typeAny = "Any";

  /** represents an element of any defined type */
  public static final String typeBoolean = "Boolean";

  /** represents an element of any defined type */
  public static final String typeByteString = "ByteString";

  /** represents a date value */
  public static final String typeDate = "Date";

  /** represents a date and time value */
  public static final String typeDateTime = "DateTime";

  /** A simple type whose values represents a fixed named set of integer values */
  public static final String typeEnum = "Enumerated";

  /** identifies a that a preexisting definition is beining extended */
  public static final String typeExtends = "Extends";

  /** represents a value with distinguished values of positive and negative whole numbers, including zero */
  public static final String typeInteger = "Integer";

  /** represents a simple type whose distinguished values are an ordered sequence of zero, one or more octets, each octet being an ordered sequence of eight b */
  public static final String typeOctetString = "OctetString";

  /** represents a simple type with distinguished values are an ordered sequence of zero, one or more characters consisting of ASCII 10, 13, and ASCII values ranging from 32 to ASCII 126 */
  public static final String typePrintableString = "PrintableString";

  /** represents a value whose distinguished values are members of the set of real numbers. */
  public static final String typeReal = "Real";

  /** identifies a that a preexisting definition is beining refined */
  public static final String typeRefine = "Refine";

  /** represents an ordered collection of one or more types, some of which can be optional */
  public static final String typeSequence = "Sequence";

  /** represents an unordered collection of zero or more occurrences of a given type */
  public static final String typeSet = "Set";

  /** represents a time value. */
  public static final String typeTime             = "Time";
  static final String        errAlreadyDefined    = "The element [%s] has been previously defined";
  static final String        errInvalid           = "Invalid SPOT document";
  static final String        errInvalidName       = "The [%s] is an invalid element name/type";
  static final String        errInvalidSize       = "The format of the 'Range' modifier is invalid for [%s]";
  static final String        errMissingBy         = "The 'BY' is required after 'DEFINED' for [%s]";
  static final String        errMissingCurlyEnd   = "The closing '}' for [%s] is missing";
  static final String        errMissingCurlyStart = "The opening '{' for [%s] is missing";
  static final String        errMissingType       = "The type being extended/refined is missing for [%s]";
  static final String        errMissingValue      = "The value for the [%s] attriobute is missing";
  static final String        errWrongEndTag       = "The tag </%s> was found when the tag </%s> was expected";
  static final char[]        padding              = new char[256];
  static final int           readBufferLen        = 1024;
  static String              spanCode             = "code";
  static String              spanComment          = "comment";
  static String              spanDescription      = "description";
  static String              spanType             = "keywordtype";
  static String              spanUserType         = "keyword";
  static final char[]        quoteChar            = "\"".toCharArray();
  static List                lcTypes              = new ArrayList();

  /** SPOT types */
  static final char[] colonColonEq = "::=".toCharArray();
  static final char[] badChars     = ",".toCharArray();

  static {
    for (int i = 0; i < theTypes.length; i++) {
      lcTypes.add(theTypes[i].toLowerCase());
    }

    int len = padding.length;

    for (int i = 0; i < len; i++) {
      padding[i] = ' ';
    }
    /*
     * lineSeparator = (String) java.security.AccessController.doPrivileged(new sun.security.action.
     *   GetPropertyAction("line.separator"));
     */
  }

  /** <code>true</code> if the element is optional <code>false</code> otherwise */
  public boolean isOptional = false;

  /** <code>true</code> if the element is being refined <code>false</code> otherwise */
  public boolean isRefine = false;

  /** <code>true</code> if the element is desgnated a reference <code>false</code> otherwise */
  public boolean isReference = false;

  /** <code>true</code> if the element is desgnated as read-only <code>false</code> otherwise */
  public boolean isReadOnly = false;

  /** <code>true</code> if the element is desgnated as protected <code>false</code> otherwise */
  public boolean isProtected = false;

  /** element's depth */
  public int theDepth = 0;

  /** map for external user's data */
  public HashMap userMap = null;

  /** list of sub nodes */
  public List<SPOTNode> childNodes;

  /** elements default value */
  public String defaultValue;

  /** DEFINED BY value */
  public String definedBy;

  /** elements name */
  public String elementName;

  /** elements type */
  public String elementType;

  /** elements type as an int */
  public int elementTypeAsInt;

  /** the EXTENDS type */
  public String extendsType;

  /** elements fixed value */
  public String fixedValue;

  /** parent node */
  @Weak
  public SPOTNode parentNode;

  /** object attributes */
  public LinkedHashMap theAttributes;

  /** elements string choices */
  public String[] theChoices;

  /** elements string choices */
  public String[] theChoicesComments;

  /** elements comment */
  public String theComment;

  /** elements comment */
  public String theDescription;

  /** elements numeric choices */
  public int[] theNumChoices;

  /** elements range */
  public String        theRange;

  /** linked data*/
  public Object linkedData;
  int                  writeXpos = 0;
  List                 choiceList;
  List                 commentList;
  boolean              manditoryModifier;
  CharArray            modLine;
  List                 modList;
  CharScanner          modScanner;
  HashMap              nameMap;
  IntList              numList;
  SPOTNode             rootNode;
  MyReader             theReader;
  private final String treePrefix =
    "<p><img src=\"ftv2vertline.png\" alt=\"|\" width=16 height=22 /><img src=\"ftv2node.png\" alt=\"o\" width=16 height=22 /><img src=\"ftv2doc.png\" alt=\"*\" width=24 height=22 /><a class=\"el\" href=\"";

  /**
   * Creates a new <b>SPOTNode</b> object
   */
  public SPOTNode() {}

  /**
   * Creates a new <b>SPOTNode</b> object
   *
   * @param in A <b>Reader</b> object containing the XML data
   *
   * @throws SPOTException if the format of the XML is invalid.
   */
  public SPOTNode(Reader in) throws SPOTException {
    read(in);
  }

  /**
   * Creates a new <b>SPOTNode</b> object
   *
   * @param name The TAG name for the element
   */
  public SPOTNode(String name) {
    elementName = name;
  }

  /**
   * Creates a new <b>SPOTNode</b> object
   *
   * @param name The TAG name for the element
   * @param type The type of the element
   */
  public SPOTNode(String name, String type) {
    elementName = name;
    elementType = type;

    if (type != null) {
      elementTypeAsInt = typeToInt(elementType);
    }
  }

  /**
   * Adds a new <b>SPOTNode</b>  to this container.
   * The element muls have a valid name.
   *
   * @param element The <b>SPOTNode</b> to add
   */
  public void addElement(SPOTNode element) {
    if (childNodes == null) {
      childNodes = new ArrayList(50);
    }

    if (nameMap == null) {
      nameMap = new HashMap();
    }

    if (nameMap.put(element.elementName, element) != null) {
      throw new SPOTException(errAlreadyDefined, element.elementName);
    }

    element.rootNode = rootNode;
    element.theDepth = theDepth + 1;
    childNodes.add(element);
  }

  /**
   * Adds a new <b>SPOTNode</b> to this container
   *
   * @param name The TAG name for the element to add
   *
   * @return the newly added element
   */
  public SPOTNode addElement(String name) {
    SPOTNode element = new SPOTNode(name);

    element.theDepth = theDepth + 1;
    addElement(element);

    return element;
  }

  /**
   * Adds a new <b>SPOTNode</b>  to this container
   *
   * @param name The TAG name for the element to add
   * @param type The type of the element
   *
   * @return the newly added element
   */
  public SPOTNode addElement(String name, String type) {
    SPOTNode element = new SPOTNode(name, type);

    element.theDepth = theDepth + 1;
    addElement(element);

    return element;
  }

  /**
   * DOCUMENT ME!
   *
   * @param val DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean checkChoicesDefault(String val) {
    for (int i = 0; i < theChoices.length; i++) {
      if (theChoices[i].equals(val)) {
        return true;
      }
    }

    return false;
  }

  /**
   * DOCUMENT ME!
   *
   * @param val DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean checkEnumDefault(String val) {
    SNumber num = new SNumber();

    if (!num.setValueEx(val, true, false)) {
      return checkChoicesDefault(val);
    }

    if (!num.isInteger()) {
      return false;
    }

    for (int i = 0; i < theChoices.length; i++) {
      if (theNumChoices[i] == num.intValue()) {
        return true;
      }
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void dumpSyntaxEx(Map identifiers, Map enums, Map attributes) {
    SPOTNode x;
    String   name;
    SPOTNode subtype;
    int      len = (childNodes == null)
                   ? 0
                   : childNodes.size();

    for (int i = 0; i < len; i++) {
      x    = childNodes.get(i);
      name = x.elementName;

      if ((name != null) && (name.length() > 0)) {
        identifiers.put(name, null);
        subtype = x.rootNode.elementFor(x.elementType);

        if (subtype != null) {
          dumpPathNames(identifiers, name, subtype);
        }
      }

      if (x.hasAttributes()) {
        attributes.putAll(x.theAttributes);
      }

      if (x.isContainer()) {
        x.dumpSyntaxEx(identifiers, enums, attributes);
      } else {
        if (typeEnum.equalsIgnoreCase(x.elementType)) {
          for (int n = 0; n < x.theChoices.length; n++) {
            enums.put(x.theChoices[n], null);
          }
        }
      }
    }
  }

  /**
   * Retrieves the element at the specified position  contained within this
   * container
   *
   * @param pos the position of the element to retrieve
   *
   * @return The element
   */
  public SPOTNode elementAt(int pos) {
    if ((childNodes == null) || (pos < 0) || (pos > childNodes.size())) {
      return null;
    }

    return childNodes.get(pos);
  }

  /**
   * Retrieves the element with the specified name
   *
   * @param name the name of the element
   *
   * @return The element
   */
  public SPOTNode elementFor(String name) {
    if ((nameMap == null) || (name == null)) {
      return null;
    }

    return (SPOTNode) nameMap.get(name);
  }

  /**
   * Returns the position within the type array of the specified type
   * @param type the string representing the type
   * @return the position within the type array of the specified type
   */
  public static int findType(String type) {
    return (type == null)
           ? -1
           : lcTypes.indexOf(type.toLowerCase());
  }

  /**
   * {@inheritDoc}
   */
  public void fixReferences() {
    SPOTNode x;

    if (isContainer()) {
      for (int i = 0; i < childNodes.size(); i++) {
        x = childNodes.get(i);

        if (!x.isReference) {
          x.isReference = isAutoReference(x);
        } else {
          SPOTNode xx = x.parentNode;

          if ((xx != null) && (xx.elementType != null) && xx.elementType.equalsIgnoreCase(typeSet)) {
            x.isReference = false;
          }
        }

        if (x.isContainer()) {
          x.fixReferences();
        }
      }
    }
  }

  /**
   * Retrieves the position of and element with the specified name starting
   * from the top of the tree.
   *
   * @param name the name of the element
   *
   * @return The element's position of -1 on failure
   */
  public int indexOf(String name) {
    return indexOf(name, 0);
  }

  /**
   * Retrieves the position of and element with the specified name starting
   * from the specified position
   *
   * @param name the name of the element
   * @param start the position frrom which to begin the search
   *
   * @return The element's position of -1 on failure
   */
  public int indexOf(String name, int start) {
    int pos = -1;

    if (childNodes != null) {
      long     len = childNodes.size();
      SPOTNode x   = null;

      if (start < 0) {
        start = 0;
      }

      for (int i = start; i < len; i++) {
        x = childNodes.get(i);

        if (x.elementName.equalsIgnoreCase(name)) {
          pos = i;

          break;
        }
      }
    }

    return pos;
  }

  /**
   * Left jsutify a string
   *
   * @param width the widht at which to word wrap
   * @param s the string
   * @return the justified and wrapped string
   */
  public static String leftJustify(int width, String s) {
    StringBuilder buf       = new StringBuilder(s);
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

  /**
   * Command line entry point for parsing a file and dumping the parsed elements to
   * standard output.
   *
   * @param args the arguments. The only allowed argument is the name of the file to parse
   */
  public static void main(String[] args) {
    boolean html   = false;
    boolean syntax = false;
    boolean index  = false;

    if (args.length < 1) {
      args    = new String[1];
      args[0] = "example.spot";
      // args[1]   = "1";
      // throw new SPOTException("Missing file name");
    }

    if (args.length > 1) {
      html   = "-html".equalsIgnoreCase(args[1]);
      syntax = "-syntax".equalsIgnoreCase(args[1]);
      index  = "-htmlindex".equalsIgnoreCase(args[1]);
    }

    String title = "SPOT Document";

    if (args.length > 2) {
      title = args[2];
    }

    SPOTNode x = null;

    try {
      java.io.FileReader         in  = new java.io.FileReader(args[0]);
      java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(System.out);

      x = new SPOTNode();
      x.read(in);

      if (syntax) {
        x.dumpSyntax();
      } else if (index) {
        String file = (args.length > 2)
                      ? args[2]
                      : "";

        title = (args.length > 3)
                ? args[3]
                : null;

        if (title == null) {
          x.dumpIndexTree(file);
        } else {
          x.dumpIndex(file, title);
        }
      } else {
        x.toString(out, html, title);
      }

      out.flush();
      System.exit(0);
    } catch(Exception ex) {
      try {
        if (x != null) {
          System.err.println("Parsed so far:");
          System.err.println(x.toString());
        }
      } catch(Exception ex1) {
        ex1.printStackTrace();
      }

      ex.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Sends a newline sequencs to the specified writer
   * @param out the writer
   * @throws IOException
   */
  public static void newLine(Writer out) throws IOException {
    out.write(lineSeparator);
  }

  /**
   * Reads and parses an input stream and populates the element with the
   * contents of the first complete XML document within the stream
   *
   * @param in the input stream
   *
   * @throws SPOTException if the format of the XML is invalid.
   */
  public void read(Reader in) throws SPOTException {
    rootNode = this;
    readEx(in);
    fixReferences();
  }

  /**
   * Gets the number of sub elements within this container
   *
   * @return The number of elements
   */
  public int size() {
    return (childNodes == null)
           ? 0
           : childNodes.size();
  }

  /**
   * Returns a string representation of the element.
   *
   * @return a strng representation of the element
   */
  public String toString() {
    StringWriter out = new StringWriter();

    try {
      toString(out, false, "SPOT Document");
      out.flush();
    } catch(IOException ex) {}

    return out.toString();
  }

  /**
   * Returns a string representation of the element.
   *
   * @param out the writer to use
   * @param html whether the outputted string should be HTML
   * @param title the title to use if HTML is being outputted
   *
   * @throws IOException if an I/O error occurs
   */
  public void toString(Writer out, boolean html, String title) throws IOException {
    SPOTNode x;

    if (html) {
      write(out, html, "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=iso-8859-1\"/>");
      write(out, html, lineSeparator);
      write(out, html, "<title>" + title + "</title>");
      write(out, html, lineSeparator);
      write(out, html, "<link href=\"doxygen.css\" rel=\"stylesheet\" type=\"text/css\"/>");
      write(out, html, lineSeparator);
      write(out, html, "</head><body>");
      write(out, html, lineSeparator);
      write(out, html, "<div class=\"maindox\">");
      write(out, html, lineSeparator);
    }

    try {
      if (isContainer()) {
        for (int i = 0; i < childNodes.size(); i++) {
          x = childNodes.get(i);
          writeHeading(out, x.elementName, html);

          if (x.theDescription != null) {
            writeDescription(out, x.theDescription, html);
            write(out, html, lineSeparator);
          }

          if (html) {
            write(out, html, "<pre>");
          }

          x.toStringEx(out, html);
          writeXpos = x.writeXpos;
          writeComment(out, x.theComment, html);
          write(out, html, lineSeparator);

          if (html) {
            write(out, html, "</pre>");
          }

          write(out, html, lineSeparator);
        }
      } else {
        if (theDescription != null) {
          writeDescription(out, theDescription, html);

          if (html) {
            write(out, html, "<pre>");
          }

          write(out, html, lineSeparator);
          toStringEx(out, html);

          if (html) {
            write(out, html, lineSeparator);
            write(out, html, "</pre>");
          }
        }
      }

      if (html) {
        write(out, html, lineSeparator);
        write(out, html, "</div>");
        write(out, html, lineSeparator);
        write(out, html, "</body>");
        write(out, html, lineSeparator);
        write(out, html, "</html>");
      }
    } catch(Exception ex) {}
  }

  /**
   * Converts the string name of an element to its integer value
   * @param type the type's string name
   * @return  the type's integer value. A non-standard type name returns SPOT_TYPE_USERCLASS
   */
  public static int typeToInt(String type) {
    if (type.equalsIgnoreCase("PrintableString")) {
      return SPOT_TYPE_PRINTABLESTRING;
    }

    if (type.equalsIgnoreCase("OctetString")) {
      return SPOT_TYPE_OCTETSTRING;
    }

    if (type.equalsIgnoreCase("Set")) {
      return SPOT_TYPE_SET;
    }

    if (type.equalsIgnoreCase("Sequence")) {
      return SPOT_TYPE_SEQUENCE;
    }

    if (type.equalsIgnoreCase("Any")) {
      return SPOT_TYPE_ANY;
    }

    if (type.equalsIgnoreCase("DateTime")) {
      return SPOT_TYPE_DATETIME;
    }

    if (type.equalsIgnoreCase("Date")) {
      return SPOT_TYPE_DATE;
    }

    if (type.equalsIgnoreCase("Time")) {
      return SPOT_TYPE_TIME;
    }

    if (type.equalsIgnoreCase("Integer")) {
      return SPOT_TYPE_INTEGER;
    }

    if (type.equalsIgnoreCase("Real")) {
      return SPOT_TYPE_REAL;
    }

    if (type.equalsIgnoreCase("Enumerated")) {
      return SPOT_TYPE_ENUMERATED;
    }

    if (type.equalsIgnoreCase("Boolean")) {
      return SPOT_TYPE_BOOLEAN;
    }

    if (type.equalsIgnoreCase("ByteString")) {
      return SPOT_TYPE_BYTESTRING;
    }

    if (type.equalsIgnoreCase("Extends")) {
      return SPOT_TYPE_EXTENDS;
    }

    if (type.equalsIgnoreCase("Refine")) {
      return SPOT_TYPE_REFINE;
    }

    return SPOT_TYPE_USERCLASS;
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth unit.
   *
   * @param out   the writer to output to
   * @param depth the depth for the requested padding
   *
   * @throws IOException If an I/O error occurs
   */
  public void writePadding(Writer out, int depth) throws IOException {
    if (depth == 0) {
      return;
    }

    int len = padding.length;

    depth     *= 2;
    writeXpos += depth;

    while(depth > len) {
      out.write(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.write(padding, 0, depth);
    }
  }

  /**
   * Tests whether this element has attributes
   *
   * @return true or false
   */
  public boolean hasAttributes() {
    return ((theAttributes != null) && (theAttributes.size() > 0));
  }

  /**
   * Returns whether this node represents a boolean type
   * @return true if it is a boolean type; false otherwise
   */
  public boolean isBooleanType() {
    return elementType == typeBoolean;
  }

  /**
   * Returns whether this node represents a nemeric type
   * @return true if it is a boolean type; false otherwise
   */
  public boolean isNumericType() {
    return elementType == typeInteger || elementType==typeReal;
  }

  /**
   * Tests whether this element is a container for other elements
   *
   * @return true or false
   */
  public boolean isContainer() {
    return ((childNodes != null) && (childNodes.size() > 0));
  }

  /**
   * Returns whether this node represents an enumerated type
   * @return true if it is an enumerated type; false otherwise
   */
  public boolean isEnumType() {
    return elementType == typeEnum;
  }

  /**
   * Returns whether this node represents a set
   * @return true if it is a set; false otherwise
   */
  public boolean isSetType() {
    return elementType == typeSet;
  }

  /**
   * Returns the element type as a integer (call this mentod instead of using the public variable)
   * @return  the element type as a integer
   */
  public int getElementTypeAsInt() {
    if(elementTypeAsInt==0) {
      elementTypeAsInt=typeToInt(elementType);
    }
    return elementTypeAsInt;
  }

  /**
   * {@inheritDoc}
   */
  void checkDefaults() {
    if (defaultValue == null) {
      return;
    }

    if (theChoices != null) {
      if (elementType.equals(typeEnum)) {
        if (!checkEnumDefault(defaultValue)) {
          throw new SPOTException("The 'Default' value of '%s' for element %s is not a valid choice", defaultValue,
                                  elementName);
        }
      } else if (!checkChoicesDefault(defaultValue)) {
        throw new SPOTException("The 'Default' value of '%s' for element %s is not a valid choice", defaultValue,
                                elementName);
      }

      return;
    }

    if (elementType.equals(typeBoolean)) {
      if (defaultValue.equalsIgnoreCase("true")) {
        defaultValue = "true";
      } else if (defaultValue.equalsIgnoreCase("false")) {
        defaultValue = "false";
      } else {
        throw new SPOTException("The 'Default' value of '%s' for element %s is not a valid choice", defaultValue,
                                elementName);
      }
    } else if (elementType.equals(typeReal) || elementType.equals(typeInteger)) {
      try {
        SNumber num = new SNumber(defaultValue, true);

        if (theRange != null) {
          SNumber m;
          int     i = theRange.indexOf("..");

          if (i > 0) {
            m = new SNumber(theRange);

            if (num.lt(m)) {
              throw new SPOTException("The 'Default' value of '%s' for element %s is not in Range(%s)", defaultValue,
                                      elementName, theRange);
            }
          }

          if ((i > -1) && (theRange.length() > (i + 2))) {
            m = new SNumber(theRange.substring(i + 2));

            if (num.gt(m)) {
              throw new SPOTException("The 'Default' value of '%s' for element %s is not in Range(%s)", defaultValue,
                                      elementName, theRange);
            }
          }
        }

        defaultValue = num.toString();
      } catch(NumberFormatException ex) {
        throw new SPOTException("The 'Default' value of '%s' for element %s is not a valid choice", defaultValue,
                                elementName);
      }
    } else if (elementType.equals(typePrintableString) && (theRange != null)) {
      int len = defaultValue.length();

      if (!defaultValue.startsWith("\"") &&!defaultValue.endsWith("\"")) {
        throw new SPOTException("The 'Default' value of '%s' for element %s is needs to be quoted", defaultValue,
                                elementName);
      }

      int i = theRange.indexOf("..");

      if (i > 0) {
        if (len < SNumber.intValue(theRange)) {
          throw new SPOTException("The 'Default' value of '%s' for element %s is not in Range(%s)", defaultValue,
                                  elementName, theRange);
        }
      }

      if ((i > -1) && (theRange.length() > (i + 2))) {
        if (len > SNumber.intValue(theRange.substring(i + 2))) {
          throw new SPOTException("The 'Default' value of '%s' for element %s is not in Range(%s)", defaultValue,
                                  elementName, theRange);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  List cleanList(List list) {
    List   cl = new ArrayList();
    String s;
    int    len = list.size();

    for (int i = 0; i < len; i++) {
      s = ((String) list.get(i)).trim();

      if (s.length() == 0) {
        continue;
      }

      cl.add(s);
    }

    return cl;
  }

  /**
   * {@inheritDoc}
   */
  void dumpIndex(String file, String title) {
    SPOTNode x;
    String   name;
    TreeMap  classes = new TreeMap();
    int      len     = (childNodes == null)
                       ? 0
                       : childNodes.size();

    for (int i = 0; i < len; i++) {
      x    = childNodes.get(i);
      name = x.elementName;

      if ((name != null) && (name.length() > 0)) {
        classes.put(name, null);
      }
    }

    Iterator it;

    it = classes.keySet().iterator();
    System.out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=iso-8859-1\"/>");
    System.out.println();
    System.out.println("<link href=\"doxygen.css\" rel=\"stylesheet\" type=\"text/css\"/>");
    System.out.println("</head><body>");
    System.out.println();
    System.out.println("<div class=\"maindox\">");
    System.out.println();

    if (title != null) {
      System.out.println("<h3>" + title + "</h3>");
    }

    System.out.println("<ul>");

    while(it.hasNext()) {
      System.out.println(makeHTMLIndexEntry((String) it.next(), file));
    }

    System.out.println("</ul>");
    System.out.println("</div>");
    System.out.println("</body>");
    System.out.println("</html>");
  }

  /**
   * {@inheritDoc}
   */
  void dumpIndexTree(String file) {
    SPOTNode x;
    String   name;
    TreeMap  classes = new TreeMap();
    int      len     = (childNodes == null)
                       ? 0
                       : childNodes.size();

    for (int i = 0; i < len; i++) {
      x    = childNodes.get(i);
      name = x.elementName;

      if ((name != null) && (name.length() > 0)) {
        classes.put(name, null);
      }
    }

    Iterator it;

    it = classes.keySet().iterator();
    System.out.println(
        "<p><img src=\"ftv2pnode.png\" alt=\"o\" width=16 height=22 onclick=\"toggleFolder('objects', this)\"/><img src=\"ftv2folderclosed.png\" alt=\"+\" width=24 height=22 onclick=\"toggleFolder('objects', this)\"/><a class=\"el\" href=\"annotated.html\" target=\"basefrm\">Configuration Objects</a></p>");
    System.out.println("<div id=\"objects\">");

    while(it.hasNext()) {
      System.out.println(makeHTMLIndexTreeEntry((String) it.next(), file));
    }

    System.out.println("</div>");
  }

  void dumpPathNames(Map identifiers, String parent, SPOTNode type) {
    int      len = (type.childNodes == null)
                   ? 0
                   : type.childNodes.size();
    SPOTNode x;
    String   name;
    SPOTNode subtype;

    for (int i = 0; i < len; i++) {
      x    = type.childNodes.get(i);
      name = x.elementName;

      if ((name != null) && (name.length() > 0)) {
        name = parent + "-" + name;
        identifiers.put(name, null);
        subtype = x.rootNode.elementFor(x.elementType);

        if (subtype != null) {
          dumpPathNames(identifiers, name, subtype);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  void dumpSyntax() {
    SPOTNode x;
    String   name;
    TreeMap  classes     = new TreeMap();
    TreeMap  identifiers = new TreeMap();
    TreeMap  enums       = new TreeMap();
    TreeMap  attributes  = new TreeMap();
    int      len         = (childNodes == null)
                           ? 0
                           : childNodes.size();

    for (int i = 0; i < len; i++) {
      x    = childNodes.get(i);
      name = x.elementName;

      if ((name != null) && (name.length() > 0)) {
        classes.put(name, null);
      }

      if (x.hasAttributes()) {
        attributes.putAll(x.theAttributes);
      }

      if (x.isContainer()) {
        x.dumpSyntaxEx(identifiers, enums, attributes);
      }
    }

    Iterator it;
    String   s;

    it = classes.keySet().iterator();
    System.out.println("[keywords]");

    while(it.hasNext()) {
      s = it.next().toString();
      System.out.println(s);
    }

    System.out.println();
    System.out.println("[identifiers]");
    it = identifiers.keySet().iterator();

    while(it.hasNext()) {
      s = it.next().toString();
      System.out.println(s);
    }

    System.out.println();
    System.out.println("[enums]");
    it = enums.keySet().iterator();

    while(it.hasNext()) {
      s = it.next().toString();
      System.out.println(s);
    }

    System.out.println();
    System.out.println("[attributes]");
    it = attributes.keySet().iterator();

    while(it.hasNext()) {
      s = it.next().toString();
      System.out.println(s);
    }
  }

  /**
   * {@inheritDoc}
   */
  final boolean findToken(MyReader reader, char c, CharArray out) {
    int     d        = 0;
    boolean bInQuote = false;
    int     ft       = 0;

    out.clear();

    try {
      while(true) {
        d = reader.read();

        if (d == -1) {
          break;
        }

        if ((d == c) &&!bInQuote) {
          ft = d;

          break;
        }

        out.append((char) d);

        if (d == '\"') {
          if (!bInQuote) {
            bInQuote = true;
          } else {
            reader.nextChar = reader.read();

            if (reader.nextChar > 0) {
              if (reader.nextChar == '\"') {
                reader.nextChar = 0;
              } else {
                bInQuote = false;
              }
            }
          }
        }
      }
    } catch(IOException ex) {}

    return ft != 0;
  }

  /**
   * {@inheritDoc}
   */
  void handleAttributes(List toks, int pos, int len, String element) {
    int ipos = pos + 1;

    len += pos;

    if (theAttributes == null) {
      theAttributes = new LinkedHashMap();
    }

    int    n;
    String s;

    while(pos < len) {
      s = (String) toks.get(pos++);

      if (s.startsWith("[")) {
        if (pos != ipos) {
          throw new SPOTException("Invalid attribute specification in element %s", s, element);
        }

        n = s.length();

        if (n == 1) {
          continue;
        }

        if (s.charAt(n - 1) == ']') {
          if (n == 2) {
            break;
          }

          s = s.substring(1, n - 1);
        } else {
          s = s.substring(1);
        }
      } else if (s.endsWith("]")) {
        n = s.length();

        if (n == 1) {
          break;
        }

        s = s.substring(0, n - 1);
      } else if (s.equals(",")) {
        continue;
      }

      try {
        CharScanner.parseOptionString(s, theAttributes, ',', true);
      } catch(Exception ex) {
        throw new SPOTException("Missing quote for attribute '%s' in element %s", s, elementName);
      }
    }
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars       the character array
   * @param pos         the starting position within the array
   * @param len         the number of characters in the array to use
   * @param c           the character delimiter
   *
   * @return   a integer representing the index of the first occurrence of the character,
   *           or -1 if the character does not occur
   */
  static int indexOf(char[] chars, int pos, int len, char c) {
    char    d        = 0;
    boolean bInQuote = false;
    int     i        = pos;
    int     n        = pos + len;

    if (pos >= n) {
      return -1;
    }

    while(i < n) {
      d = chars[i];

      if ((d == c) &&!bInQuote) {
        return i;
      }

      if (d == '\"') {
        if (!bInQuote) {
          bInQuote = true;
        } else {
          if ((i + 1) < n) {
            if (chars[i + 1] == '\"') {
              i++;
            } else {
              bInQuote = false;
            }
          }
        }
      }

      i++;
    }

    return -1;
  }

  /**
   * {@inheritDoc}
   */
  String parameter(String at, int pos, List list) {
    if (pos >= list.size()) {
      throw new SPOTException(errMissingValue, at);
    }

    String v = ((String) list.get(pos)).trim();

    if (v.length() == 0) {
      throw new SPOTException(errMissingValue, at);
    }

    return v;
  }

  /**
   * {@inheritDoc}
   */
  void parseChoices(MyReader reader) {
    if (commentList == null) {
      commentList = new ArrayList();
    }

    if (choiceList == null) {
      choiceList = new ArrayList();
    }

    if (modLine == null) {
      modLine = new CharArray();
    }

    if (modScanner == null) {
      modScanner = new CharScanner();
      modScanner.setTrimChars(badChars);
    }

    String comment;

    choiceList.clear();
    commentList.clear();
    modLine.clear();

    while(findToken(reader, '\n', modLine)) {
      modScanner.reset(modLine.A, 0, modLine._length, false);
      modScanner.trim();

      if (modScanner.getCurrentChar() == '}') {
        modScanner.read();
        modScanner.trim();
        parseModifiers(reader, modScanner.getContent(), modScanner.getPosition(), modScanner.getLength());
        theChoices         = (String[]) choiceList.toArray(new String[choiceList.size()]);
        theChoicesComments = (String[]) commentList.toArray(new String[commentList.size()]);

        return;
      }

      if (modScanner.getLength() > 0) {
        comment = parseComment(modScanner);
        commentList.add(comment);
        choiceList.add(modScanner.getLeftOver());
      }
    }

    throw new SPOTException("The 'CHOICE' modifier for '%s' must be terminated by a '}'", elementName);
  }

  /**
   * {@inheritDoc}
   */
  String parseComment(CharScanner sc) {
    boolean pound = false;
    int     n     = sc.indexOf('#', true, true);

    if (n == -1) {
      n = sc.indexOf('/', true, true);
    } else {
      pound = true;
    }

    do {
      if (n == -1) {
        break;
      }

      if (!pound && (sc.getChar(n + 1) != '/')) {
        break;
      }

      int    pos = sc.getPosition();
      int    len = sc.getLength() - (n - pos);
      String comment;

      if (pound) {
        comment = new String(sc.getContent(), n + 1, len - 1);
      } else {
        comment = new String(sc.getContent(), n + 2, len - 2);
      }

      if (comment != null) {
        comment = comment.trim();
      }

      sc.chop(len);
      sc.trim();

      return comment;
    } while(false);

    return null;
  }

  /**
   * {@inheritDoc}
   */
  void parseEnumerations(MyReader reader) {
    SNumber num;
    String  s;
    int[]   tok;

    if (choiceList == null) {
      choiceList = new ArrayList();
    }

    if (numList == null) {
      numList = new IntList();
    }

    if (commentList == null) {
      commentList = new ArrayList();
    }

    if (modLine == null) {
      modLine = new CharArray();
    }

    if (modScanner == null) {
      modScanner = new CharScanner();
      modScanner.setTrimChars(badChars);
    }

    choiceList.clear();
    modLine.clear();
    commentList.clear();
    numList.clear();

    while(findToken(reader, '\n', modLine)) {
      modScanner.reset(modLine.A, 0, modLine._length, false);
      modScanner.trim();

      if (modScanner.getCurrentChar() == '}') {
        modScanner.read();
        modScanner.trim();
        theNumChoices      = numList.toArray();
        theChoices         = (String[]) choiceList.toArray(new String[choiceList.size()]);
        theChoicesComments = (String[]) commentList.toArray(new String[commentList.size()]);
        parseModifiers(reader, modScanner.getContent(), modScanner.getPosition(), modScanner.getLength());

        return;
      }

      if (modScanner.getLength() > 0) {
        s = parseComment(modScanner);
        commentList.add(s);
        tok = modScanner.findToken('(', true, false);
        tok = modScanner.strip(tok, quoteChar);

        if (tok[1] == 0) {
          throw new SPOTException("Missing string enumeration part for '%s' in choice #" + choiceList.size(),
                                  elementName);
        }

        if (!isValidName(modScanner.getContent(), tok[0], tok[1])) {
          throw new SPOTException("Invalid enumeration name for '%s' in choice #" + choiceList.size(), elementName);
        }

        s = modScanner.getToken(tok);
        choiceList.add(s);
        modScanner.trim();

        if (modScanner.getLength() == 0) {
          throw new SPOTException("Missing numeric enumeration part for '%s' in choice #" + choiceList.size(),
                                  elementName);
        }

        if (modScanner.getLastChar() != ')') {
          throw new SPOTException("Invalid numeric enumeration part for '%s' in choice #" + choiceList.size(),
                                  elementName);
        }

        modScanner.chop(1);
        modScanner.trim();

        try {
          num = new SNumber(modScanner.getLeftOver(), true);

          if (!num.isInteger()) {
            throw new SPOTException("No integer numeric enumeration part for '%s' in choice#" + choiceList.size(),
                                    elementName);
          }
        } catch(Exception ex) {
          throw new SPOTException("Invalid numeric enumeration part for '%s' in choice#" + choiceList.size(),
                                  elementName);
        }

        numList.add(num.intValue());
      }
    }

    throw new SPOTException("The 'Choice' modifier for '%s' must be terminated by a '}'", elementName);
  }

  /**
   * {@inheritDoc}
   */
  void parseModifiers(MyReader reader, char[] chars, int pos, int len) {
    int     i;
    int     n;
    String  s;
    boolean att;

    if (len > 1) {
      if ((chars[pos] == '/') && (chars[pos + 1] == '/')) {
        theComment = new String(chars, pos + 2, len - 2);
        theComment = theComment.trim();

        return;
      }

      if ((chars[pos] == '#')) {
        theComment = new String(chars, pos + 1, len - 1);
        theComment = theComment.trim();

        return;
      }
    }

    if (modScanner == null) {
      modScanner = new CharScanner();
      modScanner.setTrimChars(badChars);
    }

    if (modList == null) {
      modList = new ArrayList();
    }

    if (modLine == null) {
      modLine = new CharArray();
    }

    modScanner.reset(chars, pos, len, false);
    modScanner.trim();
    modList.clear();
    modLine.clear();
    modScanner.getTokens(' ', true, true, false, modList);

    List   toks = cleanList(modList);
    String us;
    int    toksLength = toks.size();

    for (i = 0; i < toksLength; i++) {
      s = ((String) toks.get(i)).trim();

      if (s.length() == 0) {
        continue;
      }

      if ((n = s.indexOf("//")) != -1 || (n = s.indexOf("#")) != -1) {
        StringBuilder buf = new StringBuilder();

        if (s.charAt(n) == '#') {
          buf.append(s.substring(n + 1).trim());
        } else {
          buf.append(s.substring(n + 2).trim());
        }

        if ((n > 0) && (s.charAt(n - 1) == ',')) {
          n--;
        }

        s = s.substring(0, n);
        s = s.trim();

        for (n = i + 1; n < toksLength; n++) {
          buf.append(' ');
          buf.append((String) toks.get(n));
        }

        theComment = buf.toString().trim();
        i          = toksLength - 1;

        if (s.length() == 0) {
          break;
        }
      }

      if (s.startsWith("[")) {
        att = false;

        for (n = i; n < toksLength; n++) {
          if (((String) toks.get(n)).endsWith("]")) {
            handleAttributes(toks, i, n - i + 1, elementName);
            i   = n;
            att = true;
          }
        }

        if (!att) {
          throw new SPOTException("Invalid attribute specification for element %s", elementName);
        }

        continue;
      }

      us = s.toUpperCase(Locale.ENGLISH);

      if (us.equals("DEFAULT")) {
        defaultValue = parameter(s, i + 1, toks);
        i++;
      } else if (us.equals("OPTIONAL")) {
        isOptional = true;
      } else if (us.equals("REFERENCE")) {
        isOptional  = true;
        isReference = true;
      } else if (us.startsWith("RANGE(")) {
        s = s.substring(6);
        n = s.length();

        if ((n < 2) || (s.charAt(n - 1) != ')') || ((s.indexOf('.') != -1) && (s.indexOf("..") == -1))) {
          throw new SPOTException(Helper.expandString(errInvalidSize, new String(chars, pos, len)));
        }

        theRange = s.substring(0, n - 1);
      } else if (us.equals("VALUE")) {
        fixedValue = parameter(s, i + 1, toks);
        i++;
      } else if (us.equals("DEFINEDBY")) {
        definedBy = parameter(s, i + 1, toks);
        i++;
      } else if (us.equals("MANDITORY")) {
        isOptional        = false;
        manditoryModifier = true;
      } else if (us.equals("CHOICE")) {
        if ((i + 1) == toksLength) {
          if (!findToken(reader, '{', modLine)) {
            throw new SPOTException("The 'Choice modified must be followed by a '{'");
          }
        } else {
          if (!toks.get(i + 1).equals("{")) {
            throw new SPOTException("The 'Choice' modified must be followed by a '{'");
          }
        }

        parseChoices(reader);
        i++;
      } else if (us.equals("CHOICE{")) {
        parseChoices(reader);
      } else {
        throw new SPOTException("Unknown modifier '%s' in element %s", s, elementName);
      }
    }

    checkDefaults();
  }

  /**
   * {@inheritDoc}
   */
  int[] trim(char[] chars, int pos, int len, int[] tok) {
    int n = pos + len;

    if (tok == null) {
      tok = new int[2];
    }

    tok[0] = n - 1;

    if (tok[0] < 0) {
      tok[0] = pos;
      tok[1] = len;

      return tok;
    }

    while((pos < n) && (chars[pos] < 33)) {
      pos++;
      len--;
    }

    while((n > pos) && (chars[--n] < 33)) {
      len--;
    }

    tok[1] = len;

    if (len != 0) {
      tok[0] = pos;
    }

    return tok;
  }

  /**
   * {@inheritDoc}
   */
  void write(Writer out, boolean html, char s) throws IOException {
    if (!html) {
      out.write(s);

      return;
    }

    if (s == '\n') {
      writeXpos = 0;
    } else {
      writeXpos++;
    }

    out.write(s);
  }

  /**
   * {@inheritDoc}
   */
  void write(Writer out, boolean html, String s) throws IOException {
    if (!html) {
      out.write(s);

      return;
    }

    if (s != null) {
      char[] b = s.toCharArray();

      write(out, html, b, 0, b.length);
    }
  }

  /**
   * {@inheritDoc}
   */
  void write(Writer out, boolean html, char[] s, int pos, int len) throws IOException {
    if (!html) {
      out.write(s, pos, len);

      return;
    }

    len += pos;

    for (int i = pos; i < len; i++) {
      if (s[i] == '\n') {
        writeXpos = 0;

        continue;
      }

      writeXpos++;
    }

    out.write(s, pos, len);
  }

  /**
   * {@inheritDoc}
   */
  void writeAttributes(Writer out, boolean html) {
    if ((theAttributes == null) || (theAttributes.size() == 0)) {
      return;
    }

    try {
      if (html) {
        writeSpan(out, spanType);
      }

      Iterator      it = theAttributes.entrySet().iterator();
      StringBuilder sb = new StringBuilder();
      Map.Entry     me;

      write(out, html, " [ ");

      String  val;
      boolean first = true;

      while(it.hasNext()) {
        me = (Map.Entry) it.next();
        sb.setLength(0);

        if (!first) {
          sb.append(", ");
        } else {
          first = false;
        }

        sb.append((String) me.getKey());
        val = (String) me.getValue();

        if (val != null) {
          sb.append("=\"");
          sb.append(val);
          sb.append('"');
        }

        write(out, html, sb.toString());
      }

      write(out, html, " ]");

      if (html) {
        writeSpan(out, null);
      }
    } catch(IOException ex) {}
  }

  /**
   * {@inheritDoc}
   */
  void writeComment(Writer out, String comment, boolean html) {
    if (comment == null) {
      return;
    }

    try {
      if (html) {
        writeSpan(out, spanComment);
        out.write(" // ");
        out.write(comment);
        writeSpan(out, null);
      } else {
        out.write(" // ");
        out.write(comment);
      }

      return;
    } catch(IOException ex) {}
    /*
     *     int len = lineWrap - writeXpos - 2;
     *     int x = writeXpos;
     *
     *     if ((comment.length() + 2) < len) {
     *       writeSpan(out, spanComment);
     *       out.write(" --");
     *       out.write(comment);
     *       writeSpan(out, null);
     *
     *       return;
     *     }
     *
     *     comment = leftJustify((len < 20) ? (lineWrap - 8) : len, comment);
     *
     *     List list = CharScanner.getTokens(comment, '\n');
     *     writeSpan(out, spanComment);
     *     out.write(" --");
     *
     *     int i = 0;
     *     out.write(list.get(i));
     *
     *     for (i = 1; i < list._length; i++) {
     *       out.write(lineSeparator);
     *       writeSpace(out, x + 3);
     *       out.write(list.get(i));
     *     }
     *
     *     writeSpan(out, null);
     *   } catch (IOException ex) {
     *   }
     */
  }

  /**
   * {@inheritDoc}
   */
  void writeDescription(Writer out, String comment, boolean html) {
    try {
      if (comment != null) {
        if (html) {
          writeSpan(out, spanDescription);
          write(out, html, comment);
          writeSpan(out, null);
        } else {
          List ln = CharScanner.getTokens(comment, '\n', false);

          write(out, html, "/**" + lineSeparator);

          int len = ln.size();

          for (int i = 0; i < len; i++) {
            write(out, html, " * ");
            write(out, html, (String) ln.get(i));
            write(out, html, lineSeparator);
          }

          write(out, html, " */");
        }
      }
    } catch(IOException ex) {}
  }

  /**
   * {@inheritDoc}
   */
  void writeHeading(Writer out, String head, boolean html) {
    try {
      if (head != null) {
        if (html) {
          write(out, html, "<h2><a name=\"" + head + "\">");
          // writeSpan(out, spanComment);
          write(out, html, head);
          write(out, html, "</a></h2>");
          // writeSpan(out, null);
        }
      }
    } catch(IOException ex) {}
  }

  /**
   * {@inheritDoc}
   */
  void writeModifier(Writer out, String mod, boolean html) {
    try {
      if (html) {
        writeSpan(out, spanType);
      }

      write(out, html, mod);

      if (html) {
        writeSpan(out, null);
      }
    } catch(IOException ex) {}
  }

  /**
   * Writes space padding
   *
   * @param out the writer to output to
   *
   * @throws IOException If an I/O error occurs
   */
  void writePadding(Writer out) throws IOException {
    int depth = theDepth;

    if (depth == 0) {
      return;
    }

    int len = padding.length;

    depth     *= 2;
    writeXpos += depth;

    while(depth > len) {
      out.write(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.write(padding, 0, depth);
    }
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  void writeSpace(Writer out, int num) throws IOException {
    if (num == 0) {
      return;
    }

    writeXpos += num;

    int len = padding.length;

    while(num > len) {
      out.write(padding, 0, len);
      num -= len;
    }

    if (num > 0) {
      out.write(padding, 0, num);
    }
  }

  /**
   * {@inheritDoc}
   */
  void writeSpan(Writer out, String classname) {
    try {
      if (classname == null) {
        out.write("</span>");
      } else {
        out.write("<span class=\"");
        out.write(classname);
        out.write("\">");
      }
    } catch(IOException ex) {}
  }

  /**
   * {@inheritDoc}
   */
  void writeType(Writer out, String type, boolean html, boolean link) {
    try {
      boolean makelink = false;
      boolean user     = findType(type) == -1;

      if (html) {
        if (link && user && (rootNode != null) && (rootNode.elementFor(type) != null)) {
          makelink = true;
          out.write("<a href=\"#" + type + "\">");
        }

        writeSpan(out, user
                       ? spanUserType
                       : spanType);
      }

      write(out, html, type);

      if (html) {
        writeSpan(out, null);

        if (makelink) {
          out.write("</a>");
        }
      }
    } catch(IOException ex) {}
  }

  /**
   * {@inheritDoc}
   */
  String getNameToken(CharScanner scanner, char c) {
    String s   = null;
    int[]  tok = scanner.findToken(c);

    tok = scanner.trim(tok);

    if ((tok != null) && (tok[1] != 0)) {
      s = scanner.getToken(tok);

      if (!isValidName(scanner.getContent(), tok[0], tok[1])) {
        throw new SPOTException(errInvalidName, s);
      }
    }

    return s;
  }

  /**
   * {@inheritDoc}
   */
  String getNameToken(CharScanner scanner, int[] tok) {
    String s = null;

    tok = scanner.trim(tok);

    if ((tok != null) && (tok[1] != 0)) {
      s = scanner.getToken(tok);

      if (!isValidName(scanner.getContent(), tok[0], tok[1])) {
        throw new SPOTException(errInvalidName, s);
      }
    }

    return s;
  }

  /**
   * {@inheritDoc}
   */
  boolean isAutoReference(SPOTNode x) {
    String type = x.elementType;

    if (type == null) {
      return false;
    }

    while((x = x.parentNode) != null) {
      if (x.elementType == null) {
        continue;
      }

      if (x.elementType.equals(typeSet)) {
        return false;
      }

      if ((x.elementName != null) && x.elementName.equals(type)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Converst an elements children to thire string representation
   *
   * @param out   the writer to output to
   * @param html whether the outputted string should be HTML
   *
   * @throws IOException If an I/O error occurs
   */
  protected void childrenToString(Writer out, boolean html) throws IOException {
    int      i = 0;
    SPOTNode x = null;

    if (!isContainer()) {
      return;
    }

    long len = childNodes.size();

    for (i = 0; i < (len - 1); i++) {
      x = childNodes.get(i);
      x.toStringEx(out, html);
      write(out, html, ',');
      writeXpos = x.writeXpos;
      writeComment(out, x.theComment, html);
      write(out, html, lineSeparator);
    }

    x = childNodes.get(i);
    x.toStringEx(out, html);

    if (x.theComment != null) {
      write(out, html, ' ');
    }

    writeComment(out, x.theComment, html);
    write(out, html, lineSeparator);
  }

  protected void clean() {
    if (numList != null) {
      numList.clear();
      numList = null;
    }

    if (modList != null) {
      modList.clear();
      modList = null;
    }

    if (commentList != null) {
      commentList.clear();
      commentList = null;
    }

    if (choiceList != null) {
      choiceList.clear();
      choiceList = null;
    }

    modScanner = null;
    modLine    = null;
  }

  /**
   * Converts the element to its string representation
   *
   * @param out the writer to output to
   * @param html whether the outputted string should be HTML
   *
   * @throws IOException If an I/O error occurs
   */
  protected void toStringEx(Writer out, boolean html) throws IOException {
    int i = 0;

    writePadding(out, theDepth);

    if (theDepth == 0) {
      writeType(out, elementName, html, false);
      write(out, html, " ::= ");
    } else {
      write(out, html, elementName);
      write(out, html, ' ');
    }

    writeType(out, elementType, html, true);

    if (elementType.equals(typeExtends)) {
      write(out, html, ' ');
      writeType(out, extendsType, html, true);
    }

    if (theChoices != null) {
      if (theNumChoices != null) {
        write(out, html, " {");
        write(out, html, lineSeparator);

        int len = 0;
        int n;

        for (i = 0; i < theChoices.length; i++) {
          n = theChoices[i].length();

          if (n > len) {
            len = n;
          }
        }

        len++;

        for (i = 0; i < (theChoices.length - 1); i++) {
          if (html) {
            writeSpan(out, spanCode);
          }

          writePadding(out, theDepth + 1);
          write(out, html, theChoices[i]);
          n = len - theChoices[i].length();

          if (n > 0) {
            writeSpace(out, n);
          }

          write(out, html, '(');
          write(out, html, String.valueOf(theNumChoices[i]));
          write(out, html, ")");

          if (html) {
            writeSpan(out, null);
          }

          write(out, html, ",");
          writeComment(out, theChoicesComments[i], html);
          write(out, html, lineSeparator);
        }

        if (html) {
          writeSpan(out, spanCode);
        }

        writePadding(out, theDepth + 1);
        write(out, html, theChoices[i]);
        n = len - theChoices[i].length();

        if (n > 0) {
          writeSpace(out, n);
        }

        write(out, html, '(');
        write(out, html, String.valueOf(theNumChoices[i]));
        write(out, html, ")");

        if (html) {
          writeSpan(out, null);
        }

        if (theChoicesComments[i] != null) {
          write(out, html, ' ');
        }

        writeComment(out, theChoicesComments[i], html);
        write(out, html, lineSeparator);
        writePadding(out, theDepth);
        write(out, html, '}');
      } else {
        writeModifier(out, " Choice", html);
        write(out, html, " {");
        write(out, html, lineSeparator);

        for (i = 0; i < (theChoices.length - 1); i++) {
          if (html) {
            writeSpan(out, spanCode);
          }

          writePadding(out, theDepth + 1);
          write(out, html, theChoices[i]);

          if (html) {
            writeSpan(out, null);
          }

          write(out, html, ",");
          writeComment(out, theChoicesComments[i], html);
          write(out, html, lineSeparator);
        }

        if (html) {
          writeSpan(out, spanCode);
        }

        writePadding(out, theDepth + 1);
        write(out, html, theChoices[i]);

        if (theChoicesComments[i] != null) {
          write(out, html, ' ');
        }

        if (html) {
          writeSpan(out, null);
        }

        writeComment(out, theChoicesComments[i], html);
        write(out, html, lineSeparator);
        writePadding(out, theDepth);
        write(out, html, '}');
      }
    } else if (isContainer() || elementType.equals(typeExtends)) {
      write(out, html, " {");
      write(out, html, lineSeparator);
      childrenToString(out, html);
      writeXpos = 0;
      writePadding(out, theDepth);
      write(out, html, '}');
    }

    if (theRange != null) {
      writeModifier(out, " Range", html);
      write(out, html, "(");
      write(out, html, theRange);
      write(out, html, ')');
    }

    if (this.definedBy != null) {
      writeModifier(out, " DefinedBy ", html);
      write(out, html, definedBy);
    }

    if (this.defaultValue != null) {
      writeModifier(out, " Default ", html);
      write(out, html, defaultValue);
    }

    if (this.fixedValue != null) {
      writeModifier(out, " Value ", html);
      write(out, html, fixedValue);
    }

    if (isReference) {
      writeModifier(out, " Reference", html);
    } else if (isOptional) {
      writeModifier(out, " Optional", html);
    } else if (this.manditoryModifier) {
      writeModifier(out, " Manditory", html);
    }

    writeAttributes(out, html);
  }

  private String makeHTMLIndexEntry(String name, String file) {
    return "<li><a href=\"" + file + "#" + name + "\" target=\"definitions\">" + name + "</a></li>";
  }

  private String makeHTMLIndexTreeEntry(String name, String file) {
    return treePrefix + file + "#" + name + "\" target=\"basefrm\">" + name + "</a></p>";
  }

  private void readBody(MyReader reader) throws SPOTException {
    String      s;
    SPOTNode    x       = null;
    int[]       thistok = new int[2];
    int[]       tok;
    int         n;
    CharArray   line    = new CharArray();
    CharScanner scanner = new CharScanner();

    scanner.setTrimChars(badChars);

    boolean curlystartfound;

    do {
      curlystartfound = false;

      while(findToken(reader, '\n', line)) {
        trim(line.A, 0, line._length, thistok);

        if (thistok[1] != 0) {
          break;
        }
      }

      line.replace('\t', ' ');
      line.replace('\r', ' ');
      trim(line.A, 0, line._length, thistok);

      if ((thistok[1] == 0) || (line._length == 0) || ((thistok[1] == 1) && (line.A[thistok[0]] == '}'))) {
        break;
      }

      scanner.reset(line.A, thistok[0], thistok[1], false);
      tok = scanner.findToken(' ');
      tok = scanner.trim(tok);

      if ((tok == null) || (tok[1] == 0)) {
        continue;
      }

      if ((tok[1] == 1) && (line.A[tok[0]] == '}')) {
        scanner.trim();
        line._length = 0;
        scanner.getLeftOverCB(line);

        if (line._length > 0) {
          parseModifiers(reader, line.A, 0, line._length);
        }

        break;
      }

      x            = new SPOTNode(getNameToken(scanner, tok));
      x.parentNode = this;
      scanner.trim();
      tok = scanner.findToken(' ');
      tok = scanner.strip(tok, badChars);

      if ((tok == null) || (tok[1] == 0)) {
        throw new SPOTException("Missing type for '%s' element", x.elementName);
      }

      if (scanner.getLastChar(tok) == '{') {
        curlystartfound = true;
        tok[1]--;

        if (tok[1] == 0) {
          throw new SPOTException("Missing type for '%s' element", x.elementName);
        }
      }

      s = getNameToken(scanner, tok);
      n = findType(s);

      if (n != -1) {
        s = theTypes[n];
      }

      x.elementType = s;
      addElement(x);
      scanner.trim();

      if (x.elementType.equalsIgnoreCase(typeExtends)) {
        if (curlystartfound) {
          throw new SPOTException(errMissingType, x.elementName);
        }

        tok = scanner.findToken(' ');

        if ((tok == null) || (tok[1] == 0)) {
          continue;
        }

        x.extendsType = getNameToken(scanner, tok);
        scanner.trim();
      }

      if (x.elementType.equals(typeSequence) || x.elementType.equals(typeEnum) || x.elementType.equals(typeExtends)
          || x.elementType.equals(typeSet)) {
        if (!!curlystartfound) {
          s = scanner.getLeftOver();

          if ((s == null) || (s.length() == 0)) {
            if (!findToken(reader, '{', line)) {
              throw new SPOTException(errMissingCurlyStart, x.elementName);
            }

            trim(line.A, 0, line._length, thistok);

            if (thistok[1] != 0) {
              throw new SPOTException(errMissingCurlyStart, x.elementName);
            }
          } else if (!s.equals("{")) {
            throw new SPOTException(errMissingCurlyStart, x.elementName);
          }
        }

        if (x.elementType.equals(typeEnum)) {
          x.parseEnumerations(reader);
        } else {
          x.readBody(reader);
        }
      } else {
        line._length = 0;
        scanner.getLeftOverCB(line);

        if (line._length > 0) {
          x.parseModifiers(reader, line.A, 0, line._length);
        }
      }
    } while(true);

    clean();
  }

  @SuppressWarnings("resource")
  private void readEx(Reader in) throws SPOTException {
    CharArray   line    = new CharArray();
    CharScanner scanner = new CharScanner();
    SPOTNode    x       = null;
    CharArray   desc    = new CharArray();
    String      s;
    boolean     found;

    theDepth = -1;

    int   i = -1;
    int[] thistok;

    if (theReader == null) {
      theReader = new MyReader(in);
    }

    do {
      i = -1;
      desc.setLength(0);

      while(findToken(theReader, '\n', line)) {
        s = line.toString().trim();

        // if ( (i = line.indexOf(colonColonEq, 0)) != -1) {
        // break;
        // }
        if (line.startsWith("#")) {
          s = s.substring(1).trim();
        } else if (s.startsWith("/**")) {
          s = s.substring(3).trim();

          if (s.length() == 0) {
            continue;
          }
        } else if (s.startsWith("/*")) {
          s = s.substring(2).trim();

          if (s.length() == 0) {
            continue;
          }
        } else if (s.startsWith("//")) {
          s = s.substring(2).trim();

          if (s.length() == 0) {
            continue;
          }
        } else if (s.startsWith("*/")) {
          continue;
        } else if (s.startsWith("*")) {
          s = s.substring(1).trim();
        } else if ((i = line.indexOf(colonColonEq, 0)) != -1) {
          break;
        }

        if (s.length() == 0) {
          continue;
        }

        desc.append(s);
        desc.append(lineSeparator);
      }

      if (i == -1) {
        break;
      }

      line.replace('\t', ' ');
      line.replace('\r', ' ');
      scanner.reset(line.A, 0, line._length, false);
      scanner.trim();
      s = getNameToken(scanner, ':');

      if (s != null) {
        s = s.trim();

        if (s.length() == 0) {
          s = null;
        }
      }

      if ((s == null) || ((scanner.read() != ':') || (scanner.read() != '='))) {
        continue;
      }

      x    = new SPOTNode(s);
      desc = desc.trim();

      if (desc.length() != 0) {
        x.theDescription = desc.toString();
      }

      scanner.trim();
      thistok = scanner.findToken('{');
      found   = thistok[2] == '{';
      scanner.trim(thistok);
      scanner.setPosAndLength(thistok[0], thistok[1]);
      s = getNameToken(scanner, ' ');

      if ((s == null) || (s.length() == 0)) {
        throw new SPOTException(errInvalid);
      }

      i = findType(s);

      if (i != -1) {
        s = theTypes[i];
      }

      x.elementType = s;
      x.parentNode  = this;
      addElement(x);

      if (s.equals(typeExtends)) {
        s = getNameToken(scanner, ' ');

        if ((s == null) || (s.length() == 0)) {
          throw new SPOTException(errMissingType, x.elementName);
        }

        x.extendsType = s;
      } else if (s.equals(typeRefine)) {
        s = getNameToken(scanner, ' ');

        if ((s == null) || (s.length() == 0)) {
          throw new SPOTException(errMissingType, x.elementName);
        }

        x.isRefine    = true;
        x.extendsType = s;
      }

      scanner.trim();

      int c = scanner.read();

      if (c == '#') {
        x.theComment = scanner.getLeftOver();
      } else if (c == '/') {    // comment check
        if (scanner.read() == '/') {
          x.theComment = scanner.getLeftOver();
        }
      }

      if (scanner.getLength() != 0) {
        throw new SPOTException(errInvalid);
      }

      if (found) {
        x.readBody(theReader);
      } else {
        if ((x.extendsType != null) || x.elementType.equals(typeSequence)) {
          while(findToken(theReader, '\n', line)) {
            trim(line.A, 0, line._length, thistok);

            if (thistok[1] != 0) {
              break;
            }

            thistok[1] = 0;
          }
        }

        if ((thistok[1] != 1) || (line.A[thistok[0]] != '{')) {
          throw new SPOTException(errMissingCurlyStart, x.elementName);
        }

        x.readBody(theReader);
      }
    } while(true);
  }

  private boolean isValidName(char[] b, int pos, int len) {
    if (!Character.isJavaIdentifierStart(b[pos])) {
      return false;
    }

    for (int i = 1; i < len; i++) {
      if (!Character.isJavaIdentifierPart(b[pos + i])) {
        return false;
      }
    }

    return true;
  }

  static class MyReader {
    int    nextChar = 0;
    Reader in;

    MyReader(Reader in) {
      this.in = in;
    }

    /**
     * {@inheritDoc}
     */
    void clear() {
      nextChar = 0;
    }

    /**
     * {@inheritDoc}
     */
    int read() throws IOException {
      if (nextChar == 0) {
        return in.read();
      }

      int c = nextChar;

      nextChar = 0;

      return c;
    }
  }
}
