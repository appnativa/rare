//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/SeparatorView.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESeparatorView_H_
#define _RARESeparatorView_H_

@class RAREUIDimension;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/LineView.h"

@interface RARESeparatorView : RARELineView {
}

- (id)init;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
@end

typedef RARESeparatorView ComAppnativaRarePlatformAppleUiViewSeparatorView;

#endif // _RARESeparatorView_H_
