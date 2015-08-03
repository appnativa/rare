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
import com.appnativa.rare.iExecutionHandler;
import com.appnativa.rare.net.URLEncoder;
import com.appnativa.rare.spot.Carousel;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.carousel.CachingURLImageCreator;
import com.appnativa.rare.ui.carousel.aCarouselPanel;
import com.appnativa.rare.ui.carousel.aCarouselPanel.DataType;
import com.appnativa.rare.ui.carousel.iImageCreator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iAdjustable;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.util.SNumber;
import com.appnativa.util.iFilterableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Locale;

/**
 *
 * @author Don DeCcoteau
 */
public abstract class aCarouselViewer extends aPlatformViewer {
  protected static Listeners                    _listeners = new Listeners();
  private static iExecutionHandler              executorService;
  protected DataType                            dataType         = DataType.DATA_ITEMS;
  protected boolean                             encodeParsedURLs = true;
  protected iFilterableList<RenderableDataItem> imageList;
  protected aCarouselPanel                      panel;
  protected boolean                             useLinkedData;

  public aCarouselViewer() {
    this(null);
  }

  public aCarouselViewer(iContainer parent) {
    super(parent);
    this.ensureCapacity(10);    // force the list creation
    imageList = this.getItems();
  }

  public void addChangeListener(iChangeListener l) {
    panel.addChangeListener(l);
  }

  public void removeChangeListener(iChangeListener l) {
    panel.removeChangeListener(l);
  }

  public int getVisibleItemCount() {
    return panel.getVisibleItemCount();
  }

  @Override
  public boolean add(RenderableDataItem e) {
    boolean b = super.add(e);

    if (b) {
      updateCarousel();
    }

    return b;
  }

  /**
   * Gets whether or not the carousel is currently scrolling
   *
   * @return true if it is false otherwise
   */
  public boolean isScrolling() {
    return panel.isScrolling();
  }

  /**
   * Sets whether selection changes will cause events to be fired
   *
   * @param enabled
   *          true to enable; false to disable
   */
  public void setChangeEventsEnabled(boolean enabled) {
    panel.setChangeEventsEnabled(enabled);
  }

  @Override
  public void add(int index, RenderableDataItem element) {
    super.add(index, element);
    updateCarousel();
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    imageList.addAll(c);
    updateCarousel();

    return true;
  }

  @Override
  public void addAll(RenderableDataItem[] items) {
    imageList.addAll(Arrays.asList(items));
    updateCarousel();
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    imageList.addAll(index, c);
    updateCarousel();

    return true;
  }

  @Override
  public void addAll(int index, RenderableDataItem[] items) {
    imageList.addAll(index, Arrays.asList(items));
    updateCarousel();
  }

  /**
   * Add a row to the list without generating any events or updating the view
   *
   * @param row
   *          the row to add
   */
  public void addEx(RenderableDataItem row) {
    super.add(row);
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    String s = useLinkedData
               ? row.getLinkedData().toString()
               : row.toString();

    if (isEncodeParsedURLs()) {
      if (s != null) {
        if (row.getTooltip() == null) {
          int n = s.lastIndexOf('.');
          int p = s.lastIndexOf('/');

          if ((p > -1) && (p < n) && (n != -1)) {
            row.setTooltip(s.substring(p + 1, n));
          } else if (n != -1) {
            row.setTooltip(s.substring(0, n));
          }
        }

        s = URLEncoder.encode(s);

        if (useLinkedData) {
          row.setLinkedData(s);
        } else {
          row.setValue(s);
        }
      }
    }

    imageList.add(row);
  }

  @Override
  public void clearContents() {
    super.clearContents();
    clearSubItems();
    ((aCarouselPanel) dataComponent).refreshItems();
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((Carousel) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
    handleDataURL(vcfg);
  }

  @Override
  public void finishedLoading() {
    super.finishedLoading();
    updateCarousel();
  }

  public void refreshItems() {
    updateCarousel();
  }

  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem item = super.remove(index);

    if (item != null) {
      updateCarousel();
    }

    return item;
  }

  @Override
  public boolean remove(Object item) {
    boolean b = super.remove(item);

    if (b) {
      updateCarousel();
    }

    return b;
  }

  public void scrollHome() {
    panel.scrollHome();
  }

  public boolean scrollLeft() {
    return panel.scrollLeft();
  }

  public boolean scrollRight() {
    return panel.scrollRight();
  }

  public void scrollTo(int index) {
    panel.scrollTo(index);
  }

