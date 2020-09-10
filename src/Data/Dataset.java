package Data;/*
 * This class will contain basic functionalities to load the data sets and split them
 * into test data and training data.
 */

import Data.Record.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dataset {

    // Holds the name of the features
    private List<String> attributes = new ArrayList<>();
    // Holds the data type of the features
    private List<AttributeType> attributeTypes;
    // Holds the input values of a sample as well as the target class
    private Record[] data;

    private final int numOfSamples;
    private final int numOfAttributes;
    private final int numOfClasses;

    public Dataset(List<List<String>> data, int classes, List<AttributeType> attTypes) {
        this.numOfSamples = data.size()-1;
        this.numOfClasses = classes;
        this.numOfAttributes = attTypes.size();
        this.attributeTypes = attTypes;

        loadData(data);
    }

    /**
     * This method
     * @param data the data as a list of lists
     */
    private void loadData(List<List<String>> data) {
        // Extracting the names of the features
        this.attributes = data.get(0);

        // Data is shuffled to ensure that samples aren't grouped according to class
        data.remove(0);
        Collections.shuffle(data);

        this.data = new Record[numOfSamples];
        // Placing the data in an Array of Records
        for(int i=0; i < numOfSamples; i++){
            this.data[i] = new Record(data.get(i));
        }
    }

    public int getNumOfClasses(){
        return this.numOfClasses;
    }

    public int getNumOfSamples(){
        return this.numOfSamples;
    }

    public int getNumOfAttributes(){ return this.numOfAttributes;}

    public Record[] getData(){return this.data; }

    public List<AttributeType> getAttributeTypes(){
        return this.attributeTypes;
    }

}
