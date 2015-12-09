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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.text.HTMLCharSequence;
import com.appnativa.rare.ui.text.iPlatformTextEditor;

/*-[
 #import "RAREAPWebView.h"
 ]-*/
public class WebView extends ParentView implements iPlatformTextEditor {
  protected boolean       followHyperlinks;
  protected iLoadListener loadListener;

  public WebView() {
    super(createProxy());
  }

  public WebView(boolean textEditor) {
    super(createProxy());

    if (textEditor) {
      loadListener = new iLoadListener() {
        @Override
        public boolean loadWillStart(WebView view, String url, int loadType) {
          return followHyperlinks;
        }
        @Override
        public void loadStarted(WebView view) {}
        @Override
        public void loadFinished(WebView view) {}
        @Override
        public void loadFailed(WebView view, String reason) {}
      };
    }
  }

  public WebView(Object apwebview) {
    super(apwebview);
  }

  @Override
  public void addActionListener(iActionListener listener) {
  }

  @Override
  public void addTextChangeListener(iTextChangeListener changeListener) {}

  @Override
  public void appendText(String text) {}

  @Override
  public void boldText() {}

  @Override
  public void decreaseIndent() {}

  @Override
  public void deleteSelection() {}

  @Override
  public void enlargeFont() {}

  @Override
  public void increaseIndent() {}

  @Override
  public void insertHTML(int pos, String html) {}

  @Override
  public void insertText(int pos, String text) {}

  @Override
  public void italicText() {}

  public native void load(String url)
  /*-[
    [((RAREAPWebView*)proxy_) loadWithHREF: url];
  ]-*/
  ;

  public native void loadContent(String content, String contentType, String baseHref)
  /*-[
    [((RAREAPWebView*)proxy_) loadWithContent: content contentType: contentType  baseHREF: baseHref];
  ]-*/
  ;

  @Override
  public void pasteText(String text) {}

  public native void reload()
  /*-[
    [((RAREAPWebView*)proxy_) reload];
  ]-*/
  ;

  public native void clearContents()
  /*-[
    [((RAREAPWebView*)proxy_) clearContents];
  ]-*/
  ;

  @Override
  public void removeActionListener(iActionListener listener) {
    // TODO Auto-generated method stub
  }

  @Override
  public void removeTextChangeListener(iTextChangeListener changeListener) {}

  @Override
  public void selectAll() {}

  @Override
  public void shrinkFont() {}

  public native void stopLoading()
  /*-[
    [((RAREAPWebView*)proxy_) stopLoading];
  ]-*/
  ;

  @Override
  public void strikeThroughText() {}

  @Override
  public void subscriptText() {}

  @Override
  public void superscriptText() {}

  @Override
  public void underlineText() {}

  @Override
  public void setCaretPosition(int position) {}

  @Override
  public void setEditable(boolean editable) {}

  @Override
  public void setEmptyFieldColor(UIColor color) {}

  @Override
  public void setEmptyFieldFont(UIFont font) {}

  @Override
  public void setEmptyFieldText(String text) {}

  @Override
  public void setFollowHyperlinks(boolean follow) {
    followHyperlinks = follow;
  }

  public void setLoadListener(iLoadListener listener) {
    loadListener = listener;
  }

  @Override
  public void setSelection(int beginIndex, int endIndex) {}

  @Override
  public void setText(String data, boolean htmlDocument) {
    String type = htmlDocument
                  ? "text/html"
                  : "text/plain";

    loadContent(data, type, null);
  }

  @Override
  public void setTextFontFamily(String family) {}

  @Override
  public void setTextFontSize(int size) {}

  @Override
  public void setTextForeground(UIColor fg) {}

  @Override
  public int getCaretPosition() {
    return 0;
  }

  @Override
  public iPlatformComponent getComponent() {
    iPlatformComponent c = super.getComponent();

    if (c == null) {
      c = new ActionComponent(this);
    }

    return c;
  }

  @Override
  public iPlatformComponent getContainer() {
    return getComponent();
  }

  @Override
  public native String getHtmlText()
  /*-[
     return [((RAREAPWebView*)proxy_)  stringByEvaluatingJavaScriptFromString:
                                       @"document.body.innerHTML"];
  ]-*/
  ;

  @Override
  public String getPlainText() {
    return HTMLCharSequence.getPlainText(getHtmlText());
  }

  @Override
  public int getSelectionEnd() {
    return 0;
  }

  @Override
  public int getSelectionStart() {
    return 0;
  }

  @Override
  public String getSelectionString() {
    return null;
  }

  @Override
  public String getText(int start, int end) {
    return getPlainText();
  }

  @Override
  public int getTextLength() {
    return 0;
  }

  public native String getURL()
  /*-[
    return [((RAREAPWebView*)proxy_) getHREF];
  ]-*/
  ;

  @Override
  public boolean hasSelection() {
    return false;
  }

  @Override
  public boolean isEditable() {
    return false;
  }

  @Override
  public boolean isFollowHyperlinks() {
    return followHyperlinks;
  }

  static native Object createProxy()
  /*-[
    return [RAREAPWebView new];
  ]-*/
  ;

  @Override
  protected void disposeEx() {
    super.disposeEx();
    loadListener = null;
  }

  public interface iLoadListener {
    void loadFailed(WebView view, String reason);

    void loadFinished(WebView view);

    void loadStarted(WebView view);

    boolean loadWillStart(WebView view, String url, int loadType);
  }


  public native void setScaleToFit(boolean scaleToFit)
  /*-[
   [((RAREAPWebView*)proxy_) setScaleToFit: scaleToFit];
  ]-*/
  ;

  public void executeScript(String script, final iFunctionCallback cb) {
    final Object value = executeScriptEx(script);

    if (cb != null) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          cb.finished(false, value);
        }
      });
    }
  }

  public native void setWindowProperty(String name, Object value)
  /*-[
  return [((RAREAPWebView*)proxy_) setWindowProperty: name value: value];
 ]-*/
  ;

  public native String executeScriptEx(String script)
  /*-[
   return [((RAREAPWebView*)proxy_) stringByEvaluatingJavaScriptFromString: script];
  ]-*/
  ;

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
  }
}
