//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/KeyboardReturnButtonType.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREKeyboardReturnButtonType_H_
#define _RAREKeyboardReturnButtonType_H_

#import "JreEmulation.h"
#include "java/lang/Enum.h"

typedef enum {
  RAREKeyboardReturnButtonType_DEFAULT_TYPE = 0,
  RAREKeyboardReturnButtonType_GO_TYPE = 1,
  RAREKeyboardReturnButtonType_JOIN_TYPE = 2,
  RAREKeyboardReturnButtonType_NEXT_TYPE = 3,
  RAREKeyboardReturnButtonType_SEARCH_TYPE = 4,
  RAREKeyboardReturnButtonType_SEND_TYPE = 5,
  RAREKeyboardReturnButtonType_DONE_TYPE = 6,
  RAREKeyboardReturnButtonType_NONE_TYPE = 7,
} RAREKeyboardReturnButtonType;

@interface RAREKeyboardReturnButtonTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREKeyboardReturnButtonTypeEnum *)DEFAULT_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)GO_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)JOIN_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)NEXT_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)SEARCH_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)SEND_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)DONE_TYPE;
+ (RAREKeyboardReturnButtonTypeEnum *)NONE_TYPE;
+ (IOSObjectArray *)values;
+ (RAREKeyboardReturnButtonTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREKeyboardReturnButtonType_H_
