//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/ImagePaneViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREImagePaneViewer_H_
#define _RAREImagePaneViewer_H_

@class RARESPOTImagePane;
@class RAREaImagePanel;
@protocol RAREiContainer;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aImagePaneViewer.h"

@interface RAREImagePaneViewer : RAREaImagePaneViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)setUseSpinnerWithBoolean:(BOOL)spinner;
- (RAREaImagePanel *)createPanelWithRARESPOTImagePane:(RARESPOTImagePane *)cfg;
@end

typedef RAREImagePaneViewer ComAppnativaRareViewerImagePaneViewer;

#endif // _RAREImagePaneViewer_H_
