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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name="istarml")
/*    */ public class IStarMLTag
/*    */ {
/*    */   @XmlAttribute
/*    */   public static final String VERSION = "1.0";
/*    */   @XmlElement
/*    */   private DiagramTag diagrams;
/*    */   
/*    */   public IStarMLTag()
/*    */   {
/* 33 */     this("");
/*    */   }
/*    */   
/*    */   public IStarMLTag(String diagramName) {
/* 37 */     this(new DiagramTag(null, diagramName));
/*    */   }
/*    */   
/*    */   public IStarMLTag(DiagramTag diagram) {
/* 41 */     this.diagrams = diagram;
/*    */   }
/*    */   
/*    */   public DiagramTag getDiagrams() {
/* 45 */     return this.diagrams;
/*    */   }
/*    */   
/*    */   public void setDiagrams(DiagramTag diagrams) {
/* 49 */     this.diagrams = diagrams;
/*    */   }
/*    */ }

