package co.sena.adso.fincasapi.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cultivos")
public class Cultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(name = "ciclo_dias", nullable = false)
    private Integer cicloDias;

    @OneToMany(mappedBy = "cultivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FincaCultivo> fincaCultivos = new ArrayList<>();

    public Cultivo() {}

    public Cultivo(String nombre, String tipo, Integer cicloDias) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cicloDias = cicloDias;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getCicloDias() { return cicloDias; }
    public void setCicloDias(Integer cicloDias) { this.cicloDias = cicloDias; }
    public List<FincaCultivo> getFincaCultivos() { return fincaCultivos; }
    public void setFincaCultivos(List<FincaCultivo> fincaCultivos) { this.fincaCultivos = fincaCultivos; }
}
