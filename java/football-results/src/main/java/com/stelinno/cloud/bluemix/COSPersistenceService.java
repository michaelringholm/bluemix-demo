package com.stelinno.cloud.bluemix;

import java.util.List;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class COSPersistenceService implements ObjectPersistenceService {

	private AmazonS3 s3;
	public COSPersistenceService() {
    	//  AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
    	System.getProperties().put("aws.accessKeyId", "qjyoMGLerasoY3uZpbL9");
    	System.getProperties().put("aws.secretKey", "AoKzCG9HRJGV9iFfEe8Qcl9lRldkRsWw5tz5Iy3S");

    	String endpoint = "s3.eu-geo.objectstorage.softlayer.net";
    	s3 = AmazonS3Client.builder().withEndpointConfiguration(new EndpointConfiguration(endpoint, "eu-geo")).build();
	}
	
	public Bucket createBucket(String bucketName) {
		System.out.println("Creating bucket " + bucketName + "\n");
		return s3.createBucket(bucketName);
	}
	
	public List<Bucket> listBuckets() {
        return s3.listBuckets();
	}
	
	public void putObject() {
        //s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
	}
}
