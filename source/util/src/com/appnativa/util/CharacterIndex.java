/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class provides a basic character to index position mapping.
 *
 * @author Don DeCoteau
 */
public class CharacterIndex {

  /** the index map */
  protected HashMap<Character, Integer> indexMap;

  /**
   * Creates a new instance of CharacterIndex
   */
  public CharacterIndex() {}

  /**
   * Add an index for a character. Any previous index for this character will be removed
   * @param c the character to add the index for
   * @param pos the index position
   */
  public void addCharacter(char c, int pos) {
    if (indexMap == null) {
      indexMap = new HashMap<Character, Integer>();
    }

    Character ch = Character.valueOf(Character.toUpperCase(c));
    Integer   in = indexMap.get(ch);

    if (in == null) {
      in = Integer.valueOf(pos);
      indexMap.put(ch, in);
    }
  }

  /**
   * Removes all characters from the map
   */
  public void clear() {
    if (indexMap != null) {
      indexMap.clear();
    }
  }

  public Map<Character, Integer> getIndexMap() {
    return indexMap;
  }

  /**
   * Removes the specified character from the index
   * @param c the character to remove
   * @return the index position for the specified character or -1 if the character is not indexed
   */
  public int removeLetter(char c) {
    Integer in = null;

    if (indexMap != null) {
      Character ch = Character.valueOf(Character.toUpperCase(c));

      in = indexMap.remove(ch);
    }

    return (in == null)
           ? -1
           : in.intValue();
  }

  public Character[] getCharacters() {
    Character a[] = new Character[indexMap.size()];
    int       i   = 0;
    Iterator  it  = indexMap.keySet().iterator();

    while(it.hasNext()) {
      a[i++] = (Character) it.next();
    }

    Arrays.sort(a);

    return a;
  }

  public String[] getCharactersAsStrings() {
    String   a[] = new String[indexMap.size()];
    int      i   = 0;
    Iterator it  = indexMap.keySet().iterator();

    while(it.hasNext()) {
      a[i++] = it.next().toString();
    }

    Arrays.sort(a);

    return a;
  }

  /**
   * Get the index position for the specified character
   * @param c the character to retrieve the index for
   * @return the index position for the specified character or -1 if the character is not indexed
   */
  public int getPosition(char c) {
    Integer in = null;

    if (indexMap != null) {
      Character ch = Character.valueOf(Character.toUpperCase(c));

      in = indexMap.get(ch);
    }

    return (in == null)
           ? -1
           : in.intValue();
  }
}
