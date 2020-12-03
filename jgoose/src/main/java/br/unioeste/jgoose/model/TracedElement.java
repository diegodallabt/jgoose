/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.model;

import java.util.ArrayList;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TracedElement {
    private String code; 
    private String label;
    private Integer id;
    private String fase;
    private String classe;
    private String abreviacao;
    private String model;
    private  String[] vetor = new String[2];
    private  ArrayList <String[]> listConcflicts;
    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public void setVetConflict(String[] vetor) {
        this.vetor = vetor;
        listConcflicts.add(vetor);
    }

    public ArrayList<String[]> getListConcflicts() {
        return listConcflicts;
    }

    public void setListConcflicts(ArrayList<String[]> listConcflicts) {
        this.listConcflicts = listConcflicts;
    }


}
