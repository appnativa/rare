//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iMenuBarComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiMenuBarComponent_H_
#define _RAREiMenuBarComponent_H_

@class RAREUIMenuItem;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiActionListener;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"

@protocol RAREiMenuBarComponent < NSObject, JavaObject >
- (void)addWithRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)removeWithRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)removeAll;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)setTitleWithJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setTitleIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (id<RAREiActionComponent>)getTitleComponent;
- (BOOL)hasTitleComponent;
- (BOOL)isVisible;
@end

#define ComAppnativaRareUiIMenuBarComponent RAREiMenuBarComponent

#endif // _RAREiMenuBarComponent_H_
