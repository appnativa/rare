//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/GroupBoxViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREGroupBoxViewer_H_
#define _RAREGroupBoxViewer_H_

@protocol RAREiContainer;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aGroupBoxViewer.h"

@interface RAREGroupBoxViewer : RAREaGroupBoxViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id<RAREiPlatformComponent>)createFormsPanel;
- (id<RAREiPlatformComponent>)createFormsPanelWithInt:(int)rows
                                              withInt:(int)cols;
@end

typedef RAREGroupBoxViewer ComAppnativaRareViewerGroupBoxViewer;

#endif // _RAREGroupBoxViewer_H_