package co.sena.adso.fincasapi.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Las Entidades (Entities) representan una tabla en la base de datos relacional.
 * Cada instancia de esta clase será una "fila" o "registro" en la tabla 'fincas'.
 * 
 * @Entity: Le indica a JPA (Hibernate) que esta clase se va a mapear a una base de datos.
 * @Table(name = "fincas"): Especifica el nombre exacto de la tabla en PostgreSQL.
 */
@Entity
@Table(name = "fincas")
public class Finca {

    /**
     * @Id: Define que este campo es la Llave Primaria (Primary Key).
     * @GeneratedValue: Indica que la base de datos generará este valor automáticamente
     * (strategy = GenerationType.IDENTITY significa que usará auto-incremento, como SERIAL).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column: Opcional, pero permite definir restricciones de la base de datos.
     * nullable = false significa que este campo NO puede ser NULL (es obligatorio).
     * length = 100 limita el tamaño del texto a 100 caracteres en la base de datos (VARCHAR(100)).
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String propietario;

    @Column(nullable = false, length = 100)
    private String vereda;

    @Column(nullable = false, length = 100)
    private String municipio;

    @Column(nullable = false)
    private Double hectareas;

    /**
     * Relación Uno-a-Muchos (1 Finca tiene Muchos FincaCultivos).
     * mappedBy = "finca": Significa que la relación está controlada por la variable "finca" en la otra clase.
     * cascade = CascadeType.ALL: Si se borra la finca, se borran automáticamente sus cultivos.
     */
    @OneToMany(mappedBy = "finca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FincaCultivo> fincaCultivos = new ArrayList<>();

    // JPA requiere un constructor vacío (sin argumentos) por defecto
    public Finca() {}

    // Constructor con argumentos para facilitar la creación desde el Service o Seeder
    public Finca(String nombre, String propietario, String vereda, String municipio, Double hectareas) {
        this.nombre = nombre;
        this.propietario = propietario;
        this.vereda = vereda;
        this.municipio = municipio;
        this.hectareas = hectareas;
    }

    // Getters y Setters: Necesarios para que Spring (y Jackson) puedan leer y escribir en estos campos.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPropietario() { return propietario; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
    public String getVereda() { return vereda; }
    public void setVereda(String vereda) { this.vereda = vereda; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public Double getHectareas() { return hectareas; }
    public void setHectareas(Double hectareas) { this.hectareas = hectareas; }
    public List<FincaCultivo> getFincaCultivos() { return fincaCultivos; }
    public void setFincaCultivos(List<FincaCultivo> fincaCultivos) { this.fincaCultivos = fincaCultivos; }
}
