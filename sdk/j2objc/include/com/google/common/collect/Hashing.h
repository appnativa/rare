//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/Hashing.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectHashing_RESTRICT
#define ComGoogleCommonCollectHashing_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectHashing_RESTRICT

#if !defined (_ComGoogleCommonCollectHashing_) && (ComGoogleCommonCollectHashing_INCLUDE_ALL || ComGoogleCommonCollectHashing_INCLUDE)
#define _ComGoogleCommonCollectHashing_

#define ComGoogleCommonCollectHashing_C1 -862048943
#define ComGoogleCommonCollectHashing_C2 461845907

@interface ComGoogleCommonCollectHashing : NSObject {
}

+ (int)MAX_TABLE_SIZE;
+ (int *)MAX_TABLE_SIZERef;
- (id)init;
+ (int)smearWithInt:(int)hashCode;
+ (int)closedTableSizeWithInt:(int)expectedEntries
                   withDouble:(double)loadFactor;
+ (BOOL)needsResizingWithInt:(int)size
                     withInt:(int)tableSize
                  withDouble:(double)loadFactor;
@end
#endif