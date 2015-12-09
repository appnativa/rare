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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.effects.SizeAnimation;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.ExpansionEvent.Type;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.WeakOuter;

import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aCollapsiblePane extends BorderPanel implements iCollapsible {
  PaneSizingAnimation           sizingAnimation;
  protected boolean             paneExpanded             = true;
  protected boolean             userControllable         = true;
  protected boolean             toggleOnTitleSingleClick = false;
  protected boolean             titleIconOnLeft          = false;
  protected boolean             eventsEnabled            = true;
  protected boolean             animateTransitions;
  protected iPlatformIcon       collapseIcon;
  protected CharSequence        collapseTip;
  protected CharSequence        collapsedTitle;
  protected ExpansionEvent      eventCollapsed;
  protected ExpansionEvent      eventExpanded;
  protected ExpansionEvent      eventWillCollapse;
  protected ExpansionEvent      eventWillExpand;
  protected iPlatformIcon       expandIcon;
  protected CharSequence        expandTip;
  protected volatile boolean    inAnimation;
  protected iPlatformComponent  mainComponent;
  protected CharSequence        paneTitle;
  protected UICompoundBorder    titleBorder;
  protected aTitleComponent     titleComponent;
  protected iPlatformIcon       titleIcon;
  protected iTitleProvider      titleProvider;
  protected iTransitionAnimator transitionAnimator;
  protected boolean             useChevron;

  public aCollapsiblePane() {
    super();
  }

  public aCollapsiblePane(Object view) {
    super(view);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints) {
    add(c, constraints, -1);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (constraints instanceof Location) {
      super.add(c, constraints, position);
    } else {
      setContent(c);
    }
  }

  @Override
  public void addExpandedListener(iExpandedListener l) {
    getEventListenerList().add(iExpandedListener.class, l);
  }

  @Override
  public void addExpansionListener(iExpansionListener l) {
    getEventListenerList().add(iExpansionListener.class, l);
  }

  @Override
  public void collapsePane() {
    if (paneExpanded) {
      togglePane();
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    if ((transitionAnimator != null) && transitionAnimator.isAutoDispose()) {
      transitionAnimator.dispose();
    }

    transitionAnimator = null;
    disposePane();
  }

  @Override
  public void disposePane() {
    if (listenerList != null) {
      listenerList.clear();
    }

    listenerList       = null;
    transitionAnimator = null;
    titleProvider      = null;
    titleIcon          = null;
    titleComponent     = null;
    titleBorder        = null;
    mainComponent      = null;
    expandIcon         = null;
    eventWillCollapse  = null;
    eventWillExpand    = null;
    eventCollapsed     = null;
    eventExpanded      = null;
    collapseIcon       = null;
  }

  @Override
  public void expandPane() {
    if (!paneExpanded) {
      togglePane();
    }
  }

  @Override
  public void removeExpandedListener(iExpandedListener l) {
    if (listenerList != null) {
      listenerList.remove(iExpandedListener.class, l);
    }
  }

  @Override
  public void removeExpansionListener(iExpansionListener l) {
    if (listenerList != null) {
      listenerList.remove(iExpansionListener.class, l);
    }
  }

  @Override
  public void togglePane() {
    inAnimation = false;

    try {
      fireExpansion(!paneExpanded);
    } catch(ExpandVetoException ignored) {
      return;
    }

    final boolean expand = !paneExpanded;

    if (expand) {
      togglePaneEx(false);
    }

    if (expand) {
      refresh(true);
    } else {
      togglePaneEx(true);
    }
  }

  @Override
  public void setAnimateTransitions(boolean animateTransitions) {
    this.animateTransitions = animateTransitions;

    if (animateTransitions) {
      if (sizingAnimation == null) {
        sizingAnimation = createAnimation();
      }
    } else if (sizingAnimation != null) {
      sizingAnimation.dispose();
      sizingAnimation = null;
    }
  }

  public void setAnimationOptions(Map options) {
    if (sizingAnimation == null) {
      sizingAnimation = createAnimation();
    }

    if (options != null) {
      sizingAnimation.handleProperties(options);
    }
  }

  @Override
  public void setAnimatorOptions(Map options) {}

  @Override
  public void setCollapseIcon(iPlatformIcon icon) {
    this.collapseIcon = icon;

    if (this.paneExpanded && userControllable) {
      setIcons();
    }
  }

  @Override
  public void setCollapseTip(String collapseTip) {
    this.collapseTip = collapseTip;
  }

  @Override
  public void setCollapsed(boolean collapsed) {
    if (paneExpanded) {
      togglePane();
    }
  }

  @Override
  public void setCollapsedTitle(String title) {
    collapsedTitle = title;

    if (!paneExpanded) {
      titleComponent.setText(title);
    }
  }

  @Override
  public void setContent(iPlatformComponent c) {
    if (mainComponent != null) {
      remove(mainComponent);
    }

    mainComponent = c;
    setMainComponentVisible(c, paneExpanded);

    CharSequence title = null;

    if (titleProvider != null) {
      title = paneExpanded
              ? titleProvider.getExpandedTitle(this, c)
              : titleProvider.getCollapsedTitle(this, c);
    }

    if (title == null) {
      title = paneTitle;

      if (!paneExpanded && (collapsedTitle != null)) {
        title = collapsedTitle;
      }
    }

    titleComponent.setText(title);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    titleComponent.setEnabled(enabled);
  }

  /**
   * @param eventsEnabled
   *          the eventsEnabled to set
   */
  @Override
  public void setEventsEnabled(boolean eventsEnabled) {
    this.eventsEnabled = eventsEnabled;
  }

  @Override
  public void setExpandIcon(iPlatformIcon icon) {
    this.expandIcon = icon;

    if (!this.paneExpanded && userControllable) {
      setIcons();
    }
  }

  @Override
  public void setExpandOnDragOver(boolean expand) {}

  @Override
  public void setExpandTip(String expandTip) {
    this.expandTip = expandTip;
  }

  @Override
  public void setShowTitle(boolean show) {
    setTitleVisible(show);
  }

  @Override
  public void setTitleBackground(UIColor bg) {
    titleComponent.setBackground(bg);
  }

  @Override
  public void setTitleBorder(iPlatformBorder b) {
    titleBorder.setOutsideBorder(b);
    titleComponent.setBorder(titleBorder);
  }

  @Override
  public void setTitleFont(UIFont font) {
    titleComponent.setFont(font);
  }

  @Override
  public void setTitleForeground(UIColor fg) {
    titleComponent.setForeground(fg);
  }

  @Override
  public void setTitleIcon(iPlatformIcon icon) {
    titleIcon = icon;
    setIcons();
  }

  @Override
  public void setTitleIconOnLeft(boolean flag) {
    if (titleIconOnLeft != flag) {
      titleIconOnLeft = flag;
      titleComponent.setIconOnLeft(flag);
      setIcons();
    }
  }

  public void setTitleIconToolTip(String tooltip) {}

  @Override
  public void setTitleOpaque(boolean opaque) {
    if (opaque) {
      titleComponent.setOpaque(true);

      if ((titleComponent.getComponentPainter() == null)
          || (titleComponent.getComponentPainter().getBackgroundPainter() == null)) {
        Utils.setBackgroundPainter(titleComponent, UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_LT);
      }
    } else {
      titleComponent.setOpaque(false);

      if ((titleComponent.getComponentPainter() != null)
          && (titleComponent.getComponentPainter().getBackgroundPainter()
              == UIBackgroundPainter.BGCOLOR_GRADIENT_PAINTER_LT)) {
        titleComponent.getComponentPainter().setBackgroundPainter(null, false);
        titleComponent.getComponentPainter().setBackgroundColor(null);
      }
    }
  }

  @Override
  public void setTitleProvider(iTitleProvider tp) {
    titleProvider = tp;
  }

  @Override
  public void setTitleText(CharSequence title) {
    titleComponent.setText(title);
    paneTitle = title;
  }

  /**
   * Sets a component that will be replace the
   * the component used to display the title text
   *
   * @param component the component
   */
  public void setTitleTextComponent(iPlatformComponent component) {
    titleComponent.setCenterView(component);
  }

  /**
   * Gets the component that is used to display the title text
   *
   * @return the component
   */
  public iPlatformComponent getTitleTextComponent(iPlatformComponent component) {
    return titleComponent.getCenterView();
  }

  @Override
  public void setTitleTextHAlignment(HorizontalAlign align) {
    titleComponent.setAlignment(align, VerticalAlign.CENTER);
  }

  @Override
  public void setToggleOnTitleSingleClick(boolean toggle) {
    toggleOnTitleSingleClick = toggle;
  }

  @Override
  public void setUseChevron(boolean useChevron) {
    this.useChevron = useChevron;
    setTitleIconOnLeft(useChevron);
    initIcons();
  }

  @Override
  public void setUserControllable(boolean uc) {
    this.userControllable = uc;

    if (uc && (collapseIcon == null)) {
      initIcons();
    }

    revalidate();
    repaint();
  }

  public CharSequence getColapsedTitle() {
    return collapsedTitle;
  }

  public CharSequence getCollapseTip() {
    return collapseTip;
  }

  @Override
  public iPlatformComponent getContent() {
    return mainComponent;
  }

  public CharSequence getExpandTip() {
    return expandTip;
  }

  @Override
  public iParentComponent getPane() {
    return this;
  }

  @Override
  public CharSequence getTitle() {
    return paneTitle;
  }

  @Override
  public iPlatformComponent getTitleComponent() {
    return titleComponent;
  }

  @Override
  public iPlatformIcon getTitleIcon() {
    return titleIcon;
  }

  public iTransitionAnimator getTransitionAnimator() {
    return transitionAnimator;
  }

  public boolean isAnimateTransitions() {
    return animateTransitions;
  }

  /**
   * @return the eventsEnabled
   */
  @Override
  public boolean isEventsEnabled() {
    return eventsEnabled;
  }

  @Override
  public boolean isExpanded() {
    return paneExpanded;
  }

  @Override
  public boolean isUserControllable() {
    return userControllable;
  }

  /**
   * Configures the titlepane visuals based on its current expanded/collapsed
   */
  protected void configuredVisualsForState() {
    CharSequence       title = null;
    iPlatformComponent c     = mainComponent;

    if (titleProvider != null) {
      title = paneExpanded
              ? titleProvider.getExpandedTitle(this, c)
              : titleProvider.getCollapsedTitle(this, c);

      if ((title != null) && (title.length() > 0)) {
        titleComponent.setText(title);
      } else {
        title = null;
      }
    }

    if ((title == null) && (paneTitle != null)) {
      title = paneTitle;

      if (!paneExpanded && (collapsedTitle != null)) {
        title = collapsedTitle;
      }

      titleComponent.setText(title);
    }

    setIcons();
  }

  protected abstract void createAndAddTitleLabel();

  protected PaneSizingAnimation createAnimation() {
    return new PaneSizingAnimation();
  }

  protected abstract iPlatformIcon createChevronIcon(boolean up);

  protected abstract iPlatformIcon createTwistyIcon(boolean up);

  protected void fireExpanded(boolean expanded) {
    if ((listenerList == null) ||!isEventsEnabled()) {
      return;
    }

    iPlatformComponent c                = mainComponent;
    iWidget            w                = Platform.findWidgetForComponent(c);
    iExpandedListener  expandedListener = null;

    if ((w != null) && (w.getViewer() != null)) {
      expandedListener = w.getViewer().getExpandedListener();
    }

    if (eventCollapsed == null) {
      eventCollapsed = new ExpansionEvent(this, Type.HAS_COLLAPSED);
      eventExpanded  = new ExpansionEvent(this, Type.HAS_EXPANDED);
    }

    eventCollapsed.setItem(mainComponent);
    eventExpanded.setItem(mainComponent);

    if (expandedListener != null) {
      if (expanded) {
        expandedListener.itemHasExpanded(eventExpanded);
      } else {
        expandedListener.itemHasCollapsed(eventCollapsed);
      }
    }

    Object[]          listeners = listenerList.getListenerList();
    iExpandedListener t;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iExpandedListener.class) {
        t = (iExpandedListener) listeners[i + 1];

        if (expanded) {
          t.itemHasExpanded(eventExpanded);
        } else {
          t.itemHasCollapsed(eventCollapsed);
        }
      }
    }
  }

  protected void fireExpansion(boolean expanded) throws ExpandVetoException {
    if ((listenerList == null) ||!isEventsEnabled()) {
      return;
    }

    if (eventWillCollapse == null) {
      eventWillCollapse = new ExpansionEvent(this, Type.WILL_COLLAPSE);
      eventWillExpand   = new ExpansionEvent(this, Type.WILL_EXPAND);
    }

    eventWillCollapse.setItem(mainComponent);
    eventWillExpand.setItem(mainComponent);

    Object[]           listeners = listenerList.getListenerList();
    iExpansionListener t;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iExpansionListener.class) {
        t = (iExpansionListener) listeners[i + 1];

        if (expanded) {
          t.itemWillExpand(eventWillExpand);
        } else {
          t.itemWillCollapse(eventWillCollapse);
        }
      }
    }
  }

  /**
   * Initialize the components
   */
  protected void initComponents() {
    createAndAddTitleLabel();

    iPlatformBorder in  = BorderUtils.TWO_POINT_EMPTY_BORDER;
    iPlatformBorder out = BorderUtils.EMPTY_BORDER;

    titleBorder = new UICompoundBorder(out, in);
    titleComponent.setBorder(titleBorder);
    setIcons();
    setTitleOpaque(true);
  }

  /**
   * Initialize the icons
   */
  protected void initIcons() {
    expandIcon   = Platform.getUIDefaults().getImageIcon("Rare.CollapsiblePane.collapsedIcon");
    collapseIcon = Platform.getUIDefaults().getImageIcon("Rare.CollapsiblePane.expandedIcon");

    if (expandIcon == null) {
      expandIcon = useChevron
                   ? createChevronIcon(false)
                   : createTwistyIcon(true);
    }

    if (collapseIcon == null) {
      collapseIcon = useChevron
                     ? createChevronIcon(true)
                     : createTwistyIcon(false);
    }

    setIcons();
  }

  @Override
  protected boolean needsHiearachyInvalidated() {
    return true;
  }

  protected void refresh(boolean fire) {
    revalidate();

    if (fire) {
      repaint();
      fireExpanded(paneExpanded);
    }
  }

  protected void togglePaneEx(boolean fire) {
    paneExpanded = !paneExpanded;
    configuredVisualsForState();

    iPlatformComponent c = mainComponent;

    if (c != null) {
      setMainComponentVisible(c, paneExpanded);
      refresh(fire);

      if (animateTransitions) {
        sizingAnimation.animateTransition();
      }
    }
  }

  protected void setIcons() {
    if (!titleComponent.isVisible()) {
      return;
    }

    if (isUserControllable() && (collapseIcon != null)) {
      if (isExpanded()) {
        titleComponent.setTwisty(collapseIcon);
      } else {
        titleComponent.setTwisty(expandIcon);
      }
    }
  }

  protected void setMainComponentVisible(iPlatformComponent c, boolean visible) {
    if (c != null) {
      if (visible) {
        setCenterView(c);
      } else {
        remove(c);
      }
    }
  }

  protected void setTitleVisible(boolean visible) {
    if (visible) {
      setTopView(titleComponent);
    } else {
      remove(titleComponent);
    }
  }

  @WeakOuter
  class PaneSizingAnimation extends SizeAnimation {
    public PaneSizingAnimation() {
      super(false);
      diagonal = false;
    }

    public void animateTransition() {
      UIDimension        md = paneExpanded
                              ? mainComponent.getPreferredSize()
                              : mainComponent.getSize();
      UIDimension        d  = getPreferredSize();
      UIRectangle        to = new UIRectangle(getX(), getY(), getWidth(), d.height);
      AnimationComponent c  = PlatformHelper.createAnimationComponent(getWidget());

      c.setComponent(mainComponent, md.width, md.height);
      setCenterView(c);
      animationComponent = c;
      animateTo(aCollapsiblePane.this, to);
    }

    @Override
    protected void notifyListeners(iPlatformAnimator animator, boolean ended) {
      super.notifyListeners(animator, ended);

      if (ended) {
        mainComponent.removeFromParent();
        animationComponent.removeFromParent();

        if (paneExpanded) {
          setCenterView(mainComponent);
        }

        clear();
        revalidate();
      }
    }
  }


  protected static abstract class aTitleComponent extends BorderPanel implements iActionComponent {
    protected iPlatformIcon    icon;
    protected iActionComponent iconLabel;
    protected boolean          iconOnLeft;;
    protected iActionComponent titleLabel;
    protected iActionComponent twistyLabel;

    public aTitleComponent() {
      super();
    }

    public aTitleComponent(Object view) {
      super(view);
    }

    public void setAutoAdjustSize(boolean adjustSize) {}

    @Override
    public void addActionListener(iActionListener l) {}

    @Override
    public void addChangeListener(iChangeListener l) {}

    @Override
    public void dispose() {
      if (titleLabel != null) {
        titleLabel.dispose();
      }

      if (twistyLabel != null) {
        twistyLabel.dispose();
      }

      if (iconLabel != null) {
        iconLabel.dispose();
      }

      super.dispose();
      twistyLabel = null;
      titleLabel  = null;
      iconLabel   = null;
      icon        = null;
    }

    @Override
    public void doClick() {}

    @Override
    public void fireActionEvent() {}

    @Override
    public void removeActionListener(iActionListener l) {}

    @Override
    public void removeChangeListener(iChangeListener l) {}

    @Override
    public void setAction(UIAction a) {}

    @Override
    public void setAlignment(HorizontalAlign hal, VerticalAlign val) {}

    @Override
    public void setCenterView(iPlatformComponent c) {
      super.setCenterView(c);

      if (c instanceof iActionComponent) {
        ((iActionComponent) c).setWordWrap(true);
      }
    }

    @Override
    public void setDisabledIcon(iPlatformIcon icon) {}

    @Override
    public void setDisabledSelectedIcon(iPlatformIcon icon) {}

    @Override
    public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      titleLabel.setEnabled(enabled);

      if (iconLabel != null) {
        iconLabel.setEnabled(enabled);
      }

      if (twistyLabel != null) {
        twistyLabel.setEnabled(enabled);
      }
    }

    @Override
    public void setFont(UIFont f) {
      super.setFont(f);

      if (f != null) {
        titleLabel.setFont(f);
        updateTwistyIcon();
      }
    }

    @Override
    public void setForeground(UIColor fg) {
      super.setForeground(fg);

      if (fg != null) {
        titleLabel.setForeground(fg);
      }
    }

    @Override
    public void setIcon(iPlatformIcon icon) {
      if (iconLabel == null) {
        iconLabel = createIconComponent();
        iconLabel.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.FILL);
        iconLabel.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.FILL);
      }

      iconLabel.setIcon(icon);

      if (iconOnLeft) {
        setLeftView(iconLabel);
        iconLabel.setIconPosition(IconPosition.LEFT);
      } else {
        setRightView(iconLabel);
        iconLabel.setIconPosition(IconPosition.RIGHT_JUSTIFIED);
      }
    }

    @Override
    public void setIconGap(int iconGap) {}

    public void setIconOnLeft(boolean iconOnLeft) {
      this.iconOnLeft = iconOnLeft;
    }

    @Override
    public void setIconPosition(IconPosition iconPosition) {
      if (iconPosition != null) {
        switch(iconPosition) {
          case RIGHT_JUSTIFIED :
          case RIGHT :
          case TRAILING :
            iconOnLeft = false;

            break;

          default :
            iconOnLeft = true;

            break;
        }
      }
    }

    @Override
    public void setMargin(UIInsets insets) {
      if (insets == null) {
        setMargin(0, 0, 0, 0);
      } else {
        setMargin(insets.top, insets.right, insets.bottom, insets.left);
      }
    }

    @Override
    public void setMargin(float top, float right, float bottom, float left) {
      if (titleLabel != null) {
        titleLabel.setMargin(top, right, bottom, left);
      }
    }

    @Override
    public void setMnemonic(char mn) {}

    @Override
    public void setOrientation(Orientation orientation) {}

    @Override
    public void setPressedIcon(iPlatformIcon pressedIcon) {}

    public void setRolloverIcon(iPlatformIcon icon) {}

    public void setRolloverSelectedIcon(iPlatformIcon icon) {}

    @Override
    public void setSelectedIcon(iPlatformIcon selectedIcon) {}

    @Override
    public void setText(CharSequence text) {
      titleLabel.setText((text == null)
                         ? null
                         : text.toString());
      updateTwistyIcon();
    }

    @Override
    public void setToolTipText(CharSequence text) {
      titleLabel.setToolTipText(text);
    }

    public void setTwisty(iPlatformIcon icon) {
      if (twistyLabel == null) {
        twistyLabel = createIconComponent();
        twistyLabel.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, HorizontalAlign.FILL);
        twistyLabel.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, VerticalAlign.FILL);
      }

      twistyLabel.setIcon(icon);

      if (!iconOnLeft) {
        setLeftView(twistyLabel);
        twistyLabel.setIconPosition(IconPosition.LEFT);
      } else {
        setRightView(twistyLabel);
        twistyLabel.setIconPosition(IconPosition.RIGHT_JUSTIFIED);
      }

      updateTwistyIcon();
    }

    @Override
    public void setWordWrap(boolean wrap) {
      if (titleLabel != null) {
        titleLabel.setWordWrap(wrap);
      }
    }

    @Override
    public UIAction getAction() {
      return null;
    }

    @Override
    public iPaintedButton.ButtonState getButtonState() {
      return Utils.getState(isEnabled(), isPressed(), isSelected(), false);
    }

    @Override
    public iPlatformIcon getDisabledIcon() {
      return null;
    }

    @Override
    public iPlatformIcon getIcon() {
      return icon;
    }

    @Override
    public int getIconGap() {
      return 4;
    }

    @Override
    public IconPosition getIconPosition() {
      return iconOnLeft
             ? IconPosition.LEFT
             : IconPosition.RIGHT_JUSTIFIED;
    }

    @Override
    public UIInsets getMargin() {
      if (titleLabel != null) {
        return titleLabel.getMargin();
      }

      return new UIInsets(0);
    }

    @Override
    public iPlatformIcon getPressedIcon() {
      return icon;
    }

    @Override
    public iPlatformIcon getSelectedIcon() {
      return icon;
    }

    @Override
    public CharSequence getText() {
      return titleLabel.getText();
    }

    public boolean isIconOnLeft() {
      return iconOnLeft;
    }

    @Override
    public boolean isWordWrap() {
      if (titleLabel != null) {
        return titleLabel.isWordWrap();
      }

      return false;
    }

    protected abstract iActionComponent createIconComponent();

    protected void updateTwistyIcon() {
      if (twistyLabel != null) {
        iPlatformIcon icon = twistyLabel.getIcon();

        if (icon instanceof PainterUtils.ChevronIcon) {
          int n = titleLabel.getPreferredSize().intHeight();

          n = Math.max(n, FontUtils.getDefaultLineHeight());
          ((PainterUtils.ChevronIcon) icon).setIconSize(n);
        }

        if (icon instanceof PainterUtils.TwistyIcon) {
          int n = titleLabel.getPreferredSize().intHeight();

          n = Math.max(n, FontUtils.getDefaultLineHeight());
          ((PainterUtils.TwistyIcon) icon).setIconSize(n);
        }

        twistyLabel.setIcon(null);
        twistyLabel.setIcon(icon);
      }
    }
  }
}
