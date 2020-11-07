package numberPlay.util;

import numberPlay.observer.ObserverI;

import java.math.BigDecimal;

import java.lang.ClassCastException;
import java.io.IOException;

import java.lang.System; // For System.exit

// Purpose - ObserverI class implementation that calculates peaks
public class NumberPeaksObserver implements ObserverI {
	private NumberPeaksResultsI myPeaksData;
	private BigDecimal previousNumber;

	// Purpose - Constructor
	// Parameter o - instance of NumberPeaksResultsI that
	// is used to initialize 'myPeaksData'
	public NumberPeaksObserver(NumberPeaksResultsI o) {
		myPeaksData = o;
		previousNumber = null;
	}

	// Purpose - determine if previousNumber should be
	// considered a peak number
	// Parameter o - instance of either Integer or Double
	// that contains a number just read from the input file
	@Override
	public void update(Object o) throws IOException {
		// Determine if we should call writeToFile
		// Note: (o == null) implies call writeToFile()
		if (o != null) {
			BigDecimal currentNumber = null;
			// Try to cast to either an Integer or Double
			try {
				currentNumber = new BigDecimal(((Integer)o).intValue());
			} catch (ClassCastException e) {
				// System.out.println("Cannot cast to Integer");
			}
			if (currentNumber == null) {
				try {
					currentNumber = new BigDecimal((Double) o);
				} catch (ClassCastException e) {
					throw new ClassCastException("Cannot cast " + currentNumber.toString() + "to a Integer or Double!");
				}
			}
			// Determine if previousNumber is a peak number by
			// comparing it to currentNumber
			if (previousNumber != null) {
				if (previousNumber.compareTo(currentNumber) == 1) {
					double peakValue = previousNumber.doubleValue();
					// Round to 2 decimal places (if any)
					// src - https://stackoverflow.com/a/11701527
					peakValue = Math.round(peakValue * 100) / 100.0;
					// store 'peakValue' in 'myPeaksData'
					((NumberPeaksData) myPeaksData).store(new Double(peakValue));
				}
			}
			// Set previousNumber to currentNumber for next time
			previousNumber = currentNumber;
		}
		else {
			((NumberPeaksData) myPeaksData).writeToFile();
		}
	}

	// Purpose - Necessary for notifyObserver() method in NumberProcessor class
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
		// Cast 'o' to NumberPeaksObserver
		NumberPeaksObserver other = (NumberPeaksObserver) o;
		// Compare instance field's
		if (!myPeaksData.equals(other.myPeaksData)) {
			return false;
		}

		return true;
	}
}
