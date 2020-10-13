process.env.NODE_ENV;

import os from 'os';
import * as pkg from '../package.json';
import config from 'config';

/**
 * Environment Variables
 */
export const env = {
  node: process.env.NODE_ENV,
  isProduction: process.env.NODE_ENV === 'PR',
  isDevelopment:
    process.env.NODE_ENV === 'DEV' || process.env.NODE_ENV === 'CI',
  app: {
    name: pkg.name,
    version: pkg.version,
    description: pkg.description,
    host: os.hostname(),
    port: process.env.PORT || config.get('app.port'),
    rest_version: config.get('app.rest_version'),
  },
};
