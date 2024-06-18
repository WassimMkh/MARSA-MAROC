package org.hospital.modetravail.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NormeProductiviteRequest {
    private Long id;
    private Long traficId;
    private Long mainTheoriqueId;
    private Long modeId;
    private int norme;
    private String sens;
    private String suiviProduit;

}

