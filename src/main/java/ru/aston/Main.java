package ru.aston;

import ru.aston.randomgenerator.BarrelRandomGenerator;
import ru.aston.randomgenerator.CarRandomGenerator;
import ru.aston.randomgenerator.StudentRandomGenerator;

public class Main {
	public static void main(String[] args) {

		BarrelRandomGenerator.generate(5).stream().forEach(System.out::println);
		CarRandomGenerator.generate(5).stream().forEach(System.out::println);
		StudentRandomGenerator.generate(5).stream().forEach(System.out::println);

	}
}
