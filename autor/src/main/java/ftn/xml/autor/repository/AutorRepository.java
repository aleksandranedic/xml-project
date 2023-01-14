package ftn.xml.autor.repository;

import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.utils.AuthenticationUtilities;
import ftn.xml.autor.utils.PrettyPrint;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.IOException;
import java.io.OutputStream;

@Repository
public class AutorRepository {
    private AuthenticationUtilities.ConnectionProperties conn;
    private final String collectionId = "/db/autor";

    AutorRepository() throws IOException {
        this.conn = AuthenticationUtilities.loadProperties();
    }

    public ZahtevZaIntelektualnuSvojinu retrieve(String documentId) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = DatabaseManager.getCollection(conn.uri + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");
            res = (XMLResource) col.getResource(documentId);
            if (res == null) {
                System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
            } else {
                JAXBContext context = JAXBContext.newInstance("ftn.xml.autor.model");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ZahtevZaIntelektualnuSvojinu zahtev = (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(res.getContentAsDOM());
                System.out.println("[INFO] Showing the document as JAXB instance: ");
                PrettyPrint.printZahtev(zahtev);
                return zahtev;
            }
        } finally {
            closeConnection(col, res);
        }
        return null;
    }

    public void store(String documentId, OutputStream os) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        try {
            col = getOrCreateCollection(collectionId);
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());
            col.storeResource(res);
            System.out.println("[INFO] Done.");
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

                    // child collection does not exist

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);

                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");

                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
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
}
