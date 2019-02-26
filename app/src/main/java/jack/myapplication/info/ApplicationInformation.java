package jack.myapplication.info;

public class ApplicationInformation {
    private String appName;
    private String appVersion;
    private String appBuild;
    private String packageId;
    private int versionCode;

    public ApplicationInformation() {
        this.versionCode = -1;
    }

    public ApplicationInformation(String appName, String appVersion, String appBuild, String packageId) {
        this();
        this.appName = appName;
        this.appVersion = appVersion;
        this.appBuild = appBuild;
        this.packageId = packageId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppBuild() {
        return appBuild;
    }

    public void setAppBuild(String appBuild) {
        this.appBuild = appBuild;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ApplicationInformation that;
            label61:
            {
                that = (ApplicationInformation) o;
                if (this.appName != null) {
                    if (this.appName.equals(that.appName)) {
                        break label61;
                    }
                } else if (that.appName == null) {
                    break label61;
                }

                return false;
            }

            label54:
            {
                if (this.appVersion != null) {
                    if (this.appVersion.equals(that.appVersion)) {
                        break label54;
                    }
                } else if (that.appVersion == null) {
                    break label54;
                }

                return false;
            }

            if (this.appBuild != null) {
                if (!this.appBuild.equals(that.appBuild)) {
                    return false;
                }
            } else if (that.appBuild != null) {
                return false;
            }

            label40:
            {
                if (this.packageId != null) {
                    if (this.packageId.equals(that.packageId)) {
                        break label40;
                    }
                } else if (that.packageId == null) {
                    break label40;
                }

                return false;
            }

            if (this.versionCode != that.versionCode) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.appName != null ? this.appName.hashCode() : 0;
        result = 31 * result + (this.appVersion != null ? this.appVersion.hashCode() : 0);
        result = 31 * result + (this.appBuild != null ? this.appBuild.hashCode() : 0);
        result = 31 * result + (this.packageId != null ? this.packageId.hashCode() : 0);
        return result;
    }

    public boolean isAppUpgrade(ApplicationInformation that) {
        boolean brc = false;
        if (that.versionCode == -1) {
            brc = this.versionCode >= 0 && that.appVersion != null;
        } else {
            brc = this.versionCode > that.versionCode;
        }

        return brc;
    }
}
