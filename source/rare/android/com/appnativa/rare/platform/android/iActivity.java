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

package com.appnativa.rare.platform.android;

import android.app.Activity;
import android.app.Dialog;

import android.net.Uri;

import android.speech.tts.TextToSpeech;

import android.view.KeyEvent;
import android.view.MotionEvent;

import android.widget.Toast;

import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.ui.iPlatformMenuBar;

import java.net.URL;

import java.util.HashMap;

/**
 * An interface for working with environment specific activities
 *
 * @author Don DeCoteau
 */
public interface iActivity {
  TextToSpeech createTextToSpeech(final iFunctionCallback cb);

  /**
   * Locks the UI disabling all keyboard and touch events
   */
  void lockUI();

  /**
   * Shows a toast. The toast will be automatically
   * hidden when the activity is stopped
   * @param t the toast
   */
  void showToast(Toast t);

  void silenceTextToSpeech();

  void speak(final String text, final iFunctionCallback cb);

  void speak(final String text, final HashMap<String, String> utterance, final iFunctionCallback cb);

  /**
   * Unlocks the UI restoring keyboard and touch events
   */
  void unlockUI();

  /**
   * Called to update the options menu
   *
   * @param mb
   *          a handle to the menu bar
   * @param initialUpdate
   *          true if this is the inital update; false otherwise
   */
  void updateOptionsMenu(iPlatformMenuBar mb, boolean initialUpdate);

  /**
   * Validates the application url prior to it being loaded
   * @param app the application context
   * @param url the url
   * @return the validated url;
   */
  URL validateApplicationURL(iPlatformAppContext app, URL url);

  /**
   * Sets the  activity listener for receiving activity specific events
   * @param l the listener
   */
  void setActivityListener(aActivityListener l);

  /**
   * Sets the  activity listener for receiving activity specific events
   * return the listener
   */
  aActivityListener getActivityListener();

  /**
   * Gets the associated android activity
   *
   * @return the associated android activity
   */
  Activity getAndroidActivity();

  iPlatformAppContext getAppContext();

  /**
   * Gets the intent uri for the activity
   *
   * @return the intent uri for the activity
   */
  Uri getIntentUri();

  /**
   * Gets the last key or motion event
   *
   * @return the the last key or motion event
   */
  Object getLastEvent();

  /**
   * Gets the last key event
   *
   * @return the the last key event
   */
  KeyEvent getLastKeyEvent();

  /**
   * Gets the last motion event
   *
   * @return the the last motion event
   */
  MotionEvent getLastMotionEvent();

  TextToSpeech getTextToSpeech();

  boolean isSpinnerDialog(Dialog dialog);

  /**
   * Returns whether the UI is locked
   * @return true if keyboard and touch events are disabled; false otherwise
   */
  boolean isUILocked();
}
