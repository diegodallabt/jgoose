/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name="point")
/*    */ class PointTag
/*    */ {
/*    */   @XmlAttribute
/*    */   private String xpos;
/*    */   @XmlAttribute
/*    */   private String ypos;
/*    */   
/*    */   public String getXpos()
/*    */   {
/* 22 */     return this.xpos;
/*    */   }
/*    */   
/*    */   public void setXpos(String xpos) {
/* 26 */     this.xpos = xpos;
/*    */   }
/*    */   
/*    */   public String getYpos() {
/* 30 */     return this.ypos;
/*    */   }
/*    */   
/*    */   public void setYpos(String ypos) {
/* 34 */     this.ypos = ypos;
/*    */   }
/*    */ }

