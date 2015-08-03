/*
 * @(#)WeakReference.java   2013-02-19
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.lang.ref;
/*-[
#import "AppleHelper.h"
]-*/
/**
 *
 * @author decoteaud
 */
public class WeakReference<E> {
  Object proxy;

  public WeakReference(E value) {
    initialize(value);
  }

  public native E get() /*-[
    if(proxy_==nil) {
      return nil;
    }
    return [((SPARWeakReference*)proxy_) getValue];
  ]-*/;
  
  public native void clear() /*-[
    if(proxy_!=nil) {
      [((SPARWeakReference*)proxy_) clear];
      proxy_=nil;
     }
  ]-*/;
  
  private native void initialize(E value) /*-[
    proxy_=[[SPARWeakReference alloc] initWithNSObject: value];
  ]-*/;

}
