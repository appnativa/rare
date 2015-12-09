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

package com.appnativa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import com.appnativa.util.io.BufferedReaderEx;

/**
 * This class represents a set of general purpose streams, stream functions and
 * sub-streams. Sub-streams are streams that work on other streams providing
 * some special functionality. When these streams are closed the underlying
 * stream remains open.
 *
 * <p>
 * A sub-stream should always be closed when it is no longer needed, as the
 * close operation might be required to complete any pending operations. These
 * streams are meant to be short-lived
 * </p>
 *
 * @author Don DeCoteau
 * @version 1.0
 */
public class Streams {

  /**
   * Returns a writer thats writes to the specified character array
   * 
   * @param ca
   *          the character array
   * @return the writer
   */
  public static CharArrayWriter charArrayWriter(CharArray ca) {
    return new CharArrayWriter(ca);
  }

  /**
   * Gets a reader for decoding the specified encoding Only base64, base85 and
   * ascii85 are supported
   *
   * @param in
   *          the input stream
   * @param enc
   *          the encoding
   * @param cs
   *          the character set
   *
   * @return the reader
   */
  public static Reader getDecodingReader(InputStream in, String enc, String cs, int bufferSize) throws IOException {
    if (enc != null) {
      if (enc.equalsIgnoreCase("base64")) {
        in = Base64.decodingStream(in);
      } else if (enc.equalsIgnoreCase("base85") || enc.equalsIgnoreCase("ascii85")) {
        in = new ASCII85InputStream(in);
      }
      else if(enc.equals("application/x-www-form-urlencoded")) {
        return new BufferedReaderEx(new URLDecodingReader(in, cs),256);
      }
    }

    if (bufferSize < 1) {
      bufferSize = 1024;
    }

    return new BufferedReaderEx(new InputStreamReader(in, cs), bufferSize);
  }

  /**
   * Reads the contents of a reader and converts it to a string.
   * <p>
   * The reader is NOT closed prior to this method returning
   * </p>
   *
   * @param reader
   *          the reader
   * @return the string
   */
  @SuppressWarnings("resource")
  public static String readerToString(Reader reader) throws IOException {
    if (reader == null) {
      return null;
    }

    int n = 0;
    int pos = 0;
    int len = 1024;
    CharArray out = new CharArray(len);

    while ((n = reader.read(out.A, pos, len)) != -1) {
      pos += n;
      out.ensureCapacity(pos + 1024);
    }

    out._length = pos;

    return out.toString();
  }

  /**
   * Reads the contents of the specified reader and writes it out to the
   * specified writer.
   * <p>
   * The reader is NOT closed prior to this method returning
   * </p>
   *
   * @param reader
   *          the reader
   * @param writer
   *          the writer
   * @param block
   *          an optional character block to use to buffer the reads
   *
   * @return the number of characters read
   * @throws IOException
   *           if an I/O error occurs
   */
  public static int readerToWriter(Reader reader, Writer writer, char[] block) throws IOException {
    int len = 0, count = 0;

    if (block == null) {
      block = new char[1024];
    }

    while ((len = reader.read(block)) != -1) {
      if (len > 0) {
        writer.write(block, 0, len);
        count += len;
      }
    }

    writer.flush();

    return count;
  }

  /**
   * Reads the contents of an input stream and converts it to a byte array.
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param stream
   *          the stream to convert
   * @param ba
   *          an optional byte array to use for the conversion
   *
   * @return a byte array
   */
  public static ByteArray streamToBytes(InputStream stream, ByteArray ba) throws IOException {
    if (stream == null) {
      return null;
    }

    if (ba == null) {
      ba = new ByteArray();
    }

    int n = 0;
    int pos = 0;
    int len = 1024;

    ba.ensureCapacity(ba._length + len);

    while ((n = stream.read(ba.A, pos, len)) != -1) {
      pos += n;
      ba.ensureCapacity(pos + 1024);
    }

    ba._length += pos;

    return ba;
  }
  
