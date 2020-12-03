/*    */ package br.unioeste.jgoose.istarml.api.model.adapter;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorAdapter
/*    */   extends XmlAdapter<String, Color>
/*    */ {
/*    */   public Color unmarshal(String v)
/*    */     throws Exception
/*    */   {
/* 14 */     return Color.decode(v);
/*    */   }
/*    */   
/*    */   public String marshal(Color v) throws Exception
/*    */   {
/* 19 */     return "#" + Integer.toHexString(v.getRGB());
/*    */   }
/*    */ }


