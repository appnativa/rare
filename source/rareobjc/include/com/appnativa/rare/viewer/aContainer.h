//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aContainer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaContainer_H_
#define _RAREaContainer_H_

@class JavaNetURL;
@class JavaUtilHashMap;
@class RAREActionLink;
@class RARERenderableDataItem;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIPoint;
@class RAREUISelectionModelGroup;
@class RAREUTIdentityArrayList;
@class RAREaContainer_WidgetLocationComparator;
@class RAREiContainer_LayoutEnum;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiContainer;
@protocol RAREiFunctionCallback;
@protocol RAREiListHandler;
@protocol RAREiParentComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aPlatformViewer.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "java/util/Comparator.h"

@interface RAREaContainer : RAREaPlatformViewer < RAREiFormViewer > {
 @public
  RAREaContainer_WidgetLocationComparator *comparator_;
  BOOL actAsFormViewer_;
  RAREUTIdentityArrayList *widgetList_;
  BOOL _enabled_;
  BOOL sortedFormWidgetsDirty_;
  JavaUtilHashMap *formWidgets_;
  BOOL hogFocus_;
  JavaUtilHashMap *labelWdgets_;
  JavaUtilHashMap *selectionGroupMap_;
  BOOL showWaitCursorForChildCreation_;
  RAREUTIdentityArrayList *sortedFormWidgetsList_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (BOOL)addWithId:(RARERenderableDataItem *)item;
- (void)addWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)item;
- (RAREUISelectionModelGroup *)addToSelectionGroupWithNSString:(NSString *)name
                                          withRAREiListHandler:(id<RAREiListHandler>)comp
                                                        withId:(id)model
                                                       withInt:(int)position;
- (id<RAREiWidget>)addWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id<RAREiWidget>)addWidgetWithRAREiParentComponent:(id<RAREiParentComponent>)panel
                                  withRARESPOTWidget:(RARESPOTWidget *)cfg
                       withRAREiContainer_LayoutEnum:(RAREiContainer_LayoutEnum *)fm;
- (void)addWidgetWithRAREiWidget:(id<RAREiWidget>)widget
                          withId:(id)constraints
                         withInt:(int)position;
- (void)clearContents;
- (void)removeAllWidgets;
- (void)clearForm;
- (id<RAREiWidget>)createWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (id<RAREiWidget>)createWidgetWithRAREiContainer:(id<RAREiContainer>)parent
                               withRAREActionLink:(RAREActionLink *)link;
+ (id<RAREiWidget>)createWidgetWithRAREiContainer:(id<RAREiContainer>)parent
                               withRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (id<RAREiWidget>)createWidgetWithRAREiContainer:(id<RAREiContainer>)parent
                               withRARESPOTWidget:(RARESPOTWidget *)cfg
                                   withJavaNetURL:(JavaNetURL *)context;
- (BOOL)handleFocusWithRAREiWidget:(id<RAREiWidget>)from
                       withBoolean:(BOOL)next;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (void)register__;
- (id<RAREiWidget>)registerFormWidgetWithRAREiWidget:(id<RAREiWidget>)w;
- (id<RAREiWidget>)registerFormWidgetWithNSString:(NSString *)name
                                  withRAREiWidget:(id<RAREiWidget>)w;
- (void)registerLabelForWidgetWithId:(id)label
                        withNSString:(NSString *)widgetName;
- (void)reloadWithBoolean:(BOOL)context;
- (void)reloadForm;
- (RARERenderableDataItem *)removeWithInt:(int)index;
- (void)removeFromSelectionGroupWithNSString:(NSString *)name
                                      withId:(id)model;
- (void)removeWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)resetForm;
- (int)size;
- (void)submitFormWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)unregisterWithBoolean:(BOOL)disposing;
- (void)unregisterFormWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (id<RAREiWidget>)unregisterFormWidgetWithNSString:(NSString *)name;
- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)item;
- (void)setActAsFormViewerWithBoolean:(BOOL)act;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setHogFocusWithBoolean:(BOOL)hogFocus;
- (void)setShowWaitCursorForChildCreationWithBoolean:(BOOL)show;
- (void)setSubmittAttributeWithNSString:(NSString *)name
                                 withId:(id)value;
