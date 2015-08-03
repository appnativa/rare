/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.net;

import com.appnativa.rare.util.JSONHelper;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Streams;
import com.appnativa.util.StringCache;
import com.appnativa.util.UTF8Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class FormHelper {
  public static final String BOUNDARY_PREFIX = "--";
  public static final String NEWLINE         = "\r\n";

  /**
   * Writes a boundary end marker. The boundary prefix and suffix will be
   * automatically added
   *
   * @param writer the writer
   * @param boundary the boundary
   *
   * @throws IOException
   */
  public static void writeBoundaryEnd(Writer writer, String boundary) throws IOException {
    writer.write(NEWLINE);
    writer.write(BOUNDARY_PREFIX);
    writer.write(boundary);
    writer.write(BOUNDARY_PREFIX);
    writer.write(NEWLINE);
  }

  /**
   * Writes the header portion for a HTTP field other than a file upload or multipart field
   *
   * @param first true if this is the first item; false otherwise
   * @param writer   the writer
   * @param name     the field name
   * @param boundary the content boundary
   * @throws  IOException  on input/output errors
   */
  public static void writeFieldHeader(boolean first, Writer writer, String boundary, String name) throws IOException {
    if ((name == null) || (name.length() == 0)) {
      throw new IllegalArgumentException("Field name cannot be null or empty.");
    }

    if (!first) {
      writer.write(NEWLINE);
    }

    writer.write(BOUNDARY_PREFIX);
    writer.write(boundary);
    writer.write(NEWLINE);
    // write content header
    writer.write("Content-Disposition: form-data; name=\"" + name + "\"");
    writer.write(NEWLINE);
    writer.write(NEWLINE);
  }

  /**
   * Writes the header portion for a HTTP field other than a file upload or multipart field
   *
   * @param first true if this is the first item; false otherwise
   * @param writer    the writer
   * @param name      the field name
   * @param boundary  the content boundary
   * @param mimeType  the MIME type for the content
   * @throws  IOException  on input/output errors
   */
  public static void writeFieldHeader(boolean first, Writer writer, String boundary, String name, String mimeType)
          throws IOException {
    if ((name == null) || (name.length() == 0)) {
      throw new IllegalArgumentException("Field name cannot be null or empty.");
    }

    if (!first) {
      writer.write(NEWLINE);
    }

    writer.write(BOUNDARY_PREFIX);
    writer.write(boundary);
    writer.write(NEWLINE);
    // write content header
    writer.write("Content-Disposition: form-data; name=\"" + name + "\"");
    writer.write(NEWLINE);

    if (mimeType != null) {
      writer.write("Content-type: ");
      writer.write(mimeType);
      writer.write(NEWLINE);
    }

    writer.write(NEWLINE);
  }

  /**
   * Writes the contents of the specified file as an HTTP form value
   *
   * @param first true if this is the first item; false otherwise
   * @param writer the writer
   * @param boundary the multi-part boundary
   * @param name the name of the field that the file data is associated with
   * @param file the file to write
   *
   * @throws IOException
   */
  public static void writeFile(boolean first, Writer writer, String boundary, String name, String file)
          throws IOException {
    writeFile(first, writer, boundary, name, new File(file), false);
  }

  /**
   * Writes the contents of the specified file as an HTTP form value
   *
   * @param first true if this is the first item; false otherwise
   * @param writer the writer
   * @param boundary the multi-part boundary
   * @param name the name of the field that the file data is associated with
   * @param file the file to write
   * @param attachment true if the file should be tagged as an attachment; false otherwise
   *
   * @throws IOException
   */
  public static void writeFile(boolean first, Writer writer, String boundary, String name, File file,
                               boolean attachment)
          throws IOException {
    String fileName = file.getName();
    int    n        = fileName.lastIndexOf('.');
    String ext      = (n == -1)
                      ? "bin"
                      : fileName.substring(n + 1);
    String mimeType = MIMEMap.typeFromExtension(ext);

    writeFileHeader(first, writer, boundary, name, mimeType, fileName, attachment);

    FileInputStream fin = new FileInputStream(file);

    Streams.streamToWriter(fin, writer, null);
    writer.flush();
  }

  /**
   * Writes the contents of the specified file as an HTTP form value
   *
   * @param first true if this is the first item; false otherwise
   * @param writer the writer
   * @param boundary the multi-part boundary
   * @param name the name of the field that the file data is associated with
   * @param mimeType the MIME type for the file (can be null)
   * @param  fileName  the file name (cannot be null)
   * @param file the file to write
   * @param attachment true if the file should be tagged as an attachment; false otherwise
   * @throws  IOException  on input/output errors
   */
  public static void writeFile(boolean first, Writer writer, String boundary, String name, String mimeType,
                               String fileName, File file, boolean attachment)
          throws IOException {
    if (fileName == null) {
      fileName = file.getName();
    }

    if (mimeType == null) {
      int    n   = fileName.lastIndexOf('.');
      String ext = (n == -1)
                   ? "bin"
                   : fileName.substring(n + 1);

      mimeType = MIMEMap.typeFromExtension(ext);
    }

    writeFileHeader(first, writer, boundary, name, mimeType, fileName, attachment);

    FileInputStream fin = new FileInputStream(file);

    Streams.streamToWriter(fin, writer, null);
    writer.flush();
  }

  /**
   * Writes the contents of the specified file as an HTTP form value
   *
   * @param first true if this is the first item; false otherwise
   * @param writer the writer
   * @param boundary the multi-part boundary
   * @param name the name of the field that the file data is associated with
   * @param mimeType the MIME type for the file  (can be null)
   * @param  fileName  the file name (cannot be null)
   * @param file the file to write
   * @param attachment true if the file should be tagged as an attachment; false otherwise
   * @throws  IOException  on input/output errors
   */
  public static void writeFile(boolean first, Writer writer, String boundary, String name, String mimeType,
                               String fileName, Reader file, boolean attachment)
          throws IOException {
    if (mimeType == null) {
      int    n   = fileName.lastIndexOf('.');
      String ext = (n == -1)
                   ? "bin"
                   : fileName.substring(n + 1);

      mimeType = MIMEMap.typeFromExtension(ext);
    }

    writeFileHeader(first, writer, boundary, name, mimeType, fileName, attachment);
    Streams.readerToWriter(file, writer, null);
    writer.flush();
  }

  /**
   * Writes the contents of the specified file as an HTTP form value
   *
   * @param first true if this is the first item; false otherwise
   * @param writer the writer
   * @param boundary the multi-part boundary
   * @param name the name of the field that the file data is associated with
   * @param mimeType the MIME type for the file
   * @param  fileName  the file name (cannot be null)
   * @param attachment true if the file should be tagged as an attachment; false otherwise
   * @throws  IOException  on input/output errors
   */
  public static void writeFileHeader(boolean first, Writer writer, String boundary, String name, String mimeType,
                                     String fileName, boolean attachment)
          throws IOException {
    if ((fileName == null) || (fileName.length() == 0)) {
      throw new IllegalArgumentException("File name cannot be null or empty.");
    }

    if (!first) {
      writer.write(NEWLINE);
    }

    writer.write(BOUNDARY_PREFIX);
    writer.write(boundary);
    writer.write(NEWLINE);

    if (attachment) {
      writer.write("Content-Disposition: attachment; filename=\"" + fileName + "\"");
    } else {
      writer.write("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"");
    }

    writer.write(NEWLINE);

    if (mimeType != null) {
      writer.write("Content-Type: " + mimeType);
      writer.write(NEWLINE);
    }

    writer.write(NEWLINE);
  }

  /**
   * Writes a HTTP content value
   *
   * @param writer the writer
   * @param name the name of the value
   * @param value the value
   *
   * @throws IOException
   */
  public static void writeHTTPContent(Writer writer, String name, String value) throws IOException {
    writer.write("Content-Disposition: form-data; name=\"");
    writer.write(name);
    writer.write(NEWLINE);
    writer.write(NEWLINE);
    writer.write(value);
  }

  /**
   * Writes a HTTP content value
   *
   * @param first true if this is the first item; false otherwise
   * @param  writer      the writer
   * @param  boundary  the content boundary
   * @param  name      the field name
   * @param value the value
   *
   * @throws IOException
   */
  public static void writeHTTPContent(boolean first, Writer writer, String boundary, String name, int[] value)
          throws IOException {
    String    s;
    final int len = (value == null)
                    ? 0
                    : value.length;

    for (int i = 0; i < len; i++) {
      s = StringCache.valueOf(value[i]);
      writeFieldHeader(first, writer, boundary, name);
      writer.write(s);
    }
  }

  /**
   * Writes map values as HTTP content
   *
   * @param first true if this is the first item; false otherwise
   * @param context the context
   * @param writer the writer
   * @param boundary  the field boundary
   * @param values the map of values
   * @param expand  true to expand the string values using the specified context; false otherwise
   * @throws IOException
   */
  public static void writeHTTPContent(boolean first, iWidget context, Writer writer, String boundary, Map values,
          boolean expand)
          throws IOException {
    if (values != null) {
      Iterator<Map.Entry> it = values.entrySet().iterator();
      Map.Entry           e;
      String              value;
      String              name;
      Object              o;

      while(it.hasNext()) {
        e    = it.next();
        name = (String) e.getKey();
        o    = e.getValue();

        if (o instanceof File) {
          writeFile(first, writer, boundary, name, (File) o, "__attachment__".equals(name));
          first = false;
        } else if (o instanceof File[]) {
          for (File f : (File[]) o) {
            writeFile(first, writer, boundary, name, f, true);
            first = false;
          }
        } else {
          value = (o == null)
                  ? ""
                  : o.toString();
          writeFieldHeader(first, writer, boundary, name, "text/plain; charset=utf-8");
          first = false;

          if (value != null) {
            if (expand && (context != null)) {
              value = context.expandString(value, false);
              value = UTF8Helper.utf8String(value);
            }

            writer.write(value);
          }
        }
      }
    }
  }

  /**
   * Writes a HTTP content value
   *
   * @param first true if this is the first item; false otherwise
   * @param  writer      the writer
   * @param  boundary  the content boundary
   * @param  name      the field name
   * @param value the value
   * @param encode true to UTF-8 encode string; false otherwise
   *
   * @throws IOException
   */
  public static void writeHTTPContent(boolean first, Writer writer, String boundary, String name, String[] value,
          boolean encode)
          throws IOException {
    final int len = (value == null)
                    ? 0
                    : value.length;
    String    s;

    for (int i = 0; i < len; i++) {
      writeFieldHeader(first, writer, boundary, name);
      s = value[i];

      if (s != null) {
        if (encode) {
          s = UTF8Helper.utf8String(s);
        }

        writer.write(s);
        writer.write("\r\n");
      }
    }
  }

  /**
   * Writes a HTTP field value
   *
   * @param first whether this is the first widget to write its value
   * @param writer the writer to use
   * @param name the name of the value
   * @param value the value
   *
   * @return true if data was written; false otherwise
   * @throws IOException
   */
  public static boolean writeHTTPValue(boolean first, Writer writer, String name, int[] value) throws IOException {
    final int len = (value == null)
                    ? 0
                    : value.length;

    for (int i = 0; i < len; i++) {
      if (!first) {
        writer.write('&');
      }

      first = false;
      URLEncoder.encode(name, "ISO-8859-1", writer);
      writer.write('=');
      writer.write(StringCache.valueOf(value[i]));
    }

    return !first;
  }

  /**
   * Writes a HTTP field value
   *
   * @param first whether this is the first widget to write its value
   * @param writer the writer to use
   * @param name the name of the value
   * @param value the value
   *
   * @return true if data was written; false otherwise
   * @throws IOException
   */
  public static boolean writeHTTPValue(boolean first, Writer writer, String name, String[] value) throws IOException {
    final int len = (value == null)
                    ? 0
                    : value.length;

    for (int i = 0; i < len; i++) {
      if (!first) {
        writer.write('&');
      }

      first = false;
      URLEncoder.encode(name, "ISO-8859-1", writer);
      writer.write('=');
      writer.write(value[i]);
    }

    return !first;
  }

  /**
   * Encodes a set of attributes using standard HTTP form encoding rules
   *
   * @param first true if this is the first set of output values for the connection; false otherwise
   * @param context the context
   * @param writer the writer
   * @param values the map of values
   * @param expand  true to expand the string values using the specified context; false otherwise
   *
   * @return true if any data was output to the specified writer; false otherwise
   *
   * @throws IOException
   */
  public static boolean writeHTTPValues(boolean first, iWidget context, Writer writer, Map values, boolean expand)
          throws IOException {
    if (values != null) {
      Iterator<Map.Entry> it = values.entrySet().iterator();
      Map.Entry           e;
      String              value;
      Object              o;

      while(it.hasNext()) {
        e = it.next();

        if (!first) {
          writer.write('&');
        } else {
          first = false;
        }

        URLEncoder.encode((String) e.getKey(), "US-ASCII", writer);
        writer.write('=');
        o     = e.getValue();
        value = (o == null)
                ? null
                : o.toString();

        if (value != null) {
          if (expand && (context != null)) {
            context.expandString(value, true, writer);
          } else {
            writer.write(value);
          }
        }
      }
    }

    return !first;
  }

  /**
   * Writes a JSON field value
   *
   * @param first true if this is the first set of output values for the connection; false otherwise
   * @param writer the writer to use
   * @param name the name of the value
   * @param value the value
   *
   * @return true if data was written; false otherwise
   * @throws IOException
   */
  public static boolean writeJSONValue(boolean first, Writer writer, String name, int[] value) throws IOException {
    final int len = (value == null)
                    ? 0
                    : value.length;

    if (len == 0) {
      return false;
    }

    if (!first) {
      writer.write(",\n\t");
    }

    JSONHelper.encode(name, writer);
    writer.write(": [");

    int i = 0;

    while(i < len) {
      if (i != 0) {
        writer.write(',');
      }

      writer.write(StringCache.valueOf(value[i++]));
    }

    writer.write(']');

    return true;
  }

  /**
   * Writes a JSON field value
   *
   * @param first true if this is the first set of output values for the connection; false otherwise
   * @param writer the writer to use
   * @param name the name of the value
   * @param value the value
   * @return true if data was written; false otherwise
   * @throws IOException
   */
  public static boolean writeJSONValue(boolean first, Writer writer, String name, String[] value) throws IOException {
    final int len = (value == null)
                    ? 0
                    : value.length;

    if (len == 0) {
      return false;
    }

    if (!first) {
      writer.write(",\n\t");
    }

    JSONHelper.encode(name, writer);
    writer.write(": [");

    String s;
    int    i = 0;

    while(i < len) {
      if (i != 0) {
        writer.write(',');
      }

      s = value[i++];

      if (s == null) {
        writer.write("null");
      } else {
        JSONHelper.encode(s, writer);
      }
    }

    writer.write(']');

    return true;
  }

  /**
   * Writes a JSON field value
   *
   * @param context the context
   * @param writer the writer
   * @param values the map of values
   * @param first true if this is the first set of output values for the connection; false otherwise
   * @param expand  true to expand the string values using the specified context; false otherwise
   *
   * @return true if data was written; false otherwise
   * @throws IOException
   */
  public static boolean writeJSONValues(iWidget context, Writer writer, Map values, boolean first, boolean expand)
          throws IOException {
    if ((values != null) && (!values.isEmpty())) {
      Iterator<Map.Entry> it = values.entrySet().iterator();
      Map.Entry           e;
      String              value;
      Object              o;

      while(it.hasNext()) {
        if (!first) {
          writer.write(",\n\t");
        } else {
          first = false;
        }

        e = it.next();
        JSONHelper.encode((String) e.getKey(), writer);
        writer.write(": ");
        o     = e.getValue();
        value = (o == null)
                ? null
                : o.toString();

        if ((value != null) && expand && (context != null)) {
          value = context.expandString(value, false);
        }

        if (value != null) {
          JSONHelper.encode(value, writer);
        } else {
          writer.write("null");
        }
      }
    }

    return !first;
  }

  /**
   *   Writes the header portion for a HTTP field other than a file upload or multipart field
   *
   * @param first true if this is the first item; false otherwise
   * @param  writer      the writer
   * @param  name      the field name
   * @param  boundary  the content boundary
   * @param partboundary the part boundary
   * @throws  IOException  on input/output errors
   */
  public static void writeMultiPartFieldHeader(boolean first, Writer writer, String boundary, String name,
          String partboundary)
          throws IOException {
    if ((name == null) || (name.length() == 0)) {
      throw new IllegalArgumentException("Field name cannot be null or empty.");
    }

    if (first) {
      writer.write(NEWLINE);
    }

    writer.write(BOUNDARY_PREFIX);
    writer.write(boundary);
    writer.write(NEWLINE);
    // write content header
    writer.write("Content-Disposition: form-data; name=\"" + name + "\"");
    writer.write(NEWLINE);
    writer.write("Content-type: multipart/mixed, boundary=" + partboundary);
    writer.write(NEWLINE);
    writer.write(NEWLINE);
  }

  /**
   *   Encodes a set of attributes using standard HTTP form encoding rules
   *
   *   @param first true if this is the first set of output values for the connection; false otherwise
   *   @param context the context
   *   @param writer the writer
   *   @param values the map of values
   *
   *   @return true if any data was output to the specified writer; false otherwise
   *
   *   @throws IOException
   */
  public static boolean writeRawHTTPValues(boolean first, iWidget context, Writer writer, Map values)
          throws IOException {
    if (values != null) {
      Iterator<Map.Entry> it = values.entrySet().iterator();
      Map.Entry           e;
      String              value;
      Object              o;

      while(it.hasNext()) {
        e = it.next();

        if (!first) {
          writer.write('&');
        } else {
          first = false;
        }

        writer.write((String) e.getKey());
        writer.write('=');
        o     = e.getValue();
        value = (o == null)
                ? null
                : o.toString();

        if (value != null) {
          writer.write(value);
        }
      }
    }

    return !first;
  }
}
