package numberPlay.util;

import numberPlay.observer.ObserverI;

import java.util.ArrayList;
import java.math.BigDecimal;

import java.io.IOException;
import java.lang.ClassCastException;

import java.lang.System; // For System.exit

// Purpose - ObserverI class implementation that calculates
// the top 'k' numbers
public class TopKNumbersObserver implements ObserverI {
	private TopKNumbersResultsI myTopKNumbersData;
	private int k;
	private ArrayList<Double> fifoQueue;

	// Purpose - Constructor
	// Parameter 'o' - TopKNumbersResultsI object
	// Parameter 'kIn' - int that specifies 'k' value,
	// i.e. size of instance field 'fifoQueue'
	public TopKNumbersObserver(TopKNumbersResultsI o, int kIn) {
		myTopKNumbersData = o;
		k = kIn;
		fifoQueue = new ArrayList<Double>();
	}

	// Purpose - Update current top k numbers stored in 'fifoQueue'
	// Paramter 'o' - Either an Integer or Double that stores a
	// number read from the input file
	@Override
	public void update(Object o) throws IOException {
		// Determine if we should call writeToFile
		// Note: (o == null) implies call writeToFile()
		if (o != null) {
			BigDecimal currentNumber = null;

			// Try to cast to either an Integer
			try {
				currentNumber = new BigDecimal(((Integer)o).intValue());
			} catch (ClassCastException e) {
				// System.out.println("Cannot cast to Integer");
			}
			// Try to cast to a Double if needed
			if (currentNumber == null) {
				try {
					currentNumber = new BigDecimal(((Double) o).doubleValue());
				} catch (ClassCastException e) {
					throw new ClassCastException("Cannot cast " + currentNumber.toString() + "to a Integer or Double!");
				}
			}
			// Determine if currentNumber should be added to list 'fifoQueue'
			for (int i = fifoQueue.size()-1; i >= 0; i--) {
				BigDecimal elementI = new BigDecimal(fifoQueue.get(i).doubleValue());
				if (currentNumber.compareTo(elementI) == 1) {
					if (i == fifoQueue.size()-1) {
						// append currentNumber to the end of the list
						fifoQueue.add(currentNumber.doubleValue());
					}
					else {
						// insert at "i+1" -> shifts elements starting at 'i+1' to the right
						fifoQueue.add(i+1, currentNumber.doubleValue());
					}
					// Remove first element if there are more than 'k' elements
					if (fifoQueue.size() > k) {
						fifoQueue.remove(0);
					}
					// Exit for loop to prevent adding number again
					break;
				}
			}
			// Add to empty list if needed
			if (fifoQueue.isEmpty()) {
				fifoQueue.add(currentNumber.doubleValue());
			}
			// Store current 'fifoQueue' in instance field 'myTopKNumbersData'
			((TopKNumbersData) myTopKNumbersData).store(fifoQueue);
		}
		else {
			((TopKNumbersData) myTopKNumbersData).writeToFile();
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

		TopKNumbersObserver other = (TopKNumbersObserver) o;

		if (!myTopKNumbersData.equals(other.myTopKNumbersData)) {
			return false;
		}

		return true;
	}
}
