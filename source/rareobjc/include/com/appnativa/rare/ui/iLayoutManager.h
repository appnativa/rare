//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iLayoutManager.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiLayoutManager_H_
#define _RAREiLayoutManager_H_

@protocol RAREiLayoutManager_iLayoutTracker;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"

@protocol RAREiLayoutManager < NSObject, JavaObject >
- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position;
- (void)removeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)removeAll;
- (void)setLayoutTrackerWithRAREiLayoutManager_iLayoutTracker:(id<RAREiLayoutManager_iLayoutTracker>)tracker;
- (id)getConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
@end

#define ComAppnativaRareUiILayoutManager RAREiLayoutManager

@protocol RAREiLayoutManager_iLayoutTracker < NSObject, JavaObject >
- (void)layoutPerformedWithRAREiLayoutManager:(id<RAREiLayoutManager>)lm;
- (void)willPerformLayoutWithRAREiLayoutManager:(id<RAREiLayoutManager>)lm;
@end

#endif // _RAREiLayoutManager_H_