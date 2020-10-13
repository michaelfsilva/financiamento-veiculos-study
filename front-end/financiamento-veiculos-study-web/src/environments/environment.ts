// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const url = 'http://' + window.location.href.split('/')[2].split(':')[0] + ':8000/api/';

export const environment = {
  production: false,
  api: {
    marcas: url + 'veiculos/marcas',
    modelos: url + 'veiculos/modelos',
    simular: url + 'financiamento/simular'
  },
  googleTagManager: {
    container: 'GTM-MVLRV6K',
    data: 'dataLayer',
    enabled: true
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.