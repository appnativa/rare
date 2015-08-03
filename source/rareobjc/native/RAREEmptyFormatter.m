//
//  RAREEmptyFormatter.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 2/5/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import "RAREEmptyFormatter.h"

@implementation RAREEmptyFormatter

- (BOOL)getObjectValue:(out id *)obj forString:(NSString *)string errorDescription:(out NSString **)error {
  return NO;
  
}
-(id) initWithFormatter:(NSFormatter*) formatter {
  if(self=[super init]) {
    oldFormatter=formatter;
  }
  return self;
}

- (NSString *)stringForObjectValue:(id)obj {
    return @"";
}
@end
