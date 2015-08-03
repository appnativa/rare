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

package com.appnativa.rare.platform.android.net;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.effects.ShakeAnimation;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.UTF8Helper;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import java.util.List;
import java.util.regex.Pattern;

/**
 * This class provides a UI for obtaining a user name and associated password
 * for password protected URLs.
 *
 * @author Don DeCoteau
 */
public class URLAuthenticator extends Authenticator implements DialogInterface.OnClickListener, OnFocusChangeListener {
  private static final Object lock = new Object();
  private static boolean      _configured;
  private static boolean      _forceLowerCasePassword;
  private static boolean      _keepPassword;
  private static boolean      _keepPasswordHash;
  private static String       _lastDomainName;
  private static char[]       _lastPassWord;
  private static String       _lastPassWordHash;
  private static String       _lastUserName;
  Activity                    activity;
  UIImageIcon                 dialogIcon;
  String                      passwordHelp;
  Pattern                     passwordPattern;
  Animation                   shakeAnimation;
  String                      userNameHelp;
  Pattern                     userNamePattern;

  /**  */
  private String userName = "";

  /**  */
  private String defaultHelp;

  /**  */
  private boolean                initialized;
  private PasswordAuthentication pa;

  /**  */
  private View passwordPanel;

  /**  */
  private String realmHelp;

  /** Creates a new instance of URLAuthenticator */
  public URLAuthenticator(Activity a) {
    activity = a;
  }

  public void onClick(DialogInterface dialog, int which) {}

  public void onFocusChange(View v, boolean hasFocus) {
    if (!hasFocus) {
      validateInput(v);
    }
  }

  /**
   * Returns the last domain namethat was entered during basic authentication
   *
   * @return an SHA1 hash of the last password that was entered
   */
  public static String getLastDomainName() {
    return _lastDomainName;
  }

  /**
   * Returns the last password that was entered during basic authentication
   *
   * @return an SHA1 hash of the last password that was entered
   * @see #setkeepPassword(boolean)
   */
  public static String getLastPassword() {
    return (_lastPassWord == null)
           ? null
           : new String(_lastPassWord);
  }

  /**
   * Returns an SHA1 hash of the last password that was entered during basic
   * authentication
   *
   * @return an SHA1 hash of the last password that was entered
   * @see #setkeepPasswordHash(boolean)
   */
  public static String getLastPasswordHash() {
    return _lastPassWordHash;
  }

  /**
   * Returns the last username that was entered during basic authentication
   *
   * @return the last username that was entered
   */
  public static String getLastUserName() {
    return _lastUserName;
  }

  /**
   * Configures the authenticator for an app Can only be called once
   *
   * @param app
   *          the app
   */
  static void configure(Application app) {
    if (!_configured) {
      _forceLowerCasePassword =
        "true".equalsIgnoreCase(app.httpAuthHandler.spot_getAttribute("forceLowerCasePassword"));
      _keepPasswordHash = "true".equalsIgnoreCase(app.httpAuthHandler.spot_getAttribute("keepPasswordHash"));
      _keepPassword     = "true".equalsIgnoreCase(app.httpAuthHandler.spot_getAttribute("keepPassword"));
    }
  }

  void finish(boolean ok) {
    try {
      if (!ok) {
        return;
      }

      userName = getUsernameField().getText().toString();

      String d = getSelectedDomain();

      _lastUserName   = userName;
      _lastDomainName = d;

      if (d != null) {
        userName = userName + '@' + d;
      }

      char[] pass = getPasswordField().getText().toString().toCharArray();

      if (_forceLowerCasePassword && (pass != null)) {
        int len = pass.length;

        for (int i = 0; i < len; i++) {
          pass[i] = Character.toLowerCase(pass[i]);
        }
      }

      if (_keepPasswordHash) {
        _lastPassWordHash = hash(pass);
      }

      if (_keepPassword) {
        _lastPassWord = pass;
      }

      pa = new PasswordAuthentication(userName, pass);
    } finally {
      synchronized(lock) {
        lock.notifyAll();
      }
    }
  }

