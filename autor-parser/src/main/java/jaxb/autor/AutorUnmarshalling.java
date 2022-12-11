package jaxb.autor;


import jaxb.SchemaValidationEventHandler;
import org.xml.sax.SAXException;
import rs.ac.uns.ftn.jaxb.autor.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.List;

/** 
 * Primer 1.
 * 
 * Primer demonstrira "unmarshalling" tj. konverziju 
 * iz XML fajla u objektni model. 
 *
 */
public class AutorUnmarshalling {
	
	public void test() {
		try {
			
			System.out.println("[INFO] Autorsko delo: JAXB unmarshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance(rs.ac.uns.ftn.jaxb.autor.ObjectFactory.class);
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// XML schema validacija i podešavanje unmarshaller-a za XML schema validaciju
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("./data/a-1.xsd"));
			unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new SchemaValidationEventHandler());


			// Unmarshalling generiše objektni model na osnovu XML fajla 

			ZahtevZaIntelektualnuSvojinu zahtev = (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File("./data/a-1.xml"));

			print(zahtev);



			
		}  catch (JAXBException e) {
			System.out.println("Can't bind element");
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	private void print(ZahtevZaIntelektualnuSvojinu zahtev) {
		
		print(zahtev.getInformacijaOUstanovi());
		print(zahtev.getPopunjavaPodnosilac());
		print(zahtev.getPopunjavaZavod());
	}

	private void print(ZahtevZaIntelektualnuSvojinu.PopunjavaZavod popunjavaZavod) {
		System.out.println("\n------------------------------");
		System.out.println("Popunjava zavod:");
		System.out.println("------------------------------");
		System.out.println("\tBroj prijave: " + popunjavaZavod.getBrojPrijave());
		System.out.println("\tDatum podnosenja: " + popunjavaZavod.getDatumPodnosenja());
		print(popunjavaZavod.getPriloziUzZahtev());

	}

	private void print(ZahtevZaIntelektualnuSvojinu.PopunjavaZavod.PriloziUzZahtev priloziUzZahtev) {
		System.out.println("Prilozi uz zahtev:");

		print(priloziUzZahtev.getOpisAutorskogDela());
		if (priloziUzZahtev.getPrimerAutorskogDela() != null) {
			System.out.println("Primer dela");
		}

	}

	private void print(TOpisDela opisAutorskogDela) {
		System.out.println("Opis dela");
		System.out.println("\tNaslov dela: " + opisAutorskogDela.getNazivAutorskogDela());
		System.out.println("\tOpis sadrzaja dela: " + opisAutorskogDela.getOpisSadrzajaAutorskogDela());

	}

	private void print(ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac popunjavaPodnosilac) {
		System.out.println("\n------------------------------");
		System.out.println("Popunjava podnosilac:");
		System.out.println("------------------------------");

		System.out.println("Podnosilac");
		print(popunjavaPodnosilac.getPodnosilac());

		System.out.println("Punomocnik");
		print(popunjavaPodnosilac.getPunomocnik());

		System.out.println("Autori");
		print(popunjavaPodnosilac.getPodaciOAutoru());

		System.out.println("Autorsko delo");
		print(popunjavaPodnosilac.getAutorskoDelo());


	}

	private void print(List<ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac.PodaciOAutoru> autori) {
		for (ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac.PodaciOAutoru autor: autori) {
			if (autor.getAnoniman() != null) {
				System.out.println("\tAutor je anoniman.");
			}
			print(autor.getAutor());
		}
		System.out.print("\n");
	}

	private void print(ZahtevZaIntelektualnuSvojinu.InformacijaOUstanovi informacijaOUstanovi) {
		System.out.println("\n------------------------------");
		System.out.println("Ustanova:");
		System.out.println("------------------------------");
		System.out.println("Naziv: " + informacijaOUstanovi.getNaziv());
		print(informacijaOUstanovi.getAdresa());
		
	}

	private void print(AutorskoDelo autorskoDelo) {

		System.out.println("\tForma: " + autorskoDelo.getForma());
		System.out.println("\tVrsta: " + autorskoDelo.getForma());
		System.out.println("\tNaslov: " + autorskoDelo.getForma());
		if (autorskoDelo.isStvorenoURadnomOdnosu()) {
			System.out.println("\tStvoreno u radnom odnosu");
		}
		else {
			System.out.println("\tNije stvoreno u radnom odnosu");
		}
		System.out.println("\tNacin koriscenja: " + autorskoDelo.getForma());
		print(autorskoDelo.getPodaciONaslovuAutorskogDela());
	}

	private void print(TZasnovanoDelo podaciONaslovuAutorskogDela) {
		System.out.println("\tNaslov: " + podaciONaslovuAutorskogDela.getNaslovAutorskogDela());
		System.out.println("\tAutor: " + podaciONaslovuAutorskogDela.getImeAutora());

	}


	private void print(TLice lice) {

		if (lice instanceof TFizickoLice) {
			TFizickoLice fizickoLice = (TFizickoLice) lice;
			System.out.println(fizickoLice.getIme() + " " + fizickoLice.getPrezime());
		}
		if (lice instanceof TPoslovnoLice) {
			TPoslovnoLice poslovnoLice = (TPoslovnoLice) lice;
			System.out.println(poslovnoLice.getPoslovnoIme());
		}

		print(lice.getAdresa());
		print(lice.getKontakt());
	}

	private void print(TAutor lice) {
		if (lice != null) {
			System.out.println(lice.getIme() + " " + lice.getPrezime());
			print(lice.getAdresa());
			print(lice.getKontakt());
			System.out.println(lice.getGodinaSmrti());
			System.out.println(lice.getZnakAutora());
		}
	}

	private void print(Adresa adresa) {

		System.out.println("Adresa: ");
		System.out.println("\t" + adresa.getDrzava() + "\n\t"
				+ adresa.getUlica() + " " + adresa.getBroj() + "\n\t" +
				adresa.getPostanskiBroj()  + " " + adresa.getGrad());
	}

	private void print(Kontakt kontakt) {
		System.out.println("Kontakt: ");
		System.out.println("\t" +
				kontakt.getTelefon() + "\n\t" +
				kontakt.getEPosta() + "\n\t" +
				kontakt.getFaks() + "\n\t"
		);
	}

    public static void main( String[] args ) {
    	AutorUnmarshalling test = new AutorUnmarshalling();
    	test.test();
    }
}
