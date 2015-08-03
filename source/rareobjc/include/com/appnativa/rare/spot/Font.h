//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Font.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTFont_H_
#define _RARESPOTFont_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTFont_CStyle;
@class SPOTBoolean;
@class SPOTPrintableString;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTFont : SPOTSequence {
 @public
  SPOTPrintableString *family_;
  SPOTPrintableString *size_;
  RARESPOTFont_CStyle *style_;
  SPOTBoolean *monospaced_;
  SPOTBoolean *underlined_;
  SPOTBoolean *strikeThrough_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTFont *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTFont, family_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTFont, size_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTFont, style_, RARESPOTFont_CStyle *)
J2OBJC_FIELD_SETTER(RARESPOTFont, monospaced_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTFont, underlined_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTFont, strikeThrough_, SPOTBoolean *)

typedef RARESPOTFont ComAppnativaRareSpotFont;

#define RARESPOTFont_CStyle_bold 1
#define RARESPOTFont_CStyle_bold_italic 3
#define RARESPOTFont_CStyle_italic 2
#define RARESPOTFont_CStyle_normal 0

@interface RARESPOTFont_CStyle : SPOTEnumerated {
}

+ (int)normal;
+ (int)bold;
+ (int)italic;
+ (int)bold_italic;
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

#endif // _RARESPOTFont_H_