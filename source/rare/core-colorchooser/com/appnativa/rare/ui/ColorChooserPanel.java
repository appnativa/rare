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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.iNavigatorPanel.PanelType;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;

public class ColorChooserPanel extends BorderPanel implements iActionable {
  protected boolean           showColorWheel   = !Platform.isTouchDevice();
  protected boolean           showPalette      = true;
  protected boolean           showPaletteFirst = true;
  protected iColorWheel       colorWheel;
  protected iWindow           dialog;
  protected ColorPalettePanel palettePanel;
  protected UIColor           selectedColor;
  protected boolean           showNoneButton;
  protected boolean           showOkButton;
  private ColorPalette        colorPalette;
  private String              dialogTitle;
  private boolean             inPopup;
  private boolean             useList;

  public ColorChooserPanel(iWidget context) {
    super(context);
    setBackground(ColorUtils.getBackground());
  }

  @Override
  public void addActionListener(iActionListener l) {
    getEventListenerList().add(iActionListener.class, l);
  }

  @Override
  public void dispose() {
    if (palettePanel != null) {
      palettePanel.dispose();
    }
    if (colorWheel != null) {
      colorWheel.dispose();
    }

    if (colorPalette != null) {
      colorPalette.dispose();
    }

    super.dispose();
    colorPalette = null;
    colorWheel   = null;
    palettePanel = null;
  }

  public void handleKeyPressed(KeyEvent e) {
    if (palettePanel != null) {
      UIColor sc = selectedColor;

      if (e.isDownArrowKeyPressed()) {
        palettePanel.selecteNextColor(true);
      } else if (e.isRightArrowKeyPressed()) {
        if (isShowing()) {
          palettePanel.selecteNextColor(false);
        }
      } else if (e.isUpArrowKeyPressed()) {
        palettePanel.selectePreviousColor(true);
      } else if (e.isLeftArrowKeyPressed()) {
        if (isShowing()) {
          palettePanel.selectePreviousColor(false);
        }
      }

      if (palettePanel.getSelectedColor() != sc) {
        setSelectedColor(palettePanel.getSelectedColor());
      }
    }
  }

  @Override
  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  public void showDialog(iPlatformComponent owner) {
    if (dialog != null) {
      if (dialog.getComponent().isShowing()) {
        return;
      }

      dialog.disposeOfWindow();
    }

    String title = dialogTitle;

    if (title == null) {
      title = Platform.getResourceAsString("Rare.runtime.text.colorChooser.chooseColor");
    }

    if (title == null) {
      title = "Choose Color";
    }

    iPlatformWindowManager wm = Platform.getAppContext().getWindowManager();
    iWindow                w  = wm.createDialog(owner.getWidget(), null, title, true);
    WidgetPaneViewer       wp = new WidgetPaneViewer(this);

    wm.setViewer(w.getTargetName(), owner.getWidget(), wp, null);
    dialog = w;
    w.addWindowListener(new iWindowListener() {
      @Override
      public void windowEvent(WindowEvent e) {
        if (e.getType() == WindowEvent.Type.Closed) {
          if (dialog != null) {
            dialog.disposeOfWindow();
            dialog = null;
          }
        }
      }
    });
    w.showWindow();
  }

  public void setColorPalette(ColorPalette palette) {
    this.colorPalette = palette;
  }

