/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.controller;

import br.unioeste.jgoose.TraceabilityHorizontal.TraceBPMNHorizontal;
import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.model.BPMNActivity;
import br.unioeste.jgoose.model.BPMNArtifact;
import br.unioeste.jgoose.model.BPMNElement;
import br.unioeste.jgoose.model.BPMNEvent;
import br.unioeste.jgoose.model.BPMNGateway;
import br.unioeste.jgoose.model.BPMNLink;
import br.unioeste.jgoose.model.BPMNParticipant;
import br.unioeste.jgoose.model.TokensBPMN;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.view.Matriz;
import br.unioeste.jgoose.view.TraceabilityView;
import br.unioeste.jgoose.view.UseCasesViewBPMN;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 *
 * @author Victor Augusto Pozzan
 */
@SuppressWarnings(value = "serial")
public class HorizontalBPMNTraceController extends AbstractAction {

    private static TokensTraceability tokensTraceBPMN;
    private static final Logger LOG = Logger.getLogger("console");

    private EditorJFrame traceabilityView;
    private static TraceBPMNHorizontal traceBPMNHorizontal;

    private TraceabilityView viewTraceability = null;

    private Map<mxCell, Element> vertex = new HashMap<>();
    private Map<mxCell, Element> edges = new HashMap<>();
    private Map<mxCell, Object> mapped = new HashMap<>();
    private Map<mxCell, mxCell> deleteds = new HashMap<>();

    private mxGraphComponent component;
    private mxGraph graph;
    private mxIGraphModel model;

    // Store elements for derivation
    private TokensBPMN modelBPMN;

