//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/UTF8Helper.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUTUTF8Helper_H_
#define _RAREUTUTF8Helper_H_

#import "JreEmulation.h"
#include "com/appnativa/util/GenericCharsetHelper.h"

@interface RAREUTUTF8Helper : RAREUTGenericCharsetHelper {
}

- (id)init;
+ (RAREUTUTF8Helper *)getInstance;
+ (NSString *)utf8StringWithNSString:(NSString *)value;
@end

typedef RAREUTUTF8Helper ComAppnativaUtilUTF8Helper;

#endif // _RAREUTUTF8Helper_H_
