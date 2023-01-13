package ftn.xml.autor.service;

import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.repository.AutorRepository;
import ftn.xml.autor.utils.PrettyPrint;
import ftn.xml.autor.utils.SchemaValidationEventHandler;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    private FopFactory fopFactory;
    private TransformerFactory transformerFactory;
    private static DocumentBuilderFactory documentFactory;
    public static final String INPUT_FILE = "./src/main/resources/data/xml/a-1.xml";
    public static final String XSL_FILE = "./src/main/resources/data/xsl-fo/autor.xsl";
    public static final String HTML_XSL_FILE = "./src/main/resources/data/xhtml/autor.xsl";
    public static final String OUTPUT_FILE_PDF = "./src/main/resources/data/gen/autor.pdf";
    public static final String OUTPUT_FILE_HTML = "./src/main/resources/data/gen/autor.html";

    static {
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
    }

    public AutorService() throws IOException, SAXException {
        fopFactory = FopFactory.newInstance(new File("./src/main/resources/fop.xconf"));
        transformerFactory = new TransformerFactoryImpl();
    }

    public void getZahtev(String documentId) {
        try {
            autorRepository.retrieve(documentId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void unmarshalling() {
        try {
            System.out.println("[INFO] Zig: JAXB unmarshalling.\n");
            JAXBContext context = JAXBContext.newInstance("ftn.xml.autor.model");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./src/main/resources/data/xsd/autor.xsd"));
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new SchemaValidationEventHandler());
            ZahtevZaIntelektualnuSvojinu zahtev = (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File(INPUT_FILE));
            PrettyPrint.printZahtev(zahtev);
        } catch (JAXBException e) {
            System.out.println("Can't bind element");
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toPDF() {
        try {
            System.out.println("[INFO] XSLFOTransformer");
            // Point to the XSL-FO file
            File xslFile = new File(XSL_FILE);
            // Create transformation source
            StreamSource transformSource = new StreamSource(xslFile);
            // Initialize the transformation subject
            StreamSource source = new StreamSource(new File(INPUT_FILE));
            // Initialize user agent needed for the transformation
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            // Create the output stream to store the results
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // Initialize the XSL-FO transformer object
            Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
            // Construct FOP instance with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
            // Resulting SAX events
            Result res = new SAXResult(fop.getDefaultHandler());
            // Start XSLT transformation and FOP processing
            xslFoTransformer.transform(source, res);
            // Generate PDF file
            File pdfFile = new File(OUTPUT_FILE_PDF);
            if (!pdfFile.getParentFile().exists()) {
                System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
                pdfFile.getParentFile().mkdir();
            }
            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
            out.write(outStream.toByteArray());
            System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
            out.close();
            System.out.println("[INFO] End.");
        } catch (SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void toXHTML() {
        try {
            transformerFactory = TransformerFactory.newInstance();
            StreamSource transformSource = new StreamSource(new File(HTML_XSL_FILE));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Generate XHTML
            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

            // Transform DOM to HTML
            DOMSource source = new DOMSource(buildDocument(INPUT_FILE));
            StreamResult result = new StreamResult(new FileOutputStream(OUTPUT_FILE_HTML));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("EX");
            e.printStackTrace();
        }
    }

    public org.w3c.dom.Document buildDocument(String filePath) {
        org.w3c.dom.Document document = null;
        try {
            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            document = builder.parse(new File(filePath));
            if (document != null)
                System.out.println("[INFO] File parsed with no errors.");
            else
                System.out.println("[WARN] Document is null.");
        } catch (Exception e) {
            return null;
        }
        return document;
    }

    public void marshalling() {
        try {
            System.out.println("[INFO] Zig: JAXB unmarshalling/marshalling.\n");
            JAXBContext context = JAXBContext.newInstance("ftn.xml.autor.model");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ZahtevZaIntelektualnuSvojinu zahtev = (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File("./src/main/resources/data/xml/a-1.xml"));
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            OutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(zahtev, os);
            autorRepository.store("2.xml", os);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
