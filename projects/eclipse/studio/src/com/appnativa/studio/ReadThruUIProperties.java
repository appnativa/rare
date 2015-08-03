/*
 * @(#)ReadThruUIDefaults.java   2009-06-17
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.util.HashSet;
import java.util.Set;

import javax.swing.UIDefaults;

import com.appnativa.rare.ui.UIProperties;

/**
 * A UIDefaults call that will read thru to a secondary UIDefaults
 *
 * @author Don DeCoteau
 */
public class ReadThruUIProperties extends UIProperties {
  private UIDefaults secondary;

  public ReadThruUIProperties(UIDefaults secondary) {
    super(new UIDefaults());
    this.secondary = secondary;
  }

  public Set entrySet() {
    if (secondary == null) {
      return super.entrySet();
    }

    Set set = new HashSet();

    set.addAll(super.entrySet());
    set.addAll(secondary.entrySet());

    return set;
  }

  public Set primaryEntrySet() {
    return super.entrySet();
  }

  /**
   * @param secondary the secondary to set
   */
  public void setSecondary(UIDefaults secondary) {
    this.secondary = secondary;
  }

//  public Set keySet() {
//    if (secondary == null) {
//      return super.keySet();
//    }
//
//    Set< Object> set = new HashSet<Object>();
//
//    set.addAll(super.keySet());
//    set.addAll(secondary.keySet());
//
//    return set;
//  }
//
//  public Set primaryKeySet() {
//    return super.keySet();
//  }
//  public Collection primaryValues() {
//    return super.values();
//  }
//  public Collection values() {
//    if (secondary == null) {
//      return super.values();
//    }
//
//    Set< Object> set = new HashSet<Object>();
//
//    set.addAll(super.values());
//    set.addAll(secondary.values());
//
//    return set;
//  }
  public Object get(String key) {
    Object o = super.get(key);

    return ((o == null) && (secondary != null))
           ? secondary.get(key)
           : o;
  }

  /**
   * @return the secondary
   */
  public UIDefaults getSecondary() {
    return secondary;
  }
}
