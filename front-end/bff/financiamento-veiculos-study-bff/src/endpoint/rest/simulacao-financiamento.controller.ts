import {SimulacaoFinanciamentoGateway}
  from '../../external/gateway/simulacao-financiamento.gateway';
import {Router} from 'express';
import {Response} from '../../external/rest/client/entities/response.model';

export const simulacaoFinanciamentoController = (router: Router) => {
  const url = '/api/financiamento';

  /**
   * @swagger
   *
   * /simular:
   *   post:
   *     summary: Obtém as parcelas do financiamento.
   *     description: Obtém as parcelas e valores do financiamento.
   *     parameters:
   *       - in: body
   *         name: simulacaoFinanciamentoRequest
   *         description: simulacaoFinanciamentoRequest
   *         type: object
   *         required: true
   *         example:
   *           clienteRequest:
   *             cpf: string
   *             dataNascimento: string
   *             nome: string
   *             possuiImovel: true
   *             renda: 0
   *             telefone: string
   *           simulacaoRequest:
   *             valorEntrada: 0
   *             valorVeiculo: 0
   *     responses:
   *       200:
   *         description: OK
   *         schema:
   *           type: object
   *           properties:
   *             data:
   *               $ref: '#/definitions/ParcelaList'
   *             messages:
   *               type: object
   *       201:
   *         description: Created
   *       400:
   *         description: Bad Request
   *       401:
   *         description: Forbidden
   *       403:
   *         description: Bad Request
   *       404:
   *         description: Not Found
   *       422:
   *         description: Business Error
   *       500:
   *         description: Internal Server Error
   */
  router.post(`${url}/simular`,
      (req: any, res: any) => {
        const simulacaoFinanciamentoGateway =
        new SimulacaoFinanciamentoGateway();

        simulacaoFinanciamentoGateway.simular(req)
            .then((response: Response) => {
              res.status(response.status);
              res.json({
                data: response.data,
                messages: response.message,
              });
            });
      });
};
