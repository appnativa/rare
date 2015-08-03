//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/SectionIndex.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESectionIndex_H_
#define _RARESectionIndex_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREPaintBucket;
@class RARERenderableDataItem;

#import "JreEmulation.h"

@interface RARESectionIndex : NSObject {
 @public
  IOSObjectArray *indextitles_;
  RAREPaintBucket *painter_;
  IOSIntArray *position_;
  IOSIntArray *length_;
  IOSObjectArray *titles_;
  RARERenderableDataItem *sectionPrototype_;
  IOSIntArray *indexPath_;
}

- (id)init;
- (id)initWithNSStringArray:(IOSObjectArray *)titles
               withIntArray:(IOSIntArray *)position
               withIntArray:(IOSIntArray *)length
 withRARERenderableDataItem:(RARERenderableDataItem *)prototype;
- (int)getSectionCount;
- (NSString *)getTitleTextWithInt:(int)section;
- (NSString *)getIndexTextWithInt:(int)section;
- (int)getFlatIndexWithInt:(int)section
                   withInt:(int)position;
- (int)getSizeWithInt:(int)section;
- (IOSIntArray *)getIndexPathForFlatPositionWithInt:(int)position;
- (void)copyAllFieldsTo:(RARESectionIndex *)other;
@end

J2OBJC_FIELD_SETTER(RARESectionIndex, indextitles_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RARESectionIndex, painter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RARESectionIndex, position_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RARESectionIndex, length_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RARESectionIndex, titles_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RARESectionIndex, sectionPrototype_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RARESectionIndex, indexPath_, IOSIntArray *)

typedef RARESectionIndex ComAppnativaRarePlatformAppleUiViewSectionIndex;

#endif // _RARESectionIndex_H_
