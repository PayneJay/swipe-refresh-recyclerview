package jack.myapplication.agent;

public class AgentConfiguration {
    private String customApplicationVersion = null;
    private String customBuildId = null;

    public String getCustomApplicationVersion() {
        return customApplicationVersion;
    }

    public void setCustomApplicationVersion(String customApplicationVersion) {
        this.customApplicationVersion = customApplicationVersion;
    }

    public String getCustomBuildIdentifier() {
        return customBuildId;
    }

    public void setCustomBuildIdentifier(String customBuildId) {
        this.customBuildId = customBuildId;
    }
}
