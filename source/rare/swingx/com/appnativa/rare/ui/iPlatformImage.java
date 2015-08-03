package com.appnativa.rare.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;


public interface iPlatformImage extends iImage{

  /**
   * @return the bitmap
   */
  Image getImage();
  
  BufferedImage getBufferedImage();

}
