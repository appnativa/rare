//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/MenuItem.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREMenuItem_H_
#define _RAREMenuItem_H_

@protocol JavaLangCharSequence;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"

@interface RAREMenuItem : NSObject {
 @public
  BOOL checkbox_;
  id proxy_;
  id<JavaLangCharSequence> text_;
}

- (id)initWithBoolean:(BOOL)checkbox;
- (id)initWithId:(id)proxy;
- (void)dispose;
- (void)setCheckableWithBoolean:(BOOL)checkable;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setUserDataWithId:(id)data;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (id)getProxy;
- (id<JavaLangCharSequence>)getText;
- (id)getUserData;
- (BOOL)isSeparator;
+ (id)createProxyWithBoolean:(BOOL)checkbox;
- (void)setImageWithId:(id)apimage;
- (id)getParentUserData;
- (void)copyAllFieldsTo:(RAREMenuItem *)other;
@end

J2OBJC_FIELD_SETTER(RAREMenuItem, proxy_, id)
J2OBJC_FIELD_SETTER(RAREMenuItem, text_, id<JavaLangCharSequence>)

typedef RAREMenuItem ComAppnativaRareUiMenuItem;

#endif // _RAREMenuItem_H_