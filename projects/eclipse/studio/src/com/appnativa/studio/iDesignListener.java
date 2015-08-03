/*
 * @(#)iDesignListener.java   2008-05-14
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.util.EventListener;

/**
 *
 * @author decoteaud
 */
public interface iDesignListener extends EventListener {
  void somethingHappened(DesignEvent event);
}
