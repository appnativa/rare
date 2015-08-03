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

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;

/**
 *
 * @author Don DeCoteau
 */
public class HeadlessTarget implements iTarget {
  protected iPlatformAppContext appContext;
  protected Object              linkedData;
  protected iListener           listener;
  protected String              targetName;
  protected iViewer             theViewer;
  private boolean               disposed;
  protected boolean             locked;

  public HeadlessTarget(iPlatformAppContext appContext, String targetName, iListener listener) {
    this.appContext = appContext;
    this.targetName = targetName;
    this.listener   = listener;
  }

  @Override
  public void activate() {
    if (theViewer != null) {
      theViewer.requestFocus();
    }
  }

  @Override
  public void dispose(boolean disposeviewer) {
    if (!disposed) {
      disposed = true;

      iWindowManager wm = appContext.getWindowManager();

      if (wm == null) {
        return;
      }

      wm.removeTarget(targetName);

      try {
        iViewer v = removeViewer();

        if (v != null) {
          if (disposeviewer || v.isAutoDispose()) {
            v.dispose();
          }
        }
      } catch(Throwable e) {
        appContext.getDefaultExceptionHandler().ignoreException(null, e);
      } finally {
        appContext = null;
        linkedData = null;
        listener   = null;
      }
    }
  }

  @Override
  public void reloadViewer() {
    if (theViewer != null) {
      theViewer.reload(false);
    }
  }

  @Override
  public iViewer removeViewer() {
    if (theViewer != null) {
      theViewer.targetLost(this);

      iViewer v = theViewer;

      if (listener != null) {
        listener.viewerRemoved(v);
      }

      theViewer = null;

      return v;
    }

    return null;
  }

  @Override
  public void repaint() {
    if (theViewer != null) {
      theViewer.repaint();
    }
  }

  @Override
  public void update() {
    if (theViewer != null) {
      theViewer.update();
    }
  }

  @Override
  public void setBackgroundPainter(iBackgroundPainter painter) {}

  @Override
  public void setComponentPainter(iPlatformComponentPainter painter) {}

  @Override
  public void setLinkedData(Object data) {
    linkedData = data;
  }

  @Override
  public void setLocked(boolean lock) {
    this.locked = lock;

    if (theViewer != null) {
      theViewer.setEnabled(!lock);
    }
  }

  public void setName(String name) {
    targetName = name;
  }

  @Override
  public iViewer setViewer(iViewer viewer) {
    iViewer v = removeViewer();

    if (viewer != null) {
      if (viewer.getTarget() != null) {
        viewer.getTarget().removeViewer();
      }

      theViewer = viewer;

      if (listener != null) {
        listener.viewerSet(v);
      }

      viewer.pageHome();
      viewer.targetAcquired(this);

      if (locked) {
        viewer.setEnabled(false);
      }
    }

    return v;
  }

  @Override
  public void setVisible(boolean visible) {
    if (theViewer != null) {
      theViewer.setVisible(visible);
    }
  }

  @Override
  public iParentComponent getContainerComponent() {
    return (iParentComponent) ((theViewer == null)
                               ? null
                               : theViewer.getContainerComponent());
  }

  @Override
  public Object getLinkedData() {
    return linkedData;
  }

  @Override
  public String getName() {
    return targetName;
  }

  @Override
  public UIDimension getTargetSize() {
    return (theViewer == null)
           ? new UIDimension(0, 0)
           : theViewer.getContainerComponent().getSize();
  }

  @Override
  public iViewer getViewer() {
    return theViewer;
  }

  @Override
  public boolean isHeadless() {
    return true;
  }

  @Override
  public boolean isLocked() {
    return locked;
  }

  @Override
  public boolean isPopupWindow() {
    return false;
  }

  @Override
  public boolean isVisible() {
    return (theViewer == null)
           ? false
           : theViewer.isVisible();
  }

  @Override
  public void setTransitionAnimator(iTransitionAnimator animator) {}

  @Override
  public iTransitionAnimator getTransitionAnimator() {
    return null;
  }

  @Override
  public boolean setRenderType(RenderType renderType) {
    return false;
  }

  @Override
  public RenderType getRenderType() {
    return null;
  }
}
