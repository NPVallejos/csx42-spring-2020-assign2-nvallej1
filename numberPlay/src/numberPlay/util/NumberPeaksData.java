package numberPlay.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;

// Purpose - store calculated peaks from input file and persist results
// into output file
public class NumberPeaksData implements PersisterI, NumberPeaksResultsI {
	private String outputFilePath;
	private FileWriter myFileWriter;
	private ArrayList<Double> peaks;

	// Purpose - Constructor
	// Parameter outputFilePathIn - where results are written
	public NumberPeaksData(String outputFilePathIn) {
		outputFilePath = outputFilePathIn;
		myFileWriter = null;
		peaks = new ArrayList<Double>();
	}

	// Purpose - add to 'peaks' list
	// Parameter d - value to be added to list
	@Override
	public void store(Double d) {
		peaks.add(d);
	}

	// Purpose - write contents of 'peaks' into file 'outputFilePath'
	@Override
	public void writeToFile() throws IOException {
		myFileWriter = new FileWriter(outputFilePath);
		for (Double peakValue : peaks) {
			myFileWriter.write(peakValue.toString() + "\n");
		}
		close();
	}

	// Purpose - Close 'myFileWriter'
	@Override
	public void close() throws IOException {
		myFileWriter.close();
	}

	// Purpose - Necessary for equals() method in NumberPeaksObserver class
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

		// Cast 'o' to NumberPeaksData object
		NumberPeaksData other = (NumberPeaksData) o;
		if (peaks.size() != other.peaks.size()) {
			return false;
		}
		for (int i = 0; i < peaks.size(); i++) {
			if (peaks.get(i).compareTo(other.peaks.get(i)) != 0) {
				return false;
			}
		}

		return true;
	}
}
