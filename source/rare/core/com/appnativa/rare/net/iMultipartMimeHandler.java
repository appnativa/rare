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

package com.appnativa.rare.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.Map;

/**
 * Interface for handling multipart mime documents
 *
 * @author Don Decoteau
 */
public interface iMultipartMimeHandler {
  public iMultipart getFirstPart(InputStream stream, String multipartMimeType) throws IOException;

  public interface iMultipart {
    public String getHeader(String name);

    public Map getHeaders();

    iMultipart nextPart() throws IOException;

    Object getContent() throws IOException;

    String getContentAsString() throws IOException;

    String getContentType();

    InputStream getInputStream() throws IOException;

    String getPreamble() throws IOException;

    Reader getReader() throws IOException;
  }
}
