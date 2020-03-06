package atom.plugins.commit;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Damien Arrachequesne
 */
public class CommitPanel {
    private JPanel mainPanel;
    private JComboBox changeType;
    private JComboBox changeScope;
    private JTextField shortDescription;
    private JTextField closedIssues;
    private JTextArea breakingChanges;
    private JPanel projectCommit;
    private JButton addButton;
    private List<ProjectCommitModel>  projectCommitModels = new ArrayList<>();

    CommitPanel(Project project) {

        addButton = new JButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addButtonActionPerformed(actionEvent);
            }
        });

        addButtonActionPerformed(null);
        for (ChangeType type : ChangeType.values()) {
            changeType.addItem(type);
        }
        File workingDirectory = VfsUtil.virtualToIoFile(project.getBaseDir());
        Command.Result result = new Command(workingDirectory, "git log --all --format=%s | grep -Eo '^[a-z]+(\\(.*\\)):.*$' | sed 's/^.*(\\(.*\\)):.*$/\\1/' | sort -n | uniq").execute();
        if (result.isSuccess()) {
            result.getOutput().forEach(changeScope::addItem);
        }
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        projectCommit = new ProjectCommit().getProjectCommitPanel();
        projectCommit.setVisible(true);

        mainPanel.setLayout(mainPanel.getLayout());
        mainPanel.add(projectCommit);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    JPanel getMainPanel() {
        return mainPanel;
    }

    private List<ProjectCommitModel> getProjectCommits() {
        return this.projectCommitModels;
    }

    CommitMessage getCommitMessage() {
        return new CommitMessage(
                (ChangeType) changeType.getSelectedItem(),
                (String) changeScope.getSelectedItem(),
                shortDescription.getText().trim(),
                getProjectCommits(),
                closedIssues.getText().trim(),
                breakingChanges.getText().trim()
        );
    }

}
