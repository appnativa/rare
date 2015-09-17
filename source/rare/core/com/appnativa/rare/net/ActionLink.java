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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.ClosedChannelException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aRare;
import com.appnativa.rare.spot.Link;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.util.Base64;
import com.appnativa.util.ByteArray;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams;
import com.appnativa.util.UnescapingReader;
import com.appnativa.util.iCancelable;
import com.google.j2objc.annotations.Weak;

/**
 * This class represents a link to a resource
 *
 * @author Don DeCoteau
 */
public class ActionLink implements Runnable, iActionListener, Cloneable, iCancelable {

  /** the default column separator */
  public static final char DEFAULT_COL_SEPARATOR = '|';

  /**
   * the prefix for the column separator specifier in a mime type string
   * (separator=)
   */
  public static final String MIME_COLUMN_SEPARATOR_PREFIX = "separator=";

  /**
   * the prefix for the linked data separator specifier in a mime type string
   * (ldseparator=)
   */
  public static final String MIME_LD_SEPARATOR_PREFIX = "ldseparator=";

  /**
   * the prefix for the row information separator specifier in a mime type
   * string (riseparator=)
   */
  public static final String MIME_RI_SEPARATOR_PREFIX = "riseparator=";

  /** header indicating that the is more information available */
  public static final String PAGING_HAS_MORE = "X-Paging-Has-More";

  /** header indicating that the url to use to get the next page of information */
  public static final String PAGING_NEXT = "X-Paging-Next";

  /** header indicating that the url to use to get the next page of information */
  public static final String PAGING_PREVIOUS = "X-Paging-Previous";

  /** header indicating for custom link information */
  public static final String LINK_INFO = "X-Link-Info";

  /** Default read timeout for connections */
  public static int            defaultReadTimeout = 0;
  private static final String  DEFAULT_BOUNDARY   = "RarePart_CABC4B4F_CAB1_4C07_A6E8_E5A3169BFC48_RarePart";
  private static iErrorHandler _errorHandler;

  /** the URL */
  protected URL contextURL;

  /** the widget context */
  @Weak
  protected iWidget contextWidget;

  /** the MIME type that will be returned (if it is not null) */
  protected String mimeType;

  /** a string representation of the URL */
  protected String stringURL;

  /** the name of the target for the link */
  protected String targetName;

  /** the URL */
  protected URL theURL;

  /** the connection object */
  protected iURLConnection                   urlConnection;
  protected char                             colSeparator     = DEFAULT_COL_SEPARATOR;
  protected char                             ldSeparator      = '\0';
  protected int                              readTimeout      = defaultReadTimeout;
  protected RequestMethod                    requestMethod    = RequestMethod.GET;
  protected RequestEncoding                  requestEncoding  = RequestEncoding.HTTP_FORM;
  protected char                             rowInfoSeparator = '\0';
  protected boolean                          deferred         = true;
  protected volatile boolean                 canceled;
  protected String                           charset;
  protected boolean                          columnSeparatorSet;
  protected String                           contentEncoding;
  protected String                           contentType;
  protected String                           defaultCharset;
  protected boolean                          dontUseCache;
  protected iErrorHandler                    errorHandler;
  protected boolean                          escape;
  protected boolean                          inlineURL;
  protected Map<String, Object>              linkAttributes;
  protected String                           linkInfo;
  protected iMultipartMimeHandler.iMultipart multiPart;
  protected boolean                          multiPartForm;
  protected Map<String, String>              origWindowOptions;
  protected Writer                           outputWriter;
  protected boolean                          pagingHasMore;
  protected String                           pagingNext;
  protected String                           pagingPrevious;
  protected String                           parserClassName;
  protected String                           partBoundary;
  protected UIPoint                          popupLocation;
  protected String                           requestCharset;
  protected Map<String, Object>              requestHeaders;
  protected String                           requestType;
  protected String                           statusMessage;
  protected boolean                          unescape;
  protected boolean                          unescapeUnicodeOnly;
  protected Viewer                           viewerConfiguration;
  protected Map<String, String>              windowOptions;
  protected float                            imageDensity = PlatformHelper.getUnscaledImageDensity();

  /**
   * Creates a new action link
   */
  public ActionLink() {}

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   */
  public ActionLink(iWidget context) {
    contextWidget = context;
  }

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   * @param connection
   *          the connection for the link
   */
  public ActionLink(iWidget context, iURLConnection connection) {
    contextWidget = context;
    theURL        = connection.getURL();
    urlConnection = connection;
    inlineURL     = connection instanceof InlineURLConnection;
  }

  /**
   * Creates a new action link using the specified <code>Link</code> object
   *
   * @param context
   *          the widget context for the link
   * @param link
   *          the link configuration object
   */
  public ActionLink(iWidget context, Link link) {
    this(context, link.url);
    contextWidget     = context;
    linkAttributes    = DataParser.parseNameValuePairs(link.attributes);
    requestHeaders    = DataParser.parseNameValuePairs(link.headers);
    targetName        = Utils.getTarget(link);
    requestMethod     = RequestMethod.valueOf(link.requestType.stringValue().toUpperCase(Locale.US));
    requestEncoding   = RequestEncoding.valueOf(link.requestEncoding.stringValue().toUpperCase(Locale.US));
    statusMessage     = link.statusMessage.getValue();
    origWindowOptions = link.target.spot_getAttributesEx();
  }

  /**
   * Creates a new action link using the specified
   * <code>SPOTPrintableString </code> object
   *
   * @param context
   *          the widget context for the link
   * @param url
   *          the URL for the link
   */
  public ActionLink(iWidget context, SPOTPrintableString url) {
    contextWidget = context;

    if (url.spot_getLinkedData() instanceof Viewer) {
      setViewerConfiguration((Viewer) url.spot_getLinkedData());
      url.spot_setLinkedData(null);
    }

    String s          = url.spot_getAttribute("inline");
    String inlineData = null,
           encoding   = null;

    if ((s != null) && (s.length() > 0)) {
      inlineURL = SNumber.booleanValue(s);
    } else if (url.spot_isValuePreformatted()) {
      inlineURL = true;
    }

    s = url.getValue();

    if (inlineURL || (s == null)) {
      inlineData = s;
    } else if (s.startsWith("data:")) {
      int n = s.indexOf(',');

      if (n == -1) {
        inlineData = "";
      } else {
        inlineData = s.substring(n + 1);
        s          = s.substring(5, n);
        n          = s.indexOf(';');

        if (n != -1) {
          mimeType = s.substring(0, n);
          s        = s.substring(n + 1).trim();

          if (s.endsWith("base64")) {
            String cs = JavaURLConnection.getCharset(s, "US-ASCII");
            byte[] b  = Base64.decode(inlineData);

            try {
              inlineData = new String(b, cs);
            } catch(UnsupportedEncodingException ex) {
              throw new ApplicationException(ex);
            }
          }
        } else {
          mimeType = s;
        }

        mimeType = mimeType.trim();

        if (mimeType.length() == 0) {
          mimeType = null;
        }
      }

      inlineURL = true;
    } else {
      stringURL = s;
    }

    deferred = !"false".equals(url.spot_getAttribute("deferred"));
    s        = url.spot_getAttribute("density");

    if (s != null) {
      if (s.length() > 0) {
        imageDensity = SNumber.floatValue(s);
      }
    }

    s = url.spot_getAttribute("columnSeparator");

    if (s != null) {
      if (s.length() > 0) {
        colSeparator       = s.charAt(0);
        columnSeparatorSet = true;
      }
    }

    s = url.spot_getAttribute("ldSeparator");

    if (s != null) {
      if (s.length() > 0) {
        ldSeparator = s.charAt(0);
      }
    }

    s = url.spot_getAttribute("riSeparator");

    if (s != null) {
      if (s.length() > 0) {
        rowInfoSeparator = s.charAt(0);
      }
    }

    if (mimeType == null) {
      mimeType = url.spot_getAttribute("mimeType");
    }

    s = url.spot_getAttribute("contentEncoding");

    if (s != null) {
      if (s.length() > 0) {
        encoding = s;
      }
    }

    contentEncoding = encoding;
    s               = url.spot_getAttribute("unescape");

    if (s != null) {
      if (s.equalsIgnoreCase("unicode")) {
        unescape            = true;
        unescapeUnicodeOnly = true;
      } else {
        unescape = s.equalsIgnoreCase("true");
      }
    }

    s = url.spot_getAttribute("parser");

    if ((s != null) && (s.length() > 0)) {
      parserClassName = s;
    }

    if (inlineURL) {
      try {
        theURL = InlineURLConnection.createURL(inlineData, mimeType, encoding);
      } catch(Exception e) {
        throw new ApplicationException(e);
      }
    } else if ((stringURL != null) && stringURL.startsWith("script:")) {
      try {
        theURL = ScriptURLConnection.createURL(context, stringURL.substring(7), mimeType);
      } catch(MalformedURLException e) {
        throw new ApplicationException(e);
      }

      stringURL = null;
    }
  }

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   * @param url
   *          the URL
   */
  public ActionLink(iWidget context, String url) {
    contextWidget = context;
    stringURL     = url;
  }

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   * @param url
   *          the URL
   * @param type
   *          the MIME type (can be null)
   */
  public ActionLink(iWidget context, String url, String type) {
    contextWidget = context;
    stringURL     = url;
    mimeType      = type;
  }

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   * @param url
   *          the URL for the link
   */
  public ActionLink(iWidget context, URL url) {
    contextWidget = context;
    theURL        = url;
  }

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   * @param url
   *          the URL
   * @param type
   *          the MIME type (can be null)
   */
  public ActionLink(iWidget context, URL url, String type) {
    contextWidget = context;
    theURL        = url;
    mimeType      = type;
  }

