//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/GetChars.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _AndroidTextGetChars_H_
#define _AndroidTextGetChars_H_

@class IOSCharArray;

#import "JreEmulation.h"
#include "java/lang/CharSequence.h"

@protocol AndroidTextGetChars < JavaLangCharSequence, NSObject, JavaObject >
- (void)getCharsWithInt:(int)start
                withInt:(int)end
          withCharArray:(IOSCharArray *)dest
                withInt:(int)destoff;
@end

#endif // _AndroidTextGetChars_H_