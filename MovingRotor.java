package enigma;

/**
 * Class that represents a rotating rotor in the enigma machine.
 *
 * @author Avik Sethia
 */
class MovingRotor extends Rotor {
    /**
     * Location of notches.
     */
    private String notches2;

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        notches2 = notches;
    }



    @Override
    void advance() {
        this.set(setting() + 1);
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {


        for (int i = 0; i < notches2.length(); i++) {
            if (notches2.charAt(i) == alphabet().toChar(wrap(setting()))) {
                return true;
            }
        }
        return false;
    }




}
