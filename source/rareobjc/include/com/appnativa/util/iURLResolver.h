//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iURLResolver.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUTiURLResolver_H_
#define _RAREUTiURLResolver_H_

@class JavaNetURL;
@class JavaNetURLConnection;

#import "JreEmulation.h"
#include "com/appnativa/util/iFileResolver.h"

@protocol RAREUTiURLResolver < RAREUTiFileResolver, NSObject, JavaObject >
- (JavaNetURL *)getBaseURL;
- (JavaNetURLConnection *)getConnectionWithNSString:(NSString *)file;
- (JavaNetURL *)getURLWithNSString:(NSString *)file;
- (id)getApplicationContext;
@end

#define ComAppnativaUtilIURLResolver RAREUTiURLResolver

#endif // _RAREUTiURLResolver_H_
