//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/layout/RowSpec.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARERowSpec_H_
#define _RARERowSpec_H_

@class IOSObjectArray;
@class RAREConstantSize;
@class RAREFormSpec_DefaultAlignment;
@class RARELayoutMap;
@protocol JavaUtilMap;
@protocol RARESize;

#import "JreEmulation.h"
#include "com/appnativa/jgoodies/forms/layout/FormSpec.h"

@interface RARERowSpec : RAREFormSpec {
}

+ (RAREFormSpec_DefaultAlignment *)TOP;
+ (RAREFormSpec_DefaultAlignment *)CENTER;
+ (RAREFormSpec_DefaultAlignment *)BOTTOM;
+ (RAREFormSpec_DefaultAlignment *)FILL;
+ (RAREFormSpec_DefaultAlignment *)DEFAULT;
+ (id<JavaUtilMap>)CACHE;
- (id)initWithRAREFormSpec_DefaultAlignment:(RAREFormSpec_DefaultAlignment *)defaultAlignment
                               withRARESize:(id<RARESize>)size
                                 withDouble:(double)resizeWeight;
- (id)initWithRARESize:(id<RARESize>)size;
- (id)initWithNSString:(NSString *)encodedDescription;
+ (RARERowSpec *)createGapWithRAREConstantSize:(RAREConstantSize *)gapHeight;
+ (RARERowSpec *)decodeWithNSString:(NSString *)encodedRowSpec;
+ (RARERowSpec *)decodeWithNSString:(NSString *)encodedRowSpec
                  withRARELayoutMap:(RARELayoutMap *)layoutMap;
+ (RARERowSpec *)decodeExpandedWithNSString:(NSString *)expandedTrimmedLowerCaseSpec;
+ (IOSObjectArray *)decodeSpecsWithNSString:(NSString *)encodedRowSpecs;
+ (IOSObjectArray *)decodeSpecsWithNSString:(NSString *)encodedRowSpecs
                          withRARELayoutMap:(RARELayoutMap *)layoutMap;
- (BOOL)isHorizontal;
@end

typedef RARERowSpec ComAppnativaJgoodiesFormsLayoutRowSpec;

#endif // _RARERowSpec_H_
