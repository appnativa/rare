/*
 * @(#)iSelectionListener.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import org.eclipse.jface.viewers.ISelection;

public interface iSelectionListener {
  void selectionChanged(ISelection selection);
}
