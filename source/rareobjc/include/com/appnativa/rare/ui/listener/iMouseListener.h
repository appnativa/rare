//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/iMouseListener.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiMouseListener_H_
#define _RAREiMouseListener_H_

@class RAREMouseEvent;

#import "JreEmulation.h"

@protocol RAREiMouseListener < NSObject, JavaObject >
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (BOOL)wantsLongPress;
@end

#define ComAppnativaRareUiListenerIMouseListener RAREiMouseListener

#endif // _RAREiMouseListener_H_
