/**
 * Modelo Model class
 * 
 * @swagger
 * definitions:
 *   Modelo:
 *     properties:
 *       id:
 *         type: integer
 *       fipeName:
 *         type: string
 *       marca:
 *         type: string
 */
export class Modelo {
  id: number;
  fipeName: string;
  marca: string;

  /**
   * Class constructor
   *
   * @param {number} id
   * @param {string} fipeName
   * @param {string} marca
   */
  constructor(id: number, fipeName: string, marca: string) {
    this.id = id;
    this.fipeName = fipeName;
    this.marca = marca;
  }
}
