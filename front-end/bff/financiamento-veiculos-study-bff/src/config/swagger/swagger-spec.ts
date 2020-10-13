import swaggerJSDoc from 'swagger-jsdoc';
import {description, name, version} from '../../../package.json';
import {env} from '../../env';

/**
 * Swagger Setup
 *
 * @return {string} swagger-jsdoc
 */
export function getSwaggerJSDoc(): string {
  const swaggerDefinition = {
    info: {
      title: `Swagger UI for ${name} project.`,
      version: version,
      description: description,
    },
    produces: ['application/json'],
    consumes: ['application/json'],
    customCss: '.swagger-ui .topbar { display: none }',
    host: `localhost:${env.app.port}`,
    basePath: '/',
  };

  // options for the swagger docs
  const options = {
    // import swaggerDefinitions
    swaggerDefinition: swaggerDefinition,
    // path to the API docs
    apis: ['**/*.ts'],
  };

  // initialize swagger-jsdoc
  return swaggerJSDoc(options);
}
