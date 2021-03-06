//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Plot.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTPlot_H_
#define _RARESPOTPlot_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTMargin;
@class RARESPOTPlot_CGridLine;
@class RARESPOTPlot_CLabels;
@class RARESPOTPlot_CShapes;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTReal;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTPlot : SPOTSequence {
 @public
  SPOTPrintableString *bgImageURL_;
  RARESPOTMargin *contentPadding_;
  SPOTPrintableString *bgColor_;
  SPOTPrintableString *borderColor_;
  RARESPOTPlot_CGridLine *gridLine_;
  RARESPOTPlot_CShapes *shapes_;
  RARESPOTPlot_CLabels *labels_;
  SPOTInteger *fgAlpha_;
  SPOTReal *lineThickness_;
  SPOTReal *outlineThickness_;
  SPOTPrintableString *templateName_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTMargin *)getContentPadding;
- (RARESPOTMargin *)getContentPaddingReference;
- (void)setContentPaddingWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTPlot *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTPlot, bgImageURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, contentPadding_, RARESPOTMargin *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, bgColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, borderColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, gridLine_, RARESPOTPlot_CGridLine *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, shapes_, RARESPOTPlot_CShapes *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, labels_, RARESPOTPlot_CLabels *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, fgAlpha_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, lineThickness_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, outlineThickness_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTPlot, templateName_, SPOTPrintableString *)

typedef RARESPOTPlot ComAppnativaRareSpotPlot;

#define RARESPOTPlot_CGridLine_auto 0
#define RARESPOTPlot_CGridLine_dashed 2
#define RARESPOTPlot_CGridLine_dotted 3
#define RARESPOTPlot_CGridLine_none 4
#define RARESPOTPlot_CGridLine_solid 1

@interface RARESPOTPlot_CGridLine : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)solid;
+ (int)dashed;
+ (int)dotted;
+ (int)none;
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
- (void)spot_setAttributes;
@end

#define RARESPOTPlot_CShapes_filled 1
#define RARESPOTPlot_CShapes_filled_and_outlined 3
#define RARESPOTPlot_CShapes_none 0
#define RARESPOTPlot_CShapes_outlined 2

@interface RARESPOTPlot_CShapes : SPOTEnumerated {
}

+ (int)none;
+ (int)filled;
+ (int)outlined;
+ (int)filled_and_outlined;
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
- (void)spot_setAttributes;
@end

#define RARESPOTPlot_CLabels_linked_data 2
#define RARESPOTPlot_CLabels_tooltips 1
#define RARESPOTPlot_CLabels_values 0

@interface RARESPOTPlot_CLabels : SPOTEnumerated {
}

+ (int)values;
+ (int)tooltips;
+ (int)linked_data;
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
- (void)spot_setAttributes;
@end

#endif // _RARESPOTPlot_H_
