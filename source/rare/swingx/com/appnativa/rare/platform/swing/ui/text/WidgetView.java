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

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.FormView;
import javax.swing.text.html.HTML;
import javax.swing.text.html.Option;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.platform.swing.ui.text.HTMLEditorKitEx.iGroupView;
import com.appnativa.rare.spot.Button;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.ComboBox;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.GroupBoxViewer;
import com.appnativa.rare.viewer.ListBoxViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.CheckBoxWidget;
import com.appnativa.rare.widget.ComboBoxWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.RadioButtonWidget;
import com.appnativa.rare.widget.TextAreaWidget;
import com.appnativa.rare.widget.TextFieldWidget;
import com.appnativa.rare.widget.iWidget;
import com.jgoodies.forms.layout.CellConstraints;

/**
 * FormView that supports Rare widgets
 *
 * @author Don DeCoteau
 */
public class WidgetView extends FormView {
  iContainer           parent;
  protected int        maxIsPreferred;
  protected JComponent sComponent;

  public WidgetView(Element e, iContainer parent) {
    super(e);
    this.parent = parent;
  }

  @Override
  public void paint(Graphics g, Shape a) {
    super.paint(g, a);

    iWidget w = Platform.findWidgetForComponent(sComponent);

    if (w == null) {
      return;
    }

    if (!w.isRequired() && (w.getAttribute("_required") != Boolean.TRUE)) {
      return;
    }

    iPlatformPainter p = w.getAppContext().getRequiredFieldOverlayPainter();

    if (p != null) {
      Rectangle r = (a instanceof Rectangle)
                    ? (Rectangle) a
                    : a.getBounds();

      if (r.height > 0) {
        p.paint(this.getContainer(), (Graphics2D) g, r.x - 6, r.y - 4, r.width, r.height, true, iPainter.HORIZONTAL);
      }
    }
  }

  /**
   * @return the fontFamily
   */
  public String getFontFamily() {
    return StyleConstants.getFontFamily(getElement().getAttributes());
  }

  /**
   * @return the fontSize
   */
  public int getFontSize() {
    return StyleConstants.getFontSize(getElement().getAttributes());
  }

  @Override
  public float getMaximumSpan(int axis) {
    if ((sComponent != null) &&!sComponent.isVisible()) {
      return 0;
    }

    switch(axis) {
      case View.X_AXIS :
        if ((maxIsPreferred & 1) == 1) {
          super.getMaximumSpan(axis);

          return getPreferredSpan(axis);
        }

        return super.getMaximumSpan(axis);

      case View.Y_AXIS :
        if ((maxIsPreferred & 2) == 2) {
          super.getMaximumSpan(axis);

          return getPreferredSpan(axis);
        }

        return super.getMaximumSpan(axis);

      default :
        break;
    }

    return super.getMaximumSpan(axis);
  }

  @Override
  public float getMinimumSpan(int axis) {
    if ((sComponent != null) &&!sComponent.isVisible()) {
      return 0;
    }

    return super.getMinimumSpan(axis);
  }

  @Override
  public float getPreferredSpan(int axis) {
    if ((sComponent != null) &&!sComponent.isVisible()) {
      return 0;
    }

    return super.getPreferredSpan(axis);
  }

  @Override
  public boolean isVisible() {
    return (sComponent == null)
           ? false
           : sComponent.isVisible();
  }

  static Object getAttribute(AttributeSet set, Object key) {
    return set.isDefined(key)
           ? set.getAttribute(key)
           : null;
  }

  protected iWidget createComboBox(String name, String cls, ListModel model, AttributeSet attr) throws Exception {
    ComboBox cfg    = new ComboBox();
    boolean  is_sdf = false;

    if ((cls != null) && (cls.indexOf('{') != -1)) {
      DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
      is_sdf = true;
    } else {
      cfg.templateName.setValue((cls != null)
                                ? cls
                                : Platform.getUIDefaults().getString("Rare.template.html.combobox"));
    }

    cfg.name.setValue(name);
    cfg.submitValue.setValue(ComboBox.CSubmitValue.selected_linked_data);
    setupConfiguration(cfg, attr, false, !is_sdf);

    ComboBoxWidget w = new ComboBoxWidget(parent);

    w.configure(cfg);
    addToList(model, w);
    ((JComponent) w.getContainerComponent()).setAlignmentY(0.75f);
    maxIsPreferred = 3;

    return w;
  }

