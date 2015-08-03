//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-collapsible/com/appnativa/rare/ui/aCollapsiblePane.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaCollapsiblePane_H_
#define _RAREaCollapsiblePane_H_

@class RAREExpansionEvent;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_OrientationEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUICompoundBorder;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREaCollapsiblePane_PaneSizingAnimation;
@class RAREaCollapsiblePane_aTitleComponent;
@class RAREiPaintedButton_ButtonStateEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilMap;
@protocol RAREiActionListener;
@protocol RAREiChangeListener;
@protocol RAREiCollapsible_iTitleProvider;
@protocol RAREiExpandedListener;
@protocol RAREiExpansionListener;
@protocol RAREiParentComponent;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTransitionAnimator;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/effects/SizeAnimation.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iCollapsible.h"

@interface RAREaCollapsiblePane : RAREBorderPanel < RAREiCollapsible > {
 @public
  RAREaCollapsiblePane_PaneSizingAnimation *sizingAnimation_;
  BOOL paneExpanded_;
  BOOL userControllable_;
  BOOL toggleOnTitleSingleClick_;
  BOOL titleIconOnLeft_;
  BOOL eventsEnabled_;
  BOOL animateTransitions_;
  id<RAREiPlatformIcon> collapseIcon_;
  id<JavaLangCharSequence> collapseTip_;
  id<JavaLangCharSequence> collapsedTitle_;
  RAREExpansionEvent *eventObject_;
  id<RAREiPlatformIcon> expandIcon_;
  id<JavaLangCharSequence> expandTip_;
  BOOL inAnimation_;
  id<RAREiPlatformComponent> mainComponent_;
  id<JavaLangCharSequence> paneTitle_;
  RAREUICompoundBorder *titleBorder_;
  RAREaCollapsiblePane_aTitleComponent *titleComponent_;
  id<RAREiPlatformIcon> titleIcon_;
  id<RAREiCollapsible_iTitleProvider> titleProvider_;
  id<RAREiTransitionAnimator> transitionAnimator_;
  BOOL useChevron_;
}

- (id)init;
- (id)initWithId:(id)view;
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints;
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position;
- (void)addExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l;
- (void)addExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l;
- (void)collapsePane;
- (void)dispose;
- (void)disposePane;
- (void)expandPane;
- (void)removeExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l;
- (void)removeExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l;
- (void)togglePane;
- (void)setAnimateTransitionsWithBoolean:(BOOL)animateTransitions;
- (void)setAnimationOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)setAnimatorOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)setCollapseIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setCollapseTipWithNSString:(NSString *)collapseTip;
- (void)setCollapsedWithBoolean:(BOOL)collapsed;
- (void)setCollapsedTitleWithNSString:(NSString *)title;
- (void)setContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setEventsEnabledWithBoolean:(BOOL)eventsEnabled;
- (void)setExpandIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setExpandOnDragOverWithBoolean:(BOOL)expand;
- (void)setExpandTipWithNSString:(NSString *)expandTip;
- (void)setShowTitleWithBoolean:(BOOL)show;
- (void)setTitleBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setTitleBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (void)setTitleFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setTitleForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setTitleIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTitleIconOnLeftWithBoolean:(BOOL)flag;
- (void)setTitleIconToolTipWithNSString:(NSString *)tooltip;
- (void)setTitleOpaqueWithBoolean:(BOOL)opaque;
- (void)setTitleProviderWithRAREiCollapsible_iTitleProvider:(id<RAREiCollapsible_iTitleProvider>)tp;
- (void)setTitleTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setTitleTextComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (id<RAREiPlatformComponent>)getTitleTextComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)setTitleTextHAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)align;
- (void)setToggleOnTitleSingleClickWithBoolean:(BOOL)toggle;
- (void)setUseChevronWithBoolean:(BOOL)useChevron;
- (void)setUserControllableWithBoolean:(BOOL)uc;
- (id<JavaLangCharSequence>)getColapsedTitle;
- (id<JavaLangCharSequence>)getCollapseTip;
- (id<RAREiPlatformComponent>)getContent;
- (id<JavaLangCharSequence>)getExpandTip;
- (id<RAREiParentComponent>)getPane;
- (id<JavaLangCharSequence>)getTitle;
- (id<RAREiPlatformComponent>)getTitleComponent;
- (id<RAREiPlatformIcon>)getTitleIcon;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (BOOL)isAnimateTransitions;
- (BOOL)isEventsEnabled;
- (BOOL)isExpanded;
- (BOOL)isUserControllable;
- (void)configuredVisualsForState;
- (void)createAndAddTitleLabel;
- (RAREaCollapsiblePane_PaneSizingAnimation *)createAnimation;
- (id<RAREiPlatformIcon>)createChevronIconWithBoolean:(BOOL)up;
- (id<RAREiPlatformIcon>)createTwistyIconWithBoolean:(BOOL)up;
- (void)fireExpandedWithBoolean:(BOOL)expanded;
- (void)fireExpansionWithBoolean:(BOOL)expanded;
- (void)initComponents OBJC_METHOD_FAMILY_NONE;
- (void)initIcons OBJC_METHOD_FAMILY_NONE;
- (BOOL)needsHiearachyInvalidated;
- (void)refreshWithBoolean:(BOOL)fire;
- (void)togglePaneExWithBoolean:(BOOL)fire;
- (void)setIcons;
- (void)setMainComponentVisibleWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                              withBoolean:(BOOL)visible;
- (void)setTitleVisibleWithBoolean:(BOOL)visible;
- (void)copyAllFieldsTo:(RAREaCollapsiblePane *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, sizingAnimation_, RAREaCollapsiblePane_PaneSizingAnimation *)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, collapseIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, collapseTip_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, collapsedTitle_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, eventObject_, RAREExpansionEvent *)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, expandIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, expandTip_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, mainComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, paneTitle_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, titleBorder_, RAREUICompoundBorder *)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, titleComponent_, RAREaCollapsiblePane_aTitleComponent *)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, titleIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, titleProvider_, id<RAREiCollapsible_iTitleProvider>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane, transitionAnimator_, id<RAREiTransitionAnimator>)

