/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TokensUseCase {
    private List<UCActor> actorUC;
    private List<UCUseCase> useCase;
    private List<UCUseCaseDescription> useCaseDescription;
    private List<UCLink> link;

    public TokensUseCase(List<UCActor> actorUC, List<UCUseCase> useCase, List<UCUseCaseDescription> useCaseDescription, List<UCLink> link ) {
        this.actorUC = actorUC;
        this.useCase = useCase;
        this.useCaseDescription = useCaseDescription;
        this.link = link;
    }
    
    public TokensUseCase() {
        this.actorUC = new ArrayList<>();
        this.useCase = new ArrayList<>();
        this.useCaseDescription = new ArrayList<>();
        this.link = new ArrayList<>();
    }
    
    public List<UCActor> getActorUC() {
        return actorUC;
    }

    public List<UCLink> getLink() {
        return link;
    }

    public void setLink(List<UCLink> link) {
        this.link = link;
    }

    public void setActorUC(List<UCActor> actorUC) {
        this.actorUC = actorUC;
    }

    public List<UCUseCase> getUseCase() {
        return useCase;
    }

    public void setUseCase(List<UCUseCase> useCase) {
        this.useCase = useCase;
    }

    public List<UCUseCaseDescription> getUseCaseDescription() {
        return useCaseDescription;
    }

    public void setUseCaseDescription(List<UCUseCaseDescription> useCaseDescription) {
        this.useCaseDescription = useCaseDescription;
    }

    public void addLink(UCLink links){
        this.link.add(links);
    }

    public void addActor(UCActor actor) {
        this.actorUC.add(actor);
    }
    
    public void addUseCase(UCUseCase useCase) {
        this.useCase.add(useCase);
    }
    
    public void deleteUseCase(UCUseCase useCase){
        this.useCase.remove(useCase);
    }
}
