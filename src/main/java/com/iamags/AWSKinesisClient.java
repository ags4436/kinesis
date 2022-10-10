package com.iamags;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

public class AWSKinesisClient {

    private static final String AWS_ACCESS_KEY = "aws.accessKeyId";
    private static final String AWS_SCRECT_KEY = "aws.secretKey";

    static{
        System.setProperty(AWS_ACCESS_KEY,"");
        System.setProperty(AWS_SCRECT_KEY,"");
    }

    public static AmazonKinesis getKinesisClient(){
        return AmazonKinesisClientBuilder.standard()
                .withRegion(Regions.DEFAULT_REGION)
                .build();
    }

}