  /**
   * Reads the contents of an input stream until there are no more bytes to be read
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param stream
   *          the stream to be drained
   *@param silent true to silent ignore IO exceptions; false to throw exceptions
   * @return the number of bytes drained
   */
  public static long drain(InputStream stream, boolean silent) throws IOException {
    if (stream == null) {
      return 0;
    }
    
    byte b[]=new byte[256];
    int n;
    long len=0;
    try {
      while ((n = stream.read(b,0,256)) != -1) {
        len += n;
      }
    }
    catch(IOException e) {
      if(!silent) {
        throw e;
      }
    }
    return len;
  }

  /**
   * Reads the contents of the specified input stream and writes it out to the
   * specified output stream
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param istream
   *          the input stream
   * @param ostream
   *          the output stream
   * @param block
   *          an optional byte block to use to buffer the reads
   *
   * @return the number of bytes read/written
   * @throws IOException
   *           if an I/O error occurs
   */
  public static int streamToStream(InputStream istream, OutputStream ostream, byte[] block) throws IOException {
    if ((istream == null) || (ostream == null)) {
      return 0;
    }

    int len = 0, count = 0;

    if (block == null) {
      block = new byte[1024];
    }

    while ((len = istream.read(block)) != -1) {
      if (len > 0) {
        ostream.write(block, 0, len);
        count += len;
      }
    }

    ostream.flush();

    return count;
  }

  /**
   * Reads the contents of an input stream and converts it to an array of
   * strings The bytes are converted to characters using iso-8859-1 encoding
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param stream
   *          the stream to convert
   *
   * @return a character array
   */
  public static List<String> streamToStrings(InputStream stream) throws IOException {
    List<String> list = new ArrayList<String>();

    streamToStrings(stream, list);

    return list;
  }

  /**
   * Reads the contents of an input stream and converts it to a string The bytes
   * are converted to characters using iso-8859-1 encoding.
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param stream
   *          the stream to convert
   */
  public static String streamToString(InputStream stream) throws IOException {
    return readerToString(new InputStreamReader(stream));
  }

  /**
   * Reads the contents of an input stream and converts it to an array of
   * strings The bytes are converted to characters using iso-8859-1 encoding
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param stream
   *          the stream to convert
   * @param list
   *          the destination list
   */
  public static void streamToStrings(InputStream stream, List<String> list) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String s;

