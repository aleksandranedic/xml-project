package ftn.xml.zig.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private String meta;
    private String value;
    private String logicalOperator;
    private String operator;
}
