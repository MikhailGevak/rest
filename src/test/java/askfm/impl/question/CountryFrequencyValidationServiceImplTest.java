package askfm.impl.question;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

import askfm.api.question.CountryFrequencyValidationService;

public class CountryFrequencyValidationServiceImplTest {
	@Test
	public void doValidationTestSimple() throws InterruptedException, ExecutionException {
		CountryFrequencyValidationService frequencyValidationService = new CountryFrequencyValidationServiceImpl(2);

		Collection<Callable<Boolean>> tasks = new LinkedList<>();

		tasks.add(() -> frequencyValidationService.doValidation("US"));
		tasks.add(() -> frequencyValidationService.doValidation("US"));
		tasks.add(() -> frequencyValidationService.doValidation("US"));

		ExecutorService service = Executors.newFixedThreadPool(10);

		List<Future<Boolean>> results = service.invokeAll(tasks);
		Thread.sleep(1001);
		results.addAll(service.invokeAll(tasks));
		Pair<Integer, Integer> counters = getConters(results);

		assertEquals(Integer.valueOf(4), counters.getRight());
		assertEquals(Integer.valueOf(2), counters.getLeft());

	}

	@Test
	public void doValidationTestNotSimple() throws InterruptedException, ExecutionException {
		CountryFrequencyValidationService frequencyValidationService = new CountryFrequencyValidationServiceImpl(2);

		Collection<Callable<Boolean>> tasks = new LinkedList<>();

		tasks.add(() -> frequencyValidationService.doValidation("UA"));
		tasks.add(() -> frequencyValidationService.doValidation("UA"));
		tasks.add(() -> frequencyValidationService.doValidation("US"));
		tasks.add(() -> frequencyValidationService.doValidation("US"));
		tasks.add(() -> frequencyValidationService.doValidation("US"));
		tasks.add(() -> frequencyValidationService.doValidation("GE"));
		tasks.add(() -> frequencyValidationService.doValidation("GE"));
		tasks.add(() -> frequencyValidationService.doValidation("GE"));
		tasks.add(() -> frequencyValidationService.doValidation("GE"));

		ExecutorService service = Executors.newFixedThreadPool(10);

		List<Future<Boolean>> results = service.invokeAll(tasks);

		List<Future<Boolean>> result1 = Arrays.asList(results.get(0), results.get(1));
		List<Future<Boolean>> result2 = Arrays.asList(results.get(2), results.get(3), results.get(4));
		List<Future<Boolean>> result3 = Arrays.asList(results.get(5), results.get(6), results.get(7), results.get(8));

		Pair<Integer, Integer> counter1 = getConters(result1);
		Pair<Integer, Integer> counter2 = getConters(result2);
		Pair<Integer, Integer> counter3 = getConters(result3);

		assertEquals(Integer.valueOf(2), counter1.getRight());
		assertEquals(Integer.valueOf(0), counter1.getLeft());

		assertEquals(Integer.valueOf(2), counter2.getRight());
		assertEquals(Integer.valueOf(1), counter2.getLeft());

		assertEquals(Integer.valueOf(2), counter3.getRight());
		assertEquals(Integer.valueOf(2), counter3.getLeft());
	}

	private Pair<Integer, Integer> getConters(List<Future<Boolean>> results)
			throws InterruptedException, ExecutionException {
		Integer trueCount = 0;
		Integer falseCount = 0;

		for (Future<Boolean> result : results) {
			if (result.get()) {
				trueCount++;
			} else {
				falseCount++;
			}
		}

		return Pair.of(falseCount, trueCount);
	}
}
