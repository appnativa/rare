//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/Main.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREMain_H_
#define _RAREMain_H_

@class IOSObjectArray;
@class JavaNetURL;
@class RAREUTOrderedProperties;
@class RAREaRare_StartupInfo;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/Rare.h"

@interface RAREMain : RARERare {
}

- (id)initWithNSStringArray:(IOSObjectArray *)args;
- (void)exit;
- (void)launchWithId:(id)nswindow;
- (RAREaRare_StartupInfo *)initialize__WithNSStringArray:(IOSObjectArray *)args
                             withRAREUTOrderedProperties:(RAREUTOrderedProperties *)props OBJC_METHOD_FAMILY_NONE;
- (JavaNetURL *)resolveApplicationURLWithNSString:(NSString *)file;
- (void)dumpWithJavaNetURL:(JavaNetURL *)url
               withBoolean:(BOOL)dumpXML;
- (JavaNetURL *)resolveLibURLWithNSString:(NSString *)file;
@end

typedef RAREMain ComAppnativaRarePlatformAppleMain;

#endif // _RAREMain_H_