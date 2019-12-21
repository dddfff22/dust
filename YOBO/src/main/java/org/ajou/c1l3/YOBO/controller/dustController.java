package org.ajou.c1l3.YOBO.controller;

import org.ajou.c1l3.YOBO.domain.Dust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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
        System.out.println(dust);

        Dust user=mongoTemplate.insert(dust);
        return user;
    }


}
