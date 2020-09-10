package kNN;

import Data.AttributeType;
import Data.DataTransformation;
import Data.Dataset;
import Data.Record.Record;
import Data.Record.TrainingRecord;

import java.util.*;

/**
 * This class implements the kNN algorithm.
 */
public class kNearestNeighbours {

    /**
     * The number of neighbours to pick
     */
    private int k;
    /**
     * Lists that will hold the index of the features to be considered during kNN.
     * The NOAttributes list will hold the index of the attributes that are Numerical and Ordinal,
     * while CBAttributes will hold Nominal and Binary attributes.
     */
    private List<Integer> NOAttributes = new ArrayList<>();
    private List<Integer> CBAttributes = new ArrayList<>();
    /**
     * The total time taken to classify new data
     */
    private double executionTime;

    /**
     * This method validates the user's choice of k and accepts it if it isn't zero, isn't the same
     * as the total number of classes, and if it isn't even. If zero the method returns false and kNN
     * isn't performed. If even or a multiple of the number of classes, it is incremented to avoid ties.
     * @param k given k
     * @param classes number of classes
     * @return true if k has been set
     */
    private boolean setK(int k, int classes) {
        if (k <= 0) {
            System.out.println("k has to be larger than zero");
            return false;
        }

        if (k % 2 == 0) {
            System.out.println("k even - incremented to avoid ties");
            k++;
        }

        if (k % classes == 0) {
            this.k = k+2;
            System.out.println("k multiple of no. of classes - incremented to avoid ties");
        } else {
            this.k = k;
        }
        return true;
    }

    /**
     * This method is called when teh user wants to consider all features.
     * @param k the no. of neighbours to consider
     * @param data the dataset
     * @param ratio the percentage of training data from the number of samples
     * @return the results of the algorithm
     */
    public List<Double> kNN(int k, Dataset data, double ratio){
        // All features will be considered for the algorithm
        int[] features = new int[data.getNumOfAttributes()];
        for(int i=0; i<features.length; i++){
            features[i] = i;
        }
        // Calling other method and passing the index of all features
        return kNN(k, data, ratio, features);
    }

    /**
     * This method is called to perform kNN with "user feature selection".
     * @param k the no. of neighbours to consider
     * @param data the dataset
     * @param ratio the percentage of training data from the number of samples
     * @param featuresSelected the index of the features to consider
     * @return the results of the algorithm
     */
    public List<Double> kNN(int k, Dataset data, double ratio, int[] featuresSelected){
        List<Double> results = new ArrayList<>();
        // If the entered ration does not allow for a proper partition, the algorithm won't start
        if(ratio<1 || ratio>0) {
            // If k is not properly set the algorithm won't start
            if (setK(k, data.getNumOfClasses())) {
                System.out.println("Performing kNN with k = " + this.k);

                // Starts counting time
                final long startTime = System.currentTimeMillis();

                int trainingSize = (int) (data.getNumOfSamples() * ratio);
                int testSize = data.getNumOfSamples() - trainingSize;

                // Splitting data in a training set and test set
                List<Record> trainingSet = getSet(data.getData(), 0, trainingSize);
                List<Record> testSet = getSet(data.getData(), trainingSize, data.getNumOfSamples());

                // Normalising data
                normalise(data.getAttributeTypes(), trainingSet);
                normalise(data.getAttributeTypes(), testSet);

                splitAttributes(data.getAttributeTypes(), featuresSelected);

                List<String> predictedClasses = new ArrayList<>();
                List<String> targetClasses = new ArrayList<>();

                TrainingRecord[] nearestNeighbours;
                for (Record newPoint : testSet) {

                    nearestNeighbours = getKNearestNeighbours(trainingSet, newPoint);

                    predictedClasses.add(classify(nearestNeighbours));
                    targetClasses.add(newPoint.getTargetClass());
                }

                String predicted, target;
                int correct = 0;
                double accuracy;
                // Checking correct classifications
                for (int i = 0; i < testSize; i++) {
                    predicted = predictedClasses.get(i);
                    target = targetClasses.get(i);

                    if (predicted.equals(target)) {
                        correct++;
                    }
                }

                // Calculating accuracy
                accuracy = ((double) correct / testSize) * 100;
                System.out.println("The accuracy is " + String.format("%.2f", accuracy) + "%");

                // Stops counting time
                final long endTime = System.currentTimeMillis();
                // Calculating time taken
                this.executionTime = endTime - startTime;

                results.add((double) this.k);
                results.add((double) correct);
                results.add(accuracy);
                results.add(this.executionTime);

                //Resetting object values
                reset();
            } else {
                System.out.println("Couldn't perform kNN.");
            }
        } else {
            System.out.println("Couldn't perform kNN. \nNeed a valid ratio for training and test size.");
        }
        return results;
    }

    /**
     * This method returns a subset of an array of data
     * @param data the set from which the subset will be extracted
     * @param start the index from which the subset will start
     * @param end the index where the subset will end
     * @return the subset as a list of Records
     */
    private List<Record> getSet(Record[] data, int start, int end){
        List<Record> set = new ArrayList<>();

        for(int j=start; j<end; j++){
            set.add(data[j]);
        }

        return set;
    }

