package com.appnativa.rare.widget;


import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.ui.iProgressBar;
import com.appnativa.rare.viewer.iContainer;


/**
 *  A widget that displays the progress of some operation.
 * 
 *  @author Don DeCoteau
 */
public class ProgressBarWidget extends aProgressBarWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public ProgressBarWidget(iContainer parent) {
    super(parent);
  }
  
  @Override
  protected iProgressBar createProgressBar(ProgressBar cfg) {
    return getAppContext().getComponentCreator().getProgressBar(this, cfg);
  }

}
