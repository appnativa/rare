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

package com.appnativa.rare.widget;

import java.util.Map;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.MenuItem;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FocusedAction;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.MenuUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.ViewerCreator;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.border.SharedLineBorder;
import com.appnativa.rare.ui.border.UIBalloonBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.aSizeAnimation;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.SNumber;

public abstract class aPushButtonWidget extends aGroupableButton {

  /** whether the popup should be automatically closed */
  protected boolean autoClosePopup = true;

  /** whether the button should match background with its popup */
  protected boolean         matchBackgrounds      = true;
  protected UIColor         sharedBorderColor     = UILineBorder.getDefaultLineColor();
  protected float           sharedBorderThickness = ScreenUtils.platformPixelsf(1.5f);
  protected UIBalloonBorder balloonBorder;

  /** the action type for the button */
  protected int actionType;

  /** whether the button is a hyperlink */
  protected boolean hyperlink;

  /** Whether the button is initially selected */
  protected boolean initiallySelected;

  /** original popup properties */
  protected Map<String, String> origPopupAttributes;
  protected iPlatformAnimator   popupAnimator;

  /** resolved popup properties */
  protected Map<String, String> popupAttributes;
  protected UIRectangle         popupBounds;
  protected SPOTSet             popupButtomMenu;

  /** the button's popup component */
  protected iPopup popupComponent;

  /** the configuration of the popup widget */
  protected Widget popupConfiguration;

  /** the link of the popup widget */
  protected ActionLink                popupLink;
  protected iPlatformComponentPainter popupPainter;

  /** the popup widget */
  protected iWidget popupWidget;

  /** whether the popup and button will share a border when the popup is visible */
  protected boolean          shareBorder;
  protected int              sharedBorderArc = ScreenUtils.PLATFORM_PIXELS_4;
  protected SharedLineBorder sharedLineBorder;

  /** the current position of a toggle button */
  protected int togglePosition;

  /** if the hyper link is always underlined */
  protected boolean         underlineAlways;
  private RenderType        sizingAnchor;
  protected boolean         popupContentFocusable;
  protected boolean         popupScrollable;
  protected iActionListener popupMenuActionListener;
  protected boolean         popupTransient = true;

