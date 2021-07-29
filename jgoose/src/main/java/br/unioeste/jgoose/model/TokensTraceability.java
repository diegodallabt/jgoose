package br.unioeste.jgoose.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Augusto Pozzan
 */
public class TokensTraceability {
    // Estrutura de dados que armazena elementos e ligações
    private List<TracedElement> stakeholders;
    private List<TracedElement> informacaoOrg;
    private List<TracedElement> requisitos;
    private List<TracedElement> informacaoExt;
    private List<TracedElement> objetivoDoSistema;
    private List<TracedElement> atorSistema;
    
    public TokensTraceability(List<TracedElement> stakeholders,
            List<TracedElement> informacaoOrg, 
            List<TracedElement> requisitos,
            List<TracedElement> informacaoExt,
            List<TracedElement> objSistema,
            List<TracedElement> atorSistema) {
        this.stakeholders = stakeholders;
        this.informacaoOrg = informacaoOrg;
        this.requisitos = requisitos;
        this.informacaoExt = informacaoExt;
        this.objetivoDoSistema = objSistema;
        this.atorSistema = atorSistema;
    }
    
    public TokensTraceability() {
        this.stakeholders = new ArrayList<>();
        this.informacaoOrg = new ArrayList<>();
        this.requisitos = new ArrayList<>();
        this.informacaoExt = new ArrayList<>();
        this.objetivoDoSistema = new ArrayList<>();
        this.atorSistema = new ArrayList<>();
    }

    public List<TracedElement> getAtorSistema() {
        return atorSistema;
    }

    public void setAtorSistema(TracedAtorSistema atorSistema) {
       this.atorSistema.add(atorSistema);
    }

    public void removeAtorSistema(TracedElement atorSistema) {
        this.atorSistema.remove(atorSistema);
    }
    
    public List<TracedElement> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(TracedStakeholders stakeholder) {
        stakeholders.add(stakeholder);
    }

    public void removeStakeholder(TracedElement stakeholder) {
        this.stakeholders.remove(stakeholder);
    }
    
    public void setInformacaoExterna(TracedInformacaoExterna tracedInfExt) {
        informacaoExt.add(tracedInfExt);
    }
    
    public List<TracedElement> getInformcaoExterna(){
        return informacaoExt;
    }
    
    public void removeInformacaoExterna(TracedElement tracedInfExt) {
        this.informacaoExt.remove(tracedInfExt);
    }
    
    public List<TracedElement> getInformacaoOrg() {
        return informacaoOrg;
    }

    public void setInformacaoOrg(TracedInformacaoOrganizacional infOrg) {
        informacaoOrg.add(infOrg);
    }
    
    public void removeInformacaoOrg(TracedElement infOrg) {
        this.informacaoOrg.remove(infOrg);
    }
    
    public void setObjetivoSistema(TracedObjetivoSistema tracedObjSistema) {
        objetivoDoSistema.add(tracedObjSistema);
    }
    
    public List<TracedElement> getObjetivoSistema(){
        return objetivoDoSistema;
    }
    
    public void removeObjetivoSistema(TracedElement tracedObjSistema) {
        this.objetivoDoSistema.remove(tracedObjSistema);
    }
    
    public void setRequisitos(TracedRequisitos tracedReq) {
        requisitos.add(tracedReq);
    }
    
    public List<TracedElement> getRequisitos(){
        return requisitos;
    }
    
    public void removeRequisito(TracedElement tracedReq) {
        this.requisitos.remove(tracedReq);
    }

    public TracedElement getActorSystem(String abreviation) {
        int index = 0;
        for(int i=0; i<atorSistema.size(); i++){
            if(atorSistema.get(i).getAbreviacao().equals(abreviation)){
                index = i; 
            }
        }
        return atorSistema.get(index);
    }
    
    public TracedElement getStakeholder(String abreviation) {
        int index = 0;
        for(int i=0; i<stakeholders.size(); i++){
            if(stakeholders.get(i).getAbreviacao().equals(abreviation)){
                index = i; 
            }
        }
        return stakeholders.get(index);
    }
    
    public TracedElement getInfoExterna(String abreviation) {
        int index = 0;
        for(int i=0; i<informacaoExt.size(); i++){
            if(informacaoExt.get(i).getAbreviacao().equals(abreviation)){
                index = i; 
            }
        }
        return informacaoExt.get(index);
    }
    
    public TracedElement getInfoOrg(String abreviation) {
        int index = 0;
        for(int i=0; i<informacaoOrg.size(); i++){
            if(informacaoOrg.get(i).getAbreviacao().equals(abreviation)){
                index = i; 
            }
        }
        return informacaoOrg.get(index);
    }
    
    public TracedElement getObjetivoSistema(String abreviation) {
        int index = 0;
        for(int i=0; i<objetivoDoSistema.size(); i++){
            if(objetivoDoSistema.get(i).getAbreviacao().equals(abreviation)){
                index = i; 
            }
        }
        return objetivoDoSistema.get(index);
    }
    
    public TracedElement getRequisito(String abreviation) {
        int index = 0;
        for(int i=0; i<requisitos.size(); i++){
            if(requisitos.get(i).getAbreviacao().equals(abreviation)){
                index = i; 
            }
        }
        return requisitos.get(index);
    }
    
}