  /**
   * Creates a new action link
   *
   * @param context
   *          the widget context for the link
   * @param cfg
   *          the viewer configuration for the link
   */
  public ActionLink(iWidget context, Viewer cfg) {
    contextWidget       = context;
    viewerConfiguration = cfg;
  }

  /**
   * Creates a new action link
   *
   * @param url
   *          the URL
   */
  public ActionLink(String url) {
    stringURL = url;
  }

  /**
   * Creates a new action link. The specified data becomes the data for the
   * link.
   *
   * @param data
   *          the links data
   * @param type
   *          the MIME type
   */
  public ActionLink(String data, String type) {
    try {
      theURL    = InlineURLConnection.createURL(data, type, null);
      mimeType  = type;
      inlineURL = true;
    } catch(MalformedURLException e) {
      throw new ApplicationException(e);
    }
  }

  /**
   * Creates a new action link
   *
   * @param url
   *          the URL
   */
  public ActionLink(URL url) {
    theURL = url;
  }

  /**
   * Creates a new action link
   *
   * @param url
   *          the URL
   * @param type
   *          the MIME type (can be null)
   */
  public ActionLink(URL url, String type) {
    theURL   = url;
    mimeType = type;
  }

  /**
   * Invoked automatically when the links underlying action is triggered. This
   * will cause the link to be activated.
   *
   * @param e
   *          the event that triggered the action
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Platform.invokeLater(this);
  }

  /**
   * Activates the link
   */
  public void activate() {
    run();
  }

  /**
   * Adds another a file to an upload started with the {@link #startFileUpload}
   * method.
   *
   * @param writer
   *          the returned by the {@link #startFileUpload} method
   * @param name
   *          the name of the field that the file data is associated with
   * @param mimeType
   *          the MIME type for the file (if null then the file's extension will
   *          be used to determine the mime-type)
   * @param fileName
   *          the file name (cannot be null)
   * @throws java.io.IOException
   */
  public void addAnotherUploadFile(Writer writer, String name, String mimeType, String fileName) throws IOException {
    FormHelper.writeFileHeader(false, writer, getPartBoundary(), name, mimeType, fileName, false);
  }

  @Override
  public void cancel(boolean canInterrupt) {
    try {
      close();
    } catch(Exception ignore) {}

    canceled = true;
  }

  /**
   * Clears the link's properties including the URL
   */
  public void clear() {
    try {
      close();
    } catch(Exception ignore) {}

    theURL       = null;
    colSeparator = DEFAULT_COL_SEPARATOR;

    if (linkAttributes != null) {
      linkAttributes.clear();
    }

    windowOptions       = null;
    viewerConfiguration = null;
    mimeType            = null;
    targetName          = null;
    multiPartForm       = false;
    statusMessage       = null;
    contextWidget       = null;
  }

