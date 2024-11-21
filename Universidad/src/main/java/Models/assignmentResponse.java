/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author nacho
 */
public class assignmentResponse {
    private int id;
    private int assignmentId;
    private String nrcGroup;
    private String cedStudent;
    private String url;
    private LocalDateTime submittedAt;
    private Float grade;
    private String comment;

    public assignmentResponse() {
    }

    public assignmentResponse(int id, int assignmentId, String nrcGroup, String cedStudent, String url, LocalDateTime submittedAt, Float grade, String comment) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.nrcGroup = nrcGroup;
        this.cedStudent = cedStudent;
        this.url = url;
        this.submittedAt = submittedAt;
        this.grade = grade;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getNrcGroup() {
        return nrcGroup;
    }

    public void setNrcGroup(String nrcGroup) {
        this.nrcGroup = nrcGroup;
    }

    public String getCedStudent() {
        return cedStudent;
    }

    public void setCedStudent(String cedStudent) {
        this.cedStudent = cedStudent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    

}
