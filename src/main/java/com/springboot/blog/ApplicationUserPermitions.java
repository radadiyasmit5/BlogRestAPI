package com.springboot.blog;

public enum ApplicationUserPermitions {

    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
   COURSE_READ("course:read"),
    COURSE_WRITE("course:read");

   private final String permissions;

    ApplicationUserPermitions(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }
}
