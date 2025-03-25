package com.example.c196mobiledevelopment.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Database table for instructor class.
 */
@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instructorId;
    private String name;
    private String email;
    private String phone;

    /**
     * Constructor for instructor class.
     */
    public Instructor(int instructorId, String name, String email, String phone) {
        this.instructorId = instructorId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * @return the instructorId.
     */
    public int getInstructorId() {
        return instructorId;
    }

    /**
     * @param instructorId sets the instructorId.
     */
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name sets the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email sets the email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone sets the phone.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}

