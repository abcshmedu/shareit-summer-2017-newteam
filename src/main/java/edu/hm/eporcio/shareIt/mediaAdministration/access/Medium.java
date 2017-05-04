package edu.hm.eporcio.shareIt.mediaAdministration.access;

/**
 * A medium with a title.
 * @author Elias Porcio 
 * @version 03.05.2017
 */
public class Medium {
    private final String title;
    
    /**
     * Returns a new medium with merged information of this one and the given one.
     * The given mediums field values are preferred over this ones. Only if a field of the given medium is null or coontains an escape value, this mediums value is used instead.
     * @param newer The dominant medium to merge with.
     * @return A new merged medium. 
     */
    public Medium merge(Medium newer) {
        String title = getTitle();
        if (newer.getTitle() != null) {
            title = newer.getTitle();
        }
        return new Medium(title);
    }
    
    //CHECKSTYLE:OFF
    public Medium(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    //CHECKSTYLE:ON
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Medium [title=" + title + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Medium other = (Medium) obj;
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }
}
