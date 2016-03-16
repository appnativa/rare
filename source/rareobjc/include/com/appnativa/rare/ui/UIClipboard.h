//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIClipboard.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUIClipboard_H_
#define _RAREUIClipboard_H_

@class RAREUIImage;
@protocol JavaUtilList;
@protocol JavaUtilMap;

#import "JreEmulation.h"

@interface RAREUIClipboard : NSObject {
}

- (id)init;
+ (void)clear;
+ (BOOL)setContentWithJavaUtilMap:(id<JavaUtilMap>)map;
+ (BOOL)hasUrl;
+ (BOOL)hasString;
+ (BOOL)hasRtf;
+ (BOOL)hasImage;
+ (BOOL)hasHtml;
+ (BOOL)hasFiles;
+ (NSString *)getUrl;
+ (NSString *)getString;
+ (NSString *)getRtf;
+ (RAREUIImage *)getImage;
+ (NSString *)getHtml;
+ (id<JavaUtilList>)getFiles;
+ (id<JavaUtilList>)getAvailableFlavors;
@end

typedef RAREUIClipboard ComAppnativaRareUiUIClipboard;

#endif // _RAREUIClipboard_H_
