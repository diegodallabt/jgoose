/*     */ package br.unioeste.jgoose.istarml;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
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
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ @XmlRootElement(name="actor")
/*     */ public class ActorTag
/*     */ {
/*     */   @XmlAttribute
/*     */   @XmlID
/*     */   private String id;
/*     */   @XmlAttribute
/*     */   private String name;
/*     */   @XmlAnyAttribute
/*     */   private Map<QName, String> extraAttrs;
/*     */   @XmlAttribute
/*     */   private String type;
/*     */   @XmlAttribute
/*     */   private String aref;
/*     */   @XmlElement(required=false)
/*     */   private GraphicTag graphic;
/*     */   @XmlAnyElement(lax=true)
/*     */   private Set<ActorLinkTag> links;
/*     */   @XmlElement
/*     */   private BoundaryTag boundary;
/*     */   
/*     */   public ActorTag()
/*     */   {
/*  52 */     this.extraAttrs = new HashMap();
/*  53 */     this.links = new HashSet();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  57 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  61 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  69 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  73 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  77 */     this.type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAref(String aref)
/*     */   {
/*  86 */     this.aref = aref;
/*     */   }
/*     */   
/*     */   public BoundaryTag getBoundary() {
/*  90 */     if (this.boundary == null) {
/*  91 */       this.boundary = new BoundaryTag();
/*     */     }
/*  93 */     return this.boundary;
/*     */   }
/*     */   
/*     */   public void setBoundary(BoundaryTag boundary) {
/*  97 */     this.boundary = boundary;
/*     */   }
/*     */   
/*     */   public GraphicTag getGraphic() {
/* 101 */     return this.graphic;
/*     */   }
/*     */   
/*     */   public void setGraphic(GraphicTag graphicNode) {
/* 105 */     this.graphic = graphicNode;
/*     */   }
/*     */   
/*     */   public Set<ActorLinkTag> getLinks() {
/* 109 */     return this.links;
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
/* 120 */     if ((attributeName.equals("id")) || (attributeName.equals("name")) || 
/* 121 */       (attributeName.equals("author"))) {
/* 122 */       throw new IllegalArgumentException("Can not be 'id', 'name', 'author' or other class attribute name.");
/*     */     }
/*     */     
/* 125 */     return (String)this.extraAttrs.get(new QName(attributeName));
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, String value) {
/* 129 */     this.extraAttrs.put(new QName(name), value);
/*     */   }
/*     */   
/*     */   public Map<QName, String> getExtraAttrs() {
/* 133 */     return this.extraAttrs;
/*     */   }
/*     */   
/*     */   public String getAref() {
/* 137 */     return this.aref;
/*     */   }
/*     */   
/*     */   public void setLinks(Set<ActorLinkTag> links) {
/* 141 */     this.links = links;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 146 */     StringBuilder sb = new StringBuilder("Actor{");
/*     */     
/* 148 */     if (StringUtils.isNotBlank(this.id)) {
/* 149 */       sb.append("[id=").append(this.id).append(']');
/*     */     }
/*     */     
/* 152 */     if (StringUtils.isNotBlank(this.name)) {
/* 153 */       sb.append("[name=").append(this.name).append(']');
/*     */     }
/*     */     
/* 156 */     if (StringUtils.isNotBlank(this.type)) {
/* 157 */       sb.append("[type=").append(this.type).append(']');
/*     */     }
/*     */     
/* 160 */     if (StringUtils.isNotBlank(this.aref)) {
/* 161 */       sb.append("[aref=").append(this.aref).append(']');
/*     */     }
/* 163 */     sb.append("[links=").append(this.links.size()).append(']');
/*     */     
/* 165 */     return "}";
/*     */   }
/*     */ }


