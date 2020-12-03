package br.unioeste.jgoose.e4j.swing.palettes;

import br.unioeste.jgoose.e4j.actions.ImporStencilAction;
import br.unioeste.jgoose.e4j.actions.ImportStencilAction;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import br.unioeste.jgoose.util.BPMNUtils;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class SwinLanePalette extends AbstractPalette {

    private static final Logger CONSOLE = Logger.getLogger("console");

    public SwinLanePalette(JTabbedPane libraryPane) {
        super(mxResources.get("Elements", "Swimlanes"), libraryPane);    
        
        File shapesFolder = new File("resources/shapes/bpmn/swimlane/");
        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
        
       Element pool = BPMNUtils.createPool();
        this.addSwimlane("Pool", 
                new ImageIcon("resources/shapes/bpmn/swimlane/pool.png"), 
                "shape=swimlane;connectable=0;horizontal=false", 
                580, 230, pool);
//        Element lane = BPMNUtils.createLane();
//         this.addSwimlane("Lane", 
//                new ImageIcon("resources/shapes/bpmn/swimlane/lane.png"), 
//                "shape=swimlane;connectable=0;horizontal=false", 
//                580, 230, pool);        
//        super(mxResources.get("Elements", "Swimlanes"), libraryPane);    
//        File shapesFolder = new File("resources/shapes/bpmn/swimlane/");
//        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
//        
//        if ((files == null) || (files.length < 1)) {
//            CONSOLE.info("no shape found. " + shapesFolder.getAbsolutePath());
//            return;
//        }
        
//        for (File f : files) {
//            try {
//                String nodeXml = mxUtils.readFile(f.getAbsolutePath());
//                if (f.getName().matches("pool.shape|lane.shape")){
//                    System.out.println("aqui");
//                System.out.println(f.getName());
//                    ImportStencilAction.addStencilShape(this, nodeXml, f.getParent() + File.separator);}
//            } catch (IOException ex) {
//                CONSOLE.fatal(ex);
//            }
//        }
    }
        

}
