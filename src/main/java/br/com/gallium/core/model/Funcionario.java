package br.com.gallium.core.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
@Table(name = "TB_FUNC")
@SequenceGenerator(name = "funcionario", sequenceName = "SQ_FUNC", allocationSize = 1)
public class Funcionario {

    @Id
    @GeneratedValue(generator = "funcionario", strategy = GenerationType.SEQUENCE)
    @Column(name = "CD_FUNC")
    private int codFuncionario;

    @Column(name = "NM_FUNC", nullable = false, length = 50)
    private String nomeFuncionario;

    @Column(name = "CPF_FUNC", nullable = false)
    private long cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "CARGO_FUNC", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="CD_LOJA", nullable = false)
    @JsonIgnore //Parar a chamada recursiva da propiedade
    private LojaDeRoupa lojaDeRoupa;

    public Funcionario() {
    }

    public Funcionario(String nomeFuncionario, long cpf, Cargo cargo ) {
        this.nomeFuncionario = nomeFuncionario;
        this.cpf = cpf;
        this.cargo = cargo;
    }



    public int getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public LojaDeRoupa getLojaDeRoupa() {
        return lojaDeRoupa;
    }

    public void setLojaDeRoupa(LojaDeRoupa lojaDeRoupa) {
        this.lojaDeRoupa = lojaDeRoupa;
    }

}
