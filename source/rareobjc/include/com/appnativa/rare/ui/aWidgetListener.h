//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aWidgetListener.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaWidgetListener_H_
#define _RAREaWidgetListener_H_

@class JavaBeansPropertyChangeEvent;
@class JavaLangThrowable;
@class JavaUtilEventObject;
@class JavaUtilHashMap;
@class RAREActionEvent;
@class RAREChangeEvent;
@class RAREExpansionEvent;
@class RAREItemChangeEvent;
@class RAREKeyEvent;
@class RAREMouseEvent;
@class RARESPOTWidget;
@class RAREUIPoint;
@class RAREUTCharScanner;
@class RAREWindowEvent;
@protocol JavaLangCharSequence;
@protocol JavaUtilMap;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiScriptHandler;
@protocol RAREiScriptHandler_iScriptRunnable;
@protocol RAREiTabDocument;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/event/iExpandedListener.h"
#include "com/appnativa/rare/ui/event/iExpansionListener.h"
#include "com/appnativa/rare/ui/event/iItemChangeListener.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/event/iWindowListener.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/listener/iFocusListener.h"
#include "com/appnativa/rare/ui/listener/iKeyListener.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#include "com/appnativa/rare/ui/listener/iTabPaneListener.h"
#include "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#include "com/appnativa/rare/ui/listener/iViewListener.h"
#include "java/beans/PropertyChangeListener.h"
#include "java/lang/ThreadLocal.h"

@interface RAREaWidgetListener : NSObject < RAREiExpandedListener, RAREiExpansionListener, RAREiChangeListener, JavaBeansPropertyChangeListener, RAREiItemChangeListener, RAREiActionListener, RAREiPopupMenuListener, RAREiWindowListener, RAREiTabPaneListener, RAREiTextChangeListener, RAREiFocusListener, RAREiKeyListener, RAREiMouseListener, RAREiMouseMotionListener, RAREiViewListener, RAREiGestureListener > {
 @public
  BOOL actionEventEnabled_;
  id<RAREiActionListener> actionListener_;
  BOOL consumeTouchEvents_;
  BOOL disabled_;
  id<JavaUtilMap> eventMap_;
  NSString *eventPrefix_;
  BOOL expandedEventsEnabled_;
  BOOL expansionEventsEnabled_;
  BOOL keyEventsEnabled_;
  BOOL mouseEventsEnabled_;
  BOOL mouseMotionEventsEnabled_;
  RAREUIPoint *mousePressedPoint_;
  long long int mousePressedTime_;
  BOOL scaleEventsEnabled_;
  id<RAREiScriptHandler> scriptHandler_;
  id<RAREiWidget> theWidget_;
  BOOL hiddenFired_;
  BOOL shownFired_;
}

+ (NSString *)EVENT_PROEPRTY_CHANGE;
+ (void)setEVENT_PROEPRTY_CHANGE:(NSString *)EVENT_PROEPRTY_CHANGE;
+ (JavaLangThreadLocal *)perThreadScanner;
+ (void)setPerThreadScanner:(JavaLangThreadLocal *)perThreadScanner;
+ (JavaUtilHashMap *)webEvents;
+ (void)setWebEvents:(JavaUtilHashMap *)webEvents;
+ (JavaUtilHashMap *)webEventsEx;
+ (void)setWebEventsEx:(JavaUtilHashMap *)webEventsEx;
+ (JavaUtilHashMap *)eventHandlers;
+ (void)setEventHandlers:(JavaUtilHashMap *)eventHandlers;
+ (JavaUtilEventObject *)NO_EVENT;
+ (void)setNO_EVENT:(JavaUtilEventObject *)NO_EVENT;
- (id)initWithRAREiWidget:(id<RAREiWidget>)widget
          withJavaUtilMap:(id<JavaUtilMap>)map
   withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)mergeEventsWithJavaUtilMap:(id<JavaUtilMap>)map;
+ (id<JavaUtilMap>)createEventMapWithJavaUtilMap:(id<JavaUtilMap>)attributes;
+ (id<JavaUtilMap>)createEventMapWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                       withNSString:(NSString *)name
                             withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithNSString:(NSString *)event
                                           withJavaUtilEventObject:(JavaUtilEventObject *)e;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithNSString:(NSString *)event
                                           withJavaUtilEventObject:(JavaUtilEventObject *)e
                                                   withRAREiWidget:(id<RAREiWidget>)from;
- (void)dispose;
+ (id)evaluateWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                   withId:(id)code;
+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
                       withId:(id)code;
- (id)evaluateWithNSString:(NSString *)event
   withJavaUtilEventObject:(JavaUtilEventObject *)e
               withBoolean:(BOOL)ignoreException;
+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
      withJavaUtilEventObject:(JavaUtilEventObject *)e;
+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
                 withNSString:(NSString *)event
      withJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)executeWithNSString:(NSString *)event
    withJavaUtilEventObject:(JavaUtilEventObject *)e;
+ (void)executeWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                              withNSString:(NSString *)event
                                    withId:(id)code;
- (void)executeWithNSString:(NSString *)event
    withJavaUtilEventObject:(JavaUtilEventObject *)e
            withRAREiWidget:(id<RAREiWidget>)from;
