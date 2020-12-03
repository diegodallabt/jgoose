package br.unioeste.jgoose.model;

import java.util.ArrayList;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TracedRequisitos extends TracedElement{
    private String code; 
    private String label;
    private int id;
    private String fase = "Requisitos Finais";
    private String classe = "Requisitos";
    private String abreviacao;
    private  String model;
    private  String[] vetor = new String[2];
    private  ArrayList <String[]> listConcflicts;
    
    private static int sequence = 0;
    
    public TracedRequisitos(String code, String label, String abreviacao) {
        this.listConcflicts = new ArrayList<>();        
        this.code = code;
        this.label = label;
        this.id = sequence++;
        this.abreviacao = "["+abreviacao+" "+id+"]";
    }

    public TracedRequisitos() {
        this.listConcflicts = new ArrayList<>();
        this.code = null;
        this.label = null;
        this.id = sequence++;
        this.model = null;
        this.abreviacao = null;
    }
    
    @Override
    public Integer getId(){
        return id;
    }
    
    @Override
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getCode(){
        return code;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }
    
    @Override
    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    public String getLabel(){
        return label;
    }
    
    @Override
    public String getClasse(){
        return classe;
    }
    
    @Override
    public String getFase(){
        return fase;
    }
    
    @Override
    public void setAbreviacao(String req) {
        this.abreviacao = req + id +"]";
    }
    
    @Override
    public String getAbreviacao(){
        return abreviacao;
    }
    
    @Override
    public void setVetConflict(String vetor[]){
        this.vetor = vetor;
        listConcflicts.add(vetor);
    }
    
    @Override
    public ArrayList<String[]> getListConcflicts(){
        return listConcflicts;
    }
    
    public String[] getVetConflict(){
        return vetor;
    }
    
}
