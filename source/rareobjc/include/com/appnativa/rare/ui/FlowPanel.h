//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/FlowPanel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREFlowPanel_H_
#define _RAREFlowPanel_H_

@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/aFlowPanel.h"

@interface RAREFlowPanel : RAREaFlowPanel {
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
@end

typedef RAREFlowPanel ComAppnativaRareUiFlowPanel;

@interface RAREFlowPanel_FlowView : RAREParentView < RAREiAppleLayoutManager > {
}

- (id)init;
- (void)invalidateLayout;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
@end

#endif // _RAREFlowPanel_H_
