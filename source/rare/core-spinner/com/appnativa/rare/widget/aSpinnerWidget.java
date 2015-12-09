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
import com.appnativa.rare.spot.DateSpinner;
import com.appnativa.rare.spot.DateTimeSpinner;
import com.appnativa.rare.spot.NumberSpinner;
import com.appnativa.rare.spot.Spinner;
import com.appnativa.rare.spot.TimeSpinner;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.aSpinnerComponent;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iChangeable;
import com.appnativa.rare.ui.spinner.SpinnerDateModel;
import com.appnativa.rare.ui.spinner.SpinnerListModel;
import com.appnativa.rare.ui.spinner.SpinnerNumberModel;
import com.appnativa.rare.ui.spinner.iSpinner;
import com.appnativa.rare.ui.spinner.iSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.SimpleDateFormatEx;

import java.text.DateFormat;
import java.text.DecimalFormat;

import java.util.Date;

public abstract class aSpinnerWidget extends aPlatformWidget implements iChangeable {
  protected boolean changeEventsEnabled;

  /** the spinner's initial value */
  protected Object   initialValue;
  protected iSpinner spinner;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aSpinnerWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.Spinner;
    selectAllAllowed=true;
  }

  /**
   * Adds a change listener to listen for date value changes
   *
   * @param l the listener to add
   */
  @Override
  public void addChangeListener(iChangeListener l) {
    spinner.addChangeListener(l);
  }

  @Override
  public void clearContents() {
    super.clearContents();

    try {
      setValue(initialValue);
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }
  }

  /**
   * Commits the text value in an editable spinner
   */
  public void commitEdit() {
    spinner.commitEdit();
  }

  /**
   * Configures the spinner
   *
   * @param cfg the spinner's configuration
   */
  public void configure(Spinner cfg) {
    selectAllAllowed = true;
    deletingAllowed  = true;
    pastingAllowed   = true;
    copyingAllowed   = true;

    Date   min    = null;
    Date   max    = null;
    Date   value  = null;
    String format = null;

    spinner = createSpinnerAndComponents(cfg);
    configure(cfg, true, true, true, true);

    if (cfg.useDesktopStyle.booleanValue()) {
      spinner.setUseDesktopStyleEditor(true);
    }

    if (cfg instanceof NumberSpinner) {
      setupNumericSpinner(spinner, (NumberSpinner) cfg);
    } else if (cfg instanceof DateSpinner) {
      DateSpinner scfg = (DateSpinner) cfg;

      if (scfg.maxValue.spot_hasValue()) {
        max = scfg.maxValue.getCalendar().getTime();
      }

      if (scfg.minValue.spot_hasValue()) {
        min = scfg.minValue.getCalendar().getTime();
      }

      if (scfg.value.spot_hasValue()) {
        value = scfg.value.getCalendar().getTime();
      }

      format = scfg.format.getValue();
      setupDateSpinner(spinner, min, max, value, format, getAppContext().getDefaultDateContext().getDisplayFormat(),
                       cfg);
    } else if (cfg instanceof DateTimeSpinner) {
      DateTimeSpinner scfg = (DateTimeSpinner) cfg;

      if (scfg.maxValue.spot_hasValue()) {
        max = scfg.maxValue.getCalendar().getTime();
      }

      if (scfg.minValue.spot_hasValue()) {
        min = scfg.minValue.getCalendar().getTime();
      }

      if (scfg.value.spot_hasValue()) {
        value = scfg.value.getCalendar().getTime();
      }

      format = scfg.format.getValue();
      setupDateSpinner(spinner, min, max, value, format,
                       getAppContext().getDefaultDateTimeContext().getDisplayFormat(), cfg);
    } else if (cfg instanceof TimeSpinner) {
      TimeSpinner scfg = (TimeSpinner) cfg;

      if (scfg.maxValue.spot_hasValue()) {
        max = scfg.maxValue.getCalendar().getTime();
      }

      if (scfg.minValue.spot_hasValue()) {
        min = scfg.minValue.getCalendar().getTime();
      }

      if (scfg.value.spot_hasValue()) {
        value = scfg.value.getCalendar().getTime();
      }

      format = scfg.format.getValue();
      setupDateSpinner(spinner, min, max, value, format, getAppContext().getDefaultTimeContext().getDisplayFormat(),
                       cfg);
    } else {
      handleDataURL(cfg, false);

      if (size() == 0) {
        add(new RenderableDataItem(""));
      }

      SpinnerListModel slm = new SpinnerListModel(this.subItemsList(1), cfg.isCircular.booleanValue());

      spinner.setModel(slm);
    }

    registerEditorWithWidget(spinner.getEditor());

    if (initialValue != null) {
      // make sure script gets the event
      aWidgetListener l = this.getWidgetListener();

      if ((l != null) && l.isChangeEventEnabled()) {
        l.stateChanged(new ChangeEvent(spinner.getView()));
      }
    }

    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void configure(Widget cfg) {
    configure((Spinner) cfg);
  }

  /**
   * Gets the spinner model
   *
   * @return the spinner model
   */
  public iSpinnerModel getModel() {
    return spinner.getModel();
  }

  /**
   * Return the object in the sequence that comes after the object returned by getValue().
   *
   * @return the next value
   */
  public Object getNextValue() {
    return spinner.getNextValue();
  }

  /**
   * Return the object in the sequence that comes before the object returned by getValue().
   *
   * @return the previous value
   */
  public Object getPreviousValue() {
    return spinner.getPreviousValue();
  }

  /**
   * Returns the last selected index or -1 if the selection is empty
   * or if the spinner is not a list-type spinner
   *
   * @return the last selected index or -1 if the selection is empty
   */
  public int getSelectedIndex() {
    try {
      SpinnerListModel m = (SpinnerListModel) getModel();
      Object           o = m.getValue();

      return (o == null)
             ? -1
             : m.getList().indexOf(o);
    } catch(ClassCastException e) {
      return -1;
    }
  }

  @Override
  public Object getSelection() {
    return spinner.getValue();
  }

  @Override
  public int getValueAsInt() {
    iSpinnerModel m = spinner.getModel();

    if (m instanceof SpinnerNumberModel) {
      return ((SpinnerNumberModel) m).getNumber().intValue();
    }

    return super.getValueAsInt();
  }

  /**
   * Returns whether the spinner button are side by side
   * or above below.
   *
   * @return true for side by side; false for above/below
   *
   * @see #setButtonsSideBySide(boolean)
   */
  public boolean isButtonsSideBySide() {
    return spinner.isButtonsSideBySide();
  }

  /**
   * Gets the visibility of the spinner buttons
   *
   * @return visible true for visible; false otherwise
   */
  public boolean isButtonsVisible() {
    return spinner.isButtonsVisible();
  }

  /**
   * Gets whether selection changes will cause events to be fired
   *
   * @return true for enabled; false for disabled
   */
  public boolean isChangeEventsEnabled() {
    return changeEventsEnabled;
  }

  /**
   * Gets the visibility of the spinner editor
   *
   * @return true if the editor portion of the spinner is visible; false otherwise
   */
  public boolean isEditorVisible() {
    return spinner.getEditor().getComponent().isVisible();
  }

  /**
   * Makes the spinner buttons toolbar style.
   * That is, they have transparent backgrounds untill pressed
   */
  public void makeButtonsToolbarStyle() {
    if (spinner instanceof aSpinnerComponent) {
      ((aSpinnerComponent) spinner).setButtonPainterHolder(PainterUtils.createToolBarButtonPaintHolder());
    }
  }

  /**
   * Moves the spinner to the next value
   *
   * @return true if there was a next value; false otherwise
   */
  public boolean nextValue() {
    Object v = getModel().getNextValue();

    if (v != null) {
      getModel().setValue(v);
    }

    return v != null;
  }

  /**
   * Moves the spinner to the previous value
   *
   * @return true if there was a next value; false otherwise
   */
  public boolean previousValue() {
    Object v = getModel().getPreviousValue();

    if (v != null) {
      getModel().setValue(v);
    }

    return v != null;
  }

  /**
   * Removes a previously added change listener
   *
   * @param l the listener to remove
   */
  @Override
  public void removeChangeListener(iChangeListener l) {
    spinner.removeChangeListener(l);
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    return spinner.removeSelectedData(returnData);
  }

  @Override
  public void reset() {
    if (initialValue == null) {
      clearContents();
    } else {
      setValue(initialValue);
    }
  }

  /**
   * By default desktop style spinners have their button oriented
   * based on the platform. This method forces them one way or the other
   *
   * This is only meaningful for desktop style spinners
   * @param sideBySide true for side by side; false for above/below
   */
  public void setButtonsSideBySide(boolean sideBySide) {
    spinner.setButtonsSideBySide(sideBySide);
  }

  /**
   * Sets the visibility of the spinner buttons
   *
   * @param visible true for visible; false otherwise
   */
  public void setButtonsVisible(boolean visible) {
    spinner.setButtonsVisible(visible);
  }

  /**
   * Sets whether selection changes will cause events to be fired
   *
   * @param enabled true to enable; false to disable
   */
  public void setChangeEventsEnabled(boolean enabled) {
    this.changeEventsEnabled = enabled;

    aWidgetListener l = this.getWidgetListener();

    if (l != null) {
      spinner.removeChangeListener(l);

      if (enabled) {
        spinner.addChangeListener(l);
      }
    }
  }

  /**
   * Sets whether the buttons continuously perform
   * their actions when held down automatically.
   *
   * This is only meaningful for desktop style spinners
   *
   * @param continuous true for continuous; false otherwise
   */
  public void setContinuousAction(boolean continuous) {
    spinner.setContinuousAction(continuous);
  }

  /**
   * Sets the editing component for the spinner
   *
   * @param editor the editor
   */
  public void setEditor(iSpinnerEditor editor) {
    unregisterEditorWithWidget(spinner.getEditor());
    spinner.setEditor(editor);
    registerEditorWithWidget(editor);
  }

  /**
   * Sets the visibility of the spinner editor
   * This is only meaningful for desktop style spinners
   * @param visible true to hide the editor portion of the spinner; false otherwise
   */
  public void setEditorVisible(boolean visible) {
    spinner.getEditor().getComponent().setVisible(visible);
  }

  public void setIncrement(Number increment) {
    iSpinnerModel sm = spinner.getModel();

    if (sm instanceof SpinnerNumberModel) {
      ((SpinnerNumberModel) sm).setStepSize(increment);
    }
  }
   @Override
  public void selectAll() {
    spinner.getEditor().selectAll();
  }
  /**
   * Sets the maximum value for a number spinner
   *
   * @param maximum the maximum value
   */
  public void setMaximum(Date maximum) {
    iSpinnerModel m = getModel();

    if (m instanceof SpinnerDateModel) {
      ((SpinnerDateModel) m).setMaximum(maximum);
    }
  }

  /**
   * Sets the maximum value for a number spinner
   *
   * @param maximum the maximum value
   */
  public void setMaximum(double maximum) {
    iSpinnerModel m = getModel();

    if (m instanceof SpinnerNumberModel) {
      ((SpinnerNumberModel) m).setMaximum(maximum);
    }
  }

  /**
   * Sets the minimum value for a number spinner
   *
   * @param minimum the minimum value
   */
  public void setMinimum(Date minimum) {
    iSpinnerModel m = getModel();

    if (m instanceof SpinnerDateModel) {
      ((SpinnerDateModel) m).setMinimum(minimum);
    }
  }

  /**
   * Sets the minimum value for a number spinner
   *
   * @param minimum the minimum value
   */
  public void setMinimum(double minimum) {
    iSpinnerModel m = getModel();

    if (m instanceof SpinnerNumberModel) {
      ((SpinnerNumberModel) m).setMinimum(minimum);
    }
  }

  /**
   * Sets the spinner model
   *
   * @param model the model for the spinner
   */
  public void setModel(iSpinnerModel model) {
    iSpinnerEditor e = spinner.getEditor();

    spinner.setModel(model);

    iSpinnerEditor ne = spinner.getEditor();

    if (e != ne) {
      unregisterEditorWithWidget(e);
    }

    registerEditorWithWidget(ne);
  }

  /**
   * Selects a single row. Does nothing if the given index is greater than or equal to the list size.
   *
   * @param index the index of the item to select
   *
   * @throws UnsupportedOperationException if the spinner is not a list-type spinner
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public void setSelectedIndex(int index) {
    try {
      SpinnerListModel m = (SpinnerListModel) getModel();

      m.setValue(m.getList().get(index));
    } catch(ClassCastException e) {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void setValue(Object value) {
    if (size() > 0) {
      if (value instanceof RenderableDataItem) {
        spinner.setValue(value);
      } else {
        int i = this.indexOfValueEquals(value);

        if (i == -1) {
          spinner.setValue(new RenderableDataItem(value));
        } else {
          spinner.setValue(get(i));
        }
      }
    } else {
      spinner.setValue(value);
    }
  }

  public void setVisibleCharacters(int count) {
    spinner.setVisibleCharacters(count);
  }

  /**
   * Swaps the spinners buttons such that the up icon
   * is on the down button and vice-versa
   */
  public void swapButtonIcons() {
    spinner.swapButtonIcons();
  }

  /**
   * Creates the spinner and the data and form components and
   *
   * @param cfg the configuration
   * @return the spinner interface
   */
  protected abstract iSpinner createSpinnerAndComponents(Spinner cfg);

  @Override
  protected void initializeListeners(aWidgetListener listener) {
    super.initializeListeners(listener);

    if ((listener != null) && listener.isChangeEventEnabled()) {
      spinner.addChangeListener(listener);
    }
  }

  protected abstract void registerEditorWithWidget(iSpinnerEditor editor);

  /**
   * Sets up a spinner for dates
   *
   * @param spinner the spinner
   * @param min the minimum value
   * @param max the maximum value
   * @param value the value
   * @param format  the format
   * @param defaultdf the default date format
   * @param cfg the configuration
   */
  protected void setupDateSpinner(iSpinner spinner, Date min, Date max, Date value, String format,
                                  DateFormat defaultdf, Spinner cfg) {
    DateFormat df = null;

    if (format != null) {
      try {
        df = new SimpleDateFormatEx(format);
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    if (df == null) {
      df = defaultdf;
    }

    SpinnerDateModel m = new SpinnerDateModel(df, cfg.isCircular.booleanValue());

    m.setEditable(cfg.editable.booleanValue());

    if (min != null) {
      m.setMinimum(min);
    }

    if (max != null) {
      m.setMaximum(max);
    }

    if (value != null) {
      m.setValue(value);
      initialValue = spinner.getValue();
    } else {
      if (min != null) {
        m.setValue(min);
      }
    }

    if (cfg instanceof TimeSpinner) {
      m.setShowTime(true);
      m.setShowTimeOnly(true);
    } else if (cfg instanceof DateTimeSpinner) {
      m.setShowTime(true);
    }

    spinner.setModel(m);
  }

  /**
   * Sets up a spinner for numbers
   *
   * @param spinner the spinner
   * @param cfg the configuration
   */
  protected void setupNumericSpinner(iSpinner spinner, NumberSpinner cfg) {
    SpinnerNumberModel m = new SpinnerNumberModel(cfg.isCircular.booleanValue());

    m.setSupportDecimalValues(cfg.supportDecimalValues.booleanValue());
    m.setEditable(cfg.editable.getValue());

    String format = cfg.format.getValue();

    if (format != null) {
      m.setFormat(new DecimalFormat(format));
    }

    if (cfg.maxValue.spot_hasValue()) {
      m.setMaximum(cfg.maxValue.numberValue().makeImmutable());
    }

    if (cfg.minValue.spot_hasValue()) {
      m.setMinimum(cfg.minValue.numberValue().makeImmutable());
    }

    if (cfg.incrementValue.spot_hasValue()) {
      m.setStepSize(cfg.incrementValue.numberValue().makeImmutable());
    }

    if (cfg.value.spot_hasValue()) {
      m.setValue(cfg.value.numberValue());
      initialValue = m.getValue();
    } else {
      if (cfg.minValue.spot_hasValue()) {
        m.setValue(m.getMinimum());
      }
    }

    spinner.setModel(m);
  }

  @Override
  protected void uninitializeListeners(aWidgetListener listener) {
    super.uninitializeListeners(listener);

    if (listener != null) {
      spinner.removeChangeListener(listener);
    }
  }

  protected abstract void unregisterEditorWithWidget(iSpinnerEditor editor);
}
