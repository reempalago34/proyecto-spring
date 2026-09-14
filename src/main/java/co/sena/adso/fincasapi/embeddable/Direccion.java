package co.sena.adso.fincasapi.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {

    @Column(length = 150)
    private String calle;

    @Column(length = 100)
    private String ciudad;

    @Column(length = 80)
    private String departamento;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    public Direccion() {}

    public Direccion(String calle, String ciudad, String departamento, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
}
