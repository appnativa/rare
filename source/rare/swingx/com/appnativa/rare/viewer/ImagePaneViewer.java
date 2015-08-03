/*
 * @(#)ImagePaneViewer.java   2012-01-03
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import java.io.IOException;

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.ImagePane;
import com.appnativa.rare.ui.ImagePanel;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.iPlatformImagePanel;

/**
 * A viewer that displays and image and provides tools
 * for manipulating the image
 *
 * @author     Don DeCoteau
 */
public class ImagePaneViewer extends aImagePaneViewer {

  /**
   * Constructs a new instance
   */
  public ImagePaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public ImagePaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    try {
      UIImage img = UIImageHelper.createImage(link.getURL(this), deferred);

      setImage(img);
    } catch(IOException ex) {
      getAppContext().getDefaultExceptionHandler().handleException(ex);
    }
  }

  @Override
  public void setUseSpinner(boolean spinner) {}

  @Override
  protected iPlatformImagePanel createPanel(ImagePane cfg) {
  	//super viewer is supposed to set scaling type before calling create panel
  	ImagePanel p=new ImagePanel(scalingType.isCached());
  	p.setWidget(this);
    if (isDesignMode()) {
      p.setDefaultMinimumSize(50, 50, true);
    }
  	return p;
  }
}