  public aPushButtonWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.PushButton;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch(actionType) {
      case PushButton.CActionType.submit_form :
        this.getFormViewer().submitForm(null);

        break;

      case PushButton.CActionType.clear_form :
        this.getFormViewer().reloadForm();

        break;

      case PushButton.CActionType.popup_widget :
        showPopupWidget();

        break;

      case PushButton.CActionType.popup_menu :
        showPopupMenu(0, getHeight());

        break;

      default :
        if (size() > 0) {
          togglePosition++;

          if (togglePosition >= size()) {
            togglePosition = 0;
          }

          setValue(get(togglePosition).toString());
        }

        super.actionPerformed(e);

        break;
    }
  }

  /**
   * Invokes the action handler for the button in line. This is done when
   * showing popup, menu an popup widgets.
   *
   */
  protected void inlineActionEvent() {
    aWidgetListener l = getWidgetListener();

    if ((l != null) && l.isActionEventEnabled()) {
      l.evaluate(iConstants.EVENT_ACTION, new ActionEvent(this), false);
    }
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((PushButton) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Creates the popup widget if it has not yet been created.
   */
  public void createPopupWidget() {
    if (popupWidget != null) {
      return;
    }

    if (popupConfiguration != null) {
      popupWidget = aContainer.createWidget(getParent(), popupConfiguration);
      
      if (popupConfiguration.focusable.spot_valueWasSet()) {
        popupContentFocusable = popupConfiguration.focusable.booleanValue();
      }

      if (popupWidget.getBorder() instanceof UIBalloonBorder) {
        UIBalloonBorder b = (UIBalloonBorder) popupWidget.getBorder();

        if (b.isAutoLocatePeak()) {
          balloonBorder = b;
        }
      }

      if (actionType == PushButton.CActionType.popup_menu) {
        ((aListViewer) popupWidget).addAll(this);
        ((aListViewer) popupWidget).addActionListener(new iActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            hidePopupContainer();

            RenderableDataItem item = ((aListViewer) popupWidget).getSelectedItem();

            if (item != null) {
              if (item.getActionListener() != null) {
                item.getActionListener().actionPerformed(new ActionEvent(getDataComponent()));
              } else if (popupMenuActionListener != null) {
                popupMenuActionListener.actionPerformed(new ActionEvent(getDataComponent()));
              }
            }
          }
        });
      }

      String s;

      if (origPopupAttributes != null) {
        s = origPopupAttributes.get("okWidget");

        if (s == null) {
          origPopupAttributes.get("okwidget");
        }

        s = origPopupAttributes.get("matchbackground");

        if (s == null) {
          s = origPopupAttributes.get("matchBackground");
        }

        if (s != null) {
          matchBackgrounds = s.equalsIgnoreCase("true");
        }
      }
    }

    popupConfiguration = null;
  }

  @Override
  public void dispose() {
    super.dispose();

    if (popupComponent != null) {
      popupComponent.dispose();
    }

    if (popupWidget != null) {
      popupWidget.dispose();
    }

    popupButtomMenu    = null;
    popupConfiguration = null;
    popupComponent     = null;
    popupWidget        = null;
    popupPainter       = null;
    sharedLineBorder   = null;
  }

  @Override
  public void hidePopupContainer() {
    if(popupWidget==null) {
      super.hidePopupContainer();
      return;
    }
    if (popupComponent != null) {
      popupComponent.hidePopup();
    }
  }

  @Override
  public void showPopupMenu(int x, int y) {
    if (actionType != PushButton.CActionType.popup_menu) {
      super.showPopupMenu(x, y);

      return;
    }

    int len = (popupButtomMenu == null)
              ? 0
              : popupButtomMenu.getCount();

    if (popupButtomMenu != null) {
      popupScrollable = "true".equals(popupButtomMenu.spot_getAttribute("scrollable"));
    }

    if (len > 0) {
      this.ensureCapacity(len);

      for (int i = 0; i < len; i++) {
        add(MenuUtils.createDataItem(this, (MenuItem) popupButtomMenu.getEx(i)));
      }

      popupButtomMenu = null;
    }

    if ((popupWidget == null) && (popupConfiguration == null)) {
      popupConfiguration = createPopupMenuConfiguration(size(), null);
    }

    showPopupWidget();
  }

  /**
   * Displays the button's popup widget
   */
  public void showPopupWidget() {
    showPopupWidgetEx();
  }

  /**
   * Creates a new push button widget
   *
   * @param parent
   *          the parent
   *
   * @return the radio button widget
   */
  public static PushButtonWidget create(iContainer parent) {
    PushButtonWidget widget = new PushButtonWidget(parent);

    widget.configure((PushButton) Platform.getAppContext().getWindowViewer().createConfigurationObject("PushButton"));

    return widget;
  }

  /**
   * Creates a new push button widget
   *
   * @param parent
   *          the parent
   * @param cfg
   *          the configuration
   *
   * @return the radio button widget
   */
  public static PushButtonWidget create(iContainer parent, PushButton cfg) {
    PushButtonWidget widget = new PushButtonWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  public void setDefaultButton(boolean isdefault) {}

  public void setPopupAnimator(iPlatformAnimator popupAnimator) {
    this.popupAnimator = popupAnimator;

    if (popupAnimator instanceof aSizeAnimation) {
      sizingAnchor = ((aSizeAnimation) popupAnimator).getDiagonalAnchor();
    } else {
      sizingAnchor = null;
    }
  }

  /**
   * Returns the selected index of list type pop-up widgets/menus associated
   * with this push button
   *
   * @return the selected index or -1 if there is no selection of list style
   *         popup widget
   */
  public int getSelectedIndex() {
    if (popupWidget instanceof aListViewer) {
      return ((aListViewer) popupWidget).getSelectedIndex();
    }

    return -1;
  }

  /**
   * Returns the selected item of list type pop-up widgets/menus associated with
   * this push button
   *
   * @return the selected item or null if there is no selection of list style
   *         popup widget
   */
  public RenderableDataItem getSelectedItem() {
    if (popupWidget instanceof aListViewer) {
      return ((aListViewer) popupWidget).getSelectedItem();
    }

    return null;
  }

  /**
   * This method return the popup widget ONLY if one has been created
   *
   * @return the widget or null if the button does not have a popup widget or if the popup widget has not yet been created
   */
  public iWidget getPopupWidget() {
    return popupWidget;
  }

  public void setSelectedIndex(int index) {
    int len = size();

    if ((index < len) && (index > 0)) {
      RenderableDataItem di = get(index);

      setText(di.toString());

      if (di.getIcon() != null) {
        setIcon(di.getIcon());
      }

      if (popupWidget instanceof aListViewer) {
        ((aListViewer) popupWidget).setSelectedIndex(index);
      }
    } else {
      setText("");

      if (popupWidget instanceof aListViewer) {
        ((aListViewer) popupWidget).clearSelection();
      }
    }
  }

  @Override
  public Object getHTTPFormValue() {
    if (submitValueType == Button.CSubmitValue.text) {
      return ((iActionComponent) dataComponent).getText();
    }

    return super.getHTTPFormValue();
  }

  public iPlatformAnimator getPopupAnimator() {
    return popupAnimator;
  }

  public boolean hasPopupWidget() {
    return (popupConfiguration != null) || (popupWidget != null);
  }

  protected void configureEx(PushButton cfg) {
    int buttonStyle;

    actionType = cfg.actionType.intValue();

    if ((getParent() instanceof ToolBarViewer) &&!cfg.buttonStyle.spot_valueWasSet()) {
      buttonStyle = PushButton.CButtonStyle.toolbar;
    } else {
      switch(actionType) {
        case PushButton.CActionType.popup_widget :
        case PushButton.CActionType.popup_menu :
          if (cfg.buttonStyle.spot_valueWasSet()) {
            buttonStyle = cfg.buttonStyle.intValue();
          } else {
            buttonStyle = PushButton.CButtonStyle.toolbar;
          }

          break;

        default :
          buttonStyle = cfg.buttonStyle.intValue();

          break;
      }
    }

    switch(buttonStyle) {
      case PushButton.CButtonStyle.hyperlink :
        hyperlink = true;

        break;

      case PushButton.CButtonStyle.hyperlink_always_underline :
        underlineAlways = true;
        hyperlink       = true;

        break;

      case PushButton.CButtonStyle.standard :
        break;

      default :
        break;
    }

    String s = null;

    if ((buttonStyle == PushButton.CButtonStyle.standard) &&!cfg.bounds.height.spot_valueWasSet()) {
      s = getAppContext().getUIDefaults().getString("Rare.PushButton.height");

      if (s != null) {
        cfg.bounds.height.setValue(s);
      }
    } else {
      s = null;
    }

    super.configureEx(cfg);

    if (s != null) {
      cfg.bounds.height.spot_clear();
    }

    s              = cfg.popupWidget.spot_getAttribute("closeOnAction");
    autoClosePopup = (s != null) && s.equalsIgnoreCase("true");

    if ((actionType == PushButton.CActionType.popup_widget) || (cfg.popupWidget.getValue() != null)) {
      removeActionListener(this);
      addActionListener(this);
      origPopupAttributes = cfg.popupWidget.spot_getAttributesEx();
      popupConfiguration  = (Widget) cfg.popupWidget.getValue();

      if (origPopupAttributes != null) {
        popupScrollable = "true".equals(origPopupAttributes.get("scrollable"));
        popupTransient  = !"false".equals(origPopupAttributes.get("transient"));

        if ((popupConfiguration == null) && (origPopupAttributes.get("url") != null)) {
          popupLink = new ActionLink(this, origPopupAttributes.get("url"), null);
        }
      }

      if (popupConfiguration != null) {
        if (!isDesignMode()) {
          popupConfiguration.spot_setParent(null);
        }
      }

      if (cfg.useSharedBorderForPopup.booleanValue()) {
        shareBorder = true;
        s           = cfg.useSharedBorderForPopup.spot_getAttribute("color");

        if (s != null) {
          sharedBorderColor = ColorUtils.getColor(s);
        }

        s = cfg.useSharedBorderForPopup.spot_getAttribute("cornerArc");

        if (s != null) {
          sharedBorderArc = ScreenUtils.platformPixels(SNumber.intValue(s));
        }

        s = cfg.useSharedBorderForPopup.spot_getAttribute("thickness");

        if (s != null) {
          sharedBorderThickness = ScreenUtils.platformPixelsf(SNumber.floatValue(s));
        }
      }
    }

    iActionComponent comp          = (iActionComponent) dataComponent;
    boolean          selAction     = cfg.enabledOnSelectionOnly.booleanValue();
    boolean          focusedAction = cfg.focusedAction.booleanValue();
    String           name          = cfg.name.getValue();

    if (focusedAction && (comp.getAction() == null) && (name != null & name.length() > 0)) {
      FocusedAction a = new FocusedAction(name, cfg.value.getValue(), comp.getIcon());

      a.setEnabledOnSelectionOnly(selAction);
      getAppContext().registerFocusedAction(a);
    }

    if ((actionType == PushButton.CActionType.list_toggle) && (cfg.dataURL.getValue() != null)) {
      this.handleDataURL(cfg, false);

      String             text = getValueAsString();
      int                len  = size();
      RenderableDataItem item;

      displayIcon = getIcon();

      for (int i = 0; i < len; i++) {
        item = get(i);
        s    = item.toString();

        if ((text != null) && text.equals(s)) {
          togglePosition = i;
        }
      }
    }

    initiallySelected = cfg.selected.booleanValue();

    if (initiallySelected) {
      setSelected(true);
    }

    if (cfg.orientation.spot_valueWasSet()) {
      Utils.setOrientation((iActionComponent) dataComponent, cfg.orientation.getValue());
    }

    if (cfg.popupAnimator.getValue() != null) {
      iPlatformAnimator a = aAnimator.createAnimator(this, cfg.popupAnimator);

      if (a != null) {
        a.setAutoOrient(true);
        setPopupAnimator(a);
      }
    }

    configurePainters(cfg, buttonStyle);
  }

  @Override
  protected void configureMenus(iPlatformComponent comp, Widget cfg, boolean textMenus) {
    if (actionType == PushButton.CActionType.popup_menu) {
      popupButtomMenu  = cfg.getPopupMenu();
      shareBorder      = true;
      matchBackgrounds = true;
    } else {
      super.configureMenus(comp, cfg, textMenus);
    }
  }

  protected void configurePainters(PushButton cfg, int buttonStyle) {
    PaintBucket pp = ColorUtils.configure(this, cfg.getPressedPainter(), null);
    PaintBucket sp = ColorUtils.configure(this, cfg.getSelectionPainter(), null);
    PaintBucket dp = ColorUtils.configure(this, cfg.getDisabledPainter(), null);

    if ((pp != null) || (sp != null) || (dp != null)) {
      if ((pp == null) && (sp == null)) {
        pp = PainterUtils.createButtonPaintHolder().pressedPainter;
      }

      PainterHolder p = new PainterHolder(null, sp, null, pp, dp);
      p.setForegroundColor(getForeground());
      getComponentPainter(true, true).setPainterHolder(p);
    } else {
      PainterHolder p = null;

      switch(buttonStyle) {
        case PushButton.CButtonStyle.toggle_toolbar :
          p = PainterUtils.createToggleToolBarButtonPaintHolder();

          break;

        case PushButton.CButtonStyle.split_toolbar :
        case PushButton.CButtonStyle.toolbar :
          p = PainterUtils.createToolBarButtonPaintHolder();

          break;

        case PushButton.CButtonStyle.hyperlink :
        case PushButton.CButtonStyle.hyperlink_always_underline :
          p = PainterUtils.createHyperlinkPaintHolder();

          break;

        case PushButton.CButtonStyle.standard :
          p = PainterUtils.createButtonPaintHolder();

          break;

        default :
          break;
      }

      if (p != null) {
        boolean npCloned = false;
        boolean pCloned  = false;

        if ((cfg.bgColor.getValue() != null) || (cfg.getBorders() != null)) {
          p       = (PainterHolder) p.clone();
          pCloned = true;

          if (p.normalPainter == null) {
            p.normalPainter = new PaintBucket();
            npCloned        = true;
          }

          if (cfg.bgColor.getValue() != null) {
            if (!npCloned && (p.normalPainter.getBackgroundPainter() != null)) {
              p.normalPainter = (PaintBucket) p.normalPainter.clone();
              p.normalPainter.setBackgroundPainter(null);
              npCloned = true;
            }
          }

          if (cfg.getBorders() != null) {
            if ((p.normalPainter.getBorder() != null) &&!npCloned) {
              p.normalPainter = (PaintBucket) p.normalPainter.clone();
            }

            p.normalPainter.setBorder(null);

            if ((p.pressedPainter != null) && (p.pressedPainter.getBorder() != null)) {
              p.pressedPainter = (PaintBucket) p.pressedPainter.clone();
              p.pressedPainter.setBorder(null);
            }
          }
        }

        if (cfg.fgColor.getValue() != null) {
          if (!pCloned) {
            p       = (PainterHolder) p.clone();
            pCloned = true;
          }

          if (!npCloned) {
            p.normalPainter = (PaintBucket) p.normalPainter.clone();
          }

          UIColor fg = getDataComponent().getForeground();
          p.setForegroundColor(fg);
          p.normalPainter.setForegroundColor(fg);

          if (p.pressedPainter.getForegroundColor() != null) {
            p.pressedPainter = (PaintBucket) p.pressedPainter.clone();
            p.pressedPainter.setForegroundColor(ColorUtils.getPressedVersion(fg));
          }
        }
        getComponentPainter(true, true).setPainterHolder(p);
      }
    }
  }

  /**
   * Called to create the popup component
   *
   * @return the popup component
   */
  protected iPopup createPopupComponent() {
    return Platform.getAppContext().getWindowManager().createPopup(this);
  }

  protected ListBox createPopupMenuConfiguration(int defaultVisibleRowCount, String template) {
    setParentOnAdd = false;

    if (template == null) {
      template = "Rare.PushButton.menu";
    }

    ListBox cfg = (ListBox) Platform.getWindowViewer().createConfigurationObject("ListBox", template);

    if (!cfg.bounds.width.spot_valueWasSet()) {
      cfg.bounds.width.spot_setAttribute("min", "12ch");
    }

    if (cfg.getItemDescription() == null) {
      cfg.getItemDescriptionReference().icon.setValue("resource:Rare.icon.empty");
    }

    cfg.singleClickActionEnabled.setValue(true);

    if (!cfg.templateName.spot_hasValue()) {
      cfg.showDividerLine.setValue(true);
      cfg.showDividerLine.spot_setAttribute("showLastLine", "false");
    }

    if ((defaultVisibleRowCount > 0) &&!cfg.visibleRowCount.spot_hasValue()) {
      cfg.visibleRowCount.setValue(defaultVisibleRowCount);
    }

    cfg.getContentPaddingReference().setValues(2, 8, 2, 2);

    return cfg;
  }

  /**
   * Disposes of the popup component forcing it the be recreated the next time
   * it is needed
   */
  protected void disposeOfPopup() {
    if (popupComponent != null) {
      popupComponent.dispose();
      popupComponent = null;
      if(popupWidget!=null) {
        getFormViewer().unregisterFormWidget(popupWidget);
      }
    }
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    switch(actionType) {
      case PushButton.CActionType.submit_form :
      case PushButton.CActionType.clear_form :
      case PushButton.CActionType.popup_widget :
      case PushButton.CActionType.popup_menu :
      case PushButton.CActionType.link :
      case PushButton.CActionType.list_toggle :
        ((iActionComponent) dataComponent).addActionListener(this);

        return;

      default :
        break;
    }
  }

  /**
   * Called to reset the buttons border and background, as necessary, after the
   * popup is hidden
   */
  protected void resetButtonBorderAndBackground() {
    if (sharedLineBorder != null) {
      getComponentPainter().setSharedBorder(null);
      update();
    }
  }

  protected void setupSharedBorder() {
    if (sharedLineBorder == null) {
      UIColor c = (UIColor) popupWidget.getContainerComponent().getClientProperty(iConstants.RARE_FILLCOLOR_PROPERTY);

      if (c == null) {
        c = popupWidget.getBackground();
      }

      if (c == null) {
        c = UIColorHelper.getBackground();
      }

      SharedLineBorder b = new SharedLineBorder(sharedBorderColor, sharedBorderThickness, sharedBorderArc);

      if (matchBackgrounds) {
        b.setBackgroundColor(c);
      }

      popupPainter = new UIComponentPainter();
      popupPainter.setBorder(b);
      popupPainter.setBackgroundColor(c);
      sharedLineBorder = b;
      getComponentPainter(true, true);    // force creation oc component painter;
    }
  }

  /**
   * Displays the button's popup widget
   */
  protected void showPopupWidgetEx() {
    if (popupComponent != null) {
      disposeOfPopup();

      return;
    }

    if (popupWidget == null) {
      if ((popupConfiguration == null) && (popupLink != null)) {
        try {
          ViewerCreator.createConfiguration(this, popupLink, new iFunctionCallback() {
            @Override
            public void finished(boolean canceled, Object returnValue) {
              if (returnValue instanceof Widget) {
                popupConfiguration = (Widget) returnValue;
                showPopupWidgetEx();
              } else if (returnValue instanceof Exception) {
                handleException((Exception) returnValue);
              }
            }
          });
        } catch(Exception ex) {
          handleException(ex);

          return;
        }

        popupLink = null;

        return;
      }

      createPopupWidget();
    } else if (actionType == PushButton.CActionType.popup_menu) {
      ((aListViewer) popupWidget).clearSelection();
    }

    if (shareBorder) {
      setupSharedBorder();
    }

    popupComponent = createPopupComponent();
    popupComponent.setTransient(popupTransient);
    popupComponent.setFocusable(popupContentFocusable);
    popupComponent.setMovable(false);
    popupComponent.setAnimator(popupAnimator);

    if (sharedLineBorder != null) {
      sharedLineBorder.setSharers(getContainerComponent(), popupComponent.getWindowPane());
    }

    if (popupPainter != null) {
      popupComponent.setComponentPainter(popupPainter);
    }

    if (popupWidget instanceof iViewer) {
      popupComponent.setViewer((iViewer) popupWidget);
    } else {
      popupComponent.setContent(popupWidget.getContainerComponent());
    }
    getFormViewer().registerFormWidget(popupWidget);
    iFormViewer fv=getFormViewer();
    if(fv!=popupWidget.getFormViewer()) {
      getFormViewer().registerFormWidget(popupWidget);
    }
    popupAttributes = Utils.resolveOptions(this, origPopupAttributes, popupAttributes);
    popupComponent.setOptions(popupAttributes);
    popupComponent.addPopupMenuListener(new iPopupMenuListener() {
      @Override
      public void popupMenuWillBecomeVisible(ExpansionEvent e) {
        aWidgetListener l = getWidgetListener();

        if (l != null) {
          l.popupMenuWillBecomeVisible(e);
        }

        updateButtonBorderAndBackground();
      }
      @Override
      public void popupMenuWillBecomeInvisible(ExpansionEvent e) {
        aWidgetListener l = getWidgetListener();

        if (l != null) {
          l.popupMenuWillBecomeInvisible(e);
        }

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            disposeOfPopup();
          }
        });
        resetButtonBorderAndBackground();
      }
      @Override
      public void popupMenuCanceled(ExpansionEvent e) {
        aWidgetListener l = getWidgetListener();

        if (l != null) {
          l.popupMenuCanceled(e);
        }
      }
    });

    if (popupBounds == null) {
      popupBounds = new UIRectangle();
    }

    UIDimension d = popupWidget.getPreferredSize();

    if (shareBorder) {
      d.width  += (ScreenUtils.PLATFORM_PIXELS_2 * sharedBorderThickness);
      d.height += (ScreenUtils.PLATFORM_PIXELS_2 * sharedBorderThickness);
    }
    float maxw= ScreenUtils.getUsableScreenSize().width-ScreenUtils.PLATFORM_PIXELS_8;
    if(maxw<d.width) {
      d.width=maxw;
    }

    getProposedPopupBounds(d, popupBounds);

    if (popupAnimator != null) {
      Location loc;

      if (popupBounds.y < -popupBounds.height) {
        loc = (popupBounds.x == 0)
              ? Location.TOP
              : Location.LEFT;
      } else {
        loc = (popupBounds.x == 0)
              ? Location.BOTTOM
              : Location.LEFT;
      }

      aAnimator.adjustAnimation(popupAnimator, true, sizingAnchor, loc);
    }

    if (shareBorder) {
      getContainerComponentEx().getOrientedSize(d);

      if ((popupBounds.y == 0)
          || SNumber.isEqual(popupBounds.y + popupBounds.height + d.height, ScreenUtils.PLATFORM_PIXELS_2)) {    // ==2
        // to compensate
        // for
        // size
        // of
        // top
        // and
        // bottom
        // border
        popupBounds.y -= ScreenUtils.PLATFORM_PIXELS_1;
      }
    }

    willShowPopup(popupComponent, popupBounds);
    popupComponent.setSize(popupBounds.width, popupBounds.height);
    updatePopupComponent(popupComponent);
    inlineActionEvent();
    popupComponent.showPopup(formComponent, popupBounds.x, popupBounds.y);
  }

  /**
   * Called to update the buttons border and background, as necessary, before
   * the popup is shown. Also responsible for setting the shared border on the
   * popup, if necessary
   */
  protected void updateButtonBorderAndBackground() {
    if (shareBorder && (popupWidget != null)) {
      getComponentPainter().setSharedBorder(sharedLineBorder);
      repaint();
    }
  }

  /**
   * Called to update the popup after it is created but before it is shown
   *
   * @param popup
   */
  protected abstract void updatePopupComponent(iPopup popup);

  protected void willShowPopup(iPopup p, UIRectangle bounds) {}

  protected void getProposedPopupBounds(UIDimension contentSize, UIRectangle r) {
    iPlatformBorder b = (popupPainter == null)
                        ? null
                        : popupPainter.getBorder();

    Utils.getProposedPopupBounds(r, formComponent, contentSize, 0, HorizontalAlign.AUTO, b,
                                 popupScrollable || (popupWidget instanceof iListHandler));

    if (balloonBorder != null) {
      balloonBorder.autoLocatePeakForProposedBounds(formComponent, r);
    }
  }

  /**
   * Gets the action listener to be invoked when a pop-up menu is selected and
   * the menu item does not have and associated action listener
   *
   * @return the listener
   */
  public iActionListener getPopupMenuActionListener() {
    return popupMenuActionListener;
  }

  /**
   * Sets the action listener to be invoked when a pop-up menu is selected and
   * the menu item does not have and associated action listener
   *
   * <p>
   * You can call the {@link #getSelectedItem()} method to retrieve the selected
   * item
   * <p>
   *
   * @param l
   *          the listener
   */
  public void setPopupMenuActionListener(iActionListener l) {
    this.popupMenuActionListener = l;
  }

  /**
   * Sets the popup (for popup_menu actionType buttons) as scroll able. This
   * will force the popup to anchored above or below the button. If the popup is
   * not scrollable its position will be adjusted so that as much of it as
   * possible will be seen on the screen.
   *
   * @param scrollable
   *          true for scrollable; false otherwise
   */
  public void setPopupScrollable(boolean scrollable) {
    this.popupScrollable = scrollable;
  }
}
