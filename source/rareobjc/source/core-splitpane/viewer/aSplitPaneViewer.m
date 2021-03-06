//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-splitpane/com/appnativa/rare/viewer/aSplitPaneViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSFloatArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/Region.h"
#include "com/appnativa/rare/spot/SplitPane.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/ui/SplitPanePanel.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/effects/TransitionAnimator.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "com/appnativa/rare/viewer/aPlatformRegionViewer.h"
#include "com/appnativa/rare/viewer/aRegionViewer.h"
#include "com/appnativa/rare/viewer/aSplitPaneViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "java/lang/Boolean.h"
#include "java/util/Map.h"

@implementation RAREaSplitPaneViewer

- (id)init {
  return [self initRAREaSplitPaneViewerWithRAREiContainer:nil];
}

- (id)initRAREaSplitPaneViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum SplitPane];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaSplitPaneViewerWithRAREiContainer:parent];
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  vcfg = [self checkForURLConfigWithRARESPOTViewer:vcfg];
  [self configureExWithRARESPOTSplitPane:(RARESPOTSplitPane *) check_class_cast(vcfg, [RARESPOTSplitPane class])];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)reverseRegions {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) reverseRegions];
}

- (void)toggleOrientationWithBoolean:(BOOL)splitEvenly {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) toggleOrientationWithBoolean:splitEvenly];
}

- (void)setSplitProportionsWithFloatArray:(IOSFloatArray *)props {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) setProportionsWithFloatArray:props];
}

- (IOSFloatArray *)getSplitProportions {
  return [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) getProportions];
}

- (int)getMinimumSizePanePosition {
  return [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) getMinimumSizePanePosition];
}

- (void)setUseMinimumSizeOfPaneAtWithInt:(int)panePosition {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) setUseMinimumSizeOfPaneAtWithInt:panePosition];
}

- (void)setUserResizeableWithBoolean:(BOOL)userResizeable {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) setUserResizeableWithBoolean:userResizeable];
}

- (void)onConfigurationChangedWithBoolean:(BOOL)reset {
  if (reset) {
    [self reset];
  }
  else {
    [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) checkOrientationWithId:nil];
    [super onConfigurationChangedWithBoolean:reset];
  }
}

- (void)onConfigurationWillChangeWithId:(id)newConfig {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) checkOrientationWithId:newConfig];
  [super onConfigurationWillChangeWithId:newConfig];
}

- (void)setContinuousLayoutWithBoolean:(BOOL)continuous {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) setContinuousLayoutWithBoolean:continuous];
}

- (BOOL)isTopToBottom {
  return [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) isTopToBottom];
}

- (void)setTopToBottomWithBoolean:(BOOL)topToBottomSplit {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) setTopToBottomWithBoolean:topToBottomSplit];
}

- (void)setAutoOrientWithBoolean:(BOOL)autoOrient {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) setAutoOrientWithBoolean:autoOrient];
}

- (BOOL)isUserResizeable {
  return [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) isUserResizeable];
}

