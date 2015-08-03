//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/AlertPanel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREAlertPanel_H_
#define _RAREAlertPanel_H_

@class JavaLangThrowable;
@class RAREActionEvent;
@class RAREErrorInformation;
@class RARELabelWidget;
@class RAREPushButtonWidget;
@class RARETextAreaWidget;
@class RARETextFieldWidget;
@class RAREUIColor;
@class RAREUIImageIcon;
@class RAREWindowViewer;
@class RAREaWidget;
@protocol JavaLangCharSequence;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/LinearPanel.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "java/lang/Runnable.h"

@interface RAREAlertPanel : RARELinearPanel < RAREiActionListener > {
 @public
  id<RAREiFunctionCallback> callback_;
  RAREPushButtonWidget *cancelButton_;
  BOOL isError_;
  RAREPushButtonWidget *noButton_;
  RARELabelWidget *titleLabel_;
  RAREWindowViewer *window_;
  RAREPushButtonWidget *yesButton_;
}

+ (RAREUIColor *)foregroundColor;
+ (void)setForegroundColor:(RAREUIColor *)foregroundColor;
+ (RAREUIColor *)backgroundColor;
+ (void)setBackgroundColor:(RAREUIColor *)backgroundColor_AlertPanel;
+ (RAREUIColor *)lineColor;
+ (void)setLineColor:(RAREUIColor *)lineColor;
+ (RAREUIColor *)titleLineColor;
+ (void)setTitleLineColor:(RAREUIColor *)titleLineColor;
+ (BOOL)hasErrorTitleChecked;
+ (BOOL *)hasErrorTitleCheckedRef;
+ (BOOL)hasErrorTitleTemplate;
+ (BOOL *)hasErrorTitleTemplateRef;
+ (BOOL)showApplicationIcon;
+ (BOOL *)showApplicationIconRef;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
             withNSString:(NSString *)title
                   withId:(id)message
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
              withBoolean:(BOOL)forError;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)cancel;
+ (RAREPushButtonWidget *)createButtonWithNSString:(NSString *)text
                                      withNSString:(NSString *)templateName
                                      withNSString:(NSString *)borderInsets
                           withRAREiActionListener:(id<RAREiActionListener>)listener;
+ (RAREPushButtonWidget *)createButtonWithNSString:(NSString *)text
                                      withNSString:(NSString *)templateName
                                      withNSString:(NSString *)borderInsets
                           withRAREiActionListener:(id<RAREiActionListener>)listener
                                   withRAREUIColor:(RAREUIColor *)borderColor;
- (void)dispose;
+ (RAREAlertPanel *)errorWithRAREiWidget:(id<RAREiWidget>)context
                withRAREErrorInformation:(RAREErrorInformation *)ei;
+ (RAREAlertPanel *)errorWithRAREiWidget:(id<RAREiWidget>)context
                   withJavaLangThrowable:(JavaLangThrowable *)e
                             withBoolean:(BOOL)showTerminate;
+ (RAREAlertPanel *)okWithRAREiWidget:(id<RAREiWidget>)context
                         withNSString:(NSString *)title
           withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (RAREAlertPanel *)okWithRAREiWidget:(id<RAREiWidget>)context
                         withNSString:(NSString *)title
                               withId:(id)message
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (RAREAlertPanel *)okCancelWithRAREiWidget:(id<RAREiWidget>)context
                               withNSString:(NSString *)title
                                     withId:(id)message
                        withRAREUIImageIcon:(RAREUIImageIcon *)icon;
+ (RAREAlertPanel *)promptWithRAREiWidget:(id<RAREiWidget>)context
                             withNSString:(NSString *)title
                             withNSString:(NSString *)prompt
                                   withId:(id)value
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)showDialogWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (void)showErrorDialogWithRAREErrorInformation:(RAREErrorInformation *)ei;
+ (void)showErrorDialogWithJavaLangThrowable:(JavaLangThrowable *)error;
+ (void)showErrorDialogWithRAREErrorInformation:(RAREErrorInformation *)ei
                      withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (void)showErrorDialogWithJavaLangThrowable:(JavaLangThrowable *)error
                   withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (RAREAlertPanel *)yesNoWithRAREiWidget:(id<RAREiWidget>)context
                            withNSString:(NSString *)title
                            withNSString:(NSString *)message
                     withRAREUIImageIcon:(RAREUIImageIcon *)icon;
+ (RAREAlertPanel *)yesNoWithRAREiWidget:(id<RAREiWidget>)context
                            withNSString:(NSString *)title
                                  withId:(id)message
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                            withNSString:(NSString *)yes
                            withNSString:(NSString *)no
                             withBoolean:(BOOL)forOkCancel;
+ (RAREAlertPanel *)yesNoCancelWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)title
                                        withId:(id)message
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                  withNSString:(NSString *)yes
                                  withNSString:(NSString *)no
                                  withNSString:(NSString *)cancel;
- (void)addButtonsWithRAREiWidget:(id<RAREiWidget>)context;
- (RARELinearPanel *)createButtonPanelWithRAREiWidget:(id<RAREiWidget>)context
                                          withBoolean:(BOOL)horizontal;
- (RARELabelWidget *)createLabelWithId:(id)text
                          withNSString:(NSString *)templateName
                           withBoolean:(BOOL)title;
- (RARETextAreaWidget *)createTextAreaWithNSString:(NSString *)text
                                      withNSString:(NSString *)templateName;
- (RARETextFieldWidget *)createTextFieldWithNSString:(NSString *)text
                                        withNSString:(NSString *)templateName
                                         withBoolean:(BOOL)details;
- (id<JavaLangCharSequence>)messageToStringWithId:(id)message;
- (void)packWindow;
+ (id<RAREiPlatformIcon>)getIconWithNSString:(NSString *)name;
+ (void)adjustLabelWithRAREaWidget:(RAREaWidget *)l
                       withBoolean:(BOOL)error;
- (void)copyAllFieldsTo:(RAREAlertPanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREAlertPanel, callback_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RAREAlertPanel, cancelButton_, RAREPushButtonWidget *)
J2OBJC_FIELD_SETTER(RAREAlertPanel, noButton_, RAREPushButtonWidget *)
J2OBJC_FIELD_SETTER(RAREAlertPanel, titleLabel_, RARELabelWidget *)
J2OBJC_FIELD_SETTER(RAREAlertPanel, window_, RAREWindowViewer *)
J2OBJC_FIELD_SETTER(RAREAlertPanel, yesButton_, RAREPushButtonWidget *)

typedef RAREAlertPanel ComAppnativaRareUiAlertPanel;

@interface RAREAlertPanel_$1 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREAlertPanel_$1, val$cb_, id<RAREiFunctionCallback>)

#endif // _RAREAlertPanel_H_
