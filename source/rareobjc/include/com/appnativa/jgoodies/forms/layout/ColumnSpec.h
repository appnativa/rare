//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/layout/ColumnSpec.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREColumnSpec_H_
#define _RAREColumnSpec_H_

@class IOSObjectArray;
@class RAREConstantSize;
@class RAREFormSpec_DefaultAlignment;
@class RARELayoutMap;
@protocol JavaUtilMap;
@protocol RARESize;

#import "JreEmulation.h"
#include "com/appnativa/jgoodies/forms/layout/FormSpec.h"

@interface RAREColumnSpec : RAREFormSpec {
}

+ (RAREFormSpec_DefaultAlignment *)LEFT;
+ (RAREFormSpec_DefaultAlignment *)CENTER;
+ (RAREFormSpec_DefaultAlignment *)MIDDLE;
+ (RAREFormSpec_DefaultAlignment *)RIGHT;
+ (RAREFormSpec_DefaultAlignment *)FILL;
+ (RAREFormSpec_DefaultAlignment *)DEFAULT;
+ (id<JavaUtilMap>)CACHE;
- (id)initWithRAREFormSpec_DefaultAlignment:(RAREFormSpec_DefaultAlignment *)defaultAlignment
                               withRARESize:(id<RARESize>)size
                                 withDouble:(double)resizeWeight;
- (id)initWithRARESize:(id<RARESize>)size;
- (id)initWithNSString:(NSString *)encodedDescription;
+ (RAREColumnSpec *)createGapWithRAREConstantSize:(RAREConstantSize *)gapWidth;
+ (RAREColumnSpec *)decodeWithNSString:(NSString *)encodedColumnSpec;
+ (RAREColumnSpec *)decodeWithNSString:(NSString *)encodedColumnSpec
                     withRARELayoutMap:(RARELayoutMap *)layoutMap;
+ (RAREColumnSpec *)decodeExpandedWithNSString:(NSString *)expandedTrimmedLowerCaseSpec;
+ (IOSObjectArray *)decodeSpecsWithNSString:(NSString *)encodedColumnSpecs;
+ (IOSObjectArray *)decodeSpecsWithNSString:(NSString *)encodedColumnSpecs
                          withRARELayoutMap:(RARELayoutMap *)layoutMap;
- (BOOL)isHorizontal;
@end

typedef RAREColumnSpec ComAppnativaJgoodiesFormsLayoutColumnSpec;

#endif // _RAREColumnSpec_H_
