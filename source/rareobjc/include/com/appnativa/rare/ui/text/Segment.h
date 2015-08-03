//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/text/Segment.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESegment_H_
#define _RARESegment_H_

@class IOSCharArray;

#import "JreEmulation.h"
#include "java/lang/CharSequence.h"
#include "java/text/CharacterIterator.h"

@interface RARESegment : NSObject < NSCopying, JavaTextCharacterIterator, JavaLangCharSequence > {
 @public
  IOSCharArray *array_;
  int count_;
  int offset_;
  BOOL isPartial_;
  int pos_;
}

- (id)init;
- (id)initWithCharArray:(IOSCharArray *)array
                withInt:(int)offset
                withInt:(int)count;
- (id)clone;
- (unichar)current;
- (unichar)first;
- (int)getBeginIndex;
- (int)getEndIndex;
- (int)getIndex;
- (BOOL)isPartialReturn;
- (unichar)last;
- (unichar)next;
- (unichar)previous;
- (unichar)setIndexWithInt:(int)position;
- (void)setPartialReturnWithBoolean:(BOOL)p;
- (NSString *)sequenceDescription;
- (BOOL)isEmpty;
- (unichar)charAtWithInt:(int)index;
- (int)sequenceLength;
- (id<JavaLangCharSequence>)subSequenceFrom:(int)start to:(int)end;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RARESegment *)other;
@end

J2OBJC_FIELD_SETTER(RARESegment, array_, IOSCharArray *)

typedef RARESegment ComAppnativaRareUiTextSegment;

#endif // _RARESegment_H_