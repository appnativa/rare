//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/net/NetHelper.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARENetHelper_H_
#define _RARENetHelper_H_

@protocol JavaUtilList;

#import "JreEmulation.h"
#include "com/appnativa/rare/net/aNetHelper.h"

@interface RARENetHelper : RAREaNetHelper {
}

+ (id<JavaUtilList>)getCookieList;
+ (NSString *)getCookies;
- (id)init;
@end

typedef RARENetHelper ComAppnativaRareNetNetHelper;

#endif // _RARENetHelper_H_
