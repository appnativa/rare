//
//  RARESNumber+NSNumber.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 8/15/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RARESNumber+NSNumber.h"
#import "com/appnativa/util/SNumber.h"
#import "com/appnativa/util/SDecimal.h"

@implementation RAREUTSNumber (NSNumber)
- (long long int)longLongValue {
  return (bigNumber_ != nil) ? [((RAREUTSDecimal *) nil_chk(bigNumber_)) longValue] : mantissa_;
}
-(NSString *)descriptionWithLocale:(id)locale {
  return [self description];
}

@end