typedef RAREaCollapsiblePane ComAppnativaRareUiACollapsiblePane;

@interface RAREaCollapsiblePane_PaneSizingAnimation : RARESizeAnimation {
 @public
  __weak RAREaCollapsiblePane *this$0_;
}

- (id)initWithRAREaCollapsiblePane:(RAREaCollapsiblePane *)outer$;
- (void)animateTransition;
- (void)notifyListenersWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                     withBoolean:(BOOL)ended;
@end

@interface RAREaCollapsiblePane_aTitleComponent : RAREBorderPanel < RAREiActionComponent > {
 @public
  id<RAREiPlatformIcon> icon_;
  id<RAREiActionComponent> iconLabel_;
  BOOL iconOnLeft_;
  id<RAREiActionComponent> titleLabel_;
  id<RAREiActionComponent> twistyLabel_;
}

- (id)init;
- (id)initWithId:(id)view;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)dispose;
- (void)doClick;
- (void)fireActionEvent;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)setActionWithRAREUIAction:(RAREUIAction *)a;
- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)hal
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)val;
- (void)setCenterViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setDisabledSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconGapWithInt:(int)iconGap;
- (void)setIconOnLeftWithBoolean:(BOOL)iconOnLeft;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)iconPosition;
- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left;
- (void)setMnemonicWithChar:(unichar)mn;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)orientation;
- (void)setPressedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)pressedIcon;
- (void)setRolloverIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setRolloverSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedIcon;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setToolTipTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setTwistyWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (RAREUIAction *)getAction;
- (RAREiPaintedButton_ButtonStateEnum *)getButtonState;
- (id<RAREiPlatformIcon>)getDisabledIcon;
- (id<RAREiPlatformIcon>)getIcon;
- (int)getIconGap;
- (RARERenderableDataItem_IconPositionEnum *)getIconPosition;
- (RAREUIInsets *)getMargin;
- (id<RAREiPlatformIcon>)getPressedIcon;
- (id<RAREiPlatformIcon>)getSelectedIcon;
- (id<JavaLangCharSequence>)getText;
- (BOOL)isIconOnLeft;
- (BOOL)isWordWrap;
- (id<RAREiActionComponent>)createIconComponent;
- (void)updateTwistyIcon;
- (void)copyAllFieldsTo:(RAREaCollapsiblePane_aTitleComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCollapsiblePane_aTitleComponent, icon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane_aTitleComponent, iconLabel_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane_aTitleComponent, titleLabel_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaCollapsiblePane_aTitleComponent, twistyLabel_, id<RAREiActionComponent>)

#endif // _RAREaCollapsiblePane_H_