/*
 * @(#)DefaultContentHandler.java   2013-02-18
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;

import java.io.IOException;

import java.net.ContentHandler;
import java.net.URLConnection;

/**
 *
 * @author decoteaud
 */
public class DefaultContentHandler extends ContentHandler {
  public Object getContent(URLConnection u) throws IOException {
    return u.getInputStream();
  }
}
