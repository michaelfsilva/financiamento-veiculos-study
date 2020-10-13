import axios from 'axios';
import {Marca} from './entities/marca.model';
import {Modelo} from './entities/modelo.model';
import {Ano} from './entities/ano.model';
import {TabelaFipe} from './entities/tabela-fipe.model';

/**
 * Client class to make requests to Tabela Fipe API
 */
export class TabelaFipeClient {
  /**
   * Retrieve Marcas available
   */
  async getMarcas() {
    const response: any = await axios.get('http://fipeapi.appspot.com/api/1/carros/marcas.json')
        .catch((error) => {
          console.log(error);
          throw new Error('Unable to request data from FipeAPI');
        });

    return response.data.map((data: any) => new Marca(data.id, data.fipe_name));
  }

  /**
   * Retrieve available Modelos from Marca informed
   *
   * @param {number} marcaId
   */
  async getModelosByMarcaId(marcaId: number) {
    const response: any = await axios.get(`http://fipeapi.appspot.com/api/1/carros/veiculos/${marcaId}.json`)
        .catch((error) => {
          console.log(error);
          throw new Error('Unable to request data from FipeAPI');
        });

    return response.data.map(
        (data: any) => new Modelo(data.id, data.fipe_name, data.marca),
    );
  }

  /**
   * Retrieve available years from vehicle informed
   *
   * @param {number} marcaId
   * @param {number} modeloId
   */
  async getAnosByMarcaIdAndModeloId(marcaId: number, modeloId: number) {
    const response: any = await axios.get(`http://fipeapi.appspot.com/api/1/carros/veiculo/${marcaId}/${modeloId}.json`)
        .catch((error) => {
          console.log(error);
          throw new Error('Unable to request data from FipeAPI');
        });

    return response.data.map((data: any) => new Ano(data.id, data.name));
  }

  /**
   * Retrieve Tabela Fipe from vehicle
   *
   * @param {number} marcaId
   * @param {number} modeloId
   * @param {string} ano
   */
  async getTabelaFipeByMarcaIdAndModeloIdAndAno(marcaId: number,
      modeloId: number,
      ano: string) {
    const response: any = await
    axios.get(`http://fipeapi.appspot.com/api/1/carros/veiculo/${marcaId}/${modeloId}/${ano}.json`)
        .catch((error) => {
          console.log(error);
          throw new Error('Unable to request data from FipeAPI');
        });

    return new TabelaFipe(response.data.marca, response.data.veiculo,
        response.data.ano_modelo, response.data.preco);
  }
}
