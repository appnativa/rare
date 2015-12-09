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
import com.appnativa.rare.platform.swing.ui.DataItemListModel;
import com.appnativa.rare.platform.swing.ui.SelectiveListSelectionModel;
import com.appnativa.rare.platform.swing.ui.iTooltipHandler;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.ComponentFactory;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.dnd.Autoscroll;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JToolTip;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.MouseInputListener;
import javax.swing.text.Position;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class JListEx extends JList implements Autoscroll {
  protected boolean          overlappingTooltip = true;
  protected boolean          disableNextMatch;
  protected String           logicalCellWidth;
  protected String           logicalRowHeight;
  protected int              minimumVisibleRowCount;
  protected iTooltipHandler  tooltipHandler;
  private boolean            showLastDivider = true;
  private UIColor            alternatingRowColor;
  private UIColor            dividerLineColor;
  private float              dividerSize;
  private Stroke             dividerStroke;
  private Column             itemDescription;
  private MouseInputListener mouseOverideListener;
  private boolean            popupList;
  private int                minRowHeight;
  private ListView           listView;

  public JListEx() {
    this(new DataItemListModel());
  }

  public JListEx(ListModel lm) {
    super(lm);
    overlappingTooltip = Platform.getAppContext().isOverlapAutoToolTips();
    super.setBorder(BorderUtils.EMPTY_BORDER);
    setOpaque(false);
  }

  @Override
  public void autoscroll(Point cursorLocn) {
    SwingHelper.defaultAutoScroll(this, cursorLocn);
  }

  @Override
  public JToolTip createToolTip() {
    if (tooltipHandler != null) {
      return tooltipHandler.createToolTip(Platform.findWidgetForComponent(this));
    }

    return JToolTipEx.createNewToolTip(this, isOverlappingTooltip());
  }
  
  public void setAlternatingRowColor(UIColor color) {
    alternatingRowColor = color;
  }

  public void setAsPopupList(boolean popupList) {
    this.popupList = popupList;

    if (popupList) {
      showLastDivider = false;
    }
  }

  public void setDisableNextMatch(boolean disableNextMatch) {
    this.disableNextMatch = disableNextMatch;
  }

  public void setDividerLineColor(UIColor dividerLineColor) {
    this.dividerLineColor = dividerLineColor;
  }

  public void setDividerStroke(Stroke dividerStroke) {
    this.dividerStroke = dividerStroke;
    dividerSize        = 0;

    if (dividerStroke instanceof BasicStroke) {
      dividerSize = ((BasicStroke) dividerStroke).getLineWidth();
    }
  }

  @Override
  public void setFont(Font font) {
    super.setFont(font);

    if (logicalRowHeight != null) {
      setFixedCellHeight(ScreenUtils.toPlatformPixelHeight(logicalRowHeight, Platform.getPlatformComponent(this),
              getHeight()));
    }

    if (logicalCellWidth != null) {
      setFixedCellWidth(ScreenUtils.toPlatformPixelWidth(logicalRowHeight, Platform.getPlatformComponent(this),
              getWidth()));
    }
  }

  public void setItemDescription(Column itemDescription) {
    this.itemDescription = itemDescription;
  }

  public void setMinimumVisibleRowCount(int rows) {
    minimumVisibleRowCount = rows;
  }

  @Override
  public void setModel(ListModel model) {
    super.setModel(model);

    ListSelectionModel sm = getSelectionModel();

    if ((sm instanceof SelectiveListSelectionModel) && (model instanceof iPlatformListDataModel)) {
      ((SelectiveListSelectionModel) sm).setListModel((iPlatformListDataModel) model);
    }
  }

  public void setMouseOverideListener(MouseInputListener mouseOverideListener) {
    this.mouseOverideListener = mouseOverideListener;
  }

  public void setOverlappingTooltip(boolean overlappingTooltip) {
    this.overlappingTooltip = overlappingTooltip;
  }

  @Override
  public void setSelectionModel(ListSelectionModel selectionModel) {
    ListModel model = getModel();

    super.setSelectionModel(selectionModel);

    if ((selectionModel instanceof SelectiveListSelectionModel) && (model instanceof iPlatformListDataModel)) {
      ((SelectiveListSelectionModel) selectionModel).setListModel((iPlatformListDataModel) model);
    }
  }

  public void setShowLastDivider(boolean showLastDivider) {
    this.showLastDivider = showLastDivider;
  }

  /**
   * Sets the tooltip handler
   * @param th the tooltip handler to set
   */
  public void setTooltipHandler(iTooltipHandler th) {
    this.tooltipHandler = th;
  }

  public UIColor getAlternatingRowColor() {
    return alternatingRowColor;
  }

  @Override
  public Insets getAutoscrollInsets() {
    return SwingHelper.defaultGetAutoscrollInsets(this);
  }

  public UIColor getDividerLineColor() {
    return dividerLineColor;
  }

  public float getDividerSize() {
    return dividerSize;
  }

  public Stroke getDividerStroke() {
    return dividerStroke;
  }

  public Column getItemDescription() {
    return itemDescription;
  }

  public EventListenerList getListenerList() {
    return listenerList;
  }

  @Override
  public Dimension getMinimumSize() {
    Dimension d = super.getMinimumSize();

    if (minimumVisibleRowCount > 0) {
      int fixedCellHeight = getFixedCellHeight();

      if (fixedCellHeight < 1) {
        fixedCellHeight = FontUtils.getDefaultLineHeight();
      }

      int h = fixedCellHeight * minimumVisibleRowCount;

      if (d.height < h) {
        d.height = h;
      }
    }

    return d;
  }

  public MouseListener getMouseOverideListener() {
    return mouseOverideListener;
  }

  @Override
  public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
    return disableNextMatch
           ? -1
           : super.getNextMatch(prefix, startIndex, bias);
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    if (getLayoutOrientation() != VERTICAL) {
      return getPreferredSize();
    }

    Dimension d;
    Insets    insets          = getInsets();
    int       dx              = insets.left + insets.right;
    int       dy              = insets.top + insets.bottom;
    int       visibleRowCount = Math.max(getVisibleRowCount(), minimumVisibleRowCount);
    int       fixedCellWidth  = getFixedCellWidth();
    int       fixedCellHeight = getFixedCellHeight();

    if (fixedCellHeight == -1) {
      fixedCellHeight = getLisView().getRowHeight();
    }

    if ((fixedCellWidth > 0) && (fixedCellHeight > 0)) {
      int width  = fixedCellWidth + dx;
      int height = (visibleRowCount * fixedCellHeight) + dy;

      return new Dimension(width, height);
    } else if (getModel().getSize() > 0) {
      int len = getModel().getSize();
      if(visibleRowCount>1) {
        len=Math.max(visibleRowCount+1, len);
      }

      int width = getPreferredSize().width;
      int height;

      if (fixedCellHeight > 0) {
        height = fixedCellHeight * len;
      } else {
        Rectangle r = getCellBounds(0, len - 1);

        if (r != null) {
          height = r.height + dy;
        } else {
          // Will only happen if UI null, shouldn't matter what we return
          height = 1;
        }
      }

      d = new Dimension(width, height);
    } else {
      fixedCellWidth  = (fixedCellWidth > 0)
                        ? fixedCellWidth
                        : 50;
      fixedCellHeight = (fixedCellHeight > 0)
                        ? fixedCellHeight
                        : FontUtils.getDefaultLineHeight();
      d               = new Dimension(fixedCellWidth, fixedCellHeight * visibleRowCount);
    }

    Integer w = (Integer) getClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE);
    Integer h = (Integer) getClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE);

    if ((w != null) && (w.intValue() > 0)) {
      d.width = w;
    }

    if ((h != null) && (h.intValue() > 0)) {
      d.height = h;
    }

    return d;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    if (SwingHelper.isVerticalScrollBarHiddenAlways(listView)) {
      return true;
    }

    return super.getScrollableTracksViewportHeight();
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    if (SwingHelper.isHorizontalScrollBarHiddenAlways(listView)) {
      return true;
    }

    return super.getScrollableTracksViewportWidth();
  }

  @Override
  public Point getToolTipLocation(MouseEvent event) {
    if (tooltipHandler != null) {
      return tooltipHandler.getToolTipLocation(Platform.findWidgetForComponent(this), event);
    }

    if (!isOverlappingTooltip()) {
      return null;
    }

    Rectangle rect = (Rectangle) getToolTipText(this, locationToIndex(event.getPoint()), true, null,
                       isOverlappingTooltip());

    return (rect == null)
           ? null
           : rect.getLocation();
  }

  @Override
  public String getToolTipText(MouseEvent event) {
    if (tooltipHandler != null) {
      return tooltipHandler.getToolTipText(Platform.findWidgetForComponent(this), event);
    }

    return (String) getToolTipText(this, locationToIndex(event.getPoint()), false, null, isOverlappingTooltip());
  }

  /**
   * Gets the tooltip handler
   *
   * @return the tooltip handler
   */
  public iTooltipHandler getTooltipHandler() {
    return tooltipHandler;
  }

  public boolean hasValue() {
    return getModel().getSize() > 0;
  }

  public boolean isDisableNextMatch() {
    return disableNextMatch;
  }

  @Override
  public boolean isOpaque() {
    return false;
  }

  public boolean isOverlappingTooltip() {
    return overlappingTooltip && (getLayoutOrientation() == VERTICAL);
  }

  public boolean isPopupList() {
    return popupList;
  }

  public boolean isShowLastDivider() {
    return showLastDivider;
  }

  static boolean isMenuShortcutKeyDown(InputEvent event) {
    return (event.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
  }

  @Override
  protected void paintComponent(Graphics g) {
    int       start = getFirstVisibleIndex();
    int       end   = getLastVisibleIndex();
    Rectangle r     = null;
    int       i     = start;
    int       width = getWidth();
    int       rh    = (listView == null)
                      ? getFixedCellHeight()
                      : listView.getRowHeight();
    int       y;
    int       vheight = getHeight();
    int       count   = getModel().getSize();

    if (rh < 1) {
      rh = FontUtils.getDefaultLineHeight();
    }

    if (alternatingRowColor != null) {
      g.setColor(alternatingRowColor);

      while((i > -1) && (i <= end)) {
        if (i % 2 == 1) {
          r = getCellBounds(i, i);
          g.fillRect(r.x, r.y, r.width, r.height);
        }

        i++;
      }

      if (i==0) {
        y = 0;
      } else {
        r = getCellBounds(i - 1, i - 1);
        y = r==null ? 0 : r.y + r.height;
      }

      i = count;

      PaintBucket pb = null;
      ListModel   m  = getModel();

      if (m instanceof iPlatformListDataModel) {
        Column c = ((iPlatformListDataModel) m).getColumnDescription();

        pb = (c == null)
             ? null
             : c.getItemPainter();
      }

      if (itemDescription != null) {
        pb = itemDescription.getItemPainter();
      }

      SwingGraphics sg = null;

      if (pb != null) {
        sg = new SwingGraphics(g, this);
      }

      while(y < vheight) {
        if (i % 2 == 1) {
          g.fillRect(0, y, width, rh);
        }

        if (pb != null) {
          UIComponentPainter.paint(sg, 0, y, width, rh, pb);
        }

        y += rh;
        i++;
      }

      if (sg != null) {
        sg.clear();
      }
    }

    super.paintComponent(g);

    if (dividerLineColor != null) {
      Graphics2D g2 = (Graphics2D) g;

      g2.setStroke(dividerStroke);
      g2.setColor(dividerLineColor);
      i = start;
      r = null;
      y = 0;

      while((i > -1) && (i <= end)) {
        r = getCellBounds(i, i);
        y = r.y + r.height;

        if (showLastDivider || (y + rh - 1 < vheight)) {
          g2.drawLine(r.x, y - 1, r.width, y - 1);
        }

        i++;
      }

      while(y < vheight) {
        y += rh;

        if (showLastDivider || (y + rh - 1 < vheight)) {
          g2.drawLine(0, y - 1, width, y - 1);
        }
      }
    }
  }

  @Override
  protected void processMouseEvent(MouseEvent e) {
    if (popupList && isMenuShortcutKeyDown(e)) {
      // Fix for 4234053. Filter out the Control Key from the list.
      // ie., don't allow CTRL key deselection.
      Toolkit toolkit = Toolkit.getDefaultToolkit();

      e = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(),
                         e.getModifiers() ^ toolkit.getMenuShortcutKeyMask(), e.getX(), e.getY(), e.getXOnScreen(),
                         e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), MouseEvent.NOBUTTON);
    }

    if (mouseOverideListener != null) {
      int id = e.getID();

      switch(id) {
        case MouseEvent.MOUSE_PRESSED :
          mouseOverideListener.mousePressed(e);

          break;

        case MouseEvent.MOUSE_RELEASED :
          mouseOverideListener.mouseReleased(e);

          break;

        case MouseEvent.MOUSE_CLICKED :
          mouseOverideListener.mouseClicked(e);

          break;

        case MouseEvent.MOUSE_EXITED :
          mouseOverideListener.mouseExited(e);

          break;

        case MouseEvent.MOUSE_ENTERED :
          mouseOverideListener.mouseEntered(e);

          break;
      }

      if (e.isConsumed()) {
        return;
      }
    }

    super.processMouseEvent(e);
  }

  @Override
  protected void processMouseMotionEvent(MouseEvent e) {
    if (mouseOverideListener != null) {
      int id = e.getID();

      switch(id) {
        case MouseEvent.MOUSE_DRAGGED :
          mouseOverideListener.mouseDragged(e);

          break;

        case MouseEvent.MOUSE_MOVED :
          mouseOverideListener.mouseMoved(e);

          break;
      }

      if (e.isConsumed()) {
        return;
      }
    }

    super.processMouseMotionEvent(e);
  }

  /**
   * Gets the tooltip location for the specified row
   *
   * @param list the list
   * @param row the row
   * @param location true to return the location of an autogenerated tooltip (null if none); false to return the actual tooltip
   * @param r the cell renderer
   * @param overlapping true for overlapping tooltip; false otherwise
   * @return the tooltip location
   */
  protected static Object getToolTipText(JList list, int row, boolean location, ListCellRenderer r,
          boolean overlapping) {
    if (row == -1) {
      return null;
    }

    RenderableDataItem di  = (RenderableDataItem) list.getModel().getElementAt(row);
    CharSequence       s   = di.getTooltip();
    String             tip = (s == null)
                             ? null
                             : s.toString();

    if ((tip != null) && (tip.length() == 0)) {
      tip = null;
    }

    if ((tip != null) || (location &&!overlapping)) {
      return location
             ? null
             : ComponentFactory.resolveToolTip(list, di, tip);
    }

    Rectangle cellRect = list.getCellBounds(row, row);
    Rectangle vRect    = list.getVisibleRect();

    if (location ||!vRect.contains(cellRect)) {
      if (r == null) {
        r = list.getCellRenderer();
      }

      Component component = r.getListCellRendererComponent(list, di, row, false, false);

      if ((component instanceof LabelView) && ((LabelView) component).isHTMLText()) {
        return null;
      }

      if (!location) {
        Rectangle rect = new Rectangle(component.getPreferredSize());

        rect.x = cellRect.x;
        rect.y = cellRect.y;

        if (vRect.contains(rect)) {
          return null;
        }
      }

      return ComponentFactory.createToolTipObject(list, location, component, cellRect, di.toString(), overlapping, 1);
    }

    return null;
  }

  public void setListView(ListView listView) {
    this.listView = listView;
  }

  public ListView getLisView() {
    return listView;
  }

  public int getMinRowHeight() {
    return minRowHeight;
  }

  public void setMinRowHeight(int minRowHeight) {
    this.minRowHeight = minRowHeight;
  }
}
