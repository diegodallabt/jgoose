/*    */ package br.unioeste.jgoose.istarml.decorators;
/*    */ 
/*    */ import br.unioeste.jgoose.istarml.ActorTag;
/*    */ import br.unioeste.jgoose.istarml.DiagramTag;
/*    */ import br.unioeste.jgoose.istarml.api.FilterElementsByTypeImpl;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiagramDecorator
/*    */   extends DiagramTag
/*    */ {
/*    */   public DiagramDecorator() {}
/*    */   
/*    */   public DiagramDecorator(String id, String name)
/*    */   {
/* 20 */     super(id, name);
/*    */   }
/*    */   
/*    */   public Set<ActorTag> getAllAgents() {
/* 24 */     return FilterElementsByTypeImpl.FILTER_AGENTS.filter(getActors());
/*    */   }
/*    */   
/*    */   public Set<ActorTag> getAllPositions() {
/* 28 */     return FilterElementsByTypeImpl.FILTER_POSITIONS.filter(getActors());
/*    */   }
/*    */   
/*    */   public Set<ActorTag> getAllRoles() {
/* 32 */     return FilterElementsByTypeImpl.FILTER_ROLES.filter(getActors());
/*    */   }
/*    */ }


