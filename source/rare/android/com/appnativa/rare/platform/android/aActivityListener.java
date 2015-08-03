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

package com.appnativa.rare.platform.android;

import android.app.Dialog;

import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;

import android.os.Bundle;

import com.appnativa.rare.iPlatformAppContext;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aActivityListener implements LocationListener {
  public boolean onBackPressed(iPlatformAppContext app, iActivity a, Dialog dialog) {
    return false;
  }

  public boolean onHomeActionPressed(iPlatformAppContext app, iActivity a, Dialog dialog) {
    return false;
  }

  public boolean onActivityResult(iPlatformAppContext app, iActivity a, int requestCode, int resultCode, Intent data) {
    return false;
  }

  public void onLocationChanged(Location location) {}

  public void onProviderDisabled(String provider) {}

  public void onProviderEnabled(String provider) {}

  public void onStatusChanged(String provider, int status, Bundle extras) {}

  public void handleNewIntent(Intent intent) {}
}
