/*
 * @(#)FileNameMap.java   2013-01-13
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;

/**
 *
 * @author decoteaud
 */
public interface FileNameMap {

  /**
   *   Gets the MIME type for the specified file name.
   *   @param fileName the specified file name
   *   @return a <code>String</code> indicating the MIME
   *   type for the specified file name.
   */
  public String getContentTypeFor(String fileName);
}
