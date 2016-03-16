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

import java.util.HashMap;
import java.util.List;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.TextAreaWidget;
import com.appnativa.rare.widget.TextFieldWidget;
import com.appnativa.rare.widget.iWidget;

/**
 * This class provides a standard cross platform set of standard panels
 * for use in alerting and prompting a user. This is the class that is
 * instantiated wine using the alerting/confirmation functions on a window viewer.
 * <p>
 * This class is the action listener for all of the standard buttons on the panel.
 * To change the behavior of a standard button, simply remove this listener from
 * the button and add your own.
 * </p>
 * @author Don DeCoteau
 *
 */
public class AlertPanel extends LinearPanel implements iActionListener {
  static UIColor         foregroundColor;
  static UIColor         backgroundColor;
  static UIColor         titleBackgroundColor;
  static UIColor         lineColor;
  static UIColor         titleLineColor;
  private static boolean hasErrorTitleChecked;
  private static boolean hasErrorTitleTemplate;
  private static boolean showApplicationIcon;
  private static boolean rightAlignButtonsDefault;

  static {
    foregroundColor      = Platform.getUIDefaults().getColor("Rare.Alert.foregroundColor");
    backgroundColor      = Platform.getUIDefaults().getColor("Rare.Alert.backgroundColor");
    titleBackgroundColor = Platform.getUIDefaults().getColor("Rare.Alert.title.backgroundColor");
    showApplicationIcon  = !"false".equals(Platform.getUIDefaults().get("Rare.Alert.showApplicationIcon"));
    lineColor            = Platform.getUIDefaults().getColor("Rare.Alert.lineColor");
    rightAlignButtonsDefault  = Platform.getUIDefaults().getBoolean("Rare.Alert.rightAlignButtons",false);

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

    if (titleBackgroundColor == null) {
      titleBackgroundColor = backgroundColor;
    }
  }

  protected iFunctionCallback  callback;
  protected PushButtonWidget   cancelButton;
  protected boolean            isError;
  protected PushButtonWidget   noButton;
  protected LabelWidget        titleLabel;
  protected WindowViewer       window;
  protected PushButtonWidget   yesButton;
  protected WidgetPaneViewer   paneViewer;
  protected TextFieldWidget    promptWidget;
  protected iPlatformComponent messageComponent;
  protected boolean            rightAlignButtons;

  /**
   * Creates a new panel
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   * @param forError true if the panel is for displaying and error; false otherwise
   */
  public AlertPanel(iWidget context, String title, Object message, iPlatformIcon icon, boolean forError) {
    super(context, false, null, "FILL:[100dlu,d]:GROW");
    rightAlignButtons=rightAlignButtonsDefault;
    WindowViewer w = (context == null)
                     ? null
                     : context.getWindow();

    paneViewer = new WidgetPaneViewer((w == null)
                                      ? Platform.getWindowViewer()
                                      : w, this);
    isError    = forError;
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
    l.setParent(paneViewer);

    float d = ScreenUtils.PLATFORM_PIXELS_4;

    l.setMargin(d, d, d, d);

    if (icon == null) {
      if (w != null) {
        icon = Platform.getWindowViewer().getIcon();
      }

      if (icon == null) {
        icon = Platform.getResourceAsIcon("Rare.icon.rare");
      }
    }

    if (icon != null) {
      l.setIcon(icon);
    }

    addComponent(l.getContainerComponent(), "FILL:[20dlu,p]:NONE");
    titleLabel = l;

    if (message != null) {
      if (message instanceof iWidget) {
        ((iWidget) message).setParent(paneViewer);
        messageComponent = ((iWidget) message).getContainerComponent();
        addComponent(messageComponent, "FILL:[30dlu,d]:GROW");
      } else if (message instanceof iPlatformComponent) {
        messageComponent = (iPlatformComponent) message;
        addComponent(messageComponent, "FILL:[30dlu,d]:GROW");
      } else {
        String s = message.toString();
        TextAreaWidget ta = createTextArea(s, "Rare.Alert.message");

        ta.setParent(paneViewer);
        Utils.adjustTextWidgetPreferredWidth(ta);
        addComponent(ta.getContainerComponent(), "FILL:[30dlu,d]:GROW");
      }
    }
  }

