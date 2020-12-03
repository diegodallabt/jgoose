/*    */ package br.unioeste.jgoose.istarml;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
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
/*    */ @XmlRootElement(name="dependency")
/*    */ public class DependencyTag
/*    */ {
/*    */   @XmlElement(required=true)
/*    */   private Set<DependerTag> depender;
/*    */   @XmlElement(required=false)
/*    */   private Set<DependeeTag> dependee;
/*    */   
/*    */   public DependencyTag()
/*    */   {
/* 28 */     this.depender = new HashSet();
/* 29 */     this.dependee = new HashSet();
/*    */   }
/*    */   
/*    */   public Set<DependerTag> getDepender() {
/* 33 */     return this.depender;
/*    */   }
/*    */   
/*    */   public void setDepender(Set<DependerTag> depender) {
/* 37 */     this.depender = depender;
/*    */   }
/*    */   
/*    */   public Set<DependeeTag> getDependee() {
/* 41 */     return this.dependee;
/*    */   }
/*    */   
/*    */   public void setDependee(Set<DependeeTag> dependee) {
/* 45 */     this.dependee = dependee;
/*    */   }
/*    */ }

