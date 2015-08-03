/*
 * @(#)SoundClip.java   2010-07-10
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.appnativa.rare.Platform;

/**
 *
 * @author Don DeCoteau
 */
public class SoundClip implements Clip, Runnable {
  int                      loopCount = 1;
  int                      new_pos   = 0;
  int                      pos       = 0;
  volatile InputStream     stream    = null;
  byte                     buffer[];
  SourceDataLine           dataLine;
  AudioFormat              format;
  Info                     info;
  private volatile boolean stop   = false;
  private final Object     waiter = new Object();
  private volatile boolean paused;
  private volatile Thread  runner;
  private URL              url;

  public SoundClip(final URL url) throws Exception {
    this.url = url;
  }

  @Override
  public void addLineListener(LineListener listener) {
    dataLine.addLineListener(listener);
  }

  @Override
  public int available() {
    return dataLine.available();
  }

  @Override
  public void close() {
    stop = true;

    if ((runner != null) && runner.isAlive()) {
      synchronized(waiter) {
        waiter.notify();
      }
    } else {
      closeEx();
    }
  }

  @Override
  public void drain() {
    dataLine.drain();
  }

  @Override
  public void flush() {
    dataLine.flush();
  }

  @Override
  public void loop(int count) {
    loopCount = count;
  }

  @Override
  public void open() throws LineUnavailableException {
    open((AudioFormat) null);
  }

  public void open(AudioFormat format) throws LineUnavailableException {
    if ((runner != null) || (dataLine != null)) {
      throw new LineUnavailableException();
    }

    if (format != null) {
      this.format = format;
    }

    try {
      openStream(url);
    } catch(UnsupportedAudioFileException ex) {
      throw new LineUnavailableException(ex.getMessage());
    } catch(IOException ex) {
      throw new LineUnavailableException(ex.getMessage());
    }
  }

  @Override
  public void open(AudioInputStream stream) throws LineUnavailableException, IOException {
    throw new UnsupportedOperationException();
  }

  public void open(AudioFormat format, int bufferSize) throws LineUnavailableException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void open(AudioFormat format, byte[] data, int offset, int bufferSize) throws LineUnavailableException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeLineListener(LineListener listener) {
    dataLine.removeLineListener(listener);
  }

  @Override
  public void run() {
    try {
      dataLine.start();
      play();
      dataLine.stop();
    } catch(Exception e) {
      try {
        dataLine.stop();
      } catch(Exception ex) {}

      Platform.ignoreException(null, e);
    }
  }

  @Override
  public void start() {
    if (this.paused == true) {
      this.paused = false;

      synchronized(waiter) {
        waiter.notify();
      }
    } else if ((runner == null) ||!runner.isAlive()) {
      runner = new Thread(this, "SoundClip Player");
      runner.setDaemon(true);
      runner.start();
    }
  }

  @Override
  public void stop() {
    this.paused = true;
  }

  public int write(byte[] b, int off, int len) {
    return dataLine.write(b, off, len);
  }

  @Override
  public void setFramePosition(int frames) {
    if (frames == 0) {
      setMicrosecondPosition(0);
    }
  }

  @Override
  public void setLoopPoints(int start, int end) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {
    if (microseconds == 0) {
      new_pos = 0;
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public int getBufferSize() {
    return dataLine.getBufferSize();
  }

  @Override
  public Control getControl(Type control) {
    return dataLine.getControl(control);
  }

  @Override
  public Control[] getControls() {
    return dataLine.getControls();
  }

  @Override
  public AudioFormat getFormat() {
    return dataLine.getFormat();
  }

  @Override
  public int getFrameLength() {
    return -1;
  }

  @Override
  public int getFramePosition() {
    return dataLine.getFramePosition();
  }

  @Override
  public float getLevel() {
    return dataLine.getLevel();
  }

  @Override
  public Info getLineInfo() {
    return info;
  }

  @Override
  public long getLongFramePosition() {
    return dataLine.getLongFramePosition();
  }

  @Override
  public long getMicrosecondLength() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public long getMicrosecondPosition() {
    return dataLine.getMicrosecondPosition();
  }

  @Override
  public boolean isActive() {
    return (runner != null) && runner.isAlive() && dataLine.isActive();
  }

  @Override
  public boolean isControlSupported(Type control) {
    return dataLine.isControlSupported(control);
  }

  @Override
  public boolean isOpen() {
    return (dataLine != null) && dataLine.isOpen();
  }

  @Override
  public boolean isRunning() {
    return dataLine.isRunning();
  }

  protected void checkAndResetStream() throws Exception {
    try {
      if (new_pos == -1) {
        return;
      }

      if ((new_pos == pos) && (stream != null)) {
        return;
      }

      openStream(url);
      pos = 0;
    } finally {
      new_pos = -1;
    }
  }

  protected void closeEx() {
    if (stream != null) {
      try {
        stream.close();
      } catch(Exception ex) {}
    }

    if (dataLine != null) {
      try {
        dataLine.close();
      } catch(Exception ex) {}
    }

    dataLine  = null;
    runner    = null;
    stream    = null;
    buffer    = null;
    format    = null;
    paused    = false;
    loopCount = 1;
    new_pos   = 0;
    pos       = 0;
  }

  protected void openStream(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (stream != null) {
      try {
        stream.close();
      } catch(Throwable e) {}
    }

    AudioInputStream ain = AudioSystem.getAudioInputStream(url);

    if ((format == null) || (dataLine == null)) {
      format = ain.getFormat();

      if ((format.getEncoding() == AudioFormat.Encoding.ULAW) || (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
        AudioFormat tmp = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
                                          format.getSampleSizeInBits() * 2, format.getChannels(),
                                          format.getFrameSize() * 2, format.getFrameRate(), true);

        ain    = AudioSystem.getAudioInputStream(tmp, ain);
        format = tmp;
      } else {
        AudioFormat tmp = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16,
                                          format.getChannels(), format.getChannels() * 2, format.getSampleRate(),
                                          false);

        ain    = AudioSystem.getAudioInputStream(tmp, ain);
        format = tmp;
      }

      this.info     = new DataLine.Info(SourceDataLine.class, format);
      this.dataLine = (SourceDataLine) AudioSystem.getLine(info);
      this.dataLine.open(format);
    } else {
      this.dataLine.open(format);
      ain = AudioSystem.getAudioInputStream(format, ain);
    }

    stream = ain;
  }

  protected void play() throws Exception {
    int len;
    int count = loopCount;

    while(!stop) {
      if (paused) {
        synchronized(waiter) {
          try {
            dataLine.stop();
            waiter.wait();
          } catch(InterruptedException e) {
            break;
          }

          if (!stop) {
            dataLine.start();
          } else {
            break;
          }
        }
      }

      checkAndResetStream();
      len = processFrame();

      if (len < 1) {
        if (count > -1) {
          count--;

          if (count < 1) {
            break;
          }
        }

        new_pos = 0;

        continue;
      }

      pos += len;
    }

    if (!stop) {
      dataLine.drain();
    }

    if (pos == 0) {
      pos = -1;
    }
  }

  protected int processFrame() throws Exception {
    if (buffer == null) {
      buffer = new byte[4086];
    }

    int len = stream.read(buffer);

    if (len > 0) {
      dataLine.write(buffer, 0, len);
    }

    return len;
  }
}
