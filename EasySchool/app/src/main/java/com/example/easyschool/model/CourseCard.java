/*
 * Copyright (c) 2020. rogergcc
 */

package com.example.easyschool.model;

public class CourseCard {

    private int Id;
    private int imageCourse;
    private String courseTitle;
    private String quantityCourses;
    private String file_type ,file_id;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public CourseCard(int id, int imageCourse, String courseTitle, String quantityCourses) {
        Id = id;
        this.imageCourse = imageCourse;
        this.courseTitle = courseTitle;
        this.quantityCourses = quantityCourses;
    }

    public CourseCard(int imageCourse, String courseTitle, String quantityCourses) {
        this.imageCourse = imageCourse;
        this.courseTitle = courseTitle;
        this.quantityCourses = quantityCourses;
    }

    public void setImageCourse(int imageCourse) {
        this.imageCourse = imageCourse;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setQuantityCourses(String quantityCourses) {
        this.quantityCourses = quantityCourses;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImageCourse() {
        return imageCourse;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getQuantityCourses() {
        return quantityCourses;
    }

    @Override()
    public boolean equals(Object other) {
        // This is unavoidable, since equals() must accept an Object and not something more derived
        if (other instanceof CourseCard) {
            // Note that I use equals() here too, otherwise, again, we will check for referential equality.
            // Using equals() here allows the Model class to implement it's own version of equality, rather than
            // us always checking for referential equality.
            CourseCard courseCard = (CourseCard) other;
            return courseCard.getId()==(this.getId());
        }

        return false;
    }
}
