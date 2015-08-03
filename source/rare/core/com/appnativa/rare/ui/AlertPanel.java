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

package com.appnativa.rare.ui;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.BeanViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.TextAreaWidget;
import com.appnativa.rare.widget.TextFieldWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;

import java.util.HashMap;
import java.util.List;

public class AlertPanel extends LinearPanel implements iActionListener {
  static UIColor         foregroundColor;
  static UIColor         backgroundColor;
  static UIColor         lineColor;
  static UIColor         titleLineColor;
  private static boolean hasErrorTitleChecked;
  private static boolean hasErrorTitleTemplate;
  private static boolean showApplicationIcon;

  static {
    foregroundColor     = Platform.getUIDefaults().getColor("Rare.Alert.foregroundColor");
    backgroundColor     = Platform.getUIDefaults().getColor("Rare.Alert.backgroundColor");
    showApplicationIcon = !"false".equals(Platform.getUIDefaults().get("Rare.Alert.showApplicationIcon"));
    lineColor           = Platform.getUIDefaults().getColor("Rare.Alert.lineColor");

    if (backgroundColor == null) {
      backgroundColor = ColorUtils.getBackground();
    }

    if (lineColor == null) {
      lineColor = ColorUtils.getDisabledForeground();
    }

    if (titleLineColor == null) {
      titleLineColor = Platform.getUIDefaults().getColor("Rare.Alert.title.lineColor");
    }

    if (titleLineColor == null) {
      titleLineColor = lineColor;
    }
  }

  iFunctionCallback callback;
  PushButtonWidget  cancelButton;
  //  PushButtonWidget  detailsButton;
  //  TextAreaWidget    detailsField;
  //  UIDimension       hiddenDetailsSize;
  boolean          isError;
  PushButtonWidget noButton;
  LabelWidget      titleLabel;
  //  UIDimension       visibleDetailsSize;
  WindowViewer     window;
  PushButtonWidget yesButton;

