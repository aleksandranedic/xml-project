package ftn.xml.autor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private String meta;
    private String value;
    private String logicalOperator;
    private String operator;

    public void setLogicalOperator(String logicalOperator) {
        if (logicalOperator.equals("ILI")) {
            this.logicalOperator = "||";
        } else {
            this.logicalOperator = "&&";
        }
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
