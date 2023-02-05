package ftn.xml.autor.service;

import ftn.xml.autor.model.izvestaj.Izvestaj;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.*;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
public class IzvestajService {

    public static final String PDF_XSL_FILE = "./src/main/resources/data/xsl-fo/izvestaj.xsl";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private static final String TARGET_FOLDER = "./target/classes/data/files/";

    public static final String CONTEXT_PATH = "ftn.xml.patent.model.izvestaj";

    private final FopFactory fopFactory;
    private TransformerFactory transformerFactory;
    private static final DocumentBuilderFactory documentFactory;
    private final Marshaller marshaller;


    static {
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
    }

    public IzvestajService() throws IOException, SAXException, JAXBException {
        fopFactory = FopFactory.newInstance(new File("./src/main/resources/fop.xconf"));
        transformerFactory = new TransformerFactoryImpl();

        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }


    public String getIzvestajPdf(Izvestaj izvestaj, String fileName) throws JAXBException {
        return toPDF(marshal(izvestaj), fileName);
    }


    public OutputStream marshal(Izvestaj izvestaj) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(izvestaj, os);
        return os;
    }

    public String toPDF(String xmlFilePath, String pdfFilePath) {
        try {
            StreamSource source = new StreamSource(new File(xmlFilePath));
            toPDF(pdfFilePath, source);
            return "Successfully converted " + xmlFilePath + " to " + pdfFilePath;
        } catch (SAXException | IOException | TransformerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String toPDF(OutputStream outputStream, String pdfFilePath) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream)outputStream).toByteArray());
            StreamSource source = new StreamSource(inputStream);
            toPDF(pdfFilePath, source);
            return pdfFilePath;
        } catch (SAXException | IOException | TransformerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void toPDF(String pdfFilePath, StreamSource source) throws FOPException, TransformerException, IOException {
        File xslFile = new File(PDF_XSL_FILE);
        StreamSource transformSource = new StreamSource(xslFile);
        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
        Result res = new SAXResult(fop.getDefaultHandler());
        xslFoTransformer.transform(source, res);

        toPDF(FILE_FOLDER +pdfFilePath, outStream);
        toPDF(TARGET_FOLDER + pdfFilePath, outStream);

    }

    private static void toPDF(String pdfFilePath, ByteArrayOutputStream outStream) throws IOException {
        File pdfFile = new File( pdfFilePath);
        if (!pdfFile.getParentFile().exists()) {
            pdfFile.getParentFile().mkdir();
        }
        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        out.write(outStream.toByteArray());
        out.close();
    }
}
