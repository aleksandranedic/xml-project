package ftn.xml.patent.utils;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.stream.StreamSource;

public class OutputStreamSource extends StreamSource {

    private OutputStream outputStream;

    public OutputStreamSource(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getInputStream() {
        throw new UnsupportedOperationException();
    }


    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
