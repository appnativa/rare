//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/io/FileReader.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoFileReader_H_
#define _JavaIoFileReader_H_

@class JavaIoFile;
@class JavaIoFileDescriptor;

#import "JreEmulation.h"
#include "java/io/InputStreamReader.h"

@interface JavaIoFileReader : JavaIoInputStreamReader {
}

- (id)initWithJavaIoFile:(JavaIoFile *)file;
- (id)initWithJavaIoFileDescriptor:(JavaIoFileDescriptor *)fd;
- (id)initWithNSString:(NSString *)filename;
@end

#endif // _JavaIoFileReader_H_