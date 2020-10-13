import axios from 'axios';
import path from 'path';
import {env} from '../../../env';

/**
 * Client to send requests to back end
 */
export class SimulacaoFinanciamentoClient {
  /**
  * Send a post request to back end microservice
  *
  * @param {any} simulacaoFinanciamentoRequest
  */
  async simular(simulacaoFinanciamentoRequest: any) {
    const lib = path.join(path.dirname(require.resolve('axios')),
        'lib/adapters/http');
    const http = require(lib);

    const url = env.isDevelopment ? 'localhost:4080' : 'microservice:8080';

    return await axios.post(`http://${url}/v1/simular`, simulacaoFinanciamentoRequest.body, {
      adapter: http,
      auth: {
        username: 'financiamento',
        password: 'veiculos',
      },
    });
  }
}
