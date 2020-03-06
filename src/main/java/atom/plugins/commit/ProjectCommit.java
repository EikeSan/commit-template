package atom.plugins.commit;

import javax.swing.*;

public class ProjectCommit {
    private JTextArea longDescription;
    private JTextField projectVersion;
    private JPanel projectCommitPanel;

    public ProjectCommitModel getProjectCommitModel() {
        ProjectCommitModel model = new ProjectCommitModel();
        model.setLongDescription(longDescription.getText());
        model.setProjectVersion(projectVersion.getText());

        return model;
    }

    public JPanel getProjectCommitPanel() {
        return projectCommitPanel;
    }
}
