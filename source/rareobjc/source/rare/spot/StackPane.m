//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/StackPane.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/GridPane.h"
#include "com/appnativa/rare/spot/GroupBox.h"
#include "com/appnativa/rare/spot/SplitPane.h"
#include "com/appnativa/rare/spot/StackPane.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTStackPane

- (id)init {
  return [self initRARESPOTStackPaneWithBoolean:YES];
}

- (id)initRARESPOTStackPaneWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    loadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    reloadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    selectedIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withBoolean:NO];
    transitionAnimator_ = [[SPOTPrintableString alloc] init];
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    viewers_ = nil;
    viewerURLs_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTStackPaneWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    loadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    reloadOnActivation_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    selectedIndex_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:0] withBoolean:NO];
    transitionAnimator_ = [[SPOTPrintableString alloc] init];
    actAsFormViewer_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    viewers_ = nil;
    viewerURLs_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 7;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_addElementWithNSString:@"loadOnActivation" withISPOTElement:loadOnActivation_];
  [self spot_addElementWithNSString:@"reloadOnActivation" withISPOTElement:reloadOnActivation_];
  [self spot_addElementWithNSString:@"selectedIndex" withISPOTElement:selectedIndex_];
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
  [self spot_addElementWithNSString:@"actAsFormViewer" withISPOTElement:actAsFormViewer_];
  [self spot_addElementWithNSString:@"viewers" withISPOTElement:viewers_];
  [self spot_addElementWithNSString:@"viewerURLs" withISPOTElement:viewerURLs_];
}

- (SPOTSet *)getViewers {
  return viewers_;
}

- (SPOTSet *)getViewersReference {
  if (viewers_ == nil) {
    viewers_ = [[SPOTSet alloc] initWithNSString:@"viewer" withISPOTElement:[[SPOTAny alloc] initWithNSString:@"com.appnativa.rare.spot.Viewer"] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"viewers" withISPOTElement:viewers_];
  }
  return viewers_;
}

- (void)setViewersWithISPOTElement:(id<iSPOTElement>)reference {
  viewers_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"viewers" withISPOTElement:reference];
}

- (SPOTSet *)getViewerURLs {
  return viewerURLs_;
}

- (SPOTSet *)getViewerURLsReference {
  if (viewerURLs_ == nil) {
    viewerURLs_ = [[SPOTSet alloc] initWithNSString:@"url" withISPOTElement:[[SPOTPrintableString alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"viewerURLs" withISPOTElement:viewerURLs_];
  }
  return viewerURLs_;
}

- (void)setViewerURLsWithISPOTElement:(id<iSPOTElement>)reference {
  viewerURLs_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"viewerURLs" withISPOTElement:reference];
}

- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap {
  int len = (viewers_ == nil) ? 0 : [viewers_ size];
  for (int i = 0; i < len; i++) {
    RARESPOTViewer *v = (RARESPOTViewer *) check_class_cast([((SPOTSet *) nil_chk(viewers_)) getWithInt:i], [RARESPOTViewer class]);
    if ([v isKindOfClass:[RARESPOTGroupBox class]]) {
      RARESPOTWidget *w = [((RARESPOTGroupBox *) check_class_cast(v, [RARESPOTGroupBox class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([v isKindOfClass:[RARESPOTGridPane class]]) {
      RARESPOTWidget *w = [((RARESPOTGridPane *) check_class_cast(v, [RARESPOTGridPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([v isKindOfClass:[RARESPOTSplitPane class]]) {
      RARESPOTWidget *w = [((RARESPOTSplitPane *) check_class_cast(v, [RARESPOTSplitPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
    else if ([v isKindOfClass:[RARESPOTStackPane class]]) {
      RARESPOTWidget *w = [((RARESPOTStackPane *) check_class_cast(v, [RARESPOTStackPane class])) findWidgetWithNSString:name withBoolean:useNameMap];
      if (w != nil) {
        return w;
      }
    }
  }
  return nil;
}

- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name {
  return [self findWidgetWithNSString:name withBoolean:NO];
}

- (void)copyAllFieldsTo:(RARESPOTStackPane *)other {
  [super copyAllFieldsTo:other];
  other->actAsFormViewer_ = actAsFormViewer_;
  other->loadOnActivation_ = loadOnActivation_;
  other->reloadOnActivation_ = reloadOnActivation_;
  other->selectedIndex_ = selectedIndex_;
  other->transitionAnimator_ = transitionAnimator_;
  other->viewerURLs_ = viewerURLs_;
  other->viewers_ = viewers_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getViewers", NULL, "LSPOTSet", 0x1, NULL },
    { "getViewersReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setViewersWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getViewerURLs", NULL, "LSPOTSet", 0x1, NULL },
    { "getViewerURLsReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setViewerURLsWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "findWidgetWithNSString:withBoolean:", NULL, "LRARESPOTWidget", 0x1, NULL },
    { "findWidgetWithNSString:", NULL, "LRARESPOTWidget", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "loadOnActivation_", NULL, 0x1, "LSPOTBoolean" },
    { "reloadOnActivation_", NULL, 0x1, "LSPOTBoolean" },
    { "selectedIndex_", NULL, 0x1, "LSPOTInteger" },
    { "transitionAnimator_", NULL, 0x1, "LSPOTPrintableString" },
    { "actAsFormViewer_", NULL, 0x1, "LSPOTBoolean" },
    { "viewers_", NULL, 0x4, "LSPOTSet" },
    { "viewerURLs_", NULL, 0x4, "LSPOTSet" },
  };
  static J2ObjcClassInfo _RARESPOTStackPane = { "StackPane", "com.appnativa.rare.spot", NULL, 0x1, 10, methods, 7, fields, 0, NULL};
  return &_RARESPOTStackPane;
}

@end
