package askfm.impl.question;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.tuple.Pair;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import askfm.api.properties.PropertyService;
import askfm.api.question.CountryFrequencyValidationService;

@Singleton
public class CountryFrequencyValidationServiceImpl implements CountryFrequencyValidationService {
	private final static String QUESTIONS_COUNTRY_PER_SECOND = "askfm.country.frequency";

	// HashMap of the lockers for country and queues which contain times (in nano) of last N questions for
	// each country.

	private Map<String, Pair<Object, CircularFifoQueue<Long>>> countryLockersAndQueues = Collections.synchronizedMap(new HashMap<>());
	//private Map<String, CircularFifoQueue<Long>> questionsTimes = new HashMap<>();

	final private Integer frequency;

	@Inject
	public CountryFrequencyValidationServiceImpl(PropertyService propertyService) {
		this(Integer.valueOf(propertyService.getPropertyValue(QUESTIONS_COUNTRY_PER_SECOND)));
	}

	public CountryFrequencyValidationServiceImpl(Integer frequency) {
		this.frequency = frequency;
	}

	@Override
	/*
	 * Return true is countryCode is valid ("according frequency")
	 */
	public Boolean doValidation(String countryCode) {
		Long currentTime = System.nanoTime();
		return checkIfCountryAndTime(countryCode, currentTime);
	}

	/*
	 * Check if question is valid for country (number of questions for one
	 * country per second is limited)
	 */
	private Boolean checkIfCountryAndTime(String countryCode, Long time) {
		/*
		 * Queues contain times of last N questions for each country. We check
		 * of the time on current-N question. It must be more then 1 second to
		 * the current time.
		 */
		Pair<Object, CircularFifoQueue<Long>> lockerAndQueue = countryLockersAndQueues.get(countryCode);
		
		if (lockerAndQueue == null) {
			lockerAndQueue = Pair.of(new Object(), new CircularFifoQueue<>(frequency));
		
			Pair<Object, CircularFifoQueue<Long>> old = countryLockersAndQueues.putIfAbsent(countryCode, lockerAndQueue);
			if (old != null) {
				lockerAndQueue = old;
			}
		}

		Object locker = lockerAndQueue.getLeft();
		CircularFifoQueue<Long> lastTimes = lockerAndQueue.getRight();
		
		// lock by country. Only one queue for each country have to be processed
		synchronized (locker) {
			if ((lastTimes.size() >= frequency) && (time - lastTimes.element()) <= TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)) {
				return false;
			} else {
				lastTimes.add(time);
				return true;
			}
		}
	}
}
