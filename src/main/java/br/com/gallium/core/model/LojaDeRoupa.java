package br.com.gallium.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_LOJA")
@SequenceGenerator(name = "loja", sequenceName = "SQ_LOJA_ROUPA", allocationSize = 1)
public class LojaDeRoupa {

    @Id
    @Column(name = "CD_LOJA")
    @GeneratedValue(generator = "loja", strategy = GenerationType.SEQUENCE)
    private int codLoja;

    @Column(name = "NM_LOJA",
            nullable = false,
            length = 50)
    private String nomeLoja;

    @Column(name = "QNTD_LOJA", nullable = false)
    private int qntFuncionarios;

    @Column(name = "END_LOJA", nullable = false)
    private String endereco;

    @Column(name = "TEL_LOJA")
    private long telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAT_LOJA", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "lojaDeRoupa", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

    public LojaDeRoupa() {}

    public LojaDeRoupa(String nomeLoja,
                       int qntFuncionarios,
                       String endereco,
                       long telefone,
                       Categoria categoria) {
        this.nomeLoja = nomeLoja;
        this.qntFuncionarios = qntFuncionarios;
        this.endereco = endereco;
        this.telefone = telefone;
        this.categoria = categoria;
    }

    public int getCodLoja() {
        return codLoja;
    }

    public void setCodLoja(int codLoja) {
        this.codLoja = codLoja;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public int getQntFuncionarios() {
        return qntFuncionarios;
    }

    public void setQntFuncionarios(int qntFuncionarios) {
        this.qntFuncionarios = qntFuncionarios;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereço) {
        this.endereco = endereço;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        funcionario.setLojaDeRoupa(this);
    }
}
