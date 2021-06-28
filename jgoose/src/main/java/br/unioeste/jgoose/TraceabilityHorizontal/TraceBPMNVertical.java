/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.TraceabilityHorizontal;

import br.unioeste.jgoose.UseCases.Actor;
import br.unioeste.jgoose.UseCases.UseCase;
import br.unioeste.jgoose.controller.BPMNController;
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.UCController;
import br.unioeste.jgoose.controller.VerticalTraceController;
import br.unioeste.jgoose.model.BPMNActivity;
import br.unioeste.jgoose.model.BPMNArtifact;
import br.unioeste.jgoose.model.BPMNEvent;
import br.unioeste.jgoose.model.BPMNLink;
import br.unioeste.jgoose.model.BPMNParticipant;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedInformacaoExterna;
import br.unioeste.jgoose.model.TracedInformacaoOrganizacional;
import br.unioeste.jgoose.model.TracedObjetivoSistema;
import br.unioeste.jgoose.model.TracedRequisitos;
import br.unioeste.jgoose.model.TracedStakeholders;
import br.unioeste.jgoose.model.UCActor;
import br.unioeste.jgoose.model.UCUseCase;
import com.mxgraph.view.mxGraph;
import java.io.IOException;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TraceBPMNVertical {

    private String label;
    private String code;
    private String dependency;
    private String grau;
    private String modelBPMN = "BPMN";
    private String modelUseCase = "Use Case";
    private static TokensTraceability lista;

    public TraceBPMNVertical() {
        this.label = null;
        this.label = null;
        this.dependency = null;
        this.grau = null;
        this.lista = null;
    }

    public void TraceElementsBPMNVertical() {
        lista = new TokensTraceability();

        VerticalTraceController.setTokensVertical(lista);
        traceSwinlanes();
        traceGatways();
        traceArtifacts();
        traceEvents();
        traceActivities();
        traceActors();
        traceUseCase();
    }
    public static TokensTraceability getLista() {
        return lista;
    }
    
    private void traceSwinlanes() {
        for (int i = 0; i < BPMNController.getTokensBPMN().getParticipants().size(); i++) {
            TracedStakeholders tracedStakeholders = new TracedStakeholders();
            code = BPMNController.getTokensBPMN().getParticipants().get(i).getCode();
            label = BPMNController.getTokensBPMN().getParticipants().get(i).getLabel();
            tracedStakeholders.setCode(code);
            tracedStakeholders.setLabel(label);
            tracedStakeholders.setModel(modelBPMN);
            lista.setStakeholders(tracedStakeholders);

            for (UCActor actor : BPMNController.getActors()) {
                if (actor.getCode().equals("100" + code)) {
                    // System.out.println("Parent"+actor.getParent());
                    grau = "A";
                    String[] vetor = new String[2];
                    dependency = actor.getCode();
                    System.out.println("actor.getCode(): " + actor.getCode());

                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedStakeholders.setVetConflict(vetor);

                    if (!actor.getUseCases().isEmpty()) {
                        for (int j = 0; j < actor.getUseCases().size(); j++) {
                            String[] vetorChildren = new String[2];
                            dependency = actor.getUseCases().get(j).getBpmnElementCode();
                            vetorChildren[0] = dependency;
                            vetorChildren[1] = "A";
                            tracedStakeholders.setVetConflict(vetor);
                        }
                    }

                }
            }
        }
    } //Pronto

    private void traceGatways() {
        for (int i = 0; i < BPMNController.getTokensBPMN().getGateways().size(); i++) {
            TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
            label = BPMNController.getTokensBPMN().getGateways().get(i).getLabel();
            code = BPMNController.getTokensBPMN().getGateways().get(i).getCode();
            tracedInfOrg.setCode(code);
            tracedInfOrg.setLabel(label);
            tracedInfOrg.setModel(modelBPMN);
            lista.setInformacaoOrg(tracedInfOrg);

            for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                    if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                        for (UCUseCase useCase : BPMNController.getUseCases()) {
                            if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                grau = "A";
                            }
                            String[] vetor = new String[2];
                            dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedInfOrg.setVetConflict(vetor);
                        }

                    }
                }
            }
        }
    } //Pronto

    private void traceArtifacts() {
        boolean possuiFluxoRecebimento, inside;
        for (BPMNArtifact artifact : BPMNController.getTokensBPMN().getArtifacts()) {
            label = artifact.getLabel();
            code = artifact.getCode();
            if (artifact.isGroupArtifact() || artifact.isTextAnnotationArtifact()) {
                //System.out.println("Organizacional");
                TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                tracedInfOrg.setCode(code);
                tracedInfOrg.setLabel(label);
                tracedInfOrg.setModel(modelBPMN);
                lista.setInformacaoOrg(tracedInfOrg);
                for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                    for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                        if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                            for (UCUseCase useCase : BPMNController.getUseCases()) {
                                if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                    grau = "A";
                                }
                                String[] vetor = new String[2];
                                dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                vetor[0] = dependency;
                                vetor[1] = grau;
                                tracedInfOrg.setVetConflict(vetor);
                            }

                        }
                    }
                }
            } else {
                inside = false;
                for (BPMNParticipant bpmnParticipant : BPMNController.getTokensBPMN().getParticipants()) {
                    if (bpmnParticipant.getChildren().contains(code)) {
                        inside = true;
                    }
                }
                if (inside) {
                    possuiFluxoRecebimento = false;
                    for (BPMNLink link : artifact.getLinksTo()) {
                        possuiFluxoRecebimento = true;
                        break;
                    }
                    if (possuiFluxoRecebimento) {//é um bjetivo do sistema
                        TracedObjetivoSistema tracedObjSistema = new TracedObjetivoSistema();
                        tracedObjSistema.setCode(code);
                        tracedObjSistema.setLabel(label);
                        tracedObjSistema.setModel(modelBPMN);
                        lista.setObjetivoSistema(tracedObjSistema);
                        for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                            for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                                if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                                    for (UCUseCase useCase : BPMNController.getUseCases()) {
                                        if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                            grau = "A";
                                        }
                                        String[] vetor = new String[2];
                                        dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                        vetor[0] = dependency;
                                        vetor[1] = grau;
                                        tracedObjSistema.setVetConflict(vetor);
                                    }

                                }
                            }
                        }
                    } else {//Organizacional
                        TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                        tracedInfOrg.setCode(code);
                        tracedInfOrg.setLabel(label);
                        tracedInfOrg.setModel(modelBPMN);
                        lista.setInformacaoOrg(tracedInfOrg);
                        for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                            for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                                if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                                    for (UCUseCase useCase : BPMNController.getUseCases()) {
                                        if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                            grau = "A";
                                        }
                                        String[] vetor = new String[2];
                                        dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                        vetor[0] = dependency;
                                        vetor[1] = grau;
                                        tracedInfOrg.setVetConflict(vetor);
                                    }

                                }
                            }
                        }
                    }
                } else { //Externo
                    TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                    tracedInfExt.setCode(code);
                    tracedInfExt.setLabel(label);
                    tracedInfExt.setModel(modelBPMN);
                    lista.setInformacaoExterna(tracedInfExt);
                    for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                        for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                            if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                                for (UCUseCase useCase : BPMNController.getUseCases()) {
                                    if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                        grau = "A";
                                    }
                                    String[] vetor = new String[2];
                                    dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                    vetor[0] = dependency;
                                    vetor[1] = grau;
                                    tracedInfExt.setVetConflict(vetor);
                                }

                            }
                        }
                    }
                }
            }
        }
    }//pronto

    private void traceEvents() {
        int type;
        boolean internoSubprocesso, possuiFluxoRecebimento = false;

        for (BPMNEvent event : BPMNController.getTokensBPMN().getEvents()) {
            label = event.getLabel();
            code = event.getCode();
            // É um evento de início
            if (event.isStartEvent()) {
                internoSubprocesso = false;

                // Verifica se o evento não é interno a um subprocesso
                if (event.getParent() != null) {
                    for (BPMNActivity activity : BPMNController.getTokensBPMN().getActivities()) {
                        if (event.getParent().equals(activity.getCode())) {
                            internoSubprocesso = true;
                            break;
                        }
                    }
                }

                if (!internoSubprocesso) { // Não é interno à um subprocesso
                    possuiFluxoRecebimento = false;

                    // Link é direcionado ao Evento atual -> possui fluxo de recebimento
                    for (BPMNLink link : event.getLinksTo()) {
                        possuiFluxoRecebimento = true;
                        break;
                    }

                    // Não possui fluxo de recebimento nem esta contido em um sub-processo 
                    if (!possuiFluxoRecebimento) {//elemento Externo
                        TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                        tracedInfExt.setCode(code);
                        tracedInfExt.setLabel(label);
                        tracedInfExt.setModel(modelBPMN);

                        lista.setInformacaoExterna(tracedInfExt);
                        for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                            for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                                if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                                    for (UCUseCase useCase : BPMNController.getUseCases()) {
                                        if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                            grau = "A";
                                        }
                                        String[] vetor = new String[2];
                                        dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                        vetor[0] = dependency;
                                        vetor[1] = grau;
                                        tracedInfExt.setVetConflict(vetor);
                                    }

                                }
                            }
                        }
                    }
                } else if (possuiFluxoRecebimento || internoSubprocesso) {//eventos iniciais que não 
                    //System.out.println("Evento Org "+ "code "+code+"label "+label);
                    TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                    tracedInfOrg.setCode(code);
                    tracedInfOrg.setLabel(label);
                    tracedInfOrg.setModel(modelBPMN);

                    lista.setInformacaoOrg(tracedInfOrg);
                    for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                        for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                            if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                                for (UCUseCase useCase : BPMNController.getUseCases()) {
                                    if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                        grau = "A";
                                    }
                                    String[] vetor = new String[2];
                                    dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                    vetor[0] = dependency;
                                    vetor[1] = grau;
                                    tracedInfOrg.setVetConflict(vetor);
                                }

                            }
                        }
                    }
                }

            } else {//eventos intermediários, finais e 
                //System.out.println("Evento Org "+ "code "+code+"label "+label);
                TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                tracedInfOrg.setCode(code);
                tracedInfOrg.setLabel(label);
                tracedInfOrg.setModel(modelBPMN);
                lista.setInformacaoOrg(tracedInfOrg);
                for (int k = 0; k < BPMNController.getTokensBPMN().getActivities().size(); k++) {
                    for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().size(); j++) {
                        if (BPMNController.getTokensBPMN().getActivities().get(k).getUsedBPMNElementsToUC().get(j) == code) {
                            for (UCUseCase useCase : BPMNController.getUseCases()) {
                                if (useCase.getBpmnElementCode().equals(BPMNController.getTokensBPMN().getActivities().get(k).getCode())) {
                                    grau = "A";
                                }
                                String[] vetor = new String[2];
                                dependency = BPMNController.getTokensBPMN().getActivities().get(k).getCode();
                                vetor[0] = dependency;
                                vetor[1] = grau;
                                tracedInfOrg.setVetConflict(vetor);
                            }

                        }
                    }
                }
            }
        }

    } // Pronto

    private void traceActivities() { //Pronto
        for (int i = 0; i < BPMNController.getTokensBPMN().getActivities().size(); i++) {
            TracedRequisitos tracedReq = new TracedRequisitos();
            label = BPMNController.getTokensBPMN().getActivities().get(i).getLabel();
            code = BPMNController.getTokensBPMN().getActivities().get(i).getCode();
            tracedReq.setLabel(label);
            tracedReq.setCode(code);
            tracedReq.setModel(modelBPMN);
            tracedReq.setAbreviacao("[RF");
            lista.setRequisitos(tracedReq);

            for (UCUseCase useCase : BPMNController.getUseCases()) {
                if (useCase.getBpmnElementCode().equals(code)) {
                    System.out.println("Entrei");
                    System.out.println("Conflito entre" + useCase.getName() + " e " + label);
                    System.out.println("Codes: " + useCase.getCode() + " e ");
                    grau = "A";
                    String[] vetor = new String[2];
                    dependency = useCase.getBpmnElementCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedReq.setVetConflict(vetor);

                    for (UCActor actor : BPMNController.getActors()) {
                        if (actor.getCode() == useCase.getPrimaryActor().getCode()) {
                            String[] vetorpapa = new String[2];
                            dependency = actor.getCode();
                            vetorpapa[0] = dependency;
                            vetorpapa[1] = grau;
                            tracedReq.setVetConflict(vetorpapa);
                        }
                    }

                    if (!useCase.getIncludedUseCases().isEmpty()) {
                        for (int j = 0; j < useCase.getIncludedUseCases().size(); j++) {
                            String[] vetorinclude = new String[2];
                            dependency = useCase.getIncludedUseCases().get(j).getBpmnElementCode();
                            vetorinclude[1] = "A";
                            vetorinclude[0] = dependency;
                            tracedReq.setVetConflict(vetorinclude);
                        }
                    }
                    if (!useCase.getSecondaryActors().isEmpty()) {
                        for (int j = 0; j < useCase.getSecondaryActors().size(); j++) {
                            String[] vetorSecondary = new String[2];
                            dependency = useCase.getSecondaryActors().get(j).getCode();
                            vetorSecondary[1] = "B";
                            vetorSecondary[0] = dependency;
                            tracedReq.setVetConflict(vetorSecondary);
                        }
                    }

                }
            }
        }
    }//pronto

    private void traceActors() {
        for (UCActor actor : BPMNController.getActors()) {
            TracedStakeholders tracedStakeholders = new TracedStakeholders();
            code = actor.getCode();
            label = actor.getName();
            tracedStakeholders.setLabel(label);
            tracedStakeholders.setCode(code);
            tracedStakeholders.setModel(modelUseCase);
            lista.setStakeholders(tracedStakeholders);
            for (int i = 0; i < BPMNController.getTokensBPMN().getParticipants().size(); i++) {
                if (("100" + BPMNController.getTokensBPMN().getParticipants().get(i).getCode()).equals(code)) {
                    grau = "A";
                    String[] vetor = new String[2];
                    dependency = BPMNController.getTokensBPMN().getParticipants().get(i).getCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedStakeholders.setVetConflict(vetor);
                }
            }

        }
    }

    private void traceUseCase() {
        for (UCUseCase useCase : BPMNController.getUseCases()) {
            TracedRequisitos tracedRequitos = new TracedRequisitos();
            code = useCase.getBpmnElementCode();
            label = useCase.getName();
            tracedRequitos.setLabel(label);
            tracedRequitos.setCode(code);
            tracedRequitos.setModel(modelUseCase);
            tracedRequitos.setAbreviacao("[RF");
            lista.setRequisitos(tracedRequitos);
            for (int i = 0; i < BPMNController.getTokensBPMN().getActivities().size(); i++) {
                if (code.equals(BPMNController.getTokensBPMN().getActivities().get(i).getCode())) {
                    grau = "A";
                    String[] vetor = new String[2];
                    dependency = BPMNController.getTokensBPMN().getActivities().get(i).getCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedRequitos.setVetConflict(vetor);
                }
            }
        }
    }

}
