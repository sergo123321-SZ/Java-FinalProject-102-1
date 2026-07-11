package ru.aston.command;


import ru.aston.core.AppConstants;
import ru.aston.model.Barrel;
import ru.aston.model.Car;
import ru.aston.model.Student;

import java.util.Collection;


public class ExecutionData {
	public boolean isExitRequested = false;
	public String lastError = null;
	public String outputString = null;
	public Collection<Car> carCollection = null;
	public Collection<Student> studentCollection = null;
	public Collection<Barrel> barrelCollection = null;
	public String filePath = null;
	public AppConstants.ModelType modelType = null;
	public AppConstants.SortType sortType = null;
	public AppConstants.WriteMode writeMode = null;
}
