/*
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */
package java.net;

import java.io.IOException;

/**
 *
 * @author decoteaud
 */
public abstract class ContentHandler {
   public abstract Object getContent(URLConnection u) throws IOException;
}
