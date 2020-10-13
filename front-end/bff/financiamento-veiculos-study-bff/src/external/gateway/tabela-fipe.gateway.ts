import {Marca} from '../rest/client/entities/marca.model';
import {TabelaFipeClient} from '../rest/client/tabela-fipe.client';

/**
 * Middle class to request values to Tabela Fipe client class
 */
export class TabelaFipeGateway {
  tabelaFipeClient: TabelaFipeClient = new TabelaFipeClient();

  /**
   * Retrieve Marcas
   */
  async getMarcas(): Promise<Array<Marca>> {
    return await this.tabelaFipeClient.getMarcas();
  }

  /**
   * Retrieve Modelos by id
   *
   * @param {number} marcaId
   */
  async getModelosByMarcaId(marcaId: number): Promise<Array<Marca>> {
    return await this.tabelaFipeClient.getModelosByMarcaId(marcaId);
  }

  /**
   * Retrieve Anos by Marca and Modelo IDs
   *
   * @param {number} marcaId
   * @param {number} modeloId
   */
  async getAnosByMarcaIdAndModeloId(marcaId: number, modeloId: number) {
    return await
    this.tabelaFipeClient.getAnosByMarcaIdAndModeloId(marcaId, modeloId);
  }

  /**
   * Retrieve Tabela Fipe by Marca id, Modelo id and Ano
   *
   * @param {number} marcaId
   * @param {number} modeloId
   * @param {string} ano
   */
  async getTabelaFipeByMarcaIdAndModeloIdAndAno(marcaId: number,
      modeloId: number,
      ano: string) {
    return await
    this.tabelaFipeClient
        .getTabelaFipeByMarcaIdAndModeloIdAndAno(marcaId, modeloId, ano);
  }
}
