package ftn.xml.zig.service;

import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
public class TransformationService {
    public static final String PDF_XSL_FILE = "./src/main/resources/data/xsl-fo/zig.xsl";
    public static final String HTML_XSL_FILE = "./src/main/resources/data/xhtml/zig.xsl";
    private final FopFactory fopFactory;
    private TransformerFactory transformerFactory;
    private static final DocumentBuilderFactory documentFactory;

    static {
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
    }
    public TransformationService() throws IOException, SAXException {
        fopFactory = FopFactory.newInstance(new File("./src/main/resources/fop.xconf"));
        transformerFactory = new TransformerFactoryImpl();
    }

    public String toPDF(String xmlFilePath, String pdfFilePath) {
        try {
            File xslFile = new File(PDF_XSL_FILE);
            StreamSource transformSource = new StreamSource(xslFile);
            StreamSource source = new StreamSource(new File(xmlFilePath));
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
            Result res = new SAXResult(fop.getDefaultHandler());
            xslFoTransformer.transform(source, res);

            File pdfFile = new File(pdfFilePath);
            if (!pdfFile.getParentFile().exists()) {
                System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
                pdfFile.getParentFile().mkdir();
            }
            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
            out.write(outStream.toByteArray());
            out.close();
            return "Successfully converted " + xmlFilePath + " to " + pdfFilePath;
        } catch (SAXException | IOException | TransformerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public String toXHTML(String xmlFilePath, String htmlFilePath) {
        try {
            transformerFactory = TransformerFactory.newInstance();
            StreamSource transformSource = new StreamSource(new File(HTML_XSL_FILE));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

            DOMSource source = new DOMSource(buildDocument(xmlFilePath));
            StreamResult result = new StreamResult(new FileOutputStream(htmlFilePath));
            transformer.transform(source, result);
            return "Successfully converted " + xmlFilePath + " to " + htmlFilePath;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public org.w3c.dom.Document buildDocument(String filePath) {
        org.w3c.dom.Document document = null;
        try {
            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            document = builder.parse(new File(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return document;
    }
}
