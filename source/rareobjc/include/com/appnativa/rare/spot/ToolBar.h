//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ToolBar.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTToolBar_H_
#define _RARESPOTToolBar_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTToolBar_CLocation;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTSet;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTToolBar : RARESPOTViewer {
 @public
  RARESPOTToolBar_CLocation *location_;
  SPOTInteger *row_;
  SPOTInteger *column_;
  SPOTBoolean *horizontal_;
  SPOTBoolean *buttonShowTextDefault_;
  SPOTBoolean *actAsFormViewer_;
  SPOTSet *widgets_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTToolBar *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTToolBar, location_, RARESPOTToolBar_CLocation *)
J2OBJC_FIELD_SETTER(RARESPOTToolBar, row_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTToolBar, column_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTToolBar, horizontal_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTToolBar, buttonShowTextDefault_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTToolBar, actAsFormViewer_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTToolBar, widgets_, SPOTSet *)

typedef RARESPOTToolBar ComAppnativaRareSpotToolBar;

#define RARESPOTToolBar_CLocation_east 3
#define RARESPOTToolBar_CLocation_north 1
#define RARESPOTToolBar_CLocation_south 2
#define RARESPOTToolBar_CLocation_west 4

@interface RARESPOTToolBar_CLocation : SPOTEnumerated {
}

+ (int)north;
+ (int)south;
+ (int)east;
+ (int)west;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#endif // _RARESPOTToolBar_H_
