//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ImagePane.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/EmptyText.h"
#include "com/appnativa/rare/spot/ImagePane.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTImagePane

- (id)init {
  return [self initRARESPOTImagePaneWithBoolean:YES];
}

- (id)initRARESPOTImagePaneWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    zoomingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollWheelZoomingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    movingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    rotatingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    scaling_ = [[RARESPOTImagePane_CScaling alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTImagePane_CScaling_bilinear] withNSString:@"bilinear" withBoolean:NO];
    autoScale_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    centerImage_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    preserveAspectRatio_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    userSelectionAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showLoadStatus_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollPane_ = nil;
    emptyText_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTImagePaneWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    zoomingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollWheelZoomingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    movingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    rotatingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    scaling_ = [[RARESPOTImagePane_CScaling alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTImagePane_CScaling_bilinear] withNSString:@"bilinear" withBoolean:NO];
    autoScale_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    centerImage_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    preserveAspectRatio_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    userSelectionAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showLoadStatus_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    scrollPane_ = nil;
    emptyText_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 12;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_addElementWithNSString:@"zoomingAllowed" withISPOTElement:zoomingAllowed_];
  [((SPOTBoolean *) nil_chk(zoomingAllowed_)) spot_defineAttributeWithNSString:@"minimum" withNSString:@"10%"];
  [zoomingAllowed_ spot_defineAttributeWithNSString:@"maximum" withNSString:@"200%"];
  [zoomingAllowed_ spot_defineAttributeWithNSString:@"increment" withNSString:@"10%"];
  [self spot_addElementWithNSString:@"scrollWheelZoomingAllowed" withISPOTElement:scrollWheelZoomingAllowed_];
  [self spot_addElementWithNSString:@"movingAllowed" withISPOTElement:movingAllowed_];
  [self spot_addElementWithNSString:@"rotatingAllowed" withISPOTElement:rotatingAllowed_];
  [self spot_addElementWithNSString:@"scaling" withISPOTElement:scaling_];
  [self spot_addElementWithNSString:@"autoScale" withISPOTElement:autoScale_];
  [self spot_addElementWithNSString:@"centerImage" withISPOTElement:centerImage_];
  [self spot_addElementWithNSString:@"preserveAspectRatio" withISPOTElement:preserveAspectRatio_];
  [((SPOTBoolean *) nil_chk(preserveAspectRatio_)) spot_defineAttributeWithNSString:@"fill" withNSString:nil];
  [self spot_addElementWithNSString:@"userSelectionAllowed" withISPOTElement:userSelectionAllowed_];
  [((SPOTBoolean *) nil_chk(userSelectionAllowed_)) spot_defineAttributeWithNSString:@"selectionColor" withNSString:nil];
  [userSelectionAllowed_ spot_defineAttributeWithNSString:@"lineThickness" withNSString:@"1.5"];
  [userSelectionAllowed_ spot_defineAttributeWithNSString:@"strokeType" withNSString:@"dotted"];
  [self spot_addElementWithNSString:@"showLoadStatus" withISPOTElement:showLoadStatus_];
  [self spot_addElementWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
  [self spot_addElementWithNSString:@"emptyText" withISPOTElement:emptyText_];
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

- (RARESPOTEmptyText *)getEmptyText {
  return emptyText_;
}

- (RARESPOTEmptyText *)getEmptyTextReference {
  if (emptyText_ == nil) {
    emptyText_ = [[RARESPOTEmptyText alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"emptyText" withISPOTElement:emptyText_];
  }
  return emptyText_;
}

- (void)setEmptyTextWithISPOTElement:(id<iSPOTElement>)reference {
  emptyText_ = (RARESPOTEmptyText *) check_class_cast(reference, [RARESPOTEmptyText class]);
  (void) [self spot_setReferenceWithNSString:@"emptyText" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTImagePane *)other {
  [super copyAllFieldsTo:other];
  other->autoScale_ = autoScale_;
  other->centerImage_ = centerImage_;
  other->emptyText_ = emptyText_;
  other->movingAllowed_ = movingAllowed_;
  other->preserveAspectRatio_ = preserveAspectRatio_;
  other->rotatingAllowed_ = rotatingAllowed_;
  other->scaling_ = scaling_;
  other->scrollPane_ = scrollPane_;
  other->scrollWheelZoomingAllowed_ = scrollWheelZoomingAllowed_;
  other->showLoadStatus_ = showLoadStatus_;
  other->userSelectionAllowed_ = userSelectionAllowed_;
  other->zoomingAllowed_ = zoomingAllowed_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getScrollPane", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "getScrollPaneReference", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "setScrollPaneWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getEmptyText", NULL, "LRARESPOTEmptyText", 0x1, NULL },
    { "getEmptyTextReference", NULL, "LRARESPOTEmptyText", 0x1, NULL },
    { "setEmptyTextWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "zoomingAllowed_", NULL, 0x1, "LSPOTBoolean" },
    { "scrollWheelZoomingAllowed_", NULL, 0x1, "LSPOTBoolean" },
    { "movingAllowed_", NULL, 0x1, "LSPOTBoolean" },
    { "rotatingAllowed_", NULL, 0x1, "LSPOTBoolean" },
    { "scaling_", NULL, 0x1, "LRARESPOTImagePane_CScaling" },
    { "autoScale_", NULL, 0x1, "LSPOTBoolean" },
    { "centerImage_", NULL, 0x1, "LSPOTBoolean" },
    { "preserveAspectRatio_", NULL, 0x1, "LSPOTBoolean" },
    { "userSelectionAllowed_", NULL, 0x1, "LSPOTBoolean" },
    { "showLoadStatus_", NULL, 0x1, "LSPOTBoolean" },
    { "scrollPane_", NULL, 0x4, "LRARESPOTScrollPane" },
    { "emptyText_", NULL, 0x4, "LRARESPOTEmptyText" },
  };
  static J2ObjcClassInfo _RARESPOTImagePane = { "ImagePane", "com.appnativa.rare.spot", NULL, 0x1, 8, methods, 12, fields, 0, NULL};
  return &_RARESPOTImagePane;
}

@end
@implementation RARESPOTImagePane_CScaling

static IOSIntArray * RARESPOTImagePane_CScaling__nchoices_;
static IOSObjectArray * RARESPOTImagePane_CScaling__schoices_;

+ (int)nearest_neighbor {
  return RARESPOTImagePane_CScaling_nearest_neighbor;
}

+ (int)bilinear {
  return RARESPOTImagePane_CScaling_bilinear;
}

+ (int)bicubic {
  return RARESPOTImagePane_CScaling_bicubic;
}

+ (int)bilinear_cached {
  return RARESPOTImagePane_CScaling_bilinear_cached;
}

+ (int)bicubic_cached {
  return RARESPOTImagePane_CScaling_bicubic_cached;
}

+ (int)progressive_bilinear {
  return RARESPOTImagePane_CScaling_progressive_bilinear;
}

+ (int)progressive_bicubic {
  return RARESPOTImagePane_CScaling_progressive_bicubic;
}

+ (int)progressive_bilinear_cached {
  return RARESPOTImagePane_CScaling_progressive_bilinear_cached;
}

+ (int)progressive_bicubic_cached {
  return RARESPOTImagePane_CScaling_progressive_bicubic_cached;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTImagePane_CScaling__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTImagePane_CScaling__schoices_;
}

- (id)init {
  return [self initRARESPOTImagePane_CScalingWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTImagePane_CScaling__schoices_;
    _nChoices_ = RARESPOTImagePane_CScaling__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTImagePane_CScalingWithJavaLangInteger:(JavaLangInteger *)ival
                                           withNSString:(NSString *)sval
                                    withJavaLangInteger:(JavaLangInteger *)idefaultval
                                           withNSString:(NSString *)sdefaultval
                                            withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTImagePane_CScaling__schoices_;
    _nChoices_ = RARESPOTImagePane_CScaling__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTImagePane_CScalingWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{nearest_neighbor(0), bilinear(1), bicubic(2), bilinear_cached(3), bicubic_cached(4), progressive_bilinear(5), progressive_bicubic(6), progressive_bilinear_cached(7), progressive_bicubic_cached(8) }";
}

+ (void)initialize {
  if (self == [RARESPOTImagePane_CScaling class]) {
    RARESPOTImagePane_CScaling__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 8 } count:9];
    RARESPOTImagePane_CScaling__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"nearest_neighbor", @"bilinear", @"bicubic", @"bilinear_cached", @"bicubic_cached", @"progressive_bilinear", @"progressive_bicubic", @"progressive_bilinear_cached", @"progressive_bicubic_cached" } count:9 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "nearest_neighbor_", NULL, 0x19, "I" },
    { "bilinear_", NULL, 0x19, "I" },
    { "bicubic_", NULL, 0x19, "I" },
    { "bilinear_cached_", NULL, 0x19, "I" },
    { "bicubic_cached_", NULL, 0x19, "I" },
    { "progressive_bilinear_", NULL, 0x19, "I" },
    { "progressive_bicubic_", NULL, 0x19, "I" },
    { "progressive_bilinear_cached_", NULL, 0x19, "I" },
    { "progressive_bicubic_cached_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTImagePane_CScaling = { "CScaling", "com.appnativa.rare.spot", "ImagePane", 0x9, 1, methods, 11, fields, 0, NULL};
  return &_RARESPOTImagePane_CScaling;
}

@end
