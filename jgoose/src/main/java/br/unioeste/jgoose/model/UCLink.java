/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.model;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class UCLink {
    public static final Integer ASSOCIATION = 1;
    public static final Integer GENERALIZATION = 2;
    public static final Integer INCLUDE = 3;
    public static final Integer EXTEND = 4;

    
    private String code;
    private String label;
    private Integer type;
    private UCElement from;
    private UCElement to;
    
    public UCLink(String code, String label, int type, UCElement from, UCElement to) {
        this.code = code;
        this.label = label;
        this.type = type;
        this.from = from;
        this.to = to;
    }   
    
    public UCLink() {
        this.code = null;
        this.label = null;
        this.type = null;
        this.from = null;
        this.to = null;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UCElement getFrom() {
        return from;
    }

    public void setFrom(UCElement from) {
        this.from = from;
    }

    public UCElement getTo() {
        return to;
    }

    public void setTo(UCElement to) {
        this.to = to;
    }

}
