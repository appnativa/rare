//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/DocumentChangeEvent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREDocumentChangeEvent_H_
#define _RAREDocumentChangeEvent_H_

@class RAREDocumentChangeEvent_EventTypeEnum;
@protocol RAREiTextAttributes;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "java/lang/Enum.h"

@interface RAREDocumentChangeEvent : RAREChangeEvent {
 @public
  id<RAREiTextAttributes> attributeSet_;
  RAREDocumentChangeEvent_EventTypeEnum *eventType_;
  NSString *linkHref_;
  id linkItem_;
}

- (id)initWithId:(id)source;
- (id)initWithId:(id)source
withRAREDocumentChangeEvent_EventTypeEnum:(RAREDocumentChangeEvent_EventTypeEnum *)type;
+ (RAREDocumentChangeEvent *)hyperlinkEventWithId:(id)source
        withRAREDocumentChangeEvent_EventTypeEnum:(RAREDocumentChangeEvent_EventTypeEnum *)type
                                           withId:(id)item
                                     withNSString:(NSString *)href;
+ (RAREDocumentChangeEvent *)modificationEventWithId:(id)source;
+ (RAREDocumentChangeEvent *)navigationEventWithId:(id)source
                           withRAREiTextAttributes:(id<RAREiTextAttributes>)set;
- (NSString *)getEventName;
- (RAREDocumentChangeEvent_EventTypeEnum *)getEventType;
- (NSString *)getLinkHref;
- (id)getLinkItem;
- (id<RAREiTextAttributes>)getNavigationStyle;
- (BOOL)isHyperlinkClickEvent;
- (BOOL)isHyperlinkEvent;
- (BOOL)isModificationEvent;
- (BOOL)isStyleChangeEvent;
- (BOOL)isStyleNavigationEvent;
- (void)copyAllFieldsTo:(RAREDocumentChangeEvent *)other;
@end

J2OBJC_FIELD_SETTER(RAREDocumentChangeEvent, attributeSet_, id<RAREiTextAttributes>)
J2OBJC_FIELD_SETTER(RAREDocumentChangeEvent, eventType_, RAREDocumentChangeEvent_EventTypeEnum *)
J2OBJC_FIELD_SETTER(RAREDocumentChangeEvent, linkHref_, NSString *)
J2OBJC_FIELD_SETTER(RAREDocumentChangeEvent, linkItem_, id)

typedef RAREDocumentChangeEvent ComAppnativaRareUiEventDocumentChangeEvent;

typedef enum {
  RAREDocumentChangeEvent_EventType_UNKNOWN = 0,
  RAREDocumentChangeEvent_EventType_INSERT = 1,
  RAREDocumentChangeEvent_EventType_DELETE = 2,
  RAREDocumentChangeEvent_EventType_MODIFICATION_STATE = 3,
  RAREDocumentChangeEvent_EventType_STYLE_CHANGE = 4,
  RAREDocumentChangeEvent_EventType_STYLE_NAVIGATION = 5,
  RAREDocumentChangeEvent_EventType_LINK_ENTERED = 6,
  RAREDocumentChangeEvent_EventType_LINK_EXITED = 7,
  RAREDocumentChangeEvent_EventType_LINK_CLICKED = 8,
} RAREDocumentChangeEvent_EventType;

@interface RAREDocumentChangeEvent_EventTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREDocumentChangeEvent_EventTypeEnum *)UNKNOWN;
+ (RAREDocumentChangeEvent_EventTypeEnum *)INSERT;
+ (RAREDocumentChangeEvent_EventTypeEnum *)DELETE;
+ (RAREDocumentChangeEvent_EventTypeEnum *)MODIFICATION_STATE;
+ (RAREDocumentChangeEvent_EventTypeEnum *)STYLE_CHANGE;
+ (RAREDocumentChangeEvent_EventTypeEnum *)STYLE_NAVIGATION;
+ (RAREDocumentChangeEvent_EventTypeEnum *)LINK_ENTERED;
+ (RAREDocumentChangeEvent_EventTypeEnum *)LINK_EXITED;
+ (RAREDocumentChangeEvent_EventTypeEnum *)LINK_CLICKED;
+ (IOSObjectArray *)values;
+ (RAREDocumentChangeEvent_EventTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREDocumentChangeEvent_H_
