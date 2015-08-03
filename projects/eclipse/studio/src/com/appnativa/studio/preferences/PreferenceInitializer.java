/*
 * @(#)PreferenceInitializer.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.preferences;

import com.appnativa.studio.Activator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  public void initializeDefaultPreferences() {
    IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

    try {
      String s = Platform.getPreferencesService().getString("com.android.ide.eclipse.adt",
                   "com.android.ide.eclipse.adt.sdk", null, null);

      if (s != null) {
        ps.setDefault(PreferenceConstants.ANDROID_PATH, s);
      }

      ps.setDefault(PreferenceConstants.SMALL_SCREEN_SIZE, "960");
      ps.setDefault(PreferenceConstants.MEDIUM_SCREEN_SIZE, "960");
      ps.setDefault(PreferenceConstants.SMALL_SCREEN_SIZE_WITH_MEDIUM, "640");
      ps.setDefault(PreferenceConstants.GRID_COLOR, "0,133,0");
      ps.setDefault(PreferenceConstants.SELECTION_COLOR, "0,0,160");
      ps.setDefault(PreferenceConstants.TRACKING_COLOR, "64,64,64");
    } catch(Exception ignore) {}
  }
}
