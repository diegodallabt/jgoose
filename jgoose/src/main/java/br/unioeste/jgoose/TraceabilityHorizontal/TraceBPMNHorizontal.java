package br.unioeste.jgoose.TraceabilityHorizontal;

import br.unioeste.jgoose.controller.BPMNController;
import br.unioeste.jgoose.controller.HorizontalBPMNTraceController;
import br.unioeste.jgoose.model.BPMNActivity;
import br.unioeste.jgoose.model.BPMNArtifact;
import br.unioeste.jgoose.model.BPMNEvent;
import br.unioeste.jgoose.model.BPMNLink;
import br.unioeste.jgoose.model.BPMNParticipant;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedElement;
import br.unioeste.jgoose.model.TracedInformacaoExterna;
import br.unioeste.jgoose.model.TracedStakeholders;
import br.unioeste.jgoose.model.TracedInformacaoOrganizacional;
import br.unioeste.jgoose.model.TracedObjetivoSistema;
import br.unioeste.jgoose.model.TracedRequisitos;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;

/**
 * @author Victor Augusto Pozzan
 */
public class TraceBPMNHorizontal {

    private String label;
    private String code;
    private String dependency;
    private String grau;
    private String modelBPMN = "BPMN";
    private static TokensTraceability lista;

    public TraceBPMNHorizontal() {
        this.label = null;
        this.label = null;
        this.dependency = null;
        this.grau = null;
        this.lista = null;
    }

    public static TokensTraceability getLista() {
        return lista;
    }

    public static void setListaStakeholder(TracedStakeholders stakeholder) {
        TraceBPMNHorizontal.lista.setStakeholders(stakeholder);
    }
    
    public static void setListaInfExt(TracedInformacaoExterna tracedInfExt) {
        TraceBPMNHorizontal.lista.setInformacaoExterna(tracedInfExt);
    }

    
    public void TraceElementsBPMNHorizontal() {
        lista = new TokensTraceability();
        HorizontalBPMNTraceController.setTokensHorizontal(lista);
        traceSwinlanes();
        traceGatways();
        traceArtifacts();
        traceEvents();
        traceActivities();

        //conflicts         
        conflictsM(HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna());

        conflictsM(HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg());
        conflictsM(HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema());
        conflictsM(HorizontalBPMNTraceController.getTokensTraceability().getRequisitos());
        conflictsM(HorizontalBPMNTraceController.getTokensTraceability().getStakeholders());
        
    }

    private void traceSwinlanes() { //get POOL and Lane atribute to STAKEHOLDERS List        
        for (int i = 0; i < BPMNController.getTokensBPMN().getParticipants().size(); i++) {
            TracedStakeholders tracedStakeholders = new TracedStakeholders();
            code = BPMNController.getTokensBPMN().getParticipants().get(i).getCode();
            label = BPMNController.getTokensBPMN().getParticipants().get(i).getLabel();
            tracedStakeholders.setCode(code);
            tracedStakeholders.setLabel(label);
            tracedStakeholders.setModel(modelBPMN);
            lista.setStakeholders(tracedStakeholders);

            for (int j = 0; j < BPMNController.getTokensBPMN().getParticipants().get(i).getChildren().size(); j++) {
                String[] vetor = new String[2];
                dependency = BPMNController.getTokensBPMN().getParticipants().get(i).getChildren().get(j);
                grau = "A";
                System.out.println("children" + dependency);
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedStakeholders.setVetConflict(vetor);
            }
        }
    }

    private void traceGatways() {
        for (int i = 0; i < BPMNController.getTokensBPMN().getGateways().size(); i++) {
            TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
            label = BPMNController.getTokensBPMN().getGateways().get(i).getLabel();
            code = BPMNController.getTokensBPMN().getGateways().get(i).getCode();
            tracedInfOrg.setCode(code);
            tracedInfOrg.setLabel(label);
            tracedInfOrg.setModel(modelBPMN);
            lista.setInformacaoOrg(tracedInfOrg);
            //getting from 

            grau = "A";
            for (int j = 0; j < BPMNController.getTokensBPMN().getGateways().get(i).getLinksTo().size(); j++) {
                String[] vetor = new String[2];
                dependency = BPMNController.getTokensBPMN().getGateways().get(i).getLinksTo().get(j).getFrom().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedInfOrg.setVetConflict(vetor);
            }
            for (int j = 0; j < BPMNController.getTokensBPMN().getGateways().get(i).getLinksFrom().size(); j++) {
                String[] vetor = new String[2];
                dependency = BPMNController.getTokensBPMN().getGateways().get(i).getLinksFrom().get(j).getTo().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedInfOrg.setVetConflict(vetor);
            }

        }

    }

