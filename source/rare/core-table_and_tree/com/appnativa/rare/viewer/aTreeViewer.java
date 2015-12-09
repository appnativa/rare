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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.ui.tree.aDataItemTreeModel;
import com.appnativa.rare.util.ListHelper;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.SNumber;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * A viewer that presents a hierarchical view of information as an outline that
 * can be collapsed and expanded.
 *
 * @author Don DeCoteau
 */
public abstract class aTreeViewer extends aListViewer implements iTreeHandler {
  protected int                     currentLevel = 1;
  protected boolean                 rootSetInCfg = false;
  protected iPlatformIcon           closedIcon;
  protected RenderableDataItem      currentItem;
  protected transient iPlatformIcon disabledClosedIcon;
  protected transient iPlatformIcon disabledLeafIcon;
  protected transient iPlatformIcon disabledOpenIcon;
  protected boolean                 expandAll;

  /** iPlatformIcon used to show leaf nodes. */
  protected transient iPlatformIcon leafIcon;
  protected transient iPlatformIcon openIcon;
  protected boolean                 parsing;

  /** the tree component instance */
  protected iTreeHandler treeHandler;

  /** the list model */
  protected aDataItemTreeModel treeModel;

  /**
   * Constructs a new instance
   *
   */
  public aTreeViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the parent
   */
  public aTreeViewer(iContainer parent) {
    super(parent);
    widgetType             = WidgetType.Tree;
    initiallySelectedIndex = -1;
  }

  @Override
  public void addChild(RenderableDataItem child) {
    treeHandler.addChild(child);
  }

  @Override
  public void addChild(int row, RenderableDataItem child) {
    treeHandler.addChild(row, child);
  }

  @Override
  public void addChild(RenderableDataItem row, RenderableDataItem child) {
    treeHandler.addChild(row, child);
  }

  @Override
  public void addChildren(int row, List<RenderableDataItem> children) {
    treeHandler.addChildren(row, children);
  }

  @Override
  public void addChildren(RenderableDataItem row, List<RenderableDataItem> children) {
    treeHandler.addChildren(row, children);
  }

  @Override
  public void addExpandedListener(iExpandedListener l) {
    treeHandler.addExpandedListener(l);
  }

  @Override
  public void addExpansionListener(iExpansionListener l) {
    treeHandler.addExpansionListener(l);
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    synchronized(widgetType) {
      if (isDisposed()) {
        return;
      }

      Object o = row.getModelData();
      int    l = 1;

      if (o instanceof MutableInteger) {
        MutableInteger level = (MutableInteger) o;

        row.setModelData(null);
        l = level.intValue();

        if ((l == 0) && (this.getItemCount() == 0)) {
          setRootItem(row);

          return;
        }

        if (l < 1) {
          l = 1;
        }
      }

      if ((l < 2) || (currentItem == null)) {
        addEx(row);
        currentLevel = 1;
      } else {
        if (currentLevel == l) {
          currentItem.getParentItem().add(row);
        } else if (currentLevel < l) {
          currentItem.add(row);
          currentLevel++;
        } else {
          while(currentLevel > l) {
            currentItem = currentItem.getParentItem();
            currentLevel--;
          }

          currentItem.getParentItem().add(row);
        }
      }

      currentItem = row;
    }
  }

  /**
   * Cancels tree editing (if editing)
   */
  public abstract void cancelEditing();

  @Override
  public void clear() {
    if (!Platform.isUIThread()) {
      ListHelper.runLater(this, ListHelper.RunType.CLEAR);

      return;
    }

    listComponent.clear();
    rootSetInCfg = false;
  }

  @Override
  public void clearContents() {
    super.clearContents();

    if (rootSetInCfg) {
      treeHandler.clearRootNode();
    } else {
      clear();
    }
  }

  @Override
  public void clearRootNode() {
    treeHandler.clearRootNode();
  }

  @Override
  public void collapseAll() {
    treeHandler.collapseAll();
  }

  @Override
  public void collapseRow(int row) {
    treeHandler.collapseRow(row);
  }

