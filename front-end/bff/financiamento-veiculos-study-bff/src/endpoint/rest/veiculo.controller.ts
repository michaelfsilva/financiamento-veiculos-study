import {Router, Request} from 'express';
import {TabelaFipeGateway} from '../../external/gateway/tabela-fipe.gateway';

export const veiculoController = (router: Router) => {
  const url = '/api/veiculos';

  router.get(url, (req: any, res: any) => {
    res.json({
      status: 'API is Working',
      message: 'Welcome to BFF!',
    });
  });

  /**
   * @swagger
   *
   * /marcas:
   *   get:
   *     summary: Obtém as marcas dos veiculos.
   *     description: Obtém as marcas dos veiculos da tabela fipe.
   *     responses:
   *       200:
   *         description: OK
   *         schema:
   *           type: object
   *           properties:
   *             data:
   *               $ref: '#/definitions/Marca'
   *             messages:
   *               type: object
   *       400:
   *         description: Bad Request
   *       422:
   *         description: Business Error
   *       500:
   *         description: Internal Server Error
   */
  router.get(
      `${url}/marcas`, (req: Request, res: any) => {
        const tabelaFipeGateway = new TabelaFipeGateway();

        tabelaFipeGateway.getMarcas().then((marcas) => {
          res.status(200);
          res.json({
            data: marcas,
            messages: '',
          });
        });
      },
  );

  /**
   * @swagger
   *
   * /modelos/:marcaId:
   *   get:
   *     summary: Obtém as modelos dos veiculos.
   *     description: Obtém os modelos dos veiculos da tabela fipe.
   *     parameters:
   *       - in: url
   *         name: marcaId
   *         type: int
   *         required: true
   *     responses:
   *       200:
   *         description: OK
   *         schema:
   *           type: object
   *           properties:
   *             data:
   *               $ref: '#/definitions/Modelo'
   *             messages:
   *               type: object
   *       400:
   *         description: Bad Request
   *       422:
   *         description: Business Error
   *       500:
   *         description: Internal Server Error
   *
   */
  router.get(
      `${url}/modelos/:marcaId`,
      (req: Request, res: any) => {
        const tabelaFipeGateway = new TabelaFipeGateway();

        tabelaFipeGateway.getModelosByMarcaId(parseInt(req.params.marcaId))
            .then((modelos) => {
              res.status(200);
              res.json({
                data: modelos,
                messages: '',
              });
            });
      },
  );

  /**
   * @swagger
   *
   * /modelos/:marcaId/:modeloId:
   *   get:
   *     summary: Obtém os dados de um modelo especifico de um veiculo.
   *     description: Obtém os dados de um modelo de um veiculo da tabela fipe.
   *     parameters:
   *       - in: url
   *         name: marcaId
   *         type: int
   *         required: true
   *       - in: url
   *         name: modeloId
   *         type: int
   *         required: true
   *     responses:
   *       200:
   *         description: OK
   *         schema:
   *           type: object
   *           properties:
   *             data:
   *               $ref: '#/definitions/Ano'
   *             messages:
   *               type: object
   *       400:
   *         description: Bad Request
   *       422:
   *         description: Business Error
   *       500:
   *         description: Internal Server Error
   */
  router.get(`${url}/modelos/:marcaId/:modeloId`, (req: any, res: any) => {
    const tabelaFipeGateway = new TabelaFipeGateway();

    tabelaFipeGateway
        .getAnosByMarcaIdAndModeloId(req.params.marcaId, req.params.modeloId)
        .then((anos) => {
          res.status(200);
          res.json({
            data: anos,
            messages: '',
          });
        });
  });

  /**
   * @swagger
   *
   * /modelos/:marcaId/:modeloId/:ano:
   *   get:
   *     summary: Obtém os dados de um modelo especifico de um veiculo pelo ano dele.
   *     description: Obtém os dados de um modelo de um veiculo filtrando pelo ano diretamente da tabela fipe.
   *     parameters:
   *       - in: url
   *         name: marcaId
   *         type: int
   *         required: true
   *       - in: url
   *         name: modeloId
   *         type: int
   *         required: true
   *       - in: url
   *         name: ano
   *         type: int
   *         required: true
   *     responses:
   *       200:
   *         description: OK
   *         schema:
   *           type: object
   *           properties:
   *             data:
   *               $ref: '#/definitions/TabelaFipe'
   *             messages:
   *               type: object
   *       400:
   *         description: Bad Request
   *       422:
   *         description: Business Error
   *       500:
   *         description: Internal Server Error
   */
  router.get(`${url}/modelos/:marcaId/:modeloId/:ano`,
      (req: any, res: any) => {
        const tabelaFipeGateway = new TabelaFipeGateway();

        tabelaFipeGateway
            .getTabelaFipeByMarcaIdAndModeloIdAndAno(
                req.params.marcaId, req.params.modeloId, req.params.ano)
            .then((tabelaFipe) => {
              res.status(200);
              res.json({
                data: tabelaFipe,
                messages: '',
              });
            });
      });
};
