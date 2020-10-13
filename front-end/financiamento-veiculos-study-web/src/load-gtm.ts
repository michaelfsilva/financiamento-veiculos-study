import { environment } from 'src/environments/environment';

export class LoadGTM {
    public static carregarGTM(): void {
      if (environment.googleTagManager.enabled) {
        this.importarGTMScripts(environment.googleTagManager.container);
      }
    }

    private static importarGTMScripts(container: string): void {
      // Google Tag Manager script
      (function(w, d, s, l, i, f, j, dl) {
        w[l] = w[l] || [];
        w[l].push({'gtm.start': new Date().getTime(), event: 'gtm.js'});
        f = d.getElementsByTagName(s)[0];
        j = d.createElement(s);
        dl = l !== 'dataLayer' ? '&l=' + l : '';
        j.async = true;
        j.src = 'https://www.googletagmanager.com/gtm.js?id=' + i + dl;
        document.head.insertBefore(j, document.head.firstChild);
      })(window, document, 'script', 'dataLayer', container);

      // Google Tag Manager (noscript)
      const gtmIframe = document.createElement('iframe');
      gtmIframe.setAttribute('src', 'https://www.googletagmanager.com/ns.html?id=' + container);
      gtmIframe.setAttribute('height', '0');
      gtmIframe.setAttribute('width', '0');
      gtmIframe.setAttribute('style', 'display:none; visibility:hidden');

      const gtmNoScript = document.createElement('noscript');
      gtmNoScript.appendChild(gtmIframe);

      document.body.insertBefore(gtmNoScript, document.body.firstChild);
    }
}