  @Override
  public void collapseRow(RenderableDataItem item) {
    treeHandler.collapseRow(item);
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((Tree) vcfg);
    handleDataURL(vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  /**
   * Creates a new tree widget
   *
   * @param parent
   *          the parent
   * @param cfg
   *          the configuration
   *
   * @return the tree widget
   */
  public static TreeViewer create(iContainer parent, Tree cfg) {
    TreeViewer widget = new TreeViewer(parent);

    widget.configure(cfg);

    return widget;
  }

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    if ((selectionModelGroup != null) && (selectionModel != null)) {
      selectionModelGroup.remove(selectionModel);
    }

    super.dispose();

    if (listModel != null) {
      listModel.dispose();
    }

    if (treeModel != null) {
      treeModel.dispose();
    }

    treeModel           = null;
    selectionModelGroup = null;
    listModel           = null;
    treeHandler         = null;
    closedIcon          = null;
    currentItem         = null;
    disabledClosedIcon  = null;
    disabledLeafIcon    = null;
    disabledOpenIcon    = null;
    leafIcon            = null;
    openIcon            = null;
  }

  @Override
  public void expandAll() {
    treeHandler.expandAll();
  }

  @Override
  public void expandRow(int row) {
    treeHandler.expandRow(row);
  }

  @Override
  public void expandRow(RenderableDataItem item) {
    treeHandler.expandRow(item);
  }

  @Override
  public void refreshItems() {
    if (!Platform.isUIThread()) {
      ListHelper.runLater(this, ListHelper.RunType.REFRESH);

      return;
    }

    if (tempList != null) {
      treeModel.setAll(tempList);
      tempList.clear();
      tempList = null;

      if (sorter != null) {
        treeModel.sort(sorter);
      }
    } else {
      treeModel.refreshItems();
    }
  }

  @Override
  public void removeExpandedListener(iExpandedListener l) {
    if (treeHandler != null) {
      treeHandler.removeExpandedListener(l);
    }
  }

  @Override
  public void removeExpansionListener(iExpansionListener l) {
    if (treeHandler != null) {
      treeHandler.removeExpansionListener(l);
    }
  }

  /**
   * Notifies the tree that the contents of the specified row has changed
   *
   * @param index
   *          the index of the row that changed
   */
  @Override
  public void rowChanged(int index) {
    treeModel.nodeChanged(get(index));
  }

  /**
   * Notifies the tree that the contents of the specified row has changed
   *
   * @param item
   *          the instance of the row that changed
   */
  @Override
  public void rowChanged(RenderableDataItem item) {
    treeModel.nodeChanged(item);
  }

  /**
   * Notifies the tree that the structure of the specified row has changed
   *
   * @param index
   *          the index of the row that changed
   */
  public void rowStructureChanged(int index) {
    treeModel.nodeStructureChanged(get(index));
  }

  /**
   * Notifies the tree that the structure of the specified row has changed
   *
   * @param item
   *          the instance of the row that changed
   */
  public void rowStructureChanged(RenderableDataItem item) {
    treeModel.nodeStructureChanged(item);
  }

  @Override
  public void sort(Comparator comparator) {
    treeHandler.sort(comparator);
  }

  @Override
  public void sortEx(Comparator comparator) {
    treeHandler.sortEx(comparator);
  }

  @Override
  public void startedParsing() {
    parsing = true;
    super.startedParsing();
  }

  @Override
  public void toggleRow(int row) {
    treeHandler.toggleRow(row);
  }

  @Override
  public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
    treeHandler.setAutoScrollOnExpansion(autoScrollOnExpansion);
  }

  @Override
  public abstract void setAutoSizeRowsToFit(boolean autoSize);

  /**
   * Sets the icon used to represent disabled collapsed branch nodes.
   *
   * @param icon
   *          the icon
   */
  public void setDisabledFolderClosedIcon(iPlatformIcon icon) {
    this.disabledClosedIcon = icon;
  }

  /**
   * Sets the icon used to represent disabled expanded branch nodes.
   *
   * @param icon
   *          the icon
   */
  public void setDisabledFolderOpenIcon(iPlatformIcon icon) {
    this.disabledOpenIcon = icon;
  }

