package com.tech.prime.service.criteria;

import com.tech.prime.domain.enumeration.Category;
import com.tech.prime.domain.enumeration.Department;
import com.tech.prime.domain.enumeration.Division;
import com.tech.prime.domain.enumeration.Priority;
import com.tech.prime.domain.enumeration.Reason;
import com.tech.prime.domain.enumeration.Type;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.tech.prime.domain.Project} entity. This class is used
 * in {@link com.tech.prime.web.rest.ProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Reason
     */
    public static class ReasonFilter extends Filter<Reason> {

        public ReasonFilter() {}

        public ReasonFilter(ReasonFilter filter) {
            super(filter);
        }

        @Override
        public ReasonFilter copy() {
            return new ReasonFilter(this);
        }
    }

    /**
     * Class for filtering Type
     */
    public static class TypeFilter extends Filter<Type> {

        public TypeFilter() {}

        public TypeFilter(TypeFilter filter) {
            super(filter);
        }

        @Override
        public TypeFilter copy() {
            return new TypeFilter(this);
        }
    }

    /**
     * Class for filtering Division
     */
    public static class DivisionFilter extends Filter<Division> {

        public DivisionFilter() {}

        public DivisionFilter(DivisionFilter filter) {
            super(filter);
        }

        @Override
        public DivisionFilter copy() {
            return new DivisionFilter(this);
        }
    }

    /**
     * Class for filtering Category
     */
    public static class CategoryFilter extends Filter<Category> {

        public CategoryFilter() {}

        public CategoryFilter(CategoryFilter filter) {
            super(filter);
        }

        @Override
        public CategoryFilter copy() {
            return new CategoryFilter(this);
        }
    }

    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {

        public PriorityFilter() {}

        public PriorityFilter(PriorityFilter filter) {
            super(filter);
        }

        @Override
        public PriorityFilter copy() {
            return new PriorityFilter(this);
        }
    }

    /**
     * Class for filtering Department
     */
    public static class DepartmentFilter extends Filter<Department> {

        public DepartmentFilter() {}

        public DepartmentFilter(DepartmentFilter filter) {
            super(filter);
        }

        @Override
        public DepartmentFilter copy() {
            return new DepartmentFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter projectName;

    private ReasonFilter reason;

    private TypeFilter type;

    private DivisionFilter division;

    private CategoryFilter category;

    private PriorityFilter priority;

    private DepartmentFilter department;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter location;

    private StringFilter status;

    private Boolean distinct;

    public ProjectCriteria() {}

    public ProjectCriteria(ProjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.projectName = other.projectName == null ? null : other.projectName.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.division = other.division == null ? null : other.division.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectCriteria copy() {
        return new ProjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProjectName() {
        return projectName;
    }

    public StringFilter projectName() {
        if (projectName == null) {
            projectName = new StringFilter();
        }
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public ReasonFilter getReason() {
        return reason;
    }

    public ReasonFilter reason() {
        if (reason == null) {
            reason = new ReasonFilter();
        }
        return reason;
    }

    public void setReason(ReasonFilter reason) {
        this.reason = reason;
    }

    public TypeFilter getType() {
        return type;
    }

    public TypeFilter type() {
        if (type == null) {
            type = new TypeFilter();
        }
        return type;
    }

    public void setType(TypeFilter type) {
        this.type = type;
    }

    public DivisionFilter getDivision() {
        return division;
    }

    public DivisionFilter division() {
        if (division == null) {
            division = new DivisionFilter();
        }
        return division;
    }

    public void setDivision(DivisionFilter division) {
        this.division = division;
    }

    public CategoryFilter getCategory() {
        return category;
    }

    public CategoryFilter category() {
        if (category == null) {
            category = new CategoryFilter();
        }
        return category;
    }

    public void setCategory(CategoryFilter category) {
        this.category = category;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public PriorityFilter priority() {
        if (priority == null) {
            priority = new PriorityFilter();
        }
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public DepartmentFilter getDepartment() {
        return department;
    }

    public DepartmentFilter department() {
        if (department == null) {
            department = new DepartmentFilter();
        }
        return department;
    }

    public void setDepartment(DepartmentFilter department) {
        this.department = department;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCriteria that = (ProjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(projectName, that.projectName) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(type, that.type) &&
            Objects.equals(division, that.division) &&
            Objects.equals(category, that.category) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(department, that.department) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(location, that.location) &&
            Objects.equals(status, that.status) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            projectName,
            reason,
            type,
            division,
            category,
            priority,
            department,
            startDate,
            endDate,
            location,
            status,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (projectName != null ? "projectName=" + projectName + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (division != null ? "division=" + division + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (department != null ? "department=" + department + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
