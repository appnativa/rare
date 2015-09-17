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

package com.appnativa.rare.platform.swing.ui.util;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * MP# Sound clip handler
 */
public class MP3SoundClip extends SoundClip {
  private Bitstream    bitstream;
  private Decoder      decoder;
  private SampleBuffer pushedBacked;

  public MP3SoundClip(URL url) throws Exception {
    super(url);
  }

  @Override
  protected void closeEx() {
    try {
      super.closeEx();
    } finally {
      try {
        if (bitstream != null) {
          bitstream.close();
        }
      } catch(Exception ex) {}

      bitstream = null;
      decoder   = null;
    }
  }

  @Override
  protected void openStream(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    stream = url.openStream();

    if (!url.getProtocol().equals("file")) {
      stream = new BufferedInputStream(stream);
    }

    bitstream = new Bitstream(stream);
    decoder   = new Decoder();

    try {
      if (info == null) {
        Header h = bitstream.readFrame();

        pushedBacked = (SampleBuffer) decoder.decodeFrame(h, bitstream);
        info         = new DataLine.Info(SourceDataLine.class, getAudioFormat());
        dataLine     = (SourceDataLine) AudioSystem.getLine(info);
        dataLine.open(format);
      }
    } catch(Throwable e) {
      throw new UnsupportedAudioFileException();
    }
  }

  @Override
  protected int processFrame() throws Exception {
    SampleBuffer output = pushedBacked;

    if (output == null) {
      Header h = bitstream.readFrame();

      if (h == null) {
        return -1;
      }

      output = (SampleBuffer) decoder.decodeFrame(h, bitstream);
    } else {
      pushedBacked = null;
    }

    int    len = output.getBufferLength();
    byte[] b   = toByteArray(output.getBuffer(), 0, len);

    dataLine.write(b, 0, len * 2);
    bitstream.closeFrame();

    return len;
  }

  protected byte[] toByteArray(short[] samples, int offs, int len) {
    byte[] b   = getByteArray(len * 2);
    int    idx = 0;
    short  s;

    while(len-- > 0) {
      s        = samples[offs++];
      b[idx++] = (byte) s;
      b[idx++] = (byte) (s >>> 8);
    }

    return b;
  }

  protected AudioFormat getAudioFormat() {
    if (format == null) {
      format = new AudioFormat(decoder.getOutputFrequency(), 16, decoder.getOutputChannels(), true, false);
    }

    return format;
  }

  protected byte[] getByteArray(int length) {
    if ((buffer == null) || (buffer.length < length)) {
      buffer = new byte[length + 1024];
    }

    return buffer;
  }
}