  /**
   * Sets the icon used to represent disabled leaf nodes.
   *
   * @param icon
   *          the icon
   */
  public void setDisabledLeafIcon(iPlatformIcon icon) {
    this.disabledLeafIcon = icon;
  }

  @Override
  public void setEditingMode(EditingMode mode) {
    treeHandler.setEditingMode(mode);
  }

  @Override
  public void setExpandableStateLocked(boolean locked) {
    treeHandler.setExpandableStateLocked(locked);
  }

  /**
   * Sets the icon used to represent collapsed branch nodes.
   *
   * @param icon
   *          the icon
   */
  public void setFolderClosedIcon(iPlatformIcon icon) {
    this.closedIcon = icon;
  }

  /**
   * Sets the icon used to represent expanded branch nodes.
   *
   * @param icon
   *          the icon
   */
  public void setFolderOpenIcon(iPlatformIcon icon) {
    this.openIcon = icon;
  }

  @Override
  public void setFromHTTPFormValue(Object value) {
    switch(submitValueType) {
      case Tree.CSubmitValue.selected_index :
      case Tree.CSubmitValue.checked_index :
        if (value == null) {
          setSelectedIndex(-1);

          return;
        }

        if (value instanceof int[]) {
          setSelectedIndexes((int[]) value);

          break;
        }

        int n = -1;

        if (value instanceof Number) {
          n = ((Number) value).intValue();
        } else {
          String s = value.toString();

          if ((s.length() > 0) && Character.isDigit(s.charAt(0))) {
            n = SNumber.intValue(s);
          }
        }

        if ((n < -1) || (n >= size())) {
          n = -1;
        }

        setSelectedIndex(n);

        break;

      case Tree.CSubmitValue.selected_linked_data :
      case Tree.CSubmitValue.checked_linked_data :
        if (value != null) {
          setSelectedIndex(indexOfLinkedData(value));
        } else {
          setSelectedIndex(-1);
        }

        break;

      case Tree.CSubmitValue.selected_value_text :
        if (value != null) {
          setSelectedIndex(indexOfValue(value));
        } else {
          setSelectedIndex(-1);
        }

        break;

      default :
        setValue(value);

        break;
    }
  }

  @Override
  public void setIndentBy(int indent) {
    treeHandler.setIndentBy(indent);
  }

  /**
   * Sets the icon used to represent leaf nodes.
   *
   * @param icon
   *          the icon
   */
  public void setLeafIcon(iPlatformIcon icon) {
    leafIcon = icon;
  }

  @Override
  public void setRootItem(RenderableDataItem item) {
    if (parsing && rootSetInCfg) {
      return;
    }

    if ((treeModel != null) && (item != null)) {
      boolean ed = treeModel.isEventsEnabled();

      try {
        treeModel.setEventsEnabled(false);
        treeModel.clearEx();
        treeModel.getRoot().copy(item);
      } finally {
        treeModel.setEventsEnabled(ed);

        if (ed &&!parsing) {
          treeModel.structureChanged();

          if (!isRootNodeCollapsible()) {
            treeHandler.expandRow(0);
          }
        }
      }
    }
  }

  @Override
  public void setRootNodeCollapsible(boolean collapsible) {
    treeHandler.setRootNodeCollapsible(collapsible);
  }

  @Override
  public void setShowRootHandles(boolean show) {
    treeHandler.setShowRootHandles(show);
  }

  /**
   * Sets whether the root node is shown
   *
   * @param show
   *          true to show; false to hide
   */
  @Override
  public void setShowRootNode(boolean show) {
    treeHandler.setShowRootNode(show);
  }

  @Override
  public void setSingleClickToggle(boolean singleClickToggle) {
    treeHandler.setSingleClickToggle(singleClickToggle);
  }

  @Override
  public void setToggleOnTwistyOnly(boolean twistyOnly) {
    treeHandler.setToggleOnTwistyOnly(twistyOnly);
  }

