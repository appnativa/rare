/*
 * @(#)ButtonView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.appnativa.rare.platform.swing.plaf.RareButtonUI;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.util.CharArray;
import com.appnativa.util.xml.XMLUtils;

public class ButtonView extends JButtonEx implements ChangeListener {
  ActionEvent     autoRepeatAction;
  boolean         invalidateParent;
  ActionListener  repeatListener;
  private boolean alwaysUnderline;
  private int     autoRepeatDelay;
  private String  originalText;
  private boolean platformButton;
  private Timer   timer;
  private boolean underlined;
  private boolean wordWrap;

  public ButtonView() {
    this(null, null);
  }

  public ButtonView(Icon icon) {
    this(null, icon);
  }

  public ButtonView(String value) {
    this(value, null);
  }

  public ButtonView(String text, Icon icon) {
    super(text, icon);
  }

  @Override
  public Icon getDisabledIcon() {
    Icon icon = super.getDisabledIcon();
    if (icon == null) {
      icon = getIcon();
      if (icon instanceof iPlatformIcon) {
        return ((iPlatformIcon) icon).getDisabledVersion();
      }
    }
    return icon;
  }

  public Icon getDisabledSelectedIcon() {
    Icon icon = super.getDisabledSelectedIcon();
    if (icon == null) {
      icon = getSelectedIcon();
      if (icon == null) {
        icon = getIcon();
      }
      if (icon instanceof iPlatformIcon) {
        return ((iPlatformIcon) icon).getDisabledVersion();
      }
    }
    return icon;
  }

  @Override
  public void revalidate() {
    if (invalidateParent) {
      Container p = getParent();
      if (p != null) {
        ((JComponent) p).revalidate();
      }
    } else {
      super.revalidate();
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (model.isArmed()) {
      if (autoRepeatDelay > 0) {
        autoRepeatAction = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "repeat");

        if (timer == null) {
          ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              fireActionPerformed(autoRepeatAction);
            }
          };

          timer = new Timer(autoRepeatDelay, l);
          timer.setRepeats(true);
        }

        timer.start();
      }
    } else {
      if (timer != null) {
        timer.stop();
      }
    }
  }

  @Override
  public void updateUI() {
    if (model != null) {
      this.model.removeChangeListener(this);
      this.model.addChangeListener(this);
    }

    if (isPlatformButton()) {
      super.updateUI();
    } else {
      setUI(RareButtonUI.getInstance());
    }
  }

  public void setAutoRepeats(int autoRepeatDelay) {
    this.autoRepeatDelay = autoRepeatDelay;
  }

  public void setInvalidateParent(boolean invalidateParent) {
    this.invalidateParent = invalidateParent;
  }

  public void setPlatformButton(boolean platformButton) {
    this.platformButton = platformButton;
    updateUI();
  }

  @Override
  public void setText(String text) {
    if (text == null) {
      text = "";
    }

    originalText = text;

    int len = text.length();

    if (wordWrap && (len > 0) && !text.startsWith("<html>")) {
      CharArray ca = new CharArray(text.length() + 20);

      ca.append("<html>");
      XMLUtils.escape(text.toCharArray(), 0, len, true, ca);
      ca.append("</html>");
      text = ca.toString();
    }

    super.setText(text);
  }

  public void setUnderlined(boolean underline, boolean always) {
    underlined = underline;
    alwaysUnderline = always;
    if (underline && !always) {
      setRolloverEnabled(true);
    }
  }

  public void setWordWrap(boolean wordWrap) {
    if (this.wordWrap != wordWrap) {
      this.wordWrap = wordWrap;

      if (ui instanceof RareButtonUI) {
        RareButtonUI u = (RareButtonUI) ui;

        if (u == RareButtonUI.getInstance()) {
          if (wordWrap) {
            u = new RareButtonUI();
            u.setWordWrap(true);
            setUI(u);
          }
        } else {
          u.setWordWrap(wordWrap);
        }
      } else if (wordWrap) {
        RareButtonUI u = new RareButtonUI();

        u.setWordWrap(true);
        setUI(u);
      }
    }
  }

  @Override
  public String getText() {
    return originalText;
  }

  public boolean isDrawUnderline() {
    if (!underlined) {
      return false;
    }

    return alwaysUnderline ? true : model.isRollover();
  }

  public boolean isInvalidateParent() {
    return invalidateParent;
  }

  public boolean isPlatformButton() {
    return platformButton;
  }

  public boolean isUnderlined() {
    return underlined;
  }

  public boolean isWordWrap() {
    return wordWrap;
  }

}
