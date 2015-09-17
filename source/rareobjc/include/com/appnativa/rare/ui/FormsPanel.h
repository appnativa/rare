//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/FormsPanel.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREFormsPanel_H_
#define _RAREFormsPanel_H_

@class IOSObjectArray;
@class RAREFormsView;
@class RAREJGCellConstraints;
@class RAREJGFormLayout;
@class RAREUIDimension;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aFormsPanel.h"

@interface RAREFormsPanel : RAREaFormsPanel {
}

- (id)init;
- (id)initWithRAREFormsView:(RAREFormsView *)view;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
     withRAREJGFormLayout:(RAREJGFormLayout *)layout;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
                  withInt:(int)rows
                  withInt:(int)cols;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
                  withInt:(int)rows
                  withInt:(int)cols
             withNSString:(NSString *)rspec
             withNSString:(NSString *)cspec;
- (id)initWithId:(id)view;
- (void)setCellPaintersWithRAREiPlatformPainterArray:(IOSObjectArray *)painters;
- (RAREJGCellConstraints *)getCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (RAREJGFormLayout *)getFormLayout;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
@end

typedef RAREFormsPanel ComAppnativaRareUiFormsPanel;

#endif // _RAREFormsPanel_H_
