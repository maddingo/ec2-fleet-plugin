package com.amazon.jenkins.ec2fleet;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.ActiveInstance;
import com.amazonaws.services.ec2.model.DescribeSpotFleetInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeSpotFleetInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSpotFleetRequestsResult;
import com.amazonaws.services.ec2.model.SpotFleetRequestConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assume.assumeThat;

public class FleetStatusWidgetTest {

    @BeforeClass
    public static void checkProfile() {
        String profile = System.getenv("AWS_PROFILE");
        assumeThat(profile, is(notNullValue()));
    }

    @Test
    public void testConnection() {

        AWSCredentialsProvider prov = new DefaultAWSCredentialsProviderChain();
        AmazonEC2 client = AmazonEC2ClientBuilder.standard()
            .withRegion("eu-west-1")
            .withCredentials(prov)
            .build();

        final DescribeSpotFleetRequestsResult describeSpotFleetRequestsResult = client.describeSpotFleetRequests();
        assumeThat(describeSpotFleetRequestsResult.getSpotFleetRequestConfigs(), is(not(empty())));
        final SpotFleetRequestConfig spotFleetRequestConfig = describeSpotFleetRequestsResult.getSpotFleetRequestConfigs().get(0);
        final String spotFleetRequestId = spotFleetRequestConfig.getSpotFleetRequestId();
        final DescribeSpotFleetInstancesResult result = client.describeSpotFleetInstances(
            new DescribeSpotFleetInstancesRequest().withSpotFleetRequestId(spotFleetRequestId)
        );

        for (ActiveInstance instance : result.getActiveInstances()) {
            System.out.println(instance.toString());
        }
    }
}