  public LabelWidget addStatusLabel() {
    final LabelWidget l = createLabel("", "Rare.Alert.statusLabel", false);

    //   addComponent(l.getContainerComponent());
    if (promptWidget != null) {
      promptWidget.addTextChangeListener(new iTextChangeListener() {
        @Override
        public boolean textChanging(Object source, int startIndex, int endIndex, CharSequence replacementString) {
          if (promptWidget.getTextLength() > 0) {
            l.setText("");
          }

          return true;
        }
        @Override
        public void textChanged(Object source) {}
        @Override
        public boolean shouldStopEditing(Object source) {
          return true;
        }
      });
    }

    return l;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    boolean            canceled    = false;
    Object             returnValue = null;
    iPlatformComponent c           = e.getComponent();

    if ((yesButton != null) && (c == yesButton.getDataComponent())) {
      returnValue = Boolean.TRUE;
    }

    if ((noButton != null) && (c == noButton.getDataComponent())) {
      returnValue = Boolean.FALSE;
    }

    if ((cancelButton != null) && (c == cancelButton.getDataComponent())) {
      canceled = true;
    }

    if (messageComponent != null) {
      messageComponent.removeFromParent();    //ensure that it is not disposed
      messageComponent = null;
    }

    final iFunctionCallback cb = callback;

    callback = null;
    window.disposeOfWindow();

    if (cb != null) {
      cb.finished(canceled, returnValue);
    }
  }

