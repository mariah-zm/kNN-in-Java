package Data.Record;

/**
 * A subclass of class Record.
 * Objects of this type will be the training data. The distance between unseen data and
 * these objects will be calculated, so that the top k nearest neighbours can be picked
 * out according to the closest distance.
 */
public class TrainingRecord extends Record{
    /**
     * This will hold the distance between this record and the new point to be classified.
     */
    private double distance;

    public TrainingRecord(Record record, double distance){
        this.attributes = record.attributes;
        this.targetClass = record.targetClass;
        this.distance = distance;
    }

    /**
     * Getter
     * @return the distance
     */
    public double getDistance(){
        return this.distance;
    }
}
