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

import com.appnativa.rare.Platform;
import com.appnativa.rare.scripting.ScriptManager;

/*-[
#import "RAREJSEngine.h"
]-*/
public class aInterfaceProxy implements iInterfaceProxy {
  protected Object proxy;

  public aInterfaceProxy() {
    proxy = createProxy();
  }

  @Override
  public native void spar_addMethod(String signature, Object sparJSFunctionObject)
  /*-[
          [((NSMutableDictionary*)proxy_) setValue: sparJSFunctionObject forKey: signature];
  ]-*/
  ;

  public native Object spar_getFunctionObject(String signature)
  /*-[
          return [((NSMutableDictionary*)proxy_) objectForKey: signature];
  ]-*/
  ;

  public Object spar_callFunction(Object sparJSFunction, Object... args) {
    return ((ScriptManager) Platform.getAppContext().getWindowManager().getScriptHandler())
      .InvokeNativeScriptFunctionObject(sparJSFunction, args);
  }

  public Object spar_callFunctionEx(Object sparJSFunction, Object nsArray) {
    return ((ScriptManager) Platform.getAppContext().getWindowManager().getScriptHandler())
      .InvokeNativeScriptFunctionObjectEx(sparJSFunction, nsArray);
  }

  static native Object createProxy()
  /*-[
return [NSMutableDictionary dictionaryWithCapacity: 3];
  ]-*/
  ;
}
