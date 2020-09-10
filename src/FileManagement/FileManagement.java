package FileManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to handle file I/O operations.
 * It loads the datasets from given files and also outputs the results of unseen data.
 */
public class FileManagement {

    private String currentDirectory;

    public FileManagement(String directory){
        this.currentDirectory = directory;
    }

    /**
     * This method reads data from a given file. The data is strong as a String data type.
     * The feature names are also read from the file and stored in the first index of the list.
     * The Dataset class will then separate that list from the data.
     * @param filename the name of the file
     * @param delimiter the character which separates one value from the other
     * @return data in file as a list of lists
     * @throws Exception
     */
    public List<List<String>> readData(String filename, String delimiter) throws Exception {
        // The data organised as rows and columns
        List<List<String>> data = new ArrayList<>();
        // A variable to read a whole line from a file
        String line;
        // A list to store the attributes once separated
        List<String> values;

        try {
            File file = new File(currentDirectory + filename);
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                values = Arrays.asList(line.split(delimiter));
                data.add(values);
            }
            br.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return data;
    }

    /*
    public String writeResults(String filename, List<String> predictedClasses, int correct) throws IOException {
        String pathname = currentDirectory + filename;

        File file = new File(pathname);
        if(!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("Number of Samples: " + numOfSamples +
                "\nNumber of Training Points: " + trainingSet.length +
                "\nNumber of Test Points: " + validationSet.length +
                "\nCorrectly classified: " + correct + "\n");


        bw.write("\n{" + headers.get(0));
        for(int i=1; i<headers.size(); i++){
            bw.write(", " + headers.get(i));
        }
        bw.write(", Target Class, Predicted Class}");
        bw.newLine();

        int x = trainingSet.length;
        for(int j=0; j< validationSet.length; j++){
            for(int k=0; k<headers.size(); k++){
                bw.write(attributes.get(j+x).get(k) + " ");
            }
            bw.write(validationSet[j].getTargetClass() + " " + predictedClasses.get(j));

            bw.newLine();
        }

        bw.close();
        fw.close();

        return pathname;
    }*/
}