    while ((s = reader.readLine()) != null) {
      list.add(s);
    }
  }

  /**
   * Reads the contents of the specified input stream and writes it out to the
   * specified writer. The stream is converted to bytes using iso-8859-1
   * encoding.
   * <p>
   * The stream is NOT closed prior to this method returning
   * </p>
   *
   * @param stream
   *          the input stream
   * @param writer
   *          the writer
   * @param block
   *          an optional character block to use to buffer the reads
   *
   * @return the number of bytes read
   * @throws IOException
   *           if an I/O error occurs
   */
  public static int streamToWriter(InputStream stream, Writer writer, char[] block) throws IOException {
    int len = 0, count = 0;
    InputStreamReader ir = new InputStreamReader(stream, "iso-8859-1");

    if (block == null) {
      block = new char[1024];
    }

    while ((len = ir.read(block)) != -1) {
      if (len > 0) {
        writer.write(block, 0, len);
        count += len;
      }
    }

    writer.flush();

    return count;
  }

  /**
   * A class that implements a writer based on a character array
   */
  public static class CharArrayWriter extends Writer {

    /**  */
    CharArray array;

    /**
     * Creates a new writer thats writes to the specified character array
     *
     * @param ca
     *          the character array
     */
    public CharArrayWriter(CharArray ca) {
      super();
      array = ca;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void close() throws IOException {
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void flush() throws IOException {
    }

    /**
     * Returns the underlying character array that backs the stream
     * 
     * @return the underlying character array that backs the stream
     */
    public CharArray getArray() {
      return array;
    }

    /**
     * {@inheritDoc}
     *
     * @param c
     *          {@inheritDoc}
     */
    public void write(int c) {
      array.append((char) c);
    }

    /**
     * {@inheritDoc}
     *
     * @param str
     *          {@inheritDoc}
     */
    public void write(String str) {
      array.append(str);
    }

    /**
     * {@inheritDoc}
     *
     * @param chars
     *          {@inheritDoc}
     * @param pos
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @throws IOException
     */
    public void write(char[] chars, int pos, int len) throws IOException {
      array.append(chars, pos, len);
    }

    /**
     * {@inheritDoc}
     *
     * @param str
     *          {@inheritDoc}
     * @param off
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     */
    public void write(String str, int off, int len) {
      array.append(str.substring(off, off + len));
    }
  }

  /**
   * The fixed-length input stream reads a fixed number of bytes from an
   * underlying stream. Once the specified number of bytes are read no more
   * bytes are able to be read from this stream.
   */
  public static class FixedLengthInputStream extends InputStream {

    /**  */
    int         fixedLen;

    /**  */
    int         readLen;

    /**  */
    InputStream stream;

    /**
     * Constructs a new fixed-length input stream on top of the specified input
     * stream.
     *
     * @param stream
     *          the source stream
     * @param fixedlen
     *          the lenght of the records
     */
    public FixedLengthInputStream(InputStream stream, int fixedlen) {
      fixedLen = fixedlen;
      this.stream = stream;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int available() throws IOException {
      int n = stream.available();
      int p = fixedLen - readLen;

      return (n < p) ? n : p;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read() throws IOException {
      if (readLen == fixedLen) {
        return -1;
      }

      int c = stream.read();

      if (c == -1) {
        readLen = fixedLen;
      }

      return c;
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read(byte[] b) throws IOException {
      return read(b, 0, b.length);
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     * @param pos
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read(byte[] b, int pos, int len) throws IOException {
      if (readLen == fixedLen) {
        return -1;
      }

      int n = fixedLen - readLen;

      if (n < len) {
        len = n;
      }

      int clen = stream.read(b, pos, len);

      if (clen == -1) {
        readLen = fixedLen;
      } else {
        readLen += clen;
      }

      return clen;
    }
  }

  /**
   * A reader for the ISO88591 character set
   *
   *
   * @version 0.3, 2007-05-04
   * @author Don DeCoteau
   */
  public static class ISO88591Reader extends Reader {

    /**  */
    final static int buffSize = 1024;

    /**  */
    int              blen     = 0;

    /**  */
    int              bpos     = 0;

    /**  */
    byte             bytes[]  = new byte[buffSize];

    /**  */
    InputStream      stream;

    /**
     * Constructs a new reader
     *
     * @param stream
     *          the input stream to read wrom
     */
    public ISO88591Reader(InputStream stream) {
      this.stream = stream;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void close() throws IOException {
      stream.close();
    }

    /**
     * {@inheritDoc}
     *
     * @param cbuf
     *          {@inheritDoc}
     * @param off
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read(char[] cbuf, int off, int len) throws IOException {
      if (blen < 1) {
        bpos = 0;
        blen = stream.read(bytes, 0, buffSize);
      }

      len = (len > blen) ? blen : len;

      if (len > 0) {
        int i = bpos;
        int n = 0;

        while (n < len) {
          cbuf[off++] = (char) (bytes[i++] & 0xff);
          n++;
        }

        blen -= len;
        bpos = i;
      }

      return len;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public boolean ready() {
      try {
        return (blen > 0) ? true : stream.available() > 0;
      } catch (IOException ex) {
        return false;
      }
    }
  }

  /**
   * A writer for the ISO88591 character set
   *
   * @version 0.3, 2007-05-04
   * @author Don DeCoteau
   */
  public static class ISO88591Writer extends Writer {

    /**  */
    final static int buffSize = 1024;

    /**  */
    byte             bytes[]  = new byte[buffSize];

    /**  */
    OutputStream     stream;

    /**
     * Constructs a new writer
     *
     * @param stream
     *          the output stream to write to
     */
    public ISO88591Writer(OutputStream stream) {
      this.stream = stream;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void close() throws IOException {
      stream.close();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void flush() throws IOException {
      stream.flush();
    }

    /**
     * {@inheritDoc}
     *
     * @param cbuf
     *          {@inheritDoc}
     * @param off
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @throws IOException
     */
    public void write(char[] cbuf, int off, int len) throws IOException {
      int n = (len > buffSize) ? buffSize : len;
      int i;

      while (len > 0) {
        i = 0;

        while (i < n) {
          bytes[i++] = (byte) cbuf[off++];
        }

        stream.write(bytes, 0, n);
        len -= n;
        n = (len > buffSize) ? buffSize : len;
      }
    }
  }

  /**
   * This class implements a stream that will wait infinitely for data to be
   * received
   */
  public static class InfiniteWaitStream extends InputStream {

    /**  */
    int         sleep = 100;

    /**  */
    InputStream stream;

    /**
     * Creates a new infinite wait stream
     * 
     * @param stream
     *          the source stream
     */
    public InfiniteWaitStream(InputStream stream) {
      super();
      this.stream = stream;
    }

    /**
     * Creates a new infinite wait stream
     * 
     * @param stream
     *          the source stream
     * @param poll
     *          the polling interval to use when checking for available data
     */
    public InfiniteWaitStream(InputStream stream, int poll) {
      super();
      this.stream = stream;
      this.sleep = poll;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int available() throws IOException {
      return stream.available();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void close() throws IOException {
      stream.close();
    }

    /**
     * {@inheritDoc}
     *
     * @param readlimit
     *          {@inheritDoc}
     */
    public void mark(int readlimit) {
      stream.mark(readlimit);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public boolean markSupported() {
      return stream.markSupported();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read() throws IOException {
      int n = -1;

      while ((n = stream.read()) == -1) {
        try {
          Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
      }

      return n;
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     * @param off
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read(byte[] b, int off, int len) throws IOException {
      int n = -1;

      while ((n = stream.read(b, off, len)) == -1) {
        try {
          Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
      }

      return n;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void reset() throws IOException {
      stream.reset();
    }

    /**
     * {@inheritDoc}
     *
     * @param skip
     *          {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public long skip(long skip) throws IOException {
      return stream.skip(skip);
    }
  }

  /**
   * The segmented input stream reads variable length records from an input
   * stream and presents then as a single continuous stream. It is meant to be
   * used in conjunction with a segmented output stream.
   */
  public static class SegmentedInputStream extends InputStream {

    /**  */
    byte[]      block;

    /**  */
    int         blocklen;

    /**  */
    boolean     done;

    /**  */
    int         length;

    /**  */
    int         reclen;

    /**  */
    InputStream stream;

    /**  */
    int         thePos;

    /**
     * Constructs a new segmented input stream on top of the specified input
     * stream.
     *
     * @param stream
     *          the source stream
     */
    public SegmentedInputStream(InputStream stream) {
      this(stream, null);
    }

    /**
     * Constructs a new segmented input stream on top of the specified input
     * stream.
     *
     * @param stream
     *          the source stream
     * @param block
     *          an optional character block to use to buffer the reads
     */
    public SegmentedInputStream(InputStream stream, byte[] block) {
      this.stream = stream;
      this.block = (block == null) ? new byte[1024] : block;
      blocklen = this.block.length;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int available() throws IOException {
      int n = length - thePos;

      if (n < 1) {
        n = 0; // we dont know because the available bytes contains a length specifier
      }

      return n;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read() throws IOException {
      if (done) {
        return -1;
      }

      if (thePos >= length) {
        fillBuffer();

        return read();
      }

      return (block[thePos++] & 0xff);
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     * @param off
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    public int read(byte[] b, int off, int len) throws IOException {
      if (done) {
        return -1;
      }

      if (thePos >= length) {
        fillBuffer();

        return read(b, off, len);
      }

      if (b == null) {
        throw new NullPointerException();
      } else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
        throw new IndexOutOfBoundsException();
      }

      if ((thePos + len) > length) {
        len = length - thePos;
      }

      if (len <= 0) {
        return 0;
      }

      System.arraycopy(block, thePos, b, off, len);
      thePos += len;

      return len;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    void fillBuffer() throws IOException {
      if (reclen > 0) {
        int len = (reclen > blocklen) ? blocklen : reclen;

        len = stream.read(block, 0, len);

        if (len == -1) {
          done = true;
        } else {
          reclen -= len;
          thePos = 0;
          length = len;
        }

        return;
      }

      reclen = aStreamer.readVarLength(stream);

      if (reclen == -1) {
        done = true;
      } else {
        fillBuffer();
      }
    }
  }

  /**
   * The segmented output stream causes variable length records to be output
   * with each write operation. It is meant to be used in conjunction with a
   * segmented input stream.
   * <p>
   * Closing the stream does not close the underlying stream. Instead a null
   * record is written to the stream. This null record identifies to a segmented
   * input stream that this is the end of the data stream.
   * </p>
   */
  public static class SegmentedOutputStream extends OutputStream {

    /**  */
    OutputStream stream;

    /**
     * Constructs a new segmented output stream on top of the specified output
     * stream.
     *
     * @param stream
     *          the source stream
     */
    public SegmentedOutputStream(OutputStream stream) {
      this.stream = stream;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void close() throws IOException {
      if (stream != null) {
        aStreamer.writeVarLength(-1, stream);
        stream.flush();
        stream = null;
      }
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void flush() throws IOException {
      if (stream != null) {
        stream.flush();
      } else {
        throw new IOException("Stream closed");
      }
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     *
     * @throws java.io.IOException
     *           {@inheritDoc}
     */
    public void write(byte[] b) throws java.io.IOException {
      if (stream != null) {
        aStreamer.writeBytes(stream, b, 0, b.length);
      } else {
        throw new IOException("stream closed");
      }
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     *
     * @throws java.io.IOException
     *           {@inheritDoc}
     */
    public void write(int b) throws java.io.IOException {
      aStreamer.writeVarLength(1, stream);
      stream.write(b);
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     * @param pos
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @throws java.io.IOException
     *           {@inheritDoc}
     */
    public void write(byte[] b, int pos, int len) throws java.io.IOException {
      if (stream != null) {
        aStreamer.writeBytes(stream, b, pos, len);
      } else {
        throw new IOException("stream closed");
      }
    }
  }

  /**
   * This class provides a output stream that simple does not close the
   * underlying stream when the close method is called.
   */
  public static class SimpleSubOutStream extends OutputStream {

    /**  */
    OutputStream stream;

    /**
     * Constructs a new simple sub-output stream on top of the specified output
     * stream.
     *
     * @param stream
     *          the source stream
     */
    public SimpleSubOutStream(OutputStream stream) {
      this.stream = stream;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void close() throws IOException {
      stream = null;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    public void flush() throws IOException {
      if (stream != null) {
        stream.flush();
      } else {
        throw new IOException("Stream closed");
      }
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     *
     * @throws java.io.IOException
     *           {@inheritDoc}
     */
    public void write(byte[] b) throws java.io.IOException {
      if (stream != null) {
        stream.write(b, 0, b.length);
      } else {
        throw new IOException("Stream closed");
      }
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     *
     * @throws java.io.IOException
     *           {@inheritDoc}
     */
    public void write(int b) throws java.io.IOException {
      if (stream != null) {
        stream.write(b);
      } else {
        throw new IOException("Stream closed");
      }
    }

    /**
     * {@inheritDoc}
     *
     * @param b
     *          {@inheritDoc}
     * @param pos
     *          {@inheritDoc}
     * @param len
     *          {@inheritDoc}
     *
     * @throws java.io.IOException
     *           {@inheritDoc}
     */
    public void write(byte[] b, int pos, int len) throws java.io.IOException {
      if (stream != null) {
        stream.write(b, pos, len);
      } else {
        throw new IOException("Stream closed");
      }
    }
  }

  static class URLDecodingReader extends Reader {

    private InputStream stream;
    private Reader      reader;
    private String      charset;

    public URLDecodingReader(InputStream stream, String charset) {
      super();
      this.stream = stream;
      if(charset==null) {
        charset="utf-8";
      }
      this.charset = charset;
    }

    public int read(CharBuffer target) throws IOException {
      return getReader().read(target);
    }

    public int read() throws IOException {
      return getReader().read();
    }

    public int read(char[] cbuf) throws IOException {
      return getReader().read(cbuf);
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
      return getReader().read(cbuf, off, len);
    }

    public long skip(long n) throws IOException {
      return getReader().skip(n);
    }

    public boolean ready() throws IOException {
      return getReader().ready();
    }

    public boolean markSupported() {
      return true;
    }

    public void mark(int readAheadLimit) throws IOException {
      getReader().mark(readAheadLimit);
    }

    public void reset() throws IOException {
      getReader().reset();
    }

    @Override
    public void close()  throws IOException{
      stream.close();
    }

    private final Reader getReader() throws IOException {
      if (reader == null) {
        String s=streamToString(stream);
        s=URLEncoder.decode(s,charset);
        reader = new StringReader(s);
      }
      return reader;
    }
  }
}
