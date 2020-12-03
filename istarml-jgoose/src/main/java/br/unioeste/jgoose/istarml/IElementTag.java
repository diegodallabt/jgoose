/*     */ package br.unioeste.jgoose.istarml;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.namespace.QName;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name="ielement")
/*     */ public class IElementTag
/*     */ {
/*     */   @XmlAttribute
/*     */   @XmlID
/*     */   private String id;
/*     */   @XmlAttribute
/*     */   private String name;
/*     */   @XmlAnyAttribute
/*  42 */   protected Map<QName, String> extraAttrs = new HashMap();
/*     */   @XmlAttribute
/*     */   private String type;
/*     */   @XmlAttribute
/*     */   private String state;
/*     */   @XmlAttribute
/*     */   private String iref;
/*     */   @XmlElement(required=false)
/*     */   private GraphicTag graphicNode;
/*     */   @XmlAnyElement(lax=true)
/*     */   private Set<IElementLinkTag> links;
/*     */   @XmlElement
/*     */   private DependencyTag dependency;
/*     */   
/*     */   public IElementTag()
/*     */   {
/*  58 */     this.links = new HashSet();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  62 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  66 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  70 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  74 */     this.name = name;
/*     */   }
/*     */   
/*     */   public Map<QName, String> getExtraAttrs() {
/*  78 */     return this.extraAttrs;
/*     */   }
/*     */   
/*     */   public void setExtraAttrs(Map<QName, String> extraAttrs) {
/*  82 */     this.extraAttrs = extraAttrs;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  86 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  90 */     this.type = type;
/*     */   }
/*     */   
/*     */   public String getState() {
/*  94 */     return this.state;
/*     */   }
/*     */   
/*     */   public void setState(String state) {
/*  98 */     this.state = state;
/*     */   }
/*     */   
/*     */   public String getIref() {
/* 102 */     return this.iref;
/*     */   }
/*     */   
/*     */   public void setIref(String iref) {
/* 106 */     this.iref = iref;
/*     */   }
/*     */   
/*     */   public GraphicTag getGraphicNode() {
/* 110 */     return this.graphicNode;
/*     */   }
/*     */   
/*     */   public void setGraphicNode(GraphicTag graphicNode) {
/* 114 */     this.graphicNode = graphicNode;
/*     */   }
/*     */   
/*     */   public Set<IElementLinkTag> getLinks() {
/* 118 */     return this.links;
/*     */   }
/*     */   
/*     */   public void setLinks(Set<IElementLinkTag> links) {
/* 122 */     this.links = links;
/*     */   }
/*     */   
/*     */   public DependencyTag getDependency() {
/* 126 */     return this.dependency;
/*     */   }
/*     */   
/*     */   public void setDependency(DependencyTag dependency) {
/* 130 */     this.dependency = dependency;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAttributeValue(String attributeName)
/*     */   {
/* 141 */     if ((attributeName.equals("id")) || (attributeName.equals("name")) || 
/* 142 */       (attributeName.equals("author"))) {
/* 143 */       throw new IllegalArgumentException("Can not be 'id', 'name', 'author' or other class attribute name.");
/*     */     }
/* 145 */     return (String)this.extraAttrs.get(new QName(attributeName));
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, String value) {
/* 149 */     this.extraAttrs.put(new QName(name), value);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 154 */     int hash = 5;
/* 155 */     hash = 29 * hash + Objects.hashCode(this.id);
/* 156 */     hash = 29 * hash + Objects.hashCode(this.name);
/* 157 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 162 */     if (obj == null) {
/* 163 */       return false;
/*     */     }
/* 165 */     if (getClass() != obj.getClass()) {
/* 166 */       return false;
/*     */     }
/* 168 */     IElementTag other = (IElementTag)obj;
/* 169 */     if (!Objects.equals(this.id, other.id)) {
/* 170 */       return false;
/*     */     }
/* 172 */     if (!Objects.equals(this.name, other.name)) {
/* 173 */       return false;
/*     */     }
/* 175 */     return true;
/*     */   }
/*     */ }

