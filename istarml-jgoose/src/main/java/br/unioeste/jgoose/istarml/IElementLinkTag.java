/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAnyElement;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name="ielementlink")
/*    */ public class IElementLinkTag
/*    */ {
/*    */   @XmlAttribute
/*    */   private String type;
/*    */   @XmlAttribute
/*    */   private String value;
/*    */   @XmlElement(required=false)
/*    */   private GraphicTag graphicPath;
/*    */   @XmlAnyElement(lax=true)
/*    */   private Set<IElementTag> elements;
/*    */   
/*    */   public IElementLinkTag()
/*    */   {
/* 41 */     this.elements = new HashSet();
/*    */   }
/*    */   
/*    */   public String getType() {
/* 45 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 49 */     this.type = type;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 53 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(String value) {
/* 57 */     this.value = value;
/*    */   }
/*    */   
/*    */   public GraphicTag getGraphicPath() {
/* 61 */     return this.graphicPath;
/*    */   }
/*    */   
/*    */   public void setGraphicPath(GraphicTag graphicPath) {
/* 65 */     this.graphicPath = graphicPath;
/*    */   }
/*    */   
/*    */   public Set<IElementTag> getElements() {
/* 69 */     return this.elements;
/*    */   }
/*    */   
/*    */   public void setElements(Set<IElementTag> elements) {
/* 73 */     this.elements = elements;
/*    */   }
/*    */ }
