package ru.aston.counter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiThreadCounterTest {
	static Collection<?> primitives;

	@BeforeAll
	static void setUp() {
		AtomicInteger counter = new AtomicInteger(0);
		primitives = Stream.generate(counter::getAndIncrement)
				.limit(10_000_000)
				.collect(Collectors.toList());
	}

	@Test
	void countParallelArrayTest() throws ExecutionException, InterruptedException {
		long start1 = System.nanoTime();
		Long result = MultiThreadCounter.countParallelArray(primitives.toArray(), 999_999, 1);
		long end1 = System.nanoTime();
		long oneThreadTime = (end1 - start1) / 1_000_000;
		System.out.println("Thread One: " + oneThreadTime + " ms");

		assertEquals(1, result);

		long start2 = System.nanoTime();
		result = MultiThreadCounter.countParallelArray(primitives.toArray(), 999_999, 2);
		long end2 = System.nanoTime();
		long twoThreadsTime = (end2 - start2) / 1_000_000;
		System.out.println("Two threads: " + twoThreadsTime + " ms");

		assertEquals(1, result);
	}

	@Test
	void parallelSubListTest() {
		long start1 = System.nanoTime();
		Long result = MultiThreadCounter.countParallelSubList(primitives, 999_999, 1);
		long end1 = System.nanoTime();
		long oneThreadTime = (end1 - start1) / 1_000_000;
		System.out.println("Thread One: " + oneThreadTime + " ms");

		assertEquals(1, result);

		long start2 = System.nanoTime();
		result = MultiThreadCounter.countParallelSubList(primitives, 999_999, 2);
		long end2 = System.nanoTime();
		long twoThreadsTime = (end2 - start2) / 1_000_000;
		System.out.println("Two threads: " + twoThreadsTime + " ms");

		assertEquals(1, result);
	}

	@Test
	void parallelStreamTest() {
		long start1 = System.nanoTime();
		assertEquals(1, MultiThreadCounter.countParallelStream(primitives, 999_999));
		long end1 = System.nanoTime();
		long parallelStreamTime = (end1 - start1) / 1_000_000;
		System.out.println("parallelStreamTest: " + parallelStreamTime + " ms");
	}
}