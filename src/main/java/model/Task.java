package model;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Defines a single task, modelled with a title and additional details as well a completion
 * status, priority, deadline and creation date. It extends {@link AbstractData} to provide
 * observability and JSON serialization.
 */
public class Task extends AbstractData
{
    /** The task's description. */
    private String details;

    /** Whether the task has been marked as completed. */
    private boolean isDone;

    /** The task's priority (HIGH, MEDIUM, or LOW). */
    private Priority priority;

    /** The deadline for completing the task. May be {@code null} if no deadline is set. */
    private LocalDate deadline;

    /** The date this task was created. This is set once and never changes. */
    private final LocalDateTime creationDateTime;


    /**
     * Creates a task from a JSON object.
     * @param taskData the JSON object containing task data. Must include the fields:
     * {@code title}, {@code details}, {@code isDone}, {@code priority},
     * {@code creationDate}, and optionally {@code deadline}.
     */
    public Task(JSONObject taskData) {
        this.title = taskData.getString("title");
        this.details = taskData.getString("details");
        this.isDone = taskData.getBoolean("isDone");
        this.priority = Priority.valueOf(taskData.getString("priority"));
        this.deadline = taskData.isNull("deadline") ? null : LocalDate.parse(taskData.getString("deadline"));
        this.creationDateTime = LocalDateTime.parse(taskData.getString("creationDate"));
    }


    /** Creates an empty task with the current date as the creation date. */
    public Task() {
        creationDateTime = LocalDateTime.now();
    }


    /**
     * Serializes this task into a JSON object.
     * @return {@link JSONObject} representing this task's data
     */
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("details", details);
        json.put("isDone", isDone);
        json.put("priority", priority.name());
        json.put("deadline", deadline != null ? deadline.toString() : JSONObject.NULL);
        json.put("creationDate", creationDateTime.toString());
        return json;
    }


    /**
     * Sets the description of the task.
     * @param details task's description
     */
    public void setDetails(String details) {
        this.details = details;
        notifyObservers();
    }


    /** Toggles the task's completion status. */
    public void toggleDone() {
        isDone = !isDone;
        notifyObservers();
    }


    /**
     * Sets the priority of the task.
     * @param priority new priority; must not be {@code null}
     * @throws IllegalArgumentException if {@code priority} is {@code null}
     */
    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Task must have a priority.");
        }
        this.priority = priority;
        notifyObservers();
    }


    /**
     * Sets the deadline of the task.
     * @param deadline deadline date, or {@code null} to clear it
     * @throws IllegalArgumentException if the deadline is before the creation date
     */
    public void setDeadline(LocalDate deadline) {
        if (creationDateTime == null) {
            throw new IllegalArgumentException("Task must have a creation date.");
        }
        if (deadline != null && deadline.isBefore(creationDateTime.toLocalDate())) {
            throw new IllegalArgumentException("Deadline is in the past.");
        }
        this.deadline = deadline;
        notifyObservers();
    }


    /** Checks if the task missed its deadline. */
    public boolean deadlineMissed() {
        return !isDone && deadline != null && LocalDate.now().isAfter(deadline);
    }


    /** Returns the task's additional details. */
    public String getDetails() {
        return details;
    }


    /** Returns whether the task is marked as done. */
    public boolean isDone() {
        return isDone;
    }


    /** Returns the priority of the task. */
    public Priority getPriority() {
        return priority;
    }


    /** Returns the deadline of the task or {@code null} if none */
    public LocalDate getDeadline() {
        return deadline;
    }


    /** Returns the creation date time of the task. */
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }


    /** Defines the priority levels a task can have. */
    public enum Priority
    {
        HIGH, MEDIUM, LOW;

        @Override
        public String toString() {
            return switch (this) {
                case HIGH -> "high";
                case MEDIUM -> "medium";
                case LOW -> "low";
            };
        }
    }
}
