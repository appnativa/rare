//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UIBevelBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUIBevelBorder_H_
#define _RAREUIBevelBorder_H_

@class RAREUIColor;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIBevelBorder.h"

@interface RAREUIBevelBorder : RAREaUIBevelBorder {
}

- (id)initWithInt:(int)bevelType;
- (id)initWithInt:(int)bevelType
      withBoolean:(BOOL)fourcolor;
- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlight
  withRAREUIColor:(RAREUIColor *)shadow
      withBoolean:(BOOL)fourColor;
- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlightOuterColor
  withRAREUIColor:(RAREUIColor *)highlightInnerColor
  withRAREUIColor:(RAREUIColor *)shadowOuterColor
  withRAREUIColor:(RAREUIColor *)shadowInnerColor;
@end

typedef RAREUIBevelBorder ComAppnativaRareUiBorderUIBevelBorder;

#endif // _RAREUIBevelBorder_H_