package enigma;

import java.util.ArrayList;
import java.util.Collection;

import static enigma.EnigmaException.error;

/**
 * Class that represents a complete enigma machine.
 *
 * @author Avik Sethia
 */
class Machine {

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;
    /**
     * Number of rotors.
     */
    private int numrotors;
    /**
     * Number of pawls.
     */
    private int numpawls;
    /**
     * permutation for plugboard.
     */
    private Permutation plugboard;
    /**
     * list of all selected rotors.
     */
    private ArrayList<Rotor> listofrotors;
    /**
     * list of possible available rotors.
     */
    private Collection<Rotor> rotorlist;

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        numrotors = numRotors;
        numpawls = pawls;
        rotorlist = allRotors;


    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        return this.numrotors;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        return this.numpawls;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        listofrotors = new ArrayList<Rotor>();
        for (String rotor : rotors) {
            for (Rotor selected : rotorlist) {
                if (selected.name().toUpperCase().equals(rotor)) {
                    listofrotors.add(selected);
                }
            }
        }
        if (listofrotors.size() != numrotors) {
            throw error("Wrong number of rotors");

        }
        if (!listofrotors.get(0).reflecting()) {
            throw error("wrong placement of reflector");
        }

        for (int w = 0; w < rotors.length; w += 1) {
            for (int v = 0; v < rotors.length; v += 1) {
                if (rotors[w].equals(rotors[v]) && (v != w)) {
                    throw error("Two same rotors entered");


                }
                int counter = 0;
                for (int u = 0; u < listofrotors.size(); u++) {
                    if (listofrotors.get(u).rotates()) {
                        counter += 1;
                    }

                }
                if (numpawls != counter) {
                    throw error
                            ("number of moving rotors isn't"
                                    + " equal to number of pawls");
                }
            }
        }


    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 upper-case letters. The first letter refers to the
     * leftmost rotor setting (not counting the reflector).
     */
    void setRotors(String setting) {

        if (setting.length() != listofrotors.size() - 1) {
            throw error(" Mismatch in number of rotors and number of setting");
        } else {
            for (int i = 0; i < setting.length(); i++) {
                listofrotors.get(i + 1).set(setting.charAt(i));

            }

        }
    }

    /**
     * Set the plugboard to PLGBOARD.
     */
    void setPlugboard(Permutation plgboard) {
        this.plugboard = plgboard;

    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * <p>
     * the machine.
     */
    int convert(int c) {


        boolean[] checkadv = new boolean[listofrotors.size()];

        checkadv[listofrotors.size() - 1] = true;
        for (int w = listofrotors.size() - 2; w > 0; w -= 1) {
            if ((listofrotors.get(w).atNotch()
                    && listofrotors.get(w - 1).rotates())
                    || (listofrotors.get(w + 1).atNotch())) {
                checkadv[w] = true;
            }

        }
        for (int i = listofrotors.size() - 1; i > 0; i -= 1) {

            if (checkadv[i]) {
                listofrotors.get(i).advance();
            }
        }

        int converted = plugboard.permute(c);
        for (int j = listofrotors.size() - 1; j > 0; j -= 1) {
            converted = listofrotors.get(j).convertForward(converted);
        }
        for (int i = 0; i < listofrotors.size(); i += 1) {
            converted = listofrotors.get(i).convertBackward(converted);
        }
        converted = plugboard.permute(converted);


        return converted;
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        ArrayList<Character> characterstorer = new ArrayList<Character>();
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == ' ') {
                continue;
            } else {
                characterstorer.add(_alphabet.toChar(convert(_alphabet.toInt
                        (msg.toUpperCase().charAt(i)))));
            }

        }
        String finalreturned = "";
        for (int j = 0; j < characterstorer.size(); j++) {

            finalreturned += characterstorer.get(j);
        }
        return finalreturned;
    }


}
