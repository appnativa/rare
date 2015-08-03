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

import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.ActionLink.iErrorHandler;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.util.iCancelable;
import com.appnativa.util.iFilter;
import com.appnativa.util.iStringConverter;

import java.util.Comparator;
import java.util.EventObject;

/*-[
 #import "RAREJSEngine.h"
 ]-*/
public class GenericInterfaceProxy extends aInterfaceProxy
        implements iFunctionCallback, Runnable, iWorkerTask, iCancelable, iFilter, iErrorHandler, iStringConverter,
                   Comparator, iActionListener, iChangeListener, iAnimatorValueListener {
  public GenericInterfaceProxy() {}

  @Override
  public native void actionPerformed(ActionEvent e)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId:@[e] ];
  ]-*/
  ;

  @Override
  public native void cancel(boolean canInterrupt)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId: @[[NSNumber numberWithBool:canInterrupt]] ];
  ]-*/
  ;

  @Override
  public native int compare(Object o1, Object o2)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    if(!o1) {
     o1=[NSNull null];
    }
    if(!o2) {
     o2=[NSNull null];
    }
    NSObject* o=[self spar_callFunctionExWithId:fn withId:@[o1, o2] ];
    if([o isKindOfClass:[NSNumber class]]) {
      return [((NSNumber *)o) intValue];
    }
    return 0;
  ]-*/
  ;

  @Override
  public native Object compute()
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    return [self spar_callFunctionExWithId:fn withId:nil ];
  ]-*/
  ;

  @Override
  public native void finish(Object result)
  /*-[
   if(!result) {
     result=[NSNull null];
   }
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId:@[result] ];
  ]-*/
  ;

  @Override
  public native void finished(boolean canceled, Object returnValue)
  /*-[
   if(!returnValue) {
     returnValue=[NSNull null];
   }
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId: @[[NSNumber numberWithBool:canceled], returnValue] ];
  ]-*/
  ;

  @Override
  public native Action handleError(ActionLink link, Exception ex, iURLConnection conn)
  /*-[
   if(!ex) {
     ex=[NSNull null];
   }
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    return (RAREActionLink_iErrorHandler_ActionEnum*)[self spar_callFunctionExWithId:fn withId: @[link, ex,conn] ];
  ]-*/
  ;

  @Override
  public native boolean passes(Object value, iStringConverter converter)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    if(!converter) {
     converter=(id<RAREUTiStringConverter>)[NSNull null];
    }
   if(!value) {
     value=[NSNull null];
   }
    NSObject* o=[self spar_callFunctionExWithId:fn withId: @[value, converter] ];
    if([o isKindOfClass:[NSNumber class]]) {
      return [((NSNumber *)o) boolValue];
    }
    return NO;
  ]-*/
  ;

  @Override
  public native void run()
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId:nil ];
  ]-*/
  ;

  @Override
  public native void stateChanged(EventObject e)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId:@[e] ];
  ]-*/
  ;

  @Override
  public native String toString(Object item)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    return (NSString*)[self spar_callFunctionExWithId:fn withId: item ];
  ]-*/
  ;

  @Override
  public native iURLConnection getConnectionChange(ActionLink link, Exception ex, iURLConnection conn)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    if(!ex) {
     ex=(JavaLangException *)[NSNull null];
    }
    return (id<RAREiURLConnection>)[self spar_callFunctionExWithId:fn withId: @[link, ex,conn] ];
  ]-*/
  ;

  @Override
  public native boolean isCanceled()
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    NSObject* o=[self spar_callFunctionExWithId:fn withId:nil ];
    if([o isKindOfClass:[NSNumber class]]) {
      return [((NSNumber *)o) boolValue];
    }
    return NO;
  ]-*/
  ;

  @Override
  public native boolean isDone()
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    NSObject* o=[self spar_callFunctionExWithId:fn withId:nil ];
    if([o isKindOfClass:[NSNumber class]]) {
      return [((NSNumber *)o) boolValue];
    }
    return NO;
  ]-*/
  ;

  @Override
  public native void valueChanged(iPlatformAnimator animator, float value)
  /*-[
    NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
    [self spar_callFunctionExWithId:fn withId: @[animator,[NSNumber numberWithFloat:value]]];
  ]-*/
  ;

  @Override
  public native Exception getExceptionChange(ActionLink link, Exception ex)
  /*-[
  NSObject* fn=[self spar_getFunctionObjectWithNSString:NSStringFromSelector(_cmd)] ;
  if(!ex) {
   ex=(JavaLangException *)[NSNull null];
  }
  return (JavaLangException *)[self spar_callFunctionExWithId:fn withId: @[link, ex] ];
]-*/
  ;
}
