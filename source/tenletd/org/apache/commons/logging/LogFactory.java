/*
 * @(#)LogFactory.java   2011-07-25
 * 
 * Copyright (c) 2007-2009 SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package org.apache.commons.logging;

/**
 *
 * @author Don DeCoteau
 */
public class LogFactory {
  public static Log getLog(Class aClass) {
    return Log.getLogger(aClass);
  }
}