  public void scrollToEnd() {
    panel.scrollToEnd();
  }

  public void stopAnimation() {
    panel.stopAnimation();
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem element) {
    RenderableDataItem item = super.set(index, element);

    updateCarousel();

    return item;
  }

  public void setAdjustable(iAdjustable adjustable) {
    panel.setAdjustable(adjustable);
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    imageList.setAll(collection);
    updateCarousel();

    return true;
  }

  public void setAnimateSelection(boolean animate) {
    panel.setAnimateSelection(animate);
  }

  public void setEncodeParsedURLs(boolean encodeParsedURLs) {
    this.encodeParsedURLs = encodeParsedURLs;
  }

  public void setFlatList(boolean flatList) {
    panel.setFlatList(flatList);
  }

  public void setFlowInsets(UIInsets insets) {
    panel.setFlowInsets(insets);
  }

  public void setImageGap(int imageGap) {
    panel.setImageGap(imageGap);
  }

  public void setImageGetter(iImageCreator imageGetter) {
    panel.setImageGetter(imageGetter);
  }

  public void setMaxImageSize(int width, int height) {
    panel.setMaxImageSize(width, height);
  }

  public void setMaxSideItems(int max) {
    panel.setMaxSideItems(max);
  }

  public void setMinImageSize(int width, int height) {
    panel.setMinImageSize(width, height);
  }

  public void setPerspectiveFraction(float f) {
    panel.setPerspectiveFraction(f);
  }

  public void setPreferedImageSize(int width, int height) {
    panel.setPreferedImageSize(width, height);
  }

  public void setPreserveAspectRatio(boolean preserveAspectRatio) {
    panel.setPreserveAspectRatio(preserveAspectRatio);
  }

  public void setReflectionFraction(float f) {
    panel.setReflectionFraction(f);
  }

  public void setReflectionOpacity(float opacity) {
    panel.setReflectionOpacity(opacity);
  }

  public void setRenderSecondaryTitle(boolean render) {
    panel.setRenderSecondaryTitle(render);
  }

  public void setRenderTitles(boolean render) {
    panel.setRenderTitles(render);
  }

  public void setScalingType(ScalingType scalingType) {
    panel.setScalingType(scalingType);
  }

  public void setSelectedIndex(int index) {
    ((aCarouselPanel) dataComponent).scrollTo(index);
  }

  public void setSelectionFraction(float f) {
    panel.setSelectionFraction(f);
  }

  public void setSideFraction(float f) {
    panel.setSideFraction(f);
  }

  @Override
  public void setValue(Object value) {
    int i = imageList.indexOf(value);

    if (i != -1) {
      setSelectedIndex(i);
    }
  }

  public int getImageGap() {
    return panel.getImageGap();
  }

  public int getMaxSideItems() {
    return panel.getMaxSideItems();
  }

  public float getPrimaryFraction() {
    return panel.getPrimaryFraction();
  }

  public float getReflectionFraction() {
    return panel.getReflectionFraction();
  }

  public float getReflectionOpacity() {
    return panel.getReflectionOpacity();
  }

  public int getSelectedIndex() {
    return ((aCarouselPanel) dataComponent).getSelectedIndex();
  }

  @Override
  public Object getSelection() {
    return ((aCarouselPanel) dataComponent).getSelectedItem();
  }

  public boolean isEncodeParsedURLs() {
    return encodeParsedURLs;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);

