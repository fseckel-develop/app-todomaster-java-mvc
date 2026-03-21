package controller.mocking;

import controller.contracts.IAppNavigator;
import model.Checklist;
import model.Workspace;

import javax.swing.*;

public class MockAppNavigator implements IAppNavigator {
    public JPanel registeredPage;
    public boolean workspacePageShown;
    public Checklist shownChecklist;
    public Workspace workspace = new Workspace();

    @Override
    public void registerPage(JPanel page) {
        registeredPage = page;
    }

    @Override
    public void showWorkspacePage() {
        workspacePageShown = true;
    }

    @Override
    public void showChecklistPage(Checklist checklist) {
        shownChecklist = checklist;
    }

    @Override
    public JFrame getWindow() {
        return null;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }
}
