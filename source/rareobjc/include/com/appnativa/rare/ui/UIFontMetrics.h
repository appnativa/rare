//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIFontMetrics.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUIFontMetrics_H_
#define _RAREUIFontMetrics_H_

@class RAREUIDimension;
@class RAREUIFont;

#import "JreEmulation.h"

@interface RAREUIFontMetrics : NSObject {
 @public
  RAREUIFont *font_;
}

- (id)initWithRAREUIFont:(RAREUIFont *)font;
- (BOOL)isEqual:(id)o;
- (int)stringWidthWithNSString:(NSString *)text;
- (RAREUIDimension *)stringSizeWithNSString:(NSString *)text
                        withRAREUIDimension:(RAREUIDimension *)size;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (float)getAscent;
- (float)getDescent;
- (RAREUIFont *)getFont;
- (float)getHeight;
- (float)getLeading;
+ (RAREUIFontMetrics *)getMetricsWithRAREUIFont:(RAREUIFont *)font;
- (void)copyAllFieldsTo:(RAREUIFontMetrics *)other;
@end

J2OBJC_FIELD_SETTER(RAREUIFontMetrics, font_, RAREUIFont *)

typedef RAREUIFontMetrics ComAppnativaRareUiUIFontMetrics;

#endif // _RAREUIFontMetrics_H_