  /**
   * Cancels the dialog
   */
  public void cancel() {
    if (window != null) {
      final iFunctionCallback cb = callback;

      window.disposeOfWindow();
      window = null;

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

  /**
   * Disposes of the dialog
   */
  @Override
  public void dispose() {
    if (window != null) {
      cancel();
    }

    super.dispose();
    yesButton    = null;
    cancelButton = null;
    noButton     = null;
    callback     = null;
    titleLabel   = null;
    window       = null;
    promptWidget = null;
  }

  /**
   * Gets the cancel button for the dialog
   *
   * @return the button or null if there isn't one
   */
  public PushButtonWidget getCancelutton() {
    return cancelButton;
  }

  /**
   * Gets the no button for the dialog
   *
   * @return the button or null if there isn't one
   */
  public PushButtonWidget getNoButton() {
    return noButton;
  }

  /**
   * Gets the yes/ok button for the dialog
   *
   * @return the button
   */
  public PushButtonWidget getYesOrOkButton() {
    return yesButton;
  }

  /**
   * Shows the dialog.
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param cb the callback to be notified when the user closes the dialog
   */
  public void showDialog(iFunctionCallback cb) {
    callback = cb;
    addButtons(paneViewer);

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
    options.put("onWillClose", new Runnable() {
      @Override
      public void run() {
        if (cancelButton != null) {
          actionPerformed(new ActionEvent(cancelButton));
        } else if (noButton != null) {
          actionPerformed(new ActionEvent(noButton));
        } else if (yesButton != null) {
          actionPerformed(new ActionEvent(yesButton));
        } else {
          actionPerformed(new ActionEvent(this));
        }
      }
    });
    window = paneViewer.showAsDialog(options);
    window.setCancelable(false);
    window.setComponentPainter(pb.getCachedComponentPainter());

    if (titleLabel != null) {
      window.addWindowDragger(titleLabel);
    }

    if (yesButton != null) {
      window.setDefaultButton(yesButton);
    }

    window.showWindow();
  }

  /**
   * Adds the previously created standard buttons the to dialog
   * @param context the widget context
   */
  protected void addButtons(iWidget context) {
    LinearPanel     p = createButtonPanel(context, true);
    iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.Alert.buttonPanel.border");

    if (b != null) {
      p.setBorder(b);
    }

    addComponent(p, "FILL:[20dlu,p]:NONE");
  }

  /**
   * Creates the button panel that holds the buttons
   *   @param context the widget context
   * @param horizontal true for horizontal buttons ; false otherwise
   * @return the panel
   */
  protected LinearPanel createButtonPanel(iWidget context, boolean horizontal) {
    LinearPanel p;
    int         count = 0;

    if (rightAlignButtons) {
      p = new LinearPanel(context, horizontal, "FILL:DEFAULT:GROW", "FILL:[10dlu,d]");
      p.addExpansionComponent();
    } else {
      p = new LinearPanel(context, horizontal, "FILL:DEFAULT:GROW", "FILL:DEFAULT:GROW");
    }

    count += addOtherButtonsBefore(p);

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

    count += addOtherButtonsAfter(p);

    int[] a = (count > 1)
              ? new int[count]
              : null;

    if (a != null) {
      for (int i = 0; i < count; i++) {
        if (rightAlignButtons) {
          a[i] = i + 2;
        } else {
          a[i] = i + 1;
        }
      }

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

  /**
   * Method for sub-classes to add other buttons
   * to a panel before any of the standard buttons are added
   *
   * @param buttonPanel the button panel
   * @return the number of other buttons added
   */
  protected int addOtherButtonsBefore(LinearPanel buttonPanel) {
    return 0;
  }

  /**
   * Method for sub-classes to add other buttons
   * to a panel after the standard buttons are added
   *
   * @param buttonPanel the button panel
   * @return the number of other buttons added
   */
  protected int addOtherButtonsAfter(LinearPanel buttonPanel) {
    return 0;
  }

  /**
   * Creates a label for displaying the message
   *
   * @param text the text to display
   * @param templateName the template to use (use null for the default template)
   * @return the widget
   */
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
        iPlatformBorder b     = new UICompoundBorder(new UIMatteBorder(new UIInsets(0, 0, space / 2, 0),
                                  titleLineColor), new UIEmptyBorder(space, space, space, space));

        l.setBorder(b);
      }

      if (foregroundColor != null) {
        l.setForeground(foregroundColor);
      } else {
        l.setForeground(ColorUtils.getForeground());
      }
    }

    if (title && (cfg.bgColor.getValue() == null) && (titleBackgroundColor != null)) {
      l.setBackground(titleBackgroundColor);
    }

    l.setWordWrap(true);

    return l;
  }

  /**
   * Creates a text area for displaying the message
   *
   * @param text the text to display
   * @param templateName the template to use (use null for the default template)
   * @return the widget
   */
  protected TextAreaWidget createTextArea(String text, String templateName) {
    WindowViewer w   = Platform.getWindowViewer();
    TextArea     cfg = (TextArea) Platform.getWindowViewer().createConfigurationObject("TextArea", templateName);

    if ((cfg.templateName.getValue() == null)) {
      cfg.bgColor.setValue("transparent");
    }

    int len = Functions.length(text, "\n");

    if (len > 24) {
      len = 24;
    }

    cfg.bounds.height.setValue((len+2)+"ln");
    cfg.editable.setValue(false);
    cfg.wordWrap.setValue(true);
    cfg.focusPainted.setValue(false);

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
    ta.setCaretPosition(0);
    return ta;
  }

  /**
   * Creates a text field for receiving input
   *
   * @param text the default value
   * @param templateName the template to use (use null for the default template)
   * @return the widget
   */
  protected TextFieldWidget createTextField(String text, String templateName) {
    WindowViewer w   = Platform.getWindowViewer();
    TextField    cfg = (TextField) Platform.getWindowViewer().createConfigurationObject("TextField", templateName);

    cfg.editable.setValue(true);

    TextFieldWidget tf = new TextFieldWidget(w);

    tf.configure(cfg);
    tf.setText(text);

    return tf;
  }

  /**
   * Converts a message object to a string
   * @param message the message
   * @return the string
   */
  protected CharSequence messageToString(Object message) {
    if (message instanceof CharSequence) {
      return (CharSequence) message;
    }

    return message.toString();
  }

  /**
   * Packs the window
   */
  protected void packWindow() {
    iWidget w = Platform.findWidgetForComponent(this);

    if (w != null) {
      w.getWindow().pack();
    }
  }

  /**
   * Creates an alert style button
   *
   * @param text the text for the button
   * @param templateName the template to use (use null for the default template)
   * @param listener the action listener
   * @return the button widget
   */
  public static PushButtonWidget createButton(String text, String templateName, iActionListener listener) {
    WindowViewer w   = Platform.getWindowViewer();
    PushButton   cfg = (PushButton) Platform.getWindowViewer().createConfigurationObject("PushButton", templateName);

    if (cfg.value.getValue() == null) {
      cfg.value.setValue(text);
    }

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

  /**
   * Creates an error dialog for displaying the specified error information
   *
   * @param context the widget context
   * @param ei the error information
   */
  public static AlertPanel error(iWidget context, ErrorInformation ei) {
    iPlatformIcon icon  = getIcon("Rare.icon.alertError");
    String        title = ei.getTitle();

    if ((title == null) || (title.length() == 0)) {
      title = Platform.getResourceAsString("Rare.Alert.errorTitle");
    }

    AlertPanel     d = new AlertPanel(context, title, null, icon, true);
    final String   s = ei.toAlertPanelString();
    TextAreaWidget l = d.createTextArea(s, "Rare.Alert.message");

    if (d.titleLabel != null) {
      d.titleLabel.setEventHandler(iConstants.EVENT_CLICK, new Runnable() {
        @Override
        public void run() {
          try {
            Platform.getWindowViewer().copyToClipboard(s);
            UINotifier.showMessage(Platform.getResourceAsString("bv.text.error_text_copied_to_clipboard"));
          } catch(Exception e) {
            Platform.ignoreException(e);
          }
        }
      }, true);
    }

    Utils.adjustTextWidgetPreferredWidth(l);
    d.isError = true;
    d.addComponent(l.getContainerComponent(), "FILL:[30dlu,d]:GROW");
    d.yesButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"), "Rare.Alert.button", d);

    return d;
  }

  /**
   * Creates an error dialog for displaying information about the specified exception
   *
   * @param context the widget context
   * @param error the error
   */
  public static AlertPanel error(iWidget context, Throwable error) {
    return error(context, new ErrorInformation(error));
  }

  /**
   * Creates an alerting dialog box with an ok button
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   *
   */
  public static AlertPanel ok(iWidget context, String title, Object message, iPlatformIcon icon) {
    return ok(context, title, message, icon, null);
  }

  /**
   * Creates an alerting dialog box with an ok button
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   * @param buttonTemplate the template for the buttons (can be null)
   *
   */
  public static AlertPanel ok(iWidget context, String title, Object message, iPlatformIcon icon,
                              String buttonTemplate) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertInfo");
    }

    AlertPanel d = new AlertPanel(context, title, message, icon, false);

    d.yesButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"),
                               d.getButtonTemplate(buttonTemplate), d);
    d.yesButton.setDefaultButton(true);

