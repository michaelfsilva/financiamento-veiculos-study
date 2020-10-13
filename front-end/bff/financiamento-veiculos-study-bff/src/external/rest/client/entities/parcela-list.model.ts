import {Parcela} from './parcela.model';

/**
 * ParcelaList Model class
 * 
 * @swagger
 * definitions:
 *   ParcelaList:
 *     properties:
 *       parcelas:
 *         type: array
 *         items:
 *             $ref: '#/definitions/Parcela'
 */
export class ParcelaList {
  parcelas: Parcela[];

  /**
   * Class constructor
   *
   * @param {Parcela[]} parcelas
   */
  constructor(parcelas: Parcela[]) {
    this.parcelas = parcelas;
  }
}
