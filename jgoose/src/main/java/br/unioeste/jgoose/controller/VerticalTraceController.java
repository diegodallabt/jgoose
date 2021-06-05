/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.controller;

import br.unioeste.jgoose.TraceabilityHorizontal.TraceBPMNVertical;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceIStarVertical;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.view.Matriz;
import br.unioeste.jgoose.view.TraceabilityView;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor Augusto Pozzan
 */
@SuppressWarnings(value = "serial")

public class VerticalTraceController extends AbstractAction {

    private static TraceBPMNVertical traceBPMNVertical;
    private static TraceIStarVertical traceIStarVertical;
    private static TokensTraceability tokensTraceability;

    public static Matriz propertiesMatriz(int indice) {
        String title;
        switch (indice) {
            case 0: //Informação Externa x Informação Externa
                title = " ← <AGREGAÇÃO> Informação Externa x Informação Externa  <AGREGAÇÃO>";
                Matriz matriz0 = new Matriz(title, tokensTraceability.getInformcaoExterna().size(),
                        tokensTraceability.getInformcaoExterna().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        true);
                return matriz0;
            case 1: //Informação Externa x Informação Organizacional
                title = " ← <REC> Informação Externa x Informação Organizacional <REC>";
                Matriz matriz1 = new Matriz(title, tokensTraceability.getInformcaoExterna().size(),
                        tokensTraceability.getInformacaoOrg().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        false);
                return matriz1;
            case 2: //Informação Organizacional x Informação Organizacional
                title = " ← <AGREGAÇÃO> Informação Organizacional x Informação Organizacional  <AGREGAÇÃO>";
                Matriz matriz2 = new Matriz(title, tokensTraceability.getInformacaoOrg().size(),
                        tokensTraceability.getInformacaoOrg().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        true);
                return matriz2;
            case 3: //Informação Organizacional x Requisitos
                title = " ← <REC> Informação Organizacional x Requisitos  <REC>";
                Matriz matriz3 = new Matriz(title, tokensTraceability.getInformacaoOrg().size(),
                        tokensTraceability.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        false);
                return matriz3;
            case 4: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz matriz4 = new Matriz(title, tokensTraceability.getObjetivoSistema().size(),
                        tokensTraceability.getObjetivoSistema().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        true);
                return matriz4;
            case 5: //Objetivo do Sistema x Requisitos
                title = " ← <SAT> Objetivo do Sistema x Requisitos  <SAT>";
                Matriz matriz5 = new Matriz(title, tokensTraceability.getObjetivoSistema().size(),
                        tokensTraceability.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        false);
                return matriz5;
            case 6: //Stakeholder x Stakeholder
                title = " ← <AGREGAÇÃO> Stakeholder x Stakeholder  <AGREGAÇÃO>";
                Matriz matriz6 = new Matriz(title, tokensTraceability.getStakeholders().size(),
                        tokensTraceability.getStakeholders().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),
                        true);
                return matriz6;
            case 7: //Stakeholder x Requisitos
                title = " ← <RESP> Stakeholder x Requisitos  <RESP>";
                Matriz matriz7 = new Matriz(title, tokensTraceability.getStakeholders().size(),
                        tokensTraceability.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        false);
                return matriz7;
            case 8: //Requisitos x Requisitos
                title = " ← <AGREGAÇÃO> Requisitos x Requisitos  <AGREGAÇÃO>";
                Matriz matriz8 = new Matriz(title, tokensTraceability.getRequisitos().size(),
                        tokensTraceability.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        true);
                return matriz8;
            case 9: //Requisitos x Informação Externa
                title = " ← <REC> Requisitos x Informação Externa  <REC>";
                Matriz matriz9 = new Matriz(title, tokensTraceability.getRequisitos().size(),
                        tokensTraceability.getInformcaoExterna().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        false);
                return matriz9;
            case 10: //Objetivo do Sistema x Informação Organizacional
                title = " ← <REC> Objetivo do Sistema x Informação Organizacional  <REC>";
                Matriz matriz10 = new Matriz(title, tokensTraceability.getObjetivoSistema().size(),
                        tokensTraceability.getInformacaoOrg().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        false);
                return matriz10;
            case 11: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz matriz11 = new Matriz(title, tokensTraceability.getObjetivoSistema().size(),
                        tokensTraceability.getObjetivoSistema().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        true);
                return matriz11;
        }
        Matriz mm = new Matriz(0, 0, "This Matri doesn't exist");
        return mm;
    }

    public static void selectMatriz(int indice) {
        String title;
        switch (indice) {
            case 0: //Informação Externa x Informação Externa
                title = " ← <AGREGAÇÃO> Informação Externa x Informação Externa  <AGREGAÇÃO>";
                Matriz m0 = new Matriz(tokensTraceability.getInformcaoExterna().size(), tokensTraceability.getInformcaoExterna().size(), title);
                m0.matriz(VerticalTraceController.getTokensVertical().getInformcaoExterna(),//col
                        VerticalTraceController.getTokensVertical().getInformcaoExterna(), true);//row
                break;
            case 1: //Informação Externa x Informação Organizacional
                title = " ← <REC> Informação Externa x Informação Organizacional <REC>";
                Matriz m1 = new Matriz(tokensTraceability.getInformcaoExterna().size(), tokensTraceability.getInformacaoOrg().size(), title);
                m1.matriz(VerticalTraceController.getTokensVertical().getInformcaoExterna(),//col
                        VerticalTraceController.getTokensVertical().getInformacaoOrg(), false);//row          

                break;
            case 2: //Informação Organizacional x Informação Organizacional
                title = " ← <AGREGAÇÃO> Informação Organizacional x Informação Organizacional  <AGREGAÇÃO>";
                Matriz m2 = new Matriz(tokensTraceability.getInformacaoOrg().size(), tokensTraceability.getInformacaoOrg().size(), title);
                m2.matriz(VerticalTraceController.getTokensVertical().getInformacaoOrg(),//col
                        VerticalTraceController.getTokensVertical().getInformacaoOrg(), true);//row
                break;
            case 3: //Informação Organizacional x Requisitos
                title = " ← <REC> Informação Organizacional x Requisitos  <REC>";
                Matriz m3 = new Matriz(tokensTraceability.getInformacaoOrg().size(), tokensTraceability.getRequisitos().size(), title);
                m3.matriz(VerticalTraceController.getTokensVertical().getInformacaoOrg(),//col
                        VerticalTraceController.getTokensVertical().getRequisitos(), false);//row
                break;
            case 4: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz m4 = new Matriz(tokensTraceability.getObjetivoSistema().size(), tokensTraceability.getObjetivoSistema().size(), title);
                m4.matriz(VerticalTraceController.getTokensVertical().getObjetivoSistema(),//col
                        VerticalTraceController.getTokensVertical().getObjetivoSistema(), true);//row
                break;
            case 5: //Objetivo do Sistema x Requisitos
                title = " ← <SAT> Objetivo do Sistema x Requisitos  <SAT>";
                Matriz m5 = new Matriz(tokensTraceability.getObjetivoSistema().size(), tokensTraceability.getRequisitos().size(), title);
                m5.matriz(VerticalTraceController.getTokensVertical().getObjetivoSistema(),//col
                        VerticalTraceController.getTokensVertical().getRequisitos(), false);//row
                break;
            case 6: //Stakeholder x Stakeholder
                title = " ← <AGREGAÇÃO> Stakeholder x Stakeholder  <AGREGAÇÃO>";
                Matriz m6 = new Matriz(tokensTraceability.getStakeholders().size(), tokensTraceability.getStakeholders().size(), title);
                m6.matriz(VerticalTraceController.getTokensVertical().getStakeholders(),//col
                        VerticalTraceController.getTokensVertical().getStakeholders(), true);//row
                break;
            case 7: //Stakeholder x Requisitos
                title = " ← <RESP> Stakeholder x Requisitos  <RESP>";
                Matriz m7 = new Matriz(tokensTraceability.getRequisitos().size(), tokensTraceability.getStakeholders().size(), title);
                m7.matriz(VerticalTraceController.getTokensVertical().getRequisitos(),
                        VerticalTraceController.getTokensVertical().getStakeholders(), false);//row

                break;
            case 8: //Requisitos x Requisitos
                title = " ← <AGREGAÇÃO> Requisitos x Requisitos  <AGREGAÇÃO>";
                Matriz m8 = new Matriz(tokensTraceability.getRequisitos().size(), tokensTraceability.getRequisitos().size(), title);
                m8.matriz(VerticalTraceController.getTokensVertical().getRequisitos(),//col
                        VerticalTraceController.getTokensVertical().getRequisitos(), true);//row
                break;
            case 9: //Requisitos x Informação Externa
                title = " ← <REC> Requisitos x Informação Externa  <REC>";
                Matriz m9 = new Matriz(tokensTraceability.getRequisitos().size(), tokensTraceability.getInformcaoExterna().size(), title);
                m9.matriz(VerticalTraceController.getTokensVertical().getRequisitos(),//col
                        VerticalTraceController.getTokensVertical().getInformcaoExterna(), false);//row
                break;
            case 10: //Objetivo do Sistema x Informação Organizacional
                title = " ← <REC> Objetivo do Sistema x Informação Organizacional  <REC>";
                Matriz m10 = new Matriz(tokensTraceability.getObjetivoSistema().size(), tokensTraceability.getInformacaoOrg().size(), title);
                m10.matriz(VerticalTraceController.getTokensVertical().getObjetivoSistema(),//col
                        VerticalTraceController.getTokensVertical().getInformacaoOrg(), false);//row
                break;
            case 11: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz m11 = new Matriz(tokensTraceability.getObjetivoSistema().size(), tokensTraceability.getObjetivoSistema().size(), title);
                m11.matriz(VerticalTraceController.getTokensVertical().getObjetivoSistema(),//col
                        VerticalTraceController.getTokensVertical().getObjetivoSistema(), true);//row
                break;
        }
    }

    private static TraceabilityView viewTraceability = null;

    public static Integer type;

    public VerticalTraceController(int i) {
        VerticalTraceController.type = i;
    }

    public static Integer getType() {
        return type;
    }

    public static void setTokensVertical(TokensTraceability tokens) {
        VerticalTraceController.tokensTraceability = tokens;
    }

    public static TokensTraceability getTokensVertical() {
        return tokensTraceability;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openVerticalTraceabilityView();
    }

    public static void openVerticalTraceabilityView() {
        if (type != null) {
            switch (type) {
                case 1://rastreabilidade vertical BPMN to UC
                    if (BPMNController.getFlagMapUseCases()) {
                        traceBPMNVertical = new TraceBPMNVertical();
                        traceBPMNVertical.TraceElementsBPMNVertical();

                        if (viewTraceability == null) {
                            viewTraceability = new TraceabilityView(4);
                            viewTraceability.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        }
                        viewTraceability.updateTableVerticalBPMNtoUCTraceability();
                        viewTraceability.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "YOU NEED FIRST MAPPING BPMN to Use Cases");
                    }

                    break;
                case 2://rastreabilidade vertical i* to UC 
                    if (Controller.getFlagMapUseCases()) {
                        traceIStarVertical = new TraceIStarVertical();
                        traceIStarVertical.TraceElementsIStarVertical();

                        if (viewTraceability == null) {
                            viewTraceability = new TraceabilityView(5);
                            viewTraceability.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        }
                        viewTraceability.updateTableVerticalIStartoUCTraceability();
                        viewTraceability.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "YOU NEED FIRST MAPPING i* to Use Cases");
                    }

                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "You need traceability vertical fisrt");
        }
    }

}
