package test;

import java.util.concurrent.TimeUnit;

import main.FacadeDispatcher;

public class Main {

	public static void main(String[] args) {
		FacadeDispatcher facadeDispatcher = new FacadeDispatcher();
		int tasksCount = 50;
		RobotGenerator robotGenerator = new RobotGenerator(facadeDispatcher);
		Thread robotGeneratorThread = new Thread(robotGenerator);
		robotGeneratorThread.start();
		TaskGenerator tasksGenerator = new TaskGenerator(tasksCount, facadeDispatcher/*, robotGenerator*/);
		Thread taskGeneratorThread = new Thread(tasksGenerator);
		taskGeneratorThread.start();
		LogsPrinter logsPrinter = new LogsPrinter(tasksCount, facadeDispatcher);
		Thread logsPrinterThread = new Thread(logsPrinter);
		logsPrinterThread.start();
		
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
