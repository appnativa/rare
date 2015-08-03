/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.studio;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.viewer.aPlatformViewer;
import com.appnativa.rare.viewer.iFormViewer;

/**
 *
 * @author Don DeCoteau
 */
public class BrowserViewer extends aPlatformViewer {
  public BrowserViewer() {
    this(null);
  }

  public BrowserViewer(iFormViewer fv) {
    super(fv);
    widgetType = WidgetType.WebBrowser;
  }

  public void configure(Viewer viewer) {
    JLabel label = new JLabel(Studio.getResourceAsString("Studio.text.design.browser"));

    label.setHorizontalAlignment(JLabel.CENTER);
    label.setOpaque(false);

    JPanel p = new JPanel();

    p.setLayout(new BorderLayout());
    p.add(label);
    p.setBackground(UIColor.WHITE);
    p.setMinimumSize(new Dimension(50, 50));
    p.setPreferredSize(new Dimension(300, 400));

    Container panel = new Container(p);
    formComponent=dataComponent=panel;
    configure(viewer, true, false, true, false);
  }
}