    if (dispose) {
      panel     = null;
      imageList = null;
    }
  }

  protected void configureEx(Carousel cfg) {
    dataType = DataType.valueOf(cfg.dataType.stringValue().toUpperCase(Locale.ENGLISH));

    aCarouselPanel cp = createComponents(cfg, dataType);

    panel = cp;

    aListItemRenderer r = new aListItemRenderer() {}
    ;

    if ("true".equals(cfg.dataType.spot_getAttribute("usesImagesAlways"))) {
      cp.setUsesImagesAlways(true);
    }

    cp.setItemRenderer(r);
    cp.setDataModel(imageList);
    configureEx(cfg, true, false, true);

    if (!isDesignMode()) {
      String  s;
      int     cache       = 0;
      boolean strongCache = false;
      int     threads     = 0;

      cache       = cfg.cacheSize.intValue();
      strongCache = "true".equalsIgnoreCase(cfg.cacheSize.spot_getAttribute("strongReference"));
      cp.setScalingType(ScalingType.valueOf(cfg.scaling.stringValue().toUpperCase(Locale.ENGLISH)));
      cp.setRenderTitles(cfg.renderTitles.booleanValue());
      cp.setRenderSecondaryTitle(cfg.renderSecondaryTitles.booleanValue());
      encodeParsedURLs = cfg.encodeParsedURLs.booleanValue();
      cp.setPreserveAspectRatio(cfg.preserveAspectRatio.booleanValue());

      if (cfg.reflectionFraction.spot_valueWasSet()) {
        cp.setReflectionFraction(cfg.reflectionFraction.floatValue());
      }

      cp.setSelectionFraction(cfg.selectedFraction.floatValue());
      cp.setSideFraction(cfg.sideFraction.floatValue());
      cp.setAnimateSelection(cfg.animateSelection.booleanValue());
      cp.setFlatList(cfg.flatList.booleanValue());
      cp.setImageGap(ScreenUtils.platformPixels(cfg.imageGap.intValue()));

      if (cfg.reflectionFraction.spot_valueWasSet()) {
        cp.setReflectionFraction(cfg.reflectionFraction.floatValue());
      }

      PaintBucket pb = ColorUtils.configure(this, cfg.getItemCell(), null);

      if (pb != null) {
        cp.setCellPaint(pb);
      }

      pb = ColorUtils.configure(this, cfg.getSelectionCell(), null);

      if (pb != null) {
        cp.setSelectedCellPaint(pb);
      }

      Column col;

      if (cfg.getItemDescription() != null) {
        col = createColumn(cfg.getItemDescription());
        pb  = new PaintBucket(col.getBackground());
        pb.setBorder(col.getBorder());
        cp.setCellPaint(pb);
      }

      if (cfg.maxSideItems.spot_hasValue()) {
        cp.setMaxSideItems(cfg.maxSideItems.intValue());
      }

      if (cfg.perspectiveFraction.spot_hasValue()) {
        cp.setPerspectiveFraction(cfg.perspectiveFraction.floatValue());
      }

      Margin m = cfg.getAreaMargin();

      if (m != null) {
        cp.setFlowInsets(m.getInsets());
      }

      if (cfg.useSeparateLoader.booleanValue()) {
        s = cfg.useSeparateLoader.spot_getAttribute("maxThreads");

        if (s != null) {
          threads = SNumber.intValue(s);
        } else {
          threads = 2;
        }
      }

      switch(dataType) {
        case IMAGE_URLS : {
          if (cache == 0) {
            cache = 50;
          } else if (cache == -1) {
            cache = Integer.MAX_VALUE;
          }

          iExecutionHandler es = getAppContext();

          if (threads > 0) {
            if (threads > 10) {
              threads = 10;
            }

            if (executorService == null) {
              executorService = createExecutionHandler(threads);
            }

            es = executorService;
          }

          UIImage delayedIconImage = null;

          if (cfg.deferredImageIcon.spot_hasValue()) {
            UIImageIcon icon = (UIImageIcon) getIcon(cfg.deferredImageIcon);

            if (icon != null) {
              delayedIconImage = icon.getUIImage();
            }
          }

          CachingURLImageCreator ic = new CachingURLImageCreator(this, cache, es, delayedIconImage);

          cp.setImageGetter(ic);

          if (strongCache) {
            ic.getImageCache().setStrongReferences(true);
          }

          break;
        }

        case WIDGET_URLS : {
          break;
        }

        default :
          break;
      }
    }

    cp.addChangeListener(_listeners);
    cp.addActionListener(_listeners);
  }

  protected abstract aCarouselPanel createComponents(Carousel cfg, DataType type);

  protected abstract iExecutionHandler createExecutionHandler(int threads);

  protected void updateCarousel() {
    ((aCarouselPanel) dataComponent).refreshItems();
  }

  private static class Listeners implements iChangeListener, iActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      aWidgetListener wl = getListener((iPlatformComponent) e.getSource());

      if (wl != null) {
        wl.actionPerformed(e);
      }
    }

    @Override
    public void stateChanged(EventObject e) {
      aWidgetListener wl = getListener((iPlatformComponent) e.getSource());

      if (wl != null) {
        wl.stateChanged(e);
      }
    }

    private aWidgetListener getListener(iPlatformComponent c) {
      aCarouselViewer w = (aCarouselViewer) Platform.findWidgetForComponent(c);

      return (w == null)
             ? null
             : w.getWidgetListener();
    }
  }
}
