package br.unioeste.jgoose.model;

import java.util.ArrayList;

/**
 * @author Victor Augusto Pozzan
 */
public class TracedStakeholders extends TracedElement{
    private  String code; 
    private  String label;
    private  Integer id;
    private  String fase = " ";
    private  String classe = "Stakeholder";
    private  String model;
    private  String abreviacao;
    private  String[] vetor = new String[2];
    private  ArrayList <String[]> listConcflicts;
    
    private static int sequence = 0;
  
    public TracedStakeholders(String code, String label) {
        this.listConcflicts = new ArrayList<>();
        this.code = code;
        this.label = label;
        this.id = sequence++;
        this.abreviacao = "[STK "+id+"]";
    } 
    
    public TracedStakeholders(){
        this.listConcflicts = new ArrayList<>();
        this.code = null;
        this.model = null;
        this.label = null;
        this.fase = " ";
        this.id = sequence++;
        this.abreviacao = "[STK "+id+"]";
    }
  
    @Override
    public Integer getId(){
        return id;
    }
    
    @Override
    public void setCode(String code){
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
    
    @Override
    public void setLabel(String label){
        this.label = label; 
    }
    
    @Override
    public String getLabel(){
        return label;
    }
    
    @Override
    public void setClasse(String classe){
        this.classe = classe;
    }
    
    @Override
    public String getClasse(){
        return classe;
    }
    
    @Override
    public void setFase(String fase){
        this.fase = fase;
    }
    
    @Override
    public String getFase(){
        return fase;
    }
    
    @Override
    public void setAbreviacao(String abreviacao){
        this.abreviacao = abreviacao;
    }
    
    @Override
    public String getAbreviacao(){
        return abreviacao;
    }
}
