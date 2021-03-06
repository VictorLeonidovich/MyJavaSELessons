package main.taskmanagement;

import main.Mission;
import main.RobotTypeTarget;

public class TaskValidation {
	private String stringTask;
	private Object[] validatedTaskAttributes;
	private final TaskCreation taskCreation;

	// TODO delete this method
	public TaskCreation getTaskFactory() {
		return taskCreation;
	}

	public TaskValidation(TaskCreation taskCreation) {
		this.taskCreation = taskCreation;
	}

	public /*Object[]*/ void validateStringTask(String stringTask) throws StringTaskFormatException {
		this.stringTask = stringTask;
		// TODO object TaskParser return String[]
		String[] taskAttributes = parseStringTask();
		validateTaskAttributesAmount(taskAttributes);
		taskAttributes = prepareTaskAttributes(taskAttributes);
		validateTaskAttributes(taskAttributes);
		taskCreation.createTask(validatedTaskAttributes);
		//return validatedTaskAttributes;
	}

	private String[] parseStringTask() throws StringTaskFormatException {
		if (stringTask != null) {
			return stringTask.split(":");
		}
		throw new StringTaskFormatException(
				"Task [" + stringTask + "] can not be taken into execution. Wrong string task format.");
	}

	private void validateTaskAttributesAmount(String[] taskAttributes) throws StringTaskFormatException {
		if (taskAttributes != null) {
			if (taskAttributes.length != 3) {
				throw new StringTaskFormatException("Task [" + stringTask
						+ "] can not be taken into execution. Wrong string task format. Amount of ':' not equals 2.");
			}
			validatedTaskAttributes = new Object[taskAttributes.length];
		}
	}

	private String[] prepareTaskAttributes(String[] taskAttributes) {
		for (int i = 0; i < taskAttributes.length; i++) {
			taskAttributes[i] = taskAttributes[i].trim().toLowerCase();
		}
		return taskAttributes;
	}

	private void validateTaskAttributes(String[] taskAttributes) throws StringTaskFormatException {
		validateId(taskAttributes[0]);
		validateRobotExecutorType(taskAttributes[1]);
		validateMission(taskAttributes[2]);
	}

	private void validateId(String id) throws StringTaskFormatException {
		if (id != null) {
			if (isNumber(id)) {
				validatedTaskAttributes[0] = (Integer) Integer.parseInt(id);
			} else {
				throw new StringTaskFormatException("Task [" + stringTask
						+ "] can not be taken into execution. Wrong id format. Id is not number: [" + id + "].");
			}
		}
	}

	private boolean isNumber(String stringNumber) {
		return stringNumber.matches("[0-9]+");// TODO is maxInt
	}

	private void validateRobotExecutorType(String temporaryRobotExecutorType) throws StringTaskFormatException {
		if (temporaryRobotExecutorType != null) {
			if (isNumber(temporaryRobotExecutorType)) {
				validatedTaskAttributes[1] = Integer.parseInt(temporaryRobotExecutorType);
				return;
			} else {
				for (RobotTypeTarget robotType : RobotTypeTarget.values()) {
					if (temporaryRobotExecutorType.equals(robotType.name().toLowerCase())) {
						validatedTaskAttributes[1] = robotType;
						return;
					}
				}
			}

		}
		throw new StringTaskFormatException("Task [" + stringTask
				+ "] can not be taken into execution. Wrong RobotExecutorType format. Do not exist type of: ["
				+ temporaryRobotExecutorType + "].");
	}

	private void validateMission(String tempMission) throws StringTaskFormatException {
		if (tempMission != null) {
			for (Mission mission : Mission.values()) {
				if (mission.toString().toLowerCase().equals(tempMission)) {
					validatedTaskAttributes[2] = mission;
					return;
				}
			}
		}
		throw new StringTaskFormatException("Task [" + stringTask
				+ "] can not be taken into execution. Wrong mission format. Do not exist mission: [" + tempMission
				+ "].");
	}
}
