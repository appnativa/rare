//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/aLabelWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaLabelWidget_H_
#define _RAREaLabelWidget_H_

@class RARELabelWidget;
@class RAREMouseEvent;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RARESPOTLabel;
@class RARESPOTWidget;
@class RAREUIInsets;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/listener/iHyperlinkListener.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaLabelWidget : RAREaPlatformWidget {
 @public
  NSString *initialValue_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearContents;
- (void)configureWithRARESPOTLabel:(RARESPOTLabel *)cfg;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (RARELabelWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent;
+ (RARELabelWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTLabel:(RARESPOTLabel *)cfg;
- (void)reset;
- (void)setHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)alignment;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setMarginWithFloat:(float)top
                 withFloat:(float)right
                 withFloat:(float)bottom
                 withFloat:(float)left;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setValueWithId:(id)value;
- (void)setVerticalAlignmentWithRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)alignment;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (id)getHTTPFormValue;
- (id)getSelection;
- (id<JavaLangCharSequence>)getText;
- (id)getValue;
- (BOOL)isWordWrap;
- (id<RAREiHyperlinkListener>)createHyperlinkListener;
- (id<RAREiActionComponent>)createActionComponentWithRARESPOTLabel:(RARESPOTLabel *)cfg;
- (void)copyAllFieldsTo:(RAREaLabelWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaLabelWidget, initialValue_, NSString *)

typedef RAREaLabelWidget ComAppnativaRareWidgetALabelWidget;

@interface RAREaLabelWidget_$1 : NSObject < RAREiHyperlinkListener > {
 @public
  RAREaLabelWidget *this$0_;
}

- (void)linkExitedWithId:(id)source
                  withId:(id)item
            withNSString:(NSString *)href
      withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)linkEnteredWithId:(id)source
                   withId:(id)item
             withNSString:(NSString *)href
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)linkClickedWithId:(id)source
                   withId:(id)item
             withNSString:(NSString *)href
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (id)initWithRAREaLabelWidget:(RAREaLabelWidget *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaLabelWidget_$1, this$0_, RAREaLabelWidget *)

#endif // _RAREaLabelWidget_H_
