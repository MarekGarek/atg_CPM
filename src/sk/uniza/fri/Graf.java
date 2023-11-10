package sk.uniza.fri;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Collections;

public class Graf {

     private int pocV; //pocet vrcholov
     private int pocH; //pocet hran grafu
     private int H[][]; //pole udajov o hranach
     private int d[]; //vstupne stupne vrchola
     private int z[]; //najskor mozne zaciatky
     private int k[]; //najskor mozne konce
     private int t[]; //trvanie cinnosti
     private ArrayList<Integer> ideg0;

     public Graf(int pocetVrcholov, int pocetHran) {
         this.pocV = pocetVrcholov;
         this.pocH = pocetHran;
         this.H = new int[1 + pocH][3];
         this.d = new int[pocetVrcholov+1];
         this.z = new int[pocetVrcholov+1];
         this.k = new int[pocetVrcholov+1];
         this.t = new int[pocetVrcholov+1];
         this.ideg0 = new ArrayList<>();
     }

     public static Graf nacitajGraf(String nazovSuboru) {
         int pocVrcholov = 1;
         int pocHran = 0;
         //zistujem pocet vrcholov a pocet hran
         try {
             Scanner sc = new Scanner(new File(nazovSuboru));

             while (sc.hasNextLine()) {
                 int u = sc.nextInt();
                 int v = sc.nextInt();
                 int c = sc.nextInt();

                 //nacital som hranu, zvysim pocet
                 pocHran++;

                 //skontrolujem ci netreba zvysit pocet vrcholov
                 if (pocVrcholov < u) pocVrcholov = u;
                 if (pocVrcholov < v) pocVrcholov = v;
             }
             sc.close();
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }

         //vytvorenie a naplnenie grafu (vrchol,vrchol,cena)
        Graf graf = new Graf(pocVrcholov,pocHran);
         try {
             Scanner sc = new Scanner(new File(nazovSuboru));
             for (int j = 1; j <= pocHran; j++) {
                 int u = sc.nextInt();
                 int v = sc.nextInt();
                 int c = sc.nextInt();

                 graf.H[j][0] = u;
                 graf.H[j][1] = v;
                 graf.H[j][2] = c;
             }
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }
         return graf;
     }

     public void printInfo() {
         System.out.println("Pocet vrcholov: " + this.pocV);
         System.out.println("Pocet hran: " + this.pocH);
     }

     public void monotonneOcislovanie() {
        // nastavenie pola vstupnych stupnov vrchola na 0
         for (int i = 0;i < this.pocV+1;i++) {
             this.d[i] = 0;
         }
         this.d[0] = 999;
         // pocet vstupnych hran vrchola
         int vrchol = 0;
         for (int i = 0; i < this.pocH; i++) {
             vrchol = this.H[i][1];
             this.d[vrchol] += 1;
         }

//         System.out.println();
//         System.out.print("Stupne vrcholov:        ");
//         for (int i = 1; i < this.pocV+1; i++) {
//             System.out.print(this.d[i]+ " ");
//         }
//         System.out.println();

         // pokial niesu spracovane vsetky vrcholy
         ArrayList<Integer> spracovaneV = new ArrayList<Integer>();
         while (this.ideg0.size() != pocV) {
             // do ideg0 vlozim vsetky vrcholy so vstupnym stupon 0
             int rVrchol = 0;       //riadiaci vrchol
             for (int i = 0; i < this.pocV+1; i++) {
                 if (this.d[i] == 0 && !this.ideg0.contains(i)) {
                     this.ideg0.add(i);
                 }
             }
             // vrcholom, ktore tvoria hranu s vrcholom z ideg0 znizim vstupny stupen
             for (int i = 0; i < this.ideg0.size();i++) {
                 rVrchol = this.ideg0.get(i);
                 if (!spracovaneV.contains(rVrchol)) {
                     for (int j = 0; j < this.pocH; j++) {
                         if (this.H[j][0] == rVrchol) {
                             vrchol = this.H[j][1];
                             d[vrchol] -= 1;
                         }
                     }
                     spracovaneV.add(rVrchol);
                 }
             }
         }

         //vypis
//         System.out.print("Monotonne ocislovanie:  ");
//         for (int i = 0 ; i < this.ideg0.size();i++) {
//             System.out.print(this.ideg0.get(i) + " ");
//         }
     }

     public void nacitajTrvanie(String nazovSuboru) {
         try {
             Scanner sc = new Scanner(new File(nazovSuboru));
             for (int j = 1; j <= this.pocV; j++) {
                 int u = sc.nextInt();
                 t[j] = u;
             }
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }

//         for (int i = 0; i <= this.pocV; i++) {
//             System.out.print(t[i] + " ");
//         }
     }

     public void cpm() {
         //najskor mozni zaciatok

         for (int i = 0;i < this.pocV +1 ;i++) {
             this.z[i] = 0;
         }

         for (int k = 0; k < this.ideg0.size(); k++) {
             int vrchol;
             vrchol = this.ideg0.get(k);
             int pom = 0;

             // prejdes vsetky hrany, zoberies tie co maju zacV = vrchol
             for (int i = 0; i < pocH; i++) {
                 if (H[i][0] == vrchol) {
                     pom = H[i][1];
                     // ak najskor mozny zaciatok je mensi ako novy najskor mensi zaciatok, prepis ho
                     if (z[pom] < z[vrchol] + t[vrchol]) {
                         z[pom] = z[vrchol] + t[vrchol];
                     }
                 }
             }
         }

//         System.out.println();
//         System.out.print("Najskor mozne zaciatky: ");
//         for (int i = 1;i < this.pocV + 1;i++) {
//             System.out.print(z[i] + " ");
//         }

         int trvanie = 0;
         for (int i = 1; i < this.pocV + 1; i++) {
             if (trvanie < this.z[i] + this.t[i]) {
                 trvanie = this.z[i] + this.t[i];
             }
         }

         // naskor mozny koniec
         for (int i = 0;i < this.pocV +1 ;i++) {
             this.k[i] = trvanie;
         }

         for (int k = this.ideg0.size() -1 ; k > 0; k--) {
             int vrchol;
             vrchol = this.ideg0.get(k);
             int pom = 0;

             // prejdes vsetky hrany, zoberies tie co maju konV = vrchol
             for (int i = 0; i < pocH; i++) {
                 if (H[i][1] == vrchol) {
                     pom = H[i][0];
                     // ak najskormozny koniec je vacsi ako novy najskor vacsi koniec, prepis ho
                     if (this.k[pom] > this.k[vrchol] - t[vrchol]) {
                         this.k[pom] = this.k[vrchol] - t[vrchol];
                     }
                 }
             }
         }
         for (int i = 1;i < this.pocV + 1;i++) {
             k[i] = k[i] - t[i];
         }

//         System.out.println();
//         System.out.print("Najskor mozne konce:    ");
//         for (int i = 1;i < this.pocV + 1;i++) {
//             System.out.print(k[i]+ " ");
//         }
//         System.out.println();
//         System.out.println("Trvanie : " + trvanie);

         Collections.sort(ideg0);
         System.out.print("Kriticke cinnosti: ");
         for (int i = 1; i < this.pocV + 1; i++) {
             if (k[i] == z[i]) {
                 System.out.print(this.ideg0.get(i-1)+",");
             }
         }
     }
 }
