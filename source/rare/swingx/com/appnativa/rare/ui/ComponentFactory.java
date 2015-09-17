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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.plaf.RareButtonUI;
import com.appnativa.rare.platform.swing.ui.text.TextEditor;
import com.appnativa.rare.platform.swing.ui.text.TextPaneEditor;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.ButtonView;
import com.appnativa.rare.platform.swing.ui.view.CheckBoxView;
import com.appnativa.rare.platform.swing.ui.view.HTMLTextEditor;
import com.appnativa.rare.platform.swing.ui.view.JMenuBarEx;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.platform.swing.ui.view.LineView;
import com.appnativa.rare.platform.swing.ui.view.ListView;
import com.appnativa.rare.platform.swing.ui.view.PasswordTextFieldView;
import com.appnativa.rare.platform.swing.ui.view.ProgressBarView;
import com.appnativa.rare.platform.swing.ui.view.RadioButtonView;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;
import com.appnativa.rare.platform.swing.ui.view.SeparatorView;
import com.appnativa.rare.platform.swing.ui.view.SliderView;
import com.appnativa.rare.platform.swing.ui.view.TextAreaView;
import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.platform.swing.ui.view.ToggleButtonView;
import com.appnativa.rare.platform.swing.ui.view.TreeView;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.MenuItem;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.rare.ui.table.TreeTableView;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.SNumber;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.ObjectInput;
import java.io.ObjectInputStream;

import java.net.URL;

import java.text.ParseException;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

/**
 * Class hat holds base components
 *
 * @author Don DeCoteau
 */
public class ComponentFactory implements iPlatformComponentFactory {

