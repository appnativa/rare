//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/io/ObjectStreamException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoObjectStreamException_H_
#define _JavaIoObjectStreamException_H_

#import "JreEmulation.h"
#include "java/io/IOException.h"

#define JavaIoObjectStreamException_serialVersionUID 7260898174833392607

@interface JavaIoObjectStreamException : JavaIoIOException {
}

- (id)init;
- (id)initWithNSString:(NSString *)detailMessage;
@end

#endif // _JavaIoObjectStreamException_H_