    private void traceActivities() { //find all tasks ans subprocess

        for (int i = 0; i < BPMNController.getTokensBPMN().getActivities().size(); i++) {
            TracedRequisitos tracedReq = new TracedRequisitos();
            label = BPMNController.getTokensBPMN().getActivities().get(i).getLabel();
            code = BPMNController.getTokensBPMN().getActivities().get(i).getCode();
            tracedReq.setLabel(label);
            tracedReq.setCode(code);
            tracedReq.setModel(modelBPMN);
            tracedReq.setAbreviacao("[RF");
            lista.setRequisitos(tracedReq);

            grau = "A";
            for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(i).getLinksTo().size(); j++) {
                String[] vetor = new String[2];
                dependency = BPMNController.getTokensBPMN().getActivities().get(i).getLinksTo().get(j).getFrom().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedReq.setVetConflict(vetor);
            }
            for (int j = 0; j < BPMNController.getTokensBPMN().getActivities().get(i).getLinksFrom().size(); j++) {
                String[] vetor = new String[2];
                dependency = BPMNController.getTokensBPMN().getActivities().get(i).getLinksFrom().get(j).getTo().getCode();
                vetor[0] = dependency;
                vetor[1] = grau;
                tracedReq.setVetConflict(vetor);
            }
        }
    }

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
                grau = "A";
                for (int j = 0; j < artifact.getLinksTo().size(); j++) {
                    String[] vetor = new String[2];
                    dependency = artifact.getLinksTo().get(j).getFrom().getCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedInfOrg.setVetConflict(vetor);
                }
                for (int j = 0; j < artifact.getLinksFrom().size(); j++) {
                    String[] vetor = new String[2];
                    dependency = artifact.getLinksFrom().get(j).getTo().getCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedInfOrg.setVetConflict(vetor);
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
                        grau = "A";
                        for (int j = 0; j < artifact.getLinksTo().size(); j++) {
                            String[] vetor = new String[2];
                            dependency = artifact.getLinksTo().get(j).getFrom().getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedObjSistema.setVetConflict(vetor);
                        }
                        for (int j = 0; j < artifact.getLinksFrom().size(); j++) {
                            String[] vetor = new String[2];
                            dependency = artifact.getLinksFrom().get(j).getTo().getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedObjSistema.setVetConflict(vetor);
                        }
                    } else {//Organizacional
                        TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                        tracedInfOrg.setCode(code);
                        tracedInfOrg.setLabel(label);
                        tracedInfOrg.setModel(modelBPMN);
                        lista.setInformacaoOrg(tracedInfOrg);
                        grau = "A";
                        for (int j = 0; j < artifact.getLinksTo().size(); j++) {
                            String[] vetor = new String[2];
                            dependency = artifact.getLinksTo().get(j).getFrom().getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedInfOrg.setVetConflict(vetor);
                        }
                        for (int j = 0; j < artifact.getLinksFrom().size(); j++) {
                            String[] vetor = new String[2];
                            dependency = artifact.getLinksFrom().get(j).getTo().getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedInfOrg.setVetConflict(vetor);
                        }
                    }
                } else { //Externo
                    TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                    tracedInfExt.setCode(code);
                    tracedInfExt.setLabel(label);
                    tracedInfExt.setModel(modelBPMN);
                    lista.setInformacaoExterna(tracedInfExt);
                    grau = "A";
                    for (int j = 0; j < artifact.getLinksTo().size(); j++) {
                        String[] vetor = new String[2];
                        dependency = artifact.getLinksTo().get(j).getFrom().getCode();
                        vetor[0] = dependency;
                        vetor[1] = grau;
                        tracedInfExt.setVetConflict(vetor);
                    }
                    for (int j = 0; j < artifact.getLinksFrom().size(); j++) {
                        String[] vetor = new String[2];
                        dependency = artifact.getLinksFrom().get(j).getTo().getCode();
                        vetor[0] = dependency;
                        vetor[1] = grau;
                        tracedInfExt.setVetConflict(vetor);
                    }
                }
            }
        }
    }

    private void traceEvents() {
        //eventos de inicio são Externos quando não estão contidos em 
        //subprocessos e não possuem fluxo de menssagem chegando ate eles
        //System.out.println("Events");
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
                        grau = "A";
                        for (int j = 0; j < event.getLinksTo().size(); j++) {
                            String[] vetor = new String[2];
                            dependency = event.getLinksTo().get(j).getFrom().getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedInfExt.setVetConflict(vetor);
                        }
                        for (int j = 0; j < event.getLinksFrom().size(); j++) {
                            String[] vetor = new String[2];
                            dependency = event.getLinksFrom().get(j).getTo().getCode();
                            vetor[0] = dependency;
                            vetor[1] = grau;
                            tracedInfExt.setVetConflict(vetor);
                        }
                    }
                } else if (possuiFluxoRecebimento || internoSubprocesso) {//eventos iniciais que não 
                    //System.out.println("Evento Org "+ "code "+code+"label "+label);
                    TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                    tracedInfOrg.setCode(code);
                    tracedInfOrg.setLabel(label);
                    tracedInfOrg.setModel(modelBPMN);
                    lista.setInformacaoOrg(tracedInfOrg);
                    grau = "A";
                    for (int j = 0; j < event.getLinksTo().size(); j++) {
                        String[] vetor = new String[2];
                        dependency = event.getLinksTo().get(j).getFrom().getCode();
                        vetor[0] = dependency;
                        vetor[1] = grau;
                        tracedInfOrg.setVetConflict(vetor);
                    }
                    for (int j = 0; j < event.getLinksFrom().size(); j++) {
                        String[] vetor = new String[2];
                        dependency = event.getLinksFrom().get(j).getTo().getCode();
                        vetor[0] = dependency;
                        vetor[1] = grau;
                        tracedInfOrg.setVetConflict(vetor);
                    }
                }

            } else {//eventos intermediários, finais e 
                //System.out.println("Evento Org "+ "code "+code+"label "+label);
                TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                tracedInfOrg.setCode(code);
                tracedInfOrg.setLabel(label);
                tracedInfOrg.setModel(modelBPMN);
                lista.setInformacaoOrg(tracedInfOrg);
                grau = "A";
                for (int j = 0; j < event.getLinksTo().size(); j++) {
                    String[] vetor = new String[2];
                    dependency = event.getLinksTo().get(j).getFrom().getCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedInfOrg.setVetConflict(vetor);
                }
                for (int j = 0; j < event.getLinksFrom().size(); j++) {
                    String[] vetor = new String[2];
                    dependency = event.getLinksFrom().get(j).getTo().getCode();
                    vetor[0] = dependency;
                    vetor[1] = grau;
                    tracedInfOrg.setVetConflict(vetor);
                }
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
                    for (int k = 0; k < BPMNController.getTokensBPMN().getLinks().size(); k++) {
                        if (cod == BPMNController.getTokensBPMN().getLinks().get(k).getTo().getCode() && elementsTraced1.getCode() != BPMNController.getTokensBPMN().getLinks().get(k).getFrom().getCode()) {
                            dependency = BPMNController.getTokensBPMN().getLinks().get(k).getFrom().getCode();
                            String[] vetorr = new String[2];
                            vetorr[0] = dependency;
                            vetorr[1] = grau;
                            elementsTraced1.setVetConflict(vetorr);

                        } else if (cod == BPMNController.getTokensBPMN().getLinks().get(k).getFrom().getCode() && elementsTraced1.getCode() != BPMNController.getTokensBPMN().getLinks().get(k).getTo().getCode()) {
                            dependency = BPMNController.getTokensBPMN().getLinks().get(k).getTo().getCode();
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
                    for (int p = 0; p < BPMNController.getTokensBPMN().getLinks().size(); p++) {
                        if (elementsTraced1.getListConcflicts().get(j)[0] == BPMNController.getTokensBPMN().getLinks().get(p).getTo().getCode()) {
                            String from = BPMNController.getTokensBPMN().getLinks().get(p).getFrom().getCode();
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
                        if (elementsTraced1.getListConcflicts().get(j)[0] == BPMNController.getTokensBPMN().getLinks().get(p).getFrom().getCode()) {
                            String to = BPMNController.getTokensBPMN().getLinks().get(p).getTo().getCode();
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
