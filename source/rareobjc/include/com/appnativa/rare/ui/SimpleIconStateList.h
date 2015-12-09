//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/SimpleIconStateList.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESimpleIconStateList_H_
#define _RARESimpleIconStateList_H_

@protocol RAREiPlatformIcon;

#import "JreEmulation.h"

@interface RARESimpleIconStateList : NSObject {
 @public
  id<RAREiPlatformIcon> defaultIcon_;
  id<RAREiPlatformIcon> disabledIcon_;
  id<RAREiPlatformIcon> selectedIcon_;
  id<RAREiPlatformIcon> selectedDisabledIcon_;
}

- (id)init;
- (id)initWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)defaultIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)disabledIcon;
- (id)initWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)defaultIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)disabledIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedIcon
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedDisabledIcon;
- (id<RAREiPlatformIcon>)getDefaultIcon;
- (id<RAREiPlatformIcon>)getDisabledIcon;
- (id<RAREiPlatformIcon>)getSelectedIcon;
- (id<RAREiPlatformIcon>)getSelectedDisabledIcon;
- (void)setSelectedDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedDisabledIcon;
- (void)setSelectedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)selectedIcon;
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)disabledIcon;
- (void)setDefaultIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)defaultIcon;
- (void)copyAllFieldsTo:(RARESimpleIconStateList *)other;
@end

J2OBJC_FIELD_SETTER(RARESimpleIconStateList, defaultIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARESimpleIconStateList, disabledIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARESimpleIconStateList, selectedIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARESimpleIconStateList, selectedDisabledIcon_, id<RAREiPlatformIcon>)

typedef RARESimpleIconStateList ComAppnativaRareUiSimpleIconStateList;

#endif // _RARESimpleIconStateList_H_
