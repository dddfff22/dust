package org.ajou.c1l3.YOBO.controller;

import org.ajou.c1l3.YOBO.domain.Dust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@CrossOrigin
@RestController
public class dustController {
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/dust/getbyuser")
    public Dust getUserbyuser(@RequestParam("user") String user){
        System.out.println(user);
        Query query = query(where("user_id").is(user));

        query.with(new Sort(Sort.Direction.DESC,"timestamp"));
        List<Dust> dust=mongoTemplate.find(query, Dust.class);

        return  dust.get(0);
    }



    @PostMapping("/dust/addDustInfo")
    public Dust getUserbyDid(@RequestBody Dust dust){
        Query query = Query.query(where("user_id").is(dust.getUser_id()));
        try {
            mongoTemplate.remove(query, Dust.class);
        }catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        DateOperators.Timezone time;
        System.out.println(dust);
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        calendar.setTime(date);
        dust.setTimestamp(new Timestamp((date.getTime())));
        for(int i=0;i<dust.getRooms().length;i++){
            dust.getRooms()[i].setRoomId(UUID.randomUUID().toString());
            if( Float.parseFloat(dust.getRooms()[i].getDust())<1000){
                dust.getRooms()[i].setFlag(1);
            }else if( Float.parseFloat(dust.getRooms()[i].getDust())<2000){
                dust.getRooms()[i].setFlag(2);
            }else if( Float.parseFloat(dust.getRooms()[i].getDust())<3000){
                dust.getRooms()[i].setFlag(3);
            }else if( Float.parseFloat(dust.getRooms()[i].getDust())<4000){
                dust.getRooms()[i].setFlag(4);
            }else {
                dust.getRooms()[i].setFlag(5);
            }
        }
        Dust user=mongoTemplate.insert(dust);
        return user;
    }


}
