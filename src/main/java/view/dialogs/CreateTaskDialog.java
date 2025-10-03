package view.dialogs;

import model.Task;

import javax.swing.*;


/**
 * Defines a dialog for creating a new {@link Task}. <p>
 * This dialog allows the user to enter all the necessary information
 * for a new task, leveraging the input components defined in the
 * {@link EditTaskDialog}. Once confirmed, a new {@link Task} is created
 * and can be retrieved via {@link EditTaskDialog#getEditedData()}. </p>
 */
public class CreateTaskDialog extends EditTaskDialog
{
    /**
     * Constructs a new {@code CreateTaskDialog}.
     * @param owningWindow the parent {@link JFrame} that owns this dialog
     */
    public CreateTaskDialog(JFrame owningWindow) {
        super(new Task(), owningWindow);
        setTitle("Creating Task");
    }


    /** {@inheritDoc} */
    @Override
    protected String thisConfirmButtonText() {
        return "Create Task";
    }
}
