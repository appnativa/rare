//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUITextIcon.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaUITextIcon_H_
#define _RAREaUITextIcon_H_

@class RAREDisplayedEnum;
@class RARERenderSpaceEnum;
@class RARERenderTypeEnum;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/aUIPlatformPainter.h"

@interface RAREaUITextIcon : RAREaUIPlatformPainter < RAREiPlatformIcon, NSCopying > {
 @public
  id<RAREiPlatformBorder> border_;
  id<RAREiPlatformIcon> disabledIcon_;
  int heightPad_;
  id<RAREiActionComponent> label_;
  RAREUIDimension *size_;
  BOOL sizeSet_;
  BOOL square_;
  int widthPad_;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                   withRAREUIColor:(RAREUIColor *)fg;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                   withRAREUIColor:(RAREUIColor *)fg
                    withRAREUIFont:(RAREUIFont *)font
           withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (id)clone;
- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setForegroundColorWithRAREUIColor:(RAREUIColor *)fg;
- (void)setPaddingWithInt:(int)width
                  withInt:(int)height;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation;
- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (void)setSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (void)setSquareWithBoolean:(BOOL)square;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (RAREUIColor *)getBackgroundColor;
- (id<RAREiPlatformBorder>)getBorder;
- (id<RAREiPlatformIcon>)getDisabledVersion;
- (RAREDisplayedEnum *)getDisplayed;
- (int)getIconHeight;
- (int)getIconWidth;
- (id<RAREiActionComponent>)getLabel;
- (RARERenderSpaceEnum *)getRenderSpace;
- (RARERenderTypeEnum *)getRenderType;
- (id<JavaLangCharSequence>)getText;
- (BOOL)isSingleColorPainter;
- (BOOL)isSquare;
- (id<RAREiActionComponent>)createComponent;
- (void)updateSize;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)param0
                             withFloat:(float)param1
                             withFloat:(float)param2
                             withFloat:(float)param3
                             withFloat:(float)param4;
- (void)copyAllFieldsTo:(RAREaUITextIcon *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUITextIcon, border_, id<RAREiPlatformBorder>)
J2OBJC_FIELD_SETTER(RAREaUITextIcon, disabledIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaUITextIcon, label_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaUITextIcon, size_, RAREUIDimension *)

typedef RAREaUITextIcon ComAppnativaRareUiAUITextIcon;

#endif // _RAREaUITextIcon_H_
