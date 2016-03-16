//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-navigator/com/appnativa/rare/widget/aNavigatorWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaNavigatorWidget_H_
#define _RAREaNavigatorWidget_H_

@class RAREActionLink;
@class RARENavigatorWidget;
@class RARESPOTNavigator;
@class RARESPOTWidget;
@class RAREUIAction;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREaNavigatorPanel;
@class RAREiNavigatorPanel_PanelTypeEnum;
@protocol RAREiActionComponent;
@protocol RAREiContainer;
@protocol RAREiNavigatorPanel;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponentPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaNavigatorWidget : RAREaPlatformWidget {
 @public
  id<RAREiNavigatorPanel> panel_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id<RAREiActionComponent>)addActionWithRAREUIAction:(RAREUIAction *)a;
- (id<RAREiActionComponent>)addActionWithNSString:(NSString *)text
                               withRAREActionLink:(RAREActionLink *)link;
- (id<RAREiActionComponent>)addActionWithNSString:(NSString *)text
                                     withNSString:(NSString *)code;
- (void)backup;
- (void)clickWithInt:(int)index;
- (void)clickWithNSString:(NSString *)name;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (RARENavigatorWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTNavigator:(RARESPOTNavigator *)cfg;
- (void)dispose;
- (void)home;
- (int)indexForNameWithNSString:(NSString *)name;
- (void)removeActionWithInt:(int)index;
- (void)removeActionWithNSString:(NSString *)name;
- (void)setActionEnabledWithInt:(int)index
                    withBoolean:(BOOL)enabled;
- (void)setActionVisibleWithInt:(int)index
                    withBoolean:(BOOL)visible;
- (void)setAlwaysFireActionWithBoolean:(BOOL)always;
- (void)setPanelTypeWithRAREiNavigatorPanel_PanelTypeEnum:(RAREiNavigatorPanel_PanelTypeEnum *)type;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedNameWithNSString:(NSString *)name;
- (void)setShowIconsOnlyWithBoolean:(BOOL)icon;
- (void)setValueWithId:(id)value;
- (RAREUIAction *)getActionWithInt:(int)index;
- (RAREUIAction *)getActionWithNSString:(NSString *)name;
- (id<RAREiNavigatorPanel>)getNavigatorPanel;
- (RAREiNavigatorPanel_PanelTypeEnum *)getPanelType;
- (RAREUIAction *)getSelectedAction;
- (int)getSelectedIndex;
- (NSString *)getSelectedName;
- (id)getSelection;
- (NSString *)getValueAsString;
- (BOOL)isActionEnabledWithInt:(int)index;
- (BOOL)isActionVisibleWithInt:(int)index;
- (BOOL)isAlwaysFireAction;
- (BOOL)isShowIconsOnly;
- (void)configureExWithRARESPOTNavigator:(RARESPOTNavigator *)cfg;
- (id)createBorderLayoutView;
- (RAREaNavigatorPanel *)createNavigatorPanel;
- (void)copyAllFieldsTo:(RAREaNavigatorWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaNavigatorWidget, panel_, id<RAREiNavigatorPanel>)

typedef RAREaNavigatorWidget ComAppnativaRareWidgetANavigatorWidget;

@interface RAREaNavigatorWidget_BorderPanelEx : RAREBorderPanel {
 @public
  __weak RAREaNavigatorPanel *np_;
}

- (id)initWithId:(id)view
withRAREaNavigatorPanel:(RAREaNavigatorPanel *)np;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (id<RAREiPlatformComponentPainter>)getComponentPainter;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (void)copyAllFieldsTo:(RAREaNavigatorWidget_BorderPanelEx *)other;
@end

#endif // _RAREaNavigatorWidget_H_
