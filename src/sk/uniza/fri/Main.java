package sk.uniza.fri;

import java.util.ArrayList;

public class Main {
                                                                             //    \\ShortestPath\\TEST_mini.hrn"
    public static void main(String[] args) {                                 //    \\ACYKL\\CPM_midi.hrn"
        Graf g = Graf.nacitajGraf("C:\\Users\\garek\\Documents\\Skola\\ATG\\ATG_DAT\\ACYKL\\CPM_midi.hrn");
        g.printInfo();
        g.nacitajTrvanie("C:\\Users\\garek\\Documents\\Skola\\ATG\\ATG_DAT\\ACYKL\\CPM_midi.tim");
        g.monotonneOcislovanie();
        g.cpm();
    }
}
