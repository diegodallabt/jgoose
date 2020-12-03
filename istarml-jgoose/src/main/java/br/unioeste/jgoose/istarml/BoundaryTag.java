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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name="boundary")
/*    */ public class BoundaryTag
/*    */ {
/*    */   @XmlAttribute
/*    */   private String type;
/*    */   @XmlElement
/*    */   private GraphicTag graphPath;
/*    */   @XmlAnyElement(lax=true)
/*    */   private Set<IElementTag> elements;
/*    */   @XmlAnyElement(lax=true)
/*    */   private Set<ActorTag> actors;
/*    */   
/*    */   public BoundaryTag()
/*    */   {
/* 34 */     this.elements = new HashSet();
/* 35 */     this.actors = new HashSet();
/*    */   }
/*    */   
/*    */   public String getType() {
/* 39 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 43 */     this.type = type;
/*    */   }
/*    */   
/*    */   public GraphicTag getGraphPath() {
/* 47 */     return this.graphPath;
/*    */   }
/*    */   
/*    */   public void setGraphPath(GraphicTag graphPath) {
/* 51 */     this.graphPath = graphPath;
/*    */   }
/*    */   
/*    */   public Set<IElementTag> getElements() {
/* 55 */     return this.elements;
/*    */   }
/*    */   
/*    */   public void setElements(Set<IElementTag> elements) {
/* 59 */     this.elements = elements;
/*    */   }
/*    */   
/*    */   public Set<ActorTag> getActors() {
/* 63 */     return this.actors;
/*    */   }
/*    */   
/*    */   public void setActors(Set<ActorTag> actors) {
/* 67 */     this.actors = actors;
/*    */   }
/*    */ }

