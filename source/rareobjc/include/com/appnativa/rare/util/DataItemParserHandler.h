//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/DataItemParserHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREDataItemParserHandler_H_
#define _RAREDataItemParserHandler_H_

@class JavaIoReader;
@class JavaNetURL;
@class JavaUtilHashMap;
@class JavaUtilLinkedList;
@class RAREActionLink;
@class RAREDataEvent;
@class RARERenderableDataItem;
@class RARESPOTDataCollection;
@class RARESPOTDataItem;
@class RAREUTObjectHolder;
@class SPOTSet;
@protocol JavaUtilCollection;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiDataCollection;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformIcon;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iDataItemParser.h"
#include "com/appnativa/rare/iDataItemParserCallback.h"
#include "com/appnativa/rare/util/DataItemCollection.h"
#include "java/lang/Runnable.h"

@interface RAREDataItemParserHandler : RAREDataItemCollection < RAREiDataItemParserCallback, JavaLangRunnable > {
 @public
  BOOL tabular_;
  RAREActionLink *actionLink_;
  id<RAREiFunctionCallback> callback_;
  RARERenderableDataItem *currentRow_;
  BOOL hasRowInfo_;
  JavaUtilLinkedList *levelStack_;
  RARERenderableDataItem *rootItem_;
  id<RAREiWidget> widget_;
}

+ (JavaUtilHashMap *)customParsers;
+ (void)setCustomParsers:(JavaUtilHashMap *)customParsers;
+ (BOOL)hasCustomParsers;
+ (BOOL *)hasCustomParsersRef;
- (id)initWithRAREiWidget:(id<RAREiWidget>)v;
- (id)initWithRAREiWidget:(id<RAREiWidget>)v
         withJavaUtilList:(id<JavaUtilList>)list;
- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
+ (id<RAREiDataCollection>)createCollectionWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                                            withRARESPOTDataCollection:(RARESPOTDataCollection *)dc
                                             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (id<RAREiDataCollection>)createCollectionWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                                                          withNSString:(NSString *)name
                                                    withRAREActionLink:(RAREActionLink *)link
                                                       withJavaUtilMap:(id<JavaUtilMap>)attributes
                                                           withBoolean:(BOOL)tabular
                                             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (RARERenderableDataItem *)createItemWithId:(id)value;
- (RARERenderableDataItem *)createItemWithId:(id)value
                                     withInt:(int)type
                                      withId:(id)data
                                      withId:(id)icon
                                      withId:(id)color;
- (RARERenderableDataItem *)createItemExWithRARESPOTDataItem:(RARESPOTDataItem *)item;
- (void)disposeCollection;
- (void)finishedParsing;
- (void)load__WithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (id<JavaUtilList>)parseWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link
                                 withInt:(int)columnCount;
+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                 withSPOTSet:(SPOTSet *)items
            withJavaUtilList:(id<JavaUtilList>)list;
+ (void)parseCollectionWithRAREiWidget:(id<RAREiWidget>)context
                    withRAREActionLink:(RAREActionLink *)link
                               withInt:(int)columnCount
       withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
          withRAREActionLink:(RAREActionLink *)link
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                     withInt:(int)cols
        withRARESPOTDataItem:(RARESPOTDataItem *)items
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
+ (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                withNSString:(NSString *)mimeType
            withJavaIoReader:(JavaIoReader *)reader
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback
              withJavaNetURL:(JavaNetURL *)contextURL;
+ (RARERenderableDataItem *)parseItemWithRARESPOTDataItem:(RARESPOTDataItem *)item
                          withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback
                                              withBoolean:(BOOL)parseSubs;
- (void)refreshWithRAREiWidget:(id<RAREiWidget>)context;
- (void)run;
- (void)startedParsing;
- (void)setRootItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (id<RAREiPlatformIcon>)getIconWithNSString:(NSString *)icon
                                withNSString:(NSString *)description_;
- (RARERenderableDataItem *)getRootItem;
- (id<JavaUtilList>)getListWithRAREiWidget:(id<RAREiWidget>)context;
- (void)callCallbackWithId:(id)returnValue
               withBoolean:(BOOL)canceled
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (id<RAREiDataItemParser>)createCustomParserWithNSString:(NSString *)name;
+ (id<RAREiDataItemParser>)createCustomParserFromMimeWithNSString:(NSString *)mimeType;
- (void)setActionLinkWithRAREActionLink:(RAREActionLink *)link;
- (void)copyAllFieldsTo:(RAREDataItemParserHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREDataItemParserHandler, actionLink_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler, callback_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler, currentRow_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler, levelStack_, JavaUtilLinkedList *)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler, rootItem_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler, widget_, id<RAREiWidget>)

typedef RAREDataItemParserHandler ComAppnativaRareUtilDataItemParserHandler;

@interface RAREDataItemParserHandler_ScriptDataParser : NSObject < RAREiDataItemParser > {
 @public
  id code_;
  RAREDataEvent *dataEvent_;
  id<RAREiDataItemParserCallback> callback_;
  int columnCount_;
  RAREActionLink *link_;
  JavaIoReader *reader_;
  BOOL unescape_;
}

- (id)initWithNSString:(NSString *)code;
- (id<JavaUtilCollection>)parseWithRAREiWidget:(id<RAREiWidget>)context
                            withRAREActionLink:(RAREActionLink *)link
                                       withInt:(int)columnCount;
- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
          withRAREActionLink:(RAREActionLink *)link
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (id<JavaUtilCollection>)parseWithRAREiWidget:(id<RAREiWidget>)context
                              withJavaIoReader:(JavaIoReader *)reader
                                  withNSString:(NSString *)mimeType
                                       withInt:(int)columnCount;
- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
            withJavaIoReader:(JavaIoReader *)reader
                withNSString:(NSString *)mimeType
                     withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)setUnescapeWithBoolean:(BOOL)unescape;
- (id<RAREiDataItemParserCallback>)getCallback;
- (int)getColumnCount;
- (RAREActionLink *)getLink;
- (JavaIoReader *)getReader;
- (BOOL)isUnescape;
- (id)parseWithRAREiWidget:(id<RAREiWidget>)context
        withRAREActionLink:(RAREActionLink *)link
          withJavaIoReader:(JavaIoReader *)reader
                   withInt:(int)columnCount
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)copyAllFieldsTo:(RAREDataItemParserHandler_ScriptDataParser *)other;
@end

J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_ScriptDataParser, code_, id)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_ScriptDataParser, dataEvent_, RAREDataEvent *)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_ScriptDataParser, callback_, id<RAREiDataItemParserCallback>)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_ScriptDataParser, link_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_ScriptDataParser, reader_, JavaIoReader *)

@interface RAREDataItemParserHandler_$1 : NSObject < JavaLangRunnable > {
 @public
  BOOL val$canceled_;
  id<RAREiFunctionCallback> val$cb_;
  RAREUTObjectHolder *val$h_;
}

- (void)run;
- (id)initWithBoolean:(BOOL)capture$0
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$1
withRAREUTObjectHolder:(RAREUTObjectHolder *)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_$1, val$cb_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RAREDataItemParserHandler_$1, val$h_, RAREUTObjectHolder *)

#endif // _RAREDataItemParserHandler_H_
