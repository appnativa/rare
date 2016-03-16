//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-progressbar/com/appnativa/rare/platform/apple/ui/view/ProgressBarView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREProgressBarView_H_
#define _RAREProgressBarView_H_

@class RAREComponent;
@class RAREUIColor;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ControlView.h"
#include "com/appnativa/rare/ui/iProgressBar.h"

@interface RAREProgressBarView : RAREControlView < RAREiProgressBar > {
 @public
  BOOL indeterminate_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)indeterminate;
- (void)setIndeterminateWithBoolean:(BOOL)indeterminate;
- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg;
- (void)setMaximumWithInt:(int)maximum;
- (void)setMinimumWithInt:(int)minimum;
- (void)setValueWithInt:(int)value;
- (RAREComponent *)getComponent;
- (int)getValue;
- (BOOL)isIndeterminate;
+ (id)createProxy;
+ (id)createProxyWithBoolean:(BOOL)indeterminate;
- (void)swapOutProxyWithId:(id)newProxy;
- (void)setGraphicSizeWithInt:(int)size;
- (void)copyAllFieldsTo:(RAREProgressBarView *)other;
@end

typedef RAREProgressBarView ComAppnativaRarePlatformAppleUiViewProgressBarView;

#endif // _RAREProgressBarView_H_
