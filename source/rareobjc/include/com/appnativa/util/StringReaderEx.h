//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/StringReaderEx.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTStringReaderEx_H_
#define _RAREUTStringReaderEx_H_

#import "JreEmulation.h"
#include "java/io/StringReader.h"

@interface RAREUTStringReaderEx : JavaIoStringReader {
 @public
  NSString *theString_;
}

- (id)initWithNSString:(NSString *)s;
- (NSString *)getString;
- (void)copyAllFieldsTo:(RAREUTStringReaderEx *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTStringReaderEx, theString_, NSString *)

typedef RAREUTStringReaderEx ComAppnativaUtilStringReaderEx;

#endif // _RAREUTStringReaderEx_H_
