/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.platform;

import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.util.iPreferences;
/*-[
#import "AppleHelper.h"
]-*/

public class NSUserDefaultsPreferences implements iPreferences {
  Object            proxy;
  String            prefix;
  EventListenerList listenerList;
  boolean           isUser;

  public NSUserDefaultsPreferences(String prefix) {
    this(createProxy(), prefix, true);
  }

  public NSUserDefaultsPreferences(Object proxy, String prefix, boolean isUser) {
    this.isUser = isUser;
    this.proxy  = proxy;

    if (!prefix.endsWith(".")) {
      prefix += ".";
    }

    this.prefix = prefix;
  }

  @Override
  public native void flush()
  /*-[
          [((NSUserDefaults*)proxy_) synchronize];
  ]-*/
  ;

  @Override
  public native void put(String key, String value)
  /*-[
        key=[self makeKeyWithNSString: key];
                [((NSUserDefaults*)proxy_) setObject: value forKey: key];
        ]-*/
  ;

  @Override
  public native String get(String key, String def)
  /*-[
                key=[self makeKeyWithNSString: key];
                NSString* s=[((NSUserDefaults*)proxy_) stringForKey: key];
                return s ? s : def;
        ]-*/
  ;

  @Override
  public native void remove(String key)
  /*-[
                key=[self makeKeyWithNSString: key];
                [((NSUserDefaults*)proxy_) removeObjectForKey: key];
        ]-*/
  ;

  @Override
  public void clear() {
    removeNode();
  }

  @Override
  public native void putInt(String key, int value)
  /*-[
                key=[self makeKeyWithNSString: key];
                [((NSUserDefaults*)proxy_) setObject: [NSNumber numberWithInt: value] forKey: key];
        ]-*/
  ;

  @Override
  public native int getInt(String key, int def)
  /*-[
                key=[self makeKeyWithNSString: key];
                NSObject* o=[((NSUserDefaults*)proxy_) objectForKey: key];
                if([o isKindOfClass: [NSNumber class]]) {
                        return ((NSNumber*)o).intValue;
                }
                return def;
        ]-*/
  ;

  @Override
  public native void putLong(String key, long value)    /*-[
                 key=[self makeKeyWithNSString: key];
                 [((NSUserDefaults*)proxy_) setObject: [NSNumber numberWithLongLong: value] forKey: key];
         ]-*/
  ;

  @Override
  public native long getLong(String key, long def)
  /*-[
                key=[self makeKeyWithNSString: key];
                NSObject* o=[((NSUserDefaults*)proxy_) objectForKey: key];
                if([o isKindOfClass: [NSNumber class]]) {
                        return ((NSNumber*)o).longLongValue;
                }
                return def;
        ]-*/
  ;

  @Override
  public native void putBoolean(String key, boolean value)
  /*-[
                key=[self makeKeyWithNSString: key];
                [((NSUserDefaults*)proxy_) setObject: [NSNumber numberWithBool: value] forKey: key];
        ]-*/
  ;

  @Override
  public native boolean getBoolean(String key, boolean def)
  /*-[
                key=[self makeKeyWithNSString: key];
                NSObject* o=[((NSUserDefaults*)proxy_) objectForKey: key];
                if([o isKindOfClass: [NSNumber class]]) {
                        return ((NSNumber*)o).boolValue;
                }
                return def;
        ]-*/
  ;

  @Override
  public native void putFloat(String key, float value)
  /*-[
        key=[self makeKeyWithNSString: key];
                [((NSUserDefaults*)proxy_) setObject: [NSNumber numberWithFloat: value] forKey: key];
        ]-*/
  ;

  @Override
  public native float getFloat(String key, float def)
  /*-[
                key=[self makeKeyWithNSString: key];
                NSObject* o=[((NSUserDefaults*)proxy_) objectForKey: key];
                if([o isKindOfClass: [NSNumber class]]) {
                        return ((NSNumber*)o).floatValue;
                }
                return def;
        ]-*/
  ;

  @Override
  public native void putDouble(String key, double value)
  /*-[
                key=[self makeKeyWithNSString: key];
    [((NSUserDefaults*)proxy_) setObject: [NSNumber numberWithDouble: value] forKey: key];
        ]-*/
  ;

