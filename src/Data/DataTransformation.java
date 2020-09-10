package Data;

import java.util.List;
import Data.Record.Record;

/**
 * This class contains preprocessing methods and sorting methods.
 *
 * Data preprocessing is necessary in any ML algorithms to avoid bias in results.
 * If data isn't processed prior, features whose range is wider than others will end up
 * weigh in a lot more in calculations and hence affecting results.
 *
 * The quick sort algorithm is used to find the maximum and minimum values in a column.
 */
public class DataTransformation {

    /**
     * This method normalises numerical and ordinal features in the range of [0,1] - data scaling
     * @param attTypes the list of attribute types
     * @param data the data to be normalised
     */
    public void dataNormalisation(List<AttributeType> attTypes, List<Record> data){
        AttributeType attType;
        for(int i=0; i < attTypes.size(); i++){
            attType = attTypes.get(i);

            if(attType == AttributeType.NUMERICAL || attType == AttributeType.ORDINAL){
                double[] column = getColumn(data, i);
                quickSort(column, 0, column.length-1);
                double min = column[0];
                double max = column[data.size()-1];

                double oldX, newX;
                for (Record record : data) {
                    oldX = Double.parseDouble(record.getAttribute(i));

                    newX = (oldX - min) / (max - min);

                    data.get(i).setAttribute(i, Double.toString(newX));
                }
            }
        }
    }

    /**
     * This method returns the whole column of the dataset. The whole column represents all the
     * sample values of a particular feature.
     * @param data the list of records to get the column from
     * @param colNum the index of the column
     * @return the column as an array of type double
     */
    private double[] getColumn(List<Record> data, int colNum){
        double[] column = new double[data.size()];
        for(int i=0; i<column.length; i++){
            column[i] = Double.parseDouble(data.get(i).getAttribute(colNum));
        }
        return column;
    }

    /**
     * Implementation of the quick sort algorithm.
     * @param data the data to be sorted
     * @param beg
     * @param end
     */
    private void quickSort(double[] data, int beg, int end){
        int partition = partition(data, beg, end);

        if(partition-1>beg) {
            quickSort(data, beg, partition - 1);
        }
        if(partition+1<end) {
            quickSort(data, partition + 1, end);
        }
    }

    /**
     *
     * @param data
     * @param beg
     * @param end
     * @return
     */
    private int partition(double[] data, int beg, int end){
        double pivot = data[end];
        double temp;

        for(int i=beg; i<end; i++){
            if(data[i]<pivot){
                temp = data[beg];
                data[beg]=data[i];
                data[i]=temp;
                beg++;
            }
        }

        temp = data[beg];
        data[beg] = pivot;
        data[end] = temp;

        return beg;
    }
}


