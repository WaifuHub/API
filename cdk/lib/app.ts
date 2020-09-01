#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { DevStack } from './stacks/dev-stack';
import { ProdStack } from './stacks/prod-stack';

const app = new cdk.App();
const isDev = process.env.IS_DEV;

if (isDev) {
    new DevStack(app, 'WhubApiDevStack');
} else {
    new ProdStack(app, 'WhubApiProdStack');
}