package numberPlay.util;

import numberPlay.subject.SubjectI;
import numberPlay.observer.ObserverI;
import numberPlay.filter.FilterI;

import java.util.HashMap;
import java.util.ArrayList;

import java.lang.NumberFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.System; // For System.exit

public class NumberProcessor implements SubjectI {
	HashMap <FilterI, ArrayList<ObserverI> > hMap;
	FileProcessor fileProcessorObj;
	String fileName;

	public NumberProcessor(String fileNameIn) {
		hMap = new HashMap <FilterI, ArrayList<ObserverI> >();
		fileName = fileNameIn;
	}

	public void processInputFile() throws FileNotFoundException, IOException {
		fileProcessorObj = new FileProcessor(fileName);
		String numStr = fileProcessorObj.poll();

		while (numStr != null) {
			Integer numberI = null;
			Double numberD = null;
			// Determine if numStr is an int or float or not a number
			try {
				numberI = Integer.parseInt(numStr);
			} catch (NumberFormatException e) {
				// System.out.println("Cannot convert to Integer");
			}
			if (numberI == null) {
				try {
					numberD = Double.parseDouble(numStr);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("'"+numStr+"' is not a number!");
				}
			}
			// Notify observers with correct filter and number
			if (numberI != null) {
				this.notifyObserver("INTEGER_EVENT", numberI);
			}
			else if (numberD != null) {
				this.notifyObserver("FLOATING_POINT_EVENT", numberD);
			}
			// Get next line in input file
			numStr = fileProcessorObj.poll();
		}
		// Notify observers to start writing to files
		this.notifyObserver("PROCESSING_COMPLETE", null);
	}

	@Override
	public void registerObserver(ObserverI o, FilterI filter) {
		ArrayList<ObserverI> observerList = hMap.get(filter);

		if (observerList != null) {
			// search for 'o' in observerList
			for (ObserverI obs : observerList) {
				if (obs.equals(o)) {
					return; // observer 'o' already exists - exit function
				}
			}
		}
		else {
			observerList = new ArrayList<ObserverI>();
		}
		// observerList is either empty or 'o' is not already in observerList
		observerList.add(o);
		// update hash map with the new observerList
		hMap.put(filter, observerList);
	}

	@Override
	public void deregisterObserver(ObserverI o) {
		// Tricky to implement
		// -----------
		// for each key in hMap
		// 		search respective ArrayList <Observers> for Observer o
		// 		if it exists, remove(o) from list
		//		finally, overwrite old list in hMap
		// end for loop
		// -----------
		for (FilterI filter : hMap.keySet()) {
			ArrayList<ObserverI> observerList = hMap.get(filter);
			boolean overwriteOldList = false;
			for (int i = 0; i < observerList.size(); i++) {
				if (observerList.get(i).equals(o)) {
					observerList.remove(i);
					overwriteOldList = true;
					break;
				}
			}
			if (overwriteOldList) {
				hMap.put(filter, observerList);
			}
		}
	}

	@Override
	public void notifyObserver(String tag, Object data) throws IOException {
		ArrayList <ObserverI> observers = null;

		for (FilterI filter : hMap.keySet()) {
			if (filter.test(tag)) {
				observers = hMap.get(filter);
				for (ObserverI o : observers) {
					o.update(data);
				}
				break;
			}
		}
	}

	@Override
	public String toString() {
		String report = "Total Registered Observers Report:";
		report += "\nFilter: INTEGER_EVENT             | Observers: " + hMap.get(new FilterImpl("INTEGER_EVENT")).size();
		report += "\nFilter: FLOATING_POINT_EVENT      | Observers: " + hMap.get(new FilterImpl("FLOATING_POINT_EVENT")).size();
		report += "\nFilter: PROCESSING_COMPLETE       | Observers: " + hMap.get(new FilterImpl("PROCESSING_COMPLETE")).size();
		return report;
	}
}
