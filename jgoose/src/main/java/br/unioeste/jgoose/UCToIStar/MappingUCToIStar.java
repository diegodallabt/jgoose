/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unioeste.jgoose.UCToIStar;

import br.unioeste.jgoose.UseCases.Actor;
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.model.TokensOpenOME;
import br.unioeste.jgoose.controller.EditorWindowListener;
import br.unioeste.jgoose.controller.HorizontalControler;
import br.unioeste.jgoose.controller.ImportIStarGraph;
import java.util.ArrayList;
import br.unioeste.jgoose.controller.UCController;
import br.unioeste.jgoose.controller.VerticalTraceController;
import br.unioeste.jgoose.e4j.swing.BasicIStarEditor;
import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.e4j.swing.menubar.EditorMenuBar;
import br.unioeste.jgoose.model.IStarActorElement;
import br.unioeste.jgoose.model.IStarElement;
import br.unioeste.jgoose.model.IStarLink;
import br.unioeste.jgoose.model.TokensUseCase;
import br.unioeste.jgoose.model.UCActor;
import br.unioeste.jgoose.model.UCElement;
import br.unioeste.jgoose.model.UCLink;
import br.unioeste.jgoose.model.UCUseCase;
import br.unioeste.jgoose.view.MainView;
import com.mxgraph.util.mxResources;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Diego Dalla Bernardina Thedoldi
 */


public class MappingUCToIStar  {
    final private static ArrayList<IStarActorElement> istarActors = new ArrayList<>();
    final private static ArrayList<IStarElement> istarElements = new ArrayList<>();
    private TokensOpenOME tokensIStar = Controller.getOme();

    public MappingUCToIStar(){
       istarActors.clear();
       this.startMapping();
    }

     public void startMapping() {
        
        ArrayList<UCActor> actors = (ArrayList) UCController.getTokensUC().getActorUC();
        ArrayList<UCUseCase> useCases = (ArrayList) UCController.getTokensUC().getUseCase();
        ArrayList<UCLink> link = (ArrayList) UCController.getTokensUC().getLink();
        
        

        System.out.println("LISTA DE ATORES:");
        System.out.println(actors);
        
        System.out.println("LISTA DE CASOS DE USO:");
        System.out.println(useCases);

        System.out.println("LISTA DE LINKS");
        System.out.println(link);
        
        // Mapeamento de atores primários
        for (UCActor actor : actors) {
            if(!actor.getSecondary()){
                
                IStarActorElement istarActor = new IStarActorElement();
                istarActor.setCod(actor.getCode());
                istarActor.setName(actor.getName());
                
                if(actor.getSystem()){
                    istarActor.setSystem(true);
                    for (UCUseCase useCase : actor.getUseCasesSystem()){
                        IStarElement children = new IStarElement();
                        children.setCod(useCase.getCode());
                        children.setName(useCase.getName());

                        children.setLink(null);
                        children.setParent(null);

                        istarActor.addChildren(children);
                    }
                    
                }
                
                // Mapeamento de casos de uso
                HashMap<String, Integer> useCaseNameCount = new HashMap<>();
                HashSet<String> savedDependencies = new HashSet<>();

                for (UCUseCase useCase : actor.getUseCases()){
                    String useCaseName = useCase.getName();
                    if(useCaseNameCount.containsKey(useCaseName)){
                        useCaseNameCount.put(useCaseName, useCaseNameCount.get(useCaseName) + 1);
                    } else {
                        useCaseNameCount.put(useCaseName, 1);
                    }
                }

                for (UCUseCase useCase : actor.getUseCases()){
                    if(!savedDependencies.contains(useCase.getName())){
                        System.out.println("SALVANDO CASO DE USO...");
                        IStarElement dependency = new IStarElement();
                        dependency.setCod(useCase.getCode());
                        dependency.setName(useCase.getName());

                        dependency.setLink(null);
                        dependency.setParent(null);

                        istarActor.setDependency(dependency);
                        istarActor.setTask(dependency);
                        
                        savedDependencies.add(useCase.getName());
                    }
                }

                istarActors.add(istarActor);

                tokensIStar = new TokensOpenOME();

                Controller.setOme(tokensIStar);

                tokensIStar.setActors(istarActors);

                System.out.println("Atores mapeados");
                System.out.println(tokensIStar.getActors());
            }
        }
        

        // Prints de teste
        System.out.println("Atores no Caso de Uso:");
        for (UCActor useCaseActor : actors) {
            System.out.println(useCaseActor.getCode() + " - " + useCaseActor.getName());
        }

        System.out.println("\nAtores no Modelo I*:");
        for (IStarActorElement istarActor : istarActors) {
            System.out.println(istarActor.getCod() + " - " + istarActor.getName());
        }
        
        // Mapeia ligações ISA
        for (UCLink links : link) {
            if(links.getType() != null && links.getType() == 2){
                for(IStarActorElement actor : istarActors){
                    if(actor.getCod() == links.getFrom().getCode()){
                        actor.setChildren(links.getTo().getCode());
                    }
                }
            }
        }
        
//        // Mapeia relações AND E OR
        for (UCLink links : link) {
            if(links.getType() != null && links.getType() == links.INCLUDE){
                // cria a ligação and
                IStarLink linkAnd = new IStarLink();
                linkAnd.setCod(links.getCode());
                linkAnd.setName(links.getLabel());
                linkAnd.setFrom(links.getFrom().getCode());
                linkAnd.setTo(links.getTo().getCode());
                linkAnd.setType(2);

                tokensIStar.addAND(linkAnd);
                System.out.println("AND ADICIONADO");
                // consigo os 2 elementos ligados pelo include o From é um objetivo e o To uma tarefa do tipo and
//                System.out.println(" DE " + links.getFrom() + "LINK PARA " + links.getTo()); ;
            }
            if(links.getType() != null && links.getType() == links.EXTEND){
                IStarLink linkOr = new IStarLink();
                linkOr.setCod(links.getCode());
                linkOr.setName(links.getLabel());
                linkOr.setFrom(links.getFrom().getCode());
                linkOr.setTo(links.getTo().getCode());
                linkOr.setType(3);
                
                tokensIStar.addOR(linkOr);
                System.out.println("OR ADICIONADO");
            }
        };
    }
    
    // RGC 2: Mapear atores primários para atores genéricos no modelo iStar
    private static void mappingPrimaryActors(UCActor primaryActor) {
        /*if (primaryActor.getUseCases().isEmpty()) {
            throw new RuntimeException("O ator primário deve estar conectado a pelo menos um caso de uso.");
        }*/
        
        if (primaryActor.getFather() == null && primaryActor.getChildren().isEmpty()) {
            throw new RuntimeException("O ator primário deve possuir ao menos uma associação ou uma relação de especialização com outro ator.");
        }
        
        
        IStarActorElement istarActor = new IStarActorElement();
        istarActor.setCod(primaryActor.getCode());
        istarActor.setName(primaryActor.getName());
        
        IStarElement istarObjective = new IStarElement();
        
        for (UCUseCase useCase : primaryActor.getUseCases()) {
            IStarElement objective = new IStarElement();
            objective.setCod(useCase.getCode());
            objective.setName(useCase.getName());
            
            istarActor.setChildren(objective.getCod());
            
            
        }
        
        
        for (IStarActorElement otherActor : istarActors) {
            if (otherActor.getName().equals(istarActor.getName())) {
                throw new RuntimeException("O elemento ator do diagrama de caso de uso deve ter um nome único no diagrama.");
            }
        }
        
        istarActors.add(istarActor);
        
        
        
    }
   
    
}

