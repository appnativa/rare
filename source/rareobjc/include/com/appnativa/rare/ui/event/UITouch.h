//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/event/UITouch.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUITouch_H_
#define _RAREUITouch_H_

#import "JreEmulation.h"

@interface RAREUITouch : NSObject {
 @public
  float sx_;
  float sy_;
  long long int time_;
  float wx_;
  float wy_;
  float x_;
  float y_;
  int clickCount_;
}

- (id)init;
- (void)copyAllFieldsTo:(RAREUITouch *)other;
@end

typedef RAREUITouch ComAppnativaRareUiEventUITouch;

#endif // _RAREUITouch_H_
