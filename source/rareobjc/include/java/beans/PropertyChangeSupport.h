//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/porting/src/java/beans/PropertyChangeSupport.java
//
//  Created by decoteaud on 4/16/14.
//

#ifndef _JavaBeansPropertyChangeSupport_H_
#define _JavaBeansPropertyChangeSupport_H_

@class IOSObjectArray;
@class JavaBeansPropertyChangeEvent;
@protocol JavaBeansPropertyChangeListener;
@protocol JavaUtilEventListener;
@protocol JavaUtilList;

#import "JreEmulation.h"

@interface JavaBeansPropertyChangeSupport : NSObject {
 @public
  id<JavaUtilList> listeners_;
  __weak id sourceBean_;
}

- (id)initWithId:(id)sourceBean;
- (void)addPropertyChangeListenerWithJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)addPropertyChangeListenerWithNSString:(NSString *)propertyName
          withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)fireIndexedPropertyChangeWithNSString:(NSString *)propertyName
                                      withInt:(int)index
                                  withBoolean:(BOOL)oldValue
                                  withBoolean:(BOOL)newValue;
- (void)fireIndexedPropertyChangeWithNSString:(NSString *)propertyName
                                      withInt:(int)index
                                      withInt:(int)oldValue
                                      withInt:(int)newValue;
- (void)fireIndexedPropertyChangeWithNSString:(NSString *)propertyName
                                      withInt:(int)index
                                       withId:(id)oldValue
                                       withId:(id)newValue;
- (void)firePropertyChangeWithJavaBeansPropertyChangeEvent:(JavaBeansPropertyChangeEvent *)event;
- (void)firePropertyChangeWithNSString:(NSString *)propertyName
                           withBoolean:(BOOL)oldValue
                           withBoolean:(BOOL)newValue;
- (void)firePropertyChangeWithNSString:(NSString *)propertyName
                               withInt:(int)oldValue
                               withInt:(int)newValue;
- (void)firePropertyChangeWithNSString:(NSString *)propertyName
                                withId:(id)oldValue
                                withId:(id)newValue;
- (void)removePropertyChangeListenerWithJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (void)removePropertyChangeListenerWithNSString:(NSString *)propertyName
             withJavaBeansPropertyChangeListener:(id<JavaBeansPropertyChangeListener>)listener;
- (IOSObjectArray *)getPropertyChangeListeners;
- (IOSObjectArray *)getPropertyChangeListenersWithNSString:(NSString *)propertyName;
- (BOOL)hasListenersWithNSString:(NSString *)propertyName;
- (BOOL)equalWithId:(id)a
             withId:(id)b;
- (BOOL)equalsWithNSString:(NSString *)aName
 withJavaUtilEventListener:(id<JavaUtilEventListener>)a
 withJavaUtilEventListener:(id<JavaUtilEventListener>)b;
- (void)copyAllFieldsTo:(JavaBeansPropertyChangeSupport *)other;
@end

J2OBJC_FIELD_SETTER(JavaBeansPropertyChangeSupport, listeners_, id<JavaUtilList>)

#endif // _JavaBeansPropertyChangeSupport_H_
