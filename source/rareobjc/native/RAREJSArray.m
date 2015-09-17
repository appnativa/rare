//
// Created by Don DeCoteau on 11/12/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//


#import <IOSLongArray.h>
#import <IOSDoubleArray.h>
#import <IOSFloatArray.h>
#import <IOSShortArray.h>
#import <IOSBooleanArray.h>
#import "RAREJSArray.h"
#import "RAREJSEngine.h"

array_type_t get_array_type(NSString *type) {

  if ([type isEqualToString:@"DoubleArray"]) {
    return AT_DOUBLE;
  }
  if ([type isEqualToString:@"FloatArray"]) {
    return AT_FLOAT;
  }
  if ([type isEqualToString:@"ShortArray"]) {
    return AT_SHORT;
  }
  if ([type isEqualToString:@"IntArray"]) {
    return AT_INT;
  }
  if ([type isEqualToString:@"LongArray"]) {
    return AT_LONG;
  }
  if ([type isEqualToString:@"CharArray"]) {
    return AT_CHAR;
  }
  if ([type isEqualToString:@"ByteArray"]) {
    return AT_BYTE;
  }
  if ([type isEqualToString:@"BooleanArray"]) {
    return AT_BOOLEAN;
  }
  if ([type isEqualToString:@"NSArray"]) {
    return AT_NSARRAY;
  }
  return AT_OBJECT;
}

@implementation RAREJSArray

- (id)initWithContext:(JSContextRef)ctx andValue:(JSObjectRef)value {
  self = [super init];
  if (self) {
    arrayRef = value;
    context = ctx;
    JSValueProtect(ctx, value);
    [JSCocoaController upJSValueProtectCount];
  }
  return self;
}
-(void) dispose {
  if(arrayRef) {
    if([NSOperationQueue currentQueue]==[NSOperationQueue mainQueue] || ![RAREJSEngine jsUnprotectOnMainThreadOnly]) {
      JSCocoaController* jsc=(JSCocoaController*)[RAREJSEngine getControllerInstance];
      if(jsc) {
        JSValueUnprotect([jsc ctx], arrayRef);
        [JSCocoaController downJSValueProtectCount];
      }
    }
    else {
      dispatch_sync(dispatch_get_main_queue(), ^{
        JSCocoaController* jsc=(JSCocoaController*)[RAREJSEngine getControllerInstance];
        if(jsc) {
          JSValueUnprotect([jsc ctx], arrayRef);
          [JSCocoaController downJSValueProtectCount];
        }
      });
    }
  }
  arrayRef=NULL;
  context=NULL;
}
+ toIOSArrayFromNSArray:(NSArray *)a type:(NSString *)type {
  NSUInteger count = a.count;
  NSNumber *num = nil;
  id o = nil;
  array_type_t at = get_array_type(type);
  switch (at) {
    case AT_DOUBLE:
      o = [IOSDoubleArray arrayWithLength:count];
      break;
    case AT_FLOAT:
      o = [IOSFloatArray arrayWithLength:count];
      break;
    case AT_SHORT:
      o = [IOSShortArray arrayWithLength:count];
      break;
    case AT_INT:
      o = [IOSIntArray arrayWithLength:count];
      break;
    case AT_LONG:
      o = [IOSLongArray arrayWithLength:count];
      break;
    case AT_BOOLEAN:
      o = [IOSBooleanArray arrayWithLength:count];
      break;
    case AT_CHAR:
      o = [IOSCharArray arrayWithLength:count];
      break;
    case AT_BYTE:
      o = [IOSByteArray arrayWithLength:count];
      break;
    case AT_OBJECT:
      o = [IOSObjectArray arrayWithLength:count];
      break;
    default:
      return a;
  }
  if (count > 0 && ![[a objectAtIndex:0] isKindOfClass:[NSNumber class]]) {
    [NSObject throwClassCastException];
  }
  o = [((IOSArray *) o) initWithLength:count];
  for (int i = 0; i < count; i++) {
    if (at != AT_OBJECT) {
      id no = [a objectAtIndex:i];
      if (![no isKindOfClass:[NSNumber class]]) {
        [NSObject throwClassCastException];
      }
      num = (NSNumber *) no;
    }
    switch (at) {
      case AT_DOUBLE:
        [((IOSDoubleArray *) o) replaceDoubleAtIndex:i withDouble:num.doubleValue];
        break;
      case AT_FLOAT:
        [((IOSFloatArray *) o) replaceFloatAtIndex:i withFloat:num.floatValue];
        break;
      case AT_SHORT:
        [((IOSShortArray *) o) replaceShortAtIndex:i withShort:num.shortValue];
        break;
      case AT_INT:
        [((IOSIntArray *) o) replaceIntAtIndex:i withInt:num.intValue];
        break;
      case AT_LONG:
        [((IOSLongArray *) o) replaceLongAtIndex:i withLong:num.longLongValue];
        break;
      case AT_BOOLEAN:
        [((IOSBooleanArray *) o) replaceBooleanAtIndex:i withBoolean:num.boolValue];
        break;
      case AT_CHAR:
        [((IOSCharArray *) o) replaceCharAtIndex:i withChar:num.unsignedShortValue];
        break;
      case AT_BYTE:
        [((IOSCharArray *) o) replaceCharAtIndex:i withChar:num.unsignedCharValue];
        break;
      case AT_OBJECT:
        [((IOSObjectArray *) o) replaceObjectAtIndex:i withObject:[a objectAtIndex:i]];
        break;
      default:
        break;
    }
  }
  return o;
}

