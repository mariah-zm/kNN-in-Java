import Data.AttributeType;
import Data.Dataset;
import FileManagement.FileManagement;
import kNN.kNearestNeighbours;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        final String dir = "datasets/";
        FileManagement fManager = new FileManagement(dir);

        List<List<String>> irisData = fManager.readData("iris.txt", ",");
        List<List<String>> diabetesData = fManager.readData("diabetes.txt", ",");
        List<List<String>> wineData = fManager.readData("winequality-white.txt", ";");
        List<List<String>> cmcData = fManager.readData("contraceptives.txt", ",");
        List<List<String>> eyestateData = fManager.readData("eyestate.txt", ",");

        /**
         *  Setting attribute types
         */
        AttributeType[] irisAttTypes = new AttributeType[4];
        for(int i=0; i<4; i++){
            irisAttTypes[i] = AttributeType.NUMERICAL;
        }

        AttributeType[] wineAttTypes = new AttributeType[11];
        for(int i=0; i<11; i++){
            wineAttTypes[i] = AttributeType.NUMERICAL;
        }

        AttributeType[] cmcAttTypes = {AttributeType.NUMERICAL, AttributeType.ORDINAL, AttributeType.ORDINAL,
                AttributeType.NUMERICAL, AttributeType.BINARY, AttributeType.BINARY, AttributeType.NOMINAL,
                AttributeType.ORDINAL, AttributeType.BINARY};

        AttributeType[] diabetesAttTypes = {AttributeType.NUMERICAL, AttributeType.BINARY, AttributeType.BINARY,
                AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY,
                AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY,
                AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY, AttributeType.BINARY};

        AttributeType[] eyeStateTypes = new AttributeType[14];
        for(int i=0; i<14; i++){
            eyeStateTypes[i] = AttributeType.NUMERICAL;
        }

        /**
         * Creating the datasets
         */
        Dataset iris = new Dataset(irisData, 3, Arrays.asList(irisAttTypes));
        Dataset cmc = new Dataset(cmcData, 3, Arrays.asList(cmcAttTypes));
        Dataset wine = new Dataset(wineData, 10, Arrays.asList(wineAttTypes));
        Dataset diabetes = new Dataset(diabetesData, 2, Arrays.asList(diabetesAttTypes));
        Dataset eyestate = new Dataset(eyestateData, 2, Arrays.asList(eyeStateTypes));

        /**
         *
         */
        kNearestNeighbours knn = new kNearestNeighbours();

        System.out.println("Doing kNN on Iris Dataset . . .");
        List<Double> irisResults = knn.kNN(1, iris, 0.67);
        System.out.println("Execution time: " + knn.getExecutionTime() + " seconds");

        /*System.out.println("\nDoing kNN on Eye State Dataset . . .");
        List<Double> eyestateResults = knn.kNN(1, eyestate, 0.7);
        System.out.println("Execution time: " + knn.getExecutionTime() + " seconds");*/

        System.out.println("\nDoing kNN on Diabetes Dataset . . .");
        List<Double> diabetesResults = knn.kNN(1, diabetes, 0.7);
        System.out.println("Execution time: " + knn.getExecutionTime() + " seconds");

        System.out.println("\nDoing kNN on CMC Dataset . . .");
        List<Double> cmcResults = knn.kNN(1, cmc, 0.7);
        System.out.println("Execution time: " + knn.getExecutionTime() + " seconds");

        System.out.println("\nDoing kNN on Wine Quality Dataset . . .");
        List<Double> wineResults = knn.kNN(3, wine, 0.7, new int[]{0, 2, 8, 4, 10});
        System.out.println("Execution time: " + knn.getExecutionTime() + " seconds");

    }
}
