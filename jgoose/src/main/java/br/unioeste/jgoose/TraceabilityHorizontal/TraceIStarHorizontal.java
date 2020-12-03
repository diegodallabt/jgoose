/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.TraceabilityHorizontal;

import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.HorizontalIStarTraceController;
import br.unioeste.jgoose.model.IStarElement;
import br.unioeste.jgoose.model.IStarLink;
import br.unioeste.jgoose.model.TokensOpenOME;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedAtorSistema;
import br.unioeste.jgoose.model.TracedStakeholders;
import br.unioeste.jgoose.model.TracedAtorSistema;
import br.unioeste.jgoose.model.TracedElement;
import br.unioeste.jgoose.model.TracedInformacaoExterna;
import br.unioeste.jgoose.model.TracedObjetivoSistema;
import br.unioeste.jgoose.model.TracedRequisitos;
import br.unioeste.jgoose.model.TracedInformacaoOrganizacional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TraceIStarHorizontal {

    private ArrayList<IStarLink> dependencias = Controller.getOme().getDependenciess();
    private ArrayList<IStarElement> softgoals = Controller.getOme().getSoftgoals();
    private ArrayList<IStarElement> tasks = Controller.getOme().getTasks();
    private ArrayList<IStarElement> resources = Controller.getOme().getResourcess();
    private ArrayList<IStarElement> goals = Controller.getOme().getGoals();

    private String label;
    private String code;
    private String dependency;
    private String grau;
    private String model = "I *";
    private static TokensTraceability lista;
    private String codeSystemActor;
    private int indiceActorSystem;

    public TraceIStarHorizontal() {
        this.label = null;
        this.label = null;
        this.dependency = null;
        this.grau = null;
        this.lista = null;
    }

    public static TokensTraceability getLista() {
        return lista;
    }
    
    public void TraceElementsIStarHorizontal() {
        lista = new TokensTraceability();

        HorizontalIStarTraceController.setTokensHorizontal(lista);
        traceActor();
        traceGoal();
        traceSoftgoal();
        traceTask();
        traceResource();
        
        conflictsM(HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());

        conflictsM(HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg());
        conflictsM(HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema());
        conflictsM(HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
        conflictsM(HorizontalIStarTraceController.getTokensTraceability().getStakeholders());
        
    }

    private void traceActor() {
        //pode ser Stakeholder ou Ator Sistema

        String systemActor = Controller.getSystemActor();
        int indiceSystemActor = 0;
        TracedAtorSistema tracedAtorSistema = new TracedAtorSistema();
        for (int i = 0; i < Controller.getOme().getActors().size(); i++) {
            if (Controller.getOme().getActor(i).getCod() == systemActor) {
                indiceSystemActor = i;
                label = Controller.getOme().getActor(i).getName();
                break;
            }
        }
        indiceActorSystem = indiceSystemActor;
        codeSystemActor = systemActor;
        tracedAtorSistema.setCode(codeSystemActor);
        tracedAtorSistema.setLabel(label);
        lista.setAtorSistema(tracedAtorSistema);

        for (int i = 0; i < Controller.getOme().getActor(indiceSystemActor).getChildrens().size(); i++) {
            String[] vetor = new String[2];
            dependency = Controller.getOme().getActor(indiceSystemActor).getChildren(i);
            grau = "A";
            System.out.println("children" + dependency);
            vetor[0] = dependency;
            vetor[1] = grau;
            tracedAtorSistema.setVetConflict(vetor);
        }

        System.out.println("Code System Actor: " + codeSystemActor);
        System.out.println("Code System Actor: " + systemActor);

        for (int i = 0; i < Controller.getOme().getActors().size(); i++) {
            // System.out.println("links: "+ Controller.getOme().getActor(i).getLinks());
            code = Controller.getOme().getActor(i).getCod();
            label = Controller.getOme().getActor(i).getName();

            if (Controller.getOme().getActor(i).getCod() != codeSystemActor) {
                for (int j = 0; j < Controller.getOme().getActor(i).getLinks().size(); j++) {
                    String link = Controller.getOme().getActor(i).getLinks().get(j);

                    for (int k = 0; k < dependencias.size(); k++) {
                        if (link == dependencias.get(k).getCod()) {

                            String to = dependencias.get(k).getTo();
                            dependencias.get(k).getFrom();

                            for (int l = 0; l < dependencias.size(); l++) {
                                if (to == dependencias.get(l).getFrom() && dependencias.get(l).getTo() == codeSystemActor) {
                                    TracedStakeholders tracedStakeholders = new TracedStakeholders();
                                    System.out.println("----------------------------------------------------");
                                    System.out.println("Stakeholder encontrado caso 1");
                                    System.out.println("indice: " + i);
                                    System.out.println("Links: " + Controller.getOme().getActor(i).getLinks());
                                    tracedStakeholders.setCode(code);
                                    tracedStakeholders.setLabel(label);
                                    lista.setStakeholders(tracedStakeholders);
                                    conflictsA(Controller.getOme().getActor(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getStakeholders());
                                    k = dependencias.size();
                                    j = Controller.getOme().getActor(i).getLinks().size();
                                    break;
                                } else if (to == dependencias.get(l).getFrom()) {
                                    String toChildren = dependencias.get(l).getTo();
                                    if (Controller.getOme().getActor(indiceSystemActor).containsChildren(toChildren)) {
                                        TracedStakeholders tracedStakeholders = new TracedStakeholders();
                                        tracedStakeholders.setCode(code);
                                        tracedStakeholders.setLabel(label);
                                        lista.setStakeholders(tracedStakeholders);
                                        conflictsA(Controller.getOme().getActor(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getStakeholders());
                                        k = dependencias.size();
                                        j = Controller.getOme().getActor(i).getLinks().size();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {//ator sistema para o stakeholder
                for (int j = 0; j < Controller.getOme().getActor(i).getLinks().size(); j++) {
                    String link = Controller.getOme().getActor(i).getLinks().get(j);

                    for (int k = 0; k < dependencias.size(); k++) {
                        if (link == dependencias.get(k).getCod()) {
                            String to = dependencias.get(k).getTo();
                            for (int l = 0; l < dependencias.size(); l++) {
                                for (int m = 0; m < Controller.getOme().getActors().size(); m++) {
                                    String actor = Controller.getOme().getActor(m).getCod();
                                    if (to == dependencias.get(l).getFrom() && dependencias.get(l).getTo() == actor && dependencias.get(l).getTo() != codeSystemActor) {
                                        code = Controller.getOme().getActor(m).getCod();
                                        label = Controller.getOme().getActor(m).getName();
                                        TracedStakeholders tracedStakeholders = new TracedStakeholders();
                                        tracedStakeholders.setCode(code);
                                        tracedStakeholders.setLabel(label);
                                        lista.setStakeholders(tracedStakeholders);
                                        conflictsA(Controller.getOme().getActor(m).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getStakeholders());
                                        //l = dependencias.size();
                                        //k = dependencias.size();
                                        //j = Controller.getOme().getActor(i).getLinks().size();
                                        //break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //3 caso: um filho do ator sistema for um depender e o dependee for um actor
        for (int i = 0; i < Controller.getOme().getActor(indiceSystemActor).getChildrens().size(); i++) {
            String codeChildren = Controller.getOme().getActor(indiceSystemActor).getChildren(i);
            for (int j = 0; j < dependencias.size(); j++) {
                if (codeChildren == dependencias.get(j).getFrom()) {
                    String from = dependencias.get(j).getTo();
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int m = 0; m < Controller.getOme().getActors().size(); m++) {
                            String actor = Controller.getOme().getActor(m).getCod();
                            if (from == dependencias.get(k).getFrom() && dependencias.get(k).getTo() == actor) {
                                code = Controller.getOme().getActor(m).getCod();
                                label = Controller.getOme().getActor(m).getName();
                                TracedStakeholders tracedStakeholders = new TracedStakeholders();
                                tracedStakeholders.setCode(code);
                                tracedStakeholders.setLabel(label);
                                lista.setStakeholders(tracedStakeholders);
                                conflictsA(Controller.getOme().getActor(m).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getStakeholders());
                                //l = dependencias.size();
                                //k = dependencias.size();
                                //j = Controller.getOme().getActor(i).getLinks().size();
                                //break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void traceSoftgoal() {
        //Requisito(Não funcional) ou Informação Externa
        //1 caso: se o Ator Sistema depender de um stakeholder através de softgoal
        for (int i = 0; i < softgoals.size(); i++) {
            code = Controller.getOme().getSoftgoals().get(i).getCod();
            label = Controller.getOme().getSoftgoals().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                if (dependencias.get(j).getFrom() == codeSystemActor && code == dependencias.get(j).getTo()) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (code == dependencias.get(k).getFrom() && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("exteno encontrado softgoal: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getSoftgoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());
                            }
                        }
                    }
                }
            }
        }
        //2 caso: se um filho do ator sistema tever uma dependencia para um softgoal
        //e esse softgoal possuir dependencia para um stakeholder
        //children(depender) -> softgoal(dependum) -> stakeholder(dependee)
        for (int i = 0; i < softgoals.size(); i++) {
            code = Controller.getOme().getSoftgoals().get(i).getCod();
            label = Controller.getOme().getSoftgoals().get(i).getName();

            for (int j = 0; j < dependencias.size(); j++) {
                String fromChildren = dependencias.get(j).getFrom();
                if (Controller.getOme().getActor(indiceActorSystem).containsChildren(fromChildren) && dependencias.get(j).getTo() == code) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (dependencias.get(k).getFrom() == code && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("exteno encontrado softgoal: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getSoftgoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());
                            }
                        }
                    }
                }
            }
        }

        //3 caso: softgoal é um filho do ator sistema 
        for (int i = 0; i < softgoals.size(); i++) {
            String children = softgoals.get(i).getCod();
            if (Controller.getOme().getActor(indiceActorSystem).containsChildren(children)) {
                code = Controller.getOme().getSoftgoals().get(i).getCod();
                label = Controller.getOme().getSoftgoals().get(i).getName();
                System.out.println("3 caso: softgoal->requisito: " + label);
                TracedRequisitos tracedRequisitos = new TracedRequisitos();
                tracedRequisitos.setCode(code);
                tracedRequisitos.setLabel(label);
                tracedRequisitos.setAbreviacao("[RNF");
                lista.setRequisitos(tracedRequisitos);
                conflictsA(Controller.getOme().getSoftgoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
            }
        }
        for (int i = 0; i < softgoals.size(); i++) {
            code = Controller.getOme().getSoftgoals().get(i).getCod();
            label = Controller.getOme().getSoftgoals().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                //4 caso: quando um ator (depender) depeden de um softgoal(dependum) para o ator sistema(dependee)
                String to = dependencias.get(j).getTo();
                if (dependencias.get(j).getFrom() == code && dependencias.get(j).getTo() == codeSystemActor) {
                    System.out.println("4 caso: softgoal->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RNF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getSoftgoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
                //5 caso: quando um ator (depender) depende de um softgoal(dependum) para um filho do ator sistema(dependee)
                if (dependencias.get(j).getFrom() == code && Controller.getOme().getActor(indiceActorSystem).containsChildren(to)) {
                    System.out.println("5 caso: softgoal->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RNF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getSoftgoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
            }
        }

    }

    private void traceGoal() {
        //Objetivo do Sistema, Informação Organizacional,Informação Externa
        //1 caso: se o Ator Sistema depender de um stakeholder através de um goal 
        //2 caso: se o Ator Sistema depender de um stakeholder através de um goal 
        //e o stakeholder não possuir nenhuma dependencia com o ator sistema
        for (int i = 0; i < goals.size(); i++) {
            code = Controller.getOme().getGoals().get(i).getCod();
            label = Controller.getOme().getGoals().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                if (dependencias.get(j).getFrom() == codeSystemActor && code == dependencias.get(j).getTo()) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (code == dependencias.get(k).getFrom() && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("1 caso: exteno -> goals: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getGoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());
                            }
                        }
                    }
                }
            }
        }
        //2 caso: se um filho do ator sistema tever uma dependencia para um task
        //e esse task possuir dependencia para um stakeholder
        //children(depender) -> goal(dependum) -> stakeholder(dependee)
        for (int i = 0; i < goals.size(); i++) {
            code = Controller.getOme().getGoals().get(i).getCod();
            label = Controller.getOme().getGoals().get(i).getName();

            for (int j = 0; j < dependencias.size(); j++) {
                String fromChildren = dependencias.get(j).getFrom();
                if (Controller.getOme().getActor(indiceActorSystem).containsChildren(fromChildren) && dependencias.get(j).getTo() == code) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (dependencias.get(k).getFrom() == code && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                //verificar se o stakeholder não possui nenhuma ligação com o ator sistema ou com algum filho
                                boolean ligacao = true;
                                String codStakeholder = dependencias.get(k).getTo();
                                for (int m = 0; m < dependencias.size(); m++) {
                                    if (codStakeholder == dependencias.get(m).getFrom()) {
                                        String to = dependencias.get(m).getTo();
                                        for (int n = 0; n < dependencias.size(); n++) {
                                            if (to == dependencias.get(n).getFrom()) {
                                                for (int o = 0; o < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); o++) {
                                                    if (dependencias.get(n).getTo() == codeSystemActor
                                                            || Controller.getOme().getActor(indiceActorSystem).containsChildren(dependencias.get(n).getTo())
                                                            || dependencias.get(n).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(o).getCode()) {
                                                        ligacao = false;
                                                    }
                                                }

                                            }
                                        }
                                        if (ligacao) {
                                            System.out.println("2 caso: objetivo do sistema -> goals: " + label);
                                            TracedObjetivoSistema tracedObjs = new TracedObjetivoSistema();
                                            tracedObjs.setCode(code);
                                            tracedObjs.setLabel(label);
                                            lista.setObjetivoSistema(tracedObjs);
                                            conflictsA(Controller.getOme().getGoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //3 caso: o goal é um dependum entre dois stakeholders
        for (int i = 0; i < goals.size(); i++) {
            for (int j = 0; j < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); j++) {
                for (int k = 0; k < dependencias.size(); k++) {
                    if (dependencias.get(k).getFrom() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(j).getCode()
                            && dependencias.get(k).getTo() == goals.get(i).getCod()) {
                        System.out.println("found");
                        for (int l = 0; l < dependencias.size(); l++) {
                            for (int m = 0; m < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); m++) {
                                if (dependencias.get(l).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(m).getCode()
                                        && dependencias.get(l).getFrom() == goals.get(i).getCod()) {
                                    code = Controller.getOme().getGoals().get(i).getCod();
                                    label = Controller.getOme().getGoals().get(i).getName();
                                    System.out.println("3 caso: goals->info organizacional: " + label);
                                    TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                                    tracedInfOrg.setCode(code);
                                    tracedInfOrg.setLabel(label);
                                    tracedInfOrg.setAbreviacao("[OBJ");
                                    lista.setInformacaoOrg(tracedInfOrg);
                                    conflictsA(Controller.getOme().getGoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema());
                                }
                            }

                        }
                    }
                }
            }
        }

        //4 caso: goal é um filho do ator sistema 
        for (int i = 0; i < goals.size(); i++) {
            String children = goals.get(i).getCod();
            if (Controller.getOme().getActor(indiceActorSystem).containsChildren(children)) {
                code = Controller.getOme().getGoals().get(i).getCod();
                label = Controller.getOme().getGoals().get(i).getName();
                System.out.println("4 caso: goals->objetivo do sistema: " + label);
                TracedObjetivoSistema tracedObjSis = new TracedObjetivoSistema();
                tracedObjSis.setCode(code);
                tracedObjSis.setLabel(label);
                tracedObjSis.setAbreviacao("[OBJ");
                lista.setObjetivoSistema(tracedObjSis);
                conflictsA(Controller.getOme().getGoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema());
            }
        }

        for (int i = 0; i < goals.size(); i++) {
            code = Controller.getOme().getGoals().get(i).getCod();
            label = Controller.getOme().getGoals().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                //5 caso: quando um ator (depender) depeden de um goal(dependum) para o ator sistema(dependee)
                String to = dependencias.get(j).getTo();
                if (dependencias.get(j).getFrom() == code && dependencias.get(j).getTo() == codeSystemActor) {
                    System.out.println("5 caso: goals->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getGoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
                //6 caso: quando um ator (depender) depende de um task(dependum) para um filho do ator sistema(dependee)
                if (dependencias.get(j).getFrom() == code && Controller.getOme().getActor(indiceActorSystem).containsChildren(to)) {
                    System.out.println("6 caso: goals->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getGoals().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
            }
        }
    }

    private void traceTask() {
        //Requisito(Funcional) ou Informação Externa
        //1 caso: se o Ator Sistema depender de um stakeholder através de task
        for (int i = 0; i < tasks.size(); i++) {
            code = Controller.getOme().getTasks().get(i).getCod();
            label = Controller.getOme().getTasks().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                if (dependencias.get(j).getFrom() == codeSystemActor && code == dependencias.get(j).getTo()) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (code == dependencias.get(k).getFrom() && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("exteno encontrado tasks: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getTasks().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());
                            }
                        }
                    }
                }
            }
        }
        //2 caso: se um filho do ator sistema tever uma dependencia para um task
        //e esse task possuir dependencia para um stakeholder
        //children(depender) -> task(dependum) -> stakeholder(dependee)
        for (int i = 0; i < tasks.size(); i++) {
            code = Controller.getOme().getTasks().get(i).getCod();
            label = Controller.getOme().getTasks().get(i).getName();

            for (int j = 0; j < dependencias.size(); j++) {
                String fromChildren = dependencias.get(j).getFrom();
                if (Controller.getOme().getActor(indiceActorSystem).containsChildren(fromChildren) && dependencias.get(j).getTo() == code) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (dependencias.get(k).getFrom() == code && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("exteno encontrado tasks: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getTasks().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());

                            }
                        }
                    }
                }
            }
        }

        //3 caso: task é um filho do ator sistema 
        for (int i = 0; i < tasks.size(); i++) {
            String children = tasks.get(i).getCod();
            if (Controller.getOme().getActor(indiceActorSystem).containsChildren(children)) {
                code = Controller.getOme().getTasks().get(i).getCod();
                label = Controller.getOme().getTasks().get(i).getName();
                System.out.println("3 caso: tasks->requisito: " + label);
                TracedRequisitos tracedRequisitos = new TracedRequisitos();
                tracedRequisitos.setCode(code);
                tracedRequisitos.setLabel(label);
                tracedRequisitos.setAbreviacao("[RF");
                lista.setRequisitos(tracedRequisitos);
                conflictsA(Controller.getOme().getTasks().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
            }
        }
        for (int i = 0; i < tasks.size(); i++) {
            code = Controller.getOme().getTasks().get(i).getCod();
            label = Controller.getOme().getTasks().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                //4 caso: quando um ator (depender) depeden de um task(dependum) para o ator sistema(dependee)
                String to = dependencias.get(j).getTo();
                if (dependencias.get(j).getFrom() == code && dependencias.get(j).getTo() == codeSystemActor) {
                    System.out.println("4 caso: tasks->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getTasks().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
                //5 caso: quando um ator (depender) depende de um task(dependum) para um filho do ator sistema(dependee)
                if (dependencias.get(j).getFrom() == code && Controller.getOme().getActor(indiceActorSystem).containsChildren(to)) {
                    System.out.println("5 caso: tasks->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getTasks().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
            }
        }

    }

    private void traceResource() {
        //Requisito(Não funcional) ou Informação Externa
        //1 caso: se o Ator Sistema depender de um stakeholder através de softgoal
        for (int i = 0; i < resources.size(); i++) {
            code = Controller.getOme().getResourcess().get(i).getCod();
            label = Controller.getOme().getResourcess().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                if (dependencias.get(j).getFrom() == codeSystemActor && code == dependencias.get(j).getTo()) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (code == dependencias.get(k).getFrom() && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("exteno encontrado resources: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getResourcess().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());
                            }
                        }
                    }
                }
            }
        }
        //2 caso: se um filho do ator sistema tever uma dependencia para um softgoal
        //e esse softgoal possuir dependencia para um stakeholder
        //children(depender) -> softgoal(dependum) -> stakeholder(dependee)
        for (int i = 0; i < resources.size(); i++) {
            code = Controller.getOme().getResourcess().get(i).getCod();
            label = Controller.getOme().getResourcess().get(i).getName();

            for (int j = 0; j < dependencias.size(); j++) {
                String fromChildren = dependencias.get(j).getFrom();
                if (Controller.getOme().getActor(indiceActorSystem).containsChildren(fromChildren) && dependencias.get(j).getTo() == code) {
                    for (int k = 0; k < dependencias.size(); k++) {
                        for (int l = 0; l < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); l++) {
                            if (dependencias.get(k).getFrom() == code && dependencias.get(k).getTo() == HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(l).getCode()) {
                                System.out.println("exteno encontrado resources: " + label);
                                TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                                tracedInfExt.setCode(code);
                                tracedInfExt.setLabel(label);
                                lista.setInformacaoExterna(tracedInfExt);
                                conflictsA(Controller.getOme().getResourcess().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna());
                            }
                        }
                    }
                }
            }
        }

        //3 caso: softgoal é um filho do ator sistema 
        for (int i = 0; i < resources.size(); i++) {
            String children = resources.get(i).getCod();
            if (Controller.getOme().getActor(indiceActorSystem).containsChildren(children)) {
                code = Controller.getOme().getResourcess().get(i).getCod();
                label = Controller.getOme().getResourcess().get(i).getName();
                System.out.println("3 caso: resources->requisito: " + label);
                TracedRequisitos tracedRequisitos = new TracedRequisitos();
                tracedRequisitos.setCode(code);
                tracedRequisitos.setLabel(label);
                tracedRequisitos.setAbreviacao("[RF");
                lista.setRequisitos(tracedRequisitos);
                conflictsA(Controller.getOme().getResourcess().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
            }
        }
        for (int i = 0; i < resources.size(); i++) {
            code = Controller.getOme().getResourcess().get(i).getCod();
            label = Controller.getOme().getResourcess().get(i).getName();
            for (int j = 0; j < dependencias.size(); j++) {
                //4 caso: quando um ator (depender) depeden de um softgoal(dependum) para o ator sistema(dependee)
                String to = dependencias.get(j).getTo();
                if (dependencias.get(j).getFrom() == code && dependencias.get(j).getTo() == codeSystemActor) {
                    System.out.println("4 caso: resources->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getResourcess().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
                //5 caso: quando um ator (depender) depende de um softgoal(dependum) para um filho do ator sistema(dependee)
                if (dependencias.get(j).getFrom() == code && Controller.getOme().getActor(indiceActorSystem).containsChildren(to)) {
                    System.out.println("5 caso: resources->requisito: " + label);
                    TracedRequisitos tracedRequisitos = new TracedRequisitos();
                    tracedRequisitos.setCode(code);
                    tracedRequisitos.setLabel(label);
                    tracedRequisitos.setAbreviacao("[RF");
                    lista.setRequisitos(tracedRequisitos);
                    conflictsA(Controller.getOme().getResourcess().get(i).getLinks(), HorizontalIStarTraceController.getTokensTraceability().getRequisitos());
                }
            }
        }
    }

    private void conflictsA(ArrayList<String> links, List<TracedElement> elementsTraced) {
        String[] vetor = new String[2];
        grau = "A";
        vetor[0] = dependency;
        vetor[1] = grau;
        for (TracedElement element : elementsTraced) {
            if (element.getCode() == code) {
                for (int i = 0; i < dependencias.size(); i++) {
                    for (int j = 0; j < links.size(); j++) {
                        if (links.get(j) == dependencias.get(i).getCod()) {
                            if (dependencias.get(i).getFrom() == code) {
                                vetor[0] = dependencias.get(i).getTo();
                                element.setVetConflict(vetor);
                                /*for(int k = 0; k < dependencias.size(); k++){
                                    if(dependencias.get(i).getTo() == dependencias.get(k).getTo()){
                                        vetor[0] = dependencias.get(k).getFrom();
                                        element.setVetConflict(vetor);
                                    }else if(dependencias.get(i).getTo() == dependencias.get(k).getFrom()){
                                        vetor[0] = dependencias.get(k).getTo();
                                        element.setVetConflict(vetor);
                                    }
                                }*/
                            } else {
                                vetor[0] = dependencias.get(i).getFrom();
                                element.setVetConflict(vetor);
                                /*for(int k = 0; k < dependencias.size(); k++){
                                    if(dependencias.get(i).getTo() == dependencias.get(k).getTo()){
                                        vetor[0] = dependencias.get(k).getFrom();
                                        element.setVetConflict(vetor);
                                    }else if(dependencias.get(i).getTo() == dependencias.get(k).getFrom()){
                                        vetor[0] = dependencias.get(k).getTo();
                                        element.setVetConflict(vetor);
                                    }
                                }*/
                            }
                        }
                    }
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
                    for (int k = 0; k < dependencias.size(); k++) {
                        if (cod == dependencias.get(k).getTo() && elementsTraced1.getCode() != dependencias.get(k).getFrom()) {
                            dependency = dependencias.get(k).getFrom();
                            String[] vetorr = new String[2];
                            vetorr[0] = dependency;
                            vetorr[1] = grau;
                            elementsTraced1.setVetConflict(vetorr);

                        } else if (cod == dependencias.get(k).getFrom() && elementsTraced1.getCode() != dependencias.get(k).getTo()) {
                            dependency = dependencias.get(k).getTo();
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
                    for (int p = 0; p < dependencias.size(); p++) {
                        if (elementsTraced1.getListConcflicts().get(j)[0] == dependencias.get(p).getTo()) {
                            String from = dependencias.get(p).getFrom();
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
                        if (elementsTraced1.getListConcflicts().get(j)[0] == dependencias.get(p).getFrom()) {
                         String to = dependencias.get(p).getTo();
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
