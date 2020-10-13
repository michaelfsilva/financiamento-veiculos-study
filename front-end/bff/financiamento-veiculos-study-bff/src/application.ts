import express from 'express';
import bodyParser from 'body-parser';
import basicAuth from 'express-basic-auth';
import {simulacaoFinanciamentoController, veiculoController} from './endpoint';

/**
 * BFF Application
 */
class App {
  public express: express.Express;
  public cors = require('cors');
  private readonly corsPolicy = '*';
  public swaggerUi = require('swagger-ui-express');
  public swaggerSpec = require('./config/swagger');

  /**
   * Application constructor
   */
  constructor() {
    this.express = express();
    this.middleware();
    this.routes();
    this.swaggerSetup();
    this.start();
  }

  /**
   * Start the application
   */
  public start(): void {
    const port = 8000;
    this.express.set('port', port);

    this.express
        .listen(port, () => {
          const startupMsg = `BFF listening on port ${port}`;
          console.log(startupMsg);
        })
        .on('error', (error: ServerException) => {
          if (error.errno === 'EADDRINUSE') {
            console.log(
                `Porta ${error.port} já está em uso por outro processo.`,
            );
          } else {
            console.log(error.message);
          }
        });

    this.express.get('/', (req, res) => res.send('Welcome!'));
  }

  /**
   * Set communication params
   */
  private middleware(): void {
    console.log(`Configuring CORS with policy: ${this.corsPolicy}`);
    this.express.use(this.cors({origin: this.corsPolicy}));

    console.log('Configuring Middlewares: BodyParser');
    this.express.use(bodyParser.json());
    this.express.use(bodyParser.urlencoded({extended: false}));
  }

  /**
   * Set app routes
   */
  private routes(): void {
    this.express.use(basicAuth({
      authorizer: (username, password, cb) => {
        return cb(null, basicAuth.safeCompare(username, 'financiamento') &&
                        basicAuth.safeCompare(password, 'veiculos'));
      },
      authorizeAsync: true,
      challenge: true,
      realm: 'financiamentoveiculos',
    }));

    const router = express.Router();

    simulacaoFinanciamentoController(router);
    veiculoController(router);
    this.express.use(router);
  }

  /**
   * Setup Swagger
   */
  private swaggerSetup(): void {
    console.log('Configuring SwaggerUi: SwaggerUi and SwaggerSpec');

    const options = {
      customCss: '.swagger-ui .topbar { display: none }',
    };

    this.express.use('/swagger',
        this.swaggerUi.serve,
        this.swaggerUi.setup(this.swaggerSpec.getSwaggerJSDoc(), options));

    this.express.get('/swagger.json', (req, res) => {
      res.setHeader('Content-Type', 'application/json');
      res.send(this.swaggerSpec.getSwaggerJSDoc());
    });
  }
}

export interface ServerException extends Error {
  errno?: string;
  code?: string;
  port?: number;
}

export default new App();
