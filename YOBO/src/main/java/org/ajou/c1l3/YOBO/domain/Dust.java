package org.ajou.c1l3.YOBO.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection="dust")
public class Dust{
    private String _id;
    private String user_id;
    private Room[] Rooms;

    @Data
    public static class Room{
        private String RoomName;
        private String temp;
        private String dust;
        private String humidity;
    }

}

