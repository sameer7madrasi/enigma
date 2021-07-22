package enigma;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static enigma.TestUtils.NAVALA;
import static enigma.TestUtils.UPPER;
import static org.junit.Assert.assertEquals;

/**
 * Additional test file.
 * @author Avik Sethia
 */

public class Moretests {
    /**
     * Test invert function with edge cases.
     */
    @Test
    public void testInvertChar() {
        Permutation p =
                new Permutation(
                        "(PNH)(ABDFIKLZYXW) (JC)",
                        new CharacterRange('A', 'Z'));
        assertEquals(p.invert('B'), 'A');
        assertEquals(p.invert('G'), 'G');
        assertEquals(p.invert('C'), 'J');
        assertEquals(p.invert('P'), 'H');
        assertEquals(p.invert('K'), 'I');
        assertEquals(p.invert('A'), 'W');

    }
    /**
     * Tests permute function with edge cases.
     */
    @Test
    public void testPermuteChar() {
        Permutation p =
                new Permutation(
                        "(NPH)     (ABDFIKLZYXW) (JC)",
                        new CharacterRange('A', 'Z'));
        assertEquals(p.permute('A'), 'B');
        assertEquals(p.permute('G'), 'G');
        assertEquals(p.permute('W'), 'A');
        assertEquals(p.permute('N'), 'P');
        assertEquals(p.permute('H'), 'N');
        assertEquals(p.permute('C'), 'J');



    }
    /**
     * Test double step with a 4 alphabet long letter range.
     */
    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'D');
        Rotor one = new Reflector("R1", new Permutation("(AC) (BD)", ac));
        Rotor two = new MovingRotor("R2", new Permutation("(ABCD)", ac), "C");
        Rotor three = new MovingRotor("R3", new Permutation("(ABCD)", ac), "C");
        Rotor four =
                new MovingRotor("R4", new Permutation("(ABCD)", ac), "C");
        String setting = "AAA";
        Permutation plgb = new Permutation("", ac);
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"R1", "R2", "R3", "R4"};
        Machine mach = new Machine(ac, 4,
                3, new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setPlugboard(plgb);
        mach.setRotors(setting);

        assertEquals("AAAA", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AAAB", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AAAC", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AABD", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AABA", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AABB", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AABC", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AACD", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("ABDA", getSetting(ac, machineRotors));




    }

    /**
     * helper function for alphabet ALPH implementation
     * with the array of rotors MACHINEROTORS.
     * @return the current setting.
     */

    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }
    /**
     * Tests forward convert and backward convert with edge cases.
     */
    @Test
    public void testConvert() {
        Alphabet a = new CharacterRange('A', 'D');
        Rotor rot = new Rotor("I", new Permutation("(GWAFTI)", a));
        assertEquals(2, rot.convertForward(2));
        assertEquals(1, rot.convertForward(1));
        assertEquals(3, rot.convertForward(3));
        Alphabet g = new CharacterRange('A', 'F');
        Rotor rot2 = new Rotor("I", new Permutation("(BAFDEC)", g));
        assertEquals(4, rot2.convertBackward(2));
        assertEquals(2, rot2.convertBackward(1));
    }

    /**
     * Full scale integration test
     * that tries to incorporate each part
     * of the project.
     */
    @Test

    public void testintegrationmachine() {
        FixedRotor beta = new FixedRotor("Gamma",
                new Permutation(NAVALA.get("Gamma"), UPPER));
        Reflector ref = new Reflector("C",
                new Permutation(NAVALA.get("C"), UPPER));
        MovingRotor rot1 = new MovingRotor("I",
                new Permutation(NAVALA.get("I"), UPPER), "S");
        MovingRotor rot2 = new MovingRotor("II",
                new Permutation(NAVALA.get("II"), UPPER), "W");
        MovingRotor rot3 = new MovingRotor("III",
                new Permutation(NAVALA.get("III"), UPPER), "A");

        ArrayList<Rotor> rots = new ArrayList<>();
        rots.add(ref);
        rots.add(beta);
        rots.add(rot1);
        rots.add(rot2);
        rots.add(rot3);

        Permutation p = new Permutation("", UPPER);
        String[] names = {"C", "GAMMA", "I", "II", "III"};

        Machine m = new Machine(UPPER, 5, 3, rots);
        m.insertRotors(names);
        m.setRotors("BCDE");
        m.setPlugboard(p);
        assertEquals("ITEQL", m.convert("Memes"));
        assertEquals("HYVWPFJBER", m.convert("Avik Sethia"));



    }
}