  protected void setup() {
    AlertDialog alert = null;

    try {
      initComponents();
      getPasswordField().setText("");
      getUsernameField().setText(userName);

      String s = getRequestingPrompt();

      if ((s != null) && (s.length() > 0) && (realmHelp != null)) {
        s = Helper.expandString(realmHelp, s);
      } else {
        s = defaultHelp;
      }

      if (s != null) {
        getHelpArea().setText(s);
      }

      AlertDialog.Builder builder = new AlertDialog.Builder(activity);

      builder.setView(passwordPanel).setCancelable(false).setPositiveButton(
          Platform.getResourceAsString("Rare.text.ok"), new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          dialog.dismiss();
          finish(true);
        }
      }).setNegativeButton(Platform.getResourceAsString("Rare.text.cancel"), new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          dialog.cancel();
          finish(false);
        }
      });
      alert = builder.create();
    } catch(Exception e) {
      finish(false);

      return;
    }

    alert.show();
  }

  protected boolean validateInput(Object source) {
    if (source == getUsernameField()) {
      if ((userNamePattern != null) &&!userNamePattern.matcher(getUsernameField().getText()).matches()) {
        shake();
        showStatus("Rare.runtime.text.login.helpUsername");
        getUsernameField().requestFocus();

        return false;
      }
    } else if (source == getPasswordField()) {
      if ((passwordPattern != null)
          &&!passwordPattern.matcher(new String(getPasswordField().getText().toString())).matches()) {
        shake();
        showStatus("Rare.runtime.text.login.helpPassword");
        getPasswordField().requestFocus();

        return false;
      }
    }

    return true;
  }

  protected PasswordAuthentication getPasswordAuthentication() {
    pa = null;

    Runnable r = new Runnable() {
      public void run() {
        setup();
      }
    };

    synchronized(lock) {
      Platform.invokeLater(r);

      try {
        lock.wait();
      } catch(InterruptedException e) {}
    }

    return pa;
  }

  private String hash(char[] pass) {
    if (pass == null) {
      return "";
    }

    return Functions.sha1(UTF8Helper.getInstance().getBytes(pass, 0, pass.length), true);
  }

  private void hideDomain() {
    View v = passwordPanel.findViewWithTag("domain");

    if (v != null) {
      v.setVisibility(View.GONE);
    }

    v = passwordPanel.findViewWithTag("tableRow3");

    if (v != null) {
      v.setVisibility(View.GONE);
    }
  }

  private void initComponents() {
    if (!initialized) {
      initialized   = true;
      passwordPanel = (ViewGroup) AndroidHelper.getResourceComponentView("rare_layout_login");
      getUsernameLabel().setText(Platform.getResourceAsString("Rare.runtime.text.login.usernamePrompt"));
      getPasswordLabel().setText(Platform.getResourceAsString("Rare.runtime.text.login.passwordPrompt"));
      defaultHelp = Platform.getResourceAsString("Rare.runtime.text.login.help");
      realmHelp   = Platform.getResourceAsString("Rare.runtime.text.login.helpRealm");

      String s = Platform.getResourceAsString("Rare.runtime.text.login.passwordPattern");

      if ((s != null) && (s.length() > 0) &&!s.equals(".*")) {
        passwordPattern = Pattern.compile(s, Pattern.DOTALL);
        getPasswordField().setOnFocusChangeListener(this);
      }

      s = Platform.getResourceAsString("Rare.runtime.text.login.usernamePattern");

      if ((s != null) && (s.length() > 0) &&!s.equals(".*")) {
        userNamePattern = Pattern.compile(s, Pattern.DOTALL);
        getUsernameField().setOnFocusChangeListener(this);
      }

      String doms = Platform.getResourceAsString("Rare.runtime.text.login.domains");

      if ((doms != null) && (doms.length() > 0)) {
        setDomains(Platform.getResourceAsString("Rare.runtime.text.login.domainPrompt"),
                   CharScanner.getTokens(doms, ',', true));
      } else {
        hideDomain();
      }

      ShakeAnimation a = new ShakeAnimation();

      shakeAnimation = a.getAnimation();
    }
  }

  private void shake() {
    if ((shakeAnimation != null) && (passwordPanel != null)) {
      passwordPanel.startAnimation(shakeAnimation);
    }
  }

  private void showStatus(String key) {
    String s = Platform.getResourceAsString(key);

    getHelpArea().setText((s == null)
                          ? ""
                          : s);
  }

  private void setDomains(String prompt, List<String> tokens) {
    if ((tokens == null) || tokens.isEmpty()) {
      hideDomain();

      return;
    }

    AutoCompleteTextView tv   = (AutoCompleteTextView) passwordPanel.findViewWithTag("domain");
    ArrayAdapter<String> list = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, tokens);

    tv.setAdapter(list);

    if (prompt != null) {
      ((TextView) passwordPanel.findViewWithTag("domainLabel")).setText(prompt);
    }
  }

  private TextView getHelpArea() {
    return (TextView) passwordPanel.findViewWithTag("help");
  }

  private EditText getPasswordField() {
    return (EditText) passwordPanel.findViewWithTag("password");
  }

  private TextView getPasswordLabel() {
    return (TextView) passwordPanel.findViewWithTag("passwordLabel");
  }

  private String getSelectedDomain() {
    AutoCompleteTextView tv = (AutoCompleteTextView) passwordPanel.findViewWithTag("domain");

    if (tv.getVisibility() != View.VISIBLE) {
      return tv.getText().toString();
    }

    return null;
  }

  private EditText getUsernameField() {
    return (EditText) passwordPanel.findViewWithTag("username");
  }

  private TextView getUsernameLabel() {
    return (TextView) passwordPanel.findViewWithTag("usernameLabel");
  }
}
