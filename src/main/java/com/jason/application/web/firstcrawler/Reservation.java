package com.jason.application.web.firstcrawler;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"time",
"ampm",
"week",
"day",
"userDetails"
})
public class Reservation {

@JsonProperty("time")
private String time;
@JsonProperty("ampm")
private String ampm;
@JsonProperty("week")
private String week;
@JsonProperty("day")
private String day;
@JsonProperty("userDetails")
private UserDetails userDetails;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("time")
public String getTime() {
return time;
}

@JsonProperty("time")
public void setTime(String time) {
this.time = time;
}

@JsonProperty("ampm")
public String getAmpm() {
return ampm;
}

@JsonProperty("ampm")
public void setAmpm(String ampm) {
this.ampm = ampm;
}

@JsonProperty("week")
public String getWeek() {
return week;
}

@JsonProperty("week")
public void setWeek(String week) {
this.week = week;
}

@JsonProperty("day")
public String getDay() {
return day;
}

@JsonProperty("day")
public void setDay(String day) {
this.day = day;
}

@JsonProperty("userDetails")
public UserDetails getUserDetails() {
return userDetails;
}

@JsonProperty("userDetails")
public void setUserDetails(UserDetails userDetails) {
this.userDetails = userDetails;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}