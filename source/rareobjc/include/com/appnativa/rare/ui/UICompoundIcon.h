//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UICompoundIcon.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUICompoundIcon_H_
#define _RAREUICompoundIcon_H_

@class IOSObjectArray;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aUICompoundIcon.h"

@interface RAREUICompoundIcon : RAREaUICompoundIcon {
}

- (id)init;
- (id)initWithRAREiPlatformIconArray:(IOSObjectArray *)icons;
- (id)initWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)firstIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)secondIcon;
@end

typedef RAREUICompoundIcon ComAppnativaRareUiUICompoundIcon;

#endif // _RAREUICompoundIcon_H_
