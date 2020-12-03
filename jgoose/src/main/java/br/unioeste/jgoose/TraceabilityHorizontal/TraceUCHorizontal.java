/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.TraceabilityHorizontal;

import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.HorizontalUseCaseTraceController;
import br.unioeste.jgoose.controller.UCController;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedElement;
import br.unioeste.jgoose.model.TracedRequisitos;
import br.unioeste.jgoose.model.TracedStakeholders;
import java.util.List;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TraceUCHorizontal {

    private String label;
    private String code;
    private String dependency;
    private String grau;
    private String model = "Use Case";
    private static TokensTraceability lista;
    
    public TraceUCHorizontal() {
        this.label = null;
        this.label = null;
        this.dependency = null;
        this.grau = null;
        this.lista = null;
    }

    public static TokensTraceability getLista() {
        return lista;
    }
    
    public void TraceElementsUCHorizontal() {
          lista = new TokensTraceability();
           HorizontalUseCaseTraceController.setTokensHorizontal(lista);
        traceActors();
        traceUseCase();
        
        conflictsM(HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos());
        conflictsM(HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders());
    }

    private void traceUseCase() {
        for (int i = 0; i < UCController.getTokensUC().getUseCase().size(); i++){
            TracedRequisitos tracedReq = new TracedRequisitos();
            label = UCController.getTokensUC().getUseCase().get(i).getName();
            code = UCController.getTokensUC().getUseCase().get(i).getCode();
            tracedReq.setLabel(label);
            tracedReq.setCode(code);
            tracedReq.setAbreviacao("[RF");
            tracedReq.setModel(model);
            lista.setRequisitos(tracedReq);

            grau = "A";
            for (int j = 0; j < UCController.getTokensUC().getUseCase().get(i).getLinksTo().size(); j++) {
                String[] vetor = new String[2];
                dependency = UCController.getTokensUC().getUseCase().get(i).getLinksTo().get(j).getFrom().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedReq.setVetConflict(vetor);
            }
            for (int j = 0; j < UCController.getTokensUC().getUseCase().get(i).getLinksFrom().size(); j++) {
                String[] vetor = new String[2];
                dependency = UCController.getTokensUC().getUseCase().get(i).getLinksFrom().get(j).getTo().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedReq.setVetConflict(vetor);
            }

        }

    }

    private void traceActors() {
       for (int i = 0; i < UCController.getTokensUC().getActorUC().size(); i++){
            TracedStakeholders tracedStakeholders = new TracedStakeholders();
            label = UCController.getTokensUC().getActorUC().get(i).getName();
            code = UCController.getTokensUC().getActorUC().get(i).getCode();
            tracedStakeholders.setLabel(label);
            tracedStakeholders.setCode(code);
            tracedStakeholders.setModel(model);
            lista.setStakeholders(tracedStakeholders);

            grau = "A";
            for (int j = 0; j < UCController.getTokensUC().getActorUC().get(i).getLinksTo().size(); j++) {
                String[] vetor = new String[2];
                dependency = UCController.getTokensUC().getActorUC().get(i).getLinksTo().get(j).getFrom().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedStakeholders.setVetConflict(vetor);
            }
            for (int j = 0; j < UCController.getTokensUC().getActorUC().get(i).getLinksFrom().size(); j++) {
                String[] vetor = new String[2];
                dependency = UCController.getTokensUC().getActorUC().get(i).getLinksFrom().get(j).getTo().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedStakeholders.setVetConflict(vetor);
            }

        }
    }

    private void conflictsM(List<TracedElement> elementsTraced) {

        String[] vetor = new String[2];
        vetor[1] = grau;
        grau = "M";

        for (TracedElement elementsTraced1 : elementsTraced) {
            for (int j = 0; j < elementsTraced1.getListConcflicts().size(); j++) {
                String cod = elementsTraced1.getListConcflicts().get(j)[0];
                if ("A".equals(elementsTraced1.getListConcflicts().get(j)[1])) {
                    for (int k = 0; k < UCController.getTokensUC().getLink().size(); k++) {
                        if (cod == UCController.getTokensUC().getLink().get(k).getTo().getCode() && elementsTraced1.getCode() != UCController.getTokensUC().getLink().get(k).getFrom().getCode()) {
                            dependency = UCController.getTokensUC().getLink().get(k).getFrom().getCode();
                            String[] vetorr = new String[2];
                            vetorr[0] = dependency;
                            vetorr[1] = grau;
                            elementsTraced1.setVetConflict(vetorr);

                        } else if (cod == UCController.getTokensUC().getLink().get(k).getFrom().getCode() && elementsTraced1.getCode() != UCController.getTokensUC().getLink().get(k).getTo().getCode()) {
                            dependency = UCController.getTokensUC().getLink().get(k).getTo().getCode();
                            String[] vetorr = new String[2];
                            vetorr[0] = dependency;
                            vetorr[1] = grau;
                            elementsTraced1.setVetConflict(vetorr);
                        }
                    }
                }
            }

        }

        for (TracedElement elementsTraced1 : elementsTraced) {
            for (int j = 0; j < elementsTraced1.getListConcflicts().size(); j++) {
                String cod = elementsTraced1.getListConcflicts().get(j)[0];

                if ("M".equals(elementsTraced1.getListConcflicts().get(j)[1])) {
                    for (int p = 0; p < UCController.getTokensUC().getLink().size(); p++) {
                        if (elementsTraced1.getListConcflicts().get(j)[0] == UCController.getTokensUC().getLink().get(p).getTo().getCode()) {
                            String from = UCController.getTokensUC().getLink().get(p).getFrom().getCode();
                            boolean exist = false;
                            for (int b = 0; b < elementsTraced1.getListConcflicts().size(); b++) {
                                if (elementsTraced1.getListConcflicts().get(b)[0] == from) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (exist == false) {
                                dependency = from;
                                String[] vetorr = new String[2];
                                vetorr[0] = dependency;
                                vetorr[1] = "B";
                                elementsTraced1.setVetConflict(vetorr);
                            }
                            exist = false;
                        }
                        if (elementsTraced1.getListConcflicts().get(j)[0] == UCController.getTokensUC().getLink().get(p).getFrom().getCode()) {
                         String to = UCController.getTokensUC().getLink().get(p).getTo().getCode();
                            boolean exist = false;
                            for (int b = 0; b < elementsTraced1.getListConcflicts().size(); b++) {
                                if (elementsTraced1.getListConcflicts().get(b)[0] == to) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (exist == false) {
                                dependency = to;
                                String[] vetorr = new String[2];
                                vetorr[0] = dependency;
                                vetorr[1] = "B";
                                elementsTraced1.setVetConflict(vetorr);
                            }
                            exist = false;
                        }
                    }

                }
            }
        }

    }
    
}
