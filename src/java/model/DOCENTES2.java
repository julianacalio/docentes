/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Funcionarios generated by hbm2java
 */
@Entity
@Table(name="DOCENTES2"
)
public class DOCENTES2  implements java.io.Serializable {


     private BigDecimal id;
     private String nome;
     private BigDecimal siape;
     private String url;
     private String centro;
     

    public DOCENTES2() {
    }
 
    @Id 
    @Column(name="ID")
    public BigDecimal getId() {
        return this.id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    @Column(name="NOME")
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Column(name="SIAPE")
    public BigDecimal getSiape() {
        return this.siape;
    }
    
    public void setSiape(BigDecimal siape) {
        this.siape = siape;
    }

    @Column(name="URL")
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
  
    @Column(name="CENTRO")
    public String getCentro() {
        return this.centro;
    }
    
    public void setCentro(String centro) {
        this.centro = centro;
    }


}
