//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UIBevelBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/border/UIBevelBorder.h"

@implementation RAREUIBevelBorder

- (id)initWithInt:(int)bevelType {
  return [super initWithInt:bevelType];
}

- (id)initWithInt:(int)bevelType
      withBoolean:(BOOL)fourcolor {
  return [super initWithInt:bevelType withBoolean:fourcolor];
}

- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlight
  withRAREUIColor:(RAREUIColor *)shadow
      withBoolean:(BOOL)fourColor {
  return [super initWithInt:bevelType withRAREUIColor:highlight withRAREUIColor:shadow withBoolean:fourColor];
}

- (id)initWithInt:(int)bevelType
  withRAREUIColor:(RAREUIColor *)highlightOuterColor
  withRAREUIColor:(RAREUIColor *)highlightInnerColor
  withRAREUIColor:(RAREUIColor *)shadowOuterColor
  withRAREUIColor:(RAREUIColor *)shadowInnerColor {
  return [super initWithInt:bevelType withRAREUIColor:highlightOuterColor withRAREUIColor:highlightInnerColor withRAREUIColor:shadowOuterColor withRAREUIColor:shadowInnerColor];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREUIBevelBorder = { "UIBevelBorder", "com.appnativa.rare.ui.border", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREUIBevelBorder;
}

@end
