package com.ai.runner.apicollector.doclet;

import org.elasticsearch.common.lang3.StringUtils;

public class DocletOptions {

    // 服务提供者
    private String owner;

    // 服务提供者类型
    private String ownerType;

    // 服务版本
    private String version;

    // 服务坐标分组标识
    private String groupId;

    // 服务构件标识
    private String artifactId;

    private String esconfig;

    // Base模块专用的，适用类型
    private String belong;

    public static int getOptionLength(String option) {
        if ("-owner".equals(option)) {
            return 2;
        } else if ("-ownerType".equals(option)) {
            return 2;
        } else if ("-version".equals(option)) {
            return 2;
        } else if ("-groupId".equals(option)) {
            return 2;
        } else if ("-artifactId".equals(option)) {
            return 2;
        } else if ("-esconfig".equals(option)) {
            return 2;
        } else if ("-belong".equals(option)) {
            return 2;
        } else {
            return 0;
        }
    }

    public DocletOptions(String[][] options) {
        for (final String[] pair : options) {
            if ("-owner".equals(pair[0])) {
                this.owner = pair[1];
            }
            if ("-ownerType".equals(pair[0])) {
                this.ownerType = pair[1];
            }
            if ("-version".equals(pair[0])) {
                this.version = pair[1];
            }
            if ("-groupId".equals(pair[0])) {
                this.groupId = pair[1];
            }
            if ("-artifactId".equals(pair[0])) {
                this.artifactId = pair[1];
            }
            if ("-esconfig".equals(pair[0])) {
                this.esconfig = pair[1];
            }
            if ("-belong".equals(pair[0])) {
                this.belong = pair[1];
            }
        }

        if (StringUtils.isBlank(owner)) {
            throw new IllegalArgumentException("-owner option can not be empty");
        }
        if (StringUtils.isBlank(ownerType)) {
            throw new IllegalArgumentException("-ownerType option can not be empty");
        }
        if (StringUtils.isBlank(version)) {
            throw new IllegalArgumentException("-version option can not be empty");
        }
        if (StringUtils.isBlank(groupId)) {
            throw new IllegalArgumentException("-groupId option can not be empty");
        }
        if (StringUtils.isBlank(artifactId)) {
            throw new IllegalArgumentException("-artifactId option can not be empty");
        }
        if (StringUtils.isBlank(esconfig)) {
            throw new IllegalArgumentException("-esconfig option can not be empty");
        }
        if (StringUtils.isBlank(belong)) {
            throw new IllegalArgumentException(
                    "-belong option can not be empty.you can select 'opt-runner or opt-mvne'");
        }
    }

    public String getEsconfig() {
        return esconfig;
    }

    public void setEsconfig(String esconfig) {
        this.esconfig = esconfig;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

}