  @Override
  public native double getDouble(String key, double def)
  /*-[
    key=[self makeKeyWithNSString: key];
    NSObject* o=[((NSUserDefaults*)proxy_) objectForKey: key];
    if([o isKindOfClass: [NSNumber class]]) {
        return ((NSNumber*)o).doubleValue;
    }
    return def;
        ]-*/
  ;

  @Override
  public native void putByteArray(String key, byte[] value)
  /*-[
    key=[self makeKeyWithNSString: key];
    if(!value) {
       [((NSUserDefaults*)proxy_) removeObjectForKey: key];
    }
    else {
        [((NSUserDefaults*)proxy_) setObject:[value toNSData] forKey:key];
    }
        ]-*/
  ;

  @Override
  public native byte[] getByteArray(String key, byte[] def)
  /*-[
    key=[self makeKeyWithNSString: key];
    NSObject* o=[((NSUserDefaults*)proxy_) objectForKey: key];
    if([o isKindOfClass: [NSData class]]) {
        NSData* data=(NSData*)o;
        return [IOSByteArray arrayWithBytes:(const char*)data.bytes count:data.length];
    }
    return def;
        ]-*/
  ;

  public native String[] keys()
  /*-[
    NSString *bundleId  = [[NSBundle mainBundle] bundleIdentifier];
    NSDictionary* d=[((NSUserDefaults*)proxy_) persistentDomainForName:bundleId];
    if(!d) {
      return [IOSObjectArray arrayWithLength:0 type:[IOSClass classWithClass:[IOSObjectArray class]]];
    }
    return [AppleHelper toStringArray:[d allKeys]];
        ]-*/
  ;

  public native String[] childrenNames()
  /*-[
    NSString *bundleId  = [[NSBundle mainBundle] bundleIdentifier];
    NSDictionary* d=[((NSUserDefaults*)proxy_) persistentDomainForName:bundleId];
    __block NSDictionary* result= [NSDictionary dictionary];
    [d enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
        NSString* s=(NSString*)key;
        if ([s indexOfString: prefix_]==0) {
            int n=[s lastIndexOf:'.'];
            s=[s substring:0 endIndex:n];
            if(![s isEqualToString:prefix_]) {
                [result setValue:s forKey:s];
            }
        }

    }];
    return [AppleHelper toStringArray:[result allKeys]];
        ]-*/
  ;

  @Override
  public iPreferences getParent() {
    int n = prefix.lastIndexOf('.');

    if (n == -1) {
      return null;
    }

    return new NSUserDefaultsPreferences(prefix.substring(0, n + 1));
  }

  @Override
  public iPreferences getNode(String pathName) {
    return new NSUserDefaultsPreferences(makeKey(pathName));
  }

  @Override
  public native boolean nodeExists(String pathName)
  /*-[
    NSString *bundleId  = [[NSBundle mainBundle] bundleIdentifier];
    NSDictionary* d=[((NSUserDefaults*)proxy_) persistentDomainForName:bundleId];
    __block BOOL result= NO;
    [d enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
        NSString* s=(NSString*)key;
        if ([s indexOfString: prefix_]==0) {
            *stop=YES;
            result=YES;
        }

    }];
    return result;
        ]-*/
  ;

  @Override
  public native void removeNode()
  /*-[
          __block NSUserDefaults* defs=(NSUserDefaults*)proxy_;
    __block NSMutableArray* keys= [NSMutableArray array];
    NSString *bundleId  = [[NSBundle mainBundle] bundleIdentifier];
    NSDictionary* d=[defs persistentDomainForName:bundleId];
    [d enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
        NSString* s=(NSString*)key;
        if ([s indexOfString: prefix_]==0) {
            [keys addObject:s];
        }

    }];
    [keys enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        [defs removeObjectForKey:(NSString*)obj];
    }];
]-*/
  ;

  @Override
  public String name() {
    return prefix.substring(0, prefix.length() - 1);
  }

  @Override
  public String absolutePath() {
    return name();
  }

  @Override
  public boolean isUserNode() {
    return isUser;
  }

  @Override
  public void sync() {
    flush();
  }

  private String makeKey(String key) {
    if (prefix == null) {
      return key;
    }

    return prefix + key;
  }

  private static native Object createProxy()    /*-[
              return [NSUserDefaults standardUserDefaults];
      ]-*/
  ;
}