    /**
     * A method that normalises the values of the numerical and binary attributes
     * @param attTypes a list of attribute types
     * @param data the data to be normalised
     */
    private void normalise(List<AttributeType> attTypes, List<Record> data){
        DataTransformation dt = new DataTransformation();
        dt.dataNormalisation(attTypes, data);
    }


    /**
     * Resets the class' properties after on algorithm is performed
     */
    private void reset(){
        this.k = 0;
        NOAttributes.clear();
        CBAttributes.clear();
    }

    /**
     * This method categorises the attributes by types. The indexes of ordinal and numerical attributes
     * are stored in one array, while indexes of categorical and binary attributes are stored in another.
     * This is done since two different distance metrics will be used on the two separate groups.
     * This method also filters out non-chosen features, since only the index of the features to be considered
     * are stored.
     * @param attTypes the attribute types
     * @param features an array of the index of the features to be considered
     */
    private void splitAttributes(List<AttributeType> attTypes, int[] features) {
        for (int i : features) {
            if (attTypes.get(i) == AttributeType.NUMERICAL || attTypes.get(i) == AttributeType.ORDINAL) {
                NOAttributes.add(i);
            } else {
                CBAttributes.add(i);
            }
        }
    }

    /**
     * Getter
     * @return the time taken for training and classfication
     */
    public double getExecutionTime() {
        return this.executionTime / (double) 1000;
    }

    /**
     * This method calculates the distance between the new point and each data point in the
     * training set. The k nearest points are stored in an array.
     * @param training all the training data
     * @param test the new point
     * @return the k nearest neighbours, i.e. the points closest to new point
     */
    private TrainingRecord[] getKNearestNeighbours(List<Record> training, Record test) {
        TrainingRecord[] kNearest = new TrainingRecord[this.k];

        for (int i = 0; i < this.k; i++) {
            kNearest[i] = new TrainingRecord(training.get(i), calculateDistance(training.get(i), test));
        }

        sortNeighbours(kNearest);

        double distance;
        for (int j = this.k; j < training.size(); j++) {
            distance = calculateDistance(training.get(j), test);
            if (distance < kNearest[this.k - 1].getDistance()) {
                kNearest[this.k - 1] = new TrainingRecord(training.get(j), distance);
                sortNeighbours(kNearest);
            }
        }

        return kNearest;
    }

    /**
     * Bubble sort algorithm to sort the k nearest neighbours
     * @param kNearest the
     */
    private void sortNeighbours(TrainingRecord[] kNearest) {
        for (int m = 0; m < this.k; m++) {
            for (int n = 0; n < this.k - m - 1; n++) {
                if (kNearest[n].getDistance() > kNearest[n + 1].getDistance()) {
                    // Swap training records
                    TrainingRecord temp = kNearest[n];
                    kNearest[n] = kNearest[n + 1];
                    kNearest[n + 1] = temp;
                }
            }
        }
    }

    /**
     * This method calculates the distance between two points. Since datasets may contain mixed
     * data types, two metrics are used. These are then combined by using weights.
     * @param training the training point
     * @param test the new point
     * @return the distance between two points
     */
    private double calculateDistance(Record training, Record test) {
        return hammingDistance(training, test)*CBAttributes.size()
                + euclideanDistance(training, test)*NOAttributes.size();
    }

    /**
     * Hamming distance is used on attributes that are either categorical or binary.
     * A distance of 1 is added to the total distance if the categories differ.
     * @param training the training point
     * @param test the new point
     * @return the hamming distance of categorical attributes between the two points
     */
    private int hammingDistance(Record training, Record test) {
        int distance = 0;
        for (int i : CBAttributes) {
            String trainingValue = training.getAttribute(i);
            String testValue = test.getAttribute(i);

            // If categories differ, increment distance
            if (!testValue.equals(trainingValue)) {
                distance++;
            }
        }
        return distance;
    }

    /**
     * Euclidean distance metric is used on attributes that are numerical or ordinal.
     * @param training the training point
     * @param test the new point
     * @return the euclidean distance between the two points
     */
    private double euclideanDistance(Record training, Record test) {
        double distance = 0;
        for (int i : NOAttributes) {
            double trainingValue = Double.parseDouble(training.getAttribute(i));
            double testValue = Double.parseDouble(test.getAttribute(i));

            distance += Math.pow(testValue - trainingValue, 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

    /**
     * This method classifies the new point by considering the k nearest neighbours.
     * The class assigned is the class most common among the k neighbours.
     * @param kNearest the k nearest neighbours
     * @return the predicted class
     */
    private String classify(TrainingRecord[] kNearest) {
        String neighbour;

        HashMap<String, Integer> neighboursClass = new HashMap<>();

        for (int i = 0; i < this.k; i++) {
            neighbour = kNearest[i].getTargetClass();
            if (!neighboursClass.containsKey(neighbour)) {
                neighboursClass.put(neighbour, 1);
            } else {
                neighboursClass.put(neighbour, neighboursClass.get(neighbour) + 1);
            }
        }

        HashMap.Entry<String, Integer> maxEntry = null;
        for (HashMap.Entry<String, Integer> entry : neighboursClass.entrySet()) {
            if (maxEntry == null || maxEntry.getValue().compareTo(entry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        assert maxEntry != null;
        return maxEntry.getKey();
    }

}