  /**  */
  public final static ToolTipMouseListener toolTipMouseListener   = new ToolTipMouseListener();
  public static FocusListener              selectAllFocusListener = new FocusListener() {
    @Override
    public void focusGained(FocusEvent e) {
      if (e.getSource() instanceof JTextComponent) {
        final JTextComponent tc = (JTextComponent) e.getSource();

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            tc.selectAll();
          }
        });
      }
    }
    @Override
    public void focusLost(FocusEvent e) {}
  };

  /**  */
  protected iPlatformAppContext appContext;

  /**
   * Creates a new instance of ComponentFactory
   */
  public ComponentFactory() {}

  public static Object createToolTipObject(JComponent source, boolean location, Component component,
          Rectangle cellRect, String text, boolean overlapping, int xOffset) {
    if ((text == null) || (text.length() == 0)) {
      return null;
    }

    if ((component instanceof LabelView) && ((LabelView) component).isHTMLText()) {
      return null;
    }

    if (component instanceof LabelView) {
      Rectangle rect = SwingHelper.getToolTipRect((JLabel) component, text, cellRect.width, cellRect.height,
                         overlapping);

      rect.x   += cellRect.x;
      rect.y   += cellRect.y;
      cellRect = rect;

      if (!cellRect.intersects(source.getBounds())) {
        cellRect = null;
      } else {
        // border offset
        cellRect.x--;
        cellRect.y--;
      }
    } else {
      cellRect = null;
    }

    if (location) {
      return cellRect;
    }

    return text;
  }

  @Override
  public iPlatformComponent resolveRenderingComponent(iWidget widget, iPlatformComponent rc) {
    // TODO Auto-generated method stub
    return null;
  }

  public static String resolveToolTip(Component comp, RenderableDataItem item, String tip) {
    if (tip.startsWith("{")) {
      if ("{%linkedData}".equals(tip)) {
        Object o = item.getLinkedData();

        tip = (o == null)
              ? null
              : o.toString();
      } else {
        iWidget w = Platform.findWidgetForComponent(comp);

        tip = (w == null)
              ? null
              : w.expandString(tip, false);
      }
    }

    return tip;
  }

  @Override
  public void systemAlert(iWidget context, Object message, int displayLocation, iPlatformIcon icon,
                          iActionable actionable, int monitor) {}

  @Override
  public void setAppContext(iPlatformAppContext app) {
    appContext = app;
  }

  @Override
  public Object getBean(iWidget context, Bean cfg) {
    Object c;

    try {
      String   name = cfg.name.getValue();
      JMenuBar mb   = null;

      if ((context != null) && (context.getDataComponent() instanceof JMenuBar)) {
        mb = (JMenuBar) context.getDataComponent();
      }

      if (iConstants.MENU_SEPARATOR_NAME.equals(name)) {
        return getMenuBarSeparator(mb);
      } else if (iConstants.MENU_EXPANSION_NAME.equals(name)) {
        return getMenuBarExpander(mb);
      } else if (iConstants.MENU_BEAN_NAME.equals(name)) {
        return getMenuItem(context, cfg);
      } else if (iConstants.VSCROLLBAR_BEAN_NAME.equals(name)) {
        return new JScrollBar(JScrollBar.VERTICAL);
      } else if (iConstants.VSCROLLBAR_BEAN_NAME.equals(name)) {
        return new JScrollBar(JScrollBar.HORIZONTAL);
      }

      name = cfg.beanJAR.getValue();

      if ((name != null) && (name.length() > 0)) {
        appContext.addJarURL(context.getURL(name));
      }

      name = cfg.beanClass.getValue();

      if ((name != null) && (name.length() > 0)) {
        Class cls = Platform.loadClass(name);

        c = cls.newInstance();
      } else {
        name = cfg.beanURL.getValue();

        if ((name == null) || (name.length() == 0)) {
          c = new LabelView(" ");
        } else {
          URL            url    = context.getURL(name);
          iURLConnection conn   = context.getAppContext().openConnection(url);
          ObjectInput    stream = new ObjectInputStream(conn.getInputStream());

          c = stream.readObject();
        }
      }
    } catch(Throwable ex) {
      if (context.isDesignMode()) {
        ImagePanel p = new ImagePanel(false);

        p.setAutoScale(true);
        p.setImage(appContext.getResourceAsImage("Rare.icon.missingJar"));
        c = p;
      } else {
        String s = cfg.failureMessage.getValue();

        if (s == null) {
          s = appContext.getResourceAsString("Rare.runtime.text.beanFailure");
        }

        c = AlertPanel.error(context, new ErrorInformation(ex, null, s));
      }
    }

    return c;
  }

  @Override
  public AbstractButton getButton(iWidget context, PushButton cfg) {
    ButtonView view;

    switch(cfg.buttonStyle.intValue()) {
      case PushButton.CButtonStyle.hyperlink :
        view = new ButtonView();
        view.setUnderlined(true, false);

        break;

      case PushButton.CButtonStyle.hyperlink_always_underline :
        view = new ButtonView();
        view.setUnderlined(true, true);

        break;

      case PushButton.CButtonStyle.platform :
        view = new ButtonView();
        view.setPlatformButton(true);

        break;

      case PushButton.CButtonStyle.toggle :
        ToggleButtonView toggle = new ToggleButtonView();

        toggle.setFont(FontUtils.getDefaultFont());
        toggle.setForeground(ColorUtils.getForeground());

        return toggle;

      case PushButton.CButtonStyle.toggle_toolbar :
        ToggleButtonView toggletb = new ToggleButtonView();

        toggletb.setFont(FontUtils.getDefaultFont());
        toggletb.setForeground(ColorUtils.getForeground());
        toggletb.setUI(RareButtonUI.getInstance());
        toggletb.setOpaque(false);
        toggletb.setBorder(null);

        return toggletb;

      default :
        view = new ButtonView();

        if (cfg.actionType.intValue() == PushButton.CActionType.popup_menu) {
          view.setDrawArrow(true);
        }

        break;
    }

    if (cfg.actionRepeats.booleanValue()) {
      final int delay = SNumber.intValue(cfg.actionRepeats.spot_getAttribute("delay"));

      view.setAutoRepeats(delay);
    }

    if (!view.isPlatformButton()) {
      view.setFont(FontUtils.getDefaultFont());
      view.setForeground(ColorUtils.getForeground());
    }

    return view;
  }

  @Override
  public CheckBoxView getCheckBox(iWidget context, CheckBox cfg) {
    CheckBoxView v = new CheckBoxView(cfg.style.intValue() == CheckBox.CStyle.on_off_switch);

    if (cfg.style.intValue() == CheckBox.CStyle.tri_state) {
      v.setTriState(true);
    }

    v.setFont(FontUtils.getDefaultFont());
    v.setForeground(ColorUtils.getForeground());

    return v;
  }

  @Override
  public iCollapsible getCollapsible(iWidget context, CollapsibleInfo cfg) {
    CollapsiblePane pane = new CollapsiblePane();

    Utils.configureCollapsible(context, pane, cfg);

    return pane;
  }

  @Override
  public iPlatformTextEditor getDocumentPane(iWidget widget, DocumentPane cfg) {
    switch(cfg.style.getValue()) {
      case DocumentPane.CStyle.html_editor :
        if (Platform.isEmbedded()) {
          return new TextPaneEditor(new TextEditor(new ScrollPaneEx()));
        }

        return new HTMLTextEditor();

      default :
        return new TextPaneEditor(new TextEditor(new ScrollPaneEx()));
    }
  }

  @Override
  public LabelView getLabel(iWidget context, Label cfg) {
    LabelView view = new LabelView();

    return view;
  }

  @Override
  public LineView getLine(iWidget context, Line cfg) {
    return new LineView();
  }

  @Override
  public ListView getList(iWidget context, ListBox cfg) {
    ListView v = new ListView();

    return v;
  }

  public JMenuBar getMenuBar(iWidget context, MenuBar cfg) {
    String name = cfg.title.getValue();

    if (name == null) {
      name = iConstants.MENUBAR_NAME;
    }

    JMenuBar commandBar = new JMenuBarEx();

    commandBar.setName(name);

    return commandBar;
  }

  public Object getMenuBarExpander(JMenuBar mb) {
    com.appnativa.rare.ui.Component c = new com.appnativa.rare.ui.Component((JComponent) Box.createGlue());

    c.putClientProperty(iConstants.MENU_EXPANSION_NAME, Boolean.TRUE);

    return c;
  }

  public JComponent getMenuBarSeparator(JMenuBar mb) {
    return new SeparatorView(true);
  }

  public JComponent getMenuItem(iWidget context, Bean cfg) {
    iSPOTElement e = cfg.beanProperties.getValue();

    if (e instanceof MenuItem) {
      UIMenuItem m = MenuUtils.createMenuItem(context, (MenuItem) e);

      return (m == null)
             ? null
             : m.getMenuItem();
    }

    return null;
  }

  @Override
  public JTextField getPasswordTextField(iWidget context, PasswordField cfg) {
    PasswordTextFieldView view = new PasswordTextFieldView();

    if (cfg.echoChar.spot_hasValue()) {
      int n = cfg.echoChar.intValue();

      if ((n > 0) && (n < 65536)) {
        view.setEchoChar((char) n);
      }
    }

    return view;
  }

  @Override
  public iProgressBar getProgressBar(iWidget context, ProgressBar cfg) {
    if (cfg.indeterminate.booleanValue() && "true".equals(cfg.indeterminate.spot_getAttribute("useSpinner"))) {
      return new ProgressBarWrapper(PlatformHelper.createLabel(context.getContainerComponent()));
    }

    return new ProgressBarComponent(new ProgressBarView());
  }

  @Override
  public RadioButtonView getRadioButton(iWidget context, RadioButton cfg) {
    RadioButtonView v = new RadioButtonView();

    v.setFont(FontUtils.getDefaultFont());
    v.setForeground(ColorUtils.getForeground());

    return v;
  }

  @Override
  public JScrollPane getScrollPane(iWidget context, ScrollPane cfg) {
    return new ScrollPaneEx();
  }

  @Override
  public SliderView getSlider(iWidget context, Slider cfg) {
    SliderView v = new SliderView();

    v.setFont(FontUtils.getDefaultFont());
    v.setForeground(ColorUtils.getForeground());

    return v;
  }

  @Override
  public TextAreaView getTextArea(iWidget context, TextField cfg) {
    TextAreaView v = new TextAreaView();

    if (((TextArea) cfg).wordWrap.booleanValue()) {
      v.setWrapStyleWord(true);
      v.setLineWrap(true);
    }

    return v;
  }

  @Override
  public TextFieldView getTextField(iWidget context, TextField cfg) {
    TextFieldView v = new TextFieldView();

    v.setFont(FontUtils.getDefaultFont());

    return v;
  }

  @Override
  public AbstractButton getToolbarButton(iWidget context, PushButton cfg) {
    return getButton(context, cfg);
  }

  @Override
  public TreeView getTree(iWidget context, Tree cfg) {
    TreeView v = new TreeView();

    return v;
  }

  /**
   * A special version of the {@link javax.swing.text.MaskFormatter} for
   * {@link javax.swing.JFormattedTextField formatted text fields} that supports
   * the field being emptied/left blank.
   *
   * @author R.J. Lorimer from: http://www.javalobby.org/java/forums/t48652.html
   */
  public static class AllowBlankMaskFormatter extends MaskFormatter {
    private boolean allowBlankField = true;
    private String  blankRepresentation;

    /**
     * Constructs a new instance
     */
    public AllowBlankMaskFormatter() {
      super();
    }

    /**
     * Constructs a new instance
     *
     * @param mask
     *          {@inheritDoc}
     *
     * @throws ParseException
     *           {@inheritDoc}
     */
    public AllowBlankMaskFormatter(String mask) throws ParseException {
      super(mask);
    }

    /**
     * Override the stringToValue method to check the blank representation.
     *
     * @param value
     * @return
     * @throws java.text.ParseException
     */
    @Override
    public Object stringToValue(String value) throws ParseException {
      Object result;

      if (isAllowBlankField() && (blankRepresentation != null) && blankRepresentation.equals(value)) {
        // an empty field should have a 'null' value.
        result = null;
      } else {
        result = super.stringToValue(value);
      }

      return result;
    }

    /**
     * Sets whether a blank field is allowed; false otherwise
     *
     * @param allowBlankField
     *          true if a blank field is allowed; false otherwise
     */
    public void setAllowBlankField(boolean allowBlankField) {
      this.allowBlankField = allowBlankField;
    }

    /**
     * Sets the mask
     *
     * @param mask
     *          the mask
     * @throws java.text.ParseException
     */
    @Override
    public void setMask(String mask) throws ParseException {
      super.setMask(mask);
      updateBlankRepresentation();
    }

    /**
     * Update our blank representation whenever the mask is updated.
     *
     * @param placeholder
     */
    @Override
    public void setPlaceholderCharacter(char placeholder) {
      super.setPlaceholderCharacter(placeholder);
      updateBlankRepresentation();
    }

    /**
     * Returns if a blank field is allowed; false otherwise
     *
     * @return true if a blank field is allowed; false otherwise
     */
    public boolean isAllowBlankField() {
      return allowBlankField;
    }

    private void updateBlankRepresentation() {
      try {
        // calling valueToString on the parent class with a null attribute will
        // get the 'blank'
        // representation.
        blankRepresentation = valueToString(null);
      } catch(ParseException e) {
        blankRepresentation = null;
      }
    }
  }


  /**
   *
   *
   * @version 0.3, 2007-07-10
   * @author Don DeCoteau
   */
  public static class ToolTipMouseListener extends MouseAdapter implements HierarchyListener {
    protected Action hideAction;

    public ToolTipMouseListener() {
      JLabel   c        = new JLabel();
      InputMap inputMap = c.getInputMap(JComponent.WHEN_FOCUSED);

      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_CUT, 0), ActionHelper.getCopyAction());
      ToolTipManager.sharedInstance().registerComponent(c);

      ActionMap actionMap = c.getActionMap();

      hideAction = actionMap.get("hideTip");
      ToolTipManager.sharedInstance().unregisterComponent(c);
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
      if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
        Component c = e.getChanged();

        if (!c.isShowing()) {
          JToolTip t = (JToolTip) e.getComponent();

          unregister(t);
          t.getComponent().putClientProperty("Rare.Tooltip.closeTime", System.currentTimeMillis());
        }
      } else {    // prevent the mouseExited event of the component from hiding the
        // overlapping tooltip
        JToolTip t = (JToolTip) e.getComponent();

        t.getComponent().removeMouseListener(ToolTipManager.sharedInstance());
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
      JToolTip   t = (JToolTip) e.getComponent();
      JComponent c = t.getComponent();

      if (hideAction != null) {
        hideAction.actionPerformed(new ActionEvent(c, ActionEvent.ACTION_PERFORMED, "hideTip"));
      }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     * {@inheritDoc}
     *
     * @param e
     *          {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
      if (e.getComponent() instanceof JToolTip) {
        Component comp = ((JToolTip) e.getComponent()).getComponent();

        if (comp != null) {
          comp.removeMouseListener(ToolTipManager.sharedInstance());
          comp.addMouseListener(ToolTipManager.sharedInstance());

          Point p = e.getPoint();

          p = SwingUtilities.convertPoint(e.getComponent(), p, comp);
          e = new MouseEvent(comp, e.getID(), e.getWhen(), e.getModifiers() | e.getModifiersEx(), p.x, p.y,
                             e.getClickCount(), e.isPopupTrigger());
          comp.dispatchEvent(e);
          e = new MouseEvent(comp, MouseEvent.MOUSE_RELEASED, e.getWhen(), e.getModifiers() | e.getModifiersEx(), p.x,
                             p.y, e.getClickCount(), e.isPopupTrigger());
          comp.dispatchEvent(e);
          e = new MouseEvent(comp, MouseEvent.MOUSE_CLICKED, e.getWhen(), e.getModifiers() | e.getModifiersEx(), p.x,
                             p.y, e.getClickCount(), e.isPopupTrigger());
          comp.dispatchEvent(e);
        }
      }
    }

    public void register(JToolTip tooltip) {
      tooltip.addHierarchyListener(this);
      tooltip.addMouseListener(this);
    }

    public void unregister(JToolTip tooltip) {
      tooltip.removeMouseListener(this);
      tooltip.removeHierarchyListener(this);
      tooltip.getComponent().removeMouseListener(ToolTipManager.sharedInstance());
      tooltip.getComponent().addMouseListener(ToolTipManager.sharedInstance());
    }

    public static boolean isOkToShowTooltip(JComponent c) {
      Long l = (Long) c.getClientProperty("Rare.Tooltip.closeTime");

      if (l != null) {
        c.putClientProperty("Rare.Tooltip.closeTime", null);

        long dif = System.currentTimeMillis() - l.longValue();

        return dif > (ToolTipManager.sharedInstance().getReshowDelay() * 2);
      }

      return true;
    }
  }


  @Override
  public TableView getTable(iWidget context, Table cfg) {
    if (cfg instanceof TreeTable) {
      return new TreeTableView();
    }

    return new TableView();
  }
}
