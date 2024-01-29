/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unioeste.jgoose.UCToIStar;

import br.unioeste.jgoose.controller.Controller;
import java.util.ArrayList;
import br.unioeste.jgoose.controller.UCController;
import br.unioeste.jgoose.model.IStarActorElement;
import br.unioeste.jgoose.model.UCActor;
import br.unioeste.jgoose.model.UCUseCase;
import java.util.List;

/**
 *
 * @author Diego Dalla Bernardina Thedoldi
 */

class IStarActor {
    final private String cod;
    private String name;

    public IStarActor(String cod, String name) {
        this.cod = cod;
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public String getName() {
        return name;
    }
}

public class MappingUCToIStar {
    final private static List<IStarActor> istarActors = new ArrayList<>();

    public static void main(String[] args) {
        List<UCActor> useCaseActors = UCController.getTokensUC().getActorUC();
        List<UCUseCase> useCases = UCController.getTokensUC().getUseCase();

        // Mapeamento de atores primários
        for (UCUseCase useCase : useCases) {
            UCActor primaryActor = useCase.getPrimaryActor();
            mapToIStarActors(primaryActor);
        }

        // Prints de teste
        System.out.println("Atores no Caso de Uso:");
        for (UCActor useCaseActor : useCaseActors) {
            System.out.println(useCaseActor.getCode() + " - " + useCaseActor.getName());
        }

        System.out.println("\nAtores no Modelo I*:");
        for (IStarActor istarActor : istarActors) {
            System.out.println(istarActor.getCod() + " - " + istarActor.getName());
        }
    }
    
    // RGC 1: Alteração de nome no diagrama de caso de uso deve ser refletida no modelo iStar
    public static void updateActorName(UCActor actor, String newName) {
        // Atualiza o nome do ator no caso de uso
        actor.setName(newName);

        // Encontra o ator correspondente no modelo iStar e atualiza o nome
        for (IStarActor istarActor : istarActors) {
            if (istarActor.getCod().equals(actor.getCode())) {
                istarActor.setName(newName);
                break;
            }
        }
    }

    // RGC 2: Mapear atores primários para atores genéricos no modelo iStar
    private static void mapToIStarActors(UCActor primaryActor) {
        IStarActor istarActor = new IStarActor(primaryActor.getCode(), primaryActor.getName());
        istarActors.add(istarActor);
    }

    // RGC 3: O limite do sistema de um caso de uso será mapeado como o ator sistema do modelo iStar
    public static void mapSystemBoundaryToIStarActor() {
        int indexActor = Controller.getOme().searchActorCod(Controller.getSystemActor());
        
        IStarActorElement systemActor = Controller.getOme().getActor(indexActor);

        Controller.setSystemActor(systemActor.getCod());
        IStarActor istarActor = new IStarActor(systemActor.getCod(), systemActor.getName());
        istarActors.add(istarActor);
    }

}

/*
    VERSÃO ANTIGA DA RGC 2
    
    private static List<IStarActor> mapToIStarActors(List<UCActor> useCaseActors) {
        List<IStarActor> istarActors = new ArrayList<>();

        for (UCActor useCaseActor : useCaseActors) {
            IStarActor istarActor = new IStarActor(useCaseActor.getCode(), useCaseActor.getName() + "_Mapeado");
            istarActors.add(istarActor);
        }

        return istarActors;
    }

    VERSÃO ANTIGA DA RGC 3

    public static void mapSystemBoundaryToIStarActor(SystemBoundary systemBoundary) {
        // Como obtenho o limite do sistema?
        IStarActor istarActor = new IStarActor(systemBoundary.getCode(), systemBoundary.getName() + "_Mapeado");
        istarActors.add(istarActor);
    }
*/
