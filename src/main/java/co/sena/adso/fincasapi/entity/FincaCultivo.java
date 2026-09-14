package co.sena.adso.fincasapi.entity;

import co.sena.adso.fincasapi.enums.Estado;
import co.sena.adso.fincasapi.enums.Temporada;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "finca_cultivo")
public class FincaCultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finca_id", nullable = false)
    private Finca finca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultivo_id", nullable = false)
    private Cultivo cultivo;

    @Column(name = "area_sembrada_ha", nullable = false)
    private Double areaSembradaHa;

    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Temporada temporada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    public FincaCultivo() {}

    public FincaCultivo(Finca finca, Cultivo cultivo, Double areaSembradaHa, LocalDate fechaSiembra, Temporada temporada, Estado estado) {
        this.finca = finca;
        this.cultivo = cultivo;
        this.areaSembradaHa = areaSembradaHa;
        this.fechaSiembra = fechaSiembra;
        this.temporada = temporada;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Finca getFinca() { return finca; }
    public void setFinca(Finca finca) { this.finca = finca; }
    public Cultivo getCultivo() { return cultivo; }
    public void setCultivo(Cultivo cultivo) { this.cultivo = cultivo; }
    public Double getAreaSembradaHa() { return areaSembradaHa; }
    public void setAreaSembradaHa(Double areaSembradaHa) { this.areaSembradaHa = areaSembradaHa; }
    public LocalDate getFechaSiembra() { return fechaSiembra; }
    public void setFechaSiembra(LocalDate fechaSiembra) { this.fechaSiembra = fechaSiembra; }
    public Temporada getTemporada() { return temporada; }
    public void setTemporada(Temporada temporada) { this.temporada = temporada; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}
