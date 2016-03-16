//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/ScrollContainer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREScrollContainer_H_
#define _RAREScrollContainer_H_

@class RAREParentView;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@protocol JavaUtilList;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/event/iDataModelListener.h"

@interface RAREScrollContainer : RAREContainer < RAREiDataModelListener, RAREiAppleLayoutManager > {
 @public
  BOOL preferedSizeDirty_;
  RAREUIDimension *rowHeaderSize_;
  RAREUIDimension *rowFooterSize_;
  RAREUIDimension *mainComponentSize_;
  id<RAREiPlatformComponent> mainComponent_;
}

- (id)initWithRAREParentView:(RAREParentView *)view;
- (void)contentsChangedWithId:(id)source;
- (void)contentsChangedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (void)intervalAddedWithId:(id)source
                    withInt:(int)index0
                    withInt:(int)index1;
- (void)intervalRemovedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1
             withJavaUtilList:(id<JavaUtilList>)removed;
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position;
- (void)layoutWithFloat:(float)width
              withFloat:(float)height;
- (void)revalidate;
- (void)structureChangedWithId:(id)source;
- (id<RAREiPlatformComponent>)getMainComponent;
- (void)setMainComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)mainComponent;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)invalidateLayout;
- (void)copyAllFieldsTo:(RAREScrollContainer *)other;
@end

J2OBJC_FIELD_SETTER(RAREScrollContainer, rowHeaderSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREScrollContainer, rowFooterSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREScrollContainer, mainComponentSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREScrollContainer, mainComponent_, id<RAREiPlatformComponent>)

typedef RAREScrollContainer ComAppnativaRareUiScrollContainer;

#endif // _RAREScrollContainer_H_
