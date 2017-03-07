package com.amazonaws.codesamples;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class MusicMapperDemo {

    public static void main(String[] args) {

        AmazonDynamoDBClient client = new AmazonDynamoDBClient();

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        
        MusicItem keySchema = new MusicItem();
        keySchema.setArtist("No One You Know");
        keySchema.setSongTitle("Call Me Today");

        try {
            MusicItem result = mapper.load(keySchema);
            if (result != null) {
                System.out.println(
                "The song was released in "+ result.getYear());
            } else {
                System.out.println("No matching song was found");
            }
        } catch (Exception e) {
            System.err.println("Unable to retrieve data: ");
            System.err.println(e.getMessage());
        }
       
    }
    
} 