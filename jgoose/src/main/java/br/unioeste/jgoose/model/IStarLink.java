package br.unioeste.jgoose.model;

/**
 *
 * @author Diego Peliser
 */
/**
 * Utilizada para especificar o Elementos i* (IStarContributionLink,
 * IStarCoversLink, IStarDecompositionLink, IStarDependencyLink, IStarINSLink,
 * IStarISALink, IStarIsPartOfLink, IStarMeansEndsLink, IStarOccupies,
 * IStarPlaysLink)
 *
 * @param cod //código (Element_x)
 * @param name //nome do Elemento ("Link 1")
 * @param from //de qual (origem) elemento essa ligação é feita (códigos)
 * @param to //para qual (destino) elemento essa ligação é feita (códigos)
 */
public class IStarLink {
    public static final Integer DEPENDENCY = 1;
    public static final Integer AND = 2;
    public static final Integer OR = 3;
    
    private String cod; //código (Element_x)
    private String name; //nome do Elemento ("Link 1")
    private String from; //de (códigos)
    private String to; //para (códigos)
    private Integer type; 

    public IStarLink() {
        super();
        this.type = 1;
        this.cod = null;
        this.from = null;
        this.name = null;
        this.to = null;
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return Returns the cod.
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod The cod to set.
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @return Returns the from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from The from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the to.
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to The to to set.
     */
    public void setTo(String to) {
        this.to = to;
    }
}
