package xyz.yuzhen.springsecurity.entity;

public class Menu {
    private Long id;
    private String name;
    private String url;
    private Long parentId;
    private String permission;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getPermission() {
        return permission;
    }
}