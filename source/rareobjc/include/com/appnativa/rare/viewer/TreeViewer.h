//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/viewer/TreeViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETreeViewer_H_
#define _RARETreeViewer_H_

@class IOSIntArray;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREiListHandler_SelectionModeEnum;
@protocol JavaUtilMap;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aTreeViewer.h"

@interface RARETreeViewer : RAREaTreeViewer {
 @public
  IOSIntArray *selectedIndexes_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)cancelEditing;
- (void)setAutoSizeRowsToFitWithBoolean:(BOOL)autoSize;
- (void)setSelectionModeWithRAREiListHandler_SelectionModeEnum:(RAREiListHandler_SelectionModeEnum *)selectionMode;
- (void)setShowLastDividerWithBoolean:(BOOL)show;
- (void)setTreeIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)expanded
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)collapsed;
- (void)createModelAndComponentsWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)handleCustomPropertiesWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                 withJavaUtilMap:(id<JavaUtilMap>)properties;
- (void)handleViewerConfigurationChangedWithBoolean:(BOOL)reset;
- (void)handleViewerConfigurationWillChangeWithId:(id)newConfig;
- (void)setFlingThresholdWithInt:(int)threshold;
- (void)setSelectFlingedWithBoolean:(BOOL)select;
- (void)setWholeViewFlingWithBoolean:(BOOL)wholeViewFling;
+ (void)registerForUse;
- (void)copyAllFieldsTo:(RARETreeViewer *)other;
@end

J2OBJC_FIELD_SETTER(RARETreeViewer, selectedIndexes_, IOSIntArray *)

typedef RARETreeViewer ComAppnativaRareViewerTreeViewer;

#endif // _RARETreeViewer_H_
