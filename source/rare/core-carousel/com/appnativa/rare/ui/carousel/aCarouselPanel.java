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

package com.appnativa.rare.ui.carousel;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.XPContainer;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iAdjustable;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.SNumber;

import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aCarouselPanel extends XPContainer implements iActionable, iImageObserver, iChangeListener {
  public static final String RARE_CAROUSEL_ITEM               = "__RARE_CAROUSEL_ITEM__";
  public static final String RARE_CAROUSEL_SHOWTITLE_PROPERTY = "__RARE_CAROUSEL_SHOWTITLE_PROPERTY__";
  protected float            blockIncrement                   = 1;
  protected int              imageGap                         = 0;
  protected int              maxSideItems                     = Short.MAX_VALUE;
  protected float            perspectiveFraction              = 0.3f;
  protected boolean          preserveAspectRatio              = true;
  protected float            primaryFraction                  = 1f;
  protected float            reflectionFraction               = 0;
  protected float            reflectionOpacity                = .5f;
  protected int              selectedIndex                    = -1;
  protected float            sideFraction                     = .5f;
  protected ScalingType      scalingType                      = ScalingType.BILINEAR;
  protected UIDimension      prefImageSize                    = new UIDimension(ScreenUtils.platformPixels(100),
                                                                  ScreenUtils.platformPixels(100));
  protected UIDimension minImageSize = new UIDimension(ScreenUtils.platformPixels(32), ScreenUtils.platformPixels(32));
  protected UIDimension maxImageSize = new UIDimension(ScreenUtils.platformPixels(600),
                                         ScreenUtils.platformPixels(600));
  protected UIInsets                              flowInsets     = new UIInsets(0, 0, 0, 0);
  protected IdentityArrayList<iPlatformComponent> componentCache = new IdentityArrayList<iPlatformComponent>();
  protected boolean                               animate        = true;
  protected iAdjustable                           adjustable;
  protected Map<String, String>                   animatorOptions;
  protected iPlatformComponentPainter             cellPainter;
  protected iPlatformComponent                    centerComponent;
  protected DataType                              dataType;
  protected boolean                               flatList;
  protected boolean                               ignoreAdjustment;
  protected iImageCreator                         imageCreator;
  protected int                                   imageHeight;
  protected int                                   imageWidth;
  protected aListItemRenderer                     itemRenderer;
  protected float                                 oldHeight;
  protected float                                 oldWidth;
  protected boolean                               prefSizeSet;
  protected boolean                               renderSecondaryTitles;
  protected boolean                               renderTitles;
  protected iPlatformComponent                    secondaryLeftComponent;
  protected iPlatformComponent                    secondaryRightComponent;
  protected int                                   secondaryWidth;
  protected iPlatformComponentPainter             selectionPainter;
  protected float                                 titleHeight;
  protected boolean                               useLinkedData;
  protected List                                  dataModel           = Collections.EMPTY_LIST;
  protected boolean                               changeEventsEnabled = true;
  protected boolean                               usesImagesAlways;
  protected float                                 fadeAlpha = 0.3f;
  protected GraphicsComposite                     smallComposite;
  protected GraphicsComposite                     smallerComposite;
  protected int                                   fadeSize;

  public static enum DataType { DATA_ITEMS, IMAGE_URLS, WIDGET_URLS }

  public aCarouselPanel(DataType type) {
    super();
    dataType = type;
  }

  @Override
  public void addActionListener(iActionListener l) {
    getEventListenerList().add(iActionListener.class, l);
  }

  /**
   * Adds the specified change listener
   *
   * @param l
   *          the listener to add
   */
  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  public void animateSelect(int index) {
    updateContent(index);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (imageCreator != null) {
      imageCreator.dispose(this);
    }

    if (animatorOptions != null) {
      animatorOptions.clear();
    }

    if (componentCache != null) {
      for (iPlatformComponent c : componentCache) {
        c.dispose();
      }

      componentCache.clear();
    }

    componentCache          = null;
    secondaryLeftComponent  = null;
    secondaryRightComponent = null;
    centerComponent         = null;
    scalingType             = null;
    changeEvent             = null;
    cellPainter             = null;
    adjustable              = null;
    animatorOptions         = null;
    imageCreator            = null;
    dataModel               = null;
    itemRenderer            = null;
  }

  @Override
  public void imageLoaded(UIImage image) {
    revalidate();
    repaint();
  }

  public void refreshItems() {
    oldWidth = -1;    // force relayout

    final int sel = selectedIndex;

    selectedIndex = -1;
    configureAdjustable();
    revalidate();
    repaint();

    if (sel != -1) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          fireChangeEvent();
        }
      });
    }
  }

  @Override
  public void removeActionListener(iActionListener l) {
    listenerList.remove(iActionListener.class, l);
  }

  /**
   * Removes the specified change listener
   *
   * @param l
   *          the listener to remove
   */
  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  public void scrollHome() {
    if ((adjustable != null) && (adjustable.getValue() != adjustable.getMinimum())) {
      adjustable.setValue(adjustable.getMinimum());
    } else {
      updateContent(0);
    }
  }

  public void setChangeEventsEnabled(boolean enabled) {
    changeEventsEnabled = enabled;
  }

  public boolean isScrolling() {
    return adjustable.isAdjusting();
  }

  /**
   * Scrolls to the left
   *
   * @return true if the scrolling was performed; false otherwise
   */
  public boolean scrollLeft() {
    if (selectedIndex >= dataModel.size()) {
      return false;
    }

    if (adjustable != null) {
      double n = adjustable.getValue() + adjustable.getBlockIncrement();

      adjustable.setValue(Math.min(n, adjustable.getMaximum()));
    } else {
      updateContent(selectedIndex + 1);
    }

    return true;
  }

  /**
   * Scrolls to the right
   *
   * @return true if the scrolling was performed; false otherwise
   */
  public boolean scrollRight() {
    if (selectedIndex == 0) {
      return false;
    }

    if (adjustable != null) {
      double n = adjustable.getValue() - adjustable.getBlockIncrement();

      adjustable.setValue(Math.max(n, 0));
    } else {
      updateContent(selectedIndex - 1);
    }

    return true;
  }

  /**
   * Scrolls to the specified index
   *
   * @param index
   *          the index
   *
   */
  public void scrollTo(int index) {
    if ((adjustable != null) && (adjustable.getValue() != index)) {
      adjustable.setValue(index);
    } else {
      updateContent(index);
    }
  }

  public void scrollToEnd() {
    if ((adjustable != null) && (adjustable.getValue() != adjustable.getMaximum())) {
      adjustable.setValue(adjustable.getMaximum());
    } else {
      updateContent(dataModel.size() - 1);
    }
  }

  @Override
  public void stateChanged(EventObject e) {
    if ((adjustable != null) &&!ignoreAdjustment) {
      double value = adjustable.getValue();
      int    index = (int) Math.round(value / blockIncrement);

      if (!adjustable.isAdjusting()) {
        animateSelect(index);
      } else {
        updateContent(index);
      }
    }
  }

  public void stopAnimation() {}

  public void updateContent(int selectedIndex) {
    int  len  = getComponentCount();
    List list = dataModel;
    int  size = list.size();

    if (size == 0) {
      selectedIndex = -1;

      return;
    }

    selectedIndex = Math.max(0, selectedIndex);

    boolean fire = this.selectedIndex != selectedIndex;

    this.selectedIndex = selectedIndex;

    int                ci = (int) Math.floor(len / 2);
    int                j  = selectedIndex;
    int                i  = ci;
    iPlatformComponent c;

    while(i > 0) {
      i--;
      c = getComponentAt(i);
      j--;

      if (j > -1) {
        c.setVisible(true);
        updateComponentContent(c, list.get(j), renderSecondaryTitles && (i == ci - 1));
        c.putClientProperty(RARE_CAROUSEL_ITEM, j);
        setComposite(c, null);
        c.repaint();
      } else {
        updateComponentContent(c, null, renderSecondaryTitles && (i == ci - 1));
        c.putClientProperty(RARE_CAROUSEL_ITEM, null);
        c.setVisible(false);
      }
    }

    i = ci;
    j = selectedIndex - 1;

    boolean showTitle;

    while(i < len) {
      c = getComponentAt(i);

      if (renderTitles && (i == ci)) {
        showTitle = true;
      } else if (renderSecondaryTitles && (i == ci + 1)) {
        showTitle = true;
      } else {
        showTitle = true;
      }

      j++;

      if (j < size) {
        c.setVisible(true);
        updateComponentContent(c, list.get(j), showTitle);
        c.putClientProperty(RARE_CAROUSEL_ITEM, j);
        setComposite(c, null);
        c.repaint();
      } else {
        c.setVisible(false);
        updateComponentContent(c, null, showTitle);
        c.putClientProperty(RARE_CAROUSEL_ITEM, null);
      }

      i++;
    }

    if (smallComposite == null) {
      initAlphaComposites();
    }

    j = -1;

    float startx = flowInsets.left - getX();    // the panel is offset so it can
    // scroll
    float endx = startx + fadeSize;

    for (i = 0; i < ci - 1; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      if (c.getX() + c.getWidth() > startx) {
        j = i;

        break;
      }
    }

    if (j != -1) {
      c = getComponentAt(j);

      Integer index = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);

      if (index != null) {
        if (index == 0) {
          if (c.getX() < startx) {
            setComposite(c, smallComposite);
          }
        } else {
          if (j < (ci - 2)) {
            setComposite(c, smallerComposite);
            c = getComponentAt(j + 1);
            setComposite(c, smallComposite);
          } else {
            setComposite(c, smallComposite);
          }
        }
      }
    }

    endx   = getWidth() - flowInsets.right + getX();
    startx = endx - fadeSize;
    j      = -1;

    int endIndex = getItemCount() - 1;

    for (i = len - 1; i > ci + 1; i--) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      if (c.getX() < endx) {
        j = i;

        break;
      }
    }

    if (j != -1) {
      c = getComponentAt(j);

      Integer index = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);

      if (index != null) {
        if (index == endIndex) {
          if (c.getX() + c.getWidth() > endx) {
            setComposite(c, smallComposite);
          }
        } else {
          if (j > (ci + 2)) {
            setComposite(c, smallerComposite);
            c = getComponentAt(j - 1);
            setComposite(c, smallComposite);
          } else {
            setComposite(c, smallComposite);
          }
        }
      }
    }

    if (fire) {
      fireChangeEvent();
      conditionallyUpdateAdjustable();
    }
  }

  public void refreshVisibleContent() {
    int  len  = getComponentCount();
    List list = dataModel;

    for (int i = 0; i < len; i++) {
      iPlatformComponent c         = getComponentAt(i);
      Integer            index     = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);
      Boolean            showTitle = (Boolean) c.getClientProperty(RARE_CAROUSEL_SHOWTITLE_PROPERTY);

      if (index != null) {
        updateComponentContent(c, list.get(index.intValue()), (showTitle == null)
                ? false
                : showTitle.booleanValue());
      }
    }
  }

  /**
   * Sets the adjustable that will controll the scrolling of the carousel
   *
   * @param adjustable
   *          the adjustable that controls the scrolling
   */
  public void setAdjustable(iAdjustable adjustable) {
    if (this.adjustable != null) {
      this.adjustable.removeChangeListener(this);
    }

    this.adjustable = adjustable;

    if (this.adjustable != null) {
      this.adjustable.addChangeListener(this);
    }

    configureAdjustable();
    repaint();
  }

  /**
   * Sets whether transitions are animated
   *
   * @param animate
   *          true to animate; false otherwise
   */
  public void setAnimateSelection(boolean animate) {
    this.animate = animate;
  }

  /**
   * @param animatorOptions
   *          the animatorOptions to set
   */
  public void setAnimatorOptions(Map<String, String> animatorOptions) {
    this.animatorOptions = animatorOptions;
  }

  /**
   * Set the paint for a cover cell
   *
   * @param pb
   *          the paint for a cover cell
   */
  public void setCellPaint(PaintBucket pb) {
    cellPainter = (pb == null)
                  ? null
                  : pb.getComponentPainter();
    installPaints();
  }

  public void setFlatList(boolean flatList) {
    this.flatList = flatList;
  }

  /**
   * Sets the insets for the flow. The items will be contained within the
   * specified insets from the panel
   *
   * @param insets
   *          the insets
   */
  public void setFlowInsets(UIInsets insets) {
    if (insets == null) {
      this.flowInsets = new UIInsets(10, 0, 0, 0);
    } else {
      this.flowInsets = (UIInsets) insets.clone();
    }
  }

  public void setImageGap(int imageGap) {
    this.imageGap = imageGap;
  }

  public void setImageGetter(iImageCreator imageGetter) {
    this.imageCreator = imageGetter;
  }

  /**
   * Sets the renderer to use to render items
   *
   * @param renderer
   *          the renderer to use to render items
   */
  public void setItemRenderer(aListItemRenderer renderer) {
    this.itemRenderer = renderer;
  }

  /**
   * Sets the list to use to iterate over items
   *
   * @param model
   *          the data model
   */
  public void setDataModel(List model) {
    dataModel = model;
    configureAdjustable();
    revalidate();
    repaint();
  }

  public void setMaxImageSize(int width, int height) {
    maxImageSize.setSize(width, height);
  }

  /**
   * Sets the maximum side items to display
   *
   * @param max
   *          the maximum side items to display (1-10)
   */
  public void setMaxSideItems(int max) {
    max               = Math.max(max, 1);
    this.maxSideItems = max;
  }

  public void setMinImageSize(int width, int height) {
    minImageSize.setSize(width, height);
  }

  /**
   * Sets the perspective fraction for the difference between the primary item
   * and other items
   *
   * @param f
   *          the fraction (0-0.9f)
   */
  public void setPerspectiveFraction(float f) {
    f                   = Math.min(f, 0.9f);
    f                   = Math.max(f, 0f);
    perspectiveFraction = f;
  }

  public void setPreferedImageSize(int width, int height) {
    prefImageSize.setSize(width, height);
    prefSizeSet = true;

    if ((minImageSize.width > width) || (minImageSize.height > height)) {
      minImageSize.setSize(width, height);
    }
  }

  public void setPreserveAspectRatio(boolean preserveAspectRatio) {
    this.preserveAspectRatio = preserveAspectRatio;
  }

  /**
   * Sets the reflection fraction. The reflection fraction determines the
   * percentage of the height of the primary item that will be utilized to
   * create a reflection
   *
   * @param f
   *          the fraction (0f - 0.5f)
   */
  public void setReflectionFraction(float f) {
    f                       = Math.min(f, 0.5f);
    f                       = Math.max(f, 0);
    this.reflectionFraction = f;
  }

  /**
   * Sets the opacity of the reflection
   *
   * @param opacity
   *          the opacity of the reflection
   */
  public void setReflectionOpacity(float opacity) {
    opacity                = Math.min(opacity, 1f);
    opacity                = Math.max(opacity, 0f);
    this.reflectionOpacity = opacity;
  }

  /**
   * Sets whether titles are rendered for secondary items
   *
   * @param render
   *          true to rendered titles for secondary items; false otherwise
   */
  public void setRenderSecondaryTitle(boolean render) {
    this.renderSecondaryTitles = render;
  }

  /**
   * Sets whether titles are rendered
   *
   * @param render
   *          true if titles are rendered; false otherwise
   */
  public void setRenderTitles(boolean render) {
    this.renderTitles = render;

    if (render) {
      if (titleHeight == 0) {
        titleHeight = PlatformHelper.getFontHeight(getFont(), true) * 2;
      }
    }
  }

  public void setScalingType(ScalingType scalingType) {
    this.scalingType = scalingType;
  }

  public void setSelectedCellPaint(PaintBucket pb) {
    selectionPainter = (pb == null)
                       ? null
                       : pb.getComponentPainter();

    if (centerComponent != null) {
      centerComponent.setComponentPainter(selectionPainter);
    }
  }

  /**
   * Sets the selected item
   *
   * @param item
   *          the new selection
   */
  public void setSelectedItem(Object item) {
    int n = dataModel.indexOf(item);

    if (n > -1) {
      scrollTo(n);
    }
  }

  /**
   * Sets the selection fraction. The selection fraction determines the
   * percentage of the width of the panel that will be utilized to display the
   * selected item
   *
   * @param f
   *          the fraction (0.2f - 1.0f)
   */
  public void setSelectionFraction(float f) {
    f                    = Math.min(f, 1f);
    f                    = Math.max(f, 0.2f);
    this.primaryFraction = f;
  }

  /**
   * Sets the side fraction. The side fraction determines the percentage of the
   * height of the selected item that will be utilized to display the items
   * immediately to the left and the right of the primary item
   *
   * @param f
   *          the fraction (0.2f - 1.0f)
   */
  public void setSideFraction(float f) {
    f                 = Math.min(f, 1.0f);
    f                 = Math.max(f, 0.1f);
    this.sideFraction = f;
  }

  public void setUseLinkedData(boolean use) {
    useLinkedData = use;
  }

  /**
   * @return the animatorOptions
   */
  public Map<String, String> getAnimatorOptions() {
    return animatorOptions;
  }

  /**
   * Gets the insets for the flow. The items will be contained within the
   * specified insets from the panel
   *
   * @return the insets
   */
  public UIInsets getFlowInsets() {
    return (UIInsets) ((flowInsets == null)
                       ? null
                       : flowInsets.clone());
  }

  public int getImageGap() {
    return imageGap;
  }

  public iImageCreator getImageGetter() {
    return imageCreator;
  }

  /**
   * Gets the item at the specified position within the panel
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   * @return the item at the specified position or null
   */
  public Object getItemAt(int x, int y) {
    iPlatformComponent c = getComponentAt(x, y, false);

    if (c == null) {
      return null;
    }

    Integer pos = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);

    if (pos == null) {
      return null;
    }

    int n = pos.intValue();

    if ((n < 0) || (n >= dataModel.size())) {
      return null;
    }

    return dataModel.get(n);
  }

  public int getItemCount() {
    return dataModel.size();
  }

  /**
   * Gets the item renderer
   *
   * @return the item renderer
   */
  public aListItemRenderer getItemRenderer() {
    return itemRenderer;
  }

  /**
   * Gets the maximum number of side items
   *
   * @return the maximum number of side items
   */
  public int getMaxSideItems() {
    return maxSideItems;
  }

  /**
   * Gets the primary fraction. The primary fraction determines the percentage
   * of the width of the panel that will be utilized to display the primary item
   *
   * @return the fraction
   */
  public float getPrimaryFraction() {
    return primaryFraction;
  }

  /**
   * Gets the reflection fraction. The reflection fraction determines the
   * percentage of the height of the primary item that will be utilized to
   * create a reflection
   *
   * @return the fraction
   */
  public float getReflectionFraction() {
    return reflectionFraction;
  }

  /**
   * Gets the opacity of the reflection
   *
   * @return the opacity of the reflection
   */
  public float getReflectionOpacity() {
    return reflectionOpacity;
  }

  public ScalingType getScalingType() {
    return scalingType;
  }

  /**
   * Gets the secondary fraction. The secondary fraction determines the
   * percentage of the width of the primary item that will be utilized to
   * display the items immediately to the left and the right of the primary item
   *
   * @return the fraction
   */
  public float getSecondaryFraction() {
    return sideFraction;
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  /**
   * Get the primary item
   *
   * @return the primary item
   */
  public Object getSelectedItem() {
    if (selectedIndex == -1) {
      return null;
    }

    return dataModel.get(selectedIndex);
  }

  public boolean getUseLinkedData() {
    return useLinkedData;
  }

  /**
   * Gets whether transitions are animated
   *
   * @return true if transitions are animated; false otherwise
   */
  public boolean isAnimated() {
    return animate;
  }

  @Override
  public abstract boolean isAnimating();

  public boolean isFlatList() {
    return flatList;
  }

  public boolean isPerspectiveTransformSupported() {
    return false;
  }

  public boolean isPreserveAspectRatio() {
    return preserveAspectRatio;
  }

  public boolean isUseLinkedData() {
    return useLinkedData;
  }

  protected void animateMove(float dx) {}

  protected abstract void bringToFrontOrClip(iPlatformComponent c, float clipWidth, boolean leftSide);

  protected void callImageCreator(iPlatformComponent c, RenderableDataItem item, boolean showTitle) {
    CharSequence title = isUseLinkedData()
                         ? item.toCharSequence()
                         : null;

    if (!showTitle) {
      title = null;
    }

    updateComponentContent(c, imageCreator.createImage(this, item), title);
  }

  protected abstract void clearPerspectiveFilter(iPlatformComponent c);

  protected void conditionallyUpdateAdjustable() {
    if ((adjustable != null) &&!adjustable.isAdjusting()) {
      try {
        ignoreAdjustment = true;

        double value  = adjustable.getValue();
        double nvalue = selectedIndex * adjustable.getBlockIncrement();

        if (!SNumber.isEqual(value, nvalue)) {
          adjustable.setValue(nvalue);
        }
      } finally {
        ignoreAdjustment = false;
      }
    }
  }

  protected void configureAdjustable() {
    if (adjustable != null) {
      int size = dataModel.size();

      try {
        ignoreAdjustment = true;
        adjustable.setMinimum(0);
        adjustable.setMaximum(size * blockIncrement);
        adjustable.setVisibleAmount(1);
        adjustable.setBlockIncrement(blockIncrement);
        adjustable.setUnitIncrement(1);
      } finally {
        ignoreAdjustment = false;
      }
    }
  }

  protected abstract UIImage createImageFromComponent(iPlatformComponent c);

  protected abstract iPlatformComponent createLayoutComponent();

  /**
   * Fires the change event
   */
  public void fireChangeEvent() {
    if (changeEventsEnabled && (listenerList.getListenerCount() > 0)) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  public void setFadeAlpha(float alpha) {
    fadeAlpha = alpha;
  }

  protected void initAlphaComposites() {
    float small   = fadeAlpha;
    float smaller = fadeAlpha / 2;

    smallComposite   = new GraphicsComposite(CompositeType.SRC_OVER, small);
    smallerComposite = new GraphicsComposite(CompositeType.SRC_OVER, smaller);
  }

  protected void initComponents() {
    setForeground(UIColor.WHITE);
    centerComponent         = createLayoutComponent();
    secondaryLeftComponent  = createLayoutComponent();
    secondaryRightComponent = createLayoutComponent();
    centerComponent.setVisible(false);
    secondaryLeftComponent.setVisible(false);
    secondaryRightComponent.setVisible(false);
  }

  protected void installPaints() {
    if (centerComponent != null) {
      centerComponent.setComponentPainter(selectionPainter);
      secondaryLeftComponent.setComponentPainter(cellPainter);
      secondaryRightComponent.setComponentPainter(cellPainter);
    }
  }

  /**
   * Lays on the components. Subclasses need to ensure that the order that the
   * components are laid out in is preserved, regardless of the underlying UI
   * component order.
   *
   */
  protected void layout(float x, float y, float width, float height) {
    if ((oldWidth == width) && (oldHeight == height)) {
      return;
    }

    if (centerComponent == null) {
      initComponents();
      installPaints();
    }

    oldWidth  = width;
    oldHeight = height;

    float h = height - (flowInsets.top + flowInsets.bottom + titleHeight);
    float w = width - (flowInsets.left + flowInsets.right);

    y              += flowInsets.top;
    x              += flowInsets.left;
    imageHeight    = (int) Math.ceil(Math.min(h * primaryFraction, maxImageSize.height));
    imageWidth     = (int) Math.ceil(Math.min(h * primaryFraction, maxImageSize.width));
    imageWidth     = (int) Math.max(imageWidth, minImageSize.width);
    imageHeight    = (int) Math.max(imageHeight, minImageSize.height);
    secondaryWidth = UIScreen.snapToSize(imageWidth * sideFraction);

    float   odw, sdw;
    boolean flat = flatList ||!isPerspectiveTransformSupported();

    if (flat) {
      sdw = odw = secondaryWidth;
    } else {
      odw = secondaryWidth / 4;
      sdw = odw * 2;
      odw += (odw / 2);
    }

    sdw += imageGap;
    odw += imageGap;

    int   otherCount = 1;
    float cx         = x + ((w - imageWidth) / 2);
    float cy         = y + ((h - imageHeight) / 2);
    float lo         = cx - sdw - odw;

    while(lo > 0) {
      otherCount++;
      lo = lo - odw;
    }

    otherCount = Math.min(otherCount, (int) Math.ceil((getItemCount() - 3) / 2));
    otherCount = Math.max(otherCount, 1);

    float sh = UIScreen.snapToSize(imageHeight * sideFraction);
    float sy = cy + ((imageHeight - sh) / 2);

    otherCount = Math.min(otherCount + 3, maxSideItems);

    int                len = getComponentCount();
    iPlatformComponent c;

    for (int i = len - 1; i > -1; i--) {
      c = getComponentAt(i);
      clearComponentContent(c);

      if ((c != centerComponent) && (c != secondaryLeftComponent) && (c != secondaryRightComponent)) {
        componentCache.add(c);
      }

      remove(c);
    }

    if (dataModel.isEmpty()) {
      return;
    }

    fadeSize = (int) (odw * 2);

    float sx = cx - (otherCount * odw) - sdw;

    for (int i = 0; i < otherCount; i++) {
      c = getComponentToAdd();
      add(c);

      if (!flat) {
        setPerspectiveFilter(c, imageWidth, imageHeight, true, otherCount - i);
      }

      c.setBounds(sx, sy, secondaryWidth, sh);
      bringToFrontOrClip(c, odw, true);
      sx += odw;
    }

    add(secondaryLeftComponent);
    add(centerComponent);
    add(secondaryRightComponent);
    secondaryLeftComponent.setBounds(cx - sdw, sy, secondaryWidth, sh);
    centerComponent.setBounds(cx, cy, imageWidth, imageHeight);
    secondaryRightComponent.setBounds(cx + imageWidth - secondaryWidth + sdw, sy, secondaryWidth, sh);

    if (!flat) {
      setPerspectiveFilter(secondaryLeftComponent, imageWidth, imageHeight, true, 0);
      setPerspectiveFilter(secondaryRightComponent, imageWidth, imageHeight, false, 0);
    }

    int compPos = getComponentCount();

    sx = cx + imageWidth - secondaryWidth + sdw + sdw;

    for (int i = 0; i < otherCount; i++) {
      c = getComponentToAdd();
      add(c);

      if (!flat) {
        setPerspectiveFilter(c, imageWidth, imageHeight, false, i);
      }

      c.setBounds(sx, sy, secondaryWidth, sh);
      bringToFrontOrClip(c, odw, true);
      sx += odw;
    }

    for (int i = 0; i < otherCount; i++) {
      c = getComponentAt(compPos++);
      bringToFrontOrClip(c, odw, true);
    }

    bringToFrontOrClip(secondaryLeftComponent, secondaryWidth, true);
    bringToFrontOrClip(secondaryRightComponent, secondaryWidth, false);
    bringToFrontOrClip(centerComponent, 0, false);
  }

  protected abstract void clearComponentContent(iPlatformComponent dst);

  protected abstract void renderComponentContent(iPlatformComponent dst, RenderableDataItem src);

  protected abstract void updateComponentContent(iPlatformComponent dst, iPlatformComponent src, CharSequence title);

  protected void updateComponentContent(iPlatformComponent c, Object value, boolean showTitle) {
    c.putClientProperty(RARE_CAROUSEL_SHOWTITLE_PROPERTY, showTitle);

    switch(dataType) {
      case WIDGET_URLS : {
        if ((value == null) || (value instanceof iPlatformComponent)) {
          updateComponentContent(c, (iPlatformComponent) value, null);

          break;
        }

        if (value instanceof aPlatformWidget) {
          aPlatformWidget w = (aPlatformWidget) value;
          String          s = showTitle
                              ? w.getTitle()
                              : null;

          updateComponentContent(c, w.getContainerComponent(), s);

          break;
        }

        if (!(value instanceof RenderableDataItem)) {
          updateComponentContent(c, (iPlatformComponent) null, null);
        }

        RenderableDataItem item  = (RenderableDataItem) value;
        CharSequence       title = showTitle
                                   ? item.toCharSequence()
                                   : null;

        updateComponentContent(c, item.getRenderingComponent(), title);
      }

      break;

      case IMAGE_URLS : {
        if ((value == null) || (value instanceof UIImage)) {
          updateComponentContent(c, (UIImage) value, null);
        } else {
          callImageCreator(c, (RenderableDataItem) value, showTitle);
        }
      }

      break;

      default :
        if (imageCreator != null) {
          callImageCreator(c, (RenderableDataItem) value, showTitle);
        } else {
          renderComponentContent(c, (RenderableDataItem) value);
        }

        break;
    }
  }

  protected abstract void setComposite(iPlatformComponent component, GraphicsComposite composite);

  protected abstract void updateComponentContent(iPlatformComponent dst, UIImage src, CharSequence title);

  protected abstract void setPerspectiveFilter(iPlatformComponent c, float width, float height, boolean left, int pos);

  protected iPlatformComponent getComponentToAdd() {
    if (!componentCache.isEmpty()) {
      return componentCache.remove(componentCache.size() - 1);
    }

    iPlatformComponent c = createLayoutComponent();

    c.setComponentPainter(cellPainter);
    c.setVisible(false);

    return c;
  }

  protected int getDataIndex(iPlatformComponent c) {
    Integer i = (Integer) c.getClientProperty(RARE_CAROUSEL_ITEM);

    return (i == null)
           ? -1
           : i.intValue();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    size.setSize(minImageSize);

    float odw = secondaryWidth / 3;
    float sdw = odw * 2;

    size.width  += flowInsets.left + flowInsets.right;
    size.height += flowInsets.top + flowInsets.bottom;
    size.width  += (sdw * 2);
    size.height += titleHeight;
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (prefSizeSet) {
      size.setSize(prefImageSize);
    } else if (imageWidth == 0) {
      size.setSize(minImageSize);
    } else {
      size.setSize(imageWidth, imageHeight);
    }

    float odw = secondaryWidth / 3;
    float sdw = odw * 2;

    size.width  += flowInsets.left + flowInsets.right;
    size.height += flowInsets.top + flowInsets.bottom;
    int n=maxSideItems==Short.MAX_VALUE ? 4 : maxSideItems;
    size.width  += (Math.max(1, n - 2) * odw);
    size.width  += (sdw * 2);
    size.height += titleHeight;
  }

  public boolean isUsesImagesAlways() {
    return usesImagesAlways;
  }

  public void setUsesImagesAlways(boolean usesImagesAlways) {
    this.usesImagesAlways = usesImagesAlways;
  }

  public int getVisibleItemCount() {
    return getComponentCount();
  }
}
