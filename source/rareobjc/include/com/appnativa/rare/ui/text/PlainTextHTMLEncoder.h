//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/PlainTextHTMLEncoder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREPlainTextHTMLEncoder_H_
#define _RAREPlainTextHTMLEncoder_H_

@class RAREUIFont;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/text/iHTMLEncoder.h"

@interface RAREPlainTextHTMLEncoder : NSObject < RAREiHTMLEncoder > {
}

- (id)init;
- (id<JavaLangCharSequence>)applyFontWithNSString:(NSString *)text
                                   withRAREUIFont:(RAREUIFont *)font;
- (id<JavaLangCharSequence>)parseHTMLWithNSString:(NSString *)html
                                   withRAREUIFont:(RAREUIFont *)base;
- (NSString *)getPlainTextWithNSString:(NSString *)html;
@end

typedef RAREPlainTextHTMLEncoder ComAppnativaRareUiTextPlainTextHTMLEncoder;

#endif // _RAREPlainTextHTMLEncoder_H_
