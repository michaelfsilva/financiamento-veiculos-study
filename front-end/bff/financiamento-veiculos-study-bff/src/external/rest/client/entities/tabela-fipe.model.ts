/**
 * Tabela Fipe Model class
 * 
 * @swagger
 * definitions:
 *   TabelaFipe:
 *     properties:
 *       marca:
 *         type: string
 *       veiculo:
 *         type: string
 *       anoModelo:
 *         type: integer
 *       preco:
 *         type: string
 */
export class TabelaFipe {
  marca: string;
  veiculo: string;
  anoModelo: number;
  preco: string;

  /**
   * Class constructor
   *
   * @param {string} marca
   * @param {string} veiculo
   * @param {number} anoModelo
   * @param {string} preco
   */
  constructor(marca: string,
      veiculo: string,
      anoModelo: number,
      preco: string) {
    this.marca = marca;
    this.veiculo = veiculo;
    this.anoModelo = anoModelo;
    this.preco = preco;
  }
}
