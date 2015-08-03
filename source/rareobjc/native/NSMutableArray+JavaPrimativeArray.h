//
//  NSArray+JavaPrimativeArray.h
//  RareOSX
//
//  Created by Don DeCoteau on 6/23/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSMutableArray (JavaPrimativeArray)

- (long long)longAtIndex:(NSUInteger)index;
- (long long)replaceLongAtIndex:(NSUInteger)index withLong:(long long)value;
- (short)shortAtIndex:(NSUInteger)index;
- (short)replaceShortAtIndex:(NSUInteger)index withShort:(short)value;
- (int)intAtIndex:(NSUInteger)index;
- (int)replaceIntAtIndex:(NSUInteger)index withInt:(int)c;
- (float)floatAtIndex:(NSUInteger)index;
- (float)replaceFloatAtIndex:(NSUInteger)index withFloat:(float)value;
- (double)doubleAtIndex:(NSUInteger)index;
- (double)replaceDoubleAtIndex:(NSUInteger)index withDouble:(double)value;
- (BOOL)booleanAtIndex:(NSUInteger)index;
- (BOOL)replaceBooleanAtIndex:(NSUInteger)index withBoolean:(BOOL)boolean;

@end
