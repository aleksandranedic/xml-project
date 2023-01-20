package ftn.xml.korisnik.utils;

import ftn.xml.korisnik.model.Adresa;
import ftn.xml.korisnik.model.Korisnik;

public class PrettyPrint {
    public static void printKorisnika(Korisnik korisnik) {
        System.out.println("**** Korisnik ****");
        System.out.println("\t - Ime: " + korisnik.getIme());
        System.out.println("\t - Prezime: " + korisnik.getPrezime());
        System.out.println("\t - Email: " + korisnik.getEmail());
        System.out.println("\t - Uloga: " + korisnik.getUloga());
        System.out.println("\t - Adresa: " + printAdress(korisnik.getAdresa()));
    }

    private static String printAdress(Adresa adresa) {
        return adresa.getUlica() + " " + adresa.getBroj() + " " + adresa.getPostanskiBroj() + " " + adresa.getGrad() + " " + adresa.getDrzava();
    }

}