    return d;
  }

  /**
   * Creates an ok/cancel dialog box, allowing the user to choose one of those
   * options
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   *
   */
  public static AlertPanel okCancel(iWidget context, String title, Object message, iPlatformIcon icon) {
    return yesNo(context, title, message, icon, null, null, true);
  }

  /**
   * Creates a dialog box that prompts the user for input
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param prompt
   *          the prompt for the input field
   * @param value
   *          the default value for the input field. If the value is a widget
   *          or component then the widget/component will be displayed instead of a text field.
   * @param icon the window icon
   *
   */
  public static AlertPanel prompt(iWidget context, String title, String prompt, Object value, iPlatformIcon icon) {
    return prompt(context, title, prompt, value, icon, null);
  }

  /**
   * Creates a dialog box that prompts the user for input
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param prompt
   *          the prompt for the input field
   * @param value
   *          the default value for the input field. If the value is a widget
   *          or component then the widget/component will be displayed instead of a text field.
   * @param icon the window icon
   *
   * @param buttonTemplate the template for the buttons (can be null)
   */
  public static AlertPanel prompt(iWidget context, String title, String prompt, Object value, iPlatformIcon icon,
                                  String buttonTemplate) {
    if (icon == null) {
      icon = getIcon("Rare.icon.alertQuestion");
    }

    final AlertPanel   d = new AlertPanel(context, title, null, icon, false);
    LabelWidget        l = d.createLabel(prompt, "Rare.Alert.prompt.text", false);
    iPlatformComponent field;
    TextFieldWidget    tf = null;

    if (value instanceof iWidget) {
      field = ((iWidget) value).getContainerComponent();

      if (value instanceof TextFieldWidget) {
        tf = (TextFieldWidget) value;
      }
    } else if (value instanceof iPlatformComponent) {
      field = (iPlatformComponent) value;
    } else {
      tf    = d.createTextField((value == null)
                                ? ""
                                : value.toString(), "Rare.Alert.prompt.textfield");
      field = tf.getContainerComponent();
    }

    BorderPanel p = new BorderPanel(context);

    p.setBorder(new UIEmptyBorder(ScreenUtils.PLATFORM_PIXELS_4));
    p.setCenterView(field);

    if (ScreenUtils.getWidth() < UIScreen.platformPixels(2600)) {
      p.setTopView(l.getContainerComponent());
    } else {
      p.setLeftView(l.getContainerComponent());
      p.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, "2ln");
    }

    d.addComponent(p);
    d.promptWidget = tf;
    d.yesButton    = createButton(Platform.getAppContext().getResourceAsString("Rare.text.ok"),
                                  d.getButtonTemplate(buttonTemplate), d);
    d.cancelButton = createButton(Platform.getAppContext().getResourceAsString("Rare.text.cancel"),
                                  d.getButtonTemplate(buttonTemplate), d);

    if (tf != value) {
      final PushButtonWidget ok = d.yesButton;

      tf.addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          ok.click();
        }
      });
    }

    return d;
  }

  /**
   * Shows an error dialog displaying the specified error information
   *
   * @param ei the error information
   */
  public static void showErrorDialog(ErrorInformation ei) {
    showErrorDialog(ei, null);
  }

  /**
   * Shows an error dialog displaying the specified error information
   *
   * @param ei the error information
   * @param cb the callback to be notified when the user closes the dialog
   */
  public static void showErrorDialog(ErrorInformation ei, iFunctionCallback cb) {
    AlertPanel p = AlertPanel.error(Platform.getContextRootViewer(), ei);

    p.showDialog(cb);
  }

  /**
   * Shows an error dialog displaying information about the specified exception
   *
   * @param error the error
   */
  public static void showErrorDialog(Throwable error) {
    showErrorDialog(error, null);
  }

  /**
   * Shows an error dialog displaying information about the specified exception
   *
   * @param error the error
   * @param cb the callback to be notified when the user closes the dialog
   */
  public static void showErrorDialog(Throwable error, iFunctionCallback cb) {
    showErrorDialog(new ErrorInformation(null, error), cb);
  }

  /**
   * Creates a yes/no (or ok/cancel) dialog box, allowing the user to choose one of
   * those options.
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param icon the window icon
   * @param yes the string for the yes button
   * @param no the string for the no button
   * @param forOkCancel true to default to "Ok" for the confirming button; false for "Yes"
   */
  public static AlertPanel yesNo(iWidget context, String title, Object message, iPlatformIcon icon, String yes,
                                 String no, boolean forOkCancel) {
    return yesNo(context, title, message, icon, yes, no, forOkCancel, null);
  }

  /**
   * Creates a yes/no (or ok/cancel) dialog box, allowing the user to choose one of
   * those options.
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param icon the window icon
   * @param yes the string for the yes button
   * @param no the string for the no button
   * @param forOkCancel true to default to "Ok" for the confirming button; false for "Yes"
   * @param buttonTemplate the template for the buttons (can be null)
   */
  public static AlertPanel yesNo(iWidget context, String title, Object message, iPlatformIcon icon, String yes,
                                 String no, boolean forOkCancel, String buttonTemplate) {
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

    d.yesButton    = createButton(yes, d.getButtonTemplate(buttonTemplate), d);
    d.cancelButton = createButton(no, d.getButtonTemplate(buttonTemplate), d);

    return d;
  }

  /**
   * Creates a yes/no dialog box, allowing the user to choose one of those
   * options
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   *
   */
  public static AlertPanel yesNo(iWidget context, String title, String message, iPlatformIcon icon) {
    return yesNo(context, title, message, icon, null, null, false);
  }

  /**
   * Creates a yes/no/cancel dialog box, allowing the user to choose one of
   * those options.
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   * @param yes the string for the yes button
   * @param no the string for the no button
   * @param cancel the string for the cancel button
   */
  public static AlertPanel yesNoCancel(iWidget context, String title, Object message, iPlatformIcon icon, String yes,
          String no, String cancel) {
    return yesNoCancel(context, title, message, icon, yes, no, cancel, null);
  }

  /**
   * Creates a yes/no/cancel dialog box, allowing the user to choose one of
   * those options.
   *
   * @param context the widget context
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display (can be text, a widget, or a component)
   * @param icon the window icon
   * @param yes the string for the yes button
   * @param no the string for the no button
   * @param cancel the string for the cancel button
   * @param buttonTemplate the template for the buttons (can be null)
   */
  public static AlertPanel yesNoCancel(iWidget context, String title, Object message, iPlatformIcon icon, String yes,
          String no, String cancel, String buttonTemplate) {
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

    d.yesButton    = createButton(yes, d.getButtonTemplate(buttonTemplate), d);
    d.noButton     = createButton(no, d.getButtonTemplate(buttonTemplate), d);
    d.cancelButton = createButton(cancel, d.getButtonTemplate(buttonTemplate), d);

    return d;
  }

  /**
   * Retrieves a named icon
   * @param name the name of the icon
   * @return the icon
   */
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

  public boolean isRightAlignButtons() {
    return rightAlignButtons;
  }

  public void setRightAlignButtons(boolean rightAlignButtons) {
    this.rightAlignButtons = rightAlignButtons;
  }

  protected String getButtonTemplate(String buttonTemplate) {
    return (buttonTemplate == null)
           ? "Rare.Alert.button"
           : buttonTemplate;
  }
}