+ toIOSArrayFromNSNumber:(NSNumber *)num type:(NSString *)type {
  id o = nil;
  array_type_t at = get_array_type(type);
  switch (at) {
    case AT_DOUBLE:
      o = [IOSDoubleArray arrayWithLength:1];
      [((IOSDoubleArray *) o) replaceDoubleAtIndex:0 withDouble:num.doubleValue];
      break;
    case AT_FLOAT:
      o = [IOSFloatArray arrayWithLength:1];
      [((IOSFloatArray *) o) replaceFloatAtIndex:0 withFloat:num.floatValue];
      break;
    case AT_SHORT:
      o = [IOSShortArray arrayWithLength:1];
      [((IOSShortArray *) o) replaceShortAtIndex:0 withShort:num.shortValue];
      break;
    case AT_INT:
      o = [IOSIntArray arrayWithLength:1];
      [((IOSIntArray *) o) replaceIntAtIndex:0 withInt:num.intValue];
      break;
    case AT_LONG:
      o = [IOSLongArray arrayWithLength:1];
      [((IOSLongArray *) o) replaceLongAtIndex:0 withLong:num.longLongValue];
      break;
    case AT_BOOLEAN:
      o = [IOSBooleanArray arrayWithLength:1];
      [((IOSBooleanArray *) o) replaceBooleanAtIndex:0 withBoolean:num.boolValue];
      break;
    case AT_CHAR:
      o = [IOSCharArray arrayWithLength:1];
      [((IOSCharArray *) o) replaceCharAtIndex:0 withChar:num.unsignedShortValue];
      break;
    case AT_BYTE:
      o = [IOSByteArray arrayWithLength:1];
      [((IOSCharArray *) o) replaceCharAtIndex:0 withChar:num.unsignedCharValue];
      break;
    case AT_OBJECT:
      o = [IOSObjectArray arrayWithLength:1];
      [((IOSObjectArray *) o) replaceObjectAtIndex:0 withObject:num];
      break;
    default:
      return num;
  }
  return o;
}
- (NSMutableArray*)toNSArrayHadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  *hadException = NO;

  // Get property count
  JSValueRef exception = NULL;
  JSStringRef lengthJS = JSStringCreateWithUTF8CString("length");
  NSUInteger length = JSValueToNumber(ctx, JSObjectGetProperty(ctx, object, lengthJS, NULL), &exception);
  JSStringRelease(lengthJS);
  if (exception) {
    *hadException = YES;
    return nil;
  }
  return [self toNSArrayWithLength:length hadException:hadException];
}
- (id)toNSArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  id array = [NSMutableArray arrayWithCapacity:length];
  id value;
  int i;
  for (i = 0; i < length; i++) {
    JSValueRef jsValue = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) {
      *hadException = YES;
      return nil;
    }
    if (![JSCocoaFFIArgument unboxJSValueRef:jsValue toObject:&value inContext:ctx]) {
      *hadException = YES;
      return nil;
    }
    [array addObject:value];
  }
  return array;

}

