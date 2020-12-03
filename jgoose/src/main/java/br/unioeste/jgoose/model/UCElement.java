/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.model;

import java.util.ArrayList;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class UCElement implements Cloneable{
    public static final Integer ACTOR = 1;
    public static final Integer USECASE = 2;
    public static final Integer DESCRIPTION = 3;
    public static final Integer LINK = 4;
    
    private String code;
    private String label;
    private Integer type;
    private String parent; //father's element
    private ArrayList<UCLink> linksFrom; //links From Element
    private ArrayList<UCLink> linksTo; //links to Element
    
    public UCElement() {        
        this.linksFrom = new ArrayList<>();
        this.linksTo = new ArrayList<>();
    }
    
    public UCElement(String code, String label, Integer type, String parent, ArrayList<UCLink> linksTo, ArrayList<UCLink> linksFrom) {
        this.code = code;
        this.label = label;
        this.type = type;
        this.parent = parent;
        this.linksTo = linksTo;
        this.linksFrom = linksFrom;
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public ArrayList<UCLink> getLinksFrom() {
        return linksFrom;
    }

    public void setLinksFrom(ArrayList<UCLink> linksFrom) {
        this.linksFrom = linksFrom;
    }

    public ArrayList<UCLink> getLinksTo() {
        return linksTo;
    }

    public void setLinksTo(ArrayList<UCLink> linksTo) {
        this.linksTo = linksTo;
    }

    public void addLinkTo(UCLink ucLink){
        linksTo.add(ucLink);
    }
    
    public void addLinkFrom(UCLink ucLink){
        linksFrom.add(ucLink);
    }
    @Override
    public String toString() {
        return "UCElement{" + "code=" + code + ", label=" + label + ", type=" + type + ", parent=" + parent + ", linksFrom=" + linksFrom + ", linksTo=" + linksTo + '}';
    }
    
}
