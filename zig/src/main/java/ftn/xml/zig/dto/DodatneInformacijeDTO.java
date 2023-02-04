package ftn.xml.zig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DodatneInformacijeDTO {
    public List<klasaRobeIUslaga> klasa;
    public String zatrazenoPravoPrvenstaIOsnov;
}
