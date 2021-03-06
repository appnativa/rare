//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-navigator/com/appnativa/rare/widget/NavigatorWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARENavigatorWidget_H_
#define _RARENavigatorWidget_H_

@class RAREAppleGraphics;
@class RAREUIRectangle;
@class RAREView;
@class RAREaNavigatorPanel;
@protocol RAREiContainer;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/BorderLayoutView.h"
#include "com/appnativa/rare/widget/aNavigatorWidget.h"

@interface RARENavigatorWidget : RAREaNavigatorWidget {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id)createBorderLayoutView;
- (RAREaNavigatorPanel *)createNavigatorPanel;
+ (void)registerForUse;
@end

typedef RARENavigatorWidget ComAppnativaRareWidgetNavigatorWidget;

@interface RARENavigatorWidget_$1 : RAREBorderLayoutView {
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (id)init;
@end

#endif // _RARENavigatorWidget_H_
