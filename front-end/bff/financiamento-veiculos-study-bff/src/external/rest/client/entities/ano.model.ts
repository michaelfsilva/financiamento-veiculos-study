/**
 * Ano Model class
 * 
 * @swagger
 * definitions:
 *   Ano:
 *     properties:
 *       id:
 *         type: integer
 *       name:
 *         type: string
 */
export class Ano {
    id: number;
    name: string;

    /**
     * Class constructor
     *
     * @param {number} id
     * @param {string} name
     */
    constructor(id: number, name: string) {
      this.id = id;
      this.name = name;
    }
}
