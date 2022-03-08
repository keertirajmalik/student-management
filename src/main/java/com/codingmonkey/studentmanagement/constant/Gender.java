package com.codingmonkey.studentmanagement.constant;

public enum Gender {
  MALE("Male"), FEMALE("Female");

  private final String genderType;

  Gender(final String genderType) {
    this.genderType = genderType;
  }

  String getGenderType() {
    return genderType;
  }
}
