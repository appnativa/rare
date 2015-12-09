//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/DataItemJSONParser.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREDataItemJSONParser_H_
#define _RAREDataItemJSONParser_H_

@class JavaIoReader;
@class RARERenderableDataItem;
@class RAREUTCharArray;
@class RAREUTJSONArray;
@class RAREUTJSONObject;
@protocol JavaUtilList;
@protocol RAREiDataItemParserCallback;
@protocol RAREiWidget;

#import "JreEmulation.h"

@interface RAREDataItemJSONParser : NSObject {
 @public
  BOOL unescape_;
  RAREUTCharArray *quotedCleaner_;
  JavaIoReader *sourceReader_;
}

- (id)init;
- (id)initWithJavaIoReader:(JavaIoReader *)reader;
- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                 withBoolean:(BOOL)tabular
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
        withRAREUTJSONObject:(RAREUTJSONObject *)json
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)resetWithJavaIoReader:(JavaIoReader *)input;
- (void)setUnescapeWithBoolean:(BOOL)unescape;
- (void)addRowsWithJavaUtilList:(id<JavaUtilList>)list
            withRAREUTJSONArray:(RAREUTJSONArray *)columns
            withRAREUTJSONArray:(RAREUTJSONArray *)rows
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)addItemsWithJavaUtilList:(id<JavaUtilList>)list
             withRAREUTJSONArray:(RAREUTJSONArray *)array
 withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (RARERenderableDataItem *)createItemWithRAREUTJSONObject:(RAREUTJSONObject *)json
                           withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (RARERenderableDataItem *)createRowWithRAREUTJSONArray:(RAREUTJSONArray *)array
                         withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (RARERenderableDataItem *)createRowWithRAREUTJSONArray:(RAREUTJSONArray *)columns
                                    withRAREUTJSONObject:(RAREUTJSONObject *)json
                         withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)copyAllFieldsTo:(RAREDataItemJSONParser *)other;
@end

J2OBJC_FIELD_SETTER(RAREDataItemJSONParser, quotedCleaner_, RAREUTCharArray *)
J2OBJC_FIELD_SETTER(RAREDataItemJSONParser, sourceReader_, JavaIoReader *)

typedef RAREDataItemJSONParser ComAppnativaRareUtilDataItemJSONParser;

#endif // _RAREDataItemJSONParser_H_