  /**
   * Creates and returns a copy of this object.
   *
   * @return a copy of this object.
   */
  @Override
  public Object clone() {
    ActionLink link;

    try {
      link                = (ActionLink) super.clone();
      link.urlConnection  = null;
      link.canceled       = false;
      link.pagingHasMore  = false;
      link.linkInfo       = null;
      link.pagingNext     = null;
      link.pagingPrevious = null;

      if (linkAttributes != null) {
        link.linkAttributes = new HashMap<String, Object>(linkAttributes);
      }

      link.closeEx();

      return link;
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  /**
   * Close a link that was previously activated. After the link has been closed
   * it can be reactivated to initiate another connection
   */
  public void close() {
    try {
      if (urlConnection != null) {
        urlConnection.close();
      }
    } finally {
      urlConnection = null;
      outputWriter  = null;
      multiPart     = null;

      if (stringURL != null) {
        theURL = null;
      }
    }
  }

  /**
   * Close a link that was previously activated. After the link has been closed
   * it can be reactivated to initiate another connection. Any generated
   * exception is silently discarded
   *
   */
  public void closeEx() {
    try {
      close();
    } catch(Throwable ignore) {}
  }

  /**
   * Deletes the links data using the DELETE method
   *
   * @throws java.io.IOException
   */
  public void deleteData() throws IOException {
    this.setRequestMethod(RequestMethod.DELETE);
    hit();
  }

  /**
   * Finishes a file upload started with the {@link #startFileUpload} method.
   * This method does not close the link however no more data can be written to
   * the specified writer.
   *
   * @param writer
   *          the returned by the {@link #startFileUpload} method
   * @throws java.io.IOException
   */
  public void finishFileUpload(Writer writer) throws IOException {
    FormHelper.writeBoundaryEnd(writer, getPartBoundary());
    this.closeOutput();
  }

  /**
   * Gets the attributes that will to be sent along as part of the link's form
   * data (similar to hidden values in HTTP forms)
   *
   * @return the map of attributes or null
   */
  public Map<String, Object> getAttributes() {
    return linkAttributes;
  }

  /**
   * Gets the character set for this connection's data stream
   *
   * @return the character set for this connection's data stream
   *
   * @throws IOException
   */
  public String getCharset() throws IOException {
    return (urlConnection == null)
           ? charset
           : urlConnection.getCharset();
  }

  /**
   * Gets the collection that the link represents
   *
   * @return the collection that the link represents
   * @throws Exception
   */
  public Collection<RenderableDataItem> getCollection() throws IOException {
    iWidget w = contextWidget;

    if (w == null) {
      w = Platform.getContextRootViewer();
    }

    iURLConnection u = this.getConnection();
    Object         o = u.getConnectionObject();

    if (o instanceof CollectionURLConnection) {
      try {
        return ((CollectionURLConnection) o).getCollection();
      } finally {
        close();
      }
    }

    return DataItemParserHandler.parse(w, this, -1);
  }

  /**
   * Get the link's column separator token
   *
   * @return the link's column separator token
   */
  public char getColumnSeparator() {
    return colSeparator;
  }

  /**
   * Establishes a connection (if one is not currently active) and returns a
   * handle to the connection
   *
   * @return the connection handle
   *
   * @throws IOException
   */
  public iURLConnection getConnection() throws IOException {
    closeOutput();

    return urlConnection;
  }

  /**
   * Returns the contents of this link as a java object and closes the
   * connection
   *
   * @return the contents of this link as a java object
   *
   * @throws IOException
   */
  public Object getContent() throws IOException {
    closeOutput();

    Object o = urlConnection.getContent();

    close();

    return o;
  }

  /**
   * Returns the contents of this link as a ByteArray object
   *
   * @return the contents of this link as a ByteArray object
   *
   * @throws IOException
   */
  public ByteArray getContentAsBytes() throws IOException {
    InputStream stream = getInputStream();
    int         n      = getContentLength();
    ByteArray   ba     = new ByteArray((n > 10)
                                       ? n
                                       : 10);

    try {
      return Streams.streamToBytes(stream, ba);
    } finally {
      close();
    }
  }

  /**
   * Returns the contents of this link as a string and closes the connection
   *
   * @return the contents of this link as a string
   *
   * @throws IOException
   */
  public String getContentAsString() throws IOException {
    Reader r = getReader();

    try {
      return Streams.readerToString(r);
    } finally {
      close();
    }
  }

  /**
   * @return the encoding
   */
  public String getContentEncoding() throws IOException {
    if (contentEncoding == null) {
      contentEncoding = getConnection().getContentEncoding();
    }

    return contentEncoding;
  }

  /**
   * Gets the content length for the links data
   *
   * @return the content length for the links data
   *
   * @throws IOException
   */
  public int getContentLength() throws IOException {
    return getConnection().getContentLength();
  }

  /**
   * Gets the MIME type for the link. If a type was specified with the link then
   * it is returned. Otherwise, a connection is established and then the type is
   * requested for the server
   *
   * @return the MIME type
   *
   * @throws IOException
   */
  public String getContentType() throws IOException {
    if (mimeType == null) {
      if (contentType == null) {
        contentType = getConnection().getContentType();
      }

      return contentType;
    }

    return mimeType;
  }

  /**
   * Gets the widget context associated with this link
   *
   * @return the widget context associated with this link
   */
  public iWidget getContext() {
    return contextWidget;
  }

  /**
   * @return the defaultCharset
   */
  public String getDefaultCharset() {
    return defaultCharset;
  }

  public iErrorHandler getErrorHandler() {
    return (errorHandler == null)
           ? _errorHandler
           : errorHandler;
  }

  /**
   * Get a link representing the next part of a multi-part MIME document
   *
   * @param context
   *          the context
   * @return the link for the part or null if there are no more parts
   *
   * @throws IOException
   */
  public iMultipartMimeHandler.iMultipart getFirstPart(iWidget context) throws IOException {
    if (multiPart == null) {
      iMultipartMimeHandler mh = context.getAppContext().getMultipartMimeHandler();

      multiPart = mh.getFirstPart(getInputStream(), getContentType());
    }

    return multiPart;
  }

  public float getImageDensity() {
    return imageDensity;
  }

  /**
   * Establishes a connection (if one is not currently active) and returns an
   * input stream that will retrieve the links data
   *
   * @return the input stream for this link
   *
   * @throws IOException
   */
  public InputStream getInputStream() throws IOException {
    closeOutput();

    return urlConnection.getInputStream();
  }

  /**
   * Gets the separator that should be used to extract linked data values from
   * the data
   *
   * @return the separator
   */
  public char getLinkedDataSeparator() {
    return ldSeparator;
  }

  /**
   * Returns the X-Link-Info header for the link
   *
   * @return the the X-Link-Info header for the link
   */
  public String getLinkInfo() {
    return linkInfo;
  }

  /**
   * Gets the MIME type for the link. If a type was specified with the link then
   * it is returned. Otherwise, if the connection is active then the type is
   * requested for the server. If the connection is not active and no overriding
   * MIME type was specified then null is returned. The is different from
   * {@link #getContentType() } which never returns null
   *
   * @return the MIME type of the links contents
   *
   * @throws IOException
   */
  public String getMimeType() throws IOException {
    if ((mimeType == null) && (urlConnection != null)) {
      closeOutput();

      return urlConnection.getContentType();
    }

    return mimeType;
  }

  /**
   * Returns the X-Paging-Has-More header for the link
   *
   * @return the the X-Paging-Has-More header for the link
   */
  public boolean getPagingHasMore() {
    return pagingHasMore;
  }

  /**
   * Returns the X-Paging-Next header for the link
   *
   * @return the the X-Paging-Next header for the link
   */
  public String getPagingNext() {
    return pagingNext;
  }

  /**
   * Returns the X-Paging-Next header for the link
   *
   * @return the the X-Paging-Next header for the link
   */
  public String getPagingPrevious() {
    return pagingPrevious;
  }

  /**
   * Gets the name of the parser class to use to parse the link's data
   *
   * @return name the name of the parser class
   */
  public String getParserClassName() {
    return parserClassName;
  }

  /**
   * Get the MIME multi-part boundary that will be used by this link to separate
   * multi-part data
   *
   * @return the MIME multi-part boundary
   */
  public String getPartBoundary() {
    return (partBoundary == null)
           ? DEFAULT_BOUNDARY
           : partBoundary;
  }

  /**
   * Gets the popup location for new windows
   *
   * @return the popup location for new windows or null if none was specified
   */
  public UIPoint getPopupLocation() {
    return popupLocation;
  }

  /**
   * Establishes a connection (if one is not currently active) and returns a
   * reader that can retrieve the links data.
   *
   * @return the reader
   *
   * @throws IOException
   */
  public Reader getReader() throws IOException {
    try {
      closeOutput();

      final String enc = getContentEncoding();
      Reader       r;

      if ((enc == null) && (charset == null)) {
        r = urlConnection.getReader();
      } else {
        r = Streams.getDecodingReader(urlConnection.getInputStream(), getContentEncoding(), getCharset(),
                                      getBufferSize());
      }

      if (unescape) {
        r = new UnescapingReader(r, unescapeUnicodeOnly);
      }

      return r;
    } catch(NullPointerException e) {    // should only happen because another
      e.printStackTrace();
      ;

      // thread closed the connection and we
      // havent started to read yet
      throw new ClosedChannelException();
    }
  }

  /**
   * Returns setting for read timeout. 0 return implies that the option is
   * disabled (i.e., timeout of infinity).
   *
   * @return the read timeout value in milliseconds
   */
  public int getReadTimeout() {
    return readTimeout;
  }

  /**
   * @return the defaultCharset
   */
  public String getRequestCharset() {
    return requestCharset;
  }

  /**
   * Get the MIME content type that will be used when the request is submitted
   *
   * @return the MIME content type that will be used when the request is
   *         submitted
   */
  public String getRequestContentType() {
    if (requestType != null) {
      return requestType;
    }

    if (requestEncoding == RequestEncoding.JSON) {
      return "application/json";
    }

    return this.isMultiPartForm()
           ? "multipart/form-data; boundary=" + getPartBoundary()
           : "application/x-www-form-urlencoded";
  }

  /**
   * Gets the encoding that should be used to send data
   *
   * @return the encoding
   */
  public RequestEncoding getRequestEncoding() {
    return requestEncoding;
  }

  /**
   * Gets the HTTP method that will be used to retrieve the links data
   *
   * @return the method
   */
  public RequestMethod getRequestMethod() {
    return requestMethod;
  }

  /**
   * Establishes a connection (if one is not currently active) and returns the
   * response code returned by the server
   *
   * @return the response code
   *
   * @throws IOException
   */
  public int getResponseCode() throws IOException {
    closeOutput();

    return urlConnection.getResponseCode();
  }

  /**
   * Establishes a connection (if one is not currently active) and returns the
   * response header value of for the specified key
   *
   * @param key
   *          the header key
   *
   * @return the header value or null
   *
   * @throws IOException
   */
  public String getResponseHeader(String key) throws IOException {
    closeOutput();

    return urlConnection.getHeaderField(key);
  }

  /**
   * Gets the links row information separator
   *
   * @return the links row information separator
   */
  public char getRowInfoSeparator() {
    return rowInfoSeparator;
  }

  /**
   * Gets the status message that will be placed on the status bar during link
   * activation
   *
   * @return the status message
   */
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Get a string representation of the URL for this link
   *
   * @param context
   *          the context to use to resolve the URL
   * @return a string representation of the URL
   *
   * @throws java.net.MalformedURLException
   */
  public String getStringURL(iWidget context) throws MalformedURLException {
    if (stringURL != null) {
      return stringURL;
    }

    URL u = getURL(context);

    return (u == null)
           ? null
           : JavaURLConnection.toExternalForm(u);
  }

  /**
   * Gets the name of the target for this link
   *
   * @return the name of the target for this link
   */
  public String getTargetName() {
    return targetName;
  }

  /**
   * Establishes a connection (if one is not currently active) and returns a
   * reader that can retrieve the links data and un-escapes escape sequences in
   * the data.
   *
   * @return the reader
   *
   * @throws IOException
   */
  public Reader getUnescapingReader() throws IOException {
    closeOutput();

    return new UnescapingReader(urlConnection.getReader(), unescapeUnicodeOnly);
  }

  /**
   * Get the URL associated with the URL. First, if the connection is active
   * then the URL that the connection was created for is returned. Then, if the
   * link was created with a URL object then that object is returned. Otherwise,
   * if a string url was passed in then the string is evaluated for embedded
   * variables and function and then a URL object is created from the result of
   * the evaluation.
   *
   * @param context
   *          a context to use if the link does not have one
   * @return the URL
   *
   * @throws MalformedURLException
   */
  public URL getURL(iWidget context) throws MalformedURLException {
    if (theURL == null) {
      if (urlConnection != null) {
        theURL = urlConnection.getURL();
      }
    }

    if ((theURL == null) && (stringURL != null)) {
      if (contextWidget != null) {
        context = contextWidget;
      }

      if (context == null) {
        context = Platform.getContextRootViewer();
      }

      URL curl = (contextURL == null)
                 ? context.getViewer().getBaseURL()
                 : contextURL;

      theURL = context.getAppContext().createURL(curl, context.expandString(stringURL, false));
    }

    return theURL;
  }

  /**
   * Creates and returns a URL with the specified query string. If the link was
   * created with a URL object then a URL consisting of that URL and the
   * specified query string is returned. Otherwise, if a string URL was passed
   * in then the string is evaluated for embedded variables and functions and
   * then a URL object is created from the result of the evaluation and the
   * specified query string.
   *
   * @param context
   *          the widget context to use to resolve the url
   * @param queryString
   *          the query string
   * @return the URL
   *
   * @throws MalformedURLException
   */
  public URL getURL(iWidget context, String queryString) throws MalformedURLException {
    if ((queryString == null) || (queryString.length() == 0)) {
      return getURL(context);
    }

    URL    u = getURL(context);
    String s = u.toExternalForm();

    if (s.indexOf('?') == -1) {
      s += "?" + queryString;
    } else {
      s += queryString;
    }

    return new URL(s);
  }

  /**
   * Gets the viewer configuration associated with the link
   *
   * @return the viewer configuration or null
   */
  public Viewer getViewerConfiguration() {
    return viewerConfiguration;
  }

  /**
   * Gets a map of window options configured for the link
   *
   * @return a map of window options configured for the link or null
   */
  public Map<String, String> getWindowOptions() {
    iWidget widget = (contextWidget == null)
                     ? Platform.getContextRootViewer()
                     : contextWidget;

    if (widget != null) {
      this.resolveOptions(widget);
    }

    return windowOptions;
  }

  /**
   * Returns whether this link has a URL associated with it
   *
   * @return true if it does; false otherwise
   */
  public boolean hasURL() {
    return (theURL != null) || (stringURL != null);
  }

  /**
   * Establishes a connection (if one is not currently active) reads and
   * discards any available content and then closes the connection
   *
   * @throws IOException
   */
  public void hit() throws IOException {
    try {
      closeOutput();
      InputStream stream=getInputStream();
      Streams.drain(stream, true);
    } finally {
      close();
    }
  }

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  /**
   * Returns if the link is closed
   *
   * @return true if it is closed; false otherwise
   */
  public boolean isClosed() {
    return urlConnection == null;
  }

  /**
   * Returns whether or not the link represents a data collection object
   *
   * @return true if it does; false otherwise;
   */
  public boolean isCollection() {
    if (stringURL != null) {
      return stringURL.startsWith(iConstants.COLLECTION_PREFIX);
    }

    if (theURL != null) {
      return CollectionURLConnection.isCollectionURL(theURL);
    }

    return false;
  }

  /**
   * Gets whether the column separator has been explicitly set
   *
   * @return true if it has; false otherwise
   */
  public boolean isColumnSeparatorSet() {
    return columnSeparatorSet;
  }

  /**
   * Returns whether the preferred method for loading this link is to deferrer
   * it
   *
   * @return true if the preferred method for loading this link is to deferrer
   *         it; false otherwise
   */
  public boolean isDeferred() {
    return isDeferred(null);
  }

  /**
   * Returns whether the preferred method for loading this link is to deferrer
   * it. The passed in context (or the link's context if null) will be used to
   * check the URL to see if it as file URL. If it is a file URL then this
   * method will always return false;
   *
   * @param context
   *          the context to check against
   * @return true if the preferred method for loading this link is to deferrer
   *         it; false otherwise
   */
  public boolean isDeferred(iWidget context) {
    if ((viewerConfiguration != null) || ((context != null) && context.isDesignMode())) {
      return false;
    }

    if (context == null) {
      context = this.contextWidget;
    }

    if (isInlineURL()) {
      return false;
    }

    if (!deferred || (context == null) || ((stringURL == null) && (theURL == null))) {
      return false;
    }

    try {
      final URL    url      = getURL(context);
      final String protocol = url.getProtocol();

      if ("data".equals(protocol)) {
        return false;
      }
    } catch(MalformedURLException ignore) {}

    return true;
  }

  @Override
  public boolean isDone() {
    return urlConnection == null;
  }

  /**
   * @return the dontUseCache
   */
  public boolean isDontUseCache() {
    return dontUseCache;
  }

  /**
   * @return the escape
   */
  public boolean isEscape() {
    return escape;
  }

  /**
   * Returns whether the link points to an inline URL
   *
   * @return true if the link points to an inline URL; false otherwise
   */
  public boolean isInlineURL() {
    return inlineURL;
  }

  /**
   * Returns whether a mime type for the link has been explicitly set
   *
   * @return true if it has been set; false otherwise
   */
  public boolean isMimeTypeSet() {
    return mimeType != null;
  }

  /**
   * Returns whether the link represents multi-part data
   *
   * @return true if the link represents multi-part data; false otherwise
   */
  public boolean isMultipartDocument() throws IOException {
    String type = getContentType();

    type = type.toLowerCase(Locale.US);

    if (!type.startsWith("multipart/")) {
      return false;
    }

    return type.contains("boundary=");
  }

  /**
   * Returns whether the link represents an HTTP multi-part form
   *
   * @return true if the link represents an HTTP multi-part form; false
   *         otherwise
   */
  public boolean isMultiPartForm() {
    return multiPartForm;
  }

  /**
   * Returns whether the link is a string url or URL object
   *
   * @return true if the link is a string URL; false if it is a URL object
   */
  public boolean isStringURL() {
    return stringURL != null;
  }

  /**
   * Returns whether the link's data should be un-escaped (have backslash
   * characters resolved)
   *
   * @return true if the links data will be un-escaped; false otherwise
   */
  public boolean isUnescape() {
    return unescape;
  }

  /**
   * @return the unescapeUnicodeOnly
   */
  public boolean isUnescapeUnicodeOnly() {
    return unescapeUnicodeOnly;
  }

  /**
   * Initiates a deferred load of this link into the specified widget
   *
   * @param widget
   *          the widget the link's data is to be loaded into
   */
  public void loadLater(final iWidget widget) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        widget.setDataLink(ActionLink.this);
      }
    };

