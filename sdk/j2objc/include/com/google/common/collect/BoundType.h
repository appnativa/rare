//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/BoundType.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectBoundType_RESTRICT
#define ComGoogleCommonCollectBoundType_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectBoundType_RESTRICT

#if !defined (_ComGoogleCommonCollectBoundTypeEnum_) && (ComGoogleCommonCollectBoundType_INCLUDE_ALL || ComGoogleCommonCollectBoundTypeEnum_INCLUDE)
#define _ComGoogleCommonCollectBoundTypeEnum_

#define JavaLangEnum_RESTRICT 1
#define JavaLangEnum_INCLUDE 1
#include "java/lang/Enum.h"

typedef enum {
  ComGoogleCommonCollectBoundType_OPEN = 0,
  ComGoogleCommonCollectBoundType_CLOSED = 1,
} ComGoogleCommonCollectBoundType;

@interface ComGoogleCommonCollectBoundTypeEnum : JavaLangEnum < NSCopying > {
}
+ (ComGoogleCommonCollectBoundTypeEnum *)OPEN;
+ (ComGoogleCommonCollectBoundTypeEnum *)CLOSED;
+ (IOSObjectArray *)values;
+ (ComGoogleCommonCollectBoundTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
+ (ComGoogleCommonCollectBoundTypeEnum *)forBooleanWithBoolean:(BOOL)inclusive;
- (ComGoogleCommonCollectBoundTypeEnum *)flip;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonCollectBoundTypeEnum_$1_) && (ComGoogleCommonCollectBoundType_INCLUDE_ALL || ComGoogleCommonCollectBoundTypeEnum_$1_INCLUDE)
#define _ComGoogleCommonCollectBoundTypeEnum_$1_

#define ComGoogleCommonCollectBoundTypeEnum_RESTRICT 1
#define ComGoogleCommonCollectBoundTypeEnum_INCLUDE 1
#include "com/google/common/collect/BoundType.h"

@interface ComGoogleCommonCollectBoundTypeEnum_$1 : ComGoogleCommonCollectBoundTypeEnum {
}

- (ComGoogleCommonCollectBoundTypeEnum *)flip;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonCollectBoundTypeEnum_$2_) && (ComGoogleCommonCollectBoundType_INCLUDE_ALL || ComGoogleCommonCollectBoundTypeEnum_$2_INCLUDE)
#define _ComGoogleCommonCollectBoundTypeEnum_$2_

#define ComGoogleCommonCollectBoundTypeEnum_RESTRICT 1
#define ComGoogleCommonCollectBoundTypeEnum_INCLUDE 1
#include "com/google/common/collect/BoundType.h"

@interface ComGoogleCommonCollectBoundTypeEnum_$2 : ComGoogleCommonCollectBoundTypeEnum {
}

- (ComGoogleCommonCollectBoundTypeEnum *)flip;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif
