/*
 * @(#)PreferencesFactory.java   2013-02-22
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.util.prefs;

public interface PreferencesFactory {
  Preferences systemRoot();

  Preferences userRoot();
}
