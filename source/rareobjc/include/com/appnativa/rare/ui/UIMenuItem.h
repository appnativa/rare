//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIMenuItem.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUIMenuItem_H_
#define _RAREUIMenuItem_H_

@class RAREActionEvent;
@class RAREMenuItem;
@class RARERenderableDataItem;
@class RAREUIAction;
@protocol JavaLangCharSequence;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aUIMenuItem.h"
#include "java/beans/PropertyChangeListener.h"

@interface RAREUIMenuItem : RAREaUIMenuItem < JavaBeansPropertyChangeListener > {
 @public
  RAREMenuItem *menuItem_;
}

- (id)init;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (id)initWithRAREMenuItem:(RAREMenuItem *)item;
- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (id)initWithRAREUIAction:(RAREUIAction *)a;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id)initWithRAREUIAction:(RAREUIAction *)a
               withBoolean:(BOOL)checkbox;
- (id)initWithRAREUIAction:(RAREUIAction *)a
          withRAREMenuItem:(RAREMenuItem *)item;
- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                            withId:(id)data
                       withBoolean:(BOOL)checkbox;
- (void)dispose;
- (void)handleWithRAREActionEvent:(RAREActionEvent *)e;
- (void)updateText;
- (void)setCheckableWithBoolean:(BOOL)checkable;
- (void)setMnemonicWithChar:(unichar)mn;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setVisibleWithBoolean:(BOOL)b;
- (RAREMenuItem *)getMenuItem;
- (id)getProxy;
- (BOOL)isSeparator;
- (void)setMenuItemWithRAREMenuItem:(RAREMenuItem *)item;
- (void)setupItem;
- (void)setNativeItemIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)copyAllFieldsTo:(RAREUIMenuItem *)other;
@end

J2OBJC_FIELD_SETTER(RAREUIMenuItem, menuItem_, RAREMenuItem *)

typedef RAREUIMenuItem ComAppnativaRareUiUIMenuItem;

#endif // _RAREUIMenuItem_H_
