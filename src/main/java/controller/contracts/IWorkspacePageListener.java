package controller.contracts;

import model.Checklist;


/**
 * Defines an alias of {@link ICollectionPageListener} for handling user
 * interactions on the workspace page. It supports actions that affect the
 * entire workspace (such as renaming the workspace, adding new checklists,
 * or clearing all checklists) and {@link IDataPanelListener} for responding
 * to checklist-specific interactions.
 */
public interface IWorkspacePageListener extends ICollectionPageListener, IDataPanelListener<Checklist> {}