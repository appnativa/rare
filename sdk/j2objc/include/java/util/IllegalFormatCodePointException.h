//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/IllegalFormatCodePointException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilIllegalFormatCodePointException_H_
#define _JavaUtilIllegalFormatCodePointException_H_

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/IllegalFormatException.h"

#define JavaUtilIllegalFormatCodePointException_serialVersionUID 19080630

@interface JavaUtilIllegalFormatCodePointException : JavaUtilIllegalFormatException < JavaIoSerializable > {
 @public
  int c_;
}

- (id)initWithInt:(int)c;
- (int)getCodePoint;
- (NSString *)getMessage;
- (void)copyAllFieldsTo:(JavaUtilIllegalFormatCodePointException *)other;
@end

#endif // _JavaUtilIllegalFormatCodePointException_H_
