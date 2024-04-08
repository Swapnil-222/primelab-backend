package com.tech.prime.domain;

import com.tech.prime.domain.enumeration.Category;
import com.tech.prime.domain.enumeration.Department;
import com.tech.prime.domain.enumeration.Division;
import com.tech.prime.domain.enumeration.Priority;
import com.tech.prime.domain.enumeration.Reason;
import com.tech.prime.domain.enumeration.Type;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private Reason reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "division")
    private Division division;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public Project projectName(String projectName) {
        this.setProjectName(projectName);
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Reason getReason() {
        return this.reason;
    }

    public Project reason(Reason reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Type getType() {
        return this.type;
    }

    public Project type(Type type) {
        this.setType(type);
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Division getDivision() {
        return this.division;
    }

    public Project division(Division division) {
        this.setDivision(division);
        return this;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Category getCategory() {
        return this.category;
    }

    public Project category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Project priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Project department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Project startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Project endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return this.location;
    }

    public Project location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return this.status;
    }

    public Project status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", reason='" + getReason() + "'" +
            ", type='" + getType() + "'" +
            ", division='" + getDivision() + "'" +
            ", category='" + getCategory() + "'" +
            ", priority='" + getPriority() + "'" +
            ", department='" + getDepartment() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", location='" + getLocation() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
