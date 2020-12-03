/*    */ package br.unioeste.jgoose.istarml.api;
/*    */ 
/*    */ import br.unioeste.jgoose.istarml.ActorTag;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilterElementsByTypeImpl
/*    */   implements FilterElements<ActorTag>
/*    */ {
/* 13 */   public static final FilterElementsByTypeImpl FILTER_AGENTS = new FilterElementsByTypeImpl("agent");
/* 14 */   public static final FilterElementsByTypeImpl FILTER_POSITIONS = new FilterElementsByTypeImpl("position");
/* 15 */   public static final FilterElementsByTypeImpl FILTER_ROLES = new FilterElementsByTypeImpl("role");
/*    */   private String type;
/*    */   
/*    */   public FilterElementsByTypeImpl(String type) {
/* 19 */     this.type = type;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Set<ActorTag> filter(Set<ActorTag> actors)
/*    */   {
/* 30 */     Set<ActorTag> result = new HashSet();
/*    */     
/* 32 */     for (ActorTag actorTag : actors) {
/* 33 */       if (!match(actorTag)) {}
/*    */     }
/*    */     
/*    */ 
/* 37 */     return result;
/*    */   }
/*    */   
/*    */   public boolean match(ActorTag candidate)
/*    */   {
/* 42 */     return candidate.getType().equalsIgnoreCase(this.type);
/*    */   }
/*    */ }

