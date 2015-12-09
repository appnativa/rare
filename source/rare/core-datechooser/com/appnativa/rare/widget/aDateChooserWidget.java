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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.converters.DateContext;
import com.appnativa.rare.converters.DateTimeConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.spot.DateChooser;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.aComboBoxComponent;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.calendar.DateButton;
import com.appnativa.rare.ui.calendar.DateComboBox;
import com.appnativa.rare.ui.calendar.DatePanel;
import com.appnativa.rare.ui.calendar.DateViewManager;
import com.appnativa.rare.ui.calendar.iDateViewManager;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.FormatException;
import com.appnativa.util.Helper;
import com.appnativa.util.SimpleDateFormatEx;

public abstract class aDateChooserWidget extends aPlatformWidget implements iActionable {
  protected iDateViewManager dateViewManager;

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aDateChooserWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.DateChooser;
  }

  @Override
  public void addActionListener(iActionListener l) {
    dateViewManager.addActionListener(l);
  }

  /**
   * Adds a <code>PopupMenu</code> listener which will listen to notification
   * messages from the popup portion of the combo box.
   *
   * @param l  the <code>iPopupMenuListener</code> to add
   */
  public void addPopupMenuListener(iPopupMenuListener l) {
    if (dataComponent instanceof DateComboBox) {
      ((DateComboBox) dataComponent).addPopupMenuListener(l);
    }
  }

  @Override
  public void clearContents() {
    super.clearContents();
    setValue((Date) null);
  }

  /**
   * Configures the date chooser
   *
   * @param cfg the date chooser's configuration
   */
  public void configure(DateChooser cfg) {
    iPlatformComponent comp = null;

    switch(cfg.displayType.intValue()) {
      case DateChooser.CDisplayType.single_calendar :
        comp = createSinglePanel(cfg);

        break;

      case DateChooser.CDisplayType.multiple_calendar :
        comp = createMultiPanel(cfg);

        break;

      case DateChooser.CDisplayType.button :
        comp = createButton(cfg);

        break;

      default :
        comp = createComboBox(cfg);

        break;
    }

    dataComponent = formComponent = comp;

    if (!Platform.isTouchDevice()) {
      setFocusPainted(true);
    }

    configure(cfg, true, false, true, true);

    if (comp instanceof aComboBoxComponent) {
      ((aComboBoxComponent) comp).configurationCompleted(this, cfg);
    }

    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void configure(Widget cfg) {
    configure((DateChooser) cfg);
  }

  /**
   * Creates a new date chooser widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the date chooser widget
   */
  public static DateChooserWidget create(iContainer parent, DateChooser cfg) {
    DateChooserWidget dc = new DateChooserWidget(parent);

    dc.configure(cfg);

    return dc;
  }

  @Override
  public void dispose() {
    super.dispose();

    if (dateViewManager != null) {
      dateViewManager.dispose();
      dateViewManager = null;
    }
  }

  @Override
  public void removeActionListener(iActionListener l) {
    dateViewManager.removeActionListener(l);
  }

  /**
   * Removes a <code>iPopupMenuListener</code>.
   *
   * @param l  the <code>iPopupMenuListener</code> to remove
   * @see #addPopupMenuListener
   */
  public void removePopupMenuListener(iPopupMenuListener l) {
    if (dataComponent instanceof DateComboBox) {
      ((DateComboBox) dataComponent).removePopupMenuListener(l);
    }
  }

  /**
   * Sets the selected date
   *
   * @param date the date to select
   */
  public void setDate(Date date) {
    dateViewManager.setDate(date);
  }

  /**
   * Sets the month that is displayed for a calendar panel view
   *
   * @param year the year
   * @param month the month (1-12)
   * @param index the index for the panel when multiple panels ares displayed
   *              (use 0 for the first panel, -1 for the last panel, and -2 for the middle panel)
   *
   * @return true if the new month is displayed successfully; false if the new month is out of the valid date range
   */
  public boolean setDisplayedMonth(int year, int month, int index) {
    return false;
  }

  public void setMaxDate(Calendar date) {
    setMinMaxDate(date, false);
  }

  public void setMaxDate(Date date) {
    if (date == null) {
      setMinDate((Calendar) null);
    } else {
      Calendar cal = Calendar.getInstance();

      cal.setTime(date);
      setMaxDate(cal);
    }
  }

  public void setMinDate(Calendar date) {
    setMinMaxDate(date, true);
  }

  public void setMinDate(Date date) {
    if (date == null) {
      setMinDate((Calendar) null);
    } else {
      Calendar cal = Calendar.getInstance();

      cal.setTime(date);
      setMinDate(cal);
    }
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof RenderableDataItem) {
      value = ((RenderableDataItem) value).getValue();
    }

    if (value instanceof Date) {
      setDate((Date) value);
    } else if (value instanceof Calendar) {
      setDate(((Calendar) value).getTime());
    } else if (value != null) {
      setValueAsString(value.toString());
    }
  }

  /**
   * Sets the selected date
   *
   * @param value the string representation of the date (must be in the default date format)
   */
  public void setValueAsString(String value) {
    if ((value == null) || (value.length() == 0)) {
      return;
    }

    Date date;

    if (!Character.isDigit(value.charAt(0))) {
      date = Helper.createDate(value);
    } else {
      try {
        date = getAppContext().getDefaultDateContext().getItemFormat().parse(value);
      } catch(ParseException ex) {
        throw new FormatException(ex);
      }
    }

    setValue(date);
  }

  /**
   * Get the currently selected date as a Calendar object
   *
   * @return  the currently selected date as a Calendar object
   */
  public Calendar getCalendar() {
    Object o = this.getSelection();

    if ((o instanceof Calendar) || (o == null)) {
      return (Calendar) o;
    }

    if (o instanceof Date[]) {
      o = ((Date[]) o)[0];
    }

    if (o instanceof Date) {
      Calendar c = Calendar.getInstance();

      c.setTime((Date) o);

      return c;
    }

    if (o instanceof Calendar[]) {
      return ((Calendar[]) o)[0];
    }

    return (Calendar) o;
  }

  /**
   * Get the currently selected date
   *
   * @return  the currently selected date
   */
  public Date getDate() {
    Object o = this.getSelection();

    if ((o == null) || (o instanceof Date)) {
      return (Date) o;
    }

    if (o instanceof Date[]) {
      o = ((Date[]) o)[0];
    }

    if (o instanceof Calendar) {
      return ((Calendar) o).getTime();
    }

    if (o instanceof Calendar[]) {
      return ((Calendar[]) o)[0].getTime();
    }

    return (Date) o;
  }

  @Override
  public Object getHTTPFormValue() {
    Object o = getSelection();

    if (o instanceof Date) {
      return getAppContext().getDefaultDateContext().getItemFormat().format((Date) o);
    } else if (o instanceof Date[]) {
      return toStringArray((Date[]) o);
    }

    return null;
  }

  @Override
  public Object getSelection() {
    return dateViewManager.getValue();
  }

  @Override
  public String getSelectionAsString() {
    Object o = this.getSelection();

    if (o instanceof Date) {
      return getAppContext().getDefaultDateContext().getItemFormat().format((Date) o);
    }

    if (o instanceof Date[]) {
      DateFormat    f   = getAppContext().getDefaultDateContext().getItemFormat();
      Date[]        a   = (Date[]) o;
      int           len = a.length;
      StringBuilder sb  = new StringBuilder();

      if (len == 0) {
        return "";
      } else if (len == 1) {
        return f.format(a[0]) + "\r\n";
      } else {
        for (int i = 0; i < len; i++) {
          sb.append(f.format(a[i])).append("\r\n");
        }

        return sb.toString();
      }
    }

    return null;
  }

  /**
   * Returns an array of the values of all of the selected rows, in increasing order.
   *
   * @return an String array of the values of all of the selected rows, or null if there are no selections
   */
  public String[] getSelectionsAsStrings() {
    Object o = this.getSelection();

    if (o instanceof Date) {
      return new String[] { getAppContext().getDefaultDateContext().getItemFormat().format((Date) o) };
    }

    if (o instanceof Date[]) {
      return toStringArray((Date[]) o);
    }

    return null;
  }

  @Override
  public String getValueAsString() {
    return getSelectionAsString();
  }

  protected void configure(iDateViewManager dvm, DateChooser cfg) {
    Calendar cal = cfg.minValue.getCalendar();

    if (cal != null) {
      dvm.setMinDate(cal);
    }

    cal = cfg.maxValue.getCalendar();

    if (cal != null) {
      dvm.setMaxDate(cal);
    }

    cal = cfg.value.getCalendar();

    SimpleDateFormatEx idf = null;
    SimpleDateFormatEx df  = null;
    String             s   = cfg.format.getValue();

    if (s != null) {
      try {
        int n = s.indexOf('|');

        if (n == -1) {
          df = idf = new SimpleDateFormatEx(s);
        } else {
          idf = new SimpleDateFormatEx(s.substring(0, n));
          df  = new SimpleDateFormatEx(s.substring(n + 1));
        }

        DateContext ctx = new DateContext(idf, df);

        dvm.setConverter(new DateTimeConverter());
        dvm.setConverterContext(ctx);
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    if (cal != null) {
      dvm.setDate(cal.getTime());
    }

    dvm.setShowTodayButton(cfg.showTodayButton.booleanValue());
    dvm.setShowOKButton(cfg.showOkButton.booleanValue());
    dvm.setShowNoneButton(cfg.showNoneButton.booleanValue());
    dvm.setShowTime(cfg.showTime.booleanValue());
    dvm.setUseAmPmTimeFormat("true".equals(cfg.showTime.spot_getAttribute("ampmFormat")));
    dvm.setShowTimeOnly("true".equals(cfg.showTime.spot_getAttribute("timeOnlyChooser")));

    if ((df != null) && (cfg.valueContext.getValue() != null)) {
      if (cfg.converterClass.spot_getValue() != null) {
        iDataConverter cvt;

        try {
          cvt = getAppContext().getDataConverter(getAppContext().getDataConverterClass(cfg.converterClass.getValue()));
        } catch(ClassNotFoundException e) {
          throw new ApplicationException(e);
        }

        dvm.setConverter(cvt);

        if (cfg.valueContext.getValue() != null) {
          dvm.setConverterContext(cvt.createContext(this, cfg.valueContext.getValue()));
        }
      } else {
        DateTimeConverter converter = new DateTimeConverter();

        try {
          dvm.setConverter(converter);
          dvm.setConverterContext(new SimpleDateFormatEx(cfg.valueContext.getValue()));
        } catch(Exception e) {
          handleException(e);
        }
      }
    }

    dvm.finishConfiguring(this);
  }

  protected void configurePopupButton(iActionComponent ac, DateChooser cfg) {
    PainterHolder p = PainterHolder.create(this, cfg.showPopupButton);

    if (p != null) {
      if (p.isBackgroundPaintEnabled()) {
        iPlatformComponentPainter cp = ac.getComponentPainter();

        if (cp == null) {
          cp = new UIComponentPainter();
        }

        cp.setPainterHolder(p);
        ac.setComponentPainter(cp);
      }

      if (p.getNormalIcon() != null) {
        ac.setIcon(p.getNormalIcon());
      }

      if (p.getDisabledIcon() != null) {
        ac.setDisabledIcon(p.getDisabledIcon());
      }

      if (p.getPressedIcon() != null) {
        ac.setPressedIcon(p.getPressedIcon());
      }
    }
  }

  protected iActionComponent createButton(DateChooser cfg) {
    dateViewManager = new DateViewManager();
    configure(dateViewManager, cfg);

    return new DateButton((DateViewManager) dateViewManager);
  }

  protected iPlatformComponent createComboBox(DateChooser cfg) {
    DateComboBox comp = new DateComboBox(this);

    dateViewManager = comp.getDateViewManager();
    comp.setUseDialogButton(cfg.showPopupAsDialog.booleanValue());
    comp.setShowPopupAsDialog(cfg.showPopupAsDialog.booleanValue());
    comp.setEditable(cfg.editable.booleanValue());

    if (!cfg.showPopupButton.booleanValue()) {
      comp.setButtonVisible(false);
    } else {
      PainterHolder p = PainterHolder.create(this, cfg.showPopupButton);

      comp.setButtonPainterHolder(p);
    }

    configure(dateViewManager, cfg);
    comp.setContent();

    return comp;
  }

  protected iPlatformComponent createMultiPanel(DateChooser cfg) {
    return createSinglePanel(cfg);
  }

  protected iPlatformComponent createSinglePanel(DateChooser cfg) {
    dateViewManager = new DateViewManager();
    configure(dateViewManager, cfg);

    DatePanel comp = new DatePanel(this, (DateViewManager) dateViewManager);

    comp.setContent();

    return comp;
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (l != null) {
      if(l.isChangeEventEnabled()) {
        dateViewManager.addChangeListener(l);
      }
      if (l.isExpansionEventsEnabled()) {
        addPopupMenuListener(l);
      }
    }
  }

  @Override
  protected void uninitializeListeners(aWidgetListener l) {
    super.uninitializeListeners(l);

    if (l != null) {
      if(l!=null) {
        dateViewManager.removeChangeListener(l);
      }
      removeActionListener(l);
      removePopupMenuListener(l);
    }
  }

  protected void setMinMaxDate(Calendar date, boolean min) {
    if (min) {
      dateViewManager.setMinDate(date);
    } else {
      dateViewManager.setMaxDate(date);
    }
  }

  private String[] toStringArray(Date[] a) {
    if (a == null) {
      return null;
    }

    DateFormat f   = getAppContext().getDefaultDateContext().getItemFormat();
    int        len = a.length;
    String[]   s   = new String[len];

    for (int i = 0; i < len; i++) {
      s[i] = f.format(a[i]);
    }

    return s;
  }
}
