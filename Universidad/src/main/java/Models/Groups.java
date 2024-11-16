/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author nacho
 */
public class Groups {
    private String nrc;
    private int group_number;
    private String ced_professor;
    private String code_course;

    public Groups(String nrc, int group_number, String ced_professor, String code_course) {
        this.nrc = nrc;
        this.group_number = group_number;
        this.ced_professor = ced_professor;
        this.code_course = code_course;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public int getGroup_number() {
        return group_number;
    }

    public void setGroup_number(int group_number) {
        this.group_number = group_number;
    }

    public String getCed_professor() {
        return ced_professor;
    }

    public void setCed_professor(String ced_professor) {
        this.ced_professor = ced_professor;
    }

    public String getCode_course() {
        return code_course;
    }

    public void setCode_course(String code_course) {
        this.code_course = code_course;
    }
    
}
