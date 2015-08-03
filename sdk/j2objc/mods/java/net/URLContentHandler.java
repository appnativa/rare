/*
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */
package java.net;

/**
 *
 * @author decoteaud
 */
public interface URLContentHandler {
  Object getContent(String contentType,String URLConnection);
  
}
