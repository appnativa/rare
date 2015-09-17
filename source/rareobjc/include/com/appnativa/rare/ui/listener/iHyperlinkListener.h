//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/iHyperlinkListener.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiHyperlinkListener_H_
#define _RAREiHyperlinkListener_H_

@class RAREMouseEvent;

#import "JreEmulation.h"

@protocol RAREiHyperlinkListener < NSObject, JavaObject >
- (void)linkClickedWithId:(id)source
                   withId:(id)item
             withNSString:(NSString *)href
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)linkEnteredWithId:(id)source
                   withId:(id)item
             withNSString:(NSString *)href
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)linkExitedWithId:(id)source
                  withId:(id)item
            withNSString:(NSString *)href
      withRAREMouseEvent:(RAREMouseEvent *)e;
@end

#define ComAppnativaRareUiListenerIHyperlinkListener RAREiHyperlinkListener

#endif // _RAREiHyperlinkListener_H_
