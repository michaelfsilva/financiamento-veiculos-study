import {SimulacaoFinanciamentoClient}
  from '../rest/client/simulacao-financiamento.client';

import {AxiosError, AxiosResponse} from 'axios';
import {Response} from '../rest/client/entities/response.model';
import {ParcelaList} from '../rest/client/entities/parcela-list.model';

/**
 * Middle class to request values to Simulacao Financiamento client class
 */
export class SimulacaoFinanciamentoGateway {
  client: SimulacaoFinanciamentoClient = new SimulacaoFinanciamentoClient();

  /**
   * Request a Simulacao
   *
   * @param {Response} simulacaoFinanciamentoRequest
   */
  async simular(simulacaoFinanciamentoRequest: any) {
    return await
    this.client.simular(simulacaoFinanciamentoRequest)
        .then((response: AxiosResponse) => {
          return new Response(response.status, '',
            response.data.data as ParcelaList);
        })
        .catch((error: AxiosError) => {
          console.log(error);

          let status;
          let message = 'Unable to request data from vehicle financing service';

          if (!error.response) {
            status = 404;
          } else {
            status = error.response.status;
            message = error.response.data.messages == '' ?
              message : error.response.data.messages;
          }

          return new Response(status, message, null);
        });
  }
}
