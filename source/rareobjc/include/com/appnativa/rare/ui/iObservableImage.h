//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iObservableImage.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiObservableImage_H_
#define _RAREiObservableImage_H_

@protocol RAREiImageObserver;

#import "JreEmulation.h"

@protocol RAREiObservableImage < NSObject, JavaObject >
- (BOOL)isImageLoadedWithRAREiImageObserver:(id<RAREiImageObserver>)imageObserver;
@end

#define ComAppnativaRareUiIObservableImage RAREiObservableImage

#endif // _RAREiObservableImage_H_