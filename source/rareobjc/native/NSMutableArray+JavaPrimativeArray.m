//
//  NSArray+JavaPrimativeArray.m
//  RareOSX
//
//  Created by Don DeCoteau on 6/23/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "NSMutableArray+JavaPrimativeArray.h"

@implementation NSMutableArray (JavaPrimativeArray)

- (long long)longAtIndex:(NSUInteger)index{
  NSNumber* num=[self objectAtIndex:index];
  return [num longLongValue];
}
- (long long)replaceLongAtIndex:(NSUInteger)index withLong:(long long)value{
  NSNumber* num=[self objectAtIndex:index];
  [self replaceObjectAtIndex:index withObject:[NSNumber numberWithLongLong:value]];
  return [num longLongValue];
  
}
- (short)shortAtIndex:(NSUInteger)index{
  NSNumber* num=[self objectAtIndex:index];
  return [num shortValue];
}
- (short)replaceShortAtIndex:(NSUInteger)index withShort:(short)value{
  
  NSNumber* num=[self objectAtIndex:index];
  [self replaceObjectAtIndex:index withObject:[NSNumber numberWithShort:value]];
  return [num shortValue];
}

- (int)intAtIndex:(NSUInteger)index{
  NSNumber* num=[self objectAtIndex:index];
  return [num intValue];
  
}
- (int)replaceIntAtIndex:(NSUInteger)index withInt:(int)c{
  NSNumber* num=[self objectAtIndex:index];
  [self replaceObjectAtIndex:index withObject:[NSNumber numberWithInt:c]];
  return [num intValue];
  
}
- (float)floatAtIndex:(NSUInteger)index{
  NSNumber* num=[self objectAtIndex:index];
  return [num floatValue];
  
}
- (float)replaceFloatAtIndex:(NSUInteger)index withFloat:(float)value{
  NSNumber* num=[self objectAtIndex:index];
  [self replaceObjectAtIndex:index withObject:[NSNumber numberWithFloat:value]];
  return [num floatValue];
  
}
- (double)doubleAtIndex:(NSUInteger)index{
  NSNumber* num=[self objectAtIndex:index];
  return [num doubleValue];
  
}
- (double)replaceDoubleAtIndex:(NSUInteger)index withDouble:(double)value{
  NSNumber* num=[self objectAtIndex:index];
  [self replaceObjectAtIndex:index withObject:[NSNumber numberWithDouble:value]];
  return [num doubleValue];
  
}
- (BOOL)booleanAtIndex:(NSUInteger)index{
  NSNumber* num=[self objectAtIndex:index];
  return [num boolValue];
  
}
- (BOOL)replaceBooleanAtIndex:(NSUInteger)index withBoolean:(BOOL)boolean {
  NSNumber* num=[self objectAtIndex:index];
  [self replaceObjectAtIndex:index withObject:[NSNumber numberWithBool:boolean]];
  return [num boolValue];
  
}

@end
