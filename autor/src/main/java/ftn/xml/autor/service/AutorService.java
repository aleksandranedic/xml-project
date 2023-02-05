package ftn.xml.autor.service;

import ftn.xml.autor.dto.ResenjeDTO;
import ftn.xml.autor.dto.Zahtev;
import ftn.xml.autor.dto.ZahtevMapper;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.repository.AutorRepository;
import ftn.xml.autor.repository.RdfRepository;
import ftn.xml.autor.utils.SchemaValidationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Service
public class AutorService {
    public static final String CONTEXT_PATH = "ftn.xml.autor.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/autor.xsd";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private final AutorRepository repository;
    private final RdfRepository rdfRepository;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    private final ZahtevMapper mapper;
    private final EmailService emailService;
    private final TransformationService transformationService;

    @Autowired
    public AutorService(AutorRepository repository, RdfRepository rdfRepository, ZahtevMapper mapper, EmailService emailService, TransformationService transformationService) throws SAXException, JAXBException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.mapper = mapper;
        this.emailService = emailService;
        this.transformationService = transformationService;

        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);

        unmarshaller = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(SCHEMA_PATH));
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new SchemaValidationEventHandler());

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    public ZahtevZaIntelektualnuSvojinu getZahtev(String brojPrijave) {
        try {
            return repository.retrieve(brojPrijave + ".xml");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void unmarshall() throws JAXBException {
        unmarshall("./src/main/resources/data/xml/autor.xml");
    }

    public ZahtevZaIntelektualnuSvojinu unmarshall(String path) throws JAXBException {
        return (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File(path));
    }

    public OutputStream save(String path) throws Exception {
        ZahtevZaIntelektualnuSvojinu zahtev = (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File("./src/main/resources/data/xml/" + path));
        return save(zahtev);
    }

    public OutputStream save(ZahtevZaIntelektualnuSvojinu zahtev) throws Exception {
        OutputStream os = marshal(zahtev);
        repository.store(zahtev.getPopunjavaZavod().getBrojPrijave() + ".xml", os);
        return os;
    }

    public OutputStream marshal(ZahtevZaIntelektualnuSvojinu zahtev) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(zahtev, os);
        return os;
    }

    public void addRdf(String file_path) {
        try {
            ZahtevZaIntelektualnuSvojinu zahtev = getZahtev(file_path);
            addRdf(zahtev);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPdf(String brojPrijave) throws JAXBException {
        return this.transformationService.toPDF(marshal(getZahtev(brojPrijave)), brojPrijave + ".pdf");
    }


    private void addRdf(ZahtevZaIntelektualnuSvojinu zahtev) throws JAXBException, TransformerException {
        ByteArrayOutputStream zahtev_xml_out = (ByteArrayOutputStream) marshal(zahtev);
        InputStream zahtev_input = new ByteArrayInputStream(zahtev_xml_out.toByteArray());
        ByteArrayOutputStream zahtev_output = new ByteArrayOutputStream();
        rdfRepository.extractMetadata(zahtev_input, zahtev_output);
        rdfRepository.writeRdf(zahtev_output.toString());
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAll();
    }

    public void deleteRequest(int brojPrijave) throws Exception {
        repository.remove(brojPrijave + ".xml");
    }

    public void save(Zahtev zahtev) throws Exception {
        ZahtevZaIntelektualnuSvojinu zahtevZaIntelektualnuSvojinu = mapper.parseZahtev(zahtev);
        save(zahtevZaIntelektualnuSvojinu);
        addRdf(zahtevZaIntelektualnuSvojinu);
        System.out.println(zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave());
    }

    public void updateRequest(ResenjeDTO resenje) {
        ZahtevZaIntelektualnuSvojinu zahtev = getZahtev(resenje.getBrojPrijave());
        try {
            zahtev.setResenje(mapper.parseResenje(resenje));
            save(zahtev);
            String email = zahtev.getPopunjavaPodnosilac().getPodnosilac().getKontakt().getEPosta();
            //TODO create new xsl for zahtev with resenje and pass the path here
//        String documentPath="D:\\Fourth Year\\XML_WebServices\\XML_PROJEKAT_GIT\\xml-project\\autor\\src\\main\\resources\\data\\gen\\autor.pdf";
//        EmailDataDTO emailDataDTO= EmailService.buildEmailDTO(email,documentPath);
//        emailService.sendEmail(emailDataDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAllResolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        list.stream().filter(zahtevZaIntelektualnuSvojinu -> {
            try {
                zahtevZaIntelektualnuSvojinu.getResenje();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        //TODO:Filtriraj da li ima resenje
        return list;
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAllUnresolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        list.stream().filter(zahtevZaIntelektualnuSvojinu -> {
            try {
                zahtevZaIntelektualnuSvojinu.getResenje();
                return false;
            } catch (Exception e) {
                return true;
            }
        });
        //TODO:Filtriraj da nema resenje
        return list;
    }

    public String getHtml(String brojPrijave) throws JAXBException {
        return this.transformationService.toXHTML(marshal(getZahtev(brojPrijave)), brojPrijave + ".html");
    }
    public String getHtmlString(String brojPrijave) {

        String filePath = FILE_FOLDER + brojPrijave + ".html";

        String content;
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                content = Files.readString(file.toPath(), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            try {
                getHtml(brojPrijave);

            } catch (JAXBException e) {
                throw new RuntimeException(e.getMessage());
            }
            return getHtmlString(brojPrijave);
        }
        return content;
    }
}
