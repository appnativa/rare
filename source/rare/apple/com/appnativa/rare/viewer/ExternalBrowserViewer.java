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

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.Viewer;

/**
 *  A viewer that launched the system defined browsers to view URL's
 *  that cannot be viewed internally.
 *
 *  @author Don DeCoteau
 */
public class ExternalBrowserViewer extends com.appnativa.rare.viewer.aPlatformViewer {

  /**
   * the url fro the browser
   */
  protected java.net.URL documentURL;

  /**
   *  Constructs a new instance
   */
  public ExternalBrowserViewer() {
    this(null);
  }

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public ExternalBrowserViewer(iContainer parent) {
    super(parent);
  }

  /**
   *  Configures the browser
   *
   *  @param cfg the browser's configuration
   */
  @Override
  public void configure(Viewer cfg) {}

  @Override
  public void dispose() {}

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {}

  @Override
  public void reload(boolean context) {}

  public com.appnativa.rare.viewer.WindowViewer showAsDialog(java.util.Map winoptions, boolean modal) {
    return null;
  }

  @Override
  public com.appnativa.rare.viewer.WindowViewer showAsWindow(java.util.Map winoptions) {
    return null;
  }

  @Override
  public void setValue(Object value) {}

  /**
   *  Sets the URL for the browser
   *
   *  @param url the URL
   */
  public void setURL(java.net.URL url) {}

  /**
   *  Sets the URL for the browser. The URL is set directly into the browser
   *  No rewriting will occur
   *
   *  @param url the URL
   */
  public void setURLDirect(java.net.URL url) {}

  @Override
  public Object getSelection() {
    return null;
  }

  @Override
  public String getSelectionAsString() {
    return null;
  }

  @Override
  public boolean isExternalViewer() {
    return false;
  }
}
