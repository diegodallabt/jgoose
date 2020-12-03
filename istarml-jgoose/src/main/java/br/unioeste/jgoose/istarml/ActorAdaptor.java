/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActorAdaptor
/*    */   implements Comparable<ActorAdaptor>
/*    */ {
/*    */   private DiagramTag parent;
/*    */   private ActorTag actorTag;
/*    */   
/*    */   public ActorAdaptor(DiagramTag parent)
/*    */   {
/* 15 */     this(parent, null);
/*    */   }
/*    */   
/*    */   public ActorAdaptor(DiagramTag parent, ActorTag actorTag) {
/* 19 */     this.parent = parent;
/* 20 */     this.actorTag = actorTag;
/*    */     
/* 22 */     if (this.actorTag == null) {
/* 23 */       this.actorTag = new ActorTag();
/*    */     }
/*    */   }
/*    */   
/*    */   public int compareTo(ActorAdaptor actor)
/*    */   {
/* 29 */     int result = 0;
/* 30 */     String id = getId();
/* 31 */     String _id = actor.getId();
/*    */     
/*    */ 
/* 34 */     int resultIds = compare(id, _id);
/* 35 */     if (resultIds == 0) {
/* 36 */       return 0;
/*    */     }
/*    */     
/* 39 */     int resultNames = compare(getName(), actor.getName());
/*    */     
/* 41 */     return result;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int compare(String a, String b)
/*    */   {
/* 53 */     if ((StringUtils.isBlank(a)) && (StringUtils.isBlank(b))) {
/* 54 */       return -1;
/*    */     }
/*    */     
/* 57 */     if (a.equalsIgnoreCase(b)) {
/* 58 */       return 0;
/*    */     }
/*    */     
/* 61 */     if (StringUtils.isBlank(a)) {
/* 62 */       return -2;
/*    */     }
/*    */     
/* 65 */     if (StringUtils.isBlank(b)) {
/* 66 */       return -3;
/*    */     }
/*    */     
/* 69 */     return -4;
/*    */   }
/*    */   
/*    */   private String getId() {
/* 73 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */   
/*    */   private String getName() {
/* 77 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }

