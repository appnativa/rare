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

package com.appnativa.rare.ui;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;

import android.util.Log;

import android.view.Gravity;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.platform.android.ui.view.ButtonViewEx;
import com.appnativa.rare.platform.android.ui.view.CheckBoxView;
import com.appnativa.rare.platform.android.ui.view.DrawableWrapper;
import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.LineView;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.platform.android.ui.view.ProgressBarView;
import com.appnativa.rare.platform.android.ui.view.RadioButtonView;
import com.appnativa.rare.platform.android.ui.view.SeparatorView;
import com.appnativa.rare.platform.android.ui.view.SliderView;
import com.appnativa.rare.platform.android.ui.view.SpacerView;
import com.appnativa.rare.platform.android.ui.view.SwitchView;
import com.appnativa.rare.platform.android.ui.view.ToggleButtonView;
import com.appnativa.rare.platform.android.ui.view.ViewEx;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.rare.ui.table.TreeTableView;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.ui.tree.TreeViewEx;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.TextFieldWidget;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.SNumber;

import java.io.ObjectInput;
import java.io.ObjectInputStream;

import java.lang.reflect.Constructor;

import java.net.URL;

import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class ComponentFactory implements iPlatformComponentFactory {
  iPlatformAppContext appContext;

  public void setAppContext(iPlatformAppContext app) {
    this.appContext = app;
  }

  public Object getBean(iWidget context, Bean cfg) {
    Object c;

    try {
      String name = cfg.name.getValue();

      if (iConstants.MENU_SEPARATOR_NAME.equals(name)) {
        Component comp = new Component(new SeparatorView(((aPlatformWidget) context).getAndroidContext()));

        comp.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.FILL);
        comp.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.FILL);

        return comp;
      }

      if (iConstants.MENU_EXPANSION_NAME.equals(name)) {
        Component comp = new Component(new SpacerView(((aPlatformWidget) context).getAndroidContext()));

        comp.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.FILL);
        comp.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.FILL);
        comp.putClientProperty(iConstants.MENU_EXPANSION_NAME, Boolean.TRUE);

        return comp;
      }

      name = cfg.beanJAR.getValue();

      if ((name != null) && (name.length() > 0)) {
        appContext.addJarURL(context.getURL(name));
      }

      name = cfg.beanClass.getValue();

      if ((name != null) && (name.length() > 0)) {
        Class cls = Platform.loadClass(name);

        if (View.class.isAssignableFrom(cls)) {
          Constructor con = cls.getConstructor(new Class[] { Context.class });

          c = con.newInstance(((aPlatformWidget) context).getAndroidContext());
        } else {
          c = cls.newInstance();
        }
      } else {
        name = cfg.beanURL.getValue();

        if ((name == null) || (name.length() == 0)) {
          c = new ViewEx(((aPlatformWidget) context).getAndroidContext());
        } else {
          if (name.startsWith("resource:")) {
            c = AndroidHelper.getResourceComponentView(name.substring(9));
          } else {
            URL            url    = context.getURL(name);
            iURLConnection conn   = context.getAppContext().openConnection(url);
            ObjectInput    stream = new ObjectInputStream(conn.getInputStream());

            c = stream.readObject();
          }
        }
      }
    } catch(Throwable ex) {
      Log.e("Rare", "Bean Failure", ex);

      String s = cfg.failureMessage.getValue();

      if (s == null) {
        s = appContext.getResourceAsString("Rare.runtime.text.beanFailure");
      }

      c = AlertPanel.error(context, new ErrorInformation(ex, null, s));
    }

    return c;
  }

  public View getButton(iWidget context, PushButton cfg) {
    switch(cfg.buttonStyle.intValue()) {
      case PushButton.CButtonStyle.toggle_toolbar :
        return new ToggleButtonView(AppContext.getAndroidContext(), false);

      case PushButton.CButtonStyle.toggle :
        return new ToggleButtonView(AppContext.getAndroidContext(), true);

      case PushButton.CButtonStyle.hyperlink_always_underline :
      case PushButton.CButtonStyle.hyperlink :
        ButtonViewEx h = new ButtonViewEx(AppContext.getAndroidContext(), false);

        h.setHyperlink(true);

        if ((cfg.buttonStyle.intValue() == PushButton.CButtonStyle.hyperlink_always_underline)
            ||!PlatformHelper.hasPointingDevice()) {
          h.setUnderlined(true);
        }

        h.setPadding(ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2,
                     ScreenUtils.PLATFORM_PIXELS_2);

        return h;

      case PushButton.CButtonStyle.platform :
        return new ButtonViewEx(AppContext.getAndroidContext(), true);

      default :
        ButtonViewEx b = new ButtonViewEx(AppContext.getAndroidContext(), false);

        if (cfg.actionRepeats.booleanValue()) {
          final int delay = SNumber.intValue(cfg.actionRepeats.spot_getAttribute("delay"));

          b.setAutoRepeats(delay);
        }

        if (cfg.actionType.intValue() == PushButton.CActionType.popup_menu) {
          b.setDrawArrow(true);
        }

        return b;
    }
  }

  public CompoundButton getCheckBox(iWidget context, com.appnativa.rare.spot.CheckBox cfg) {
    switch(cfg.style.intValue()) {
      case com.appnativa.rare.spot.CheckBox.CStyle.tri_state :
        return new CheckBoxView(AppContext.getAndroidContext(), true);

      case com.appnativa.rare.spot.CheckBox.CStyle.on_off_switch :
        return new SwitchView(AppContext.getAndroidContext());

      default :
        return new CheckBoxView(AppContext.getAndroidContext());
    }
  }

  public iCollapsible getCollapsible(iWidget context, CollapsibleInfo cfg) {
    CollapsiblePane pane = new CollapsiblePane(AppContext.getAndroidContext());

    Utils.configureCollapsible(context, pane, cfg);

    return pane;
  }

  public iPlatformTextEditor getDocumentPane(iWidget context, DocumentPane cfg) {
    EditTextEx e = null;

    if (cfg.customProperties.getValue() != null) {
      Map<String, Object> map = DataParser.parseNameValuePairs(cfg.customProperties);

      cfg.customProperties.spot_setLinkedData(map);

      Object o = map.get("android:config");

      if (o instanceof Map) {
        o = ((Map) o).get("layout");
      } else {
        o = map.get("android:config.layout");
      }

      String s = (o == null)
                 ? null
                 : o.toString();

      if ((s != null) && (s.length() > 0)) {
        e = (EditTextEx) AndroidHelper.getResourceComponentView(s);
      }
    }

    if (e == null) {
      Integer layout = Platform.getUIDefaults().getInteger("Rare.DocumentPane.layout");

      if (layout != null) {
        e = (EditTextEx) AndroidHelper.getResourceComponentView(layout);
      }

      if (e == null) {
        e = new EditTextEx(AppContext.getAndroidContext());
      }
    }

    configureEditText(e, cfg.keyboardType, null, true, true);

    String s = cfg.keyboardType.spot_getAttribute("autoShow");

    if ((s != null) && s.equals("false")) {
      e.setAutoShowKeyboard(false);
    }

    e.setHorizontallyScrolling(false);
    e.setVerticalScrollBarEnabled(true);

    return e;
  }

  public static EditText getEditText(iWidget context, Widget cfg) {
    EditTextEx e = null;

    if (cfg.customProperties.getValue() != null) {
      Map<String, Object> map = DataParser.parseNameValuePairs(cfg.customProperties);

      cfg.customProperties.spot_setLinkedData(map);

      Object o = map.get("android:layout");
      String s = (o == null)
                 ? null
                 : o.toString();

      if ((s != null) && (s.length() > 0)) {
        e = (EditTextEx) AndroidHelper.getResourceComponentView(s);
      } else if (o != null) {    //empty string config means we want the platform default
        e = new EditTextEx(AppContext.getAndroidContext());
      }
    }

    if (e == null) {
      e = EditTextEx.createEditText(AppContext.getAndroidContext());
    }

    if (e == null) {
      e = new EditTextEx(AppContext.getAndroidContext());
    }

    boolean ta = false;

    if (cfg instanceof TextArea) {
      ta = true;
    }

    if (cfg instanceof TextField) {
      TextField tf = (TextField) cfg;

      configureEditText(e, tf.keyboardType, tf.inputValidator, ta, false);

      String s = tf.keyboardType.spot_getAttribute("autoShow");

      if ((s != null) && s.equals("false")) {
        e.setAutoShowKeyboard(false);
      }
    } else if (cfg instanceof DocumentPane) {
      configureEditText(e, ((DocumentPane) cfg).keyboardType, null, true, true);
    } else {
      configureEditText(e, new TextField().keyboardType, null, false, false);
    }

    return e;
  }

  public LabelView getLabel(iWidget context, Label cfg) {
    return new LabelView(AppContext.getAndroidContext());
  }

  public LineView getLine(iWidget context, Line cfg) {
    return new LineView(AppContext.getAndroidContext(), true);
  }

  public ListViewEx getList(iWidget context, ListBox cfg) {
    int        sm;
    ListViewEx list = new ListViewEx(AppContext.getAndroidContext());

    switch(cfg.selectionMode.intValue()) {
      case ListBox.CSelectionMode.multiple :
        sm = ListViewEx.CHOICE_MODE_MULTIPLE;

        break;

      case ListBox.CSelectionMode.invisible :
        sm = ListViewEx.CHOICE_MODE_SINGLE;

        break;

      case ListBox.CSelectionMode.none :
        sm = ListViewEx.CHOICE_MODE_NONE;

        break;

      default :    // single
        sm = ListViewEx.CHOICE_MODE_SINGLE;

        break;
    }

    list.setChoiceMode(sm);

    return list;
  }

  public EditText getPasswordTextField(iWidget context, PasswordField cfg) {
    EditText e = getEditText(context, cfg);

    e.setSingleLine(true);
    e.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    e.setTransformationMethod(PasswordTransformationMethod.getInstance());

    return e;
  }

  public iProgressBar getProgressBar(iWidget context, ProgressBar cfg) {
    if (cfg.indeterminate.booleanValue() && "true".equals(cfg.indeterminate.spot_getAttribute("useSpinner"))) {
      return new ProgressBarWrapper(PlatformHelper.createLabel(context.getContainerComponent()));
    }

    return new ProgressBarView(AppContext.getAndroidContext());
  }

  public android.widget.RadioButton getRadioButton(iWidget context, RadioButton cfg) {
    return new RadioButtonView(AppContext.getAndroidContext());
  }

  public SeekBar getSlider(iWidget context, Slider cfg) {
    final SliderView sv = new SliderView(AppContext.getAndroidContext());
    GridCell         gc = cfg.getTrackPainter();

    if (gc != null) {
      PaintBucket pb = ColorUtils.configure(context, gc, null);

      if (!cfg.horizontal.booleanValue()) {
        iGradientPainter gp = pb.getGradientPainter();

        if (gp != null) {
          switch(gp.getGradientDirection()) {
            case VERTICAL_TOP :
              gp.setGradientDirection(Direction.HORIZONTAL_LEFT);

              break;

            case VERTICAL_BOTTOM :
              gp.setGradientDirection(Direction.HORIZONTAL_RIGHT);

              break;

            case HORIZONTAL_LEFT :
              gp.setGradientDirection(Direction.VERTICAL_TOP);

              break;

            case HORIZONTAL_RIGHT :
              gp.setGradientDirection(Direction.VERTICAL_BOTTOM);

              break;

            default :
              break;
          }
        }
      }

      iPlatformPainter p = pb.getPainter();
      Drawable         d = (p == null)
                           ? null
                           : p.getDrawable(sv);
      int              w = gc.getWidthPixels(null);

      if (w > 0) {
        DrawableWrapper dw = new DrawableWrapper(d);

        if (cfg.horizontal.booleanValue()) {
          dw.setIntrinsicHeightEx(w);
        } else {
          dw.setIntrinsicWidthEx(w);
        }

        d = dw;
      }

      sv.setProgressDrawable(d);
    }

    iPlatformIcon icon = context.getIcon(cfg.thumbIcon);

    if (icon != null) {
      if (!cfg.horizontal.booleanValue() && (icon instanceof UIImageIcon)) {
        UIImageIcon ic = (UIImageIcon) icon;
        Object      o  = ic.getLinkedData();

        if (o instanceof RotatedIconHolder) {
          icon = ((RotatedIconHolder) o).icon;
        } else {
          Bitmap      bm    = ic.getImage().getBitmap();
          UIImageIcon ricon = new UIImageIcon(new UIImage(ImageUtils.rotateRight(bm)));

          if (o == null) {
            ic.setLinkedData(new RotatedIconHolder(ricon));
          }

          icon = ricon;
        }
      }

      sv.setThumb(icon.createDrawable(sv));
    }

    return sv;
  }

  @Override
  public ListViewEx getTable(iWidget context, Table cfg) {
    if (cfg instanceof TreeTable) {
      return new TreeTableView(AppContext.getAndroidContext());
    }

    return new TableView(AppContext.getAndroidContext());
  }

  public EditText getTextField(iWidget context, TextField cfg) {
    return getEditText(context, cfg);
  }

  public View getToolbarButton(iWidget context, PushButton cfg) {
    return getButton(context, cfg);
  }

  public TreeViewEx getTree(iWidget context, Tree cfg) {
    int        sm;
    TreeViewEx list = new TreeViewEx(AppContext.getAndroidContext());

    switch(cfg.selectionMode.intValue()) {
      case ListBox.CSelectionMode.multiple :
        sm = ListViewEx.CHOICE_MODE_MULTIPLE;

        break;

      case ListBox.CSelectionMode.invisible :
        sm = ListViewEx.CHOICE_MODE_SINGLE;

        break;

      case ListBox.CSelectionMode.none :
        sm = ListViewEx.CHOICE_MODE_NONE;

        break;

      default :    // single
        sm = ListViewEx.CHOICE_MODE_SINGLE;

        break;
    }

    list.setItemsCanFocus(false);
    list.setChoiceMode(sm);

    return list;
  }

  protected static void configureEditText(EditText et, iSPOTElement keyboardType, iSPOTElement inputValidator,
          boolean multiline, boolean longMessage) {
    String s  = keyboardType.spot_getAttribute("autoCapatilize");
    int    i  = et.getInputType();
    int    ii = i;

    if (s != null) {
      if (s.equals("words")) {
        i |= InputType.TYPE_TEXT_FLAG_CAP_WORDS;
      } else if (s.equals("sentences")) {
        i |= InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
      } else if (s.equals("all")) {
        i |= InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
      }
    }

    s = keyboardType.spot_getAttribute("autoCorrect");

    if (s != null) {
      if ("true".equals(s)) {
        i |= InputType.TYPE_TEXT_FLAG_AUTO_CORRECT;
      } else if ((i & InputType.TYPE_TEXT_FLAG_AUTO_CORRECT) != 0) {
        i ^= InputType.TYPE_TEXT_FLAG_AUTO_CORRECT;
      }
    }
    else {
      i|=InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
    }

    s = keyboardType.spot_getAttribute("autoComplete");

    if (s != null) {
      if ("true".equals(s)) {
        i |= InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
      } else if ((i & InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
        i ^= InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
      }
    }
    else {
      
    }

    if ((inputValidator != null) && inputValidator.spot_hasValue()) {
      String type = inputValidator.spot_getAttribute("valueType");

      if ("number".equals(type)) {
        i = InputType.TYPE_CLASS_NUMBER;
      }

      if ("date".equals(type)) {
        i = InputType.TYPE_CLASS_DATETIME;
      } else {
        Integer in = TextFieldWidget.inputTypeForName(type);

        if (in != null) {
          i = in.intValue();
        }
      }
    } else {
      i |= InputType.TYPE_CLASS_TEXT;
    }

    et.setSingleLine(!multiline);

    if (multiline) {
      i |= InputType.TYPE_TEXT_FLAG_MULTI_LINE;
      et.setGravity(Gravity.TOP);
    }

    if (longMessage) {
      i |= InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE;
    }

    if (ii != i) {
      et.setInputType(i);
    }
  }

  static class RotatedIconHolder {
    UIImageIcon icon;

    public RotatedIconHolder(UIImageIcon icon) {
      super();
      this.icon = icon;
    }
  }
}
