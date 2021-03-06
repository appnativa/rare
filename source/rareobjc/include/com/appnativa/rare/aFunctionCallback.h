//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/aFunctionCallback.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaFunctionCallback_H_
#define _RAREaFunctionCallback_H_

@class RAREWindowViewer;
@protocol JavaLangCharSequence;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"

@interface RAREaFunctionCallback : NSObject < RAREiFunctionCallback > {
 @public
  __weak id<RAREiWidget> contextWidget_;
  BOOL done_;
  __weak id<RAREiWidget> updateWidget_;
  __weak RAREWindowViewer *window_;
  BOOL contextWidgetSet_;
}

- (id)init;
- (id)initWithRAREiWidget:(id<RAREiWidget>)contextWidget;
- (id)initWithRAREiWidget:(id<RAREiWidget>)contextWidget
          withRAREiWidget:(id<RAREiWidget>)updateWidget;
- (void)hideProgressPopup;
- (void)hideWaitCursor;
- (void)showProgressPopupWithJavaLangCharSequence:(id<JavaLangCharSequence>)message;
- (void)showWaitCursor;
- (BOOL)isContextAndWindowValid;
- (BOOL)isContextValid;
- (void)finishedWithBoolean:(BOOL)param0
                     withId:(id)param1;
- (void)copyAllFieldsTo:(RAREaFunctionCallback *)other;
@end

typedef RAREaFunctionCallback ComAppnativaRareAFunctionCallback;

#endif // _RAREaFunctionCallback_H_
