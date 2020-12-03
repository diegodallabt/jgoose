package br.unioeste.jgoose.model;

import com.sun.javafx.scene.control.skin.VirtualFlow;
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
    
    public List<TracedElement> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(TracedStakeholders stakeholder) {
        stakeholders.add(stakeholder);
    }

    public void setInformacaoExterna(TracedInformacaoExterna tracedInfExt) {
        informacaoExt.add(tracedInfExt);
    }
    
    public List<TracedElement> getInformcaoExterna(){
        return informacaoExt;
    }
    
    public List<TracedElement> getInformacaoOrg() {
        return informacaoOrg;
    }

    public void setInformacaoOrg(TracedInformacaoOrganizacional infOrg) {
        informacaoOrg.add(infOrg);
    }
    
    public void setObjetivoSistema(TracedObjetivoSistema tracedObjSistema) {
        objetivoDoSistema.add(tracedObjSistema);
    }
    
    public List<TracedElement> getObjetivoSistema(){
        return objetivoDoSistema;
    }
    
    public void setRequisitos(TracedRequisitos tracedReq) {
        requisitos.add(tracedReq);
    }
    
    public List<TracedElement> getRequisitos(){
        return requisitos;
    }
    
   /* @Override
    public String toString() {
        String resposta = "Modelo BPMN\n";
        
        resposta += "\n\tStakeholders\n";
        
        for(TracedStakeholders bPMNStakeholders : stakeholders){
            resposta += "\t\t" + bPMNStakeholders.toString() + "\n";
        }
                
        return resposta;
    }*/
}

