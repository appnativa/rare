//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/DataItemCSVParser.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREDataItemCSVParser_H_
#define _RAREDataItemCSVParser_H_

@class IOSCharArray;
@class JavaIoBufferedReader;
@class JavaIoReader;
@class RAREColumn;
@class RARERenderableDataItem;
@class RAREUTCharArray;
@class RAREUTCharScanner;
@class RAREUTMutableInteger;
@class SPOTSequence;
@protocol RAREiDataItemParserCallback;
@protocol RAREiWidget;

#import "JreEmulation.h"

@interface RAREDataItemCSVParser : NSObject {
 @public
  unichar ldSep_;
  unichar riSeparator_;
  RAREUTCharScanner *scanner_;
  RAREUTCharScanner *fscanner_;
  BOOL unescape_;
  JavaIoBufferedReader *biReader_;
  unichar colSep_;
  RAREUTMutableInteger *itemLevel_;
  RAREUTCharArray *quotedCleaner_;
}

- (id)init;
- (id)initWithJavaIoReader:(JavaIoReader *)reader;
- (id)initWithJavaIoReader:(JavaIoReader *)input
                  withChar:(unichar)colsep
                  withChar:(unichar)ldsep;
- (void)parseWithRAREiWidget:(id<RAREiWidget>)context
                     withInt:(int)cols
withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (RAREColumn *)parseColumnItemWithRAREiWidget:(id<RAREiWidget>)context
                                  withNSString:(NSString *)value
               withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (RARERenderableDataItem *)parseDataItemWithRAREiWidget:(id<RAREiWidget>)context
                                            withNSString:(NSString *)value
                         withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)resetWithJavaIoReader:(JavaIoReader *)input
                     withChar:(unichar)colsep
                     withChar:(unichar)ldsep;
- (void)setLinkedDataSeparatorWithChar:(unichar)sep;
- (void)setRowInfoSeparatorWithChar:(unichar)sep;
- (void)setUnescapeWithBoolean:(BOOL)unescape;
- (unichar)getRowInfoSeparator;
- (RARERenderableDataItem *)parseColumnWithRAREiWidget:(id<RAREiWidget>)context
                                 withRAREUTCharScanner:(RAREUTCharScanner *)sc
                       withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback
                                           withBoolean:(BOOL)itemdescription;
- (void)parseItemWithRAREiWidget:(id<RAREiWidget>)context
                withJavaIoReader:(JavaIoReader *)reader
                withSPOTSequence:(SPOTSequence *)item;
- (NSString *)cleanQuotedWithCharArray:(IOSCharArray *)a
                               withInt:(int)pos
                               withInt:(int)len;
- (RAREColumn *)createColumnWithNSString:(NSString *)value
         withRAREiDataItemParserCallback:(id<RAREiDataItemParserCallback>)callback;
- (void)copyAllFieldsTo:(RAREDataItemCSVParser *)other;
@end

J2OBJC_FIELD_SETTER(RAREDataItemCSVParser, scanner_, RAREUTCharScanner *)
J2OBJC_FIELD_SETTER(RAREDataItemCSVParser, fscanner_, RAREUTCharScanner *)
J2OBJC_FIELD_SETTER(RAREDataItemCSVParser, biReader_, JavaIoBufferedReader *)
J2OBJC_FIELD_SETTER(RAREDataItemCSVParser, itemLevel_, RAREUTMutableInteger *)
J2OBJC_FIELD_SETTER(RAREDataItemCSVParser, quotedCleaner_, RAREUTCharArray *)

typedef RAREDataItemCSVParser ComAppnativaRareUtilDataItemCSVParser;

#endif // _RAREDataItemCSVParser_H_