+ (void)executeWithRAREiWidget:(id<RAREiWidget>)w
        withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                        withId:(id)code
                  withNSString:(NSString *)event
       withJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)focusChangedWithId:(id)view
               withBoolean:(BOOL)hasFocus
                    withId:(id)oldView
               withBoolean:(BOOL)temporary;
+ (NSString *)fromWebEventWithNSString:(NSString *)webEvent;
+ (NSString *)fromWebEventExWithNSString:(NSString *)webEvent;
- (void)itemChangedWithRAREItemChangeEvent:(RAREItemChangeEvent *)e;
- (void)itemHasCollapsedWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)itemHasExpandedWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)itemWillCollapseWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)itemWillExpandWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)keyPressedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyReleasedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyTypedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)loadEventWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)me1
   withRAREMouseEvent:(RAREMouseEvent *)me2
            withFloat:(float)velocityX
            withFloat:(float)velocityY;
- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY;
- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor;
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
+ (NSString *)processEventStringWithNSString:(NSString *)code;
- (void)propertyChangeWithJavaBeansPropertyChangeEvent:(JavaBeansPropertyChangeEvent *)e;
- (id)removeEventHandlerWithNSString:(NSString *)event
                              withId:(id)code;
- (BOOL)shouldStopEditingWithId:(id)source;
- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)tabActivatedWithRAREiTabDocument:(id<RAREiTabDocument>)target;
- (void)tabClosedWithRAREiTabDocument:(id<RAREiTabDocument>)target;
- (void)tabDeactivatedWithRAREiTabDocument:(id<RAREiTabDocument>)target;
- (void)tabOpenedWithRAREiTabDocument:(id<RAREiTabDocument>)target;
- (void)tabWillCloseWithRAREiTabDocument:(id<RAREiTabDocument>)target;
- (void)textChangedWithId:(id)source;
- (BOOL)textChangingWithId:(id)source
                   withInt:(int)startIndex
                   withInt:(int)endIndex
  withJavaLangCharSequence:(id<JavaLangCharSequence>)replacementString;
- (void)textValueChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)unloadEventWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)viewHiddenWithRAREChangeEvent:(RAREChangeEvent *)e;
- (void)viewResizedWithRAREChangeEvent:(RAREChangeEvent *)e;
- (void)viewShownWithRAREChangeEvent:(RAREChangeEvent *)e;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (BOOL)wantsResizeEvent;
- (void)windowEventWithRAREWindowEvent:(RAREWindowEvent *)e;
- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)setConsumeTouchEventsWithBoolean:(BOOL)consumeTouchEvents;
- (void)setDisabledWithBoolean:(BOOL)disabled;
- (id)setEventHandlerWithNSString:(NSString *)event
                           withId:(id)code
                      withBoolean:(BOOL)append;
- (id)getEventHandlerWithNSString:(NSString *)event;
- (BOOL)isActionEventEnabled;
- (BOOL)isChangeEventEnabled;
- (BOOL)isConsumeTouchEvents;
- (BOOL)isDisabled;
- (BOOL)isEnabledWithNSString:(NSString *)event;
- (BOOL)isExpandedEventsEnabled;
- (BOOL)isExpansionEventsEnabled;
- (BOOL)isKeyEventsEnabled;
- (BOOL)isMouseEventsEnabled;
- (BOOL)isMouseMotionEventsEnabled;
- (BOOL)isPropertyChangeEventEnabled;
+ (void)initailizeMaps OBJC_METHOD_FAMILY_NONE;
- (JavaLangThrowable *)runInlineWithRAREiScriptHandler_iScriptRunnable:(id<RAREiScriptHandler_iScriptRunnable>)r;
- (JavaLangThrowable *)getExceptionWithJavaLangThrowable:(JavaLangThrowable *)ex;
- (id<RAREiPlatformComponent>)getSourceWithId:(id)view;
- (void)copyAllFieldsTo:(RAREaWidgetListener *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWidgetListener, actionListener_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RAREaWidgetListener, eventMap_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREaWidgetListener, eventPrefix_, NSString *)
J2OBJC_FIELD_SETTER(RAREaWidgetListener, mousePressedPoint_, RAREUIPoint *)
J2OBJC_FIELD_SETTER(RAREaWidgetListener, scriptHandler_, id<RAREiScriptHandler>)
J2OBJC_FIELD_SETTER(RAREaWidgetListener, theWidget_, id<RAREiWidget>)

typedef RAREaWidgetListener ComAppnativaRareUiAWidgetListener;

@interface RAREaWidgetListener_MiniWidgetListener : NSObject {
 @public
  id<JavaUtilMap> _events_;
  id<RAREiWidget> _widget_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)w
          withJavaUtilMap:(id<JavaUtilMap>)events;
- (void)fireEventWithNSString:(NSString *)event
      withJavaUtilEventObject:(JavaUtilEventObject *)e
                  withBoolean:(BOOL)eval;
- (void)copyAllFieldsTo:(RAREaWidgetListener_MiniWidgetListener *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWidgetListener_MiniWidgetListener, _events_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREaWidgetListener_MiniWidgetListener, _widget_, id<RAREiWidget>)

@interface RAREaWidgetListener_$1 : JavaLangThreadLocal {
}

- (RAREUTCharScanner *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _RAREaWidgetListener_H_
