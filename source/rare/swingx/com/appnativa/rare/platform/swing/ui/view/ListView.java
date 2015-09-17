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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.plaf.BasicListExUI;
import com.appnativa.rare.platform.swing.ui.EmptyListSelectionModel;
import com.appnativa.rare.platform.swing.ui.SelectiveListSelectionModel;
import com.appnativa.rare.platform.swing.ui.aFlingMouseListener;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.PainterUtils.GripperIcon;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aListHandler;
import com.appnativa.rare.ui.dnd.RenderableDataItemTransferable;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListHandler.SelectionMode;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformListView;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iRenderingComponent;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.RendererContainer;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IntList;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class ListView extends ScrollPaneEx
        implements iPlatformListView, ListSelectionListener, MouseListener, MouseMotionListener, iDataModelListener,
                   FocusListener {
  protected static final int          ICON_GAP                = ScreenUtils.PLATFORM_PIXELS_4;
  protected static final Object       PROPERTY_CHECKMARK_ICON = "__RARE_PROPERTY_CHECKMARK_ICON__";
  protected final static int          PAD_SIZE                = ScreenUtils.PLATFORM_PIXELS_2;
  protected static int                INDICATOR_SLOP          = ScreenUtils.PLATFORM_PIXELS_4;
  int                                 editingRow              = -1;
  int                                 pressedRow              = -1;
  boolean                             dndDragEnabled;
  EditingSelectionModel               editingSelectionModel;
  ListRowContainer                    listRow;
  ListRowContainer                    listRowWithEditor;
  UIAction                            markAll;
  int                                 measuringMaxWidth;
  RendererContainer                   rendererContainer;
  protected int                       currentSelection = -1;
  protected int                       hilightIndex     = -1;
  protected int                       popupIndex       = -1;
  protected boolean                   selectable       = true;
  protected UIInsets                  rinsets          = new UIInsets();
  protected SelectionType             selectionType    = SelectionType.ROW_ON_BOTTOM;
  protected UIInsets                  paintInsets      = new UIInsets();
  EditingMode                         editingMode      = EditingMode.NONE;
  protected iActionListener           actionListener;
  protected boolean                   autoHilight;
  protected boolean                   autoSizeRows;
  protected iItemChangeListener       changeListener;
  protected SelectionChangeMaintainer changeManager;
  protected CheckListManager          checkListManager;
  protected int                       checkboxHeight;
  protected int                       checkboxWidth;
  protected boolean                   disableChangeEvent;
  protected boolean                   editingSwipingAllowed;
  protected int                       indicatorHeight;
  protected int                       indicatorWidth;
  protected boolean                   invisibleSelection;
  protected ListItemRenderer          itemRenderer;
  protected int                       leftOffset;
  protected boolean                   linkedSelection;
  protected Component                 listComponent;
  protected int                       rightOffset;
  protected int                       rowHeight;
  protected ListSelectionModel        selectionModel;
  protected boolean                   singleClickAction;
  private int                         lastEditedRow = -1;
  private int                         animateX;
  private ValueRangeAnimator          animator;
  private boolean                     autoEndEditing;
  private boolean                     centerRowEditorVertically;
  private boolean                     deletingAllowed;
  private GripperIcon                 draggableIcon;
  private boolean                     draggingAllowed;
  private UIAction[]                  editActions;
  private iFunctionCallback           editModeNotifier;
  private iToolBar                    editToolbar;
  private EditableGestureListener     editableGestureListener;
  private boolean                     editing;
  private iPlatformComponent          editingComponent;
  private boolean                     editingSelectionAllowed;
  private JListEx                     list;
  private iPlatformListDataModel      listModel;
  private Point                       mousePressedPoint;
  private long                        mousePressedTime;
  private boolean                     keepSelectionVisible;
  protected static Action             enterKeyAction = new AbstractAction("Rare.enterKeyAction") {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
      Object o = (e == null)
                 ? null
                 : e.getSource();

      if (o instanceof JListEx) {
        ListView lv = ((JListEx) o).getLisView();

        if (lv.isEditing()) {
          lv.stopEditing(true);

          return;
        }

        int n = lv.getSelectedIndex();

        if ((n != -1) && (lv.actionListener != null)) {
          ActionEvent ae = new ActionEvent(o, "enter");

          lv.actionListener.actionPerformed(ae);
        }
      }
    }
  };

  public ListView() {
    super();
    list = new JListEx();
    setViewportView(list);
    list.setSelectionModel(new SelectiveListSelectionModel(null));
    listComponent = new Component(list);
    list.addListSelectionListener(this);
    list.addMouseListener(this);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setBackground(ColorUtils.getListBackground());
    setForeground(ColorUtils.getListForeground());
    setRowHeight((int) Math.ceil(FontUtils.getDefaultLineHeight() * 1.25));
    setBorder(BorderUtils.EMPTY_BORDER);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    list.setMouseOverideListener(createOverrideListener());
    list.setListView(this);

    InputMap  im = list.getInputMap(JList.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    ActionMap am = list.getActionMap();

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterKeyAction.getValue(Action.NAME));
    am.put(enterKeyAction.getValue(Action.NAME), enterKeyAction);
    list.addFocusListener(this);
    getVerticalScrollBar().setFocusable(false);
    getHorizontalScrollBar().setFocusable(false);
  }

  public ListView(iPlatformListDataModel lm) {
    this();
    list.setModel(lm);
    listModel = lm;
  }

  @Override
  public void addSelectionIndex(int index) {
    disableChangeEvent = true;
    list.addSelectionInterval(index, index);
    disableChangeEvent = false;
  }

  protected void animateEditMode(final boolean in, final int distance, final boolean draggable) {
    if (in) {
      animator = ValueRangeAnimator.ofFloat(1f, 0f);
    } else {
      animator = ValueRangeAnimator.ofFloat(0f, 1f);
    }

    animator.setDuration(250);
    animator.addListener(new iAnimatorListener() {
      @Override
      public void animationEnded(iPlatformAnimator animation) {
        animator = null;

        if (!in) {
          stopEditingEx();
        } else if (draggable) {}

        animateX = 0;

        if (editModeNotifier != null) {
          editModeNotifier.finished(false, Platform.findWidgetForComponent(this));
        }

        repaint();
      }
      @Override
      public void animationStarted(iPlatformAnimator animation) {}
    });
    animator.addValueListener(new iAnimatorValueListener() {
      @Override
      public void valueChanged(iPlatformAnimator animator, float fv) {
        int x = (int) (fv * distance);

        animateX = x;
        repaint();
      }
    });
    animator.start();
  }

  protected void calculateOffset() {
    int left  = 0;
    int right = 0;

    if (checkboxWidth > 0) {
      if (selectionType == SelectionType.CHECKED_LEFT) {
        left = checkboxWidth + PAD_SIZE;
      } else {
        right = checkboxWidth + PAD_SIZE;
      }
    }

    updateRenderInsetsForCheckBox(left, right);
  }

  protected boolean checkForCellHotspot(int row, float x, float y, float width, float height) {
    if ((checkListManager != null) &&!linkedSelection && isOnCheckBox(x, y, width, height, getIndent(row))) {
      if (checkListManager.toggleRow(row)) {
        repaint();
      } else {
        repaintRow(row);
      }

      return true;
    }

    return false;
  }

  public void clearCheckedItems() {
    if (linkedSelection) {
      list.clearSelection();
    } else if (checkListManager != null) {
      checkListManager.clear();
    }
  }

  @Override
  public void clearPopupMenuIndex() {
    popupIndex = -1;
  }

  protected void clickCheck(MouseEvent e, boolean release) {
    if (editing ||!selectable || e.isPopupTrigger() ||!list.isEnabled()) {
      return;
    }

    if (singleClickAction) {
      if (!release || (e.getClickCount() > 1)) {
        return;
      }
    } else if (e.getClickCount() < 2) {
      return;
    }

    if (release &&!PlatformHelper.isMouseClick(mousePressedPoint, mousePressedTime, e)) {
      return;
    }

    int row = list.locationToIndex(e.getPoint());

    if (row == -1) {
      return;
    }

    RenderableDataItem item = getItemAt(row);

    if (!item.isEnabled() ||!item.isSelectable()) {
      return;
    }

    if (actionListener != null) {
      ActionEvent ae = new ActionEvent(list, (e.getClickCount() > 1)
              ? "dblClick"
              : "click");

      actionListener.actionPerformed(ae);
    }
  }

  @Override
  public void contentsChanged(Object source) {
    lastEditedRow = -1;

    if (editing) {
      if (autoEndEditing && ((editableGestureListener == null) || (editableGestureListener.reordingRow == -1))) {
        stopEditingEx();
      }
    } else {
      hideRowEditingComponentEx();
    }
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    contentsChanged(source);
  }

  protected CheckListManager createCheckListManager() {
    return new CheckListManager(false, -2);
  }

  protected MouseInputListener createOverrideListener() {
    return new MouseInputAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        pressedRow = -1;
        super.mouseDragged(e);
      }
      @Override
      public void mousePressed(MouseEvent e) {
        int row = list.locationToIndex(e.getPoint());

        pressedRow = row;

        if ((checkboxWidth > 0) &&!linkedSelection && list.isEnabled() &&!e.isPopupTrigger()) {
          Rectangle r = (row == -1)
                        ? null
                        : list.getCellBounds(row, row);

          if (r != null) {
            if (checkForCellHotspot(row, e.getX(), e.getY() - r.y, r.width, r.height)) {
              e.consume();
            }
          }
        }
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        pressedRow = -1;
        super.mouseReleased(e);
      }
    };
  }

  public void dispose() {
    actionListener = null;
    changeListener = null;
    listComponent  = null;
    list           = null;
    itemRenderer   = null;
    listModel      = null;
  }

  @Override
  public void doLayout() {
    super.doLayout();

    int w = list.getScrollableTracksViewportWidth()
            ? getViewport().getWidth()
            : 0;

    if ((w != measuringMaxWidth) && (list.getFixedCellHeight() == -1)) {
      measuringMaxWidth = w;

      if (list.getUI() instanceof BasicListExUI) {
        ((BasicListExUI) list.getUI()).updateLayoutState();
      }
    }
  }

  public void editModeChangeAllMarks(boolean mark) {
    listModel.editModeChangeAllMarks(mark);

    if (!mark) {
      editingSelectionModel.clearSelectionEx();
    } else {
      editingSelectionModel.selectAll();
    }

    repaint();
    updateActions();
  }

  protected void editModeDeleteMarkedItems() {
    aListViewer lv = (aListViewer) Component.fromView(this).getWidget();

    lv.removeAll(Arrays.asList(listModel.editModeGetMarkedItems()));
    listModel.editModeClearMarks();
    editingSelectionModel.clearSelectionEx();
  }

  @Override
  public void focusGained(FocusEvent e) {
    aListViewer        lv = (aListViewer) Platform.getWidgetForComponent(this);
    iPlatformComponent pc = lv.getContainerComponent();

    if (pc.isFocusPainted()) {
      pc.repaint();
    }

    if ((getSelectedIndex() == -1) && (getRowCount() > 0)) {
      if (lv.isHandleFirstFocusSelection()) {
        int n = aListHandler.getFirstSelectableIndex(listModel);

        if (n != -1) {
          setSelectedIndex(n);
        }
      }
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    aListViewer lv = (aListViewer) Platform.getWidgetForComponent(this);

    if ((lv == null) || lv.isDisposed() ||!lv.isShowing()) {
      return;
    }

    iPlatformComponent pc = lv.getContainerComponent();

    if (pc.isFocusPainted()) {
      pc.repaint();
    }

    int n = getSelectedIndex();

    if (n != -1) {
      if (lv.isChangeSelColorOnLostFocus()) {
        if (lv.getSelectedIndexCount() > 1) {
          int sels[] = lv.getSelectedIndexes();

          for (int i : sels) {
            lv.repaintRow(i);
          }
        } else {
          lv.repaintRow(n);
        }
      }
    }
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return list.getAlternatingRowColor();
  }

  public int getAnimateX() {
    return animateX;
  }

  public int getEditingRow() {
    return editingRow;
  }

  public iFunctionCallback getEditModeNotifier() {
    return editModeNotifier;
  }

  @Override
  public int getFirstVisibleIndex() {
    return list.getFirstVisibleIndex();
  }

  @Override
  public int getHilightedIndex() {
    return hilightIndex;
  }

  protected int getIndent(int row) {
    return 0;
  }

  public RenderableDataItem getItemAt(int index) {
    return listModel.get(index);
  }

  @Override
  public aListItemRenderer getItemRenderer() {
    return itemRenderer;
  }

  public int getLastEditedRow() {
    return lastEditedRow;
  }

  @Override
  public int getLastVisibleIndex() {
    return list.getLastVisibleIndex();
  }

  public JListEx getList() {
    return list;
  }

  @Override
  public iPlatformComponent getListComponent() {
    return listComponent;
  }

  @Override
  public int getPopupMenuIndex() {
    return popupIndex;
  }

  public int getPreferredWidth() {
    return getPreferredSize().width;
  }

  public int getRowAtPoint(int x, int y) {
    Point p = new Point(x, y);

    return list.locationToIndex(p);
  }

  public int getRowAtPoint(Point p) {
    return list.locationToIndex(p);
  }

  public Rectangle getRowBounds(int index) {
    return list.getCellBounds(index, index);
  }

  public Rectangle getRowBounds(int firstRow, int lastRow) {
    return list.getCellBounds(firstRow, lastRow);
  }

  public int getRowCount() {
    return (listModel == null)
           ? 0
           : listModel.size();
  }

  public iPlatformComponent getRowEditingComponent(int index) {
    editingRow    = index;
    lastEditedRow = index;
    list.clearSelection();

    ListSelectionModel sm = list.getSelectionModel();

    if (!(sm instanceof EditingSelectionModel)) {
      selectionModel = sm;

      if (editingSelectionModel == null) {
        editingSelectionModel = new EditingSelectionModel();
      }

      list.setSelectionModel(editingSelectionModel);
    }

    if (editingComponent == null) {
      centerRowEditorVertically = Platform.getUIDefaults().getBoolean("Rare.List.centerDeleteButton", false);

      WindowViewer w   = Platform.getWindowViewer();
      PushButton   cfg = (PushButton) Platform.getWindowViewer().createConfigurationObject("PushButton",
                           "Rare.List.deleteButton");

      if (cfg.value.getValue() == null) {
        cfg.value.setValue(Platform.getResourceAsString("Rare.action.delete"));
      }

      if (cfg.bgColor.getValue() == null) {
        if (centerRowEditorVertically) {
          cfg.bgColor.setValue("red,");
          cfg.addBorder("line").spot_setAttribute("cornerArc", "4");
          ;
          cfg.addBorder("empty").spot_setAttribute("insets", "6,6,6,6");
        } else {
          cfg.bgColor.setValue("red");
          cfg.addBorder("matte").spot_setAttribute("insets", "0,0,0,1");
          cfg.addBorder("empty").spot_setAttribute("insets", "6,6,6,6");
        }

        cfg.fgColor.setValue("white");
      }

      PushButtonWidget pb = PushButtonWidget.create(w, cfg);

      pb.addActionListener(new iActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          RenderableDataItemTransferable t  = new RenderableDataItemTransferable(listModel.get(editingRow));
          aListViewer                    lv = (aListViewer) Component.fromView(ListView.this).getWidget();

          hideRowEditingComponent(false);
          lv.removeData(t);
          updateActions();
        }
      });
      editingComponent = pb.getDataComponent();
    }

    return editingComponent;
  }

  public int getRowHeight() {
    return rowHeight;
  }

  @Override
  public int getSelectedIndex() {
    return list.getSelectedIndex();
  }

  public SelectionType getSelectionType() {
    return selectionType;
  }

  protected void handlePressedRowEditingComponent(int x, int y) {
    try {
      if (editingComponent instanceof ActionComponent) {
        ((ActionComponent) editingComponent).actionPerformed(new ActionEvent(this));

        return;
      }

      JComponent c  = editingComponent.getView();
      Rectangle  eb = listRowWithEditor.editorBounds;

      c.setBounds(eb);
      x -= eb.x;
      y -= eb.y;

      java.awt.Component cc = SwingUtilities.getDeepestComponentAt(c, x, y);

      if (cc != null) {
        iPlatformComponent pc = Platform.findPlatformComponent(cc);

        if (pc instanceof ActionComponent) {
          ((ActionComponent) pc).actionPerformed(new ActionEvent(this));

          return;
        }
      }
    } finally {
      hideRowEditingComponent(false);
    }
  }

  public boolean hasCheckedRows() {
    return (checkListManager != null) && (checkListManager.hasCheckedRows());
  }

  public void hideRowEditingComponent(boolean animate) {
    int row = editingRow;

    if ((selectionModel != null) && (list.getSelectionModel() != selectionModel)) {
      list.setSelectionModel(selectionModel);
    }

    if (row > -1) {
      if (listRowWithEditor != null) {
        listRowWithEditor.hideRowEditingComponent(animate);
      }

      if (!animate) {
        Rectangle r = list.getCellBounds(row, row);

        if (r != null) {
          list.repaint(r);
        }

        editingRow = -1;
      }
    }
  }

  protected void hideRowEditingComponentEx() {
    int row = editingRow;

    if (row > -1) {
      if ((selectionModel != null) && (list.getSelectionModel() != selectionModel)) {
        list.setSelectionModel(selectionModel);
      }

      if (listRowWithEditor != null) {
        listRowWithEditor.hideRowEditingComponent(false);
      }

      editingRow = -1;
    }
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    contentsChanged(source);
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    contentsChanged(source);
  }

  public boolean isAutoEndEditing() {
    return autoEndEditing;
  }

  @Override
  public boolean isAutoHilight() {
    return autoHilight;
  }

  protected boolean isDeleteButtonTouched(MouseEvent e) {
    Point p   = e.getPoint();
    int   row = getRowAtPoint(p);

    if ((row == -1) || (row != editingRow)) {
      return false;
    }

    Rectangle r  = listRowWithEditor.getDeleteBounds();
    Rectangle cr = list.getCellBounds(row, row);

    r.x += cr.x;
    r.y += cr.y;

    return r.contains(p.x, p.y);
  }

  @Override
  public boolean isEditing() {
    return editing;
  }

  public boolean isEditingSwipingAllowed() {
    return editingSwipingAllowed;
  }

  @Override
  public boolean isFocusOwner() {
    return list!=null && list.isFocusOwner();
  }

  @Override
  public boolean isKeepSelectionVisible() {
    return keepSelectionVisible;
  }

  public boolean isMultipleSelectionAllowed() {
    ListSelectionModel sm = list.getSelectionModel();

    switch(sm.getSelectionMode()) {
      case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION :
      case ListSelectionModel.SINGLE_INTERVAL_SELECTION :
        return true;

      default :
        return false;
    }
  }

  protected boolean isOnCheckBox(float x, float y, float width, float height, int indent) {
    float sy = (height - checkboxHeight) / 2;

    if ((y < sy - INDICATOR_SLOP) || (y > (sy + checkboxHeight + INDICATOR_SLOP))) {
      return false;
    }

    float sx;
    int   slop = INDICATOR_SLOP;

    if (selectionType == SelectionType.CHECKED_RIGHT) {
      sx = width - rightOffset - PAD_SIZE - checkboxWidth;
      ;
    } else {
      sx = leftOffset + PAD_SIZE + indent;
    }

    return (x >= sx - slop) && (x <= (sx + checkboxWidth + slop));
  }

  public boolean isRowChecked(int row) {
    return (checkListManager != null) && checkListManager.isRowChecked(row);
  }

  @Override
  public boolean isRowSizeFixed() {
    return list.getFixedCellHeight() > 0;
  }

  @Override
  public boolean isSelectable() {
    return selectable;
  }

  @Override
  public boolean isSingleClickAction() {
    return singleClickAction;
  }

  @Override
  public void makeSelectionVisible() {
    int index = getSelectedIndex();

    if (index > -1) {
      list.ensureIndexIsVisible(index);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    clickCheck(e, false);
  }

  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    int n = list.locationToIndex(e.getPoint());

    if (n != hilightIndex) {
      hilightIndex = n;
      list.repaint(list.getCellBounds(n, n));
    }
  }

  private boolean mousePopupCheck(MouseEvent e) {
    if (!e.isConsumed() && e.isPopupTrigger() && (list != null) && list.isEnabled()) {
      list.requestFocus();

      int row = list.locationToIndex(e.getPoint());

      popupIndex = row;

      if ((row == -1) || list.isSelectedIndex(row)) {
        return true;
      }

      list.getSelectionModel().setSelectionInterval(row, row);

      return true;
    }

    return false;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (!e.isPopupTrigger() && list.isEnabled()) {
      mousePressedPoint = e.getLocationOnScreen();
      mousePressedTime  = e.getWhen();
    }

    mousePopupCheck(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mousePopupCheck(e);
    clickCheck(e, true);
  }

  @Override
  public iPlatformRenderingComponent prepareRendererForReuse(iPlatformRenderingComponent rc, int row, int col) {
    if ((checkListManager != null) && (rc instanceof UIListLabelRenderer)) {
      ListItemLabelRenderer view = (ListItemLabelRenderer) ((UIListLabelRenderer) rc).getView();

      view.checkIcon = checkListManager.getRowIcon(row, listModel.get(row));
    }

    if (autoSizeRows) {
      rc.setWordWrap(true);
    }

    if (rendererContainer == null) {
      rc.getComponent().getView().putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, measuringMaxWidth);
      rc.prepareForReuse(row, col);

      return rc;
    }

    ListRowContainer lc = (row == editingRow)
                          ? listRowWithEditor
                          : listRow;
    iPlatformIcon    ic = null;

    if (draggingAllowed) {
      RenderableDataItem item = listModel.get(row);

      if (item.isDraggingAllowed()) {
        ic = draggableIcon;
      }
    }

    lc.prepareForReuse(row, editing
                            ? editingSelectionModel.isChecked(row)
                            : false, animateX, rc.getComponent().getView(), ic);
    rendererContainer.setView(lc);
    rendererContainer.setRenderingComponent(rc);
    lc.measuringMaxWidth = measuringMaxWidth;

    return rendererContainer;
  }

  public void repaintRow(int row) {
    if (row != -1) {
      Rectangle r = list.getCellBounds(row, row);

      if (r != null) {
        list.repaint(r);
      }
    }
  }

  public void repaintRow(RenderableDataItem item) {
    int row = (item == null)
              ? -1
              : listModel.indexOf(item);

    if (row > -1) {
      repaintRow(row);
    }
  }

  public void repaintRows(int firstRow, int lastRow) {
    Rectangle r = list.getCellBounds(firstRow, lastRow);

    if (r != null) {
      list.repaint(r);
    }
  }

  @Override
  public void scrollRowToBottom(int row) {
    Rectangle r = list.getCellBounds(row, row);

    if (r != null) {
      JViewport vp = getViewport();
      Point     p  = vp.getViewPosition();
      int       y  = r.y + vp.getExtentSize().height - r.height;

      if (y > vp.getViewSize().height) {
        y = vp.getViewSize().height - vp.getExtentSize().height;
      }

      if (p.y != y) {
        p.y = y;
        vp.setViewPosition(p);
      }
    }
  }

  @Override
  public void scrollRowToTop(int row) {
    Rectangle r = list.getCellBounds(row, row);

    if (r != null) {
      JViewport vp = getViewport();
      Point     p  = vp.getViewPosition();

      if (vp.getExtentSize().height - r.y > 0) {
        p.y = r.y;
        vp.setViewPosition(p);
      }
    }
  }

  @Override
  public void scrollRowToVisible(int index) {
    list.ensureIndexIsVisible(index);
  }

  public void selectAll() {
    int len = listModel.size();

    if (len > 0) {
      disableChangeEvent = true;

      ListSelectionModel sm = list.getSelectionModel();

      switch(sm.getSelectionMode()) {
        case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION :
        case ListSelectionModel.SINGLE_INTERVAL_SELECTION :
          sm.clearSelection();
          sm.addSelectionInterval(0, len - 1);

          break;

        default :
          break;
      }

      disableChangeEvent = false;
    }
  }

  @Override
  public void setActionListener(iActionListener l) {
    actionListener = l;
  }

  @Override
  public void setAlternatingRowColor(UIColor color) {
    list.setAlternatingRowColor(color);
  }

  public void setAutoEndEditing(boolean autoEndEditing) {
    this.autoEndEditing = autoEndEditing;
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    if (this.autoHilight != autoHilight) {
      this.autoHilight = autoHilight;
      hilightIndex     = -1;

      if (autoHilight) {
        this.addMouseMotionListener(this);
      } else {
        this.removeMouseMotionListener(this);
      }
    }
  }

  public void setAutoSizeRows(boolean autoSize) {
    autoSizeRows = autoSize;

    if (autoSize) {
      list.setFixedCellHeight(-1);
    }
  }

  @Override
  public void setBackground(Color bg) {
    if (list != null) {
      list.setBackground(bg);
    }

    super.setBackground(bg);
  }

  @Override
  public void setBorder(Border border) {
    super.setBorder(border);

    if (border != null) {
      Insets in = border.getBorderInsets(this);

      leftOffset  = in.left;
      rightOffset = in.right;
    } else {
      leftOffset  = 0;
      rightOffset = 0;
    }
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);

    if (keepSelectionVisible) {
      int n = getSelectedIndex();

      if (n != -1) {
        Rectangle r = list.getCellBounds(n, n);

        if ((r == null) ||!getViewport().getViewRect().contains(r)) {
          makeSelectionVisible();
        }
      }
    }
  }

  public void setCheckedRows(int[] indices) {
    if (checkListManager != null) {
      checkListManager.setCheckedRows(indices);
      list.repaint();
    }
  }

  @Override
  public void setDividerLine(UIColor color, UIStroke stroke) {
    Stroke s = (stroke == null)
               ? SwingHelper.SOLID_STROKE
               : SwingHelper.getStroke(stroke);

    list.setDividerLineColor(color);
    list.setDividerStroke(s);
  }

  @Override
  public void setEditingMode(EditingMode mode) {
    if (mode == null) {
      mode = EditingMode.NONE;
    }

    editingMode = mode;

    aListViewer lv = (aListViewer) Component.fromView(this).getWidget();

    draggingAllowed = (mode == EditingMode.REORDERING) || (mode == EditingMode.REORDERING_AND_SELECTION)
                      || (mode == EditingMode.REORDERING_AND_DELETEING);
    deletingAllowed         = lv.canDelete();
    editingSelectionAllowed = (mode == EditingMode.SELECTION) || (mode == EditingMode.REORDERING_AND_SELECTION);

    if ((rendererContainer == null) && ((mode != EditingMode.NONE) || deletingAllowed)) {
      listRow           = new ListRowContainer(this);
      rendererContainer = new RendererContainer(this, null);

      if (draggingAllowed) {
        draggableIcon = new GripperIcon(false, lv.getDataComponent().getForeground());
        draggableIcon.setSize(ScreenUtils.platformPixels(16), ScreenUtils.platformPixels(16));
      }

      AbstractAction escapeAction = new AbstractAction() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent ae) {
          if (editing) {
            if (editableGestureListener.reordingRow != -1) {
              editableGestureListener.stopReordering();
            } else {
              stopEditing(true);
            }
          } else {
            hideRowEditingComponent(true);
          }
        }
      };

      getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
          KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "ESCAPE_KEY");
      getActionMap().put("ESCAPE_KEY", escapeAction);
    }

    list.setMouseOverideListener(editableGestureListener = new EditableGestureListener());
  }

  public void setEditingSwipingAllowed(boolean editingSwipingAllowed) {
    this.editingSwipingAllowed = editingSwipingAllowed;
  }

  public void setEditingToolbar(iToolBar tb) {
    editToolbar = tb;

    if (tb != null) {
      tb.getComponent().setVisible(false);
    }
  }

  public void setEditModeNotifier(iFunctionCallback editModeNotifier) {
    this.editModeNotifier = editModeNotifier;
  }

  @Override
  public void setForeground(Color fg) {
    super.setForeground(fg);

    if (list != null) {
      list.setForeground(fg);
    }
  }

  public void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    checkboxHeight = checked.getIconHeight();
    checkboxWidth  = checked.getIconWidth();
    checkboxHeight = Math.max(checkboxHeight, unchecked.getIconHeight());
    checkboxWidth  = Math.max(checkboxWidth, unchecked.getIconWidth());

    if (checkListManager == null) {
      checkListManager = createCheckListManager();

      if (listModel != null) {
        checkListManager.setListModel(listModel);
      }
    }

    checkListManager.setIcons(checked, unchecked, indeterminate);
    calculateOffset();
  }

  public void setItemRenderer(ListItemRenderer lr) {
    itemRenderer = lr;
    lr.setRenderingComponent(new UIListLabelRenderer());
    lr.setSelectionPainted(!invisibleSelection);
    list.setCellRenderer(lr);
    rinsets.set(lr.getInsets());
    calculateOffset();
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    this.keepSelectionVisible = keepSelectionVisible;
  }

  protected void setLinkedSelectionChecked(int row, boolean checked) {
    setRowChecked(row, checked);
  }

  public void setLinkSelection(boolean linked) {
    linkedSelection = linked;

    if (linked) {
      list.removeListSelectionListener(this);
      list.addListSelectionListener(this);
    }
  }

  public void setListModel(iPlatformListDataModel listModel) {
    list.setModel(listModel);
    lastEditedRow = -1;

    if (this.listModel != null) {
      this.listModel.removeDataModelListener(this);
    }

    this.listModel = listModel;

    if (this.listModel != null) {
      this.listModel.addDataModelListener(this);
    }

    if (checkListManager != null) {
      checkListManager.setListModel(listModel);
    }
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    list.setMinimumVisibleRowCount(rows);
  }

  public void setRowChecked(int row, boolean checked) {
    if (checkListManager != null) {
      if (checkListManager.isRowChecked(row) != checked) {
        toggleCheckedState(row);
      }
    }
  }

  public void setRowEditingComponent(iPlatformComponent c, boolean centerVertically) {
    editingComponent          = c;
    centerRowEditorVertically = centerVertically;

    if (c != null) {
      if (rendererContainer == null) {
        listRow           = new ListRowContainer(this);
        rendererContainer = new RendererContainer(this, null);
      }

      if (editableGestureListener == null) {
        list.setMouseOverideListener(editableGestureListener = new EditableGestureListener());
      }
    }
  }

  @Override
  public void setRowHeight(int height) {
    rowHeight = height;
    list.setMinRowHeight(height);

    if (!autoSizeRows) {
      list.setFixedCellHeight(height);
    }
  }

  @Override
  public void setSelectable(boolean selectable) {
    this.selectable = selectable;

    if (selectable) {
      if (selectionModel != null) {
        list.setSelectionModel(selectionModel);
      }
    } else {
      selectionModel = list.getSelectionModel();
      list.setSelectionModel(EmptyListSelectionModel.getSharableInstance());
    }
  }

  public void setSelectedIndex(int index) {
    disableChangeEvent = true;
    list.setSelectedIndex(index);
    disableChangeEvent = false;
  }

  public void setSelectedIndices(int[] indices) {
    disableChangeEvent = true;
    list.setSelectedIndices(indices);
    disableChangeEvent = false;
  }

  @Override
  public void setSelectionChangeListener(iItemChangeListener l) {
    list.removeListSelectionListener(this);
    list.addListSelectionListener(this);
    changeListener = l;
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    if (itemRenderer != null) {
      itemRenderer.setSelectionPainted(true);
    }

    if (list.getSelectionModel() instanceof EmptyListSelectionModel) {
      list.setSelectionModel(new DefaultListSelectionModel());
    }

    switch(selectionMode) {
      case BLOCK :
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        break;

      case MULTIPLE :
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        break;

      case INVISIBLE :
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (itemRenderer != null) {
          itemRenderer.setSelectionPainted(false);
        }

        break;

      case NONE :
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectionModel(new EmptyListSelectionModel());

        break;

      default :
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
  }

  public void setSelectionType(SelectionType type) {
    this.selectionType = type;
  }

  @Override
  public void setShowDivider(boolean show) {
    if (show) {
      if (list.getDividerLineColor() == null) {
        setDividerLine(ColorUtils.getListDividerColor(), UIStroke.SOLID_STROKE);
      }
    } else {
      setDividerLine(null, null);
    }
  }

  public void setShowLastDivider(boolean show) {
    list.setShowLastDivider(show);
  }

  @Override
  public void setSingleClickAction(boolean singleClickAction) {
    this.singleClickAction = singleClickAction;
  }

  protected void setTreeInfoForRow(iRenderingComponent rc, iPlatformIcon indicator, int indent) {
    if (rc instanceof UIListLabelRenderer) {
      ListItemLabelRenderer view = (ListItemLabelRenderer) ((UIListLabelRenderer) rc).getView();

      view.indicatorIcon = indicator;
      view.indent        = indent;
    }
  }

  @Override
  public void setVisibleRowCount(int rows) {
    list.setVisibleRowCount(rows);
  }

  public void startEditing(UIAction[] actions, boolean animate, boolean draggable) {
    if ((editingMode == EditingMode.NONE) || (getRowCount() == 0)) {
      return;
    }

    if (!this.editing) {
      this.editing = true;
      listRow.setEditing(editing);
      selectionModel = list.getSelectionModel();
      list.clearSelection();
      listModel.setEditing(true);

      if (editingSelectionModel == null) {
        editingSelectionModel = new EditingSelectionModel();
      }

      list.setSelectionModel(editingSelectionModel);

      if (listRowWithEditor != null) {
        listRowWithEditor.setEditing(false);
      }

      if (draggingAllowed) {
        dndDragEnabled = list.getDragEnabled();
        list.setDragEnabled(false);
      }

      if (actions == null) {
        if (deletingAllowed && editingSelectionAllowed) {
          actions    = new UIAction[2];
          actions[0] = new UIAction("Rare.action.markAll", Platform.getResourceAsString("Rare.action.markAll"), null);
          actions[1] = new UIAction("Rare.action.delete", Platform.getResourceAsString("Rare.action.delete"), null);
        } else if (deletingAllowed) {
          actions    = new UIAction[1];
          actions[0] = new UIAction("Rare.action.delete", Platform.getResourceAsString("Rare.action.delete"), null);
        } else if (editingSelectionAllowed) {
          actions    = new UIAction[1];
          actions[0] = new UIAction("Rare.action.markAll", Platform.getResourceAsString("Rare.action.markAll"), null);
        }
      }

      if ((actions != null) && (actions.length > 0) && (editToolbar != null)) {
        this.editActions = actions;

        iToolBar tb       = editToolbar;
        boolean  isDelete = false;

        for (UIAction a : actions) {
          if ("Rare.action.delete".equalsIgnoreCase(a.getActionName())) {
            a.setActionListener(new iActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                editModeDeleteMarkedItems();
              }
            });
            a.setEnabledOnSelectionOnly(true);
            a.setEnabled(false);
            isDelete = true;
          } else if ("Rare.action.markAll".equalsIgnoreCase(a.getActionName())) {
            a.setActionListener(new iActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                editModeChangeAllMarks(listModel.editModeGetMarkCount() < listModel.size());
              }
            });
            markAll = a;
          }

          iWidget pb = tb.add(a);

          if (isDelete) {
            ((ActionComponent) pb.getDataComponent()).setPainterHolder(PainterUtils.createRedHyperlinkPainterHolder());
          }

          isDelete = false;
        }

        tb.getComponent().setVisible(true);
      }

      int distance = listRow.getContentViewX();

      if (animate && (distance > 0)) {
        animateX = -distance;
        animateEditMode(true, -distance, draggable);
      } else {
        if (editModeNotifier != null) {
          editModeNotifier.finished(false, Platform.findWidgetForComponent(this));
        }

        repaint();
      }
    }
  }

  public void stopEditing(boolean animate) {
    if (this.editing) {
      int distance = listRow.getContentViewX();

      if (animate && (distance > 0)) {
        animateEditMode(false, -distance, false);
      } else {
        stopEditingEx();
      }
    }
  }

  protected void stopEditingEx() {
    this.editing     = false;
    this.editActions = null;
    animateX         = 0;
    listRow.setEditing(false);

    if (listRowWithEditor != null) {
      listRowWithEditor.setEditing(false);
    }

    editingSelectionModel.clearSelectionEx();
    list.setSelectionModel(selectionModel);

    if (editToolbar != null) {
      editToolbar.getComponent().setVisible(false);
      editToolbar.removeAllWidgets();
    }

    list.setDragEnabled(dndDragEnabled);
    listModel.editModeClearMarks();

    if (editModeNotifier != null) {
      editModeNotifier.finished(true, Platform.findWidgetForComponent(this));
    }

    repaint();
  }

  @Override
  public void structureChanged(Object source) {
    contentsChanged(source);
  }

  protected void toggleCheckedState(int row) {
    checkListManager.toggleRow(row);
    repaintRow(row);
  }

  protected void updateActions() {
    if (this.editActions != null) {
      int     mc        = listModel.editModeGetMarkCount();
      int     size      = listModel.size();
      boolean hasMarks  = mc > 0;
      boolean allMarked = (mc == size) && (mc != 0);

      if (markAll != null) {
        markAll.setActionText(Platform.getResourceAsString(allMarked
                ? "Rare.action.unmarkAll"
                : "Rare.action.markAll"));
      }

      for (UIAction a : editActions) {
        a.setEnabled(size > 0);

        if (a.isEnabledOnSelectionOnly()) {
          a.setEnabled(hasMarks);
        }
      }
    }
  }

  protected void updateRenderInsetsForCheckBox(int left, int right) {
    if (itemRenderer != null) {
      UIInsets in = itemRenderer.getInsets();

      in.set(rinsets);

      if (indicatorWidth > 0) {
        left += indicatorWidth + ICON_GAP;
      }

      in.left  += left;
      in.right += right;
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting() && (changeListener != null) &&!disableChangeEvent) {
      Object  oldValue = null;
      Object  newValue = null;
      boolean multiple = isMultipleSelectionAllowed();

      if (multiple) {
        if (changeManager == null) {
          changeManager = new SelectionChangeMaintainer();
        }

        changeManager.selectionChanged(e.getFirstIndex(), e.getLastIndex());
        newValue = changeManager.newSelections;
        oldValue = changeManager.oldSelections;
      } else {
        oldValue = currentSelection;
        newValue = e.getFirstIndex();
      }

      ItemChangeEvent ie = new ItemChangeEvent(this, oldValue, newValue);

      changeListener.itemChanged(ie);

      if (multiple) {
        changeManager.makeNewOld();
      } else {
        currentSelection = e.getFirstIndex();
      }
    }

    if ((checkListManager != null) && linkedSelection &&!e.getValueIsAdjusting()) {
      ListSelectionModel sm    = list.getSelectionModel();
      int                first = e.getFirstIndex();
      int                last  = e.getLastIndex();

      for (int i = first; i <= last; i++) {
        setLinkedSelectionChecked(i, sm.isSelectedIndex(i));
      }
    }
  }

  public static void fireActionEvent(EventListenerList listenerList, ActionEvent ae) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iActionListener.class) {
        ((iActionListener) listeners[i + 1]).actionPerformed(ae);
      }
    }
  }

  public static void fireChangeEvent(EventListenerList listenerList, ChangeEvent e) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iChangeListener.class) {
        ((iChangeListener) listeners[i + 1]).stateChanged(e);
      }
    }
  }

  class EditableGestureListener extends aFlingMouseListener {
    int             reordingRow = -1;
    int             currentRow;
    int             dragThreshold;
    int             reordingMouseY;
    int             reordingRowY;
    BasicListExUI   ui;
    private boolean pressConsumed;
    private Point   pressPoint;

    public EditableGestureListener() {
      if (list.getUI() instanceof BasicListExUI) {
        ui            = (BasicListExUI) list.getUI();
        dragThreshold = java.awt.dnd.DragSource.getDragThreshold();
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      super.mouseDragged(e);

      if (draggingAllowed && editing && ((pressedRow != -1) || (reordingRow != -1)) && (ui != null)) {
        e.consume();

        if ((reordingRow != -1) || (Math.abs(e.getY() - pressPoint.y) > dragThreshold)) {
          Rectangle r;

          if (reordingRow == -1) {
            reordingRow    = pressedRow;
            r              = list.getCellBounds(pressedRow, pressedRow);
            pressedRow     = -1;
            reordingMouseY = pressPoint.y;

            int y      = pressPoint.y;
            int height = getRowHeight();

            if (r != null) {
              height       = r.height;
              y            = r.y + (e.getY() - y);
              reordingRowY = r.y;
            } else {
              reordingRowY = y;
            }

            currentRow = reordingRow;
            ui.setReordingInfo(reordingRow, y, height);
          } else {
            int y = reordingRowY + (e.getY() - pressPoint.y);

            ui.setReordingY(y);

            int row = list.locationToIndex(e.getPoint());

            if ((row != -1) && (row != currentRow)) {
              currentRow = row;
              r          = list.getCellBounds(row, row);

              if (r != null) {
                list.scrollRectToVisible(r);
              }
            }
          }

          repaint();
        }
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {
      pressPoint    = e.getPoint();
      reordingRow   = -1;
      pressConsumed = false;

      if (editingRow != -1) {
        if (isDeleteButtonTouched(e) && (editingRow < getRowCount())) {
          handlePressedRowEditingComponent(e.getX(), e.getY());
        } else {
          hideRowEditingComponent(true);
        }

        pressConsumed = true;
        e.consume();

        return;
      }

      pressedRow = list.locationToIndex(e.getPoint());
      super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      pressedRow = -1;

      if ((ui != null) && (reordingRow != -1)) {
        int row = list.locationToIndex(e.getPoint());

        ui.setReordingInfo(-1, 0, 0);

        if (row != reordingRow) {
          aListViewer lv = (aListViewer) Component.fromView(ListView.this).getWidget();

          editingSelectionModel.clearSelectionEx();
          lv.move(reordingRow, row);
        }

        reordingRow = -1;
        repaint();
      }

      if (pressConsumed) {
        e.consume();
      } else {
        super.mouseReleased(e);
      }
    }

    @Override
    public void onFling(Object view, MouseEvent e1, MouseEvent e2, float velocityX, float velocityY) {
      if ((deletingAllowed || (editingComponent != null)) && (reordingRow == -1)
          && (!editing || editingSwipingAllowed)) {
        int row = list.locationToIndex(e1.getPoint());

        if ((row != -1) && (editingRow == -1) && (e1.getX() > e2.getX())) {
          if (listRowWithEditor == null) {
            listRowWithEditor = new ListRowContainer(ListView.this);
          }

          Rectangle r = list.getCellBounds(row, row);

          if (r != null) {
            listRowWithEditor.setBounds(r);
          }

          listRowWithEditor.showRowEditingComponent(getRowEditingComponent(row), true, centerRowEditorVertically);
        }
      }
    }

    public void stopReordering() {
      if ((ui != null) && (reordingRow != -1)) {
        pressedRow  = -1;
        reordingRow = -1;
        ui.setReordingInfo(-1, 0, 0);
        repaint();
      }
    }
  }


  class EditingSelectionModel extends DefaultListSelectionModel {
    DefaultListSelectionModel sm = new DefaultListSelectionModel();

    EditingSelectionModel() {
      setSelectionMode(SINGLE_SELECTION);
      sm.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
    }

    @Override
    public void addListSelectionListener(ListSelectionListener l) {}

    @Override
    public void addSelectionInterval(int index0, int index1) {
      if (editing) {
        if (pressedRow != index0) {
          return;
        }

        for (int i = index0; i <= index1; i++) {
          if (sm.isSelectedIndex(i)) {
            sm.removeSelectionInterval(i, i);
            listModel.editModeChangeMark(i, false);
          } else {
            listModel.editModeChangeMark(i, true);
            sm.addSelectionInterval(i, i);
          }
        }

        updateActions();
        repaint();
      }
    }

    @Override
    public void clearSelection() {}

    public void clearSelectionEx() {
      sm.clearSelection();

      if (editing) {
        updateActions();
      }
    }

    public boolean isChecked(int row) {
      return sm.isSelectedIndex(row);
    }

    @Override
    public void removeSelectionInterval(int index0, int index1) {}

    public void selectAll() {
      if (editing) {
        sm.addSelectionInterval(0, listModel.getSize() - 1);
        repaint();
      }
    }

    @Override
    public void setAnchorSelectionIndex(int anchorIndex) {}

    @Override
    public void setLeadSelectionIndex(int leadIndex) {}

    @Override
    public void setSelectionInterval(int index0, int index1) {
      addSelectionInterval(index0, index1);
    }
  }


  class ListItemLabelRenderer extends LabelRenderer {
    Insets                  insets = new Insets(0, 0, 0, 0);
    protected iPlatformIcon checkIcon;
    protected int           indent;
    protected iPlatformIcon indicatorIcon;

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if ((checkboxWidth > 0) || (indicatorWidth > 0)) {
        int x = PAD_SIZE + leftOffset + indent;
        ;

        if (indicatorIcon != null) {
          int y = (getHeight() - indicatorHeight) / 2;

          indicatorIcon.paintIcon(this, g, x, y);
        }

        if (indicatorWidth > 0) {
          x += indicatorWidth + ICON_GAP;
        }

        if (checkboxWidth > 0) {
          if (selectionType == SelectionType.CHECKED_LEFT) {
            int y = (getHeight() - checkboxHeight) / 2;

            checkIcon.paintIcon(this, g, x, y);
          } else {
            int y = (getHeight() - checkboxHeight) / 2;

            x = getWidth() - rightOffset - PAD_SIZE - checkboxWidth;
            checkIcon.paintIcon(this, g, x, y);
          }
        }
      }
    }
  }


  public static class SelectionChangeMaintainer {
    IntList newSelections;
    IntList oldSelections;

    public void makeNewOld() {
      if (oldSelections == null) {
        oldSelections = new IntList();
      } else {
        oldSelections.clear();
      }

      oldSelections.addAll(newSelections);
    }

    public void selectionChanged(int first, int last) {
      if (newSelections == null) {
        newSelections = new IntList();
      } else {
        newSelections.clear();
      }

      for (int i = first; i <= last; i++) {
        newSelections.add(i);
      }
    }
  }


  class UIListLabelRenderer extends UILabelRenderer {
    public UIListLabelRenderer() {
      super(new ListItemLabelRenderer());
    }

    public void setIndent(int indent) {
      ((ListItemLabelRenderer) view).indent = indent;
    }

    @Override
    public void setMargin(UIInsets insets) {
      float left = insets.left;

      insets.left += ((ListItemLabelRenderer) view).indent;
      super.setMargin(insets);
      insets.left = left;
    }
  }


  @Override
  public iScrollerSupport getScrollerSupport() {
    return this;
  }
}
