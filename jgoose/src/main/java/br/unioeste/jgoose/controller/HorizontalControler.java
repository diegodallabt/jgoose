/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.controller;

import br.unioeste.jgoose.TraceabilityHorizontal.TraceBPMNHorizontal;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceIStarHorizontal;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceUCHorizontal;
import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.view.TraceabilityView;
import br.unioeste.jgoose.view.TraceabilityView1;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor August Pozzan
 */
public class HorizontalControler extends AbstractAction {

    private static HorizontalIStarTraceController horizontalIStarTraceController;
    private static TraceIStarHorizontal traceIStarHorizontal;

    private static HorizontalBPMNTraceController horizontalBPMNTraceController;
    private static TraceBPMNHorizontal traceBPMNHorizontal;

    private static HorizontalUseCaseTraceController horizontalUseCaseTraceController;
    private static TraceUCHorizontal traceUCHorizontal;

    private static TraceabilityView viewTraceability = null;
    private static TraceabilityView1 viewTraceability1 = null;

    public static int type;
    public static EditorJFrame E4J;
    public static int index = -1;

    public HorizontalControler(EditorJFrame E4J, int type) {
        this.type = type;
        this.E4J = E4J;
        index = -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openHorizontalTraceabilityView(e);
    }

    public static void openHorizontalTraceabilityView(ActionEvent e) {
        switch (type) {
            case 1: // I* Horizontal Trace
                horizontalIStarTraceController = new HorizontalIStarTraceController(E4J);
                horizontalIStarTraceController.actionPerformed(e);
                traceIStarHorizontal = new TraceIStarHorizontal();
                traceIStarHorizontal.TraceElementsIStarHorizontal();
                index = 3;
                break;

            case 2: // BPMN Horizontal Trace
                horizontalBPMNTraceController = new HorizontalBPMNTraceController(E4J);
                horizontalBPMNTraceController.actionPerformed(e);
                traceBPMNHorizontal = new TraceBPMNHorizontal();
                traceBPMNHorizontal.TraceElementsBPMNHorizontal();
                index = 2;
                break;

            case 3: // UC Horizontal Trace
                horizontalUseCaseTraceController = new HorizontalUseCaseTraceController(E4J);
                horizontalUseCaseTraceController.actionPerformed(e);
                traceUCHorizontal = new TraceUCHorizontal();
                traceUCHorizontal.TraceElementsUCHorizontal();
                index = 1;
                break;

            default:
                JOptionPane.showMessageDialog(null, "Sorry! An error occurred");
                break;
        }

        openViewTraceabilityHorizontal();

    }

    public static void openViewTraceabilityHorizontal() {
        if (index != -1) {
            if (viewTraceability1 == null) {
                viewTraceability1 = new TraceabilityView1(index);
                viewTraceability1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
            switch (type) {
                case 1: //update i* trace Horizontal
                    viewTraceability1.updateTableIStarHorizontalTraceability();
                    break;

                case 2: //update BPMN trace Horizontal
                    viewTraceability1.updateTableBPMNHorizontalTraceability();
                    break;

                case 3: //update UC trace Horizontale
                    viewTraceability1.updateTableUCHorizontalTraceability();
                    break;
            }
            viewTraceability1.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "You need Mapping Horizontal first");
        }
    }
}
