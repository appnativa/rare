//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/FlowPanel.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/FlowPanel.h"
#include "com/appnativa/rare/ui/aFlowPanel.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREFlowPanel

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  if (self = [super initWithId:[[RAREFlowPanel_FlowView alloc] init]]) {
    [self setNeedsMultitplePassesWithBoolean:YES];
  }
  return self;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREFlowPanel = { "FlowPanel", "com.appnativa.rare.ui", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREFlowPanel;
}

@end
@implementation RAREFlowPanel_FlowView

- (id)init {
  if (self = [super initWithId:[RAREaView createAPView]]) {
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
  }
  return self;
}

- (void)invalidateLayout {
  RAREFlowPanel *container = (RAREFlowPanel *) check_class_cast(component_, [RAREFlowPanel class]);
  ((RAREFlowPanel *) nil_chk(container))->cacheInvalidated_ = YES;
  [super invalidateLayout];
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  RAREFlowPanel *container = (RAREFlowPanel *) check_class_cast(component_, [RAREFlowPanel class]);
  if (container != nil) {
    [container layoutWithFloat:width withFloat:height];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x0, NULL },
  };
  static J2ObjcClassInfo _RAREFlowPanel_FlowView = { "FlowView", "com.appnativa.rare.ui", "FlowPanel", 0x8, 1, methods, 0, NULL, 0, NULL};
  return &_RAREFlowPanel_FlowView;
}

@end
