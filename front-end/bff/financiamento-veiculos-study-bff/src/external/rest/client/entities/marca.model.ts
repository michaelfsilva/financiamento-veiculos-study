/**
 * Marca Model class
 * 
 * @swagger
 * definitions:
 *   Marca:
 *     properties:
 *       id:
 *         type: integer
 *       fipeName:
 *         type: string
 */
export class Marca {
  id: number;
  fipeName: string;

  /**
   * Class constructor
   *
   * @param {number} id
   * @param {string} fipeName
   */
  constructor(id: number, fipeName: string) {
    this.id = id;
    this.fipeName = fipeName;
  }
}
