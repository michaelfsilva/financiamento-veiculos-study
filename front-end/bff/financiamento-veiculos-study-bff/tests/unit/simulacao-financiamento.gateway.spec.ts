import { SimulacaoFinanciamentoGateway } from '../../src/external/gateway/simulacao-financiamento.gateway'
import { SimulacaoFinanciamentoClient } from '../../src/external/rest/client/simulacao-financiamento.client';
import { AxiosResponse, AxiosError } from 'axios';

function mockClient(serviceResponse: Promise<AxiosResponse>) {
  const mock = jest.fn();
  SimulacaoFinanciamentoClient.prototype.simular = mock;
  mock.mockReturnValue(serviceResponse);
}

describe('SimulateFinancing', () => {
  const simulacaoFinanciamentoGateway: SimulacaoFinanciamentoGateway = new SimulacaoFinanciamentoGateway();

  it('shouldReturnParcelas', (done) => {
    //given a valid request
    const request = {
      body: {
        clienteRequest: {
          nome: 'Teste',
          cpf: '123.456.789-00',
          dataNascimento: '1990-12-03',
          telefone: '19 98765-4321',
          renda: 5200.10,
          possuiImovel: true
        },
        simulacaoRequest: {
          valorEntrada: 8560.90,
          valorVeiculo: 70000.00
        },
      }
    };

    //and valid parcela
    const parcelaExpected = '{"meses":12,"valor":5493.34}';

    //when call the gateway method
    simulacaoFinanciamentoGateway.simular(request)
      //then values should be returned as expected
      .then((response) => {
        const parcelasStr = JSON.stringify(response.data.parcelas[0]);
        expect(parcelasStr).toBe(parcelaExpected);
        done();
      }
    );
  });

  it('shouldReturnParcelasMock', (done) => {
    //given a mocked request
    const mockRequest = {
      body: {
        clienteRequest: {
          nome: 'Teste Mock',
          cpf: '123.456.789-01',
          dataNascimento: '1990-12-03',
          telefone: '19 98765-4321',
          renda: 5000.00,
          possuiImovel: true
        },
        simulacaoRequest: {
          valorEntrada: 8560.90,
          valorVeiculo: 70000.00
        },
      }
    };

    //and a mock response
    const mockResponse = {
      status: 200,
      data: {
        messages: null,
        data: {
          parcelas: [
            {
              meses: 12,
              valor: 7014.233
            }
          ]
        }
      }
    } as AxiosResponse;

    mockClient(Promise.resolve(mockResponse));

    //when call the simular method
    simulacaoFinanciamentoGateway.simular(mockRequest)
      //then parcelas should not be null
      .then((parcelas) => {
        const parcelasStr = JSON.stringify(parcelas);
        expect(parcelasStr).toContain('parcelas');
        done();
      });
  });

  it('shouldReturn404Error', (done) => {
    //given a mocked request and response
    const mockRequest = {};
    const mockResponse = {};

    mockClient(Promise.reject(mockResponse));

    //when call the simular method
    simulacaoFinanciamentoGateway.simular(mockRequest)
      //then parcelas should not be null
      .then((erroResponse) => {
        const errorResponseStr = JSON.stringify(erroResponse);
        expect(errorResponseStr).toContain('404');
        done();
      });
  });

  it('shouldReturn400Error', (done) => {
    //given a mocked request and response
    const mockRequest = {};
    const mockResponse = {
      response: {
        status: 400,
        data: {
          messages: [''],
          data: null
        }
      }
    } as AxiosError;

    mockClient(Promise.reject(mockResponse));

    //when call the simular method
    simulacaoFinanciamentoGateway.simular(mockRequest)
      //then parcelas should not be null
      .then((erroResponse) => {
        const errorResponseStr = JSON.stringify(erroResponse);
        expect(errorResponseStr).toContain('400');
        done();
      });
  });

  it('shouldReturn422Error', (done) => {
    //given a mocked request and response
    const mockRequest = {};
    const mockResponse = {
      response: {
        status: 422,
        data: {
          messages: ['CPF já registrado com outro Nome'],
          data: null
        }
      }
    } as AxiosError;

    mockClient(Promise.reject(mockResponse));

    //when call the simular method
    simulacaoFinanciamentoGateway.simular(mockRequest)
      //then parcelas should not be null
      .then((erroResponse) => {
        const errorResponseStr = JSON.stringify(erroResponse);
        expect(errorResponseStr).toContain('422');
        done();
      });
  });
});
