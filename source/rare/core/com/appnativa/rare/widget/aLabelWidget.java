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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.viewer.iContainer;

public abstract class aLabelWidget extends aPlatformWidget {

  /** the label's initial value */
  protected String initialValue;

  /**
   * Constructs a new instance.
   *
   * @param parent
   *          the widget's parent
   */
  public aLabelWidget(iContainer parent) {
    super(parent);
    widgetType    = WidgetType.Label;
    isSubmittable = false;
  }

  @Override
  public void clearContents() {
    super.clearContents();
    setValue("");
  }

  /**
   * Configures the label
   *
   * @param cfg
   *          the label's configuration
   */
  public void configure(Label cfg) {
    iActionComponent comp = createActionComponent(cfg);

    comp.setWordWrap(false);
    dataComponent = formComponent = comp;
    configure(cfg, true, false, true, true);

    String s;

    if ((cfg.textHAlignment.intValue() != Label.CTextHAlignment.auto)
        || (cfg.textVAlignment.intValue() != Label.CTextVAlignment.auto)) {
      VerticalAlign   val = Utils.getVerticalAlignment(cfg.textVAlignment.intValue());
      HorizontalAlign hal = Utils.getHorizontalAlignment(cfg.textHAlignment.intValue());

      comp.setAlignment(hal, val);
    }

    if (cfg.orientation.spot_valueWasSet()) {
      Utils.setOrientation(comp, cfg.orientation.getValue());
    }

    if (cfg.iconPosition.intValue() != Label.CIconPosition.auto) {
      comp.setIconPosition(Utils.getIconPosition(cfg.iconPosition.intValue()));
    }

    if (cfg.wordWrap.spot_valueWasSet() && getAppContext().okForOS(cfg.wordWrap)) {
      comp.setWordWrap(cfg.wordWrap.booleanValue());
    }

    iPlatformIcon icon = getIcon(cfg.icon);

    if (icon != null) {
      comp.setIcon(icon);
    }

    s = cfg.value.getValue();

    if (s != null) {
      initialValue = s;
    }

    comp.setText(expandString(initialValue, false));

    if (draggingAllowed || (!cfg.draggingAllowed.spot_valueWasSet() && getAppContext().areAllLabelsDraggable())) {
      draggingAllowed = true;
    }

    configureGenericDnD(comp, cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public void configure(Widget cfg) {
    configure((Label) cfg);
  }

  /**
   * Creates a new label widget
   *
   * @param parent
   *          the parent
   *
   * @return the label widget
   */
  public static LabelWidget create(iContainer parent) {
    return create(parent, null);
  }

  /**
   * Creates a new label widget
   *
   * @param parent
   *          the parent
   * @param cfg
   *          the configuration
   *
   * @return the label widget
   */
  public static LabelWidget create(iContainer parent, Label cfg) {
    if (cfg == null) {
      cfg = new Label();
    }

    LabelWidget widget = new LabelWidget(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void reset() {
    if (initialValue != null) {
      setValue(expandString(initialValue, false));
    }
  }

  @Override
  public void setHorizontalAlignment(HorizontalAlign alignment) {
    ((iActionComponent) dataComponent).setAlignment(alignment, getVerticalAlignment());
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    ((iActionComponent) dataComponent).setIcon(icon);
  }

  /**
   * Sets the icon position
   *
   * @param position
   *          the icon position
   */
  @Override
  public void setIconPosition(IconPosition position) {
    ((iActionComponent) dataComponent).setIconPosition(position);
  }

  public void setMargin(UIInsets insets) {
    ((iActionComponent) dataComponent).setMargin(insets);
  }

  public void setMargin(float top, float right, float bottom, float left) {
    ((iActionComponent) dataComponent).setMargin(top, right, bottom, left);
  }

  public void setText(CharSequence text) {
    ((iActionComponent) dataComponent).setText(text);
  }

  @Override
  public void setValue(Object value) {
    iActionComponent label = (iActionComponent) getDataComponentEx();

    if (value == null) {
      label.setText("");
    } else if (value instanceof CharSequence) {
      label.setText((CharSequence) value);
    } else {
      label.setText(value.toString());
    }
  }

  @Override
  public void setVerticalAlignment(VerticalAlign alignment) {
    ((iActionComponent) dataComponent).setAlignment(getHorizontalAlignment(), alignment);
  }

  public void setWordWrap(boolean wrap) {
    ((iActionComponent) dataComponent).setWordWrap(wrap);
  }

  @Override
  public Object getHTTPFormValue() {
    return null;
  }

  @Override
  public Object getSelection() {
    return getValue();
  }

  public CharSequence getText() {
    return ((iActionComponent) dataComponent).getText();
  }

  @Override
  public Object getValue() {
    return ((iActionComponent) dataComponent).getText();
  }

  public boolean isWordWrap() {
    return ((iActionComponent) dataComponent).isWordWrap();
  }

  protected iHyperlinkListener createHyperlinkListener() {
    return new iHyperlinkListener() {
      @Override
      public void linkExited(Object source, Object item, String href, MouseEvent e) {
        aWidgetListener l = getWidgetListener();

        if ((l != null) && l.isChangeEventEnabled()) {
          ItemChangeEvent ce = new ItemChangeEvent(getDataComponent(), href, null);

          l.itemChanged(ce);
        }
      }
      @Override
      public void linkEntered(Object source, Object item, String href, MouseEvent e) {
        aWidgetListener l = getWidgetListener();

        if ((l != null) && l.isChangeEventEnabled()) {
          ItemChangeEvent ce = new ItemChangeEvent(getDataComponent(), null, href);

          l.itemChanged(ce);
        }
      }
      @Override
      public void linkClicked(Object source, Object item, String href, MouseEvent e) {
        aWidgetListener l = getWidgetListener();

        if ((l != null) && l.isActionEventEnabled()) {
          l.actionPerformed(new ActionEvent(getDataComponent(), href));
        }
      }
    };
  }

  protected abstract iActionComponent createActionComponent(Label cfg);
}
