/**
 * Parcela Model class
 * 
 * @swagger
 * definitions:
 *   Parcela:
 *     properties:
 *       meses:
 *         type: integer
 *       valor:
 *         type: integer
 */
export class Parcela {
  meses: number;
  valor: number;

  /**
   * Class constructor
   *
   * @param {number} meses
   * @param {number} valor
   */
  constructor(meses: number, valor: number) {
    this.meses = meses;
    this.valor = valor;
  }
}
