//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/aPlatformViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaPlatformViewer_H_
#define _RAREaPlatformViewer_H_

@protocol RAREiContainer;
@protocol RAREiPageSetup;
@protocol RAREiPopup;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aViewer.h"

@interface RAREaPlatformViewer : RAREaViewer {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id<RAREiPageSetup>)createPageSetup;
- (id<RAREiPopup>)createPopupWithInt:(int)width
                             withInt:(int)height;
@end

typedef RAREaPlatformViewer ComAppnativaRareViewerAPlatformViewer;

#endif // _RAREaPlatformViewer_H_
