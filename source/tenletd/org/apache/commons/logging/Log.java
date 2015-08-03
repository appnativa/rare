/*
 * @(#)Logger.java   2010-10-24
 * 
 * Copyright (c) 2007-2009 SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package org.apache.commons.logging;

import java.util.logging.Level;

/**
 *
 * @author Don DeCoteau
 */
public class Log {
  private final java.util.logging.Logger logger;

  private Log(java.util.logging.Logger l) {
    logger = l;
  }

  public void debug(String s) {
    logger.log(Level.FINE, s);
  }

  public void debug(String s, Throwable e) {
    logger.log(Level.FINE, s, e);
  }

  public void error(String s) {
    logger.log(Level.WARNING, s);
  }

  public void error(String s, Throwable e) {
    logger.log(Level.WARNING, s, e);
  }

  public void error(Throwable e) {
    logger.log(Level.WARNING, "error",e);
  }

  public void fatal(String s) {
    logger.log(Level.SEVERE, s);
  }

  public void fatal(String s, Throwable e) {
    logger.log(Level.SEVERE, s, e);
  }

  public void info(String s) {
    logger.info(s);
  }

  public void trace(String s) {
    logger.log(Level.FINER, s);
  }

  public static Log getLogger(Class aClass) {
    return getLogger(aClass.getName());
  }

  public static Log getLogger(String name) {
    return new Log(java.util.logging.Logger.getLogger(name));
  }

  public boolean isDebugEnabled() {
    return logger.isLoggable(Level.FINE);
  }

  public boolean isTraceEnabled() {
    return logger.isLoggable(Level.FINER);
  }
}
