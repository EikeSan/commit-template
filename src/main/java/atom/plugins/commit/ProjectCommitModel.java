package atom.plugins.commit;

public class ProjectCommitModel {

    public ProjectCommitModel() {
    }

    public ProjectCommitModel(String longDescription, String projectVersion) {
        this.longDescription = longDescription;
        this.projectVersion = projectVersion;
    }

    private String longDescription;
    private String projectVersion;

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription.trim();
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion.trim();
    }
}
