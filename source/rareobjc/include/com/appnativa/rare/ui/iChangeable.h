//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iChangeable.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiChangeable_H_
#define _RAREiChangeable_H_

@protocol RAREiChangeListener;

#import "JreEmulation.h"

@protocol RAREiChangeable < NSObject, JavaObject >
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
@end

#define ComAppnativaRareUiIChangeable RAREiChangeable

#endif // _RAREiChangeable_H_
