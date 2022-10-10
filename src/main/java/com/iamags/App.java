package com.iamags;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/** Hello world! */
public class App {

  List<String> productList = new ArrayList<>();
    Random random = new Random();
  public static void main(String[] args) {

    App app = new App();
    app.populateProduct();
    System.out.println("Hello World!");

    // 1. get Client
    AmazonKinesis kinesisClient = AWSKinesisClient.getKinesisClient();

      // 2. PutRecordRequest
    List<PutRecordsRequestEntry> requestEntryList = app.getRecordsRequestList();

    PutRecordsRequest recordsRequest = new PutRecordsRequest();
    recordsRequest.setStreamName("Order_stream");
    recordsRequest.setRecords(requestEntryList);
      // 3. putRecord or PutReccords
    PutRecordsResult result =  kinesisClient.putRecords(recordsRequest);

  }

  private List<PutRecordsRequestEntry> getRecordsRequestList(){
   Gson gson = new GsonBuilder().setPrettyPrinting().create();
   List<PutRecordsRequestEntry> putRecordsRequestEntries = new ArrayList<>();
   for(Order order: getOrderList()){
      PutRecordsRequestEntry requestEntry = new PutRecordsRequestEntry();
      requestEntry.setData(ByteBuffer.wrap(gson.toJson(order).getBytes()));
      requestEntry.setPartitionKey(UUID.randomUUID().toString());
      putRecordsRequestEntries.add((requestEntry));
   }
   return putRecordsRequestEntries;
  }

  private  List<Order> getOrderList(){
      List<Order> orders = new ArrayList<>();
      for(int i=0;i<500;i++){
          Order order = new Order();
          order.setOrderId(random.nextInt());
          order.setProduct(productList.get(random.nextInt(productList.size())));
          order.setQuantity(random.nextInt(20));
          orders.add(order);
      }
      return orders;
  }

  private void populateProduct(){
      productList.add("Shirt");
      productList.add("Pant");
      productList.add("T-Shirt");
      productList.add("Shoes");
      productList.add("Jeans");
      productList.add("Belt");
  }
}
