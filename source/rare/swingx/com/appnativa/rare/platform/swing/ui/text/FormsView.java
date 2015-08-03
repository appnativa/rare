/*
 * @(#)FormsView.java   2009-05-07
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Graphics;
import java.awt.Shape;
import java.io.StringReader;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.BlockView;
import javax.swing.text.html.HTML;

import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.ui.text.HTMLEditorKitEx.iGroupView;
import com.appnativa.rare.spot.Form;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.DocumentPaneViewer;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.GroupBoxViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.iWidget;

/**
 * A custom view that encapsulates HTML FORM and DIV elements
 * to provide a mapping to FormViewer and GroupBoxViewer.
 * If the the class of the element is equal to 'GroupBox{}' then a groupbox
 * viewer is created otherwise a from viewer is created
 *
 * @author Don DeCoteau
 */
public class FormsView extends BlockView implements iGroupView {
  iWidget         context;
  iContainer      theViewer;
  private boolean visible = true;

  public FormsView(iWidget context, Element elem, int axis) {
    super(elem, axis);
    this.context=context;
    iContainer fv = context.getContainerViewer();
    theViewer    = createViewer(this, fv, elem);


    theViewer.setParent(fv);

    if ((context instanceof DocumentPaneViewer) && (context.getAttribute("hasForm") != Boolean.TRUE)) {
      context.setAttribute("hasForm", Boolean.TRUE);
      context.setLinkedData(theViewer);
    }
    if (!isVisible()) {
      Platform.invokeLater(new Runnable() {

        @Override
        public void run() {
          setContainerVisible(false);
        }
        
      });
    }
  }
  @Override
  public iContainer getViewer() {
    return theViewer;
  }


  public static GroupBoxViewer createViewer(final iGroupView view, iContainer parent, Element elem) {
    AttributeSet   attr   = elem.getAttributes();
    String         name   = (String) WidgetView.getAttribute(attr,HTML.Attribute.NAME);
    String         target = (String) WidgetView.getAttribute(attr,HTML.Attribute.TARGET);
    Object         action = WidgetView.getAttribute(attr,HTML.Attribute.ACTION);
    String         cls    = (String) WidgetView.getAttribute(attr,HTML.Attribute.CLASS);
    boolean        gb     = (cls != null) && cls.startsWith("GroupBox{");
    GroupBoxViewer fv     = null;

    if ((name != null) && name.startsWith("rare-span-begin:")) {
      name = name.substring("rare-span-begin:".length());
    }

    try {
      GroupBox cfg = createGroupBox(parent, gb
              ? new GroupBox()
              : new Form(), cls);

      if (name != null) {
        cfg.name.setValue(name);
      }

      if (attr.isDefined("required")) {
        cfg.required.setValue(true);
      }

      cfg.spot_setAllowInvalidAttributes(true);
      cfg.spot_setAttribute("htmlForm", "true");
      cfg.disableBehavior.setValue(Viewer.CDisableBehavior.disable_widgets);

      if (gb) {
        fv = new GroupBoxViewer(parent) {
          @Override
          public void setVisible(boolean visible) {
            view.setContainerVisible(visible);
          }
        };
      } else {
        fv = new FormViewer(parent) {
          @Override
          public void setVisible(boolean visible) {
            view.setContainerVisible(visible);
          }
        };
      }

      if (action instanceof String) {
        cfg.getActionLinkReference().url.setValue((String) action);

        try {
          cfg.getActionLinkReference().target.setValue(target);
        } catch(Exception e) {
          AppContext.debugLog("invalid link target:" + target);
        }
      }

      fv.configure(cfg);
      if(!cfg.visible.booleanValue()) {
        view.setContainerVisible(false);
      }
    } catch(Exception e) {
      Platform.ignoreException("creating form", e);
    }

    return fv;
  }

  @Override
  public void paint(Graphics g, Shape allocation) {
    if (visible) {
      super.paint(g, allocation);
    }
  }

  public void update() {
    this.layoutChanged(Y_AXIS);
    context.update();
  }

  /**
   * @param visible the visible to set
   */
  @Override
  public void setContainerVisible(boolean visible) {
    this.visible = visible;
    if(theViewer!=null) {
      int        len = theViewer.getWidgetCount();
      iContainer gb  = theViewer;

      for (int i = 0; i < len; i++) {
        gb.getWidget(i).setVisible(visible);
      }

      update();
    }
  }


  /**
   * @return the fontFamily
   */
  public String getFontFamily() {
    return StyleConstants.getFontFamily(getElement().getAttributes());
  }

  /**
   * @return the fontSize
   */
  public int getFontSize() {
    return StyleConstants.getFontSize(getElement().getAttributes());
  }

  @Override
  public float getMaximumSpan(int axis) {
    if (!isVisible()) {
      return 0;
    }

    return super.getMaximumSpan(axis);
  }

  @Override
  public float getMinimumSpan(int axis) {
    if (!isVisible()) {
      return 0;
    }

    return super.getMinimumSpan(axis);
  }

  @Override
  public float getPreferredSpan(int axis) {
    if (!isVisible()) {
      return 0;
    }

    return super.getPreferredSpan(axis);
  }

  /**
   * @return the visible
   */
  @Override
  public boolean isVisible() {
    return visible
           ? super.isVisible()
           : false;
  }

  private static GroupBox createGroupBox(iWidget context, GroupBox cfg, String cls) {
    if (cls != null) {
      try {
        DataParser.loadSPOTObjectSDF(context, new StringReader(cls), cfg, "text/x-sdf",null);

        String tname = cfg.templateName.getValue();

        TemplateHandler.getInstance(context, null).applyTemplate(cfg, tname);
      } catch(Exception ex) {}
    }

    return cfg;
  }
}
