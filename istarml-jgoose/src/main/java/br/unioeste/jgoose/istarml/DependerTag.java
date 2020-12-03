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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name="depender")
/*    */ public class DependerTag
/*    */ {
/*    */   @XmlAttribute(required=true)
/*    */   private String aref;
/*    */   @XmlAttribute(required=false)
/*    */   private String iref;
/*    */   @XmlAttribute(required=false)
/*    */   private String value;
/*    */   @XmlElement
/*    */   private GraphicTag graphic;
/*    */   
/*    */   public String getAref()
/*    */   {
/* 31 */     return this.aref;
/*    */   }
/*    */   
/*    */   public void setAref(String aref) {
/* 35 */     this.aref = aref;
/*    */   }
/*    */   
/*    */   public String getIref() {
/* 39 */     return this.iref;
/*    */   }
/*    */   
/*    */   public void setIref(String iref) {
/* 43 */     this.iref = iref;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(String value) {
/* 51 */     this.value = value;
/*    */   }
/*    */   
/*    */   public GraphicTag getGraphic() {
/* 55 */     return this.graphic;
/*    */   }
/*    */   
/*    */   public void setGraphic(GraphicTag graphic) {
/* 59 */     this.graphic = graphic;
/*    */   }
/*    */ }


