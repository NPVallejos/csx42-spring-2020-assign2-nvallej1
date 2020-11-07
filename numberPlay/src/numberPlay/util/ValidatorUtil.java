package numberPlay.util;

import java.lang.IllegalArgumentException;

// src: ValidationTest.java -> https://piazza.com/class/k5k3yuyx97612d?cid=37
public final class ValidatorUtil {
	public static void validate(String baseErrMsg, Validator... validators) throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
		for (Validator v : validators) {
			try {
				v.run();
			} catch (NumberOfArgsException e) {
				throw new NumberOfArgsException(baseErrMsg.concat(": " + e.getMessage()));
			} catch (InvalidArgNameException e) {
				throw new InvalidArgNameException(baseErrMsg.concat(": " + e.getMessage()));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(baseErrMsg.concat(": " + e.getMessage()));
			}
		}

	}
}
