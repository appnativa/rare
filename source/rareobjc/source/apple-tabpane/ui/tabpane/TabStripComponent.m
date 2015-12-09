//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/ui/tabpane/TabStripComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/EventHelper.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/tabpane/TabStripComponent.h"
#include "com/appnativa/rare/ui/tabpane/aPlatformTabPainter.h"
#import "RAREAPView.h"

@implementation RARETabStripComponent

- (id)init {
  return [super initWithId:[[RARETabStripComponent_TabStripView alloc] init]];
}

- (void)setTabPainterWithRAREaPlatformTabPainter:(RAREaPlatformTabPainter *)painter {
  [((RARETabStripComponent_TabStripView *) check_class_cast(view_, [RARETabStripComponent_TabStripView class])) setTabPainterWithRAREaPlatformTabPainter:painter];
}

- (RAREaPlatformTabPainter *)getTabPainter {
  return [((RARETabStripComponent_TabStripView *) check_class_cast(view_, [RARETabStripComponent_TabStripView class])) getTabPainter];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getTabPainter", NULL, "LRAREaPlatformTabPainter", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARETabStripComponent = { "TabStripComponent", "com.appnativa.rare.ui.tabpane", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RARETabStripComponent;
}

@end
@implementation RARETabStripComponent_TabStripView

