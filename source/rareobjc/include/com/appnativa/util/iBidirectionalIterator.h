//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iBidirectionalIterator.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUTiBidirectionalIterator_H_
#define _RAREUTiBidirectionalIterator_H_

#import "JreEmulation.h"
#include "java/util/Iterator.h"

@protocol RAREUTiBidirectionalIterator < JavaUtilIterator, NSObject, JavaObject >
- (void)end;
- (void)home;
- (void)mark;
- (id)previous;
- (void)reset;
- (int)setRelativePositionWithInt:(int)percent;
- (int)getRelativePositionWithId:(id)item;
- (int)getSize;
- (BOOL)hasPrevious;
- (BOOL)isFixedSize;
@end

#define ComAppnativaUtilIBidirectionalIterator RAREUTiBidirectionalIterator

#endif // _RAREUTiBidirectionalIterator_H_
