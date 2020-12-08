# k-Nearest Neighbours in Java

This project was an assignment for the study unit ICS2207, Machine Learning: Introduction to Classification, Search and Optimisation.

*Note that in this implementation the distance metrics of choice were Euclidean Distance for numerical and ordinal data, and Hamming Distance for nominal and binary data. The combination of these distances is weighted.*

## Future Enhancements

Currently, this implementation of the algorithm only contains 2 different types of Distance Metrics, and doesn't contain any features for data pre-processing. In the future I plan to revisit this project, and:
- Fix any issues which may be present
- Add unit tests
- Add more distance metrics
- Add Feature Selection methods
- Add data visualisation and better analysis of results

## Using the Model

#### Using your own dataset

Currently, the main class performs kNN on the Eye State Dataset. This implementation comes along with another 3 datasets.
To use this model on another dataset, open the project folder in an IDE and amend the Main class as follows:

- If you would like to use one of the mentioned 4 datasets, the project folder contains a text file with snippets of code that may be put in the Main class instead of the code currently in place.
- If you would like to use your own dataset, make sure your data is in the form of a text file. Place the file in the following directory: kNN/datasets. Prior to applying the model to your data, you need to do some manual data pre-processing. Your text file should follow this template which may be found in this repository. 

#### Setting the Directory

To change the directories from which the program currently reads and writes data, this must be done through a change of variable in the Main class. Amend the value of variable *dir* to your desired directory.

## FAQ's

#### What is kNN?

The k-Nearest Neighbours algorithm is a supervised machine learning algorithm used for both classification and regression problems. It classifies unseen data by considering the k nearest neighbours of new data points.

#### What is the 'k' in kNN?

The k in kNN refers to the number of neighbours to be chosen. These neighbours are found by calculating distances between points. The k points with the shortest distance from the new point are considered to be the nearest neighbours. For classification problems, the most common class among the k neighbours is chosen to be the predicted class for the new point. For regression problems, the predicted value is calculated to be the average of the target value of the k neighbours. The best choice of k depends on the dataset. To avoid ties, k should be odd and coprime to the total number of classes. Also, generally, the larger k is, the less is the effect of the noise on the classifications.

#### How is the distance calculated? 

The distance between two points may be calculated using different distance metrics. The metric of choice depends on the attribute types of the data. Euclidean Distance and Manhattan Distance are among the most popular metrics for numerical attributes, while Hamming Distance is used for categorical types.

#### How is the performance of the algorithm? 

The performance of the k-Nearest Neighbours algorithm depends on many factors. The choice of k, the choice of the distance metric as well as the data itself play a big role in the effectiveness of the algorithm. If our choice of similarity measure, that is the distance metric, is poor, then this will affect the classification process of the algorithm as it will end up wrongly classifying unseen data. The choice of k, as well as the overall complexity of the algorithm, might also affect the performance on especially large datasets. If the algorithm is not implemented efficiently, it will hit worst case scenarios when datasets start getting big. Accuracy might also start decreasing as the dimensionality of data increases. Datasets with high dimensionality will result in a lot of noise in the data which can overfit the model. Feature selection should ideally be applied to reduce the number of features to be considered and hence provide more accurate results.
