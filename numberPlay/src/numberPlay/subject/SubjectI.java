package numberPlay.subject;

import numberPlay.observer.ObserverI;
import numberPlay.filter.FilterI;

import java.io.IOException;

public interface SubjectI {
	public void registerObserver(ObserverI o, FilterI filter);
	public void deregisterObserver(ObserverI o);
	public void notifyObserver(String tag, Object data) throws IOException;
}
