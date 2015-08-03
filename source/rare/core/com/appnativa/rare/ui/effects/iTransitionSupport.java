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

package com.appnativa.rare.ui.effects;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;

public interface iTransitionSupport {

  /**
   * The type of transition. This determines how
   * the components are laid out during the transition
   */
  public static enum TransitionType {

    /** component start of side by side horizontally (out on the left) */
    HORIZONTAL,

    /** component start of side by side vertically (out at the top) */
    VERTICAL,

    /** components are stacked (out on top) */
    STACK
  }

  /**
   * Sets whether the out going component should be faded out
   * @param fade true if it should; false otherwise
   */
  void setFadeOut(boolean fade);

  /**
   * Sets whether the in comming component should be faded in
   * @param fade true if it should; false otherwise
   */
  void setFadeIn(boolean fade);

  /**
   * Allows to animator to setup for the transition
   * @param inComponent the incomming component
   * @return the optional alternate component to use and the incomming component.
   */
  iPlatformComponent setIncommingComponent(iPlatformComponent inComponent);

  /**
   * Allows to animator to setup for the transition
   * @param outComponent the outgoing component
   * @param outImage the image of the out going component
   */
  void setOutgoingComponent(iPlatformComponent outComponent, UIImage outImage);

  /**
   * Sets the type of transition
   * @param type the type of transition
   */
  void setTransitionType(TransitionType type);

  /**
   * Gets the type of transition
   * @return the type of transition
   */
  TransitionType getTransitionType();
}