  @Override
  protected Component createComponent() {
    AttributeSet attr  = getElement().getAttributes();
    HTML.Tag     t     = (HTML.Tag) attr.getAttribute(StyleConstants.NameAttribute);
    Object       model = attr.getAttribute(StyleConstants.ModelAttribute);
    iWidget      w     = null;
    View         p     = getParent();

    while((p != null) &&!(p instanceof iGroupView)) {
      p = p.getParent();
    }

    if (p instanceof iGroupView) {
      iContainer con = ((iGroupView) p).getViewer();

      if ((con != parent) && (con.getWidgetType() == iWidget.WidgetType.GroupBox)) {
        parent = con;
      }
    }

    try {
      if (t == HTML.Tag.INPUT) {
        w = createInputComponent(attr, model);
        attr.getAttribute(StyleConstants.ModelAttribute);
      } else {
        String cls = (String) getAttribute(attr, HTML.Attribute.CLASS);

        if (t == HTML.Tag.SELECT) {
          String name = (String) attr.getAttribute(HTML.Attribute.NAME);

          if ((cls != null) && (cls.indexOf("{") != -1)) {
            int     rows     = HTML.getIntegerAttributeValue(attr, HTML.Attribute.SIZE, 1);
            boolean multiple = attr.isDefined("multiple");

            w = handleSpecialSelect(cls, (ListModel) model, rows < 2, multiple, attr);

            if (w != null) {
              ((JComponent) w.getContainerComponent()).setAlignmentY((rows < 2)
                      ? 0.75f
                      : 0.05f);
            }

            maxIsPreferred = 3;
          } else if (model instanceof ComboBoxModel) {
            w = createComboBox(name, cls, (ListModel) model, attr);
          } else {
            w = createListBox(name, cls, (ListModel) model, attr);
          }
        } else if (t == HTML.Tag.TEXTAREA) {
          String   name   = (String) getAttribute(attr, HTML.Attribute.NAME);
          TextArea cfg    = new TextArea();
          boolean  is_sdf = false;

          if ((cls != null) && (cls.indexOf('{') != -1)) {
            DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
            is_sdf = true;
          } else {
            cfg.templateName.setValue((cls != null)
                                      ? cls
                                      : Platform.getUIDefaults().getString("Rare.template.html.textarea"));
          }

          int rows = HTML.getIntegerAttributeValue(attr, HTML.Attribute.ROWS, 1);
          int cols = HTML.getIntegerAttributeValue(attr, HTML.Attribute.COLS, 20);

          setupConfiguration(cfg, null, false, !is_sdf);
          cfg.name.setValue(name);
          cfg.visibleLines.setValue(rows);
          cfg.visibleCharacters.setValue(cols);
          w = createTextAreaWidget(parent, cfg);

          JTextArea field = (JTextArea) w.getDataComponent();

          field.putClientProperty("RARE_USE_HTML_WIDTH", Boolean.TRUE);

          if (attr.isDefined("readonly")) {
            field.setEditable(false);
          }

          if (model != null) {
            field.setDocument((Document) model);
          }

          maxIsPreferred = 3;
          ((JComponent) w.getContainerComponent()).setAlignmentY(0.04f);
        }
      }

      if (w != null) {
        sComponent = (JComponent) w.getContainerComponent();

        iContainer container = parent;

        w.setParent(container);

        if (container.isRequired() && (container.getWidgetCount() == 1) && (w.getOverlayPainter() == null)) {
          w.setOverlayPainter(parent.getAppContext().getRequiredFieldOverlayPainter());
        } else if (w.getOverlayPainter() == parent.getAppContext().getRequiredFieldOverlayPainter()) {
          w.setOverlayPainter(null);    // we will handle the painting ourselves
        }

        return sComponent;
      }
    } catch(Exception ex) {
      Platform.ignoreException((String) getAttribute(attr, HTML.Attribute.CLASS), ex);
    }

    return null;
  }

  private iWidget createTextAreaWidget(iContainer parent, TextArea cfg) {
    TextAreaWidget w = new TextAreaWidget(parent);

    w.configure(cfg);

    return null;
  }

