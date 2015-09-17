//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ImagePane.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARESPOTImagePane_H_
#define _RARESPOTImagePane_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTEmptyText;
@class RARESPOTImagePane_CScaling;
@class RARESPOTScrollPane;
@class SPOTBoolean;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTImagePane : RARESPOTViewer {
 @public
  SPOTBoolean *zoomingAllowed_;
  SPOTBoolean *scrollWheelZoomingAllowed_;
  SPOTBoolean *movingAllowed_;
  SPOTBoolean *rotatingAllowed_;
  RARESPOTImagePane_CScaling *scaling_;
  SPOTBoolean *autoScale_;
  SPOTBoolean *centerImage_;
  SPOTBoolean *preserveAspectRatio_;
  SPOTBoolean *userSelectionAllowed_;
  SPOTBoolean *showLoadStatus_;
  RARESPOTScrollPane *scrollPane_;
  RARESPOTEmptyText *emptyText_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTScrollPane *)getScrollPane;
- (RARESPOTScrollPane *)getScrollPaneReference;
- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTEmptyText *)getEmptyText;
- (RARESPOTEmptyText *)getEmptyTextReference;
- (void)setEmptyTextWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTImagePane *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTImagePane, zoomingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, scrollWheelZoomingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, movingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, rotatingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, scaling_, RARESPOTImagePane_CScaling *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, autoScale_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, centerImage_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, preserveAspectRatio_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, userSelectionAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, showLoadStatus_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, scrollPane_, RARESPOTScrollPane *)
J2OBJC_FIELD_SETTER(RARESPOTImagePane, emptyText_, RARESPOTEmptyText *)

typedef RARESPOTImagePane ComAppnativaRareSpotImagePane;

#define RARESPOTImagePane_CScaling_bicubic 2
#define RARESPOTImagePane_CScaling_bicubic_cached 4
#define RARESPOTImagePane_CScaling_bilinear 1
#define RARESPOTImagePane_CScaling_bilinear_cached 3
#define RARESPOTImagePane_CScaling_nearest_neighbor 0
#define RARESPOTImagePane_CScaling_progressive_bicubic 6
#define RARESPOTImagePane_CScaling_progressive_bicubic_cached 8
#define RARESPOTImagePane_CScaling_progressive_bilinear 5
#define RARESPOTImagePane_CScaling_progressive_bilinear_cached 7

@interface RARESPOTImagePane_CScaling : SPOTEnumerated {
}

+ (int)nearest_neighbor;
+ (int)bilinear;
+ (int)bicubic;
+ (int)bilinear_cached;
+ (int)bicubic_cached;
+ (int)progressive_bilinear;
+ (int)progressive_bicubic;
+ (int)progressive_bilinear_cached;
+ (int)progressive_bicubic_cached;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#endif // _RARESPOTImagePane_H_
