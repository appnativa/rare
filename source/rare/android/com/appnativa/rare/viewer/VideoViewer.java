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

package com.appnativa.rare.viewer;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

import android.net.Uri;

import android.widget.MediaController;
import android.widget.VideoView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.android.ui.view.VideoViewEx;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.JavaHandler;
import com.appnativa.util.UtilityMap;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Don DeCoteau
 */
public class VideoViewer extends aPlatformViewer {
  protected VideoView     videoView;
  private MediaController mediaController;
  private MediaPlayer     mediaPlayer;
  private Uri             videoUri;

  /**
   * Constructs a new instance
   */
  public VideoViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv the widget's parent
   */
  public VideoViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.Custom;
  }

  public boolean canPause() {
    return videoView.canPause();
  }

  public boolean canSeekBackward() {
    return videoView.canSeekBackward();
  }

  public boolean canSeekForward() {
    return videoView.canSeekForward();
  }

  public void clearContents() {
    if (videoView != null) {
      videoView.stopPlayback();
    }
  }

  /**
   * Configures the browser
   *
   * @param vcfg the viewer's configuration
   */
  public void configure(Viewer vcfg) {
    configureEx(vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
    handleDataURL(vcfg, false);
  }

  public void dispose() {
    if (isDisposed()) {
      return;
    }

    if (videoView != null) {
      try {
        videoView.stopPlayback();
      } catch(Exception e) {
        Platform.ignoreException("VideoViewer.dispose()", e);
      }
    }

    mediaController = null;
    mediaPlayer     = null;
    videoView       = null;
  }

  public void handleActionLink(ActionLink link, boolean deferred) {
    try {
      setURL(link.getURL(this));
    } catch(IOException ex) {
      this.handleException(ex);
    }
  }

  public void pause() {
    videoView.pause();
  }

  public void reload(boolean context) {
    if (videoUri != null) {
      videoView.setVideoURI(videoUri);
    }
  }

  public void resume() {
    videoView.resume();
  }

  public void seekTo(int msec) {
    videoView.seekTo(msec);
  }

  public void start() {
    videoView.start();
  }

  public void stopPlayback() {
    videoView.stopPlayback();
  }

  public void suspend() {
    videoView.suspend();
  }

  @Override
  public void targetAcquired(iTarget target) {
    super.targetAcquired(target);
  }

  public void targetLost(iTarget target) {
    try {
      if (mediaPlayer != null) {
        mediaPlayer.pause();
      }
    } catch(Throwable e) {}

    super.targetLost(target);
  }

  /**
   * @param mediaController the mediaController to set
   */
  public void setMediaController(MediaController mediaController) {
    this.mediaController = mediaController;
    videoView.setMediaController(mediaController);
  }

  public void setURL(String url) {
    checkConfigure();

    ActionLink l = new ActionLink(url);

    try {
      Platform.getAppContext().getAsyncLoadStatusHandler().loadStarted(this, l, null);
      videoView.setVideoURI(videoUri = Uri.parse(url));
    } finally {
      Platform.getAppContext().getAsyncLoadStatusHandler().loadCompleted(this, l);
    }
  }

  /**
   * Sets the URL for the browser
   *
   * @param url the URL
   * @throws IOException
   */
  public void setURL(URL url) throws IOException {
    setURL(url.toExternalForm());
  }

  public void setValue(Object value) {
    try {
      if (value instanceof URL) {
        setURL((URL) value);
      } else if (value == null) {
        clearContents();
      } else {
        setURL(value.toString());
      }
    } catch(IOException ex) {
      handleException(ex);
    }
  }

  public int getCurrentPosition() {
    return videoView.getCurrentPosition();
  }

  public int getDuration() {
    return videoView.getDuration();
  }

  public String getLocation() {
    return (videoUri == null)
           ? null
           : videoUri.toString();
  }

  /**
   * @return the mediaController
   */
  public MediaController getMediaController() {
    return mediaController;
  }

  /**
   * @return the mediaPlayer
   */
  public MediaPlayer getMediaPlayer() {
    return mediaPlayer;
  }

  public Object getSelection() {
    return getLocation();
  }

  public URL getURL() throws MalformedURLException {
    String s = getLocation();

    return ((s == null) || (s.length() == 0))
           ? null
           : new URL(s);
  }

  public boolean isPlaying() {
    return videoView.isPlaying();
  }

  protected void checkConfigure() {
    if (videoView == null) {
      videoView     = new VideoViewEx(getAndroidContext());
      dataComponent = formComponent = new Component(videoView);
    }
  }

  /**
   * Configures the browser
   *
   * @param cfg the viewer's configuration
   */
  protected void configureEx(Viewer cfg) {
    videoView     = new VideoViewEx(getAndroidContext());
    dataComponent = formComponent = new Component(videoView);
    configureEx(cfg, true, false, true);

    boolean useController = true;
    boolean autoStart     = true;
    boolean looping       = false;
    int     startPosition = 0;

    try {
      if (getCustomProperties() != null) {
        UtilityMap options = new UtilityMap(DataParser.getConfigStruct(getCustomProperties()));

        useController = options.removeBoolean("useController", useController);

        String s = options.removeString("mediaController");

        if (s != null) {
          mediaController = (MediaController) JavaHandler.allocate(Class.forName(s),
                  new Object[] { getAndroidContext() });
          mediaController.setAnchorView(videoView);
          videoView.setMediaController(mediaController);
        }

        autoStart     = options.removeBoolean("autoStart", autoStart);
        looping       = options.removeBoolean("looping", looping);
        startPosition = options.removeInt("startPosition", startPosition);
      }
    } catch(Exception e) {
      handleException(e);

      return;
    }

    if (useController && (mediaController == null)) {
      mediaController = new MediaController(getAndroidContext());
      mediaController.setAnchorView(videoView);
      videoView.setMediaController(mediaController);
    }

    final boolean as   = autoStart;
    final boolean l    = looping;
    final int     seek = startPosition;

    videoView.setOnPreparedListener(new OnPreparedListener() {
      public void onPrepared(MediaPlayer mp) {
        mediaPlayer = mp;
        finishedLoadingEx();

        if (seek > 0) {
          mp.seekTo(seek);
        }

        if (l) {
          mp.setLooping(l);
        }

        if (as) {
          mp.start();
        }
      }
    });
    configureSize(getContainerComponent(), cfg.bounds);
  }

  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);
  }
}
