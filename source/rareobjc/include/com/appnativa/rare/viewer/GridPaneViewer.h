//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/GridPaneViewer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREGridPaneViewer_H_
#define _RAREGridPaneViewer_H_

@protocol RAREiContainer;
@protocol RAREiFormsPanel;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aGridPaneViewer.h"

@interface RAREGridPaneViewer : RAREaGridPaneViewer {
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id<RAREiFormsPanel>)createFormsPanel;
@end

typedef RAREGridPaneViewer ComAppnativaRareViewerGridPaneViewer;

#endif // _RAREGridPaneViewer_H_