- (void)configureExWithRARESPOTSplitPane:(RARESPOTSplitPane *)cfg {
  RARESplitPanePanel *splitPane = [[RARESplitPanePanel alloc] initWithRAREiWidget:self];
  formComponent_ = dataComponent_ = splitPane;
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  actAsFormViewer_ = [((SPOTBoolean *) nil_chk(((RARESPOTSplitPane *) nil_chk(cfg))->actAsFormViewer_)) booleanValue];
  if ([((RARESPOTSplitPane_CSplitOrientation *) nil_chk(cfg->splitOrientation_)) spot_valueWasSet]) {
    [splitPane setLeftToRightSplitWithBoolean:[cfg->splitOrientation_ intValue] == RARESPOTSplitPane_CSplitOrientation_left_to_right];
  }
  if ([((SPOTInteger *) nil_chk(cfg->dividerSize_)) spot_hasValue]) {
    [splitPane setDividerSizeWithFloat:[cfg->dividerSize_ intValue]];
  }
  if ([((SPOTBoolean *) nil_chk(cfg->continuousLayout_)) spot_valueWasSet]) {
    [splitPane setContinuousLayoutWithBoolean:[cfg->continuousLayout_ booleanValue]];
  }
  if ([((SPOTBoolean *) nil_chk(cfg->oneTouchExpandable_)) booleanValue]) {
    [splitPane setOneTouchExpandableWithBoolean:YES];
  }
  [splitPane setAutoAdjustProportionsWithBoolean:[((SPOTBoolean *) nil_chk(cfg->autoAdjustProportions_)) booleanValue]];
  int len = [((SPOTSet *) nil_chk(cfg->regions_)) getCount];
  RARESPOTRegion *region;
  id<RAREiParentComponent> panel;
  NSString *name;
  BOOL grow;
  int i;
  IOSFloatArray *props = nil;
  if ([cfg getSplitProportions] != nil) {
    props = [((SPOTSet *) nil_chk([cfg getSplitProportions])) floatValues];
  }
  if ((props == nil) || ((int) [props count] == 0)) {
    float pos = 1 / (float) (len);
    props = [IOSFloatArray arrayWithLength:len - 1];
    for (i = 0; i < len - 1; i++) {
      (*IOSFloatArray_GetRef(props, i)) = pos;
    }
  }
  if ([((SPOTBoolean *) nil_chk(cfg->showGripper_)) spot_valueWasSet]) {
    [splitPane setShowGripperWithBoolean:[cfg->showGripper_ booleanValue]];
  }
  for (i = 0; i < len; i++) {
    grow = NO;
    region = (RARESPOTRegion *) check_class_cast([cfg->regions_ getWithInt:i], [RARESPOTRegion class]);
    name = [self isDesignMode] ? nil : [((SPOTPrintableString *) nil_chk(((RARESPOTRegion *) nil_chk(region))->name_)) getValue];
    if (name == nil) {
      name = [self generateTargetNameWithNSString:[NSString stringWithFormat:@"view#%d", i]];
    }
    if ([cfg->splitOrientation_ intValue] == RARESPOTSplitPane_CSplitOrientation_top_to_bottom) {
      switch ([((RARESPOTRegion_CVerticalFill *) nil_chk(((RARESPOTRegion *) nil_chk(region))->verticalFill_)) intValue]) {
        case RARESPOTRegion_CVerticalFill_maximum:
        grow = YES;
        break;
        default:
        break;
      }
    }
    else {
      switch ([((RARESPOTRegion_CHorizontalFill *) nil_chk(((RARESPOTRegion *) nil_chk(region))->horizontalFill_)) intValue]) {
        case RARESPOTRegion_CHorizontalFill_maximum:
        grow = YES;
        break;
        default:
        break;
      }
    }
    panel = [self createPanelWithRARESPOTCollapsibleInfo:[((RARESPOTRegion *) nil_chk(region)) getCollapsibleInfo]];
    [splitPane addWithRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk([self createTargetWithNSString:name withRAREiParentComponent:panel withRARESPOTRegion:region])) getContainerComponent] withId:[JavaLangBoolean valueOfWithBoolean:grow]];
  }
  if ((int) [((IOSFloatArray *) nil_chk(props)) count] == len) {
    IOSFloatArray *d = [IOSFloatArray arrayWithLength:len - 1];
    for (i = 0; i < len - 1; i++) {
      (*IOSFloatArray_GetRef(d, i)) = IOSFloatArray_Get(props, i);
    }
    props = d;
  }
  [splitPane setProportionsWithFloatArray:props];
  if (![self isDesignMode] && ([((SPOTPrintableString *) nil_chk(cfg->transitionAnimator_)) getValue] != nil)) {
    id<RAREiTransitionAnimator> ta = [RAREaAnimator createTransitionAnimatorWithRAREiWidget:self withNSString:[cfg->transitionAnimator_ getValue] withJavaUtilMap:[cfg->transitionAnimator_ spot_getAttributesEx]];
    if (ta != nil) {
      [splitPane setTransitionAnimatorWithRAREiTransitionAnimator:ta];
    }
  }
}

- (id<RAREiTransitionAnimator>)getTransitionAnimator {
  return [((RARESplitPanePanel *) check_class_cast(dataComponent_, [RARESplitPanePanel class])) getTransitionAnimator];
}

- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator {
  [((RARESplitPanePanel *) check_class_cast(dataComponent_, [RARESplitPanePanel class])) setTransitionAnimatorWithRAREiTransitionAnimator:animator];
}

- (void)setTransitionAnimatorWithNSString:(NSString *)inAnimation {
  id<RAREiPlatformAnimator> ia = [((RAREWindowViewer *) nil_chk([RAREPlatform getWindowViewer])) createAnimatorWithNSString:inAnimation];
  if (ia == nil) {
    [RAREPlatform debugLogWithNSString:[NSString stringWithFormat:@"Unknown annimation:%@", ia]];
    return;
  }
  [self setTransitionAnimatorWithRAREiTransitionAnimator:[[RARETransitionAnimator alloc] initWithRAREiPlatformAnimator:ia withRAREiPlatformAnimator:nil]];
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
  [super initializeListenersWithRAREaWidgetListener:l];
  if ((l != nil) && [l isChangeEventEnabled]) {
    [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) addChangeListenerWithRAREiChangeListener:l];
  }
}

- (void)targetVisibilityChangedWithRAREiTarget:(id<RAREiTarget>)t
                                   withBoolean:(BOOL)visibile {
  [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) paneVisibilityChanged];
}

- (void)uninitializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
  [super uninitializeListenersWithRAREaWidgetListener:l];
  if (l != nil) {
    [((RARESplitPanePanel *) nil_chk([self getSplitPanePanel])) removeChangeListenerWithRAREiChangeListener:l];
  }
}

- (RARESplitPanePanel *)getSplitPanePanel {
  return (RARESplitPanePanel *) check_class_cast(dataComponent_, [RARESplitPanePanel class]);
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setSplitProportionsWithFloatArray:", NULL, "V", 0x81, NULL },
    { "getSplitProportions", NULL, "LIOSFloatArray", 0x1, NULL },
    { "isTopToBottom", NULL, "Z", 0x1, NULL },
    { "isUserResizeable", NULL, "Z", 0x1, NULL },
    { "configureExWithRARESPOTSplitPane:", NULL, "V", 0x4, NULL },
    { "getTransitionAnimator", NULL, "LRAREiTransitionAnimator", 0x1, NULL },
    { "initializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
    { "targetVisibilityChangedWithRAREiTarget:withBoolean:", NULL, "V", 0x4, NULL },
    { "uninitializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
    { "getSplitPanePanel", NULL, "LRARESplitPanePanel", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREaSplitPaneViewer = { "aSplitPaneViewer", "com.appnativa.rare.viewer", NULL, 0x401, 10, methods, 0, NULL, 0, NULL};
  return &_RAREaSplitPaneViewer;
}

@end
