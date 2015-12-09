//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UICompositeRenderer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUICompositeRenderer_H_
#define _RAREUICompositeRenderer_H_

@class RAREView;
@protocol RAREiPlatformRenderingComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/renderer/aCompositeRenderer.h"

@interface RAREUICompositeRenderer : RAREaCompositeRenderer {
}

- (id)init;
- (id)initWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc;
- (id)createNewNativeView;
- (id<RAREiPlatformRenderingComponent>)newCopy OBJC_METHOD_FAMILY_NONE;
- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)column;
- (void)clearRenderer;
- (void)setNativeViewWithId:(id)proxy;
- (void)setRenderingViewWithRAREView:(RAREView *)view;
@end

typedef RAREUICompositeRenderer ComAppnativaRareUiRendererUICompositeRenderer;

#endif // _RAREUICompositeRenderer_H_
