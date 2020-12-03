/*     */ package br.unioeste.jgoose.istarml;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ @XmlRootElement(name="diagram")
/*     */ public class DiagramTag
/*     */ {
/*     */   @XmlAttribute
/*     */   @XmlID
/*     */   private String id;
/*     */   @XmlAttribute
/*     */   private String name;
/*     */   @XmlAttribute(name="author", required=false)
/*     */   private String author;
/*     */   @XmlElement
/*     */   private GraphicTag graphic;
/*     */   @XmlAnyElement(lax=true)
/*     */   private Set<ActorTag> actors;
/*     */   @XmlAnyElement(lax=true)
/*     */   private Set<IElementTag> intentionalElements;
/*     */   
/*     */   public DiagramTag()
/*     */   {
/*  48 */     this(null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DiagramTag(String id, String name)
/*     */   {
/*  61 */     if ((null == id) && (null == name))
/*     */     {
/*     */ 
/*  64 */       this.name = "";
/*     */     } else {
/*  66 */       this.id = id;
/*  67 */       this.name = name;
/*     */     }
/*     */     
/*  70 */     this.actors = new HashSet();
/*  71 */     this.intentionalElements = new HashSet();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  75 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  79 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  83 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  87 */     this.name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  95 */     return this.author;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/* 103 */     this.author = author;
/*     */   }
/*     */   
/*     */   public GraphicTag getGraphic() {
/* 107 */     return this.graphic;
/*     */   }
/*     */   
/*     */   public void setGraphic(GraphicTag graphic) {
/* 111 */     this.graphic = graphic;
/*     */   }
/*     */   
/*     */   public Set<ActorTag> getActors() {
/* 115 */     return this.actors;
/*     */   }
/*     */   
/*     */   public void setActors(Set<ActorTag> actors) {
/* 119 */     this.actors = actors;
/*     */   }
/*     */   
/*     */   public Set<IElementTag> getIntentionalElements() {
/* 123 */     return this.intentionalElements;
/*     */   }
/*     */   
/*     */   public void setIntentionalElements(Set<IElementTag> intentionalElements) {
/* 127 */     this.intentionalElements = intentionalElements;
/*     */   }
/*     */ }

