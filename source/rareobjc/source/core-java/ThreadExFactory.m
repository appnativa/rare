//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-java/com/appnativa/rare/ThreadExFactory.java
//
//  Created by decoteaud on 9/15/14.
//

#include "com/appnativa/rare/ThreadEx.h"
#include "com/appnativa/rare/ThreadExFactory.h"
#include "java/lang/Runnable.h"
#include "java/lang/Thread.h"

@implementation RAREThreadExFactory

- (JavaLangThread *)newThreadWithJavaLangRunnable:(id<JavaLangRunnable>)r {
  return [[RAREThreadEx alloc] initWithJavaLangRunnable:r];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "newThreadWithJavaLangRunnable:", NULL, "LJavaLangThread", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREThreadExFactory = { "ThreadExFactory", "com.appnativa.rare", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREThreadExFactory;
}

@end