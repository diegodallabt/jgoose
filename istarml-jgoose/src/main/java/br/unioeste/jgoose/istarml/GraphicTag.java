/*     */ package br.unioeste.jgoose.istarml;
/*     */ 
/*     */ import br.unioeste.jgoose.istarml.api.model.adapter.ColorAdapter;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*     */ @XmlRootElement(name="graphic")
/*     */ public class GraphicTag
/*     */ {
/*     */   @XmlAttribute
/*     */   private String content;
/*     */   @XmlAttribute
/*     */   private String xpos;
/*     */   @XmlAttribute
/*     */   private String ypos;
/*     */   @XmlAttribute
/*     */   private String width;
/*     */   @XmlAttribute
/*     */   private String height;
/*     */   @XmlAttribute
/*     */   private String unit;
/*     */   @XmlJavaTypeAdapter(ColorAdapter.class)
/*     */   @XmlAttribute
/*     */   private Color bgcolor;
/*     */   @XmlJavaTypeAdapter(ColorAdapter.class)
/*     */   @XmlAttribute
/*     */   private Color fontcolor;
/*     */   @XmlAttribute
/*     */   private String fontfamily;
/*     */   @XmlAttribute
/*     */   private String fontsize;
/*     */   @XmlAnyElement
/*     */   private List<PointTag> points;
/*     */   @XmlAttribute
/*     */   private String shape;
/*     */   
/*     */   public String getContent()
/*     */   {
/*  55 */     return this.content;
/*     */   }
/*     */   
/*     */   public void setContent(String content) {
/*  59 */     this.content = content;
/*     */   }
/*     */   
/*     */   public String getXpos() {
/*  63 */     return this.xpos;
/*     */   }
/*     */   
/*     */   public void setXpos(String xpos) {
/*  67 */     this.xpos = xpos;
/*     */   }
/*     */   
/*     */   public String getYpos() {
/*  71 */     return this.ypos;
/*     */   }
/*     */   
/*     */   public void setYpos(String ypos) {
/*  75 */     this.ypos = ypos;
/*     */   }
/*     */   
/*     */   public String getWidth() {
/*  79 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(String width) {
/*  83 */     this.width = width;
/*     */   }
/*     */   
/*     */   public String getHeight() {
/*  87 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(String height) {
/*  91 */     this.height = height;
/*     */   }
/*     */   
/*     */   public String getUnit() {
/*  95 */     return this.unit;
/*     */   }
/*     */   
/*     */   public void setUnit(String unit) {
/*  99 */     this.unit = unit;
/*     */   }
/*     */   
/*     */   public Color getBgcolor() {
/* 103 */     return this.bgcolor;
/*     */   }
/*     */   
/*     */   public void setBgcolor(Color bgcolor) {
/* 107 */     this.bgcolor = bgcolor;
/*     */   }
/*     */   
/*     */   public Color getFontcolor() {
/* 111 */     return this.fontcolor;
/*     */   }
/*     */   
/*     */   public void setFontcolor(Color fontcolor) {
/* 115 */     this.fontcolor = fontcolor;
/*     */   }
/*     */   
/*     */   public String getFontfamily() {
/* 119 */     return this.fontfamily;
/*     */   }
/*     */   
/*     */   public void setFontfamily(String fontfamily) {
/* 123 */     this.fontfamily = fontfamily;
/*     */   }
/*     */   
/*     */   public String getFontsize() {
/* 127 */     return this.fontsize;
/*     */   }
/*     */   
/*     */   public void setFontsize(String fontsize) {
/* 131 */     this.fontsize = fontsize;
/*     */   }
/*     */   
/*     */   public List<PointTag> getPoints() {
/* 135 */     return this.points;
/*     */   }
/*     */   
/*     */   public void setPoints(List<PointTag> points) {
/* 139 */     this.points = points;
/*     */   }
/*     */   
/*     */   public String getShape() {
/* 143 */     return this.shape;
/*     */   }
/*     */   
/*     */   public void setShape(String shape) {
/* 147 */     this.shape = shape;
/*     */   }
/*     */ }

