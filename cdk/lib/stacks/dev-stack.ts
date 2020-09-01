import * as cdk from '@aws-cdk/core';
import * as dynamodb from '@aws-cdk/aws-dynamodb';
import * as s3 from '@aws-cdk/aws-s3'

export class DevStack extends cdk.Stack {
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
  }
}
