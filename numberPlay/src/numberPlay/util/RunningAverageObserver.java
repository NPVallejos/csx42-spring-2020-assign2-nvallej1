package numberPlay.util;

import numberPlay.observer.ObserverI;

import java.util.ArrayList;

import java.io.IOException;

// Purpose - ObserverI class implementation that calculates
// running averages
public class RunningAverageObserver implements ObserverI {
	private RunningAverageResultsI myRunningAvgData;
	private int avgWindowSize;
	private double runningTotal;
	private ArrayList<Integer> fifoQueue;

	// Purpose - Constructor
	// Parameter 'o' - instance of RunningAverageResultsI
	// Parameter 'avgWindowSizeIn' - an int that indicates
	// average calculation window size, i.e. the size of
	// instance field 'fifoQueue'
	public RunningAverageObserver(RunningAverageResultsI o, int avgWindowSizeIn) {
		myRunningAvgData = o;
		avgWindowSize = avgWindowSizeIn;
		runningTotal = 0;
		fifoQueue = new ArrayList<Integer>();
	}

	// Purpose - Calculate running average and manipulate 'fifoQueue'
	// Parameter 'o' - holds the number read from input file
	// Assumption: type of Object o is an Integer as
	// RunningAverageObserver only has "INTEGER_EVENT" filter
	@Override
	public void update(Object o) throws IOException {
		// Determine if we should call writeToFile
		// Note: (o == null) implies call writeToFile()
		if (o != null) {
			Integer d = (Integer) o;
			double newRunningAverage = 0;
			// Remove an element in fifoQueue if necessary
			if (fifoQueue.size() == avgWindowSize) {
				runningTotal -= fifoQueue.get(0);
				fifoQueue.remove(0);
			}
			fifoQueue.add(d);
			runningTotal += d;
			// Calculate Running Average - Constant Time
			newRunningAverage = (runningTotal)/fifoQueue.size();
			// Round new average to 2 decimal places (if any)
			newRunningAverage = Math.round(newRunningAverage * 100) / 100.0;
			// Store new average
			((RunningAverageData) myRunningAvgData).store(newRunningAverage);
		}
		else {
			((RunningAverageData) myRunningAvgData).writeToFile();
		}
	}

	// Purpose - Necessary for registerObserver() method in NumberProcessor class
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

		// Cast 'o' to RunningAverageObserver
		RunningAverageObserver other = (RunningAverageObserver) o;

		// Compare RunningAverageData instances
		if (!myRunningAvgData.equals(other.myRunningAvgData)) {
			return false;
		}

		return true;
	}
}
