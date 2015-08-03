//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iFrame.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiFrame_H_
#define _RAREiFrame_H_

@class RAREUIFont;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTarget;
@protocol RAREiViewer;

#import "JreEmulation.h"

@protocol RAREiFrame < NSObject, JavaObject >
- (void)dispose;
- (void)reset;
- (void)setFrameVisibleWithBoolean:(BOOL)visible;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTitleWithNSString:(NSString *)title;
- (void)setTitleAndIconWithNSString:(NSString *)title
              withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTitleComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setTitleFontWithRAREUIFont:(RAREUIFont *)f;
- (id<RAREiViewer>)getContent;
- (id<RAREiPlatformComponent>)getFrameComponent;
- (id<RAREiTarget>)getTarget;
- (NSString *)getTargetName;
- (id<RAREiPlatformComponent>)getTitleComponent;
@end

#define ComAppnativaRareUiIFrame RAREiFrame

#endif // _RAREiFrame_H_