- (void)setWidgetsEnabledWithBoolean:(BOOL)enabled
                         withBoolean:(BOOL)all;
- (RARERenderableDataItem *)getWithInt:(int)index;
- (id<RAREiContainer>)getContainerViewer;
- (id)getElementByIdWithNSString:(NSString *)name;
- (id<RAREiFormViewer>)getFormViewer;
- (id<JavaUtilList>)getFormWidgets;
- (JavaUtilHashMap *)getHTTPValuesHash;
- (id)getSubmittAttributeWithNSString:(NSString *)name;
- (id<RAREiContainer>)getViewer;
- (id<RAREiWidget>)getWidget;
- (id<RAREiWidget>)getWidgetWithInt:(int)index;
- (id<RAREiWidget>)getWidgetWithNSString:(NSString *)name;
- (int)getWidgetCount;
- (id<RAREiWidget>)getWidgetFromPathWithNSString:(NSString *)path;
- (id<JavaUtilList>)getWidgetList;
- (id<JavaUtilList>)getWidgetNames;
- (id<JavaUtilMap>)getWidgets;
- (BOOL)isActAsFormViewer;
- (BOOL)isAncestorOfWithRAREiWidget:(id<RAREiWidget>)w;
- (BOOL)isBackPressedHandled;
- (BOOL)isContainer;
- (BOOL)isContainerEnabled;
- (BOOL)isEnabled;
- (BOOL)isFocusableInCurrentState;
- (BOOL)isHogFocus;
- (BOOL)isRetainInitialWidgetValues;
- (BOOL)isShowWaitCursorForChildCreation;
- (BOOL)isTextDirectionSet;
- (id<RAREiWidget>)addWidgetExWithRAREiParentComponent:(id<RAREiParentComponent>)panel
                                    withRARESPOTWidget:(RARESPOTWidget *)cfg
                         withRAREiContainer_LayoutEnum:(RAREiContainer_LayoutEnum *)layout;
- (void)callaViewerRegister;
- (void)callaViewerunRegisterWithBoolean:(BOOL)disposing;
- (RARESPOTViewer *)checkForURLConfigWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (id)createConstraintsWithRAREiParentComponent:(id<RAREiParentComponent>)panel
                             withRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id)getConsraintsWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)registerWidgetWithRAREiWidget:(id<RAREiWidget>)w;
- (void)registerWidgets;
- (void)unregisterWidgets;
- (void)unregisterWidgetWithRAREiWidget:(id<RAREiWidget>)w;
- (void)updateEx;
- (id<JavaUtilList>)getSortedFormWidgetList;
- (id<JavaUtilList>)getWidgetListEx;
- (void)copyAllFieldsTo:(RAREaContainer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaContainer, comparator_, RAREaContainer_WidgetLocationComparator *)
J2OBJC_FIELD_SETTER(RAREaContainer, widgetList_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(RAREaContainer, formWidgets_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaContainer, labelWdgets_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaContainer, selectionGroupMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaContainer, sortedFormWidgetsList_, RAREUTIdentityArrayList *)

typedef RAREaContainer ComAppnativaRareViewerAContainer;

@interface RAREaContainer_WidgetLocationComparator : NSObject < JavaUtilComparator > {
 @public
  RAREUIPoint *p_;
}

- (int)compareWithId:(id<RAREiWidget>)o1
              withId:(id<RAREiWidget>)o2;
- (id)init;
- (void)copyAllFieldsTo:(RAREaContainer_WidgetLocationComparator *)other;
@end

J2OBJC_FIELD_SETTER(RAREaContainer_WidgetLocationComparator, p_, RAREUIPoint *)

#endif // _RAREaContainer_H_