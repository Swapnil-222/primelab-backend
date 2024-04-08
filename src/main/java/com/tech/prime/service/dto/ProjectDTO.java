package com.tech.prime.service.dto;

import com.tech.prime.domain.enumeration.Category;
import com.tech.prime.domain.enumeration.Department;
import com.tech.prime.domain.enumeration.Division;
import com.tech.prime.domain.enumeration.Priority;
import com.tech.prime.domain.enumeration.Reason;
import com.tech.prime.domain.enumeration.Type;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.tech.prime.domain.Project} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectDTO implements Serializable {

    private Long id;

    private String projectName;

    private Reason reason;

    private Type type;

    private Division division;

    private Category category;

    private Priority priority;

    private Department department;

    private Instant startDate;

    private Instant endDate;

    private String location;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
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
