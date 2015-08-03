//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/NSAttributedStringHTMLEncoder.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARENSAttributedStringHTMLEncoder_H_
#define _RARENSAttributedStringHTMLEncoder_H_

@class RAREUIFont;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/text/iHTMLEncoder.h"

@interface RARENSAttributedStringHTMLEncoder : NSObject < RAREiHTMLEncoder > {
}

- (id)init;
- (id<JavaLangCharSequence>)applyFontWithNSString:(NSString *)text
                                   withRAREUIFont:(RAREUIFont *)font;
- (id<JavaLangCharSequence>)parseHTMLWithNSString:(NSString *)html
                                   withRAREUIFont:(RAREUIFont *)base;
- (NSString *)getPlainTextWithNSString:(NSString *)html;
- (id)parseHTMLExWithNSString:(NSString *)html;
+ (id)createAttributedStringWithNSString:(NSString *)text;
@end

typedef RARENSAttributedStringHTMLEncoder ComAppnativaRareUiTextNSAttributedStringHTMLEncoder;

#endif // _RARENSAttributedStringHTMLEncoder_H_