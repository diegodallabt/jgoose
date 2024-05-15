package br.unioeste.jgoose.model;

import java.util.ArrayList;

/**
 *
 * @author Diego Peliser
 */
/**
 * Utilizada para especificar o Actor i* (IStarActorElement)
 *
 * @param cod //código (Element_x)
 * @param name //nome do Elemento ("Ator 1")
 * @param childrens //filhos associados ao ATOR: SR ou ISA (códigos)
 * @param links //ligações (códigos)
 */
    public class IStarActorElement {

    private String cod; //código (Element_x)
    private String name; //nome do Elemento ("Ator 1")
    private ArrayList<String> childrens; //filhos associados ao ATOR: SR ou ISA (códigos)
    private ArrayList<IStarElement> children; // dentro do boundary
    private ArrayList<IStarElement> dependencies; // dependência derivada
    private ArrayList<IStarElement> task; // task derivada
    private boolean system;
    private boolean secondary;
    private ArrayList<String> links; //ligações (códigos)
    
    public IStarActorElement() {
        this.task = new ArrayList();
        this.children = new ArrayList();
        this.system = false;
        this.dependencies = new ArrayList();
        this.childrens = new ArrayList();
        this.links = new ArrayList();
    }
    
    public boolean isSecondary() {
        return secondary;
    }

    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
    }

    public ArrayList<IStarElement> getChildren() {
        return children;
    }
    
    public ArrayList<IStarElement> getTask() {
        return task;
    }

    public void setTask(IStarElement task) {
        this.task.add(task);
    }
    /**
     * @param children the element to set.
     */
    public void addChildren(IStarElement children) {
        this.children.add(children);
    }

    public boolean getSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }
    
    /**
     * @return returns the dependencies.
     */
    public ArrayList<IStarElement> getDependencies() {
        return dependencies;
    }
    
    /**
     * @param dependency the element to set.
     */
    public void setDependency(IStarElement dependency) {
        this.dependencies.add(dependency);
    }

    /**
     * @return returns the childrens.
     */
    public ArrayList getChildrens() {
        return childrens;
    }

    public String getChildren(int i) {
        return String.valueOf(childrens.get(i));
    }

    /**
     * @param children the children to set.
     */
    public void setChildren(String children) {
        this.childrens.add(children);
    }

    /**
     * @return returns the cod.
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set.
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @param children the sequence to search for
     * @return Returns if childrens exists
     */
    public boolean containsChildren(String children) {
        for (String filho : childrens) {
            if (filho.equals(children)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param link the sequence to search for
     * @return Returns if link exists.
     */
    public boolean containsLink(ArrayList<String> ligacoes) {
        for (String ligacao : ligacoes) {
            for (String link : links) {
                if (ligacao.equals(link)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return returns the links.
     */
    public ArrayList<String> getLinks() {
        return links;
    }

    /**
     * @param link The link to set.
     */
    public void setLink(String link) {
        this.links.add(link);
    }

    /**
     * @param Seta os links, informando o tipo de ligação, se ela é "FROM" ou
     * "TO" 0 - dependência, 1 - Decomposição de Tarefas, 2 - Meio-FIM, 3 - ISA,
     * -1 - Outras
     */
    public void setLink(int tipo, String link) {
        this.links.add(tipo + " " + link);
    }

    /**
     * @return returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        
        this.name = name;
    }
}
