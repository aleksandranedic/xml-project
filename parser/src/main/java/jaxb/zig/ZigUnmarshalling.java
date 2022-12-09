package jaxb.zig;
import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import jaxb.SchemaValidationEventHandler;
import rs.ac.uns.ftn.jaxb.zig.*;

/** 
 * Primer 1.
 * 
 * Primer demonstrira "unmarshalling" tj. konverziju 
 * iz XML fajla u objektni model. 
 *
 */
public class ZigUnmarshalling {
	
	public void test() {
		try {
			
			System.out.println("[INFO] Zig: JAXB unmarshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.jaxb.zig");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// XML schema validacija i podešavanje unmarshaller-a za XML schema validaciju
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("./data/zig_schema.xsd"));
			unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new SchemaValidationEventHandler());


			// Unmarshalling generiše objektni model na osnovu XML fajla 
			ZahtevZaPriznanjeZiga zahtev = (ZahtevZaPriznanjeZiga) unmarshaller.unmarshal(new File("./data/z-1.xml"));

			// Prikazuje unmarshallovan objekat
			printZahtev(zahtev);
			
		}  catch (JAXBException e) {
			System.out.println("Can't bind element");
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void printZahtev(ZahtevZaPriznanjeZiga zahtev) {
		System.out.println("***** Zahtev za priznanje ziga *****");
		System.out.println("INFORMACIJE O USTANOVI: ");
		System.out.println("\t-Naziv: " + zahtev.getInformacijeOUstanovi().getNaziv());
		System.out.println("\t-Adresa: " + getAdresa(zahtev.getInformacijeOUstanovi().getAdresa()));

		printPopunjavaPodnosilac(zahtev.getPopunjavaPodnosilac());
		printPopunjavaZahtev(zahtev.getPopunjavaZavod());
	}

	private void printPopunjavaPodnosilac(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac popunjavaPodnosioc) {
		System.out.println("POPUNJAVA PODNOSILAC: ");
		int i = 1;
		for(TLice podnosilac: popunjavaPodnosioc.getPodnosilac()) {
			System.out.println("Podnosilac " + i + ":");
			System.out.println(getLice(podnosilac));
			i++;
		}
		System.out.println("Punomocnik:");
		System.out.println(getLice(popunjavaPodnosioc.getPunomocnik()));

		if (popunjavaPodnosioc.getZajednickiPredstavnik() != null) {
			System.out.println("Zajednicki predstavnik:");
			System.out.println(getLice(popunjavaPodnosioc.getZajednickiPredstavnik()));
		}

		System.out.println("Zig:");
		System.out.println(getZig(popunjavaPodnosioc.getZig()));

		System.out.println("Dodatne informacije:");
		System.out.println(getDodatneInformacije(popunjavaPodnosioc.getDodatneInformacije()));

		System.out.println("Placene takse:");
		System.out.println(getPlceneTakse(popunjavaPodnosioc.getPlaceneTakse()));
	}

	private void printPopunjavaZahtev(ZahtevZaPriznanjeZiga.PopunjavaZavod popunjavaZavod) {
		System.out.println("POPUNJAVA ZAVOD: ");
		System.out.println(getPriloziUzZahtev(popunjavaZavod.getPriloziUzZahtev()));
		System.out.println("Broj prijave ziga: " + popunjavaZavod.getBrojPrijaveZiga());
		System.out.println("Datum podnosenja ziga: " + popunjavaZavod.getDatumPodnosenja().toString());
	}

	private String getAdresa(Adresa adresa) {
		String adr = adresa.getUlica() + " " + adresa.getBroj() + ", " + adresa.getPostanskiBroj() + " " + adresa.getGrad();
		if (adresa.getDrzava() != null) {
		   adr += ", " + adresa.getDrzava();
		}
		return adr;
	}
	private String getLice(TLice lice) {
		String info = "";
		if (lice instanceof TFizickoLice) {
			TFizickoLice fizickoLice = (TFizickoLice) lice;
			info += "\t-Ime: " + fizickoLice.getIme() + "\n\t-Prezime:" + fizickoLice.getPrezime();
		}
		if (lice instanceof TPoslovnoLice) {
			TPoslovnoLice poslovnoLice = (TPoslovnoLice) lice;
			info += "\t-Poslovno ime: " + poslovnoLice.getPoslovnoIme();
		}
		info += "\n\t-Adresa: " + getAdresa(lice.getAdresa());
		info += "\n\t-Telefon: " + lice.getTelefon();
		info += "\n\t-Email: " + lice.getEmail();
		info += "\n\t-Faks: " + lice.getFaks();
		return info;
	}

	private String getZig(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig zig) {
		String info = "";
		info += "\t-Tip A: " + getTipA(zig.getVrsta().getTipA());
		info += "\n\t-Tip B: " + getTipB(zig.getVrsta().getTipB());
		info += "\n\t-Naznacenje boje: " + zig.getNaznacenjeBoje();
		info += "\n\t-Transliteracija znaka: " + zig.getTransliteracijaZnak();
		info += "\n\t-Prevod znaka: " + zig.getPrevodZnaka();
		info += "\n\t-Putanja do fajla koji prikazuje izgled znaka: " + zig.getIzgledZnaka();
		return info;
	}

	private String getTipA(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta.TipA tipA) {
		if (tipA.getIndividualniZig() != null)
			return "Individualni zig";
		else if (tipA.getKolektivniZig() != null)
			return "Kolektivni zig";
		return "Zig garancije";
	}

	private String getTipB(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta.TipB tipB) {
		if (tipB.getGrafickiZnak() != null)
			return "Graficki znak";
		else if (tipB.getKombinovaniZnak() != null)
			return "Kombinovani znak";
		else if (tipB.getTrodimenzionalniZnak() != null)
			return "Trodimenzionalni znak";
		else if (tipB.getDrugaVrsta() != null)
			return tipB.getDrugaVrsta();
		return "Verbalni znak";
	}

	private String getDodatneInformacije(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije info) {
		return "\t-Klasa robe i usluga: " + getKlasa(info.getKlasaRobeIUslaga()) + "\n\t-Zatrazeno pravo prvensta i osnov: " + info.getZatrazenoPravoPrvenstaIOsnov();
	}

	private String getKlasa(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije.KlasaRobeIUslaga klasaRobeIUslaga) {
		String info = "";
		for (Integer klasa : klasaRobeIUslaga.getKlasa()) {
			info = info.concat(klasa + ", ");
		}
		return info.substring(0, info.length() - 2);
	}
	private String getPlceneTakse(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse placeneTakse) {
		String info = "";
		info += "\t-Osnovna taksa: " + placeneTakse.getOsnovnaTaksa();
		info += "\n\t-Za " + placeneTakse.getZaKlasa().getNazivKlase() + "klasa: " + placeneTakse.getZaKlasa().getSuma();
		info += "\n\t-Za graficko resenje: " + placeneTakse.getZaGrafickoResenje();
		info += "\n\t-UKUPNO: " + placeneTakse.getUkupno();
		return info;
	}

	private String getPriloziUzZahtev(ZahtevZaPriznanjeZiga.PopunjavaZavod.PriloziUzZahtev prilozi) {
		System.out.println("Prilozi uz zahtev: ");
		String info = "";
		if (prilozi.getPrimerakZnaka() != null)
			info += "\t-Primerak znaka\n";
		if (prilozi.getSpisakRobeIUsluga() != null)
			info += "\t-Spisak robe i usluga\n";
		if (prilozi.getPunomocje() != null )
			info += "\t-Punomocje\n";
		if (prilozi.getGeneralnoPunomocje() != null)
			info += "\t-Generalno punomocje\n";
		if (prilozi.getPunomocjeNaknadnoDostavljeno() != null)
			info += "\t-Punomocje naknadno dostavljeno\n";
		if (prilozi.getOpstiAkt() != null)
			info += "\t-Opsti akt\n";
		if (prilozi.getDokazOPravuPrvenstva() != null)
			info += "\t-Dokaz o pravu prvenstva\n";
		if (prilozi.getDokazOUplatiTakse() != null)
			info += "\t-Dokaz o uplati takse";
		return info;
	}
    public static void main( String[] args ) {
    	ZigUnmarshalling test = new ZigUnmarshalling();
    	test.test();
    }
}
