//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/Location.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARELocation_H_
#define _RARELocation_H_

#import "JreEmulation.h"
#include "java/lang/Enum.h"

typedef enum {
  RARELocation_AUTO = 0,
  RARELocation_LEFT = 1,
  RARELocation_RIGHT = 2,
  RARELocation_TOP = 3,
  RARELocation_BOTTOM = 4,
  RARELocation_CENTER = 5,
} RARELocation;

@interface RARELocationEnum : JavaLangEnum < NSCopying > {
}
+ (RARELocationEnum *)AUTO;
+ (RARELocationEnum *)LEFT;
+ (RARELocationEnum *)RIGHT;
+ (RARELocationEnum *)TOP;
+ (RARELocationEnum *)BOTTOM;
+ (RARELocationEnum *)CENTER;
+ (IOSObjectArray *)values;
+ (RARELocationEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RARELocation_H_
