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

package com.appnativa.rare.ui.calendar;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.DateConverter;
import com.appnativa.rare.converters.DateTimeConverter;
import com.appnativa.rare.converters.TimeConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.ui.AlertPanel;
import com.appnativa.rare.ui.LinearPanel;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;

import java.util.Calendar;
import java.util.Date;

public abstract class aDateViewManager implements iDateViewManager, iActionListener {
  protected Calendar           date              = Calendar.getInstance();
  protected Calendar           endDate           = Calendar.getInstance();
  protected EventListenerList  listenerList      = new EventListenerList();
  protected boolean            useAmPmTimeFormat = true;
  protected iPlatformComponent buttonPanel;
  protected ChangeEvent        changeEvent;
  protected Object             converterContext;
  protected iDataConverter     dateConverter;
  protected iWindow            dialog;
  protected String             dialogTitle;
  protected boolean            ignoreChangeEvent;
  protected Calendar           maxDate;
  protected Calendar           minDate;
  protected iPlatformComponent noneButton;
  protected iPlatformComponent okButton;
  protected boolean            showCalendar;
  protected boolean            showNoneButton;
  protected boolean            showOkButton;
  protected boolean            showTime;
  protected boolean            showTimeOnly;
  protected boolean            showTodayButton;
  protected boolean            showWeekNumbers;
  protected CharSequence       stringValue;
  protected iPlatformComponent todayButton;
  protected Calendar           validationDate;
  protected boolean            valueSet;

  public aDateViewManager() {
    super();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getComponent() == todayButton) {
      date = Calendar.getInstance();
      fireEvent();
    } else if (e.getComponent() == noneButton) {
      valueSet = false;
      fireEvent();

      if (dialog != null) {
        dialog.close();
      }
    } else if (e.getComponent() == okButton) {
      if (dialog != null) {
        dialog.close();
      } else {
        fireEvent();
      }
    }
  }

  @Override
  public void addActionListener(iActionListener l) {
    listenerList.add(iActionListener.class, l);
  }

  /**
   * Adds a change listener to listen for date value changes
   *
   * @param l
   *          the listener to add
   */
  @Override
  public void addChangeListener(iChangeListener l) {
    listenerList.add(iChangeListener.class, l);
  }

  @Override
  public void dispose() {
    if (buttonPanel != null) {
      buttonPanel.dispose();
    }

    if (listenerList != null) {
      listenerList.clear();
    }

    buttonPanel      = null;
    listenerList     = null;
    dialog           = null;
    date             = null;
    endDate          = null;
    changeEvent      = null;
    converterContext = null;
    dateConverter    = null;
    maxDate          = null;
    minDate          = null;
    validationDate   = null;
    buttonPanel      = null;
    noneButton       = null;
    okButton         = null;
    todayButton      = null;
  }

  @Override
  public void finishConfiguring(iWidget context) {}

  @Override
  public void removeActionListener(iActionListener l) {
    listenerList.remove(iActionListener.class, l);
  }

  /**
   * Removes a previously added change listener
   *
   * @param l
   *          the listener to remove
   */
  @Override
  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  public void showDialog(iPlatformComponent owner) {
    if (dialog != null) {
      if (dialog.getComponent().isShowing()) {
        return;
      }

      dialog.disposeOfWindow();
    }

    String title = getDialogTitle();

    if (title == null) {
      if (showTime) {
        if (showTimeOnly) {
          title = Platform.getResourceAsString("Rare.runtime.text.dateChooser.chooseTime");
        } else {
          title = Platform.getResourceAsString("Rare.runtime.text.dateChooser.chooseDateTime");
        }
      } else {
        title = Platform.getResourceAsString("Rare.runtime.text.dateChooser.chooseDate");
      }
    }

    iPlatformWindowManager wm = Platform.getAppContext().getWindowManager();
    iWindow                w  = wm.createDialog(owner.getWidget(), null, title, true);
    LinearPanel            p  = new LinearPanel(owner.getWidget(), false);

    p.addComponent(getDatePickerComponent(), "f:d:g");

    String spec = null;

    if (Platform.isTouchDevice()) {
      spec = "FILL:[25dlu,d]:NONE";
    } else {
      spec = "FILL:[15dlu,d]:NONE";
    }

    p.addComponent(getButtonPanel(owner.getWidget()), spec);

    WidgetPaneViewer wp = new WidgetPaneViewer(p);

    wm.setViewer(w.getTargetName(), owner.getWidget(), wp, null);
    dialog = w;
    w.showWindow();
  }

  @Override
  public void setConverter(iDataConverter converter) {
    dateConverter = converter;
  }

  @Override
  public void setConverterContext(Object context) {
    converterContext = context;
  }

  @Override
  public void setDate(Calendar date) {
    if (date == null) {
      valueSet = false;
      this.date.setTimeInMillis(System.currentTimeMillis());
      setValueEx(this.date);
    } else {
      valueSet = true;
      setValueEx(date);
    }
  }

  @Override
  public void setDate(Date date) {
    if (date == null) {
      valueSet = false;
      this.date.setTimeInMillis(System.currentTimeMillis());
      setValueEx(this.date);
    } else {
      valueSet = true;

      Calendar cal = Calendar.getInstance();

      cal.setTime(date);
      setValueEx(cal);
    }
  }

  public void setDialogTitle(String dialogTitle) {
    this.dialogTitle = dialogTitle;
  }

  public void setEndDate(Calendar date) {
    endDate = date;
  }

  public void setEndDate(Date date) {
    if (date == null) {
      endDate = null;
    } else {
      Calendar cal = Calendar.getInstance();

      cal.setTime(date);
      endDate = cal;
    }
  }

  public void setIgnoreChangeEvent(boolean ignoreChangeEvent) {
    this.ignoreChangeEvent = ignoreChangeEvent;
  }

  @Override
  public void setMaxDate(Calendar cal) {
    maxDate = cal;
  }

  @Override
  public void setMinDate(Calendar cal) {
    minDate = cal;
  }

  @Override
  public void setShowCalendar(boolean showCalendar) {
    this.showCalendar = showCalendar;
  }

  @Override
  public void setShowNoneButton(boolean show) {
    showNoneButton = show;
  }

  @Override
  public void setShowOKButton(boolean show) {
    showOkButton = show;
  }

  public void setShowSpinners(boolean show) {}

  @Override
  public void setShowTime(boolean show) {
    showTime = show;
  }

  @Override
  public void setShowTimeOnly(boolean showTimeOnly) {
    this.showTimeOnly = showTimeOnly;
  }

  @Override
  public void setShowTodayButton(boolean show) {
    showTodayButton = show;
  }

  @Override
  public void setShowWeekNumbers(boolean show) {
    showWeekNumbers = show;
  }

  @Override
  public void setUseAmPmTimeFormat(boolean useAmPmTimeFormat) {
    this.useAmPmTimeFormat = useAmPmTimeFormat;
  }

  public iPlatformComponent getButtonPanel(iWidget context) {
    if (!showNoneButton &&!showOkButton &&!showTodayButton) {
      return null;
    }

    if (buttonPanel == null) {
      LinearPanel     p = createButtonPanel(context);
      iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.DataChooser.buttonPanel.border");

      if (b == null) {
        b = new UIMatteBorder(new UIInsets(1, 0, 0, 0), UILineBorder.getDefaultLineColor());
      }

      p.setBorder(b);
      buttonPanel = p;
    }

    return buttonPanel;
  }

  public iPlatformComponent getDatePickerComponent() {
    return null;
  }

  public String getDialogTitle() {
    return dialogTitle;
  }

  public Date getEndDate() {
    if (endDate == null) {
      return null;
    }

    return valueSet
           ? endDate.getTime()
           : null;
  }

  public Date getStartValue() {
    return valueSet
           ? date.getTime()
           : null;
  }

  @Override
  public Date getValue() {
    return valueSet
           ? date.getTime()
           : null;
  }

  @Override
  public CharSequence getValueAsString() {
    if (stringValue == null) {
      stringValue = getDateConverter().objectToString(Platform.getWindowViewer(), date, converterContext);
    }

    return stringValue;
  }

  public boolean isIgnoreChangeEvent() {
    return ignoreChangeEvent;
  }

  @Override
  public boolean isShowCalendar() {
    return showCalendar;
  }

  public boolean isShowOkButton() {
    return showOkButton;
  }

  public boolean isShowSpinners() {
    return false;
  }

  @Override
  public boolean isShowTimeOnly() {
    return showTimeOnly;
  }

  @Override
  public boolean isUseAmPmTimeFormat() {
    return useAmPmTimeFormat;
  }

  protected LinearPanel createButtonPanel(iWidget context) {
    String cspec, rspec;

    cspec = "FILL:[20dlu,d]:GROW";
    rspec = "FILL:[15dlu,d]:GROW";

    LinearPanel p = new LinearPanel(context, true, rspec, cspec);
//
//    if (!Platform.isTouchDevice()) {
//      p.addComponent(PlatformHelper.createSpacerComponent(p), "FILL:DEFAULT:GROW");
//    }
    int              count = 0;
    PushButtonWidget w     = null;

    if (showNoneButton) {
      w = AlertPanel.createButton(Platform.getResourceAsString("Rare.runtime.text.dateChooser.none"),
                                  "Rare.dateChooser.button", "0,0,0,1", this);
      noneButton = w.getContainerComponent();
      p.addComponent(noneButton);
      count++;
    }

    if (showTodayButton) {
      String s;

      if (showTimeOnly) {
        s = "Rare.runtime.text.dateChooser.now";
      } else {
        s = "Rare.runtime.text.dateChooser.today";
      }

      w           = AlertPanel.createButton(Platform.getResourceAsString(s), "Rare.dateChooser.button", "0,0,0,1",
              this);
      todayButton = w.getContainerComponent();
      p.addComponent(todayButton);
      count++;
    }

    if (showOkButton) {
      w = AlertPanel.createButton(Platform.getResourceAsString("Rare.runtime.text.dateChooser.done"),
                                  "Rare.dateChooser.button", "0,0,0,1", this);
      okButton = w.getContainerComponent();
      p.addComponent(okButton);
      count++;
    }

    if (w != null) {
      w.setBorder(null);    //remove the border on the last button
    }

    int[] a = null;

    if (count == 2) {
      a = new int[] { 1, 2 };
    } else if (count == 3) {
      a = new int[] { 1, 2, 3 };
    }

    if (a != null) {
      p.getFormLayout().setColumnGroups(new int[][] {
        a
      });
    }

    return p;
  }

  protected void fireEvent() {
    if (ignoreChangeEvent) {
      return;
    }

    if (listenerList.getListenerCount(iChangeListener.class) > 0) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  protected void setValueEx(Calendar cal) {
    date        = cal;
    stringValue = null;
  }

  protected iDataConverter getDateConverter() {
    if (dateConverter == null) {
      if (showTime) {
        if (showTimeOnly) {
          dateConverter = new TimeConverter();
        } else {
          dateConverter = new DateTimeConverter();
        }
      } else {
        dateConverter = new DateConverter();
      }
    }

    return dateConverter;
  }
}
