package ru.stqa.pft.mantis.model;

public class BagifyIssue {

    private int id;
    private String subject;
    private String description;

    public int getId() {
        return id;
    }

    public BagifyIssue withId(int id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public BagifyIssue withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BagifyIssue withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BagifyIssue that = (BagifyIssue) o;

        if (id != that.id) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
