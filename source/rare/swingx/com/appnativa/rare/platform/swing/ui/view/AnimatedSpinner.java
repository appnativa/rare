package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import com.appnativa.rare.ui.UISpriteIcon;

public class AnimatedSpinner extends JComponent{
  UISpriteIcon spinnerIcon;
  
  public AnimatedSpinner(UISpriteIcon icon) {
    spinnerIcon=icon;
  }


  @Override
  public Dimension getMinimumSize() {
    return new Dimension(spinnerIcon.getIconWidth(),spinnerIcon.getIconHeight());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(spinnerIcon.getIconWidth(),spinnerIcon.getIconHeight());
  }

  @Override
  protected void paintComponent(Graphics g) {
    spinnerIcon.paintIcon(this, g, (getWidth()-spinnerIcon.getIconWidth()),(getHeight()-spinnerIcon.getIconHeight())/2);
  }


  public UISpriteIcon getSpinner() {
    return spinnerIcon;
  }


}
