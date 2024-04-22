/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.model;

import br.unioeste.jgoose.UseCases.UseCase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alysson Girotto
 */
public class UCActor extends UCElement{
    
    private String code;
    private String bpmnElementoCode;
    private String name;
    private List<UCUseCase> useCases;
    private List<UCUseCase> useCasesSystem;
    private UCActor father;
    private boolean system;
    private boolean secondary;
    private List<UCActor> children;

    public UCActor() {
        system = false;
        useCases = new ArrayList<>();
        father = null;
        children = new ArrayList<>();
        useCasesSystem = new ArrayList<>();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getBpmnElementoCode() {
        return bpmnElementoCode;
    }

    public void setBpmnElementoCode(String bpmnElementoCode) {
        this.bpmnElementoCode = bpmnElementoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getSecondary() {
        return secondary;
    }

    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
    }
    
    public boolean getSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }
    
    public List<UCUseCase> getUseCasesSystem() {
        return useCasesSystem;
    }

    public void setUseCasesSystem(List<UCUseCase> useCasesSystem) {
        this.useCasesSystem = useCasesSystem;
    }

    public void adduseCasesSystem(UCUseCase useCasesSystem){
        this.useCasesSystem.add(useCasesSystem);
    }

    public List<UCUseCase> getUseCases() {
        return useCases;
    }

    public void setUseCases(List<UCUseCase> useCases) {
        this.useCases = useCases;
    }

    public void addUseCase(UCUseCase useCase){
        this.useCases.add(useCase);
    }
    
    public UCActor getFather() {
        return father;
    }

    public void setFather(UCActor father) {
        this.father = father;
    }

    public List<UCActor> getChildren() {
        return children;
    }

    public void setChildren(List<UCActor> children) {
        this.children = children;
    }

    public void addChild(UCActor child){
        children.add(child);
    }
    
    @Override
    public String toString() {
        return "\nUCActor{" + 
                    "\tcod=" + code + 
                    "\tbpmn cod=" + bpmnElementoCode + 
                    "\tname=" + name + 
                    "\t\tuseCases=" + useCases +
                    "\t\tchildren=" + children +
                '}';
    }
    
    
    
}
