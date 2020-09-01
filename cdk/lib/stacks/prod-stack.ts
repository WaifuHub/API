import * as cdk from '@aws-cdk/core';
import * as ecs from '@aws-cdk/aws-ecs';
import * as dynamodb from '@aws-cdk/aws-dynamodb';
import * as s3 from '@aws-cdk/aws-s3'
import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecs_patterns from '@aws-cdk/aws-ecs-patterns';

export class ProdStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const userTable = new dynamodb.Table(this, 'UserTable', {
      partitionKey: { name: 'id', type: dynamodb.AttributeType.STRING }
    });

    const hubTable = new dynamodb.Table(this, 'HubTable', {
      partitionKey: { name: 'id', type: dynamodb.AttributeType.STRING }
    });

    const commentTable = new dynamodb.Table(this, 'CommentTable', {
      partitionKey: { name: 'id', type: dynamodb.AttributeType.STRING }
    });

    const imageBucket = new s3.Bucket(this, 'ImageBucket');

    const vpc = new ec2.Vpc(this, 'WhubApiVpc', { maxAzs: 2 });

    const cluster = new ecs.Cluster(this, 'WhubApiCluster', {
      vpc,
    });

    cluster.addCapacity('WhubApiAutoScaleCapacity', {
      instanceType: new ec2.InstanceType("t2.micro"),
      desiredCapacity: 1,
    });

    const fargateService = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "WhubApiFargate", {
      cluster,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry('apol12/whub_api'),
        containerPort: 8080,
        environment: {
          DEPLOYED_DATE: Date.now().toLocaleString()
        }
      },
      desiredCount: 1
    });

    new cdk.CfnOutput(this, 'WhubApiLoadBalancerDNS', { value: fargateService.loadBalancer.loadBalancerDnsName });
  }
}