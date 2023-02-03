package ftn.xml.patent.repository;

import ftn.xml.patent.utils.AuthenticationUtilities;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Repository
public class PatentRepository {
    private final AuthenticationUtilities.ConnectionProperties conn;
    private final String COLLECTION_ID = "/db/patent";
    private final Unmarshaller unmarshaller;

    public PatentRepository() throws IOException, JAXBException {
        this.conn = AuthenticationUtilities.loadProperties();
        JAXBContext context = JAXBContext.newInstance("ftn.xml.patent.model");
        unmarshaller = context.createUnmarshaller();
    }


    public List<ZahtevZaPriznanjePatenta> retrieveAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaPriznanjePatenta> retrieveBasedOnTerm(String term) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[contains(., \"" + term + "\")]";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaPriznanjePatenta> retrieveAllWithoutResenje() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[not(exists(Zahtev_za_priznanje_patenta/Resenje))]";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaPriznanjePatenta> retrieveAllWithResenje() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[exists(Zahtev_za_priznanje_patenta/Resenje)]";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaPriznanjePatenta> retrieveAllWithResenjeStatus(String startDate, String endDate, String status) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //Date format is yyyy-MM-dd (example: 2023-02-01)
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[Zahtev_za_priznanje_patenta/Resenje/Status='" + status +
                "' and Zahtev_za_priznanje_patenta/Popunjava_zavod/Datum_prijema >= xs:date('" + startDate
                + "') and Zahtev_za_priznanje_patenta/Popunjava_zavod/Datum_prijema <= xs:date('" + endDate + "')]";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaPriznanjePatenta> retrieveAllWithinDatePeriod(String startDate, String endDate) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //Date format is yyyy-MM-dd (example: 2023-02-01)
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[Zahtev_za_priznanje_patenta/Popunjava_zavod/Datum_prijema >= xs:date('" + startDate
                + "') and Zahtev_za_priznanje_patenta/Popunjava_zavod/Datum_prijema <= xs:date('" + endDate + "')]";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaPriznanjePatenta> retrieveBasedOnTermList(String[] termList) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        StringBuilder xquery = new StringBuilder("let $files := collection(\"" + COLLECTION_ID + "\") return $files[");
        for (int i = 0; i < termList.length; i++) {
            if (i == 0) {
                xquery.append("contains(., \"").append(termList[i]).append("\")");
            } else {
                xquery.append(" and contains(., \"").append(termList[i]).append("\")");
            }
        }
        xquery.append("]");
        return retrieveBasedOnXQuery(xquery.toString());
    }


    public List<ZahtevZaPriznanjePatenta> retrieveBasedOnBrojPrijave(String Broj_prijave) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[Zahtev_za_priznanje_patenta/Popunjava_zavod/Broj_prijave = \"" + Broj_prijave + "\"]";
        return retrieveBasedOnXQuery(xquery);
    }


    private List<ZahtevZaPriznanjePatenta> retrieveBasedOnXQuery(String xquery) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
        createConnection();
        Collection col = null;
        List<XMLResource> resources = new ArrayList<>();
        try {
            col = DatabaseManager.getCollection(conn.uri + COLLECTION_ID);
            col.setProperty(OutputKeys.INDENT, "yes");
            XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");

            ResourceSet result = xqs.query(xquery);
            for (ResourceIterator i = result.getIterator(); i.hasMoreResources(); ) {
                Resource resource = i.nextResource();
                resources.add((XMLResource) resource);
            }
        } finally {
            closeConnection(col, resources);
        }
        List<ZahtevZaPriznanjePatenta> list = resources.stream().map(res -> {
            try {
                return (ZahtevZaPriznanjePatenta) unmarshaller.unmarshal(res.getContentAsDOM());
            } catch (JAXBException | XMLDBException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        return list;
    }


    public ZahtevZaPriznanjePatenta retrieve(String documentName) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + COLLECTION_ID);
            col.setProperty(OutputKeys.INDENT, "yes");
            res = (XMLResource) col.getResource(documentName);
            if (res == null) {
                return null;
            } else {
                return (ZahtevZaPriznanjePatenta) unmarshaller.unmarshal(res.getContentAsDOM());
            }
        } finally {
            closeConnection(col, res);
        }
    }

    public void remove(String documentName) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + COLLECTION_ID);
            col.setProperty(OutputKeys.INDENT, "yes");
            res = (XMLResource) col.getResource(documentName);
            col.removeResource(res);
            if (res == null) {
                throw new RuntimeException("No document with given name.");
            } else {
                col.removeResource(res);

            }
        } finally {
            closeConnection(col, res);
        }
    }

    public void store(String documentId, OutputStream os) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = getOrCreateCollection();
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
            res.setContent(os);
            col.storeResource(res);
        } finally {
            closeConnection(col, res);
        }
    }

    private void createConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
        Class<?> cl = Class.forName(this.conn.driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    private static void closeConnection(Collection col, XMLResource res) {
        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException xe) {
                xe.printStackTrace();
            }
        }
        if (col != null) {
            try {
                col.close();
            } catch (XMLDBException xe) {
                xe.printStackTrace();
            }
        }
    }

    private static void closeConnection(Collection col, List<XMLResource> resources) {
        for (XMLResource res : resources) {
            if (res != null) {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        if (col != null) {
            try {
                col.close();
            } catch (XMLDBException xe) {
                xe.printStackTrace();
            }
        }
    }

    private Collection getOrCreateCollection() throws XMLDBException {
        return getOrCreateCollection(COLLECTION_ID, 0);
    }

    private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {
        Collection col = DatabaseManager.getCollection(this.conn.uri + collectionUri, this.conn.user, this.conn.password);

        if (col == null) {
            if (collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }
            String pathSegments[] = collectionUri.split("/");
            if (pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();
                for (int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }
                Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);
                if (startCol == null) {
                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);
                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    col.close();
                    parentCol.close();

                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }

    public String getNextBrojPrijave() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + COLLECTION_ID);
            col.setProperty(OutputKeys.INDENT, "yes");
            List<String> brojeviPrijave = Arrays.stream(col.listResources()).toList();

            String brojPrijave = null;

            while (brojPrijave == null || brojeviPrijave.contains(brojPrijave)) {
                brojPrijave = getRandomBrojPrijave();
            }
            return brojPrijave.split(Pattern.quote("."))[0];

        } finally {
            closeConnection(col, res);
        }
    }

    private String getRandomBrojPrijave() {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            randomString.append(random.nextInt(10));
        }
        return "P-" + randomString.toString() + ".xml";
    }
}
