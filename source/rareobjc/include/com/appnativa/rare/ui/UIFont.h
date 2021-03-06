//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIFont.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUIFont_H_
#define _RAREUIFont_H_

#import "JreEmulation.h"

@interface RAREUIFont : NSObject {
 @public
  NSString *family_;
  NSString *name_;
  int style_;
  float relativeSize_;
  float baseSize_;
  BOOL monospace_;
  id proxy_;
  float size_;
  BOOL strikethrough_;
  BOOL underline_;
  id iosProxy_;
}

+ (int)BOLD;
+ (int *)BOLDRef;
+ (int)ITALIC;
+ (int *)ITALICRef;
+ (int)PLAIN;
+ (int *)PLAINRef;
- (id)initWithNSString:(NSString *)family
               withInt:(int)style
             withFloat:(float)size;
- (id)initWithNSString:(NSString *)family
               withInt:(int)style
               withInt:(int)size;
- (id)initWithRAREUIFont:(RAREUIFont *)f;
- (id)initWithRAREUIFont:(RAREUIFont *)font
               withFloat:(float)rs;
- (id)initWithRAREUIFont:(RAREUIFont *)font
               withFloat:(float)rs
               withFloat:(float)bs;
- (id)initWithId:(id)proxy;
- (id)initWithId:(id)proxy
     withBoolean:(BOOL)underline
     withBoolean:(BOOL)strikethrough;
- (void)addToAttributedStringWithId:(id)astring
                            withInt:(int)offset
                            withInt:(int)length;
- (RAREUIFont *)deriveWithBoolean:(BOOL)strikethrough
                      withBoolean:(BOOL)underlined;
- (RAREUIFont *)deriveBold;
- (RAREUIFont *)deriveBoldItalic;
- (RAREUIFont *)deriveFontWithInt:(int)style
                        withFloat:(float)size;
- (RAREUIFont *)deriveFontSizeWithFloat:(float)size;
- (RAREUIFont *)deriveItalic;
- (RAREUIFont *)deriveRelativeFontWithFloat:(float)nrs;
- (RAREUIFont *)deriveStrikethrough;
- (RAREUIFont *)deriveUnderline;
- (float)getBaseSize;
- (NSString *)getFamily;
- (id)getIOSProxy;
- (id)getProxy;
- (float)getRelativeSize;
- (int)getSize;
- (float)getSize2D;
- (int)getStyle;
- (BOOL)isBold;
- (BOOL)isItalic;
- (BOOL)isStrikeThrough;
- (BOOL)isUnderline;
- (void)setBaseSizeWithFloat:(float)baseSize;
- (void)setRelativeSizeWithFloat:(float)relativeSize;
- (void)setStrikeThroughWithBoolean:(BOOL)strikeThrough;
- (void)setUnderlineWithBoolean:(BOOL)underline;
- (NSString *)description;
- (void)initialize__ OBJC_METHOD_FAMILY_NONE;
+ (id)deriveFontWithRAREUIFont:(RAREUIFont *)base
                     withFloat:(float)size
                   withBoolean:(BOOL)bold
                   withBoolean:(BOOL)italic;
+ (BOOL)isBoldWithInt:(int)style;
+ (BOOL)isItalicWithInt:(int)style;
+ (id)newFontWithNSString:(NSString *)family
                withFloat:(float)size
              withBoolean:(BOOL)bold
              withBoolean:(BOOL)italic OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(RAREUIFont *)other;
@end

J2OBJC_FIELD_SETTER(RAREUIFont, family_, NSString *)
J2OBJC_FIELD_SETTER(RAREUIFont, name_, NSString *)
J2OBJC_FIELD_SETTER(RAREUIFont, proxy_, id)
J2OBJC_FIELD_SETTER(RAREUIFont, iosProxy_, id)

typedef RAREUIFont ComAppnativaRareUiUIFont;

#endif // _RAREUIFont_H_
