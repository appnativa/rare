/*
 * @(#)PropertyEditors.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTInteger;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTReal;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.composite.BackgroundColorEditorComposite;
import com.appnativa.studio.composite.BaseEditorComposite;
import com.appnativa.studio.composite.BordersEditorComposite;
import com.appnativa.studio.composite.ColorEditorComposite;
import com.appnativa.studio.composite.FontChooserComposite;
import com.appnativa.studio.composite.IconEditorComposite;
import com.appnativa.studio.composite.ImageEditorComposite;
import com.appnativa.studio.composite.InsetsEditorComposite;
import com.appnativa.studio.composite.ListEditorComposite;
import com.appnativa.studio.composite.PrimitiveArrayEditorComposite;
import com.appnativa.studio.composite.SequenceArrayEditorComposite;
import com.appnativa.studio.composite.StringEditorComposite;
import com.appnativa.studio.composite.URLEditorComposite;
import com.appnativa.studio.composite.URLSetEditorComposite;
import com.appnativa.studio.views.PropertiesView;

public class PropertyEditors {
  static final Object ATTRIBUTES_EDITOR = new Object();
  Control activeEditor;

  public PropertyEditors() {}

  public void disposeOfLastEditor() {
    if (activeEditor != null) {
      try {
        activeEditor.dispose();
      } catch(Exception e) {
        e.printStackTrace();
      }

      activeEditor = null;
    }
  }

  public void focusActiveEditor() {
    if (activeEditor instanceof BaseEditorComposite) {
      ((BaseEditorComposite) activeEditor).focusEditWidget();
    }
  }

  public void hideEditorPreview() {
    if (activeEditor instanceof BaseEditorComposite) {
      ((BaseEditorComposite) activeEditor).hidePreview();
    }
  }

  public Control showBackgroundColorEditor(Composite parent, final Object source, SPOTPrintableString color,
          final PropertyChangeListener listener) {
    BackgroundColorEditorComposite cc;

    if (activeEditor instanceof BackgroundColorEditorComposite) {
      cc = (BackgroundColorEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      cc = new BackgroundColorEditorComposite(parent, SWT.NONE);
    }

    UIColor c = ColorUtils.getBackgroundColor(color);

    cc.setSelectedColor(c);
    cc.setPropertyChangeListener(listener, source);
    activeEditor = cc;

    return activeEditor;
  }

  public Control showBordersEditor(Composite parent, final Object source, SPOTSet borders,
                                   final PropertyChangeListener listener) {
    BordersEditorComposite bc;

    if (activeEditor instanceof BordersEditorComposite) {
      bc = (BordersEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      bc = new BordersEditorComposite(parent, SWT.NONE);
    }

    iWidget context;

    if (listener instanceof PropertiesView) {
      context = ((PropertiesView) listener).getContextWidget();
    } else {
      context = Platform.getWindowViewer();
    }

    bc.setPropertyChangeListener(listener, source);
    bc.setBordersSet(context, borders);
    activeEditor = bc;

    return activeEditor;
  }

  public Control showColorEditor(Composite parent, final Object source, String text,
                                 final PropertyChangeListener listener) {
    ColorEditorComposite cc;

    if (activeEditor instanceof ColorEditorComposite) {
      cc = (ColorEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      cc = new ColorEditorComposite(parent, SWT.NONE);
    }

    UIColor c = (text == null)
                ? null
                : ColorUtils.getColor(text);

    cc.setPropertyChangeListener(listener, source);
    cc.setSelectedColor(c);
    activeEditor = cc;

    return activeEditor;
  }

  public Control showFontEditor(Composite parent, Object eventSource, Font font, PropertyChangeListener listener) {
    FontChooserComposite fc;
    iWidget              context;

    if (activeEditor instanceof FontChooserComposite) {
      fc = (FontChooserComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      fc = new FontChooserComposite(parent, SWT.NONE);
    }

    if (listener instanceof PropertiesView) {
      context = ((PropertiesView) listener).getContextWidget();
    } else {
      context = Platform.getWindowViewer();
    }

    fc.setSelectedFont(context.getFont(), font);
    fc.setPropertyChangeListener(listener, eventSource);
    activeEditor = fc;

    return activeEditor;
  }

  public Control showIconEditor(Composite parent, Object eventSource, iSPOTElement element,
                                PropertyChangeListener listener) {
    IconEditorComposite uc;

    if (activeEditor instanceof IconEditorComposite) {
      uc = (IconEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      uc = new IconEditorComposite(parent, SWT.NONE);;
    }

    uc.setSelectedItem(element);
    uc.setPropertyChangeListener(listener, eventSource);
    activeEditor = uc;

    return activeEditor;
  }

  public Control showImageEditor(Composite parent, Object eventSource, iSPOTElement element,
                                 PropertyChangeListener listener) {
    ImageEditorComposite uc;

    if (activeEditor instanceof ImageEditorComposite) {
      uc = (ImageEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      uc = new ImageEditorComposite(parent, SWT.NONE);;
    }

    uc.setSelectedItem(element);
    uc.setPropertyChangeListener(listener, eventSource);
    activeEditor = uc;

    return activeEditor;
  }

  public Control showInsetsEditor(Composite parent, Object eventSource, iSPOTElement element,
                                  PropertyChangeListener listener) {
    InsetsEditorComposite uc;

    if (activeEditor instanceof InsetsEditorComposite) {
      uc = (InsetsEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      uc = new InsetsEditorComposite(parent, SWT.NONE);;
    }

    uc.setSelectedItem(element);
    uc.setPropertyChangeListener(listener, eventSource);
    activeEditor = uc;

    return activeEditor;
  }

  public Control showList(Composite parent, final Object source, Object value, String text,
                          final PropertyChangeListener listener) {
    ListEditorComposite lc;

    if (activeEditor instanceof ListEditorComposite) {
      lc = (ListEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      lc = new ListEditorComposite(parent, SWT.NONE);
    }

    if (value instanceof Object[]) {
      lc.setItems(Arrays.asList((Object[]) value), text);
    } else if (value instanceof java.util.List) {
      lc.setItems((java.util.List) value, text);
    } else {
      lc.setSelectedItem((iSPOTElement) value);
    }

    activeEditor = lc;
    lc.setPropertyChangeListener(listener, source);

    return activeEditor;
  }

  public Control showPrimitiveSetEditor(Composite parent, Object eventSource, iSPOTElement element,
          PropertyChangeListener listener) {
    PrimitiveArrayEditorComposite sc;

    if (activeEditor instanceof PrimitiveArrayEditorComposite) {
      sc = (PrimitiveArrayEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      sc = new PrimitiveArrayEditorComposite(parent, SWT.NONE);
    }

    sc.setPropertyChangeListener(listener, eventSource);
    sc.setSelectedItem(element);
    activeEditor = sc;

    return activeEditor;
  }

  public Control showSequenceArrayEditor(Composite parent, final Object source, iSPOTElement element,
          final PropertyChangeListener listener) {
    SequenceArrayEditorComposite sc;

    if (activeEditor instanceof SequenceArrayEditorComposite) {
      sc = (SequenceArrayEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      sc = new SequenceArrayEditorComposite(parent, SWT.NONE);
    }

    sc.setPropertyChangeListener(listener, source);
    sc.setSelectedItem(element);
    activeEditor = sc;

    return activeEditor;
  }

  public Control showText(Composite parent, String text) {
    Text tc;

    if (activeEditor instanceof Text) {
      tc = (Text) activeEditor;
    } else {
      disposeOfLastEditor();
      tc = new Text(parent, SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
      tc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
      tc.setEditable(false);
    }

    tc.setText(text);
    activeEditor = tc;

    return activeEditor;
  }

  public Control showTextEditor(Composite parent, final Object source, Object value, PropertyChangeListener listener) {
    StringEditorComposite tc        = null;
    boolean               multiline = value instanceof SPOTSet;

    if (activeEditor instanceof StringEditorComposite) {
      tc = (StringEditorComposite) activeEditor;

      if (tc.isMultiline() != multiline) {
        tc = null;
      }
    }

    if (tc == null) {
      disposeOfLastEditor();
      tc = new StringEditorComposite(parent, multiline);
    }
    if(value instanceof SPOTInteger || value instanceof SPOTReal) {
      tc.hideHeader();
    }
    else {
      tc.showHeader();
    }

    tc.setPropertyChangeListener(listener, source);
    
    if (value instanceof iSPOTElement) {
      tc.setCloneItem(((iSPOTElement)value).spot_getLinkedData()!=ATTRIBUTES_EDITOR);
      tc.setSelectedItem((iSPOTElement) value);
    } else {
      tc.setSelectedText((value == null)
                         ? ""
                         : value.toString());
    }

    activeEditor = tc;

    return activeEditor;
  }

  public Control showURLEditor(Composite parent, Object eventSource, iSPOTElement element,
                               PropertyChangeListener listener) {
    URLEditorComposite uc;

    if (activeEditor instanceof URLEditorComposite) {
      uc = (URLEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      uc = new URLEditorComposite(parent, SWT.NONE);;
    }

    uc.setSelectedItem(element);
    uc.setPropertyChangeListener(listener, eventSource);
    activeEditor = uc;

    return activeEditor;
  }

  public Control showURLSetEditor(Composite parent, Object eventSource, iSPOTElement element,
                                 PropertyChangeListener listener) {
    URLSetEditorComposite sc;

    if (activeEditor instanceof URLSetEditorComposite) {
      sc = (URLSetEditorComposite) activeEditor;
    } else {
      disposeOfLastEditor();
      sc = new URLSetEditorComposite(parent, SWT.NONE);
    }

    sc.setPropertyChangeListener(listener, eventSource);
    sc.setSelectedItem(element);
    activeEditor = sc;

    return activeEditor;
  }

  public Control getActiveEditor() {
    return activeEditor;
  }

  public static Map<String, String> getAttributes(iSPOTElement e) {
    Map<String, String> map = new HashMap<String, String>(e.spot_getSupportedAttributes());
    Map                 m   = e.spot_getAttributesEx();

    if (m != null) {
      map.putAll(m);
    }

    return map;
  }
}
