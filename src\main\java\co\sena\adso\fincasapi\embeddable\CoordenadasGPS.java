package co.sena.adso.fincasapi.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CoordenadasGPS {

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column
    private Double altitud;

    public CoordenadasGPS() {}

    public CoordenadasGPS(Double latitud, Double longitud, Double altitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
    }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public Double getAltitud() { return altitud; }
    public void setAltitud(Double altitud) { this.altitud = altitud; }
}
