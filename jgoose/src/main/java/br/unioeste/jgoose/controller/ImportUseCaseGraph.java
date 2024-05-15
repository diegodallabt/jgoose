/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unioeste.jgoose.controller;

import br.unioeste.jgoose.UCToIStar.MappingUCToIStar;
import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.model.BPMNParticipant;
import br.unioeste.jgoose.model.IStarActorElement;
import br.unioeste.jgoose.model.IStarElement;
import br.unioeste.jgoose.model.TokensOpenOME;
import br.unioeste.jgoose.model.TokensUseCase;
import br.unioeste.jgoose.model.UCActor;
import br.unioeste.jgoose.model.UCElement;
import br.unioeste.jgoose.model.UCLink;
import br.unioeste.jgoose.model.UCUseCase;
import br.unioeste.jgoose.view.MainView;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;

/**
 *
 * @author Diego Dalla Bernardina Thedoldi
 */
@SuppressWarnings(value = "serial")
public class ImportUseCaseGraph extends AbstractAction {
    
    private static MainView mainView = new MainView();;
    private final EditorJFrame E4JUseCases;
    private static final Logger LOG = Logger.getLogger("console");
    
    private mxGraphComponent component;
    private mxGraph graph;
    private mxIGraphModel model;
    
    private Map<mxCell, Element> vertex = new HashMap<>();
    private Map<mxCell, Element> edges = new HashMap<>();
    private Map<mxCell, Object> mapped = new HashMap<>();
    
     private TokensUseCase modelUC = UCController.getTokensUC();
    
    public ImportUseCaseGraph(EditorJFrame E4JUseCases) {
        this.E4JUseCases = E4JUseCases;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        LOG.debug("mapping for i* init.");
        vertex.clear();
        edges.clear();
        modelUC = new TokensUseCase();

        UCController.setTokensUC(modelUC);

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
                
            // 3) link actor and use cases
            for (UCActor actor : modelUC.getActorUC()) {
                for(UCLink link : modelUC.getLink()){
                    if(link.getFrom().getCode() == actor.getCode()){
                        for(UCUseCase useCase : modelUC.getUseCase()){
                            if(link.getTo().getCode() == useCase.getCode()){
                                actor.addUseCase(useCase);
                            }
                        }
                    }
                }
            }

        }
        try {
            // showActorPrimarySelectionView();
            new MappingUCToIStar();
            
            mainView.setE4JUseCases(E4JUseCases);
            mainView.showE4JiStarMappedFromUC();
            
            this.E4JUseCases.setVisible(false);
        } catch (HeadlessException ex) {
            
        } catch (IOException ex) {
           
        }

    }

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
        System.out.println(tagName + ' ' + type);
        if (tagName == null || type == null) {
            LOG.debug("tagname and type is null.");
            return null;
        }

        edges.put(cell, element);

        // 1) get elements of this edge
        mxCell source = (mxCell) cell.getSource();
        mxCell target = (mxCell) cell.getTarget();


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
            UCLink ucLink = new UCLink();

            ucLink.setCode(cell.getId());
            ucLink.setLabel(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));
            ucLink.setFrom((UCElement) mappedSource);
            ucLink.setTo((UCElement) mappedTarget);

            switch (type) {
                case "extend":
                    ucLink.setType(UCLink.EXTEND);
                    break;
                case "association":
                    ucLink.setType(UCLink.ASSOCIATION);
                    break;
                case "generalization":
                    ucLink.setType(UCLink.GENERALIZATION);
                    break;
                case "include":
                    ucLink.setType(UCLink.INCLUDE);
                    break;
                default:
                    LOG.debug("case for "
                            + "'" + type
                            + "' link type is not implemented yet.");
                    break;
            }

            result = ucLink.getCode();
            
            mapped.put(cell, ucLink);
            modelUC.addLink(ucLink);

            // update link in 'from' and 'to'
            if (mappedSource instanceof UCElement) {
                UCElement ucElementSource = (UCElement) mappedSource;
                ucElementSource.addLinkFrom(ucLink);
            }

            if (mappedTarget instanceof UCElement) {
                UCElement ucElementTarget = (UCElement) mappedTarget;
                ucElementTarget.addLinkTo(ucLink);
            }
        }
        return result;
    }

    private void findEdges(mxCell cell) {
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
                findEdges((mxCell) cell.getChildAt(i));
            }
        }
    }

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

        System.out.println("TagNAME:" + tagName);
        switch (tagName) {
            // is a vertex
            case "actorLink":
                vertex.put(cell, element);

                UCActor actor = new UCActor();
                actor.setCode(cell.getId());
                actor.setName(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));
                
                if("secondary_actor".equals(type)){
                    actor.setSecondary(true);
                    LOG.debug("set secondary actor.");
                }
                // set father, if exists
                father = cell.getParent();

                modelUC.addActor(actor);
                mapped.put(cell, actor);
                result = actor.getCode();

                break;
            case "ielement":
                vertex.put(cell, element);

                UCUseCase usecase = new UCUseCase();
                usecase.setCode(cell.getId());
                usecase.setName(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));

                modelUC.addUseCase(usecase);
                mapped.put(cell, usecase);
                result = usecase.getCode();

                break;
            case "ielementBoundary":
                vertex.put(cell, element);
                
                UCActor actorSystem = new UCActor();             
                actorSystem.setCode(cell.getId());
                
                actorSystem.setName(element.getAttribute("label").replaceAll("\n", " ").replaceAll("\\s+", " ").replaceAll("^\\s+", ""));
                actorSystem.setSystem(true);
                modelUC.addActor(actorSystem);
                
                mapped.put(cell, actorSystem);
                // check if have children
                int children = cell.getChildCount();
                if (children > 0) {
                    // iterate children
                    for (int i = children - 1; i >= 0; i--) {
                       mxCell child = (mxCell) cell.getChildAt(i);
                       UCUseCase usecaseSystem = new UCUseCase();
                       usecaseSystem.setCode(child.getId());
                       
                       usecaseSystem.setName(child.getAttribute("label"));
                       modelUC.addUseCase(usecaseSystem);
                       mapped.put(child, usecaseSystem);
                       result = usecaseSystem.getCode();
                       
                       actorSystem.adduseCasesSystem(usecaseSystem);
                    }
                    
                }
        }
        return result;
    
    }
} 
        
        
