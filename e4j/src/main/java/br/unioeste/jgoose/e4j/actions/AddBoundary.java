/*    */ package br.unioeste.jgoose.e4j.actions;
/*    */ 
       
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import br.unioeste.jgoose.model.IStarElement;
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
/* 18 */   final int PORT_DIAMETER = 80;
/*    */   
/* 20 */   final int PORT_RADIUS = 140;

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
/* 24 */     
            
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

                File shapesFolder = new File("resources/shapes/elements/");
                File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
                String nodeXml = nodeXml = mxUtils.readFile(files[7].getAbsolutePath());
                mxStencilShape newShape = new mxStencilShape(nodeXml);
                String styleCase = "shape=" + newShape.getName() + ";";
/*    */        Element test;
                double xCase = 400;
                double yCase = 100;
                String cod;
                
                
                
                 
                 
                for(IStarElement element : childrens){
                    
                    test = IStarUtils.createTask();
                    
                    test.setAttribute("label", element.getName().replace("\"", ""));
                    mxGeometry geoDep = new mxGeometry(xCase, yCase, 120, 60);
                    geoDep.setX(xCase);
                    geoDep.setY(yCase);

                    mxCell depCell = new mxCell(element.getName(), geoDep, styleCase);
                    depCell.setVertex(true);
                    graph.addCell(depCell, cell);
                    
                    mapping.put(element.getCod(), depCell);
                    
                }
/*    */ 
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