    widget.getAppContext().executeBackgroundTask(r);
  }

  /**
   * Establishes a connection (if one is not currently active) and returns a
   * writer that can send data to the connected server
   *
   * @return the output writer
   *
   * @throws IOException
   */
  public Writer openForOutput() throws IOException {
    if (outputWriter == null) {
      if ((requestMethod == null) || (requestMethod == RequestMethod.GET)) {
        requestMethod = RequestMethod.POST;
      }

      connect(true);
    }

    return outputWriter;
  }

  /**
   * Sends the specified data using the HTTP PUT method
   *
   * @param data
   *          the data
   * @param return_content
   *          true to return the content from the server
   *
   * @return null or the data returned from the server
   * @throws java.io.IOException
   */
  public String putData(Reader data, boolean return_content) throws IOException {
    this.setRequestMethod(RequestMethod.PUT);

    Writer writer = null;

    writer = this.openForOutput();
    Streams.readerToWriter(data, writer, null);
    writer.flush();
    this.closeOutput();

    try {
      if (return_content) {
        return getContentAsString();
      } else {
        urlConnection.getContent();
      }
    } finally {
      this.close();
    }

    return null;
  }

  /**
   * Sends the specified data using the HTTP PUT method
   *
   * @param data
   *          the data
   * @param return_content
   *          true to return the content from the server
   *
   * @return null or the data returned from the server
   * @throws java.io.IOException
   */
  public String putData(String data, boolean return_content) throws IOException {
    this.setRequestMethod(RequestMethod.PUT);

    Writer writer = null;

    writer = this.openForOutput();
    writer.write(data);
    writer.flush();
    this.closeOutput();

    try {
      if (return_content) {
        return getContentAsString();
      } else {
        urlConnection.getContent();
      }
    } finally {
      this.close();
    }

    return null;
  }

  /**
   * Resets the link. The link is closed and the canceled flag is reset.
   */
  public void reset() {
    try {
      close();
    } catch(Exception ignore) {}

    canceled = false;
  }

  /**
   * Resets the links URL to the specified URL
   *
   * @param url
   *          the new URL
   */
  public void resetURL(String url) {
    clean();
    setURL(url);
  }

  /**
   * Resets the links URL to the specified URL
   *
   * @param url
   *          the new URL
   */
  public void resetURL(URL url) {
    clean();
    setURL(url);
  }

  /**
   * Invoked by threads to perform the links action
   */
  @Override
  public void run() {
    canceled = false;

    final iWidget widget = (contextWidget == null)
                           ? Platform.getContextRootViewer()
                           : contextWidget;

    if (widget == null) {    // we don't have a context
      return;
    }

    resolveOptions(widget);

    if ((popupLocation != null) && (popupLocation.x != -1) && (popupLocation.y != -1)) {
      if (windowOptions == null) {
        windowOptions = new HashMap<String, String>(2);
      }

      windowOptions.put("pleft", String.valueOf(popupLocation.x));
      windowOptions.put("ptop", String.valueOf(popupLocation.y));
    } else if (windowOptions != null) {
      windowOptions.remove("pleft");
      windowOptions.remove("ptop");
    }

    final iPlatformWindowManager w = widget.getAppContext().getWindowManager();

    if (Platform.isUIThread()) {
      w.asyncActivateViewer(widget, (ActionLink) this.clone());
    } else {
      try {
        final Viewer cfg = DataParser.checkAndInstantiateViewer(widget, null, getConnection());

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            w.activateViewer(widget, cfg, getTargetName());
          }
        });
      } catch(Exception e) {
        throw ApplicationException.runtimeException(e);
      }
    }
  }

  /**
   * Sends the specified data as form data
   *
   * @param context
   *          the context
   * @param data
   *          the data
   *
   * @return the data returned from the server
   * @throws java.io.IOException
   */
  public String sendFormData(iWidget context, Map data) throws IOException {
    return sendFormData(context, data, false, true);
  }

  /**
   * Sends the specified data as form data
   *
   * @param context
   *          the context
   * @param data
   *          the data
   * @param multipart
   *          true to send the data as multi-part content
   * @param return_content
   *          true to return the content from the server
   *
   * @return null or the data returned from the server (the link is
   *         automatically closed)
   * @throws java.io.IOException
   */
  public String sendFormData(iWidget context, Map data, boolean multipart, boolean return_content) throws IOException {
    sendFormDataEx(context, data, multipart);

    try {
      if (return_content) {
        return getContentAsString();
      } else {
        urlConnection.getContent();
      }
    } finally {
      this.close();
    }

    return null;
  }

  /**
   * Initiates the sending of the specified data as form data. The connection
   * remains open after this call returns
   *
   * @param context
   *          the context
   * @param data
   *          the data
   * @param multipart
   *          true to send the data as multi-part content
   * @throws java.io.IOException
   */
  public void sendFormDataEx(iWidget context, Map data, boolean multipart) throws IOException {
    this.setRequestMethod(RequestMethod.POST);

    Writer writer = null;

    if (multipart) {
      this.setMultiPartForm(true);
      writer = this.openForOutput();
      FormHelper.writeHTTPContent(true, context, writer, getPartBoundary(), data, false);

      if (linkAttributes != null) {
        FormHelper.writeHTTPContent(true, context, writer, getPartBoundary(), linkAttributes, false);
      }

      FormHelper.writeBoundaryEnd(writer, getPartBoundary());
    } else {
      writer = this.openForOutput();

      boolean first = FormHelper.writeHTTPValues(true, context, writer, data, false);

      if (linkAttributes != null) {
        FormHelper.writeHTTPValues(first, context, writer, linkAttributes, false);
      }
    }

    this.closeOutput();
  }

  /**
   * Set attributes that are to be sent along as part of the link's form data
   * (similar to hidden values in HTTP forms)
   *
   * @param attributes
   *          the attributes
   */
  public void setAttributes(Map<String, Object> attributes) {
    this.linkAttributes = attributes;
  }

  /**
   * Sets the character set for reading character data
   *
   * @param charset
   *          the character set for reading character data
   */
  public void setCharset(String charset) {
    this.charset = charset;

    if (urlConnection != null) {
      urlConnection.setCharset(charset);
    }
  }

  /**
   * Sets the column separator to use to parse the link's data
   *
   * @param colSeparator
   *          the column separator
   */
  public void setColumnSeparator(char colSeparator) {
    this.colSeparator  = colSeparator;
    columnSeparatorSet = true;
  }

  /**
   * Sets the connection to use for this link
   *
   * @param conn
   *          the connection;
   */
  public void setConnection(iURLConnection conn) {
    urlConnection = conn;
  }

  /**
   * Sets the content encoding
   *
   * @param contentEncoding
   *          the encoding to set
   */
  public void setContentEncoding(String contentEncoding) {
    this.contentEncoding = contentEncoding;
  }

  /**
   * Sets the widget context
   *
   * @param context
   *          the widget context
   */
  public void setContext(iWidget context) {
    this.contextWidget = context;
  }

  /**
   * Sets the context url
   *
   * @param contextURL
   *          the context url
   */
  public void setContextURL(URL contextURL) {
    this.contextURL = contextURL;
  }

  /**
   * Sets the default character set for reading character data
   *
   * @param charset
   *          the default character set for reading character data
   */
  public void setDefaultCharset(String charset) {
    defaultCharset = charset;

    if (urlConnection != null) {
      urlConnection.setDefaultCharset(charset);
    }
  }

  /**
   * Sets whether the preferred method for loading this link is to deferrer it
   *
   * @param deferred
   *          true if the preferred method for loading this link is to deferrer
   *          it; false otherwise
   */
  public void setDeferred(boolean deferred) {
    this.deferred = deferred;
  }

  /**
   * @param dontUseCache
   *          the dontUseCache to set
   */
  public void setDontUseCache(boolean dontUseCache) {
    this.dontUseCache = dontUseCache;
  }

  public void setErrorHandler(iErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
  }

  /**
   * @param escape
   *          the escape to set
   */
  public void setEscape(boolean escape) {
    this.escape = escape;
  }

  public void setImageDensity(float imageDensity) {
    this.imageDensity = imageDensity;
  }

  /**
   * Sets inline data for the link. The links URL will become and inline URL as
   * a result
   *
   * @param value
   *          the links data
   * @param mimeType
   *          the MIME type of the data
   * @param contentEncoding
   *          content encoding for the data (can be null)
   */
  public void setInlineData(String value, String mimeType, String contentEncoding) {
    clear();
    inlineURL = true;

    try {
      theURL = InlineURLConnection.createURL(value, mimeType, contentEncoding);
    } catch(MalformedURLException e) {
      throw new ApplicationException(e);
    }
  }

  /**
   * Sets the separator that should be used to extract linked data values from
   * the data
   *
   * @param ldSeparator
   *          the separator
   */
  public void setLinkedDataSeparator(char ldSeparator) {
    this.ldSeparator = ldSeparator;
  }

  /**
   * Sets the MIME type to use for the links data
   *
   * @param mimeType
   *          the MIME type
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Sets whether the links output data should be encoded as a multi-part form
   *
   * @param multiPartForm
   *          true the links output data should be encoded as a multi-part form;
   *          false otherwise
   */
  public void setMultiPartForm(boolean multiPartForm) {
    this.multiPartForm = multiPartForm;
  }

  /**
   * Sets the name of the parser class to use to parse the link's data
   *
   * @param name
   *          the name of the parser class
   */
  public void setParserClassName(String name) {
    this.parserClassName = name;
  }

  /**
   * Sets the part boundary to use when writing multi-part from data
   *
   * @param partBoundary
   *          the part boundary
   */
  public void setPartBoundary(String partBoundary) {
    this.partBoundary = partBoundary;
  }

  /**
   * Set the popup location for a link whose target is a new window
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   */
  public void setPopupLocation(int x, int y) {
    if (popupLocation == null) {
      popupLocation = new UIPoint(x, y);
    } else {
      popupLocation.setLocation(x, y);
    }
  }

  /**
   * Set the popup location for a link whose target is a new window
   *
   * @param popupLocation
   *          the popup location for the new window
   */
  public void setPopupLocation(UIPoint popupLocation) {
    this.popupLocation = popupLocation;
  }

  /**
   * Sets the read timeout to a specified timeout, in milliseconds. A non-zero
   * value specifies the timeout when reading from Input stream when a
   * connection is established to a resource. If the timeout expires before
   * there is data available for read, a java.net.SocketTimeoutException is
   * raised. A timeout of zero is interpreted as an infinite timeout.
   *
   * @param milliseconds
   *          the timeout
   */
  public void setReadTimeout(int milliseconds) {
    readTimeout = milliseconds;

    if (urlConnection != null) {
      urlConnection.setReadTimeout(milliseconds);
    }
  }

  /**
   * Sets the character set to use when making the request
   *
   * @param charset
   *          the character set
   */
  public void setRequestCharset(String charset) {
    requestCharset = charset;
  }

  /**
   * The content type for the request
   *
   * @param type
   *          the requestType to set
   */
  public void setRequestContentType(String type) {
    this.requestType = type;
  }

  /**
   * Sets the encoding that should be used to send data
   *
   * @param encoding
   *          the encoding
   */
  public void setRequestEncoding(RequestEncoding encoding) {
    if (encoding == null) {
      encoding = RequestEncoding.HTTP_FORM;
    }

    this.requestEncoding = encoding;
  }

  /**
   * Sets the encoding that should be used to send data
   *
   * @param encoding
   *          the encoding
   */
  public void setRequestEncoding(String encoding) {
    this.requestEncoding = RequestEncoding.valueOf(encoding.toUpperCase(Locale.US));
  }

  /**
   * Sets a request header value (must be called before a connection is
   * established)
   *
   * @param key
   *          the header key
   * @param value
   *          the header value
   */
  public void setRequestHeader(String key, String value) {
    if (requestHeaders == null) {
      requestHeaders = new HashMap<String, Object>();
    }

    requestHeaders.put(key, value);
  }

  /**
   * Sets the HTTP method that should be used to retrieve the links data
   *
   * @param method
   *          the method
   */
  public void setRequestMethod(RequestMethod method) {
    if (method == null) {
      method = RequestMethod.GET;
    }

    this.requestMethod = method;
  }

  /**
   * Sets the HTTP method that should be used to retrieve the links data
   *
   * @param method
   *          the method
   */
  public void setRequestMethod(String method) {
    this.requestMethod = RequestMethod.valueOf(method.toUpperCase(Locale.US));
  }

  /**
   * Sets the separator that should be used to extract row level information
   * from the data
   *
   * @param riSeparator
   *          the separator
   */
  public void setRowInfoSeparator(char riSeparator) {
    this.rowInfoSeparator = riSeparator;
  }

  /**
   * Sets the separators using values in the mime-type string
   *
   * @param type
   *          the mime type
   * @param always
   *          true to always set; false to only set when values have not been
   *          explicitly set
   */
  public void setSeparatorsFromMimeType(String type, boolean always) {
    if (always ||!columnSeparatorSet) {
      char c = getSeparatorCharacter(type, MIME_COLUMN_SEPARATOR_PREFIX);

      if (c != 0) {
        colSeparator = c;
      }
    }

    if (always || (ldSeparator == '\0')) {
      char c = getSeparatorCharacter(type, MIME_LD_SEPARATOR_PREFIX);

      if (c != 0) {
        ldSeparator = c;
      }
    }

    if (always || (rowInfoSeparator == '\0')) {
      char c = getSeparatorCharacter(type, MIME_RI_SEPARATOR_PREFIX);

      if (c != 0) {
        rowInfoSeparator = c;
      }
    }
  }

  /**
   * Sets the message to place on the status bar during link activation
   *
   * @param msg
   *          the message
   */
  public void setStatusMessage(String msg) {
    this.statusMessage = msg;
  }

  /**
   * Sets the name of the target for the link
   *
   * @param targetName
   *          the target name
   */
  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

  /**
   * Sets whether the links data should be un-escaped (have backslash characters
   * resolved)
   *
   * @param unescape
   *          true if the links data will be un-escaped; false otherwise
   */
  public void setUnescape(boolean unescape) {
    this.unescape = unescape;
  }

  /**
   * @param unescapeUnicodeOnly
   *          the unescapeUnicodeOnly to set
   */
  public void setUnescapeUnicodeOnly(boolean unescapeUnicodeOnly) {
    this.unescapeUnicodeOnly = unescapeUnicodeOnly;
  }

  /**
   * Sets the URL for the link
   *
   * @param url
   *          the URL
   */
  public void setURL(String url) {
    close();
    stringURL = url;
    theURL    = null;
  }

  /**
   * Sets the URL for the link
   *
   * @param url
   *          the URL
   */
  public void setURL(URL url) {
    close();
    theURL    = url;
    stringURL = null;
  }

  /**
   * Sets the viewer configuration to associate with this link The link can have
   * an associated viewer configuration or a URL but not both
   *
   * @param config
   *          the viewer configuration
   */
  public void setViewerConfiguration(Viewer config) {
    this.viewerConfiguration = config;
  }

  /**
   * Sets the window options for the link
   *
   * @param options
   *          the window options for the link
   */
  public void setWindowOptions(Map<String, String> options) {
    origWindowOptions = options;
  }

  /**
   * Starts a file upload process
   *
   * @param name
   *          the name of the field that the file data is associated with
   * @param mimeType
   *          the MIME type for the file (if null then the file's extension will
   *          be used to determine the mime-type)
   * @param fileName
   *          the file name (cannot be null)
   * @return the writer to use to write the file data
   * @throws java.io.IOException
   */
  public Writer startFileUpload(String name, String mimeType, String fileName) throws IOException {
    this.setRequestMethod(RequestMethod.POST);
    setMultiPartForm(true);

    Writer writer = this.openForOutput();

    FormHelper.writeFileHeader(true, writer, getPartBoundary(), name, mimeType, fileName, false);

    return writer;
  }

  /**
   * Returns a String representation of this link.
   *
   * @return A a String representation of this link.
   */
  @Override
  public String toString() {
    String s;
    if (stringURL != null) {
      s=stringURL;
    } else {
      if(theURL == null) {
        return "";
      }
      s=JavaURLConnection.toExternalForm(theURL);
    }
    if(requestMethod==RequestMethod.GET && linkAttributes!=null) { 
      StringWriter sw = new StringWriter();
      sw.write(s);
      sw.append('?');
      try {
        FormHelper.writeHTTPValues(true, contextWidget, sw, getAttributes(), false);
      } catch (IOException e) {
        Platform.ignoreException(null, e);
      }
      s=sw.toString();
    }
      
    return s;
  }

  /**
   * Uploads the specified data as a file
   *
   * @param data
   *          the data (can be a string or {@link Reader} or {@link InputStream}
   *          or {@link File}
   * @param name
   *          the name of the field that the file data is associated with
   * @param mimeType
   *          the MIME type for the file (if null then the file's extension will
   *          be used to determine the mime-type)
   * @param fileName
   *          the file name (cannot be null)
   * @param return_content
   *          true to return the content from the server
   *
   * @return null or the data returned from the server (the link is
   *         automatically closed)
   * @throws java.io.IOException
   */
  public String uploadData(Object data, String name, String mimeType, String fileName, boolean return_content)
          throws IOException {
    try {
      uploadDataEx(data, name, mimeType, fileName);

      if (return_content) {
        return getContentAsString();
      } else {
        urlConnection.getContent();
      }
    } finally {
      this.close();
    }

    return null;
  }

  /**
   * Initiates sending the specified data as form data an uploads the specified
   * file. The connection remains open after this call returns.
   *
   * @param data
   *          the data (can be a string or {@link Reader} or {@link InputStream}
   *          or {@link File}
   * @param name
   *          the name of the field that the file data is associated with
   * @param mimeType
   *          the MIME type for the file (if null then the file's extension will
   *          be used to determine the mime-type)
   * @param fileName
   *          the file name (cannot be null)
   * @throws java.io.IOException
   */
  public void uploadDataEx(Object data, String name, String mimeType, String fileName) throws IOException {
    this.setRequestMethod(RequestMethod.POST);

    Writer writer = null;

    setMultiPartForm(true);
    writer = this.openForOutput();

    Reader r;

    if (data instanceof Reader) {
      r = (Reader) data;
    } else if (data instanceof InputStream) {
      r = new InputStreamReader((InputStream) data, "utf-8");
    } else if (data instanceof File) {
      r = new FileReader((File) data);
    } else {
      r = new StringReader((data == null)
                           ? ""
                           : data.toString());
    }

    FormHelper.writeFile(true, writer, getPartBoundary(), name, mimeType, fileName, r, false);

    if (linkAttributes != null) {
      FormHelper.writeHTTPContent(false, null, writer, getPartBoundary(), linkAttributes, false);
    }

    FormHelper.writeBoundaryEnd(writer, getPartBoundary());
    this.closeOutput();
  }

  /**
   * Closes the link for output. All pending output is written to the connected
   * server
   *
   * @throws IOException
   */
  protected void closeOutput() throws IOException {
    final iErrorHandler eh = getErrorHandler();

    if (eh == null) {
      closeOutputEx();

      return;
    }

    try {
      closeOutputEx();
    } catch(Exception ex) {
      iErrorHandler.Action a = eh.handleError(this, ex, urlConnection);

      if (a == null) {
        a = iErrorHandler.Action.ERROR;
      }

      if (urlConnection == null) {
        throw new ClosedChannelException();
      }

      switch(a) {
        case CHANGE : {
          urlConnection = eh.getConnectionChange(this, ex, urlConnection);
          handlePostConnect();

          break;
        }

        case RETRY : {
          String info = linkInfo;

          close();
          linkInfo = info;
          closeOutputEx();

          break;
        }

        case ERROR : {
          if (ex instanceof IOException) {
            throw(IOException) ex;
          }

          throw ApplicationException.runtimeException(ex);
        }

        case CHANGE_ERROR : {
          ex = eh.getExceptionChange(this, ex);

          if (ex instanceof IOException) {
            throw(IOException) ex;
          }

          throw ApplicationException.runtimeException(ex);
        }

        case ERROR_MESSAGE : {
          if (ex instanceof HTTPException) {
            String s = ((HTTPException) ex).getMessageBody();

            if ((s != null) && (s.length() > 0)) {
              throw new ApplicationException(s);
            }
          }

          throw new ApplicationException(ApplicationException.getMessageEx(ex));
        }
      }
    }
  }

  protected void closeOutputEx() throws IOException {
    if (urlConnection == null) {
      connect(false);
    } else {
      if (outputWriter != null) {
        try {
          outputWriter.flush();
          handlePostConnect();
        } finally {
          outputWriter = null;
        }
      }
    }
  }

  /**
   * Establishes a connection to the underlying server
   *
   * @param output
   *          whether the connection should support output
   *
   * @throws IOException
   */
  protected void connect(boolean output) throws IOException {
    pagingNext     = null;
    pagingPrevious = null;
    linkInfo       = null;

    URL           url;
    RequestMethod method = getRequestMethod();
    iWidget       widget = (contextWidget == null)
                           ? Platform.getContextRootViewer()
                           : contextWidget;

    switch(method) {
      case HEAD :
      case GET :
      case DELETE : {
        if (getAttributes() != null) {
          StringWriter sw = new StringWriter();

          if (FormHelper.writeHTTPValues(true, contextWidget, sw, getAttributes(), false)) {
            url = getURL(widget, sw.toString());
          } else {
            url = getURL(widget);
          }
        } else {
          url = getURL(widget);
        }

        urlConnection = Platform.openConnection(widget, url, mimeType);

        if (urlConnection.getConnectionObject() instanceof HttpURLConnection) {
          HttpURLConnection connection = (HttpURLConnection) urlConnection.getConnectionObject();

          if (method.supportsOutput()) {
            connection.setDoOutput(method.supportsOutput());
          }

          connection.setRequestMethod(method.name());

          if (dontUseCache) {
            connection.setUseCaches(false);
          }
        }

        urlConnection.setReadTimeout(readTimeout);
        addHeaders(urlConnection, requestHeaders);
        urlConnection.open();
        handlePostConnect();

        break;
      }

      case PUT :
      case POST :
      case OPTIONS : {
        url           = getURL(widget);
        urlConnection = Platform.openConnection(widget, url, mimeType);
        urlConnection.setReadTimeout(readTimeout);

        HttpURLConnection connection = (HttpURLConnection) urlConnection.getConnectionObject();

        connection.setDoOutput(method.supportsOutput());
        connection.setRequestMethod(method.name());
        addHeaders(urlConnection, requestHeaders);

        String type = this.getRequestContentTypeEx();
        String cs   = getRequestCharset();

        if (cs == null) {
          cs = "ISO-8859-1";
        }

        connection.setRequestProperty("Content-Type", type);

        if (output) {
          OutputStream stream = urlConnection.getOutputStream();

          outputWriter = new OutputStreamWriter(stream, cs);
        } else if (getAttributes() != null) {
          OutputStream       stream = urlConnection.getOutputStream();
          OutputStreamWriter w      = new OutputStreamWriter(stream, cs);

          // outputWriter = new BufferedWriter(outputWriter, 1024);
          if (getRequestEncoding() == RequestEncoding.JSON) {
            FormHelper.writeJSONValues(contextWidget, w, getAttributes(), true, false);
          } else if (this.isMultiPartForm()) {
            FormHelper.writeHTTPContent(true, contextWidget, w, getPartBoundary(), getAttributes(), false);
            FormHelper.writeBoundaryEnd(w, getPartBoundary());
          } else {
            FormHelper.writeHTTPValues(true, contextWidget, w, getAttributes(), false);
          }

          w.flush();
          handlePostConnect();
        }

        break;
      }

      default :
        break;
    }
  }

  protected void handlePostConnect() throws IOException {
    if (urlConnection == null) {
      throw new ClosedChannelException();
    }

    urlConnection.setDefaultCharset(getDefaultCharset());

    if (charset != null) {
      urlConnection.setCharset(charset);
    }

    if (urlConnection instanceof JavaURLConnection) {
      URL u = ((JavaURLConnection) urlConnection).getHTTP301URL();

      if (u != null) {
        stringURL = null;
        theURL    = u;
      }

      Object co = urlConnection.getConnectionObject();

      if (co instanceof HttpURLConnection) {
        HttpURLConnection connection = (HttpURLConnection) co;
        int               code       = connection.getResponseCode();

        if (code != 200) {
          throw new HTTPException(connection);
        }
      }

      pagingHasMore  = "true".equalsIgnoreCase(urlConnection.getHeaderField(PAGING_HAS_MORE));
      pagingNext     = urlConnection.getHeaderField(PAGING_NEXT);
      pagingPrevious = urlConnection.getHeaderField(PAGING_PREVIOUS);
      linkInfo       = urlConnection.getHeaderField(LINK_INFO);
    } else {
      pagingHasMore  = false;
      pagingNext     = null;
      pagingPrevious = null;
      linkInfo       = null;
    }
  }

  private void clean() {
    close();
    theURL    = null;
    stringURL = null;

    if (linkAttributes != null) {
      linkAttributes.clear();
    }

    linkAttributes      = null;
    viewerConfiguration = null;
  }

  private int getBufferSize() throws IOException {
    int n = getContentLength();

    if ((n < 1) || (n > 4096)) {
      n = 4096;
    }

    return n;
  }

  private String getRequestContentTypeEx() {
    if (requestType != null) {
      return requestType;
    }

    String cs = getRequestCharset();

    if (cs == null) {
      return getRequestContentType();
    }

    if (requestEncoding == RequestEncoding.JSON) {
      return "application/json; charset=" + cs;
    }

    return this.isMultiPartForm()
           ? "multipart/form-data; charset=" + cs + "; boundary=" + getPartBoundary()
           : "application/x-www-form-urlencoded; charset=" + cs;
  }

  private void resolveOptions(iWidget widget) {
    windowOptions = Utils.resolveOptions(widget, origWindowOptions, windowOptions);
  }

  /**
   * Returns whether the specified printable string points to an inline URL
   *
   * @param url
   *          a printable string representing a URL
   * @return true if the string points to an inline URL; false otherwise
   * @throws IOException
   */
  public static ActionLink createInlineLinkIfNecessary(iWidget context, SPOTPrintableString url) throws IOException {
    if (url.getValue() == null) {
      return null;
    }

    if (isInlineURL(url)) {
      return null;
    }

    ActionLink link = new ActionLink(context, url);
    String     type = link.getContentType();
    URL        u    = InlineURLConnection.createURL(link.getContentAsString(), type, null);
    ActionLink l    = new ActionLink(context, u);

    l.inlineURL = true;

    return l;
  }

  public static ActionLink getActionLink(iWidget context, SPOTPrintableString url, int index) {
    if (url.getValue() == null) {
      return null;
    }

    Object o = url.spot_getLinkedData();

    if (o instanceof ActionLink[]) {
      ActionLink[] a = (ActionLink[]) o;

      return (a.length > index)
             ? a[index]
             : null;
    }

    if (o instanceof ActionLink) {
      return (index == 0)
             ? (ActionLink) o
             : null;
    }

    return (index == 0)
           ? new ActionLink(context, url)
           : null;
  }

  /**
   * Gets a separator character from the mime-type string
   *
   * @param mime
   *          the mime string
   * @param sepstring
   *          the separator prefix string (including the equal sign)
   *
   * @return the separator character or zero if no separator was found
   */
  public static char getSeparatorCharacter(String mime, String sepstring) {
    char c = 0;

    if (mime != null) {
      int len = mime.length();
      int n   = mime.indexOf(sepstring);

      if (n != -1) {
        n += sepstring.length();
        c = mime.charAt(n);

        if (n + 1 < len) {
          if (c == '\\') {
            c = mime.charAt(n + 1);

            if (c == 'n') {
              c = '\n';
            } else if (c == 't') {
              c = '\t';
            } else if (c == 'f') {
              c = '\f';
            }
          }
        }
      }
    }

    return c;
  }

  /**
   * Returns whether the specified printable string points to an inline URL
   *
   * @param url
   *          a printable string representing a URL
   * @return true if the string points to an inline URL; false otherwise
   */
  public static boolean isInlineURL(SPOTPrintableString url) {
    String s = url.getValue();

    if (s == null) {
      return false;
    }

    if (s.startsWith("data:")) {
      return true;
    }

    s = url.spot_getAttribute("inline");

    if ((s != null) && (s.length() > 0)) {
      return SNumber.booleanValue(s);
    } else if (url.spot_isValuePreformatted()) {
      return true;
    }

    return false;
  }

  public static void setGlobalErrorHandler(iErrorHandler errorHandler) {
    if (_errorHandler instanceof aRare.MultiScreenFallbackErrorHandler) {
      ((aRare.MultiScreenFallbackErrorHandler) _errorHandler).setErrorHandler(errorHandler);
    } else {
      if ((_errorHandler != null) && (errorHandler instanceof aRare.MultiScreenFallbackErrorHandler)) {
        ((aRare.MultiScreenFallbackErrorHandler) errorHandler).setErrorHandler(_errorHandler);
      }

      _errorHandler = errorHandler;
    }
  }

  private static void addHeaders(iURLConnection connection, Map<String, Object> headers) {
    if ((connection != null) && (headers != null) && (headers.size() > 0)) {
      URLConnection                   conn = (URLConnection) connection.getConnectionObject();
      Entry<String, Object>           e;
      Iterator<Entry<String, Object>> it = headers.entrySet().iterator();

      while(it.hasNext()) {
        e = it.next();
        conn.setRequestProperty(e.getKey(), e.getValue().toString());
      }
    }
  }

  public static interface iErrorHandler {
    iURLConnection getConnectionChange(ActionLink link, Exception ex, iURLConnection conn) throws IOException;

    Exception getExceptionChange(ActionLink link, Exception ex);

    Action handleError(ActionLink link, Exception ex, iURLConnection conn);

    public enum Action {
      RETRY, ERROR, CHANGE, ERROR_MESSAGE, CHANGE_ERROR
    }
  }


  /** Request encoding types */
  public static enum RequestEncoding { HTTP_FORM, JSON }

  /**
   * HTTP request method values
   */
  public static enum RequestMethod {
    HEAD(false), GET(false), DELETE(false), POST(true), PUT(true), TRACE(true), OPTIONS(true);

    private final boolean output;

    RequestMethod(boolean output) {
      this.output = output;
    }

    /**
     * Returns whether the method supports output
     *
     * @return true if the method supports output; false otherwise
     */
    public boolean supportsOutput() {
      return output;
    }
  }

  /** Return data types */
  public static enum ReturnDataType {
    IGNORE, STRING, JSON, BYTES, LIST, TABLE, IMAGE, ICON, LINES, CONFIG
  }
}
