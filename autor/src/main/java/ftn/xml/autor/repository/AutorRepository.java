package ftn.xml.autor.repository;

import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.utils.AuthenticationUtilities;
import ftn.xml.autor.utils.PrettyPrint;
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
public class AutorRepository {
    private AuthenticationUtilities.ConnectionProperties conn;
    private final String COLLECTION_ID = "/db/autor";
    private final Unmarshaller unmarshaller;

    AutorRepository() throws IOException, JAXBException {
        this.conn = AuthenticationUtilities.loadProperties();
        JAXBContext context = JAXBContext.newInstance("ftn.xml.autor.model");
        this.unmarshaller = context.createUnmarshaller();
    }

    public List<ZahtevZaIntelektualnuSvojinu> retrieveAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaIntelektualnuSvojinu> retrieveBasedOnTerm(String term) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[contains(., \"" + term + "\")]";
        return retrieveBasedOnXQuery(xquery);
    }

    public List<ZahtevZaIntelektualnuSvojinu> retrieveBasedOnTermList(String[] termList) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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


    public List<ZahtevZaIntelektualnuSvojinu> retrieveBasedOnBrojPrijave(String Broj_prijave) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String xquery = "let $files := collection(\"" + COLLECTION_ID + "\") return $files[ZahtevZaIntelektualnuSvojinu/Popunjava_zavod/Broj_prijave = \"" + Broj_prijave + "\"]";
        return retrieveBasedOnXQuery(xquery);
    }


    private List<ZahtevZaIntelektualnuSvojinu> retrieveBasedOnXQuery(String xquery) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
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
        List<ZahtevZaIntelektualnuSvojinu> list = resources.stream().map(res -> {
            try {
                return (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(res.getContentAsDOM());
            } catch (JAXBException | XMLDBException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        return list;
    }

    public ZahtevZaIntelektualnuSvojinu retrieve(String documentId) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + COLLECTION_ID);
            col.setProperty(OutputKeys.INDENT, "yes");
            res = (XMLResource) col.getResource(documentId);
            if (res == null) {
                return null;
            } else {

                return (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(res.getContentAsDOM());
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
            col = getOrCreateCollection(COLLECTION_ID);
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

    private Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }

    private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

        Collection col = DatabaseManager.getCollection(this.conn.uri + collectionUri, this.conn.user, this.conn.password);

        // create the collection if it does not exist
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
        return "A-" + randomString.toString() + ".xml";
    }
}
