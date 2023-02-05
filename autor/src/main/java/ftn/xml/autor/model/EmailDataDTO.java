package ftn.xml.autor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDataDTO {
    private String content;
    private String recipient;
    private String subject;
    private String documentPath;
}
