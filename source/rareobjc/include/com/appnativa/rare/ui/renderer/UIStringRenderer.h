//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UIStringRenderer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUIStringRenderer_H_
#define _RAREUIStringRenderer_H_

@class RARELabelView;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformRenderingComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"

@interface RAREUIStringRenderer : RAREUILabelRenderer {
}

- (id)initWithRARELabelView:(RARELabelView *)lv;
- (id)init;
- (id<RAREiPlatformRenderingComponent>)newCopy OBJC_METHOD_FAMILY_NONE;
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
@end

typedef RAREUIStringRenderer ComAppnativaRareUiRendererUIStringRenderer;

#endif // _RAREUIStringRenderer_H_
