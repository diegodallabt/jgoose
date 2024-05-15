/*    */ package br.unioeste.jgoose.e4j.actions;
/*    */ 
       
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import br.unioeste.jgoose.model.IStarElement;
import br.unioeste.jgoose.model.IStarLink;
import br.unioeste.jgoose.util.IStarUtils;
/*    */ import com.mxgraph.model.mxCell;
/*    */ import com.mxgraph.model.mxGeometry;
/*    */ import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.shape.mxStencilShape;
/*    */ import com.mxgraph.swing.util.mxGraphActions;
/*    */ import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;
/*    */ import com.mxgraph.view.mxGraph;
/*    */ import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
/*    */ import javax.swing.AbstractAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddBoundary
/*    */   extends AbstractAction
/*    */ {
/* 18 */   final int PORT_DIAMETER = 700;
/*    */   
/* 20 */   final int PORT_RADIUS = 700;

           Map<String, mxCell> mapping = new HashMap<>();
           
           public Map<String, mxCell> getMapping(){
               return mapping;
           }
           
/*    */   
/*    */   public void actionPerformed(ActionEvent e)
/*    */   {
/* 24 */     mxGraph graph = mxGraphActions.getGraph(e);
/* 25 */     if ((graph != null) && (!graph.isSelectionEmpty())) {
/* 26 */       mxCell cell = (mxCell)graph.getSelectionCell();
/* 27 */       boolean isActor = cell.getAttribute("type").matches("actor|agent");
/* 28 */       boolean hasChild = cell.getChildCount() > 0;
/*    */       
/* 30 */       if ((cell.isVertex()) && (!hasChild) && (isActor)) {
/* 31 */         mxIGraphModel model = graph.getModel();
/*    */         
/*    */ 
/* 34 */         model.beginUpdate();
/*    */         
/*    */ 
/*    */ 
/* 38 */         mxGeometry geo = new mxGeometry(0, 0.5, 80.0D, 80.0D);
/*    */         
/*    */ 
/*    */ 
/* 42 */         geo.setOffset(new mxPoint(-25.0D, -25.0D));
/* 43 */         geo.setRelative(true);
/*    */         
/*    */ 
/* 46 */         String style = "";
/* 47 */         switch (cell.getAttribute("type")) {
/*    */         case "actor": 
/* 49 */           style = "shape=Actor";
/* 50 */           break;
/*    */         case "agent": 
/* 52 */           style = "shape=Agent";
/* 53 */           break;
/*    */         case "role": 
/* 55 */           style = "shape=Role";
/* 56 */           break;
/*    */         case "position": 
/* 58 */           style = "shape=Position";
/*    */         }
/*    */         
/*    */         
/*    */         
/* 63 */         mxCell port = new mxCell(cell.getAttribute("label"), geo, style);/*    */         
/* 65 */         port.setVertex(true);
/*    */         
/* 67 */         port.setConnectable(false);
/* 68 */         graph.addCell(port, cell);
/*    */         
/*    */ 
/* 71 */         cell.setStyle(cell.getStyle() + ";noLabel=1");
/* 72 */         graph.cellsOrdered(new Object[] { cell }, true);
/*    */         
/* 74 */         model.endUpdate();
/*    */       }
/*    */     }


/*    */   }
            public Map<String, mxCell> setBoundary(mxCell cell, mxGraph graph, Integer sizeChildren, ArrayList<IStarElement> childrens) throws IOException
/*    */   {
/* 24 */     ArrayList<IStarLink> ands = Controller.getOme().getAND();
             ArrayList<IStarLink> ors = Controller.getOme().getOR();
            
/* 25 */     if ((graph != null)) {
/* 26 */       
/* 27 */       boolean isActor = cell.getAttribute("type").matches("actor|agent");
/* 28 */       boolean hasChild = cell.getChildCount() > 0;
/*    */       
/* 30 */       if ((cell.isVertex()) && (!hasChild) && (isActor)) {
/* 31 */         mxIGraphModel model = graph.getModel();
/*    */         
/*    */      
/* 34 */         model.beginUpdate();
/*    */         
/*    */ 
/*    */ 
/* 38 */         mxGeometry geo = new mxGeometry(0, 0.5, 80.0D, 80.0D);
/*    */ 
/*    */ 
/* 42 */         geo.setOffset(new mxPoint(-25.0D, 25.0D));
/* 43 */         geo.setRelative(true);
/*    */         
/*    */ 
/* 46 */         String style = "";
/* 47 */         switch (cell.getAttribute("type")) {
/*    */         case "actor": 
/* 49 */           style = "shape=Actor";
/* 50 */           break;
/*    */         case "agent": 
/* 52 */           style = "shape=Agent";
/* 53 */           break;
/*    */         case "role": 
/* 55 */           style = "shape=Role";
/* 56 */           break;
/*    */         case "position": 
/* 58 */           style = "shape=Position";
/*    */         }
/*    */         
/*    */         
/*    */         
/* 63 */         mxCell port = new mxCell(cell.getAttribute("label"), geo, style);/*    */         
/* 65 */         port.setVertex(true);
/*    */         
/* 67 */         port.setConnectable(false);
/* 68 */         graph.addCell(port, cell);

                File shapesFolder = new File("resources/shapes/elements/");
                File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
                String nodeXml = nodeXml = mxUtils.readFile(files[7].getAbsolutePath());
                mxStencilShape newShape = new mxStencilShape(nodeXml);
                String styleCase = "shape=" + newShape.getName() + ";";
/*    */        Element test;
                Element newTask;
                double xCase = 40;
                double yCase = 20;
                String cod;
                mxCell aresta;
                Element linkAND;
                Element linkOR;
                
                Collections.reverse(childrens);
                 
                for(IStarElement element : childrens){
                    if("<< include >>".equals(element.getName()) || "<< extend >>".equals(element.getName())){
                        continue;
                    }
                    
                    test = IStarUtils.createTask();
                    test.setAttribute("label", element.getName().replace("\"", ""));
                    mxGeometry geoDep = new mxGeometry(xCase, yCase, 120, 60);
                   
                    xCase = xCase == 240? 40 : 240;
                    yCase += 100;
                    geoDep.setX(xCase);
                    geoDep.setY(yCase);

                    mxCell depCell = new mxCell(element.getName(), geoDep, styleCase);
                    depCell.setVertex(true);
                    graph.addCell(depCell, cell);
                    
                    
                    mapping.put(element.getCod(), depCell);
                    
                }
                
/*    */        for(IStarLink link : ands){
                    String codTo = link.getTo();
                    String codFrom = link.getFrom();

                    mxCell to = mapping.get(codTo);
                    mxCell from = mapping.get(codFrom);
                  
                    linkAND = IStarUtils.createDecomposition();
                    mxGeometry geom = new mxGeometry(0, 0, 80, 80);
                    mxCell cellAND = new mxCell(linkAND, geom, "straight;endArrow=decomposition;noLabel=1");

                    cellAND.setEdge(true);

                    cellAND.setSource(to);
                    cellAND.setTarget(from);
                    graph.addCell(cellAND);
                }

                
                String name = "Default";
                for(IStarLink link : ors){
                  boolean rgc12_validation = false;
                  String codTo = link.getTo();
                  String codFrom = link.getFrom();
                  
                  for(IStarLink rgc12 : ands){
                      
                     if(rgc12.getFrom().equals(codTo)){
                        Collections.reverse(childrens);
                        for(IStarElement element : childrens){
                            if(element.getCod().equals(codFrom)){
                                name = element.getName();
                            }
                        }
                        newTask = IStarUtils.createTask();
                        newTask.setAttribute("label", name);
                        mxGeometry geoDep = new mxGeometry(xCase, yCase, 120, 60);
                        xCase += 80;
                        yCase += yCase > 300? 100 : 300;
                        geoDep.setX(xCase);
                        geoDep.setY(yCase);
                        System.out.println("NAME ::: " + name);
                        mxCell depCell = new mxCell(name, geoDep, styleCase);
                        depCell.setVertex(true);
                        graph.addCell(depCell, cell);
                        
                        linkAND = IStarUtils.createDecomposition();
                        mxGeometry geom = new mxGeometry(0, 0, 80, 80);
                        mxCell cellAND = new mxCell(linkAND, geom, "straight;endArrow=decomposition;noLabel=1");

                        cellAND.setEdge(true);
                        
                        mxCell to = mapping.get(rgc12.getFrom());
                        
                        
                        cellAND.setSource(depCell);
                        cellAND.setTarget(to);
                        graph.addCell(cellAND);
                        
                        linkOR = IStarUtils.createMeansEnd();
                        mxCell cellOR = new mxCell(linkOR, geom, "straight;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");

                        cellOR.setEdge(true);

                        to = mapping.get(codFrom);
                        
                        cellOR.setSource(to);
                        cellOR.setTarget(depCell);
                        graph.addCell(cellOR);
                    
                        rgc12_validation = true;
                     }
                     
                  }
                  
                  if(rgc12_validation){
                      continue;
                  }
                  
                  mxCell to = mapping.get(codTo);
                  mxCell from = mapping.get(codFrom);
                  
                  linkOR = IStarUtils.createMeansEnd();
                  mxGeometry geom = new mxGeometry(0, 0, 80, 80);
                  mxCell cellOR = new mxCell(linkOR, geom, "straight;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle");

                  cellOR.setEdge(true);

                  cellOR.setSource(from);
                  cellOR.setTarget(to);
                  graph.addCell(cellOR);
                }
/* 71 */         cell.setStyle(cell.getStyle() + ";noLabel=1");
/* 72 */         graph.cellsOrdered(new Object[] { cell }, true);
/*    */         
/* 74 */         model.endUpdate();
/*    */       }
/*    */     }
                System.out.println("TUPLA MAPEADA" + mapping);
                return mapping;
            }
/*    */ }
