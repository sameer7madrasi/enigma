package enigma;

import java.util.ArrayList;

/**
 * Stores alphabets in an array list.
 *  @author Avik Sethia
 */


public class Alphabetextension extends Alphabet {
    /**
     * Stores provided alphabet sequentially.
     */
    private ArrayList<Character> storer;
    /**
     * Range of characters in this Alphabet.
     */
    private char _first, _last;
    /**
     * For any combination of letters for alphabet F.
     */

    Alphabetextension(String f) {
        storer = new ArrayList<>();

        for (int s = 0; s < f.length(); s++) {
            storer.add(f.charAt(s));
        }
    }

    @Override
    int size() {
        return storer.size();
    }

    @Override
    boolean contains(char ch) {
        for (int s = 0; s < storer.size(); s++) {
            if (storer.get(s).equals(ch)) {
                return true;

            }
            return false;
        }
        return false;
    }

    @Override
    char toChar(int index) {
        return storer.get(index);
    }

    @Override
    int toInt(char ch) {
        return storer.indexOf(ch);
    }

}
