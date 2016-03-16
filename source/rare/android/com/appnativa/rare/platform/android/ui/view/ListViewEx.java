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

package com.appnativa.rare.platform.android.ui.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import android.annotation.SuppressLint;

import android.content.Context;

import android.database.DataSetObserver;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;

import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.MainActivity;
import com.appnativa.rare.platform.android.MainActivity.iBackPressListener;
import com.appnativa.rare.platform.android.iActivity;
import com.appnativa.rare.platform.android.iContextMenuInfoHandler;
import com.appnativa.rare.platform.android.ui.ListRowContainer;
import com.appnativa.rare.platform.android.ui.ListSynchronizer;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.aDataItemListModelEx;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.iFlingSupport;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.dnd.DnDConstants;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.RenderableDataItemTransferable;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.iEditableListHandler;
import com.appnativa.rare.ui.iListHandler.SelectionMode;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.table.TableHeader;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.ui.table.aTableAdapter;
import com.appnativa.rare.ui.table.aTableAdapter.TableRow;
import com.appnativa.rare.viewer.aListViewer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IntList;

import java.util.Arrays;

/**
 *
 * @author Don DeCoteau
 */
public class ListViewEx extends ListView
        implements iPainterSupport, iComponentView, OnItemClickListener, iFlingSupport, iContextMenuInfoHandler,
                   OnScrollListener, iScrollerSupport, RecyclerListener, iEditableListHandler {
  private final static int                    DELAY          = ViewConfiguration.getDoubleTapTimeout();
  protected static final int                  PAD_SIZE       = aDataItemListModelEx.PAD_SIZE;
  protected static int                        INDICATOR_SLOP = ScreenUtils.platformPixels(Platform.isTouchDevice()
          ? 24
          : 5);
  protected static final int                  ICON_GAP       = aDataItemListModelEx.ICON_GAP;
  private static int[]                        EMPTY_ARRAY    = new int[0];
  iBackPressListener                          backPressListener;
  UIAction                                    markAll;
  protected int                               editingRow                = -1;
  protected int                               popupMenuIndex            = -1;
  protected boolean                           extendBackgroundRendering = true;
  protected UIColor                           alternateRowColor;
  protected Paint                             alternateRowPaint;
  protected CheckListManager                  checkListManager;
  protected int                               checkboxHeight;
  protected int                               checkboxLeftXSlop;
  protected int                               checkboxWidth;
  protected OnItemClickListener               clickListener;
  protected iPlatformComponentPainter         componentPainter;
  protected Paint                             dividerPaint;
  protected boolean                           doubleClick;
  protected iDrawCallback                     drawCallback;
  protected GestureDetector.OnGestureListener flingGestureListener;
  protected float                             flingThreshold;
  protected AndroidGraphics                   graphics;
  protected boolean                           ignoreLastItemClick;
  protected int                               indentBy;
  protected int                               indicatorHeight;
  protected int                               indicatorWidth;
  protected long                              lastClickTime;
  protected UIColor                           lineColor;
  protected UIStroke                          lineStroke;
  protected boolean                           linkedSelection;
  protected iPlatformListDataModel            listModel;
  protected int                               minRowHeight;
  protected int                               minVisibleRowCount;
  protected boolean                           needsRelayoutOnClick;
  protected int                               rowHeight;
  protected boolean                           selectFlinged;
  protected SelectionType                     selectionType;
  protected boolean                           showDivider;
  protected boolean                           swipeHappened;
  protected MotionEvent                       swipeStartEvent;
  protected float                             swipeX;
  protected boolean                           swipeable;
  protected OnTouchListener                   touchListener;
  protected VelocityTracker                   velocityTracker;
  protected int                               visibleRowCount;
  protected boolean                           wasAttached;
  protected boolean                           wholeViewFling;
  private EditingMode                         editingMode   = EditingMode.NONE;
  private int                                 lastEditedRow = -1;
  protected UIPoint                           pressedPoint  = new UIPoint(-1, -1);
  private int                                 animateX;
  private ValueAnimator                       animator;
  private boolean                             autoEndEditing;
  private boolean                             centerEditingComponentVertically;
  private boolean                             deletingAllowed;
  private boolean                             draggingAllowed;
  private UIAction[]                          editActions;
  private iToolBar                            editToolbar;
  private boolean                             editing;
  private iPlatformComponent                  editingComponent;
  private DataSetObserver                     editingObserver;
  private boolean                             editingSelectionAllowed;
  private boolean                             editingSwipingAllowed;
  private RowReordingSupport                  reorderingSupport;
  private OnItemLongClickListener             savedLongClickListener;
  private OnScrollListener                    scrollListener;
  private boolean                             showLastDivider;
  private IntList                             ids;
  private ListSynchronizer                    listSynchronizer;
  private UIScrollingEdgePainter              scrollingEdgePainter;
  boolean                                     drawableInvalidated;
  protected TableHeader                       header;
  private boolean                             scrolling;
  private iExpansionListener                  editModeListener;
  private iExpansionListener                  rowEditModeListener;
  protected iScrollerSupport                  rowHeader;
  protected iScrollerSupport                  rowFooter;
  protected float                             enabledAlpha = 1;

  public ListViewEx(Context context) {
    this(context, null);
  }

  public ListViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
    setChoiceMode(CHOICE_MODE_SINGLE);
    setFadingEdgeLength(150);
    setItemsCanFocus(false);
    flingThreshold = (ViewConfiguration.get(context).getScaledTouchSlop() + 0.5f) * 2;
    setDividerHeight(0);
    super.setOnItemClickListener(this);
    checkboxLeftXSlop = INDICATOR_SLOP;

    if (Platform.getAppContext().getPressedPainter() != null) {
      this.setSelector(NullDrawable.getStatefulInstance());
    }

    super.setOnScrollListener(this);

    if (UIScrollingEdgePainter.isPaintVerticalScrollEdge()) {
      scrollingEdgePainter = UIScrollingEdgePainter.getInstance();
    }

    this.setRecyclerListener(this);
  }

  public void clearCheckMarks() {
    if (checkListManager != null) {
      checkListManager.clear();
      invalidate();
    }
  }

  @Override
  public void clearChoices() {
    SparseBooleanArray a   = getCheckedItemPositions();
    int                len = (a == null)
                             ? 0
                             : a.size();

    for (int i = 0; i < len; i++) {
      if (a.valueAt(i)) {
        setItemChecked(i, false);
      }
    }
  }

  public void clearContextMenuIndex() {
    int n = popupMenuIndex;

    popupMenuIndex = -1;

    if (n != -1) {
      changeHilight(n, false);
    }
  }

  @Override
  public void setAlpha(float alpha) {
    enabledAlpha = alpha;
    super.setAlpha(alpha);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);

    if (enabled) {
      super.setAlpha(enabledAlpha * ColorUtils.getDisabledAplhaPercent());
    } else {
      super.setAlpha(0.5f);
    }
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    if (!isEnabled()) {
      return true;
    }

    return super.dispatchKeyEvent(event);
  }

  @Override
  public boolean dispatchGenericMotionEvent(MotionEvent event) {
    if (!isEnabled()) {
      return true;
    }

    return super.dispatchGenericMotionEvent(event);
  }

  @Override
  @SuppressLint("Recycle")
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (!isEnabled()) {
      return true;
    }

    if (animator != null) {
      animator.cancel();
      animator = null;
    }

    if ((flingGestureListener == null) || ((reorderingSupport != null) && reorderingSupport.mCellIsMobile)
        || (editing &&!editingSwipingAllowed)) {
      if (ev.getAction() == MotionEvent.ACTION_UP) {
        if (!checkForCheckboxClick(ev) && (System.currentTimeMillis() <= (DELAY + lastClickTime))) {
          doubleClick = true;
        } else {
          lastClickTime = System.currentTimeMillis();
          doubleClick   = false;
        }
      } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        pressedPoint.x = ev.getX();
        pressedPoint.y = ev.getY();
      }

      return super.dispatchTouchEvent(ev);
    }

    switch(ev.getAction()) {
      case MotionEvent.ACTION_UP :
        if (!swipeHappened) {
          if (System.currentTimeMillis() <= (DELAY + lastClickTime)) {
            doubleClick = true;
          } else {
            lastClickTime = System.currentTimeMillis();
            doubleClick   = false;
          }
        }

        return super.dispatchTouchEvent(ev);

      case MotionEvent.ACTION_CANCEL :
        if (swipeStartEvent != null) {
          swipeStartEvent.recycle();
        }

        swipeStartEvent = null;
        swipeable       = true;
        swipeHappened   = false;

        return super.dispatchTouchEvent(ev);

      case MotionEvent.ACTION_DOWN :
        if (swipeStartEvent != null) {
          swipeStartEvent.recycle();
        }

        swipeStartEvent = null;
        swipeHappened   = false;
        swipeable       = true;
        swipeX          = -1;
        pressedPoint.x  = ev.getX();
        pressedPoint.y  = ev.getY();

        if ((editingRow != -1)) {
          if (!isEditingComponentTouched(ev)) {
            Platform.invokeLater(new Runnable() {
              @Override
              public void run() {
                hideRowEditingComponent(true);
              }
            });
          }

          swipeable = false;

          return true;
        }

        return super.dispatchTouchEvent(ev);

      case MotionEvent.ACTION_MOVE :
        if (!swipeHappened && swipeable) {
          if (swipeX == -1) {
            swipeStartEvent = MotionEvent.obtain(ev);
            swipeX          = ev.getX();
            velocityTracker = VelocityTracker.obtain();
            velocityTracker.addMovement(ev);
          } else {
            velocityTracker.addMovement(ev);

            if (Math.abs(swipeX - ev.getX()) > flingThreshold) {
              int pos = pointToPosition((int) ev.getX(), (int) ev.getY());

              if (wholeViewFling
                  || ((pos != -1)
                      && (pointToPosition((int) swipeStartEvent.getX(), (int) swipeStartEvent.getY()) == pos))) {
                velocityTracker.computeCurrentVelocity(1000);
                swipeHappened = flingGestureListener.onFling(swipeStartEvent, ev, velocityTracker.getXVelocity(),
                        velocityTracker.getYVelocity());
              }
            }
          }
        }

        return super.dispatchTouchEvent(ev);

      default :
        return super.dispatchTouchEvent(ev);
    }
  }

  @Override
  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }

    if (editingComponent != null) {
      if (editingComponent.getWidget() != null) {
        editingComponent.getWidget().dispose();
      } else {
        editingComponent.dispose();
      }

      editingComponent = null;
    }

    if (editToolbar != null) {
      editToolbar.dispose();
      editToolbar      = null;
      this.editActions = null;
    }

    if (reorderingSupport != null) {
      reorderingSupport.dispose();
    }

    if (backPressListener != null) {
      ((MainActivity) Platform.getAppContext().getActivity()).removeBackPressListener(backPressListener);
    }
  }

  @Override
  public void draw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;
    int                             w  = getWidth();
    int                             h  = getHeight();
    int                             x  = getScrollX();
    int                             y  = getScrollY();

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, x, y, w, h, iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, x, y, w, h, iPlatformPainter.UNKNOWN, true);
    }

    if (scrollingEdgePainter != null) {
      h -= (getPaddingTop() + getPaddingBottom());
      w -= (getPaddingLeft() + getPaddingRight());
      x += getPaddingLeft();
      y += getPaddingTop();
      scrollingEdgePainter.paint(graphics, x, y, w, h, true);
    }

    graphics.clear();
  }

  public void editModeChangeAllMarks(boolean mark) {
    listModel.editModeChangeAllMarks(mark);

    int start = getFirstVisiblePosition();
    int end   = getLastVisiblePosition();

    for (int n = start; n <= end; n++) {
      View v = getChildAt(n - start);

      if (v instanceof ListRowContainer) {
        ((ListRowContainer) v).setChecked(mark);
      }
    }

    updateActions();
  }

  public UIColor getAlternateRowColor() {
    return alternateRowColor;
  }

  public Paint getAlternatingRowPaint() {
    if ((alternateRowPaint == null) && (alternateRowColor != null)) {
      UIColor c = lineColor;

      if (c == null) {
        c = ColorUtils.getListDividerColor();
      }

      alternateRowPaint = new Paint();
      alternateRowPaint.setStyle(Paint.Style.FILL);
      alternateRowPaint.setColor(alternateRowColor.getColor());
    }

    return alternateRowPaint;
  }

  public int getCheckboxLeftXSlop() {
    return checkboxLeftXSlop;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public UIPoint getContentOffset() {
    return new UIPoint(0, getChildAt(0).getTop());
  }

  public int getContextMenuIndex() {
    return popupMenuIndex;
  }

  public Paint getDividerPaint() {
    if (dividerPaint == null) {
      UIColor c = lineColor;

      if (c == null) {
        c = ColorUtils.getListDividerColor();
      }

      dividerPaint = new Paint();
      dividerPaint.setStyle(Paint.Style.FILL);
      dividerPaint.setColor(c.getColor());
      AndroidHelper.setLineStroke(lineStroke, dividerPaint);
    }

    return dividerPaint;
  }

  public EditingMode getEditingMode() {
    return editingMode;
  }

  public int getEditingRow() {
    return editingRow;
  }

  public GestureDetector.OnGestureListener getFlingGestureListener() {
    return flingGestureListener;
  }

  public float getFlingThreshold() {
    return flingThreshold;
  }

  public TableHeader getHeader() {
    return header;
  }

  public int getIndent(int row) {
    return 0;
  }

  public int getIndentBy() {
    return indentBy;
  }

  public int getIndicatorHeight() {
    return indicatorHeight;
  }

  public int getIndicatorWidth() {
    return indicatorWidth;
  }

  public int getLastEditedRow() {
    return lastEditedRow;
  }

  public ListSynchronizer getListSynchronizer() {
    return listSynchronizer;
  }

  public int getMaxSelectionIndex() {
    IntList list = getSelectedIndexesList();

    return (list._length == 0)
           ? -1
           : list.A[list._length - 1];
  }

  /**
   * @return the minRowHeight
   */
  public int getMinRowHeight() {
    return minRowHeight;
  }

  public int getMinSelectionIndex() {
    IntList list = getSelectedIndexesList();

    return (list._length == 0)
           ? -1
           : list.A[0];
  }

  public void getPreferredSize(iPlatformComponent comp, UIDimension size, float maxWidth) {
    TableHelper.calculateListSize(listModel, comp, listModel.getItemRenderer(), null, size, -1, (int) maxWidth,
                                  rowHeight);

    int h = TableHelper.getPreferredListHeight(comp, Math.max(minVisibleRowCount, visibleRowCount), rowHeight,
              getAdapter().getCount() + 1);

    size.height = Math.max(h, size.height);
    size.height += getPaddingTop() + getPaddingBottom();
  }

  public int getPreferredHeight() {
    int h = TableHelper.getPreferredListHeight(Component.findFromView(this),
              Math.max(minVisibleRowCount, visibleRowCount), rowHeight, getAdapter().getCount());

    h += getPaddingTop() + getPaddingBottom();

    return Math.max(super.getSuggestedMinimumHeight(), h);
  }

  public UIPoint getPressedPoint() {
    return pressedPoint;
  }

  public int getReorderingRow() {
    return (reorderingSupport == null)
           ? -1
           : reorderingSupport.mMobileItemId;
  }

  public iScrollerSupport getRowFooter() {
    return rowFooter;
  }

  public iScrollerSupport getRowHeader() {
    return rowHeader;
  }

  /**
   * @return the rowHeight
   */
  public int getRowHeight() {
    return rowHeight;
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  public int getSelectedIndex() {
    if (getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
      return getCheckedItemPosition();
    }

    SparseBooleanArray a   = getCheckedItemPositions();
    int                len = (a == null)
                             ? 0
                             : a.size();

    for (int i = 0; i < len; i++) {
      if (a.valueAt(i)) {
        return i;
      }
    }

    return -1;
  }

  public int getSelectedIndexCount() {
    if (getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
      return (getCheckedItemPosition() == -1)
             ? 0
             : 1;
    }

    return getSelectedIndexesList()._length;
  }

  public int[] getSelectedIndexes() {
    if (getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
      final int n = getCheckedItemPosition();

      return (n == -1)
             ? EMPTY_ARRAY
             : new int[] { n };
    }

    return getSelectedIndexesList().toArray();
  }

  public int[] getCheckedIndexes() {
    if (checkListManager != null) {
      return checkListManager.getCheckedIndexes(listModel);
    }

    return null;
  }

  public SelectionType getSelectionType() {
    return selectionType;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    int h = TableHelper.getMinimumListHeight(Component.findFromView(this), minVisibleRowCount, rowHeight);

    h += getPaddingTop() + getPaddingBottom();

    return Math.max(super.getSuggestedMinimumHeight(), h);
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return super.getSuggestedMinimumWidth();
  }

  public View getViewForRow(int row) {
    if (row < 0) {
      return null;
    }

    int first = getFirstVisiblePosition();
    int last  = getLastVisiblePosition();

    if ((row < first) || (row > last)) {
      return null;
    }

    first -= getHeaderViewsCount();
    row   -= first;

    if ((row > -1) && (row < getChildCount())) {
      return getChildAt(row);
    }

    return null;
  }

  @Override
  public void handleContextMenuInfo(View v, ContextMenuInfo menuInfo) {
    if (menuInfo instanceof AdapterContextMenuInfo) {
      handleLongpressSelection((AdapterContextMenuInfo) menuInfo);
    }
  }

  public void handleLongpressSelection(AdapterContextMenuInfo mi) {
    if (popupMenuIndex != -1) {
      clearContextMenuIndex();
    }

    int n = mi.position;

    if (n != -1) {
      RenderableDataItem row = listModel.get(n);

      if (!isSelectable(row)) {
        n = -1;
      }
    }

    popupMenuIndex      = n;
    ignoreLastItemClick = true;

    if (popupMenuIndex != -1) {
      changeHilight(popupMenuIndex, true);
    }
  }

  public void hideRowEditingComponent(boolean animate) {
    int row = editingRow;

    editingRow = -1;

    if (row != -1) {
      if (rowEditModeListener != null) {
        rowEditModeListener.itemWillCollapse(new ExpansionEvent(editingComponent, ExpansionEvent.Type.WILL_COLLAPSE));
      }
    }

    View v = (row == -1)
             ? null
             : getViewForRow(row);

    if (v instanceof ListRowContainer) {
      ListRowContainer lc = (ListRowContainer) v;

      lc.hideRowEditingComponent(animate);
    }
  }

  @Override
  public void invalidateDrawable(Drawable drawable) {
    if (drawable instanceof NullDrawable) {
      drawableInvalidated = true;
    }

    super.invalidateDrawable(drawable);
  }

  @Override
  public boolean isAtBottomEdge() {
    int index = getLastVisiblePosition();

    if (index < (getAdapter().getCount() - 1)) {
      return false;
    }

    View v = getViewForRow(index);

    return (v == null)
           ? true
           : v.getBottom() <= getBottom();
  }

  @Override
  public boolean isAtLeftEdge() {
    return true;
  }

  @Override
  public boolean isAtRightEdge() {
    return true;
  }

  @Override
  public boolean isAtTopEdge() {
    int index = getFirstVisiblePosition();

    if ((index > 0) || (getChildCount() == 0)) {
      return false;
    }

    return getChildAt(0).getTop() == 0;
  }

  public boolean isAutoEndEditing() {
    return autoEndEditing;
  }

  public boolean isEditing() {
    return editing;
  }

  public boolean isEditingSelectionAllowed() {
    return editingSelectionAllowed;
  }

  public boolean isEditingSwipingAllowed() {
    return editingSwipingAllowed;
  }

  public boolean isExtendBackgroundRendering() {
    return extendBackgroundRendering;
  }

  public boolean isNeedsRelayoutOnClick() {
    return needsRelayoutOnClick;
  }

  public boolean isRowChecked(int row) {
    return (checkListManager != null) && checkListManager.isRowChecked(row);
  }

  @Override
  public boolean isScrolling() {
    return scrolling;
  }

  public boolean isSelectFlinged() {
    return selectFlinged;
  }

  /**
   * @return the showDivider
   */
  public boolean isShowDivider() {
    return showDivider;
  }

  public boolean isWholeViewFling() {
    return wholeViewFling;
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {}

  @Override
  public void moveUpDown(boolean up, boolean block) {
    if (up) {
      int row = block
                ? getLastVisiblePosition()
                : getFirstVisiblePosition() + 1;

      if (row < getCount()) {
        scrollRowToTop(row);

        int row2 = getFirstVisiblePosition();

        if ((row2 != row) && (row2 + 1 < getCount())) {
          scrollRowToTop(row + 1);
        }
      }
    } else {
      if (block) {
        int row = getFirstVisiblePosition();

        if (row > 0) {
          scrollRowToBottom(row);
        }
      } else {
        int row = getFirstVisiblePosition() - 1;

        if (row >= 0) {
          scrollRowToTop(row);

          int row2 = getFirstVisiblePosition();

          if ((row2 != row) && (row2 > 0)) {
            scrollRowToTop(row - 1);
          }
        }
      }
    }
  }

  public boolean needsRowContainer() {
    return (deletingAllowed && (editingComponent != null)) || draggingAllowed;
  }

  @Override
  public void onItemClick(AdapterView<?> list, View view, int position, long id) {
    if (ignoreLastItemClick && (lastClickTime + 100) < System.currentTimeMillis()) {
      ignoreLastItemClick = false;

      return;
    }

    itemClicked(view, position, id);

    if (clickListener != null) {
      clickListener.onItemClick(list, view, position, id);
    }
  }

  @Override
  public void onMovedToScrapHeap(View view) {
    if (view.getVisibility() == INVISIBLE) {
      view.setVisibility(VISIBLE);
    }
  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    if ((listSynchronizer != null) && listSynchronizer.isSyncScrolling()) {
      listSynchronizer.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    if (scrollListener != null) {
      scrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    if ((rowFooter != null) && (rowHeader != null) && (totalItemCount > 0)) {
      View c = getChildAt(0);
      int  y = -c.getTop() + firstVisibleItem * c.getHeight();

      if (rowHeader != null) {
        rowHeader.setContentOffset(0, y);
      }

      if (rowFooter != null) {
        rowFooter.setContentOffset(0, y);
      }
    }
  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
    scrolling = scrollState != 0;

    if (scrolling && drawableInvalidated) {
      drawableInvalidated = false;

      if (pressedPoint != null) {
        int row = pointToPosition((int) pressedPoint.x, (int) pressedPoint.y);

        if (row != -1) {
          repaintRow(row);
        }
      }
    }

    if ((listSynchronizer != null) && listSynchronizer.isSyncScrolling()) {
      listSynchronizer.onScrollStateChanged(view, scrollState);
    }

    if (scrollListener != null) {
      scrollListener.onScrollStateChanged(view, scrollState);
    }
  }

  @Override
  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(MotionEvent event) {
    if (editing && (reorderingSupport != null)) {
      if (!reorderingSupport.onTouchEvent(event)) {
        return false;
      }
    }

    if (touchListener != null) {
      if (touchListener.onTouch(this, event)) {
        return true;
      }
    }

    return super.onTouchEvent(event);
  }

  @Override
  public boolean performItemClick(View view, int position, long id) {
    if (editingRow != -1) {
      hideRowEditingComponent(true);

      return true;
    }

    if (editing) {
      if (view instanceof ListRowContainer) {
        ((ListRowContainer) view).toggleEditingModeCheckedState();
        listModel.editModeChangeMark(position, ((ListRowContainer) view).isCheckedInEditingMode());
        updateActions();
      }

      return true;
    }

    if (swipeHappened &&!selectFlinged) {
      swipeHappened = false;

      return true;
    } else {
      if (ignoreLastItemClick && (lastClickTime + 100) > System.currentTimeMillis()) {
        ignoreLastItemClick = false;

        if (popupMenuIndex != -1) {
          invalidate();
        }

        return true;
      }

      if (!isSelectable((RenderableDataItem) getItemAtPosition(position))) {
        return true;
      }

      int old = getCheckedItemPosition();

      if (linkedSelection && (checkListManager != null)) {
        if ((old != -1) && (old != position) && (getChoiceMode() == CHOICE_MODE_SINGLE)) {
          checkListManager.toggleRow(old);
          repaintRow(old);
        }
      }

      if ((getChoiceMode() == CHOICE_MODE_MULTIPLE) && isItemChecked(position)) {
        setItemChecked(position, false);
        fireDeselected();

        return true;
      }

      if ((header != null) && header.isColumnSelectionAllowed()) {
        if (view instanceof TableRow) {
          if (!header.columnInRowClicked(position, ((TableRow) view).getColumnAtX(pressedPoint.x))) {
            return true;
          }
        }
      }

      boolean ok = super.performItemClick(view, position, id);

      if (listSynchronizer != null) {
        listSynchronizer.setItemChecked(this, position, true);
      }

      if (linkedSelection && (checkListManager != null)) {
        checkListManager.toggleRow(position);
        repaintRow(position);
      }

      return ok;
    }
  }

  public void repaintRow(int row) {
    View view = getViewForRow(row);

    if (view != null) {
      view.invalidate();
    }
  }

  public void repaintRow(RenderableDataItem parent) {
    if ((listModel != null) && (parent != null)) {
      int row = listModel.indexOf(parent);

      repaintRow(row);
    }
  }

  public void repaintRows(int firstRow, int lastRow) {
    int n = getLastVisiblePosition();

    if (n < lastRow) {
      lastRow = n;
    }

    int start = getFirstVisiblePosition();

    if (start > firstRow) {
      firstRow = start;
    }

    for (n = firstRow; n <= lastRow; n++) {
      View v = getChildAt(n - start);

      if (v != null) {
        v.invalidate();
      }
    }
  }

  public void scrollRowToBottom(int row) {
    View v = getViewForRow(row);
    int  h = getHeight() - getPaddingTop() - getPaddingBottom();

    if (v == null) {
      smoothScrollToPositionFromTop(row, h - getRowHeight(), 0);
    } else {
      smoothScrollToPositionFromTop(row, h - v.getHeight(), 0);
    }
  }

  public void scrollRowToTop(int row) {
    smoothScrollToPositionFromTop(row, 0, 0);
  }

  public void scrollRowToVisible(final int row) {
    int first = getFirstVisiblePosition();
    int last  = getLastVisiblePosition();

    if ((row > first) && (row < last)) {
      return;
    }

    smoothScrollToPositionFromTop(row, getHeight() / 2, 0);
  }

  @Override
  public void scrollToBottomEdge() {
    int count = getCount();

    if (count > 0) {
      scrollRowToBottom(count - 1);
    }
  }

  @Override
  public void scrollToLeftEdge() {}

  @Override
  public void scrollToRightEdge() {}

  @Override
  public void scrollToTopEdge() {
    int count = getCount();

    if (count > 0) {
      smoothScrollToPositionFromTop(0, 0, 0);
    }
  }

  @Override
  public void setAdapter(ListAdapter adapter) {
    if ((listModel != null) && (editingObserver != null)) {
      ((ListAdapter) listModel).unregisterDataSetObserver(editingObserver);
    }

    super.setAdapter(adapter);
    lastEditedRow = -1;

    if (editingObserver == null) {
      editingObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
          onInvalidated();
        }
        @Override
        public void onInvalidated() {
          lastEditedRow = -1;
          editingRow    = -1;

          if (editing) {
            updateActions();
          }
        }
      };
    }

    adapter.registerDataSetObserver(editingObserver);
    listModel = null;

    if (adapter instanceof iPlatformListDataModel) {
      listModel = (iPlatformListDataModel) adapter;
      listModel.setShowLastDivider(showLastDivider);
      listModel.setRowEditingSupported(editingComponent != null);
    }
  }

  public void setAlternateRowColor(UIColor alternateRowColor) {
    this.alternateRowColor = alternateRowColor;
  }

  public void setAutoEndEditing(boolean autoEndEditing) {
    this.autoEndEditing = autoEndEditing;
  }

  public void setCheckboxLeftXSlop(int checkboxLeftXSlop) {
    this.checkboxLeftXSlop = checkboxLeftXSlop;
  }

  public void setCheckedRows(int[] indices) {
    if (checkListManager != null) {
      checkListManager.setCheckedRows(indices);

      if (linkedSelection) {
        clearChoices();

        if (indices != null) {
          ;
        }

        {
          for (int i : indices) {
            super.setItemChecked(i, true);
          }
        }
      }
    } else {
      clearChoices();

      if (indices != null) {
        ;
      }

      {
        for (int i : indices) {
          super.setItemChecked(i, true);
        }
      }
    }

    invalidate();
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
    setCacheColorHint(0);
  }

  @Override
  public void setContentOffset(float x, float y) {
    scrollTo(0, (int) y);
  }

  public void setDividerLine(UIColor color, UIStroke stroke) {
    lineColor    = color;
    lineStroke   = stroke;
    showDivider  = true;
    dividerPaint = null;
  }

  public iDrawCallback setDrawCallback(iDrawCallback cb) {
    final iDrawCallback ocb = drawCallback;

    drawCallback = cb;

    return ocb;
  }

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
  }

  public void setEditingSwipingAllowed(boolean editingSwipingAllowed) {
    this.editingSwipingAllowed = editingSwipingAllowed;
  }

  public void setEditModeToolBar(iToolBar tb) {
    editToolbar = tb;

    if (tb != null) {
      tb.getComponent().setVisible(false);
    }
  }

  @Override
  public iToolBar getEditModeToolBar() {
    return editToolbar;
  }

  public void setEditModeListener(iExpansionListener l) {
    editModeListener = l;
  }

  public void setExtendBackgroundRendering(boolean extendBackgroundRendering) {
    this.extendBackgroundRendering = extendBackgroundRendering;
  }

  @Override
  public void setFlingGestureListener(GestureDetector.OnGestureListener flingGestureListener) {
    this.flingGestureListener = flingGestureListener;
  }

  public void setFlingThreshold(float flingThreshold) {
    if (flingThreshold >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
      this.flingThreshold = flingThreshold;
    }
  }

  public void setHeader(TableHeader header) {
    this.header = header;
  }

  public void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    checkboxHeight = checked.getIconHeight();
    checkboxWidth  = checked.getIconWidth();
    checkboxHeight = Math.max(checkboxHeight, unchecked.getIconHeight());
    checkboxWidth  = Math.max(checkboxWidth, unchecked.getIconWidth());

    if (checkListManager == null) {
      checkListManager = createCheckListManager();
    }

    checkListManager.setIcons(checked, unchecked, indeterminate);

    Adapter a = getAdapter();

    if (a instanceof aDataItemListModelEx) {
      ((aDataItemListModelEx) a).setCheckListManager(checkListManager);
    }
  }

  @Override
  public void setItemChecked(int position, boolean value) {
    int old = getCheckedItemPosition();

    super.setItemChecked(position, value);

    if (linkedSelection && (checkListManager != null)) {
      if (value != checkListManager.isRowChecked(position)) {
        checkListManager.toggleRow(position);
        repaintRow(position);
      }

      if ((old != -1) && (old != position) && (getChoiceMode() == CHOICE_MODE_SINGLE)) {
        checkListManager.toggleRow(old);
        repaintRow(old);
      }
    }

    if (listSynchronizer != null) {
      listSynchronizer.setItemChecked(this, position, value);
    }

    if (old != position) {
      fireSelected(this, position, getItemIdAtPosition(position));
    }
  }

  public void setLinkedSelection(boolean linkedSelection) {
    this.linkedSelection = linkedSelection;
  }

  public void setListSynchronizer(ListSynchronizer listSynchronizer) {
    this.listSynchronizer = listSynchronizer;

    if (listSynchronizer.isSyncScrolling()) {
      setOverScrollMode(OVER_SCROLL_NEVER);
    }
  }

  public void setMinimumVisibleRowCount(int rows) {
    minVisibleRowCount = rows;

    int rh = getRowHeight();

    if (rh < 1) {
      rh = ScreenUtils.lineHeight(Component.fromView(this));
    }

    setMinimumHeight(rh * rows);
    requestLayout();
  }

  public void setMinRowHeight(int min) {
    this.minRowHeight = min;

    if (rowHeight < min) {
      setRowHeight(min);
    }
  }

  // @Override
  // public void moveUpDown(boolean up, boolean block) {
  // int count=getCount();
  // if(count==0) {
  // return;
  // }
  // int li=getLastVisiblePosition();
  // int fi=getLastVisiblePosition();
  // if(fi==0 && li<count) {
  // return;
  // }
  // View v1=getChildAt(0);
  // View v2=getChildAt(getChildCount()-1);
  // int y;
  // if(block) {
  // y=(int)v2.getY();
  // }
  // else {
  // y=up ? v1.getHeight() : v2.getHeight();
  // }
  // scrollListBy(y);
  // invalidate();
  // }
  public void setNeedsRelayoutOnClick(boolean needsRelayoutOnClick) {
    this.needsRelayoutOnClick = needsRelayoutOnClick;
  }

  @Override
  public void setOnItemClickListener(OnItemClickListener l) {
    clickListener = l;
  }

  @Override
  public void setOnItemLongClickListener(OnItemLongClickListener l) {
    super.setOnItemLongClickListener(l);

    if ((reorderingSupport == null) || (l != reorderingSupport.mDraggingItemLongClickListener)) {
      savedLongClickListener = l;
    }
  }

  @Override
  public void setOnScrollListener(OnScrollListener l) {
    scrollListener = l;
  }

  @Override
  public void setOnTouchListener(OnTouchListener l) {
    touchListener = l;
  }

  public void setRowChecked(int row, boolean checked) {
    if ((checkListManager != null) && (checkListManager.isRowChecked(row) != checked)) {
      if (checkListManager.toggleRow(row)) {
        invalidate();
      } else {
        repaintRow(row);
      }
    }
  }

  public void setRowEditingComponent(iPlatformComponent c, boolean centerVertically) {
    editingComponent                 = c;
    centerEditingComponentVertically = centerVertically;

    if (c != null) {
      if (listModel != null) {
        listModel.setRowEditingSupported(true);
      }

      if (flingGestureListener == null) {
        flingGestureListener = new RowEditingGestureListener();
      }
    } else {
      flingGestureListener = null;
    }
  }

  public void setRowFooter(iScrollerSupport rowFooter) {
    this.rowFooter = rowFooter;
  }

  public void setRowHeader(iScrollerSupport rowHeader) {
    this.rowHeader = rowHeader;
  }

  /**
   * @param rowHeight
   *          the rowHeight to set
   */
  public void setRowHeight(int rowHeight) {
    if (rowHeight < minRowHeight) {
      rowHeight = minRowHeight;
    }

    this.rowHeight = rowHeight;
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
  }

  public void setSelectFlinged(boolean selectFlinged) {
    this.selectFlinged = selectFlinged;
  }

  public void setSelectionMode(SelectionMode selectionMode) {
    int sm;

    switch(selectionMode) {
      case SINGLE :
        sm = ListView.CHOICE_MODE_SINGLE;

        break;

      case BLOCK :
      case MULTIPLE :
        sm = ListView.CHOICE_MODE_MULTIPLE;

        break;

      case INVISIBLE :
        sm = ListView.CHOICE_MODE_SINGLE;

        break;

      default :
        sm = ListView.CHOICE_MODE_NONE;

        break;
    }

    setChoiceMode(sm);
  }

  public void setSelectionType(SelectionType selectionType) {
    this.selectionType = selectionType;
  }

  /**
   * @param showDivider
   *          the showDivider to set
   */
  public void setShowDivider(boolean showDivider) {
    this.showDivider = showDivider;
  }

  public void setShowLastDivider(boolean show) {
    showLastDivider = show;

    if (listModel != null) {
      listModel.setShowLastDivider(showLastDivider);
    }
  }

  public void setVisibleRowCount(int rows) {
    if (minVisibleRowCount == 0) {
      int rh = getRowHeight();

      if (rh < 1) {
        rh = ScreenUtils.lineHeight(Component.fromView(this));
      }

      setMinimumHeight(rh * rows);
      requestLayout();
    }

    visibleRowCount = rows;
  }

  public void setWholeViewFling(boolean wholeViewFling) {
    this.wholeViewFling = wholeViewFling;
  }

  public void showRowEditingComponent(int row, iPlatformComponent comp, boolean animate, boolean centerVertically) {
    View v = (row == -1)
             ? null
             : getViewForRow(row);

    if (v instanceof ListRowContainer) {
      ListRowContainer lc = (ListRowContainer) v;

      if ((editingRow != -1) && (editingRow != row)) {
        lc.hideRowEditingComponent(false);
      }

      lc.showRowEditingComponent(comp, animate, centerVertically);
    }
  }

  public void startEditing(boolean animate, UIAction... actions) {
    if (!this.editing) {
      this.editing = true;
      listModel.setEditing(editing);

      if (backPressListener == null) {
        backPressListener = new iBackPressListener() {
          @Override
          public boolean onBackKeyPressed(iActivity activity) {
            boolean e = editing;

            if (editing) {
              stopEditing(true);
            }

            return e
                   ? true
                   : false;
          }
        };
        ((MainActivity) Platform.getAppContext().getActivity()).addBackPressListener(backPressListener);
      }

      if (editModeListener != null) {
        editModeListener.itemWillExpand(new ExpansionEvent(this, ExpansionEvent.Type.WILL_EXPAND));
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
            if (a.getActionListener() == null) {
              a.setActionListener(new iActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  editModeDeleteMarkedItems();

                  if (autoEndEditing) {
                    stopEditing(true);
                  }
                }
              });
            }

            if (a.getActionText() == null) {
              a.setActionText(Platform.getResourceAsString("Rare.action.delete"));
            }

            a.setEnabledOnSelectionOnly(true);
            isDelete = true;
          } else if ("Rare.action.markAll".equalsIgnoreCase(a.getActionName())) {
            if (a.getActionListener() == null) {
              a.setActionListener(new iActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  editModeChangeAllMarks(listModel.editModeGetMarkCount() < listModel.size());
                }
              });
            }

            markAll = a;
          }

          iWidget pb = tb.add(a);

          if (isDelete) {
            ((ActionComponent) pb.getDataComponent()).setPainterHolder(PainterUtils.createRedHyperlinkPainterHolder());
            a.setEnabled(false);
          }

          isDelete = false;
        }

        updateActions();
        tb.getComponent().setVisible(true);
      }

      int start    = getFirstVisiblePosition();
      int end      = getLastVisiblePosition();
      int distance = 0;

      for (int n = start; n <= end; n++) {
        View v = getChildAt(n - start);

        if (v instanceof ListRowContainer) {
          ListRowContainer lc = (ListRowContainer) v;

          lc.setInListEditingMode(true);

          if (animate) {
            if (distance == 0) {
              distance = lc.getContentViewX();
            }
          }
        }
      }

      requestLayout();
      invalidate();

      if (animate && (distance > 0)) {
        animateX = -distance;
        animateEditMode(true, -distance, start, end);
      } else if (draggingAllowed) {
        if (reorderingSupport == null) {
          reorderingSupport = new RowReordingSupport();
        }

        reorderingSupport.startListening();
      }
    }
  }

  public void stopEditing(boolean animate) {
    if (this.editing) {
      if (animate) {
        int start    = getFirstVisiblePosition();
        int end      = getLastVisiblePosition();
        int distance = 0;

        for (int n = start; n <= end; n++) {
          View v = getChildAt(n - start);

          if (v instanceof ListRowContainer) {
            ListRowContainer lc = (ListRowContainer) v;

            distance = lc.getContentViewX();

            break;
          }
        }

        animateEditMode(false, -distance, start, end);
        invalidate();
      } else {
        stopEditingEx();
      }
    }
  }

  public void viewConfigured() {
    aDataItemListModelEx dlm     = null;
    ListAdapter          adapter = getAdapter();

    if (adapter instanceof aDataItemListModelEx) {
      dlm             = (aDataItemListModelEx) adapter;
      showLastDivider = dlm.isShowLastDivider();

      if ((flingGestureListener == null) && (dlm.getWidget() != null) && ((aWidget) dlm.getWidget()).canDelete()) {
        flingGestureListener = new RowEditingGestureListener();
      }
    }

    if ((checkListManager != null) && (listModel != null)) {
      checkListManager.setListModel(listModel);

      if (dlm != null) {
        dlm.setCheckListManager(checkListManager);
      }
    }
  }

  public boolean wasDoubleClick() {
    return doubleClick;
  }

  @Override
  public int getEditModeMarkCount() {
    return listModel.editModeGetMarkCount();
  }

  @Override
  public boolean isEditModeItemMarked(int index) {
    return listModel.editModeIsItemMarked(index);
  }

  @Override
  public RenderableDataItem[] getEditModeMarkedItems() {
    return listModel.editModeGetMarkedItems();
  }

  public void setEditModeAllItemMarks(boolean mark) {
    listModel.editModeChangeAllMarks(mark);

    if (editing) {
      updateActions();
      invalidate();
    }
  }

  public void clearEditModeMarks() {
    listModel.editModeClearMarks();

    if (editing) {
      updateActions();
      invalidate();
    }
  }

  @Override
  public void setEditModeItemMark(int index, boolean mark) {
    if (listModel.editModeIsItemMarked(index) != mark) {
      listModel.editModeChangeMark(index, mark);

      if (editing) {
        updateActions();
        invalidate();
      }
    }
  }

  @Override
  public void toggleEditModeItemMark(int index) {
    setEditModeItemMark(index, !listModel.editModeIsItemMarked(index));
  }

  @Override
  public int[] getEditModeMarkedIndices() {
    return listModel.editModeGetMarkedIndices();
  }

  protected void animateEditMode(final boolean in, final int distance, final int start, final int end) {
    if (in) {
      animator = ValueAnimator.ofFloat(1f, 0f);
    } else {
      animator = ValueAnimator.ofFloat(0f, 1f);
    }

    animator.setDuration(250);
    animator.addListener(new AnimatorListener() {
      @Override
      public void onAnimationCancel(Animator animation) {
        animator = null;

        if ((editModeListener != null) &&!in) {
          editModeListener.itemWillCollapse(new ExpansionEvent(this, ExpansionEvent.Type.WILL_COLLAPSE));
        }

        if (!in) {
          stopEditingEx();
        } else if (draggingAllowed) {
          if (reorderingSupport == null) {
            reorderingSupport = new RowReordingSupport();
          }

          reorderingSupport.startListening();
        }

        animateX = 0;
        relayoutVisibleViews();
      }
      @Override
      public void onAnimationEnd(Animator animation) {
        onAnimationCancel(animation);
      }
      @Override
      public void onAnimationRepeat(Animator animation) {}
      @Override
      public void onAnimationStart(Animator animation) {}
    });
    animator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        Float fv = (Float) animation.getAnimatedValue();
        int   x  = (int) (fv * distance);

        animateX = x;

        for (int n = start; n <= end; n++) {
          View v = getChildAt(n - start);

          if (v instanceof ListRowContainer) {
            ((ListRowContainer) v).setAnimateX(x);
            v.requestLayout();
          }
        }
      }
    });
    animator.start();
  }

  protected boolean checkForCheckboxClick(MotionEvent ev) {
    if ((checkListManager != null) &&!linkedSelection) {
      int  x   = (int) ev.getX();
      int  y   = (int) ev.getY();
      int  row = pointToPosition(x, y);
      View v   = getViewForRow(row);

      if (v == null) {
        return false;
      }

      if ((row > -1) && isOnCheckBox(x, y - v.getTop(), v.getWidth(), v.getHeight(), getIndent(row))) {
        toggleCheckedState(row);

        if (!linkedSelection) {
          lastClickTime       = System.currentTimeMillis();
          ignoreLastItemClick = true;
        }

        return true;
      }
    }

    return false;
  }

  protected CheckListManager createCheckListManager() {
    CheckListManager cm = new CheckListManager(false, -2);
    Adapter          a  = getAdapter();

    if (a instanceof iPlatformListDataModel) {
      cm.setListModel((iPlatformListDataModel) a);
    }

    return cm;
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);

    if (reorderingSupport != null) {
      reorderingSupport.dispatchDraw(canvas);
    }
  }

  protected void editModeDeleteMarkedItems() {
    aListViewer lv = (aListViewer) Component.fromView(ListViewEx.this).getWidget();

    lv.removeAll(Arrays.asList(listModel.editModeGetMarkedItems()));
    listModel.editModeClearMarks();
    updateActions();
  }

  protected void fireDeselected() {
    android.widget.AdapterView.OnItemSelectedListener l = getOnItemSelectedListener();

    if (l != null) {
      l.onNothingSelected(this);
    }
  }

  protected void fireSelected(View view, int position, long id) {
    android.widget.AdapterView.OnItemSelectedListener l = getOnItemSelectedListener();

    if (l != null) {
      l.onItemSelected(this, view, position, id);
    }
  }

  protected boolean isEditingComponentTouched(MotionEvent e) {
    float x     = e.getRawX();
    float y     = e.getRawY();
    View  v     = editingComponent.getView();
    int   loc[] = new int[2];

    v.getLocationOnScreen(loc);

    RectF   r       = new RectF(loc[0], loc[1], loc[0] + v.getWidth(), loc[1] + v.getHeight());
    boolean touched = r.contains(x, y);

    if (touched) {
      if (e.getAction() == MotionEvent.ACTION_DOWN) {
        if (editingComponent instanceof ActionComponent) {
          ((ActionComponent) editingComponent).actionPerformed(new ActionEvent(this));
        } else {
          iPlatformComponent c = ((Container) Component.fromView(this)).getComponentAt(e.getX(), e.getY(), true);

          if (c instanceof ActionComponent) {
            ((ActionComponent) c).actionPerformed(new ActionEvent(this));
          }
        }
      }

      hideRowEditingComponent(false);
    }

    return touched;
  }

  protected boolean isOnCheckBox(float x, float y, int width, int height, int indent) {
    float sy = (height - checkboxHeight) / 2;

    if ((y < sy - INDICATOR_SLOP) || (y > (sy + checkboxHeight + INDICATOR_SLOP))) {
      return false;
    }

    int sx;
    int slop = INDICATOR_SLOP;

    if (selectionType == SelectionType.CHECKED_RIGHT) {
      sx = width - getPaddingRight() - PAD_SIZE - checkboxWidth;
    } else {
      slop = checkboxLeftXSlop;
      sx   = getPaddingLeft() + PAD_SIZE + indent;
    }

    return (x >= sx - slop) && (x <= (sx + checkboxWidth + slop));
  }

  protected boolean isSelectable(RenderableDataItem item) {
    return item.isEnabled() && item.isSelectable();
  }

  protected void itemClicked(View v, int index, long id) {
    super.setItemChecked(index, true);

    if (getOnItemSelectedListener() != null) {
      getOnItemSelectedListener().onItemSelected(this, v, index, id);
    }
  }

  protected void move(int from, int to) {
    iWidget         w  = Component.fromView(this).getWidget();
    DropInformation di = new DropInformation(w);

    di.setTargetWidget(w);
    di.setDropIndex(to);
    di.setDropAction(DnDConstants.ACTION_MOVE);
    w.importData(new RenderableDataItemTransferable(listModel.get(from)), di);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
    ScrollViewEx.addScrollerView(this);

    if (wasAttached) {
      wasAttached = false;
      requestLayout();
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
    ScrollViewEx.removeScrollerView(this);
    wasAttached = true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (drawCallback != null) {
      drawCallback.beforeOnDraw(this, canvas);
    }

    iPlatformListDataModel lm = listModel;

    super.onDraw(canvas);

    if (extendBackgroundRendering && (lm != null)) {
      int size = lm.size();

      if ((getLastVisiblePosition() == size - 1) && (getFirstVisiblePosition() == 0)) {
        int lc     = getChildCount() - 1;
        int fc     = getFooterViewsCount();
        int top    = getPaddingTop();
        int left   = getPaddingLeft();
        int right  = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();

        if (fc > 0) {
          View v = getChildAt(lc - fc + 1);

          bottom = v.getTop();
          lc     -= fc;
        }

        if (lc > -1) {
          View v = getChildAt(lc);

          top = v.getBottom();
        }

        int rh = getRowHeight();

        if (rh == 0) {
          rh = FontUtils.getDefaultLineHeight();
        }

        int   rh4   = rh / 4;
        Paint dp    = isShowDivider()
                      ? getDividerPaint()
                      : null;
        Paint ap    = getAlternatingRowPaint();
        int   width = right - left;
        int   i     = size;

        while(top < bottom) {
          if ((ap != null) && (i % 2 == 1)) {
            canvas.drawRect(left, top, right, top + rh, ap);
          }

          if (lm != null) {
            paintEmptyRowColumns(graphics, lm, left, top, width, rh);
          }

          if ((dp != null) && (i > size)) {
            if (showLastDivider || (top + rh4 < bottom)) {
              canvas.drawLine(left, top - 1, right, top - 1, dp);
            }
          }

          i++;
          top += rh;
        }
      }
    }

    if (drawCallback != null) {
      drawCallback.afterOnDraw(this, canvas);
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    if (animateX != 0) {
      int start = getFirstVisiblePosition();
      int end   = getLastVisiblePosition();

      for (int n = start; n <= end; n++) {
        View v = getChildAt(n - start);

        if (v instanceof ListRowContainer) {
          ListRowContainer lc = (ListRowContainer) v;

          lc.setInListEditingMode(true);
          lc.setAnimateX(animateX);
        }
      }
    }

    super.onLayout(changed, l, t, r, b);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int h = Math.max(getMeasuredHeight(), getSuggestedMinimumHeight());
    int w = Math.max(getMeasuredWidth(), getSuggestedMinimumWidth());

    setMeasuredDimension(resolveSize(w, widthMeasureSpec), resolveSize(h, heightMeasureSpec));
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  protected void paintEmptyRowColumns(AndroidGraphics g, iPlatformListDataModel model, float x, float y, float width,
          float height) {
    if (model instanceof aTableAdapter) {
      ((aTableAdapter) model).paintEmptyRowColumns(g, x, y, width, height);
    } else {
      Column                    c  = model.getItemRenderer().getItemDescription();
      PaintBucket               pb = (c == null)
                                     ? null
                                     : c.getItemPainter();
      iPlatformComponentPainter cp = (pb == null)
                                     ? null
                                     : pb.getCachedComponentPainter();

      if (cp != null) {
        cp.paint(g, x, y, width, height, iPainter.UNKNOWN);
      }
    }
  }

  protected void relayoutVisibleViews() {
    int start = getFirstVisiblePosition();
    int end   = getLastVisiblePosition();

    for (int n = start; n <= end; n++) {
      View v = getChildAt(n - start);

      if (v != null) {
        v.invalidate();
        v.requestLayout();
      }
    }
  }

  protected void stopEditingEx() {
    this.editing     = false;
    this.editActions = null;
    animateX         = 0;

    if (editToolbar != null) {
      editToolbar.removeAllWidgets();
      editToolbar.getComponent().setVisible(false);
    }

    if (reorderingSupport != null) {
      reorderingSupport.stopListening();
    }

    listModel.setEditing(false);

    int     start = getFirstVisiblePosition();
    int     end   = getLastVisiblePosition();
    Adapter a     = getAdapter();

    for (int n = start; n <= end; n++) {
      View v = getChildAt(n - start);

      if (v instanceof ListRowContainer) {
        ListRowContainer lv = (ListRowContainer) v;

        lv.setInListEditingMode(false);

        if (isItemChecked(n)) {
          a.getView(n, v, this);
        }
      }
    }

    requestLayout();
    invalidate();
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

  private void changeHilight(int row, boolean hilight) {
    if (isItemChecked(row) && (getSelectedIndexCount() > 1)) {
      int len = getChildCount();
      int n   = getFirstVisiblePosition();

      for (int i = 0; i < len; i++) {
        if (isItemChecked(i + n)) {
          View view = getChildAt(i);

          if (view instanceof iListViewRow) {
            ((iListViewRow) view).setHilight(hilight);
          }
        }
      }
    } else {
      View view = getViewForRow(row);

      if (view instanceof iListViewRow) {
        ((iListViewRow) view).setHilight(hilight);
      }
    }
  }

  private IntList getSelectedIndexesList() {
    SparseBooleanArray a   = getCheckedItemPositions();
    int                len = (a == null)
                             ? 0
                             : getCount();

    if (ids == null) {
      ids = new IntList();
    }

    ids.clear();

    for (int i = 0; i < len; i++) {
      if (a.get(i)) {
        ids.add(i);
      }
    }

    return ids;
  }

  private void setOnScrollListenerEx(OnScrollListener l) {
    super.setOnScrollListener(l);
  }

  public iExpansionListener getRowEditModeListener() {
    return rowEditModeListener;
  }

  public void setRowEditModeListener(iExpansionListener rowEditModeListener) {
    this.rowEditModeListener = rowEditModeListener;
  }

  public interface iListViewRow {
    void setHilight(boolean hilight);
  }


  /*
   * Portions of the code Copyright (C) 2013 The Android Open Source Project
   * Licensed under the Apache License, Version 2.0 (the "License"); you may not
   * use this file except in compliance with the License. You may obtain a copy
   * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless
   * required by applicable law or agreed to in writing, software distributed
   * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   * CONDITIONS OF ANY KIND, either express or implied. See the License for the
   * specific language governing permissions and limitations under the License.
   * Don DeCoteau - Modified to using raw indexes instead of needing stable id's
   */
  private class RowReordingSupport {
    final int INVALID_ID                   = -1;
    final int INVALID_POINTER_ID           = -1;
    final int LINE_THICKNESS               = ScreenUtils.PLATFORM_PIXELS_2;
    final int MOVE_DURATION                = 150;
    final int SMOOTH_SCROLL_AMOUNT_AT_EDGE = 15;
    int       mDownX                       = -1;
    int       mDownY                       = -1;
    int       mLastEventY                  = -1;
    int       mSmoothScrollAmountAtEdge    = 0;
    int       mTotalOffset                 = 0;
    // private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;
    int     mMobileItemId             = INVALID_ID;
    boolean mIsWaitingForScrollFinish = false;
    boolean mIsMobileScrolling        = false;

    /**
     * This scroll listener is added to the listview in order to handle cell
     * swapping when the cell is either at the top or bottom edge of the
     * listview. If the hover cell is at either edge of the listview, the
     * listview will begin scrolling. As scrolling takes place, the listview
     * continuously checks if new cells became visible and determines whether
     * they are potential candidates for a cell swap.
     */
    private AbsListView.OnScrollListener mDraggingScrollListener = new AbsListView.OnScrollListener() {
      private int mPreviousFirstVisibleItem = -1;
      private int mPreviousVisibleItemCount = -1;
      private int mCurrentFirstVisibleItem;
      private int mCurrentVisibleItemCount;
      private int mCurrentScrollState;

      /**
       * Determines
       * if
       * the
       * listview
       * scrolled
       * up
       * enough
       * to
       * reveal
       * a
       * new
       * cell
       * at
       * the
       * top
       * of
       * the
       * list
       * .
       * If
       * so,
       * then
       * the
       * appropriate
       * parameters
       * are
       * updated
       * .
       */
      public void checkAndHandleFirstVisibleCellChange() {
        if (mCurrentFirstVisibleItem != mPreviousFirstVisibleItem) {
          if (mCellIsMobile && (mMobileItemId != INVALID_ID)) {
            updateNeighborViewsForID(mMobileItemId);
            handleCellSwitch();
          }
        }
      }

      /**
       * Determines
       * if
       * the
       * listview
       * scrolled
       * down
       * enough
       * to
       * reveal
       * a
       * new
       * cell
       * at
       * the
       * bottom
       * of
       * the
       * list
       * .
       * If
       * so,
       * then
       * the
       * appropriate
       * parameters
       * are
       * updated
       * .
       */
      public void checkAndHandleLastVisibleCellChange() {
        int currentLastVisibleItem  = mCurrentFirstVisibleItem + mCurrentVisibleItemCount;
        int previousLastVisibleItem = mPreviousFirstVisibleItem + mPreviousVisibleItemCount;

        if (currentLastVisibleItem != previousLastVisibleItem) {
          if (mCellIsMobile && (mMobileItemId != INVALID_ID)) {
            updateNeighborViewsForID(mMobileItemId);
            handleCellSwitch();
          }
        }
      }
      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mCurrentFirstVisibleItem  = firstVisibleItem;
        mCurrentVisibleItemCount  = visibleItemCount;
        mPreviousFirstVisibleItem = (mPreviousFirstVisibleItem == -1)
                                    ? mCurrentFirstVisibleItem
                                    : mPreviousFirstVisibleItem;
        mPreviousVisibleItemCount = (mPreviousVisibleItemCount == -1)
                                    ? mCurrentVisibleItemCount
                                    : mPreviousVisibleItemCount;
        checkAndHandleFirstVisibleCellChange();
        checkAndHandleLastVisibleCellChange();
        mPreviousFirstVisibleItem = mCurrentFirstVisibleItem;
        mPreviousVisibleItemCount = mCurrentVisibleItemCount;
      }
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;
        // mScrollState
        // =
        // scrollState;
        isScrollCompleted();
      }

      /**
       * This method is in
       * charge
       * of
       * invoking
       * 1
       * of
       * 2
       * actions
       * .
       * Firstly
       * ,
       * if
       * the
       * listview
       * is
       * in
       * a
       * state
       * of
       * scrolling
       * invoked
       * by
       * the
       * hover
       * cell
       * being
       * outside
       * the
       * bounds
       * of
       * the
       * listview
       * ,
       * then
       * this
       * scrolling
       * event
       * is
       * continued
       * .
       * Secondly
       * ,
       * if
       * the
       * hover
       * cell
       * has
       * already
       * been
       * released
       * ,
       * this
       * invokes
       * the
       * animation
       * for
       * the
       * hover
       * cell
       * to
       * return
       * to
       * its
       * correct
       * position
       * after
       * the
       * listview
       * has
       * entered
       * an
       * idle
       * scroll
       * state
       * .
       */
      private void isScrollCompleted() {
        if ((mCurrentVisibleItemCount > 0) && (mCurrentScrollState == SCROLL_STATE_IDLE)) {
          if (mCellIsMobile && mIsMobileScrolling) {
            handleMobileCellScroll();
          } else if (mIsWaitingForScrollFinish) {
            touchEventsEnded();
          }
        }
      }
    };

    /**
     * Listens for long clicks on any items in the listview. When a cell has
     * been selected, the hover cell is created and set up.
     */
    private OnItemLongClickListener mDraggingItemLongClickListener = new OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
        mTotalOffset = 0;

        if (editingRow != -1) {
          hideRowEditingComponent(false);
        }

        editModeChangeAllMarks(false);

        int  position     = pointToPosition(mDownX, mDownY);
        int  itemNum      = position - getFirstVisiblePosition();
        View selectedView = getChildAt(itemNum);

        mMobileItemId = position;
        mHoverCell    = getAndAddHoverView(selectedView);
        selectedView.setVisibility(INVISIBLE);
        mCellIsMobile = true;
        updateNeighborViewsForID(mMobileItemId);

        return true;
      }
    };
    private boolean        mCellIsMobile    = false;
    private int            mBelowItemId     = INVALID_ID;
    private int            mActivePointerId = INVALID_POINTER_ID;
    private int            mAboveItemId     = INVALID_ID;
    private BitmapDrawable mHoverCell;
    private Rect           mHoverCellCurrentBounds;
    private Rect           mHoverCellOriginalBounds;

    public RowReordingSupport() {
      DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

      mSmoothScrollAmountAtEdge = (int) (SMOOTH_SCROLL_AMOUNT_AT_EDGE / metrics.density);
    }

    public void dispose() {
      mHoverCell                     = null;
      mDraggingItemLongClickListener = null;
      mDraggingScrollListener        = null;
    }

    /**
     * This method is in charge of determining if the hover cell is above or
     * below the bounds of the listview. If so, the listview does an appropriate
     * upward or downward smooth scroll so as to reveal new items.
     */
    public boolean handleMobileCellScroll(Rect r) {
      int offset       = computeVerticalScrollOffset();
      int height       = getHeight();
      int extent       = computeVerticalScrollExtent();
      int range        = computeVerticalScrollRange();
      int hoverViewTop = r.top;
      int hoverHeight  = r.height();

      if ((hoverViewTop <= 0) && (offset > 0)) {
        smoothScrollBy(-mSmoothScrollAmountAtEdge, 0);

        return true;
      }

      if ((hoverViewTop + hoverHeight >= height) && (offset + extent) < range) {
        smoothScrollBy(mSmoothScrollAmountAtEdge, 0);

        return true;
      }

      return false;
    }

    public void startListening() {
      setOnItemLongClickListener(mDraggingItemLongClickListener);
      setOnScrollListenerEx(mDraggingScrollListener);
    }

    public void stopListening() {
      setOnItemLongClickListener(savedLongClickListener);
      setOnScrollListenerEx(ListViewEx.this);
    }

    /**
     * dispatchDraw gets invoked when all the child views are about to be drawn.
     * By overriding this method, the hover cell (BitmapDrawable) can be drawn
     * over the listview's items whenever the listview is redrawn.
     */
    protected void dispatchDraw(Canvas canvas) {
      if (mHoverCell != null) {
        mHoverCell.draw(canvas);
      }
    }

    protected boolean onTouchEvent(MotionEvent event) {
      switch(event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN :
          mDownX           = (int) event.getX();
          mDownY           = (int) event.getY();
          mActivePointerId = event.getPointerId(0);

          break;

        case MotionEvent.ACTION_MOVE :
          if (mActivePointerId == INVALID_POINTER_ID) {
            break;
          }

          int pointerIndex = event.findPointerIndex(mActivePointerId);

          mLastEventY = (int) event.getY(pointerIndex);

          int deltaY = mLastEventY - mDownY;

          if (mCellIsMobile) {
            mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left,
                                             mHoverCellOriginalBounds.top + deltaY + mTotalOffset);
            mHoverCell.setBounds(mHoverCellCurrentBounds);
            invalidate();
            handleCellSwitch();
            mIsMobileScrolling = false;
            handleMobileCellScroll();

            return false;
          }

          break;

        case MotionEvent.ACTION_UP :
          touchEventsEnded();

          break;

        case MotionEvent.ACTION_CANCEL :
          touchEventsCancelled();

          break;

        case MotionEvent.ACTION_POINTER_UP :
          /*
           * If a multitouch event took place and the original touch dictating
           * the movement of the hover cell has ended, then the dragging event
           * ends and the hover cell is animated to its corresponding position
           * in the listview.
           */
          pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                         >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

          final int pointerId = event.getPointerId(pointerIndex);

          if (pointerId == mActivePointerId) {
            touchEventsEnded();
          }

          break;

        default :
          break;
      }

      return true;
    }

    /**
     * Creates the hover cell with the appropriate bitmap and of appropriate
     * size. The hover cell's BitmapDrawable is drawn on top of the bitmap every
     * single time an invalidate call is made.
     */
    private BitmapDrawable getAndAddHoverView(View v) {
      int            w        = v.getWidth();
      int            h        = v.getHeight();
      int            top      = v.getTop();
      int            left     = v.getLeft();
      Bitmap         b        = getBitmapWithBorder(v);
      BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

      mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
      mHoverCellCurrentBounds  = new Rect(mHoverCellOriginalBounds);
      drawable.setBounds(mHoverCellCurrentBounds);

      return drawable;
    }

    /** Returns a bitmap showing a screenshot of the view passed in. */
    private Bitmap getBitmapFromView(View v) {
      Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);

      v.draw(canvas);

      return bitmap;
    }

    /** Draws a black border over the screenshot of the view passed in. */
    private Bitmap getBitmapWithBorder(View v) {
      Bitmap bitmap = getBitmapFromView(v);
      Canvas can    = new Canvas(bitmap);
      Rect   rect   = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      Paint  paint  = new Paint();

      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeWidth(LINE_THICKNESS);
      paint.setColor(Color.BLACK);
      can.drawBitmap(bitmap, 0, 0, null);
      can.drawRect(rect, paint);

      return bitmap;
    }

    /**
     * This method determines whether the hover cell has been shifted far enough
     * to invoke a cell swap. If so, then the respective cell swap candidate is
     * determined and the data set is changed. Upon posting a notification of
     * the data set change, a layout is invoked to place the cells in the right
     * place. Using a ViewTreeObserver and a corresponding OnPreDrawListener, we
     * can offset the cell being swapped to where it previously was and then
     * animate it to its new position.
     */
    @SuppressLint("NewApi")
    private void handleCellSwitch() {
      final int deltaY      = mLastEventY - mDownY;
      int       deltaYTotal = mHoverCellOriginalBounds.top + mTotalOffset + deltaY;
      View      belowView   = getViewForRow(mBelowItemId);
      View      mobileView  = getViewForRow(mMobileItemId);
      View      aboveView   = getViewForRow(mAboveItemId);
      boolean   isBelow     = (belowView != null) && (deltaYTotal > belowView.getTop());
      boolean   isAbove     = (aboveView != null) && (deltaYTotal < aboveView.getTop());

      if (isBelow || isAbove) {
        final int switchItemID = isBelow
                                 ? mBelowItemId
                                 : mAboveItemId;
        View      switchView   = isBelow
                                 ? belowView
                                 : aboveView;
        final int originalItem = getPositionForView(mobileView);

        if (switchView == null) {
          updateNeighborViewsForID(mMobileItemId);

          return;
        }

        int newPos = getPositionForView(switchView);

        move(originalItem, newPos);
        mMobileItemId = newPos;
        mDownY        = mLastEventY;

        final int switchViewStartTop = switchView.getTop();

        mobileView.setVisibility(View.VISIBLE);
        switchView.setVisibility(View.INVISIBLE);
        updateNeighborViewsForID(mMobileItemId);

        final ViewTreeObserver observer = getViewTreeObserver();

        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
          @Override
          @SuppressLint("NewApi")
          public boolean onPreDraw() {
            observer.removeOnPreDrawListener(this);

            View switchView = getViewForRow(switchItemID);

            mTotalOffset += deltaY;

            int switchViewNewTop = switchView.getTop();
            int delta            = switchViewStartTop - switchViewNewTop;

            switchView.setTranslationY(delta);

            ObjectAnimator animator = ObjectAnimator.ofFloat(switchView, View.TRANSLATION_Y, 0);

            animator.setDuration(MOVE_DURATION);
            animator.start();

            return true;
          }
        });
      }
    }

    /**
     * Determines whether this listview is in a scrolling state invoked by the
     * fact that the hover cell is out of the bounds of the listview;
     */
    private void handleMobileCellScroll() {
      mIsMobileScrolling = handleMobileCellScroll(mHoverCellCurrentBounds);
    }

    /**
     * Resets all the appropriate fields to a default state.
     */
    private void touchEventsCancelled() {
      View mobileView = getViewForRow(mMobileItemId);

      mAboveItemId  = INVALID_ID;
      mMobileItemId = INVALID_ID;
      mBelowItemId  = INVALID_ID;

      if (mobileView != null) {
        mobileView.setVisibility(VISIBLE);
      }

      mHoverCell = null;
      invalidate();
      mCellIsMobile      = false;
      mIsMobileScrolling = false;
      mActivePointerId   = INVALID_POINTER_ID;
    }

    /**
     * Resets all the appropriate fields to a default state while also animating
     * the hover cell back to its correct location.
     */
    private void touchEventsEnded() {
      // final View mobileView = getViewForRow(mMobileItemId);
      //
      // if (mCellIsMobile || mIsWaitingForScrollFinish) {
      // mCellIsMobile = false;
      // mIsWaitingForScrollFinish = false;
      // mIsMobileScrolling = false;
      // mActivePointerId = INVALID_POINTER_ID;
      //
      // // If the autoscroller has not completed scrolling, we need to wait for
      // it to
      // // finish in order to determine the final location of where the hover
      // cell
      // // should be animated to.
      // if (mScrollState != OnScrollListener.SCROLL_STATE_IDLE) {
      // mIsWaitingForScrollFinish = true;
      //
      // return;
      // }
      //
      // mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left,
      // mobileView.getTop());
      //
      // ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(mHoverCell,
      // "bounds", sBoundEvaluator,
      // mHoverCellCurrentBounds);
      //
      // hoverViewAnimator.addUpdateListener(new
      // ValueAnimator.AnimatorUpdateListener() {
      // @Override
      // public void onAnimationUpdate(ValueAnimator valueAnimator) {
      // invalidate();
      // }
      // });
      // hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
      // @Override
      // public void onAnimationStart(Animator animation) {
      // setEnabled(false);
      // }
      // @Override
      // public void onAnimationEnd(Animator animation) {
      // mAboveItemId = INVALID_ID;
      // mMobileItemId = INVALID_ID;
      // mBelowItemId = INVALID_ID;
      // mobileView.setVisibility(VISIBLE);
      // mHoverCell = null;
      // setEnabled(true);
      // invalidate();
      // }
      // });
      // hoverViewAnimator.start();
      // } else {
      // touchEventsCancelled();
      // }
      touchEventsCancelled();
    }

    /**
     * Stores a reference to the views above and below the item currently
     * corresponding to the hover cell. It is important to note that if this
     * item is either at the top or bottom of the list, mAboveItemId or
     * mBelowItemId may be invalid.
     */
    private void updateNeighborViewsForID(int itemID) {
      mAboveItemId = (itemID < 0)
                     ? -1
                     : itemID - 1;
      mBelowItemId = (itemID + 1 >= listModel.size())
                     ? -1
                     : itemID + 1;
    }
  }


  class RowEditingGestureListener implements GestureDetector.OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
      return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      int  row = pointToPosition((int) e1.getX(), (int) e1.getY());
      View v   = (row == -1)
                 ? null
                 : getViewForRow(row);

      if (v instanceof ListRowContainer) {
        ListRowContainer lc = (ListRowContainer) v;

        if ((editingRow == -1) && (e1.getX() > e2.getX())) {
          editingRow    = row;
          lastEditedRow = row;
          clearChoices();

          if (rowEditModeListener != null) {
            rowEditModeListener.itemWillExpand(new ExpansionEvent(editingComponent, ExpansionEvent.Type.WILL_EXPAND));
          }

          lc.showRowEditingComponent(editingComponent, true, centerEditingComponentVertically);
        }
      }

      return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
      return false;
    }
  }
}
