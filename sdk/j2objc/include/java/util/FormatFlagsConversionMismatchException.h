//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/FormatFlagsConversionMismatchException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilFormatFlagsConversionMismatchException_H_
#define _JavaUtilFormatFlagsConversionMismatchException_H_

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/IllegalFormatException.h"

#define JavaUtilFormatFlagsConversionMismatchException_serialVersionUID 19120414

@interface JavaUtilFormatFlagsConversionMismatchException : JavaUtilIllegalFormatException < JavaIoSerializable > {
 @public
  NSString *f_;
  unichar c_;
}

- (id)initWithNSString:(NSString *)f
              withChar:(unichar)c;
- (NSString *)getFlags;
- (unichar)getConversion;
- (NSString *)getMessage;
- (void)copyAllFieldsTo:(JavaUtilFormatFlagsConversionMismatchException *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilFormatFlagsConversionMismatchException, f_, NSString *)

#endif // _JavaUtilFormatFlagsConversionMismatchException_H_