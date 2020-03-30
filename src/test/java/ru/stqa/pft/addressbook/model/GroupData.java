package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("group")
public class GroupData {
    @Expose
    private String name;
    @Expose
    private String header;
    @Expose
    private String footer;
    @XStreamOmitField
    private int id = Integer.MAX_VALUE;

    public String getName() {
        return name;
    }

    public GroupData withName(String groupName) {
        this.name = groupName;
        return this;
    }

    public String getHeader() {
        return header;
    }

    public GroupData withHeader(String groupHeader) {
        this.header = groupHeader;
        return this;
    }

    public String getFooter() {
        return footer;
    }

    public GroupData withFooter(String groupFooter) {
        this.footer = groupFooter;
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
                "name='" + name + '\'' +
                ", header='" + header + '\'' +
                ", footer='" + footer + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupData groupData = (GroupData) o;

        if (id != groupData.id) return false;
        return name != null ? name.equals(groupData.name) : groupData.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
