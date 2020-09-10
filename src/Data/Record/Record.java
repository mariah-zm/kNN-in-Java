package Data.Record;

import java.util.List;

/**
 * This is the base class for a record of a dataset.
 * A record will have its attributes and the target class.
 */
public class Record {
    protected String[] attributes;
    protected String targetClass;

    public Record(){
    }

    /**
     * Constructor
     * @param attributes the list of attributes
     */
    public Record(List<String> attributes){
        int numOfAttributes = attributes.size() -1;

        // Initialising and filling the attributes for record
        this.attributes = new String[numOfAttributes];
        for(int i=0; i<numOfAttributes; i++){
            this.attributes[i] = attributes.get(i);
        }

        // Setting the target class for this record
        this.targetClass = attributes.get(numOfAttributes);
    }

    /**
     * Getter
     * @param i the index of the needed feature value
     * @return the value of the attribute
     */
    public String getAttribute(int i){
        return this.attributes[i];
    }

    /**
     * Setter - to be used to normalise
     * @param i
     * @param value
     */
    public void setAttribute(int i, String value){
        this.attributes[i] = value;
    }

    /**
     * Getter
     * @return class of this object
     */
    public String getTargetClass(){
        return this.targetClass;
    }
}
