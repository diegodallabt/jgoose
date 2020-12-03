/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import br.unioeste.jgoose.istarml.decorators.DiagramDecorator;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IStarMLModel
/*    */ {
/* 15 */   private static final Logger LOG = Logger.getLogger(IStarMLModel.class.getName());
/*    */   private IStarMLTag root;
/*    */   
/*    */   public IStarMLModel() {
/* 19 */     this(null);
/*    */   }
/*    */   
/*    */   public IStarMLModel(String diagramName) {
/* 23 */     this.root = new IStarMLTag(diagramName);
/*    */   }
/*    */   
/*    */   public IStarMLTag getRoot() {
/* 27 */     return this.root;
/*    */   }
/*    */   
/*    */   public void setRoot(IStarMLTag root) {
/* 31 */     this.root = root;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Set<ActorTag> getAllActors()
/*    */   {
/* 43 */     Set<ActorTag> result = new HashSet();
/* 44 */     DiagramTag diagrams = this.root.getDiagrams();
/*    */     
/* 46 */     return diagrams.getActors();
/*    */   }
/*    */   
/*    */   public Set<ActorTag> getAllIElements()
/*    */   {
/* 51 */     DiagramTag diagrams = this.root.getDiagrams();
/*    */     
/* 53 */     return diagrams.getActors();
/*    */   }
/*    */   
/*    */   public Set<ActorTag> getAllAgents() {
/* 57 */     DiagramTag diagrams = this.root.getDiagrams();
/*    */     
/* 59 */     return ((DiagramDecorator)diagrams).getAllAgents();
/*    */   }
/*    */ }

