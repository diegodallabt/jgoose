/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActorFactory
/*    */ {
/* 15 */   private static Map<String, ActorTag> references = new HashMap();
/*    */   
/*    */   public static ActorTag createActor() {
/* 18 */     ActorTag actor = new ActorTag();
/* 19 */     return actor;
/*    */   }
/*    */   
/*    */   public static ActorTag createAgent() {
/* 23 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */   
/*    */   public static ActorTag createRole() {
/* 27 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */   
/*    */   public static ActorTag createPosition() {
/* 31 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getReference(ActorTag actor)
/*    */   {
/* 45 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }

