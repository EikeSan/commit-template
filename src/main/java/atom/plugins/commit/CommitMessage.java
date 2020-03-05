package atom.plugins.commit;

import org.apache.commons.lang.WordUtils;

import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * @author Damien Arrachequesne <damien.arrachequesne@gmail.com>
 */
class CommitMessage {
    private static final int MAX_LINE_LENGTH = 72; // https://stackoverflow.com/a/2120040/5138796
    private final String content;

    CommitMessage(ChangeType changeType, String changeScope, String shortDescription, List<ProjectCommitModel> projectCommitModels, String closedIssues, String breakingChanges) {
        this.content = buildContent(changeType, changeScope, shortDescription, projectCommitModels, closedIssues, breakingChanges);
    }

    private String buildContent(ChangeType changeType, String changeScope, String shortDescription, List<ProjectCommitModel> projectCommitModels, String closedIssues, String breakingChanges) {
        StringBuilder builder = new StringBuilder();
        builder.append(changeType.label());
        if (isNotBlank(changeScope)) {
            builder
                    .append('(')
                    .append(changeScope)
                    .append(')');
        }
        builder
                .append(": ")
                .append(shortDescription);

        for (ProjectCommitModel projectCommitModel : projectCommitModels) {
            builder
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(WordUtils.wrap(projectCommitModel.getLongDescription(), MAX_LINE_LENGTH))
                    .append(System.lineSeparator())
                    .append(WordUtils.wrap(projectCommitModel.getProjectVersion(), MAX_LINE_LENGTH));
        }

        if (isNotBlank(breakingChanges)) {
            builder
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(WordUtils.wrap("BREAKING CHANGE: " + breakingChanges, MAX_LINE_LENGTH));
        }

        if (isNotBlank(closedIssues)) {
            builder.append(System.lineSeparator());
            for (String closedIssue : closedIssues.split(",")) {
                builder
                        .append(System.lineSeparator())
                        .append("Closes ")
                        .append(closedIssue);
            }
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return content;
    }
}