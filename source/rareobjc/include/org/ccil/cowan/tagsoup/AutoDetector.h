//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/org/ccil/cowan/tagsoup/AutoDetector.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREAutoDetector_H_
#define _RAREAutoDetector_H_

@class JavaIoInputStream;
@class JavaIoReader;

#import "JreEmulation.h"

@protocol RAREAutoDetector < NSObject, JavaObject >
- (JavaIoReader *)autoDetectingReaderWithJavaIoInputStream:(JavaIoInputStream *)i;
@end

#define OrgCcilCowanTagsoupAutoDetector RAREAutoDetector

#endif // _RAREAutoDetector_H_