  protected iWidget createCustom(String cls, AttributeSet attr) throws Exception {
    String name  = (String) getAttribute(attr, HTML.Attribute.NAME);
    String value = (String) getAttribute(attr, HTML.Attribute.VALUE);

    if (cls.indexOf('{') == -1) {
      cls += "{}";
    }

    Widget cfg = (Widget) DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), null, "text/x-sdf", null);

    setupConfiguration(cfg, attr, false, true);

    if (name != null) {
      cfg.name.setValue(name);
    }

    iWidget w = FormViewer.createWidget(parent.getContainerViewer(), cfg);

    if (value != null) {
      w.setValue(w.expandString(value, false));
    }

    return w;
  }

  protected iWidget createGroupBox(ListModel model, boolean horiz, boolean multiple, AttributeSet attr) {
    String   cls  = (String) attr.getAttribute(HTML.Attribute.CLASS);
    String   name = (String) attr.getAttribute(HTML.Attribute.NAME);
    int      len  = model.getSize();
    GroupBox gb   = new GroupBox();

    gb.name.setValue(name);

    Button cfg;

    if ((cls != null) && (cls.indexOf('{') != -1)) {
      try {
        cfg = (Button) DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), null, "text/x-sdf", null);
      } catch(Exception ex) {
        Platform.ignoreException("createGroupBox", ex);

        return null;
      }
    } else {
      cfg = multiple
            ? new CheckBox()
            : new RadioButton();
      cfg.templateName.setValue(cls);
    }

    Option o;

    if (!multiple) {
      cfg.groupName.setValue(name);
    }

    String  s;
    iWidget ww;
    Font    f = null;

    if (!cfg.font.spot_valueWasSet()) {
      f = getFont();
    }

    GroupBoxViewer w = new GroupBoxViewer(parent);

    w.configure(gb);

    FormsPanel fp = (FormsPanel) w.getDataComponent();

    if (horiz) {
      fp.ensureGrid(1, len, 2, 2);
    } else {
      fp.ensureGrid(len, 1, 2, 2);
    }

    CellConstraints cc = new CellConstraints();

    setupConfiguration(cfg, null, false, true);

    for (int i = 0; i < len; i++) {
      if (horiz) {
        cc.gridX = i * 2 + 1;
      } else {
        cc.gridY = i * 2 + 1;
      }

      o = (Option) model.getElementAt(i);
      cfg.value.setValue(o.getLabel().trim());
      cfg.name.setValue(name + "_" + (i + 1));
      s = o.getValue();

      if ((s != null) && (s.length() > 0)) {
        cfg.customProperties.setValue("value=" + s);
      } else {
        cfg.customProperties.spot_clear();
      }

      ww = FormViewer.createWidget(parent.getContainerViewer(), cfg);

      if (o.isSelected()) {
        ww.setSelected(true);
      }

      if (f != null) {
        ww.setFont(UIFont.fromFont(f));
      }

      w.addWidget(ww, cc, -1);
    }

    return w;
  }

  /**
   * Creates a component for an &lt;INPUT&gt; element based on the
   * value of the "type" attribute.
   *
   * @param attr of attributes associated with the &lt;INPUT&gt; element.
   * @param model the value of the StyleConstants.ModelAttribute
   * @return the component.
   */
  protected iWidget createInputComponent(AttributeSet attr, Object model) throws Exception {
    JComponent c      = null;
    int        mp     = -1;
    String     type   = (String) getAttribute(attr, HTML.Attribute.TYPE);
    String     name   = (String) getAttribute(attr, HTML.Attribute.NAME);
    String     value  = (String) getAttribute(attr, HTML.Attribute.VALUE);
    String     cls    = (String) getAttribute(attr, HTML.Attribute.CLASS);
    iWidget    w      = null;
    float      alignY = 0.75f;
    boolean    actionSet;
    boolean    is_sdf = ((cls != null) && (cls.indexOf('{') != -1));

    if (value != null) {
      value = parent.expandString(value, false);
    }

    if (type.equals("submit") || type.equals("reset")) {
      if ((name == null) || (name.length() == 0)) {
        name = type;
      }

      if (value == null) {
        if (type.equals("submit")) {
          value = Platform.getUIDefaults().getString("FormView.submitButtonText");
        } else {
          value = Platform.getUIDefaults().getString("FormView.resetButtonText");
        }
      }

      PushButton cfg = new PushButton();

      if (is_sdf) {
        DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
      }

      if (type.equals("submit")) {
        cfg.actionType.setValue(PushButton.CActionType.submit_form);
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.submit"));
      } else {
        cfg.actionType.setValue(PushButton.CActionType.clear_form);
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.reset"));
      }

      actionSet = setupConfiguration(cfg, attr, true, !is_sdf);
      cfg.value.setValue(value);
      cfg.name.setValue(name);
      w = PushButtonWidget.create(parent, cfg);

      JButton button = (JButton) w.getDataComponent();

      if (model != null) {
        button.setModel((ButtonModel) model);

        if (!actionSet) {
          // button.addActionListener(this);
        }
      }
    } else if (type.equals("image")) {
      String     srcAtt = (String) getAttribute(attr, HTML.Attribute.SRC);
      PushButton cfg    = new PushButton();

      if (is_sdf) {
        DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
      } else {
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.image"));
      }

      cfg.buttonStyle.spot_setDefaultValue("toolbar");
      actionSet = setupConfiguration(cfg, attr, true, !is_sdf);
      cfg.name.setValue(name);
      w = PushButtonWidget.create(parent, cfg);

      JButton button = (JButton) w.getDataComponent();

      try {
        button.setIcon(parent.getIcon(srcAtt, null));
      } catch(Exception e) {
        button.setText(srcAtt);
      }

      if (model != null) {
        button.setModel((ButtonModel) model);

        if (!actionSet) {
          button.addMouseListener(new MouseEventListenerEx());
        }
      }
    } else if (type.equals("checkbox")) {
      CheckBox cfg = new CheckBox();

      cfg.submitValue.setValue(CheckBox.CSubmitValue.value_property);

      if (is_sdf) {
        DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);

        if ((value != null) &&!cfg.value.spot_valueWasSet()) {
          cfg.value.setValue(value);
        }
      } else {
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.check"));
      }

      if (((ButtonModel) model).isSelected()) {
        cfg.selected.setValue(true);
      }

      cfg.name.setValue(name);
      setupConfiguration(cfg, attr, false, !is_sdf);
      w = CheckBoxWidget.create(parent, cfg);
      w.setAttribute("value", value);
    } else if (type.equals("radio")) {
      RadioButton cfg = new RadioButton();

      cfg.submitValue.setValue(CheckBox.CSubmitValue.value_property);

      if (is_sdf) {
        DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);

        if ((value != null) &&!cfg.value.spot_valueWasSet()) {
          cfg.value.setValue(value);
        }
      } else {
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.radio"));
      }

      if (attr.isDefined("selected")) {
        cfg.selected.setValue(true);
      }

      setupConfiguration(cfg, attr, false, true);

      if ((name != null) && (!cfg.groupName.spot_valueWasSet() || (cfg.groupName.getValue().length() == 0))) {
        cfg.groupName.setValue(name);
      }

      w = createRadioButtonWidget(parent, cfg);
      w.setAttribute("value", value);
      alignY = 0.7f;
    } else if (type.equals("text")) {
      if (is_sdf &&!cls.startsWith("TextField")) {
        w = createCustom(cls, attr);
      } else {
        int       size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.SIZE, 20);
        TextField cfg  = new TextField();

        if (is_sdf) {
          DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
        } else {
          cfg.templateName.setValue((cls != null)
                                    ? cls
                                    : Platform.getUIDefaults().getString("Rare.template.html.text"));
        }

        cfg.name.setValue(name);
        actionSet = setupConfiguration(cfg, attr, false, !is_sdf);
        cfg.visibleCharacters.setValue(size);
        size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.MAXLENGTH, 0);

        if (size > 0) {
          cfg.maxCharacters.setValue(size);
        }

        w = createTextFieldWidget(parent, cfg);

        JTextField field = (JTextField) w.getDataComponent();

        field.putClientProperty("RARE_USE_HTML_WIDTH", Boolean.TRUE);

        if (attr.isDefined("readonly")) {
          field.setEditable(false);
        }

        if (model != null) {
          field.setDocument((Document) model);
        }

        if (!actionSet) {
          field.addActionListener(this);
        }
      }
    } else if (type.equals("password")) {
      int           size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.SIZE, 20);
      PasswordField cfg  = new PasswordField();

      if (is_sdf) {
        DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
      } else {
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.password"));
      }

      cfg.name.setValue(name);
      actionSet = setupConfiguration(cfg, attr, false, !is_sdf);
      cfg.visibleCharacters.setValue(size);
      size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.MAXLENGTH, 0);

      if (size > 0) {
        cfg.maxCharacters.setValue(size);
      }

      w = createTextFieldWidget(parent, cfg);

      JPasswordField field = (JPasswordField) w.getDataComponent();

      if (model != null) {
        field.setDocument((Document) model);
      }

      if (!actionSet) {
        field.addActionListener(this);
      }
    } else if (type.equals("button")) {
      String     imgSrc = (String) getAttribute(attr, HTML.Attribute.SRC);
      PushButton cfg    = new PushButton();

      if (is_sdf) {
        DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
      } else {
        cfg.templateName.setValue((cls != null)
                                  ? cls
                                  : Platform.getUIDefaults().getString("Rare.template.html.button"));
      }

      actionSet = setupConfiguration(cfg, attr, true, !is_sdf);
      cfg.name.setValue(name);
      cfg.value.setValue(value);
      w = PushButtonWidget.create(parent, cfg);

      JButton button = (JButton) w.getDataComponent();

      if (model != null) {
        button.setModel((ButtonModel) model);

        if (!actionSet) {
          button.addActionListener(this);
        }
      }

      if ((imgSrc != null) && (imgSrc.length() > 0)) {
        try {
          button.setIcon(parent.getIcon(imgSrc, null));
        } catch(Exception e) {}
      }
    } else if (type.equals("custom")) {
      w = createCustom(cls, attr);
    } else if (type.equals("hidden")) {    // must be a button
      parent.getFormViewer().setSubmittAttribute(name, value);
    }

    if (w != null) {
      if (!w.getDataComponent().isFontSet()) {
        w.setFont(UIFont.fromFont(getFont()));
      }

      maxIsPreferred = (mp == -1)
                       ? 3
                       : mp;

      if (attr.isDefined("readonly") && (w.getDataComponent() instanceof JTextComponent)) {
        ((JTextComponent) w.getDataComponent()).setEditable(false);
      }

      c = (JComponent) w.getContainerComponent();
      c.setAlignmentY(alignY);
    }

    return w;
  }

  private iWidget createTextFieldWidget(iContainer parent, TextField cfg) {
    TextFieldWidget w = new TextFieldWidget(parent);

    w.configure(cfg);

    return w;
  }

  private iWidget createRadioButtonWidget(iContainer parent, RadioButton cfg) {
    RadioButtonWidget w = new RadioButtonWidget(parent);

    w.configure(cfg);

    return w;
  }

  protected iWidget createListBox(String name, String cls, ListModel model, AttributeSet attr) throws Exception {
    int     size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.SIZE, 1);
    ListBox cfg  = new ListBox();

    cfg.submitValue.setValue(ListBox.CSubmitValue.selected_linked_data);

    if ((cls != null) && (cls.indexOf('{') != -1)) {
      DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);
    } else {
      cfg.templateName.setValue((cls != null)
                                ? cls
                                : Platform.getUIDefaults().getString("Rare.template.html.listbox"));
    }

    cfg.name.setValue(name);
    cfg.visibleRowCount.setValue(size);

    if (attr.isDefined("multiple")) {
      cfg.selectionMode.setValue(ListBox.CSelectionMode.multiple);
    }

    setupConfiguration(cfg, attr, false, true);

    ListBoxViewer w = ListBoxViewer.create(parent, cfg);

    addToList(model, w);
    ((JComponent) w.getContainerComponent()).setAlignmentY(1f);
    maxIsPreferred = 3;

    return w;
  }

  protected iWidget handleSpecialSelect(String cls, ListModel model, boolean horiz, boolean multiple, AttributeSet attr)
          throws Exception {
    if (cls.startsWith("Check") || cls.startsWith("Radio")) {
      return createGroupBox(model, horiz, multiple, attr);
    } else if (cls.startsWith("Push")) {
      int        len = model.getSize();
      Option     o;
      String     s;
      int        size = HTML.getIntegerAttributeValue(attr, HTML.Attribute.SIZE, 0);
      PushButton cfg  = new PushButton();

      DataParser.loadSPOTObjectSDF(parent, new StringReader(cls), cfg, "text/x-sdf", null);

      ArrayList<RenderableDataItem> alist = new ArrayList<RenderableDataItem>(len);
      int                           sel   = -1;

      for (int i = 0; i < len; i++) {
        o = (Option) model.getElementAt(i);
        s = o.getLabel();
        alist.add(new RenderableDataItem(s, o.getValue()));

        if (o.isSelected()) {
          sel = i;
        }
      }

      String name = (String) attr.getAttribute(HTML.Attribute.NAME);

      setupConfiguration(cfg, attr, false, true);

      if (name != null) {
        cfg.name.setValue(name);
      }

      if (size > 0) {
        cfg.bounds.width.setValue(size + "ch");
      }

      PushButtonWidget w = (PushButtonWidget) FormViewer.createWidget(parent.getContainerViewer(), cfg);

      w.addAll(alist);

      if (sel > -1) {
        w.setSelectedIndex(sel);
      }

      return w;
    }

    iWidget w = createCustom(cls, attr);

    if (w instanceof iListHandler) {
      int          len  = model.getSize();
      iListHandler list = (iListHandler) w;
      Option       o;

      for (int i = 0; i < len; i++) {
        o = (Option) model.getElementAt(i);
        list.add(new RenderableDataItem(o.getLabel(), o.getValue()));
      }
    }

    return w;
  }

  protected boolean setupConfiguration(Widget cfg, AttributeSet attr, boolean fixClick, boolean applyTemplate) {
    cfg.spot_setAllowInvalidAttributes(true);

    String tname = cfg.templateName.getValue();

    TemplateHandler.getInstance(parent, null).applyTemplate(cfg, tname);

    String value;

    if (attr != null) {
      if (attr.isDefined("required")) {
        cfg.required.setValue(true);
      }

      if (attr.isDefined("disabled")) {
        cfg.enabled.setValue(false);
      }

      for (Enumeration e = attr.getAttributeNames(); e.hasMoreElements(); ) {
        Object a    = e.nextElement();
        String name = a.toString();

        name = WidgetListener.fromWebEventEx(name);

        if (name == null) {
          continue;
        }

        a = attr.getAttribute(a);

        if (a != null) {
          value = a.toString();
          cfg.setEventHandler(name, value);
        }
      }
    }

    if (!cfg.font.spot_valueWasSet()) {
      value = getFontFamily();

      if ("Monospaced".equalsIgnoreCase(value)) {
        cfg.font.monospaced.setValue(true);
      }

      int size = getFontSize();

      if (size > 0) {
        cfg.font.size.setValue(size);
      }
    }

    return cfg.spot_getAttribute("onAction") != null;
  }

  private void addToList(ListModel model, iListHandler lc) {
    int    len = model.getSize();
    Option o;
    Object data;
    String value;

    for (int i = 0; i < len; i++) {
      o     = (Option) model.getElementAt(i);
      value = o.getLabel();

      if (value == null) {
        continue;
      }

      value = value.trim();
      data  = o.getValue();
      lc.add(new RenderableDataItem(value, data));

      if (o.isSelected()) {
        lc.addSelectionIndex(i);
      }
    }
  }

  private Font getFont() {
    AttributeSet set    = this.getElement().getAttributes();
    String       family = StyleConstants.getFontFamily(set);
    int          size   = StyleConstants.getFontSize(set);
    int          style  = Font.PLAIN;
    Font         f      = null;

    if ("monospaced".equalsIgnoreCase(family)) {
      f      = Platform.getUIDefaults().getFont("Rare.font.plaintext");
      family = f.getFamily();
    } else if ((family != null) && family.endsWith("Serif")) {
      f      = Platform.getUIDefaults().getFont("Label.font");
      family = f.getFamily();
    }

    if (StyleConstants.isBold(set)) {
      style = Font.BOLD;
    }

    if (StyleConstants.isItalic(set)) {
      style += Font.ITALIC;
    }

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Rare.html.font");
    }

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Label.font");
    }

    if ((f.getStyle() != style) || (f.getSize() != size) ||!f.getFamily().equals(family)) {
      if (family == null) {
        family = f.getFamily();
      }

      f = new Font(family, style, size);
    }

    return f;
  }

  protected class MouseEventListenerEx extends MouseEventListener {}
}
