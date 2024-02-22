/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unioeste.jgoose.UCToIStar;

import br.unioeste.jgoose.UseCases.Actor;
import br.unioeste.jgoose.controller.Controller;
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
    final private static List<IStarActorElement> istarActors = new ArrayList<>();
    

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
        
        System.out.println("LISTA DE ATORES:");
        System.out.println(useCases);

        System.out.println("LISTA DE LINKS");
        System.out.println(link);
        
        for (UCLink links : link) {
            System.out.println(links.getFrom() );
            UCElement to = links.getTo();
            
            
            
           
            System.out.println(to.getCode());
            System.out.println(to.getLabel());
            
        }
        
        // Mapeamento de atores primários
        for (UCActor actor : actors) {
            IStarActorElement istarActor = new IStarActorElement();
            
            
           
            
            istarActor.setChildren(objective.getCod());
            
            
            istarActors.add(istarActor);
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
    }
    
    // RGC 2: Mapear atores primários para atores genéricos no modelo iStar
    private static void mappingPrimaryActors(UCActor primaryActor) {
        /*if (primaryActor.getUseCases().isEmpty()) {
            throw new RuntimeException("O ator primário deve estar conectado a pelo menos um caso de uso.");
        }*/
        
        if (primaryActor.getFather() == null && primaryActor.getChildren().isEmpty()) {
            throw new RuntimeException("O ator primário deve possuir ao menos uma associação ou uma relação de especialização com outro ator.");
        }
        
        Set<UCUseCase> uniqueUseCases = new HashSet<>(primaryActor.getUseCases());
        if (uniqueUseCases.size() != primaryActor.getUseCases().size()) {
            throw new RuntimeException("Um ator pode ter no máximo uma associação com um caso de uso.");
        }
        
        for (UCLink link : primaryActor.getLinksFrom()) {
            if (link.getType().equals(UCLink.ASSOCIATION) && link.getTo() instanceof UCActor) {
                throw new RuntimeException("Um ator primário não pode se ligar diretamente a outro ator via associação.");
            }
        }

        for (UCLink link : primaryActor.getLinksTo()) {
            if (link.getType().equals(UCLink.ASSOCIATION) && link.getFrom() instanceof UCActor) {
                throw new RuntimeException("Um ator primário não pode se ligar diretamente a outro ator via associação.");
            }
        }

        IStarActorElement istarActor = new IStarActorElement();
        istarActor.setCod(primaryActor.getCode());
        istarActor.setName(primaryActor.getName());
        
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

/*
    VERSÃO ANTIGA DA RGC 3

    public static void mapSystemBoundaryToIStarActor(SystemBoundary systemBoundary) {
        // Como obtenho o limite do sistema?
        IStarActor istarActor = new IStarActor(systemBoundary.getCode(), systemBoundary.getName() + "_Mapeado");
        istarActors.add(istarActor);
    }
*/
