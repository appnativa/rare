//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/iFocusListener.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiFocusListener_H_
#define _RAREiFocusListener_H_

#import "JreEmulation.h"

@protocol RAREiFocusListener < NSObject, JavaObject >
- (void)focusChangedWithId:(id)view
               withBoolean:(BOOL)hasFocus
                    withId:(id)oppositeView;
@end

#define ComAppnativaRareUiListenerIFocusListener RAREiFocusListener

#endif // _RAREiFocusListener_H_