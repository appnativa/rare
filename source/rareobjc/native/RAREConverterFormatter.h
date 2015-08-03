//
// Created by Don DeCoteau on 8/7/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>

@class RAREaConverter;
@class RAREConverterContext;

@interface RAREConverterFormatter : NSFormatter  {
  RAREaConverter* converter_;
  RAREConverterContext* context_;
  NSArray* strings_;
}
- (id)initWithConverter:(RAREaConverter *)converter context:(RAREConverterContext *)context;

- (id)initWithStrings:(NSArray *)strings;

+ (id)formatterWithStrings:(NSArray *)strings;


+ (id)formatterWithConverter:(RAREaConverter *)converter context:(RAREConverterContext *)context;

@end

@interface RAREToStringFormatter : NSFormatter
@end