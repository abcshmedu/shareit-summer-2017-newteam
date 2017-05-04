package edu.hm.eporcio.shareIt.mediaAdministration.access;

/**
 * A specific copy of a medium.
 * @author Elias Porcio, elias.porcio@hm.edu
 * @version May 3, 2017
 */
public class Copy {
    private final Medium medium;
    private final String owner;
    
    //CHECKSTYLE:OFF
    public Medium getMedium() {
        return medium;
    }

    public String getOwner() {
        return owner;
    }
    //CHECKSTYLE:ON

    /**
     * Default constructor. Not a copy constructor.
     * @param medium The medium of which this object is a copy.
     * @param owner The owner of the copy.
     */
    public Copy(Medium medium, String owner) {
        super();
        this.medium = medium;
        this.owner = owner;
    }
}
