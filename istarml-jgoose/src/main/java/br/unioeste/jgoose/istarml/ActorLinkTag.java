/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name="actorLink")
/*    */ public class ActorLinkTag
/*    */ {
/*    */   @XmlAttribute(required=true)
/*    */   private String type;
/*    */   @XmlAttribute(required=true)
/*    */   private String aref;
/*    */   @XmlElement(required=false)
/*    */   private GraphicTag graphicPath;
/*    */   
/*    */   public String getType()
/*    */   {
/* 65 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 69 */     this.type = type;
/*    */   }
/*    */   
/*    */   public String getAref() {
/* 73 */     return this.aref;
/*    */   }
/*    */   
/*    */   public void setAref(String aref) {
/* 77 */     this.aref = aref;
/*    */   }
/*    */   
/*    */   public GraphicTag getGraphicPath() {
/* 81 */     return this.graphicPath;
/*    */   }
/*    */   
/*    */   public void setGraphicPath(GraphicTag graphicPath) {
/* 85 */     this.graphicPath = graphicPath;
/*    */   }
/*    */ }

