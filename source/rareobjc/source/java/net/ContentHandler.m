//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/porting/src/java/net/ContentHandler.java
//
//  Created by decoteaud on 4/16/14.
//

#include "IOSClass.h"
#include "java/io/IOException.h"
#include "java/net/ContentHandler.h"
#include "java/net/URLConnection.h"

@implementation JavaNetContentHandler

- (id)getContentWithJavaNetURLConnection:(JavaNetURLConnection *)u {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getContentWithJavaNetURLConnection:", NULL, "LNSObject", 0x401, "JavaIoIOException" },
  };
  static J2ObjcClassInfo _JavaNetContentHandler = { "ContentHandler", "java.net", NULL, 0x401, 1, methods, 0, NULL, 0, NULL};
  return &_JavaNetContentHandler;
}

@end