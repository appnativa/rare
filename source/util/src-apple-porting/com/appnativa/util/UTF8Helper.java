/*
 * @(#)UTF8Helper.java   2013-01-22
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.util;

import java.nio.charset.Charset;

/**
 *
 * @author Don DeCoteau
 */
public class UTF8Helper extends GenericCharsetHelper {
  public UTF8Helper() {
    super(Charset.forName("UTF-8"));
  }
  
  public static UTF8Helper getInstance() {
    return new UTF8Helper();
  }
 public native static String utf8String(String value) /*-[
  return nil;
  ]-*/;
   
 
 }
