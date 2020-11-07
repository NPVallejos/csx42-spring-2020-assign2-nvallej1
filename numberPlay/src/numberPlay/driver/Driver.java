package numberPlay.driver;

import numberPlay.util.RunningAverageData;
import numberPlay.util.NumberPeaksData;
import numberPlay.util.TopKNumbersData;

import numberPlay.observer.ObserverI;
import numberPlay.util.RunningAverageObserver;
import numberPlay.util.NumberPeaksObserver;
import numberPlay.util.TopKNumbersObserver;

import numberPlay.subject.SubjectI;
import numberPlay.util.NumberProcessor;

import numberPlay.filter.FilterI;
import numberPlay.util.FilterImpl;

import java.io.FileNotFoundException;
import java.io.IOException;

import numberPlay.util.CommandArgHandler;
import numberPlay.util.InvalidArgNameException;
import numberPlay.util.NumberOfArgsException;
import java.lang.IllegalArgumentException;

/**
 * @author Nicholas Vallejos
 */
public class Driver {
	public static void main(String[] args) {

		/*
		* As the build.xml specifies the arguments as argX, in case the
		* argument value is not given java takes the default value specified in
		* build.xml. To avoid that, below condition is used
		* FIXME Refactor commandline validation using the validation design taught in class.
		*/
		try {
			CommandArgHandler cmdArgHandler = new CommandArgHandler(args.length, args);
		} catch (NumberOfArgsException | InvalidArgNameException | IllegalArgumentException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// FIXME Create an instance of each of the classes implementing PersisterI and the
		// corresponding ResultsI interface.
		// Observers use these objects to dump data to be stored and eventually persisted
		// to the corresponding output file.
		RunningAverageData runningAvgDataHandler = new RunningAverageData(args[2]);
		NumberPeaksData numberPeaksDataHandler = new NumberPeaksData(args[5]);
		TopKNumbersData topKNumbersDataHandler = new TopKNumbersData(args[4]);

		// FIXME Instantiate the subject.
		NumberProcessor subject = new NumberProcessor(args[0]);

		// Creating filters...
		FilterImpl intEventFilter = new FilterImpl("INTEGER_EVENT");
		FilterImpl floatEventFilter = new FilterImpl("FLOATING_POINT_EVENT");
		FilterImpl completeEventFilter = new FilterImpl("PROCESSING_COMPLETE");

		// FIXME Instantiate the observers, providing the necessary filter and the results object.
		RunningAverageObserver runningAvgObserver = new RunningAverageObserver(runningAvgDataHandler,
			Integer.parseInt(args[1]));

		NumberPeaksObserver numberPeaksObserver = new NumberPeaksObserver(numberPeaksDataHandler);

		TopKNumbersObserver topKNumbersObserver = new TopKNumbersObserver(topKNumbersDataHandler,
			Integer.parseInt(args[3]));

		// FIXME Register each observer with the subject for the necessary set of events.
		subject.registerObserver(runningAvgObserver, intEventFilter);
		subject.registerObserver(runningAvgObserver, completeEventFilter);

		subject.registerObserver(numberPeaksObserver, intEventFilter);
		subject.registerObserver(numberPeaksObserver, floatEventFilter);
		subject.registerObserver(numberPeaksObserver, completeEventFilter);

		subject.registerObserver(topKNumbersObserver, intEventFilter);
		subject.registerObserver(topKNumbersObserver, floatEventFilter);
		subject.registerObserver(topKNumbersObserver, completeEventFilter);

		// FIXME Delegate control to a separate utility class/method that provides numbers one at a time, from the FileProcessor,
		// to the subject.
		try {
			subject.processInputFile();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		** FINAL REPORT TEST
		** -----------------
		** Tests the following:
		** NumberProcessor method - deregisterObserver(ObserverI o)
		** Each Observer Class method - equals()
		** Each Data Class method - equals()
		*/
		/*
		System.out.println("--------------------------FINAL REPORT-------------------------");
		System.out.println(subject.toString());
		System.out.println("deregisterObserver(runningAvgObserver)-------------------------");
		subject.deregisterObserver(runningAvgObserver);
		System.out.println(subject.toString());
		System.out.println("deregisterObserver(numberPeaksObserver)------------------------");
		subject.deregisterObserver(numberPeaksObserver);
		System.out.println(subject.toString());
		System.out.println("deregisterObserver(topKNumbersObserver)------------------------");
		subject.deregisterObserver(topKNumbersObserver);
		System.out.println(subject.toString());
		*/
	}
}
