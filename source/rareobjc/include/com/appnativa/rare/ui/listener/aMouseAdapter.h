//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/aMouseAdapter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaMouseAdapter_H_
#define _RAREaMouseAdapter_H_

@class RAREMouseEvent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"

@interface RAREaMouseAdapter : NSObject < RAREiMouseListener > {
}

- (id)init;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (BOOL)wantsLongPress;
@end

typedef RAREaMouseAdapter ComAppnativaRareUiListenerAMouseAdapter;

#endif // _RAREaMouseAdapter_H_
