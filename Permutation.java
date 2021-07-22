package enigma;

import java.util.ArrayList;

/**
 * Represents a permutation of a range of integers starting at 0 corresponding
 * to the characters of an alphabet.
 *
 * @author Avik Sethia
 */
class Permutation {
    /**
     * Stores cycles as elements of an array list.
     */
    private ArrayList<String> gottagetthemperms;
    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        gottagetthemperms = new ArrayList<String>();
        _alphabet = alphabet;
        int startindex = 0;
        int endindex = 0;
        for (int i = 0; i < cycles.length(); i++) {

            if (cycles.charAt(i) == '(') {
                startindex = i;

            } else if (cycles.charAt(i) == ')') {
                endindex = i;
                gottagetthemperms.add(
                        cycles.substring(startindex + 1, endindex));

            }

        }


    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    private void addCycle(String cycle) {

    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return _alphabet.size();
    }


    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        int index2 = wrap(p);
        char indexofletter = _alphabet.toChar(index2);
        for (int i = 0; i < gottagetthemperms.size(); i++) {
            for (int j = 0; j < gottagetthemperms.get(i).length(); j++) {
                if (gottagetthemperms.get(i).charAt(j) == indexofletter) {
                    if (j == gottagetthemperms.get(i).length() - 1) {
                        return _alphabet.toInt
                                (gottagetthemperms.get(i).charAt(0));
                    } else {
                        return _alphabet.toInt
                                (gottagetthemperms.get(i).charAt(j + 1));
                    }
                }


            }
        }
        return index2;
    }



    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        int index3 = wrap(c);
        char indexofletter = _alphabet.toChar(index3);
        for (int i = 0; i < gottagetthemperms.size(); i++) {
            for (int j = 0; j < gottagetthemperms.get(i).length(); j++) {
                if (gottagetthemperms.get(i).charAt(j) == indexofletter) {
                    if (j == 0) {
                        return _alphabet.toInt(gottagetthemperms.get(i).
                                charAt(gottagetthemperms.get(i).length() - 1));
                    } else {
                        return _alphabet.toInt
                                (gottagetthemperms.get(i).charAt(j - 1));
                    }
                }
            }
        }
        return index3;
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        return _alphabet.toChar(this.permute(_alphabet.toInt(p)));

    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        return _alphabet.toChar(this.invert(_alphabet.toInt(c)));
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        return true;
    }


}
