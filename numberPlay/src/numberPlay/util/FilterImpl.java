package numberPlay.util;

import java.util.ArrayList;

import numberPlay.filter.FilterI;

// Purpose - used as 'key' for storing
// ObserverI's in HashMap in NumberProcessor.
public class FilterImpl implements FilterI {
	private String tag;

	// Constructor
	public FilterImpl(String tagIn) {
		tag = tagIn;
	}

	@Override
	public String toString() {
		return tag;
	}

	// Purpose - test instance field 'tag' against parameter
	@Override
	public boolean test(String testTag) {
		return tag.equals(testTag);
	}

	/*
	** Purpose - necessary for registerObserver() in NumberProcessor class
	** src - (https://javarevisited.blogspot.com/2011/10/override-hashcode-in-java-example.html)
	*/
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		FilterImpl other = (FilterImpl) o;

		return (this.tag.equals(other.tag));
	}

	/*
	** Purpose - FilterImpl objects are the 'key' values in NumberProcessor's
	** 			 HashMap, i.e. need to custom hashCode() method
	** src - (https://www.baeldung.com/java-hashcode)
	*/
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (tag == null ? 0 : tag.hashCode());
		return hash;
	}
}
