package jaxb.patent;



import rs.ac.uns.ftn.jaxb.patent.ZahtevZaPriznanjePatenta;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/** 
 * Primer 2.
 * 
 * Primer demonstrira "unmarshalling" tj. konverziju iz XML fajla
 * u objektni model, izmenu objektnog modela i "marshalling" načinjenih
 * izmena, tj. njegovu serijalizaciju nazad u XML fajl.
 * 
 */
public class PatentMarshalling {
	
	public void test() throws Exception {
		try {
			System.out.println("[INFO] Patent: JAXB unmarshalling/marshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.jaxb.patent");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			ZahtevZaPriznanjePatenta zahtev = (ZahtevZaPriznanjePatenta) unmarshaller.unmarshal(new File("./data/p-1.xml"));
			
			// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
			Marshaller marshaller = context.createMarshaller();
			
			// Podešavanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Umesto System.out-a, može se koristiti FileOutputStream
			marshaller.marshal(zahtev, System.out);
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
    public static void main( String[] args ) throws Exception {
    	PatentMarshalling test = new PatentMarshalling();
    	test.test();
    }
}
