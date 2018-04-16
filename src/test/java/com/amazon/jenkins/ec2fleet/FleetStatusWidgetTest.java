package com.amazon.jenkins.ec2fleet;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.ActiveInstance;
import com.amazonaws.services.ec2.model.DescribeSpotFleetInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeSpotFleetInstancesResult;
import org.junit.Test;

public class FleetStatusWidgetTest {

    @Test
    public void testConection() {
//        AWSCredentialsProvider prov = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAIDVU6EKZPDNNHNVQ", "9o6JqL/OwPxN86m+ZvBQgnL4KvH7mid4vaq2N7Pz"));
        AWSCredentialsProvider prov = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAIOHM2UWU3MWMP4DQ", "Q/6Uk7wGqon2TsIMsvQWE01Dlwe4uovracfdOvH+"));
        AmazonEC2 client = AmazonEC2ClientBuilder.standard()
            .withRegion("eu-west-1")
            .withCredentials(prov)
            .build();

        String fleetId = "sfr-c81b73d3-d9c9-4348-b2a0-e3b93dd40854";
        final DescribeSpotFleetInstancesResult result = client.describeSpotFleetInstances(
            new DescribeSpotFleetInstancesRequest().withSpotFleetRequestId(fleetId)
        );

        for (ActiveInstance instance : result.getActiveInstances()) {
            System.out.println(instance.toString());
        }
    }
}