- (id)init {
  if (self = [super initWithId:[RAREaView createAPView]]) {
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
    [self setUsePainterBackgroundColorWithBoolean:YES];
    [self setUseMainLayerForPainterWithBoolean:NO];
    [self setPaintHandlerEnabledWithBoolean:YES];
  }
  return self;
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  [((RAREaPlatformTabPainter *) nil_chk(tabPainter_)) layoutWithFloat:0 withFloat:0 withFloat:[RAREUIScreen snapToSizeWithFloat:width] withFloat:[RAREUIScreen snapToSizeWithFloat:height]];
  [self setUseFlipTransformWithBoolean:([tabPainter_ getPosition] == [RARELocationEnum BOTTOM]) && ![tabPainter_ isHandlesBottomRotation]];
  [self repaint];
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
  RAREComponent *c = (RAREComponent *) check_class_cast([((RAREComponent *) nil_chk(component_)) getParent], [RAREComponent class]);
  if ((c != nil) && [c hasMouseListeners]) {
    [c mouseEnteredWithRAREMouseEvent:[RAREEventHelper retargetWithRAREMouseEvent:e withRAREView:[c getView]]];
  }
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
  RAREComponent *c = (RAREComponent *) check_class_cast([((RAREComponent *) nil_chk(component_)) getParent], [RAREComponent class]);
  if ((c != nil) && [c hasMouseListeners]) {
    [c mouseExitedWithRAREMouseEvent:[RAREEventHelper retargetWithRAREMouseEvent:e withRAREView:[c getView]]];
  }
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)event {
  int height = (int) [self getHeight];
  int width = (int) [self getWidth];
  RAREUIInsets *in = [((RAREContainer *) check_class_cast(component_, [RAREContainer class])) getInsetsWithRAREUIInsets:nil];
  int x = (int) [((RAREMouseEvent *) nil_chk(event)) getX];
  int y = (int) [event getY];
  int padding = [((RAREaPlatformTabPainter *) nil_chk(tabPainter_)) getTabsPadding];
  switch ([[tabPainter_ getPosition] ordinal]) {
    case RARELocation_BOTTOM:
    if (![tabPainter_ isHandlesBottomRotation]) {
      y = height - y;
    }
    y += padding;
    break;
    case RARELocation_RIGHT:
    break;
    case RARELocation_LEFT:
    x -= padding;
    break;
    default:
    y -= padding;
    break;
  }
  width -= (((RAREUIInsets *) nil_chk(in))->left_ + in->right_);
  height -= (in->top_ + in->bottom_);
  int n = [tabPainter_ findTabWithFloat:x withFloat:y withFloat:in->left_ withFloat:in->top_ withFloat:width withFloat:height];
  if ((n != -1) && (n != [tabPainter_ getSelectedTab])) {
    RAREUIAction *a = [tabPainter_ getTabWithInt:n];
    if ([((RAREUIAction *) nil_chk(a)) isEnabled]) {
      [a actionPerformedWithRAREActionEvent:[[RAREActionEvent alloc] initWithId:a]];
      [self revalidate];
    }
  }
  RAREComponent *c = (RAREComponent *) check_class_cast([((RAREComponent *) nil_chk(component_)) getParent], [RAREComponent class]);
  if ((c != nil) && [c hasMouseListeners]) {
    [c mousePressedWithRAREMouseEvent:[RAREEventHelper retargetWithRAREMouseEvent:event withRAREView:[c getView]]];
  }
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
  RAREComponent *c = (RAREComponent *) check_class_cast([((RAREComponent *) nil_chk(component_)) getParent], [RAREComponent class]);
  if ((c != nil) && [c hasMouseListeners]) {
    [c mouseReleasedWithRAREMouseEvent:[RAREEventHelper retargetWithRAREMouseEvent:e withRAREView:[c getView]]];
  }
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
  [super paintBackgroundWithRAREAppleGraphics:g withRAREView:v withRAREUIRectangle:rect];
  if (![((RAREaPlatformTabPainter *) nil_chk(tabPainter_)) isHorizontal] && ![tabPainter_ isHandlesRightLeftRotation]) {
    [tabPainter_ paintWithRAREiPlatformGraphics:g withFloat:(int) ((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:(int) rect->y_ withFloat:(int) rect->height_ withFloat:(int) rect->width_];
  }
  else {
    [tabPainter_ paintWithRAREiPlatformGraphics:g withFloat:(int) ((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:(int) rect->y_ withFloat:(int) rect->width_ withFloat:(int) rect->height_];
  }
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
  RAREComponent *c = (RAREComponent *) check_class_cast([((RAREComponent *) nil_chk(component_)) getParent], [RAREComponent class]);
  if ((c != nil) && [c hasMouseListeners]) {
    [c pressCanceledWithRAREMouseEvent:[RAREEventHelper retargetWithRAREMouseEvent:e withRAREView:[c getView]]];
  }
}

- (BOOL)wantsLongPress {
  return NO;
}

- (void)setComponentWithRAREComponent:(RAREComponent *)component {
  [super setComponentWithRAREComponent:component];
  if (component != nil) {
    [component addMouseListenerWithRAREiMouseListener:self];
  }
}

- (void)setTabPainterWithRAREaPlatformTabPainter:(RAREaPlatformTabPainter *)tabPainter {
  if (self->tabPainter_ != nil) {
    [self->tabPainter_ setHeaderWithRAREiParentComponent:nil];
  }
  self->tabPainter_ = tabPainter;
  if (self->tabPainter_ != nil) {
    [self->tabPainter_ setHeaderWithRAREiParentComponent:(id<RAREiParentComponent>) check_protocol_cast(component_, @protocol(RAREiParentComponent))];
  }
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth {
  [((RAREaPlatformTabPainter *) nil_chk(tabPainter_)) getMinimumSizeWithRAREUIDimension:size];
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [((RAREaPlatformTabPainter *) nil_chk(tabPainter_)) getPreferredSizeWithRAREUIDimension:size];
}

- (RAREaPlatformTabPainter *)getTabPainter {
  return tabPainter_;
}

- (void)disposeEx {
  [super disposeEx];
  tabPainter_ = nil;
}

- (void)copyAllFieldsTo:(RARETabStripComponent_TabStripView *)other {
  [super copyAllFieldsTo:other];
  other->tabPainter_ = tabPainter_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
    { "getTabPainter", NULL, "LRAREaPlatformTabPainter", 0x1, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARETabStripComponent_TabStripView = { "TabStripView", "com.appnativa.rare.ui.tabpane", "TabStripComponent", 0x9, 3, methods, 0, NULL, 0, NULL};
  return &_RARETabStripComponent_TabStripView;
}

@end
