//
// Created by Don DeCoteau on 8/7/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <com/appnativa/rare/converters/aConverter.h>
#import <com/appnativa/rare/converters/ConverterContext.h>
#import "RAREConverterFormatter.h"


@implementation RAREConverterFormatter

- (id)initWithConverter:(RAREaConverter *)converter context:(RAREConverterContext *)context {
  self = [super init];
  if (self) {
    converter_ = converter;
    context_ = context;
  }

  return self;
}

- (id)initWithStrings:(NSArray *)strings {
  self = [super init];
  if (self) {
    strings_ = strings;
  }

  return self;
}

+ (id)formatterWithStrings:(NSArray *)strings {
  return [[self alloc] initWithStrings:strings];
}


+ (id)formatterWithConverter:(RAREaConverter *)converter context:(RAREConverterContext *)context {
  return [[self alloc] initWithConverter:converter context:context];
}


- (BOOL)getObjectValue:(out id *)obj forString:(NSString *)string errorDescription:(out NSString **)error {
  __block BOOL ok=NO;
  if(strings_) {
    [strings_ enumerateObjectsUsingBlock:^(id s, NSUInteger idx, BOOL *stop) {
      if([string isEqualToString:(NSString*)s]) {
        *obj= [NSNumber numberWithUnsignedInt:(int)idx];
        ok=YES;
        *stop=YES;
      }
    }];
    return ok;
  }
  @try {
    NSObject* o=[converter_ fromStringWithNSString:string withRAREConverterContext:context_];
    *obj=o;
    return YES;
  }
  @catch (NSException *exception) {
   *error=[exception description];
    return NO;
  }
}

- (NSString *)stringForObjectValue:(id)obj {
  if(strings_) {
    if([obj isKindOfClass:[NSNumber class]]) {
      NSUInteger i=[((NSNumber*)obj) unsignedIntegerValue];
      return i<strings_.count ? (NSString*)[strings_ objectAtIndex:i] : @"";
    }
    return @"";
  }
  return [[converter_ objectToStringWithRAREiWidget:nil withId:obj withId:context_] description];
}

@end
@implementation RAREToStringFormatter : NSFormatter

- (BOOL)getObjectValue:(out id *)obj forString:(NSString *)string errorDescription:(out NSString **)error {
  *obj= string;
  return YES;
}

- (NSString *)stringForObjectValue:(id)obj {
  return [obj description];
}
@end