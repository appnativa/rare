//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/SpacerView.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESpacerView_H_
#define _RARESpacerView_H_

@class RAREUIDimension;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"

@interface RARESpacerView : RAREView {
 @public
  int width_;
  int height_;
}

- (id)init;
- (id)initWithInt:(int)width
          withInt:(int)height;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (BOOL)isMouseTransparent;
- (void)copyAllFieldsTo:(RARESpacerView *)other;
@end

typedef RARESpacerView ComAppnativaRarePlatformAppleUiViewSpacerView;

#endif // _RARESpacerView_H_