    public static Matriz propertiesMatriz(int indice) {
        String title;
        switch (indice) {
            case 0: //Informação Externa x Informação Externa
                title = " ← <AGREGAÇÃO> Informação Externa x Informação Externa  <AGREGAÇÃO>";
                Matriz matriz0 = new Matriz(title, tokensTraceBPMN.getInformcaoExterna().size(),
                        tokensTraceBPMN.getInformcaoExterna().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        true);
                return matriz0;
            case 1: //Informação Externa x Informação Organizacional
                title = " ← <REC> Informação Externa x Informação Organizacional <REC>";
                Matriz matriz1 = new Matriz(title, tokensTraceBPMN.getInformcaoExterna().size(),
                        tokensTraceBPMN.getInformacaoOrg().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        false);
                return matriz1;
            case 2: //Informação Organizacional x Informação Organizacional
                title = " ← <AGREGAÇÃO> Informação Organizacional x Informação Organizacional  <AGREGAÇÃO>";
                Matriz matriz2 = new Matriz(title, tokensTraceBPMN.getInformacaoOrg().size(),
                        tokensTraceBPMN.getInformacaoOrg().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        true);
                return matriz2;                
            case 3: //Informação Organizacional x Requisitos
                title = " ← <REC> Informação Organizacional x Requisitos  <REC>";
                Matriz matriz3 = new Matriz(title, tokensTraceBPMN.getInformacaoOrg().size(),
                        tokensTraceBPMN.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        false);
                return matriz3;                 
            case 4: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz matriz4 = new Matriz(title, tokensTraceBPMN.getObjetivoSistema().size(),
                        tokensTraceBPMN.getObjetivoSistema().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        true);
                return matriz4;                 
            case 5: //Objetivo do Sistema x Requisitos
                title = " ← <SAT> Objetivo do Sistema x Requisitos  <SAT>";
                Matriz matriz5 = new Matriz(title, tokensTraceBPMN.getObjetivoSistema().size(),
                       tokensTraceBPMN.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        false);
                return matriz5;  
            case 6: //Stakeholder x Stakeholder
                title = " ← <AGREGAÇÃO> Stakeholder x Stakeholder  <AGREGAÇÃO>";
                Matriz matriz6 = new Matriz(title, tokensTraceBPMN.getStakeholders().size(),
                        tokensTraceBPMN.getStakeholders().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),
                        true);
                return matriz6;                 
            case 7: //Stakeholder x Requisitos
                title = " ← <RESP> Stakeholder x Requisitos  <RESP>";
                Matriz matriz7 = new Matriz(title, tokensTraceBPMN.getStakeholders().size(),
                        tokensTraceBPMN.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        false);
                return matriz7;                    
            case 8: //Requisitos x Requisitos
                title = " ← <AGREGAÇÃO> Requisitos x Requisitos  <AGREGAÇÃO>";
                Matriz matriz8 = new Matriz(title, tokensTraceBPMN.getRequisitos().size(),
                        tokensTraceBPMN.getRequisitos().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        true);
                return matriz8;                    
            case 9: //Requisitos x Informação Externa
                title = " ← <REC> Requisitos x Informação Externa  <REC>";
                Matriz matriz9 = new Matriz(title, tokensTraceBPMN.getRequisitos().size(),
                        tokensTraceBPMN.getInformcaoExterna().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),
                        false);
                return matriz9;               
            case 10: //Objetivo do Sistema x Informação Organizacional
                title = " ← <REC> Objetivo do Sistema x Informação Organizacional  <REC>";
                Matriz matriz10 = new Matriz(title, tokensTraceBPMN.getObjetivoSistema().size(),
                        tokensTraceBPMN.getInformacaoOrg().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),
                        false);
                return matriz10;                       
            case 11: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz matriz11 = new Matriz(title, tokensTraceBPMN.getObjetivoSistema().size(),
                        tokensTraceBPMN.getObjetivoSistema().size(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),
                        true);
                return matriz11;                      
        }
        Matriz mm = new Matriz(0, 0 , "This Matri doesn't exist");
        return mm;
    }

    public static void selectMatriz(int indice) {
        //refazer isso e colocar em uma classe específica
        String title;
        switch (indice) {
            case 0: //Informação Externa x Informação Externa
                title = " ← <AGREGAÇÃO> Informação Externa x Informação Externa  <AGREGAÇÃO>";
                Matriz m0 = new Matriz(tokensTraceBPMN.getInformcaoExterna().size(), tokensTraceBPMN.getInformcaoExterna().size(), title);
                m0.matriz(HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(), true);//row
                break;
            case 1: //Informação Externa x Informação Organizacional
                title = " ← <REC> Informação Externa x Informação Organizacional <REC>";
                Matriz m1 = new Matriz(tokensTraceBPMN.getInformcaoExterna().size(), tokensTraceBPMN.getInformacaoOrg().size(), title);
                m1.matriz(HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(), false);//row          

                break;
            case 2: //Informação Organizacional x Informação Organizacional
                title = " ← <AGREGAÇÃO> Informação Organizacional x Informação Organizacional  <AGREGAÇÃO>";
                Matriz m2 = new Matriz(tokensTraceBPMN.getInformacaoOrg().size(), tokensTraceBPMN.getInformacaoOrg().size(), title);
                m2.matriz(HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(), true);//row
                break;
            case 3: //Informação Organizacional x Requisitos
                title = " ← <REC> Informação Organizacional x Requisitos  <REC>";
                Matriz m3 = new Matriz(tokensTraceBPMN.getInformacaoOrg().size(), tokensTraceBPMN.getRequisitos().size(), title);
                m3.matriz(HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(), false);//row
                break;
            case 4: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz m4 = new Matriz(tokensTraceBPMN.getObjetivoSistema().size(), tokensTraceBPMN.getObjetivoSistema().size(), title);
                m4.matriz(HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(), true);//row
                break;
            case 5: //Objetivo do Sistema x Requisitos
                title = " ← <SAT> Objetivo do Sistema x Requisitos  <SAT>";
                Matriz m5 = new Matriz(tokensTraceBPMN.getObjetivoSistema().size(), tokensTraceBPMN.getRequisitos().size(), title);
                m5.matriz(HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(), false);//row
                break;
            case 6: //Stakeholder x Stakeholder
                title = " ← <AGREGAÇÃO> Stakeholder x Stakeholder  <AGREGAÇÃO>";
                Matriz m6 = new Matriz(tokensTraceBPMN.getStakeholders().size(), tokensTraceBPMN.getStakeholders().size(), title);
                m6.matriz(HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(), true);//row
                break;
            case 7: //Stakeholder x Requisitos
                title = " ← <RESP> Stakeholder x Requisitos  <RESP>";
                Matriz m7 = new Matriz(tokensTraceBPMN.getStakeholders().size(), tokensTraceBPMN.getRequisitos().size(), title);
                m7.matriz(HorizontalBPMNTraceController.getTokensTraceability().getStakeholders(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(), false);//row

                break;
            case 8: //Requisitos x Requisitos
                title = " ← <AGREGAÇÃO> Requisitos x Requisitos  <AGREGAÇÃO>";
                Matriz m8 = new Matriz(tokensTraceBPMN.getRequisitos().size(), tokensTraceBPMN.getRequisitos().size(), title);
                m8.matriz(HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(), true);//row
                break;
            case 9: //Requisitos x Informação Externa
                title = " ← <REC> Requisitos x Informação Externa  <REC>";
                Matriz m9 = new Matriz(tokensTraceBPMN.getRequisitos().size(), tokensTraceBPMN.getInformcaoExterna().size(), title);
                m9.matriz(HorizontalBPMNTraceController.getTokensTraceability().getRequisitos(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna(), false);//row
                break;
            case 10: //Objetivo do Sistema x Informação Organizacional
                title = " ← <REC> Objetivo do Sistema x Informação Organizacional  <REC>";
                Matriz m10 = new Matriz(tokensTraceBPMN.getObjetivoSistema().size(), tokensTraceBPMN.getInformacaoOrg().size(), title);
                m10.matriz(HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg(), false);//row
                break;
            case 11: //Objetivo do Sistema x Objetivo do Sistema
                title = " ← <AGREGAÇÃO> Objetivo do Sistema x Objetivo do Sistema  <AGREGAÇÃO>";
                Matriz m11 = new Matriz(tokensTraceBPMN.getObjetivoSistema().size(), tokensTraceBPMN.getObjetivoSistema().size(), title);
                m11.matriz(HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(),//col
                        HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema(), true);//row
                break;
        }

    }

    public static void setTokensHorizontal(TokensTraceability tokens) {
        HorizontalBPMNTraceController.tokensTraceBPMN = tokens;
    }

    public static TokensTraceability getTokensTraceability() {
        return tokensTraceBPMN;
    }

    public HorizontalBPMNTraceController(EditorJFrame e4jInstance) {
        this.traceabilityView = traceabilityView;
    }

    //find edges
    private void findEdges(mxICell cell) {

        if (!(cell.getValue() instanceof Element)) {
            return;
        }

        if (cell.isEdge()) {
            convertEdge((mxCell) cell);
            return;
        } else if (cell.getChildCount() == 0) {
            return;
        } else {
            for (int i = 0; i < cell.getChildCount(); i++) {
                findEdges(cell.getChildAt(i));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.debug("BPMN Traceability Horizontal init.");
        vertex.clear();
        edges.clear();
        modelBPMN = new TokensBPMN();

        BPMNController.setTokensBPMN(modelBPMN);

        Object source = e.getSource();

        if (source instanceof mxGraphComponent) {
            this.component = (mxGraphComponent) source;
            this.graph = component.getGraph();
            this.model = graph.getModel();

            // get all cells
            Object[] cells = this.selectAll();

            // filter cells and put them in the map structure (elements and links)
            // 1) compute all vertex
            for (Object c : cells) {
                mxCell cell = (mxCell) c;

                if (cell.isVertex()) {
                    this.convertVertex(cell);
                }
            }

            // 2) compute all edges
            for (Object c : cells) {
                mxCell cell = (mxCell) c;

                findEdges(cell);
            }

            LOG.debug("total elements interpreted: " + vertex.size());
            LOG.debug("total links interpreted: " + edges.size());

            //System.out.println("ModelBPMN:\n + " + modelBPMN.toString());
            // close editor and call view to select the main actor (system).
        }
    }

    /**
     * Root? selection.
     *
     * @return user selection or forced 'selectAll'.
     */
    private Object[] selectAll() {
        // first, get user selected cells
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        graph.clearSelection();

        LOG.debug("Total root cells founded: " + cells.length);
        return cells;
    }

    private Object convertEdge(mxCell cell) {
        Object result = null;
        Element element = null;
        Object v = cell.getValue();

        if (v instanceof Element) {
            element = (Element) v;
        } else {
            LOG.debug("Value of cell is not a Element type.");
            return null;
        }

        String tagName = element.getTagName();
        String type = element.getAttribute("type");

        if (tagName == null || type == null) {
            LOG.debug("tagname and type is null.");
            return null;
        }

        edges.put(cell, element);

        // 1) get elements of this edge
        mxCell source = (mxCell) cell.getSource();
        mxCell target = (mxCell) cell.getTarget();

        System.out.println("source id " + source.getId() + " target id: " + target.getId());
        // 2) get mapped elements to create a link between then
        Object mappedSource = mapped.get(source);
        if (mappedSource == null) {
            LOG.error("mapped source dont founded.");
        }

        Object mappedTarget = mapped.get(target);
        if (mappedTarget == null) {
            LOG.error("mapped target dont founded.");
        }

        if (mappedSource != null && mappedTarget != null) {
            BPMNLink bpmnLink = new BPMNLink();

            bpmnLink.setCode(cell.getId());
            bpmnLink.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));
            bpmnLink.setFrom((BPMNElement) mappedSource);
            bpmnLink.setTo((BPMNElement) mappedTarget);

            switch (type) {
                case "sequence_flow":
                    bpmnLink.setType(BPMNLink.SEQUENCE);
                    break;
                case "association_flow":
                    bpmnLink.setType(BPMNLink.ASSOCIATION);
                    break;
                case "message_flow":
                    bpmnLink.setType(BPMNLink.MESSAGE);
                    break;
                default:
                    LOG.debug("case for "
                            + "'" + type
                            + "' link type is not implemented yet.");
                    break;
            }

            result = bpmnLink.getCode();
            mapped.put(cell, bpmnLink);
            modelBPMN.addLink(bpmnLink);

            // update link in 'from' and 'to'
            if (mappedSource instanceof BPMNElement) {
                BPMNElement bpmnElementSource = (BPMNElement) mappedSource;
                bpmnElementSource.addLinkFrom(bpmnLink);
            }

            if (mappedTarget instanceof BPMNElement) {
                BPMNElement bpmnElementTarget = (BPMNElement) mappedTarget;
                bpmnElementTarget.addLinkTo(bpmnLink);
            }
        }

        return result;
    }

    /**
     *
     * @param cell
     * @return element mapped or null with any error.
     */
    private Object convertVertex(mxCell cell) {

        Object result = null;
        Object father = null;

        Element element = null;
        Object v = cell.getValue();

        if (v instanceof Element) {
            element = (Element) v;
        } else {
            LOG.debug("Value of cell is not a Element type.");
            return null;
        }

        String tagName = element.getTagName();
        String type = element.getAttribute("type");

        if (tagName == null || type == null) {
            LOG.debug("tagname and type is null.");
            return null;
        }

        switch (tagName) {
            // is a vertex
            case "event":
                vertex.put(cell, element);

                BPMNEvent event = new BPMNEvent();
                event.setCode(cell.getId());
                event.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));

                switch (type) {
                    case "end_event":
                        event.setEventType(BPMNEvent.END);
                        break;
                    case "end-cancel":
                        event.setEventType(BPMNEvent.END_CANCEL);
                        break;
                    case "end-compensation":
                        event.setEventType(BPMNEvent.END_COMPENSATION);
                        break;
                    case "end-error":
                        event.setEventType(BPMNEvent.END_ERROR);
                        break;
                    case "end-link":
                        event.setEventType(BPMNEvent.END_LINK);
                        break;
                    case "end-message":
                        event.setEventType(BPMNEvent.END_MESSAGE);
                        break;
                    case "end-multiple":
                        event.setEventType(BPMNEvent.END_MULTIPLE);
                        break;
                    case "end-terminate":
                        event.setEventType(BPMNEvent.END_TERMINATE);
                        break;
                    case "intermediate_event":
                        event.setEventType(BPMNEvent.INTERMEDIATE);
                        break;
                    case "intermediate-cancel":
                        event.setEventType(BPMNEvent.INTERMEDIATE_CANCEL);
                        break;
                    case "intermediate-compensation":
                        event.setEventType(BPMNEvent.INTERMEDIATE_COMPENSATION);
                        break;
                    case "intermediate-error":
                        event.setEventType(BPMNEvent.INTERMEDIATE_ERROR);
                        break;
                    case "intermediate-link":
                        event.setEventType(BPMNEvent.INTERMEDIATE_LINK);
                        break;
                    case "intermediate-message":
                        event.setEventType(BPMNEvent.INTERMEDIATE_MESSAGE);
                        break;
                    case "intermediate-multiple":
                        event.setEventType(BPMNEvent.INTERMEDIATE_MULTIPLE);
                        break;
                    case "intermediate-rule":
                        event.setEventType(BPMNEvent.INTERMEDIATE_RULE);
                        break;
                    case "intermediate-timer":
                        event.setEventType(BPMNEvent.INTERMEDIATE_TIMER);
                        break;
                    case "start_event":
                        event.setEventType(BPMNEvent.START);
                        break;
                    case "start-link":
                        event.setEventType(BPMNEvent.START_LINK);
                        break;
                    case "start-message":
                        event.setEventType(BPMNEvent.START_MESSAGE);
                        break;
                    case "start-multiple":
                        event.setEventType(BPMNEvent.START_MULTIPLE);
                        break;
                    case "start-rule":
                        event.setEventType(BPMNEvent.START_RULE);
                        break;
                    case "start-timer":
                        event.setEventType(BPMNEvent.START_TIMER);
                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' event type is not implemented yet.");
                        break;
                }

                // set father, if exists
                father = cell.getParent();
                if (father != null && father instanceof mxCell) {
                    mxCell cellFather = (mxCell) father;

                    if (!cellFather.getId().equals("1")) { // Possui elemento pai
                        event.setParent(cellFather.getId());
                    }
                }

                modelBPMN.addEvent(event);
                mapped.put(cell, event);
                result = event.getCode();

                break;
            case "activity":
                vertex.put(cell, element);

                BPMNActivity activity = new BPMNActivity();
                activity.setCode(cell.getId());
                activity.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));

                switch (type) {
                    case "task":
                        activity.setActivityType(BPMNActivity.TASK);
                        break;
                    case "subprocess":
                        activity.setActivityType(BPMNActivity.SUBPROCESS);

                        // check if have children
                        int children = cell.getChildCount();
                        if (children > 0) {
                            // iterate children
                            for (int i = children - 1; i >= 0; i--) {

                                mxCell child = (mxCell) cell.getChildAt(i);
                                String t = child.getAttribute("type");
                                if (t == null) {
                                    LOG.debug("(null) (null) children removed..");
                                    deleteds.put(cell, child);
                                    cell.remove(child);
                                } else if (child.isVertex()) {
                                    Object childObject = convertVertex(child);
                                    if (childObject != null) {
                                        activity.addChildren((String) childObject);
                                    }
                                } /*else if(child.isEdge()){
                                        convertEdge(child);
                                    }*/
                            }
                        }

                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' activity type is not implemented yet.");
                        break;
                }

                // set father, if exists
                father = cell.getParent();
                if (father != null && father instanceof mxCell) {
                    mxCell cellFather = (mxCell) father;

                    if (!cellFather.getId().equals("1")) { // Possui elemento pai
                        activity.setParent(cellFather.getId());
                    }
                }

                modelBPMN.addActivity(activity);
                mapped.put(cell, activity);
                result = activity.getCode();

                break;
            case "artifact":
                vertex.put(cell, element);

                BPMNArtifact artifact = new BPMNArtifact();
                artifact.setCode(cell.getId());
                artifact.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));

                switch (type) {
                    case "data_object":
                        artifact.setArtifactType(BPMNArtifact.DATA_OBJECT);
                        break;
                    case "data_store":
                        artifact.setArtifactType(BPMNArtifact.DATA_STORE);
                        break;
                    case "text_annotation":
                        artifact.setArtifactType(BPMNArtifact.TEXT_ANNOTATION);
                        break;
                    case "group":
                        artifact.setArtifactType(BPMNArtifact.GROUP);

                        // check if have children
                        int children = cell.getChildCount();
                        if (children > 0) {
                            // iterate children
                            for (int i = children - 1; i >= 0; i--) {

                                mxCell child = (mxCell) cell.getChildAt(i);
                                String t = child.getAttribute("type");
                                if (t == null) {
                                    LOG.debug("(null) (null) children removed..");
                                    deleteds.put(cell, child);
                                    cell.remove(child);
                                } else if (child.isVertex()) {
                                    Object childObject = convertVertex(child);
                                    if (childObject != null) {
                                        artifact.addChildren((String) childObject);
                                    }
                                } /*else if(child.isEdge()){
                                        convertEdge(child);
                                    } */
                            }
                        }

                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' artifact type is not implemented yet.");
                        break;
                }

                // set father, if exists
                father = cell.getParent();
                if (father != null && father instanceof mxCell) {
                    mxCell cellFather = (mxCell) father;

                    if (!cellFather.getId().equals("1")) { // Possui elemento pai
                        artifact.setParent(cellFather.getId());
                    }
                }

                modelBPMN.addArtifact(artifact);
                mapped.put(cell, artifact);
                result = artifact.getCode();

                break;
            case "swimlane":
                vertex.put(cell, element);

                BPMNParticipant swimlane = new BPMNParticipant();
                swimlane.setCode(cell.getId());
                swimlane.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));

                switch (type) {
                    case "pool":
                        swimlane.setParticipantType(BPMNParticipant.POOL);
                        break;
                    case "lane":
                        swimlane.setParticipantType(BPMNParticipant.LANE);
                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' artifact type is not implemented yet.");
                        break;
                }

                // check if have children
                int children = cell.getChildCount();
                if (children > 0) {
                    // iterate children
                    for (int i = children - 1; i >= 0; i--) {

                        mxCell child = (mxCell) cell.getChildAt(i);
                        String t = child.getAttribute("type");
                        if (t == null) {
                            LOG.debug("(null) (null) children removed..");
                            deleteds.put(cell, child);
                            cell.remove(child);
                        } else if (child.isVertex()) {
                            Object childObject = convertVertex(child);
                            if (childObject != null) {
                                swimlane.addChildren((String) childObject);
                            }
                        } /*else if(child.isEdge()){
                                convertEdge(child);
                            }*/
                    }
                }

                // set father, if exists
                father = cell.getParent();
                if (father != null && father instanceof mxCell) {
                    mxCell cellFather = (mxCell) father;

                    if (!cellFather.getId().equals("1")) { // Possui elemento pai
                        swimlane.setParent(cellFather.getId());
                    }
                }

                modelBPMN.addParticipant(swimlane);
                mapped.put(cell, swimlane);
                result = swimlane.getCode();

                break;
            case "gateway":
                vertex.put(cell, element);

                BPMNGateway gateway = new BPMNGateway();
                gateway.setCode(cell.getId());
                gateway.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));

                switch (type) {
                    case "gateway":
                        gateway.setGatewayType(BPMNGateway.GATEWAY_BASIC);
                        break;
                    case "parallel":
                        gateway.setGatewayType(BPMNGateway.PARALLEL);
                        break;
                    case "inclusive":
                        gateway.setGatewayType(BPMNGateway.INCLUSIVE);
                        break;
                    case "event_based":
                        gateway.setGatewayType(BPMNGateway.EVENT_BASED);
                        break;
                    case "exclusive_event_based":
                        gateway.setGatewayType(BPMNGateway.EXCLUSIVE_EVENT_BASED);
                        break;
                    case "exclusive":
                        gateway.setGatewayType(BPMNGateway.EXCLUSIVE);
                        break;
                    case "parallel_event_based":
                        gateway.setGatewayType(BPMNGateway.PARALLEL_EVENT_BASED);
                        break;
                    case "complex":
                        gateway.setGatewayType(BPMNGateway.COMPLEX);
                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' event type is not implemented yet.");
                        break;
                }

                // set father, if exists
                father = cell.getParent();
                if (father != null && father instanceof mxCell) {
                    mxCell cellFather = (mxCell) father;

                    if (!cellFather.getId().equals("1")) { // Possui elemento pai
                        gateway.setParent(cellFather.getId());
                    }
                }

                modelBPMN.addGateway(gateway);
                mapped.put(cell, gateway);
                result = gateway.getCode();

                break;
            default:
                LOG.debug("case for "
                        + "'" + tagName
                        + "' tagName is not implemented yet.");
                break;
        }
        return result;
    }

}
