/**
 * Response Model class
 */
export class Response {
    status: number;
    message: string;
    data: any;

    /**
     * Class constructor
     *
     * @param {string} status
     * @param {string} message
     * @param {any} data
     */
    constructor(status: number, message: string, data: any) {
      this.status = status;
      this.message = message;
      this.data = data;
    }
}