  public AlertPanel(iWidget context, String title, Object message, iPlatformIcon icon, boolean forError) {
    super(context, false, null, "FILL:[130dlu,d]:GROW");
    isError = forError;
    setBackground(backgroundColor);

    if (forError && hasErrorTitleChecked) {
      hasErrorTitleChecked = true;

      Label cfg = (Label) Platform.getWindowViewer().createConfigurationObject("Label", "Rare.Alert.errorTitle");

      hasErrorTitleTemplate = cfg.templateName.getValue() != null;
    }

    if (title == null) {
      title = "";
    }

    String template;

    if (forError && hasErrorTitleTemplate) {
      template = "Rare.Alert.errorTitle";
    } else {
      template = "Rare.Alert.title";
    }

    LabelWidget l = createLabel(title, template, true);

    l.setWordWrap(true);

    float d = ScreenUtils.PLATFORM_PIXELS_4;

    l.setMargin(d, d, d, d);

    if (icon == null) {
      icon = Platform.getResourceAsIcon("Rare.icon.rare");
    }

    if (icon != null) {
      l.setIcon(icon);
    }

    addComponent(l.getContainerComponent(), "FILL:[20dlu,d]:NONE");
    titleLabel = l;

    if (message != null) {
      if (message instanceof iWidget) {
        addComponent(((iWidget) message).getContainerComponent(), "FILL:[30dlu,d]:GROW");
      } else if (message instanceof iPlatformComponent) {
        addComponent((iPlatformComponent) message, "FILL:[30dlu,d]:GROW");
      } else {
        String         s  = message.toString();
        TextAreaWidget ta = createTextArea(s, "Rare.Alert.message");

        //        l = createLabel(s, "Rare.Alert.message", false);
        adjustLabel(ta, false);
        addComponent(ta.getContainerComponent(), "FILL:[30dlu,d]:GROW");
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    boolean            canceled    = false;
    Object             returnValue = null;
    iPlatformComponent c           = e.getComponent();

    //    if ((detailsButton != null) && (c == detailsButton.getDataComponent())) {
    //      if (detailsField != null) {
    //        boolean visible = !detailsField.isVisible();
    //
    //        detailsField.setVisible(visible);
    //
    //        String s = Platform.getWindowViewer().getString(visible
    //                ? "Rare.runtime.text.detailsExpand"
    //                : "Rare.runtime.text.detailsCollapse");
    //
    //        detailsButton.setValue(s);
    //        detailsButton.update();
    //
    //        if (visible) {
    //          PlatformHelper.setPreferredSizeEx(this, StringCache.valueOf(visibleDetailsSize.width),
    //                                            StringCache.valueOf(visibleDetailsSize.height));
    //        } else {
    //          PlatformHelper.setPreferredSizeEx(this, StringCache.valueOf(hiddenDetailsSize.width),
    //                                            StringCache.valueOf(hiddenDetailsSize.height));
    //        }
    //
    //        revalidate();
    //        packWindow();
    //      }
    //
    //      return;
    //    }
    if ((yesButton != null) && (c == yesButton.getDataComponent())) {
      returnValue = Boolean.TRUE;
    }

    if ((noButton != null) && (c == noButton.getDataComponent())) {
      returnValue = Boolean.FALSE;
    }

    if ((cancelButton != null) && (c == cancelButton.getDataComponent())) {
      canceled = true;
    }

    final iFunctionCallback cb = callback;

    callback = null;
    window.disposeOfWindow();

    if (cb != null) {
      cb.finished(canceled, returnValue);
    }
  }

  public void cancel() {
    if (window != null) {
      final iFunctionCallback cb = callback;

      window.disposeOfWindow();

      if (cb != null) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(true, null);
          }
        });
      }
    }
  }

  public static PushButtonWidget createButton(String text, String templateName, String borderInsets,
          iActionListener listener) {
    return createButton(text, templateName, borderInsets, listener, lineColor);
  }

  public static PushButtonWidget createButton(String text, String templateName, String borderInsets,
          iActionListener listener, UIColor borderColor) {
    WindowViewer w   = Platform.getWindowViewer();
    PushButton   cfg = (PushButton) Platform.getWindowViewer().createConfigurationObject("PushButton", templateName);

    if (cfg.value.getValue() == null) {
      cfg.value.setValue(text);
    }
    //
    //    if ((borderInsets != null) && (cfg.getBorders() == null)) {
    //      CBorder b = cfg.addBorder(Widget.CBorder.matte);
    //
    //      b.spot_setAttribute("insets", borderInsets);
    //      b.spot_setAttribute("color", borderColor.toString());
    //    }

    if (cfg.templateName.getValue() == null) {
      cfg.buttonStyle.setValue(PushButton.CButtonStyle.hyperlink_always_underline);
    }

    PushButtonWidget pb = PushButtonWidget.create(w, cfg);

    if (cfg.templateName.getValue() == null) {
      if (foregroundColor != null) {
        pb.setForeground(foregroundColor);
      } else {
        pb.setForeground(ColorUtils.getForeground());
      }
    }

    pb.addActionListener(listener);

    return pb;
  }

  @Override
  public void dispose() {
    final iFunctionCallback cb = callback;

    super.dispose();
    yesButton    = null;
    cancelButton = null;
    noButton     = null;
    callback     = null;
    titleLabel   = null;
    window       = null;
    //    detailsField  = null;
    //    detailsButton = null;

    if (cb != null) {
      cb.finished(true, null);
    }
  }

  public static AlertPanel error(iWidget context, ErrorInformation ei) {
    iPlatformIcon icon  = getIcon("Rare.icon.alertError");
    String        title = ei.getTitle();

    if ((title == null) || (title.length() == 0)) {
      title = Platform.getResourceAsString("Rare.Alert.errorTitle");
    }

    AlertPanel     d = new AlertPanel(context, title, null, icon, true);
    String         s = ei.toAlertPanelString();
    TextAreaWidget l = d.createTextArea(s, "Rare.Alert.message");

    adjustLabel(l, true);
    d.isError = true;
    d.addComponent(l.getContainerComponent(), "FILL:[30dlu,d]:GROW");
    d.yesButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"), "Rare.Alert.button", null,
                               d);
    d.addButtons(context);

    return d;
  }

  public static AlertPanel error(iWidget context, Throwable e, boolean showTerminate) {
    return error(context, new ErrorInformation(e));
  }

  public static AlertPanel ok(iWidget context, String title, iPlatformComponent comp, iPlatformIcon icon) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertInfo");
    }

    AlertPanel d = new AlertPanel(context, title, null, icon, false);

    d.addComponent(comp);
    d.yesButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"), "Rare.Alert.button", null,
                               d);
    d.yesButton.setDefaultButton(true);
    d.addButtons(context);

    return d;
  }

  public static AlertPanel ok(iWidget context, String title, Object message, iPlatformIcon icon) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertInfo");
    }

    AlertPanel d = new AlertPanel(context, title, message, icon, false);

    d.yesButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"), "Rare.Alert.button", null,
                               d);
    d.yesButton.setDefaultButton(true);
    d.addButtons(context);

    return d;
  }

  public static AlertPanel okCancel(iWidget context, String title, Object message, UIImageIcon icon) {
    return yesNo(context, title, message, icon, null, null, true);
  }

  public static AlertPanel prompt(iWidget context, String title, String prompt, Object value, iPlatformIcon icon) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertQuestion");
    }

    AlertPanel         d = new AlertPanel(context, title, null, icon, false);
    LabelWidget        l = d.createLabel(prompt, "Rare.Alert.prompt.text", false);
    iPlatformComponent field;

    if (value instanceof iWidget) {
      field = ((iWidget) value).getContainerComponent();
    } else if (value instanceof iPlatformComponent) {
      field = (iPlatformComponent) value;
    } else {
      TextFieldWidget tf = d.createTextField((value == null)
              ? ""
              : value.toString(), "Rare.Alert.prompt.textfield", false);

      field = tf.getContainerComponent();
    }

    if (ScreenUtils.getWidth() < UIScreen.platformPixels(600)) {
      d.addComponent(l.getContainerComponent());
      d.addComponent(field);
    } else {
      LinearPanel p = new LinearPanel(context, true);

      p.addComponent(l.getContainerComponent(), "RIGHT:DEFAULT:NONE");
      p.addComponent(field, "FILL:DEFAULT:GROW");
      d.addComponent(p);
    }

    d.yesButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"), "Rare.Alert.button",
                               "0,0,0,1", d);
    d.cancelButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.cancel"),
                                  "Rare.Alert.button", "0,0,0,1", d);
    d.addButtons(context);

    return d;
  }

  public void showDialog(iFunctionCallback cb) {
    callback = cb;

    BeanViewer  viewer = new BeanViewer(Platform.getWindowViewer(), this);
    PaintBucket pb;

    if (isError) {
      pb = Platform.getUIDefaults().getPaintBucket("Rare.Alert.errorPanelPainter");

      if (pb == null) {
        pb = Platform.getUIDefaults().getPaintBucket("Rare.Alert.panelPainter");
      }
    } else {
      pb = Platform.getUIDefaults().getPaintBucket("Rare.Alert.panelPainter");
    }

    if (pb == null) {
      UIColor bg = Platform.getUIDefaults().getColor("Rare.Alert.backgroundColor");

      if (bg == null) {
        bg = ColorUtils.getBackground();
      }

      pb = new PaintBucket(bg);
      pb.setBorder(BorderUtils.createShadowBorder(ScreenUtils.PLATFORM_PIXELS_7));
      Platform.getUIDefaults().put("Rare.Alert.panelPainter", pb);
    }

    HashMap options = new HashMap();

    options.put("decorated", false);
    options.put("opaque", false);
    options.put("visible", false);
    window = viewer.showAsDialog(options);
    window.setComponentPainter(pb.getCachedComponentPainter());

    if (titleLabel != null) {
      window.addWindowDragger(titleLabel);
    }

    window.setDefaultButton(yesButton);
    window.showWindow();
  }

  public static void showErrorDialog(ErrorInformation ei) {
    showErrorDialog(ei, null);
  }

  public static void showErrorDialog(Throwable error) {
    showErrorDialog(error, null);
  }

  public static void showErrorDialog(ErrorInformation ei, iFunctionCallback cb) {
    AlertPanel p = AlertPanel.error(Platform.getContextRootViewer(), ei);

    p.showDialog(cb);
  }

  public static void showErrorDialog(Throwable error, iFunctionCallback cb) {
    showErrorDialog(new ErrorInformation(null, error), cb);
  }

  public static AlertPanel yesNo(iWidget context, String title, String message, UIImageIcon icon) {
    return yesNo(context, title, message, icon, null, null, false);
  }

  public static AlertPanel yesNo(iWidget context, String title, Object message, iPlatformIcon icon, String yes,
                                 String no, boolean forOkCancel) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertQuestion");
    }

    WindowViewer w = Platform.getWindowViewer();

    if (yes == null) {
      yes = w.getString(forOkCancel
                        ? "Rare.text.ok"
                        : "Rare.text.yes");
    }

    if (no == null) {
      no = w.getString(forOkCancel
                       ? "Rare.text.cancel"
                       : "Rare.text.no");
    }

    AlertPanel d = new AlertPanel(context, title, message, icon, false);

    d.yesButton    = createButton(yes, "Rare.Alert.button", "0,0,0,1", d);
    d.cancelButton = createButton(no, "Rare.Alert.button", null, d);
    d.addButtons(context);

    return d;
  }

  public static AlertPanel yesNoCancel(iWidget context, String title, Object message, iPlatformIcon icon, String yes,
          String no, String cancel) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertQuestion");
    }

    WindowViewer w = Platform.getWindowViewer();

    if (yes == null) {
      yes = w.getString("Rare.text.yes");
    }

    if (no == null) {
      no = w.getString("Rare.text.no");
    }

    if (cancel == null) {
      cancel = w.getString("Rare.text.cancel");
    }

    AlertPanel d = new AlertPanel(context, title, message, icon, false);

    d.yesButton    = createButton(yes, "Rare.Alert.button", "0,0,0,1", d);
    d.noButton     = createButton(no, "Rare.Alert.button", "0,0,0,1", d);
    d.cancelButton = createButton(cancel, "Rare.Alert.button", null, d);
    d.addButtons(context);

    return d;
  }

  protected void addButtons(iWidget context) {
    LinearPanel     p = createButtonPanel(context, true);
    iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.Alert.buttonPanel.border");

    if (b != null) {
      p.setBorder(b);
    }

    addComponent(p, "FILL:[20dlu,d]:NONE");
  }

  protected LinearPanel createButtonPanel(iWidget context, boolean horizontal) {
    LinearPanel p     = new LinearPanel(context, horizontal, "FILL:DEFAULT:GROW", "FILL:DEFAULT:GROW");
    int         count = 0;

    if (cancelButton != null) {
      p.addComponent(cancelButton.getContainerComponent());
      count++;
    }

    if (noButton != null) {
      p.addComponent(noButton.getContainerComponent());
      count++;
    }

    if (yesButton != null) {
      p.addComponent(yesButton.getContainerComponent());
      count++;
    }

    int[] a = null;

    if (count == 2) {
      a = new int[] { 1, 2 };
    } else if (count == 3) {
      a = new int[] { 1, 2, 3 };
    }

    if (a != null) {
      if (horizontal) {
        p.getFormLayout().setColumnGroups(new int[][] {
          a
        });
      } else {
        p.getFormLayout().setRowGroups(new int[][] {
          a
        });
      }
    }

    return p;
  }

  protected LabelWidget createLabel(Object text, String templateName, boolean title) {
    WindowViewer w   = Platform.getWindowViewer();
    Label        cfg = (Label) Platform.getWindowViewer().createConfigurationObject("Label", templateName);

    if (!cfg.templateName.spot_hasValue()) {
      if (title) {
        cfg.font.size.setValue("+2");
      } else {
        cfg.getContentPaddingReference().setValues(8, 4, 8, 8);
      }
    }

    LabelWidget l = LabelWidget.create(w, cfg);

    l.setText(messageToString(text));

    if (!cfg.templateName.spot_hasValue()) {
      if (title) {
        UIFont f = FontUtils.getDefaultFont();

        l.setFont(f.deriveFontSize(f.getSize2D() + 2));

        int             space = UIScreen.platformPixels(4);
        iPlatformBorder b     = new UICompoundBorder(new UIMatteBorder(new UIInsets(0, 0, 2, 0), titleLineColor),
                                  new UIEmptyBorder(space, space, space, space));

        l.setBorder(b);
      }

      if (foregroundColor != null) {
        l.setForeground(foregroundColor);
      } else {
        l.setForeground(ColorUtils.getForeground());
      }
    }

    l.setWordWrap(true);

    return l;
  }

  protected TextAreaWidget createTextArea(String text, String templateName) {
    WindowViewer w   = Platform.getWindowViewer();
    TextArea     cfg = (TextArea) Platform.getWindowViewer().createConfigurationObject("TextArea", templateName);

    if ((cfg.templateName.getValue() == null)) {
      cfg.bgColor.setValue("transparent");
    }

    cfg.editable.setValue(false);
    cfg.wordWrap.setValue(true);

    TextAreaWidget ta = new TextAreaWidget(w);

    ta.configure(cfg);

    if ((cfg.templateName.getValue() == null)) {
      if (foregroundColor != null) {
        ta.setForeground(foregroundColor);
      } else {
        ta.setForeground(ColorUtils.getForeground());
      }

      ta.setBorder(new UIEmptyBorder(4, 8, 4, 4));
    }

    ta.setText(text);

    return ta;
  }

  protected TextFieldWidget createTextField(String text, String templateName, boolean details) {
    WindowViewer w   = Platform.getWindowViewer();
    TextField    cfg = (TextField) Platform.getWindowViewer().createConfigurationObject("TextField", templateName);

    cfg.editable.setValue(true);

    TextFieldWidget tf = new TextFieldWidget(w);

    tf.configure(cfg);
    tf.setText(text);

    return tf;
  }

  protected CharSequence messageToString(Object message) {
    if (message instanceof CharSequence) {
      return (CharSequence) message;
    }

    return message.toString();
  }

  protected void packWindow() {
    iWidget w = Platform.findWidgetForComponent(this);

    if (w != null) {
      w.getWindow().pack();
    }
  }

  protected static iPlatformIcon getIcon(String name) {
    iPlatformIcon icon = Platform.getAppContext().getResourceAsIconEx(name);

    if ((icon == null) && showApplicationIcon) {
      List<UIImageIcon> icons = Platform.getAppContext().getWindowManager().getWindowIcons();

      if ((icons != null) && (icons.size() > 0)) {
        icon = icons.get(0);
      }
    }

    return icon;
  }

  private static void adjustLabel(aWidget l, boolean error) {
    float sw = Platform.getWindowViewer().getWidth();

    if (UIScreen.fromPlatformPixels(sw) < 400) {
      sw = UIScreen.getWidth();
    }

    UIFont f = l.getFont();

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    String s = l.getValueAsString();

    if (s.length() != 0) {
      int cw  = 0;
      int mcw = 0;
      int len = s.length();

      for (int i = 0; i < len; i++) {
        cw++;

        if (s.charAt(i) == '\n') {
          if (cw > mcw) {
            mcw = cw;
          }

          cw = 0;
        }
      }

      if (mcw == 0) {
        mcw = cw;
      }

      mcw = (int) (mcw * .66);
      cw  = FontUtils.getCharacterWidth(f);

      int chars = (int) sw / cw;

      chars = Math.min(mcw, chars - 2);
      s     = chars + "ch";

      int ln = Math.max((int) (len / chars), 3);

      l.setPreferredSize(s, ln + "ln");
    }
  }
}
