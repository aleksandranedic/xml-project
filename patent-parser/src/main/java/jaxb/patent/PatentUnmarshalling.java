package jaxb.patent;

import jaxb.SchemaValidationEventHandler;
import org.xml.sax.SAXException;
import rs.ac.uns.ftn.jaxb.patent.Adresa;
import rs.ac.uns.ftn.jaxb.patent.Kontakt;
import rs.ac.uns.ftn.jaxb.patent.TLice;
import rs.ac.uns.ftn.jaxb.patent.TFizickoLice;
import rs.ac.uns.ftn.jaxb.patent.TPoslovnoLice;
import rs.ac.uns.ftn.jaxb.patent.ZahtevZaPriznanjePatenta;

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
public class PatentUnmarshalling {
	
	public void test() {
		try {
			
			System.out.println("[INFO] Patent: JAXB unmarshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.jaxb.patent");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// XML schema validacija i podešavanje unmarshaller-a za XML schema validaciju
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("./data/p-1.xsd"));
			unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new SchemaValidationEventHandler());


			// Unmarshalling generiše objektni model na osnovu XML fajla 
			ZahtevZaPriznanjePatenta zahtev = (ZahtevZaPriznanjePatenta) unmarshaller.unmarshal(new File("./data/p-1.xml"));

			print(zahtev);



			
		}  catch (JAXBException e) {
			System.out.println("Can't bind element");
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		}
	}


	private void print(ZahtevZaPriznanjePatenta zahtev) {

		print(zahtev.getInformacijeOUstanovi());
		print(zahtev.getPopunjavaZavod());
		print(zahtev.getPopunjavaPodnosioc());

	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc popunjavaPodnosioc) {
		System.out.println("\n------------------------------");
		System.out.println("Popunjava podnosioc:");
		System.out.println("------------------------------");
		
		print(popunjavaPodnosioc.getNazivPatenta());
		print(popunjavaPodnosioc.getPodaciOPodnosiocu());
		print(popunjavaPodnosioc.getPodaciOPronalazacu());
		print(popunjavaPodnosioc.getPodaciOPunomocniku());
		print(popunjavaPodnosioc.getDostavljanje());
		print(popunjavaPodnosioc.getPrvobitnaPrijava());
		print(popunjavaPodnosioc.getRanijePrijave());


	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave ranijePrijave) {
		if (ranijePrijave.getPrijava().size() > 0) {
			System.out.println("Ranije prijave: ");
			for (ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave.Prijava prijava: ranijePrijave.getPrijava()) {
				System.out.println("\tBroj prijave: " + prijava.getBrojPrijave());
				System.out.println("\tDvoslovna oznaka: " + prijava.getDvoslovnaOznaka());
				System.out.println("\tDatum podnosenja: " + prijava.getDatumPodnosenja());
			}
		}
	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PrvobitnaPrijava prvobitnaPrijava) {
		System.out.println("Prvobitna prijava: ");
		System.out.println("\tBroj prijave: " + prvobitnaPrijava.getBrojPrijave());
		System.out.println("\tTip prijave: " + prvobitnaPrijava.getTipPrijave());
		System.out.println("\tDatum podnosenja: " + prvobitnaPrijava.getDatumPodnosenja());

	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.Dostavljanje dostavljanje) {
		System.out.println("Dostavljanje: ");
		System.out.println("\tNacin: " + dostavljanje.getNacin());
		print(dostavljanje.getAdresa());
	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku podaciOPunomocniku) {
		System.out.println("\nPunomocnik: ");

		System.out.print("\tTip:");
		if (podaciOPunomocniku.getTipPunomocnika().getPredstavnik() != null) {
			System.out.print(" predstavnik");
		}
		if (podaciOPunomocniku.getTipPunomocnika().getZaPrijem() != null) {
			System.out.print(" za_prijem");
		}
		if (podaciOPunomocniku.getTipPunomocnika().getZaZastupanje() != null) {
			System.out.print(" za_zastupanje");
		}
		System.out.print("\n");

		print(podaciOPunomocniku.getPunomocnik());
	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu podaciOPronalazacu) {
		System.out.println("\nPronalazac: ");
		print(podaciOPronalazacu.getPronalazac());
	}

	private void print(List<ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu> podaciOPodnosiocima) {
		System.out.println("\nPodnosioci: ");
		for (ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu podaci: podaciOPodnosiocima) {
			print(podaci.getPodnosioc());
		}
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

	private void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.NazivPatenta nazivPatenta) {
		System.out.println("Naziv patenta:");
		System.out.println("\tSrpski: " + nazivPatenta.getNazivNaSrpskom());
		System.out.println("\tEngleski: " + nazivPatenta.getNazivNaEngleskom());
	}

	private void print(ZahtevZaPriznanjePatenta.PopunjavaZavod popunjavaZavod) {
		System.out.println("\n------------------------------");
		System.out.println("Popunjava zavod:");
		System.out.println("------------------------------");
		System.out.println("\tBroj prijave: " + popunjavaZavod.getBrojPrijave());
		System.out.println("\tDatum prijema: " + popunjavaZavod.getDatumPrijema());
		System.out.println("\tPriznati datum podnosenja: " + popunjavaZavod.getPriznatiDatumPodnosenja());
	}

	private void print(ZahtevZaPriznanjePatenta.InformacijeOUstanovi info) {
		System.out.println("\n------------------------------");
		System.out.println("Ustanova:");
		System.out.println("------------------------------");
		System.out.println("Naziv: " + info.getNaziv());
		print(info.getAdresa());

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
    	PatentUnmarshalling test = new PatentUnmarshalling();
    	test.test();
    }
}
