package ru.stqa.pft.addressbook.model;

public class GroupData {
    private String groupName;
    private String groupHeader;
    private String groupFooter;
    private int id = Integer.MAX_VALUE;

    public String getGroupName() {
        return groupName;
    }

    public GroupData withName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getGroupHeader() {
        return groupHeader;
    }

    public GroupData withHeader(String groupHeader) {
        this.groupHeader = groupHeader;
        return this;
    }

    public String getGroupFooter() {
        return groupFooter;
    }

    public GroupData withFooter(String groupFooter) {
        this.groupFooter = groupFooter;
        return this;
    }

    public int getId() {
        return id;
    }

    public GroupData withId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "groupName='" + groupName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupData groupData = (GroupData) o;

        return groupName != null ? groupName.equals(groupData.groupName) : groupData.groupName == null;
    }

    @Override
    public int hashCode() {
        return groupName != null ? groupName.hashCode() : 0;
    }
}