- (id)toArrayWithType:(NSString *)type hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  *hadException = NO;

  // Get property count
  JSValueRef exception = NULL;
  JSStringRef lengthJS = JSStringCreateWithUTF8CString("length");
  NSUInteger length = JSValueToNumber(ctx, JSObjectGetProperty(ctx, object, lengthJS, NULL), &exception);
  JSStringRelease(lengthJS);
  if (exception) {
    *hadException = YES;
    return nil;
  }
  array_type_t at = get_array_type(type);
  switch (at) {
    case AT_DOUBLE:
      return [self toIOSDoubleArrayWithLength:length hadException:hadException];
    case AT_FLOAT:
      return [self toIOSFloatArrayWithLength:length hadException:hadException];
    case AT_SHORT:
      return [self toIOSShortArrayWithLength:length hadException:hadException];
    case AT_INT:
      return [self toIOSIntArrayWithLength:length hadException:hadException];
    case AT_LONG:
      return [self toIOSLongArrayWithLength:length hadException:hadException];
    case AT_BOOLEAN:
      return [self toIOSBooleanArrayWithLength:length hadException:hadException];
    case AT_CHAR:
      return [self toIOSCharArrayWithLength:length hadException:hadException];
    case AT_BYTE:
      return [self toIOSByteArrayWithLength:length hadException:hadException];
    case AT_NSARRAY:
      return [self toNSArrayWithLength:length hadException:hadException];
    default:
      return [self toIOSObjectArrayWithLength:length hadException:hadException];
  }
}

- (id)toIOSObjectArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSObjectArray *array = [IOSObjectArray arrayWithLength:length type:[IOSClass classWithClass:[NSObject class]]];
  id v;
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (![JSCocoaFFIArgument unboxJSValueRef:value toObject:&v inContext:ctx]) {
      *hadException = YES;
      return nil;
    }
    [array replaceObjectAtIndex:i withObject:v];
  }
  return array;

}

- (id)toIOSIntArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSIntArray *array = [IOSIntArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceIntAtIndex:i withInt:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceIntAtIndex:i withInt:(int) v];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSShortArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSShortArray *array = [IOSShortArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceShortAtIndex:i withShort:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceShortAtIndex:i withShort:(int) v];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSLongArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSLongArray *array = [IOSLongArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceLongAtIndex:i withLong:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceLongAtIndex:i withLong:(long long) v];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSDoubleArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSDoubleArray *array = [IOSDoubleArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceDoubleAtIndex:i withDouble:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceDoubleAtIndex:i withDouble:v];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSFloatArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSFloatArray *array = [IOSFloatArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceFloatAtIndex:i withFloat:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceFloatAtIndex:i withFloat:v];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSByteArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSByteArray *array = [IOSByteArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceByteAtIndex:i withByte:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceByteAtIndex:i withByte:(char) ((int) v & 0xff)];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSCharArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSCharArray *array = [IOSCharArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceCharAtIndex:i withChar:0];
    }
    else if (JSValueIsNumber(ctx, value)) {
      double v = JSValueToNumber(ctx, value, NULL);
      [array replaceCharAtIndex:i withChar:(char) ((int) v & 0xffff)];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}

- (id)toIOSBooleanArrayWithLength:(NSUInteger)length hadException:(BOOL *)hadException {
  JSContextRef ctx = context;
  JSObjectRef object = arrayRef;
  JSValueRef exception = NULL;
  *hadException = NO;
  IOSBooleanArray *array = [IOSBooleanArray arrayWithLength:length];
  for (int i = 0; i < length; i++) {
    JSValueRef value = JSObjectGetPropertyAtIndex(ctx, object, i, &exception);
    if (exception) return nil;
    if (!value || JSValueIsNull(ctx, value) || JSValueIsUndefined(ctx, value)) {
      [array replaceBooleanAtIndex:i withBoolean:NO];
    }
    else if (JSValueIsBoolean(ctx, value)) {
      bool v = JSValueToBoolean(ctx, value);
      [array replaceBooleanAtIndex:i withBoolean:v];
    }
    else {
      if (exception) {
        *hadException = YES;
        return nil;
      }
    }
  }
  return array;

}


@end