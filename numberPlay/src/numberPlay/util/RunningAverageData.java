package numberPlay.util;

import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;

// Purpose - store calculated running averages and persist results
// into output file
public class RunningAverageData implements PersisterI, RunningAverageResultsI {
	private String outputFilePath;
	private FileWriter myFileWriter;
	private ArrayList<Double> runningAvgList;

	// Purpose - Constructor
	// Parameter outputFilePathIn - where results are written
	public RunningAverageData(String outputFilePathIn) {
		outputFilePath = outputFilePathIn;
		myFileWriter = null;
		runningAvgList = new ArrayList<Double>();
	}

	// Purpose - add to 'runningAvgList'
	// Parameter d - value to be added to list
	@Override
	public void store(Double d) {
		runningAvgList.add(d);
	}

	// Purpose - write contents of 'peaks' into file 'outputFilePath'
	@Override
	public void writeToFile()  throws IOException {
		myFileWriter = new FileWriter(outputFilePath);
		for (Double avg : runningAvgList) {
			myFileWriter.write(avg.toString() + "\n");
		}
		close();
	}

	// Purpose - Close 'myFileWriter'
	@Override
	public void close() throws IOException{
		myFileWriter.close();
	}

	// Purpose - Necessary for equals() method in RunningAverageObserver class
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

		// Cast 'o' to RunningAverageData object
		RunningAverageData other = (RunningAverageData) o;
		if (runningAvgList.size() != other.runningAvgList.size()) {
			return false;
		}
		for (int i = 0; i < runningAvgList.size(); i++) {
			if (!runningAvgList.get(i).equals(other.runningAvgList.get(i))) {
				return false;
			}
		}

		return true;
	}
}