  public void setContent(iWidget context) {
    if (showColorWheel) {
      colorWheel = (iColorWheel) PlatformHelper.createColorWheel(context);
    }

    if (showPalette || (colorWheel == null)) {
      palettePanel = new ColorPalettePanel(context);
      palettePanel.setColorPalette((colorPalette == null)
                                   ? ColorPalette.getColorPalette40()
                                   : colorPalette);
      palettePanel.addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (listenerList != null) {
            selectedColor = palettePanel.getSelectedColor();

            if (!showOkButton) {
              Utils.fireActionEvent(listenerList, e);
            }
          }
        }
      });

      if (useList) {
        palettePanel.setUseList(true);
      }

      palettePanel.setInPopup(inPopup);
    }

    if (showPaletteFirst || (colorWheel == null)) {
      setCenterView(palettePanel);
    } else {
      setCenterView(colorWheel.getComponent());
    }

    iPlatformComponent bp = getButtonPannel();

    if (bp != null) {
      setBottomView(bp);
    }
  }

  public void setDialogTitle(String dialogTitle) {
    this.dialogTitle = dialogTitle;
  }

  public void setInPopup(boolean inPopup) {
    this.inPopup = inPopup;
    setFocusable(!inPopup);
  }

  public void setSelectedColor(UIColor selectedColor) {
    this.selectedColor = selectedColor;

    if (palettePanel != null) {
      palettePanel.setSelectedColor(selectedColor);
    }

    if (colorWheel != null) {
      colorWheel.setSelectedColor(selectedColor);
    }
  }

  public void setShowColorWheel(boolean showColorWheel) {
    this.showColorWheel = showColorWheel;
  }

  public void setShowNoneButton(boolean show) {
    showNoneButton = show;
  }

  public void setShowOKButton(boolean show) {
    showOkButton = show;
  }

  public void setShowPalette(boolean showPalette) {
    this.showPalette = showPalette;
  }

  public void setShowPaletteFirst(boolean showPaletteFirst) {
    this.showPaletteFirst = showPaletteFirst;
  }

  public void setUseList(boolean use) {
    useList = use;
  }

  public String getDialogTitle() {
    return dialogTitle;
  }

  public UIColor getSelectedColor() {
    return selectedColor;
  }

  public boolean isInPopup() {
    return inPopup;
  }

  public boolean isShowColorWheel() {
    return showColorWheel;
  }

  public boolean isShowPalette() {
    return showPalette;
  }

  public boolean isShowPaletteFirst() {
    return showPaletteFirst;
  }

  protected void noneButtonPressed() {
    setSelectedColor(null);
    Utils.fireActionEvent(listenerList, new ActionEvent(palettePanel));
  }

  protected void okButtonPressed() {
    Utils.fireActionEvent(listenerList, new ActionEvent(palettePanel));
  }

  protected iPlatformComponent getButtonPannel() {
    if (!showNoneButton &&!showOkButton && ((colorWheel == null) && (palettePanel == null))) {
      return null;
    }

    String      s;
    LinearPanel p = new LinearPanel(widget, true);

    p.setOpaque(false);

    iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.ColorChooser.buttonPanel.border");

    if (b == null) {
      b = new UIMatteBorder(ScreenUtils.PLATFORM_PIXELS_1, 0, 0, 0, UILineBorder.getDefaultLineColor());
    }

    p.setBorder(b);

    if ((colorWheel != null) && (palettePanel != null)) {
      NavigatorPanel np = new NavigatorPanel(widget, null);

      np.setPanelType(PanelType.TOGGLE);
      s = Platform.getResourceAsString("Rare.runtime.text.colorChooser.palette");
      np.addButton(s).addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          setCenterView(palettePanel);
        }
      });
      s = Platform.getResourceAsString("Rare.runtime.text.colorChooser.wheel");
      np.addButton(s).addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          setCenterView(colorWheel.getComponent());
        }
      });
      b = Platform.getUIDefaults().getBorder("Rare.ColorChooser.navigator.border");

      if (b == null) {
        b = new UILineBorder(UILineBorder.getDefaultLineColor(), ScreenUtils.PLATFORM_PIXELS_1,
                             ScreenUtils.PLATFORM_PIXELS_4);
        ((UILineBorder) b).setNoTop(true);
        np.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.CENTER);
        np.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.TOP);
      }

      setBottomView(np);
      np.setSelectedIndex(showPaletteFirst
                          ? 0
                          : 1);
      p.addComponent(np, "TOP:DEFAULT:NONE");

      if (showNoneButton || showOkButton) {
        p.addExpansionComponent();
      }
    }

    PushButtonWidget pb;
    boolean          grow = p.getComponentCount() == 0;

    if (showNoneButton) {
      s  = Platform.getResourceAsString("Rare.runtime.text.colorChooser.none");
      pb = AlertPanel.createButton(s, "Rare.ColorChooser.noneButton", new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          noneButtonPressed();
        }
      });

      if (Platform.isTouchDevice()) {
        pb.setMinimumSize(null, "2ln");
      }

      if (grow) {
        p.addComponent(pb.getContainerComponent(), "FILL:DEFAULT:GROW");
      } else {
        p.addComponent(pb.getContainerComponent(), "FILL:[20dlu,d]:NONE");
      }
    }

    if (showOkButton) {
      s  = Platform.getResourceAsString("Rare.text.ok");
      pb = AlertPanel.createButton(s, "Rare.ColorChooser.noneButton", new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          okButtonPressed();
        }
      });

      if (Platform.isTouchDevice()) {
        pb.setMinimumSize(null, "2ln");
      }

      if (grow) {
        p.addComponent(pb.getContainerComponent(), "FILL:DEFAULT:GROW");
      } else {
        p.addComponent(pb.getContainerComponent(), "FILL:[20dlu,d]:NONE");
      }
    }

    return p;
  }

  public static interface iColorWheel extends iActionable {
    void setLiveUpdate(boolean live);

    void setSelectedColor(UIColor color);

    void setShowAlphaSelector(boolean show);

    iPlatformComponent getComponent();

    UIColor getSelectedColor();

    void dispose();
  }


  public static class ColorButton extends XPActionComponent implements iActionListener {
    ColorIcon                   colorIcon = new ColorIcon(null);
    protected ColorChooserPanel panel;

    public ColorButton(iWidget widget) {
      super(PlatformHelper.createDateButtonView());
      this.widget = widget;
      setIcon(colorIcon);
      super.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      doClick();
    }

    @Override
    public void addActionListener(iActionListener l) {
      panel.addActionListener(l);
    }

    @Override
    public void dispose() {
      super.dispose();
      panel = null;
    }

    @Override
    public void doClick() {
      panel.showDialog(this);
    }

    public void setContent() {
      getPanel().setContent(widget);
    }

    public void setSelectedColor(UIColor color) {
      panel.setSelectedColor(color);
      colorIcon.setColor(color);
      repaint();
    }

    public ColorChooserPanel getPanel() {
      if (panel == null) {
        panel = new ColorChooserPanel(widget) {
          @Override
          public void setSelectedColor(UIColor selectedColor) {
            super.setSelectedColor(selectedColor);
            colorIcon.setColor(selectedColor);
            ColorButton.this.repaint();
          }
        };
      }

      return panel;
    }
  }


  public static class ColorChooserComboBox extends ComboBoxComponent {
    ColorChooserPanel panel;
    private boolean   showValueAsHex = true;
    ColorIcon         colorIcon      = new ColorIcon(null);

    public ColorChooserComboBox(iWidget widget) {
      super(widget);
      setEditable(false);
      restrictInput=false;
      autoFilter=false;
      this.widget = widget;
      colorIcon.setIconWidth((int) Math.ceil(ScreenUtils.PLATFORM_PIXELS_8 * 4));
      setEditorIcon(colorIcon);
    }

    @Override
    public void cancelPopup() {
      updateComboboxEditor();
      super.cancelPopup();
    }

    @Override
    public void dispose() {
      super.dispose();
      panel = null;
    }

    @Override
    public void showPopup() {
      if (showAsDialog) {
        panel.showDialog(this);
      } else {
        super.showPopup();
      }
    }

    public void setContent() {
      getPanel().setContent(widget);
      setPopupContent(getPanel());
    }

    public void setSelectedColor(UIColor color) {
      panel.setSelectedColor(color);
    }

    public void setShowValueAsHex(boolean showValueAsHex) {
      this.showValueAsHex = showValueAsHex;
    }

    public ColorChooserPanel getPanel() {
      if (panel == null) {
        panel = new ColorChooserPanel(widget) {
          @Override
          public void setSelectedColor(UIColor selectedColor) {
            super.setSelectedColor(selectedColor);
            updateComboboxEditor();
          }
        };
        panel.setInPopup(true);
      }

      panel.revalidate();

      return panel;
    }

    public boolean isShowValueAsHex() {
      return showValueAsHex;
    }

    @Override
    protected void handleKeyPressed(KeyEvent e) {
      super.handleKeyPressed(e);

      if (!e.isConsumed()) {
        panel.handleKeyPressed(e);
      }
    }

    @Override
    protected void keyboardActionPerformed(ActionEvent e) {
      UIColor c = null;
      String  s = getEditorValue();

      if (s != null) {
        s = s.trim();
      }

      if ((s != null) && (s.length() > 0)) {
        c = ColorUtils.getNamedColor(s);

        if (c == null) {
          c = ColorUtils.getColor(s);
        } else {
          c = new UIColorShade(c, s);
        }
      }

      panel.setSelectedColor(c);
      updateComboboxEditor();
      super.keyboardActionPerformed(e);
    }

    @Override
    protected void popupActionPerformed(ActionEvent e) {
      updateComboboxEditor();
      super.popupActionPerformed(e);
    }

    protected void updateComboboxEditor() {
      UIColor c = panel.getSelectedColor();
      String  s;

      if (c == null) {
        s = "";
      } else {
        s = showValueAsHex
            ? c.toString()
            : c.toString();
      }

      setEditorValue(s);
      editor.selectAll();
      colorIcon.setColor(c);
      repaint();
    }

    @Override
    protected iWidget getPopupWidget() {
      return null;
    }

    @Override
    protected void getProposedPopupBounds(UIRectangle r) {
      popupContent.revalidate();
      Utils.getProposedPopupBounds(r, this, popupContent.getPreferredSize(), 0, HorizontalAlign.RIGHT,
                                   getPopupBorder(), false);
    }
  }
}