  @Override
  public void setExpandAll(boolean expandAll) {
    treeHandler.setExpandAll(expandAll);
  }

  @Override
  public void setTreeEventsEnabled(boolean enabled) {
    if (treeHandler != null) {
      treeHandler.setTreeEventsEnabled(enabled);
    }
  }

  public abstract void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed);

  @Override
  public void setTwistyMarginOfError(int twistyMarginOfError) {
    treeHandler.setTwistyMarginOfError(twistyMarginOfError);
  }

  @Override
  public void setParentItemsSelectable(boolean parentItemsSelectable) {
    treeHandler.setParentItemsSelectable(parentItemsSelectable);
  }

  @Override
  public boolean isParentItemsSelectable() {
    return treeHandler.isParentItemsSelectable();
  }

  /**
   * Returns the icon used to represent collapsed disabled.branch nodes
   *
   * @return the icon
   */
  public iPlatformIcon getDisabledFolderClosedIcon() {
    if ((disabledClosedIcon == null) && (closedIcon != null)) {
      disabledClosedIcon = UIImageHelper.createDisabledIcon(closedIcon);
    }

    return disabledClosedIcon;
  }

  /**
   * Returns the icon used to represent expanded.disabled branch nodes
   *
   * @return the icon
   */
  public iPlatformIcon getDisabledFolderOpenIcon() {
    if ((disabledOpenIcon == null) && (openIcon != null)) {
      disabledOpenIcon = UIImageHelper.createDisabledIcon(openIcon);
    }

    return disabledOpenIcon;
  }

  /**
   * Returns the icon used to represent disabled leaf nodes
   *
   * @return the icon
   */
  public iPlatformIcon getDisabledLeafIcon() {
    if ((disabledLeafIcon == null) && (leafIcon != null)) {
      disabledLeafIcon = UIImageHelper.createDisabledIcon(leafIcon);
    }

    return disabledLeafIcon;
  }

  @Override
  public int getExpandableColumn() {
    return 0;
  }

  /**
   * Returns the icon used to represent collapsed branch nodes
   *
   * @return the icon
   */
  public iPlatformIcon getFolderClosedIcon() {
    return closedIcon;
  }

  /**
   * Returns the icon used to represent expanded.branch nodes
   *
   * @return the icon
   */
  public iPlatformIcon getFolderOpenIcon() {
    return openIcon;
  }

  @Override
  public Object getHTTPFormValue() {
    if (!hasSelection()) {
      return null;
    }

    switch(submitValueType) {
      case Tree.CSubmitValue.selected_index :
      case Tree.CSubmitValue.checked_index :
        if (!selectAllAllowed) {
          return getSelectedIndex();
        }

        return getSelectedIndexes();

      case Tree.CSubmitValue.selected_linked_data :
      case Tree.CSubmitValue.checked_linked_data :
        return this.getSelectionData();

      case Tree.CSubmitValue.selected_value_text :
        if (!selectAllAllowed) {
          return this.getSelectionAsString();
        }

        return getSelectionAsString();

      default :
        if (!selectAllAllowed) {
          return this.getSelection();
        }

        return getSelections();
    }
  }

  @Override
  public iPlatformIcon getIcon() {
    if (displayIcon != null) {
      return displayIcon;
    }

    RenderableDataItem item = this.getSelectedItem();

    if ((item != null) && (item.getIcon() != null)) {
      return item.getIcon();
    }

    if (this.itemDescription != null) {
      return itemDescription.getIcon();
    }

    return null;
  }

  /**
   * Returns the icon used to represent leaf nodes.
   *
   * @return the icon
   */
  public iPlatformIcon getLeafIcon() {
    return leafIcon;
  }

  @Override
  public RenderableDataItem getParent(int index) {
    return treeHandler.getParent(index);
  }

  /**
   * Gets the parent row index of the item at the specified index
   *
   * @param index
   *          the index of the item to get the parent of
   *
   * @return the parent item index or -1 if the item is a top level item
   */
  public int getParentIndex(int index) {
    RenderableDataItem item = treeHandler.getParent(index);

    if (item == null) {
      return -1;
    }

    while(index > 0) {
      index--;

      if (get(index) == item) {
        return index;
      }
    }

    return -1;
  }

  @Override
  public List<RenderableDataItem> getRawRows() {
    return treeHandler.getRawRows();
  }

  /**
   * Gets the root item for the tree
   *
   * @return the root item for the tree
   */
  @Override
  public RenderableDataItem getRootItem() {
    return treeHandler.getRootItem();
  }

  @Override
  public int getTwistyMarginOfError() {
    return treeHandler.getTwistyMarginOfError();
  }

  @Override
  public boolean isAutoScrollOnExpansion() {
    return treeHandler.isAutoScrollOnExpansion();
  }

  @Override
  public boolean isExpandableStateLocked() {
    return treeHandler.isExpandableStateLocked();
  }

  /**
   * Returns the specified tree path is editable
   *
   * @param item
   *          the item
   * @return true if it is editable; false otherwise
   */
  @Override
  public boolean isItemEditable(RenderableDataItem item) {
    return treeHandler.isItemEditable(item);
  }

  @Override
  public boolean isLeafItem(int index) {
    return treeHandler.isLeafItem(index);
  }

  /**
   * Returns whether the root node can be collapsed
   *
   * @return true if the root node can be collapsed; false otherwise
   */
  @Override
  public boolean isRootNodeCollapsible() {
    return treeHandler.isRootNodeCollapsible();
  }

  @Override
  public boolean isRowExpanded(int row) {
    return treeHandler.isRowExpanded(row);
  }

  @Override
  public boolean isRowExpanded(RenderableDataItem item) {
    return treeHandler.isRowExpanded(item);
  }

  @Override
  public boolean isSingleClickToggle() {
    return treeHandler.isSingleClickToggle();
  }

  /**
   * Gets whether to toggle node collapse/expansion only when the twisty is
   * clicked. If true then clicking anywhere other than the twisty on an
   * expandable node will result if the same behavior as a non expandable node
   *
   * @return true if the node is toggled only when the twisty is clicked; false
   *         otherwise
   */
  @Override
  public boolean isToggleOnTwistyOnly() {
    return treeHandler.isToggleOnTwistyOnly();
  }

  /**
   * Configures the viewer (does not fire the configure event)
   *
   * @param cfg
   *          the viewer configuration
   */
  protected void configureEx(Tree cfg) {
    createModelAndComponents(cfg);
    configureEx(cfg, true, true, true);

    if (!cfg.focusPainted.spot_hasValue() ||!cfg.focusPainted.booleanValue()) {
      formComponent.setFocusPainted(true);
    }

    this.setSubItems(listModel);

    iPlatformIcon icon = getIcon(cfg.leafIcon);
    iPlatformIcon dicon;

    if (icon != null) {
      setLeafIcon(icon);
    } else {
      icon = Platform.getUIDefaults().getIcon("Tree.leafIcon");

      if (icon == null) {
        setLeafIcon(Platform.getResourceAsIcon("Rare.icon.page"));
        setDisabledLeafIcon(Platform.getResourceAsIcon("Rare.icon.pageDisabled"));
      } else {
        setLeafIcon(icon);
        dicon = Platform.getUIDefaults().getIcon("Tree.disabledLeafIcon");

        if (dicon == null) {
          dicon = UIImageHelper.createDisabledIcon(icon);
        }

        setDisabledLeafIcon(dicon);
      }
    }

    icon = getIcon(cfg.folderOpenIcon);

    if (icon != null) {
      setFolderOpenIcon(icon);
    } else {
      icon = Platform.getUIDefaults().getIcon("Tree.openIcon");

      if (icon == null) {
        setFolderOpenIcon(Platform.getResourceAsIcon("Rare.icon.folderOpen"));
        setDisabledFolderOpenIcon(Platform.getResourceAsIcon("Rare.icon.folderOpenDisabled"));
      } else {
        setFolderOpenIcon(icon);
        dicon = Platform.getUIDefaults().getIcon("Tree.disabledOpenIcon");

        if (dicon == null) {
          dicon = UIImageHelper.createDisabledIcon(icon);
        }

        setDisabledFolderOpenIcon(dicon);
      }
    }

    icon = getIcon(cfg.folderClosedIcon);

    if (icon != null) {
      setFolderClosedIcon(icon);
    } else {
      icon = Platform.getUIDefaults().getIcon("Tree.closedIcon");

      if (icon == null) {
        setFolderClosedIcon(Platform.getResourceAsIcon("Rare.icon.folderClosed"));
        setDisabledFolderClosedIcon(Platform.getResourceAsIcon("Rare.icon.folderClosedDisabled"));
      } else {
        setFolderClosedIcon(icon);
        dicon = Platform.getUIDefaults().getIcon("Tree.disabledClosedIcon");

        if (dicon == null) {
          dicon = UIImageHelper.createDisabledIcon(icon);
        }

        setDisabledFolderClosedIcon(dicon);
      }
    }

    listComponent.setDeselectEventsDisabled(!cfg.deselectEventsEnabled.booleanValue());

    if (cfg.visibleRowCount.spot_valueWasSet()) {
      int n = cfg.visibleRowCount.intValue();

      listComponent.setVisibleRowCount(n);
    } else {
      listComponent.setVisibleRowCount(TableHelper.getDefaultPreferredRows());
    }

    if (cfg.getItemDescription() != null) {
      itemDescription = createColumn(cfg.getItemDescription());
      getItemRenderer().setItemDescription(itemDescription);

      if (itemDescription.getFont() != null) {
        dataComponent.setFont(itemDescription.getFont());
        formComponent.setFont(itemDescription.getFont());
      }
    }

    listModel.setWidget(this);
    listModel.setColumnDescription(itemDescription);
    expandAll = cfg.expandAll.booleanValue();

    DataItem root = cfg.getRootNode();

    if (root != null) {
      RenderableDataItem rootItem = populateItem(this, root, null);

      treeModel.setRoot(rootItem);
      rootSetInCfg = true;
    }

    if (cfg.alternatingHighlightColor.spot_valueWasSet()) {
      UIColor c = UIColorHelper.getColor(cfg.alternatingHighlightColor.getValue());

      if ((c != null) && (c != ColorUtils.TRANSPARENT_COLOR)) {
        listComponent.setAlternatingRowColor(c);
      }
    }

    boolean showDivider = Platform.isTouchableDevice();

    if (cfg.showDividerLine.spot_valueWasSet()) {
      showDivider = cfg.showDividerLine.booleanValue();
    }

    if (showDivider) {
      listComponent.setShowDivider(true);

      UIColor c = UIColorHelper.getColor(cfg.dividerLineColor.getValue());

      if (c == null) {
        c = ColorUtils.getListDividerColor();
      }

      setDividerLine(c, UIStroke.getStroke(cfg.dividerLineStyle.stringValue()));

      if ("false".equals(cfg.showDividerLine.spot_getAttribute("showLastLine"))) {
        setShowLastDivider(false);
      }
    }

    int min = (int) UIFontHelper.getDefaultLineHeight();

    min = Math.max(min, openIcon.getIconHeight());
    min = Math.max(min, closedIcon.getIconHeight());
    min = Math.max(min, leafIcon.getIconHeight());
    listComponent.setMinRowHeight(min);
    configureSelectionModelGroup(cfg.selectionGroupName, new Object());
    if (cfg.singleClickActionEnabled.spot_valueWasSet()) {
      listComponent.setSingleClickAction(cfg.singleClickActionEnabled.booleanValue());
    }
    else if(Platform.isTouchDevice()) {
      listComponent.setSingleClickAction(true);
    }

    String s = null;

    if (cfg.rowHeight.spot_valueWasSet()) {
      s = cfg.rowHeight.getValue();
    }

    if (s == null) {
      s = PlatformHelper.getDefaultRowHeight();
    }

    setRowHeight(ScreenUtils.toPlatformPixelHeight(s, dataComponent, 400, true));

    switch(cfg.selectionMode.intValue()) {
      case Tree.CSelectionMode.multiple :
        setSelectionMode(SelectionMode.MULTIPLE);

        break;

      case Tree.CSelectionMode.block :
        setSelectionMode(SelectionMode.BLOCK);

        break;

      case Tree.CSelectionMode.invisible :
        setSelectionMode(SelectionMode.INVISIBLE);

        break;

      case Tree.CSelectionMode.none :
        setSelectionMode(SelectionMode.NONE);

        break;

      case ListBox.CSelectionMode.single_auto :
        setSelectionMode(SelectionMode.SINGLE);
        listComponent.setAutoHilight(true);

        break;

      default :
        setSelectionMode(SelectionMode.SINGLE);

        break;
    }

    setTreeIcons(new PainterUtils.TwistyIcon(dataComponent, false), new PainterUtils.TwistyIcon(dataComponent, true));
    treeHandler.setShowRootNode(cfg.showRootNode.booleanValue());
    treeHandler.setShowRootHandles(cfg.showRootHandles.booleanValue());
    treeHandler.setIndentBy(UIScreen.platformPixels(cfg.indentBy.intValue()));
    treeHandler.setExpandAll(cfg.expandAll.booleanValue());
    treeHandler.setParentItemsSelectable(cfg.parentItemsSelectable.booleanValue());

    if (cfg.toggleOnTwistyOnly.spot_hasValue()) {
      treeHandler.setToggleOnTwistyOnly(cfg.toggleOnTwistyOnly.booleanValue());
    } else {
      treeHandler.setToggleOnTwistyOnly(!Platform.isTouchDevice());
    }

    try {
      if (cfg.editingMode.spot_valueWasSet()) {
        treeHandler.setEditingMode(EditingMode.valueOf(cfg.editingMode.stringValue().toUpperCase(Locale.US)));
      }
    } catch(Exception ex) {
      Platform.ignoreException(null, ex);
    }

    if (cfg.autoSizeRowsToFit.booleanValue()) {
      setAutoSizeRowsToFit(true);
    }

    treeHandler.setRootNodeCollapsible(cfg.rootNodeCollapsible.booleanValue());
    listComponent.setHandleFirstFocusSelection(cfg.handleFirstFocusSelection.booleanValue());
    initiallySelectedIndex = cfg.selectedIndex.intValue();
    submitValueType        = cfg.submitValue.intValue();
  }

  @Override
  protected void handleInitialStuff() {
    refreshItems();

    if (expandAll) {
      treeHandler.expandAll();
    }

    if (itemAttributes != null) {
      if (itemAttributes.expand != null) {
        treeHandler.expandRow(itemAttributes.expand);
      }

      List<RenderableDataItem> list = itemAttributes.expanded;
      int                      len  = (list == null)
                                      ? 0
                                      : list.size();

      for (int i = 0; i < len; i++) {
        treeHandler.expandRow(list.get(i));
      }

      handleSelections(listComponent, itemAttributes);

      if ((itemAttributes.check == null) && (getFormViewer() != null)
          &&!getFormViewer().isRetainInitialWidgetValues()) {
        itemAttributes = null;
      }
    }

    if ((getSelectedIndex() == -1) && (initiallySelectedIndex > -1) && (initiallySelectedIndex < getItemCount())) {
      setSelectedIndex(initiallySelectedIndex);
    }

    if (!isRootNodeCollapsible()) {
      treeHandler.expandRow(0);
    }
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (l != null) {
      if (l.isExpandedEventsEnabled()) {
        treeHandler.addExpandedListener(l);
      }

      if (l.isExpansionEventsEnabled()) {
        treeHandler.addExpansionListener(l);
      }
    }
  }

  @Override
  protected void uninitializeListeners(aWidgetListener l) {
    super.uninitializeListeners(l);

    if (l != null) {
      treeHandler.removeExpandedListener(l);
      treeHandler.removeExpansionListener(l);
    }
  }

  @Override
  protected void setFlingThreshold(int threshold) {}

  @Override
  protected void setSelectFlinged(boolean select) {}

  @Override
  protected void setWholeViewFling(boolean wholeViewFling) {}
}
