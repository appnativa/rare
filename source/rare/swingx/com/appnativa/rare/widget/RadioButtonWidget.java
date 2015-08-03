package com.appnativa.rare.widget;

import javax.swing.AbstractButton;
import javax.swing.JToggleButton.ToggleButtonModel;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.viewer.iContainer;

/**
 * A widget that allows a user to select one, and only one option from a set of
 * alternatives.
 * 
 * @author Don DeCoteau
 */
public class RadioButtonWidget extends aGroupableButton {

  /** if the radio button was initially selected */
  protected boolean initiallySelected;

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public RadioButtonWidget(iContainer parent) {
    super(parent);
    widgetType = WidgetType.RadioButton;
  }

  @Override
  public void configure(Widget cfg) {
    configureEx((RadioButton) cfg);
    configurePainters((Button) cfg);
    fireConfigureEvent(cfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public Object getHTTPFormValue() {
    return isSelected() ? super.getHTTPFormValue() : null;
  }

  @Override
  public void reset() {
    setSelected(initiallySelected);
  }

  class ToggleButtonModelEx extends ToggleButtonModel {
    @Override
    public void setSelected(boolean b) {
      if (!b && buttonGroup != null) {
        if (buttonGroup.getSelectedButton() == RadioButtonWidget.this && !allowDeselection) {
          return;
        }
        ;
      }
      super.setSelected(b);
    }
  }

  @Override
  protected iActionComponent createButton(Button cfg) {
    AbstractButton b = getAppContext().getComponentCreator().getRadioButton(getViewer(), (RadioButton) cfg);
    b.setModel(new ToggleButtonModelEx());
    return new ActionComponent(b);

  }
}
