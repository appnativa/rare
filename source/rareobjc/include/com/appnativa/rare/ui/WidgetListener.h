//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/WidgetListener.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREWidgetListener_H_
#define _RAREWidgetListener_H_

@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;
@protocol RAREiScriptHandler;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"

@interface RAREWidgetListener : RAREaWidgetListener {
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget
          withJavaUtilMap:(id<JavaUtilMap>)map
   withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (id<RAREiPlatformComponent>)getSourceWithId:(id)view;
@end

typedef RAREWidgetListener ComAppnativaRareUiWidgetListener;

#endif // _RAREWidgetListener_H_
