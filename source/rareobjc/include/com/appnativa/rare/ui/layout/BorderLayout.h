//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/layout/BorderLayout.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARELOBorderLayout_H_
#define _RARELOBorderLayout_H_

@class IOSObjectArray;
@class RARELayoutMap;

#import "JreEmulation.h"
#include "com/appnativa/jgoodies/forms/layout/FormLayout.h"

@interface RARELOBorderLayout : RAREFormLayout {
}

- (id)init;
- (id)initWithRAREColumnSpecArray:(IOSObjectArray *)colSpecs;
- (id)initWithNSString:(NSString *)encodedColumnSpecs;
- (id)initWithRAREColumnSpecArray:(IOSObjectArray *)colSpecs
             withRARERowSpecArray:(IOSObjectArray *)rowSpecs;
- (id)initWithNSString:(NSString *)encodedColumnSpecs
     withRARELayoutMap:(RARELayoutMap *)layoutMap;
- (id)initWithNSString:(NSString *)encodedColumnSpecs
          withNSString:(NSString *)encodedRowSpecs;
- (id)initWithNSString:(NSString *)encodedColumnSpecs
          withNSString:(NSString *)encodedRowSpecs
     withRARELayoutMap:(RARELayoutMap *)layoutMap;
@end

typedef RARELOBorderLayout ComAppnativaRareUiLayoutBorderLayout;

#endif // _RARELOBorderLayout_H_
