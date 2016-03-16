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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.ComboBox;
import com.appnativa.rare.spot.EmptyText;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aComboBoxComponent;
import com.appnativa.rare.ui.aNonListListHandler;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iComboBox;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.iSpeechEnabler;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.SNumber;

import com.google.j2objc.annotations.Weak;

import java.util.Locale;
import java.util.Map;

public abstract class aComboBoxWidget extends aListWidget {
  protected boolean hideWhenReset;

  /** the list model */
  protected iPlatformListDataModel listModel;
  protected String                 originalValue;
  protected float                  popupFraction;
  protected String                 popupHeight;

  /** the popup widget */
  protected iWidget         popupWidget;
  protected String          popupX;
  protected String          popupY;
  protected String          valueAttribute;
  protected HorizontalAlign popupHorizontalAlignment = HorizontalAlign.AUTO;
  protected iWidget         valueWidget;

  public aComboBoxWidget() {}

  public aComboBoxWidget(iContainer parent) {
    super(parent);
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    if (isDisposed()) {
      return;
    }

    addEx(row);
  }

  /**
   * Adds a <code>PopupMenu</code> listener which will listen to notification
   * messages from the popup portion of the combo box.
   *
   * @param l
   *          the <code>iPopupMenuListener</code> to add
   */
  public void addPopupMenuListener(iPopupMenuListener l) {
    getComboBox().addPopupMenuListener(l);
  }

