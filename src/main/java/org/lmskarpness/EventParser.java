package org.lmskarpness;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventParser {

    private List<Event> events;

    public EventParser(String json) {
        this.events = generateEventsFromJSON(json);
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public String getEventDetails(Event event) {
        return " - " + getEventTypeString(event.type) + " to " + event.repo.name;

    }

    private List<Event> generateEventsFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Event> events = mapper.readValue(
                    json,
                    mapper.getTypeFactory().constructCollectionType(List.class, Event.class)
            );
            return events;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private String getEventTypeString(String type) {
        String[] split_event_type = type.split("(?=\\p{Upper})");
        // Pop off last element, "Event", by creating a smaller array, then return the joined string.
        String[] new_type = new String[split_event_type.length - 1];
        System.arraycopy(split_event_type, 0, new_type, 0, split_event_type.length - 1);
        return String.join(" ", new_type);
    }
}

// Represents an Event object
@JsonIgnoreProperties(ignoreUnknown = true)
class Event {
    public String id;
    public String type;
    public Actor actor;
    public Repo repo;
    public Payload payload;
    public boolean isPublic;
    public String created_at;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Actor {
    public int id;
    public String login;
    public String display_login;
    public String url;
    public String avatar_url;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Repo {
    public int id;
    public String name;
    public String url;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Payload {
    public String ref;
    public String ref_type;
    public String master_branch;
    public String description;
    public String pusher_type;
    public List<Commit> commits; // for PushEvent
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Commit {
    public String sha;
    public Author author;
    public String message;
    public boolean distinct;
    public String url;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Author {
    public String email;
    public String name;
}
