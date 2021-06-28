package br.unioeste.jgoose.UseCases;

import java.util.ArrayList;

/**
 *
 * @author Diego Peliser
 * @author Victor Augusto Pozzan
 */
public class UseCase extends NFR {

    private final long id;
    private static long idCounter = 0;
    private String type;
    private String codDecomposedElement;
    private String nameDecomposedElement;
    private ArrayList<Step> steps;
    private Actor primaryActor;


    public UseCase(String cod, String name, String tipo, String codElementoDecomposto, String nameElementoDecomposto, Actor primaryActor , Boolean mapping) {
        super(cod, name);
        if (!mapping) {
            this.id = setId();
        }else{
            this.id = idCounter;
        }        
        this.type = tipo;
        this.codDecomposedElement = codElementoDecomposto;
        this.nameDecomposedElement = nameDecomposedElement;
        this.steps = new ArrayList<>();
        this.primaryActor = primaryActor;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Returns the codDecomposedElement.
     */
    public String getCodDecomposedElement() {
        return codDecomposedElement;
    }

    /**
     * @return Returns the codDecomposedElement.
     */
    public String setCodDecomposedElement(String codDecomposedElement) {
        return this.codDecomposedElement = codDecomposedElement;
    }

    /**
     * @return Returns the nomeDecomposedElement.
     */
    public String getNameDecomposedElement() {
        return nameDecomposedElement;
    }

    /**
     * @return Returns the nomeDecomposedElement.
     */
    public String setNameDecomposedElement(String nameDecomposedElement) {
        return this.nameDecomposedElement = nameDecomposedElement;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setStep(Step step) {
        this.steps.add(step);
    }

    private Long setId() {
        return idCounter++;
    }

    public long getId() {
        return id;
    }
    
    public Actor getPrimaryActor(){
        return primaryActor;
    }
}