  /**
   * Cancels the popup
   */
  public void cancelPopup() {
    getComboBox().cancelPopup();
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((ComboBox) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
    handleDataURL(cfg);
  }

  @Override
  public boolean isKeepSelectionVisible() {
    return false;
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {}

  /**
   * Creates a new listbox widget
   *
   * @param parent
   *          the parent
   * @param cfg
   *          the configuration
   *
   * @return the listbox widget
   */
  public static ComboBoxWidget create(iContainer parent, ComboBox cfg) {
    ComboBoxWidget widget = new ComboBoxWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void dispose() {
    super.dispose();

    if (listModel != null) {
      listModel.dispose();
    }

    if (popupWidget != null) {
      popupWidget.dispose();
    }

    listModel                = null;
    popupWidget              = null;
    popupHorizontalAlignment = null;
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    if (deletingAllowed) {
      iPlatformTextEditor textEditor = getComboBox().getTextEditor();

      if (textEditor != null) {
        int    s = textEditor.getSelectionStart();
        int    e = textEditor.getSelectionEnd();
        Object o = null;

        if (s != e) {
          if (returnData) {
            o = textEditor.getSelectionString();
          }

          textEditor.deleteSelection();
        }

        return o;
      }
    }

    return null;
  }

  /**
   * Removes a <code>iPopupMenuListener</code>.
   *
   * @param l
   *          the <code>iPopupMenuListener</code> to remove
   * @see #addPopupMenuListener
   */
  public void removePopupMenuListener(iPopupMenuListener l) {
    getComboBox().removePopupMenuListener(l);
  }

  @Override
  public void reset() {
    super.reset();

    if (isHideWhenReset()) {
      setPopupVisible(false);
    }
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param index
   *          the index of the row that changed
   */
  public void rowChanged(int index) {
    if (listModel != null) {
      listModel.rowChanged(index);

      if (index == getSelectedIndex()) {
        setEditorValue(getSelectedItem());
      }
    }
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param item
   *          the instance of the row that changed
   */
  public void rowChanged(RenderableDataItem item) {
    if (listModel != null) {
      int index = indexOf(item);

      if (index != -1) {
        repaintRow(index);
      }
    }
  }

  /**
   * Notifies the list that the contents of the specified range of rows have
   * changed
   *
   * @param firstRow
   *          the first row
   * @param lastRow
   *          the last row
   */
  public void rowsChanged(int firstRow, int lastRow) {
    listModel.rowsChanged(firstRow, lastRow);

    int index = getSelectedIndex();

    if ((index >= firstRow) && (index <= lastRow)) {
      setEditorValue(getSelectedItem());
    }
  }

  @Override
  public void setAlternatingRowColor(UIColor color) {
    listComponent.setAlternatingRowColor(color);
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    listComponent.setAutoHilight(autoHilight);
  }

  @Override
  public void setDataEventsEnabled(boolean enabled) {
    if (listModel != null) {
      listModel.setEventsEnabled(enabled);
    }
  }

  @Override
  public void setDeselectEventsDisabled(boolean disabled) {
    listComponent.setDeselectEventsDisabled(disabled);
  }

  @Override
  public void setEditable(boolean editable) {
    getComboBox().setEditable(editable);
  }

  /**
   * Sets the value of the text to display when there is no selection
   *
   * @param text
   *          the text
   */
  public void setEmptyFieldText(String text) {
    ((aComboBoxComponent) dataComponent).setEmptyFieldText(text);
  }

  @Override
  public void setFromHTTPFormValue(Object value) {
    switch(submitValueType) {
      case ComboBox.CSubmitValue.selected_index :
        if (value == null) {
          setSelectedIndex(-1);

          return;
        }

        int n = -1;

        if (value instanceof Number) {
          n = ((Number) value).intValue();
        } else {
          String s = value.toString();

          if ((s.length() > 0) && Character.isDigit(s.charAt(0))) {
            n = SNumber.intValue(s);
          }
        }

        if ((n < -1) || (n >= size())) {
          n = -1;
        }

        setSelectedIndex(n);

        break;

      case ComboBox.CSubmitValue.selected_linked_data :
        if (value != null) {
          setSelectedIndex(indexOfLinkedData(value));
        } else {
          setSelectedIndex(-1);
        }

        break;

      case ComboBox.CSubmitValue.selected_value_text :
        if (value != null) {
          setSelectedIndex(indexOfValue(value));
        } else {
          setSelectedIndex(-1);
        }

        break;

      default :
        setValue(value);

        break;
    }
  }

  /**
   * Sets whether to hide the popup (if it is visible) when the widget is reset.
   * The default is true
   *
   * @param hide
   *          true to hide; false otherwise
   */
  public void setHideWhenReset(boolean hide) {
    hideWhenReset = hide;
  }

  /**
   * Sets the border for the popup
   *
   * @param b
   *          the border
   */
  public void setPopupBorder(iPlatformBorder b) {
    getComboBox().setPopupBorder(b);
  }

  public void setPopupFraction(float fraction) {
    popupFraction = fraction;
  }

  public void setPopupHorizontalAlignment(HorizontalAlign popupHorizontalAlignment) {
    this.popupHorizontalAlignment = popupHorizontalAlignment;
  }

  /**
   * Sets the visibility of the popup
   *
   * @param visible
   *          true to make the popup visible; false otherwise
   */
  public void setPopupVisible(boolean visible) {
    getComboBox().setPopupVisible(visible);
  }

  @Override
  public void setShowDivider(boolean show) {
    listComponent.setShowDivider(show);
  }

  @Override
  public void setSingleClickAction(boolean singleClick) {
    listComponent.setSingleClickAction(singleClick);
  }

  /**
   * Sets whether a button representing a dialog window is used for the popup
   * button
   *
   * @param dialog
   *          true to use; false for the standard
   */
  public void setUseDialogButton(boolean dialog) {
    getComboBox().setUseDialogButton(dialog);
  }

  @Override
  public void setValue(Object value) {
    setValueEx(value);
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return listComponent.getAlternatingRowColor();
  }

  @Override
  public int getFirstVisibleIndex() {
    return listComponent.getFirstVisibleIndex();
  }

  @Override
  public Object getHTTPFormValue() {
    if (!hasSelection()) {
      return null;
    }

    switch(submitValueType) {
      case ComboBox.CSubmitValue.selected_index :
        return getSelectedIndex();

      case ComboBox.CSubmitValue.selected_linked_data :
        return this.getSelectionData();

      case ComboBox.CSubmitValue.selected_value_text :
        return this.getSelectionAsString();

      default :
        return getSelection();
    }
  }

  @Override
  public int getLastVisibleIndex() {
    return listComponent.getLastVisibleIndex();
  }

  /**
   * Gets the button that is used to activate the popup
   *
   * @return the button that is used to activate the popup
   */
  public iActionComponent getPopupButton() {
    return getComboBox().getPopupButton();
  }

  public HorizontalAlign getPopupHorizontalAlignment() {
    return popupHorizontalAlignment;
  }

  /**
   * Gets the popup widget for a popup widget combobox
   *
   * @return the popup widget;
   */
  public iWidget getPopupWidget() {
    return popupWidget;
  }

  /**
   * Gets the bounds the the popup will become visible at.
   * This is meant to be called when executing code during an expansion event.
   * the x an y values are relative to the combox itself
   *
   * @param rect the rectangle to hold the bounds
   */
  public void getWillBecomeVisibleBounds(UIRectangle rect) {
    getComboBox().getWillBecomeVisibleBounds(rect);
  }

  public void getProposedPopupBounds(UIDimension contentSize, UIRectangle r) {
    Utils.getProposedPopupBounds(r, formComponent, contentSize, popupFraction, popupHorizontalAlignment,
                                 getComboBox().getPopupBorder(), true);

    if (popupHeight != null) {
      r.height = ScreenUtils.toPlatformPixelHeight(popupHeight, formComponent, ScreenUtils.getHeight());
    }

    if (popupX != null) {
      r.x = ScreenUtils.toPlatformPixelWidth(popupX, formComponent, UIScreen.snapToSize(r.width));
    }

    if (popupY != null) {
      r.y = ScreenUtils.toPlatformPixelHeight(popupY, formComponent, UIScreen.snapToSize(r.height));
    }
  }

  @Override
  public int getRowHeight() {
    if (listComponent != null) {
      return listComponent.getRowHeight();
    }

    if (popupWidget instanceof iListHandler) {
      return ((iListHandler) popupWidget).getRowHeight();
    }

    return 0;
  }

  @Override
  public RenderableDataItem getSelectedItem() {
    if (listComponent != null) {
      return listComponent.getSelectedItem();
    }

    if (popupWidget instanceof iListHandler) {
      return (RenderableDataItem) popupWidget.getSelection();
    }

    return super.getSelectedItem();
  }

  @Override
  public Object getSelection() {
    if (listComponent != null) {
      return listComponent.getSelectedItem();
    }

    if (popupWidget instanceof iListHandler) {
      return popupWidget.getSelection();
    }

    return super.getSelectedItem();
  }

  /**
   * Gets the length of the widget's text
   *
   * @return the length of the widget's text
   */
  public int getTextLength() {
    return getComboBox().getEditorValue().length();
  }

  @Override
  public String getValueAsString() {
    return getComboBox().getEditorValue();
  }

  public boolean hasPopupWidget() {
    return popupWidget != null;
  }

  /**
   * Gets the visibility of the drop-down button
   *
   * @return true if the button is visible; false otherwise
   */
  public boolean isButtonVisible() {
    return getComboBox().isButtonVisible();
  }

  @Override
  public boolean isDataEventsEnabled() {
    if (listModel != null) {
      listModel.isEventsEnabled();
    }

    return false;
  }

  @Override
  public boolean isEditable() {
    return getComboBox().isEditable();
  }

  /**
   * Gets whether the popup is hidden (if it is visible) when the widget is
   * reset. The default is true
   *
   * @return true if it will be hidden; false otherwise
   */
  public boolean isHideWhenReset() {
    return hideWhenReset;
  }

  /**
   * Gets the visibility of the popup
   *
   * @return true if the popup is visible; false otherwise
   */
  public boolean isPopupVisible() {
    return getComboBox().isPopupVisible();
  }

  @Override
  protected void initializeListeners(aWidgetListener listener) {
    super.initializeListeners(listener);

    if (listener != null) {
      getComboBox().addPopupMenuListener(listener);
    }
  }

  /**
   * Configures the combo box
   *
   * @param cfg
   *          the combo box's configuration
   */
  protected void configureEx(ComboBox cfg) {
    aComboBoxComponent cb = createModelAndComponents(cfg);

    this.setSubItems(listModel);
    cb.setPopupContent(listComponent = createListHandler(cb, cfg));
    configure(cfg, true, true, true, true);

    iSpeechEnabler sp = getAppContext().getSpeechEnabler();

    if ((sp != null) && cfg.speechInputSupported.booleanValue()) {
      formComponent = (iPlatformComponent) sp.configure(this, dataComponent, cfg);
    }

    PaintBucket pb = ColorUtils.configure(this, cfg.getSelectionPainter(), null);

    if (pb != null) {
      listComponent.getItemRenderer().setSelectionPaint(pb);
    }

    listComponent.setDeselectEventsDisabled(!cfg.deselectEventsEnabled.booleanValue());
    listComponent.setMinimumVisibleRowCount(cfg.minVisibleRowCount.intValue());

    if (cfg.maxVisibleRowCount.spot_valueWasSet()) {
      cb.setMaximumVisibleRowCount(cfg.maxVisibleRowCount.intValue());
    }

    if (cfg.visibleRowCount.spot_valueWasSet()) {
      listComponent.setVisibleRowCount(cfg.visibleRowCount.intValue());
    }

    if (cfg.getItemDescription() != null) {
      itemDescription = createColumn(cfg.getItemDescription());
    }

    if (!cfg.editable.booleanValue()) {
      setEditable(false);
    }
    
    listComponent.getListComponent().setWidget(this);
    listModel.setWidget(this);
    listModel.setColumnDescription(itemDescription);
    listModel.setUseIndexForFiltering(cfg.indexForFiltering.booleanValue());

    if (itemDescription != null) {
      if (itemDescription.getFont() != null) {
        listComponent.getListComponent().setFont(itemDescription.getFont());    // set
        // so
        // row
        // height
        // can
        // be
        // calculated
      }
    }

    if (cfg.rowHeight.spot_valueWasSet()) {
      String s = cfg.rowHeight.getValue();

      if (s == null) {
        s = "1ln";
      }

      setRowHeight(ScreenUtils.toPlatformPixelHeight(s, listComponent.getListComponent(), 1000));
    }

    pb = UIColorHelper.getPaintBucket(this, cfg.getPopupPainter());

    if (pb != null) {
      cb.setPopupPainter(pb.getComponentPainter(true));
    }

    PainterHolder ph = PainterUtils.createPaintHolderFromAttributes(this, cfg.showPopupButton.spot_getAttributes());

    if ("true".equals(cfg.showPopupButton.spot_getAttribute("scaleIcons"))) {
      cb.setScaleButtonIcons(true);
    }

    if (ph != null) {
      cb.setButtonPainterHolder(ph);
    }

    if (cfg.selectedIndex.spot_valueWasSet()) {
      initiallySelectedIndex = cfg.selectedIndex.intValue();
    }

    if (cfg.getEmptyText() != null) {
      EmptyText et = cfg.getEmptyText();
      String    s  = et.value.getValue();

      if (s != null) {
        cb.setEmptyFieldText(expandString(s));
      }

      s = et.fgColor.getValue();

      if (s != null) {
        cb.setEmptyFieldColor(ColorUtils.getColor(s));
      }

      if (et.getFont() != null) {
        cb.setEmptyFieldFont(UIFontHelper.getFont(this, et.getFont()));
      }
    }

    if (cfg.icon.spot_hasValue()) {
      setIcon(getIcon(cfg.icon));
    }

    originalValue   = cfg.value.getValue();
    submitValueType = cfg.submitValue.intValue();
    cb.configurationCompleted(this, cfg);
    handleInitialStuff();
  }

  /**
   * Configures the listeners for the pipup widget's ok and cancel widgets
   *
   * @param ok
   *          the ok widget
   * @param cancel
   *          the cancel widget
   */
  protected void configurePopupActionWidgets(iWidget ok, iWidget cancel) {
    if (ok instanceof iActionable) {
      ((iActionable) ok).addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (valueWidget != null) {
            if (valueAttribute != null) {
              setValueEx(valueWidget.getAttribute(valueAttribute));
            } else {
              setValueEx(valueWidget.getValue());
            }
          } else {
            if (valueAttribute != null) {
              setValueEx(popupWidget.getAttribute(valueAttribute));
            } else {
              setValueEx(popupWidget.getValue());
            }
          }

          setPopupVisible(false);
        }
      });
    }

    if (cancel instanceof iActionable) {
      ((iActionable) cancel).addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          cancelPopup();
        }
      });
    }
  }

  protected iPlatformListHandler createListHandler(aComboBoxComponent cb, ComboBox cfg) {
    if (cfg.componentType.getValue() == ComboBox.CComponentType.widget) {
      Widget popupConfiguration = (Widget) cfg.popupWidget.getValue();

      if (popupConfiguration == null) {
        popupConfiguration = (Widget) cfg.popupWidget.spot_getLinkedData();
        cfg.popupWidget.spot_setLinkedData(null);
      }

      if (popupConfiguration == null) {
        popupConfiguration = new Label();
      }

      createPopupWidget(popupConfiguration, cfg.popupWidget.spot_getAttributes());
      listModel.add(new RenderableDataItem(""));

      if (cfg.focusable.spot_valueWasSet()) {
        cb.setPopupContentFocusable(cfg.focusable.booleanValue());
      }

      return new WidgetComboBoxComponent(popupWidget.getContainerComponent());
    } else {
      return cb.createDefaultListHandler(this, listModel);
    }
  }

  protected abstract aComboBoxComponent createModelAndComponents(ComboBox cfg);

  protected void createPopupWidget(Widget cfg, Map<String, String> attributes) {
    popupWidget = FormViewer.createWidget(getContainerViewer(), cfg);

    if (cfg.bounds.width.spot_valueWasSet()) {
      popupFraction = cfg.bounds.getWidth() / 100f;
    }

    if (cfg.bounds.height.spot_valueWasSet()) {
      popupHeight = cfg.bounds.height.getValue();
    }

    if (cfg.bounds.x.spot_valueWasSet()) {
      popupX = cfg.bounds.x.getValue();
    }

    if (cfg.bounds.y.spot_valueWasSet()) {
      popupY = cfg.bounds.y.getValue();
    }

    iWidget aw = popupWidget;
    iWidget cw = null;

    if (attributes != null) {
      String s = attributes.get("horizontalAlignment");

      if (s != null) {
        try {
          popupHorizontalAlignment = HorizontalAlign.valueOf(s.toUpperCase(Locale.US));
        } catch(Exception e) {
          Platform.ignoreException(null, e);
        }
      }

      s = attributes.get("okWidget");

      if (s == null) {
        s = attributes.get("okwidget");
      }

      if (s != null) {
        aw = popupWidget.getContainerViewer().getWidgetFromPath(s);
      }

      if (aw == null) {
        throw DataParser.invalidConfigurationException(
            getAppContext().getResourceAsString("Rare.runtime.text.unknowCloseWidget"), s);
      }

      s = attributes.get("cancelWidget");

      if (s == null) {
        s = attributes.get("cancelwidget");
      }

      if (s != null) {
        cw = popupWidget.getContainerViewer().getWidgetFromPath(s);

        if (cw == null) {
          throw DataParser.invalidConfigurationException(
              getAppContext().getResourceAsString("Rare.runtime.text.unknowCloseWidget"), s);
        }
      }

      s = attributes.get("valueWidget");

      if (s == null) {
        s = attributes.get("valuewidget");
      }

      if (s != null) {
        valueWidget = popupWidget.getContainerViewer().getWidgetFromPath(s);

        if (valueWidget == null) {
          throw DataParser.invalidConfigurationException(
              getAppContext().getResourceAsString("Rare.runtime.text.unknowValueWidget"), s);
        }
      }

      valueAttribute = attributes.get("valueAttribute");

      if (valueAttribute == null) {
        valueAttribute = attributes.get("valueattribute");
      }
    }

    if ((cw != null) || (aw != null)) {
      configurePopupActionWidgets(aw, cw);
    }
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    super.setIcon(icon);
    getComboBox().setEditorIcon(icon);
  }

  @Override
  protected void handleInitialStuff() {
    if ((initiallySelectedIndex == -1) && (originalValue != null)) {
      initiallySelectedIndex = indexOfValueEquals(expandString(originalValue, false));
    }

    if ((originalValue != null) && (initiallySelectedIndex == -1)) {
      setValue(expandString(originalValue));
    }

    super.handleInitialStuff();
  }

  /**
   * Sets the value for the combo box's editor
   *
   * @param value
   *          the value
   */
  protected void setEditorValue(Object value) {
    getComboBox().setEditorValue((value == null)
                                 ? ""
                                 : value.toString());
  }

  protected void setValueEx(Object value) {
    if (value == null) {
      clearSelection();
    } else if (value instanceof RenderableDataItem) {
      setSelectedItem((RenderableDataItem) value);
    } else {
      int index = indexOfValue(value);

      if (index != -1) {
        setSelectedIndex(index);
      } else {
        setEditorValue(value);

        if (valueWidget != null) {
          valueWidget.setValue(value);
        }
      }
    }
  }

  protected iComboBox getComboBox() {
    return (iComboBox) getDataComponent();
  }

  static class WidgetComboBoxComponent extends aNonListListHandler {
    @Weak
    iPlatformComponent component;

    public WidgetComboBoxComponent(iPlatformComponent component) {
      super();
      this.component = component;
    }

    @Override
    public iPlatformItemRenderer getItemRenderer() {
      return null;
    }

    @Override
    public iPlatformComponent getListComponent() {
      return component;
    }

    @Override
    public UIDimension getPreferredSize() {
      return component.getPreferredSize();
    }

    @Override
    public float getPreferredWidth() {
      return component.getPreferredSize().width;
    }

    @Override
    public int getRowHeight() {
      return FontUtils.getDefaultLineHeight();
    }
  }
}
