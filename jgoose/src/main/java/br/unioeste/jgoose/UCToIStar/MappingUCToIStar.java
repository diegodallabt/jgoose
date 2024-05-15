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
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Dalla Bernardina
 */


public class MappingUCToIStar  {
    final private static ArrayList<IStarActorElement> istarActors = new ArrayList<>();
    private TokensOpenOME tokensIStar = Controller.getOme();

    public MappingUCToIStar(){
       istarActors.clear();
       this.startMapping();
    }

     public void startMapping() {
        
        ArrayList<UCActor> actors = (ArrayList) UCController.getTokensUC().getActorUC();
        ArrayList<UCUseCase> useCases = (ArrayList) UCController.getTokensUC().getUseCase();
        ArrayList<UCLink> link = (ArrayList) UCController.getTokensUC().getLink();
        ArrayList<String> subCases = new ArrayList<>();
        boolean hasSystemBoundary = false;
        boolean validation = true;
        

        System.out.println("LISTA DE ATORES:");
        System.out.println(actors);
        
        System.out.println("LISTA DE CASOS DE USO:");
        System.out.println(useCases);

        System.out.println("LISTA DE LINKS");
        System.out.println(link);
           
        if(actors.isEmpty()){
            JOptionPane.showMessageDialog(null, "You need add actors.", "No actors added", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("No actors added."); 
        } else {
            for(UCActor actor : actors){
                if(actor.getSystem()){
                    hasSystemBoundary = true;
                    if(actor.getUseCasesSystem().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Your system boundary is empty.", "System boundary empty", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException("System boundary is empty.");
                    }
                }

                if(actor.getLinksFrom().isEmpty() || actor.getLinksTo().isEmpty()){
                    // JOptionPane.showMessageDialog(null, "You have actors without links.", "Linking actors", JOptionPane.ERROR_MESSAGE);
                }
            }

            if(!hasSystemBoundary){
                JOptionPane.showMessageDialog(null, "You need add system boundary.", "No system boundary added", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("No system boundary added.");
            }
        }

        
        // Mapeamento de atores primários
        for (UCActor actor : actors) {
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
                
                if(actor.getSecondary()){
                    istarActor.setSecondary(true);
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
                        IStarElement dependency = new IStarElement();
                        dependency.setCod(useCase.getCode());
                        dependency.setName(useCase.getName());

                        for (UCLink links : link) {
                             if(links.getFrom().getCode().equals(useCase.getCode())){
                                 dependency.setLink(links.getCode());
                             }
                         }

                        istarActor.setDependency(dependency);
                        istarActor.setTask(dependency);
                       
                        
                        
                        savedDependencies.add(useCase.getName());
                    }
                }

                istarActors.add(istarActor);

                tokensIStar = new TokensOpenOME();

                Controller.setOme(tokensIStar);

                tokensIStar.setActors(istarActors);
            
        }
        
        for(UCUseCase subCase : useCases){
            boolean haveFather = false;
            for(UCActor father : actors){
                for(UCUseCase subcaseParent : father.getUseCases()){
                    if(subcaseParent.getCode().equals(subCase.getCode())){
                        haveFather = true;
                        break;
                    }
                }
                
            }
            
            if(!haveFather){
               for (UCLink links : link) {
                   if(links.getType() != null && (links.getType() == links.INCLUDE )){
                       if(links.getTo().getCode() == subCase.getCode()){
                           for (UCLink dependencia : link) {
                                if((dependencia.getFrom() instanceof UCActor && dependencia.getTo() instanceof UCUseCase) && links.getFrom().getCode().equals(dependencia.getTo().getCode())){
                                    for(IStarActorElement actorI : tokensIStar.getActors()){
                                        if(actorI.getCod().equals(dependencia.getFrom().getCode())){
                                            subCases.add(subCase.getCode());
                                            IStarActorElement father = actorI;
                                            int indexRemove =istarActors.indexOf(father);
                                            istarActors.remove(indexRemove);
                                            IStarElement dependency = new IStarElement();
                                            dependency.setCod(subCase.getCode());
                                            dependency.setName(subCase.getName());
                                            father.setDependency(dependency);

                                            istarActors.add(father);
                                            tokensIStar.setActors(istarActors);
                                            break;
                                        }
                                    }
                                }
                           }
                       }
                   }
                   
               }
               for (UCLink links : link) {
               if(links.getType() != null && links.getType() == links.EXTEND){
                       if(links.getFrom().getCode() == subCase.getCode()){
                           
                           for (UCLink dependencia : link) {
                                if(dependencia.getFrom() instanceof UCActor && dependencia.getTo() instanceof UCUseCase && links.getTo().getCode().equals(dependencia.getTo().getCode())){
                                    for(IStarActorElement actorI : tokensIStar.getActors()){
                                        if(actorI.getCod() == dependencia.getFrom().getCode()){
                                            subCases.add(subCase.getCode());
                                            IStarActorElement father = actorI;
                                            int indexRemove =istarActors.indexOf(father);
                                            istarActors.remove(indexRemove);
                                            IStarElement dependency = new IStarElement();
                                            dependency.setCod(subCase.getCode());
                                            dependency.setName(subCase.getName());
                                            father.setDependency(dependency);

                                            istarActors.add(father);
                                            tokensIStar.setActors(istarActors);
                                            break;
                                        }
                                    }
                                }
                           }
                       }
                   }
               }
            }
        }
        
        // Mapeia relações AND E OR
        for (UCLink links : link) {
            if(links.getType() != null && links.getType() == links.ASSOCIATION){
                IStarLink dependencyMapped = new IStarLink();
                dependencyMapped.setCod(links.getCode());
                dependencyMapped.setName(links.getLabel());
                dependencyMapped.setFrom(links.getFrom().getCode());
                dependencyMapped.setTo(links.getTo().getCode());
                dependencyMapped.setType(1);

                tokensIStar.addDependency(dependencyMapped);
                System.out.println("DEPENDÊNCIA ADICIONADA");
            }
            
            if(links.getType() != null && links.getType() == links.INCLUDE){
                
                if(subCases.contains(links.getFrom().getCode())){
                    System.out.println("Ligação AND descartada, pois o elemento é um subcaso de uso.");
                } else {
                    // cria a ligação and
                    IStarLink linkAndMapped = new IStarLink();
                    linkAndMapped.setCod(links.getCode());
                    linkAndMapped.setName(links.getLabel());
                    linkAndMapped.setFrom(links.getFrom().getCode());
                    linkAndMapped.setTo(links.getTo().getCode());
                    linkAndMapped.setType(2);

                    tokensIStar.addAND(linkAndMapped);
                    System.out.println("AND ADICIONADO");
                }
                
            }
            if(links.getType() != null && links.getType() == links.EXTEND){
                
                if(subCases.contains(links.getTo().getCode())){
                    System.out.println("Ligação OR descartada, pois o elemento é um subcaso de uso.");
                } else {
                    IStarLink linkOrMapped = new IStarLink();
                    linkOrMapped.setCod(links.getCode());
                    linkOrMapped.setName(links.getLabel());
                    linkOrMapped.setFrom(links.getFrom().getCode());
                    linkOrMapped.setTo(links.getTo().getCode());
                    linkOrMapped.setType(3);

                    tokensIStar.addOR(linkOrMapped);
                    System.out.println("OR ADICIONADO");
                }
                
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
        
    }
}