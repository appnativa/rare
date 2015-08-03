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

package com.appnativa.rare.spot;

import com.appnativa.spot.*;

//USER_IMPORTS_AND_COMMENTS_MARK{}
//GENERATED_COMMENT{}

/**
 * This class represents the configuration information for a
 * viewer that splits its section of the screen into multiple
 * resizable regions.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class SplitPane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Layout: the orientation of the split */
  public CSplitOrientation splitOrientation = new CSplitOrientation(null, null, CSplitOrientation.left_to_right,
                                                "left_to_right", true);
//GENERATED_COMMENT{}

  /** Behavior: specifies whether the split pane should provide a collapse/expand widget for panes */
  public SPOTBoolean oneTouchExpandable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: specifies whether the split pane should automatically adjust its proportions when a widget is bigger than it alloted space */
  public SPOTBoolean autoAdjustProportions = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the resizing gripper is shown */
  public SPOTBoolean showGripper = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: the divider size (zero hides the divider) */
  public SPOTInteger dividerSize = new SPOTInteger(null, 0, 100, true);
//GENERATED_COMMENT{}

  /** Design: whether the regions a continuously laid out during resize operations */
  public SPOTBoolean continuousLayout = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the pane should act as a form viewer (if false widgets will be registered with the next higher up form viewer) */
  public SPOTBoolean actAsFormViewer = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Hidden: the split proportions the set should contain on less entry than there are viewers (defaults to an even split). For example a splitProportion of 0.4 would provide a 40/60 split */
  protected SPOTSet splitProportions = null;
//GENERATED_COMMENT{}

  /** Design: the set of regions */
  public SPOTSet regions = new SPOTSet("region", new Region(), -1, -1, false);
//GENERATED_COMMENT{}

  /** Behavior: name of the animator to use to animate transitions */
  public SPOTPrintableString transitionAnimator = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the viewer is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>SplitPane</code> object.
   */
  public SplitPane() {
    this(true);
  }

  /**
   * Creates a new <code>SplitPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public SplitPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>SplitPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected SplitPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 11;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("splitOrientation", splitOrientation);
    spot_addElement("oneTouchExpandable", oneTouchExpandable);
    spot_addElement("autoAdjustProportions", autoAdjustProportions);
    spot_addElement("showGripper", showGripper);
    spot_addElement("dividerSize", dividerSize);
    spot_addElement("continuousLayout", continuousLayout);
    spot_addElement("actAsFormViewer", actAsFormViewer);
    spot_addElement("splitProportions", splitProportions);
    spot_addElement("regions", regions);
    spot_addElement("transitionAnimator", transitionAnimator);
    transitionAnimator.spot_defineAttribute("duration", null);
    transitionAnimator.spot_defineAttribute("direction", null);
    transitionAnimator.spot_defineAttribute("acceleration", null);
    transitionAnimator.spot_defineAttribute("deceleration", null);
    transitionAnimator.spot_defineAttribute("horizontal", null);
    transitionAnimator.spot_defineAttribute("fadeIn", null);
    transitionAnimator.spot_defineAttribute("fadeOut", null);
    transitionAnimator.spot_defineAttribute("diagonal", null);
    transitionAnimator.spot_defineAttribute("diagonalAnchor", null);
    transitionAnimator.spot_defineAttribute("autoOrient", null);
    transitionAnimator.spot_defineAttribute("customProperties", null);
    disableBehavior.spot_setDefaultValue(0, "disable_container");
    disableBehavior.spot_setOptional(false);
    spot_addElement("scrollPane", scrollPane);
  }

  /**
   * Gets the splitProportions element
   *
   * @return the splitProportions element or null if a reference was never created
   */
  public SPOTSet getSplitProportions() {
    return splitProportions;
  }

  /**
   * Gets the reference to the splitProportions element
   * A reference is created if necessary
   *
   * @return the reference to the splitProportions element
   */
  public SPOTSet getSplitProportionsReference() {
    if (splitProportions == null) {
      splitProportions = new SPOTSet("splitProportion", new SPOTReal(null, "0", "1", false), -1, -1, true);
      super.spot_setReference("splitProportions", splitProportions);
    }

    return splitProportions;
  }

  /**
   * Sets the reference to the splitProportions element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSplitProportions(iSPOTElement reference) throws ClassCastException {
    splitProportions = (SPOTSet) reference;
    spot_setReference("splitProportions", reference);
  }

  /**
   * Gets the scrollPane element
   *
   * @return the scrollPane element or null if a reference was never created
   */
  public ScrollPane getScrollPane() {
    return scrollPane;
  }

  /**
   * Gets the reference to the scrollPane element
   * A reference is created if necessary
   *
   * @return the reference to the scrollPane element
   */
  public ScrollPane getScrollPaneReference() {
    if (scrollPane == null) {
      scrollPane = new ScrollPane(true);
      super.spot_setReference("scrollPane", scrollPane);
    }

    return scrollPane;
  }

  /**
   * Sets the reference to the scrollPane element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setScrollPane(iSPOTElement reference) throws ClassCastException {
    scrollPane = (ScrollPane) reference;
    spot_setReference("scrollPane", reference);
  }

  //}GENERATED_METHODS
  public void setSplitProportions(double... props) {
    SPOTSet set = getSplitProportionsReference();

    set.clear();

    final int len = (props == null)
                    ? 0
                    : props.length;

    for (int i = 0; i < len; i++) {
      set.add(props[i]);
    }
  }

  /**
   * Finds an returns the named widget (does not recurse)
   *
   * @param name the name of the widget to find
   * @param useNameMap true to use a name map to improve furture search performance; false otherwise
   *
   * @return the named widget or null
   */
  @Override
  public Widget findWidget(String name, boolean useNameMap) {
    final int len = regions.size();

    for (int i = 0; i < len; i++) {
      Region       r = (Region) regions.get(i);
      iSPOTElement v = r.viewer.getValue();

      if (v instanceof GroupBox) {
        Widget w = ((GroupBox) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (v instanceof GridPane) {
        Widget w = ((GridPane) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (v instanceof SplitPane) {
        Widget w = ((SplitPane) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (v instanceof StackPane) {
        Widget w = ((StackPane) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      }
    }

    return null;
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>SplitPane.splitOrientation</code> ENUMERATED object
   */
  public static class CSplitOrientation extends SPOTEnumerated {
    public final static int top_to_bottom = 0;
    public final static int left_to_right = 1;

    /**
     * Creates a new <code>CSplitOrientation</code> object
     */
    public CSplitOrientation() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CSplitOrientation</code> object
     *
     * @param val the value
     */
    public CSplitOrientation(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>splitOrientation</code> object
     * the <code>SplitPane.splitOrientation</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CSplitOrientation(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "top_to_bottom(0), " + "left_to_right(1) }";
    }

    private final static int    _nchoices[] = { 0, 1 };
    private final static String _schoices[] = { "top_to_bottom", "left_to_right" };
  }
  //}GENERATED_INNER_CLASSES
}