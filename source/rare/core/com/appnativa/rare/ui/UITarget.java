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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.effects.aTransitionAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class UITarget implements iTarget {
  boolean                       partialOutAnimation;
  protected iTransitionAnimator animator;
  protected iPlatformAppContext appContext;
  protected boolean             ignoreViewerRenderType;
  protected Object              linkedData;
  protected iListener           listener;
  protected boolean             manualUpdate;
  protected boolean             popupWindow;
  protected iParentComponent    targetContainer;
  protected String              targetName;
  protected iViewer             theViewer;
  protected iTransitionAnimator transitionAnimator;

  /**
   * Constructs a new instance
   *
   * @param app the application context
   * @param name the target name
   */
  public UITarget(iPlatformAppContext app, String name) {
    this(app, name, PlatformHelper.createTargetContainer(app), true, null);
    targetContainer.setOpaque(false);
  }

  /**
   * Constructs a new instance
   *
   * @param app the application context
   * @param name the target name
   * @param listener a listener to me notified when a viewer is set or removed
   */
  public UITarget(iPlatformAppContext app, String name, iListener listener) {
    this(app, name, PlatformHelper.createTargetContainer(app), true, listener);
    targetContainer.setOpaque(false);
  }

  /**
   * Constructs a new instance
   *
   * @param app the application context
   * @param name the target name
   * @param container the target container
   */
  public UITarget(iPlatformAppContext app, String name, iParentComponent container) {
    this(app, name, container, true);
  }

  /**
   * Constructs a new instance
   *
   * @param app the application context
   * @param name the target name
   * @param register true to register the target; false otherwise
   * @param listener a listener to me notified when a viewer is set or removed
   */
  public UITarget(iPlatformAppContext app, String name, boolean register, iListener listener) {
    this(app, name, PlatformHelper.createTargetContainer(app), register, listener);
    targetContainer.setOpaque(false);
  }

  /**
   * Constructs a new instance
   *
   * @param app the application context
   * @param name the target name
   * @param container the target container
   * @param register true to register the target; false otherwise
   */
  public UITarget(iPlatformAppContext app, String name, iParentComponent container, boolean register) {
    this(app, name, container, register, null);
  }

  /**
   * Constructs a new instance
   *
   * @param app the application context
   * @param name the target name
   * @param container the target container
   * @param register true to register the target; false otherwise
   * @param listener a listener to me notified when a viewer is set or removed
   */
  public UITarget(iPlatformAppContext app, String name, iParentComponent container, boolean register,
                  iListener listener) {
    this.appContext      = app;
    this.targetName      = name;
    this.targetContainer = container;
    this.listener        = listener;

    if (register) {
      app.getWindowManager().registerTarget(targetName, this);
    }

    targetContainer.putClientProperty(iConstants.RARE_TARGET_COMPONENT_PROPERTY, this);
  }

  @Override
  public void activate() {
    targetContainer.setVisible(true);

    if (theViewer != null) {
      theViewer.requestFocus();
    } else {
      targetContainer.requestFocus();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param disposeviewer {@inheritDoc}
   */
  @Override
  public void dispose(boolean disposeviewer) {
    if (targetContainer != null) {
      boolean disposed = targetContainer.isDisposed();    //container could be disposed before this is called;

      if (!disposed) {                                    //container could be disposed before this is called;
        targetContainer.setVisible(false);                // cause windows/dialogs to close
      }

      iWindowManager wm = appContext.getWindowManager();

      if (wm == null) {
        return;
      }

      wm.removeTarget(targetName);

      try {
        iViewer v;

        if (disposed) {
          v = theViewer;

          if (v != null) {
            v.targetLost(this);
          }
        } else {
          v = removeViewer();
        }

        if (v != null) {
          if (disposeviewer) {
            v.dispose();
          }
        }
      } catch(Throwable e) {
        appContext.getDefaultExceptionHandler().ignoreException(null, e);
      } finally {
        targetContainer = null;
      }
    }

    if ((transitionAnimator != null) && transitionAnimator.isAutoDispose()) {
      transitionAnimator.dispose();
    }

    targetContainer    = null;
    theViewer          = null;
    appContext         = null;
    transitionAnimator = null;
    animator           = null;
  }

  @Override
  public void reloadViewer() {
    iViewer v = theViewer;

    if (v != null) {
      v.reload(false);
    }
  }

  @Override
  public iViewer removeViewer() {
    if (theViewer != null) {
      removeComponentEx(theViewer.getContainerComponent());

      try {
        theViewer.targetLost(this);
      } catch(Exception ignore) {}

      setComponent(null);

      iViewer v = theViewer;

      theViewer = null;

      return v;
    }

    return null;
  }

  @Override
  public void repaint() {
    targetContainer.repaint();
  }

  @Override
  public void update() {
    targetContainer.revalidate();
  }

  @Override
  public void setBackgroundPainter(iBackgroundPainter painter) {
    UIComponentPainter.setBackgroundPainter(targetContainer, painter);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter painter) {
    targetContainer.setComponentPainter(painter);
  }

  public void setIgnoreViewerRenderType(boolean ignoreViewerRenderType) {
    this.ignoreViewerRenderType = ignoreViewerRenderType;
  }

  @Override
  public void setLinkedData(Object data) {
    linkedData = data;
  }

  @Override
  public void setLocked(boolean lock) {
    targetContainer.setLocked(lock);
  }

  public void setLockedColor(UIColor color) {
    targetContainer.setDisabledColor(color);
  }

  /**
   * Sets whether the target is in manual update mode
   * In manual update mode a call to update() must be made after a new widget has been set,
   * in order for the widget to be instantly sized and shown. If manual mode is false (the default)
   * then the update method is automatically called when a new widget is set.
   *
   * @param manual true if the target is in manual update mode; false otherwise
   */
  public void setManualUpdate(boolean manual) {
    this.manualUpdate = manual;
  }

  /**
   * Sets the target name
   *
   * @param name the name for the target
   */
  public void setName(String name) {
    targetName = name;
  }

  public void setPopupWindow(boolean popupWindow) {
    this.popupWindow = popupWindow;
  }

  @Override
  public boolean setRenderType(RenderType renderType) {
    if (targetContainer instanceof iTargetContainer) {
      ((iTargetContainer) targetContainer).setRenderType(renderType);

      return true;
    }

    return false;
  }

  @Override
  public void setTransitionAnimator(iTransitionAnimator animator) {
    this.transitionAnimator = animator;
  }

  @Override
  public iViewer setViewer(iViewer viewer) {
    if ((viewer == null) && (theViewer == null)) {
      return null;
    }

    if (transitionAnimator != null) {
      iViewer v = theViewer;

      setViewer(viewer, transitionAnimator, null);

      return v;
    }

    if ((viewer != null) && (viewer == theViewer) && isChildOfTargetContainer(viewer.getContainerComponent())) {
      if (!isManualUpdate()) {
        update();
      }

      return null;
    }

    if ((viewer != null) && (viewer.getTarget() != null)) {
      viewer.getTarget().removeViewer();
    }

    return setViewerEx(viewer);
  }

  public void setViewer(final iViewer viewer, iTransitionAnimator animator, final iFunctionCallback cb) {
    if ((viewer == null) && (theViewer == null)) {
      return;
    }

    if ((animator != null) && animator.isRunning()) {
      targetContainer.removeAll();
      animator.cancel();
    }

    if ((viewer != null) && (viewer == theViewer) && isChildOfTargetContainer(viewer.getContainerComponent())) {
      if (!isManualUpdate()) {
        update();
      }

      if (cb != null) {
        cb.finished(false, null);
      }

      return;
    }

    if ((viewer != null) && (viewer.getTarget() != null)) {
      viewer.getTarget().removeViewer();
    }

    iPlatformComponent outc = null;

    if (animator != null) {
      outc = aTransitionAnimator.resolveTransitionComponent(targetContainer, (theViewer == null)
              ? null
              : theViewer.getContainerComponent());
    }

    UIRectangle r = (outc == null)
                    ? null
                    : outc.getBounds();

    if ((outc != null) && ((r.width > 0) && (r.height > 0))) {
      animator.setOutgoingComponent(outc);
    } else {
      outc = null;
    }

    final iViewer v = setViewerEx(viewer);

    if (outc != null) {
      iFunctionCallback mycb = null;

      if (cb != null) {
        mycb = new iFunctionCallback() {
          @Override
          public void finished(boolean canceled, Object returnValue) {
            cb.finished(false, v);
          }
        };
      }

      iPlatformComponent inc = (viewer == null)
                               ? null
                               : viewer.getContainerComponent();

      if (inc == null) {
        inc = aTransitionAnimator.resolveTransitionComponent(targetContainer, null);
      }

      animator.setIncommingComponent(inc);
      animator.animate(targetContainer, r, mycb);
    } else {
      if (cb != null) {
        cb.finished(false, null);
      }
    }
  }

  @Override
  public void setVisible(boolean visible) {
    targetContainer.setVisible(visible);
  }

  @Override
  public iParentComponent getContainerComponent() {
    return targetContainer;
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
  public RenderType getRenderType() {
    return (targetContainer instanceof iTargetContainer)
           ? ((iTargetContainer) targetContainer).getRenderType()
           : null;
  }

  @Override
  public UIDimension getTargetSize() {
    return targetContainer.getSize();
  }

  @Override
  public iTransitionAnimator getTransitionAnimator() {
    return transitionAnimator;
  }

  @Override
  public iViewer getViewer() {
    return theViewer;
  }

  public boolean isDisposed() {
    return targetContainer == null;
  }

  @Override
  public boolean isHeadless() {
    return false;
  }

  public boolean isIgnoreViewerRenderType() {
    return ignoreViewerRenderType;
  }

  @Override
  public boolean isLocked() {
    return targetContainer.isLocked();
  }

  /**
   * Returns whether the target is in manual update mode
   *
   * @return true if the target is in manual update mode; false otherwise
   */
  public boolean isManualUpdate() {
    return manualUpdate;
  }

  @Override
  public boolean isPopupWindow() {
    return popupWindow;
  }

  @Override
  public boolean isVisible() {
    return targetContainer.isVisible();
  }

  protected void removeComponentEx(iPlatformComponent comp) {
    if (comp != null) {
      targetContainer.remove(comp);
    }
  }

  protected void removeViewerEx() {
    iViewer v = theViewer;

    theViewer = null;

    if (v != null) {
      v.targetLost(this);
      removeComponentEx(v.getContainerComponent());

      if (listener != null) {
        listener.viewerRemoved(theViewer);
      }
    }
  }

  protected void setComponent(iPlatformComponent comp) {
    setComponentEx(comp);

    if ((comp != null) &&!isManualUpdate()) {
      update();
    }
  }

  protected void setComponentEx(iPlatformComponent comp) {
    if (comp != null) {
      targetContainer.add(comp);
    }
  }

  protected iViewer setViewerEx(iViewer viewer) {
    iViewer v = theViewer;

    removeViewerEx();

    if (viewer != null) {
      if (!ignoreViewerRenderType) {
        PlatformHelper.setTargetRenderType(this, viewer.getRenderType());
      }

      theViewer = viewer;
      setComponentEx(theViewer.getContainerComponent());
      viewer.pageHome();
      viewer.targetAcquired(this);

      if (!isManualUpdate()) {
        update();
      }

      if (listener != null) {
        listener.viewerSet(theViewer);
      }
    } else {
      setComponent(null);
    }

    return v;
  }

  protected boolean isChildOfTargetContainer(iPlatformComponent c) {
    return c.getParent() == targetContainer;
  }

  public static class DelegatingTarget extends UITarget {
    iParentComponent delegateComponent;

    public DelegatingTarget(iPlatformAppContext app, String name, iParentComponent container, boolean register) {
      this(app, name, container, register, null);
    }

    public DelegatingTarget(iPlatformAppContext app, String name, iParentComponent container, boolean register,
                            iListener listener) {
      super(app, name, PlatformHelper.createTargetContainer(app), register, listener);
      targetContainer.add(container);
      delegateComponent = container;
    }

    @Override
    protected void removeComponentEx(iPlatformComponent comp) {
      if (comp != null) {
        delegateComponent.remove(comp);
      }
    }

    @Override
    protected void setComponentEx(iPlatformComponent comp) {
      if (comp != null) {
        delegateComponent.add(comp);
      }
    }
  }
}
