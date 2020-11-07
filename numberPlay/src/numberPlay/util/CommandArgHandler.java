package numberPlay.util;

import java.lang.IllegalArgumentException;

// purpose - store and validate command line arguments
// Reference: Person.java -> https://piazza.com/class/k5k3yuyx97612d?cid=37
public class CommandArgHandler {
	private int numArgs;
	private String[] args;
	private static final int REQUIRED_NUMBER_OF_ARGS = 6;

	private static class ValidatorFetcher {
		// Purpose - Test if there are correct number of args
		public static Validator numArgsValidator(CommandArgHandler c) {
			return new Validator() {
				@Override
				public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
					if (c.numArgs != REQUIRED_NUMBER_OF_ARGS) {
						throw new NumberOfArgsException("Incorrect number of arguments [" + c.numArgs + "].");
					}
				}
			};
		}
		// Purpose - Test if cmd arg(s) is(are) missing
		public static Validator argNamesValidator(CommandArgHandler c) {
			return new Validator() {
				@Override
				public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
					if (c.args[0].equals("${inputNumStream}")) {
						throw new InvalidArgNameException("Path not given for the input file.");
					}
					else if (c.args[1].equals("${runAvgWindowSize}")) {
						throw new InvalidArgNameException("Running average window size not provided.");
					}
					else if (c.args[2].equals("${runAvgOutFile}")) {
						throw new InvalidArgNameException("Path not given for running average output file.");
					}
					else if (c.args[3].equals("${k}")) {
						throw new InvalidArgNameException("K-value not given for top k numbers calculation.");
					}
					else if (c.args[4].equals("${topKNumOutFile}")) {
						throw new InvalidArgNameException("Top K numbers output file path not provided.");
					}
					else if (c.args[5].equals("${numPeaksOutFile}")) {
						throw new InvalidArgNameException("Number of Peaks output file path not provided.");
					}
				}
			};
		}
		// Purpose - Tests if -Dk > 0 and -DrunAvgWindowSize > 0
		public static Validator argValuesValidator(CommandArgHandler c) {
			return new Validator() {
				@Override
				public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
					if (Integer.parseInt(c.args[1]) <= 0) {
						throw new IllegalArgumentException("Running average window size must be greater than 0!");
					}
					else if (Integer.parseInt(c.args[3]) <= 0) {
						throw new IllegalArgumentException("K-value must be greater than 0!");
					}
				}
			};
		}
	}

	// Purpose - Constructor
	public CommandArgHandler(int numArgs, String[] args) throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
		this.numArgs = numArgs;
		this.args = args;

		// Validating fields
		ValidatorUtil.validate("Failed: ",
			ValidatorFetcher.numArgsValidator(this),
			ValidatorFetcher.argNamesValidator(this),
			ValidatorFetcher.argValuesValidator(this));
	}
}
