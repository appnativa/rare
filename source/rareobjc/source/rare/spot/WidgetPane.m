//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/WidgetPane.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/GridPane.h"
#include "com/appnativa/rare/spot/GroupBox.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/SplitPane.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/spot/WidgetPane.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"

@implementation RARESPOTWidgetPane

- (id)init {
  return [self initRARESPOTWidgetPaneWithBoolean:YES];
}

- (id)initRARESPOTWidgetPaneWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    autoResizeWidget_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollPane_ = nil;
    transitionAnimator_ = [[SPOTPrintableString alloc] init];
    widget_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget" withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTWidgetPaneWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    autoResizeWidget_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollPane_ = nil;
    transitionAnimator_ = [[SPOTPrintableString alloc] init];
    widget_ = [[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Widget" withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 5;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"actAsFormViewer" withISPOTElement:actAsFormViewer_];
  [self spot_addElementWithNSString:@"autoResizeWidget" withISPOTElement:autoResizeWidget_];
  [self spot_addElementWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
  [self spot_addElementWithNSString:@"transitionAnimator" withISPOTElement:transitionAnimator_];
  [((SPOTPrintableString *) nil_chk(transitionAnimator_)) spot_defineAttributeWithNSString:@"duration" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"direction" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"acceleration" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"deceleration" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"horizontal" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"fadeIn" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"fadeOut" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"diagonal" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"diagonalAnchor" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"autoOrient" withNSString:nil];
  [transitionAnimator_ spot_defineAttributeWithNSString:@"customProperties" withNSString:nil];
  [self spot_addElementWithNSString:@"widget" withISPOTElement:widget_];
}

- (RARESPOTScrollPane *)getScrollPane {
  return scrollPane_;
}

- (RARESPOTScrollPane *)getScrollPaneReference {
  if (scrollPane_ == nil) {
    scrollPane_ = [[RARESPOTScrollPane alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
  }
  return scrollPane_;
}

- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference {
  scrollPane_ = (RARESPOTScrollPane *) check_class_cast(reference, [RARESPOTScrollPane class]);
  (void) [self spot_setReferenceWithNSString:@"scrollPane" withISPOTElement:reference];
}

- (id)initWithRARESPOTWidget:(RARESPOTWidget *)widget {
  if (self = [self initRARESPOTWidgetPaneWithBoolean:YES]) {
    [((SPOTAny *) nil_chk(self->widget_)) setValueWithISPOTElement:widget];
  }
  return self;
}

- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap {
  id<iSPOTElement> v = [((SPOTAny *) nil_chk(widget_)) getValue];
  if (v == nil) {
    return nil;
  }
  if ([((NSString *) nil_chk(name)) isEqual:[((SPOTPrintableString *) nil_chk(((RARESPOTWidget *) nil_chk(v))->name_)) getValue]]) {
    return (RARESPOTWidget *) check_class_cast(v, [RARESPOTWidget class]);
  }
  if ([(id) v isKindOfClass:[RARESPOTGroupBox class]]) {
    RARESPOTWidget *w = [((RARESPOTGroupBox *) check_class_cast(v, [RARESPOTGroupBox class])) findWidgetWithNSString:name withBoolean:useNameMap];
    if (w != nil) {
      return w;
    }
  }
  else if ([(id) v isKindOfClass:[RARESPOTGridPane class]]) {
    RARESPOTWidget *w = [((RARESPOTGridPane *) check_class_cast(v, [RARESPOTGridPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
    if (w != nil) {
      return w;
    }
  }
  else if ([(id) v isKindOfClass:[RARESPOTSplitPane class]]) {
    RARESPOTWidget *w = [((RARESPOTSplitPane *) check_class_cast(v, [RARESPOTSplitPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
    if (w != nil) {
      return w;
    }
  }
  return nil;
}

- (void)copyAllFieldsTo:(RARESPOTWidgetPane *)other {
  [super copyAllFieldsTo:other];
  other->actAsFormViewer_ = actAsFormViewer_;
  other->autoResizeWidget_ = autoResizeWidget_;
  other->scrollPane_ = scrollPane_;
  other->transitionAnimator_ = transitionAnimator_;
  other->widget_ = widget_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getScrollPane", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "getScrollPaneReference", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "setScrollPaneWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "findWidgetWithNSString:withBoolean:", NULL, "LRARESPOTWidget", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "actAsFormViewer_", NULL, 0x1, "LSPOTBoolean" },
    { "autoResizeWidget_", NULL, 0x1, "LSPOTBoolean" },
    { "scrollPane_", NULL, 0x4, "LRARESPOTScrollPane" },
    { "transitionAnimator_", NULL, 0x1, "LSPOTPrintableString" },
    { "widget_", NULL, 0x1, "LSPOTAny" },
  };
  static J2ObjcClassInfo _RARESPOTWidgetPane = { "WidgetPane", "com.appnativa.rare.spot", NULL, 0x1, 6, methods, 5, fields, 0, NULL};
  return &_RARESPOTWidgetPane;
}

@end
