//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/iPopupMenuListener.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiPopupMenuListener_H_
#define _RAREiPopupMenuListener_H_

@class RAREExpansionEvent;

#import "JreEmulation.h"
#include "java/util/EventListener.h"

@protocol RAREiPopupMenuListener < JavaUtilEventListener, NSObject, JavaObject >
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
@end

#define ComAppnativaRareUiEventIPopupMenuListener RAREiPopupMenuListener

#endif // _RAREiPopupMenuListener_H_
