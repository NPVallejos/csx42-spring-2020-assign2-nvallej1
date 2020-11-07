package numberPlay.util;

import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;

// Purpose - store the top K numbers and persist results
// into output file
public class TopKNumbersData implements PersisterI, TopKNumbersResultsI {
	private String outputFilePath;
	private FileWriter myFileWriter;
	private ArrayList< List<Double> > topKNumbers;

	// Purpose - Constructor
	// Parameter outputFilePathIn - where results are written
	public TopKNumbersData(String outputFilePathIn) {
		outputFilePath = outputFilePathIn;
		myFileWriter = null;
		topKNumbers = new ArrayList< List<Double> >();
	}

	// Purpose - add to ArrayList 'topKNumbers'
	// Parameter topK - List to be added to 'topKNumbers' ArrayList
	@Override
	public void store(List<Double> topK) {
		List<Double> newList = new ArrayList<Double>();
		for (Double num : topK) {
			newList.add(num);
		}
		topKNumbers.add(newList);
	}

	// Purpose - write contents of 'topKNumbers' into file 'outputFilePath'
	@Override
	public void writeToFile() throws IOException {
		myFileWriter = new FileWriter(outputFilePath);
		for (List<Double> list : topKNumbers) {
			myFileWriter.write("[");
			for (int i = list.size()-1; i >= 0; i--) {
				myFileWriter.write(list.get(i).toString());
				if (i != 0) {
					myFileWriter.write(", ");
				}
			}
			myFileWriter.write("]\n");
		}
		close();
	}

	// Purpose - Close 'myFileWriter'
	@Override
	public void close() throws IOException {
		myFileWriter.close();
	}

	// Purpose - Necessary for equals() method in TopKNumbersObserver class
	// Parameter o - Object to be compared with 'this'
	// src - (https://javarevisited.blogspot.com/2011/10/override-hashcode-in-java-example.html)
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		// Cast 'o' to TopKNumbersData
		TopKNumbersData other = (TopKNumbersData) o;
		if (topKNumbers.size() != other.topKNumbers.size()) {
			return false;
		}
		for (int i = 0; i < topKNumbers.size(); i++) {
			// Cache lists
			List<Double> myList = topKNumbers.get(i);
			List<Double> otherList = other.topKNumbers.get(i);
			// Compare each element in the above lists
			for (int j = 0; j < myList.size(); j++) {
				if (!myList.get(j).equals(otherList.get(j))) {
					return false;
				}
			}
		}

		return true;
	}
}
