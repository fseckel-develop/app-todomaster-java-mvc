package auxiliaries;

import model.Task;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Utility class for sorting {@link Task} objects using various predefined modes. <p>
 * Provides comparators and methods to sort tasks by status, title, details, priority,
 * deadline, creation date, or combinations thereof. </p>
 */
public class TaskSorter
{
    /**
     * Enumeration of sorting modes for tasks. Each mode represents a different
     * property or combination of properties by which a list of tasks can be sorted.
     */
    public enum Mode
    {
        BY_IMPORTANCE, BY_DEADLINE, BY_PRIORITY, OLDEST_FIRST, LEXICOGRAPHIC;

        @Override
        public String toString() {
            return switch (this) {
                case BY_IMPORTANCE -> "by Importance";
                case BY_DEADLINE -> "by Deadline";
                case BY_PRIORITY -> "by Priority";
                case OLDEST_FIRST -> "oldest first";
                case LEXICOGRAPHIC -> "lexicographical";
            };
        }
    }


    /** Comparator for sorting tasks by completion status. Base-Comparator */
    private static final Comparator<Task> sortByStatus =
            Comparator.comparing(Task::isDone);


    /** Comparator for sorting tasks lexicographically by title (case-insensitive). */
    private static final Comparator<Task> sortByTitle =
            Comparator.comparing(Task::getTitle, String.CASE_INSENSITIVE_ORDER);


    /** Comparator for sorting tasks lexicographically by details (case-insensitive). */
    private static final Comparator<Task> sortByDetails =
            Comparator.comparing(Task::getDetails, String.CASE_INSENSITIVE_ORDER);


    /** Comparator for sorting tasks by priority. */
    private static final Comparator<Task> sortByPriority =
            Comparator.comparing(Task::getPriority);


    /** Comparator for sorting tasks by deadline (nulls last). */
    private static final Comparator<Task> sortByDeadline =
            Comparator.comparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder()));


    /** Comparator for sorting tasks by creation date. */
    private static final Comparator<Task> sortByCreationDate =
            Comparator.comparing(Task::getCreationDateTime);


    /**
     * Returns a new list of tasks sorted by the given sort property.
     * @param property property by which to sort
     * @param tasks list of tasks to sort
     * @return new sorted list of tasks
     */
    public static List<Task> getTasksSortedBy(Mode property, List<Task> tasks) {
        return tasks.stream().sorted(getComparatorFor(property)).collect(Collectors.toList());
    }


    /**
     * Returns a comparator that sorts tasks by the given sort property.
     * @param property property by which to sort
     * @return the suited comparator
     */
    private static Comparator<Task> getComparatorFor(Mode property) {
        return switch (property) {
            case BY_IMPORTANCE -> sortByStatusThen(sortBy(sortByDeadline, sortByPriority));
            case BY_DEADLINE -> sortByStatusThen(sortByDeadline);
            case BY_PRIORITY -> sortByStatusThen(sortByPriority);
            case OLDEST_FIRST -> sortByStatusThen(sortByCreationDate);
            case LEXICOGRAPHIC -> sortByStatusThen(sortBy(sortByTitle, sortByDetails));
        };
    }


    /**
     * Returns a comparator that sorts tasks by combining two comparators.
     * @param first primary comparator
     * @param second secondary comparator to use after status
     * @return combined comparator
     */
    private static Comparator<Task> sortBy(Comparator<Task> first, Comparator<Task> second) {
        return first.thenComparing(second);
    }


    /**
     * Returns a comparator that sorts tasks by status first, then by a secondary comparator.
     * @param otherComparator secondary comparator to use after status
     * @return combined comparator
     */
    private static Comparator<Task> sortByStatusThen(Comparator<Task> otherComparator) {
        return sortBy(sortByStatus, otherComparator);
    }
}
