package ru.aston.counter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class MultiThreadCounter {

	public static Long countParallelArray(Object[] objects, @NotNull Object object, int threadsCount)
			throws ExecutionException, InterruptedException
	{

		if (threadsCount <= 0) {
			throw new IllegalArgumentException("Threads count must be greater than 0");
		}

		List<Callable<Integer>> tasks = new ArrayList<>();
		int chunkSize = objects.length / threadsCount;
		for (int i = 0; i < threadsCount; i++) {
			int start = i * chunkSize;
			int end = (i == threadsCount - 1) ? objects.length : start + chunkSize;

			Callable<Integer> future = () -> {
				int count = 0;
				for (int j = start; j < end; j++) {
					if (Objects.equals(objects[j], object)) {
						count++;
					}
				}
				return count;
			};
			tasks.add(future);
		}

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadsCount);
		List<Future<Integer>> futures = fixedThreadPool.invokeAll(tasks);

		Long totalCount = 0L;
		for (Future<Integer> future : futures) {
			totalCount += future.get();
		}

		fixedThreadPool.shutdown();
		if (fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
			fixedThreadPool.shutdownNow();
		}

		return totalCount;
	}

	public static Long countParallelSubList(Collection<?> objects, @NotNull Object object, int threadsCount)
			throws ExecutionException, InterruptedException
	{

		Long totalCount = 0L;

		try (ForkJoinPool customThreadPool = new ForkJoinPool(threadsCount)) {
			int chunkSize = objects.size() / threadsCount;

			List<?> searchList = objects.stream().toList();
			List<Future<Long>> futures = new ArrayList<>();

			for (int i = 0; i < threadsCount; i++) {
				int start = i * chunkSize;
				int end = (i == threadsCount - 1) ? objects.size() : start + chunkSize;

				Future<Long> future = customThreadPool.submit(
						() -> searchList.subList(start, end)
								.stream()
								.filter(o -> Objects.equals(o, object))
								.count()
				);
				futures.add(future);
			}

			for (Future<Long> future : futures) {
				totalCount += future.get();
			}
		}
		return totalCount;
	}

	public static Long countParallelStream(Collection<?> objects, @NotNull Object object) {
		return objects.parallelStream()
				.filter(o -> Objects.equals(o, object))
				.count();
	}
}
