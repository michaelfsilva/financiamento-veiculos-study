import { TabelaFipeGateway } from '../../src/external/gateway/tabela-fipe.gateway';

describe('ListVehicleData', () => {
  const tabelaFipeGateway: TabelaFipeGateway = new TabelaFipeGateway();

  it('shouldReturnMarcas', (done) => {
    //given a valid marca
    const marcaExpected = 'AUDI';

    //when call the gateway method
    tabelaFipeGateway.getMarcas()
      //then values should be returned as expected
      .then((marcas) => {
        expect(marcas[0].fipeName.toUpperCase()).toBe(marcaExpected);
        done();
      }
    );
  });

  it('shouldReturnModelosByMarca', (done) => {
    //given a valid marcaId
    const marcaId = 7;

    //when call the gateway method
    tabelaFipeGateway.getModelosByMarcaId(marcaId)
      //then values should be returned as expected
      .then((modelos) => {
        expect(modelos.length).toBeGreaterThanOrEqual(1);
        done();
      }
    );
  });

  it('shouldReturnAnosByMarcaAndModelo', (done) => {
    //given a valid marcaId and modeloId
    const marcaId = 7;
    const modeloId = 6090;

    //when call the gateway method
    tabelaFipeGateway.getAnosByMarcaIdAndModeloId(marcaId, modeloId)
      //then values should be returned as expected
      .then((anos) => {
        expect(anos.length).toBeGreaterThanOrEqual(1);
        done();
      }
    );
  });

  it('shouldReturnTabelaFipeByMarcaAndModeloAndAno', (done) => {
    //given a valid marcaId and modeloId and ano
    const marcaId = 7;
    const modeloId = 6090;
    const ano = '2015-1';

    //when call the gateway method
    tabelaFipeGateway.getTabelaFipeByMarcaIdAndModeloIdAndAno(marcaId, modeloId, ano)
      //then values should be returned as expected
      .then((tabelaFipe) => {
        expect(tabelaFipe).toBeTruthy();
        done();
      }
    );
  });
});
