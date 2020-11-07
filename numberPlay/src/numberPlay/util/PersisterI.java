package numberPlay.util;

import java.io.IOException;

/**
* To be implemented by classes that persist generated output data.
*/
public interface PersisterI {
	void close() throws IOException;
	void writeToFile() throws IOException;
}
